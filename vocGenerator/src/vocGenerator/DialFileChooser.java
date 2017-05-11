package vocGenerator;



import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileView;
import javax.swing.SwingConstants;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.BoxLayout;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.CardLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;



//import net.miginfocom.swing.MigLayout;



import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

public class DialFileChooser extends JDialog {
	private JFileChooser parcours;
	private JPanel panneauAff;
	private static File repertoir= new File(System.getProperty("user.dir"));
	private File fichierSelectionne= null;
	private static Liste liste;
	private int retourfile;
	public static String SELECTION="";
	public static String SELECTION2= "";
	SFFiltre filtre;
	
	private static DialRep dialogRepertoir;
	
	private static int quefaire;
	// -1 :                                0 :                               1 : message
// true : on veut enregistrer nous meme | false : on selectionne un fichier
// param supplementaire true : si on veut afficher le petit message

	/**
	 * getFile
	 * @return Retourne le fichier selectionné
	 */
	public File getFile()
	{
		return fichierSelectionne;
	}
	
	/**
	 * Creer la boite de dialogue du parcours de fichier
	 * @param quefaire 1 si on demande l'enregistrement d'un fichier via le message, 0 si on demande le chargement d'un fichier, -1 si on demande l'enregistrement d'un fichier via le JFileChooser
	 * @param liste si un fichier doit être sauvegarder
	 */
	public DialFileChooser(int quefaire, String nom_extension, final Liste... liste) {
		setResizable(false);
		setBounds(100, 100, 275, 102);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		
		filtre= new SFFiltre(nom_extension);
		panneauAff = new JPanel();
		panneauAff.setBounds(0, 0, 269, 74);
		getContentPane().add(panneauAff);
		JPanel buttonPane = new JPanel();
		
		if (liste.length==1)
			this.liste= liste[0];
		this.quefaire= quefaire;
		
		JButton okButton = new JButton("Oui");
		okButton.setVerticalAlignment(SwingConstants.BOTTOM);
		
		buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnOui = new JButton("Oui");
		buttonPane.add(btnOui);
		
		btnOui.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				super.mouseClicked(arg0);
				changement("quitter");
				
				//hide();
				dispose();
			}
		});
		
		JButton btnEnregistrer = new JButton("Enregistrer");
		buttonPane.add(btnEnregistrer);
		buttonPane.add(btnEnregistrer);
		
		btnEnregistrer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				super.mouseClicked(arg0);
				changement("sauver");
				dispose();
			}
		});
		
		
		JButton cancel = new JButton("Annuler");
		cancel.setVerticalAlignment(SwingConstants.TOP);
		buttonPane.add(cancel);
		panneauAff.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		cancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				super.mouseClicked(arg0);
				changement("annuler");
				dispose();
			}
		});
		
		JLabel lbltesvousSDe = new JLabel("Quitter sans enregistrer ?");
		lbltesvousSDe.setHorizontalAlignment(SwingConstants.CENTER);
		lbltesvousSDe.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		panneauAff.add(lbltesvousSDe);
		panneauAff.add(buttonPane);
		
		if (!repertoir.exists()) // si le repertoir n'existe pas je le crée
			if (repertoir.mkdirs()) // et si j'arrive a le creer j'ouvre un dialog
				dialogRepertoir = new DialRep("vocGenerator", Color.GRAY, Color.BLACK);
	}
	
	public synchronized void changement(String changement)
	{
		SELECTION= changement;
		
		notify();
		setVisible(false);
	}
	public synchronized void hop()
	{
		switch(quefaire)
		{
			case 0 : // on charge un fichier
				panneauAff.setVisible(false);
				parcours= new JFileChooser();
				parcours.setCurrentDirectory(repertoir);
				parcours.addChoosableFileFilter(filtre);
				parcours.setFileFilter(filtre);
				parcours.setAcceptAllFileFilterUsed(true);
				
				retourfile= parcours.showOpenDialog(getContentPane());
				
				if (retourfile== JFileChooser.APPROVE_OPTION) // si je suis sorti par le bouton OK (un fichier a été choisi)
				{
					fichierSelectionne= parcours.getSelectedFile();
					changement("charger");
					dispose();
				}
				else
					changement("annuler");
				
			break;
			case -1: // si on sauve sans message (mais avec la boite de dialogue)
				panneauAff.setVisible(false);
				parcours= new JFileChooser();
				parcours.setCurrentDirectory(repertoir);
				parcours.addChoosableFileFilter(filtre);
				parcours.setFileFilter(filtre);
				parcours.setAcceptAllFileFilterUsed(true);
				parcours.setDialogTitle("Enregistrer sous");
				
				retourfile= parcours.showSaveDialog(getContentPane());
				
				if (retourfile== JFileChooser.APPROVE_OPTION) // si je suis sorti par le bouton OK (un fichier a été choisi)
				{
					try {
						String fichier;
						String extension= parcours.getSelectedFile().getAbsolutePath().substring(parcours.getSelectedFile().getAbsolutePath().length()-filtre.getLength());
						
						if (extension.toLowerCase().compareTo(filtre.getSuffixe().toLowerCase())==0)
						{
							fichier = parcours.getSelectedFile().getAbsolutePath();
							extension= "h";
						}
						else
							fichier = parcours.getSelectedFile().getAbsolutePath()+filtre.getSuffixe();
						
						liste.sauvegarder(fichier);
						
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					dispose();
				}
				//else
					//changement("annuler");
			break;
			case 1 : // on affiche le message pour sauver (sans la boite de dialogue)
				setVisible(true);
			break;
		}
	}
	public synchronized String Selection()
	{
		while (SELECTION.compareTo("")==0)
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		setVisible(false);
		return SELECTION;
	}
}
