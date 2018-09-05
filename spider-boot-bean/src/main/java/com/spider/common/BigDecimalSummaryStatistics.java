package com.spider.common;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.function.Consumer;

/**
 * BigDecimal stream 汇总类
 *
 * @author liuzhongkai
 */
public class BigDecimalSummaryStatistics implements Consumer<BigDecimal> {

    private long count;
    private BigDecimal sum = BigDecimal.ZERO;
    private BigDecimal min;
    private BigDecimal max;

    @Override
    public void accept(BigDecimal each) {
        this.count++;
        if (each != null) {
            this.sum = this.sum.add(each);
            this.min = this.min == null ? each : this.min.min(each);
            this.max = this.max == null ? each : this.max.max(each);
        }
    }

    public long getCount() {
        return this.count;
    }

    public BigDecimal getSum() {
        return this.sum;
    }

    public BigDecimal getMin() {
        return this.min == null ? BigDecimal.ZERO : min;
    }


    public BigDecimal getMax() {
        return this.max == null ? BigDecimal.ZERO : max;
    }

    public BigDecimal getAverage(MathContext context) {
        return this.count == 0 ? BigDecimal.ZERO : this.getSum().divide(BigDecimal.valueOf(this.count), context);
    }

    public BigDecimal getAverage() {
        return this.getAverage(MathContext.DECIMAL128);
    }

    public BigDecimalSummaryStatistics combine(BigDecimalSummaryStatistics summaryStatistics) {
        this.count += summaryStatistics.count;
        this.sum = this.sum.add(summaryStatistics.sum);
        if (summaryStatistics.min != null) {
            this.min = this.min == null ? summaryStatistics.min : this.min.min(summaryStatistics.min);
        }
        if (summaryStatistics.max != null) {
            this.max = this.max == null ? summaryStatistics.max : this.max.max(summaryStatistics.max);
        }
        return this;
    }

    @Override
    public String toString() {
        return String.format(
                "%s{count=%d, sum=%f, min=%f, average=%f, max=%f}",
                this.getClass().getSimpleName(),
                getCount(),
                getSum().doubleValue(),
                getMin().doubleValue(),
                getAverage().doubleValue(),
                getMax().doubleValue());
    }
}
