package com.julio.contadorelect.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.julio.contadorelect.model.IncomeStatement;
import com.julio.contadorelect.model.ElectPrecos;
import com.julio.contadorelect.model.TableRows;

/**
 * AbstractDAO.java This DAO class provides CRUD database operations for the
 * table facturaelects in the database.
 * 
 * @author Ramesh Fadatare
 * 
 */
public class ContadorElectDAO {
	private String jdbcURL = "jdbc:mysql://localhost:3306/agro?useSSL=false";
	private String jdbcUsername = "root";
	private String jdbcPassword = "j301052";
	private static String env = "XXXX";
	private static String envirnmt = "";

	private static String INSERT_CONTADORELECT_SQL = "INSERT INTO "
			+ env
			+ ".contadorelect"
			+ " (Fatura, Nome, Data1, Data2, LeituraData, Est, Vazio, Ponta, Cheia, foraVazio, Valor, Companhia) VALUES "
			+ " (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

	private static final String SELECT_CONTADORELECT_BY_ID = "select id, Fatura, Nome, Data1, Data2, LeituraData, Est,"
			+ " Vazio, Ponta, Cheia, foraVazio, Valor"
			+ "  from "
			+ env
			+ ".contadorelect where id =?";
	private static String SELECT_ALL_CONTADORELECT = "select * from " + env
			+ ".contadorelect" + " where Data1 between ? and ? and Nome like ?";
	private static String DELETE_CONTADORELECT_SQL = "delete from " + env
			+ ".contadorelect where id = ?;";
	private static String UPDATE_CONTADORELECT_SQL = "update "
			+ env
			+ ".contadorelect set Fatura = ?, Nome= ?, Data1 =?,"
			+ " Data2 = ?, LeituraData= ?, Est =?, Vazio =?, Ponta = ?, Cheia=?, foraVazio=?, Valor=?  where id = ?;";
	private static final String SELECT_MIN_DATE = "SELECT MIN(Data1) FROM "
			+ env + ".contadorelect";
	private static final String SELECT_MAX_DATE = "SELECT MAX(Data1) FROM "
			+ env + ".contadorelect";
	private static String SELECT_CONTADORELECT = "select id, Fatura, Nome, Data1, Data2, LeituraData,"
			+ " Est, Vazio, Ponta, Cheia, foraVazio, Valor, Companhia"
			+ " from " + env + ".contadorelect where id = ?";
	private static String SELECT_LATEST_PRECOS = "select  a.Companhia, a.Data, Simples,  "
			+ "	VazioBi, ForaVazioBi, DescBi, VazioTri, PontaTri, CheiasTri,  DescTri, a.Iva, b.Tipo, "
			+ " c.potencia, c.preco, c.Desc, c.Iva"
			+ "	 from   "
			+ env
			+ ".electricidadeprecos a "
			+ "        ,"
			+ env
			+ ".Parcela b "
			+ "        ,"
			+ env
			+ ".potenciatbl c"
			+ "      where a.Companhia = b.Companhia "
			+ "        and a.Companhia = c.Companhia "
			+ "        and b.Nome = ? "
			+ "        and c.Data <= ? "
			+ "        and c.potencia = b.potencia "
			+ "        and c.potencia = a.potencia "
			+ "	 order by a.Data Desc Limit 1";

