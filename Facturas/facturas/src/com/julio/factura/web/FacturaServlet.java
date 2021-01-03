package com.julio.factura.web;

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

import com.julio.factura.dao.FacturaDAO;
import com.julio.factura.model.Factura;
import com.mysql.jdbc.Connection;

/**
 * ControllerServlet.java This servlet acts as a page controller for the
 * application, handling all requests from the factura.
 * 
 * @email Ramesh Fadatare
 */

@WebServlet("/")
public class FacturaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private FacturaDAO facturaDAO;
	String action, data1 = "", data2 = "", designacao = "", fornecedor = "";

	public void init() {
		facturaDAO = new FacturaDAO();
		System.out.println("## init() ##");

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("## doPost(....) ##");

		System.out.println("--------> doPost, " + data1 + " " + data2
				+ " Designacao: " + designacao + ", Fornecedor: " + fornecedor);
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath();
		System.out.println("## doGet() ##");

		System.out.println("--------> doGet, action: " + action + ", data1: "
				+ data1 + ", data2: " + data2 + "designacao: " + designacao);
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
		System.out.println("--> User: " + userName + ", role: " + role
				+ ", action: ");
		if (!role.equalsIgnoreCase("Admin")
				&& (!action.equalsIgnoreCase("/list"))
				&& (!action.equalsIgnoreCase("/"))
				&& (!action.equalsIgnoreCase("/FacturaServlet"))) {
			pwriter.println("<h2>" + role + " not allowed to <mark>" + action
					+ "</mark> Factura</h2>" + userName + "/" + role);
			pwriter.close();
		} else {
			try {
				switch (action) {
				case "/new":
					showNewForm(request, response);
					break;
				case "/insert":
					insertFactura(request, response);
					break;
				case "/delete":
					deleteFactura(request, response);
					break;
				case "/edit":
					showEditForm(request, response);
					break;
				case "/update":
					updateFactura(request, response);
					break;
				case "/list":
					listFactura(request, response);
					break;
				default:
					listFactura(request, response);
					break;
				}
			} catch (SQLException ex) {
				throw new ServletException(ex);
			}
		}
	}

	private void listFactura(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException,
			ServletException {
		System.out.println("## listFactura() ##");

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date threeMonthsAgo = null;
		java.util.Date today = null;

		Calendar aCalendar = Calendar.getInstance();
		today = aCalendar.getTime();
		aCalendar.add(Calendar.MONTH, -3);
		aCalendar.set(Calendar.DATE, 1);
		threeMonthsAgo = aCalendar.getTime();
		if (request.getSession().getAttribute("data1") == null) {
			data1 = dateFormat.format(threeMonthsAgo);
			data2 = dateFormat.format(today);
		} else {
			data1 = request.getSession().getAttribute("data1").toString();
			data2 = request.getSession().getAttribute("data2").toString();
		}
		if (request.getParameter("data1") != null) {
			data1 = request.getParameter("data1").toString();
			System.out
					.println(" in lisFactura, request.getParameter('data1').toString(): "
							+ data1);
		}
		if (request.getParameter("data2") != null) {
			data2 = request.getParameter("data2").toString();
		}
		if (request.getSession().getAttribute("designacao") != null)
			designacao = request.getSession().getAttribute("designacao")
					.toString();
		if (request.getParameter("designacao") != null) {
			designacao = request.getParameter("designacao").toString();
		}

		if (request.getSession().getAttribute("fornecedor") != null)
			fornecedor = request.getSession().getAttribute("fornecedor")
					.toString();
		if (request.getParameter("fornecedor") != null) {
			fornecedor = request.getParameter("fornecedor").toString();
		}

		request.getSession().setAttribute(("data1"), data1);
		request.getSession().setAttribute(("data2"), data2);
		request.getSession().setAttribute(("designacao"), designacao);
		request.getSession().setAttribute(("fornecedor"), fornecedor);

		System.out.println("--------> listFactura data1: " + data1
				+ ", data2; " + data2);

		List<Factura> listFactura = facturaDAO.selectAllFacturas(data1, data2,
				designacao, fornecedor);

		request.setAttribute("listFactura", listFactura);
		RequestDispatcher dispatcher = request
				.getRequestDispatcher("factura-list.jsp");
		dispatcher.forward(request, response);
	}

	private void showNewForm(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("--------> showNewForm");
		RequestDispatcher dispatcher = request
				.getRequestDispatcher("factura-form.jsp");

		dispatcher.forward(request, response);
	}

	private void showEditForm(HttpServletRequest request,
			HttpServletResponse response) throws SQLException,
			ServletException, IOException {
		String message = null;

		int no = Integer.parseInt(request.getParameter("no"));
		System.out.println("--------> showEditForm, no: " + no);

		List<Factura> existingFactura = facturaDAO.selectFactura(no);

		request.setAttribute("existingFactura", existingFactura);

		request.setAttribute("exists", "update");

		RequestDispatcher dispatcher = request
				.getRequestDispatcher("factura-form.jsp");
		dispatcher.forward(request, response);

	}

	private void insertFactura(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException {
		String page = "list";
		String message = "";
		int no = 0;
		int[] storeNo = new int[50];
		boolean recsInserted = false;
		try {
			int x;
			String[] values = request.getParameterValues("designacao");
			int size = values.length;
			String[] designacao = new String[size];
			for (x = 0; x < size && values[x] != null; x++) {
				designacao[x] = values[x];
			}
			size = x;

			values = request.getParameterValues("date");
			String[] date = new String[size];
			for (x = 0; x < size && values[x] != null; x++) {
				date[x] = values[x];
			}

			values = request.getParameterValues("invNo");
			String[] invno = new String[size];
			for (x = 0; x < size && values[x] != null; x++) {
				invno[x] = values[x];
			}
			values = request.getParameterValues("fornecedor");
			String[] fornecedor = new String[size];
			for (x = 0; x < size && values[x] != null; x++) {
				fornecedor[x] = values[x];
			}

			values = request.getParameterValues("quantidade");
			double[] quantidade = new double[size];
			for (int i = 0; i < size; i++) {
				if (values[i] == null || values[i].isEmpty())
					values[i] = "0";
				String s = values[i].replaceAll("[^0-9.-]", "");
				quantidade[i] = Double.parseDouble(s);
			}

			values = request.getParameterValues("un");
			String[] un = new String[size];
			for (int i = 0; i < size; i++) {
				un[i] = values[i];
			}

			values = request.getParameterValues("preco");
			double[] preco = new double[size];
			for (int i = 0; i < size; i++) {
				if (values[i] == null || values[i].isEmpty())
					values[i] = "0";
				String s = values[i].replaceAll("[^0-9.-]", "");
				preco[i] = Double.parseDouble(s);
			}

			values = request.getParameterValues("desconto");
			double[] desc = new double[size];
			for (int i = 0; i < size; i++) {
				String s = "";
				if (values[i] == null || values[i] == "")
					values[i] = "0";
				s = values[i].replaceAll("[^0-9.-]", "");
				desc[i] = Double.parseDouble(s);
			}

			values = request.getParameterValues("descontoV");
			double[] descV = new double[size];
			for (int i = 0; i < size; i++) {
				if (values[i] == null || values[i].isEmpty())
					values[i] = "0";
				String s = values[i].replaceAll("[^0-9.]", "");
				descV[i] = Double.parseDouble(s);
			}

			values = request.getParameterValues("valorLiq");
			double[] valorLiq = new double[size];
			for (int i = 0; i < size; i++) {
				if (values[i] == null || values[i].isEmpty())
					values[i] = "0";
				String s = values[i].replaceAll("[^0-9.-]", "");
				valorLiq[i] = Double.parseDouble(s);
			}

			values = request.getParameterValues("iva");
			double[] iva = new double[size];
			for (int i = 0; i < size; i++) {
				if (values[i] == null || values[i].isEmpty())
					values[i] = "0";
				String s = values[i].replaceAll("[^0-9.]", "");
				iva[i] = Double.parseDouble(s);
			}

			values = request.getParameterValues("ivaV");
			double[] ivaV = new double[size];
			for (int i = 0; i < size; i++) {
				if (values[i] == null || values[i].isEmpty())
					values[i] = "0";
				String s = values[i].replaceAll("[^0-9.]", "");
				ivaV[i] = Double.parseDouble(s);
			}

			values = request.getParameterValues("parcela");
			String[] parcela = new String[size];
			for (x = 0; x < size && values[x] != null; x++) {
				parcela[x] = values[x];
			}

			String design = "Factura - " + fornecedor[0];
			Factura newFactura = null;
			int ii = 0;
			int num = 0;

			for (int i = 0; i < size; i++) {
				if (i == 0)
					design = "Factura - " + fornecedor[0];
				else
					design = designacao[i];
				if (valorLiq[i] != 0.0) {
					newFactura = new Factura(date[0], invno[0], i, design,
							quantidade[i], un[i], preco[i], desc[i], descV[i],
							valorLiq[i], iva[i], ivaV[i], fornecedor[0],
							parcela[i]);
					recsInserted = true;
					storeNo[ii] = facturaDAO.insertFactura(newFactura);
					if (i == 0)
						num = storeNo[ii];
					ii++;

				}
			}
			List<Factura> existingFactura;
			int count = FacturaDAO.countFacturas(invno[0], num);
			if (count > 0) {
				message = "Factura: " + invno[0] + " já existe";
				request.setAttribute("message", message);
				request.setAttribute("color", "gtextred");
				existingFactura = facturaDAO.selectThisFactura(num);
				int rowsDeleted = facturaDAO.deleteFactura(no, date[0],
						invno[0], "00");

			} else {
				message = "Factura: " + invno[0] + " Adicionada";
				request.setAttribute("message", message);
				request.setAttribute("color", "gtextgreen");
				existingFactura = facturaDAO.selectFactura(num);

				response.sendRedirect("list");
				return;
			}
			request.setAttribute("existingFactura", existingFactura);
		} catch (Exception ex) {
			System.out.println("Error occured when parsing data.");
		}
		RequestDispatcher dispatcher = request
				.getRequestDispatcher("factura-form.jsp");
		try {
			dispatcher.forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void updateFactura(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException {
		System.out.println(" in updateFactura, ");
		String page = "list";
		String message = "";
		int number = 0;
		try {
			String[] values = request.getParameterValues("no");
			int size = values.length;
			int[] no = new int[size];
			for (int i = 0; i < size; i++) {
				no[i] = Integer.parseInt(values[i]);
			}
			values = request.getParameterValues("date");
			String[] date = new String[size];
			for (int i = 0; i < size; i++) {
				date[i] = values[i];
			}
			values = request.getParameterValues("invNo");
			String[] invNo = new String[size];
			for (int i = 0; i < size; i++) {
				invNo[i] = values[i];
			}
			values = request.getParameterValues("item");
			int[] item = new int[size];
			for (int i = 0; i < size; i++) {
				if (values[i] != null && !values[i].isEmpty()) {
					String ss = values[i].replaceAll("[^0-9.-]", "");
					item[i] = Integer.parseInt(ss);
				}
			}

			values = request.getParameterValues("designacao");
			String[] designacao = new String[size];
			for (int i = 0; i < size; i++) {
				designacao[i] = values[i];
			}

			values = request.getParameterValues("quantidade");
			size = values.length;
			double[] quantidade = new double[size];
			for (int i = 0; i < size; i++) {
				if (values[i] != null && !values[i].isEmpty())
					quantidade[i] = Double.parseDouble(values[i]);
			}

			values = request.getParameterValues("un");
			size = values.length;
			String[] un = new String[size];
			for (int i = 0; i < size; i++) {
				un[i] = values[i];
			}

			values = request.getParameterValues("preco");
			size = values.length;
			double[] preco = new double[size];
			for (int i = 0; i < size; i++) {
				if (values[i] != null && !values[i].isEmpty()) {
					String ss = values[i].replaceAll("[^0-9.-]", "");
					preco[i] = Double.parseDouble(ss);
				}
			}

			values = request.getParameterValues("desconto");
			size = values.length;
			double[] desconto = new double[size];
			for (int i = 0; i < size; i++) {
				if (values[i] != null && !values[i].isEmpty()) {
					String ss = values[i].replaceAll("[^0-9.]", "");
					desconto[i] = Double.parseDouble(ss);
				}
			}

			values = request.getParameterValues("descontoV");
			size = values.length;
			double[] descontoV = new double[size];
			for (int i = 0; i < size; i++) {
				if (values[i] != null && !values[i].isEmpty()) {
					String ss = values[i].replaceAll("[^0-9.]", "");
					descontoV[i] = Double.parseDouble(ss);
				}
			}

			values = request.getParameterValues("valorLiq");
			size = values.length;
			double[] valorLiq = new double[size];
			for (int i = 0; i < size; i++) {
				if (values[i] != null && !values[i].isEmpty()) {

					String ss = values[i].replaceAll("[^0-9.]", "");
					valorLiq[i] = Double.parseDouble(ss);
				}
			}

			values = request.getParameterValues("iva");
			size = values.length;
			double[] iva = new double[size];
			for (int i = 0; i < size; i++) {
				if (values[i] != null && !values[i].isEmpty()) {

					String ss = values[i].replaceAll("[^0-9.]", "");
					iva[i] = Double.parseDouble(ss);
				}
			}

			values = request.getParameterValues("ivaV");
			size = values.length;
			double[] ivaV = new double[size];
			for (int i = 0; i < size; i++) {
				if (values[i] != null && !values[i].isEmpty()) {

					String ss = values[i].replaceAll("[^0-9.]", "");
					ivaV[i] = Double.parseDouble(ss);
				}
			}

			values = request.getParameterValues("fornecedor");
			size = values.length;
			String[] fornecedor = new String[size];
			for (int i = 0; i < size; i++) {
				fornecedor[i] = values[i];
			}

			values = request.getParameterValues("parcela");
			size = values.length;
			String[] parcela = new String[size];
			for (int i = 0; i < size; i++) {
				parcela[i] = values[i];
			}

			String design;

			for (int i = 0; i < no.length; i++) {
				if (i == 0)
					design = "Factura - " + fornecedor[0];
				else
					design = designacao[i];
				if (i == 0 || (i > 0 && valorLiq[i] != 0.0)) {
					if (no[i] > 0) {
						Factura book = new Factura(no[i], date[0], invNo[0], i,
								design, quantidade[i], un[i], preco[i],
								desconto[i], descontoV[i], valorLiq[i], iva[i],
								ivaV[i], fornecedor[i], parcela[i]);
						facturaDAO.updateFactura(book);
					} else {
						Factura book = new Factura(date[0], invNo[0], i,
								design, quantidade[i], un[i], preco[i],
								desconto[i], descontoV[i], valorLiq[i], iva[i],
								ivaV[i], fornecedor[0], parcela[i]);
						number = facturaDAO.insertFactura(book);
					}
				}
			}
		} catch (Exception ex) {
			System.out.println("Error occured when parsing data.");
		}
		response.sendRedirect("list");
	}

	private void deleteFactura(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException {
		int no = Integer.parseInt(request.getParameter("no"));
		String data = request.getParameter("data");
		String invno = request.getParameter("invno");
		String item = request.getParameter("item");

		int rowsDeleted = facturaDAO.deleteFactura(no, data, invno, item);
		System.out.println("-----> Deleted no: " + no + ", item: " + item
				+ ", data: " + data + ", invno: " + invno
				+ "\n        rowsDeleted: " + rowsDeleted);
		response.sendRedirect("");

	}

	String removeLeadingZeroes(String s) {
		int index;
		for (index = 0; index < s.length(); index++) {
			if (s.charAt(index) >= '0' && s.charAt(index) <= '9') {
				break;
			}
		}
		return s.substring(index);
	}

	String removeTrailingZeroes(String s) {
		int index;
		for (index = s.length() - 1; index >= 0; index--) {
			if (s.charAt(index) >= '0' && s.charAt(index) <= '9') {
				break;
			}
		}
		return s.substring(index);
	}
}