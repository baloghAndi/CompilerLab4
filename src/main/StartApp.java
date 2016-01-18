package main;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import grammar.ContextFreeGrammar;
import grammar.Controller;

public class StartApp {

	private JFrame frame;
	private JTextField readGR;
	private JTextField textFieldAlphabet;
	private JTextField textFieldNonTerm;
	private JTextField textFieldStart;
	private JTextField textFieldProd;
	private JTextField textFieldFinal;
	private JTextField textFieldGetProd;
	private JButton btnReadGr,btnReadGrammar,btnProductionsOf,btnShowAlphabetGR,btnShowNonTerm,btnProductions;
	private JTextArea textArea;
	private Controller ctrl;
	private JLabel lblGrammar;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StartApp window = new StartApp();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public StartApp() {
		initialize();
		ctrl = new Controller();
		setListeners();
	}


	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 845, 423);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		readGR = new JTextField();
		readGR.setBounds(10, 60, 86, 20);
		frame.getContentPane().add(readGR);
		readGR.setColumns(10);
		
		btnReadGr = new JButton("Read gr");
		
		btnReadGr.setBounds(121, 59, 89, 23);
		frame.getContentPane().add(btnReadGr);
		
		JLabel lblAlpabet = new JLabel("Alpabet:");
		lblAlpabet.setBounds(10, 143, 46, 14);
		frame.getContentPane().add(lblAlpabet);
		
		textFieldAlphabet = new JTextField();
		textFieldAlphabet.setBounds(205, 140, 177, 20);
		frame.getContentPane().add(textFieldAlphabet);
		textFieldAlphabet.setColumns(10);
		
		JLabel lblNonterminals = new JLabel("Non-terminals(Q)/States(N):");
		lblNonterminals.setBounds(10, 179, 185, 14);
		frame.getContentPane().add(lblNonterminals);
		
		textFieldNonTerm = new JTextField();
		textFieldNonTerm.setBounds(205, 176, 177, 20);
		frame.getContentPane().add(textFieldNonTerm);
		textFieldNonTerm.setColumns(10);
		
		JLabel lblStartingSymbolstate = new JLabel("Starting symbol/state(q0/S):");
		lblStartingSymbolstate.setBounds(10, 208, 185, 18);
		frame.getContentPane().add(lblStartingSymbolstate);
		
		textFieldStart = new JTextField();
		textFieldStart.setBounds(205, 207, 177, 20);
		frame.getContentPane().add(textFieldStart);
		textFieldStart.setColumns(10);
		
		textFieldProd = new JTextField();
		textFieldProd.setBounds(205, 243, 177, 20);
		frame.getContentPane().add(textFieldProd);
		textFieldProd.setColumns(10);
		
		JLabel lblProductions = new JLabel("Productions(P)/Transitions(delta):");
		lblProductions.setBounds(10, 246, 194, 14);
		frame.getContentPane().add(lblProductions);
		
		JLabel lblFinalStatesfolny = new JLabel("Final states(F)  -only FA-");
		lblFinalStatesfolny.setBounds(10, 277, 185, 14);
		frame.getContentPane().add(lblFinalStatesfolny);
		
		textFieldFinal = new JTextField();
		textFieldFinal.setBounds(205, 274, 177, 20);
		frame.getContentPane().add(textFieldFinal);
		textFieldFinal.setColumns(10);
		
		JLabel lblFileName = new JLabel("File name");
		lblFileName.setBounds(10, 23, 152, 14);
		frame.getContentPane().add(lblFileName);
		
		btnReadGrammar = new JButton("Read Grammar");
		
		btnReadGrammar.setBounds(10, 315, 152, 23);
		frame.getContentPane().add(btnReadGrammar);
		
		btnShowAlphabetGR = new JButton("Alphabet(gr)");
		
		btnShowAlphabetGR.setBounds(408, 49, 152, 23);
		frame.getContentPane().add(btnShowAlphabetGR);
		
		btnShowNonTerm = new JButton("Non-terminals");
		
		btnShowNonTerm.setBounds(408, 83, 152, 23);
		frame.getContentPane().add(btnShowNonTerm);
		
		btnProductions = new JButton("Productions");
		
		btnProductions.setBounds(408, 117, 152, 23);
		frame.getContentPane().add(btnProductions);
		
		btnProductionsOf = new JButton("Productions of :");
		btnProductionsOf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnProductionsOf.setBounds(408, 151, 152, 23);
		frame.getContentPane().add(btnProductionsOf);
		
		textFieldGetProd = new JTextField();
		textFieldGetProd.setBounds(408, 176, 76, 20);
		frame.getContentPane().add(textFieldGetProd);
		textFieldGetProd.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(570, 47, 251, 325);
		frame.getContentPane().add(scrollPane);
		
		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		
		lblGrammar = new JLabel("Grammar");
		lblGrammar.setBounds(408, 11, 152, 14);
		frame.getContentPane().add(lblGrammar);
		
		JButton btnParse = new JButton("Parse");
		
		btnParse.setBounds(408, 237, 89, 23);
		frame.getContentPane().add(btnParse);
	}
	
	private void setListeners(){
		btnReadGr.addActionListener(new ActionListener() { //from file
			public void actionPerformed(ActionEvent e) {
				try {
					ContextFreeGrammar gr = ContextFreeGrammar.readFromFileGr(readGR.getText());
					//Grammar gr = ReadInput.readFromFileGr(readGR.getText());
					ctrl.setContextFreeGrammar(gr);
//					Config conf = ctrl.parse();
				//	textArea.setText(textArea.getText()+"\n"+gr.toString());
//					textArea.setText(textArea.getText()+"\n"+conf.toString());
					textArea.setText(textArea.getText()+"\n");
					textArea.setText(textArea.getText()+"\n"+ctrl.printParseTree());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
	
		
	}
}
