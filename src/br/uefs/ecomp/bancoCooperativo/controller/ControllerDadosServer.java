package br.uefs.ecomp.bancoCooperativo.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import br.uefs.ecomp.bancoCooperativo.exceptions.AgenciaIncorretaException;
import br.uefs.ecomp.bancoCooperativo.exceptions.CampoVazioException;
import br.uefs.ecomp.bancoCooperativo.exceptions.ClienteJaExistenteException;
import br.uefs.ecomp.bancoCooperativo.exceptions.ClienteNaoEncontradoException;
import br.uefs.ecomp.bancoCooperativo.exceptions.ClientesNullException;
import br.uefs.ecomp.bancoCooperativo.exceptions.ContaConflitoException;
import br.uefs.ecomp.bancoCooperativo.exceptions.SaldoInsuficienteException;
import br.uefs.ecomp.bancoCooperativo.exceptions.SenhaInvalidaException;
import br.uefs.ecomp.bancoCooperativo.model.*;

/**
 * 
 * @author Alyson Dantas
 *
 */
public class ControllerDadosServer {
	private static ControllerDadosServer unicaInstancia;

	private ControllerDadosServer(){
	}

	/**
	 * controla o instanciamento de objetos Controller
	 * @return unicaInstancia
	 */
	public static synchronized ControllerDadosServer getInstance(){
		if(unicaInstancia==null){
			unicaInstancia = new ControllerDadosServer();
		}
		return unicaInstancia;
	}

	/**
	 * reseta o objeto Controller ja instanciado
	 */
	public static void zerarSingleton (){
		unicaInstancia = null;
	}

	/**
	 * Metodo que rece o deposito para uma conta
	 * @param num Numero da conta
	 * @param agencia Agencia da conta
	 * @param valor Valor a depositar
	 * @return
	 * @throws FileNotFoundException
	 * @throws ClassNotFoundException
	 * @throws CampoVazioException
	 * @throws IOException
	 * @throws ClienteNaoEncontradoException
	 * @throws AgenciaIncorretaException
	 */
	public Double recebeDeposito(String num, String agencia, Double valor) throws FileNotFoundException, ClassNotFoundException, CampoVazioException, IOException, ClienteNaoEncontradoException, AgenciaIncorretaException{
		if(num == null || num.equals("") || agencia == null || agencia.equals("") || valor<0){
			throw new CampoVazioException();//lança exeção caso uma dos campos estejam vazios
		}
		Conta contaDepositar = getConta(num, agencia);//recupera conta a ser depositada
		Double atual = contaDepositar.getSaldo();
		atual = atual + valor;
		contaDepositar.setSaldo(atual);//adiciona saldo

		//grava conta novamente em directorio
		escreveConta(contaDepositar);

		return atual;
	}

	/**
	 * Metodo de receber as transações.
	 * @param numero1 Numero da conta remetente
	 * @param agencia1 Agencia da conta remetente
	 * @param numero2 Numero da conta destino
	 * @param agencia2 Numero da conta destino
	 * @param valor Valor de transferencia
	 * @param cpfcnpj Cpf ou Cpnj do cliente remtente
	 * @return
	 * @throws CampoVazioException
	 * @throws FileNotFoundException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws ClienteNaoEncontradoException
	 * @throws AgenciaIncorretaException
	 * @throws ContaConflitoException
	 * @throws SaldoInsuficienteException
	 */
	public Double recebeTransacao(String numero1, String agencia1, String numero2, String agencia2, Double valor, String cpfcnpj) throws CampoVazioException, FileNotFoundException, ClassNotFoundException, IOException, ClienteNaoEncontradoException, AgenciaIncorretaException, ContaConflitoException, SaldoInsuficienteException{
		if(numero1 == null || numero1.equals("") || agencia1 == null || agencia1.equals("") || numero2 == null || numero2.equals("") || agencia2 == null || agencia2.equals("") || valor<0){
			throw new CampoVazioException();//lança exceção caso uma dos campos estejam vazios
		}
		Conta contaRemetente = getConta(numero1, agencia1);// recupera a conta remetente
		Conta contaDestino = getConta(numero2, agencia2);// recupera a conta destino

		if(contaRemetente.equals(contaDestino)){
			throw new ContaConflitoException();//se as duas contas forem a mesma lança conflito
		}

		Double valorRemetente = contaRemetente.getSaldo();//recupera saldo da conta remetente
		if(valorRemetente-valor<0){//se ele for menor que o valor a ser debitado lança exceção
			throw new SaldoInsuficienteException();
		}

		List<String> clientes = contaRemetente.getClientes();//recupera lista de clientes da conta remetente
		Iterator<String> iterando = clientes.iterator();
		boolean existe = false;
		String atual = "";
		while(iterando.hasNext()){//verifica se nessa lista existe realmente o cliente que esta tentando realizar a transação
			atual = iterando.next();
			if(atual.equals(cpfcnpj)){
				existe = true;
			}
		}

		if(existe == false){//se não existir lança exceção
			throw new ClienteNaoEncontradoException();
		}

		Double valorDestino = contaDestino.getSaldo();//recupera a conta destino
		valorDestino = valorDestino + valor;//insere o novo saldo na conta destino
		contaDestino.setSaldo(valorDestino);

		valorRemetente = contaRemetente.getSaldo();//recupera o saldo da conta remetente
		valorRemetente = valorRemetente - valor;//insere o novo saldo na conta remetente
		contaRemetente.setSaldo(valorRemetente);

		escreveConta(contaRemetente);//escreve o a conta remetente
		escreveConta(contaDestino);//escreve a conta destino

		return valorRemetente;
	}

