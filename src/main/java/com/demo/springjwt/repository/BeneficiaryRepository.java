package com.demo.springjwt.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.demo.springjwt.models.Beneficiary;
import com.demo.springjwt.payload.response.BeneficiaryResponse;

public interface BeneficiaryRepository extends JpaRepository<Beneficiary, Long>{
	Optional<Beneficiary> findByAccountNumber(Long accountNumber);
	
	@Query(value = "SELECT * FROM beneficiary WHERE beneficiary.approved = 'no'",
			nativeQuery = true)
	List<Beneficiary> findUnapprovedBeneficiaries();
	
//	@Query(value = "SELECT users.id, account_number, date_added, approved, firstname, lastname "
//			+ "FROM beneficiary, users, user_beneficiary "
//			+ "WHERE beneficiary.id = user_beneficiary.beneficiary_id "
//			+ "AND users.id = user_beneficiary.user_id", nativeQuery = true)
//	List<BeneficiaryResponse> findAllBeneficiaryWithCustomer();
}
 