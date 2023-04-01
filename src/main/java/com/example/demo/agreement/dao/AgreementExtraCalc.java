package com.example.demo.agreement.dao;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AgreementExtraCalc {

    @Getter @Setter
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Getter @Setter
    private Long agreementId;

    @Getter @Setter
    private BigDecimal commission;

    @Getter @Setter
    private BigDecimal tax;

    @Getter @Setter
    private BigDecimal net;



}
