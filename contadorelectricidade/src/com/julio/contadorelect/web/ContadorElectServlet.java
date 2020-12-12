package com.julio.contadorelect.web;

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
import java.util.Map.Entry;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.julio.contadorelect.dao.ContadorElectDAO;
import com.julio.contadorelect.model.IncomeStatement;
import com.julio.contadorelect.model.ElectPrecos;
import com.julio.contadorelect.model.TableRows;

/**
 * ControllerServlet.java This servlet acts as a page controller for the
 * application, handling all requests from the contadorelect.
 * 
 * @email Ramesh Fadatare
 */

@WebServlet("/")
public class ContadorElectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ContadorElectDAO contadorelectDAO;
	String data1 = "1900-01-01";
	String data2 = "2900-01-01";
	String leituradata = "";
	String parcela = "";
	String companhia = "";
	String fatura = "FT 0001/110";
	String est = "Phy";
	String valor = "0.00";
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	java.util.Date threeMonthsAgo = null;
	java.util.Date today = null;

	public void init() {
		contadorelectDAO = new ContadorElectDAO();
		System.out.println("====================> init()");
		Calendar aCalendar = Calendar.getInstance();
		today = aCalendar.getTime();
		aCalendar.add(Calendar.MONTH, -3);
		aCalendar.set(Calendar.DATE, 1);
		threeMonthsAgo = aCalendar.getTime();
		data1 = dateFormat.format(threeMonthsAgo);
		data2 = dateFormat.format(today);
		leituradata = dateFormat.format(today);
		valor = "0.00";
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

		try {
			switch (action) {
			case "/new":
				showNewForm(request, response);
				break;
			case "/insert":
				insertcontadorelect(request, response);
				break;
			case "/delete":
				deletecontadorelect(request, response);
				break;
			case "/edit":
				showEditForm(request, response);
				break;
			case "/update":
				updatecontadorelect(request, response);
				break;
			case "/list":
				listSomecontadorelect(request, response);
				break;
			case "/ContadorElectServlet":
				listSomecontadorelect(request, response);
				break;
			case "/ElectPrecos":
				getElectPrecos(request, response);
				break;
			case "/getPrices":
				getElectPrecos(request, response);
				break;
			case "/storeAttributes":
				storeAttributes(request, response);
				break;
			default:
				listSomecontadorelect(request, response);
				break;
			}
		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}

	@SuppressWarnings("unused")
	private void listcontadorelect(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException,
			ServletException {
		System.out.println("====================> Listcontadorelect: ");

		List<IncomeStatement> listcontadorelect = contadorelectDAO
				.selectAllContadorElect(data1, data2, parcela);
		request.setAttribute("listcontadorelect", listcontadorelect);
		RequestDispatcher dispatcher = request
				.getRequestDispatcher("contadorelect-list.jsp");
		dispatcher.forward(request, response);
	}

	private void listSomecontadorelect(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException,
			ServletException {
		String d1 = data1, d2 = data2, p = "";
		System.out.println("====================> ListSomecontadorelect: ");
		if (request.getParameter("Data1") != null)
			d1 = request.getParameter("Data1");
		if (request.getParameter("Data2") != null)
			d2 = request.getParameter("Data2");
		if (request.getParameter("Parcela") != null)
			p = request.getParameter("Parcela");

		request.getSession().setAttribute("D1Value", d1);
		request.getSession().setAttribute("D2Value", d2);
		request.getSession().setAttribute("PValue", p);

		List<IncomeStatement> listcontadorelect = contadorelectDAO
				.selectAllContadorElect(d1, d2, p);
		request.setAttribute("listcontadorelect", listcontadorelect);
		RequestDispatcher dispatcher = request
				.getRequestDispatcher("contadorelect-list.jsp");
		dispatcher.forward(request, response);
	}

	private void getElectPrecos(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException,
			ServletException {

		String fatura = request.getParameter("fatura1");
		String parcela = request.getParameter("parcela1");
		String companhia = request.getParameter("companhia1");
		String data1 = request.getParameter("data11");
		String data2 = request.getParameter("data21");
		String leituradata = request.getParameter("leituradata1");
		String est = request.getParameter("est1");
		String valor = request.getParameter("totvalor1");

		request.getSession().setAttribute("FaturaValue", fatura);
		request.getSession().setAttribute("ParcelaValue", parcela);
		request.getSession().setAttribute("CompanhiaValue", companhia);
		request.getSession().setAttribute("Data1Value", data1);
		request.getSession().setAttribute("Data2Value", data2);
		request.getSession().setAttribute("LeituradataValue", leituradata);
		request.getSession().setAttribute("EstValue", est);
		request.getSession().setAttribute("ValorValue", valor);

		List<ElectPrecos> listelectprecos = contadorelectDAO.getElectPrecos(
				parcela, leituradata);

		request.setAttribute("listelectprecos", listelectprecos);

		ElectPrecos ep = listelectprecos.get(0);
		int tipo = ep.getTipo();
		List<TableRows> tablerows = contadorelectDAO.getTableRows(tipo,
				companhia, listelectprecos, 0, 0, 0, 0);
		System.out.println("..............> getElectPrecos: , parcela: "
				+ parcela + ", companhia: " + companhia + ", leitutadata: "
				+ leituradata);
		for (int i = 0; i < tablerows.size(); i++) {
			TableRows tr = tablerows.get(i);
			System.out.println("              > getElectPrecos: , descr: "
					+ tr.getDescr() + ", preço: " + tr.getPreco() + ", desc: "
					+ tr.getDesc() + ", iva: " + tr.getIva());
		}
		request.setAttribute("tablerows", tablerows);

		RequestDispatcher dispatcher = request
				.getRequestDispatcher("contadorelect-form.jsp");
		dispatcher.forward(request, response);
	}

	private void storeAttributes(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException,
			ServletException {

		if (request.getParameter("fatura") != null)
			fatura = request.getParameter("fatura");
		if (request.getParameter("parcela") != null)
			parcela = request.getParameter("parcela");
		if (request.getParameter("companhia") != null)
			companhia = request.getParameter("companhia");
		if (request.getParameter("data1") != null)
			data1 = request.getParameter("data1");
		if (request.getParameter("data2") != null)
			data2 = request.getParameter("data2");
		if (request.getParameter("leituradata") != null)
			leituradata = request.getParameter("leituradata");
		if (request.getParameter("est") != null)
			est = request.getParameter("est");
		if (request.getParameter("valor") != null)
			valor = request.getParameter("valor");
		request.getSession().setAttribute("FaturaValue", fatura);
		request.getSession().setAttribute("ParcelaValue", parcela);
		request.getSession().setAttribute("CompanhiaValue", companhia);
		request.getSession().setAttribute("Data1Value", data1);
		request.getSession().setAttribute("Data2Value", data2);
		request.getSession().setAttribute("LeituradataValue", leituradata);
		request.getSession().setAttribute("EstValue", est);
		request.getSession().setAttribute("ValorValue", valor);

		RequestDispatcher dispatcher = request
				.getRequestDispatcher("contadorelect-form.jsp");
		dispatcher.forward(request, response);

	}

	private void showNewForm(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("====================> showNewForm(");

		// Get initial dates and Parcela from contadorelect-list.jsp

		if (request.getSession().getAttribute("D1Value") != null) {
			data1 = request.getSession().getAttribute("D1Value").toString();
			data2 = request.getSession().getAttribute("D2Value").toString();
			// parcela = request.getSession().getAttribute("PValue").toString();
		}

		if (request.getSession().getAttribute("fatura") != null)
			fatura = request.getSession().getAttribute("FaturaValue")
					.toString();
		if (request.getSession().getAttribute("parcela") != null)
			parcela = request.getSession().getAttribute("ParcelaValue")
					.toString();
		if (request.getSession().getAttribute("companhia") != null)
			companhia = request.getSession().getAttribute("CompanhiaValue")
					.toString();
		if (request.getSession().getAttribute("data1") != null)
			data1 = request.getSession().getAttribute("Data1Value").toString();
		if (request.getSession().getAttribute("data2") != null)
			data2 = request.getSession().getAttribute("Data2Value").toString();
		if (request.getSession().getAttribute("leituradata") != null)
			leituradata = request.getSession().getAttribute("LeituradataValue")
					.toString();
		if (request.getSession().getAttribute("est") != null)
			est = request.getSession().getAttribute("EstValue").toString();
		if (request.getSession().getAttribute("valor") != null)
			valor = request.getSession().getAttribute("ValorValue").toString();
		request.getSession().setAttribute("FaturaValue", fatura);
		request.getSession().setAttribute("ParcelaValue", parcela);
		request.getSession().setAttribute("CompanhiaValue", companhia);
		request.getSession().setAttribute("Data1Value", data1);
		request.getSession().setAttribute("Data2Value", data2);
		request.getSession().setAttribute("LeituradataValue", leituradata);
		request.getSession().setAttribute("EstValue", est);
		request.getSession().setAttribute("ValorValue", valor);
		RequestDispatcher dispatcher = request
				.getRequestDispatcher("contadorelect-form.jsp");
		dispatcher.forward(request, response);
	}

	private void showEditForm(HttpServletRequest request,
			HttpServletResponse response) throws SQLException,
			ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));

		System.out.println("====================> showEditForm, no: " + id);

		List<IncomeStatement> existingcontadorelect = contadorelectDAO
				.selectContadorElect(id);
		int vazio = existingcontadorelect.get(0).getVazio();
		int foraVazio = existingcontadorelect.get(0).getForavazio();
		int cheia = existingcontadorelect.get(0).getCheia();
		int ponta = existingcontadorelect.get(0).getPonta();
		System.out.println("----> vazio: " + vazio + ", foraVazio: "
				+ foraVazio + ", cheia: " + cheia + ", ponta: " + ponta);
		request.getSession().setAttribute("FaturaValue",
				existingcontadorelect.get(0).getFatura());
		parcela = existingcontadorelect.get(0).getParcela();
		request.getSession().setAttribute("ParcelaValue",
				existingcontadorelect.get(0).getParcela());

		request.getSession().setAttribute("CompanhiaValue",
				existingcontadorelect.get(0).getCompanhia());

		request.getSession().setAttribute("Data1Value",
				existingcontadorelect.get(0).getData1());
		request.getSession().setAttribute("Data2Value",
				existingcontadorelect.get(0).getData2());

		leituradata = existingcontadorelect.get(0).getLeituradata();
		request.getSession().setAttribute("LeituradataValue",
				existingcontadorelect.get(0).getLeituradata());
		request.getSession().setAttribute("EstValue",
				existingcontadorelect.get(0).getEst());
		request.getSession().setAttribute("ValorValue",
				existingcontadorelect.get(0).getValor());
		request.setAttribute("existingcontadorelect", existingcontadorelect);

		List<ElectPrecos> listelectprecos = contadorelectDAO.getElectPrecos(
				parcela, leituradata);

		request.setAttribute("listelectprecos", listelectprecos);

		ElectPrecos ep = listelectprecos.get(0);
		int tipo = ep.getTipo();
		List<TableRows> tablerows = contadorelectDAO.getTableRows(tipo,
				companhia, listelectprecos, vazio, foraVazio, ponta, cheia);
		for (int i = 0; i < tablerows.size(); i++) {
			TableRows tr = tablerows.get(i);
			System.out.println("#$#$#$#$#$#$#$#    > getElectPrecos: , descr: "
					+ tr.getDescr() + ", quant: " + tr.getQuant() + ", preço: "
					+ tr.getPreco() + ", desc: " + tr.getDesc() + ", iva: "
					+ tr.getIva());
		}
		request.setAttribute("tablerows", tablerows);
		request.setAttribute("id", id);
		RequestDispatcher dispatcher = request
				.getRequestDispatcher("contadorelect-form.jsp");
		dispatcher.forward(request, response);

	}

	private void insertcontadorelect(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException {
		try {

			String fatura = request.getSession().getAttribute("FaturaValue")
					.toString();
			String parcela = request.getSession().getAttribute("ParcelaValue")
					.toString();
			String companhia = request.getSession()
					.getAttribute("CompanhiaValue").toString();
			String data1 = request.getSession().getAttribute("Data1Value")
					.toString();
			String data2 = request.getSession().getAttribute("Data2Value")
					.toString();
			String leituradata = request.getSession()
					.getAttribute("LeituradataValue").toString();
			String est = request.getSession().getAttribute("EstValue")
					.toString();
			String valorValue = request.getSession().getAttribute("ValorValue")
					.toString();
			System.out.println("|||----> insertcontadorelect  fatura: "
					+ fatura + ", parcela: " + parcela + ", data1: " + data1
					+ ", data2: " + data2 + ", leituradata: " + leituradata
					+ ", est: " + est + ", valorValue: " + valorValue);
			String value, s;
			String[] quant = request.getParameterValues("quant");
			int vazio = 0;
			int ponta = 0;
			int cheia = 0;
			int foraVazio = 0;
			double valor = 0.0;
			/*
			 * for (int i = 0; i < quant.length; i++) { value = quant[i]; // s =
			 * value.replaceAll("[^0-9.]", ""); if (i == 0) vazio =
			 * Integer.parseInt(value); else if (i == 1) ponta =
			 * Integer.parseInt(value); else if (i == 2) cheia =
			 * Integer.parseInt(value); else foraVazio =
			 * Integer.parseInt(value); }
			 */
			value = request.getParameter("vazio");
			s = value.replaceAll("[^0-9.]", "");
			vazio = Integer.parseInt(s);

			value = request.getParameter("ponta");
			s = value.replaceAll("[^0-9.]", "");
			ponta = Integer.parseInt(s);

			value = request.getParameter("cheias");
			s = value.replaceAll("[^0-9.]", "");
			cheia = Integer.parseInt(s);

			value = request.getParameter("foravazio");
			s = value.replaceAll("[^0-9.]", "");
			foraVazio = Integer.parseInt(s);

			System.out.println("%&%& vazio: " + vazio + ", foravazio: "
					+ foraVazio + ", ponta: " + ponta + ", cheias: " + cheia
					+ ", valor: " + valor);

			String grandtot = request.getParameter("grandtot");
			String totiva = request.getParameter("totiva");

			// s = grandtot.replaceAll("[^0-9.]", "");
			double gtot = Double.parseDouble(grandtot);
			// s = totiva.replaceAll("[^0-9.]", "");
			double tiva = Double.parseDouble(totiva);

			valor = gtot + tiva;
			System.out.println("valor: " + valor);

			IncomeStatement newcontadorelect = new IncomeStatement(fatura, parcela,
					data1, data2, leituradata, est, vazio, ponta, cheia,
					foraVazio, valor, companhia, 0);

			contadorelectDAO.insertContadorElect(newcontadorelect);
		} catch (Exception ex) {
			System.out.println("Error occured when parsing data.");
		}
		response.sendRedirect("list");
	}

	private void updatecontadorelect(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException {
		System.out.println("====================> updatecontadorelect");
		String idString = null;
		try {
			idString = request.getParameter("id");
			int id = Integer.parseInt(idString);
			String fatura = request.getParameter("fatura");
			// String parcela = request.getParameter("parcela");
			String parcela = request.getSession().getAttribute("ParcelaValue")
					.toString();

			String data1 = request.getParameter("data1");
			String data2 = request.getParameter("data2");
			String leituradata = request.getParameter("leituradata");
			String est = request.getParameter("est");

			String value = request.getParameter("vazio");
			String s = value.replaceAll("[^0-9.]", "");
			int vazio = Integer.parseInt(s);

			value = request.getParameter("ponta");
			s = value.replaceAll("[^0-9.]", "");
			int ponta = Integer.parseInt(s);

			value = request.getParameter("cheias");
			s = value.replaceAll("[^0-9.]", "");
			int cheia = Integer.parseInt(s);

			value = request.getParameter("foravazio");
			s = value.replaceAll("[^0-9.]", "");
			int foraVazio = Integer.parseInt(s);

			value = request.getParameter("totvalor");
			s = value.replaceAll("[^0-9.]", "");
			double valor = Double.parseDouble(s);
			System.out.println("%&%& vazio: " + vazio + ", foravazio: "
					+ foraVazio + ", ponta: " + ponta + ", cheias: " + cheia
					+ ", valor: " + valor);
			String companhia = request.getParameter("companhia");
			IncomeStatement book = new IncomeStatement(fatura, parcela, data1,
					data2, leituradata, est, vazio, ponta, cheia, foraVazio,
					valor, companhia, id);
			contadorelectDAO.updateContadorElect(book);

		} catch (Exception ex) {
			System.out.println("Error occured when parsing data.");
		}
		response.sendRedirect("list");
	}

	private void deletecontadorelect(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException {
		System.out.println("====================> deletecontadorelect");
		int id = Integer.parseInt(request.getParameter("id"));
		contadorelectDAO.deleteContadorElect(id);
		// System.out.println("-----> Deleted no: " + id);
		response.sendRedirect("list");

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