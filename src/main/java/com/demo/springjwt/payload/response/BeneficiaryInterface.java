package com.demo.springjwt.payload.response;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "beneficiary_user", types = {BeneficiaryResponse.class})
public interface BeneficiaryInterface {
	Long getId();
	Long getAccountNumber();
	String getApproved();
	String getFirstname();
	String getLastname();
}
