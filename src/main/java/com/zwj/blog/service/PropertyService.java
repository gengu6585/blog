package com.zwj.blog.service;

import com.zwj.blog.dto.MetaDto;
import com.zwj.blog.model.Vo.Meta;

import java.util.List;

/**
 * 分类信息service接口
 */
public interface PropertyService {
    /**
     * 根据类型和名字查询项
     *
     * @param type
     * @param name
     * @return
     */
    MetaDto getMeta(String type, String name);

    /**
     * 根据文章id获取项目个数
     *
     * @param mid
     * @return
     */
    Integer countMeta(Integer mid);

    /**
     * 根据类型查询项目列表
     *
     * @param types
     * @return
     */
    List<Meta> getMetas(String types);


    /**
     * 保存多个项目
     *
     * @param cid
     * @param names
     * @param type
     */
    void saveMetas(Integer cid, String names, String type);

    /**
     * 保存项目
     *
     * @param type
     * @param name
     * @param mid
     */
    void saveMeta(String type, String name, Integer mid);

    /**
     * 根据类型查询项目列表，带项目下面的文章数
     *
     * @return
     */
    List<MetaDto> getMetaList(String type, String orderby, int limit);

    /**
     * 删除项目
     *
     * @param mid
     */
    void delete(int mid);

    /**
     * 保存项目
     *
     * @param metas
     */
    void saveMeta(Meta metas);

    /**
     * 更新项目
     *
     * @param metas
     */
    void update(Meta metas);
}
