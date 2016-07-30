package de.number26.transactions.dto;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigDecimal;

@XmlRootElement
public class Sum implements Serializable {
    private BigDecimal sum = BigDecimal.ZERO;

    public Sum() {
    }

    public Sum(double sum) {
        this.sum = BigDecimal.valueOf(sum);
    }

    public double getSum() {
        return sum.doubleValue();
    }

    public void setSum(double sum) {
        this.sum = BigDecimal.valueOf(sum);
    }

    public void add(double value) {
        sum = sum.add(BigDecimal.valueOf(value));
    }
}
