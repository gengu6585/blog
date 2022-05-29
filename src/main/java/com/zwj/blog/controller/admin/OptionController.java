package com.zwj.blog.controller.admin;

import com.zwj.blog.constant.BlogConst;
import com.zwj.blog.controller.BaseController;
import com.zwj.blog.dto.LogActions;
import com.zwj.blog.exception.TipException;
import com.zwj.blog.model.Bo.BackResponse;
import com.zwj.blog.model.Bo.ForeResponse;
import com.zwj.blog.model.Vo.Option;
import com.zwj.blog.service.LogService;
import com.zwj.blog.service.OptionService;
import com.zwj.blog.service.SiteService;
import com.zwj.blog.utils.GsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


@Controller
@RequestMapping("")
public class OptionController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(OptionController.class);

    @Resource
    private OptionService optionService;

    @Resource
    private LogService logService;

    @Resource
    private SiteService siteService;

    static void modifyTheme(String theme) {
        if (theme != null && theme.length() != 0) {
            BaseController.THEME = "themes/" + theme;
        }
    }

    /**
     * 系统设置
     */
    @RequestMapping("/admin/setting")
    public String setting(HttpServletRequest request) {
        List<Option> voList = optionService.getOptions();
        Map<String, String> options = new HashMap<>();
        voList.forEach((option) -> {
            options.put(option.getName(), option.getValue());
        });
        if (options.get("site_record") == null) {
            options.put("site_record", "");
        }
        request.setAttribute("options", options);
        return "admin/setting";
    }

    /***
     * description: 系统设置接口
     * @param: [site_theme, request]
     * @return: com.zwj.blog.model.Bo.ForeResponse
     * @author zwj
     */
    @PostMapping("/admin/setting")
    @ResponseBody
    public ForeResponse saveSetting(HttpServletRequest req) {
        String theme = req.getParameter("site_theme");
        Set<Map.Entry<String, String[]>> set = req.getParameterMap().entrySet();
        Map<String, String> settings = new HashMap<>();
        for (Map.Entry<String, String[]> stringEntry : set) {
            settings.put(stringEntry.getKey(), arrayToString(stringEntry.getValue()));
        }
        try {
            optionService.saveOptions(settings);
            modifyTheme(theme);
            BlogConst.config = settings;
            logService.insertLog(LogActions.SYS_SETTING.getAction(), GsonUtils.toJsonString(settings), req.getRemoteAddr(), this.getUid(req));
            return ForeResponse.success();
        } catch (Exception e) {
            return ForeResponse.fail("保存设置失败");
        }
    }

    /**
     * 系统备份
     *
     * @return
     */
    @PostMapping(value = "/admin/setting/backup")
    @ResponseBody
    public ForeResponse backup(@RequestParam String bk_type, @RequestParam String bk_path,
                               HttpServletRequest request) {
        if (StringUtils.isBlank(bk_type)) {
            return ForeResponse.fail("请确认信息输入完整");
        }
        try {
            BackResponse backResponse = siteService.backup(bk_type, bk_path, "yyyyMMddHHmm");
            logService.insertLog(LogActions.SYS_BACKUP.getAction(), null, request.getRemoteAddr(), this.getUid(request));
            return ForeResponse.success(backResponse);
        } catch (Exception e) {
            String msg = "备份失败";
            if (e instanceof TipException) {
                msg = e.getMessage();
            } else {
                LOGGER.error(msg, e);
            }
            return ForeResponse.fail(msg);
        }
    }


    /**
     * 数组转字符串
     *
     * @param arr
     * @return
     */
    private String arrayToString(String[] arr) {
        StringBuilder ret = new StringBuilder();
        String[] var3 = arr;
        int var4 = arr.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            String item = var3[var5];
            ret.append(',').append(item);
        }

        return ret.length() > 0 ? ret.substring(1) : ret.toString();
    }

}
