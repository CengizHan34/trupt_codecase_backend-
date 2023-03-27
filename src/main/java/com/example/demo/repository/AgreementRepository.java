package com.example.demo.repository;

import com.example.demo.agreement.dao.Agreement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface AgreementRepository extends JpaRepository<Agreement, Long> {

    List<Agreement> findByType(Integer type);

}
