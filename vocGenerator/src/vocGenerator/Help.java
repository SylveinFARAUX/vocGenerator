package vocGenerator;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

public class Help extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private static String version="1.1";
	private static JButton okButton;
	private static Help dialog;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			dialog = new Help();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Help() {
		setResizable(false);
		setBounds(100, 100, 450, 318);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		String texte=	"<html><CENTER><u>vocGenerator</u></CENTER><BR><BR><BR>"+
						"Logiciel libre<BR>"+
						"vocGenerator charge une liste de mots de vocabulaire pré-enregistré<BR>"+
						"dans un fichier (voc_list.txt) et en renvoie une sous-liste aléatoire <BR>"+
						"avec des paramètres spécifiés.<BR><BR><BR><BR>"+
						"Développeur : Sylvein FARAUX<BR>"+
						"Collaborateur : Jérémy ANTOINE<BR>"+
						"Version : "+version+"</html>";
		
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JLabel lblNewLabel = new JLabel(texte);
			contentPanel.add(lblNewLabel);
		}
		{
			JPanel buttonPane = new JPanel();
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			{
				okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
				
				okButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						// TODO Auto-generated method stub
						super.mouseClicked(e);
						dispose();
					}
				});
			}
		}
	}

}
