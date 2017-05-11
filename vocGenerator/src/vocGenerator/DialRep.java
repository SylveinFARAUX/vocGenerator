package vocGenerator;


import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeListener;

public class DialRep extends JDialog {

	private final JPanel contentPanel = new JPanel();
/**
 * 
 * @param title Le titre du JDialog
 * @param fond La couleur du fond du JPanel
 * @param text_color La couleur du texte
 */
	public DialRep(String title, Color fond, Color text_color) {
		setAutoRequestFocus(false);
		setAlwaysOnTop(true);
		setVisible(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle(title);
		setResizable(false);
		setBounds(100, 100, 238, 110);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(fond);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel = new JLabel("Le r\u00E9pertoire a bien \u00E9t\u00E9 cr\u00E9\u00E9 !");
		lblNewLabel.setForeground(text_color);
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPanel.add(lblNewLabel);
	
		JPanel buttonPane = new JPanel();
		buttonPane.setBackground(fond);
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton okButton = new JButton("OK");
		okButton.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		okButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				super.mouseClicked(arg0);
				dispose();
			}
		});
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
			
	}

}