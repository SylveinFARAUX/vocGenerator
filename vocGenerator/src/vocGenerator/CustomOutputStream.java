package vocGenerator;

import java.awt.Font;
import java.io.IOException;
import java.io.OutputStream;
 
import javax.swing.JTextArea;
 
/**
 * This class extends from OutputStream to redirect output to a JTextArrea
 * @author www.codejava.net
 *
 */
public class CustomOutputStream extends OutputStream {
    private JTextArea textArea;
     
    public CustomOutputStream(JTextArea textArea) {
        this.textArea = textArea;
        textArea.setFont(new Font("Monospaced", Font.BOLD, 12));
        textArea.setFont(textArea.getFont().deriveFont(12f));
    }
     
    @Override
    public void write(int b) throws IOException {
        // redirects data to the text area
        textArea.append(new String(new byte[] { (byte) b }));
        // scrolls the text area to the end of data
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }
    
    @Override
	public synchronized void write(byte[] b, int off, int len) throws IOException {
		textArea.append(new String(b, off, len));
		textArea.setCaretPosition(textArea.getDocument().getLength());
	}
}