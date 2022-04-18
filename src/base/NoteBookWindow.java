package base;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import base.Folder;
import base.Note;
import base.NoteBook;
import base.TextNote;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.stage.*;
import java.io.File;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
/**
 * 
 * NoteBook GUI with JAVAFX
 * 
 * COMP 3021
 * 
 * 
 * @author valerio
 *
 */
public class NoteBookWindow extends Application {

	/**
	 * TextArea containing the note
	 */
	final TextArea textAreaNote = new TextArea("");
	/**
	 * list view showing the titles of the current folder
	 */
	final ListView<String> titleslistView = new ListView<String>();
	/**
	 * 
	 * Combobox for selecting the folder
	 * 
	 */
	final ComboBox<String> foldersComboBox = new ComboBox<String>();
	/**
	 * This is our Notebook object
	 */
	NoteBook noteBook = null;
	/**
	 * current folder selected by the user
	 */
	String currentFolder = "";
	/**
	 * current search string
	 */
	String currentSearch = "";
	/**
	 * current note selected by the user
	 */
	String currentNote = "";
	
	public String thisFolder = "";
	
	Stage stage;

	public static void main(String[] args) {
		launch(NoteBookWindow.class, args);
	}

	@Override
	public void start(Stage stage) {
		loadNoteBook();
		this.stage = stage;
		// Use a border pane as the root for scene
		BorderPane border = new BorderPane();
		// add top, left and center
		border.setTop(addHBox());
		border.setLeft(addVBox());
		border.setCenter(addGridPane());

		Scene scene = new Scene(border);
		stage.setScene(scene);
		stage.setTitle("NoteBook COMP 3021");
		stage.show();
	}

