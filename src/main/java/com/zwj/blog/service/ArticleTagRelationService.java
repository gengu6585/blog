package com.zwj.blog.service;

import com.zwj.blog.model.Vo.ArticleTagRelation;

import java.util.List;

public interface ArticleTagRelationService {
    /**
     * 按住键删除
     *
     * @param cid
     * @param mid
     */
    void deleteById(Integer cid, Integer mid);

    /**
     * 按主键统计条数
     *
     * @param cid
     * @param mid
     * @return 条数
     */
    Long countById(Integer cid, Integer mid);


    /**
     * 保存對象
     *
     * @param articleTagRelation
     */
    void insertVo(ArticleTagRelation articleTagRelation);

    /**
     * 根据id搜索
     *
     * @param cid
     * @param mid
     * @return
     */
    List<ArticleTagRelation> getRelationshipById(Integer cid, Integer mid);
}
