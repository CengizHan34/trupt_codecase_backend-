package com.example.demo.premiums;

import java.math.BigDecimal;

public class Z implements PremiumType {
    @Override
    public BigDecimal calculate(BigDecimal premium,BigDecimal penaltyRate) {
        return premium.multiply(penaltyRate).multiply(new BigDecimal("10"));
    }

    @Override
    public BigDecimal getNet(BigDecimal premium, BigDecimal penaltyRate) {
        return premium.subtract(premium.multiply(penaltyRate.divide(BigDecimal.valueOf(100))));
    }

    @Override
    public BigDecimal getTax() {
        return BigDecimal.ZERO;
    }
}
