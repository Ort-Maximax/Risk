package com.ort.risk.ui.javafx;

import com.ort.risk.ui.Launcher;
import com.ort.risk.storage.MapFileHandler;
import com.ort.risk.storage.Parser;
import com.ort.risk.model.Game;
import com.ort.risk.ui.javafx.views.UIModeStage;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 
 * @author tibo
 *
 * Main Launcher
 * Game selector
 */
public class UILauncher extends Application {

	private Stage stage;
	protected File choosedMapFile;
	private MapFileHandler mapFileHandler;
	private Label fileNameLabel;
    private Button mapChooserInput;
    private Button loadMapButton;
    private Label mapFileFileChooserLabel;
	private ChoiceBox<String> savedMapChoiceBox;
	
	private Game game = Game.getInstance();
	
	@Override
	public void start(Stage primaryStage) {
		
		this.stage = primaryStage;
		this.stage.setTitle("Risk");
		
		mapFileHandler = new MapFileHandler();
		
		initRootLayout();
				
	}

	public static void run(String[] args) {
		launch(args);
	}
	
	private void initRootLayout() {
        
		GridPane root = new GridPane();
        root.setPadding(new Insets(5));
        root.setHgap(5);
        root.setVgap(5);
        ColumnConstraints column1 = new ColumnConstraints(150);
        ColumnConstraints column2 = new ColumnConstraints(50, 150, 300);
        column2.setHgrow(Priority.ALWAYS);
        root.getColumnConstraints().addAll(column1, column2);
		
        fileNameLabel = new Label();
        mapChooserInput = new Button("Choose a game");
        loadMapButton = new Button("Load");

		List<File> mapFiles = mapFileHandler.getSavedMapFiles();
		if (mapFiles.size() > 0) {
			List<String> mapFilesName = mapFiles
					.stream()
					.map(File::getName)
					.sorted()
					.collect(Collectors.toList());
			HashMap<String, File> mapMapFiles = new HashMap<String, File> ();
			for (File file : mapFiles)
				mapMapFiles.put(file.getName(), file);
			
			mapFileFileChooserLabel = new Label("Choose a saved game");
			savedMapChoiceBox = new ChoiceBox<String>(FXCollections.observableArrayList(
					mapMapFiles.keySet().stream().sorted().collect(Collectors.toList())));

			savedMapChoiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number> () {
				
				@Override
				public void changed(ObservableValue<? extends Number> ov, Number value, Number newValue) {
					choosedMapFile = mapMapFiles.get(mapFilesName.get(newValue.intValue()));
					fileNameLabel.setText(choosedMapFile.getName());
				}
				
			});
			
			// ChoiceBox Label
			GridPane.setHalignment(mapFileFileChooserLabel, HPos.RIGHT);
			root.add(mapFileFileChooserLabel, 0, 0);
			
			// ChoiceBox
			GridPane.setHalignment(savedMapChoiceBox, HPos.LEFT);
			root.add(savedMapChoiceBox, 1, 0);
		}
		
        mapChooserInput.setOnAction(new MapChooserHandler());
        
        loadMapButton.setOnAction(new LoadButtonHander());

        // FileChooser
        GridPane.setHalignment(mapChooserInput, HPos.LEFT);
        root.add(mapChooserInput, 1, 1, 2, 1);

        // file name
        GridPane.setHalignment(fileNameLabel, HPos.RIGHT);
        root.add(fileNameLabel, 0, 2);

        // Load button
        GridPane.setHalignment(loadMapButton, HPos.RIGHT);
        root.add(loadMapButton, 1, 2);

        // Show the scene containing the root layout.
        stage.setScene(new Scene(root, 400, 200));
        stage.show();
    }
	
	class LoadButtonHander implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent arg0) {
			mapFileHandler.saveMap(choosedMapFile);
			mapFileHandler.moveMapFileToCurrent(choosedMapFile);
			game.setExMode(Launcher.ExecMode.GUI.value());
			Parser.prepMap();
			new UIModeStage().getDisplay(300, 300);
		}
    	
    }
	
	class MapChooserHandler implements EventHandler<ActionEvent> {
		
		@Override
		public void handle(ActionEvent arg0) {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Open the game XML file");
			fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML", "*.xml"));
			File fileToUpload = fileChooser.showOpenDialog(stage);
			if (fileToUpload != null) {
				fileNameLabel.setText(fileToUpload.getName());
				choosedMapFile = fileToUpload;
			}
		}
		
	}
}
