package test.basic.te;

// 保留固定小数位数：最后一位四舍五入
public class test005 {

    public static void main(String[] args) {

        System.out.println("数值 123.121 保留两位小数：\t" + round(123.121, 2));

    }

    public static String round(double value, int dotNum) {
        String strValue = String.valueOf(value); // 转换为字符串
        int pos = strValue.indexOf("."); // 小数点的位置
        int len = strValue.length(); // 字符串长度（数值总位数）
        int dotLen = len - 1 - pos; // 小数的位数
        double endValue = 0.0; // 保存运算结果的中间变量
        String endNum = ""; // 保留最终结果的变量
        if (dotNum < dotLen) { // 需要保留的小数位数小于实际小数位数
            String cNum = strValue.substring(pos + dotNum + 1, pos + dotNum + 2); // 获得需要进位的小数位
            int iNum = Integer.valueOf(cNum); // 转换为整数
            // 获得需要保留的未进行舍入处理的数值
            String sNum = strValue.substring(0, pos + dotNum + 1);
            endValue = Double.valueOf(sNum); // 转换为 double 型
            if (iNum >= 5) { // 如果需要舍入的值大于等于5
                String endPos = ""; // 存放需要进位的小数值
                String dotValue = ""; // 连接小数点后的多个 0
                for (int i = 1; i < dotNum; i++) {
                    dotValue = dotValue + "0"; // 将小数点后的多个0连接到一起
                }
                endPos = "0." + dotValue + "1"; // 需要进位的小数值
                endValue = endValue + Double.valueOf(endPos); // 四舍五入处理之后的值
                strValue = String.valueOf(endValue); // 处理后的值转换为字符串
                pos = strValue.indexOf("."); // 小数点的位置
                len = strValue.length(); // 数值的总位数
                dotLen = len - 1 - pos; // 小数的位数
                if (dotLen < dotNum) { // 如果小数位数不足，则补足位数
                    for (int i = pos + dotLen + 1; i < pos + dotNum + 1; i++) {
                        endNum = String.valueOf(endValue) + "0"; // 补足小数位数
                    }
                } else { // 如果小数位数正好，或超过要求，则进行截位处理
                    endNum = String.valueOf(endValue).substring(0, pos + dotNum + 1);
                }
            } else {
                endNum = strValue.substring(0, strValue.indexOf(".")) +
                        strValue.substring(strValue.indexOf("."), strValue.indexOf(".") + dotNum + 1);
            }

        } else if (dotNum == dotLen) {
            endNum = String.valueOf(value); // 小数位数与要求的位数相同，直接转换为字符串
        } else {
            for (int i = 1; i <= dotNum - dotLen; i++) {
                strValue = strValue + "0"; // 补足小数位数
            }
            endNum = strValue; // 最终的值
        }

        return endNum;
    }

}
