package com.zwj.blog.controller.admin;

import com.zwj.blog.constant.BlogConst;
import com.zwj.blog.controller.BaseController;
import com.zwj.blog.dto.LogActions;
import com.zwj.blog.exception.TipException;
import com.zwj.blog.model.Bo.ForeResponse;
import com.zwj.blog.model.Vo.User;
import com.zwj.blog.service.LogService;
import com.zwj.blog.service.UserService;
import com.zwj.blog.utils.Utils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@Controller
@RequestMapping("/admin")
@Transactional(rollbackFor = TipException.class)
public class AuthController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    @Resource
    private UserService usersService;

    @Resource
    private LogService logService;

    @GetMapping(value = "/login")
    public String login() {
        return "admin/login";
    }

    @PostMapping(value = "/login")
    @ResponseBody
    public ForeResponse doLogin(@RequestParam String username,
                                @RequestParam String password,
                                @RequestParam(required = false) String remeber_me,
                                HttpServletRequest request,
                                HttpServletResponse response) {

        Integer error_count = cache.get("login_error_count");
        try {
            User user = usersService.login(username, password);
            request.getSession().setAttribute(BlogConst.LOGIN_SESSION_KEY, user);
            if (StringUtils.isNotBlank(remeber_me)) {
                Utils.setCookie(response, user.getUid());
            }
            logService.insertLog(LogActions.LOGIN.getAction(), null, request.getRemoteAddr(), user.getUid());
        } catch (Exception e) {
            error_count = null == error_count ? 1 : error_count + 1;
            if (error_count > 3) {
                return ForeResponse.fail("您输入密码已经错误超过3次，请10分钟后尝试");
            }
            cache.set("login_error_count", error_count, 10 * 60);
            String msg = "登录失败";
            if (e instanceof TipException) {
                msg = e.getMessage();
            } else {
                LOGGER.error(msg, e);
            }
            return ForeResponse.fail(msg);
        }
        return ForeResponse.success();
    }

    /**
     * 注销
     *
     * @param session
     * @param response
     */
    @RequestMapping("/logout")
    public void logout(HttpSession session, HttpServletResponse response, HttpServletRequest request) {
        session.removeAttribute(BlogConst.LOGIN_SESSION_KEY);
        Cookie cookie = new Cookie(BlogConst.USER_IN_COOKIE, "");
        cookie.setValue(null);
        cookie.setMaxAge(0);// 立即销毁cookie
        cookie.setPath("/");
        response.addCookie(cookie);
        try {
            response.sendRedirect("/admin/login");
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error("注销失败", e);
        }
    }

}
