package com.zwj.blog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zwj.blog.constant.BlogConst;
import com.zwj.blog.dao.ArticleMapper;
import com.zwj.blog.dao.PropertyMapper;
import com.zwj.blog.dto.SiteProperty;
import com.zwj.blog.exception.TipException;
import com.zwj.blog.model.Vo.Article;
import com.zwj.blog.model.Vo.ArticleExample;
import com.zwj.blog.service.ArticleService;
import com.zwj.blog.service.ArticleTagRelationService;
import com.zwj.blog.service.PropertyService;
import com.zwj.blog.utils.DateKit;
import com.zwj.blog.utils.Utils;
import com.zwj.blog.utils.Tools;
import com.vdurmont.emoji.EmojiParser;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleServiceImpl.class);

    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private PropertyMapper metaDao;

    @Resource
    private ArticleTagRelationService articleTagRelationService;

    @Resource
    private PropertyService metasService;

    @Override
    @Transactional
    public String publishArticle(Article contents) {
        if (null == contents) {
            return "文章对象为空";
        }
        if (StringUtils.isBlank(contents.getTitle())) {
            return "文章标题不能为空";
        }
        if (StringUtils.isBlank(contents.getContent())) {
            return "文章内容不能为空";
        }
        int titleLength = contents.getTitle().length();
        if (titleLength > BlogConst.MAX_TITLE_COUNT) {
            return "文章标题过长";
        }
        int contentLength = contents.getContent().length();
        if (contentLength > BlogConst.MAX_TEXT_COUNT) {
            return "文章内容过长";
        }
        if (null == contents.getAuthorId()) {
            return "请登录后发布文章";
        }
        if (StringUtils.isNotBlank(contents.getSlug())) {
            if (contents.getSlug().length() < 5) {
                return "路径太短了";
            }
            if (!Utils.isPath(contents.getSlug())) return "您输入的路径不合法";
            ArticleExample articleExample = new ArticleExample();
            articleExample.createCriteria().andTypeEqualTo(contents.getType()).andStatusEqualTo(contents.getSlug());
            long count = articleMapper.countByExample(articleExample);
            if (count > 0) return "该路径已经存在，请重新输入";
        } else {
            contents.setSlug(null);
        }

        contents.setContent(EmojiParser.parseToAliases(contents.getContent()));

        int time = DateKit.getCurrentUnixTime();
        contents.setCreated(time);
        contents.setModified(time);
        contents.setHits(0);
        contents.setCommentsNum(0);

        String tags = contents.getTags();
        String categories = contents.getCategories();
        int insert = articleMapper.insert(contents);
        Integer cid = contents.getCid();
        metasService.saveMetas(cid, tags, SiteProperty.TAG.getProperty());
        metasService.saveMetas(cid, categories, SiteProperty.CATEGORY.getProperty());
        return BlogConst.SUCCESS_RESPONSE_MESSAGE;
    }

    /***
     * description: 查询所有文章分页展示
     * @param: [page, maxPageNumber]
     * @return: com.github.pagehelper.PageInfo<com.my.blog.website.model.Vo.Article>
     * @author zwj
     */
    @Override
    public PageInfo<Article> getArticles(int page, int maxPageNumber) {
        LOGGER.debug("Enter getContents method");
        ArticleExample articleExample = new ArticleExample();
        articleExample.createCriteria().andStatusEqualTo(SiteProperty.PUBLISH.getProperty());
        articleExample.createCriteria().andTypeEqualTo(SiteProperty.ARTICLE.getProperty());
        articleExample.setOrderByClause("created desc");
        PageHelper.startPage(page, maxPageNumber);
        List<Article> articleList = articleMapper.selectByExampleWithBLOBs(articleExample);
        PageInfo<Article> contents = new PageInfo<>(articleList);
        LOGGER.debug("Exit getContents method");
        return contents;
    }

    @Override
    public int getArticleNumsByMetaId(Integer mid) {
        return metaDao.countWithSql(mid);

    }

    @Override
    public Article getArticles(String id) {
        if (StringUtils.isNotBlank(id)) {
            if (Tools.isNumber(id)) {
                ArticleExample articleExample = new ArticleExample();
                ArticleExample.Criteria criteria = articleExample.createCriteria();
                criteria.andCidEqualTo(Integer.valueOf(id)).andStatusEqualTo("publish");
                List<Article> articles = articleMapper.selectByExampleWithBLOBs(articleExample);
                if (articles.size() == 0) {
                    return null;
                }
                if (articles.size() != 1) {
                    throw new RuntimeException("query content by id and return is not one or no return");
                }
                return articles.get(0);
            } else {
                ArticleExample articleExample = new ArticleExample();
                articleExample.createCriteria().andSlugEqualTo(id).andStatusEqualTo("publish");

                List<Article> contentVos = articleMapper.selectByExampleWithBLOBs(articleExample);
                if (contentVos == null || contentVos.size() != 1) {
                    throw new TipException("query content by id and return is not one");
                }
                return contentVos.get(0);
            }
        }
        return null;
    }

    @Override
    public Article getArticlesWithAdmin(String id) {
        if (StringUtils.isNotBlank(id)) {
            if (Tools.isNumber(id)) {
                Article article = articleMapper.selectByPrimaryKey(Integer.valueOf(id));
                return article;
            } else {
                ArticleExample articleExample = new ArticleExample();
                articleExample.createCriteria().andSlugEqualTo(id);
                List<Article> contentVos = articleMapper.selectByExampleWithBLOBs(articleExample);
                if (contentVos.size() != 1) {
                    throw new TipException("query content by id and return is not one");
                }
                return contentVos.get(0);
            }
        }
        return null;
    }

    @Override
    public void updateArticleById(Article contentVo) {
        if (null != contentVo && null != contentVo.getCid()) {
            articleMapper.updateByPrimaryKeySelective(contentVo);
        }
    }

    @Override
    public PageInfo<Article> getArticles(Integer mid, int page, int limit) {
        int total = metaDao.countWithSql(mid);
        PageHelper.startPage(page, limit);
        List<Article> list = articleMapper.findByCatalog(mid);
        PageInfo<Article> paginator = new PageInfo<>(list);
        paginator.setTotal(total);
        return paginator;
    }

    @Override
    public PageInfo<Article> getArticles(String keyword, Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        ArticleExample articleExample = new ArticleExample();
        ArticleExample.Criteria criteria = articleExample.createCriteria();
        criteria.andTypeEqualTo(SiteProperty.ARTICLE.getProperty());
        criteria.andStatusEqualTo(SiteProperty.PUBLISH.getProperty());
        criteria.andTitleLike("%" + keyword + "%");
        articleExample.setOrderByClause("created desc");
        List<Article> contentVos = articleMapper.selectByExampleWithBLOBs(articleExample);
        return new PageInfo<>(contentVos);
    }

    @Override
    public PageInfo<Article> getArticlesWithpage(ArticleExample commentVoExample, Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        List<Article> contentVos = articleMapper.selectByExampleWithBLOBs(commentVoExample);
        return new PageInfo<>(contentVos);
    }

    @Override
    @Transactional
    public String deleteByCid(Integer cid) {
        Article contents = this.getArticles(cid + "");
        if (null != contents) {
            articleMapper.deleteByPrimaryKey(cid);
            articleTagRelationService.deleteById(cid, null);
            return BlogConst.SUCCESS_RESPONSE_MESSAGE;
        }
        return "数据为空";
    }

    @Override
    public void updateCategory(String ordinal, String newCatefory) {
        Article contentVo = new Article();
        contentVo.setCategories(newCatefory);
        ArticleExample example = new ArticleExample();
        example.createCriteria().andCategoriesEqualTo(ordinal);
        articleMapper.updateByExampleSelective(contentVo, example);
    }

    @Override
    @Transactional
    public String modifyArticle(Article contents) {
        if (null == contents) {
            return "文章对象为空";
        }
        if (StringUtils.isBlank(contents.getTitle())) {
            return "文章标题不能为空";
        }
        if (StringUtils.isBlank(contents.getContent())) {
            return "文章内容不能为空";
        }
        int titleLength = contents.getTitle().length();
        if (titleLength > BlogConst.MAX_TITLE_COUNT) {
            return "文章标题过长";
        }
        int contentLength = contents.getContent().length();
        if (contentLength > BlogConst.MAX_TEXT_COUNT) {
            return "文章内容过长";
        }
        if (null == contents.getAuthorId()) {
            return "请登录后发布文章";
        }
        if (StringUtils.isBlank(contents.getSlug())) {
            contents.setSlug(null);
        }
        int time = DateKit.getCurrentUnixTime();
        contents.setModified(time);
        Integer cid = contents.getCid();
        contents.setContent(EmojiParser.parseToAliases(contents.getContent()));

        articleMapper.updateByPrimaryKeySelective(contents);
        articleTagRelationService.deleteById(cid, null);
        metasService.saveMetas(cid, contents.getTags(), SiteProperty.TAG.getProperty());
        metasService.saveMetas(cid, contents.getCategories(), SiteProperty.CATEGORY.getProperty());
        return BlogConst.SUCCESS_RESPONSE_MESSAGE;
    }
}
