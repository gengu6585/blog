package com.zwj.blog.controller;

import com.zwj.blog.model.Vo.User;
import com.zwj.blog.utils.Utils;
import com.zwj.blog.utils.MapCache;

import javax.servlet.http.HttpServletRequest;


public abstract class BaseController {

    public static String THEME = "themes/default";

    protected MapCache cache = MapCache.single();

    /**
     * 主页的页面主题
     *
     * @param viewName
     * @return
     */
    public String setTheme(String viewName) {
        return THEME + "/" + viewName;
    }

    public String setTheme(String viewName, String themeName) {
        return "theme/" + themeName + "/" + viewName;
    }

    public BaseController title(HttpServletRequest request, String title) {
        request.setAttribute("title", title);
        return this;
    }

    public BaseController keywords(HttpServletRequest request, String keywords) {
        request.setAttribute("keywords", keywords);
        return this;
    }

    /**
     * 获取请求绑定的登录对象
     *
     * @param request
     * @return
     */
    public User getCurrentLogin(HttpServletRequest request) {
        return Utils.getLoginUser(request);
    }

    public Integer getUid(HttpServletRequest request) {
        return this.getCurrentLogin(request).getUid();
    }

    public String forwardTo404() {
        return "comm/error_404";
    }

}
