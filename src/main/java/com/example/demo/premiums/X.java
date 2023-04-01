package com.example.demo.premiums;

import java.math.BigDecimal;

public class X implements PremiumType {
    @Override
    public BigDecimal calculate(BigDecimal premium,BigDecimal penaltyRate) {
        return premium.multiply(penaltyRate);
    }

    @Override
    public BigDecimal getNet(BigDecimal premium, BigDecimal penaltyRate) {
        return premium;
    }

    @Override
    public BigDecimal getTax() {
        return BigDecimal.ONE;
    }
}
