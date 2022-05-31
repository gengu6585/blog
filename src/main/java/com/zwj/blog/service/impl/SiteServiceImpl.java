package com.zwj.blog.service.impl;

import com.github.pagehelper.PageHelper;
import com.zwj.blog.dao.UploadFileMapper;
import com.zwj.blog.dto.MetaDto;
import com.zwj.blog.exception.TipException;
import com.zwj.blog.model.Bo.ArchiveBo;
import com.zwj.blog.model.Vo.*;
import com.zwj.blog.service.SiteService;
import com.zwj.blog.utils.DateKit;
import com.zwj.blog.utils.Utils;
import com.zwj.blog.utils.backup.Backup;
import com.zwj.blog.constant.BlogConst;
import com.zwj.blog.controller.admin.UploadFileController;
import com.zwj.blog.dao.CommentMapper;
import com.zwj.blog.dao.ArticleMapper;
import com.zwj.blog.dao.PropertyMapper;
import com.zwj.blog.dto.SiteProperty;
import com.zwj.blog.model.Bo.BackResponse;
import com.zwj.blog.model.Bo.Statistics;
import com.zwj.blog.utils.ZipUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

@Service
public class SiteServiceImpl implements SiteService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SiteServiceImpl.class);

    @Resource
    private CommentMapper commentDao;

    @Resource
    private ArticleMapper contentDao;

    @Resource
    private UploadFileMapper attachDao;

    @Resource
    private PropertyMapper metaDao;

    @Override
    public List<Comment> recentComments(int limit) {
        LOGGER.debug("Enter recentComments method:limit={}", limit);
        if (limit < 0 || limit > 10) {
            limit = 10;
        }
        CommentExample example = new CommentExample();
        example.setOrderByClause("created desc");
        PageHelper.startPage(1, limit);
        List<Comment> byPage = commentDao.selectByExampleWithBLOBs(example);
        LOGGER.debug("Exit recentComments method");
        return byPage;
    }

    @Override
    public List<Article> recentContents(int limit) {
        LOGGER.debug("Enter recentContents method");
        if (limit < 0 || limit > 10) {
            limit = 10;
        }
        ArticleExample example = new ArticleExample();
        example.createCriteria().andStatusEqualTo(SiteProperty.PUBLISH.getProperty()).andTypeEqualTo(SiteProperty.ARTICLE.getProperty());
        example.setOrderByClause("created desc");
        PageHelper.startPage(1, limit);
        List<Article> list = contentDao.selectByExample(example);
        LOGGER.debug("Exit recentContents method");
        return list;
    }

    @Override
    public BackResponse backup(String bk_type, String bk_path, String fmt) throws Exception {
        BackResponse backResponse = new BackResponse();
        if (bk_type.equals("attach")) {
            if (StringUtils.isBlank(bk_path)) {
                throw new TipException("请输入备份文件存储路径");
            }
            if (!(new File(bk_path)).isDirectory()) {
                throw new TipException("请输入一个存在的目录");
            }
            String bkAttachDir = UploadFileController.CLASSPATH + "upload";
            String bkThemesDir = UploadFileController.CLASSPATH + "templates/themes";

            String fname = DateKit.dateFormat(new Date(), fmt) + "_" + Utils.getRandomNumber(5) + ".zip";

            String attachPath = bk_path + "/" + "attachs_" + fname;
            String themesPath = bk_path + "/" + "themes_" + fname;

            ZipUtils.zipFolder(bkAttachDir, attachPath);
            ZipUtils.zipFolder(bkThemesDir, themesPath);

            backResponse.setAttachPath(attachPath);
            backResponse.setThemePath(themesPath);
        }
        if (bk_type.equals("db")) {

            String bkAttachDir = UploadFileController.CLASSPATH + "upload/";
            if (!(new File(bkAttachDir)).isDirectory()) {
                File file = new File(bkAttachDir);
                if (!file.exists()) {
                    file.mkdirs();
                }
            }
            String sqlFileName = "tale_" + DateKit.dateFormat(new Date(), fmt) + "_" + Utils.getRandomNumber(5) + ".sql";
            String zipFile = sqlFileName.replace(".sql", ".zip");

            Backup backup = new Backup(Utils.getNewDataSource().getConnection());
            String sqlContent = backup.execute();

            File sqlFile = new File(bkAttachDir + sqlFileName);
            write(sqlContent, sqlFile, Charset.forName("UTF-8"));

            String zip = bkAttachDir + zipFile;
            ZipUtils.zipFile(sqlFile.getPath(), zip);

            if (!sqlFile.exists()) {
                throw new TipException("数据库备份失败");
            }
            sqlFile.delete();

            backResponse.setSqlPath(zipFile);

            // 10秒后删除备份文件
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    new File(zip).delete();
                }
            }, 10 * 1000);
        }
        return backResponse;
    }

    @Override
    public Comment getComment(Integer coid) {
        if (null != coid) {
            return commentDao.selectByPrimaryKey(coid);
        }
        return null;
    }

    @Override
    public Statistics getStatistics() {
        LOGGER.debug("Enter getStatistics method");
        Statistics statistics = new Statistics();

        ArticleExample articleExample = new ArticleExample();
        articleExample.createCriteria().andTypeEqualTo(SiteProperty.ARTICLE.getProperty()).andStatusEqualTo(SiteProperty.PUBLISH.getProperty());
        Integer articles = contentDao.countByExample(articleExample);

        Long comments = commentDao.countByExample(new CommentExample());

        Long attachs = (long) attachDao.countByExample(new UploadFileExample());

        MetaExample metaExample = new MetaExample();
        metaExample.createCriteria().andTypeEqualTo(SiteProperty.LINK.getProperty());
        Long links = metaDao.countByExample(metaExample);

        statistics.setArticles(articles);
        statistics.setComments(comments);
        statistics.setAttachs(attachs);
        statistics.setLinks(links);
        LOGGER.debug("Exit getStatistics method");
        return statistics;
    }

    @Override
    public List<ArchiveBo> getArchives() {
        LOGGER.debug("Enter getArchives method");
        List<ArchiveBo> archives = contentDao.findReturnArchiveBo();
        if (null != archives) {
            archives.forEach(archive -> {
                ArticleExample example = new ArticleExample();
                ArticleExample.Criteria criteria = example.createCriteria().andTypeEqualTo(SiteProperty.ARTICLE.getProperty()).andStatusEqualTo(SiteProperty.PUBLISH.getProperty());
                example.setOrderByClause("created desc");
                String date = archive.getDate();
                Date sd = DateKit.dateFormat(date, "yyyy年");
                int start = DateKit.getUnixTimeByDate(sd);
                int end = DateKit.getUnixTimeByDate(DateKit.dateAdd(DateKit.INTERVAL_YEAR, sd, 1)) - 1;
                criteria.andCreatedGreaterThan(start);
                criteria.andCreatedLessThan(end);
                List<Article> contentss = contentDao.selectByExample(example);
                archive.setArticles(contentss);
            });
        }
        LOGGER.debug("Exit getArchives method");
        return archives;
    }

    @Override
    public List<MetaDto> metas(String type, String orderBy, int limit) {
        LOGGER.debug("Enter metas method:type={},order={},limit={}", type, orderBy, limit);
        List<MetaDto> retList = null;
        if (StringUtils.isNotBlank(type)) {
            if (StringUtils.isBlank(orderBy)) {
                orderBy = "count desc, a.mid desc";
            }
            if (limit < 1 || limit > BlogConst.MAX_POSTS) {
                limit = 10;
            }
            Map<String, Object> paraMap = new HashMap<>();
            paraMap.put("type", type);
            paraMap.put("order", orderBy);
            paraMap.put("limit", limit);
            retList = metaDao.selectFromSql(paraMap);
        }
        LOGGER.debug("Exit metas method");
        return retList;
    }


    private void write(String data, File file, Charset charset) {
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(file);
            os.write(data.getBytes(charset));
        } catch (IOException var8) {
            throw new IllegalStateException(var8);
        } finally {
            if (null != os) {
                try {
                    os.close();
                } catch (IOException var2) {
                    var2.printStackTrace();
                }
            }
        }

    }

}
