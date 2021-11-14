package util;

import java.util.HashMap;
import java.util.Map;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/5/18 8:54
 */
public class _24SolarTerms {

    private static final double D = 0.2422D;
    private final static Map<String, Integer[]> INCREASE_OFFSETMAP = new HashMap<>(); // +1偏移
    private final static Map<String, Integer[]> DECREASE_OFFSETMAP = new HashMap<>(); // -1偏移

    /**
     * 24节气
     **/
    private enum SolarTermsEnum {
        LI_CHUN, // --立春
        YU_SHUI, // --雨水
        JING_ZHE, // --惊蛰
        CHUN_FEN, // 春分
        QING_MING, // 清明
        GU_YU, // 谷雨
        LI_XIA, // 立夏
        XIAO_MAN, // 小满
        MANG_ZHONG, // 芒种
        XIA_ZHI, // 夏至
        XIAO_SHU, // 小暑
        DA_SHU, // 大暑
        LI_QIU, // 立秋
        CHU_SHU, // 处暑
        BAI_LU, // 白露
        QIU_FEN, // 秋分
        HAN_LU, // 寒露
        SHUANG_JIANG, // 霜降
        LI_DONG, // 立冬
        XIAO_XUE, // 小雪
        DA_XUE, // 大雪
        DONG_ZHI, // 冬至
        XIAO_HAN, // 小寒
        DA_HAN; // 大寒
    }

    static {
        DECREASE_OFFSETMAP.put(SolarTermsEnum.YU_SHUI.name(), new Integer[]{2026});//雨水
        INCREASE_OFFSETMAP.put(SolarTermsEnum.CHUN_FEN.name(), new Integer[]{2084});//春分
        INCREASE_OFFSETMAP.put(SolarTermsEnum.XIAO_MAN.name(), new Integer[]{2008});//小满
        INCREASE_OFFSETMAP.put(SolarTermsEnum.MANG_ZHONG.name(), new Integer[]{1902});//芒种
        INCREASE_OFFSETMAP.put(SolarTermsEnum.XIA_ZHI.name(), new Integer[]{1928});//夏至
        INCREASE_OFFSETMAP.put(SolarTermsEnum.XIAO_SHU.name(), new Integer[]{1925, 2016});//小暑
        INCREASE_OFFSETMAP.put(SolarTermsEnum.DA_SHU.name(), new Integer[]{1922});//大暑
        INCREASE_OFFSETMAP.put(SolarTermsEnum.LI_QIU.name(), new Integer[]{2002});//立秋
        INCREASE_OFFSETMAP.put(SolarTermsEnum.BAI_LU.name(), new Integer[]{1927});//白露
        INCREASE_OFFSETMAP.put(SolarTermsEnum.QIU_FEN.name(), new Integer[]{1942});//秋分
        INCREASE_OFFSETMAP.put(SolarTermsEnum.SHUANG_JIANG.name(), new Integer[]{2089});//霜降
        INCREASE_OFFSETMAP.put(SolarTermsEnum.LI_DONG.name(), new Integer[]{2089});//立冬
        INCREASE_OFFSETMAP.put(SolarTermsEnum.XIAO_XUE.name(), new Integer[]{1978});//小雪
        INCREASE_OFFSETMAP.put(SolarTermsEnum.DA_XUE.name(), new Integer[]{1954});//大雪
        DECREASE_OFFSETMAP.put(SolarTermsEnum.DONG_ZHI.name(), new Integer[]{1918, 2021});//冬至

        INCREASE_OFFSETMAP.put(SolarTermsEnum.XIAO_HAN.name(), new Integer[]{1982});//小寒
        DECREASE_OFFSETMAP.put(SolarTermsEnum.XIAO_HAN.name(), new Integer[]{2019});//小寒

        INCREASE_OFFSETMAP.put(SolarTermsEnum.DA_HAN.name(), new Integer[]{2082});//大寒
    }

    // 定义一个二维数组，第一维数组存储的是20世纪的节气C值，第二维数组存储的是21世纪的节气C值,0到23个，依次代表立春、雨水...大寒节气的C值
    private static final double[][] CENTURY_ARRAY =
            {{4.6295, 19.4599, 6.3826, 21.4155, 5.59, 20.888, 6.318, 21.86, 6.5, 22.2, 7.928, 23.65, 8.35,
                    23.95, 8.44, 23.822, 9.098, 24.218, 8.218, 23.08, 7.9, 22.6, 6.11, 20.84}
                    , {3.87, 18.73, 5.63, 20.646, 4.81, 20.1, 5.52, 21.04, 5.678, 21.37, 7.108, 22.83,
                    7.5, 23.13, 7.646, 23.042, 8.318, 23.438, 7.438, 22.36, 7.18, 21.94, 5.4055, 20.12}};

