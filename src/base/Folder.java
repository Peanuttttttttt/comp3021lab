package base;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
public class Folder implements Comparable<Folder>{
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
	public int compareTo(Folder o) {
		if(this.name.compareTo(o.getName())<0) {
			return -1;
		}
		else if(this.name.compareTo(o.getName())>0){
			return 1;
		}
		else{
			return 0;
		}
	}
	Comparator<Note> SortByDate = new Comparator<Note>() {
		public int compare(Note A1,Note A2) {
			return A1.compareTo(A2);
		}
	};
	public void sortNotes() {
		Collections.sort(notes,SortByDate);
	}
	public List<Note> searchNotes(String keywords){
		String[] SplitKeywords = keywords.split(" ");
		ArrayList<Note> KeywordsNote = new ArrayList<Note>();
		for(Note CurrentNote:notes) {
			for(int CurrentKeywords=0;CurrentKeywords<SplitKeywords.length;CurrentKeywords++) {
				if(SplitKeywords[CurrentKeywords].equalsIgnoreCase("or")) {
					continue;
				}
				else if(CurrentNote.getTitle().toUpperCase().contains( SplitKeywords[CurrentKeywords].toUpperCase())) {
					if(CurrentKeywords == SplitKeywords.length-1) {
						KeywordsNote.add(CurrentNote);
					}
					continue;
				}
				else if(CurrentKeywords+2<SplitKeywords.length) {
					if(SplitKeywords[CurrentKeywords+1].equalsIgnoreCase("or") &&
						CurrentNote.getTitle().toUpperCase().contains( SplitKeywords[CurrentKeywords+2].toUpperCase())) {
						continue;
					}
				}
				else if(SplitKeywords[CurrentKeywords-1].equalsIgnoreCase("or") &&
						CurrentNote.getTitle().toUpperCase().contains( SplitKeywords[CurrentKeywords-2].toUpperCase())) {
					if(CurrentKeywords == SplitKeywords.length-1) {
						KeywordsNote.add(CurrentNote);
					}
					continue;
				}
				else if(CurrentNote instanceof TextNote) {
					TextNote CurrentTextNote = (TextNote)CurrentNote;
		
					if(CurrentTextNote.getText().toUpperCase().contains( SplitKeywords[CurrentKeywords].toUpperCase())) {
						if(CurrentKeywords == SplitKeywords.length-1) {
							KeywordsNote.add(CurrentTextNote);
						}
						continue;
					}
					else if(CurrentKeywords+2<SplitKeywords.length) {
						if(SplitKeywords[CurrentKeywords+1].equalsIgnoreCase("or") &&
							CurrentTextNote.getText().toUpperCase().contains( SplitKeywords[CurrentKeywords+2].toUpperCase())) {
						continue;
						}
					}
					else if(SplitKeywords[CurrentKeywords-1].equalsIgnoreCase("or") &&
							CurrentTextNote.getText().toUpperCase().contains( SplitKeywords[CurrentKeywords-2].toUpperCase())) {
						if(CurrentKeywords == SplitKeywords.length-1) {
							KeywordsNote.add(CurrentTextNote);
						}
						continue;
					}
				}
				else {
					break;
				}
				
			}
			
		}
		return KeywordsNote;
	}
}
