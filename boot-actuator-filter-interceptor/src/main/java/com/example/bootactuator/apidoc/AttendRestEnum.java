package com.example.bootactuator.apidoc;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/9/9 19:56
 */
public enum AttendRestEnum {

    /**
     * @apiDefine ATTEND_EMPTY_ID
     * @apiError 5001 规则不能为空
     */
    ATTEND_EMPTY_ID(5001, "规则不能为空"),
    /**
     * @apiDefine ATTEND_EMPTY_VALUE
     * @apiError 5002 值不能为空
     */
    ATTEND_EMPTY_VALUE(5002, "值不能为空"),
    /**
     * @apiDefine ATTEND_ERROR_EQUAL_VALUE
     * @apiError 5003 设置参数的个数不一致
     */
    ATTEND_ERROR_EQUAL_VALUE(5003, "设置参数的个数不一致"),
    /**
     * @apiDefine ATTEND_EMPTY_LONGITUDE
     * @apiError 5004 经度不能为空
     */
    ATTEND_EMPTY_LONGITUDE(5004, "经度不能为空"),
    /**
     * @apiDefine ATTEND_EMPTY_LATITUDE
     * @apiError 5005 纬度不能为空
     */
    ATTEND_EMPTY_LATITUDE(5005, "纬度不能为空"),
    /**
     * @apiDefine ATTEND_EMPTY_DEVICE_SN
     * @apiError 5006 设备不能为空
     */
    ATTEND_EMPTY_DEVICE_SN(5006, "设备不能为空"),

    /**
     * @apiDefine ATTEND_EMPTY_ORG
     * @apiError 5007 机构不能为空
     */
    ATTEND_EMPTY_ORG(5007, "机构不能为空"),

    /**
     * @apiDefine ATTEND_NOT_FIND_ORG
     * @apiError 5008 机构没有找到
     */
    ATTEND_NOT_FIND_ORG(5008, "机构没有找到"),

    /**
     * @apiDefine ATTEND_EMPTY_MINUTES
     * @apiError 5009 使用时长不能为空
     */
    ATTEND_EMPTY_MINUTES(5009, "使用时长不能为空"),

    /**
     * @apiDefine ATTEND_ERROR_MINUTES
     * @apiError 5010 使用时长不能为负数
     */
    ATTEND_ERROR_MINUTES(5010, "使用时长不能为负数"),

    /**
     * @apiDefine ATTEND_ERROR2_MINUTES
     * @apiError 5011 当天使用时长不能大于24小时
     */
    ATTEND_ERROR2_MINUTES(5011, "当天使用时长不能大于24小时");

    private final int code;
    private final String msg;

    AttendRestEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }


}