	/**
	 * Metodo que cadastra novos clientes
	 * @param nome Nome do cliente
	 * @param data Data do cliente
	 * @param cpfcnpj Cpf ou Cnpj do cliente
	 * @param sexo Sexo caso seja fisico
	 * @param uf Estado
	 * @param cidade Cidade
	 * @param rua Rua
	 * @param num Numero
	 * @param fisicaJuridica Verificador se é pessoa fisica ou juridica
	 * @param senha Senha de acesso
	 * @throws CampoVazioException
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClienteJaExistenteException
	 */
	public void cadastraCliente(String nome, String data,String cpfcnpj, boolean sexo, String uf, String cidade, String rua, String num, boolean fisicaJuridica, String senha) throws CampoVazioException, FileNotFoundException, IOException, ClienteJaExistenteException{
		if(nome == null || nome.equals("") || cpfcnpj == null || cpfcnpj.equals("") || uf == null || uf.equals("") || rua == null || rua.equals("") || num == null || num.equals("") || senha == null || senha.equals("")){
			throw new CampoVazioException();//lança exceção caso uma dos campos estejam vazios
		}else{
			criaCaminho();//cria o caminho
			File arquivos = new File("/servidor/clientes/");
			File todosarquivos[] = arquivos.listFiles();//verifica todos os arquivos que ja estão na pasta
			int cont = 0;
			for (int i = todosarquivos.length; cont < i; cont++) {//procura na pasta por algum cliente ja cadastrado para não conflitar
				File arquivo = todosarquivos[cont];
				if(arquivo.getName().equals(cpfcnpj + ".dat")){
					throw new ClienteJaExistenteException();
				}
				System.out.println(arquivo.getName());
			}

			Endereco end = new Endereco(rua, num, cidade, uf);//cria endereço do cliente
			Cliente cliente = new Cliente(nome, end, fisicaJuridica, cpfcnpj, data, sexo, senha);//cria novo cliente
			escreveCliente(cliente);//escreve o cliente
		}
	}

	/**
	 * Metodo que edita o cliente
	 * @param nome Nome do cliente de edição
	 * @param data Data de edição
	 * @param cpfcnpj CPF ou Cnpj do cliente
	 * @param sexo Sexo de edição
	 * @param uf Estado de edição
	 * @param cidade Cidade de edição
	 * @param rua Rua de edição
	 * @param num Numero de edição
	 * @param senha Senha do cliente
	 * @param senhaNova Senha de edição
	 * @throws CampoVazioException
	 * @throws FileNotFoundException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws SenhaInvalidaException
	 */
	public void editarCliente(String nome, String data,String cpfcnpj, boolean sexo, String uf, String cidade, String rua, String num, String senha, String senhaNova) throws CampoVazioException, FileNotFoundException, ClassNotFoundException, IOException, SenhaInvalidaException{
		if(nome == null || nome.equals("") || cpfcnpj == null || cpfcnpj.equals("") || uf == null || uf.equals("") || rua == null || rua.equals("") || num == null || num.equals("") || senha == null || senha.equals("") || senhaNova == null || senhaNova.equals("")){
			throw new CampoVazioException();//lança exceção caso uma dos campos estejam vazios
		}else{
			Cliente cliente = getCliente(cpfcnpj, senha);//recupera o cliente
			cliente.setDataNascimento(data);//coloca as novas informações no cliente
			cliente.setNome(nome);
			cliente.setSexo(sexo);
			Endereco end = cliente.getEndereco();
			cliente.setSenha(senhaNova);
			end.setCidade(cidade);
			end.setEstado(uf);
			end.setNum(num);
			end.setRua(rua);
			cliente.setEndereco(end);

			escreveCliente(cliente);//escreve o cliente editado
		}
	}

