package com.julio.evento.model;

public class Prestadores {
	private String prestador;

	public Prestadores(String prestador) {
		super();
		this.prestador = prestador;
	}

	public Prestadores() {
		super();
	}

	/**
	 * @return the prestador
	 */
	public String getPrestador() {
		return prestador;
	}

	/**
	 * @param prestador
	 *            the prestador to set
	 */
	public void setPrestador(String prestador) {
		this.prestador = prestador;
	}
}
