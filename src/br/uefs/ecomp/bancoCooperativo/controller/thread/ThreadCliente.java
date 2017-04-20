package br.uefs.ecomp.bancoCooperativo.controller.thread;

import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JTextArea;

import br.uefs.ecomp.bancoCooperativo.controller.ControllerDadosServer;
import br.uefs.ecomp.bancoCooperativo.exceptions.AgenciaIncorretaException;
import br.uefs.ecomp.bancoCooperativo.exceptions.CampoVazioException;
import br.uefs.ecomp.bancoCooperativo.exceptions.ClienteJaExistenteException;
import br.uefs.ecomp.bancoCooperativo.exceptions.ClienteNaoEncontradoException;
import br.uefs.ecomp.bancoCooperativo.exceptions.ClientesNullException;
import br.uefs.ecomp.bancoCooperativo.exceptions.ContaConflitoException;
import br.uefs.ecomp.bancoCooperativo.exceptions.SaldoInsuficienteException;
import br.uefs.ecomp.bancoCooperativo.exceptions.SenhaInvalidaException;
import br.uefs.ecomp.bancoCooperativo.model.Cliente;
import br.uefs.ecomp.bancoCooperativo.model.Conta;
import br.uefs.ecomp.bancoCooperativo.model.Pacote;

/**
 * 
 * @author Alyson Dantas
 *
 */
public class ThreadCliente extends Thread {
	private Socket cliente;//socket do cliente
	private ServerSocket server;//socket do servidor
	private JTextArea textField;//para atualizar a interface
	private ControllerDadosServer controller = ControllerDadosServer.getInstance();//instancia do controller

	/**
	 * Construtor
	 * @param server Servidor que aceita os clientes
	 * @param textField Area de log na interface
	 * @param cliente Cliente que ja foi aceito
	 */
	public ThreadCliente(ServerSocket server, JTextArea textField, Socket cliente) {//recebe o socket server e o textArea
		this.server = server; 
		this.cliente = cliente;
		this.textField = textField;
	}

