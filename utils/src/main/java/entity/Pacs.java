package entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.util.TypeUtils;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/10/18 20:20
 * @see TypeUtils#compatibleWithFieldName 根据field name的大小写输出输入数据
 */
@Data
public class Pacs {


        /**
         * 一个申请单对应多个检查项目
         *
         * {@code com.df.cbhis.lc.shenqingdanzx.jiancha.model.YJ_JianChaSqd#jianChaSqdId}
         */
        private String jianChaSqdId;
        /**
         * 院区ID
         *
         * {@code com.df.cbhis.lc.shenqingdanzx.jiancha.model.YJ_JianChaSqd#yuanQuId}
         */
        private String yuanQuId;
        /**
         * 病人ID: 患者院内的唯一号，多次门诊住院相同
         * {@code com.df.cbhis.lc.shenqingdanzx.jiancha.model.YJ_JianChaSqd#bingRenId}
         */
        private String bingRenId;
        /**
         * 住院号, 每次住院不变
         *
         * {@code com.df.cbhis.lc.shenqingdanzx.jiancha.model.YJ_JianChaSqd#zhuYuanHao}
         */
        private String zhuYuanHao;
        /**
         * 就诊卡号
         *
         * {@code com.df.cbhis.lc.shenqingdanzx.jiancha.model.YJ_JianChaSqd#jiuZhenKh}
         */
        private String jiuZhenKh;
        /**
         * 门诊住院标志
         *
         * {@code com.df.cbhis.lc.shenqingdanzx.jiancha.model.YJ_JianChaSqd#menZhenZyBz}
         */
        private Integer menZhenZyBz;
        /**
         * 一个申请单多个检查项目用,逗号分开
         *
         * {@code com.df.cbhis.lc.shenqingdanzx.jiancha.model.YJ_JianChaSqd#jianChaXmId}
         */
        private String jianChaXmId;
        /**
         * 检查项目名称
         *
         * {@code com.df.cbhis.lc.shenqingdanzx.jiancha.model.YJ_JianChaSqd#jianChaXmMc}
         */
        private String jianChaXmMc;
        /**
         * 检查分类名称
         *
         * {@code com.df.cbhis.lc.shenqingdanzx.jiancha.model.YJ_JianChaSqd#jianChaBzFlMc}
         */
        private String jianChaBzFlMc;

        /**
         * 检查分类 id
         */
        private String jianChaFlId;
        /**
         * 检查分类名称: 第二级分类
         */
        private String jianChaFlMc;
        /**
         * 检查大类分类 ID: 第一级分类
         */
        private String jianChaDLFlId;
        /**
         * 检查大类分类名称: 第一级分类
         */
        private String jianChaDLFlMc;
        /**
         * 就诊ID: 患者门诊的流水号（每次挂号不一样）
         *
         * {@code com.df.cbhis.lc.shenqingdanzx.jiancha.model.YJ_JianChaSqd#jiuZhenId}
         */
        private String jiuZhenId;
        /**
         * 病人住院ID: 患者住院的流水号（每次住院不一样）
         *
         * {@code com.df.cbhis.lc.shenqingdanzx.jiancha.model.YJ_JianChaSqd#bingRenZyId}
         */
        private String bingRenZyId;
        /**
         * 住院次数
         * GY_BINGRENZSY.GY_BINGRENXX
         *
         * {@code com.df.cbhis.bingrenzsy.bingrengl.model.GY_BingRenXx#zhuYuanCs}
         */
        private Integer zhuYuanCs;
        /**
         * 患者姓名
         *
         * {@code com.df.cbhis.lc.shenqingdanzx.jiancha.model.YJ_JianChaSqd#bingRenXm}
         */
        private String bingRenXm;
        /**
         * 患者年龄: 包含了年龄+单位
         *
         * {@code com.df.cbhis.lc.shenqingdanzx.jiancha.model.YJ_JianChaSqd#nianLing}
         */
        private String nianLing;
        /**
         * 患者性别: 1:男， 2：女 ，9未知
         *
         * {@code com.df.cbhis.lc.shenqingdanzx.jiancha.model.YJ_JianChaSqd#xingBie}
         */
        private Integer xingBie;
        /**
         * 出生日期
         *
         * {@code com.df.cbhis.lc.shenqingdanzx.jiancha.model.YJ_JianChaSqd#chuShengRq}
         */
        @JSONField(format = "yyyy-MM-dd'T'HH:mm:ss")
        private Date chuShengRq = new Date();

        // 病人信息
        /**
         * 身份证号
         *
         * {@code com.df.cbhis.bingrenzsy.bingrengl.model.GY_BingRenXx#shenFenZh}
         */
        private String shenFenZh;
        /**
         * 现住址
         *
         * {@code com.df.cbhis.bingrenzsy.bingrengl.model.GY_BingRenXx#xianZhuZhiXxDz}
         */
        private String xianZhuZhiXxDz;
        /**
         * 现住址电话
         *
         * {@code com.df.cbhis.bingrenzsy.bingrengl.model.GY_BingRenXx#xianZhuZhiDh}
         */
        private String xianZhuZhiDh;
        /**
         * 婚否
         *
         * {@code com.df.cbhis.bingrenzsy.bingrengl.model.GY_BingRenXx#hunYinMc}
         */
        private String hunYinMc;
        /**
         * 职业名称
         *
         * {@code com.df.cbhis.bingrenzsy.bingrengl.model.GY_BingRenXx#zhiYeMc}
         */
        private String zhiYeMc;
        /**
         * 民族
         *
         * {@code com.df.cbhis.bingrenzsy.bingrengl.model.GY_BingRenXx#minZuMc}
         */
        private String minZuMc;
        /**
         * 当前科室ID
         *
         * {@code com.df.cbhis.jj.zhuyuan.bingrengl.model.ZY_BingRenXx#dangQianKsId}
         */
        private String keShiId;
        /**
         * 科室名称
         *
         * {@code com.df.cbhis.jj.zhuyuan.bingrengl.model.ZY_BingRenXx#dangQianKsMc}
         */
        private String keShiMc;
        /**
         * 当前病区ID
         *
         * {@code com.df.cbhis.jj.zhuyuan.bingrengl.model.ZY_BingRenXx#dangQianBqId}
         */
        private String bingQuId;
        /**
         * 当前病区名称
         * {@code com.df.cbhis.jj.zhuyuan.bingrengl.model.ZY_BingRenXx#dangQianBqMc}
         */
        private String bingQuMc;
        /**
         * 当前病床号
         * {@code com.df.cbhis.jj.zhuyuan.bingrengl.model.ZY_BingRenXx#dangQianCwId}
         */
        private String chuangWeiHao;

