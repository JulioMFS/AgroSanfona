package com.julio.bancoextracto.model;

public class Classificacao {
	protected int id;
	protected String classificacao;

	public Classificacao(int id, String classific) {
		super();
		this.id = id;
		this.classificacao = classific;
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the Classificacao
	 */
	public String getClassificacao() {
		return classificacao;
	}

	/**
	 * @param classificacao
	 *            the Classificacao to set
	 */
	public void setClassificacao(String classificacao) {
		this.classificacao = classificacao;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
}
