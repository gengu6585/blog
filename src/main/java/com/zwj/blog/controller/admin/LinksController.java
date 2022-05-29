package com.zwj.blog.controller.admin;

import com.zwj.blog.controller.BaseController;
import com.zwj.blog.model.Bo.ForeResponse;
import com.zwj.blog.service.PropertyService;
import com.zwj.blog.dto.SiteProperty;
import com.zwj.blog.model.Vo.Meta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
@RequestMapping("admin/links")
public class LinksController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LinksController.class);

    @Resource
    private PropertyService metasService;

    @GetMapping(value = "")
    public String index(HttpServletRequest request) {
        List<Meta> metas = metasService.getMetas(SiteProperty.LINK.getProperty());
        request.setAttribute("links", metas);
        return "admin/links";
    }

    @PostMapping(value = "save")
    @ResponseBody
    public ForeResponse saveLink(@RequestParam String title, @RequestParam String url,
                                 @RequestParam String logo, @RequestParam Integer mid,
                                 @RequestParam(value = "sort", defaultValue = "0") int sort) {
        try {
            Meta metas = new Meta();
            metas.setName(title);
            metas.setSlug(url);
            metas.setDescription(logo);
            metas.setSort(sort);
            metas.setType(SiteProperty.LINK.getProperty());
            if (null != mid) {
                metas.setMid(mid);
                metasService.update(metas);
            } else {
                metasService.saveMeta(metas);
            }
        } catch (Exception e) {
            String msg = "友链保存失败";
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
            String msg = "友链删除失败";
            LOGGER.error(msg, e);
            return ForeResponse.fail(msg);
        }
        return ForeResponse.success();
    }

}
