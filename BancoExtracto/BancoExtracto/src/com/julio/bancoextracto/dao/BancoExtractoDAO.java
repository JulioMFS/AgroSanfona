package com.julio.bancoextracto.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.julio.bancoextracto.model.BancoExtracto;
import com.julio.bancoextracto.model.Classificacao;
import com.julio.bancoextracto.model.ContraFactura;
import com.julio.bancoextracto.model.Descr;
import com.julio.bancoextracto.model.Parcela;
import com.julio.bancoextracto.model.Seara;

/**
 * AbstractDAO.java This DAO class provides CRUD database operations for the
 * table bancoextractos in the database.
 * 
 * @author Ramesh Fadatare
 * 
 */
public class BancoExtractoDAO {
	private static String envirnmt = "xxxx";
	private static String jdbcURL = "jdbc:mysql://localhost:3306/" + envirnmt
			+ "?useSSL=false";
	private static String jdbcUsername = "root";
	private static String jdbcPassword = "j301052";
	private static final String INSERT_BANCOEXTRACTO_SQL = "INSERT INTO bancoextracto"
			+ "  (Data_mov, Data_valor, Descricao, Montante, Saldo, Classificacao, Descr,"
			+ " Parcela, Seara, Banco) VALUES "
			+ " (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

	private static final String SELECT_BANCOEXTRACTO_BY_ID = "select ID, Data_mov, Data_valor,"
			+ " Descricao, Montante, Saldo, Classificacao, Descr, Parcela, Seara, Banco"
			+ " from bancoextracto where id =?";
	private static final String SELECT_ALL_BANCOEXTRACTO = "select * from bancoextracto"
			+ " where Data_mov between ? and ? and Descricao like ? and Classificacao like ? "
			+ "and Descr like ? and Parcela like ? and Seara like ? order by Data_mov, ID";
	private static final String DELETE_BANCOEXTRACTO_SQL = "delete from bancoextracto where id = ?;";
	private static final String UPDATE_BANCOEXTRACTO_SQL = "update bancoextracto set Data_mov = ?, "
			+ "Data_valor = ?, Descricao = ?, Montante = ?, Saldo = ?, Classificacao = ?, Descr = ?, "
			+ "Parcela = ?, Seara  = ?, Banco = ? where id = ?;";
	private static String SELECT_ALL_DESCR = "select id, Descr from test.desc order by Descr";
	private static final String SELECT_ALL_CLASSIFICACAO = "select id, Classificacao from classificacao order by Classificacao";
	private static final String SELECT_ALL_PARCELA = "select ID, Nome from parcela order by Nome";
	private static final String SELECT_ALL_SEARA = "select Seara from seara order by Seara";
	private static final String SELECT_BANCOEXTRACTO = "select count(*) from bancoextracto"
			+ " where Data_mov = ? and Descricao = ? and Classificacao = ? and Montante = ?";
	private static String SELECT_FACTURA_LINE = "SELECT InvNo, ValorLiq + IvaV as Valor, Parcela FROM facturas "
			+ " where date <= ? and item != '00' and InvNo = "
			+ "(SELECT InvNo FROM facturas where date <= ? and item = '00' and valorLiq = ?)";

	public BancoExtractoDAO() {
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

	public void insertBancoExtracto(BancoExtracto bancoextracto)
			throws SQLException {
		System.out.println(INSERT_BANCOEXTRACTO_SQL);
		// try-with-resource statement will auto close the connection.
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection
						.prepareStatement(INSERT_BANCOEXTRACTO_SQL)) {
			preparedStatement.setString(1, bancoextracto.getDataMov());
			preparedStatement.setString(2, bancoextracto.getDataValor());
			preparedStatement.setString(3, bancoextracto.getDescricao());
			preparedStatement.setDouble(4, bancoextracto.getMontante());
			preparedStatement.setDouble(5, bancoextracto.getSaldo());
			preparedStatement.setString(6, bancoextracto.getClassificacao());
			preparedStatement.setString(7, bancoextracto.getDescr());
			preparedStatement.setString(8, bancoextracto.getParcela());
			preparedStatement.setString(9, bancoextracto.getSeara());
			preparedStatement.setString(10, bancoextracto.getBanco());

			System.out.println(preparedStatement);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			printSQLException(e);
		}
	}

