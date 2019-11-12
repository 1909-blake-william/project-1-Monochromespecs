package com.revature.daos;

import java.util.List;

import com.revature.models.ERSReimbursement;

public interface ERSReimbursementDao {
	
	ERSReimbursementDao currentImplementation = new ERSReimbursementDaoSQL();
	
	int save(ERSReimbursement ersR);
	
	List<ERSReimbursement> findAll();

	List<ERSReimbursement> findByERSName(String ersName);
	
	List<ERSReimbursement> findByERSReimbursementType(String ersType);
	
	List<ERSReimbursement> findByERSRole(String ersRole);
	
	ERSReimbursement findByERSId(int reimId);
	
	ERSReimbursement findByERSUsername(String ersUsername);
	
	ERSReimbursement findByERSUserEmail(String ersUserEmail);
	
	ERSReimbursement deactivate(int reimbId);

}
