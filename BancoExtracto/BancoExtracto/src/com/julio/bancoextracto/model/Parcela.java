package com.julio.bancoextracto.model;

public class Parcela {

	protected int ID;
	protected String nome;

	public Parcela() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Parcela(int iD, String nome) {
		super();
		ID = iD;
		this.nome = nome;
	}

	/**
	 * @return the iD
	 */
	public int getID() {
		return ID;
	}

	/**
	 * @param iD
	 *            the iD to set
	 */
	public void setID(int iD) {
		ID = iD;
	}

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome
	 *            the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

}
