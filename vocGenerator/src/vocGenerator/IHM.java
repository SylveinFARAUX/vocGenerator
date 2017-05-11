package vocGenerator;

import java.awt.EventQueue;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.GridLayout;

import javax.swing.JTextPane;

import java.awt.BorderLayout;

import javax.swing.JButton;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Component;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.JTextArea;

import java.awt.Font;


public class IHM {

	private JFrame frame= new JFrame();
	private static JPanel panel = new JPanel();
	private JTextField textField= new JTextField();
	private static DialFileChooser filedial;
	private static File voc_list= new File(System.getProperty("user.dir"), "voc_list.txt"), temp_list= new File(System.getProperty("user.dir"), "temp.list"), config= new File(System.getProperty("user.dir"), "config.config");
	private static JPanel panelCouleur;
	private static JPanel panel_lower= new JPanel(), panel_upper= new JPanel(), panel_outils= new JPanel();
	private static JTextPane textPane= new JTextPane();
	private JScrollPane pane= new JScrollPane(textPane);
	private static Liste liste= new Liste();
	private static Option option= new Option();
	private static String list_string, nbMotsString= "";
	private static JOptionPane popup= new JOptionPane(); 
	private static JMenuBar menuBar = new JMenuBar();
	
	private JMenu mnConfiguration = new JMenu("Configuration"), 
				  mnOption = new JMenu("Option"),
				  mnSauvegardeAuto = new JMenu("Sauvegarde auto."),
				  mnChargementAuto = new JMenu("Chargement auto.");
	
	private JMenuItem mntmSauverLaListe, mntmEnregistrer, mntmSauverLaConfiguration, mntmEnregistrerSous, mntmCouleurDeFond_1, mntmChargerUneConfiguration, mntmQuitter = new JMenuItem("Quitter");
	private static JButton btnGnrer= new JButton("G\u00E9n\u00E9rer");;
	private JCheckBoxMenuItem	chckbxmntmConfiguration= new JCheckBoxMenuItem("Configuration"),	chckbxmntmConfiguration_1 = new JCheckBoxMenuItem("Configuration"),
										chckbxmntmListeDesMots= new JCheckBoxMenuItem("Liste des mots"),		chckbxmntmListeDesMots_1= new JCheckBoxMenuItem("Liste des mots");	
	private static Help dialog;

	private JMenu mnPropos= new JMenu("Informations");
	private JPanel panel_log= new JPanel();
	private final JTextArea logArea = new JTextArea();
	private JScrollPane logScroll= new JScrollPane(logArea);
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IHM window = new IHM();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public IHM() {
		setUI();
		setActions();
	}

