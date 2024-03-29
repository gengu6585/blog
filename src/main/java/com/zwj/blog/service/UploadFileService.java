package com.zwj.blog.service;

import com.github.pagehelper.PageInfo;
import com.zwj.blog.model.Vo.UploadFile;

import java.util.List;

public interface UploadFileService {

    List<UploadFile> getAttachsByCid(Integer cid);


    /**
     * 分页查询附件
     *
     * @param page
     * @param limit
     * @return
     */
    PageInfo<UploadFile> getAttachs(Integer page, Integer limit);

    /**
     * 保存附件
     *
     * @param fname
     * @param fkey
     * @param ftype
     * @param author
     */
    void save(String fname, String fkey, String ftype, Integer author,Integer articleId);

    /**
     * 根据附件id查询附件
     *
     * @param id
     * @return
     */
    UploadFile selectById(Integer id);

    /**
     * 删除附件
     *
     * @param id
     */
    void deleteById(Integer id);
}
