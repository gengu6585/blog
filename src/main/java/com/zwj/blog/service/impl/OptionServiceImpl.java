package com.zwj.blog.service.impl;

import com.zwj.blog.dao.OptionMapper;
import com.zwj.blog.model.Vo.Option;
import com.zwj.blog.model.Vo.OptionExample;
import com.zwj.blog.service.OptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * options表的service
 */
@Service
public class OptionServiceImpl implements OptionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OptionServiceImpl.class);

    @Resource
    private OptionMapper optionDao;

    @Override
    public void insertOption(Option option) {
        LOGGER.debug("Enter insertOption method:optionVo={}", option);
        optionDao.insertSelective(option);
        LOGGER.debug("Exit insertOption method.");
    }

    @Override
    @Transactional
    public void insertOption(String name, String value) {
        LOGGER.debug("Enter insertOption method:name={},value={}", name, value);
        Option option = new Option();
        option.setName(name);
        option.setValue(value);
        if (optionDao.selectByPrimaryKey(name) == null) {
            optionDao.insertSelective(option);
        } else {
            optionDao.updateByPrimaryKeySelective(option);
        }
        LOGGER.debug("Exit insertOption method.");
    }

    @Override
    @Transactional
    public void saveOptions(Map<String, String> options) {
        if (null != options && !options.isEmpty()) {
            options.forEach(this::insertOption);
        }
    }

    @Override
    public Option getOptionByName(String name) {
        return optionDao.selectByPrimaryKey(name);
    }

    @Override
    public List<Option> getOptions() {
        return optionDao.selectByExample(new OptionExample());
    }
}
