package com.zwj.blog.controller.admin;

import com.github.pagehelper.PageInfo;
import com.zwj.blog.model.Bo.ForeResponse;
import com.zwj.blog.model.Vo.User;
import com.zwj.blog.service.LogService;
import com.zwj.blog.constant.BlogConst;
import com.zwj.blog.controller.BaseController;
import com.zwj.blog.dto.LogActions;
import com.zwj.blog.dto.SiteProperty;
import com.zwj.blog.model.Vo.Article;
import com.zwj.blog.model.Vo.ArticleExample;
import com.zwj.blog.service.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


@Controller()
@RequestMapping("admin/page")
public class PageController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PageController.class);

    @Resource
    private ArticleService contentsService;

    @Resource
    private LogService logService;

    @GetMapping(value = "")
    public String index(HttpServletRequest request) {
        ArticleExample articleExample = new ArticleExample();
        articleExample.setOrderByClause("created desc");
        articleExample.createCriteria().andTypeEqualTo(SiteProperty.PAGE.getProperty());
        PageInfo<Article> contentsPaginator = contentsService.getArticlesWithpage(articleExample, 1, BlogConst.MAX_POSTS);
        request.setAttribute("articles", contentsPaginator);
        return "admin/page_list";
    }

    @GetMapping(value = "new")
    public String newPage(HttpServletRequest request) {
        return "admin/page_edit";
    }

    @GetMapping(value = "/{cid}")
    public String editPage(@PathVariable String cid, HttpServletRequest request) {
        Article contents = contentsService.getArticles(cid);
        request.setAttribute("contents", contents);
        return "admin/page_edit";
    }

    @PostMapping(value = "publish")
    @ResponseBody
    public ForeResponse publishPage(@RequestParam String title, @RequestParam String content,
                                    @RequestParam String status, @RequestParam String slug,
                                    @RequestParam(required = false) Integer allowComment, @RequestParam(required = false) Integer allowPing, HttpServletRequest request) {

        User users = this.getCurrentLogin(request);
        Article contents = new Article();
        contents.setTitle(title);
        contents.setContent(content);
        contents.setStatus(status);
        contents.setSlug(slug);
        contents.setType(SiteProperty.PAGE.getProperty());
        if (null != allowComment) {
            contents.setAllowComment(allowComment == 1);
        }
        if (null != allowPing) {
            contents.setAllowPing(allowPing == 1);
        }
        contents.setAuthorId(users.getUid());
        String result = contentsService.publishArticle(contents);
        if (!BlogConst.SUCCESS_RESPONSE_MESSAGE.equals(result)) {
            return ForeResponse.fail(result);
        }
        return ForeResponse.success();
    }

    @PostMapping(value = "modify")
    @ResponseBody
    public ForeResponse modifyArticle(@RequestParam Integer cid, @RequestParam String title,
                                      @RequestParam String content,
                                      @RequestParam String status, @RequestParam String slug,
                                      @RequestParam(required = false) Integer allowComment, @RequestParam(required = false) Integer allowPing, HttpServletRequest request) {

        User users = this.getCurrentLogin(request);
        Article contents = new Article();
        contents.setCid(cid);
        contents.setTitle(title);
        contents.setContent(content);
        contents.setStatus(status);
        contents.setSlug(slug);
        contents.setType(SiteProperty.PAGE.getProperty());
        if (null != allowComment) {
            contents.setAllowComment(allowComment == 1);
        }
        if (null != allowPing) {
            contents.setAllowPing(allowPing == 1);
        }
        contents.setAuthorId(users.getUid());
        String result = contentsService.modifyArticle(contents);
        if (!BlogConst.SUCCESS_RESPONSE_MESSAGE.equals(result)) {
            return ForeResponse.fail(result);
        }
        return ForeResponse.success();
    }

    @RequestMapping(value = "delete")
    @ResponseBody
    public ForeResponse delete(@RequestParam int cid, HttpServletRequest request) {
        String result = contentsService.deleteByCid(cid);
        logService.insertLog(LogActions.DEL_ARTICLE.getAction(), cid + "", request.getRemoteAddr(), this.getUid(request));
        if (!BlogConst.SUCCESS_RESPONSE_MESSAGE.equals(result)) {
            return ForeResponse.fail(result);
        }
        return ForeResponse.success();
    }
}
