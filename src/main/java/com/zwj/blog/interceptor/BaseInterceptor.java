package com.zwj.blog.interceptor;

import com.zwj.blog.model.Vo.Meta;
import com.zwj.blog.model.Vo.Option;
import com.zwj.blog.model.Vo.User;
import com.zwj.blog.service.ArticleService;
import com.zwj.blog.service.OptionService;
import com.zwj.blog.service.PropertyService;
import com.zwj.blog.service.UserService;
import com.zwj.blog.constant.BlogConst;
import com.zwj.blog.dto.SiteProperty;
import com.zwj.blog.utils.*;
import com.zwj.blog.utils.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 自定义拦截器
 */
@Component
public class BaseInterceptor implements HandlerInterceptor {
    private static final Logger LOGGE = LoggerFactory.getLogger(BaseInterceptor.class);
    private static final String USER_AGENT = "user-agent";
    @Resource
    PropertyService propertyService;
    @Resource
    ArticleService articleService;
    @Resource
    private UserService userService;
    @Resource
    private OptionService optionService;
    private MapCache cache = MapCache.single();
    @Resource
    private Commons commons;
    @Resource
    private AdminCommons adminCommons;

    static private List<Meta> createRandoms(List<Meta> list, int n) {
        Map<Integer, String> map = new HashMap();
        List<Meta> news = new ArrayList();
        if (list.size() <= n) {
            return list;
        } else {
            while (map.size() < n) {
                int random = (int) (Math.random() * list.size());
                if (!map.containsKey(random)) {
                    map.put(random, "");
                    news.add(list.get(random));
                }
            }
            return news;
        }
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        String contextPath = request.getContextPath();
        // System.out.println(contextPath);
        String uri = request.getRequestURI();

        LOGGE.info("UserAgent: {}", request.getHeader(USER_AGENT));
        LOGGE.info("用户访问地址: {}, 来路地址: {}", uri, WebUtil.getIpOfUser(request));


        //请求拦截处理
        User user = Utils.getLoginUser(request);
        if (null == user) {
            Integer uid = Utils.getCookieUid(request);
            if (null != uid) {
                //这里还是有安全隐患,cookie是可以伪造的
                user = userService.queryUserById(uid);
                request.getSession().setAttribute(BlogConst.LOGIN_SESSION_KEY, user);
            }
        }
        if (uri.startsWith(contextPath + "/admin") && !uri.startsWith(contextPath + "/admin/login") && !uri.startsWith(contextPath + "/admin/login.html") && null == user) {
            response.sendRedirect(request.getContextPath() + "/admin/login");
            return false;
        }
        //设置get请求的token
        if (request.getMethod().equals("GET")) {
            String csrf_token = UUID.UU64();
            // 默认存储30分钟
            cache.hset(SiteProperty.TOKEN.getProperty(), csrf_token, uri, 30 * 60);
            request.setAttribute("_csrf_token", csrf_token);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        Option ov = optionService.getOptionByName("site_record");
        httpServletRequest.setAttribute("commons", commons);//一些工具类和公共方法
        httpServletRequest.setAttribute("option", ov);
        httpServletRequest.setAttribute("adminCommons", adminCommons);
        List<Meta> categories = propertyService.getMetas(SiteProperty.CATEGORY.getProperty());
        HashMap<String, Integer> categoriesMap = new HashMap<>();
//        计算各个目录对应的文章数
        for (Meta category : categories) {
            categoriesMap.put(category.getName(), articleService.getArticleNumsByMetaId(category.getMid()));
        }

        Set<Map.Entry<String, Integer>> entries = categoriesMap.entrySet();
        httpServletRequest.setAttribute("categoriesEntries", categoriesMap);
        List<Meta> metas = propertyService.getMetas(SiteProperty.TAG.getProperty());
        List<Meta> tags = createRandoms(metas, 5);
        httpServletRequest.setAttribute("tags", tags);


    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