	public BancoExtracto selectBancoExtracto(int id) {
		BancoExtracto bancoextracto = null;
		// Step 1: Establishing a Connection
		try (Connection connection = getConnection();
		// Step 2:Create a statement using connection object
				PreparedStatement preparedStatement = connection
						.prepareStatement(SELECT_BANCOEXTRACTO_BY_ID);) {
			preparedStatement.setInt(1, id);
			// Step 3: Execute the query or update query
			ResultSet rs = preparedStatement.executeQuery();

			// Step 4: Process the ResultSet object.
			while (rs.next()) {
				String dataMov = rs.getString("Data_mov");
				String dataValor = rs.getString("Data_Valor");
				String descricao = rs.getString("Descricao");
				double montante = rs.getDouble("Montante");
				double saldo = rs.getDouble("Saldo");
				String classificacao = rs.getString("Classificacao");
				String descr = rs.getString("Descr");
				String parcela = rs.getString("Parcela");
				String seara = rs.getString("Seara");
				String banco = rs.getString("Banco");
				id = rs.getInt("ID");

				bancoextracto = new BancoExtracto(id, dataMov, dataValor,
						descricao, montante, saldo, classificacao, descr,
						parcela, seara, banco);
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return bancoextracto;
	}

	public static List<ContraFactura> selectFactura(String dataMov, double valor) {
		List<ContraFactura> facturas = new ArrayList<>();
		// Step 1: Establishing a Connection
		try (Connection connection = getConnection();
		// Step 2:Create a statement using connection object
				PreparedStatement preparedStatement = connection
						.prepareStatement(SELECT_FACTURA_LINE);) {
			preparedStatement.setString(1, dataMov);
			preparedStatement.setString(2, dataMov);
			preparedStatement.setDouble(3, valor);

			// Step 3: Execute the query or update query
			ResultSet rs = preparedStatement.executeQuery();

			// Step 4: Process the ResultSet object.
			while (rs.next()) {
				String invno = rs.getString("InvNo");
				double valorliq = rs.getDouble("valor");
				String parcela = rs.getString("Parcela");

				facturas.add(new ContraFactura(invno, valorliq, parcela));
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return facturas;
	}

	public List<BancoExtracto> selectAllBancoExtracto(String data1,
			String data2, String descrica, String classificaca, String desc,
			String parcel, String sear) {

		// using try-with-resources to avoid closing resources (boiler plate
		// code)
		List<BancoExtracto> bancoextracto = new ArrayList<>();
		// Step 1: Establishing a Connection
		try (Connection connection = getConnection();

		// Step 2:Create a statement using connection object
				PreparedStatement preparedStatement = connection
						.prepareStatement(SELECT_ALL_BANCOEXTRACTO);) {
			String descricao = "%";
			if (descrica != null && !descrica.isEmpty())
				descricao = "%" + descrica + "%";
			String classificacao = "%";
			if (classificaca != null && !classificaca.isEmpty())
				classificacao = "%" + classificaca + "%";
			String descr = "%";
			if (desc != null && !desc.isEmpty())
				descr = "%" + desc + "%";
			String parcela = "%";
			if (parcel != null && !parcel.isEmpty())
				parcela = "%" + parcel + "%";
			String seara = "%";
			if (sear != null && !sear.isEmpty())
				seara = "%" + sear + "%";

			preparedStatement.setString(1, data1);
			preparedStatement.setString(2, data2);
			preparedStatement.setString(3, descricao);
			preparedStatement.setString(4, classificacao);
			preparedStatement.setString(5, descr);
			preparedStatement.setString(6, parcela);
			preparedStatement.setString(7, seara);

			System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			ResultSet rs = preparedStatement.executeQuery();

			// Step 4: Process the ResultSet object.
			while (rs.next()) {
				int id = rs.getInt("ID");
				String dataMov = rs.getString("Data_mov");
				String dataValor = rs.getString("Data_Valor");
				descricao = rs.getString("Descricao");
				double montante = rs.getDouble("Montante");
				double saldo = rs.getDouble("Saldo");
				classificacao = rs.getString("Classificacao");
				descr = rs.getString("Descr");
				parcela = rs.getString("Parcela");
				seara = rs.getString("Seara");
				String banco = rs.getString("Banco");

				bancoextracto.add(new BancoExtracto(id, dataMov, dataValor,
						descricao, montante, saldo, classificacao, descr,
						parcela, seara, banco));
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return bancoextracto;
	}

	public boolean deleteBancoExtracto(int id) throws SQLException {
		boolean rowDeleted;
		try (Connection connection = getConnection();
				PreparedStatement statement = connection
						.prepareStatement(DELETE_BANCOEXTRACTO_SQL);) {
			statement.setInt(1, id);
			rowDeleted = statement.executeUpdate() > 0;
		}
		return rowDeleted;
	}

	public boolean updateBancoExtracto(BancoExtracto bancoextracto)
			throws SQLException {
		boolean rowUpdated;
		try (Connection connection = getConnection();
				PreparedStatement statement = connection
						.prepareStatement(UPDATE_BANCOEXTRACTO_SQL);) {
			statement.setString(1, bancoextracto.getDataMov());
			statement.setString(2, bancoextracto.getDataValor());
			statement.setString(3, bancoextracto.getDescricao());
			statement.setDouble(4, bancoextracto.getMontante());
			statement.setDouble(5, bancoextracto.getSaldo());
			statement.setString(6, bancoextracto.getClassificacao());
			statement.setString(7, bancoextracto.getDescr());
			statement.setString(8, bancoextracto.getParcela());
			statement.setString(9, bancoextracto.getSeara());
			statement.setString(10, bancoextracto.getBanco());
			statement.setInt(11, bancoextracto.getId());

			rowUpdated = statement.executeUpdate() > 0;
		}
		return rowUpdated;
	}

	public boolean recordExists(String dataMov, String descricao,
			String classificacao, double montante) {
		boolean recExists = false;
		try (Connection connection = getConnection();
				PreparedStatement ps = connection
						.prepareStatement(SELECT_BANCOEXTRACTO);) {
			ps.setString(1, dataMov);
			ps.setString(2, descricao);
			ps.setString(3, classificacao);
			ps.setDouble(4, montante);
			ResultSet rs = ps.executeQuery();
			rs.next();
			int count = rs.getInt("count(*)");
			if (count > 0)
				recExists = true;

		} catch (SQLException e) {
			printSQLException(e);
		}
		return recExists;
	}

	public List<Descr> getDescr() {
		List<Descr> descr = new ArrayList<>();
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection
						.prepareStatement(SELECT_ALL_DESCR);) {
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String desc = rs.getString("Descr");
				descr.add(new Descr(id, desc));
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return descr;
	}

	public List<Classificacao> getClassificacao() {
		List<Classificacao> classificacao = new ArrayList<>();
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection
						.prepareStatement(SELECT_ALL_CLASSIFICACAO);) {
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String classific = rs.getString("Classificacao");
				classificacao.add(new Classificacao(id, classific));
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return classificacao;
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

	public List<Seara> getSeara() {
		List<Seara> seara = new ArrayList<>();
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection
						.prepareStatement(SELECT_ALL_SEARA);) {
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				String searaa = rs.getString("Seara");
				seara.add(new Seara(searaa));
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return seara;
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
