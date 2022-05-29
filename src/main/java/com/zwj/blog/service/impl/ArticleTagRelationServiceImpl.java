package com.zwj.blog.service.impl;

import com.zwj.blog.model.Vo.ArticleTagRelationsExample;
import com.zwj.blog.model.Vo.ArticleTagRelation;
import com.zwj.blog.dao.ArticleTagMapper;
import com.zwj.blog.service.ArticleTagRelationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ArticleTagRelationServiceImpl implements ArticleTagRelationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleTagRelationServiceImpl.class);

    @Resource
    private ArticleTagMapper articleTagMapper;

    @Override
    public void deleteById(Integer cid, Integer mid) {
        ArticleTagRelationsExample relationshipVoExample = new ArticleTagRelationsExample();
        ArticleTagRelationsExample.Criteria criteria = relationshipVoExample.createCriteria();
        if (cid != null) {
            criteria.andCidEqualTo(cid);
        }
        if (mid != null) {
            criteria.andMidEqualTo(mid);
        }
        articleTagMapper.deleteByExample(relationshipVoExample);
    }

    @Override
    public List<ArticleTagRelation> getRelationshipById(Integer cid, Integer mid) {
        ArticleTagRelationsExample relationshipVoExample = new ArticleTagRelationsExample();
        ArticleTagRelationsExample.Criteria criteria = relationshipVoExample.createCriteria();
        if (cid != null) {
            criteria.andCidEqualTo(cid);
        }
        if (mid != null) {
            criteria.andMidEqualTo(mid);
        }
        return articleTagMapper.selectByExample(relationshipVoExample);
    }

    @Override
    public void insertVo(ArticleTagRelation articleTagRelation) {
        articleTagMapper.insert(articleTagRelation);
    }

    @Override
    public Long countById(Integer cid, Integer mid) {
        LOGGER.debug("Enter countById method:cid={},mid={}", cid, mid);
        ArticleTagRelationsExample relationshipVoExample = new ArticleTagRelationsExample();
        ArticleTagRelationsExample.Criteria criteria = relationshipVoExample.createCriteria();
        if (cid != null) {
            criteria.andCidEqualTo(cid);
        }
        if (mid != null) {
            criteria.andMidEqualTo(mid);
        }
        long num = articleTagMapper.countByExample(relationshipVoExample);
        LOGGER.debug("Exit countById method return num={}", num);
        return num;
    }
}
