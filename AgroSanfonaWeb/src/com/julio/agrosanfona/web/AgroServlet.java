package com.julio.agrosanfona.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import com.julio.evento.dao.EventDAO;

/**
 * Servlet implementation class AgroServlet
 */
@WebServlet("/AgroServlet")
public class AgroServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String action;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AgroServlet() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		System.out.println("init getenv('env'): " + System.getProperty("env")
				+ "\ngetenv('envirnmt'): " + System.getProperty("envirnmt"));

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String env = "test";
		action = request.getServletPath();

		String currentDirectory = System.getProperty("user.dir");
		if (currentDirectory.indexOf("Apache") > 0) {
			env = "agro";
		}
		System.out.println("user.dir: " + currentDirectory + "\nenvironment: "
				+ env);

		try {
			switch (action) {
			case "/AgroServlet":
				setEnvironment(request, response);
				break;
			default:
				break;
			}
		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("====================> doPost  ");

		doGet(request, response);
	}

	private void setEnvironment(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException,
			ServletException {
		// int no = Integer.parseInt(request.getParameter("no"));
		String env = request.getParameter("ans");

		System.out.println("====================> setEnvironment to: " + env);

		// response.sendRedirect("Search");
		RequestDispatcher dispatcher = request
				.getRequestDispatcher("index.jsp");
		dispatcher.forward(request, response);

	}

}
