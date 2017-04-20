package br.uefs.ecomp.bancoCooperativo.model;

import java.io.Serializable;

/**
 * 
 * @author Alyson Dantas
 *
 */
public class Endereco implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String rua;
	private String num;
	private String cidade;
	private String estado;
	
	/**
	 * Construtor
	 * @param rua Rua do endere�o
	 * @param num Numero do endere�o
	 * @param cidade Cidade do endere�o
	 * @param estado Estado do endere�o
	 */
	public Endereco(String rua, String num, String cidade, String estado){
		this.setRua(rua);
		this.setNum(num);
		this.setCidade(cidade);
		this.setEstado(estado);
	}
	
	public String getRua() {
		return rua;
	}
	public void setRua(String rua) {
		this.rua = rua;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}

}
