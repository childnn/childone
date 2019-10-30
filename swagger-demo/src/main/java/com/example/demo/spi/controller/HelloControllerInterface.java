package com.example.demo.spi.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author MiaoOne
 * 2019/7/31 10:58
 */
@Api("测试 swagger 资源")
public interface HelloControllerInterface {

    /**
     * @see ApiOperation 描述方法
     * @see ApiParam 描述参数: 形参, 实参, 成员变量
     * @param name
     * @return
     */
    @ApiOperation(value = "获取名字", notes = "详细的描述..............", tags = {"什么鬼1", "什么鬼东西"}) // 描述方法
    String hello(String name);

    @ApiOperation("测试方法")
    String greet(@ApiParam(value = "随便写的", /*required = true, */allowableValues = "jack,rose", defaultValue = "jack") String aaa);

}
