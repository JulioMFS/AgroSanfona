package com.julio.guia.model;

public class Parcela {
	protected int id;
	protected String nome;
	protected String local;
	protected double area;
	protected int electContracto;
	protected int electContador;
	protected String aguaContador;
	protected String companhia;
	protected double potencia;
	protected int tipo;

	public Parcela() {
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return the local
	 */
	public String getLocal() {
		return local;
	}

	/**
	 * @param local the local to set
	 */
	public void setLocal(String local) {
		this.local = local;
	}

	/**
	 * @return the area
	 */
	public double getArea() {
		return area;
	}

	/**
	 * @param area the area to set
	 */
	public void setArea(double area) {
		this.area = area;
	}

	/**
	 * @return the electContracto
	 */
	public int getElectContracto() {
		return electContracto;
	}

	/**
	 * @param electContracto the electContracto to set
	 */
	public void setElectContracto(int electContracto) {
		this.electContracto = electContracto;
	}

	/**
	 * @return the electContador
	 */
	public int getElectContador() {
		return electContador;
	}

	/**
	 * @param electContador the electContador to set
	 */
	public void setElectContador(int electContador) {
		this.electContador = electContador;
	}

	/**
	 * @return the aguaContador
	 */
	public String getAguaContador() {
		return aguaContador;
	}

	/**
	 * @param aguaContador the aguaContador to set
	 */
	public void setAguaContador(String aguaContador) {
		this.aguaContador = aguaContador;
	}

	/**
	 * @return the companhia
	 */
	public String getCompanhia() {
		return companhia;
	}

	/**
	 * @param companhia the companhia to set
	 */
	public void setCompanhia(String companhia) {
		this.companhia = companhia;
	}

	/**
	 * @return the potencia
	 */
	public double getPotencia() {
		return potencia;
	}

	/**
	 * @param potencia the potencia to set
	 */
	public void setPotencia(double potencia) {
		this.potencia = potencia;
	}

	/**
	 * @return the tipo
	 */
	public int getTipo() {
		return tipo;
	}

	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
}
