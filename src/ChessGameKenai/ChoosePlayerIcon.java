/*
 * Description: Class Choose_Icon.java
 * Author: Dimtri Pankov
 * Date: 20-Jan-2011
 * Version: 1.0
 */
package ChessGameKenai;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

/**
 * The Choose_Icon class is a Simple JDialog that is used to
 * display the possible player icons to the user when the user
 * clicks on the icon then clicks next the icon he chose will represent him
 * This JDialog is only used if the user wants to choose another icon to represent himself
 * @author Dimitri Pankov
 * @see JDialog
 * @author 1.5
 */
public class ChoosePlayerIcon extends JDialog {

	private static final long serialVersionUID = 1L;
	private JScrollPane scroll;
    private TreeMap<Integer, Thumbnail> map = new TreeMap<Integer, Thumbnail>();
    private JRadioButton pl1Button, pl2Button;
    private ButtonGroup btnGroup;
    private JButton applyButton;
    private Chess_Data chessData;
    private ConnectionBridge bridge;
    private String playerIconPath;
    private JPanel southPanel,panel;
    Thumbnail thumbnail;

    /**
     * Overloaded constructor of the class
     * Has all needed GUI to represent itself graphically on the screen
     * @param chessData as Chess_Data
     * @param bridge as a ConnectionBridge
     */
    public ChoosePlayerIcon(ConnectionBridge bridge) {
        this.bridge = bridge;
        this.chessData = Chess_Data.getChessData();
        
        createApplyButton();
        createPlayersButton();
        showPossiblePlayerIcons();
        addAllButtonsToJPanel();
        createSouthPanel();
        addAllSmileysToDialog();
        addComponentsToContainer();
        initializeJFrameProperties();
    }
    private void initializeJFrameProperties()
    {
		this.setModal(true);
	    this.setTitle("Choose Icon");
	    this.setLocation(500, 300);
	    this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	    this.setResizable(false);
	    this.setSize(600, 200);
	    this.setVisible(true);
    }
    
    private void addComponentsToContainer()
    {
    	Container container = this.getContentPane();
    	container.add(scroll, BorderLayout.CENTER);
    	container.add(panel, BorderLayout.SOUTH);
    }
    private void createApplyButton()
    {
    	applyButton = new JButton(new ImageIcon(getClass().getResource("Icons/next.gif")));
        applyButton.setBackground(new Color(139, 69, 19));
        applyButton.setPreferredSize(new Dimension(100, 30));
        applyButton.addActionListener(new ActionListener() {

            /**
             * The actionPerfomed method of our class
             * @param e ActionEvent object that is generated when button is clicked
             */
            public void actionPerformed(ActionEvent e) {
                if (ChoosePlayerIcon.this.chessData.isGameOnLine() && Chess_Data.getChessData().getIsClientConnected()) {
                    sendPlayerIcon();
                }
                else
                {
                	updatePlayerImage();
                }
                dispose();
            }
        });
    }
    private void createPlayersButton()
    {
    	btnGroup = new ButtonGroup();

        pl1Button = new JRadioButton("Player 1");
        pl1Button.setBackground(Color.GRAY);
        pl1Button.setFont(new Font("Verdana", Font.BOLD, 16));

        pl2Button = new JRadioButton("Player 2");
        pl2Button.setBackground(Color.GRAY);
        pl2Button.setFont(new Font("Verdana", Font.BOLD, 16));

        btnGroup.add(pl1Button);
        btnGroup.add(pl2Button);
    }
    private void showPossiblePlayerIcons() {
        if (chessData.isGameOnLine()) {
            if (chessData.isServer()) {
                pl2Button.setEnabled(false);
                pl1Button.setSelected(true);
            } else {
                pl1Button.setEnabled(false);
                pl2Button.setSelected(true);
            }
        }
    }
    private void addAllButtonsToJPanel()
    {
    	panel = new JPanel();
        panel.setBackground(Color.GRAY);
        panel.add(pl1Button);
        panel.add(pl2Button);
        panel.add(applyButton);
    }
    private void createSouthPanel()
    {
    	southPanel = new JPanel();
        southPanel.setBackground(Color.GRAY);
        scroll = new JScrollPane(southPanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    }
    private void addAllSmileysToDialog()
    {
    	for (int i = 1; i < 16; i++) {
            map.put(new Integer(i), new Thumbnail("Icons/hercules" + i + ".gif"));
            southPanel.add(map.get(i));
            map.get(i).addMouseListener(new MouseAdapter() {

                /**
                 * The method mousePressed is overwritten in our class
                 * @param e MouseEvent object that is generated when mouse is clicked
                 */
                @Override
                public void mousePressed(MouseEvent e) {
                	
                    thumbnail = (Thumbnail) e.getSource();
                    for (Entry<Integer, Thumbnail> entry : map.entrySet())
                    {
                    	if (((Thumbnail) entry.getValue()).getBorder() != null) {
                            ((Thumbnail) entry.getValue()).setBorder(new LineBorder(Color.GRAY, 5));
                        }
                    }
                    updatePlayerImage();
                    ChoosePlayerIcon.this.chessData.setPlayers(ChoosePlayerIcon.this.chessData.getPlayers());
                    thumbnail.setBorder(new LineBorder(Color.GREEN, 5));
                }
            });
        }

    }

    public void updatePlayerImage()
    {
    	if(thumbnail != null)
    	{
	    	if (pl1Button.isSelected()) {
	            playerIconPath = thumbnail.getImagePath();
	            chessData.getPlayers().get(0).setImagePath(thumbnail.getImagePath());
	        } else if (pl2Button.isSelected()) {
	            playerIconPath = thumbnail.getImagePath();
	            chessData.getPlayers().get(1).setImagePath(thumbnail.getImagePath());
	        }
    	}
    }
    public void sendPlayerIcon() {
    	bridge.sendPlayerIconInfo(playerIconPath);
    }

    
}
