package br.uefs.ecomp.bancoCooperativo.view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import br.uefs.ecomp.bancoCooperativo.model.Cliente;
import br.uefs.ecomp.bancoCooperativo.model.Endereco;
import br.uefs.ecomp.bancoCooperativo.model.Pacote;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JCheckBox;

public class BancoClientGUI {

	private JFrame frame;
	private JTextField textFieldAcessoCPFCNPJ;
	private JPasswordField passwordAcessoSenha;
	private JTextField textFieldCadFisicaNome;
	private JTextField textFieldCadFisicaData;
	private JTextField textFieldCadFisicaCpf;
	private JRadioButton rdbtnMasculino;
	private final ButtonGroup sexo = new ButtonGroup();
	private final ButtonGroup sexoEdit = new ButtonGroup();
	private final ButtonGroup tipoConta = new ButtonGroup();
	private JTextField textFieldCadFisicaRua;
	private JTextField textFieldCadFisicaUf;
	private JTextField textFieldCadFisicaCidade;
	private JTextField textFieldCadFisicaNum;
	private JPasswordField passwordFieldCadFisicaSenha;
	private JTextField textFieldipservidor;
	private JTextField textFieldportaservidor;
	private JTabbedPane tabbedPaneAreaRestrita;
	private int porta = 1099;
	private String ip = "192.168.22.100";
	private JTextField textFieldEditFisicaNome;
	private JTextField textFieldEditFisicaData;
	private JTextField textFieldEditFisicaUF;
	private JTextField textFieldEditFisicaCidade;
	private JTextField textFieldEditFisicaNum;
	private JTextField textFieldEditFisicaRua;
	private JRadioButton radioButtonEditFisicaM;
	private JRadioButton radioButtonEditFisicaF;
	private JButton btnAcessar;
	private JButton btnSair;
	private JButton btnAlterar;
	private JPasswordField passwordFieldNovaSenha;
	private JTextField textFieldCadCNPJJuri;
	private JTextField textFieldCadNomeJuri;
	private JTextField textFieldCadDataJuri;
	private JTextField textFieldCadCidadeJuri;
	private JTextField textFieldCadNumJuri;
	private JPasswordField passwordFieldCadJuri;
	private JTextField textFieldCadRuaJuri;
	private JTextField textFieldCadUFJuri;
	private JButton btnSolicitarCadastroJuridica;
	private JLabel labelAcessoSexo;
	private JTextField textFieldCadContaCPFCNPJ;
	private JTextField textFieldNumDep;
	private JTextField textFieldValorDep;
	private JTextField textFieldAgenciaDep;
	private JTextField textFieldAcessoNumConta;
	private JTextField textFieldAcessoAgencia;
	private JTextField textFieldAcessoSaldo;
	private JTextField textFieldNumConta1;
	private JTextField textFieldAgenciaConta1;
	private JTextField textField_9;
	private JTextField textFieldAgenciaConta2;
	private JTextField textFieldNumConta2;
	private JRadioButton rdbtnCorrente;
	private JPasswordField passwordCadConta;
	private JTextArea textAreaAcessoContas;
	private JRadioButton rdbtnCorrenteAcesso;
	private JRadioButton rdbtnPoupanaAcesso;
	private JRadioButton rdbtnFisicaAcesso;
	private JRadioButton rdbtnJuridicaAcesso;
	private JTextField textFieldTransferenciaValor;
	private JButton btnAdicionarTitular;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BancoClientGUI window = new BancoClientGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public BancoClientGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		String nome = System.getProperty("os.name");//recupera o nome do SO
		if(nome.substring(0, 7).equals("Windows")){//se ele for WINDOWS é colocado um LookAndFeel do windows para rodar melhorar a aparencia
			try { 
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (UnsupportedLookAndFeelException ex) {
				ex.printStackTrace();
			} catch (IllegalAccessException ex) {
				ex.printStackTrace();
			} catch (InstantiationException ex) {
				ex.printStackTrace();
			} catch (ClassNotFoundException ex) {
				ex.printStackTrace();
			}
		}
		frame = new JFrame();
		frame.setTitle("BancoCooperativo Cliente");
		frame.setResizable(false);
		frame.setBounds(100, 100, 552, 379);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 536, 340);
		frame.getContentPane().add(tabbedPane);

		JPanel acesso = new JPanel();
		tabbedPane.addTab("Seu Acesso", null, acesso, null);
		acesso.setLayout(null);

		JLabel lblNewLabel = new JLabel("CPF/CNPJ:");
		lblNewLabel.setBounds(10, 11, 57, 14);
		acesso.add(lblNewLabel);

		textFieldAcessoCPFCNPJ = new JTextField();
		textFieldAcessoCPFCNPJ.setBounds(63, 8, 107, 20);
		acesso.add(textFieldAcessoCPFCNPJ);
		textFieldAcessoCPFCNPJ.setColumns(10);

		JLabel lblSenha = new JLabel("Senha:");
		lblSenha.setBounds(190, 11, 34, 14);
		acesso.add(lblSenha);

		passwordAcessoSenha = new JPasswordField();
		passwordAcessoSenha.setBounds(226, 8, 117, 20);
		acesso.add(passwordAcessoSenha);

