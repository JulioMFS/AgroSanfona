package com.julio.evento.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.julio.evento.model.Alfaias;
import com.julio.evento.model.Category;
import com.julio.evento.model.Event;
import com.julio.evento.model.Eventos;
import com.julio.evento.model.Fornecedores;
import com.julio.evento.model.Parcela;
import com.julio.evento.model.Prestadores;
import com.julio.utilities.*;

/**
 * AbstractDAO.java This DAO class provides CRUD database operations for the
 * table event in the database.
 * 
 * @author Ramesh Fadatare
 * 
 */
public class EventDAO {
	private static String jdbcURL = "jdbc:mysql://localhost:3306/agro?useSSL=false";
	private static String jdbcUsername = "root";
	private static String jdbcPassword = "j301052";
	private static String env = "XXXX";
	private static String envirnmt = "";
	private static String INSERT_Event_SQL = "INSERT INTO "
			+ "Event"
			+ " (Date, Parcela, event, Prestador, Alfaia, Produto, Fornecedor, Quantidade, Unidade, Settings) VALUES "
			+ " (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

	private static String DELETE_Event_SQL = "delete from "
			+ "Event where No = ?;";

	private static String SELECT_Event = "select Date, Parcela, event, Prestador, Alfaia, "
			+ "Produto, Fornecedor, Quantidade, Unidade, Settings"
			+ " from "
			+ "Event where No = ?";

	private static String SELECT_Event_DATA = "select No, Date, Parcela, event, Prestador, Alfaia, "
			+ "Produto, Fornecedor, Quantidade, Unidade, Settings"
			+ " from "
			+ "Event where Date between ? and ?"
			+ " and Parcela like ? and event like ? and Prestador like ?";

	private static String UPDATE_Event_SQL = "update "
			+ "event set Date = ?, Parcela= ?, event =?,"
			+ " Prestador = ?, Alfaia= ?, Produto =?, Fornecedor =?, Quantidade = ?, Unidade=?, Settings=?  where No = ?";

	private static String SELECT_EVENTOS = "select Eventos from eventos order by Eventos;";
	private static String SELECT_PRESTADORES = "select Prestador from prestadores order by Prestador";
	private static String SELECT_ALFAIAS = "select Alfaia from alfaias order by Alfaia";
	private static String SELECT_FORNECEDORES = "select Fornecedores from fornecedores order by Fornecedores";
	private static final String SELECT_ALL_PARCELA = "select ID, Nome from parcela order by Nome";

	public EventDAO() {

		String currentDirectory = System.getProperty("user.dir");
		System.out.println("currentDirectory: " + currentDirectory);
		if (currentDirectory.indexOf("Apache") > 0
				|| currentDirectory.indexOf("apache") > 0
				|| currentDirectory.indexOf("pi") > 0)
			envirnmt = "agro";
		else
			envirnmt = "test";
		jdbcURL = "jdbc:mysql://localhost:3306/" + envirnmt + "?useSSL=false";
	}

