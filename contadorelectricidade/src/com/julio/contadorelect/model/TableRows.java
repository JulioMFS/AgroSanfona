package com.julio.contadorelect.model;

public class TableRows {
	protected String Descr;
	protected int quant;
	protected double Preco;
	protected double Desc;
	protected double Iva;
	protected int Tipo;

	public TableRows(String descr, int quant, double preco, double desc,
			double iva, int tipo) {
		super();
		this.quant = quant;
		this.Descr = descr;
		this.Preco = preco;
		this.Desc = desc;
		this.Iva = iva;
		this.Tipo = tipo;
	}

	public String getDescr() {
		return Descr;
	}

	public void setDescr(String descr) {
		Descr = descr;
	}

	public int getTipo() {
		return Tipo;
	}

	public void setTipo(int tipo) {
		Tipo = tipo;
	}

	public int getQuant() {
		return quant;
	}

	public void setQuant(int Quant) {
		quant = Quant;
	}

	public double getPreco() {
		return Preco;
	}

	public void setPreco(double preco) {
		Preco = preco;
	}

	public double getDesc() {
		return Desc;
	}

	public void setDesc(double desc) {
		Desc = desc;
	}

	public double getIva() {
		return Iva;
	}

	public void setIva(double iva) {
		Iva = iva;
	}
}
