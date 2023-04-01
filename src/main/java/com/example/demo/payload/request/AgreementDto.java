package com.example.demo.payload.request;

import com.example.demo.agreement.dao.Type;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AgreementDto {
    @Getter
    private String agreementNo;
    @Getter
    private BigDecimal premium;
    @Getter
    private BigDecimal penaltyRate;
    @Getter
    private Integer periodInDay;
    @Getter
    private Type type;
}
