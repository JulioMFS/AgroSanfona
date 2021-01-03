package com.julio.guia.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.julio.guia.model.GuiaEntrada;

/**
 * AbstractDAO.java This DAO class provides CRUD database operations for the
 * table guiaentradas in the database.
 * 
 * @author Ramesh Fadatare
 * 
 */
public class GuiaEntradaDAO {
	private String envirnmt = "xxxx";
	private String jdbcURL = "jdbc:mysql://localhost:3306/" + envirnmt
			+ "?useSSL=false";
	private String jdbcUsername = "root";
	private String jdbcPassword = "j301052";
	private static final String INSERT_GUIAENTRADAS_SQL = "INSERT INTO guiaentrada"
			+ "  (GuiaNo, Data, Hora, Artigo, Descricao, Matricula, PesoEsp, PesoVerde,"
			+ " Humidade, ConvTN, Peso, NotaPagamento, Parcela) VALUES "
			+ " (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

	private static final String SELECT_GUIAENTRADA_BY_ID = "select ID, GuiaNo, Data, Hora, Artigo, Descricao, Matricula,"
			+ " PesoEsp, PesoVerde,  Humidade, ConvTN, Peso, NotaPagamento, Parcela"
			+ " from guiaentrada where id =?";
	private static final String SELECT_ALL_GUIAENTRADAS = "select * from guiaentrada"
			+ " where Data between ? and ? and Matricula like ? and Parcela like ?";
	private static final String DELETE_GUIAENTRADAS_SQL = "delete from guiaentrada where id = ?;";
	private static final String UPDATE_GUIAENTRADAS_SQL = "update guiaentrada set GuiaNo = ?, Data = ?, Hora = ?, "
			+ "Artigo = ?, Descricao  = ?, Matricula = ?, PesoEsp = ?, PesoVerde = ?, "
			+ "Humidade = ?, ConvTN = ?, Peso = ?, NotaPagamento = ?, Parcela = ? where id = ?;";
	private static final String SELECT_PARCELA_AREA = "select Area from parcela where Nome = ?";

	public GuiaEntradaDAO() {
		String currentDirectory = System.getProperty("user.dir");
		if (currentDirectory.indexOf("Apache") > 0
				|| currentDirectory.indexOf("apache") > 0
				|| currentDirectory.indexOf("pi") > 0)
			envirnmt = "agro";
		else
			envirnmt = "test";
		jdbcURL = "jdbc:mysql://localhost:3306/" + envirnmt + "?useSSL=false";
	}

