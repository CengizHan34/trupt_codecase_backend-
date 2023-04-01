package com.example.demo.service;

import com.example.demo.agreement.dao.Agreement;
import com.example.demo.agreement.dao.AgreementExtraCalc;
import com.example.demo.agreement.dao.Type;
import com.example.demo.payload.request.AgreementDto;
import com.example.demo.payload.response.AgreementTaxDto;
import com.example.demo.premiums.PremiumFactory;
import com.example.demo.premiums.PremiumType;
import com.example.demo.repository.AgreementExtraCalcRepository;
import com.example.demo.repository.AgreementRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@Log4j2
public class AgreementService {

    @Autowired
    AgreementRepository agreementRepository;

    @Autowired
    AgreementExtraCalcRepository agreementExtraCalcRepository;

    @Autowired
    ObjectMapper objectMapper;

    public Agreement getAgreementById(Long id) {
        log.info("Agreement request ID : {}, method : getAgreementById() ", id);
        Agreement agreement = agreementRepository.findById(id).get();
        return agreement;
    }

    public List<Agreement> getAllAgreements() {
        return agreementRepository.findAll();
    }

    private void validPremium(BigDecimal premium) {
        if (premium.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Premium cannot have a value less than or equal to zero");
        }
    }

    private void validPremiumRate(BigDecimal premiumRate) {
        if (!(premiumRate.compareTo(BigDecimal.ZERO) >= 0) && !(premiumRate.compareTo(new BigDecimal(100)) <= 0)) {
            throw new RuntimeException("Premium Rate must be between 0 and 100(including 0 and 100)");
        }
    }

    public Long addNewAgreement(Agreement agreement) {
        log.info("Agreement Request Object -> {}", agreement.toString());

        validPremium(agreement.getPremium());
        validPremiumRate(agreement.getPenaltyRate());

        agreementRepository.save(agreement);
        log.info("Agreement Object successfully created. ID : {}", agreement.getId());
        return agreement.getId();
    }

    public BigDecimal getAgreementPenaltyOfAgreement(Long id) {
        log.info("Agreement request ID : {}, method : getAgreementPenaltyOfAgreement() ", id);

        Agreement agreement = agreementRepository.findById(id).get();
        try {
            PremiumType premium = getPremiumType(agreement.getType());
            BigDecimal agreementPenalty = premium.calculate(agreement.getPremium(), agreement.getPenaltyRate());
            log.info("Agreement ID: {}, Agreement Penalty calculated. Agreement Penalty: {}", id, agreementPenalty);
            return agreementPenalty;
        } catch (Exception e) {
            return BigDecimal.ZERO;
        }
    }

    private PremiumType getPremiumType(Type type) {
        try {
            PremiumType premium = PremiumFactory.createPremium(type);
            log.info("Premium object successfully created. PremiumType: {}", type.name());
            return premium;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public long addNewAgreementExtraCalc(AgreementExtraCalc agreementExtraCalc) {
        log.info("AgreementExtraCalc request Object : {}, method : addNewAgreementExtraCalc() ",
                agreementExtraCalc.toString());

        Agreement agreement = agreementRepository.findById(agreementExtraCalc.getAgreementId()).get();

        agreementExtraCalc.setCommission(agreement.getPremium().multiply(BigDecimal.valueOf(17)));
        PremiumType premiumType = getPremiumType(agreement.getType());
        agreementExtraCalc.setNet(premiumType.getNet(agreement.getPremium(), agreement.getPenaltyRate()));
        agreementExtraCalc.setTax(premiumType.getTax());

        agreementExtraCalcRepository.save(agreementExtraCalc);
        log.info("AgreementExtraCalc successfully saved! AgreementExtraCalc ->  {}", agreementExtraCalc.toString());

        return agreementExtraCalc.getId();
    }

    public AgreementTaxDto getAgreementExtraCalc(Long id) {
        log.info("AgreementExtraCalc request ID : {}, method : getAgreementExtraCalc() ", id);

        AgreementTaxDto agreementTaxDto;
        BigDecimal taxAmount = BigDecimal.ZERO;

        AgreementExtraCalc agreementExtraCalc = agreementExtraCalcRepository.findById(id).get();

        Agreement agreement = getAgreementById(id);
        String agreementNo = agreement.getAgreementNo();

        taxAmount = agreementExtraCalc.getTax().multiply(agreement.getPremium());
        log.info("AgreementExtraCalc ID : {}, calculated tax value : {}", id, taxAmount);

        if (taxAmount.compareTo(BigDecimal.valueOf(1000)) < 1) {
            agreementTaxDto = new AgreementTaxDto(agreementNo, taxAmount);
        } else {
            taxAmount = taxAmount.subtract(BigDecimal.valueOf(500));
            agreementTaxDto = new AgreementTaxDto(agreementNo, taxAmount);
        }

        log.info("AgreementExtraCalc object -> {}", agreementTaxDto.toString());
        return agreementTaxDto;
    }

    public void saveAgreementAndAgreementExtraCalc(AgreementDto agreementDto) {
        log.info("AgreementDto request Object : {}, method : getAgreementExtraCalc() ", agreementDto.toString());

        validPremium(agreementDto.getPremium());
        validPremiumRate(agreementDto.getPenaltyRate());

        try {
            String jsonString = objectMapper.writeValueAsString(agreementDto);
            Agreement agreement = objectMapper.readValue(jsonString, Agreement.class);
            agreementRepository.save(agreement);
            log.info("Agreement successfully saved! ID : {}", agreement.getId());

            AgreementExtraCalc agreementExtraCalc = AgreementExtraCalc.builder()
                    .agreementId(agreement.getId()).build();
            addNewAgreementExtraCalc(agreementExtraCalc);

            log.info("Agreement and AgreementExtraCalc objects successfully saved! Agreement -> {}", agreement.toString());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
