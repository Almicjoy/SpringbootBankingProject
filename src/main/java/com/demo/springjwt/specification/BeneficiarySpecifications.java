package com.demo.springjwt.specification;

import javax.persistence.criteria.Join;

import org.springframework.data.jpa.domain.Specification;

import com.demo.springjwt.models.Beneficiary;
import com.demo.springjwt.models.User;

public class BeneficiarySpecifications {
	
	public static Specification<Beneficiary> getBeneficiariesWithCustomerInfo() {
		return (root, query, criteriaBuilder) -> {
			Join<User, Beneficiary> beneficiaryUser = root.join("username");
			return criteriaBuilder.equal(beneficiaryUser, root);
		};
	}

}
