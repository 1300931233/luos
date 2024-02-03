package com.luos.common.excel;

import cn.afterturn.easypoi.handler.inter.IExcelDictHandler;
import com.luos.console.system.service.SysDictDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 支持字典参数设置
 * 举例： @Excel(name = "性别", width = 15, dicCode = "sex")
 * 1、导出的时候会根据字典配置，把值1,2翻译成：男、女;
 * 2、导入的时候，会把男、女翻译成1,2存进数据库;
 */
@Slf4j
@Component
public class IExcelDictHandlerImpl implements IExcelDictHandler {

    @Autowired
    private SysDictDetailService testSysDictDetailService;
    private static SysDictDetailService sysDictDetailService;

    @PostConstruct
    public void init() {
        sysDictDetailService = this.testSysDictDetailService;
    }

    /**
     * 从值翻译到名称
     *
     * @param dict  字典Key
     * @param obj   对象
     * @param name  属性名称
     * @param value 属性值
     * @return
     */
    @Override
    public String toName(String dict, Object obj, String name, Object value) {
        return sysDictDetailService.getDictDataByTypeAndValue(dict, String.valueOf(value));
    }

    /**
     * 从名称翻译到值
     *
     * @param dict  字典Key
     * @param obj   对象
     * @param name  属性名称
     * @param value 属性值
     * @return
     */
    @Override
    public String toValue(String dict, Object obj, String name, Object value) {
        return null;
    }
}