	/**
	 * This create the top section
	 * 
	 * @return
	 */
	private HBox addHBox() {

		HBox hbox = new HBox();
		hbox.setPadding(new Insets(15, 12, 15, 12));
		hbox.setSpacing(10); // Gap between nodes

		Button buttonLoad = new Button("Load from File");
		buttonLoad.setPrefSize(100, 20);
		buttonLoad.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Please Choose An File Which Contains a NoteBook Object!");
				
				FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Serialized Object File (*.ser)", "*.ser");
				fileChooser.getExtensionFilters().add(extFilter);
				
				File file = fileChooser.showOpenDialog(stage);
				
				if(file== null){
					return;
				}
				loadNoteBook(file);
			}
		});
		//buttonLoad.setDisable(true);
		Button buttonSave = new Button("Save to File");
		buttonSave.setPrefSize(100, 20);
		buttonSave.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				FileChooser fileChooser = new FileChooser();
				FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Serialized Object File (*.ser)", "*.ser");
				fileChooser.getExtensionFilters().add(extFilter);
				File file = fileChooser.showOpenDialog(stage);
				noteBook.save(file.getName());
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Successfully saved");
				alert.setContentText("You file has been save to file"+file.getName());
				alert.showAndWait().ifPresent(rs->{
					if(rs == ButtonType.OK) {
						System.out.println("Pressed OK.");
					}
				});
			}
		});
		//buttonSave.setDisable(true);
		
		Label text = new Label("search:");
		TextField input = new TextField();
		Button search = new Button("Search");
		search.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				currentSearch = input.getText();
				ArrayList<String> list = new ArrayList<String>();
				List<Note> note = new ArrayList<Note>();
				for(Folder i : noteBook.getFolders()) {
					if(i.getName()==thisFolder) {
						note = i.searchNotes(currentSearch);
					}
				}
				for(Note j : note) {
					list.add(j.getTitle());
				}
				ObservableList<String> combox2 = FXCollections.observableArrayList(list);
				titleslistView.setItems(combox2);
				textAreaNote.setText("");
			}
		});
		Button clearSearch = new Button("Clear Search");
		clearSearch.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				ArrayList<String> list = new ArrayList<String>();
				List<Note> note = new ArrayList<Note>();
				for(Folder i : noteBook.getFolders()) {
					if(i.getName()==thisFolder) {
						note = i.getNotes();
						break;
					}
				}
				for(Note j : note) {
					list.add(j.getTitle());
				}
				ObservableList<String> combox2 = FXCollections.observableArrayList(list);
				titleslistView.setItems(combox2);
				textAreaNote.setText("");
				input.setText("");
			}
		});

		hbox.getChildren().addAll(buttonLoad, buttonSave,text,input,search,clearSearch);

		return hbox;
	}

	/**
	 * this create the section on the left
	 * 
	 * @return
	 */
	private VBox addVBox() {

		VBox vbox = new VBox();
		HBox hbox = new HBox();
		hbox.setSpacing(10);
		vbox.setPadding(new Insets(10)); // Set all sides to 10
		vbox.setSpacing(8); // Gap between nodes

		// TODO: This line is a fake folder list. We should display the folders in noteBook variable! Replace this with your implementation
		//foldersComboBox.getItems().addAll(noteBook.getFolders().get(0).getName(), "FOLDER NAME 2", "FOLDER NAME 3");
		for(Folder i : noteBook.getFolders()) {
			foldersComboBox.getItems().add(i.getName());
		}
		foldersComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue ov, Object t, Object t1) {
				if(t1 == null)
					return;
				currentFolder = t1.toString();
				thisFolder = currentFolder;
				// this contains the name of the folder selected
				// TODO update listview
				updateListView();

			}

		});

		foldersComboBox.setValue("-----");

		titleslistView.setPrefHeight(100);

		titleslistView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue ov, Object t, Object t1) {
				if (t1 == null)
					return;
				String title = t1.toString();
				currentNote = title;
				// This is the selected title
				// TODO load the content of the selected note in
				// textAreNote
				String content = "";
				for(Folder i:noteBook.getFolders()) {
					for(Note j:i.getNotes()) {
						if(j.getTitle() == title && j instanceof TextNote) {
							TextNote k  = (TextNote)j;
							content = k.getText();
							break;
						}
					}
				}
				textAreaNote.setText(content);

			}
		});
		Button buttonAddFolder = new Button("Add a Folder");
		buttonAddFolder.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				TextInputDialog dialog = new TextInputDialog("Add a Folder");
				dialog.setTitle("Input");
				dialog.setHeaderText("Add a new folder for your notebook:");
				dialog.setContentText("Please enter the name you want to create:");
				
				Optional<String> result = dialog.showAndWait();
				if(result.isPresent() && result.get().equals("")) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Warning");
					alert.setContentText("Please input an valid folder name");
					alert.showAndWait().ifPresent(rs->{
						if(rs == ButtonType.OK) {
						}
					});
					return;
				}
				if(result.isPresent()) {
					String folderName = result.get();
					if(noteBook.isFolderExist(folderName)) {
						Alert alert = new Alert(AlertType.WARNING);
						alert.setTitle("Warning");
						alert.setContentText("You already have a folder named with "+folderName);
						alert.showAndWait().ifPresent(rs->{
							if(rs == ButtonType.OK) {
							}
						});
					}
					else {
						Folder folder = new Folder(folderName);
						noteBook.getFolders().add(folder);
						foldersComboBox.getItems().add(folder.getName());
					}
				}

			}
		});
		
		
		Button buttonAddNote = new Button("Add a Note");
		buttonAddNote.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				if(!isFolderSelected(currentFolder)) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Warning");
					alert.setContentText("Please choose a folder first!");
					alert.showAndWait().ifPresent(rs->{
						if(rs == ButtonType.OK) {
						}
					});
					return;
				}
				TextInputDialog dialog = new TextInputDialog("Add a Note");
				dialog.setTitle("Input");
				dialog.setHeaderText("Add a new note to current folder:");
				dialog.setContentText("Please enter the name of your note:");
				
				Optional<String> result = dialog.showAndWait();
				if(result.isPresent()) {
					String titleName = result.get();
					if(noteBook.createTextNote(currentFolder, titleName)) {
						updateListView();
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Successful");
						alert.setContentText("Insert note "+titleName+" to folder "+currentFolder+" successfully!");
						alert.showAndWait().ifPresent(rs->{
							if(rs == ButtonType.OK) {
							}
						});
					}
					else {
						Alert alert = new Alert(AlertType.WARNING);
						alert.setTitle("Warning");
						alert.setContentText("Such title already exist");
						alert.showAndWait().ifPresent(rs->{
							if(rs == ButtonType.OK) {
							}
						});
					}
				}
					
				
			}
		});
		
		hbox.getChildren().add(foldersComboBox);
		hbox.getChildren().add(buttonAddFolder);
		vbox.getChildren().add(new Label("Choose folder: "));
		vbox.getChildren().add(hbox);
		//vbox.getChildren().add(foldersComboBox);
		vbox.getChildren().add(new Label("Choose note title"));
		vbox.getChildren().add(titleslistView);
		vbox.getChildren().add(buttonAddNote);

		return vbox;
	}

	private void updateListView() {
		ArrayList<String> list = new ArrayList<String>();

		// TODO populate the list object with all the TextNote titles of the
		// currentFolder
		for(Folder i : noteBook.getFolders()) {
			if(currentFolder == i.getName()) {
				for(Note j:i.getNotes()) {
					if(j instanceof TextNote) {
						list.add(j.getTitle());
					}
				}
				break;
			}
		}

		ObservableList<String> combox2 = FXCollections.observableArrayList(list);
		titleslistView.setItems(combox2);
		textAreaNote.setText("");
	}

	/*
	 * Creates a grid for the center region with four columns and three rows
	 */
	private GridPane addGridPane() {

		GridPane grid = new GridPane();
		HBox hbox = new HBox();
		hbox.setSpacing(10);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(10, 10, 10, 10));
		textAreaNote.setEditable(true);
		textAreaNote.setMaxSize(450, 400);
		textAreaNote.setWrapText(true);
		textAreaNote.setPrefWidth(450);
		textAreaNote.setPrefHeight(400);
		ImageView saveView = new ImageView(new Image(new File("save.png").toURI().toString()));
		//System.out.println(new File("save.png").toURI().toString());
		saveView.setFitHeight(18);
		saveView.setFitWidth(18);
		saveView.setPreserveRatio(true);
		
		Button saveNote = new Button("Save Note");
		saveNote.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				if(currentNote.equals(new String("")) || currentFolder.equals(new String(""))) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Warning");
					alert.setContentText("Please select a folder and a note!");
					alert.showAndWait().ifPresent(rs->{
						if(rs == ButtonType.OK) {
						}
					});
					return;
				}
				String text = textAreaNote.getText();
				for(Folder i : noteBook.getFolders()) {
					for(Note j : i.getNotes()) {
						if(j.getTitle().equals(currentNote)) {
							if(j instanceof TextNote)
								((TextNote) j).setContent(text);
						}
					}
				}
				updateListView();
			}
		});
		Button deleteNote = new Button("Delete Note");
		deleteNote.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				if(currentNote.equals(new String("")) || currentFolder.equals(new String(""))) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Warning");
					alert.setContentText("Please select a folder and a note!");
					alert.showAndWait().ifPresent(rs->{
						if(rs == ButtonType.OK) {
						}
					});
					return;
				}
				for(Folder i : noteBook.getFolders()) {
					for(Note j: i.getNotes()) {
						if(j.getTitle().equals(currentNote)){
							i.getNotes().remove(j);
							break;
						}
					}
				}
				updateListView();
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Succeed!");
				alert.setContentText("Your note has been successfully removed!");
				alert.showAndWait().ifPresent(rs->{
					if(rs == ButtonType.OK) {
					}
				});
			}
		});
		// 0 0 is the position in the grid
		hbox.getChildren().add(saveView);
		hbox.getChildren().add(saveNote);
		hbox.getChildren().add(deleteNote);
		grid.add(hbox, 0, 0);
		grid.add(textAreaNote, 0, 5);

		return grid;
	}

	private void loadNoteBook() {
		NoteBook nb = new NoteBook();
		nb.createTextNote("COMP3021", "COMP3021 syllabus", "Be able to implement object-oriented concepts in Java.");
		nb.createTextNote("COMP3021", "course information",
				"Introduction to Java Programming. Fundamentals include language syntax, object-oriented programming, inheritance, interface, polymorphism, exception handling, multithreading and lambdas.");
		nb.createTextNote("COMP3021", "Lab requirement",
				"Each lab has 2 credits, 1 for attendence and the other is based the completeness of your lab.");

		nb.createTextNote("Books", "The Throwback Special: A Novel",
				"Here is the absorbing story of twenty-two men who gather every fall to painstakingly reenact what ESPN called “the most shocking play in NFL history” and the Washington Redskins dubbed the “Throwback Special”: the November 1985 play in which the Redskins’ Joe Theismann had his leg horribly broken by Lawrence Taylor of the New York Giants live on Monday Night Football. With wit and great empathy, Chris Bachelder introduces us to Charles, a psychologist whose expertise is in high demand; George, a garrulous public librarian; Fat Michael, envied and despised by the others for being exquisitely fit; Jeff, a recently divorced man who has become a theorist of marriage; and many more. Over the course of a weekend, the men reveal their secret hopes, fears, and passions as they choose roles, spend a long night of the soul preparing for the play, and finally enact their bizarre ritual for what may be the last time. Along the way, mishaps, misunderstandings, and grievances pile up, and the comforting traditions holding the group together threaten to give way. The Throwback Special is a moving and comic tale filled with pitch-perfect observations about manhood, marriage, middle age, and the rituals we all enact as part of being alive.");
		nb.createTextNote("Books", "Another Brooklyn: A Novel",
				"The acclaimed New York Times bestselling and National Book Award–winning author of Brown Girl Dreaming delivers her first adult novel in twenty years. Running into a long-ago friend sets memory from the 1970s in motion for August, transporting her to a time and a place where friendship was everything—until it wasn’t. For August and her girls, sharing confidences as they ambled through neighborhood streets, Brooklyn was a place where they believed that they were beautiful, talented, brilliant—a part of a future that belonged to them. But beneath the hopeful veneer, there was another Brooklyn, a dangerous place where grown men reached for innocent girls in dark hallways, where ghosts haunted the night, where mothers disappeared. A world where madness was just a sunset away and fathers found hope in religion. Like Louise Meriwether’s Daddy Was a Number Runner and Dorothy Allison’s Bastard Out of Carolina, Jacqueline Woodson’s Another Brooklyn heartbreakingly illuminates the formative time when childhood gives way to adulthood—the promise and peril of growing up—and exquisitely renders a powerful, indelible, and fleeting friendship that united four young lives.");

		nb.createTextNote("Holiday", "Vietnam",
				"What I should Bring? When I should go? Ask Romina if she wants to come");
		nb.createTextNote("Holiday", "Los Angeles", "Peter said he wants to go next Agugust");
		nb.createTextNote("Holiday", "Christmas", "Possible destinations : Home, New York or Rome");
		noteBook = nb;

	}
	private void loadNoteBook(File file) {
		String fileName = file.getName();
		NoteBook nb = new NoteBook(fileName);
		noteBook = nb;
		foldersComboBox.getItems().clear();
		foldersComboBox.setValue("-----");
		
		for(Folder i : noteBook.getFolders()) {
			foldersComboBox.getItems().add(i.getName());
		}
	}
	public boolean isFolderSelected(String name) {
		for(Folder i : noteBook.getFolders()) {
			if(i.getName() == name)
				return true;
		}
		return false;
	}

}
