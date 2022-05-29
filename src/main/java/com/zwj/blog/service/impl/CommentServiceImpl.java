package com.zwj.blog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zwj.blog.constant.BlogConst;
import com.zwj.blog.exception.TipException;
import com.zwj.blog.utils.DateKit;
import com.zwj.blog.utils.Utils;
import com.zwj.blog.dao.CommentMapper;
import com.zwj.blog.model.Bo.CommentBo;
import com.zwj.blog.model.Vo.Comment;
import com.zwj.blog.model.Vo.CommentExample;
import com.zwj.blog.model.Vo.Article;
import com.zwj.blog.service.CommentService;
import com.zwj.blog.service.ArticleService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommentServiceImpl.class);

    @Resource
    private CommentMapper commentDao;

    @Resource
    private ArticleService articleService;

    @Override
    @Transactional
    public String saveComment(Comment comments) {
        if (null == comments) {
            return "评论对象为空";
        }
        if (StringUtils.isBlank(comments.getAuthor())) {
            comments.setAuthor("热心网友");
        }
        if (StringUtils.isNotBlank(comments.getMail()) && !Utils.isEmail(comments.getMail())) {
            return "请输入正确的邮箱格式";
        }
        if (StringUtils.isBlank(comments.getContent())) {
            return "评论内容不能为空";
        }
        if (comments.getContent().length() < 5 || comments.getContent().length() > 2000) {
            return "评论字数在5-2000个字符";
        }
        if (null == comments.getCid()) {
            return "评论文章不能为空";
        }
        Article contents = articleService.getArticles(String.valueOf(comments.getCid()));
        if (null == contents) {
            return "不存在的文章";
        }
        comments.setOwnerId(contents.getAuthorId());
//        comments.setStatus("approved");
        comments.setCreated(DateKit.getCurrentUnixTime());
        commentDao.insertSelective(comments);

        Article temp = new Article();
        temp.setCid(contents.getCid());
        temp.setCommentsNum(contents.getCommentsNum() + 1);
        articleService.updateArticleById(temp);

        return BlogConst.SUCCESS_RESPONSE_MESSAGE;
    }

    @Override
    public PageInfo<CommentBo> getComments(Integer cid, int page, int limit) {

        if (null != cid) {
            PageHelper.startPage(page, limit);
            CommentExample commentExample = new CommentExample();
            commentExample.createCriteria().andCidEqualTo(cid).andParentEqualTo(0).andStatusIsNotNull().andStatusEqualTo("approved");
            commentExample.setOrderByClause("coid desc");
            List<Comment> parents = commentDao.selectByExampleWithBLOBs(commentExample);
            PageInfo<Comment> commentPaginator = new PageInfo<>(parents);
            PageInfo<CommentBo> returnBo = copyPageInfo(commentPaginator);
            if (parents.size() != 0) {
                List<CommentBo> comments = new ArrayList<>(parents.size());
                parents.forEach(parent -> {
                    CommentBo comment = new CommentBo(parent);
                    comments.add(comment);
                });
                returnBo.setList(comments);
            }
            return returnBo;
        }
        return null;
    }

    @Override
    public PageInfo<Comment> getCommentsWithPage(CommentExample commentExample, int page, int limit) {
        PageHelper.startPage(page, limit);
        List<Comment> commentVos = commentDao.selectByExampleWithBLOBs(commentExample);
        PageInfo<Comment> pageInfo = new PageInfo<>(commentVos);
        return pageInfo;
    }

    @Override
    @Transactional
    public void updateStatus(Comment comments) {
        if (null != comments && null != comments.getCoid()) {
            commentDao.updateByPrimaryKeyWithBLOBs(comments);
        }
    }

    @Override
    @Transactional
    public void delete(Integer coid, Integer cid) {
        if (null == coid) {
            throw new TipException("主键为空");
        }
        commentDao.deleteByPrimaryKey(coid);
        Article contents = articleService.getArticles(cid + "");
        if (null != contents && contents.getCommentsNum() > 0) {
            Article temp = new Article();
            temp.setCid(cid);
            temp.setCommentsNum(contents.getCommentsNum() - 1);
            articleService.updateArticleById(temp);
        }
    }

    @Override
    public Comment getCommentById(Integer coid) {
        if (null != coid) {
            return commentDao.selectByPrimaryKey(coid);
        }
        return null;
    }

    /**
     * copy原有的分页信息，除数据
     *
     * @param ordinal
     * @param <T>
     * @return
     */
    private <T> PageInfo<T> copyPageInfo(PageInfo ordinal) {
        PageInfo<T> returnBo = new PageInfo<T>();
        returnBo.setPageSize(ordinal.getPageSize());
        returnBo.setPageNum(ordinal.getPageNum());
        returnBo.setEndRow(ordinal.getEndRow());
        returnBo.setTotal(ordinal.getTotal());
        returnBo.setHasNextPage(ordinal.isHasNextPage());
        returnBo.setHasPreviousPage(ordinal.isHasPreviousPage());
        returnBo.setIsFirstPage(ordinal.isIsFirstPage());
        returnBo.setIsLastPage(ordinal.isIsLastPage());
        returnBo.setNavigateFirstPage(ordinal.getNavigateFirstPage());
        returnBo.setNavigateLastPage(ordinal.getNavigateLastPage());
        returnBo.setNavigatepageNums(ordinal.getNavigatepageNums());
        returnBo.setSize(ordinal.getSize());
        returnBo.setPrePage(ordinal.getPrePage());
        returnBo.setNextPage(ordinal.getNextPage());
        return returnBo;
    }
}
