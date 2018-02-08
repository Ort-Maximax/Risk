package com.ort.risk.ui.views;

/**
 * @author tibo
 */
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.text.StringEscapeUtils;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;

import com.ort.risk.game.actions.DeploymentAction;
import com.ort.risk.model.Player;
import com.ort.risk.model.Region;

public class UIGameStage extends CustomStage {

	private ListView<String> regionsInitialView;
	private Label playersInfoLbl, playerLbl;
	private Button placeTroopsBtn, deployTroops, attackBtn, reinforceBtn, endOfTurnBtn;
	private Player currentPlayer;
	
	public UIGameStage() {
		super("Risk - Game");
        
        // Map name
        this.setTitle(getTitle() + " - " + map.getName());
        
        // Label current player name
        playerLbl = new Label(map.getPlayerList().stream().filter(p -> p.getOrder() == 1).findFirst().get().getName());
        playerLbl.setFont(new Font(playerLbl.getFont().getFamily(), 30));
        
        // Label current player order
        currentPlayer = map.getPlayerList().stream().filter(p -> p.getOrder() == 1).findFirst().get();
        
        // Map image
        String imgUri = StringEscapeUtils.unescapeJava(map.getImg()).trim();
        Image mapImg = new Image(imgUri);
        ImageView mapImgView = new ImageView();
        mapImgView.setImage(mapImg);
        
        // Initial Regions
        List<Region> everyRegions = map.getRegions();
        regionsInitialView = new ListView<String>();
        regionsInitialView.setItems(FXCollections.observableArrayList(
        		everyRegions.stream()
        		.map(r -> r.getName())
        		.collect(Collectors.toList())));
        regionsInitialView.autosize();
        
        // Players regions label
        // TODO : Changer cette partie pour avoir quelque chose de plus personnalisé pour les joueurs
        playersInfoLbl = new Label();
        playersInfoLbl.setMaxWidth(200);
        playersInfoLbl.setWrapText(true);
        updatePlayersInfoLbl();
        
        // Actions button
        placeTroopsBtn = new Button("Get region");
        placeTroopsBtn.setOnAction(new EventHandler<ActionEvent> () {

			@Override
			public void handle(ActionEvent arg0) {
				String selectedItem = regionsInitialView.selectionModelProperty().get().getSelectedItem();
				if (selectedItem != null) {
					Region selectedRegion = map.getRegions().stream()
							.filter(r -> r.getName().equalsIgnoreCase(selectedItem))
							.findFirst()
							.get();
					DeploymentAction.attribRegion(currentPlayer, selectedRegion);
					updateRegions();
					updatePlayersInfoLbl();
				}
			}
        	
        });
        deployTroops = new Button("Deploy troops");
        attackBtn = new Button("Attack");
        reinforceBtn = new Button("Reinforce region");
        endOfTurnBtn = new Button("End of turn");
        endOfTurnBtn.setOnAction(new EventHandler<ActionEvent> () {

			@Override
			public void handle(ActionEvent arg0) {
				nextTurn();
			}
        	
        });

		// Building main panel
		mainPane = new GridPane();
		mainPane.setPadding(new Insets(5));
		mainPane.setHgap(5);
		mainPane.setVgap(5);
		ColumnConstraints column1 = new ColumnConstraints(mapImg.getWidth());
		ColumnConstraints column2 = new ColumnConstraints(150);
		ColumnConstraints column3 = new ColumnConstraints(200);
		ColumnConstraints column4 = new ColumnConstraints(150);
		column1.setHgrow(Priority.ALWAYS);
		mainPane.getColumnConstraints().addAll(column1, column2, column3, column4);
        
        // Filling main panel
        // Players Regions
		GridPane.setHalignment(playersInfoLbl, HPos.LEFT);
		mainPane.add(playersInfoLbl, 2, 1);
        
        // Current player name
        GridPane.setHalignment(playerLbl, HPos.CENTER);
		mainPane.add(playerLbl, 0, 0, 3, 1);
        
        // Map View
        GridPane.setHalignment(mapImgView, HPos.LEFT);
		mainPane.add(mapImgView, 0, 1);
		
		// Starting regions View
		GridPane.setHalignment(regionsInitialView, HPos.LEFT);
		mainPane.add(regionsInitialView, 1, 1);
		
		// Buttons
		GridPane.setHalignment(placeTroopsBtn, HPos.LEFT);
		mainPane.add(placeTroopsBtn, 0, 2);
		GridPane.setHalignment(attackBtn, HPos.LEFT);
		mainPane.add(attackBtn, 1, 2);
		GridPane.setHalignment(reinforceBtn, HPos.LEFT);
		mainPane.add(reinforceBtn, 2, 2);
		GridPane.setHalignment(endOfTurnBtn, HPos.LEFT);
		mainPane.add(endOfTurnBtn, 3, 2);
		GridPane.setHalignment(deployTroops, HPos.LEFT);
		mainPane.add(deployTroops, 0, 3);
		
	}
	
	private void updatePlayersInfoLbl() {
		StringBuilder renderText = new StringBuilder();
		for (Player player : map.getPlayerList()) {
			String playerType = (player.getIsHuman()) ? "Player" : "AI";
			renderText.append(String.format("%s n°%d : %s", playerType, player.getOrder(), player.getName()));
			renderText.append(String.format("\n%d troops", player.getNbTroops()));
			if (player.getControlledRegions().size() > 0)
				renderText.append(String.format("Regions : { %s }", player.getControlledRegions().stream()
						.map(Region::getName).collect(Collectors.joining(", "))));
			renderText.append("\n\n");
		}
		playersInfoLbl.setText(renderText.toString());
	}
	
	private void updateRegions() {
		regionsInitialView.setItems(FXCollections.observableArrayList(
        		map.getRegions().stream()
        		.filter(r -> !r.getIsOccupied())
        		.map(r -> r.getName())
        		.collect(Collectors.toList())));
	}
	
	private Player nextTurn() {
		Player nextPlayer = map.getPlayerList().stream().filter(p -> p.getOrder() == currentPlayer.getOrder() + 1).findFirst().get();
		if (nextPlayer == null)
			nextPlayer = map.getPlayerList().stream().filter(p -> p.getOrder() == 1).findFirst().get();
		playerLbl.setText(nextPlayer.getName());
		return nextPlayer;
	}

}
