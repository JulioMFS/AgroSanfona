package com.julio.evento.model;

public class Event {
	protected int No;
	protected static String Date;
	protected static String Parcela;
	protected static String event;
	protected static String Prestador;
	protected static String Alfaia;
	protected static String Produto;
	protected static String Fornecedor;
	protected static double Quantidade;
	protected static String Unidade;
	protected static String Settings;

	public Event(String date, String parcela, String Event, String prestador,
			String alfaia, String produto, String fornecedor,
			double quantidade, String unidade, String settings) {
		super();
		this.Date = date;
		this.Parcela = parcela;
		this.event = Event;
		this.Prestador = prestador;
		this.Alfaia = alfaia;
		this.Produto = produto;
		this.Fornecedor = fornecedor;
		this.Quantidade = quantidade;
		this.Unidade = unidade;
		this.Settings = settings;

	}

	public Event(int no, String date, String parcela, String Event,
			String prestador, String alfaia, String produto, String fornecedor,
			double quantidade, String unidade, String settings) {
		super();
		this.No = no;
		this.Date = date;
		this.Parcela = parcela;
		this.event = Event;
		this.Prestador = prestador;
		this.Alfaia = alfaia;
		this.Produto = produto;
		this.Fornecedor = fornecedor;
		this.Quantidade = quantidade;
		this.Unidade = unidade;
		this.Settings = settings;

	}

	public int getNo() {
		return No;
	}

	public void setNo(int no) {
		No = no;
	}

	public static String getDate() {
		return Date;
	}

	public void setDate(String date) {
		Date = date;
	}

	public static String getParcela() {
		return Parcela;
	}

	public void setParcela(String parcela) {
		Parcela = parcela;
	}

	public static String getEvent() {
		return event;
	}

	public void setEvent(String Event) {
		this.event = Event;
	}

	public static String getPrestador() {
		return Prestador;
	}

	public void setPrestador(String prestador) {
		Prestador = prestador;
	}

	public static String getAlfaia() {
		return Alfaia;
	}

	public void setAlfaia(String alfaia) {
		Alfaia = alfaia;
	}

	public static String getProduto() {
		return Produto;
	}

	public void setProduto(String produto) {
		Produto = produto;
	}

	public static String getFornecedor() {
		return Fornecedor;
	}

	public void setFornecedor(String fornecedor) {
		Fornecedor = fornecedor;
	}

	public static double getQuantidade() {
		return Quantidade;
	}

	public void setQuantidade(double quantidade) {
		Quantidade = quantidade;
	}

	public static String getUnidade() {
		return Unidade;
	}

	public void setUnidade(String unidade) {
		Unidade = unidade;
	}

	public static String getSettings() {
		return Settings;
	}

	public void setSettings(String settings) {
		Settings = settings;
	}
}
