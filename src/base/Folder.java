package base;
import java.util.ArrayList;
public class Folder {
	private ArrayList<Note> notes;
	private String name;
	
	public Folder(String name) {
		this.name = name;
		notes = new ArrayList<Note>();
	}
	public void addNote(Note Notename) {
		notes.add(Notename);
	}
	public String getName() {
		return name;
	}
	public ArrayList<Note> getNotes(){
		return notes;
	}
	public String toString() {
		int nText=0;
		int nImage = 0;
		for(Note i:notes) {
			if(i instanceof TextNote) {
				nText+= 1;
			}
			else {
				nImage+= 1;
			}
		}
		return name+":"+nText+":"+nImage;
	}
	public Boolean equal(Folder folder) {
		return (this.name == folder.name);
	}
}
