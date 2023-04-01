package com.example.demo.premiums;

import java.math.BigDecimal;

public class EXTRA implements PremiumType {
    @Override
    public BigDecimal calculate(BigDecimal premium,BigDecimal penaltyRate) {
        return premium.divide(new BigDecimal("2.5")).multiply(penaltyRate).multiply(new BigDecimal("8"));
    }

    @Override
    public BigDecimal getNet(BigDecimal premium, BigDecimal penaltyRate) {
        return premium.subtract(BigDecimal.ONE);
    }

    @Override
    public BigDecimal getTax() {
        return null;
    }
}
