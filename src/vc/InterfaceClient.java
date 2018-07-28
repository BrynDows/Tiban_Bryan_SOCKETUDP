package vc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.SocketException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import m.Client;
import m.Packet;


@SuppressWarnings("serial")
public class InterfaceClient extends JFrame{
	
	
	JPanel panel = new JPanel();
	JLabel lblTipo = new JLabel("Tipo");
	JLabel lblTexto = new JLabel("Texto");
	JButton btnEnviar = new JButton("Enviar");
	JTextField tfType = new JTextField();
	JTextField tfText = new JTextField();
	JPanel panel_1 = new JPanel();
	JLabel lblTipo_1 = new JLabel("Tipo");
	JLabel lblTexto_1 = new JLabel("Texto");
	JLabel lblRemitente = new JLabel("Remitente");
	JTextField tfType2 = new JTextField();
	JTextField tfText2 = new JTextField();
	JTextField tfIp2 = new JTextField();
	JLabel lblNewLabel = new JLabel("IP o Nombre");
	JTextField tfIp = new JTextField();
	JLabel lblPuerto = new JLabel("Puerto");
	JTextField tfPort = new JTextField();
	
	private Client client;
	
	public InterfaceClient() {
		
		client = new Client(tfType2, tfText2, tfIp2);
		
		setTitle("UDP Cliente");
		getContentPane().setLayout(null);
		
		this.setSize(500,500);
		panel.setBorder(new TitledBorder(null, "A enviar", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(60, 111, 375, 137);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		
		lblTipo.setBounds(12, 27, 87, 16);
		panel.add(lblTipo);
		
		lblTexto.setBounds(12, 62, 87, 16);
		panel.add(lblTexto);
		
	
		btnEnviar.setBounds(126, 99, 97, 25);
		panel.add(btnEnviar);
		tfType.setText("10");
		
		tfType.setColumns(10);
		tfType.setBounds(82, 24, 63, 22);
		panel.add(tfType);
		tfText.setText("Hola mundo");
		
		tfText.setColumns(10);
		tfText.setBounds(82, 56, 217, 22);
		panel.add(tfText);
		
		panel_1.setBorder(new TitledBorder(null, "Recibido", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(60, 261, 375, 130);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		lblTipo_1.setBounds(12, 29, 87, 16);
		panel_1.add(lblTipo_1);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		lblTexto_1.setBounds(12, 57, 87, 16);
		panel_1.add(lblTexto_1);
		
		lblRemitente.setBounds(12, 89, 87, 16);
		panel_1.add(lblRemitente);
		
		tfType2.setEditable(false);
		tfType2.setColumns(10);
		tfType2.setBounds(80, 26, 67, 22);
		panel_1.add(tfType2);
		
		
		tfText2.setEditable(false);
		tfText2.setColumns(10);
		tfText2.setBounds(80, 58, 283, 22);
		panel_1.add(tfText2);
		
		tfIp2.setEditable(false);
		tfIp2.setColumns(10);
		tfIp2.setBounds(80, 86, 283, 22);
		panel_1.add(tfIp2);
		
		lblNewLabel.setBounds(60, 37, 87, 16);
		getContentPane().add(lblNewLabel);
		tfIp.setText("localhost");
		
		tfIp.setBounds(143, 34, 221, 22);
		getContentPane().add(tfIp);
		tfIp.setColumns(10);
		
		lblPuerto.setBounds(60, 69, 87, 16);
		getContentPane().add(lblPuerto);
		tfPort.setText("1025");
		
		tfPort.setColumns(10);
		tfPort.setBounds(143, 66, 221, 22);
		getContentPane().add(tfPort);
		
		this.setVisible(true);
		
		event();
		
		
	}
	
	private void event()
	{
		btnEnviar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int type = Integer.parseInt(tfType.getText());
				String text = tfText.getText();
				Packet packet = new Packet(type, text);
				String hostDestination = tfIp.getText();
				int portDestination =Integer.parseInt(tfPort.getText());
				
				try {
					client.sendMessage(packet, hostDestination, portDestination);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}
	
	public static void main (String[] args)
	{
		//Se estace un tema para cambiar visualmente la ventana que se mostrará posteriormente
				try 
			    { 
			        UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel"); 
			    } 
			    catch(Exception e){ 
			    }
		InterfaceClient i = new InterfaceClient();
	}
	
}
