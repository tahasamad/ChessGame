/*
 * Description: About.java
 * Author: Mario Bruno
 * Date: 28-Feb-2011
 * Version: 2.0
 */
package ChessGameKenai;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class About extends JDialog {

    //INSTANCE VARIABLES
    private CostumPanel panelContainer;
    private int Ypos = 200;
    private volatile boolean isKill = true;
    private ChessBoardView chessBoardView;

    /**
     * Overloaded constructor which receives a reference to the ChessBoardView class
     * only to set the location of the dialog in front of the main game. The constructor
     * also has all the components needed to create its gui.
     * @param view ChessBoardView object
     */
    public About(ChessBoardView view) {
        this.chessBoardView = view;
        
        addPanelContainer();
        createAnimation();
        addCloseButton();
        addTitle();
        addWindowCloseListener();
        setUIProperties();
    }
    private void setUIProperties()
    {
        this.setSize(350, 300);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(chessBoardView);
        this.setTitle("About");
        this.setVisible(true);
    }

    private void createAnimation()
    {
    	Animation animate = new Animation();
        animate.makePanel();
        animate.start();
    }
    private void addWindowCloseListener()
    {
    	this.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                isKill = false;
            }
        });

    }
    private void addCloseButton()
    {
    	CostumPanel closeButton = new CostumPanel();
        closeButton.setOpaque(false);
        JButton btnClose = new JButton("Close");
        btnClose.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                About.this.dispose();
                isKill = false;

            }
        });
        closeButton.add(btnClose);
        panelContainer.add(closeButton, BorderLayout.SOUTH);
    }
    private void addTitle()
    {
    	CostumPanel topBar = new CostumPanel();
        topBar.setOpaque(false);
        
        JLabel title = new JLabel("Chess'N'Chat");
        title.setFont(new Font("Verdana", Font.BOLD, 23));
        title.setForeground(Color.WHITE);
        
        topBar.add(title);
        panelContainer.add(topBar, BorderLayout.NORTH);
    }
    private void addPanelContainer()
    {
    	panelContainer = new CostumPanel("Icons/background.jpg", new BorderLayout());
        getContentPane().add(panelContainer);
    }
    class Animation extends Thread {

        @Override
        public void run() {
            while (isKill) {
                repaint();
                try {
                    Thread.sleep(44);
                } catch (InterruptedException ex) {
                    System.out.println(ex.getMessage());
                    isKill = false;
                }
            }
        }

        /**
         * makePanel is the method that creates the center panel. It uses the
         * paintComponent method to draw the strings and images on the panel.
         * Once the text has reached the top of the panel, it just stops there.
         */
        public void makePanel() {
            final Image image = new ImageIcon(getClass().getResource("Icons/logo.gif")).getImage();
            final Image image2 = new ImageIcon(getClass().getResource("Icons/logo.gif")).getImage();

            CostumPanel aboutText = new CostumPanel() {

                @Override
                public void paintComponent(Graphics g) {

                    g.setFont(new Font("Arial", Font.PLAIN, 18));
                    g.setColor(Color.ORANGE);
                    g.drawString("Created By:", 120, Ypos);
                    g.drawImage(image, 30, Ypos + 35, this);
                    g.drawImage(image2, 230, Ypos + 35, this);
                    g.drawString("Dimitri", 135, Ypos + 35);
                    g.drawString("Mario", 135, Ypos + 65);
                    g.drawString("Val", 135, Ypos + 95);
                    g.drawString("Version 2.0", 118, Ypos + 135);
                    Ypos--;

                    //Once the texts have reached the top of the panel, keep the Y position
                    if (Ypos <= 15) {
                        Ypos = 15;
                        isKill = false;

                    }
                }
            };
            aboutText.setOpaque(false);
            panelContainer.add(aboutText, BorderLayout.CENTER);
        }
    }
}
