package com.demo.springjwt.service;

import java.util.Optional;

import com.demo.springjwt.models.Beneficiary;

public interface BeneficiaryService {
	Optional<Beneficiary> findById(Long id);
	Beneficiary approveBeneficiary(Long id);
}