		btnAcessar = new JButton("ACESSAR");
		btnAcessar.setBounds(368, 7, 89, 23);
		acesso.add(btnAcessar);
		btnAcessar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				acessarCliente();
			}
		});

		tabbedPaneAreaRestrita = new JTabbedPane(JTabbedPane.TOP);
		tabbedPaneAreaRestrita.setEnabled(false);
		tabbedPaneAreaRestrita.setBounds(10, 49, 511, 252);
		acesso.add(tabbedPaneAreaRestrita);

		JPanel panel_1 = new JPanel();
		tabbedPaneAreaRestrita.addTab("Dados", null, panel_1, null);
		panel_1.setLayout(null);

		textFieldEditFisicaNome = new JTextField();
		textFieldEditFisicaNome.setEnabled(false);
		textFieldEditFisicaNome.setColumns(10);
		textFieldEditFisicaNome.setBounds(42, 14, 168, 20);
		panel_1.add(textFieldEditFisicaNome);

		JLabel label = new JLabel("Nome:");
		label.setBounds(10, 17, 39, 14);
		panel_1.add(label);

		labelAcessoSexo = new JLabel("Sexo:");
		labelAcessoSexo.setBounds(220, 42, 39, 14);
		panel_1.add(labelAcessoSexo);

		radioButtonEditFisicaM = new JRadioButton("Masculino");
		sexoEdit.add(radioButtonEditFisicaM);
		radioButtonEditFisicaM.setEnabled(false);
		radioButtonEditFisicaM.setSelected(true);
		radioButtonEditFisicaM.setBounds(255, 38, 71, 23);
		panel_1.add(radioButtonEditFisicaM);

		radioButtonEditFisicaF = new JRadioButton("Feminino");
		sexoEdit.add(radioButtonEditFisicaF);
		radioButtonEditFisicaF.setEnabled(false);
		radioButtonEditFisicaF.setBounds(323, 38, 109, 23);
		panel_1.add(radioButtonEditFisicaF);

		textFieldEditFisicaData = new JTextField();
		textFieldEditFisicaData.setEnabled(false);
		textFieldEditFisicaData.setColumns(10);
		textFieldEditFisicaData.setBounds(261, 14, 117, 20);
		panel_1.add(textFieldEditFisicaData);

		JLabel lblData = new JLabel("Data:");
		lblData.setBounds(220, 14, 106, 14);
		panel_1.add(lblData);

		textFieldEditFisicaUF = new JTextField();
		textFieldEditFisicaUF.setEnabled(false);
		textFieldEditFisicaUF.setColumns(10);
		textFieldEditFisicaUF.setBounds(42, 92, 86, 20);
		panel_1.add(textFieldEditFisicaUF);

		JLabel label_3 = new JLabel("Endere\u00E7o:");
		label_3.setBounds(10, 67, 71, 14);
		panel_1.add(label_3);

		JLabel label_4 = new JLabel("UF:");
		label_4.setBounds(10, 95, 23, 14);
		panel_1.add(label_4);

		textFieldEditFisicaCidade = new JTextField();
		textFieldEditFisicaCidade.setEnabled(false);
		textFieldEditFisicaCidade.setColumns(10);
		textFieldEditFisicaCidade.setBounds(261, 95, 158, 20);
		panel_1.add(textFieldEditFisicaCidade);

		JLabel label_5 = new JLabel("Cidade:");
		label_5.setBounds(220, 98, 46, 14);
		panel_1.add(label_5);

		JLabel label_6 = new JLabel("Num\u00BA:");
		label_6.setBounds(220, 123, 46, 14);
		panel_1.add(label_6);

		textFieldEditFisicaNum = new JTextField();
		textFieldEditFisicaNum.setEnabled(false);
		textFieldEditFisicaNum.setColumns(10);
		textFieldEditFisicaNum.setBounds(261, 120, 158, 20);
		panel_1.add(textFieldEditFisicaNum);

		textFieldEditFisicaRua = new JTextField();
		textFieldEditFisicaRua.setEnabled(false);
		textFieldEditFisicaRua.setColumns(10);
		textFieldEditFisicaRua.setBounds(42, 117, 86, 20);
		panel_1.add(textFieldEditFisicaRua);

		JLabel label_7 = new JLabel("Rua:");
		label_7.setBounds(10, 120, 23, 14);
		panel_1.add(label_7);

		btnSair = new JButton("SAIR");
		btnSair.setEnabled(false);
		btnSair.setBounds(10, 190, 89, 23);
		btnSair.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				sairEdicao();
			}
		});
		panel_1.add(btnSair);

		btnAlterar = new JButton("ALTERAR");
		btnAlterar.setEnabled(false);
		btnAlterar.setBounds(407, 190, 89, 23);
		panel_1.add(btnAlterar);

		JLabel lblNovaSenha = new JLabel("Nova Senha:");
		lblNovaSenha.setBounds(188, 148, 71, 14);
		panel_1.add(lblNovaSenha);

		passwordFieldNovaSenha = new JPasswordField();
		passwordFieldNovaSenha.setEnabled(false);
		passwordFieldNovaSenha.setBounds(261, 145, 158, 20);
		panel_1.add(passwordFieldNovaSenha);
		btnAlterar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				alterarCliente();
			}
		});

		JPanel panel_3 = new JPanel();
		tabbedPaneAreaRestrita.addTab("Conta", null, panel_3, null);
		panel_3.setLayout(null);

		JLabel lblNumeroContaagencia = new JLabel("Numero Conta\\Agencia");
		lblNumeroContaagencia.setBounds(10, 23, 119, 14);
		panel_3.add(lblNumeroContaagencia);

		JLabel lblNewLabel_1 = new JLabel("Suas Contas:");
		lblNewLabel_1.setBounds(10, 9, 71, 14);
		panel_3.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Num\u00BA da Conta:");
		lblNewLabel_2.setBounds(175, 9, 77, 14);
		panel_3.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("Agencia:");
		lblNewLabel_3.setBounds(175, 35, 46, 14);
		panel_3.add(lblNewLabel_3);

		textFieldAcessoNumConta = new JTextField();
		textFieldAcessoNumConta.setBounds(259, 6, 86, 20);
		panel_3.add(textFieldAcessoNumConta);
		textFieldAcessoNumConta.setColumns(10);

		textFieldAcessoAgencia = new JTextField();
		textFieldAcessoAgencia.setBounds(259, 32, 86, 20);
		panel_3.add(textFieldAcessoAgencia);
		textFieldAcessoAgencia.setColumns(10);

		JButton btnAcessarConta = new JButton("ACESSAR");
		btnAcessarConta.setBounds(366, 5, 89, 23);
		panel_3.add(btnAcessarConta);
		btnAcessarConta.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				acessarConta();
			}
		});

		JLabel lblSaldo = new JLabel("Saldo:");
		lblSaldo.setBounds(175, 76, 37, 14);
		panel_3.add(lblSaldo);

		textFieldAcessoSaldo = new JTextField();
		textFieldAcessoSaldo.setBounds(259, 73, 86, 20);
		textFieldAcessoSaldo.setEnabled(false);
		textFieldAcessoSaldo.setEditable(false);
		panel_3.add(textFieldAcessoSaldo);
		textFieldAcessoSaldo.setColumns(10);

		JLabel lblR = new JLabel("R$:");
		lblR.setBounds(235, 76, 17, 14);
		panel_3.add(lblR);

		btnAdicionarTitular = new JButton("Adicionar Titular");
		btnAdicionarTitular.setBounds(366, 72, 109, 23);
		btnAdicionarTitular.setEnabled(false);
		panel_3.add(btnAdicionarTitular);
		btnAdicionarTitular.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				addTitular();
			}
		});

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 48, 155, 108);
		panel_3.add(scrollPane);

		textAreaAcessoContas = new JTextArea();
		scrollPane.setViewportView(textAreaAcessoContas);
		textAreaAcessoContas.setEditable(false);

		rdbtnCorrenteAcesso = new JRadioButton("Corrente");
		rdbtnCorrenteAcesso.setEnabled(false);
		rdbtnCorrenteAcesso.setBounds(259, 106, 71, 23);
		panel_3.add(rdbtnCorrenteAcesso);

		rdbtnPoupanaAcesso = new JRadioButton("Poupan\u00E7a");
		rdbtnPoupanaAcesso.setEnabled(false);
		rdbtnPoupanaAcesso.setBounds(332, 106, 77, 23);
		panel_3.add(rdbtnPoupanaAcesso);

		rdbtnFisicaAcesso = new JRadioButton("Fisica");
		rdbtnFisicaAcesso.setEnabled(false);
		rdbtnFisicaAcesso.setBounds(259, 132, 71, 23);
		panel_3.add(rdbtnFisicaAcesso);

		rdbtnJuridicaAcesso = new JRadioButton("Juridica");
		rdbtnJuridicaAcesso.setEnabled(false);
		rdbtnJuridicaAcesso.setBounds(332, 132, 77, 23);
		panel_3.add(rdbtnJuridicaAcesso);

		JPanel panel_2 = new JPanel();
		tabbedPaneAreaRestrita.addTab("Transferencia", null, panel_2, null);
		panel_2.setLayout(null);

		JLabel lblContaRemetente = new JLabel("Conta Remetente:");
		lblContaRemetente.setBounds(10, 11, 95, 14);
		panel_2.add(lblContaRemetente);

		textFieldNumConta1 = new JTextField();
		textFieldNumConta1.setBounds(75, 36, 74, 20);
		panel_2.add(textFieldNumConta1);
		textFieldNumConta1.setColumns(10);

		JLabel lblNumConta = new JLabel("Num\u00BA Conta:");
		lblNumConta.setBounds(10, 36, 86, 14);
		panel_2.add(lblNumConta);

		JLabel lblAgencia_1 = new JLabel("Agencia:");
		lblAgencia_1.setBounds(10, 70, 46, 14);
		panel_2.add(lblAgencia_1);

		textFieldAgenciaConta1 = new JTextField();
		textFieldAgenciaConta1.setBounds(75, 67, 74, 20);
		panel_2.add(textFieldAgenciaConta1);
		textFieldAgenciaConta1.setColumns(10);

		textField_9 = new JTextField();
		textField_9.setEnabled(false);
		textField_9.setEditable(false);
		textField_9.setBounds(246, 11, 5, 205);
		panel_2.add(textField_9);
		textField_9.setColumns(10);

		textFieldAgenciaConta2 = new JTextField();
		textFieldAgenciaConta2.setColumns(10);
		textFieldAgenciaConta2.setBounds(342, 67, 74, 20);
		panel_2.add(textFieldAgenciaConta2);

		textFieldNumConta2 = new JTextField();
		textFieldNumConta2.setColumns(10);
		textFieldNumConta2.setBounds(342, 36, 74, 20);
		panel_2.add(textFieldNumConta2);

		JLabel label_1 = new JLabel("Num\u00BA Conta:");
		label_1.setBounds(277, 36, 86, 14);
		panel_2.add(label_1);

		JLabel label_8 = new JLabel("Agencia:");
		label_8.setBounds(277, 70, 46, 14);
		panel_2.add(label_8);

		JLabel lblContaDestinatario = new JLabel("Conta Destinatario:");
		lblContaDestinatario.setBounds(277, 11, 95, 14);
		panel_2.add(lblContaDestinatario);

		JButton btnTransferir = new JButton("Transferir");
		btnTransferir.setBounds(327, 174, 89, 23);
		panel_2.add(btnTransferir);

		JLabel lblValor_1 = new JLabel("Valor:");
		lblValor_1.setBounds(10, 106, 46, 14);
		panel_2.add(lblValor_1);

		textFieldTransferenciaValor = new JTextField();
		textFieldTransferenciaValor.setColumns(10);
		textFieldTransferenciaValor.setBounds(75, 103, 74, 20);
		panel_2.add(textFieldTransferenciaValor);
		btnTransferir.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				transferir();
			}
		});

		JPanel paneltransacao = new JPanel();
		tabbedPane.addTab("Transações", null, paneltransacao, null);
		paneltransacao.setLayout(null);

		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_1.setBounds(0, 0, 531, 312);
		paneltransacao.add(tabbedPane_1);

		JPanel deposito = new JPanel();
		tabbedPane_1.addTab("Deposito", null, deposito, null);
		deposito.setLayout(null);

		JLabel lblConta_1 = new JLabel("Numero da Conta:");
		lblConta_1.setBounds(10, 11, 89, 14);
		deposito.add(lblConta_1);

		textFieldNumDep = new JTextField();
		textFieldNumDep.setBounds(100, 8, 86, 20);
		deposito.add(textFieldNumDep);
		textFieldNumDep.setColumns(10);

		JLabel lblValor = new JLabel("Valor R$:");
		lblValor.setBounds(10, 36, 49, 14);
		deposito.add(lblValor);

		textFieldValorDep = new JTextField();
		textFieldValorDep.setBounds(100, 33, 86, 20);
		deposito.add(textFieldValorDep);
		textFieldValorDep.setColumns(10);

		JButton btnDepositar = new JButton("Depositar");
		btnDepositar.setBounds(10, 77, 89, 23);
		deposito.add(btnDepositar);
		btnDepositar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				sairEdicao();
				depositar();
			}
		});

		JLabel lblAgencia = new JLabel("Agencia:");
		lblAgencia.setBounds(231, 11, 46, 14);
		deposito.add(lblAgencia);

		textFieldAgenciaDep = new JTextField();
		textFieldAgenciaDep.setBounds(276, 8, 86, 20);
		deposito.add(textFieldAgenciaDep);
		textFieldAgenciaDep.setColumns(10);

		JPanel cadastrocliente = new JPanel();
		tabbedPane.addTab("Cadastrar Cliente", null, cadastrocliente, null);
		cadastrocliente.setLayout(null);

		JTabbedPane tabbedPane_2 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_2.setBounds(0, 0, 531, 312);
		cadastrocliente.add(tabbedPane_2);

		JPanel pfisica = new JPanel();
		tabbedPane_2.addTab("Fisica", null, pfisica, null);
		pfisica.setLayout(null);

		JLabel lblNome = new JLabel("Nome:");
		lblNome.setBounds(10, 17, 39, 14);
		pfisica.add(lblNome);

		textFieldCadFisicaNome = new JTextField();
		textFieldCadFisicaNome.setBounds(42, 14, 168, 20);
		pfisica.add(textFieldCadFisicaNome);
		textFieldCadFisicaNome.setColumns(10);

		JLabel lblDataDeNascimento = new JLabel("Data de nascimento:");
		lblDataDeNascimento.setBounds(218, 17, 106, 14);
		pfisica.add(lblDataDeNascimento);

		textFieldCadFisicaData = new JTextField();
		textFieldCadFisicaData.setBounds(323, 14, 117, 20);
		pfisica.add(textFieldCadFisicaData);
		textFieldCadFisicaData.setColumns(10);

		JLabel lblCpf = new JLabel("CPF:");
		lblCpf.setBounds(10, 42, 23, 14);
		pfisica.add(lblCpf);

		textFieldCadFisicaCpf = new JTextField();
		textFieldCadFisicaCpf.setBounds(42, 39, 168, 20);
		pfisica.add(textFieldCadFisicaCpf);
		textFieldCadFisicaCpf.setColumns(10);

		JLabel lblSexo = new JLabel("Sexo:");
		lblSexo.setBounds(10, 70, 39, 14);
		pfisica.add(lblSexo);

		rdbtnMasculino = new JRadioButton("Masculino");
		rdbtnMasculino.setSelected(true);
		sexo.add(rdbtnMasculino);
		rdbtnMasculino.setBounds(45, 66, 71, 23);
		pfisica.add(rdbtnMasculino);

		JRadioButton rdbtnFeminino = new JRadioButton("Feminino");
		sexo.add(rdbtnFeminino);
		rdbtnFeminino.setBounds(113, 66, 109, 23);
		pfisica.add(rdbtnFeminino);

		JLabel lblRua = new JLabel("Rua:");
		lblRua.setBounds(230, 155, 23, 14);
		pfisica.add(lblRua);

		textFieldCadFisicaRua = new JTextField();
		textFieldCadFisicaRua.setBounds(262, 152, 157, 20);
		pfisica.add(textFieldCadFisicaRua);
		textFieldCadFisicaRua.setColumns(10);

		JLabel lblEndereo = new JLabel("Endere\u00E7o:");
		lblEndereo.setBounds(10, 96, 71, 14);
		pfisica.add(lblEndereo);

		JLabel lblUf = new JLabel("UF:");
		lblUf.setBounds(10, 124, 23, 14);
		pfisica.add(lblUf);

		textFieldCadFisicaUf = new JTextField();
		textFieldCadFisicaUf.setBounds(42, 121, 86, 20);
		pfisica.add(textFieldCadFisicaUf);
		textFieldCadFisicaUf.setColumns(10);

		JLabel lblCidade = new JLabel("Cidade:");
		lblCidade.setBounds(220, 127, 46, 14);
		pfisica.add(lblCidade);

		textFieldCadFisicaCidade = new JTextField();
		textFieldCadFisicaCidade.setBounds(261, 124, 158, 20);
		pfisica.add(textFieldCadFisicaCidade);
		textFieldCadFisicaCidade.setColumns(10);

		JLabel lblNum = new JLabel("Num\u00BA:");
		lblNum.setBounds(10, 152, 46, 14);
		pfisica.add(lblNum);

		textFieldCadFisicaNum = new JTextField();
		textFieldCadFisicaNum.setBounds(42, 149, 86, 20);
		pfisica.add(textFieldCadFisicaNum);
		textFieldCadFisicaNum.setColumns(10);

		JLabel lblSenhaDeAcesso = new JLabel("Senha de acesso:");
		lblSenhaDeAcesso.setBounds(220, 42, 86, 14);
		pfisica.add(lblSenhaDeAcesso);

		passwordFieldCadFisicaSenha = new JPasswordField();
		passwordFieldCadFisicaSenha.setBounds(323, 39, 117, 20);
		pfisica.add(passwordFieldCadFisicaSenha);

		JButton btnSolicitarCadastro = new JButton("Solicitar Cadastro");
		btnSolicitarCadastro.setBounds(10, 202, 118, 23);
		pfisica.add(btnSolicitarCadastro);
		btnSolicitarCadastro.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				sairEdicao();
				cadastrarClienteFisico();
			}
		});

		JPanel pjuridica = new JPanel();
		tabbedPane_2.addTab("Juridica", null, pjuridica, null);
		pjuridica.setLayout(null);

		textFieldCadCNPJJuri = new JTextField();
		textFieldCadCNPJJuri.setColumns(10);
		textFieldCadCNPJJuri.setBounds(42, 39, 168, 20);
		pjuridica.add(textFieldCadCNPJJuri);

		textFieldCadNomeJuri = new JTextField();
		textFieldCadNomeJuri.setColumns(10);
		textFieldCadNomeJuri.setBounds(42, 14, 168, 20);
		pjuridica.add(textFieldCadNomeJuri);

		JLabel label_2 = new JLabel("Nome:");
		label_2.setBounds(10, 17, 39, 14);
		pjuridica.add(label_2);

		JLabel lblCnpj = new JLabel("CNPJ:");
		lblCnpj.setBounds(10, 42, 39, 14);
		pjuridica.add(lblCnpj);

		JLabel lblDataDeFundao = new JLabel("Data de funda\u00E7\u00E3o:");
		lblDataDeFundao.setBounds(220, 17, 106, 14);
		pjuridica.add(lblDataDeFundao);

		textFieldCadDataJuri = new JTextField();
		textFieldCadDataJuri.setColumns(10);
		textFieldCadDataJuri.setBounds(323, 14, 117, 20);
		pjuridica.add(textFieldCadDataJuri);

		textFieldCadCidadeJuri = new JTextField();
		textFieldCadCidadeJuri.setColumns(10);
		textFieldCadCidadeJuri.setBounds(261, 95, 158, 20);
		pjuridica.add(textFieldCadCidadeJuri);

		JLabel label_10 = new JLabel("Cidade:");
		label_10.setBounds(220, 98, 46, 14);
		pjuridica.add(label_10);

		JLabel label_11 = new JLabel("Num\u00BA:");
		label_11.setBounds(10, 125, 46, 14);
		pjuridica.add(label_11);

		textFieldCadNumJuri = new JTextField();
		textFieldCadNumJuri.setColumns(10);
		textFieldCadNumJuri.setBounds(51, 122, 86, 20);
		pjuridica.add(textFieldCadNumJuri);

		passwordFieldCadJuri = new JPasswordField();
		passwordFieldCadJuri.setBounds(323, 39, 117, 20);
		pjuridica.add(passwordFieldCadJuri);

		JLabel label_12 = new JLabel("Senha de acesso:");
		label_12.setBounds(220, 42, 118, 14);
		pjuridica.add(label_12);

		textFieldCadRuaJuri = new JTextField();
		textFieldCadRuaJuri.setColumns(10);
		textFieldCadRuaJuri.setBounds(261, 122, 158, 20);
		pjuridica.add(textFieldCadRuaJuri);

		JLabel label_13 = new JLabel("Rua:");
		label_13.setBounds(230, 123, 23, 14);
		pjuridica.add(label_13);

		JLabel label_14 = new JLabel("UF:");
		label_14.setBounds(10, 95, 23, 14);
		pjuridica.add(label_14);

		textFieldCadUFJuri = new JTextField();
		textFieldCadUFJuri.setColumns(10);
		textFieldCadUFJuri.setBounds(51, 95, 86, 20);
		pjuridica.add(textFieldCadUFJuri);

		JLabel label_15 = new JLabel("Endere\u00E7o:");
		label_15.setBounds(10, 67, 71, 14);
		pjuridica.add(label_15);

		btnSolicitarCadastroJuridica = new JButton("Solicitar Cadastro");
		btnSolicitarCadastroJuridica.setBounds(10, 202, 118, 23);
		pjuridica.add(btnSolicitarCadastroJuridica);
		btnSolicitarCadastroJuridica.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				sairEdicao();
				cadastrarClienteJuridico();
			}
		});

		JPanel criarconta = new JPanel();
		tabbedPane.addTab("Criar Conta", null, criarconta, null);
		criarconta.setLayout(null);

		JLabel lblConta = new JLabel("Conta:");
		lblConta.setBounds(10, 11, 39, 14);
		criarconta.add(lblConta);

		rdbtnCorrente = new JRadioButton("Corrente");
		rdbtnCorrente.setSelected(true);
		rdbtnCorrente.setBounds(42, 7, 75, 23);
		criarconta.add(rdbtnCorrente);
		tipoConta.add(rdbtnCorrente);

		JRadioButton rdbtnPoupana = new JRadioButton("Poupan\u00E7a");
		rdbtnPoupana.setBounds(119, 7, 75, 23);
		criarconta.add(rdbtnPoupana);
		tipoConta.add(rdbtnPoupana);

		JLabel lblClientes = new JLabel("Cliente:");
		lblClientes.setBounds(10, 46, 46, 14);
		criarconta.add(lblClientes);

		textFieldCadContaCPFCNPJ = new JTextField();
		textFieldCadContaCPFCNPJ.setBounds(68, 72, 113, 20);
		criarconta.add(textFieldCadContaCPFCNPJ);
		textFieldCadContaCPFCNPJ.setColumns(10);

		JLabel lblCpfcnpj = new JLabel("CPF/CNPJ:");
		lblCpfcnpj.setBounds(10, 75, 57, 14);
		criarconta.add(lblCpfcnpj);

		JButton btnCriarConta = new JButton("Solicitar Cadastro");
		btnCriarConta.setBounds(10, 113, 118, 23);
		criarconta.add(btnCriarConta);

		JLabel lblSenha_1 = new JLabel("Senha:");
		lblSenha_1.setBounds(195, 75, 39, 14);
		criarconta.add(lblSenha_1);

		passwordCadConta = new JPasswordField();
		passwordCadConta.setBounds(233, 72, 113, 20);
		criarconta.add(passwordCadConta);
		btnCriarConta.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				sairEdicao();
				criarConta();
			}
		});

		JPanel panel = new JPanel();
		tabbedPane.addTab("Configuração", null, panel, null);
		panel.setLayout(null);

		JLabel lblIpDoServidor = new JLabel("IP do Servidor:");
		lblIpDoServidor.setBounds(10, 11, 79, 14);
		panel.add(lblIpDoServidor);

		textFieldipservidor = new JTextField();
		textFieldipservidor.setText("192.168.22.100");
		textFieldipservidor.setBounds(87, 8, 91, 20);
		panel.add(textFieldipservidor);
		textFieldipservidor.setColumns(10);

		JLabel lblPorta = new JLabel("Porta:");
		lblPorta.setBounds(246, 11, 46, 14);
		panel.add(lblPorta);

		textFieldportaservidor = new JTextField();
		textFieldportaservidor.setText("1099");
		textFieldportaservidor.setBounds(283, 8, 38, 20);
		panel.add(textFieldportaservidor);
		textFieldportaservidor.setColumns(10);

		JButton btnDefinir = new JButton("Definir");
		btnDefinir.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				definirConfig();
			}
		});
		btnDefinir.setBounds(10, 53, 89, 23);
		panel.add(btnDefinir);
	}

	/**
	 * Metodo de cadastrar cliente fisico
	 */
	private void cadastrarClienteFisico(){
		try {
			//Cria o Socket para buscar o arquivo no servidor 
			Socket rec = new Socket(ip,porta);

			//Enviando o nome do arquivo a ser baixado do servidor
			ObjectOutputStream saida = new ObjectOutputStream(rec.getOutputStream());
			Pacote pack = new Pacote();

			String nome = textFieldCadFisicaNome.getText();//obtem informações da interface
			String data = textFieldCadFisicaData.getText();
			String cpf = textFieldCadFisicaCpf.getText();
			boolean sexo;
			if(rdbtnMasculino.isSelected()){
				sexo = true;
			}else{
				sexo = false;
			}
			String uf = textFieldCadFisicaUf.getText();
			String cidade = textFieldCadFisicaCidade.getText();
			String rua = textFieldCadFisicaRua.getText();
			String num = textFieldCadFisicaNum.getText();
			String senha = passwordFieldCadFisicaSenha.getText();
			//JOptionPane.showMessageDialog(null,"Senha:" + senha,"OK",2);

			pack.setOpcao(0);//identifico o tipo de operação 0 = cadastrar cliente
			List<String> informacoes = new ArrayList<>();
			//envio todas as informações do cliente
			informacoes.add(nome);
			informacoes.add(data);
			informacoes.add(cpf);
			pack.setB(sexo);
			informacoes.add(uf);
			informacoes.add(cidade);
			informacoes.add(rua);
			informacoes.add(num);
			informacoes.add(senha);
			pack.setLista(informacoes);
			saida.writeObject(pack);
			saida.flush();

			ObjectInputStream entrada = new ObjectInputStream(rec.getInputStream());//recebo o pacote do cliente
			String recebido = (String) entrada.readObject(); 
			saida.close();//fecha a comunicação com o servidor
			entrada.close();
			rec.close();

			if(recebido.equals("concluido")){//se receber concluido
				JOptionPane.showMessageDialog(null, "Cliente Cadastrado!");
			}else if(recebido.equals("camponaopreenchido")){//se receber erro de campo não preenchido
				JOptionPane.showMessageDialog(null, "Campo não preenchido!");
			}else if(recebido.equals("clientejaexistente")){//se receber erro de cliente existente
				JOptionPane.showMessageDialog(null, "Este cliente ja foi cadastrado!");
			}
			JOptionPane.showMessageDialog(null, "Recebido: " + recebido);//exibe o que foi recebido
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null,"Exceção:" + e.getMessage(),"Erro",2);//exibe erro caso não tenha sido tratado
		}
	}

	/**
	 * Metodo de definir as configurações do servidor.
	 */
	private void definirConfig(){
		ip = textFieldipservidor.getText();//recupera ip da interface
		try{
			porta = Integer.parseInt(textFieldportaservidor.getText());//recupera porta da interface
			JOptionPane.showMessageDialog(null,"Porta e ip alterados","OK",2);//avisa que foi alterado
		}catch(NumberFormatException e){//caso não seja numerico a porta exibe um erro
			JOptionPane.showMessageDialog(null,"Coloque porta numerica","Erro",2);
		}
	}

	/**
	 * Metodo de acessar o cliente
	 */
	private void acessarCliente() {
		try {
			//Cria o Socket para buscar o arquivo no servidor 
			Socket rec = new Socket(ip,porta);

			//Enviando o nome do arquivo a ser baixado do servidor
			ObjectOutputStream saida = new ObjectOutputStream(rec.getOutputStream());

			String cpfcnpju = textFieldAcessoCPFCNPJ.getText();//recupera informações da interface
			String senha = passwordAcessoSenha.getText();

			Pacote pack = new Pacote();//cria um novo pacote
			pack.setOpcao(1);//identifico o tipo de operação 1 = acessa dados do cliente
			//envio cpfdocliente
			List<String> informacoes = new ArrayList();
			informacoes.add(cpfcnpju);
			informacoes.add(senha);
			pack.setLista(informacoes);
			saida.writeObject(pack);//envia o pacote
			saida.flush();

			ObjectInputStream entrada = new ObjectInputStream(rec.getInputStream());//recebe o pacote de entrada
			Pacote recebidoInfos = (Pacote) entrada.readObject();
			List<String> resultado = recebidoInfos.getLista();
			Iterator<String> itera = resultado.iterator();
			String recebido = itera.next();//obtem a informação
			if(recebido.equals("encontrado")){//se encontrado prepara a interface
				tabbedPaneAreaRestrita.setEnabled(true);
				textFieldEditFisicaNome.setEnabled(true);
				textFieldEditFisicaUF.setEnabled(true);
				radioButtonEditFisicaM.setEnabled(true);
				radioButtonEditFisicaF.setEnabled(true);
				textFieldEditFisicaCidade.setEnabled(true);
				textFieldEditFisicaRua.setEnabled(true);
				textFieldEditFisicaNum.setEnabled(true);
				textFieldEditFisicaData.setEnabled(true);
				passwordFieldNovaSenha.setEnabled(true);
				passwordFieldNovaSenha.setText(senha);
				btnSair.setEnabled(true);
				btnAlterar.setEnabled(true);
				//recebe os dados do cliente
				String nome = itera.next();
				textFieldEditFisicaNome.setText(nome);
				String data = itera.next();
				textFieldEditFisicaData.setText(data);
				boolean sexo = recebidoInfos.isB();
				if(sexo == true){
					radioButtonEditFisicaM.setSelected(true);
				}else{
					radioButtonEditFisicaF.setSelected(true);
				}
				String estado = itera.next();
				textFieldEditFisicaUF.setText(estado);
				String cidade = itera.next();
				textFieldEditFisicaCidade.setText(cidade);
				String rua = itera.next();
				textFieldEditFisicaRua.setText(rua);
				String num = itera.next();
				textFieldEditFisicaNum.setText(num);
				textFieldAcessoCPFCNPJ.setEnabled(false);
				passwordAcessoSenha.setEnabled(false);
				btnAcessar.setEnabled(false);
				boolean fisicaJuridica = recebidoInfos.isC();
				if(fisicaJuridica == false){
					radioButtonEditFisicaF.setVisible(false);;
					radioButtonEditFisicaM.setVisible(false);
					labelAcessoSexo.setVisible(false);
				}
				String contas = "";//recebe a lista de contas do cliente
				List<String> contasrecebidas = recebidoInfos.getLista2();
				Iterator<String> iteraContas = contasrecebidas.iterator();
				while(iteraContas.hasNext()){
					contas = contas + iteraContas.next() + "\n";
				}
				textAreaAcessoContas.setText(contas);//coloca conta no textArea

			}else if(recebido.equals("usuarioinvalido")){//caso receba erro de usuario invalido
				JOptionPane.showMessageDialog(null,"CPF ou CNPJ invalido","Erro",2);
			}else if(recebido.equals("senhainvalida")){//caso receba erro de senha invalida
				JOptionPane.showMessageDialog(null,"Senha invalida","Erro",2);
			}

			saida.close();
			entrada.close();
			rec.close();//fecha comunicação com o servidor
			JOptionPane.showMessageDialog(null, "Recebido: " + recebido);//exibe o que foi recebido
		}
		catch(Exception e) {//caso alguma exceção não tenha sido tratada é exibida
			JOptionPane.showMessageDialog(null,"Exceção:" + e.getMessage(),"Erro",2);
		}
	}

	/**
	 * Metodo que reinicia o estado da interface para antes do acesso do usuario
	 */
	private void sairEdicao(){
		tabbedPaneAreaRestrita.setEnabled(false);
		textFieldEditFisicaNome.setEnabled(false);
		textFieldEditFisicaNome.setText("");
		textFieldEditFisicaUF.setEnabled(false);
		textFieldEditFisicaUF.setText("");
		textFieldEditFisicaCidade.setEnabled(false);
		textFieldEditFisicaCidade.setText("");
		textFieldEditFisicaRua.setEnabled(false);
		textFieldEditFisicaRua.setText("");
		textFieldEditFisicaNum.setEnabled(false);
		textFieldEditFisicaNum.setText("");
		textFieldEditFisicaData.setEnabled(false);
		textFieldEditFisicaData.setText("");
		radioButtonEditFisicaM.setEnabled(false);
		radioButtonEditFisicaF.setEnabled(false);
		passwordFieldNovaSenha.setText("");
		passwordFieldNovaSenha.setEnabled(false);
		btnSair.setEnabled(false);
		btnAlterar.setEnabled(false);
		textFieldAcessoCPFCNPJ.setEnabled(true);
		passwordAcessoSenha.setEnabled(true);
		btnAcessar.setEnabled(true);
		rdbtnCorrenteAcesso.setSelected(false);
		rdbtnPoupanaAcesso.setSelected(false);
		textFieldAcessoNumConta.setText("");
		textFieldAcessoAgencia.setText("");
		textFieldAcessoSaldo.setText("");
		rdbtnFisicaAcesso.setSelected(false);
		rdbtnJuridicaAcesso.setSelected(false);
		textAreaAcessoContas.setText("");
		textFieldNumConta1.setText("");
		textFieldNumConta2.setText("");
		textFieldAgenciaConta1.setText("");
		textFieldAgenciaConta2.setText("");
		textFieldTransferenciaValor.setText("");
		btnAdicionarTitular.setEnabled(false);
	}

	/**
	 * Metodo de alterar o Cliente
	 */
	private void alterarCliente(){
		try {
			//Cria o Socket para buscar o arquivo no servidor 
			Socket rec = new Socket(ip,porta);

			//Enviando o nome do arquivo a ser baixado do servidor
			ObjectOutputStream saida = new ObjectOutputStream(rec.getOutputStream());
			Pacote envio = new Pacote();

			//envio cpfdocliente
			List<String> informacoes = new ArrayList();

			String nome = textFieldEditFisicaNome.getText();//obtem informações da interface
			String data = textFieldEditFisicaData.getText();
			String cpf = textFieldAcessoCPFCNPJ.getText();
			boolean sexo;
			if(radioButtonEditFisicaM.isSelected()){
				sexo = true;
			}else{
				sexo = false;
			}
			String uf = textFieldEditFisicaUF.getText();
			String cidade = textFieldEditFisicaCidade.getText();
			String rua = textFieldEditFisicaRua.getText();
			String num = textFieldEditFisicaNum.getText();
			String senha = passwordAcessoSenha.getText();
			String novaSenha = passwordFieldNovaSenha.getText();
			JOptionPane.showMessageDialog(null,"Senha:" + senha,"OK",2);

			envio.setOpcao(2);//identifico o tipo de operação 2 = editar cliente
			informacoes.add(nome);
			informacoes.add(data);
			informacoes.add(cpf);
			envio.setB(sexo);
			informacoes.add(uf);
			informacoes.add(cidade);
			informacoes.add(rua);
			informacoes.add(num);
			informacoes.add(senha);
			informacoes.add(novaSenha);
			envio.setLista(informacoes);

			saida.writeObject(envio);
			saida.flush();
			//envio todas as informações do cliente

			ObjectInputStream entrada = new ObjectInputStream(rec.getInputStream());//recebo o pacote do servidor
			Pacote recebido = (Pacote) entrada.readObject();
			List<String> resultadoLista = recebido.getLista();
			Iterator<String> itera = resultadoLista.iterator();
			String resultado = itera.next();//obtenho o resultado

			saida.close();
			entrada.close();
			rec.close();//fecho comunicação com o servidor

			if(resultado.equals("concluido")){//se concluiu com sucesso
				JOptionPane.showMessageDialog(null,"Cliente alterado com sucesso.","Sucesso",2);
			}else if(resultado.equals("camponaopreenchido")){//caso o campo não tenha sido preenchido
				JOptionPane.showMessageDialog(null,"Campo não foi preenchido.","Erro",2);
			}else if(resultado.equals("clientenaoencontrado")){//caso receba erro de cliente não encontrado
				JOptionPane.showMessageDialog(null,"Cliente não foi encontrado","Erro",2);
			}
			JOptionPane.showMessageDialog(null, "Recebido :" + resultado);//exibe o resultado recebido

		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null,"Exceção:" + e.getMessage(),"Erro",2);//exibe exceção não tratada
		}
	}

	/**
	 * Metodo de cadastrar cliente juridico
	 */
	private void cadastrarClienteJuridico(){
		try {
			//Cria o Socket para buscar o arquivo no servidor 
			Socket rec = new Socket(ip,porta);

			//Enviando o nome do arquivo a ser baixado do servidor
			ObjectOutputStream saida = new ObjectOutputStream(rec.getOutputStream());
			Pacote envio = new Pacote();

			String nome = textFieldCadNomeJuri.getText();//obtem informações da interface
			String data = textFieldCadDataJuri.getText();
			String cnpj = textFieldCadCNPJJuri.getText();
			String uf = textFieldCadUFJuri.getText();
			String cidade = textFieldCadCidadeJuri.getText();
			String rua = textFieldCadRuaJuri.getText();
			String num = textFieldCadNumJuri.getText();
			String senha = passwordFieldCadJuri.getText();
			JOptionPane.showMessageDialog(null,"Senha:" + senha,"OK",2);

			envio.setOpcao(3);//identifico o tipo de operação 3 = cadastrar cliente juridico
			List<String> informacoes = new ArrayList<>();
			informacoes.add(nome);//envio as informações obtidas para o pacote
			informacoes.add(data);
			informacoes.add(cnpj);
			informacoes.add(uf);
			informacoes.add(cidade);
			informacoes.add(rua);
			informacoes.add(num);
			informacoes.add(senha);
			envio.setLista(informacoes);
			//envio todas as informações do cliente
			saida.writeObject(envio);
			saida.flush();

			ObjectInputStream entrada = new ObjectInputStream(rec.getInputStream());//recebo o pacote do servidor
			Pacote recebido = (Pacote) entrada.readObject();
			List<String> resultadoLista = recebido.getLista();
			Iterator<String> itera = resultadoLista.iterator();
			String resultado = itera.next();//recebo o resultado

			saida.close();
			entrada.close();
			rec.close();//fecho a comunicação com o servidor

			if(resultado.equals("concluido")){//se concluiu com sucesso
				JOptionPane.showMessageDialog(null,"Cliente cadastrado com sucesso.","Sucesso",2);
			}else if(resultado.equals("camponaopreenchido")){//caso o campo não tenha sido preenchido
				JOptionPane.showMessageDialog(null,"Campo não foi preenchido.","Erro",2);
			}else if(resultado.equals("clientejaexistente")){//caso receba erro de cliente ja cadastrado
				JOptionPane.showMessageDialog(null,"Cliente ja cadastrado","Erro",2);
			}

			JOptionPane.showMessageDialog(null, "Recebido: " + resultado);//exibe o que foi recebido para o usuario
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null,"Exceção:" + e.getMessage(),"Erro",2);//exibe exceçã não tratada
		}
	}

	/**
	 * Metodo de criar conta
	 */
	private void criarConta(){
		try {
			//Cria o Socket para buscar o arquivo no servidor 
			Socket rec = new Socket(ip,porta);

			//Enviando o nome do arquivo a ser baixado do servidor
			ObjectOutputStream saida = new ObjectOutputStream(rec.getOutputStream());
			Pacote envio = new Pacote();//cria novo pacote

			String cpfcnpj = textFieldCadContaCPFCNPJ.getText();//obtem informações da interface
			boolean corrente = rdbtnCorrente.isSelected();
			String senha = passwordCadConta.getText();

			envio.setOpcao(4);//identifico o tipo de operação 3 = cadastrar conta
			List<String> informacoes = new ArrayList<>();
			informacoes.add(cpfcnpj);//adiciona informações ao pacote
			informacoes.add(senha);
			envio.setB(corrente);
			envio.setLista(informacoes);
			//envio todas as informações do cliente
			saida.writeObject(envio);
			saida.flush();

			ObjectInputStream entrada = new ObjectInputStream(rec.getInputStream());//recebe pacote do servidor
			Pacote recebido = (Pacote) entrada.readObject();
			List<String> resultadoLista = recebido.getLista();
			Iterator<String> itera = resultadoLista.iterator();
			String resultado = itera.next();

			saida.close();
			entrada.close();
			rec.close();//fecha comunicação com o servidor

			if(resultado.equals("concluido")){//se concluiu com sucesso
				JOptionPane.showMessageDialog(null,"Conta cadastrado com sucesso.","Sucesso",2);
			}else if(resultado.equals("camponaopreenchido")){//caso o campo não tenha sido preenchido
				JOptionPane.showMessageDialog(null,"Campo não foi preenchido.","Erro",2);
			}else if(resultado.equals("clientenaoencontrado")){//caso receba erro de cliente nao foi encontrado
				JOptionPane.showMessageDialog(null,"Cliente nao foi encontrado","Erro",2);
			}else if(resultado.equals("senhainvalida")){//caso receba erro de senha invalida
				JOptionPane.showMessageDialog(null,"Senha não é valida","Erro",2);
			}else if(resultado.equals("clientenulo")){//caso receba erro de cliente nulo
				JOptionPane.showMessageDialog(null,"Erro interno cliente nulo","Erro",2);
			}else if(resultado.equals("clienteJuridicoContaCorrente")){//caso receba erro de cliente juridico com conta poupança
				JOptionPane.showMessageDialog(null,"Cliente juridico não pode ter conta poupança","Erro",2);
			}

			JOptionPane.showMessageDialog(null, "Recebido: " + resultado);//exibe o resultado


		}catch(Exception e) {//caso alguma exceção não tenha sido tratada exibe
			JOptionPane.showMessageDialog(null,"Exceção:" + e.getMessage(),"Erro",2);
		}
	}

	/**
	 * Metodo de acessar conta
	 */
	private void acessarConta(){
		rdbtnCorrenteAcesso.setSelected(false);//reinicia os radio buttons
		rdbtnPoupanaAcesso.setSelected(false);
		rdbtnFisicaAcesso.setSelected(false);
		rdbtnJuridicaAcesso.setSelected(false);
		try {
			//Cria o Socket para buscar o arquivo no servidor 
			Socket rec = new Socket(ip,porta);

			//Enviando o nome do arquivo a ser baixado do servidor
			ObjectOutputStream saida = new ObjectOutputStream(rec.getOutputStream());
			Pacote envio = new Pacote();//cria novo pacote

			String conta = textFieldAcessoNumConta.getText();//recupera informações da interface
			String agencia = textFieldAcessoAgencia.getText();
			String cpfcnpj = textFieldAcessoCPFCNPJ.getText();
			envio.setOpcao(5);//identifico o tipo de operação 5 = Acessar Conta
			List<String> informacoes = new ArrayList<>();
			informacoes.add(conta);//adiciona informações ao pacote
			informacoes.add(agencia);
			informacoes.add(cpfcnpj);
			envio.setLista(informacoes);
			//envio todas as informações do cliente
			saida.writeObject(envio);
			saida.flush();

			ObjectInputStream entrada = new ObjectInputStream(rec.getInputStream());//obtem pacote do servidor
			Pacote recebido = (Pacote) entrada.readObject();
			List<String> resultadoLista = recebido.getLista();
			Iterator<String> itera = resultadoLista.iterator();
			String resultado = itera.next();//obtem informações do servidor
			if(resultado.equals("encontrado")){//se for encontrado
				String saldo = itera.next();//pega as informações da conta
				textFieldAcessoSaldo.setText(saldo);
				btnAdicionarTitular.setEnabled(true);
				boolean corrente = recebido.isB();
				if(corrente == true){
					rdbtnCorrenteAcesso.setSelected(true);
				}else{
					rdbtnPoupanaAcesso.setSelected(true);
				}

				boolean fisica = recebido.isC();
				if(fisica == true){
					rdbtnFisicaAcesso.setSelected(true);
				}else{
					rdbtnJuridicaAcesso.setSelected(true);
				}
			}else if(resultado.equals("containvalidacliente")){//caso receba erro de cliente errado para a conta
				JOptionPane.showMessageDialog(null,"Este cliente não possui essa conta","Erro",2);
			}else if(resultado.equals("camponaopreenchido")){//caso receba erro de campo não preenchido
				JOptionPane.showMessageDialog(null,"Campo não foi preenchido","Erro",2);
			}else if(resultado.equals("contanaoencontrada")){//caso receba erro de conta não encontrada
				JOptionPane.showMessageDialog(null,"Conta não foi encontrada","Erro",2);
			}else if(resultado.equals("agenciaincorreta")){//caso receba erro de agencia incorreta
				JOptionPane.showMessageDialog(null,"Agencia incorreta","Erro",2);
			}

			saida.close();
			entrada.close();
			rec.close();// fecha comunicação com o servidor

			JOptionPane.showMessageDialog(null, "Recebido: " + resultado);//exibe resultado

		}catch(Exception e) {//caso exceção não tenha sido tratada é exibida
			JOptionPane.showMessageDialog(null,"Exceção:" + e.getMessage(),"Erro",2);
		}
	}

	/**
	 * Metodo de depositar
	 */
	private void depositar(){
		try{
			//Cria o Socket para buscar o arquivo no servidor 
			Socket rec = new Socket(ip,porta);

			//Enviando o nome do arquivo a ser baixado do servidor
			ObjectOutputStream saida = new ObjectOutputStream(rec.getOutputStream());
			Pacote envio = new Pacote();
			String numConta = textFieldNumDep.getText();
			String agencia = textFieldAgenciaDep.getText();
			String valorS = textFieldValorDep.getText();
			envio.setOpcao(6);//identifico o tipo de operação 6 = Depositar
			List<String> informacoes = new ArrayList<>();
			informacoes.add(numConta);
			informacoes.add(agencia);
			informacoes.add(valorS);
			envio.setLista(informacoes);
			//envio todas as informações do cliente
			saida.writeObject(envio);
			saida.flush();

			ObjectInputStream entrada = new ObjectInputStream(rec.getInputStream());//recebe pacote do servidor
			Pacote recebido = (Pacote) entrada.readObject();
			List<String> resultadoLista = recebido.getLista();
			Iterator<String> itera = resultadoLista.iterator();
			String resultado = itera.next();
			if(resultado.equals("concluido")){
				String valorAtual = itera.next();//recebe o falor atual do titular
				JOptionPane.showMessageDialog(null, "Valor total: " + valorAtual);
			}else if(resultado.equals("erroDouble")){//caso receba erro de valor incorreto
				JOptionPane.showMessageDialog(null,"Valor incorreto deve ser exemplo 10.0","Erro",2);
			}else if(resultado.equals("contanaoencontrada")){//caso receba erro de conta nao econtrada
				JOptionPane.showMessageDialog(null,"Conta não foi encontrada","Erro",2);
			}else if(resultado.equals("camponaopreenchido")){//caso receba erro de campo não preenchido
				JOptionPane.showMessageDialog(null,"Campo não foi preenchido","Erro",2);
			}else if(resultado.equals("clientenaoencontrado")){//caso receba erro de cliente não encontrado
				JOptionPane.showMessageDialog(null,"Cliente não encontrado","Erro",2);
			}else if(resultado.equals("agenciaincorreta")){//caso receba erro de agencia incorreta
				JOptionPane.showMessageDialog(null,"Agencia incorreta","Erro",2);
			}

			saida.close();
			entrada.close();
			rec.close();//fecha comunicação com o servidor
			JOptionPane.showMessageDialog(null, "Recebido :" + resultado);


		}catch(Exception e){
			JOptionPane.showMessageDialog(null,"Exceção:" + e.getMessage(),"Erro",2);//caso alguma exceção não tenha sido tratada é exibida
		}
	}

	/**
	 * Metodo de transferir
	 */
	private void transferir(){
		textFieldAcessoSaldo.setText("");
		btnAdicionarTitular.setEnabled(false);
		rdbtnCorrenteAcesso.setSelected(false);//reinicia os radio buttons
		rdbtnPoupanaAcesso.setSelected(false);
		rdbtnFisicaAcesso.setSelected(false);
		rdbtnJuridicaAcesso.setSelected(false);
		try{
			//Cria o Socket para buscar o arquivo no servidor 
			Socket rec = new Socket(ip,porta);

			//Enviando o nome do arquivo a ser baixado do servidor
			ObjectOutputStream saida = new ObjectOutputStream(rec.getOutputStream());
			Pacote envio = new Pacote();

			String numConta1 = textFieldNumConta1.getText();//obtem informações da interface
			String numConta2 = textFieldNumConta2.getText();
			String agenciaConta1 = textFieldAgenciaConta1.getText();
			String agenciaConta2 = textFieldAgenciaConta2.getText();
			String valor = textFieldTransferenciaValor.getText();
			String cpfcnpj = textFieldAcessoCPFCNPJ.getText();

			envio.setOpcao(7);//identifico o tipo de operação 7 = Transferir
			List<String> informacoes = new ArrayList<>();
			informacoes.add(numConta1);//envio informações par ao servidor
			informacoes.add(agenciaConta1);
			informacoes.add(numConta2);
			informacoes.add(agenciaConta2);
			informacoes.add(valor);
			informacoes.add(cpfcnpj);
			envio.setLista(informacoes);
			//envio todas as informações do cliente
			saida.writeObject(envio);
			saida.flush();

			ObjectInputStream entrada = new ObjectInputStream(rec.getInputStream());
			Pacote recebido = (Pacote) entrada.readObject();//ler informações do pacote
			List<String> resultadoLista = recebido.getLista();
			Iterator<String> itera = resultadoLista.iterator();
			String resultado = itera.next();
			if(resultado.equals("concluido")){//se houve sucesso
				String valorAtual = itera.next();
				JOptionPane.showMessageDialog(null, "Valor atual do Remetente: " + valorAtual);//exibe o valor atual do cliente
			}else if(resultado.equals("contanaoencontrada")){//caso receba erro de conta não encontrada
				JOptionPane.showMessageDialog(null,"Conta não foi encontrada","Erro",2);
			}else if(resultado.equals("camponaopreenchido")){//caso receba erro de campo não preenchido
				JOptionPane.showMessageDialog(null,"Campo não preenchido","Erro",2);
			}else if(resultado.equals("clientenaoencontrado")){//caso receba erro de cliente não encontrado
				JOptionPane.showMessageDialog(null,"Cliente não encontrado","Erro",2);
			}else if(resultado.equals("agenciaincorreta")){//caso receba erro de agencia não encontrada
				JOptionPane.showMessageDialog(null,"Agencia não encontrada","Erro",2);
			}else if(resultado.equals("conflitonaconta")){//caso receba erro de conflito na conta
				JOptionPane.showMessageDialog(null,"Conflito na conta","Erro",2);
			}else if(resultado.equals("saldoinsuficiente")){//caso receba erro de saldo insuficiente
				JOptionPane.showMessageDialog(null,"Saldo insuficiente","Erro",2);
			}

			saida.close();
			entrada.close();
			rec.close();//fecha comunicação
			JOptionPane.showMessageDialog(null, "Recebido :" + resultado);//exibe o resultado ao cliente

		}catch(Exception e){
			JOptionPane.showMessageDialog(null,"Exceção:" + e.getMessage(),"Erro",2);//lança aviso de exceção caso não tenha tratado
		}
	}

	/**
	 * Adiciona o titular
	 */
	private void addTitular(){
		try{
			//Cria o Socket para buscar o arquivo no servidor 
			Socket rec = new Socket(ip,porta);

			//Enviando o nome do arquivo a ser baixado do servidor
			ObjectOutputStream saida = new ObjectOutputStream(rec.getOutputStream());
			Pacote envio = new Pacote();//cria o pacote

			String numConta = textFieldAcessoNumConta.getText();//envia informações para o pacote
			String agencia = textFieldAcessoAgencia.getText();
			String cpfAtual = textFieldAcessoCPFCNPJ.getText();
			String cpfAdd = JOptionPane.showInputDialog("Qual o cpf/cnpj para adicionar?");

			envio.setOpcao(8);//identifico o tipo de operação 7 = Transferir
			List<String> informacoes = new ArrayList<>();
			informacoes.add(numConta);
			informacoes.add(agencia);
			informacoes.add(cpfAtual);
			informacoes.add(cpfAdd);
			envio.setLista(informacoes);
			//envio todas as informações do cliente
			saida.writeObject(envio);//envia o pacote
			saida.flush();

			ObjectInputStream entrada = new ObjectInputStream(rec.getInputStream());//recebe objeto do servidor
			Pacote recebido = (Pacote) entrada.readObject();//ler o que o servidor enviou
			List<String> resultadoLista = recebido.getLista();
			Iterator<String> itera = resultadoLista.iterator();
			String resultado = itera.next();
			if(resultado.equals("concluido")){
				JOptionPane.showMessageDialog(null, "Novo Titular Adicionado");//avisa que foi concluido
			}else if(resultado.equals("contaonaoencontrada")){//caso receba erro de conta não encontrada
				JOptionPane.showMessageDialog(null,"Conta não encontrada","Erro",2);
			}else if(resultado.equals("camponaopreenchido")){//caso receba erro de campo não preenchido
				JOptionPane.showMessageDialog(null,"Campo não preenchido","Erro",2);
			}else if(resultado.equals("agenciaincorreta")){//agencia incorreta
				JOptionPane.showMessageDialog(null,"Agencia incorreta","Erro",2);
			}else if(resultado.equals("clientenaoencontrado")){//caso receba erro de cliente não encontrado
				JOptionPane.showMessageDialog(null,"Cliente não encontrado","Erro",2);
			}else if(resultado.equals("titularjacadastrado")){//caso receba erro de titular ja cadastrado
				JOptionPane.showMessageDialog(null,"Titular ja cadastrado","Erro",2);
			}

			saida.close();//fecha a saida
			entrada.close();//fecha a recepção
			rec.close();//fecha conecção com o cliente
			JOptionPane.showMessageDialog(null, "Recebido :" + resultado);//mostra o resultado obtido ao cliente
		}catch(Exception e){
			JOptionPane.showMessageDialog(null,"Exceção:" + e.getMessage(),"Erro",2);//lança aviso de exceção caso não tenha tratado
		}
	}
}
