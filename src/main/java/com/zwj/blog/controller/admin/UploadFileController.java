package com.zwj.blog.controller.admin;

import com.github.pagehelper.PageInfo;
import com.zwj.blog.constant.BlogConst;
import com.zwj.blog.controller.BaseController;
import com.zwj.blog.dto.LogActions;
import com.zwj.blog.dto.SiteProperty;
import com.zwj.blog.model.Bo.ForeResponse;
import com.zwj.blog.model.Vo.UploadFile;
import com.zwj.blog.model.Vo.User;
import com.zwj.blog.service.UploadFileService;
import com.zwj.blog.service.LogService;
import com.zwj.blog.utils.Commons;
import com.zwj.blog.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;


@Controller
@RequestMapping("admin/attach")
public class UploadFileController extends BaseController {

    public static final String CLASSPATH = Utils.getUploadFilePath();
    private static final Logger LOGGER = LoggerFactory.getLogger(UploadFileController.class);
    @Resource
    private UploadFileService uploadFileService;

    @Resource
    private LogService logService;

    /**
     * 附件页面
     *
     * @param request
     * @param page
     * @param limit
     * @return
     */
    @GetMapping(value = "")
    public String index(HttpServletRequest request, @RequestParam(value = "page", defaultValue = "1") int page,
                        @RequestParam(value = "limit", defaultValue = "12") int limit) {
        PageInfo<UploadFile> attachPaginator = uploadFileService.getAttachs(page, limit);
        request.setAttribute("attachs", attachPaginator);
        request.setAttribute(SiteProperty.ATTACH_URL.getProperty(), Commons.site_option(SiteProperty.ATTACH_URL.getProperty(), Commons.site_url()));
        request.setAttribute("max_file_size", BlogConst.MAX_FILE_SIZE / 1024);
        return "admin/attach";
    }

    /***
     * description: 文件上传接口
     * @param: [req, files]
     * @return: com.zwj.blog.model.Bo.ForeResponse
     * @author zwj
     */
    @PostMapping("/upload")
    @ResponseBody
    public ForeResponse fileUpload(HttpServletRequest req, @RequestParam("file") MultipartFile[] files) throws Exception {
        User users = this.getCurrentLogin(req);
        Integer uid = users.getUid();
        //记录错误文件
        ArrayList<String> errors = new ArrayList<>();
        try {
            for (MultipartFile uploadFile : files) {
                String filename = uploadFile.getOriginalFilename();
                if (uploadFile.getSize() <= BlogConst.MAX_FILE_SIZE) {
                    String filekey = Utils.getFileKey(filename);
                    String filetype = Utils.isImage(uploadFile.getInputStream()) ? SiteProperty.IMAGE.getProperty() : SiteProperty.FILE.getProperty();
                    File localFile = new File(CLASSPATH + filekey);
                    FileCopyUtils.copy(uploadFile.getInputStream(), new FileOutputStream(localFile));
                    uploadFileService.save(filename, filekey, filetype, uid);
                } else {
                    errors.add(filename);
                }
            }

        } catch (Exception e) {
            return ForeResponse.fail();
        }
        return ForeResponse.success(errors);
    }

    @RequestMapping("/delete")
    @ResponseBody
    public ForeResponse delete(@RequestParam Integer articleId, HttpServletRequest req) {

        UploadFile file = uploadFileService.selectById(articleId);
        if (file == null) {
            return ForeResponse.fail("文件不存在");
        }
        try {
            uploadFileService.deleteById(articleId);
            boolean result = new File(CLASSPATH + file.getFkey()).delete();
            logService.insertLog(LogActions.DEL_ARTICLE.getAction(), file.getFkey(), req.getRemoteAddr(), this.getUid(req));
        } catch (Exception e) {
            LOGGER.error("文件删除失败", e);
            return ForeResponse.fail("文件删除失败");
        }
        return ForeResponse.success();
    }

}
