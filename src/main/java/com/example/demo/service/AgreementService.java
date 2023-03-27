package com.example.demo.service;

import com.example.demo.agreement.dao.Agreement;
import com.example.demo.agreement.dao.AgreementExtraCalc;
import com.example.demo.agreement.dao.Type;
import com.example.demo.repository.AgreementExtraCalcRepository;
import com.example.demo.repository.AgreementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class AgreementService {

    @Autowired
    AgreementRepository agreementRepository;

    @Autowired
    AgreementExtraCalcRepository agreementExtraCalcRepository;

    public Agreement getAgreementById(Long id){
        return agreementRepository.findById(id).get();
    }

    public List<Agreement> getAllAgreements(){
        return agreementRepository.findAll();
    }

    public Long addNewAgreement(Agreement agreement){
        agreementRepository.save(agreement);
        return agreement.getId();
    }

    public BigDecimal getAgreementPenaltyOfAgreement(Long id){

        Agreement agreement=agreementRepository.findById(id).get();

        if(agreement.getType()==Type.X){
            return agreement.getPremium().multiply(agreement.getPenaltyRate());
        }else if(agreement.getType()==Type.Y){
            return agreement.getPremium().multiply(agreement.getPenaltyRate()).multiply(new BigDecimal("5"));
        }else if(agreement.getType()==Type.Z){
            return agreement.getPremium().multiply(agreement.getPenaltyRate()).multiply(new BigDecimal("10"));
        }else if(agreement.getType()==Type.EXTRA){
            return agreement.getPremium().divide(new BigDecimal("2.5")).multiply(agreement.getPenaltyRate()).multiply(new BigDecimal("8"));
        }

        return BigDecimal.ZERO;

    }


    public long addNewAgreementExtraCalc(AgreementExtraCalc agreementExtraCalc){

        Agreement agreement=agreementRepository.findById(agreementExtraCalc.getAgreementId()).get();

        agreementExtraCalc.setCommission(agreement.getPremium().multiply(BigDecimal.valueOf(17)));

        if(agreement.getType()==Type.X){
            agreementExtraCalc.setNet(agreement.getPremium());
        }else if(agreement.getType()==Type.Y){
            agreementExtraCalc.setNet(agreement.getPremium().multiply(agreement.getPenaltyRate()));
        }else if(agreement.getType()==Type.Z){
            agreementExtraCalc.setNet(agreement.getPremium().subtract(agreement.getPremium().multiply(agreement.getPenaltyRate().divide(BigDecimal.valueOf(100)))));
        }else if(agreement.getType()==Type.EXTRA){
            agreementExtraCalc.setNet(agreement.getPremium().subtract(BigDecimal.ONE));
        }

        if(agreement.getType()==Type.X){
            agreementExtraCalc.setTax(BigDecimal.ONE);
        }else if(agreement.getType()==Type.Y){
            agreementExtraCalc.setTax(BigDecimal.TEN);
        }else if(agreement.getType()==Type.Z){
            agreementExtraCalc.setTax(BigDecimal.ZERO);
        }else if(agreement.getType()==Type.EXTRA){
            agreementExtraCalc.setTax(null);
        }

        agreementExtraCalcRepository.save(agreementExtraCalc);

        return agreementExtraCalc.getId();
    }

    public AgreementExtraCalc getAgreementExtraCalc(Long id){
        return agreementExtraCalcRepository.findById(id).get();
    }


}
