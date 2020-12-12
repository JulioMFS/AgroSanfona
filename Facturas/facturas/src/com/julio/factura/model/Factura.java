package com.julio.factura.model;

/**
 * User.java This is a model class represents a User entity
 * 
 * @author Ramesh Fadatare
 * 
 */
public class Factura {
	public Factura(int no, String data, String invno, int item,
			String designacao, double quantidade, String un, double preco,
			double desconto, double descontov, double valorliq, double iva,
			double ivav, String fornecedor, String parcela) {
		super();
		this.no = no;
		this.data = data;
		this.invno = invno;
		this.item = item;
		this.designacao = designacao;
		this.quantidade = quantidade;
		this.un = un;
		this.preco = preco;
		this.desconto = desconto;
		this.descontov = descontov;
		this.valorliq = valorliq;
		this.iva = iva;
		this.ivav = ivav;
		this.fornecedor = fornecedor;
		this.parcela = parcela;
	}

	public Factura(String data, String invno, int item, String designacao,
			double quantidade, String un, double preco, double desconto,
			double descontov, double valorliq, double iva, double ivav,
			String fornecedor, String parcela) {
		super();
		this.data = data;
		this.invno = invno;
		this.item = item;
		this.designacao = designacao;
		this.quantidade = quantidade;
		this.un = un;
		this.preco = preco;
		this.desconto = desconto;
		this.descontov = descontov;
		this.valorliq = valorliq;
		this.iva = iva;
		this.ivav = ivav;
		this.fornecedor = fornecedor;
		this.parcela = parcela;
	}

	protected int no;
	protected String data;
	protected String invno;
	protected int item;
	protected String designacao;
	protected double quantidade;
	protected String un;
	protected double preco;
	protected double desconto;
	protected double descontov;
	protected double valorliq;
	protected double iva;
	protected double ivav;
	protected String fornecedor;
	protected String parcela;

	public Factura() {
	}

	public Factura(String data, String invno, int item, String designacao,
			double quantidade, String un, double preco, double iva,
			String fornecedor, String parcela) {
		super();
		this.data = data;
		this.invno = invno;
		this.item = item;
		this.designacao = designacao;
		this.quantidade = quantidade;
		this.un = un;
		this.preco = preco;
		this.iva = iva;
		this.fornecedor = fornecedor;
		this.parcela = parcela;
	}

	public Factura(int no, String data, String invno, int item,
			String designacao, double quantidade, String un, double preco,
			double iva, String fornecedor, String parcela) {
		super();
		this.no = no;
		this.data = data;
		this.invno = invno;
		this.item = item;
		this.designacao = designacao;
		this.quantidade = quantidade;
		this.un = un;
		this.preco = preco;
		this.iva = iva;
		this.fornecedor = fornecedor;
		this.parcela = parcela;
	}

	public Factura(int no, String data, String invno, String designacao,
			double quantidade, String un, double preco, double iva,
			String fornecedor, String parcela) {
		super();
		this.no = no;
		this.data = data;
		this.invno = invno;
		this.designacao = designacao;
		this.quantidade = quantidade;
		this.un = un;
		this.preco = preco;
		this.iva = iva;
		this.fornecedor = fornecedor;
		this.parcela = parcela;
	}

	public Factura(String invno, double valorLiq, String parcela) {
		// TODO Auto-generated constructor stub
		super();
		this.invno = invno;
		this.valorliq = valorLiq;
		this.parcela = parcela;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getInvno() {
		return invno;
	}

	public void setInvno(String invno) {
		this.invno = invno;
	}

	public int getItem() {
		return item;
	}

	public void setItem(int item) {
		this.item = item;
	}

	public String getDesignacao() {
		return designacao;
	}

	public void setDesignacao(String designacao) {
		this.designacao = designacao;
	}

	public double getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(double quantidade) {
		this.quantidade = quantidade;
	}

	public String getUn() {
		return un;
	}

	public void setUn(String un) {
		this.un = un;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public double getIva() {
		return iva;
	}

	public void setIva(double iva) {
		this.iva = iva;
	}

	public String getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(String fornecedor) {
		this.fornecedor = fornecedor;
	}

	public double getDesconto() {
		return desconto;
	}

	public void setDesconto(double desconto) {
		this.desconto = desconto;
	}

	public double getDescontov() {
		return descontov;
	}

	public void setDescontov(double descontov) {
		this.descontov = descontov;
	}

	public double getValorliq() {
		return valorliq;
	}

	public void setValorliq(double valorliq) {
		this.valorliq = valorliq;
	}

	public double getIvav() {
		return ivav;
	}

	public void setIvav(double ivav) {
		this.ivav = ivav;
	}

	public String getParcela() {
		return parcela;
	}

	public void setParcela(String parcela) {
		this.parcela = parcela;
	}
}