    /**
     * @param year 年份
     * @param name 节气的名称
     * @return 返回节气是相应月份的第几天
     */
    public static int getSolarTermNum(int year, String name) {

        double centuryValue = 0;//节气的世纪值，每个节气的每个世纪值都不同
        name = name.trim().toUpperCase();
        int ordinal = SolarTermsEnum.valueOf(name).ordinal();

        int centuryIndex = -1;
        if (year >= 1901 && year <= 2000) {//20世纪
            centuryIndex = 0;
        } else if (year >= 2001 && year <= 2100) {//21世纪
            centuryIndex = 1;
        } else {
            throw new RuntimeException("不支持此年份: " + year + "，目前只支持1901年到2100年的时间范围");
        }
        centuryValue = CENTURY_ARRAY[centuryIndex][ordinal];
        int dateNum;
        /*
         * 计算 num =[Y*D+C]-L这是传说中的寿星通用公式
         * 公式解读: 年数的后2位乘0.2422加C(即: centuryValue)取整数后，减闰年数
         */
        int y = year % 100;//步骤1:取年分的后两位数
        if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) { //闰年
            if (ordinal == SolarTermsEnum.XIAO_HAN.ordinal()
                    || ordinal == SolarTermsEnum.DA_HAN.ordinal()
                    || ordinal == SolarTermsEnum.LI_CHUN.ordinal()
                    || ordinal == SolarTermsEnum.YU_SHUI.ordinal()) {
                // 注意: 凡闰年3月1日前闰年数要减一，即: L=[(Y-1)/4],因为小寒、大寒、立春、雨水这两个节气都小于3月1日,所以 y = y-1
                y = y - 1;//步骤2
            }
        }
        dateNum = (int) (y * D + centuryValue) - (y / 4); // 步骤3，使用公式[Y*D+C]-L计算
        dateNum += specialYearOffset(year, name); // 步骤4，加上特殊的年分的节气偏移量
        return dateNum;
    }

    /**
     * 特例,特殊的年分的节气偏移量,由于公式并不完善，所以算出的个别节气的第几天数并不准确，在此返回其偏移量
     *
     * @param year 年份
     * @param name 节气名称
     * @return 返回其偏移量
     */
    public static int specialYearOffset(int year, String name) {
        int offset = 0;
        offset += getOffset(DECREASE_OFFSETMAP, year, name, -1);
        offset += getOffset(INCREASE_OFFSETMAP, year, name, 1);

        return offset;
    }

    public static int getOffset(Map<String, Integer[]> map, int year, String name, int offset) {
        int off = 0;
        Integer[] years = map.get(name);
        if (null != years) {
            for (int i : years) {
                if (i == year) {
                    off = offset;
                    break;
                }
            }
        }
        return off;
    }

    public static String solarTermToString(int year) {
        StringBuilder sb = new StringBuilder();
        sb.append("---").append(year);
        if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {//闰年
            sb.append(" 闰年");
        } else {
            sb.append(" 平年");
        }

        sb.append("\n")
                .append("立春: 2月").append(getSolarTermNum(year, SolarTermsEnum.LI_CHUN.name()))
                .append("日,雨水: 2月").append(getSolarTermNum(year, SolarTermsEnum.YU_SHUI.name()))
                .append("日,惊蛰:3月").append(getSolarTermNum(year, SolarTermsEnum.JING_ZHE.name()))
                .append("日,春分:3月").append(getSolarTermNum(year, SolarTermsEnum.CHUN_FEN.name()))
                .append("日,清明:4月").append(getSolarTermNum(year, SolarTermsEnum.QING_MING.name()))
                .append("日,谷雨:4月").append(getSolarTermNum(year, SolarTermsEnum.GU_YU.name()))
                .append("日,立夏:5月").append(getSolarTermNum(year, SolarTermsEnum.LI_XIA.name()))
                .append("日,小满:5月").append(getSolarTermNum(year, SolarTermsEnum.XIAO_MAN.name()))
                .append("日,芒种:6月").append(getSolarTermNum(year, SolarTermsEnum.MANG_ZHONG.name()))
                .append("日,夏至:6月").append(getSolarTermNum(year, SolarTermsEnum.XIA_ZHI.name()))
                .append("日,小暑:7月").append(getSolarTermNum(year, SolarTermsEnum.XIAO_SHU.name()))
                .append("日,大暑:7月").append(getSolarTermNum(year, SolarTermsEnum.DA_SHU.name()))
                .append("日,\n立秋:8月").append(getSolarTermNum(year, SolarTermsEnum.LI_QIU.name()))
                .append("日,处暑:8月").append(getSolarTermNum(year, SolarTermsEnum.CHU_SHU.name()))
                .append("日,白露:9月").append(getSolarTermNum(year, SolarTermsEnum.BAI_LU.name()))
                .append("日,秋分:9月").append(getSolarTermNum(year, SolarTermsEnum.QIU_FEN.name()))
                .append("日,寒露:10月").append(getSolarTermNum(year, SolarTermsEnum.HAN_LU.name()))
                .append("日,霜降:10月").append(getSolarTermNum(year, SolarTermsEnum.SHUANG_JIANG.name()))
                .append("日,立冬:11月").append(getSolarTermNum(year, SolarTermsEnum.LI_DONG.name()))
                .append("日,小雪:11月").append(getSolarTermNum(year, SolarTermsEnum.XIAO_XUE.name()))
                .append("日,大雪:12月").append(getSolarTermNum(year, SolarTermsEnum.DA_XUE.name()))
                .append("日,冬至:12月").append(getSolarTermNum(year, SolarTermsEnum.DONG_ZHI.name()))
                .append("日,小寒:1月").append(getSolarTermNum(year, SolarTermsEnum.XIAO_HAN.name()))
                .append("日,大寒:1月").append(getSolarTermNum(year, SolarTermsEnum.DA_HAN.name()));

        return sb.toString();
    }

    public static void main(String[] args) {
        for (int year = 1901; year < 2050; year++) {
            System.out.println(solarTermToString(year));
        }
    }
}
