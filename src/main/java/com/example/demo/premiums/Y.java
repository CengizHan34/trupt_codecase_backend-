package com.example.demo.premiums;

import java.math.BigDecimal;

public class Y implements PremiumType {
    @Override
    public BigDecimal calculate(BigDecimal premium,BigDecimal penaltyRate) {
        return premium.multiply(penaltyRate).multiply(new BigDecimal("5"));
    }

    @Override
    public BigDecimal getNet(BigDecimal premium, BigDecimal penaltyRate) {
        return premium.multiply(penaltyRate);
    }

    @Override
    public BigDecimal getTax() {
        return BigDecimal.TEN;
    }

}
