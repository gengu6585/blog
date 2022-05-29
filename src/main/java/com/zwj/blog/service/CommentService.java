package com.zwj.blog.service;

import com.github.pagehelper.PageInfo;
import com.zwj.blog.model.Vo.Comment;
import com.zwj.blog.model.Vo.CommentExample;
import com.zwj.blog.model.Bo.CommentBo;

public interface CommentService {

    /**
     * 保存对象
     *
     * @param commentVo
     */
    String saveComment(Comment commentVo);

    /**
     * 获取文章下的评论
     *
     * @param cid
     * @param page
     * @param limit
     * @return CommentBo
     */
    PageInfo<CommentBo> getComments(Integer cid, int page, int limit);

    /**
     * 获取文章下的评论
     *
     * @param commentExample
     * @param page
     * @param limit
     * @return CommentVo
     */
    PageInfo<Comment> getCommentsWithPage(CommentExample commentExample, int page, int limit);


    /**
     * 根据主键查询评论
     *
     * @param coid
     * @return
     */
    Comment getCommentById(Integer coid);


    /**
     * 删除评论，暂时没用
     *
     * @param coid
     * @param cid
     * @throws Exception
     */
    void delete(Integer coid, Integer cid);

    /**
     * 更新评论状态
     *
     * @param comments
     */
    void updateStatus(Comment comments);

}
