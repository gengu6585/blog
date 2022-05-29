package com.zwj.blog.service;

import com.github.pagehelper.PageInfo;
import com.zwj.blog.model.Vo.ArticleExample;
import com.zwj.blog.model.Vo.Article;

public interface ArticleService {

//    /**
//     * 保存文章
//     * @param contentVo contentVo
//     */
//    void insertContent(ContentVo contentVo);

    /**
     * 发布文章
     *
     * @param contents
     */
    String publishArticle(Article contents);

    /**
     * 查询文章返回多条数据
     *
     * @param page          当前页
     * @param maxPageNumber 每页条数
     * @return ContentVo
     */
    PageInfo<Article> getArticles(int page, int maxPageNumber);

    int getArticleNumsByMetaId(Integer mid);

    /**
     * 根据id或slug获取文章
     *
     * @param id id
     * @return ContentVo
     */
    Article getArticles(String id);

    public Article getArticlesWithAdmin(String id);

    /**
     * 根据主键更新
     *
     * @param contentVo contentVo
     */
    void updateArticleById(Article contentVo);


    /**
     * 查询分类/标签下的文章归档
     *
     * @param mid   mid
     * @param page  page
     * @param limit limit
     * @return ContentVo
     */
    PageInfo<Article> getArticles(Integer mid, int page, int limit);

    /**
     * 搜索、分页
     *
     * @param keyword keyword
     * @param page    page
     * @param limit   limit
     * @return ContentVo
     */
    PageInfo<Article> getArticles(String keyword, Integer page, Integer limit);


    /**
     * @param commentVoExample
     * @param page
     * @param limit
     * @return
     */
    PageInfo<Article> getArticlesWithpage(ArticleExample commentVoExample, Integer page, Integer limit);

    /**
     * 根据文章id删除
     *
     * @param cid
     */
    String deleteByCid(Integer cid);

    /**
     * 编辑文章
     *
     * @param contents
     */
    String modifyArticle(Article contents);


    /**
     * 更新原有文章的category
     *
     * @param ordinal
     * @param newCatefory
     */
    void updateCategory(String ordinal, String newCatefory);
}
