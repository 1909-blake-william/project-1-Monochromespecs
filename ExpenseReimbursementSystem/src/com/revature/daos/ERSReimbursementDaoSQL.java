package com.revature.daos;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.models.ERSReimbursement;
import com.revature.util.ConnectionUtil;

public class ERSReimbursementDaoSQL implements ERSReimbursementDao {

	private ERSReimbursementDao ersReimbursementDao = ERSReimbursementDaoSQL.currentImplementation;
	private Logger log = Logger.getRootLogger();

	ERSReimbursement extractERSReimbursement(ResultSet rs) throws SQLException {
		int reimbId = rs.getInt("reimb_Id");
		int reimbAmt = rs.getInt("reimb_amount");
		Timestamp reimbSmt = rs.getTimestamp("reimb_submitted");
		Timestamp reimRslvd = rs.getTimestamp("reimb_resolved");
		String reimbDesc = rs.getString("reimb_description");
		int reimbAuthor = rs.getInt("reimb_author");
		int reimbRslvr = rs.getInt("reimb_resolver");
		int reimbStatId = rs.getInt("reimb_status_id");
		int reimbTypeId = rs.getInt("reimb_type_id");
		return new ERSReimbursement(reimbId, reimbAmt, reimbSmt, reimRslvd, reimbDesc, reimbAuthor, reimbRslvr,
				reimbStatId, reimbTypeId);
	}

	@Override
	public int save(ERSReimbursement ersr) {
		log.debug("creating a new ERS reimbursement");
		try (Connection con = ConnectionUtil.getConnection()) {
			CallableStatement cs = con.prepareCall("Call create_ersreimbursement(?,?,?,?,?,?,?,?,?)");
			cs.setInt(1, ersr.getReimbId());
			cs.setInt(2, ersr.getReimbAmount());
			cs.setTimestamp(3, ersr.getReimbSubmitted());
			cs.setTimestamp(4, ersr.getReimbResolved());
			cs.setString(5, ersr.getReimbDescription());
			cs.setInt(6, ersr.getReimbAuthor());
			cs.setInt(7, ersr.getReimbResolver());
			cs.setInt(8, ersr.getReimbStatusId());
			cs.setInt(9, ersr.getReimbTypeId());

			cs.registerOutParameter(10, Types.INTEGER);
			cs.execute();

			int generatedId = cs.getInt(10);
			log.debug("Generated ers reimbursement id is: " + generatedId);
			return generatedId;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public List<ERSReimbursement> findAll() {
		log.debug("Attempting to find all ERS reimbursements from DB");
		try (Connection con = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM ERS_REIMBURSEMENT";
			PreparedStatement ps = con.prepareStatement(sql);

			ResultSet rs = ps.executeQuery(sql);

			List<ERSReimbursement> temp = new ArrayList<>();
			while (rs.next()) {
				temp.add(extractERSReimbursement(rs));
			}
			return temp;
		} catch (Exception e) {
			e.getStackTrace();
			return null;
		}

	}

	@Override
	public List<ERSReimbursement> findByERSUsername(String ersUsername) {
		try (Connection con = ConnectionUtil.getConnection()) {
			String sql = "Select * FROM ERS_REIMBURSEMENT WHERE ERS_USERNAME = ?";

			PreparedStatement ps = con.prepareStatement(sql);

			ps.setString(1, ersUsername);
			ResultSet rs = ps.executeQuery();
			List<ERSReimbursement> temp = new ArrayList<>();
			while (rs.next()) {
				temp.add(extractERSReimbursement(rs));
			}
			return temp;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public List<ERSReimbursement> findByERSReimbursementType(int ersType) {
		log.debug("Attempting to find ers reimbursements by type from DB");
		try (Connection con = ConnectionUtil.getConnection()) {

			String sql = "SELECT * FROM ERS_Reimbursement ersr "
					+ "LEFT JOIN ERS_Reimbursement_Type ersrt ON (ersr.reimb_type_id = ersrt.reimb_types_id) "
					+ "LEFT JOIN ers_users_id ersu ON (ersr.reimb_id = ersu.ers_users_id) " + "WHERE reimb_type_id = ?";

			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, ersType);

			ResultSet rs = ps.executeQuery();
			List<ERSReimbursement> ersReimbursements = new ArrayList<>();
			while (rs.next()) {
				ersReimbursements.add(extractERSReimbursement(rs));
			}

			return ersReimbursements;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public List<ERSReimbursement> findByERSUserRole(int ersUserRole) {
		log.debug("Attempting to find ERS reimbursements by role...");
		try (Connection con = ConnectionUtil.getConnection()) {

			String sql = "SELECT * FROM ERS_REIMBURSEMENT ersr "
					+ "LEFT JOIN ERS_USER_ROLES ersur ON (ersr.reimb_author = ersur.user_role_id) "
					+ "LEFT JOIN ERS_USERS_ID ersu ON (ersr.ers_reimb_id = ersu.ers_users_id) "
					+ "WHERE reimb_author = ?";

			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, ersUserRole);
			List<ERSReimbursement> temp = new ArrayList<>();
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				temp.add(extractERSReimbursement(rs));
			}
			return temp;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ERSReimbursement findByERSId(int reimId) {
		try (Connection con = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM ERS_REIMBURSEMENT ersr " + "LEFT JOIN ERS_USER_ID ersur ON (ersr) ";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}

	@Override
	public ERSReimbursement findByERSUserEmail(String ersUserEmail) {

		return null;
	}

	@Override
	public ERSReimbursement deactivate(int reimbId) {
		try (Connection con = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM ERS_REIMBURSEMENT ersr " + "LEFT JOIN ERS_USER_ID ersur ON (ersr) ";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, reimbId);

			ResultSet rs = ps.executeQuery();
			ERSReimbursement ersreim = extractERSReimbursement(rs);

//			while (rs.next()) {
//				temp.add(extractERSReimbursement(rs));				
//			}
			// return temp;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
}
