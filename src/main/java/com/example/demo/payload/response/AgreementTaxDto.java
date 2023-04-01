package com.example.demo.payload.response;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AgreementTaxDto {
    private String agreementNo;
    private BigDecimal agreementTax;
}
