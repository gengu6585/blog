package com.zwj.blog.service;

import com.zwj.blog.model.Vo.Log;

import java.util.List;

public interface LogService {

    /**
     * 保存操作日志
     *
     * @param log
     */
    void insertLog(Log log);

    /**
     * 保存
     *
     * @param action
     * @param data
     * @param ip
     * @param authorId
     */
    void insertLog(String action, String data, String ip, Integer authorId);

    /**
     * 获取日志分页
     *
     * @param page  当前页
     * @param limit 每页条数
     * @return 日志
     */
    List<Log> getLogs(int page, int limit);
}
