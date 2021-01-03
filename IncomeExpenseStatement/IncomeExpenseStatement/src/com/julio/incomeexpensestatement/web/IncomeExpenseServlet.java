package com.julio.incomeexpensestatement.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.SortedMap;
import java.util.Map.Entry;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.julio.incomeexpensestatement.dao.IncomeStatementDAO;
import com.julio.incomeexpensestatement.model.InOut;
import com.julio.incomeexpensestatement.model.IncExpClassificacao;
import com.julio.incomeexpensestatement.model.IncomeStatement;

/**
 * ControllerServlet.java This servlet acts as a page controller for the
 * application, handling all requests from the contadorelect.
 * 
 * @email Ramesh Fadatare
 */

@WebServlet("/")
public class IncomeExpenseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String data1 = "1900-01-01";
	String data2 = "2900-01-01";
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	java.util.Date jan01 = null;
	java.util.Date today = null;
	private IncomeStatementDAO incomestatementDAO;

	public void init() {
		incomestatementDAO = new IncomeStatementDAO();
		System.out.println("====================> init()");
		Calendar aCalendar = Calendar.getInstance();
		today = aCalendar.getTime();
		aCalendar.set(Calendar.MONTH, 0);
		aCalendar.set(Calendar.DATE, 1);
		jan01 = aCalendar.getTime();
		data1 = dateFormat.format(jan01);
		data2 = dateFormat.format(today);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("====================> doPost  ");

		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String action = request.getServletPath();

		System.out.println("====================> doGet, action: " + action);

		String selType = request.getParameter("selectiontype");
		if (selType != null) {
			System.out.println("value of selected radio button: " + selType);
		} else {
			selType = "classificacao";
			System.out.println("no radio button was selected");
		}
		request.setAttribute("selectiontype", selType);
		//
		response.setContentType("text/html");
		PrintWriter pwriter = response.getWriter();
		String userName = "No User", role = "No Role";
		// Reading cookies
		Cookie cookie = null;
		Cookie[] cookies = null;
		cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				cookie = cookies[i];
				if (cookie.getName().equalsIgnoreCase("userName"))
					userName = cookie.getValue();
				if (cookie.getName().equalsIgnoreCase("role"))
					role = cookie.getValue();
			}
		} else {
			pwriter.println("<h2>No cookies founds</h2>");
		}
		if (!role.equals("Admin") && !role.equals("Editor")) {
			pwriter.println("<h2> Only Administrator and Editor allowed to <mark> view </mark> Entradas e Saidas</h2>"
					+ userName + "/" + role);
		} else {
			try {
				switch (action) {
				case "/list":
					listIncomeStatement(request, response);
					break;
				default:
					listIncomeStatement(request, response);
					break;
				}
			} catch (SQLException ex) {
				throw new ServletException(ex);
			}
		}
	}

	private void listIncomeStatement(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException,
			ServletException {
		String datainicial = data1, datafinal = data2;
		System.out.println("====================> listIncomeStatement: ");
		if (request.getParameter("datainicial") != null)
			datainicial = request.getParameter("datainicial");
		if (request.getParameter("datafinal") != null)
			datafinal = request.getParameter("datafinal");

		List<IncExpClassificacao> incomeExpenses = incomestatementDAO
				.selectIncome(datainicial, datafinal);

		request.setAttribute("incomeExpenses", incomeExpenses);

		double totIncome = 0, totExpenses = 0;
		for (int i = 0; i < incomeExpenses.size(); i++) {
			IncExpClassificacao incexp = incomeExpenses.get(i);
			totIncome = totIncome + incexp.getIncMontante();
			totExpenses = totExpenses + incexp.getExpMontante();
		}

		request.setAttribute("inc", totIncome);
		request.setAttribute("exp", totExpenses);
		request.setAttribute("incexp", totIncome + totExpenses);

		List<InOut> inOut = incomestatementDAO.selectInOut(datainicial,
				datafinal);

		request.setAttribute("inOut", inOut);

		RequestDispatcher dispatcher = request
				.getRequestDispatcher("IncomeExpenseStatement.jsp");
		dispatcher.forward(request, response);
	}

}