	/**
	 * Metdodo que cadastra a conta
	 * @param cpfcnpj Cpf ou Cnpj do titular
	 * @param corrente Verificador se é corrente ou não
	 * @param senha Senha do titular
	 * @throws CampoVazioException
	 * @throws FileNotFoundException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws SenhaInvalidaException
	 * @throws ClientesNullException
	 * @throws ContaConflitoException
	 */
	public void cadastrarConta(String cpfcnpj, boolean corrente, String senha) throws CampoVazioException, FileNotFoundException, ClassNotFoundException, IOException, SenhaInvalidaException, ClientesNullException, ContaConflitoException{
		if(cpfcnpj == null || cpfcnpj.equals("") || senha == null || senha.equals("")){
			throw new CampoVazioException();//lança exceção caso uma dos campos estejam vazios
		}

		Cliente cliente = getCliente(cpfcnpj,senha);//recupera o cliente

		if(!cliente.isFisica()){//se ele não for cliente fisico
			if(corrente == false){//e a conta for poupaça lança exceção
				throw new ContaConflitoException();
			}
		}

		List<String> clientes = new ArrayList<>();
		clientes.add(cliente.getCpfCnpj());//adiciona o cliente a lista
		Random radomica = new Random();//cria um numero randomico para o numero da conta
		int numeroaleatorio = radomica.nextInt(10000);
		String numero = "" + numeroaleatorio;
		numeroaleatorio = radomica.nextInt(100000);//gera outro numero randomico para a agencia
		String agencia = "" + numeroaleatorio;;

		Conta conta = new Conta(numero,agencia,clientes);//cria a conta
		conta.setFisica(cliente.isFisica());//o que determina se a conta é de pessoa fisica ou não é qual a pessoa que esta criando a conta
		conta.setCorrent(corrente);
		List<String> contas = cliente.getContas();
		contas.add(numero + "\\" + agencia);
		cliente.setContas(contas);//adiciona no cliente a conta

		escreveCliente(cliente);//escreve o cliente

		escreveConta(conta);//escreve a conta
	}

	/**
	 * Metodo que recupera o cliente
	 * @param cpfCnpj CPF ou Cnpj do cliente
	 * @param senha Senha do cliente
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws SenhaInvalidaException
	 */
	public Cliente getCliente(String cpfCnpj, String senha) throws FileNotFoundException, IOException, ClassNotFoundException, SenhaInvalidaException{
		criaCaminho();//cria o caminho
		ObjectInputStream objectIn = new ObjectInputStream(new BufferedInputStream(new FileInputStream("/servidor/clientes/" + cpfCnpj + ".dat")));//recupera o cliente dos arquivos
		Cliente cliente = (Cliente) objectIn.readObject();
		objectIn.close();
		if(cliente.getSenha().equals(senha)){//se a senha estiver correta retorna ele
			return cliente;
		}else{//caso não retorna uma exceção
			throw new SenhaInvalidaException();
		}
	}

	/**
	 * Metodo que recupera a conta
	 * @param numConta Numero da conta
	 * @param agencia Agencia da conta
	 * @return
	 * @throws CampoVazioException
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws AgenciaIncorretaException
	 */
	public Conta getConta(String numConta, String agencia) throws CampoVazioException, FileNotFoundException, IOException, ClassNotFoundException, AgenciaIncorretaException{
		criaCaminho();//cria o caminho
		if(numConta == null || numConta.equals("") || agencia == null || agencia.equals("")){
			throw new CampoVazioException();//lança exceção caso uma dos campos estejam vazios
		}
		ObjectInputStream objectIn = new ObjectInputStream(new BufferedInputStream(new FileInputStream("/servidor/contas/" + numConta + ".dat")));//recupera a conta
		Conta conta = (Conta) objectIn.readObject();
		objectIn.close();

		String a = conta.getAgencia();

		if(a.equals(agencia)){//se a agencia for a mesma retorna a conta
			return conta;
		}else{//caso não retorna uma exceção
			throw new AgenciaIncorretaException();
		}
	}

