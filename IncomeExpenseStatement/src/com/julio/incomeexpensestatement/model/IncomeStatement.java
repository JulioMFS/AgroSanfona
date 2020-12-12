package com.julio.incomeexpensestatement.model;

/**
 * User.java This is a model class represents a User entity
 * 
 * @author Ramesh Fadatare
 * 
 */
public class IncomeStatement {

	public IncomeStatement(String classificacao, String descr,
			String seara, String parcela, double montante,
			double total, double gtotal) {
		super();
		this.classificacao = classificacao;
		this.descr = descr;
		this.seara = seara;
		this.parcela = parcela;
		this.montante = montante;
		this.total = total;
		this.gtotal = gtotal;
	}

	public IncomeStatement() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected String classificacao;
	protected String descr;
	protected String seara;
	protected String parcela;
	protected double montante;
	protected double total;
	protected double gtotal;
	/**
	 * @return the classificacao
	 */
	public String getClassificacao() {
		return classificacao;
	}

	/**
	 * @param classificacao the classificacao to set
	 */
	public void setClassificacao(String classificacao) {
		this.classificacao = classificacao;
	}

	/**
	 * @return the descr
	 */
	public String getDescr() {
		return descr;
	}

	/**
	 * @param descr the descr to set
	 */
	public void setDescr(String descr) {
		this.descr = descr;
	}

	/**
	 * @return the seara
	 */
	public String getSeara() {
		return seara;
	}

	/**
	 * @param seara the seara to set
	 */
	public void setSeara(String seara) {
		this.seara = seara;
	}

	/**
	 * @return the parcela
	 */
	public String getParcela() {
		return parcela;
	}

	/**
	 * @param parcela the parcela to set
	 */
	public void setParcela(String parcela) {
		this.parcela = parcela;
	}

	/**
	 * @return the montante
	 */
	public double getMontante() {
		return montante;
	}

	/**
	 * @param montante the montante to set
	 */
	public void setMontante(double montante) {
		this.montante = montante;
	}

	/**
	 * @return the total
	 */
	public double getTotal() {
		return total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(double total) {
		this.total = total;
	}

	/**
	 * @return the gtotal
	 */
	public double getGtotal() {
		return gtotal;
	}

	/**
	 * @param gtotal the gtotal to set
	 */
	public void setGtotal(double gtotal) {
		this.gtotal = gtotal;
	}

	/**
	 * @return the incClassificacao
	 */

}