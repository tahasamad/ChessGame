package ChessGameKenai;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

public class UserManual extends JDialog {

    private JEditorPane webView;
    private ChessBoardView chessBoardView;
    public UserManual(ChessBoardView view) 
    {
        this.chessBoardView = view;
        createWebView();
        loadPageInWebView();
        createScrollBars();
        setUIProperties();
    }
    
    private void createScrollBars()
    {
    	JScrollPane scroll = new JScrollPane(webView, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    	this.add(scroll, "Center");
    }
    private void createWebView()
    {
    	 webView = new JEditorPane();         
         webView.setEditable(false);
         webView.setOpaque(false);
    }
    private void loadPageInWebView()
    {
        URL url = UserManual.class.getResource("TxtFiles/userguide.htm");
        try {
            webView.setPage(url);
        } catch (FileNotFoundException ex) {
            webView.setText("userguide.htm File Not Found");
        } catch (IOException ee) {
            JOptionPane.showMessageDialog(this, ee.getMessage());
        }
    }
    private void setUIProperties()
    {
    	this.setSize(700, 650);
        this.setTitle("User Manual");
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(chessBoardView);
        this.setVisible(true);
    }
}
