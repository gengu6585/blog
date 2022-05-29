package com.zwj.blog.controller;

import com.github.pagehelper.PageInfo;
import com.vdurmont.emoji.EmojiParser;
import com.zwj.blog.constant.BlogConst;
import com.zwj.blog.dto.MetaDto;
import com.zwj.blog.dto.SiteProperty;
import com.zwj.blog.model.Bo.ArchiveBo;
import com.zwj.blog.model.Bo.CommentBo;
import com.zwj.blog.model.Bo.ForeResponse;
import com.zwj.blog.model.Vo.Comment;
import com.zwj.blog.model.Vo.Article;
import com.zwj.blog.model.Vo.Meta;
import com.zwj.blog.service.CommentService;
import com.zwj.blog.service.ArticleService;
import com.zwj.blog.service.PropertyService;
import com.zwj.blog.service.SiteService;
import com.zwj.blog.utils.WebUtil;
import com.zwj.blog.utils.RegExUtils;
import com.zwj.blog.utils.Utils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
public class IndexController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);

    @Resource
    private ArticleService articleService;

    @Resource
    private CommentService commentService;

    @Resource
    private PropertyService propertyService;

    @Resource
    private SiteService siteService;


    /**
     * 首页
     */
    @GetMapping(value = "/")
    public String foreIndex(HttpServletRequest request, @RequestParam(value = "limit", defaultValue = "8") int limit) {

        return this.foreIndex(request, limit, 1);
    }

    /***
     * description: 默认每页显示10篇文章
     * @param: [req, maxPageNumber, p]
     * @return: java.lang.String
     * @author zwj
     */
    @GetMapping(value = "page/{p}")
    public String foreIndex(HttpServletRequest req, @RequestParam(value = "limit", defaultValue = "8") int maxPageNumber, @PathVariable int p) {
        PageInfo<Article> articles = articleService.getArticles(p, maxPageNumber);
        req.setAttribute("articles", articles);
        if (p > BlogConst.MAX_PAGE || p < 0) {
            p = 1;
        }
        if (p != 1) {
            this.title(req, "第" + p + "页");
        }
        return setTheme("DIYgod");
    }

    /***
     * description: 文章页，根据ID查询文章，查找失败跳转到404
     * @param: [request, cid]
     * @return: java.lang.String
     * @author zwj
     */
    @GetMapping(value = {"article/{articleID}", "article/{articleID}.html"})
    public String getArticle(HttpServletRequest req, @PathVariable String articleID) {
        Article blog = articleService.getArticles(articleID);
        //若查询到的文章为空或状态为草稿返回到404页面
        if (blog == null || blog.getStatus().equals("draft")) {
            return forwardTo404();
        }

        //把文章对应的评论注入到request对象中
        AddArticleComments(req, blog);
        //更新文章的阅读数
        if (!isRecentlyRead(req, articleID)) {
            ArticleReadIngNumsAdd(blog.getCid(), blog.getHits());
        }
        req.setAttribute("is_post", true);
        req.setAttribute("article", blog);
        String name = blog.getTitle();
        return "forward:" + "/article/view/" + name;
    }

    //用于id到文章名的映射
    @GetMapping(value = {"article/view/{name}"})
    public String getArticle(@PathVariable String name) {
        return setTheme("DIYgodPost");
    }

    /**
     * 文章页(预览)
     *
     * @param request 请求
     * @param cid     文章主键
     * @return
     */
    @GetMapping(value = {"/admin/article/preview"})
    public String articlePreview(HttpServletRequest request, @RequestParam(required = true) String cid) {
        Article contents = articleService.getArticlesWithAdmin(cid);
        if (null == contents) {
            return this.forwardTo404();
        }
        request.setAttribute("article", contents);
        request.setAttribute("is_post", true);
        AddArticleComments(request, contents);
        if (!isRecentlyRead(request, cid)) {
            ArticleReadIngNumsAdd(contents.getCid(), contents.getHits());
        }
        return this.setTheme("DIYgodPost");


    }


    /**
     * 抽取公共方法
     *
     * @param request
     * @param contents
     */
    private void AddArticleComments(HttpServletRequest request, Article contents) {
        if (contents.getAllowComment()) {
            String cp = request.getParameter("cp");
            if (StringUtils.isBlank(cp)) {
                cp = "1";
            }
            request.setAttribute("cp", cp);
            PageInfo<CommentBo> commentsPaginator = commentService.getComments(contents.getCid(), Integer.parseInt(cp), 6);
            request.setAttribute("comments", commentsPaginator);
        }
    }

    /**
     * 注销
     *
     * @param session
     * @param response
     */
    @RequestMapping("logout")
    public void logout(HttpSession session, HttpServletResponse response) {
        Utils.logout(session, response);
    }

    /***
     * description: 文章评论
     * @param: [req, resp, _csrf_token, text, comment]
     * @return: com.my.blog.website.model.Bo.ForeResponse
     * @author zwj
     */
    @PostMapping(value = "comment")
    @ResponseBody
    public ForeResponse comment(HttpServletRequest req, HttpServletResponse resp,
                                @RequestParam String _csrf_token, @RequestParam(required = false) String text, Comment comment) {
        if (comment == null) {
            comment = new Comment();
        }
        comment.setContent(text);
        String ref = req.getHeader("Referer");
        if (StringUtils.isBlank(ref) || StringUtils.isBlank(_csrf_token)) {
            return ForeResponse.fail("非法请求");
        }

        String token = cache.hget(SiteProperty.TOKEN.getProperty(), _csrf_token);
        if (token == null || token.equals("")) {
            return ForeResponse.fail("非法请求");
        }

        if (text == null || text.equals("")) {
            return ForeResponse.fail("评论内容为空");
        }

        if (comment.getAuthor() != null && comment.getAuthor().length() > 50) {
            return ForeResponse.fail("昵称过长");
        }

        if (comment.getMail() != null && comment.getMail().length() != 0 && !Utils.isEmail(comment.getMail())) {
            return ForeResponse.fail("邮箱格式错误");
        }

        if (comment.getUrl() != null && comment.getUrl().length() != 0 && !RegExUtils.isURL(comment.getUrl())) {
            return ForeResponse.fail("url格式错误");
        }

        if (text.length() > 100) {
            return ForeResponse.fail("评论请控制在100字以内");
        }

        String val = WebUtil.getIpOfUser(req) + ":" + comment.getCid();
        Integer count = cache.hget(SiteProperty.IsRecentCommented.getProperty(), val);
        if (null != count && count > 0) {
            return ForeResponse.fail("评论过于频繁，请稍后再试");
        }
        //过滤html无法直接展示的字符
        comment.setAuthor(Utils.filtratingtHtml(comment.getAuthor()));
        comment.setContent(Utils.filtratingtHtml(comment.getContent()));
        comment.setAuthor(EmojiParser.parseToAliases(comment.getAuthor()));
        comment.setContent(EmojiParser.parseToAliases(comment.getContent()));
        comment.setIp(req.getRemoteAddr());
        comment.setParent(comment.getCoid());
        comment.setCoid(null);
        try {
            String result = commentService.saveComment(comment);
            //为访客设置时效7天的cookie保存访客的上次输入的昵称和邮箱
            cookie("tale_remember_author", URLEncoder.encode(comment.getAuthor(), "UTF-8"), 604800, resp);
            cookie("tale_remember_mail", URLEncoder.encode(comment.getMail(), "UTF-8"), 604800, resp);
            if (StringUtils.isNotBlank(comment.getUrl())) {
                cookie("tale_remember_url", URLEncoder.encode(comment.getUrl(), "UTF-8"), 604800, resp);
            }
            cache.hset(SiteProperty.IsRecentCommented.getProperty(), val, 1, BlogConst.COMMENT_FREQUENCY_TIME);
            if (!BlogConst.SUCCESS_RESPONSE_MESSAGE.equals(result)) {
                return ForeResponse.fail(result);
            }
            return ForeResponse.success();
        } catch (Exception e) {
            String msg = "评论失败";
            LOGGER.error(msg, e);
            return ForeResponse.fail(msg);
        }
    }


    /**
     * 分类页
     *
     * @return
     */
    @GetMapping(value = "category/{keyword}")
    public String categories(HttpServletRequest request, @PathVariable String keyword, @RequestParam(value = "limit", defaultValue = "8") int limit) {
        return this.categories(request, keyword, 1, limit);
    }

    @GetMapping(value = "category/{keyword}/{page}")
    public String categories(HttpServletRequest request, @PathVariable String keyword,
                             @PathVariable int page, @RequestParam(value = "limit", defaultValue = "8") int limit) {
        page = page < 0 || page > BlogConst.MAX_PAGE ? 1 : page;
        MetaDto metaDto = propertyService.getMeta(SiteProperty.CATEGORY.getProperty(), keyword);
        if (null == metaDto) {
            return this.forwardTo404();
        }

        PageInfo<Article> contentsPaginator = articleService.getArticles(metaDto.getMid(), page, limit);

        request.setAttribute("articles", contentsPaginator);
        request.setAttribute("meta", metaDto);
        request.setAttribute("type", "分类");
        request.setAttribute("keyword", keyword);

        return this.setTheme("DIYgodPage-category");
    }


    /**
     * 归档页
     *
     * @return
     */
    @GetMapping(value = "/archives")
    public String archives(HttpServletRequest request) {
        List<ArchiveBo> archives = siteService.getArchives();
        List<Meta> categories = propertyService.getMetas(SiteProperty.CATEGORY.getProperty());
        HashMap<String, Integer> categoriesMap = new HashMap<>();
        for (Meta category : categories) {
            categoriesMap.put(category.getName(), articleService.getArticleNumsByMetaId(category.getMid()));
        }
        Set<Map.Entry<String, Integer>> entries = categoriesMap.entrySet();
        request.setAttribute("archives", archives);
        request.setAttribute("categoriesEntries", categoriesMap);
        return this.setTheme("DIYgodArchives");
    }

    /**
     * 友链页
     *
     * @return
     */
    @GetMapping(value = "links")
    public String links(HttpServletRequest request) {
        List<Meta> links = propertyService.getMetas(SiteProperty.LINK.getProperty());
        request.setAttribute("links", links);
        return this.setTheme("links");
    }

    /**
     * 自定义页面,如关于的页面
     */
    @GetMapping(value = "/selfpage/{pagename}")
    public String page(@PathVariable String pagename, HttpServletRequest request) {
        Article contents = articleService.getArticles(pagename);
        if (null == contents) {
            return this.forwardTo404();
        }
        if (contents.getAllowComment()) {
            String cp = request.getParameter("cp");
            if (StringUtils.isBlank(cp)) {
                cp = "1";
            }
            PageInfo<CommentBo> commentsPaginator = commentService.getComments(contents.getCid(), Integer.parseInt(cp), 6);
            request.setAttribute("comments", commentsPaginator);
        }
        request.setAttribute("article", contents);
        if (!isRecentlyRead(request, String.valueOf(contents.getCid()))) {
            ArticleReadIngNumsAdd(contents.getCid(), contents.getHits());
        }
        return this.setTheme("DIYgodPost");
    }


    /**
     * 搜索页
     *
     * @param keyword
     * @return
     */
    @GetMapping(value = "search/{keyword}")
    public String search(HttpServletRequest request, @PathVariable String keyword, @RequestParam(value = "limit", defaultValue = "8") int limit) {
        return this.search(request, keyword, 1, limit);
    }

    @GetMapping(value = "search/{keyword}/{page}")
    public String search(HttpServletRequest request, @PathVariable String keyword, @PathVariable int page, @RequestParam(value = "limit", defaultValue = "8") int limit) {
        page = page < 0 || page > BlogConst.MAX_PAGE ? 1 : page;
        PageInfo<Article> articles = articleService.getArticles(keyword, page, limit);
        request.setAttribute("articles", articles);
        request.setAttribute("type", "搜索");
        request.setAttribute("keyword", keyword);
        return this.setTheme("DIYgodPage-category");
    }

    /***
     * description: 文章点击量计算
     * @param: [articleID, readTiems]
     * @return: void
     * @author zwj
     */
    private void ArticleReadIngNumsAdd(Integer articleID, Integer readTiems) {
        Integer readTiemsInCache = cache.hget("article" + articleID, "read_times");
        if (readTiems == null) {
            readTiems = 0;
        }
        readTiemsInCache = null == readTiemsInCache ? 1 : readTiemsInCache + 1;
        //当缓存中的阅读数达到一个阈值时，更新数据库的阅读数
        if (readTiemsInCache >= BlogConst.READ_TIMES_UPDATE_THRESHOLD) {
            Article article = new Article();
            article.setCid(articleID);
            article.setHits(readTiems + readTiemsInCache);
            articleService.updateArticleById(article);
            cache.hset("article" + articleID, "read_times", 1);
        } else {
            cache.hset("article" + articleID, "read_times", readTiemsInCache);
        }
    }

    /**
     * 标签页
     *
     * @param name
     * @return
     */
    @GetMapping(value = "tag/{name}")
    public String tags(HttpServletRequest request, @PathVariable String name, @RequestParam(value = "limit", defaultValue = "8") int limit) {
        return this.tags(request, name, 1, limit);
    }

    /**
     * 标签分页
     *
     * @param request
     * @param name
     * @param page
     * @param limit
     * @return
     */
    @GetMapping(value = "tag/{name}/{page}")
    public String tags(HttpServletRequest request, @PathVariable String name, @PathVariable int page, @RequestParam(value = "limit", defaultValue = "8") int limit) {

        page = page < 0 || page > BlogConst.MAX_PAGE ? 1 : page;
//        对于空格的特殊处理
        name = name.replaceAll("\\+", " ");
        MetaDto metaDto = propertyService.getMeta(SiteProperty.TAG.getProperty(), name);
        if (null == metaDto) {
            return this.forwardTo404();
        }

        PageInfo<Article> contentsPaginator = articleService.getArticles(metaDto.getMid(), page, limit);
        request.setAttribute("articles", contentsPaginator);
        request.setAttribute("meta", metaDto);
        request.setAttribute("type", "标签");
        request.setAttribute("keyword", name);

        return this.setTheme("DIYgodPage-category");
    }

    /**
     * 设置cookie
     *
     * @param name
     * @param value
     * @param maxAge
     * @param response
     */
    private void cookie(String name, String value, int maxAge, HttpServletResponse response) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAge);
        cookie.setSecure(false);
        response.addCookie(cookie);
    }

    /***
     * description: 检查一个小时内是否是同一个IP在点击
     * @param: [request, cid]
     * @return: boolean
     * @author zwj
     */
    private boolean isRecentlyRead(HttpServletRequest request, String cid) {
        String ipAddress = WebUtil.getIpOfUser(request) + ":" + cid;
        Integer readTimes = cache.hget(SiteProperty.IsRecentRead.getProperty(), ipAddress);
        if (null != readTimes && readTimes > 0) {
            return true;
        }
        cache.hset(SiteProperty.IsRecentRead.getProperty(), ipAddress, 1, BlogConst.RECENT_READ_TIME_SPAN);
        return false;
    }

}
