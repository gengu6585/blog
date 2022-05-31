package com.zwj.blog.controller.admin;

import com.github.pagehelper.PageInfo;
import com.zwj.blog.constant.BlogConst;
import com.zwj.blog.controller.BaseController;
import com.zwj.blog.dto.LogActions;
import com.zwj.blog.dto.SiteProperty;
import com.zwj.blog.model.Bo.ForeResponse;
import com.zwj.blog.model.Vo.Article;
import com.zwj.blog.model.Vo.UploadFile;
import com.zwj.blog.model.Vo.UploadFileExample;
import com.zwj.blog.model.Vo.User;
import com.zwj.blog.service.ArticleService;
import com.zwj.blog.service.UploadFileService;
import com.zwj.blog.service.LogService;
import com.zwj.blog.utils.Commons;
import com.zwj.blog.utils.UUID;
import com.zwj.blog.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("admin/attach")
public class UploadFileController extends BaseController {

    public static final String CLASSPATH = Utils.getUploadFilePath();
    private static final Logger LOGGER = LoggerFactory.getLogger(UploadFileController.class);
    @Resource
    private UploadFileService uploadFileService;
    @Resource
    private ArticleService articleService;
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
                    String filekey = Utils.getFileKey(filename,null);
                    String filetype = Utils.isImage(uploadFile.getInputStream()) ? SiteProperty.IMAGE.getProperty() : SiteProperty.FILE.getProperty();
                    File localFile = new File(CLASSPATH + filekey);
                    FileCopyUtils.copy(uploadFile.getInputStream(), new FileOutputStream(localFile));
                    uploadFileService.save(filename, filekey, filetype, uid,null);
                } else {
                    errors.add(filename);
                }
            }

        } catch (Exception e) {
            return ForeResponse.fail();
        }
        return ForeResponse.success(errors);
    }

    public class FileResponseMessage {

        public ArrayList<String> getOk_files() {
            return ok_files;
        }

        public void setOk_files(ArrayList<String> ok_files) {
            this.ok_files = ok_files;
        }

        public ArrayList<String> getErrors_files() {
            return errors_files;
        }

        public void setErrors_files(ArrayList<String> errors_files) {
            this.errors_files = errors_files;
        }

        private ArrayList<String> ok_files=new ArrayList<String>() ;
        private ArrayList<String> errors_files =new ArrayList<String>();
    }
    //// TODO: 2022-05-30 为md的文件上传写后端接口
    @PostMapping("/upload_files_for_md")
    @ResponseBody
    public ForeResponse file_upload(HttpServletRequest req, @RequestParam("file") MultipartFile[] files,@RequestParam("cid")String cid,@RequestParam("file_dir")String file_dir) throws Exception {

        User users = this.getCurrentLogin(req);
        Integer uid = users.getUid();
        String upload_dir = file_dir;
        try {
            java.util.UUID.fromString(file_dir);
        } catch (Exception e) {
            return ForeResponse.fail("fil_dir is not a uuid");
        }
        List<UploadFile> attachs = null;
        if (file_dir.equals("")) {
            return ForeResponse.fail("不存在fil_dir");
        }

        Article article = articleService.getArticles(cid);
        assert cid != null;
        if (!cid.equals("") && article.getFileDir() != null && !article.getFileDir().equals(file_dir)) {
            return ForeResponse.fail("图片file_dir与文章id不匹配");
        }
        upload_dir = file_dir;


        //记录错误文件
        FileResponseMessage message = new FileResponseMessage();
        try {
            for (MultipartFile uploadFile : files) {
                String filename = uploadFile.getOriginalFilename();
                if (uploadFile.getSize() <= BlogConst.MAX_FILE_SIZE) {
                    String filekey = Utils.getFileKey(filename,upload_dir);
                    String filetype = Utils.isImage(uploadFile.getInputStream()) ? SiteProperty.IMAGE.getProperty() : SiteProperty.FILE.getProperty();
                    File localFile = new File(CLASSPATH + filekey);
                    FileCopyUtils.copy(uploadFile.getInputStream(), new FileOutputStream(localFile));
                    uploadFileService.save(filename, filekey, filetype, uid,cid==null?null:Integer.parseInt(cid));
                    message.ok_files.add(filename);
                } else {
                    message.errors_files.add(filename);
                }
            }

        } catch (Exception e) {
            return ForeResponse.fail(message, "文件存储出错", -1);
        }
        return ForeResponse.success(message);
    }
    // // TODO: 2022-05-30 编写Markdown文件上传后台接口
    @PostMapping("/upload_markdown")
    @ResponseBody
    public ForeResponse file_upload_for_md_file(HttpServletRequest req, @RequestParam("file") MultipartFile file,@RequestParam(name = "file_dir",required = true) String fil_dir) throws Exception {
        User users = this.getCurrentLogin(req);
        //记录错误文件
        String result = "";
        if (fil_dir.equals("")) {
            return ForeResponse.fail("不存在fil_dir");
        }

        try {
                String filename = file.getOriginalFilename();
                if (file.getSize() <= BlogConst.MAX_FILE_SIZE) {
                    ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
                    FileCopyUtils.copy(file.getInputStream(), byteStream);
                    result = byteStream.toString();
                    result = Utils.ReplaceImagePath(result,fil_dir);

                } else {
                    return ForeResponse.fail("文件过大");
                }

        } catch (Exception e) {
            return ForeResponse.fail();
        }
        return ForeResponse.success(result,666);
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
