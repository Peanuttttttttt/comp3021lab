package base;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;
import java.util.Collection;
import java.util.Collections;
public class NoteBook {
	private ArrayList<Folder> folders;
	public NoteBook() {
		folders = new ArrayList<Folder>();
	}
	public boolean createTextNote(String folderName,String title) {
		TextNote note = new TextNote(title);
		return insertNote(folderName,note);
	}
	public boolean createImageNote(String folderName,String title) {
		ImageNote note = new ImageNote(title);
		return insertNote(folderName,note);
	}
	public ArrayList<Folder> getFolders(){
		return folders;
	}
	public boolean insertNote(String foldername,Note notetitle) {
		Folder checkfolder = null;
		boolean exist = false;
		for(Folder i :folders) {
			if(foldername == i.getName()) {
				checkfolder = i;
				exist = true;
			}
		}
		if(exist == false) {
			checkfolder = new Folder(foldername);
			folders.add(checkfolder);
		}
		for(Note n:checkfolder.getNotes()) {
			if(n.equals(notetitle)) {
				System.out.println("Creating note "+ notetitle.getTitle()+" under folder " + foldername + " failed");
				return false;
			}
		}
		checkfolder.addNote(notetitle);
		return true;
	}
	Comparator<Folder> SortByFolderName = new Comparator<Folder>() {
		public int compare(Folder A1,Folder A2) {
			return A1.compareTo(A2);
		}
	};
	public void sortFolders() {
		Collections.sort(folders,SortByFolderName);
		for(Folder i : folders) {
			i.sortNotes();
		}
	}
	public List<Note> searchNotes(String keywords){
		ArrayList<Note> allNotes = new ArrayList<Note>();
		for(Folder CurrentFolder:folders) {
			allNotes.addAll(CurrentFolder.searchNotes(keywords));
		}
		return allNotes;
	}
	//overload the createTextNote()
	public boolean createTextNote(String folderName, String title, String content) {
		TextNote note = new TextNote(title,content);
		return insertNote(folderName,note);
	}
}
