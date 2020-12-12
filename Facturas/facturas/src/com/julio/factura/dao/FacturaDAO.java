package com.julio.factura.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.julio.factura.model.Factura;

/**
 * AbstractDAO.java This DAO class provides CRUD database operations for the
 * table facturas in the database.
 * 
 * @author Ramesh Fadatare
 * 
 */
public class FacturaDAO {
	private static String jdbcURL = "jdbc:mysql://localhost:3306/agro?useSSL=false";
	private static String jdbcUsername = "root";
	private static String jdbcPassword = "j301052";
	private static String env = "XXXX";
	private static String envirnmt = "";

	private static String INSER_FACTURAS_SQL = "INSERT INTO "
			+ "facturas"
			+ " (Date, InvNo, Item, Designacao, Quantidade, UN, Preco, Desconto,"
			+ " DescontoV, ValorLiq, Iva, IvaV, Fornecedor, Parcela) VALUES "
			+ " (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

	private static final String SELECT_FACTURA_BY_NO = "select No ,Date, InvNo, Item, Designacao,"
			+ " Quantidade, UN, Preco, Iva, Fornecedor from "
			+ "facturas where No =?";
	private static String SELECT_ALL_FACTURAS = "select * from "
			+ "facturas"
			+ " where Date between ? and ? and Designacao like ? and Fornecedor like ?"
			+ " order by Date, InvNo, Item";
	private static final String DELETE_FACTURA_SQL = "delete from "
			+ "facturas where No = ?;";
	private static String DELETE_FACTURAS_SQL = "delete from "
			+ "facturas where Date = ? and InvNo = ?;";
	private static final String FACTURAS_LINES_SQL = "Select No from " + env
			+ "facturas where Date = ? and InvNo = ?;";

	private static String UPDATE_FACTURAS_SQL = "update "
			+ "facturas set Date = ?, InvNo= ?, Item =?,"
			+ " Designacao = ?, Quantidade= ?, UN =?, Preco =?, Desconto = ?, DescontoV = ?, ValorLiq = ?,"
			+ " Iva = ?, IvaV = ?, Fornecedor = ?, Parcela = ? where No = ?";
	private static final String SELECT_MIN_DATE = "SELECT MIN(Date) FROM "
			+ "facturas";
	private static final String SELECT_MAX_DATE = "SELECT MAX(Date) FROM "
			+ "facturas";
	private static String SELECT_FACTURA = "select No, Date, InvNo, Item, Designacao,"
			+ " Quantidade, UN, Preco, Desconto, DescontoV, ValorLiq, Iva, IvaV, Fornecedor, Parcela"
			+ " from "
			+ "facturas where InvNo in "
			+ "(select InvNo from "
			+ "facturas where No = ?) order by Item";
	private static String SELECT_COUNT_FACTURAS = "SELECT count(*) FROM "
			+ "facturas where InvNo = ? and Item = '00' and No != ?";
	private static String DELETE_NEW_FACTURA_SQL = "delete from "
			+ "facturas where InvNo = ? and No >= ?";
	private static String SELECT_THIS_FACTURA = "select No, Date, InvNo, Item, Designacao,"
			+ " Quantidade, UN, Preco, Desconto, DescontoV, ValorLiq, Iva, IvaV, Fornecedor, Parcela"
			+ " from "
			+ "facturas where InvNo in "
			+ "(select InvNo from "
			+ "facturas where No = ?) and No >= ? order by Item";

	public FacturaDAO() {
		String currentDirectory = System.getProperty("user.dir");
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

	public int insertFactura(Factura factura) throws SQLException {
		// try-with-resource statement will auto close the connection.
		String generatedKeys = "";
		int no = 0;
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection
						.prepareStatement(INSER_FACTURAS_SQL,
								Statement.RETURN_GENERATED_KEYS)) {
			preparedStatement.setString(1, factura.getData());
			preparedStatement.setString(2, factura.getInvno());
			preparedStatement.setInt(3, factura.getItem());
			preparedStatement.setString(4, factura.getDesignacao());
			preparedStatement.setDouble(5, factura.getQuantidade());
			preparedStatement.setString(6, factura.getUn());
			preparedStatement.setDouble(7, factura.getPreco());
			preparedStatement.setDouble(8, factura.getDesconto());
			preparedStatement.setDouble(9, factura.getDescontov());
			preparedStatement.setDouble(10, factura.getValorliq());
			preparedStatement.setDouble(11, factura.getIva());
			preparedStatement.setDouble(12, factura.getIvav());
			preparedStatement.setString(13, factura.getFornecedor());
			preparedStatement.setString(14, factura.getParcela());

			System.out.println(preparedStatement);

			preparedStatement.executeUpdate();
			ResultSet res = preparedStatement.getGeneratedKeys();
			System.out
					.println("Auto-incremented values of the column ID generated by the current PreparedStatement object: ");
			res.next();
			generatedKeys = res.getString(1);
			no = Integer.parseInt(generatedKeys);
			System.out.println("££££ Inserted new row no: " + res.getString(1));
			factura.setNo(no);

		} catch (SQLException e) {
			printSQLException(e);
		}
		return no;
	}

