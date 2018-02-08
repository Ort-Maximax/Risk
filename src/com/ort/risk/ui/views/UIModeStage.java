package com.ort.risk.ui.views;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.ort.risk.model.Mode;
import com.ort.risk.model.Player;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Screen;


/**
 * 
 * @author tibo
 *
 * Mode and players selector frame
 */
public class UIModeStage extends CustomStage {

	private GridPane playersFieldsPane;
	private ChoiceBox<String> modeChoiceBox;
	private Label modeChoiceBoxLabel;
		
	public UIModeStage() {
		super("Risk - Mode & Players");

		// Building main panel
		mainPane = new GridPane();
        mainPane.setPadding(new Insets(5));
        mainPane.setHgap(5);
        mainPane.setVgap(5);
        ColumnConstraints column1 = new ColumnConstraints(100);
        ColumnConstraints column2 = new ColumnConstraints(150);
        column2.setHgrow(Priority.ALWAYS);
        mainPane.getColumnConstraints().addAll(column1, column2);
        
        // Building mode selecter
        modeChoiceBoxLabel = new Label("Choose a mode");
		HashMap<String, Mode> mapModes = new HashMap<String, Mode> ();
		for (Mode mode : map.getModes()) {
			String computedMode = String.format("%s - %s", mode.getNbPlayer(), mode.getNbInitTroops());
			mapModes.put(computedMode, mode);
		}
		Set<String> computedModes = mapModes.keySet();
		modeChoiceBox = new ChoiceBox<String>();
		modeChoiceBox.setItems(FXCollections.observableArrayList(computedModes));
		
		modeChoiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number> () {
			
			@Override
			public void changed(ObservableValue<? extends Number> ov, Number value, Number newValue) {
				String computedMode = (String) computedModes.toArray()[newValue.intValue()];
				Mode selectedMode = mapModes.get(computedMode);
				if (!value.equals(-1)) {
					String computedOldMode = (String) computedModes.toArray()[value.intValue()];
					Mode previousMode = mapModes.get(computedOldMode);
					previousMode.setIsSelected(false);					
				}
				selectedMode.setIsSelected(true);
				// We build dynamically the inputs for all players based on the number in the mode
				// We first clean the panel containing the inputs and the previous players
				cleanPlayersGrid();
				// Then we build the inputs
				buildPlayersInput(playersFieldsPane, selectedMode.getNbPlayer());
			}
			
		});
		
		// Building the panel for the players input
        playersFieldsPane = new GridPane();
        playersFieldsPane.setPadding(new Insets(5));
        playersFieldsPane.setHgap(5);
        playersFieldsPane.setVgap(5);
        ColumnConstraints columnLabel = new ColumnConstraints(100);
        ColumnConstraints columnTextField = new ColumnConstraints(150);
        playersFieldsPane.getColumnConstraints().addAll(columnLabel, columnTextField);
        
        // Submit button
        Button submitBtn = new Button("Play!");
        submitBtn.setOnAction( new EventHandler<ActionEvent> () {

        	List<Player> players = new ArrayList<Player>();
        	
			@Override
			public void handle(ActionEvent arg0) {
				List<Node> textFields = playersFieldsPane.getChildren().stream()
						.filter(n -> (n instanceof TextField))
						.collect(Collectors.toList());
				Mode selectedMode = map.getModes().stream().filter(m -> m.getIsSelected()).findFirst().get();
				if (selectedMode != null) {
					for ( Node playerTextField : textFields ) {
						TextField tf = (TextField) playerTextField;
						String name = (tf.getText().length() > 0) ? tf.getText() : String.format("Player %d", textFields.indexOf(playerTextField) + 1);
						players.add(new Player(name, true, textFields.indexOf(playerTextField) + 1, selectedMode.getNbInitTroops()));
					}
					map.setPlayerList(players);
					
					UIGameStage nextStage = new UIGameStage();
					Screen screen = Screen.getPrimary();
					Rectangle2D bounds = screen.getVisualBounds();
					nextStage.setWidth(bounds.getWidth());
					nextStage.setHeight(bounds.getHeight());
					nextStage.getDisplay(Double.valueOf(nextStage.getWidth()).intValue(), Double.valueOf(nextStage.getHeight()).intValue());					
				}
			}
        	
        });
		
		// Adding controllers to main panel
		// ChoiceBox Label
		GridPane.setHalignment(modeChoiceBoxLabel, HPos.RIGHT);
		mainPane.add(modeChoiceBoxLabel, 0, 0);
		
		// ChoiceBox
		GridPane.setHalignment(modeChoiceBox, HPos.RIGHT);
		mainPane.add(modeChoiceBox, 1, 0);
		
		// Players field panel
		GridPane.setHalignment(playersFieldsPane, HPos.LEFT);
		mainPane.add(playersFieldsPane, 0, 1, 2, 1);
		
		// Submit button
		GridPane.setHalignment(submitBtn, HPos.CENTER);
		mainPane.add(submitBtn, 1, 2, 2, 1);
	}
	
	private void buildPlayersInput(GridPane pane, int playersCount) {
		// root.add(mapChooserInput, 1, 1, 2, 1);
		// Col span example
		for (int i = 1; i <= playersCount; i++) {
			Label lbl = new Label("Player " + i);
			GridPane.setHalignment(lbl, HPos.LEFT);
			pane.add(lbl, 0, i);
			TextField tf = new TextField();
			GridPane.setHalignment(modeChoiceBox, HPos.RIGHT);
			pane.add(tf, 1, i);
		}
	}
	
	private void cleanPlayersGrid() {
		List<Node> filtered = playersFieldsPane.getChildren().stream()
			.filter(n -> (n instanceof TextField || n instanceof Label))
			.collect(Collectors.toList());
		playersFieldsPane.getChildren().removeAll(filtered);
	}
	
}
