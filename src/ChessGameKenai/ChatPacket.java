package ChessGameKenai;

import java.awt.Color;

import javax.swing.text.SimpleAttributeSet;

public class ChatPacket {

	private Color color;
	private String message;
	private SimpleAttributeSet attributeSet;
	
	public ChatPacket(String msg, SimpleAttributeSet attributeS, Color clr)
	{
		message = msg;
		attributeSet = attributeS;
		color = clr;
	}
	public ChatPacket(Packet packet)
	{
		message = packet.getMessage();
		attributeSet = packet.getSmpSet();
		color = packet.getColor();
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public SimpleAttributeSet getAttributeSet() {
		return attributeSet;
	}

	public void setAttributeSet(SimpleAttributeSet attributeSet) {
		this.attributeSet = attributeSet;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

}