	/**
	 * Metodo Run da Thread
	 */
	public void run() {
		try {
			//while(true) {
				//cliente = server.accept();
				//Inicia thread do cliente aceitando clientes

				//ObjectInputStream para receber o nome do arquivo
				ObjectInputStream   entrada = new ObjectInputStream(cliente.getInputStream());//cria um objeto de entrada
				ObjectOutputStream saida = new ObjectOutputStream(cliente.getOutputStream());//cria um objeto de saida
				Pacote pack = (Pacote) entrada.readObject();//obtem o pacote de entrada
				int opcao = pack.getOpcao();//recebe a opcao que o cliente mandou
				List<String> informacoes = pack.getLista();//recebe a lista de informações
				Iterator<String> itera = informacoes.iterator();
				Pacote enviarResultado = new Pacote();//cria novo pacote para envio
				List<String> result = new ArrayList<String>();//cria uma nova lista de informaçõoes para envio
				String s = "erro";//string de log com erro
				switch(opcao){
				case 0://Cadastro de cliente Fisico
					String nome = itera.next();//recebe as informações para cadastro
					String data = itera.next();
					String cpf = itera.next();
					boolean sexo = pack.isB();
					String uf = itera.next();
					String cidade = itera.next();
					String rua = itera.next();
					String num = itera.next();
					String senha = itera.next();
					
					try{
						controller.cadastraCliente(nome, data, cpf, sexo,uf, cidade, rua, num, true, senha);//controller cadastra cliente
						s = "Criar cadastro " + cpf;//log
						saida.writeObject("concluido");//envia resposta concluido
					}catch(CampoVazioException e){
						saida.writeObject("camponaopreenchido");//erro de campo nao preenchido
					}catch(ClienteJaExistenteException e){
						saida.writeObject("clientejaexistente");//erro de cliente ja existente
					}
					saida.flush();
					break;
				case 1://Acessar Cliente
					String usuario = itera.next();//recebe informações
					String senha2 = itera.next();
					Cliente cliente = null;
					
					try{
						cliente = controller.getCliente(usuario,senha2);//controller retorna cliente
						enviarResultado.setLista2(cliente.getContas());//coloca na segunda lista do pacote as contas do cliente
						result.add("encontrado");//envia resposta de encontrado
						result.add(cliente.getNome());//envia informações do cliente
						result.add(cliente.getDataNascimento());;
						enviarResultado.setB(cliente.isSexo());;
						result.add(cliente.getEndereco().getEstado());
						result.add(cliente.getEndereco().getCidade());
						result.add(cliente.getEndereco().getRua());
						result.add(cliente.getEndereco().getNum());
						enviarResultado.setC(cliente.isFisica());
						enviarResultado.setLista(result);//envio o resultado e todo o resto
						saida.writeObject(enviarResultado);//envio o pacote
						saida.flush();
						s = "Acesso ao " + usuario;//log
					}catch(FileNotFoundException e){//caso cliente seja invalido
						result.add("usuarioinvalido");
						enviarResultado.setLista(result);//envio o resultado
						saida.writeObject(enviarResultado);//envia erro
						saida.flush();
					}catch(SenhaInvalidaException e){//caso a senha do cliente seja invalido
						result.add("senhainvalida");
						enviarResultado.setLista(result);//envio o resultado
						saida.writeObject(enviarResultado);
						saida.flush();
					}
					saida.flush();
					break;
				case 2://editar cliente
					
					String nomeEdit = itera.next();//recebe as informações 
					String dataEdit = itera.next();
					String cpfEdit = itera.next();
					boolean sexoEdit = pack.isB();
					String ufEdit = itera.next();
					String cidadeEdit = itera.next();
					String ruaEdit = itera.next();
					String numEdit = itera.next();
					String senhaEdit = itera.next();
					String senhaNovaEdit = itera.next();
					try{
						controller.editarCliente(nomeEdit, dataEdit, cpfEdit, sexoEdit, ufEdit, cidadeEdit, ruaEdit, numEdit, senhaEdit, senhaNovaEdit);//controller edita o cliente
						s = "Editar cadastro " + cpfEdit;//log
						result.add("concluido");//envia resultado concluido
						enviarResultado.setLista(result);//envio o resultado
						saida.writeObject(enviarResultado);//envia o pacote
					}catch(CampoVazioException e){//caso exista um campo vazio
						result.add("camponaopreenchido");//envia o erro
						enviarResultado.setLista(result);//envio o resultado
						saida.writeObject(enviarResultado);//envia o pacote
					}catch(FileNotFoundException e){//caso não encontre o cliente
						result.add("clientenaoencontrado");//envia o erro
						enviarResultado.setLista(result);//envio o resultado
						saida.writeObject(enviarResultado);//envia o pacote
					}
					saida.flush();
					break;
				case 3://cadastro de cliente juridico
					String nomeJuri = itera.next();//recebe informações
					String dataJuri = itera.next();
					String cnpj = itera.next();
					String ufJuri = itera.next();
					String cidadeJuri = itera.next();
					String ruaJuri = itera.next();
					String numJuri = itera.next();
					String senhaJuri = itera.next();
					try{
						controller.cadastraCliente(nomeJuri, dataJuri, cnpj, true, ufJuri, cidadeJuri, ruaJuri, numJuri, false, senhaJuri);//controller cadastra o cliente
						s = "Criar cadastro " + cnpj;//log
						result.add("concluido");//envia o concluido
						enviarResultado.setLista(result);//envio o resultado
						saida.writeObject(enviarResultado);//envia o pacote
					}catch(CampoVazioException e){//caso tenha algum campo vazio
						result.add("camponaopreenchido");//envia o erro
						enviarResultado.setLista(result);//envio o resultado
						saida.writeObject(enviarResultado);//envia pacote
					}catch(ClienteJaExistenteException e){//caso o cliente ja exista
						result.add("clientejaexistente");//envia o erro
						enviarResultado.setLista(result);//envio o resultado
						saida.writeObject(enviarResultado);//envia o pacote
					}
					saida.flush();
					break;
				case 4://criar uma conta
					String cpfcnpj = itera.next();//recebe informações
					String senhaCriarconta = itera.next();
					boolean corrente = pack.isB();
					
					try{
						controller.cadastrarConta(cpfcnpj, corrente, senhaCriarconta);//controller cadastra a conta
						s = "Criar conta " + cpfcnpj;//log
						result.add("concluido");//envia concluido
						enviarResultado.setLista(result);//envio o resultado
						saida.writeObject(enviarResultado);//envia o pacote
					}catch(CampoVazioException e){//caso tenha algum campo vazio
						result.add("camponaopreenchido");//envia erro
						enviarResultado.setLista(result);//envio o resultado
						saida.writeObject(enviarResultado);//envia o pacote
					}catch(FileNotFoundException e){//caso o cliente não seja encontrado
						result.add("clientenaoencontrado");//envia erro
						enviarResultado.setLista(result);//envio o resultado
						saida.writeObject(enviarResultado);//envia o pacote
					}catch(SenhaInvalidaException e){//caso a senha do cliente seja invalida
						result.add("senhainvalida");//envia erro
						enviarResultado.setLista(result);//envio o resultado
						saida.writeObject(enviarResultado);//envia o pacote
					}catch(ClientesNullException e){//caso o cliente seja nulo
						result.add("clientenulo");//envia erro
						enviarResultado.setLista(result);//envio o resultado
						saida.writeObject(enviarResultado);//envia o pacote
					}catch(ContaConflitoException e){//caso a conta seja juridica e não seja corrente
						result.add("clienteJuridicoContaCorrente");//envia erro
						enviarResultado.setLista(result);//envio o resultado
						saida.writeObject(enviarResultado);//envia objeto
					}
					saida.flush();
					break;
				case 5://acessar conta
					String numConta = itera.next();//recebe informações
					String agencia = itera.next();
					String cpfcnpjConta = itera.next();
					try{
						Conta contaAcesso = controller.getConta(numConta, agencia);//controller recupera a cota
						
						List<String> clientes = contaAcesso.getClientes();//lista declientes da conta
						Iterator<String> iterando = clientes.iterator();
						boolean existe = false;//verificador se a conta atual tem o cliente que solicitou
						String atual = "";
						while(iterando.hasNext()){//busca o clitente na lista
							atual = iterando.next();
							if(atual.equals(cpfcnpjConta)){
								existe = true;
							}
						}
						
						if(existe == true){//se existir
							s = "Acessar conta " + numConta;//log
							result.add("encontrado");//envia o resultado e as informações da conta
							result.add(contaAcesso.getSaldo().toString());
							enviarResultado.setB(contaAcesso.isCorrent());
							enviarResultado.setC(contaAcesso.isFisica());
							enviarResultado.setLista(result);//envio o resultado
							saida.writeObject(enviarResultado);//envia o pacote
						}else{
							result.add("containvalidacliente");//envia erro de conta invalida para o cliente
							enviarResultado.setLista(result);//envio o resultado
							saida.writeObject(enviarResultado);//envia o pacote
						}
						
					}catch(CampoVazioException e){//caso tenha algum campo vazio
						result.add("camponaopreenchido");//enviao erro
						enviarResultado.setLista(result);//envio o resultado
						saida.writeObject(enviarResultado);//envia o pacote
					}catch(FileNotFoundException e){//caso não encontre a conta
						result.add("contanaoencontrada");//envia erro
						enviarResultado.setLista(result);//envio o resultado
						saida.writeObject(enviarResultado);//envia o pacote
					}catch(AgenciaIncorretaException e){//caso a agencia seja incorreta
						result.add("agenciaincorreta");//envia erro
						enviarResultado.setLista(result);//envio o resultado
						saida.writeObject(enviarResultado);//envia o pacote 
					}
					saida.flush();
					break;
				case 6://depositar conta
					String numContaDeposito = itera.next();////recebe informações
					String agenciaDeposito = itera.next();
					String valorS = itera.next();
					Double valor = null;
					Double valortotal = null;
					try{
						valor = Double.parseDouble(valorS);//transforma a string em Double
						valortotal = controller.recebeDeposito(numContaDeposito, agenciaDeposito, valor);//deposita o valor, e retorna o valor atual da conta
						result.add("concluido");//envia conclido
						result.add(valortotal.toString());//envia o valor
						enviarResultado.setLista(result);//envio o resultado
						saida.writeObject(enviarResultado);//envia o pacote
						s = "Depositar " + numContaDeposito;//log
					}catch(NumberFormatException e){//caso não seja um numero no valor
						result.add("erroDouble");//envia erro
						enviarResultado.setLista(result);//envio o resultado
						saida.writeObject(enviarResultado);//envia o pacote
					}catch(FileNotFoundException e){//caso a conta não seja encontrada
						result.add("contanaoencontrada");//envia erro
						enviarResultado.setLista(result);//envio o resultado
						saida.writeObject(enviarResultado);//envia o pacote
					}catch(CampoVazioException e){//caso algum campo esteja vazio
						result.add("camponaopreenchido");//envia erro
						enviarResultado.setLista(result);//envio o resultado
						saida.writeObject(enviarResultado);//envia o pacote
					}catch(ClienteNaoEncontradoException e){//caso o cliente não seja encontrado
						result.add("clientenaoencontrado");//envia erro
						enviarResultado.setLista(result);//envio o resultado
						saida.writeObject(enviarResultado);//envia o pacote
					}catch(AgenciaIncorretaException e){//caso agencia esteja incorreta
						result.add("agenciaincorreta");//envia erro
						enviarResultado.setLista(result);//envio o resultado
						saida.writeObject(enviarResultado);//envia pacote
					}
					break;
				case 7://transferir
					//recupera informações
					String numConta1 = itera.next();//1 é o remetente
					String agenciaConta1 = itera.next();
					String numConta2 = itera.next();//2 é o destinatario
					String agenciaConta2 = itera.next();
					String valorST = itera.next();
					String cpfCnpjTransfer = itera.next();
					Double valorT;
					Double valorTR;
					
					try{
						valorT = Double.parseDouble(valorST);//transforma a string em double
						valorTR = controller.recebeTransacao(numConta1, agenciaConta1, numConta2, agenciaConta2, valorT, cpfCnpjTransfer);//controller realiza a transferencia
						result.add("concluido");//envia concluido
						result.add(valorTR.toString());//envia o valor atual do remetente
						enviarResultado.setLista(result);//envio o resultado
						saida.writeObject(enviarResultado);//envia o pacote
						s = "Transferir " + numConta1 + "\\" + numConta2;//log
					}catch(FileNotFoundException e){//caso a conta não seja encontrada
						result.add("contanaoencontrada");//envia erro
						enviarResultado.setLista(result);//envio o resultado
						saida.writeObject(enviarResultado);//envia pacote
					}catch(CampoVazioException e){//caso tenha algum campo vazio
						result.add("camponaopreenchido");//envia erro
						enviarResultado.setLista(result);//envio o resultado
						saida.writeObject(enviarResultado);//envia pacote
					}catch(ClienteNaoEncontradoException e){//caso não encontre o cliente
						result.add("clientenaoencontrado");//envia erro
						enviarResultado.setLista(result);//envio o resultado
						saida.writeObject(enviarResultado);//envia pacote
					}catch(AgenciaIncorretaException e){//caso a agencia esteja incorreta
						result.add("agenciaincorreta");//envia erro
						enviarResultado.setLista(result);//envio o resultado
						saida.writeObject(enviarResultado);//envia pacote
					}catch(ContaConflitoException e){//caso a conta esteja com conflito
						result.add("conflitonaconta");//envia erro
						enviarResultado.setLista(result);//envio o resultado
						saida.writeObject(enviarResultado);//envia pacote
					}catch(SaldoInsuficienteException e){//caso o saldo seja insuficiente
						result.add("saldoinsuficiente");//envia erro
						enviarResultado.setLista(result);//envio o resultado
						saida.writeObject(enviarResultado);//envia pacote
					}
					break;
				case 8://adicionar titular
					String numContaAdd = itera.next();//recupera informações
					String agenciaAdd = itera.next();
					String cpfCnpjExistente = itera.next();
					String cpfCnpjNovo = itera.next();
					try{
						controller.addClienteConta(numContaAdd, agenciaAdd, cpfCnpjExistente, cpfCnpjNovo);//controller adiciona titular na conta
						result.add("concluido");//envia concluido
						enviarResultado.setLista(result);//envio o resultado
						saida.writeObject(enviarResultado);//envia pacote
						s = "AdicionarConta " + numContaAdd + "\\" + cpfCnpjNovo;//log
					}catch(FileNotFoundException e){//Caso a conta não seja encontrada
						result.add("contaonaoencontrada");//envia erro
						enviarResultado.setLista(result);//envio o resultado
						saida.writeObject(enviarResultado);//envia pacote
					}catch(CampoVazioException e){//caso campo seja vazio
						result.add("camponaopreenchido");//envia erro
						enviarResultado.setLista(result);//envio o resultado
						saida.writeObject(enviarResultado);//eniva pacote
					}catch(AgenciaIncorretaException e){//caso a agencia esteja incorreta
						result.add("agenciaincorreta");//envia erro
						enviarResultado.setLista(result);//envio o resultado
						saida.writeObject(enviarResultado);//envia pacote
					}catch(ClienteNaoEncontradoException e){//caso o cliente não seja encontrado
						result.add("clientenaoencontrado");//envia erro
						enviarResultado.setLista(result);//envio o resultado
						saida.writeObject(enviarResultado);//envia pacote
					}catch(ContaConflitoException e){//caso a conta esteja em conflito
						result.add("titularjacadastrado");//titular ja cadastrado envia erro
						enviarResultado.setLista(result);//envio o resultado
						saida.writeObject(enviarResultado);//envia pacote
					}
					
					break;
				}
				System.out.println("\nCliente atendido com sucesso: " + s + cliente.getRemoteSocketAddress().toString());
				textField.setText(textField.getText() + "\nCliente atendido com sucesso: " + s + cliente.getRemoteSocketAddress().toString());//coloca o log no textArea

				entrada.close();//finaliza a entrada
				saida.close();//finaliza a saida
				cliente.close();//fecha o cliente
			//}   
		}

		catch(Exception e) {//caso alguma exceção seja lançada
			System.out.println("Excecao ocorrida na thread: " + e.getMessage());
			textField.setText(textField.getText() + "\nExcecao ocorrida na thread: " + e.getMessage());//caso alguma exceção desconheciada seja lançada ela encerra a thread e é exibida
			try {
				cliente.close();   //finaliza o cliente
			}catch(Exception ec) {
				textField.setText(textField.getText() + "\nErro fatal cliente não finalizado: " + e.getMessage());//cliente não foi finalizado
			}     
		}
	}
}
