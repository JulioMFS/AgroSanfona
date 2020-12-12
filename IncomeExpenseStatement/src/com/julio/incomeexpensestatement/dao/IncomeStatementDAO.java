package com.julio.incomeexpensestatement.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import com.julio.incomeexpensestatement.model.InOut;
import com.julio.incomeexpensestatement.model.IncExpClassificacao;
import com.julio.incomeexpensestatement.model.IncomeStatement;
import com.sun.javafx.collections.MappingChange.Map;

/**
 * AbstractDAO.java This DAO class provides CRUD database operations for the
 * table facturaelects in the database.
 * 
 * @author Ramesh Fadatare
 * 
 */
public class IncomeStatementDAO {
	private String jdbcURL = "jdbc:mysql://localhost:3306/agro?useSSL=false";
	private String jdbcUsername = "root";
	private String jdbcPassword = "j301052";
	private static String envirnmt = "";
	private static String env = "XXXX";

	private static String SELECT_INCOME = "select Classificacao, Descr, Seara, Parcela, sum(Montante)"
			+ " from bancoextracto "
			+ " where data_mov between ? and ? "
			+ "   and Classificacao not like '%Contra%'"
			+ " and Classificacao not like '%Transfer%'"
			+ " and Classificacao not like '%Levantamentos%'"
			+ " and Seara not like '%JMF%'" + " and Montante > 0"
			// + " group by Classificacao, Seara, Parcela"
			+ " order by Classificacao, Seara, Parcela";
	private static String SELECT_EXPENSES = "select Classificacao, Seara, Parcela, Montante"
			+ " from bancoextracto "
			+ " where data_mov between ? and ? "
			+ " and Classificacao not like '%Levantamentos%'"
			+ " and Seara not like '%JMF%'"
			+ " and Parcela not like '%JCC%'"
			+ " and Parcela not like '%JMF%'"
			// + " group by Classificacao, Seara, Parcela"
			+ " Union All"
			+ " select  Classificacao, Seara, Parcela, Amount * -1"
			+ "  from cashexpenses where date between ? and ? ";
	private static String SELECT_INOUT_OPENBAL = "SELECT Saldo -  montante as 'openBal' "
			+ "FROM bancoextracto where Data_mov between ? and ? "
			+ " and Banco = ? order by Data_mov, Data_valor, id limit 1";
	private static String SELECT_INOUT_CLOSEBAL = "SELECT Saldo as 'closeBal' "
			+ " FROM bancoextracto where Data_mov between ? and ? "
			+ " and Banco = ? order by Data_mov desc, Data_valor desc, id desc limit 1";

	// + " group by  Classificacao, Seara, Parcela";

	public IncomeStatementDAO() {
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

	public List<IncExpClassificacao> selectIncome(String data1, String data2) {
		SELECT_EXPENSES = SELECT_EXPENSES.replaceAll("XXXX", envirnmt);
		SortedMap<String, Double> income = new TreeMap<String, Double>();
		SortedMap<String, Double> expenses = new TreeMap<String, Double>();
		List<IncExpClassificacao> incExpClassificacao = new ArrayList<>();
		DecimalFormat df = new DecimalFormat("#,###,##0.00");

		int year1 = Integer.parseInt(data1.substring(0, 4));
		int year2 = Integer.parseInt(data2.substring(0, 4));

		// Get Income
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection
						.prepareStatement(SELECT_EXPENSES);) {
			preparedStatement.setString(1, data1);
			preparedStatement.setString(2, data2);
			preparedStatement.setString(3, data1);
			preparedStatement.setString(4, data2);

			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				String classificacao = rs.getString("Classificacao");
				if (classificacao.contains("##Contra")
						|| classificacao.contains("##contra"))
					classificacao = "##Contra";
				String seara = rs.getString("Seara");
				String parcela = rs.getString("Parcela");
				double montante = rs.getDouble("Montante");
				int searaYear = Integer.parseInt(extractInt(seara));
				if (searaYear == -1
						|| (searaYear >= year1 && searaYear <= year2)) {
					double amt = 0;
					if (montante > 0) {
						// update/put income
						if (income.containsKey(classificacao)) {
							amt = income.get(classificacao) + montante;
							income.replace(classificacao, amt);
						} else {
							income.put(classificacao, montante);
						}
					} else {
						// update/put expenses
						if (expenses.containsKey(classificacao)) {
							amt = expenses.get(classificacao) + montante;
							expenses.replace(classificacao, amt);
						} else {
							expenses.put(classificacao, montante);
						}
					}
				}
			}

			// Insert income list into IncExpClassificacao
			for (String i : income.keySet()) {
				double incMontante = income.get(i);
				double expMontante = 0, amt = 0;
				if (expenses.containsKey(i)) {
					expMontante = expenses.get(i);
				}
				String classificacao = i;
				amt = incMontante + expMontante;
				if (amt != 0) {
					if (incMontante != 0 && expMontante != 0) {
						classificacao = classificacao + " ("
								+ df.format(incMontante) + " + "
								+ df.format(expMontante) + ")";
					}
					if (amt > 0) {
						incMontante = amt;
						expMontante = 0;
					} else {
						incMontante = 0;
						expMontante = amt;
					}
					expenses.remove(i);
					incExpClassificacao.add(new IncExpClassificacao(
							classificacao, incMontante, expMontante));

				} else {
					expenses.remove(i);
				}
			}
			// Insert remaining expenses list into IncExpClassificacao
			for (String i : expenses.keySet()) {
				double expMontante = expenses.get(i);
				incExpClassificacao.add(new IncExpClassificacao(i, 0,
						expMontante));

			}

		} catch (SQLException e) {
			printSQLException(e);
		}