	protected static Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connection = DriverManager.getConnection(jdbcURL, jdbcUsername,
					jdbcPassword);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("SQLException: " + e);
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("ClassNotFoundException: " + e);
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			System.out.println("InstantiationException: " + e);
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			System.out.println("IllegalAccessException: " + e);
			e.printStackTrace();
		}
		return connection;
	}

	public static void insertEvent(Event event) throws SQLException {
		INSERT_Event_SQL = INSERT_Event_SQL.replaceAll("XXXX", envirnmt);
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection
						.prepareStatement(INSERT_Event_SQL)) {
			preparedStatement.setString(1, Event.getDate());
			preparedStatement.setString(2, Event.getParcela());
			preparedStatement.setString(3, Event.getEvent());
			preparedStatement.setString(4, Event.getPrestador());
			preparedStatement.setString(5, Event.getAlfaia());
			preparedStatement.setString(6, Event.getProduto());
			preparedStatement.setString(7, Event.getFornecedor());
			preparedStatement.setDouble(8, Event.getQuantidade());
			preparedStatement.setString(9, Event.getUnidade());
			preparedStatement.setString(10, Event.getSettings());

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			printSQLException(e);
		}
	}

	public static boolean deleteEvent(int no) throws SQLException {
		boolean rowDeleted;
		DELETE_Event_SQL = DELETE_Event_SQL.replaceAll("XXXX", envirnmt);
		try (Connection connection = getConnection();
				PreparedStatement statement = connection
						.prepareStatement(DELETE_Event_SQL);) {
			statement.setInt(1, no);
			rowDeleted = statement.executeUpdate() > 0;
		}
		return rowDeleted;
	}

	public static List<Event> selectEvent(int no) {
		// SELECT_Event = SELECT_Event.replaceAll("XXXX", envirnmt);
		List<Event> evento = new ArrayList<>();
		// Step 1: Establishing a Connection
		try (Connection connection = getConnection();
		// Step 2:Create a statement using connection object
				PreparedStatement preparedStatement = connection
						.prepareStatement(SELECT_Event);) {
			preparedStatement.setInt(1, no);
			ResultSet rs = preparedStatement.executeQuery();

			// Step 4: Process the ResultSet object.
			while (rs.next()) {
				String date = rs.getString("Date");
				String parcela = rs.getString("parcela");
				String event = rs.getString("event");
				String prestador = rs.getString("prestador");
				String alfaia = rs.getString("alfaia");
				String produto = rs.getString("produto");
				String fornecedor = rs.getString("fornecedor");
				double quantidade = rs.getDouble("quantidade");
				String unidade = rs.getString("unidade");
				String settings = rs.getString("settings");
				evento.add(new Event(date, parcela, event, prestador, alfaia,
						produto, fornecedor, quantidade, unidade, settings));
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return evento;
	}

	public static List<Event> selectEvent(String data1, String data2,
			String parcla, String evnto, String prstador) {
		SELECT_Event_DATA = SELECT_Event_DATA.replaceAll("XXXX", envirnmt);
		List<Event> evento = new ArrayList<>();
		// Step 1: Establishing a Connection
		String p1, ev, p2;
		if (parcla.equalsIgnoreCase(""))
			p1 = "%";
		else
			p1 = parcla;
		if (evnto.equalsIgnoreCase(""))
			ev = "%";
		else
			ev = parcla;
		if (prstador.equalsIgnoreCase(""))
			p2 = "%";
		else
			p2 = prstador;
		System.out.println("SELECT_Event_DATA: " + SELECT_Event_DATA);
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection
						.prepareStatement(SELECT_Event_DATA);) {

			preparedStatement.setString(1, data1);
			preparedStatement.setString(2, data2);
			preparedStatement.setString(3, p1);
			preparedStatement.setString(4, ev);
			preparedStatement.setString(5, p2);
			ResultSet rs = preparedStatement.executeQuery();

			// Step 4: Process the ResultSet object.
			while (rs.next()) {
				int no = rs.getInt("No");
				String date = rs.getString("Date");
				String parcela = rs.getString("Parcela");
				String event = rs.getString("event");
				String prestador = rs.getString("Prestador");
				String alfaia = rs.getString("Alfaia");
				String produto = rs.getString("Produto");
				String fornecedor = rs.getString("Fornecedor");
				double quantidade = rs.getDouble("Quantidade");
				String unidade = rs.getString("Unidade");
				String settings = rs.getString("Settings");
				evento.add(new Event(no, date, parcela, event, prestador,
						alfaia, produto, fornecedor, quantidade, unidade,
						settings));
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return evento;
	}

	public boolean updateEvent(Event event) throws SQLException {
		boolean rowUpdated;
		UPDATE_Event_SQL = UPDATE_Event_SQL.replaceAll("XXXX", envirnmt);
		try (Connection connection = getConnection();
				PreparedStatement ps = connection
						.prepareStatement(UPDATE_Event_SQL);) {
			ps.setString(1, Event.getDate());
			ps.setString(2, Event.getParcela());
			ps.setString(3, Event.getEvent());
			ps.setString(4, Event.getPrestador());
			ps.setString(5, Event.getAlfaia());
			ps.setString(6, Event.getProduto());
			ps.setString(7, Event.getFornecedor());
			ps.setDouble(8, Event.getQuantidade());
			ps.setString(9, Event.getUnidade());
			ps.setString(10, Event.getSettings());
			ps.setInt(11, event.getNo());
			rowUpdated = ps.executeUpdate() > 0;
		}
		return rowUpdated;
	}

	public static List<Alfaias> getAlfaias() {
		List<Alfaias> alfaias = new ArrayList<>();
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection
						.prepareStatement(SELECT_ALFAIAS);) {
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				String alfaia = rs.getString("Alfaia");

				alfaias.add(new Alfaias(alfaia));
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return alfaias;
	}

	public static List<Prestadores> getPrestadores() {
		List<Prestadores> prestadores = new ArrayList<>();
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection
						.prepareStatement(SELECT_PRESTADORES);) {
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				String prestador = rs.getString("Prestador");
				Prestadores prestadore = new Prestadores(prestador);
				prestadores.add(prestadore);
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return prestadores;
	}

	public static List<Eventos> getEventos() {
		List<Eventos> eventos = new ArrayList<>();
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection
						.prepareStatement(SELECT_EVENTOS);) {
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				String evento = rs.getString("Eventos");
				eventos.add(new Eventos(evento));
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return eventos;
	}

	public static List<Fornecedores> getFornecedores() {
		List<Fornecedores> fornecedores = new ArrayList<>();
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection
						.prepareStatement(SELECT_FORNECEDORES);) {
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				String fornecedor = rs.getString("Fornecedores");

				fornecedores.add(new Fornecedores(fornecedor));
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return fornecedores;
	}

	public static List<Parcela> getParcela() {
		List<Parcela> parcela = new ArrayList<>();
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection
						.prepareStatement(SELECT_ALL_PARCELA);) {
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("ID");
				String nome = rs.getString("Nome");

				parcela.add(new Parcela(id, nome));
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return parcela;
	}

	private static void printSQLException(SQLException ex) {
		for (Throwable e : ex) {
			if (e instanceof SQLException) {
				e.printStackTrace(System.err);
				System.err.println("SQLState: "
						+ ((SQLException) e).getSQLState());
				System.err.println("Error Code: "
						+ ((SQLException) e).getErrorCode());
				System.err.println("Message: " + e.getMessage());
				Throwable t = ex.getCause();
				while (t != null) {
					System.out.println("Cause: " + t);
					t = t.getCause();
				}
			}
		}
	}
}
