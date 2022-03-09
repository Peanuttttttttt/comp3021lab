package base;

public class TextNote extends Note {
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
}