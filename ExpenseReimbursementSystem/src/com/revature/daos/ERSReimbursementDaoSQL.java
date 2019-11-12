package com.revature.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import com.revature.models.ERSReimbursement;
import com.revature.util.ConnectionUtil;

public class ERSReimbursementDaoSQL implements ERSReimbursementDao {
	
	private ERSReimbursementDao ersReimbursementDao = ERSReimbursementDaoSQL.currentImplementation;
	
	ERSReimbursement extractERSReimbursement(ResultSet rs) throws SQLException {
		int reimbId = rs.getReimbId("reimb_id");
		int reimbAmt = rs.getReimbAmount("reimb_amount");
		Timestamp reimbSmt = rs.getReimbSubmitted("reimb_submitted");
		Timestamp reimRslvd = rs.getReimbResolved("reimb_resolved");
		String reimbDesc = rs.getReimbDescription("reimb_description");
		int reimbAuthor = rs.getReimbAuthor("reimb_author");
		int reimbRslvr = rs.getReimbResolver("reimb_resolver");
		int reimbStatId = rs.getReimbStatusId("reimb_status_id");
		int reimbTypeId = rs.getReimbTypeId("reimb_type_id");
		return new ERSReimbursement(reimbId, reimbAmt, reimbSmt, reimRslvd, reimbDesc, 
				reimbAuthor, reimbRslvr, reimbStatId, reimbTypeId);
	}
	
	@Override
	public int save(ERSReimbursement ersR) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<ERSReimbursement> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ERSReimbursement> findByERSName(String ersName) {
		try (Connection con = new ConnectionUtil.getConnection()){
			String sql = "Select * FROM ERS_REIMBURSEMENT WHERE REIMB_ID = ? AND ERS_USERNAME = ?";
			
			PreparedStatement ps = con.prepareStatement(sql);
						
			int ersId = ps.setInt(1, reimbId);
			String ersUsr = ps.setString(2, );
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<ERSReimbursement> findByERSReimbursementType(String ersType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ERSReimbursement> findByERSRole(String ersRole) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ERSReimbursement findByERSId(int reimId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ERSReimbursement findByERSUsername(String ersUsername) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ERSReimbursement findByERSUserEmail(String ersUserEmail) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ERSReimbursement deactivate(int reimbId) {
		// TODO Auto-generated method stub
		return null;
	}}
