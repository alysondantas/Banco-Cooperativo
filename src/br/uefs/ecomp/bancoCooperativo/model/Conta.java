package br.uefs.ecomp.bancoCooperativo.model;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author Alyson Dantas
 *
 */
public class Conta implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String numero;
	private String agencia;
	private List<String> clientes;
	private Double saldo;
	private boolean contaAtiva;
	private boolean corrent;
	private boolean fisica;
	
	/**
	 * Construtor
	 * @param numero
	 * @param agencia
	 * @param clientes
	 */
	public Conta(String numero, String agencia, List<String> clientes){
		this.numero = numero;
		this.agencia = agencia;
		this.saldo = 0.0;
		this.contaAtiva = true;
		this.clientes = clientes;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getAgencia() {
		return agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public List<String> getClientes() {
		return clientes;
	}

	public void setClientes(List<String> clientes) {
		this.clientes = clientes;
	}

	public Double getSaldo() {
		return saldo;
	}

	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}

	public boolean isContaAtiva() {
		return contaAtiva;
	}

	public void setContaAtiva(boolean contaAtiva) {
		this.contaAtiva = contaAtiva;
	}

	public boolean isCorrent() {
		return corrent;
	}

	public void setCorrent(boolean corrent) {
		this.corrent = corrent;
	}

	public boolean isFisica() {
		return fisica;
	}

	public void setFisica(boolean fisica) {
		this.fisica = fisica;
	}
}
