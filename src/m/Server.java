package m;

import java.awt.EventQueue;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;

import javax.swing.JTextArea;

/**
 * Clase que representa a un servidor. Este servdidor tiene como función la de aceptar conexiones de varios clientes
 * y recibir de ellos un objeto de tipo {@link Packet}, una vez recibido este objeto es modificado y devuelto al cliente
 * 
 * @author Bryan Ti
 *
 */
public class Server {

	private DatagramSocket socket;
	private final int MAX_LENGTH = 1024;
	
	public static final String HOST = "localhost";
	public static final int PORT = 1025;
	
	/**
	 * Constructor por defecto, Instancia el DatagramSocket y pone el servidor a la escuha mediante el método {@link #receiveMsg(JTextArea)}
	 * @param textarea
	 */
	public Server(JTextArea textarea) 
	{
		try {
			socket = new DatagramSocket(new InetSocketAddress(HOST, PORT));
			receiveMsg(textarea);
		} catch (SocketException e) {
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * Método que modifica el paquete pasado por parametro para posteriormente ser devuelto al cliente que lo envió.
	 * @param packet, {@link Packet} objeto enviado por el cliente.
	 */
	private void convertAndSend(Packet packet) 
	{
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true)
				{
					try {
						int type = packet.getType()+100;
						String text = packet.getText().toUpperCase();
						
						Packet packet2 = new Packet(type, text);
						
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						ObjectOutput oo = new ObjectOutputStream(baos);
						oo.writeObject(packet2);
						oo.flush();
						
						byte[] data = baos.toByteArray();
						socket.send(new DatagramPacket(data, data.length, InetAddress.getByName(HOST),1026));
						baos.close();						
						
						Thread.sleep(2000);
					} catch (IOException | InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
	
	/**
	 * Método que costantemente está recibiendo paquetes de clientes. al recibir un paquete este se encuentra en un formato
	 * de array de bytes, pero al saber nosotros que se trata de un objeto {@link Packet}, convertirmos el array de bytes en 
	 * un objeto Packet.
	 * Este método también se encarga de pintar por la IU del servidor la información referente al remitente.
	 * @param textarea Donde se pintará la información que contiene el paquete recibido.
	 */
	private void receiveMsg(JTextArea textarea)
	{
		while(true) {
			
			try {
				byte[] buf = new byte[MAX_LENGTH];
				DatagramPacket packetReceived = new DatagramPacket(buf, MAX_LENGTH);
				socket.receive(packetReceived);
				ByteArrayInputStream bais = new ByteArrayInputStream(buf);
				ObjectInputStream ois = new ObjectInputStream(bais);
				Packet packet = (Packet) ois.readObject();//paquete recibido
				
				EventQueue.invokeAndWait(new Runnable() {
					
					@Override
					public void run() {
						textarea.setText( textarea.getText() + packetReceived.getAddress() + "(" + packetReceived.getPort() + "): "+ packet.toString() +"\n");
						convertAndSend(packet);
					}
				});
				
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	
}
