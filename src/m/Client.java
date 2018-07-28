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
import java.net.UnknownHostException;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * Clase que representa a un cliente, este cliente envía un mensaje a un servidor, el servidor devuelve el mensaje pero
 * modificado, esta clase interactua con la interfaz de usuario para establecer en uno de sus campos de texto
 * el valor retornado por el servidor.
 * El mensaje transmitido por ambos es un objeto de tipo {@link Packet}.
 * @author Bryan Ti
 *
 */
public class Client {
	
	private static DatagramSocket socket;
	private final int MAX_LENGTH = 2600;
	
	public static final String HOST = "localhost";
	public static final int PORT = 1026;
	
	private JTextField tfYpe2, tfText2, tfSender2;
	
	/**
	 * Constructor, inicia el DatagramSocket.
	 * @param tfType2 Campo de texto en el cual se va a escribir parte del mensaje retornado por el servidor.
	 * @param tfText2 Campo de texto en el cual se va a escribir parte del mensaje retornado por el servidor.
	 * @param tfSender2 Campo de texto en el cual se va a escribir parte del mensaje retornado por el servidor.
	 */
	public Client(JTextField tfType2, JTextField tfText2, JTextField tfSender2 ) {
		
		this.tfYpe2 = tfType2;
		this.tfText2 = tfText2;
		this.tfSender2 = tfSender2;
		
		try {
			socket = new DatagramSocket(new InetSocketAddress(HOST,PORT));
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	 /**
	 * Método que crea un nuevo hilo para enviar un mensaje al servidor destino, este mensaje es un objeto de la clase
	 * {@link Packet}, el objeto es convertido en un flujo de bytes para poder ser transmitido al servidor.
	 * En caso de no encontrarse el destino se muestra un mensaje informativo al usuario.
	 * @param packet, De la clase {@link Packet}, objeto a enviar
	 * @param hostDestination, host o ip del destino
	 * @param portDestination, puerto del destino
	 * @throws UnknownHostException, No se ha encontrado el host destino.
	 * @throws IOException
	 */
	public void sendMessage(Packet packet, String hostDestination, int portDestination) throws UnknownHostException, IOException {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ObjectOutput ou = new ObjectOutputStream(baos); 
				ou.writeObject(packet);
				ou.flush();// importante limpiar.
				byte[] message = baos.toByteArray();
				socket.send(new DatagramPacket(message, message.length,InetAddress.getByName(hostDestination),portDestination));
				socket.setSoTimeout(1000);
				baos.close();
				
				receiveMessage();
				}catch(UnknownHostException e) {
					JOptionPane.showMessageDialog(null, "Host "+ hostDestination.toUpperCase() +" no encontrado");
				}catch(Exception e) {
					
				}
			}
		}).start();
		
		
	}
	
	/**
	 * Método que recibe un mensaje proveniente del servidor, el mensaje recibido es un flujo de bytes que se convierten a 
	 * un objeto de tipo {@link Packet}.
	 * De este objeto de tipo {@link Packet} se obtienen los valores deseados para rellandos algunos campos de texto de la IU(campo de type modificad, campo
	 * de mensaje modificado, campo de "remitente".)
	 */
	private void receiveMessage() {
		
		byte[] message = new byte[MAX_LENGTH];
		try {
			
			DatagramPacket packet = new DatagramPacket(message, MAX_LENGTH);
			socket.receive(packet);
			ByteArrayInputStream bais = new ByteArrayInputStream(message);
			ObjectInputStream in = new ObjectInputStream(bais);
			Packet p = (Packet) in.readObject(); 
			EventQueue.invokeAndWait(new Runnable() {
				
				@Override
				public void run() {
					tfYpe2.setText(String.valueOf(p.getType()));
					tfText2.setText(p.getText());
					tfSender2.setText(packet.getAddress() +"("+String.valueOf(packet.getPort()+")"));
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
