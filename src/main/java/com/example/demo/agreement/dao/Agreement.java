package com.example.demo.agreement.dao;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Agreement {

    @Getter @Setter
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Getter @Setter
    @Column(name="agreement_no")
    private String agreementNo;

    @Getter @Setter
    @Column(name="premium")
    private BigDecimal premium;

    @Getter @Setter
    @Column(name="penalty_rate")
    private BigDecimal penaltyRate;

    @Getter @Setter
    @Column(name="period_in_day")
    private Integer periodInDay;

    @Getter @Setter
    @Column(name="type")
    private Type type;


}
