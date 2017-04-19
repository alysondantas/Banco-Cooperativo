package br.uefs.ecomp.bancoCooperativo.controller;

import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JTextArea;

import br.uefs.ecomp.bancoCooperativo.controller.thread.ThreadCliente;
import br.uefs.ecomp.bancoCooperativo.controller.thread.ThreadGUI;

/**
 * 
 * @author Alyson Dantas
 *
 */
public class ControllerServer {
	//private ControllerDadosServer controller;
	private ServerSocket server;
	private ThreadCliente thread;
	private static ControllerServer unicaInstancia;
	
	private ControllerServer(){
		//controller.getInstance();
	}
	/**
	 * controla o instanciamento de objetos Controller
	 * @return unicaInstancia
	 */
	public static synchronized ControllerServer getInstance(){
		if(unicaInstancia==null){
			unicaInstancia = new ControllerServer();
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
	 * Metodo que inicia o servidor
	 * @param port
	 * @param textField
	 * @return
	 */
	public String iniciaServer(int port, JTextArea textField){
		try {     
	        System.out.println("Incializando o servidor...");
	        textField.setText(textField.getText() + "Incializando o servidor... \n");
	        
	        server = new ServerSocket(port);//instancia um socket server na porta desejada
	        System.out.println("Servidor iniciado, ouvindo a porta " + port);
	        textField.setText(textField.getText() + "Servidor iniciado, ouvindo a porta " + port);//indica que o servidor foi ligado em determinada porta
	        
	        ThreadGUI threadGUI = new ThreadGUI(thread, textField, server);//thread que permite a atualização periodica da GUI
	        threadGUI.start();
	        
	        /*while(true){
	        	Socket cliente = server.accept();
		        thread = new ThreadCliente(server, textField, cliente);//passa parametros para thread como o socket server e a textArea
		        thread.start();//inicia a thread
	        }*/
	        
	         
	     }catch(Exception e){
	    	 e.printStackTrace();//exibe a exceção que foi lançada
	    	 textField.setText(textField.getText() + "Excecao ocorrida ao criar thread: " + e);//caso alguma exceção desconheciada seja lançada ela encerra a thread e é exibida
	     }
		
		return null;
	}
}
