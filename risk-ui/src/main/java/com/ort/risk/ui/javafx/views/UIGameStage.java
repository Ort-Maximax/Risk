package com.ort.risk.ui.javafx.views;

/**
 * @author tibo
 */

import com.ort.risk.business.DeploymentAction;
import com.ort.risk.model.Player;
import com.ort.risk.model.Region;
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
import org.apache.commons.text.StringEscapeUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UIGameStage extends CustomStage {

	private ListView<String> regionsInitialView, playerRegionView;
	private Label turnLbl, playersInfoLbl, playerLbl;
	private Button placeTroopsBtn, deployTroopsBtn, attackBtn, reinforceBtn, endOfTurnBtn;
	private Player currentPlayer;
	private boolean attackDone, reinforceDone, deployDone, phaseInitAttrib, phaseInitDeploy;
	private int countTurn;
	
	public UIGameStage() {
		super("Risk - Game");
        
		currentPlayer = game.getPlayerList().stream().filter(p -> p.getOrder() == 1).findFirst().get();
		phaseInitAttrib = false;
		phaseInitDeploy = false;
		
        // Game name
        this.setTitle(getTitle() + " - " + game.getName());
        
        // Turn label
        countTurn = 0;
        turnLbl = new Label(String.format("Turn no%d", countTurn++));
        turnLbl.setFont(new Font(turnLbl.getFont().getFamily(), 30));
        
        // Label current player name
        playerLbl = new Label("Player " + currentPlayer.getName());
        playerLbl.setFont(new Font(playerLbl.getFont().getFamily(), 30));
        
        // Label current player order
        currentPlayer = game.getPlayerList().stream().filter(p -> p.getOrder() == 1).findFirst().get();
        
        // Game image
        String imgUri = StringEscapeUtils.unescapeJava(game.getImg()).trim();
        Image mapImg = new Image(imgUri);
        ImageView mapImgView = new ImageView();
        mapImgView.setImage(mapImg);
        
        // Initial Regions
        List<Region> everyRegions = game.getRegions();
        regionsInitialView = new ListView<String>();
        regionsInitialView.setItems(FXCollections.observableArrayList(
        		everyRegions.stream()
        		.map(r -> r.getName())
        		.collect(Collectors.toList())));
        regionsInitialView.autosize();
        
        // Player Regions
        playerRegionView = new ListView<String>();
        playerRegionView.autosize();
        
        // Players regions label
        // TODO : Changer cette partie pour avoir quelque chose de plus personnalise pour les joueurs
        playersInfoLbl = new Label();
        playersInfoLbl.setMaxWidth(200);
        playersInfoLbl.setWrapText(true);
        updatePlayersInfoLbl();
        
        // Actions button
        // First step of the game
        // attribute regions until there isn't left
        placeTroopsBtn = new Button("Get region");
        placeTroopsBtn.setOnAction(new EventHandler<ActionEvent> () {

			@Override
			public void handle(ActionEvent arg0) {
				String selectedItem = regionsInitialView.selectionModelProperty().get().getSelectedItem();
				if (selectedItem != null) {
					Region selectedRegion = game.getRegions().stream()
							.filter(r -> r.getName().equalsIgnoreCase(selectedItem))
							.findFirst()
							.get();
					DeploymentAction.attribRegion(currentPlayer, selectedRegion);
					updateInitRegions();
					updatePlayersInfoLbl();
				}
			}
        	
        });
        // Second step 
        deployTroopsBtn = new Button("Deploy troops");
        deployTroopsBtn.setDisable(true);
        attackBtn = new Button("Attack");
        attackBtn.setDisable(true);
        reinforceBtn = new Button("Reinforce region");
        reinforceBtn.setDisable(true);
        endOfTurnBtn = new Button("End of turn");
        endOfTurnBtn.setOnAction(new EventHandler<ActionEvent> () {

			@Override
			public void handle(ActionEvent arg0) {
				if (!phaseInitAttrib) {
					nextTurnPhaseInit();
				}
				if (phaseInitAttrib && !phaseInitDeploy) {
					
				}
				currentPlayer = nextTurn();
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
		// Current turn
		GridPane.setHalignment(turnLbl, HPos.CENTER);
		mainPane.add(turnLbl, 0, 0);
		
		// Current player name
		GridPane.setHalignment(playerLbl, HPos.CENTER);
		mainPane.add(playerLbl, 1, 0, 2, 1);
		
		// Players Regions
		GridPane.setHalignment(playersInfoLbl, HPos.LEFT);
		mainPane.add(playersInfoLbl, 2, 1);
        
        // Game View
        GridPane.setHalignment(mapImgView, HPos.LEFT);
		mainPane.add(mapImgView, 0, 1);
		
		// Starting regions View
		GridPane.setHalignment(regionsInitialView, HPos.LEFT);
		mainPane.add(regionsInitialView, 1, 1);
		
		// Buttons
		GridPane.setHalignment(placeTroopsBtn, HPos.LEFT);
		mainPane.add(placeTroopsBtn, 1, 2);
		GridPane.setHalignment(attackBtn, HPos.RIGHT);
		mainPane.add(attackBtn, 1, 2);
		GridPane.setHalignment(reinforceBtn, HPos.LEFT);
		mainPane.add(reinforceBtn, 2, 2);
		GridPane.setHalignment(deployTroopsBtn, HPos.RIGHT);
		mainPane.add(deployTroopsBtn, 2, 2);
		GridPane.setHalignment(endOfTurnBtn, HPos.LEFT);
		mainPane.add(endOfTurnBtn, 3, 3);
		
	}
	
	private void updatePlayersInfoLbl() {
		StringBuilder renderText = new StringBuilder();
		for (Player player : game.getPlayerList()) {
			String playerType = (player.getIsHuman()) ? "Player" : "AI";
			renderText.append(String.format("%s no%d : %s", playerType, player.getOrder(), player.getName()));
			renderText.append(String.format("\n%d troops", player.getNbTroops()));
			if (player.getControlledRegions().size() > 0)
				renderText.append(String.format("\nRegions : { %s }", player.getControlledRegions().stream()
						.map(Region::getName).collect(Collectors.joining(", "))));
			renderText.append("\n\n");
		}
		playersInfoLbl.setText(renderText.toString());
	}
	
	private void updateInitRegions() {
		regionsInitialView.setItems(FXCollections.observableArrayList(
        		game.getRegions()
        		.stream()
        		.filter(r -> !r.getIsOccupied())
        		.map(r -> r.getName())
        		.collect(Collectors.toList())));
	}
	
	private void updatePlayerRegions() {
		playerRegionView.setItems(FXCollections.observableArrayList(
				currentPlayer.getControlledRegions()
				.stream()
				.map(r -> String.format("%s - Troops : %d", r.getName(), r.getDeployedTroops()))
				.collect(Collectors.toList())
				));
	}
	
	private Player nextTurn() {
		turnLbl.setText(String.format("Turn no%d", countTurn++));
		Player nextPlayer = null;
		Optional<Player> nextPlayerSearch = game.getPlayerList().stream().filter(p -> p.getOrder() == currentPlayer.getOrder() + 1).findFirst();
		if (nextPlayerSearch.isPresent())
			nextPlayer = nextPlayerSearch.get();
		else
			nextPlayer = game.getPlayerList().stream().filter(p -> p.getOrder() == 1).findFirst().get();
		playerLbl.setText("Player " + nextPlayer.getName());
		return nextPlayer;
	}
	
	private void nextTurnPhaseInit() {
		if (regionsInitialView.getItems().isEmpty()) {
			deployTroopsBtn.setDisable(false);
			placeTroopsBtn.setDisable(true);
			phaseInitAttrib = true;
		}
	}
	
	private void nextTurnPhaseDeploy() {
		
	}

	private void attack() {
		
	}
	
	private void reinforce() {
		
	}
	
	private void deploy() {
		
	}
}
