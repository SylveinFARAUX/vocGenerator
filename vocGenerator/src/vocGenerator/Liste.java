package vocGenerator;

import java.util.LinkedList;
import java.util.Random;
import java.awt.Color;
import java.io.BufferedReader;

// lire & ecrire dans les fichier //
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
//------------------------------ //

import java.io.IOException;
import java.io.Serializable;


public class Liste implements Serializable{

	public LinkedList<String> liste_mot, liste_mot_fichier, 
							  liste_brut, doublons;
	private static File temp;
	
	public Liste()
	{
		liste_mot= new LinkedList<String>();
		liste_mot_fichier= new LinkedList<String>();
		liste_brut = new LinkedList<String>();
		doublons= new LinkedList<String>();
	}
	public LinkedList<String> getList() {return liste_mot;}
	
	public String premier_mot()
	{
		String mot_1= "";
		try{
			InputStream flux=new FileInputStream("voc_list.txt"); 
			InputStreamReader lecture=new InputStreamReader(flux);
			BufferedReader buff=new BufferedReader(lecture);
			mot_1=buff.readLine();
			buff.close(); 
		} catch (Exception e){System.out.println(e.toString());}
		return mot_1;
	}
	public void get_liste_mot_fichier()
	{
		try{
			InputStream flux=new FileInputStream("voc_list.txt"); 
			InputStreamReader lecture=new InputStreamReader(flux);
			BufferedReader buff=new BufferedReader(lecture);
			String ligne;
			while ((ligne=buff.readLine())!=null){
				liste_brut.add(ligne);
			}
			buff.close(); 
		} catch (Exception e){System.out.println(e.toString());}
		
		// -- RENSEIGNEMENT DES DOUBLONS -- //
		for (int i=0; i<liste_brut.size()-1; i++)
			for (int j=i+1; j<liste_brut.size(); j++)
				if (liste_brut.get(i).compareTo(liste_brut.get(j))==0)
					doublons.add(liste_brut.get(i));
		// -------------------------------- //	
		
		// -- NETTOYAGE DE BRUT POUR AVOIR LA LISTE SANS DOUBLONS -- //
		for (String mot : liste_brut) {
			liste_mot_fichier.add(mot);
		}
		
		if (doublons.size()>0)
			for (String mot : doublons)
				liste_mot_fichier.remove(mot);	
		// --------------------------------------------------------- //		
	}
	public int get_taille_fichier()
	{
		if (liste_mot_fichier.size()== 0)
			get_liste_mot_fichier();
		return liste_mot_fichier.size();
	}
	public int get_taille_fichierSansVerif()
	{
		if (liste_mot_fichier.size()== 0)
			get_liste_mot_fichier();
		return liste_brut.size();
	}
	public void charger_voc(int nombre)
	{
		liste_mot.clear();
		if (liste_mot_fichier.size()==0)
			get_liste_mot_fichier();
		
		int nombreAleatoire;
		nombre= (nombre>liste_mot_fichier.size())? liste_mot_fichier.size() : nombre;
		int tab[]= new int[nombre];
		boolean valide;
		String first= liste_mot_fichier.get(0);
		for (int i=0 ; i < nombre ; i++)
		{
			do {
			valide= true;
			nombreAleatoire = new Random().nextInt(liste_mot_fichier.size());
			
			if (nombreAleatoire==0 && first.compareTo("Jeremy")==0)
				valide= false;
			for (String mot : liste_mot)
				if (mot.compareTo(liste_mot_fichier.get(nombreAleatoire))==0)
					valide = false;
			} while (!valide);
			liste_mot.add(liste_mot_fichier.get(nombreAleatoire));
		}
	}
	
	public void sauvegarder(String path_file) throws IOException
	{
		FileOutputStream fos = new FileOutputStream(path_file);
		ObjectOutputStream oos= new ObjectOutputStream(fos);

		try 
		{
			oos.writeObject(this); // Ecriture dans le flux de sortie
			oos.flush(); // Vide le tampon
		} 
		finally 
		{
			try {oos.close();} 
			finally {fos.close();}
		}	
	}
	
	public Liste charger(String path_file)
	{
		Liste u = null;
		try {
			// Ouverture du flux d'entrée sur le fichier
			FileInputStream fis = new FileInputStream(path_file);
			ObjectInputStream ois= new ObjectInputStream(fis);

			try {
				// Lecture du flux et attribution à l'objet u
				u = (Liste) ois.readObject();
			} finally {

				// Fermeture des flux
				try {
				ois.close();
				} finally {
					fis.close();
				}
			}
		} catch(IOException ioe) {
			ioe.printStackTrace();
		} catch(ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
		return u;
	}
	//static Liste l;
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		
		/*TESTS 
		Liste liste= new Liste();
		liste.charger_voc(4);
		Option o= new Option();*/
		
		/*/ enregistrer la liste via ENREGISTRER SOUS //
		DialFileChooser filedial= new DialFileChooser(-1, "list", liste);
		filedial.hop();
		filedial.SELECTION= "";
		// ----------------------------------------- /*/
		
		/*/ enregistrer la liste en temporaire //
		temp= new File(System.getProperty("user.dir"), "temp"+".list");
		
			if (temp.exists())
				temp.delete();
			liste.sauvegarder(System.getProperty("user.dir")+"\\temp"+".list");
		// ---------------------------------- /*/
		
		/*/ charger la liste (auto) //
		liste = liste.charger(System.getProperty("user.dir")+"\\temp"+".list");
		// ----------------------- /*/
		
		/*/ charger la liste via OUVRIR //
		DialFileChooser filedial= new DialFileChooser(0, "list");
		filedial.hop(); // peut-être à mettre dans un thread (injustifié)
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				switch(filedial.Selection())
				{
					case "charger" :
						l= liste.charger(filedial.getFile().getAbsolutePath());
						for (String mot : l.liste_mot)
							System.out.println(mot);
						filedial.SELECTION= "";
					break;
					case "annuler" :
						filedial.SELECTION= "";
					break;
				}
			}
		}).start();
		// --------------------------- /*/
		
		
	}
}
