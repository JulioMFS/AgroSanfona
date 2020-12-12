package com.julio.bancoextracto.model;

public class Descr {
	protected int id;
	protected String descr;

	public Descr(int id, String desc) {
		super();
		this.id = id;
		this.descr = desc;
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the descr
	 */
	public String getDescr() {
		return descr;
	}

	/**
	 * @param descr
	 *            the descr to set
	 */
	public void setDescr(String descr) {
		this.descr = descr;
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