	public List<Factura> selectFactura(int no) {
		List<Factura> facturas = new ArrayList<>();
		// Step 1: Establishing a Connection
		try (Connection connection = getConnection();
		// Step 2:Create a statement using connection object
				PreparedStatement preparedStatement = connection
						.prepareStatement(SELECT_FACTURA);) {
			preparedStatement.setInt(1, no);
			System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			ResultSet rs = preparedStatement.executeQuery();

			// Step 4: Process the ResultSet object.
			while (rs.next()) {
				int No = rs.getInt("No");
				String data = rs.getString("Date");
				String invno = rs.getString("InvNo");
				int item = rs.getInt("Item");
				String designacao = rs.getString("Designacao");
				double quantidade = rs.getDouble("Quantidade");
				String un = rs.getString("UN");
				double preco = rs.getDouble("Preco");
				double desconto = rs.getDouble("Desconto");
				double descontov = rs.getDouble("DescontoV");
				double valorliq = rs.getDouble("ValorLiq");
				double iva = rs.getDouble("Iva");
				double ivav = rs.getDouble("IvaV");
				String fornecedor = rs.getString("Fornecedor");
				String parcela = rs.getString("Parcela");
				if (item == 0) {
					int s = designacao.indexOf(" - ");
					if (s > 0)
						designacao = designacao.substring(0, s);
					designacao = designacao + " - " + fornecedor;
				}
				facturas.add(new Factura(No, data, invno, item, designacao,
						quantidade, un, preco, desconto, descontov, valorliq,
						iva, ivav, fornecedor, parcela));
				System.out.println(No + ", " + data + ", " + invno + ", "
						+ item + ", " + designacao + ", " + quantidade + ", "
						+ un + ", " + preco + ", " + iva + "," + fornecedor);
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		System.out.println("factura; " + facturas);
		return facturas;
	}

	public List<Factura> selectThisFactura(int no) {
		List<Factura> facturas = new ArrayList<>();
		// Step 1: Establishing a Connection
		try (Connection connection = getConnection();
		// Step 2:Create a statement using connection object
				PreparedStatement preparedStatement = connection
						.prepareStatement(SELECT_THIS_FACTURA);) {
			preparedStatement.setInt(1, no);
			preparedStatement.setInt(2, no);
			System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			ResultSet rs = preparedStatement.executeQuery();

			// Step 4: Process the ResultSet object.
			while (rs.next()) {
				int No = rs.getInt("No");
				String data = rs.getString("Date");
				String invno = rs.getString("InvNo");
				int item = rs.getInt("Item");
				String designacao = rs.getString("Designacao");
				double quantidade = rs.getDouble("Quantidade");
				String un = rs.getString("UN");
				double preco = rs.getDouble("Preco");
				double desconto = rs.getDouble("Desconto");
				double descontov = rs.getDouble("DescontoV");
				double valorliq = rs.getDouble("ValorLiq");
				double iva = rs.getDouble("Iva");
				double ivav = rs.getDouble("IvaV");
				String fornecedor = rs.getString("Fornecedor");
				String parcela = rs.getString("Parcela");
				if (item == 0) {
					int s = designacao.indexOf(" - ");
					if (s > 0)
						designacao = designacao.substring(0, s);
					designacao = designacao + " - " + fornecedor;
				}
				facturas.add(new Factura(No, data, invno, item, designacao,
						quantidade, un, preco, desconto, descontov, valorliq,
						iva, ivav, fornecedor, parcela));
				System.out.println(No + ", " + data + ", " + invno + ", "
						+ item + ", " + designacao + ", " + quantidade + ", "
						+ un + ", " + preco + ", " + iva + "," + fornecedor);
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		System.out.println("factura; " + facturas);
		return facturas;
	}

	public static int countFacturas(String invNo, int no) {

		int count = 0;
		// Step 1: Establishing a Connection
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection
						.prepareStatement(SELECT_COUNT_FACTURAS);) {

			preparedStatement.setString(1, invNo);
			preparedStatement.setInt(2, no);

			ResultSet rs = preparedStatement.executeQuery();

			rs.next();
			count = rs.getInt("count(*)");
		} catch (SQLException e) {
			printSQLException(e);
		}
		return count;
	}

	public List<Factura> selectAllFacturas(String data1, String data2,
			String designac, String fornecedor) {

		List<Factura> facturas = new ArrayList<>();
		// Step 1: Establishing a Connection
		try (Connection connection = getConnection();

		// Step 2:Create a statement using connection object
				PreparedStatement preparedStatement = connection
						.prepareStatement(SELECT_ALL_FACTURAS);) {
			String designa;
			if (designac == null || designac.isEmpty()
					|| designac.length() == 0)
				designa = "%";
			else
				designa = "%" + designac + "%";

			String fornecdor;
			if (fornecedor == null || fornecedor.isEmpty()
					|| fornecedor.length() == 0)
				fornecdor = "%";
			else
				fornecdor = "%" + fornecedor + "%";

			preparedStatement.setString(1, data1);
			preparedStatement.setString(2, data2);
			preparedStatement.setString(3, designa);
			preparedStatement.setString(4, fornecdor);

			System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			ResultSet rs = preparedStatement.executeQuery();

			// Step 4: Process the ResultSet object.
			while (rs.next()) {
				int no = rs.getInt("No");
				String data = rs.getString("Date");
				String invno = rs.getString("InvNo");
				int item = rs.getInt("Item");
				String designacao = rs.getString("Designacao");
				double quantidade = rs.getDouble("Quantidade");
				String un = rs.getString("UN");
				double preco = rs.getDouble("Preco");
				double desc = rs.getDouble("Desconto");
				double descv = rs.getDouble("DescontoV");
				double valorliq = rs.getDouble("ValorLiq");
				double iva = rs.getDouble("Iva");
				double ivav = rs.getDouble("IvaV");
				fornecdor = rs.getString("Fornecedor");
				String parcela = rs.getString("Parcela");
				if (item == 0) {
					int s = designacao.indexOf(" - ");
					if (s > 0) {
						designacao = designacao.substring(0, s);
					}
					if (fornecdor.length() > 0) {
						designacao = designacao + " - " + fornecdor;
					}
				}
				facturas.add(new Factura(no, data, invno, item, designacao,
						quantidade, un, preco, desc, descv, valorliq, iva,
						ivav, fornecdor, parcela));
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return facturas;
	}

	public int deleteFactura(int id, String data, String invno, String item)
			throws SQLException {
		PreparedStatement statement = null;
		Connection connection = getConnection();

		if (item.equalsIgnoreCase("0") || item.equalsIgnoreCase("00")) {
			statement = connection.prepareStatement(DELETE_FACTURAS_SQL);
			statement.setString(1, data);
			statement.setString(2, invno);
		} else {
			statement = connection.prepareStatement(DELETE_FACTURA_SQL);
			statement.setInt(1, id);
		}

		int rows = statement.executeUpdate();

		return rows;
	}

	public boolean updateFactura(Factura factura) throws SQLException {
		boolean rowUpdated;
		try (Connection connection = getConnection();
				PreparedStatement ps = connection
						.prepareStatement(UPDATE_FACTURAS_SQL);) {
			ps.setString(1, factura.getData());
			ps.setString(2, factura.getInvno());
			ps.setInt(3, factura.getItem());
			ps.setString(4, factura.getDesignacao());
			ps.setDouble(5, factura.getQuantidade());
			ps.setString(6, factura.getUn());
			ps.setDouble(7, factura.getPreco());
			ps.setDouble(8, factura.getDesconto());
			ps.setDouble(9, factura.getDescontov());
			ps.setDouble(10, factura.getValorliq());
			ps.setDouble(11, factura.getIva());
			ps.setDouble(12, factura.getIvav());
			ps.setString(13, factura.getFornecedor());
			ps.setString(14, factura.getParcela());
			ps.setInt(15, factura.getNo());

			System.out.println("----> updateFactura sql: " + ps);
			rowUpdated = ps.executeUpdate() > 0;
		}
		return rowUpdated;
	}

	public String minDate() throws SQLException {
		String date = null;
		try (Connection connection = getConnection();

		// Step 2:Create a statement using connection object
				PreparedStatement preparedStatement = connection
						.prepareStatement(SELECT_ALL_FACTURAS);) {
			System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			ResultSet rs = preparedStatement.executeQuery();

			// Step 4: Process the ResultSet object.
			while (rs.next()) {
				date = rs.getDate(1).toString();
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return date;
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

	public int deleteFactura(String invno, int no) {
		// TODO Auto-generated method stub
		int rows = 0;
		PreparedStatement statement = null;
		Connection connection = getConnection();
		try {
			statement = connection.prepareStatement(DELETE_NEW_FACTURA_SQL);
			statement.setString(1, invno);
			statement.setInt(2, no);

			System.out.println("## " + statement);
			rows = statement.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rows;
	}
}