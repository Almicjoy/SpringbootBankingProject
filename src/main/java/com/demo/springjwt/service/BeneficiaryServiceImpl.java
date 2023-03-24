package com.demo.springjwt.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.springjwt.Exceptions.BadRequestException;
import com.demo.springjwt.models.Beneficiary;
import com.demo.springjwt.repository.BeneficiaryRepository;

@Service
public class BeneficiaryServiceImpl implements BeneficiaryService{

	@Autowired
	BeneficiaryRepository beneficiaryRepository;
	@Override
	public Optional<Beneficiary> findById(Long id) {
		return beneficiaryRepository.findById(id);
	}

	@Override
	public Beneficiary approveBeneficiary(Long id) {
		Optional<Beneficiary> optBeneficiary = beneficiaryRepository.findById(id);
		if(optBeneficiary.isEmpty()) {
			throw new BadRequestException("Beneficiary not Found");
		}
		Beneficiary beneficiary = optBeneficiary.get();
		beneficiary.setApproved("yes");
		return beneficiaryRepository.save(beneficiary);
	}

}
