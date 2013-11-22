package ChessGameKenai;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class HelpTopics extends JDialog {

    private JTextArea txtArea;   
    private ChessBoardView chessBoardView;

    public HelpTopics(ChessBoardView view) {
        this.chessBoardView = view;
        
        createTextArea();
        addTextAreaToScrollPanel();
        readChessRulesAndAddToTextArea();
        setUIProperties();
        
    }
    private void setUIProperties()
    {
    	this.setTitle(" CHESS RULES ");
        this.setSize(800, 400);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(chessBoardView);
        this.setVisible(true);
    }
    private JTextArea createTextArea()
    {
    	txtArea = new JTextArea() {
            @Override
            public void paintComponent(Graphics g) {
                int w = txtArea.getWidth();
                int h = txtArea.getHeight();

                URL url = txtArea.getClass().getResource("Icons/background.jpg");
                Toolkit toolkit = this.getToolkit();
                Image image = toolkit.getImage(url);
                g.drawImage(image, 0, 0, w, h, txtArea);
                super.paintComponent(g);
            }
        };
        txtArea.setForeground(Color.WHITE);
        txtArea.setFont(new Font("Verdana", Font.PLAIN, 16));
        txtArea.setOpaque(false);
        txtArea.setLineWrap(true);
        txtArea.setEditable(false);
        txtArea.setCaretPosition(0);
        return txtArea;
    	
    }
    private void addTextAreaToScrollPanel()
    {
    	JScrollPane scrollPane = new JScrollPane(txtArea);
    	Container container = this.getContentPane();
        container.add(scrollPane);
    }
    private void  readChessRulesAndAddToTextArea()
    {
    	try {
            BufferedReader input = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("TxtFiles/chessrules.txt")));

            try {
                String line = null;
                while ((line = input.readLine()) != null) {
                    txtArea.append(line + "\n");
                }
            } finally {
                input.close();
            }
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(chessBoardView, "chessrules.txt Not Found", "File Not Found", JOptionPane.ERROR_MESSAGE);
            HelpTopics.this.dispose();
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }

    }
}
