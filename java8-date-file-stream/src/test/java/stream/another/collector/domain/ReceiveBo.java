package stream.another.collector.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ReceiveBo {

    private String receiveTime;

    private BigDecimal receiveAmount;

}