	public ContadorElectDAO() {
		String currentDirectory = System.getProperty("user.dir");
		if (currentDirectory.indexOf("Apache") > 0
				|| currentDirectory.indexOf("apache") > 0
				|| currentDirectory.indexOf("pi") > 0)
			envirnmt = "agro";
		else
			envirnmt = "test";

		System.out.println("user.dir: " + currentDirectory + "\nenvironment: "
				+ env);
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

	public void insertContadorElect(IncomeStatement contadorelect)
			throws SQLException {
		INSERT_CONTADORELECT_SQL = INSERT_CONTADORELECT_SQL.replaceAll("XXXX",
				envirnmt);
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection
						.prepareStatement(INSERT_CONTADORELECT_SQL)) {
			// preparedStatement.setInt(1, contadorelect.getId());
			preparedStatement.setString(1, contadorelect.getFatura());
			preparedStatement.setString(2, contadorelect.getParcela());
			preparedStatement.setString(3, contadorelect.getData1());
			preparedStatement.setString(4, contadorelect.getData2());
			preparedStatement.setString(5, contadorelect.getLeituradata());
			preparedStatement.setString(6, contadorelect.getEst());
			preparedStatement.setInt(7, contadorelect.getVazio());
			preparedStatement.setInt(8, contadorelect.getPonta());
			preparedStatement.setInt(9, contadorelect.getCheia());
			preparedStatement.setInt(10, contadorelect.getForavazio());
			preparedStatement.setDouble(11, contadorelect.getValor());
			preparedStatement.setString(12, contadorelect.getCompanhia());

			System.out.println(preparedStatement);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			printSQLException(e);
		}
	}

