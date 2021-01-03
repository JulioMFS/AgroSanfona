package com.julio.guia.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.julio.guia.dao.GuiaEntradaDAO;
import com.julio.guia.model.GuiaEntrada;

/**
 * ControllerServlet.java This servlet acts as a page controller for the
 * application, handling all requests from the user.
 * 
 * @email Ramesh Fadatare
 */

@WebServlet("/")
public class GuiaEntradaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private GuiaEntradaDAO guiaentradaDAO;
	String matricula = "";
	String parcela = "";
	String data1 = null;
	String data2 = null;

	public void init() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date threeMonthsAgo = null;
		java.util.Date today = null;

		Calendar aCalendar = Calendar.getInstance();
		today = aCalendar.getTime();
		aCalendar.add(Calendar.YEAR, -1);
		aCalendar.set(Calendar.DATE, 1);
		threeMonthsAgo = aCalendar.getTime();
		data1 = dateFormat.format(threeMonthsAgo);
		data2 = dateFormat.format(today);

		guiaentradaDAO = new GuiaEntradaDAO();
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("---> doPost crud, ");
		doGet(request, response);

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath();
		System.out.println("---> doGet GuiaEntrada, action: " + action);
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
		if (!role.equals("Admin") && (!action.equalsIgnoreCase("/list"))
				&& (!action.equalsIgnoreCase("/"))) {
			pwriter.println("<h2> " + role + " not allowed to <mark>" + action
					+ "</mark> Factura</h2>" + userName + "/" + role);
		} else {
			try {
				switch (action) {
				case "/new":
					showNewForm(request, response);
					break;
				case "/insert":
					insertGuiaEntrada(request, response);
					break;
				case "/delete":
					deleteGuiaEntrada(request, response);
					break;
				case "/edit":
					showEditForm(request, response);
					break;
				case "/update":
					updateGuiaEntrada(request, response);
					break;
				default:
					listGuiaEntrada(request, response);
					break;
				}
			} catch (SQLException ex) {
				throw new ServletException(ex);
			}
		}
	}

	private void listGuiaEntrada(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException,
			ServletException {

		if (request.getParameter("data1") != null)
			data1 = request.getParameter("data1");
		if (request.getParameter("data2") != null)
			data2 = request.getParameter("data2");

		if (request.getParameter("matricula") != null)
			matricula = request.getParameter("matricula");
		if (request.getParameter("parcela") != null)
			parcela = request.getParameter("parcela");

		request.getSession().setAttribute("data1", data1);
		request.getSession().setAttribute("data2", data2);
		request.getSession().setAttribute("matricula", matricula);
		request.getSession().setAttribute("parcela", parcela);

		List<GuiaEntrada> listGuiaEntrada = guiaentradaDAO
				.selectAllGuiaEntradas(data1, data2, matricula, parcela);
		request.setAttribute("guiaentrada", listGuiaEntrada);
		RequestDispatcher dispatcher = request
				.getRequestDispatcher("guiaentrada-list.jsp");
		dispatcher.forward(request, response);
	}

	private void showNewForm(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request
				.getRequestDispatcher("guiaentrada-form.jsp");
		dispatcher.forward(request, response);
	}

	private void showEditForm(HttpServletRequest request,
			HttpServletResponse response) throws SQLException,
			ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		String data1 = request.getParameter("data1");
		request.getSession().setAttribute("data1", data1);
		String data2 = request.getParameter("data2");
		request.getSession().setAttribute("data2", data2);
		GuiaEntrada existingGuiaEntrada = guiaentradaDAO.selectGuiaEntrada(id);
		RequestDispatcher dispatcher = request
				.getRequestDispatcher("guiaentrada-form.jsp");
		request.setAttribute("guiaentrada", existingGuiaEntrada);
		dispatcher.forward(request, response);

	}

	private void insertGuiaEntrada(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException {
		String guiaNo = request.getParameter("guiaNo");
		String data = request.getParameter("data");
		String hora = request.getParameter("hora");
		String artigo = request.getParameter("artigo");
		String descricao = request.getParameter("descricao");
		String matricula = request.getParameter("matricula");

		String text = request.getParameter("pesoEsp");
		String s = text.replaceAll("[^0-9.]", "");
		double pesoEsp = Double.parseDouble(s);

		text = request.getParameter("pesoVerde");
		s = text.replaceAll("[^0-9.]", "");
		double pesoVerde = Double.parseDouble(s);

		text = request.getParameter("humidade");
		s = text.replaceAll("[^0-9.]", "");
		double humidade = Double.parseDouble(s);

		text = request.getParameter("convTN");
		s = text.replaceAll("[^0-9.]", "");
		double convTN = Double.parseDouble(s);

		text = request.getParameter("peso");
		s = text.replaceAll("[^0-9.]", "");
		double peso = Double.parseDouble(s);

		text = request.getParameter("notaPagamento");
		if (text == null || text.equalsIgnoreCase(""))
			text = "0";
		s = text.replaceAll("[^0-9.]", "");
		int notaPagamento = Integer.parseInt(s);

		String parcela = request.getParameter("parcela");

		GuiaEntrada newGuiaEntrada = new GuiaEntrada(guiaNo, data, hora,
				artigo, descricao, matricula, pesoEsp, pesoVerde, humidade,
				convTN, peso, notaPagamento, parcela);
		guiaentradaDAO.insertGuiaEntrada(newGuiaEntrada);
		response.sendRedirect("list");
	}

	private void updateGuiaEntrada(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		String guiaNo = request.getParameter("guiaNo");
		String data = request.getParameter("data");
		String hora = request.getParameter("hora");
		String artigo = request.getParameter("artigo");
		String descricao = request.getParameter("descricao");
		String matricula = request.getParameter("matricula");

		String text = request.getParameter("pesoEsp");
		String s = text.replaceAll("[^0-9.]", "");
		double pesoEsp = Double.parseDouble(s);

		text = request.getParameter("pesoVerde");
		s = text.replaceAll("[^0-9.]", "");
		double pesoVerde = Double.parseDouble(s);

		text = request.getParameter("humidade");
		s = text.replaceAll("[^0-9.]", "");
		double humidade = Double.parseDouble(s);

		text = request.getParameter("convTN");
		s = text.replaceAll("[^0-9.]", "");
		double convTN = Double.parseDouble(s);

		text = request.getParameter("peso");
		s = text.replaceAll("[^0-9.]", "");
		double peso = Double.parseDouble(s);

		text = request.getParameter("notaPagamento");
		s = text.replaceAll("[^0-9.]", "");
		int notaPagamento = Integer.parseInt(s);

		String parcela = request.getParameter("parcela");

		GuiaEntrada book = new GuiaEntrada(id, guiaNo, data, hora, artigo,
				descricao, matricula, pesoEsp, pesoVerde, humidade, convTN,
				peso, notaPagamento, parcela);
		guiaentradaDAO.updateGuiaEntrada(book);
		response.sendRedirect("list");
	}

	private void deleteGuiaEntrada(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		guiaentradaDAO.deleteGuiaEntrada(id);
		response.sendRedirect("list");

	}
}