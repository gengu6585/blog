package com.zwj.blog.controller.admin;

import com.zwj.blog.constant.BlogConst;
import com.zwj.blog.controller.BaseController;
import com.zwj.blog.dto.MetaDto;
import com.zwj.blog.dto.SiteProperty;
import com.zwj.blog.model.Bo.ForeResponse;
import com.zwj.blog.service.PropertyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
@RequestMapping("admin/category")
public class CategoryController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);

    @Resource
    private PropertyService metasService;

    @GetMapping(value = "")
    public String index(HttpServletRequest request) {
        List<MetaDto> categories = metasService.getMetaList(SiteProperty.CATEGORY.getProperty(), null, BlogConst.MAX_POSTS);
        List<MetaDto> tags = metasService.getMetaList(SiteProperty.TAG.getProperty(), null, BlogConst.MAX_POSTS);
        request.setAttribute("categories", categories);
        request.setAttribute("tags", tags);
        return "admin/category";
    }

    @PostMapping(value = "save")
    @ResponseBody
    public ForeResponse saveCategory(@RequestParam String cname, @RequestParam Integer mid) {
        try {
            metasService.saveMeta(SiteProperty.CATEGORY.getProperty(), cname, mid);
        } catch (Exception e) {
            String msg = "分类保存失败";
            LOGGER.error(msg, e);
            return ForeResponse.fail(msg);
        }
        return ForeResponse.success();
    }

    @RequestMapping(value = "delete")
    @ResponseBody
    public ForeResponse delete(@RequestParam int mid) {
        try {
            metasService.delete(mid);
        } catch (Exception e) {
            String msg = "删除失败";
            LOGGER.error(msg, e);
            return ForeResponse.fail(msg);
        }
        return ForeResponse.success();
    }

}
