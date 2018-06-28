package com.ort.risk.ui.javafx.views;

import com.ort.risk.model.Game;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class CustomStage extends Stage {

	protected Game game = Game.getInstance();
	
	protected GridPane mainPane;
	
	public CustomStage(String title) {
		setTitle(title);
		
		this.setOnCloseRequest(new EventHandler<WindowEvent>() {
		    @Override
		    public void handle(WindowEvent event) {
		        Platform.exit();
		        System.exit(0);
		    }
		});
		
	}
	
	/**
	 * Display a CustomStage
	 * @param width
	 * @param height
	 */
	public void getDisplay(Integer width, Integer height) {
		if ( width != null && height != null )
			setScene(new Scene(mainPane, width, height));
		else
			setScene(new Scene(mainPane));			
		show();
	}
	
}
