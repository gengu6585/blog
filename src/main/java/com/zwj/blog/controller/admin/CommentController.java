package com.zwj.blog.controller.admin;

import com.github.pagehelper.PageInfo;
import com.zwj.blog.controller.BaseController;
import com.zwj.blog.model.Bo.ForeResponse;
import com.zwj.blog.model.Vo.Comment;
import com.zwj.blog.model.Vo.CommentExample;
import com.zwj.blog.model.Vo.User;
import com.zwj.blog.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/admin/comments")
public class CommentController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommentController.class);

    @Resource
    private CommentService commentsService;

    @GetMapping(value = "")
    public String index(@RequestParam(value = "page", defaultValue = "1") int page,
                        @RequestParam(value = "limit", defaultValue = "15") int limit, HttpServletRequest request) {
        User users = this.getCurrentLogin(request);
        CommentExample commentExample = new CommentExample();
        commentExample.setOrderByClause("coid desc");
        commentExample.createCriteria().andAuthorIdNotEqualTo(users.getUid());
        PageInfo<Comment> commentsPaginator = commentsService.getCommentsWithPage(commentExample, page, limit);
        request.setAttribute("comments", commentsPaginator);
        return "admin/comment_list";
    }

    /**
     * 删除一条评论
     *
     * @param coid
     * @return
     */
    @PostMapping(value = "delete")
    @ResponseBody
    public ForeResponse approve(@RequestParam Integer coid) {
        try {
            Comment comments = commentsService.getCommentById(coid);
            if (null == comments) {
                return ForeResponse.fail("不存在该评论");
            }
            commentsService.delete(coid, comments.getCid());
        } catch (Exception e) {
            String msg = "评论删除失败";
            LOGGER.error(msg, e);
            return ForeResponse.fail(msg);
        }
        return ForeResponse.success();
    }

    /***
     * description: 审核评论
     * @param: [comment]
     * @return: com.zwj.blog.model.Bo.ForeResponse
     * @author zwj
     */
    @PostMapping("/status")
    @ResponseBody
    public ForeResponse approve(Comment comment) {
        if (comment == null) {
            return ForeResponse.fail("操作失败");
        }
        try {
            Comment savedComment = commentsService.getCommentById(comment.getCoid());
            if (savedComment != null) {
                savedComment.setStatus(comment.getStatus());
                commentsService.updateStatus(savedComment);
            }
        } catch (Exception e) {
            return ForeResponse.fail("操作失败");
        }
        return ForeResponse.success();
    }

}
