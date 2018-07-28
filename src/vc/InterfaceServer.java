package vc;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

import m.Packet;
import m.Server;

import javax.swing.JLabel;
import java.awt.Font;

@SuppressWarnings("serial")
public class InterfaceServer extends JFrame{
	
	JScrollPane scrollPane = new JScrollPane();
	JTextArea textArea = new JTextArea();
	JLabel lblNewLabel = new JLabel("Mensajes recibidos");
	
	private Server server;
	
	public InterfaceServer() {
		setTitle("UDP Server");
		getContentPane().setLayout(null);
		this.setSize(600,500);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		scrollPane.setBounds(12, 57, 557, 292);
		getContentPane().add(scrollPane);
		textArea.setEditable(false);
		
		scrollPane.setViewportView(textArea);
		
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setBounds(12, 13, 175, 31);
		getContentPane().add(lblNewLabel);
		
		this.setVisible(true);
		server = new Server(textArea);
		
	}
	
	public static void main(String[] args) {
		
		//Se estace un tema para cambiar visualmente la ventana que se mostrará posteriormente
				try 
			    { 
			        UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel"); 
			    } 
			    catch(Exception e){ 
			    }
		InterfaceServer serv = new InterfaceServer();
	}
}
