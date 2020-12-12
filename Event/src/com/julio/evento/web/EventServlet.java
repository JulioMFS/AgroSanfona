package com.julio.evento.web;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.julio.evento.dao.EventDAO;
import com.julio.evento.model.Alfaias;
import com.julio.evento.model.Category;
import com.julio.evento.model.Event;
import com.julio.evento.model.Eventos;
import com.julio.evento.model.Fornecedores;
import com.julio.evento.model.Parcela;
import com.julio.evento.model.Prestadores;

/**
 * ControllerServlet.java This servlet acts as a page controller for the
 * application, handling all requests from the Evento.
 * 
 * @email Ramesh Fadatare
 */

@WebServlet("/")
public class EventServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private EventDAO EventoDAO;
	String action;
	String parcela = "", evento = "", prestador = "", data2 = "";

	public void init() {
		EventoDAO = new EventDAO();
		System.out.println("====================> init()  ");

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("====================> doPost  ");

		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		action = request.getServletPath();

		System.out
				.println("====================> EventoServlet - doGet, action: "
						+ action);

		try {
			switch (action) {
			case "/new":
				showNewForm(request, response);
				break;
			case "/insert":
				insertEvent(request, response);
				break;
			case "/delete":
				deleteEvent(request, response);
				break;
			case "/edit":
				editEvent(request, response);
				break;
			case "/update":
				updateEvento(request, response);
				break;
			case "/Search":
				listEvent(request, response);
				break;
			case "/":
				listEvent(request, response);
				break;
			case "/List":
				listEvent(request, response);
				break;
			case "/EventServlet":
				listEvent(request, response);
				break;
			default:
				break;
			}
		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}

	private void listEvent(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException,
			ServletException {
		System.out.println("====================> listEvent: ");

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date threeMonthsAgo = null;
		java.util.Date today = null;

		Calendar aCalendar = Calendar.getInstance();
		today = aCalendar.getTime();
		aCalendar.add(Calendar.MONTH, -3);
		aCalendar.set(Calendar.DATE, 1);
		threeMonthsAgo = aCalendar.getTime();
		String data1 = dateFormat.format(threeMonthsAgo);
		data2 = dateFormat.format(today);

		if (request.getParameter("Data1") != null)
			data1 = request.getParameter("Data1");
		if (request.getParameter("Data2") != null)
			data2 = request.getParameter("Data2");
		if (request.getParameter("Parcela") != null)
			parcela = request.getParameter("Parcela");
		if (request.getParameter("Evento") != null)
			evento = request.getParameter("Evento");
		if (request.getParameter("Prestador") != null)
			prestador = request.getParameter("Prestador");

		request.getSession().setAttribute("data1", data1);
		request.getSession().setAttribute("data2", data2);
		request.getSession().setAttribute("parcela", parcela);
		request.getSession().setAttribute("evento", evento);
		request.getSession().setAttribute("prestador", prestador);

		List<Event> event = EventDAO.selectEvent(data1, data2, parcela, evento,
				prestador);
		System.out.println("=======2==2==2======> listEvent: data1: " + data1
				+ ", data2: " + data2 + ", parcela: " + parcela + ", evento: "
				+ evento + ", prestador: " + prestador);
		request.setAttribute("listEvent", event);
		RequestDispatcher dispatcher = request
				.getRequestDispatcher("EventoList.jsp");
		dispatcher.forward(request, response);
	}

	private void insertEvent(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {
		System.out.println("====================> insertEvent: ");

		String date = request.getParameter("data");
		String parcela = request.getParameter("parcela");
		String event = request.getParameter("evento");
		String prestador = request.getParameter("prestador");
		String alfaia = request.getParameter("alfaia");
		String produto = request.getParameter("produto");
		String fornecedor = request.getParameter("fornecedor");
		String quant = request.getParameter("quantidade");
		String s = quant.replaceAll("[^0-9.]", "");
		int quantidade = Integer.parseInt(s);
		String unidade = request.getParameter("un");
		String settings = request.getParameter("settings");

		Event newEvent = new Event(date, parcela, event, prestador, alfaia,
				produto, fornecedor, quantidade, unidade, settings);

		EventDAO.insertEvent(newEvent);
		RequestDispatcher dispatcher = request
				.getRequestDispatcher("EventoList.jsp");
		dispatcher.forward(request, response);
	}

	private void showNewForm(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		List<Parcela> parcela = EventoDAO.getParcela();
		request.setAttribute("pparcela", parcela);

		List<Eventos> event = EventoDAO.getEventos();
		request.setAttribute("eevento", event);

		List<Prestadores> prestador = EventoDAO.getPrestadores();
		request.setAttribute("pprestador", prestador);

		List<Alfaias> alfaia = EventoDAO.getAlfaias();
		request.setAttribute("aalfaia", alfaia);

		List<Fornecedores> fornecedor = EventoDAO.getFornecedores();
		request.setAttribute("ffornecedor", fornecedor);

		System.out.println("--------> showNewForm");
		request.setAttribute("date", data2);
		request.setAttribute("parcela", "");
		request.setAttribute("evento", "");
		request.setAttribute("prestador", "");
		request.setAttribute("alfaia", "");
		request.setAttribute("produto", "");
		request.setAttribute("fornecedor", "");
		request.setAttribute("quantidade", "");
		request.setAttribute("un", "");
		request.setAttribute("settings", "");
		RequestDispatcher dispatcher = request
				.getRequestDispatcher("evento-form.jsp");

		dispatcher.forward(request, response);
	}

	private void editEvent(HttpServletRequest request,
			HttpServletResponse response) throws SQLException,
			ServletException, IOException {

		List<Parcela> parcela = EventoDAO.getParcela();
		request.setAttribute("pparcela", parcela);

		List<Eventos> event = EventoDAO.getEventos();
		request.setAttribute("eevento", event);

		List<Prestadores> prestador = EventoDAO.getPrestadores();
		request.setAttribute("pprestador", prestador);

		List<Alfaias> alfaia = EventoDAO.getAlfaias();
		request.setAttribute("aalfaia", alfaia);

		List<Fornecedores> fornecedor = EventoDAO.getFornecedores();
		request.setAttribute("ffornecedor", fornecedor);

		int no = Integer.parseInt(request.getParameter("no"));
		request.setAttribute("no", no);
		System.out.println("====================> editEvent, no: " + no);

		List<Event> existingEvent = EventDAO.selectEvent(no);

		request.setAttribute("existingEvent", existingEvent);

		request.setAttribute("date", Event.getDate());
		request.setAttribute("parcela", Event.getParcela());
		request.setAttribute("evento", Event.getEvent());
		request.setAttribute("prestador", Event.getPrestador());
		request.setAttribute("alfaia", Event.getAlfaia());
		request.setAttribute("produto", Event.getProduto());
		request.setAttribute("fornecedor", Event.getFornecedor());
		request.setAttribute("quantidade", Event.getQuantidade());
		request.setAttribute("un", Event.getUnidade());
		request.setAttribute("settings", Event.getSettings());

		RequestDispatcher dispatcher = request
				.getRequestDispatcher("evento-form.jsp");
		dispatcher.forward(request, response);

	}

	private void updateEvent(HttpServletRequest request,
			HttpServletResponse response) throws SQLException,
			ServletException, IOException {
		String number = request.getParameter("no");
		String s = number.replaceAll("[^0-9.]", "");
		int no = Integer.parseInt(s);
		System.out.println("====================> updateEvent, no: " + no);

		RequestDispatcher dispatcher = request
				.getRequestDispatcher("evento-form.jsp");
		dispatcher.forward(request, response);

	}

	private void updateEvento(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException {
		System.out.println("====================> updateEvento");
		String x = null;
		try {
			String date = request.getParameter("data");
			String parcela = request.getParameter("parcela");
			String event = request.getParameter("evento");
			String prestador = request.getParameter("prestador");
			String alfaia = request.getParameter("alfaia");
			String produto = request.getParameter("produto");
			String fornecedor = request.getParameter("fornecedor");
			x = request.getParameter("no");
			String s = x.replaceAll("[^0-9.]", "");
			int no = Integer.parseInt(s);

			String quant = request.getParameter("quantidade");
			s = quant.replaceAll("[^0-9.]", "");
			double quantidade = Double.parseDouble(s);
			String unidade = request.getParameter("un");
			String settings = request.getParameter("settings");
			Event evento = new Event(no, date, parcela, event, prestador,
					alfaia, produto, fornecedor, quantidade, unidade, settings);
			System.out
					.println("No: " + evento.getNo() + ", date: "
							+ Event.getDate() + ", parcela: "
							+ Event.getParcela() + ", event: "
							+ Event.getEvent() + ", prestador: "
							+ Event.getPrestador() + ", alfaia: "
							+ Event.getAlfaia() + ", produto: "
							+ Event.getProduto() + ", fornecedor: "
							+ Event.getFornecedor() + ", quantidade: "
							+ Event.getQuantidade() + ", unidade: "
							+ Event.getUnidade() + ", settings: "
							+ Event.getSettings());
			EventoDAO.updateEvent(evento);

		} catch (Exception ex) {
			System.out.println("Error occured when parsing data.");
		}
		// response.sendRedirect("edit?no=" + x);
		RequestDispatcher dispatcher = request
				.getRequestDispatcher("EventoList.jsp");
		try {
			dispatcher.forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void deleteEvent(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException {
		int no = Integer.parseInt(request.getParameter("no"));
		System.out.println("====================> deleteEvent no: " + no);

		EventDAO.deleteEvent(no);
		// System.out.println("-----> Deleted no: " + id);
		response.sendRedirect("Search");

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