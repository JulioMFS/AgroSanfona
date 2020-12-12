package com.julio.incomeexpensestatement.model;

/**
 * User.java This is a model class represents a User entity
 * 
 * @author Ramesh Fadatare
 * 
 */
public class IncExpClassificacao {

	public IncExpClassificacao(String classificacao, double incMontante,
			double expMontante) {
		super();
		this.classificacao = classificacao;
		this.incMontante = incMontante;
		this.expMontante = expMontante;
	}
	protected String classificacao;
	protected double incMontante;
	protected double expMontante;
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
	 * @return the incMontante
	 */
	public double getIncMontante() {
		return incMontante;
	}
	/**
	 * @param incMontante the incMontante to set
	 */
	public void setIncMontante(double incMontante) {
		this.incMontante = incMontante;
	}
	/**
	 * @return the expMontante
	 */
	public double getExpMontante() {
		return expMontante;
	}
	/**
	 * @param expMontante the expMontante to set
	 */
	public void setExpMontante(double expMontante) {
		this.expMontante = expMontante;
	}

}