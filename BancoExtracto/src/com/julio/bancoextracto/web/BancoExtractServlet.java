package com.julio.bancoextracto.web;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.julio.bancoextracto.dao.BancoExtractoDAO;
import com.julio.bancoextracto.model.BancoExtracto;
import com.julio.bancoextracto.model.Classificacao;
import com.julio.bancoextracto.model.ContraFactura;
import com.julio.bancoextracto.model.Descr;
import com.julio.bancoextracto.model.Parcela;
import com.julio.bancoextracto.model.Seara;

/**
 * Servlet implementation class BancoExtract2Servlet
 */
@WebServlet("/")
public class BancoExtractServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	BancoExtractoDAO bancoextractoDAO;
	String descricao = "";
	String classificacao = "";
	String descr = "";
	String seara = "";
	String parcela = "";
	String data1 = "";
	String data2 = "";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BancoExtractServlet() {
		super();
		// TODO Auto-generated constructor stub

		System.out.println("BancoExtract2Servlet()");
	}

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		bancoextractoDAO = new BancoExtractoDAO();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date threeMonthsAgo = null;
		java.util.Date today = null;

		Calendar aCalendar = Calendar.getInstance();
		today = aCalendar.getTime();
		aCalendar.add(Calendar.MONTH, -3);
		aCalendar.set(Calendar.DATE, 1);
		threeMonthsAgo = aCalendar.getTime();
		data1 = dateFormat.format(threeMonthsAgo);
		data2 = dateFormat.format(today);
		System.out.println("init(ServletConfig config)");

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("doPost");
		doGet(request, response);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath();
		System.out.println("---> doGet GuiaEntrada, action: " + action);
		try {
			switch (action) {
			case "/new":
				showNewForm(request, response);
				break;
			case "/insert":
				insertBancoExtracto(request, response);
				break;
			case "/delete":
				deleteBancoExtracto(request, response);
				break;
			case "/edit":
				showEditForm(request, response);
				break;
			case "/update":
				updateBancoExtracto(request, response);
				break;
			case "/facturas":
				getFactura(request, response);
				break;
			default:
				listBancoExtracto(request, response);
				break;
			}
		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}

	private void listBancoExtracto(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException,
			ServletException {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date threeMonthsAgo = null;
		java.util.Date today = null;

		if (request.getParameter("data1") != null)
			data1 = request.getParameter("data1");
		if (request.getParameter("data2") != null)
			data2 = request.getParameter("data2");
		if (request.getParameter("descricao") != null)
			descricao = request.getParameter("descricao");
		if (request.getParameter("classificacao") != null)
			classificacao = request.getParameter("classificacao");
		if (request.getParameter("descr") != null)
			descr = request.getParameter("descr");

		if (request.getParameter("parcela") != null)
			parcela = request.getParameter("parcela");
		if (request.getParameter("seara") != null)
			seara = request.getParameter("seara");

		request.getSession().setAttribute("data1", data1);
		request.getSession().setAttribute("data2", data2);
		request.getSession().setAttribute("descricao", descricao);
		request.getSession().setAttribute("classificacao", classificacao);
		request.getSession().setAttribute("descr", descr);
		request.getSession().setAttribute("parcela", parcela);
		request.getSession().setAttribute("seara", seara);

		List<BancoExtracto> listBancoExtracto = bancoextractoDAO
				.selectAllBancoExtracto(data1, data2, descricao, classificacao,
						descr, parcela, seara);
		request.setAttribute("bancoextracto", listBancoExtracto);
		RequestDispatcher dispatcher = request
				.getRequestDispatcher("bancoextracto-list.jsp");
		dispatcher.forward(request, response);
	}

	private void showNewForm(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		List<Descr> descr = bancoextractoDAO.getDescr();
		request.setAttribute("descr", descr);

		List<Classificacao> classificacao = bancoextractoDAO.getClassificacao();
		request.setAttribute("classificacao", classificacao);

		List<Parcela> parcela = bancoextractoDAO.getParcela();
		request.setAttribute("parcela", parcela);

		List<Seara> seara = bancoextractoDAO.getSeara();
		request.setAttribute("seara", seara);

		RequestDispatcher dispatcher = request
				.getRequestDispatcher("bancoextracto-form.jsp");
		dispatcher.forward(request, response);
	}

	private void showEditForm(HttpServletRequest request,
			HttpServletResponse response) throws SQLException,
			ServletException, IOException {

		int id = Integer.parseInt(request.getParameter("id"));
		BancoExtracto existingBancoExtracto = bancoextractoDAO
				.selectBancoExtracto(id);

		List<Descr> descr = bancoextractoDAO.getDescr();
		descr.add(new Descr(id, existingBancoExtracto.getDescr()));

		request.setAttribute("descr", descr);

		List<Classificacao> classificacao = bancoextractoDAO.getClassificacao();
		request.setAttribute("classificacao", classificacao);

		List<Parcela> parcela = bancoextractoDAO.getParcela();
		request.setAttribute("parcela", parcela);

		List<Seara> seara = bancoextractoDAO.getSeara();
		request.setAttribute("seara", seara);

		if (existingBancoExtracto.getClassificacao().contains("Contra"))
			request.setAttribute("contra",
					existingBancoExtracto.getClassificacao());

		RequestDispatcher dispatcher = request
				.getRequestDispatcher("bancoextracto-form.jsp");
		request.setAttribute("bancoextracto", existingBancoExtracto);
		dispatcher.forward(request, response);

	}

	private void insertBancoExtracto(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException {
		String dataMov = request.getParameter("datamov");
		String dataValor = request.getParameter("datavalor");
		String descricao = request.getParameter("descricao");
		String text = request.getParameter("montante");
		String s = text.replaceAll("[^0-9.-]", "");
		double montante = Double.parseDouble(s);
		text = request.getParameter("saldo");
		s = text.replaceAll("[^0-9.-]", "");
		double saldo = Double.parseDouble(s);
		String classificacao = request.getParameter("classificacao");
		String descr = request.getParameter("descr");
		String parcela = request.getParameter("parcela");
		String seara = request.getParameter("seara");
		String banco = request.getParameter("banco");

		BancoExtracto newBancoExtracto = new BancoExtracto(dataMov, dataValor,
				descricao, montante, saldo, classificacao, descr, parcela,
				seara, banco);
		bancoextractoDAO.insertBancoExtracto(newBancoExtracto);
		response.sendRedirect("list");
	}

	private void getFactura(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		BancoExtracto existingBancoExtracto = bancoextractoDAO
				.selectBancoExtracto(id);

		String dataMov = existingBancoExtracto.getDataMov();
		String dataValor = existingBancoExtracto.getDataValor();
		String descricao = existingBancoExtracto.getDescricao();
		double montante = existingBancoExtracto.getMontante();
		String parcela = "parcela";
		List<ContraFactura> facturas = BancoExtractoDAO.selectFactura(dataMov,
				montante);
		double saldo = 0;
		String classificacao = request.getParameter("classificacao");
		String descr = "descr";
		String seara = existingBancoExtracto.getSeara();
		String banco = existingBancoExtracto.getBanco();
		for (int i = 0; i < facturas.size(); i++) {
			ContraFactura factura = facturas.get(i);
			descr = factura.getInvno();
			parcela = factura.getParcela();
			montante = factura.getValorliq();
			BancoExtracto newBancoExtracto = new BancoExtracto(dataMov,
					dataValor, descricao, montante, saldo, classificacao,
					descr, parcela, seara, banco);
			bancoextractoDAO.insertBancoExtracto(newBancoExtracto);
		}
		response.sendRedirect("list");
	}

	private void updateBancoExtracto(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		String dataMov = request.getParameter("datamov");
		String dataValor = request.getParameter("datavalor");
		String descricao = request.getParameter("descricao");
		String text = request.getParameter("montante");
		String s = text.replaceAll("[^0-9.-]", "");
		double montante = Double.parseDouble(s);
		text = request.getParameter("saldo");
		s = text.replaceAll("[^0-9.-]", "");
		double saldo = Double.parseDouble(s);
		String classificacao = request.getParameter("classificacao");
		String descr = request.getParameter("descr");
		String parcela = request.getParameter("parcela");
		String seara = request.getParameter("seara");
		String banco = request.getParameter("banco");
		BancoExtracto book = new BancoExtracto(id, dataMov, dataValor,
				descricao, montante, saldo, classificacao, descr, parcela,
				seara, banco);
		bancoextractoDAO.updateBancoExtracto(book);
		if (classificacao.contains("Contra")) {
			montante = montante * -1;
			if (!bancoextractoDAO.recordExists(dataMov, descricao,
					classificacao, montante)) {
				book = new BancoExtracto(id, dataMov, dataValor, descricao,
						montante, saldo, classificacao, descr, parcela, seara,
						banco);
				bancoextractoDAO.insertBancoExtracto(book);
			}

		}
		response.sendRedirect("list");
	}

	private void deleteBancoExtracto(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		bancoextractoDAO.deleteBancoExtracto(id);
		response.sendRedirect("list");

	}

}
