package com.julio.guia.model;

/**
 * User.java This is a model class represents a User entity
 * 
 * @author Ramesh Fadatare
 * 
 */
public class GuiaEntrada {

	protected int id;
	protected String guiaNo;
	protected String data;
	protected String hora;
	protected String artigo;
	protected String descricao;
	protected String matricula;
	protected double pesoEsp;
	protected double pesoVerde;
	protected double humidade;
	protected double convTN;
	protected double peso;
	protected int notaPagamento;
	protected String parcela;

	public GuiaEntrada() {
	}

	public GuiaEntrada(String guiaNo, String data, String hora, String artigo,
			String descricao, String matricula, double pesoEsp,
			double pesoVerde, double humidade, double convTN, double peso,
			int notaPagamento, String parcela) {
		super();
		this.guiaNo = guiaNo;
		this.data = data;
		this.hora = hora;
		this.artigo = artigo;
		this.descricao = descricao;
		this.matricula = matricula;
		this.pesoEsp = pesoEsp;
		this.pesoVerde = pesoVerde;
		this.humidade = humidade;
		this.convTN = convTN;
		this.peso = peso;
		this.notaPagamento = notaPagamento;
		this.parcela = parcela;

	}

	public GuiaEntrada(int id, String guiaNo, String data, String hora,
			String artigo, String descricao, String matricula, double pesoEsp,
			double pesoVerde, double humidade, double convTN, double peso,
			int notaPagamento, String parcela) {
		super();
		this.id = id;
		this.guiaNo = guiaNo;
		this.data = data;
		this.hora = hora;
		this.artigo = artigo;
		this.descricao = descricao;
		this.matricula = matricula;
		this.pesoEsp = pesoEsp;
		this.pesoVerde = pesoVerde;
		this.humidade = humidade;
		this.convTN = convTN;
		this.peso = peso;
		this.notaPagamento = notaPagamento;
		this.parcela = parcela;

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGuiaNo() {
		return guiaNo;
	}

	public void setGuiaNo(String guiaNo) {
		this.guiaNo = guiaNo;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public String getArtigo() {
		return artigo;
	}

	public void setArtigo(String artigo) {
		this.artigo = artigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public double getPesoEsp() {
		return pesoEsp;
	}

	public void setPesoEsp(double pesoEsp) {
		this.pesoEsp = pesoEsp;
	}

	public double getPesoVerde() {
		return pesoVerde;
	}

	public void setPesoVerde(double pesoVerde) {
		this.pesoVerde = pesoVerde;
	}

	public double getHumidade() {
		return humidade;
	}

	public void setHumidade(double humidade) {
		this.humidade = humidade;
	}

	public double getConvTN() {
		return convTN;
	}

	public void setConvTN(double convTN) {
		this.convTN = convTN;
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

	public int getNotaPagamento() {
		return notaPagamento;
	}

	public void setNotaPagamento(int notaPagamento) {
		this.notaPagamento = notaPagamento;
	}

	public String getParcela() {
		return parcela;
	}

	public void setParcela(String parcela) {
		this.parcela = parcela;
	}
}