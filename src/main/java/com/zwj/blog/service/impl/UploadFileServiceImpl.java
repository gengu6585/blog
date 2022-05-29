package com.zwj.blog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zwj.blog.dao.UploadFileMapper;
import com.zwj.blog.utils.DateKit;
import com.zwj.blog.model.Vo.UploadFile;
import com.zwj.blog.model.Vo.UploadFileExample;
import com.zwj.blog.service.UploadFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UploadFileServiceImpl implements UploadFileService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UploadFileServiceImpl.class);

    @Resource
    private UploadFileMapper attachDao;

    @Override
    public PageInfo<UploadFile> getAttachs(Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        UploadFileExample uploadFileExample = new UploadFileExample();
        uploadFileExample.setOrderByClause("id desc");
        List<UploadFile> attachVos = attachDao.selectByExample(uploadFileExample);
        return new PageInfo<>(attachVos);
    }

    @Override
    public UploadFile selectById(Integer id) {
        if (null != id) {
            return attachDao.selectByPrimaryKey(id);
        }
        return null;
    }

    @Override
    @Transactional
    public void save(String fname, String fkey, String ftype, Integer author) {
        UploadFile attach = new UploadFile();
        attach.setFname(fname);
        attach.setAuthorId(author);
        attach.setFkey(fkey);
        attach.setFtype(ftype);
        attach.setCreated(DateKit.getCurrentUnixTime());
        attachDao.insertSelective(attach);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        if (null != id) {
            attachDao.deleteByPrimaryKey(id);
        }
    }
}
