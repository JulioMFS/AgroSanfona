package com.julio.incomeexpensestatement.model;

public class InOut {
	public InOut(String desc, double bal) {
		super();
		this.desc = desc;
		this.bal = bal;
	}

	String desc;
	double bal;

	public InOut() {
		// TODO Auto-generated constructor stub

	}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @param desc
	 *            the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

	/**
	 * @return the bal
	 */
	public double getBal() {
		return bal;
	}

	/**
	 * @param bal
	 *            the bal to set
	 */
	public void setBal(double bal) {
		this.bal = bal;
	}

}