        // 检查报告内容
        /**
         * 临床诊断
         * {@code com.df.cbhis.lc.shenqingdanzx.jiancha.model.YJ_JianChaSqdNr#linChuangZd}
         */
        private String linChuangZd;

        private String lingChuangZd;

        /**
         * 简要病史
         * {@code com.df.cbhis.lc.shenqingdanzx.jiancha.model.YJ_JianChaSqdNr#jianYaoBs}
         */
        private String jianYaoBs;
        /**
         * 相关检查
         * {@code com.df.cbhis.lc.shenqingdanzx.jiancha.model.YJ_JianChaSqdNr#xiangGuanJc}
         */
        private String xiangGuanJc;
        /**
         * 注意事项
         * {@code com.df.cbhis.lc.shenqingdanzx.jiancha.model.YJ_JianChaSqd#zhuYiSx}
         */
        @JSONField(name = "ZHUYISX")// 序列化首字母不转小写
        private String ZHUYISX;

        /**
         * 检查部位名称(多个用逗号分开)
         * {@code com.df.cbhis.lc.shenqingdanzx.jiancha.model.YJ_JianChaSqd#jianChaBwMc}
         */
        private String jianChaBwMc;
        /**
         * 检查说明
         * {@code com.df.cbhis.lc.shenqingdanzx.jiancha.model.YJ_JianChaSqd#jianChaSm}
         */
        private String jianChaSm;
        /**
         * 开单科室ID
         * {@code com.df.cbhis.lc.shenqingdanzx.jiancha.model.YJ_JianChaSqd#kaiDanKs}
         */
        private String kaiDanKs;
        /**
         * 开单科室名称
         * {@code com.df.cbhis.lc.shenqingdanzx.jiancha.model.YJ_JianChaSqd#kaiDanKsMc}
         */
        private String kaiDanKsMc;
        /**
         * 开单人
         * {@code com.df.cbhis.lc.shenqingdanzx.jiancha.model.YJ_JianChaSqd#kaiDanRen}
         */
        private String kaiDanRen;
        /**
         * 开单人姓名
         * {@code com.df.cbhis.lc.shenqingdanzx.jiancha.model.YJ_JianChaSqd#kaiDanRenXm}
         */
        private String kaiDanRenXm;
        /**
         * 开单日期: 即为申请日期
         * {@code com.df.cbhis.lc.shenqingdanzx.jiancha.model.YJ_JianChaSqd#kaiDanRq}
         */
        @JSONField(format = "yyyy-MM-dd'T'HH:mm:ss")
        private Date kaiDanRq = new Date();
        /**
         * 检查科室
         * {@code com.df.cbhis.lc.shenqingdanzx.jiancha.model.YJ_JianChaSqd#jianChaKs}
         */
        private String jianChaKs;
        /**
         * 检查科室名称
         * {@code com.df.cbhis.lc.shenqingdanzx.jiancha.model.YJ_JianChaSqd#jianChaKsMc}
         */
        private String jianChaKsMc;
        /**
         * 申请单状态
         * 检查状态：
         *             510 新开立
         *             520 待划价
         *             530 待登记
         *             540 已预约
         *             550 已安排
         *             560 已完成
         *             570 已报告
         *             580 已审核
         *             590 已打印
         *             501已撤销
         *             502已退单
         * {@code com.df.cbhis.mic.lc.shenqingdanzx.enums.EnumUtil.JianChaDangQianZtEnum}
         * {@code com.df.cbhis.lc.shenqingdanzx.jiancha.model.YJ_JianChaSqd#dangQianZt}
         */
        private String shenQingDanZt;
        /**
         * 收费标识
         * 0：未收费 1：已收费
         * {@code com.df.cbhis.lc.shenqingdanzx.jiancha.model.YJ_JianChaSqd#shouFeiBz}
         */
        private Integer shouFeiBz;
        /**
         * 急诊标志: 1：加急 0：不加急
         * {@code com.df.cbhis.lc.shenqingdanzx.jiancha.model.YJ_JianChaSqd#jiZhenBz}
         */
        private Integer jiZhenBz;
        /**
         * 金额
         * {@code com.df.cbhis.lc.shenqingdanzx.jiancha.model.YJ_JianChaSqd#feiYongHj}
         */
        @JSONField(name = "JinE") // 序列化首字母不转小写
        private BigDecimal JinE;

        @JSONField(name = "JIAOPIANFBZ") // 序列化首字母不转小写
        private Integer JIAOPIANFBZ; // 胶片费标志 0 否 1 是

        private Integer weiShouXfBz; // 担保病人, 未收先发标志: 0 否 1 是

        private String zuHao; // 申请单表的 医嘱 ID

        private String yuanQuBzBm; // 院区标准编码

}
