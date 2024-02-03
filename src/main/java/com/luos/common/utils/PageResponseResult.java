package com.luos.common.utils;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.luos.common.enums.StatusEnums;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.validation.BindingResult;

import java.util.List;

/**
 * 分页接口返回工具类
 */
@CommonsLog
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageResponseResult<T>{

    @ApiModelProperty(value = "执行是否成功,true:成功,false:失败", required = true)
    private boolean success;//是否成功

    /**
     * 响应码
     */
    @ApiModelProperty(value = "状态码")
    private int code = 200;

    /**
     * 响应消息
     */
    @ApiModelProperty(value = "消息")
    private String msg = "成功";

    /**
     * 响应数据
     */
    @ApiModelProperty(value = "返回数据")
    private List<T> data;

    /**
     * 接口数据长度
     */
    @ApiModelProperty(value = "总条数")
    private long total;

    @ApiModelProperty(value = "总页数")
    private long pages;

    @ApiModelProperty(value = "当前页数")
    private long current;

    @ApiModelProperty(value = "当前页条数")
    private long size;

    public PageResponseResult() {
    }

    public PageResponseResult(boolean success, Integer code, String message, List<T> data) {
        this.success = success;
        this.code = code;
        this.msg = message;
        this.data = data;
    }

    public PageResponseResult(boolean success, Integer code, String message) {
        this.success = success;
        this.code = code;
        this.msg = message;
    }

    /**
     * 前台语料列表专用返回（最多返回5页数据）
     * @param count
     * @param page
     * @param limit
     * @param data
     */
    public PageResponseResult(Long count,Integer page, Integer limit, List<T> data){
        super();
        this.success= true;
        this.pages = (count%limit == 0) ? count/limit:(count/limit)+1 > 5?5:(count%limit == 0) ? count/limit:(count/limit)+1;//总页数
        this.total = pages >= 5?(5*limit):count;//在总条数
        this.size = limit;//每页条数
        this.current = page;//当前页
        this.data = data;
        this.code = StatusEnums.OK.getCode();
        this.msg = StatusEnums.OK.getMsg();
    }

    /**
     * 返回数据给表格
     * <p>
     * 配合mybatisplus分页查询使用
     */
    public PageResponseResult(IPage<T> iPage) {
        super();
        this.success= true;
        this.pages = iPage.getPages();//总页数
        this.total = iPage.getTotal();//在总条数
        this.current = iPage.getCurrent();//当前页
        this.size = iPage.getSize();//每页条数
        this.data = iPage.getRecords();
        this.code = StatusEnums.OK.getCode();
        this.msg = StatusEnums.OK.getMsg();
    }


    /**
     * 成功
     */
    public static <T> PageResponseResult<T> success(List<T> data) {
        return new PageResponseResult<>(true, StatusEnums.OK.getCode(), StatusEnums.OK.getMsg(), data);
    }

    /**
     * 成功
     */
    public static PageResponseResult success(String msg) {
        return new PageResponseResult<>(true, StatusEnums.OK.getCode(), StatusEnums.OK.getMsg(), null);
    }
    /**
     * 失败
     */
    public static <T> PageResponseResult<T> error() {
        return new PageResponseResult<>(false, StatusEnums.ERROR.getCode(), StatusEnums.ERROR.getMsg(), null);
    }

    /**
     * 失败
     */
    public static <T> PageResponseResult<T> error(String msg) {
        return new PageResponseResult<>(false, StatusEnums.ERROR.getCode(), msg, null);
    }

    /**
     * 失败
     */
    public static <T> PageResponseResult<T> error(int code, String msg, List<T> data) {
        return new PageResponseResult<>(false, code, msg, data);
    }

    public static <T> PageResponseResult<T> error(BindingResult bindingResult) {
        StringBuffer bindingErrorInfoList = new StringBuffer();
        bindingResult.getAllErrors().forEach(
                objectError -> bindingErrorInfoList.append(objectError.getDefaultMessage()).append(",")
        );
        log.error(bindingResult);
        return new PageResponseResult<>(false, StatusEnums.ERROR.getCode(),bindingErrorInfoList.delete(bindingErrorInfoList.length() - 1, bindingErrorInfoList.length()).toString() , null);
    }

    /**
     * 失败
     */
    public static <T> PageResponseResult<T> error(StatusEnums statusEnums, List<T> date) {
        return new PageResponseResult<>(false, statusEnums.getCode(), statusEnums.getMsg(), date);
    }

    /**
     * 失败
     */
    public static <T> PageResponseResult<T> error(StatusEnums statusEnums) {
        return new PageResponseResult<>(false, statusEnums.getCode(), statusEnums.getMsg(),null);
    }

}