	/**
	 * Initialise les éléments graphiques
	 */
	private void setUI() {
		frame.setTitle("vocGenerator");
		if(liste.get_taille_fichier()>0 && liste.premier_mot().compareTo("Jeremy")==0)
		{
			frame.setIconImage(Toolkit.getDefaultToolkit().getImage(IHM.class.getResource("/vocGenerator/ico.png")));
			frame.setTitle("Boss Jerem' Staws");
		}
		else
			frame.setIconImage(Toolkit.getDefaultToolkit().getImage(IHM.class.getResource("/vocGenerator/icon_.png")));
		
		frame.setBounds(100, 100, 700, 466);
		frame.setMinimumSize(new Dimension(715, 466));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		list_string= "";
		
		frame.setJMenuBar(menuBar);
		
		JMenu mnFichier = new JMenu("Liste des mots");
		menuBar.add(mnFichier);
		InputStream stream_ouvrir = getClass().getResourceAsStream("/vocGenerator/file.png");
		ImageIcon icon_ouvrir;
		try {
			icon_ouvrir = new ImageIcon(ImageIO.read(stream_ouvrir));
			mntmSauverLaListe = new JMenuItem("Ouvrir", icon_ouvrir);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		mnFichier.add(mntmSauverLaListe);
		mnFichier.addSeparator();

		
		InputStream stream_sauver = getClass().getResourceAsStream("/vocGenerator/sauver.png");
		ImageIcon icon_sauver;
		try {
			icon_sauver = new ImageIcon(ImageIO.read(stream_sauver));
			mntmEnregistrer = new JMenuItem("Enregistrer", icon_sauver);
			mntmEnregistrerSous = new JMenuItem("Enregistrer Sous ...", icon_sauver);
			mntmSauverLaConfiguration = new JMenuItem("Enregistrer", icon_sauver);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		mnFichier.add(mntmEnregistrer);
		mnFichier.add(mntmEnregistrerSous);
		menuBar.add(mnConfiguration);
		
		mnConfiguration.add(mntmSauverLaConfiguration);
		
		InputStream stream_charger = getClass().getResourceAsStream("/vocGenerator/charger.png");
		ImageIcon icon_charger;
		try {
			icon_charger = new ImageIcon(ImageIO.read(stream_charger));
			mntmChargerUneConfiguration = new JMenuItem("Charger", icon_charger);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		mnConfiguration.add(mntmChargerUneConfiguration);
		
		InputStream stream_palette = getClass().getResourceAsStream("/vocGenerator/palette.png");
		ImageIcon icon_palette;
		try {
			icon_palette = new ImageIcon(ImageIO.read(stream_palette));
			mntmCouleurDeFond_1 = new JMenuItem("Couleur de fond", icon_palette);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		mnConfiguration.addSeparator();
		mnConfiguration.add(mntmCouleurDeFond_1);
		
		menuBar.add(mnOption);
		
		mnOption.add(mnSauvegardeAuto);
		
		mnSauvegardeAuto.add(chckbxmntmConfiguration);
		
		mnSauvegardeAuto.add(chckbxmntmListeDesMots);
		
		mnOption.add(mnChargementAuto);
		
		mnChargementAuto.add(chckbxmntmConfiguration_1);
		
		mnChargementAuto.add(chckbxmntmListeDesMots_1);
		mnOption.addSeparator();
		
		mnOption.add(mntmQuitter);
		
		menuBar.add(mnPropos);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
		frame.getContentPane().add(panel);
		
		GridLayout gl = new GridLayout(1, 2);
		gl.setHgap(5); //Cinq pixels d'espace entre les colonnes (H comme Horizontal)
		gl.setVgap(5); //Cinq pixels d'espace entre les lignes (V comme Vertical) 
		//Ou en abrégé : GridLayout gl = new GridLayout(1, 2, 5, 5);

		
		panel.setLayout(gl);
		
		textPane.setText(list_string);
		
		pane.setAutoscrolls(true);
		textPane.setEditable(false);
		textPane.setToolTipText("espace de visualisation");
		panel.add(pane);

		panel.add(panel_outils);
		panel_outils.setLayout(new GridLayout(2, 1, 5, 5));
		panel_upper.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Informations", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		
		panel_outils.add(panel_upper);
		panel_upper.setLayout(new GridLayout(0, 1, 0, 0));
		panel_log.setBackground(UIManager.getColor("Button.background"));
		
		panel_log.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_upper.add(panel_log);
		panel_log.setLayout(new GridLayout(1, 0, 0, 0));
		
		logArea.setLineWrap(true);
		
		logArea.setBackground(Color.BLACK);
		logArea.setForeground(Color.WHITE);
		logArea.setEditable(false);

		PrintStream printStream = new PrintStream(new CustomOutputStream(logArea));
		System.setOut(printStream);
		panel_log.add(logScroll);
		System.out.println("Initialisation...");
		System.out.println("Lecture de voc_list.txt et config.config");
		System.out.print("Nombre d'entrées du fichier de vocabulaire : ");
		if (voc_list.exists() && liste.get_taille_fichierSansVerif()>0)
			System.out.println(String.valueOf(liste.get_taille_fichierSansVerif()));
		else
		{
			System.out.println("Le fichier spécifié est introuvable ou ne contient pas de mot");
			 btnGnrer.setEnabled(false);
		}

		System.out.println("Nombre de mots différents du fichier : "+liste.get_taille_fichier());
		
		
		FlowLayout fl_panel_lower = (FlowLayout) panel_lower.getLayout();
		fl_panel_lower.setAlignOnBaseline(true);
		panel_outils.add(panel_lower);
		//panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));
		
		//On crée un conteneur avec gestion horizontale
		Box b2 = Box.createVerticalBox();
		b2.setAlignmentY(Component.TOP_ALIGNMENT);
		b2.setAlignmentX(Component.CENTER_ALIGNMENT);

		JLabel label= new JLabel("Nb de mots à afficher dans l'espace de visualisation");
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
	    b2.add(label);
	    
	    b2.add(Box.createRigidArea(new Dimension(0, 5)));
	    
	    Box b1 = Box.createHorizontalBox();
	    b1.setAlignmentY(Component.BOTTOM_ALIGNMENT);
	    
	    
	    JPanel panel_bouton= new JPanel();
	    panel_bouton.setLayout(new BorderLayout());
	   
	    
	    btnGnrer.setAlignmentX(Component.RIGHT_ALIGNMENT);
	    
	    panel_bouton.add(btnGnrer, BorderLayout.CENTER);
	    b1.add(panel_bouton);
	    
	    //Idem
	    Box global = Box.createVerticalBox();
	    
	    global.add(b2);
	    
	    textField.setHorizontalAlignment(SwingConstants.CENTER);
	    nbMotsString= String.valueOf(option.getNbMots());
	    textField.setText(nbMotsString);
	    
	    b2.add(textField);
	    textField.setColumns(10);
	    Component rigidArea = Box.createRigidArea(new Dimension(10, 10));
	    global.add(rigidArea);
	    global.add(b1);
	    panel_lower.add(global);
	    
	    recharger_app(); 
	    System.out.println("Configuration actuelle :");
	    
	    if (chckbxmntmConfiguration.isSelected() || chckbxmntmListeDesMots.isSelected())
	    {
	    	if (chckbxmntmConfiguration.isSelected() && chckbxmntmListeDesMots.isSelected())
	    		System.out.println("Sauvegarde auto. de la liste de mot & la configuration");
	    	else if (chckbxmntmConfiguration.isSelected() && !chckbxmntmListeDesMots.isSelected())
	    		System.out.println("Sauvegarde auto. de la configuration");
	    	else if (!chckbxmntmConfiguration.isSelected() && chckbxmntmListeDesMots.isSelected())
	    		System.out.println("Sauvegarde auto. de la liste de mot");
	    }
	    else
	    	System.out.println("Aucune sauvegarde automatique");
	    if (chckbxmntmConfiguration_1.isSelected() || chckbxmntmListeDesMots_1.isSelected())
	    {
	    	if (chckbxmntmConfiguration_1.isSelected() && chckbxmntmListeDesMots_1.isSelected())
	    		System.out.println("Chargement auto. de la liste de mot & la configuration");
	    	else if (chckbxmntmConfiguration_1.isSelected() && !chckbxmntmListeDesMots_1.isSelected())
	    		System.out.println("Chargement auto. de la configuration");
	    	else if (!chckbxmntmConfiguration_1.isSelected() && chckbxmntmListeDesMots_1.isSelected())
	    		System.out.println("Chargement auto. de la liste de mot");
	    }
	    else
	    	System.out.println("Aucun chargement automatique");
	}

	/**
	 * Initialise les listeners
	 */
	private void setActions() {
		
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				option.setCouleur(panel.getBackground());
	    		option.setNbMots(Integer.parseInt(nbMotsString));
	    		option.setListeSauvAuto(chckbxmntmListeDesMots.isSelected());
	    		option.setListeChargeAuto(chckbxmntmListeDesMots_1.isSelected());
	    		option.setOptSauvAuto(chckbxmntmConfiguration.isSelected());
	    		option.setOptChargeAuto(chckbxmntmConfiguration_1.isSelected());
	    		if (config.exists())
					config.delete();
	    		try {
					option.sauvegarder(System.getProperty("user.dir")+"\\config.config");
				} catch (IOException evt) {
					// TODO Auto-generated catch block
					evt.printStackTrace();
				}
	    		
	    		if (option.getListeSauvAuto())
	    		{
	    			if (temp_list.exists())
						temp_list.delete();
					try {
						liste.sauvegarder(System.getProperty("user.dir")+"\\temp.list");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	    		}
			}
		});
		
		mntmSauverLaListe.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				
				filedial= new DialFileChooser(0, "list");
				filedial.hop();
				
				new Thread(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						
						switch(filedial.Selection())
						{
							case "charger" :
								list_string= "";
								liste= liste.charger(filedial.getFile().getAbsolutePath());
								for (String mot : liste.getList())
									list_string+= mot+"\n";
								textPane.setText(list_string);
								filedial.SELECTION= "";
							break;
							case "annuler" :
								filedial.SELECTION= "";
							break;
						}
					}
				}).start();
			}
		});
		
		mntmEnregistrer.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {				
				if (temp_list.exists())
					temp_list.delete();
				try {
					liste.sauvegarder(System.getProperty("user.dir")+"\\temp.list");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		mntmSauverLaConfiguration.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				option.setCouleur(panel.getBackground());
	    		option.setNbMots(Integer.parseInt(nbMotsString));
	    		option.setListeSauvAuto(chckbxmntmListeDesMots.isSelected());
	    		option.setListeChargeAuto(chckbxmntmListeDesMots_1.isSelected());
	    		option.setOptSauvAuto(chckbxmntmConfiguration.isSelected());
	    		option.setOptChargeAuto(chckbxmntmConfiguration_1.isSelected());
	    		if (config.exists())
					config.delete();
	    		try {
					option.sauvegarder(System.getProperty("user.dir")+"\\config.config");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		mntmEnregistrerSous.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				DialFileChooser filedial= new DialFileChooser(-1, "list", liste);
				filedial.hop();
				filedial.SELECTION= "";
			}
		});
		
		btnGnrer.addMouseListener(new MouseAdapter() {
	    	@Override
	    	public void mouseClicked(MouseEvent e) {
	    		
	    		if (btnGnrer.isEnabled())
	    		{
		    		if (nbMotsString.length()<1 || Integer.valueOf(nbMotsString)==0)
		    		{
		    			popup.showMessageDialog(btnGnrer,"Le nombre de mots à générer est incorrect\nLa valeur par défaut sera 2","nombre de mots à générer",JOptionPane.INFORMATION_MESSAGE);
		    			nbMotsString= "2";
		    			textField.setText(nbMotsString);
		    		}
		    		
		    		if (nbMotsString.length()>1 && Integer.valueOf(nbMotsString)>liste.get_taille_fichier())
		    		{
		    			popup.showMessageDialog(btnGnrer,"Le nombre de mots à générer est incorrect\nLa valeur par défaut sera le nombre max de mot de vocabulaire : "+liste.get_taille_fichier(),"nombre de mots à générer",JOptionPane.INFORMATION_MESSAGE);
		    			nbMotsString= String.valueOf(liste.get_taille_fichier());
		    			textField.setText(nbMotsString);
		    		}
		    		
		    		liste.charger_voc(Integer.valueOf(nbMotsString));
		    		list_string= "";
		    		for (String mot : liste.getList())
		    			list_string+= mot+"\n";
		    		
		    		option.setCouleur(panel.getBackground());
		    		option.setNbMots(Integer.parseInt(nbMotsString));
		    		option.setListeSauvAuto(chckbxmntmListeDesMots.isSelected());
		    		option.setListeChargeAuto(chckbxmntmListeDesMots_1.isSelected());
		    		option.setOptSauvAuto(chckbxmntmConfiguration.isSelected());
		    		option.setOptChargeAuto(chckbxmntmConfiguration_1.isSelected());
		    		
		    		textPane.setText(list_string);
	    		}
	    	}
	    });
		
		mntmChargerUneConfiguration.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (config.exists())
				{
					option= option.charger(System.getProperty("user.dir")+"\\config.config");
			    	
			    	chckbxmntmListeDesMots.setSelected(option.getListeSauvAuto());
			    	chckbxmntmListeDesMots_1.setSelected(option.getListeChargeAuto());
			    	chckbxmntmConfiguration.setSelected(option.getOptSauvAuto());
			    	chckbxmntmConfiguration_1.setSelected(option.getOptChargeAuto());
			    	
		    		nbMotsString= String.valueOf(option.getNbMots());
		    		textField.setText(nbMotsString);
			    	
		    		panel.setBackground(option.getColor());
					panel_lower.setBackground(option.getColor());
					panel_upper.setBackground(option.getColor());
					panel_outils.setBackground(option.getColor());
			    	
				}
				
			}
		});
		
		mntmCouleurDeFond_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				JColorChooser choixCouleur= new JColorChooser();
				Color couleur= null;
				
				couleur = choixCouleur.showDialog(panelCouleur, "Choisissez la couleur du fond", panel.getBackground());
				if (couleur==null)
					couleur=panel.getBackground();
				
				panel.setBackground(couleur);
				panel_lower.setBackground(couleur);
				panel_upper.setBackground(couleur);
				panel_outils.setBackground(couleur);
			}
		});
		
		textField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
				try {
				int y= Integer.parseInt(""+e.getKeyChar());
				nbMotsString+= e.getKeyChar();
				} catch(NumberFormatException nfe) { }
			}
			
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
				if (e.getKeyCode()==8) // si on efface
					try {
					nbMotsString= nbMotsString.substring(0, nbMotsString.length()-1); } catch(StringIndexOutOfBoundsException se) { }
				
				textField.setText("");
				textField.setText(nbMotsString);
			}
		});
		
		mnPropos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dialog = new Help();
				dialog.setVisible(true);
			}
		});
		
		mntmQuitter.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
					option.setCouleur(panel.getBackground());
		    		option.setNbMots(Integer.parseInt(nbMotsString));
		    		option.setListeSauvAuto(chckbxmntmListeDesMots.isSelected());
		    		option.setListeChargeAuto(chckbxmntmListeDesMots_1.isSelected());
		    		option.setOptSauvAuto(chckbxmntmConfiguration.isSelected());
		    		option.setOptChargeAuto(chckbxmntmConfiguration_1.isSelected());
		    		if (config.exists())
						config.delete();
		    		try {
						option.sauvegarder(System.getProperty("user.dir")+"\\config.config");
					} catch (IOException evt) {
						// TODO Auto-generated catch block
						evt.printStackTrace();
					}
		    		
		    		if (option.getListeSauvAuto())
		    		{
		    			if (temp_list.exists())
							temp_list.delete();
						try {
							liste.sauvegarder(System.getProperty("user.dir")+"\\temp.list");
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
		    		}
				frame.setVisible(false);
			    frame.dispose();
			}
		});
	}
	
	private void recharger_app() {
		if(config.exists())
	    {
	    	option= option.charger(System.getProperty("user.dir")+"\\config.config");
	    	
	    	chckbxmntmListeDesMots.setSelected(option.getListeSauvAuto());
	    	chckbxmntmListeDesMots_1.setSelected(option.getListeChargeAuto());
	    	chckbxmntmConfiguration.setSelected(option.getOptSauvAuto());
	    	chckbxmntmConfiguration_1.setSelected(option.getOptChargeAuto());
	    	
	    	if (option.getListeChargeAuto())
	    	{
	    		list_string= "";
	    		liste= liste.charger(temp_list.getAbsolutePath());
	    		for (String mot : liste.getList())
	    			list_string+= mot+"\n";
	    		textPane.setText(list_string);
	    	}
	    	if (option.getOptChargeAuto())
	    	{
	    		nbMotsString= String.valueOf(option.getNbMots());
	    		textField.setText(nbMotsString);
		    	
	    		panel.setBackground(option.getColor());
				panel_lower.setBackground(option.getColor());
				panel_upper.setBackground(option.getColor());
				panel_outils.setBackground(option.getColor());
	    	}
	    }
	}
}
