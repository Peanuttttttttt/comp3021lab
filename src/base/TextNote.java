package base;

import java.io.File;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.Serializable;
public class TextNote extends Note implements Serializable{
	private String content;
	
	public TextNote(String title) {
		super(title);
		
	}
	public String getText() {
		return this.content;
	}
	//overload the constructor
	public TextNote(String title, String content) {
		super(title);
		this.content = content;
	}
	public TextNote(File f) {
		super(f.getName());
		this.content = getTextFromFile(f.getAbsolutePath());
	}
	private String getTextFromFile(String absolutePath) {
		String result = "";
		try {
			FileInputStream fis = new FileInputStream(absolutePath);
			BufferedReader readin = new BufferedReader(new InputStreamReader(fis));
			result = readin.readLine();
			readin.close();
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("FileNotFoundException");
			return result;
		}
		catch(IOException e) {
			e.printStackTrace();
			System.out.println("IOException");
			return result;
		}
		return result;
	}
	public void exportTextToFile(String pathFolder) {
		if(pathFolder == "") {
			pathFolder = ".";
		}
		File file = new File(pathFolder + File.separator + this.getTitle().replaceAll(" ","_") + ".txt");
		try {
		BufferedWriter writeout = new BufferedWriter(new FileWriter(file));
		writeout.write(this.getText());
		writeout.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