	/**
	 * Metodo que adiciona um titular a uma conta
	 * @param numConta Numero da conta
	 * @param agencia Agencia da conta
	 * @param cpfCnpj CPF ou CNPJ do titular atual
	 * @param cpfCnpj2 CPF ou CNPJ do novo titular
	 * @throws FileNotFoundException
	 * @throws ClassNotFoundException
	 * @throws CampoVazioException
	 * @throws IOException
	 * @throws AgenciaIncorretaException
	 * @throws ClienteNaoEncontradoException
	 * @throws ContaConflitoException
	 */
	public void addClienteConta(String numConta, String agencia, String cpfCnpj,String cpfCnpj2) throws FileNotFoundException, ClassNotFoundException, CampoVazioException, IOException, AgenciaIncorretaException, ClienteNaoEncontradoException, ContaConflitoException{
		if(numConta == null || numConta.equals("") || agencia == null || agencia.equals("") || cpfCnpj == null || cpfCnpj.equals("") || cpfCnpj2 == null || cpfCnpj2.equals("")){
			throw new CampoVazioException();//lança exceção caso uma dos campos estejam vazios
		}
		Conta conta = getConta(numConta, agencia);//recupera a conta

		List<String> clientes = conta.getClientes();//recupera a lista de clientes da conta 
		Iterator<String> iterando = clientes.iterator();
		boolean existe = false;
		boolean jaEstanaConta = false;
		String atual = "";
		while(iterando.hasNext()){
			atual = iterando.next();//itera a lista de cpfs e cnpjs
			if(atual.equals(cpfCnpj)){
				existe = true;//verifica se o primeiro cliente é um titular da conta
			}
			if(atual.equals(cpfCnpj2)){
				jaEstanaConta = true;//verifica se o segundo cliente não ja esta na conta
			}
		}
		if(existe == false){//se não existir lança conflito
			throw new ClienteNaoEncontradoException();
		}else if(jaEstanaConta == true){//se ja estiver na conta lança conflito
			throw new ContaConflitoException();
		}

		clientes.add(cpfCnpj2);//adiciona o cnpj na conta
		conta.setClientes(clientes);//adiciona todos os cnpjs nas contas

		ObjectInputStream objectIn = new ObjectInputStream(new BufferedInputStream(new FileInputStream("/servidor/clientes/" + cpfCnpj2 + ".dat")));//procura o cliente diretamente
		Cliente cliente = (Cliente) objectIn.readObject();//ler o cliente
		objectIn.close();


		List<String> contas = cliente.getContas();//recupera as contas do cliente
		contas.add(numConta + "\\" + agencia);//adiciona a conta atual no cliente
		cliente.setContas(contas);//adiciona as contas no cliente

		escreveCliente(cliente);//escreve o cliente
		escreveConta(conta);//escreve a conta
	}

	/**
	 * Metodo que escreve o arquivo conta
	 * @param conta Conta
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private void escreveConta(Conta conta) throws FileNotFoundException, IOException{
		criaCaminho();//cria o caminho caso ele não exista
		ObjectOutputStream objectOut = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("/servidor/contas/" + conta.getNumero() + ".dat"))); //grava o objeto no caminho informado				
		objectOut.writeObject(conta);//escreve o arquivo
		objectOut.flush();
		objectOut.close();
	}

	/**
	 * Metodo que escreve o arquivo cliente
	 * @param cliente Cliente
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private void escreveCliente(Cliente cliente) throws FileNotFoundException, IOException{
		criaCaminho();//cria o caminho caso ele não exista
		ObjectOutputStream objectOutC = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("/servidor/clientes/" + cliente.getCpfCnpj() + ".dat")));	//grava o objeto no caminho informado		
		objectOutC.writeObject(cliente);//escreve o arquivo
		objectOutC.flush();
		objectOutC.close();
	}

	/**
	 * Metedo que cria as pastas de acesso caso não ja existam
	 */
	private void criaCaminho(){
		File caminhoCliente = new File("\\servidor\\clientes"); // verifica se a pasta existe
		if (!caminhoCliente.exists()) {
			caminhoCliente.mkdirs(); //caso não exista cria a pasta
		}
		File caminhoConta = new File("\\servidor\\contas"); // verifica se a pasta existe
		if (!caminhoConta.exists()) {
			caminhoConta.mkdirs(); //caso não exista cria a pasta
		}
	}
}
