package com.julio.contadorelect.model;

public class ElectPrecos {
	protected String Companhia;
	protected String Data;
	protected double Simples;
	protected double VazioBi;
	protected double ForaVazioBi;
	protected double DescBi;
	protected double VazioTri;
	protected double PontaTri;
	protected double CheiasTri;
	protected double DescTri;
	protected double Iva;
	protected int Tipo;
	protected double Potencia;
	protected double Preco;
	protected double PotenciaDesc;
	protected double PotenciaIva;

	public ElectPrecos(String companhia, String data, double simples,
			double vaziobi, double foravaziobi, double descbi, double vaziotri,
			double pontatri, double cheiastri, double desctri, double iva,
			int tipo, double potencia, double preco, double potenciadesc,
			double potenciaiva) {
		super();
		this.Companhia = companhia;
		this.Data = data;
		this.Simples = simples;
		this.VazioBi = vaziobi;
		this.ForaVazioBi = foravaziobi;
		this.DescBi = descbi;
		this.VazioTri = vaziotri;
		this.PontaTri = pontatri;
		this.CheiasTri = cheiastri;
		this.DescTri = desctri;
		this.Iva = iva;
		this.Tipo = tipo;
		this.Potencia = potencia;
		this.Preco = preco;
		this.PotenciaDesc = potenciadesc;
		this.PotenciaIva = potenciaiva;

	}

	public String getCompanhia() {
		return Companhia;
	}

	public void setCompanhia(String companhia) {
		Companhia = companhia;
	}

	public String getData() {
		return Data;
	}

	public void setData(String data) {
		Data = data;
	}

	public double getSimples() {
		return Simples;
	}

	public void setSimples(double simples) {
		Simples = simples;
	}

	public double getVazioBi() {
		return VazioBi;
	}

	public void setVazioBi(double vazioBi) {
		VazioBi = vazioBi;
	}

	public double getForaVazioBi() {
		return ForaVazioBi;
	}

	public void setForaVazioBi(double foraVazioBi) {
		ForaVazioBi = foraVazioBi;
	}

	public double getDescBi() {
		return DescBi;
	}

	public void setDescBi(double descBi) {
		DescBi = descBi;
	}

	public double getVazioTri() {
		return VazioTri;
	}

	public void setVazioTri(double vazioTri) {
		VazioTri = vazioTri;
	}

	public double getPontaTri() {
		return PontaTri;
	}

	public void setPontaTri(double pontaTri) {
		PontaTri = pontaTri;
	}

	public double getCheiasTri() {
		return CheiasTri;
	}

	public void setCheiasTri(double cheiasTri) {
		CheiasTri = cheiasTri;
	}

	public double getDescTri() {
		return DescTri;
	}

	public void setDescTri(double descTri) {
		DescTri = descTri;
	}

	public double getIva() {
		return Iva;
	}

	public void setIva(double iva) {
		Iva = iva;
	}

	public int getTipo() {
		return Tipo;
	}

	public void setTipo(int tipo) {
		Tipo = tipo;
	}

	public double getPotencia() {
		return Potencia;
	}

	public void setPotencia(double potencia) {
		Potencia = potencia;
	}

	public double getPreco() {
		return Preco;
	}

	public void setPreco(double preco) {
		Preco = preco;
	}

	public double getPotenciaDesc() {
		return PotenciaDesc;
	}

	public void setPotenciaDesc(double potenciadesc) {
		PotenciaDesc = potenciadesc;
	}

	public double getPotenciaIva() {
		return PotenciaIva;
	}

	public void setPotenciaIva(double potenciaiva) {
		PotenciaIva = potenciaiva;
	}
}
