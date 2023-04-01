package com.example.demo.api;

import com.example.demo.payload.request.AgreementDto;
import com.example.demo.payload.response.AgreementTaxDto;
import com.example.demo.agreement.dao.Agreement;
import com.example.demo.agreement.dao.AgreementExtraCalc;
import com.example.demo.service.AgreementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class AgreementRestController {

    @Autowired
    AgreementService agreementService;

    @GetMapping("/agreementAllList")
    public List<Agreement> getAllAgreements() {
        return agreementService.getAllAgreements();
    }

    @GetMapping("/agreement/{id}")
    public Agreement getAgreement(@PathVariable Long id) {
        return agreementService.getAgreementById(id);
    }

    @RequestMapping(path = "/newAgreement", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Long addNewAgreement(@RequestBody Agreement agreement) {
        return agreementService.addNewAgreement(agreement);
    }

    @GetMapping("/agreementPenalty")
    public BigDecimal getAgreementPenaltyOfAgreement(@RequestParam("id") long id) {
        return agreementService.getAgreementPenaltyOfAgreement(id);
    }

    @RequestMapping(path = "/newAgreementExtraCalc", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public long addNewAgreementExtraCalc(@RequestBody AgreementExtraCalc agreementExtraCalc) {
        return agreementService.addNewAgreementExtraCalc(agreementExtraCalc);
    }

    @GetMapping("/agreementTax")
    public AgreementTaxDto getAgreementTax(@RequestParam("id") Long id) {
        return agreementService.getAgreementExtraCalc(id);
    }
    @PostMapping("/agreementAndExtra")
    public ResponseEntity saveAgreementAndExtra(@RequestBody AgreementDto agreementDto){
        agreementService.saveAgreementAndAgreementExtraCalc(agreementDto);
        return ResponseEntity.ok().build();
    }
}
