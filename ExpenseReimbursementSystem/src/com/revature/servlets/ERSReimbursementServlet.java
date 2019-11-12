package com.revature.servlets;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.daos.ERSReimbursementDao;
import com.revature.daos.ERSReimbursementDaoSQL;
import com.revature.models.ERSReimbursement;


	public class ERSReimbursementServlet extends HttpServlet {

		private ERSReimbursementDao ersReimbursementDao = ERSReimbursementDaoSQL.currentImplementation;

		@Override
		protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			// TODO Auto-generated method stub
			super.service(req, resp);
			System.out.println("To context param: " + req.getServletContext().getInitParameter("To"));

			resp.addHeader("Access-Control-Allow-Origin", "http://localhost:4200");
			resp.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
			resp.addHeader("Access-Control-Allow-Headers",
					"Origin, Methods, Credentials, X-Requested-With, Content-Type, Accept");
			resp.addHeader("Access-Control-Allow-Credentials", "true");
			resp.setContentType("application/json");
		}

		@Override
		protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

			List<ERSReimbursement> ersReimbursements = null;

			String ersName = req.getParameter("ersName");

			if (ersName != null) { // find by ERSUsername
				ersReimbursements = ersReimbursementDao.findByERSName(ersName);
			} else { // find all
				ersReimbursementDao.findAll();
			}

			ObjectMapper om = new ObjectMapper();
			String json = om.writeValueAsString(ersReimbursements);

			resp.addHeader("content-type", "application/json");
			resp.getWriter().write(json);
		}

		@Override
		protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			// read the ERSReimbursement from the request body
			ObjectMapper om = new ObjectMapper();
			ERSReimbursement ersR = (ERSReimbursement) om.readValue(req.getReader(), ERSReimbursement.class);

			System.out.println(ersR);

			int reimbId = ersReimbursementDao.save(ersR);
			ersR.setReimbId(reimbId);

			String json = om.writeValueAsString(ersR);

			resp.getWriter().write(json);
			resp.setStatus(201); // created status code

		}
		
		@Override
		protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			String reimbIdString = req.getParameter("reimbId");
			int ersR = Integer.parseInt(reimbIdString);
			ersReimbursementDao.deactivate(ersR);
		}
	}
	