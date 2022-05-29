package com.zwj.blog.controller.admin;


import com.github.pagehelper.PageInfo;
import com.zwj.blog.constant.BlogConst;
import com.zwj.blog.controller.BaseController;
import com.zwj.blog.dto.LogActions;
import com.zwj.blog.dto.SiteProperty;
import com.zwj.blog.exception.TipException;
import com.zwj.blog.model.Bo.ForeResponse;
import com.zwj.blog.model.Vo.Article;
import com.zwj.blog.model.Vo.ArticleExample;
import com.zwj.blog.model.Vo.Meta;
import com.zwj.blog.model.Vo.User;
import com.zwj.blog.service.ArticleService;
import com.zwj.blog.service.LogService;
import com.zwj.blog.service.PropertyService;
import org.jboss.logging.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
@RequestMapping("/admin/article")
@Transactional(rollbackFor = TipException.class)
public class ArticleController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleController.class);

    @Resource
    private ArticleService articleService;

    @Resource
    private PropertyService metasService;

    @Resource
    private LogService logService;

    @GetMapping(value = "")
    public String index(@RequestParam(value = "page", defaultValue = "1") int page,
                        @RequestParam(value = "limit", defaultValue = "15") int limit, HttpServletRequest request) {
        ArticleExample articleExample = new ArticleExample();
        articleExample.setOrderByClause("created desc");
        articleExample.createCriteria().andTypeEqualTo(SiteProperty.ARTICLE.getProperty());
        PageInfo<Article> contentsPaginator = articleService.getArticlesWithpage(articleExample, page, limit);
        request.setAttribute("articles", contentsPaginator);
        return "admin/article_list";
    }

    @GetMapping(value = "/publish")
    public String newArticle(HttpServletRequest request) {
        List<Meta> categories = metasService.getMetas(SiteProperty.CATEGORY.getProperty());
        request.setAttribute("categories", categories);
        return "admin/article_edit";
    }

    @GetMapping(value = "/edit")
    public String editArticle(@RequestParam String cid, HttpServletRequest request) {
        Article contents = articleService.getArticlesWithAdmin(cid);
        request.setAttribute("contents", contents);
        List<Meta> categories = metasService.getMetas(SiteProperty.CATEGORY.getProperty());
        request.setAttribute("categories", categories);
        request.setAttribute("active", "article");
        return "admin/article_edit";
    }

    @PostMapping("/publish")
    @ResponseBody
    public ForeResponse publishArticle(Article article, HttpServletRequest req) {
        User users = this.getCurrentLogin(req);
        article.setAuthorId(users.getUid());
        article.setType(SiteProperty.ARTICLE.getProperty());
        if (article.getCategories() != null && article.getCategories().length() != 0) {
            article.setCategories("默认分类");
        }
        String result = articleService.publishArticle(article);
        if (!BlogConst.SUCCESS_RESPONSE_MESSAGE.equals(result)) {
            return ForeResponse.fail(result);
        }
        return ForeResponse.success("发布成功");
    }

    @PostMapping("/modify")
    @ResponseBody
    public ForeResponse modifyArticle(Article article, HttpServletRequest req) {
        User user = this.getCurrentLogin(req);
        article.setAuthorId(user.getUid());
        article.setType(SiteProperty.ARTICLE.getProperty());
        String result = articleService.modifyArticle(article);
        if (!BlogConst.SUCCESS_RESPONSE_MESSAGE.equals(result)) {
            return ForeResponse.fail(result);
        }
        return ForeResponse.success();
    }


    @RequestMapping(value = "/delete")
    @ResponseBody
    public ForeResponse delete(@RequestParam int cid, HttpServletRequest request) {
        String result = articleService.deleteByCid(cid);
        logService.insertLog(LogActions.DEL_ARTICLE.getAction(), cid + "", request.getRemoteAddr(), this.getUid(request));
        if (!BlogConst.SUCCESS_RESPONSE_MESSAGE.equals(result)) {
            return ForeResponse.fail(result);
        }
        return ForeResponse.success();
    }
}
