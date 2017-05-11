package vocGenerator;



import java.io.File;

import javax.swing.filechooser.FileFilter;

public class SFFiltre extends FileFilter{

	private String suffixe;
	
	public SFFiltre(String suffixe)
	{
		this.suffixe= "."+suffixe;
	}
	@Override
	public boolean accept(File arg0) {
		// TODO Auto-generated method stub
		if(arg0.isDirectory()) // les dossiers sont affichés
			return true;
		else
		{
			String nom= arg0.getAbsolutePath().toLowerCase();
			String extension= nom.substring(nom.length()-getLength());
			if (extension.compareTo(suffixe.toLowerCase())==0)
				return true;
		}
		
		return false;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Liste aléatoire de mots (*.list)";
	}
	
	public String getSuffixe()
	{
		return suffixe;
	}
	
	public int getLength()
	{
		return suffixe.length();
	}

}
