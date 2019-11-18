package com.revature.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.models.User;
import com.revature.util.ConnectionUtil;

public class UserDaoSQL implements UserDao {

	private Logger log = Logger.getRootLogger();

	User extractUser(ResultSet rs) throws SQLException {
		int id = rs.getInt("ers_user_id");
		String rsUsername = rs.getString("ers_username");
		String rsPassword = rs.getString("ers_password");
		String role = rs.getString("ers_user_role_id");
		return new User(id, rsUsername, rsPassword, role);
	}

	@Override
	public int save(User u) {
		log.debug("attempting to find user by credentials from DB");
		try (Connection c = ConnectionUtil.getConnection()) {

			String sql = "INSERT INTO ERS_USERS (ers_users_id, ers_username, ers_password) "
					+ " VALUES (ers_users_id_seq.nextval ,?,?)";

			PreparedStatement ps = c.prepareStatement(sql);
			ps.setString(1, u.getUsername());
			ps.setString(2, u.getPassword());

			return ps.executeUpdate();

		} catch (SQLException e) {

			// e.printStackTrace();
			return 0;
		}
	}

	@Override
	public List<User> findAll() {
		log.debug("attempting to find all users from DB");
		try (Connection c = ConnectionUtil.getConnection()) {

			String sql = "SELECT * FROM ERS_USERS";

			PreparedStatement ps = c.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();
			List<User> users = new ArrayList<User>();
			while (rs.next()) {
				users.add(extractUser(rs));
			}

			return users;

		} catch (SQLException e) {
//			e.printStackTrace();
			return null;
		}
	}

	@Override
	public User findById() {
		try (Connection c = ConnectionUtil.getConnection()) {

			String sql = "SELECT * FROM ERS_USERS " + "WHERE ers_users_id = ?";

			PreparedStatement ps = c.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();
			List<User> users = new ArrayList<User>();
			while (rs.next()) {
				users.add(extractUser(rs));
			}

			return extractUser(rs);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			return null;
		}
	}

	@Override
	public User findByUsernameAndPassword(String ers_username, String ers_password) {
		log.debug("attempting to find user by credentials from DB");
		try (Connection c = ConnectionUtil.getConnection()) {

			String sql = "SELECT * FROM ers_users " + "WHERE ers_username = ? AND ers_password = ?";

			PreparedStatement ps = c.prepareStatement(sql);
			ps.setString(1, ers_username);
			ps.setString(2, ers_password);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return extractUser(rs);
			} else {
				return null;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			return null;
		}
	}

	@Override
	public User findByUsername(String ers_username) {
		try (Connection c = ConnectionUtil.getConnection()) {

			String sql = "SELECT * FROM ers_users " + "WHERE ers_username = ?";

			PreparedStatement ps = c.prepareStatement(sql);
			ps.setString(1, ers_username);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return extractUser(rs);
			} else {
				return null;
			}

		} catch (SQLException e) {

//			e.printStackTrace();
			return null;
		}

	}
}