	public List<IncomeStatement> selectContadorElect(int idd) {
		SELECT_CONTADORELECT = SELECT_CONTADORELECT
				.replaceAll("XXXX", envirnmt);
		List<IncomeStatement> facturaelects = new ArrayList<>();
		// Step 1: Establishing a Connection
		try (Connection connection = getConnection();
		// Step 2:Create a statement using connection object
				PreparedStatement preparedStatement = connection
						.prepareStatement(SELECT_CONTADORELECT);) {
			preparedStatement.setInt(1, idd);
			// System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			ResultSet rs = preparedStatement.executeQuery();

			// Step 4: Process the ResultSet object.
			while (rs.next()) {
				int id = rs.getInt("id");
				String fatura = rs.getString("Fatura");
				String nome = rs.getString("Nome");
				String data1 = rs.getString("Data1");
				String data2 = rs.getString("Data2");
				String leituradata = rs.getString("LeituraData");
				String est = rs.getString("Est");
				int vazio = rs.getInt("Vazio");
				int ponta = rs.getInt("Ponta");
				int cheia = rs.getInt("Cheia");
				int foravazio = rs.getInt("foraVazio");
				double valor = rs.getDouble("Valor");
				String companhia = rs.getString("Companhia");
				facturaelects.add(new IncomeStatement(id, fatura, nome, data1,
						data2, leituradata, est, vazio, ponta, cheia,
						foravazio, valor, companhia));
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		// System.out.println("facturaelect; " + facturaelects);
		return facturaelects;
	}

	public List<IncomeStatement> selectAllContadorElect(String data1,
			String data2, String nome1) {

		SELECT_ALL_CONTADORELECT = SELECT_ALL_CONTADORELECT.replaceAll("XXXX",
				envirnmt);
		List<IncomeStatement> contadorelect = new ArrayList<>();
		// Step 1: Establishing a Connection
		try (Connection connection = getConnection();

		// Step 2:Create a statement using connection object
				PreparedStatement preparedStatement = connection
						.prepareStatement(SELECT_ALL_CONTADORELECT);) {
			String nome;
			if (nome1.isEmpty() || nome1.length() == 0)
				nome = "%";
			else
				nome = "%" + nome1 + "%";
			preparedStatement.setString(1, data1);
			preparedStatement.setString(2, data2);
			preparedStatement.setString(3, nome);

			System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			ResultSet rs = preparedStatement.executeQuery();

			// Step 4: Process the ResultSet object.
			while (rs.next()) {
				int id = rs.getInt("id");
				String fatura = rs.getString("Fatura");
				String nome02 = rs.getString("Nome");
				String data01 = rs.getString("Data1");
				String data02 = rs.getString("Data2");
				String leituradata = rs.getString("LeituraData");
				String est = rs.getString("Est");
				int vazio = rs.getInt("Vazio");
				int ponta = rs.getInt("Ponta");
				int cheia = rs.getInt("Cheia");
				int foravazio = rs.getInt("foraVazio");
				double valor = rs.getDouble("Valor");
				String companhia = rs.getString("Companhia");
				contadorelect.add(new IncomeStatement(id, fatura, nome02,
						data01, data02, leituradata, est, vazio, ponta, cheia,
						foravazio, valor, companhia));
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return contadorelect;
	}

	public List<ElectPrecos> getElectPrecos(String parcela, String leituradata) {

		SELECT_LATEST_PRECOS = SELECT_LATEST_PRECOS
				.replaceAll("XXXX", envirnmt);
		List<ElectPrecos> electprecos = new ArrayList<>();
		// Step 1: Establishing a Connection
		try (Connection connection = getConnection();

		// Step 2:Create a statement using connection object
				PreparedStatement preparedStatement = connection
						.prepareStatement(SELECT_LATEST_PRECOS);) {
			String nome;

			preparedStatement.setString(1, parcela);
			preparedStatement.setString(2, leituradata);

			System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			ResultSet rs = preparedStatement.executeQuery();

			// Step 4: Process the ResultSet object.
			while (rs.next()) {
				String companhia = rs.getString("Companhia");
				String data = rs.getString("Data");
				double simples = rs.getDouble("Simples");
				double vaziobi = rs.getDouble("VazioBi");
				double foravaziobi = rs.getDouble("ForaVazioBi");
				double descbi = rs.getDouble("DescBi");
				double vaziotri = rs.getDouble("VazioTri");
				double pontatri = rs.getDouble("PontaTri");
				double cheiastri = rs.getDouble("CheiasTri");
				double desctri = rs.getDouble("DescTri");
				double iva = rs.getDouble("Iva");
				int tipo = rs.getInt("Tipo");
				double potencia = rs.getDouble("Potencia");
				double preco = rs.getDouble("Preco");
				double potenciaDesc = rs.getDouble("Desc");
				double potenciaIva = rs.getDouble("Iva");
				electprecos.add(new ElectPrecos(companhia, data, simples,
						vaziobi, foravaziobi, descbi, vaziotri, pontatri,
						cheiastri, desctri, iva, tipo, potencia, preco,
						potenciaDesc, potenciaIva));
				System.out.println("----------> getElectPrecos: " + parcela
						+ " | " + leituradata + " | " + simples + " | "
						+ vaziobi + " | " + foravaziobi + " | " + descbi
						+ " | " + vaziotri + " | " + pontatri + " | "
						+ cheiastri + " | " + desctri + " | " + iva + " | "
						+ potencia + " | " + preco);
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return electprecos;
	}

	public List<TableRows> getTableRows(int tipo, String companhia,
			List<ElectPrecos> electprecos, int vazio, int foraVazio, int ponta,
			int cheias) {
		ElectPrecos ep = electprecos.get(0);
		List<TableRows> tablerows = new ArrayList<>();
		String descr = "";
		double preco = 0;
		double desc = 0;
		double iva = 0;
		double potencia = 0;
		double potenciaPreco = 0;
		double potenciaDesc = 0;
		double potenciaIva = 0;
		int quant = 0;
		if (companhia.equalsIgnoreCase("Nabalia")) {
			tablerows.add(new TableRows("Juros de Mora", 0, 3.70, 0, 0, tipo));
		}
		potencia = ep.getPotencia();
		potenciaPreco = ep.getPreco();
		potenciaDesc = ep.getPotenciaDesc();
		potenciaIva = ep.getPotenciaIva();
		if (tipo == 1) {
			String f = "";
			tablerows.add(new TableRows(f, 0, potenciaPreco, potenciaDesc,
					potenciaIva, 0));
		} else {
			String f = "Potencia contratada (" + potencia + " kva)";
			tablerows.add(new TableRows(f, 0, potenciaPreco, potenciaDesc,
					potenciaIva, 0));
		}
		for (int i = 0; i < tipo; i++) {
			descr = "unknown";
			preco = 0;
			desc = 0;
			iva = 0;
			quant = 0;
			if (tipo == 1) {
				descr = "Simples";
				preco = ep.getSimples();
				iva = ep.getIva();
				quant = ponta;
			} else if (tipo == 2) {
				if (i == 0) {
					descr = "Fora Vazio";
					preco = ep.getForaVazioBi();
					quant = foraVazio;
				} else {
					descr = "Vazio";
					preco = ep.getVazioBi();
					quant = vazio;
				}
				desc = ep.getDescBi();
				iva = ep.getIva();
			} else if (tipo == 3) {
				if (i == 0) {
					descr = "Ponta";
					preco = ep.getPontaTri();
					quant = ponta;
				} else if (i == 1) {
					descr = "Cheias";
					preco = ep.getCheiasTri();
					quant = cheias;
				} else {
					descr = "Vazio";
					preco = ep.getVazioTri();
					quant = vazio;
				}
				desc = ep.getDescTri();
				iva = ep.getIva();
			}
			String description = descr;
			if (tipo == 1)
				description = descr;
			if (companhia.equalsIgnoreCase("Nabalia")
					|| companhia.equalsIgnoreCase("EDP")) {
				description = descr + " medido";
			}
			System.out.println("TableRows: " + " | " + descr + " | " + preco
					+ " | " + desc + " | " + iva + " | " + tipo);

			tablerows.add(new TableRows(description, quant, preco, desc, iva,
					tipo));
			if (companhia.equalsIgnoreCase("Nabalia")
					|| companhia.equalsIgnoreCase("EDP")) {
				description = descr + " estimado";
				tablerows.add(new TableRows(description, quant, preco, desc,
						iva, tipo));

			}

		}
		tablerows.add(new TableRows("Gestão Energética", 0, 3.99, 0, 23, tipo));
		tablerows.add(new TableRows("CAV", 0, 2.85, 0, 6, tipo));
		tablerows.add(new TableRows("IEC Medido", 0, .001, 0, 23, tipo));
		tablerows.add(new TableRows("IEC Estimado", 0, .001, 0, 23, tipo));
		tablerows.add(new TableRows("Taxa DGEG", 0, .35, 0, 23, tipo));

		return tablerows;
	}

	public boolean deleteContadorElect(int id) throws SQLException {
		DELETE_CONTADORELECT_SQL = DELETE_CONTADORELECT_SQL.replaceAll("XXXX",
				envirnmt);
		boolean rowDeleted;
		try (Connection connection = getConnection();
				PreparedStatement statement = connection
						.prepareStatement(DELETE_CONTADORELECT_SQL);) {
			statement.setInt(1, id);
			rowDeleted = statement.executeUpdate() > 0;
		}
		return rowDeleted;
	}

	public boolean updateContadorElect(IncomeStatement facturaelect)
			throws SQLException {
		UPDATE_CONTADORELECT_SQL = UPDATE_CONTADORELECT_SQL.replaceAll("XXXX",
				envirnmt);
		boolean rowUpdated;
		try (Connection connection = getConnection();
				PreparedStatement ps = connection
						.prepareStatement(UPDATE_CONTADORELECT_SQL);) {
			ps.setString(1, facturaelect.getFatura());
			ps.setString(2, facturaelect.getParcela());
			ps.setString(3, facturaelect.getData1());
			ps.setString(4, facturaelect.getData2());
			ps.setString(5, facturaelect.getLeituradata());
			ps.setString(6, facturaelect.getEst());
			ps.setInt(7, facturaelect.getVazio());
			ps.setInt(8, facturaelect.getPonta());
			ps.setInt(9, facturaelect.getCheia());
			ps.setInt(10, facturaelect.getForavazio());
			ps.setDouble(11, facturaelect.getValor());
			ps.setInt(12, facturaelect.getId());
			System.out.println("----> updateFacturaElect sql: " + ps);
			rowUpdated = ps.executeUpdate() > 0;
		}
		return rowUpdated;
	}

	public String minDate() throws SQLException {
		SELECT_ALL_CONTADORELECT = SELECT_ALL_CONTADORELECT.replaceAll("XXXX",
				envirnmt);
		String date = null;
		try (Connection connection = getConnection();

		// Step 2:Create a statement using connection object
				PreparedStatement preparedStatement = connection
						.prepareStatement(SELECT_ALL_CONTADORELECT);) {
			// System.out.println(preparedStatement);
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