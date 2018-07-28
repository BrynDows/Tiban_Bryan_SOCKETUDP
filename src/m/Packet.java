package m;

import java.io.Serializable;



/**
 * Clase que representa a un mensaje que será transmitido mediante DatagramSocket entre cliente y servidor.
 * Para que pueda ser transimito mediante sockets esta clase implementa la interfaz {@link Serializable}
 * @author Bryan Ti
 *
 */
public class Packet implements Serializable{

	private static final long serialVersionUID = 1L;
	private int type ;
	private String text;
	
	/**
	 * Constructor por defecto que instancia la clase.
	 * @param type 
	 * @param text
	 */
	public Packet(int type, String text) {
		this.type = type;
		this.text = text;
	}
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return "Packet [type=" + type + ", text=" + text + "]";
	}
	
	
	
}