		// Displaying the values
		// after iterating through the list
		System.out.println("\nThe iterator values" + " of list are: ");
		for (int i = 0; i < incExpClassificacao.size(); i++) {
			IncExpClassificacao IncExp = incExpClassificacao.get(i);
			String classificacao = IncExp.getClassificacao();
			double incMontante = IncExp.getIncMontante();
			double expMontante = IncExp.getExpMontante();

			System.out.println(classificacao + "\t\t" + incMontante + "\t\t"
					+ expMontante);

		}

		return incExpClassificacao;
	}

	public List<InOut> selectInOut(String data1, String data2) {

		List<InOut> inOut = new ArrayList<>();
		DecimalFormat df = new DecimalFormat("#,###,##0.00");

		int year1 = Integer.parseInt(data1.substring(0, 4));
		int year2 = Integer.parseInt(data2.substring(0, 4));
		double openBalCGD = 0;
		double openBalCA = 0;
		double openBal = 0;
		double closeBalCGD = 0;
		double closeBalCA = 0;
		double closeBal = 0;

		// Get Open Bal CGD
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection
						.prepareStatement(SELECT_INOUT_OPENBAL);) {
			preparedStatement.setString(1, data1);
			preparedStatement.setString(2, data2);
			preparedStatement.setString(3, "CGD");
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			rs.next();
			openBalCGD = rs.getDouble("openBal");

		} catch (SQLException e) {
			printSQLException(e);
		}

		// Get Open Bal CA
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection
						.prepareStatement(SELECT_INOUT_OPENBAL);) {
			preparedStatement.setString(1, data1);
			preparedStatement.setString(2, data2);
			preparedStatement.setString(3, "CA");
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			rs.next();
			openBalCA = rs.getDouble("openBal");

		} catch (SQLException e) {
			printSQLException(e);
		}
		openBal = openBalCGD + openBalCA;

		// Get Close Bal CGD
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection
						.prepareStatement(SELECT_INOUT_CLOSEBAL);) {
			preparedStatement.setString(1, data1);
			preparedStatement.setString(2, data2);
			preparedStatement.setString(3, "CGD");
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			rs.next();
			closeBalCGD = rs.getDouble("closeBal");

		} catch (SQLException e) {
			printSQLException(e);
		}

		// Get Close Bal CA
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection
						.prepareStatement(SELECT_INOUT_CLOSEBAL);) {
			preparedStatement.setString(1, data1);
			preparedStatement.setString(2, data2);
			preparedStatement.setString(3, "CA");
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			rs.next();
			closeBalCA = rs.getDouble("closeBal");

		} catch (SQLException e) {
			printSQLException(e);
		}
		closeBal = closeBalCGD + closeBalCA;

		String desc = "Saldo Inicial (" + df.format(openBalCGD) + " + "
				+ df.format(openBalCA) + ")";
		inOut.add(new InOut(desc, openBal));

		desc = "Saldo Final (" + df.format(closeBalCGD) + " + "
				+ df.format(closeBalCA) + ")";
		inOut.add(new InOut(desc, closeBal));

		desc = "Entradas/SaidaS (" + df.format(closeBalCGD - openBalCGD)
				+ " + " + df.format(closeBalCA - openBalCA) + ")";
		inOut.add(new InOut(desc, closeBal - openBal));

		return inOut;
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

	static String extractInt(String str) {
		// Replacing every non-digit number
		// with a space(" ")
		str = str.replaceAll("[^\\d]", " ");

		// Remove extra spaces from the beginning
		// and the ending of the string
		str = str.trim();

		// Replace all the consecutive white
		// spaces with a single space
		str = str.replaceAll(" +", " ");

		if (str.equals(""))
			return "-1";

		return str;
	}

}