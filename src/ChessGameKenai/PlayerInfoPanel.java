package ChessGameKenai;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class PlayerInfoPanel extends JPanel {
	
	private JLabel playerNameLabel, numberOfWinsLabel, timerLabel, playerIcon;
	private Player playerInfo;
	
	public PlayerInfoPanel(Player data)
	{
		super(new GridLayout(1, 2));
		setOpaque(false);
		
		playerInfo = data;
		setUpInfoLayer();
	}
	private void setUpInfoLayer()
	{
		addPlayerIcon();
		addPlayerProperties();
	}
	private void addPlayerProperties()
	{
		JPanel propetiesPanel = new JPanel(new GridLayout(3, 1));
		propetiesPanel.setOpaque(false);
		
		playerNameLabel =getLabelWithString(playerInfo.getName(), 20);		
		numberOfWinsLabel = getLabelWithString("Number of wins: "+playerInfo.getNumberOfWins(), 20);
		timerLabel = getLabelWithString("00:00:00", 20);

        propetiesPanel.add(playerNameLabel);
		propetiesPanel.add(numberOfWinsLabel);
		propetiesPanel.add(timerLabel);
		
		add(propetiesPanel);
	}
	private void addPlayerIcon()
	{
		playerIcon = new JLabel(new ImageIcon(getClass().getResource(playerInfo.getIconPath())));

		JPanel iconPanel = new JPanel();
		iconPanel.setOpaque(false);
		iconPanel.add(playerIcon);
		add(iconPanel);
	}
	private JLabel getLabelWithString(String lblStr, int size)
	{
		JLabel lbl = new JLabel(lblStr, SwingConstants.LEFT);
		lbl.setFont(new Font("Times Roman", Font.PLAIN, size));
		lbl.setForeground(Color.WHITE);
		return lbl;
	}
	public void resetPanelInformation()
	{
		numberOfWinsLabel.setText("Number of wins: "+playerInfo.getNumberOfWins());
		playerIcon.setIcon(new ImageIcon(getClass().getResource(playerInfo.getIconPath())));
		updatePlayerName();
	}
	public void updatePlayerName()
	{
		playerNameLabel.setText(playerInfo.getName());
	}
	public void resetTimerLabel()
	{
		timerLabel.setText("00:00:00");
	}
	public void updateTimerLabel(String text)
	{
		timerLabel.setText(text);
	}
}
