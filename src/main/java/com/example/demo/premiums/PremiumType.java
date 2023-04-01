package com.example.demo.premiums;

import java.math.BigDecimal;

public interface PremiumType {
    BigDecimal calculate(BigDecimal premium,BigDecimal penaltyRate);
    BigDecimal getNet(BigDecimal premium, BigDecimal penaltyRate);
    BigDecimal getTax();
}
