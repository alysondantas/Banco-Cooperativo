package br.uefs.ecomp.bancoCooperativo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Alyson Dantas
 *
 */
public class Cliente implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nome;
	private Endereco endereco;
	private boolean fisica;
	private String cpfCnpj;
	private String dataNascimento;
	private List<String> contas;//lista de contas do cliente
	private boolean sexo;
	private String senha;
	
	/**
	 * Construtor
	 * @param nome Nome do cliente
	 * @param endereco Endereço do cliente
	 * @param fisica Verificador se é pessoa fisica ou juridica
	 * @param cpfCnpj Cnpj ou CPF do cliente
	 * @param dataNasc Data de nascimento ou fundação do cliente
	 * @param sexo Sexo se for pessoa fisica
	 * @param senha Senha do cliente
	 */
	public Cliente(String nome, Endereco endereco, boolean fisica,String cpfCnpj, String dataNasc, boolean sexo, String senha){
		this.setNome(nome);
		this.setEndereco(endereco);
		this.setFisica(fisica);
		this.setCpfCnpj(cpfCnpj);
		this.setDataNascimento(dataNasc);
		this.sexo = sexo;
		contas = new ArrayList<String>();
		this.senha = senha;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public boolean isFisica() {
		return fisica;
	}

	public void setFisica(boolean fisicaJuridica) {
		this.fisica = fisicaJuridica;
	}

	public String getCpfCnpj() {
		return cpfCnpj;
	}

	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}

	public String getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public List<String> getContas() {
		return contas;
	}

	public void setContas(List<String> contas) {
		this.contas = contas;
	}

	public boolean isSexo() {
		return sexo;
	}

	public void setSexo(boolean sexo) {
		this.sexo = sexo;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
}
