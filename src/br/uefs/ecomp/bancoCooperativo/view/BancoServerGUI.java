package br.uefs.ecomp.bancoCooperativo.view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JTextField;

import br.uefs.ecomp.bancoCooperativo.controller.ControllerServer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

/**
 * 
 * @author Alyson Dantas
 *
 */
public class BancoServerGUI {

	private JFrame frmBancoServer;
	public JTextArea textField;
	private JButton btnStartServer;
	private JLabel lblPorta;
	private JTextField textFieldPorta;
	private ControllerServer controller = ControllerServer.getInstance();
	private JScrollPane scrollPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BancoServerGUI window = new BancoServerGUI();
					window.frmBancoServer.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public BancoServerGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmBancoServer = new JFrame();
		frmBancoServer.setTitle("Banco Server");
		frmBancoServer.setBounds(100, 100, 450, 300);
		frmBancoServer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmBancoServer.getContentPane().setLayout(null);
		
		btnStartServer = new JButton("Start Server");
		btnStartServer.setBounds(309, 204, 115, 23);
		frmBancoServer.getContentPane().add(btnStartServer);
		
		lblPorta = new JLabel("Porta:");
		lblPorta.setBounds(10, 204, 46, 14);
		frmBancoServer.getContentPane().add(lblPorta);
		
		textFieldPorta = new JTextField();
		textFieldPorta.setBounds(46, 204, 86, 20);
		frmBancoServer.getContentPane().add(textFieldPorta);
		textFieldPorta.setColumns(10);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 414, 182);
		frmBancoServer.getContentPane().add(scrollPane);
		
		textField = new JTextArea();
		scrollPane.setViewportView(textField);
		textField.setEditable(false);
		textField.setColumns(10);
		
		btnStartServer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				iniciaServer();
			}
		});
	}
	
	/**
	 * Metodo que incia o servidor
	 */
	private void iniciaServer(){
		if(controller != null){//se o controller existir
			btnStartServer.setEnabled(false);//desabilita o botão de inciar
			int i = 1099;//porta padrão
			String portaS = textFieldPorta.getText();//recupera a porta do usuario
			try{
				i = Integer.parseInt(portaS);//converte a string em int
			}catch(NumberFormatException e){
				textField.setText("Erro ao digitar porta, porta escolhida padrao: 1099\n");//caso não seja valida a porta avisa ao usuario e usa a porta padrão
			}
			controller.iniciaServer(i, textField);//inicia o servidor
		}
	}
}
