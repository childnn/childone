package poi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@Accessors(chain = true)
public class ExcelHeaderInfo {

    // 行/列 都是由 索引 0 开始
    //标题的首行坐标
    private int firstRow;
    //标题的末行坐标
    private int lastRow;
    //标题的首列坐标
    private int firstCol;
    //标题的末列坐标
    private int lastCol;
    // 标题
    private String title;

}
