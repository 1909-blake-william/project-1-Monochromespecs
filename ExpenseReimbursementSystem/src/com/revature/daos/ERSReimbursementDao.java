package com.revature.daos;

import java.util.List;

import com.revature.models.ERSReimbursement;

public interface ERSReimbursementDao {
	
	ERSReimbursementDao currentImplementation = new ERSReimbursementDaoSQL();
	
	int save(ERSReimbursement ersR);
	
	List<ERSReimbursement> findAll();

	//List<ERSReimbursement> findByERSUsername(String ersUserName);
	
	List<ERSReimbursement> findByERSReimbursementType(int ersType);
	
	List<ERSReimbursement> findByERSUserRole(int ersUserRole);
	
	ERSReimbursement findByERSId(int reimId);
	
	List<ERSReimbursement> findByERSUsername(String ersUsername);
	
	ERSReimbursement findByERSUserEmail(String ersUserEmail);
	
	ERSReimbursement deactivate(int reimbId);

}
