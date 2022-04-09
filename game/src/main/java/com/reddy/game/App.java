package com.reddy.game;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Arrays;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;

/**
 * JavaFX App
 */
public class App extends Application {

	protected static int side = 4;
	protected static int size = 100;
	protected static int[][] array = new int[side][side];	
	
    @Override
    public void start(Stage stage) {
    	for(int i = 0; i < array.length; i++) {
    		Arrays.fill(array[i], 0);
    	}
    	
    	try {
    		FXMLLoader loader = new FXMLLoader(App.class.getResource("primary.fxml"));
    		Parent root = loader.load();
    		PrimaryController cntrl = loader.getController();	
    		Scene scene = new Scene(root, (size * side) + 50, (size * side) + 100);
    		scene.getStylesheets().add(App.class.getResource("primary.css").toExternalForm());	
    		
    		cntrl.randGen();
    		cntrl.randGen();
        
    		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
	            @Override
	            public void handle(KeyEvent key) {
	               if(key.getCode() == KeyCode.DOWN) {    
	            	   cntrl.slide(0, 1, 0);
	               }
	               else if(key.getCode() == KeyCode.UP) {
	            	   cntrl.slide(0, -1, (side * side) - 1);
	               }
	               else if(key.getCode() == KeyCode.RIGHT) {
	            	   cntrl.slide(1, 0, 0);
	               }
	               else if(key.getCode() == KeyCode.LEFT) {
	            	   cntrl.slide(-1, 0, (side * side) - 1);
	               }	              
	            }
	        });; 
    		
	        stage.getIcons().add(new Image(App.class.getResourceAsStream("icon.png")));
    		stage.setScene(scene);
    		stage.show();
    	} 
    	catch(IOException e) {
    		e.printStackTrace();
    	}
    }


    public static void main(String[] args) {
        launch();
    }

}