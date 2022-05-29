package com.zwj.blog.service;

import com.zwj.blog.model.Vo.Option;

import java.util.List;
import java.util.Map;

/**
 * options的接口
 */
public interface OptionService {

    void insertOption(Option option);

    void insertOption(String name, String value);

    List<Option> getOptions();


    /**
     * 保存一组配置
     *
     * @param options
     */
    void saveOptions(Map<String, String> options);

    Option getOptionByName(String name);
}
