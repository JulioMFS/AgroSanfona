package com.julio.bancoextracto.model;

public class BancoExtracto {

	protected int id;
	protected String dataMov;
	protected String dataValor;
	protected String descricao;
	protected double montante;
	protected double saldo;
	protected String classificacao;
	protected String descr;
	protected String parcela;
	protected String seara;
	protected String banco;

	public BancoExtracto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BancoExtracto(int id, String dataMov, String dataValor,
			String descricao, double montante, double saldo,
			String classificacao, String descr, String parcela, String seara,
			String banco) {
		super();
		this.id = id;
		this.dataMov = dataMov;
		this.dataValor = dataValor;
		this.descricao = descricao;
		this.montante = montante;
		this.saldo = saldo;
		this.classificacao = classificacao;
		this.descr = descr;
		this.parcela = parcela;
		this.seara = seara;
		this.banco = banco;
	}

	public BancoExtracto(String dataMov, String dataValor, String descricao,
			double montante, double saldo, String classificacao, String descr,
			String parcela, String seara, String banco) {
		super();
		this.id = id;
		this.dataMov = dataMov;
		this.dataValor = dataValor;
		this.descricao = descricao;
		this.montante = montante;
		this.saldo = saldo;
		this.classificacao = classificacao;
		this.descr = descr;
		this.parcela = parcela;
		this.seara = seara;
		this.banco = banco;
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

	/**
	 * @return the dataMov
	 */
	public String getDataMov() {
		return dataMov;
	}

	/**
	 * @param dataMov
	 *            the dataMov to set
	 */
	public void setDataMov(String dataMov) {
		this.dataMov = dataMov;
	}

	/**
	 * @return the dataValor
	 */
	public String getDataValor() {
		return dataValor;
	}

	/**
	 * @param dataValor
	 *            the dataValor to set
	 */
	public void setDataValor(String dataValor) {
		this.dataValor = dataValor;
	}

	/**
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * @param descricao
	 *            the descricao to set
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	/**
	 * @return the montante
	 */
	public double getMontante() {
		return montante;
	}

	/**
	 * @param montante
	 *            the montante to set
	 */
	public void setMontante(double montante) {
		this.montante = montante;
	}

	/**
	 * @return the saldo
	 */
	public double getSaldo() {
		return saldo;
	}

	/**
	 * @param saldo
	 *            the saldo to set
	 */
	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	/**
	 * @return the classificacao
	 */
	public String getClassificacao() {
		return classificacao;
	}

	/**
	 * @param classificacao
	 *            the classificacao to set
	 */
	public void setClassificacao(String classificacao) {
		this.classificacao = classificacao;
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

	/**
	 * @return the seara
	 */
	public String getSeara() {
		return seara;
	}

	/**
	 * @param seara
	 *            the seara to set
	 */
	public void setSeara(String seara) {
		this.seara = seara;
	}

	/**
	 * @return the banco
	 */
	public String getBanco() {
		return banco;
	}

	/**
	 * @param banco
	 *            the banco to set
	 */
	public void setBanco(String banco) {
		this.banco = banco;
	}

}