	protected Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(jdbcURL, jdbcUsername,
					jdbcPassword);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}

	public void insertGuiaEntrada(GuiaEntrada guiaentrada) throws SQLException {
		System.out.println(INSERT_GUIAENTRADAS_SQL);
		// try-with-resource statement will auto close the connection.
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection
						.prepareStatement(INSERT_GUIAENTRADAS_SQL)) {
			preparedStatement.setString(1, guiaentrada.getGuiaNo());
			preparedStatement.setString(2, guiaentrada.getData());
			preparedStatement.setString(3, guiaentrada.getHora());
			preparedStatement.setString(4, guiaentrada.getArtigo());
			preparedStatement.setString(5, guiaentrada.getDescricao());
			preparedStatement.setString(6, guiaentrada.getMatricula());
			preparedStatement.setDouble(7, guiaentrada.getPesoEsp());
			preparedStatement.setDouble(8, guiaentrada.getPesoVerde());
			preparedStatement.setDouble(9, guiaentrada.getHumidade());
			preparedStatement.setDouble(10, guiaentrada.getConvTN());
			preparedStatement.setDouble(11, guiaentrada.getPeso());
			preparedStatement.setInt(12, guiaentrada.getNotaPagamento());
			preparedStatement.setString(13, guiaentrada.getParcela());

			System.out.println(preparedStatement);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			printSQLException(e);
		}
	}

	public GuiaEntrada selectGuiaEntrada(int id) {
		GuiaEntrada guiaentrada = null;
		// Step 1: Establishing a Connection
		try (Connection connection = getConnection();
		// Step 2:Create a statement using connection object
				PreparedStatement preparedStatement = connection
						.prepareStatement(SELECT_GUIAENTRADA_BY_ID);) {
			preparedStatement.setInt(1, id);
			System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			ResultSet rs = preparedStatement.executeQuery();

			// Step 4: Process the ResultSet object.
			while (rs.next()) {
				String guiaNo = rs.getString("GuiaNo");
				String data = rs.getString("Data");
				String hora = rs.getString("Hora");
				String artigo = rs.getString("Artigo");
				String descricao = rs.getString("Descricao");
				String matricula = rs.getString("Matricula");
				double pesoEsp = rs.getDouble("PesoEsp");
				double pesoVerde = rs.getDouble("PesoVerde");
				double humidade = rs.getDouble("Humidade");
				double convTN = rs.getDouble("ConvTN");
				double peso = rs.getDouble("Peso");
				int notaPagamento = rs.getInt("NotaPagamento");
				String parcela = rs.getString("Parcela");
				id = rs.getInt("ID");

				guiaentrada = new GuiaEntrada(id, guiaNo, data, hora, artigo,
						descricao, matricula, pesoEsp, pesoVerde, humidade,
						convTN, peso, notaPagamento, parcela);
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return guiaentrada;
	}

	public List<GuiaEntrada> selectAllGuiaEntradas(String data1, String data2,
			String matricul, String parcel) {

		// using try-with-resources to avoid closing resources (boiler plate
		// code)
		double totPesoVerde = 0;
		double totPeso = 0;
		double avgPesoEsp = 0;
		double avgHumidade = 0;
		double avgConvTN = 0;
		HashMap<String, Double> parcelaArea = new HashMap<String, Double>();

		DecimalFormat df = new DecimalFormat("0.00");
		List<GuiaEntrada> guiaentradas = new ArrayList<>();
		// Step 1: Establishing a Connection
		try (Connection connection = getConnection();

		// Step 2:Create a statement using connection object
				PreparedStatement preparedStatement = connection
						.prepareStatement(SELECT_ALL_GUIAENTRADAS);) {
			String matricu = "%";
			if (matricul != null && !matricul.isEmpty())
				matricu = matricul + "%";
			String parce = "%";
			if (parcel != null && !parcel.isEmpty())
				parce = parcel + "%";
			preparedStatement.setString(1, data1);
			preparedStatement.setString(2, data2);
			preparedStatement.setString(3, matricu);
			preparedStatement.setString(4, parce);
			System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			ResultSet rs = preparedStatement.executeQuery();
			int i = 0;
			// Step 4: Process the ResultSet object.
			while (rs.next()) {
				i++;
				int id = rs.getInt("ID");
				String guiaNo = rs.getString("GuiaNo");
				String data = rs.getString("Data");
				String hora = rs.getString("Hora");
				String artigo = rs.getString("Artigo");
				String descricao = rs.getString("Descricao");
				String matricula = rs.getString("Matricula");
				double pesoEsp = rs.getDouble("PesoEsp");
				avgPesoEsp = avgPesoEsp + pesoEsp;
				double pesoVerde = rs.getDouble("PesoVerde");
				totPesoVerde = totPesoVerde + pesoVerde;
				double humidade = rs.getDouble("Humidade");
				avgHumidade = avgHumidade + humidade;
				double convTN = rs.getDouble("ConvTN");
				avgConvTN = avgConvTN + convTN;
				double peso = rs.getDouble("Peso");
				totPeso = totPeso + peso;
				int notaPagamento = rs.getInt("NotaPagamento");
				String parcela = rs.getString("Parcela");
				if (!parcelaArea.containsKey(parcela)) {
					parcelaArea.put(parcela, getParcelaArea(parcela));
				}
				guiaentradas.add(new GuiaEntrada(id, guiaNo, data, hora,
						artigo, descricao, matricula, pesoEsp, pesoVerde,
						humidade, convTN, peso, notaPagamento, parcela));
			}
			double area = 0;
			double tonPerHectare = 0;
			// Print keys and values
			for (String ii : parcelaArea.keySet()) {
				System.out.println("key: " + ii + " value: "
						+ parcelaArea.get(ii));
				area += parcelaArea.get(ii);
			}
			tonPerHectare = totPeso / area;
			String tonPerHect = df.format(tonPerHectare) + " ton/ha";
			guiaentradas.add(new GuiaEntrada(0, "", "", "", "", "Totals", "",
					avgPesoEsp / i, totPesoVerde, avgHumidade / i, avgConvTN
							/ i, totPeso, 0, tonPerHect));

		} catch (SQLException e) {
			printSQLException(e);
		}
		return guiaentradas;
	}

	public boolean deleteGuiaEntrada(int id) throws SQLException {
		boolean rowDeleted;
		try (Connection connection = getConnection();
				PreparedStatement statement = connection
						.prepareStatement(DELETE_GUIAENTRADAS_SQL);) {
			statement.setInt(1, id);
			rowDeleted = statement.executeUpdate() > 0;
		}
		return rowDeleted;
	}

	public boolean updateGuiaEntrada(GuiaEntrada guiaentrada)
			throws SQLException {
		boolean rowUpdated;
		try (Connection connection = getConnection();
				PreparedStatement statement = connection
						.prepareStatement(UPDATE_GUIAENTRADAS_SQL);) {
			statement.setString(1, guiaentrada.getGuiaNo());
			statement.setString(2, guiaentrada.getData());
			statement.setString(3, guiaentrada.getHora());
			statement.setString(4, guiaentrada.getArtigo());
			statement.setString(5, guiaentrada.getDescricao());
			statement.setString(6, guiaentrada.getMatricula());
			statement.setDouble(7, guiaentrada.getPesoEsp());
			statement.setDouble(8, guiaentrada.getPesoVerde());
			statement.setDouble(9, guiaentrada.getHumidade());
			statement.setDouble(10, guiaentrada.getConvTN());
			statement.setDouble(11, guiaentrada.getPeso());
			statement.setInt(12, guiaentrada.getNotaPagamento());
			statement.setString(13, guiaentrada.getParcela());
			statement.setInt(14, guiaentrada.getId());

			rowUpdated = statement.executeUpdate() > 0;
		}
		return rowUpdated;
	}

	public double getParcelaArea(String nome) {
		// Step 1: Establishing a Connection
		double area = 0;
		try (Connection connection = getConnection();
		// Step 2:Create a statement using connection object
				PreparedStatement preparedStatement = connection
						.prepareStatement(SELECT_PARCELA_AREA);) {
			preparedStatement.setString(1, nome);
			System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			ResultSet rs = preparedStatement.executeQuery();

			// Step 4: Process the ResultSet object.
			while (rs.next()) {
				area = rs.getDouble("Area");
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return area;
	}

	private void printSQLException(SQLException ex) {
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