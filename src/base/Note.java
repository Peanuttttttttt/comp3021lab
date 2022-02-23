package base;

import java.util.ArrayList;
import java.util.Date;

public class Note {
	private Date date;
	private String title;
	
	public Note(String title) {
		this.title = title;
		date = new Date(System.currentTimeMillis());
	}
	public String getTitle() {
		return this.title;
	}
	public Boolean equals(Note note) {
		return title.equals(note.getTitle());
	}
}
