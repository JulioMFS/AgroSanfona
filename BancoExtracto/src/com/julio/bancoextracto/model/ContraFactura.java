package com.julio.bancoextracto.model;

public class ContraFactura {
	protected String invno;
	protected double valorliq;
	protected String parcela;

	public ContraFactura() {
		// TODO Auto-generated constructor stub
	}

	public ContraFactura(String invno, double valorliq, String parcela) {
		// TODO Auto-generated constructor stub
		super();
		this.invno = invno;
		this.valorliq = valorliq;
		this.parcela = parcela;
	}

	/**
	 * @return the invno
	 */
	public String getInvno() {
		return invno;
	}

	/**
	 * @param invno
	 *            the invno to set
	 */
	public void setInvno(String invno) {
		this.invno = invno;
	}

	/**
	 * @return the valorliq
	 */
	public double getValorliq() {
		return valorliq;
	}

	/**
	 * @param valorliq
	 *            the valorliq to set
	 */
	public void setValorliq(double valorliq) {
		this.valorliq = valorliq;
	}

	/**
	 * @return the parcela
	 */
	public String getParcela() {
		return parcela;
	}

	/**
	 * @param parcela
	 *            the parcela to set
	 */
	public void setParcela(String parcela) {
		this.parcela = parcela;
	}

}
