package br.uefs.ecomp.bancoCooperativo.controller.thread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JTextArea;

/**
 * 
 * @author Alyson Dantas
 *
 */
public class ThreadGUI extends Thread {

	private JTextArea textField;//para atualizar a interface
	private ThreadCliente thread;
	private ServerSocket server;
	
	/**
	 * Construtor
	 * @param thread
	 * @param textField
	 * @param server
	 */
	public ThreadGUI(ThreadCliente thread, JTextArea textField,ServerSocket server){
		this.textField = textField;
		this.thread = thread;
		this.server = server;
	}
	
	/**
	 * Metodo Run da thread
	 */
	public void run(){
		while(true){
        	Socket cliente = null;
			try {
				cliente = server.accept();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(cliente!=null){//caso o cliente não seja nulo
				thread = new ThreadCliente(server, textField, cliente);//passa parametros para thread como o socket server e a textArea
		        thread.start();//inicia a thread
			}else{
				System.out.println("erro cliente nulo");
			}
        }
	}

}
