package com.zwj.blog.interceptor;

import com.zwj.blog.utils.Utils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.Resource;

/**
 * 向mvc中添加自定义组件
 */
@Component
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    @Resource
    private BaseInterceptor baseInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(baseInterceptor).addPathPatterns("/**").excludePathPatterns("/admin/images/**", "/admin/css/**", "/admin/js/**");

    }

    /**
     * 添加静态资源文件，外部可以直接访问地址
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload/**").addResourceLocations("file:" + Utils.getUploadFilePath() + "upload/");
        registry.addResourceHandler("/admin/article/edit/**").addResourceLocations("file:" + Utils.getUploadFilePath() + "upload/page/");
        registry.addResourceHandler("/admin/article/publish/**").addResourceLocations("file:" + Utils.getUploadFilePath() + "upload/temp/");
        registry.addResourceHandler("/admin/article/view/**").addResourceLocations("file:" + Utils.getUploadFilePath() + "upload/page/");

//        registry.addResourceHandler("/admin/**").addResourceLocations("classpath:/static/admin/");
        super.addResourceHandlers(registry);
    }
}
