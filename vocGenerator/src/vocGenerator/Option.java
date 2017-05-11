package vocGenerator;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import java.io.InputStream;
import java.io.InputStreamReader;


public class Option implements Serializable{

	public int nb_mots;
	public boolean 	liste_sauv_auto=false,	liste_charge_auto=false,
							opt_sauv_auto=false,		opt_charge_auto=false;
	public Color couleur= Color.LIGHT_GRAY;
	public Option() 
	{
		nb_mots= 2;
		liste_sauv_auto= liste_charge_auto=	opt_sauv_auto= opt_charge_auto= false;
		//couleur= coul;
	}
	
	public int getNbMots(){return nb_mots;}
	public boolean getListeSauvAuto(){return liste_sauv_auto;}
	public boolean getListeChargeAuto(){return liste_charge_auto;}
	public boolean getOptSauvAuto(){return opt_sauv_auto;}
	public boolean getOptChargeAuto(){return opt_charge_auto;}
	public Color getColor(){return couleur;}
	
	public void setNbMots(int nb_mot){nb_mots= nb_mot;}
	public void setListeSauvAuto(boolean arg){liste_sauv_auto= arg;}
	public void setListeChargeAuto(boolean arg){liste_charge_auto= arg;}
	public void setOptSauvAuto(boolean arg){opt_sauv_auto= arg;}
	public void setOptChargeAuto(boolean arg){opt_charge_auto= arg;}
	public void setCouleur(Color coul){couleur= coul;}
	
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
	
	public Option charger(String path_file)
	{
		Option u = null;
		try {
			// Ouverture du flux d'entrée sur le fichier
			FileInputStream fis = new FileInputStream(path_file);
			ObjectInputStream ois= new ObjectInputStream(fis);

			try {
				// Lecture du flux et attribution à l'objet u
				
				u = (Option) ois.readObject();
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
}
