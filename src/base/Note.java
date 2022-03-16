package base;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Note implements Comparable<Note>,Serializable{
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
	public Date getDate() {
		return this.date;
	}
	public int compareTo(Note o) {
		if(this.date.compareTo(o.getDate())<0) {
			return 1;
		}
		else if(this.date.compareTo(o.getDate())>0) {
			return -1;
		}
		else {
			return 0;
		}
	}
	public String toString() {
		return date.toString()+"\t"+title;
	}
}
