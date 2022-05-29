package com.zwj.blog.controller.admin;

import com.zwj.blog.service.SiteService;
import com.zwj.blog.constant.BlogConst;
import com.zwj.blog.controller.BaseController;
import com.zwj.blog.dto.LogActions;
import com.zwj.blog.exception.TipException;
import com.zwj.blog.model.Bo.ForeResponse;
import com.zwj.blog.model.Bo.Statistics;
import com.zwj.blog.model.Vo.Comment;
import com.zwj.blog.model.Vo.Article;
import com.zwj.blog.model.Vo.Log;
import com.zwj.blog.model.Vo.User;
import com.zwj.blog.service.LogService;
import com.zwj.blog.service.UserService;
import com.zwj.blog.utils.GsonUtils;
import com.zwj.blog.utils.Utils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;


@Controller("adminIndexController")
@RequestMapping("/admin")
@Transactional(rollbackFor = TipException.class)
public class IndexController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);

    @Resource
    private SiteService siteService;

    @Resource
    private LogService logService;

    @Resource
    private UserService userService;

    /**
     * 页面跳转
     *
     * @return
     */
    @GetMapping(value = {"", "/index", "index.html"})
    public String index(HttpServletRequest request) {
        LOGGER.info("Enter admin index method");
        List<Comment> comments = siteService.recentComments(5);
        List<Article> contents = siteService.recentContents(5);
        Statistics statistics = siteService.getStatistics();
        // 取最新的20条日志
        List<Log> logs = logService.getLogs(1, 5);

        request.setAttribute("comments", comments);
        request.setAttribute("articles", contents);
        request.setAttribute("statistics", statistics);
        request.setAttribute("logs", logs);
        LOGGER.info("Exit admin index method");
        return "admin/index";
    }

    /**
     * 个人设置页面
     */
    @GetMapping(value = "profile")
    public String profile() {
        return "admin/profile";
    }


    /**
     * 保存个人信息
     */
    @PostMapping(value = "/profile")
    @ResponseBody
    public ForeResponse saveProfile(@RequestParam String screenName, @RequestParam String email, HttpServletRequest request, HttpSession session) {
        User users = this.getCurrentLogin(request);
        if (StringUtils.isNotBlank(screenName) && StringUtils.isNotBlank(email)) {
            User temp = new User();
            temp.setUid(users.getUid());
            temp.setScreenName(screenName);
            temp.setEmail(email);
            userService.updateByUid(temp);
            logService.insertLog(LogActions.UP_INFO.getAction(), GsonUtils.toJsonString(temp), request.getRemoteAddr(), this.getUid(request));

            //更新session中的数据
            User original = (User) session.getAttribute(BlogConst.LOGIN_SESSION_KEY);
            original.setScreenName(screenName);
            original.setEmail(email);
            session.setAttribute(BlogConst.LOGIN_SESSION_KEY, original);
        }
        return ForeResponse.success();
    }

    /**
     * 修改密码
     */
    @PostMapping(value = "/password")
    @ResponseBody
    public ForeResponse upPwd(@RequestParam String oldPassword, @RequestParam String password, HttpServletRequest request, HttpSession session) {
        User users = this.getCurrentLogin(request);
        if (StringUtils.isBlank(oldPassword) || StringUtils.isBlank(password)) {
            return ForeResponse.fail("请确认信息输入完整");
        }

        if (!users.getPassword().equals(Utils.MD5encode(users.getUsername() + oldPassword))) {
            return ForeResponse.fail("旧密码错误");
        }
        if (password.length() < 6 || password.length() > 14) {
            return ForeResponse.fail("请输入6-14位密码");
        }

        try {
            User temp = new User();
            temp.setUid(users.getUid());
            String pwd = Utils.MD5encode(users.getUsername() + password);
            temp.setPassword(pwd);
            userService.updateByUid(temp);
            logService.insertLog(LogActions.UP_PWD.getAction(), null, request.getRemoteAddr(), this.getUid(request));

            //更新session中的数据
            User original = (User) session.getAttribute(BlogConst.LOGIN_SESSION_KEY);
            original.setPassword(pwd);
            session.setAttribute(BlogConst.LOGIN_SESSION_KEY, original);
            return ForeResponse.success();
        } catch (Exception e) {
            String msg = "密码修改失败";
            if (e instanceof TipException) {
                msg = e.getMessage();
            } else {
                LOGGER.error(msg, e);
            }
            return ForeResponse.fail(msg);
        }
    }
}
