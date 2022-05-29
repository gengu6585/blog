package com.zwj.blog.service;

import com.zwj.blog.dto.MetaDto;
import com.zwj.blog.model.Bo.ArchiveBo;
import com.zwj.blog.model.Bo.BackResponse;
import com.zwj.blog.model.Bo.Statistics;
import com.zwj.blog.model.Vo.Comment;
import com.zwj.blog.model.Vo.Article;

import java.util.List;

/**
 * 站点服务
 */
public interface SiteService {


    /**
     * 最新收到的评论
     *
     * @param limit
     * @return
     */
    List<Comment> recentComments(int limit);

    /**
     * 最新发表的文章
     *
     * @param limit
     * @return
     */
    List<Article> recentContents(int limit);

    /**
     * 查询一条评论
     *
     * @param coid
     * @return
     */
    Comment getComment(Integer coid);

    /**
     * 系统备份
     *
     * @param bk_type
     * @param bk_path
     * @param fmt
     * @return
     */
    BackResponse backup(String bk_type, String bk_path, String fmt) throws Exception;


    /**
     * 获取后台统计数据
     *
     * @return
     */
    Statistics getStatistics();

    /**
     * 查询文章归档
     *
     * @return
     */
    List<ArchiveBo> getArchives();

    /**
     * 获取分类/标签列表
     *
     * @return
     */
    List<MetaDto> metas(String type, String orderBy, int limit);

}
