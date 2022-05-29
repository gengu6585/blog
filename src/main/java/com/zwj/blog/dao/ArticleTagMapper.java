package com.zwj.blog.dao;

import com.zwj.blog.model.Vo.ArticleTagRelationsExample;
import com.zwj.blog.model.Vo.ArticleTagRelation;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface ArticleTagMapper {
    long countByExample(ArticleTagRelationsExample example);

    int deleteByExample(ArticleTagRelationsExample example);

    int deleteByPrimaryKey(ArticleTagRelation key);

    int insert(ArticleTagRelation record);

    int insertSelective(ArticleTagRelation record);

    List<ArticleTagRelation> selectByExample(ArticleTagRelationsExample example);

    int updateByExampleSelective(@Param("record") ArticleTagRelation record, @Param("example") ArticleTagRelationsExample example);

    int updateByExample(@Param("record") ArticleTagRelation record, @Param("example") ArticleTagRelationsExample example);
}