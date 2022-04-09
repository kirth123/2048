package com.reddy.game;

import java.util.Random;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class PrimaryController {
	
	@FXML private GridPane grid;
	@FXML private Pane root;
	@FXML private Pane scoreboard;
	@FXML private Text score;
	@FXML private StackPane banner;
	private int side = App.side;
	private int size = App.size;
	private int init = 2;
	private int points = 0;
	
	private void genBlock(int x, int y, int value) {
		StackPane stack = new StackPane();
		Rectangle rect = new Rectangle();
		Text text = new Text();
		text.setText("" + value);
		
		rect.setWidth(size);
		rect.setHeight(size);
		rect.getStyleClass().add("tile");
		text.getStyleClass().add("number");
		
		switch(value) {
			case 2:
				rect.setFill(Color.web("#eee4da",1.0));
				break;
			case 4:
				rect.setFill(Color.web("#eee1c9",1.0));
				break;
			case 8:
				rect.setFill(Color.web("#f3b27a",1.0));
				break;
			case 16:
				rect.setFill(Color.web("#f69664",1.0));
				break;
			case 32:
				rect.setFill(Color.web("#f77c5f",1.0));
				break;
			case 64:
				rect.setFill(Color.web("#f75f3b",1.0));
				break;
			case 128:
				rect.setFill(Color.web("#edd073",1.0));
				break;
			case 256:
				rect.setFill(Color.web("#edcc62",1.0));
				break;
			case 512:
				rect.setFill(Color.web("#edc950",1.0));
				break;
			case 1024:
				rect.setFill(Color.web("#edc53f",1.0));
				break;
			case 2048:
				rect.setFill(Color.web("#edc22e",1.0));
				gameOver(true);
				break;
		}
		
		stack.getChildren().addAll(rect, text);		
		stack.getStyleClass().add("block");
		grid.add(stack, x, y);
		App.array[x][y] = value;
	}
	
	private Node getNode(int x, int y) {
		ObservableList<Node> children = grid.getChildren();	
		Node temp = null;
		
		for (Node node: children) {
			if(grid.getColumnIndex(node) == x && grid.getRowIndex(node) == y) {
				temp = node;
			}
		}
		
		return temp;
	}
	
	protected void randGen() {
		Random rand = new Random();
		int pos = rand.nextInt(side * side);
        int row, col;
        
        do {
            pos = (pos + 1) % (side * side);
            row = pos / side;
            col = pos % side;
        } 
        while (App.array[col][row] != 0);
 
        App.array[col][row] = init;
        genBlock(col, row, init);
	}
	
	private void gameOver(boolean win) {
		grid.setOpacity(0.5);
		banner.setOpacity(1.0);
		banner.toFront();
		//boolean finish = win ? banner.getChildren().add(new Text("You win")): banner.getChildren().add(new Text("You lose"));
	}
	
	private boolean canMerge() {
		for(int i = 0; i < side; i++) { //columns
			for(int j = 0; j < side; j++) { //rows
				if(i == 0 && j == 0) {
					if(App.array[i][j] == App.array[i+1][j] || App.array[i][j] == App.array[i][j+1]) { 
						return true;
					}
					else {
						return false;
					}
				}
				else if(i == side - 1 && j == side - 1) {
					if(App.array[i][j] == App.array[i][j-1] || App.array[i][j] == App.array[i-1][j]) { 
						return true;
					}
					else {
						return false;
					}
				}
				else if(i == 0 && j == side - 1) {
					if(App.array[i][j] == App.array[i][j-1] || App.array[i][j] == App.array[i+1][j]) { 
						return true;
					}
					else {
						return false;
					}
				}
				else if(i == side - 1 && j == 0) {
					if(App.array[i][j] == App.array[i-1][j] || App.array[i][j] == App.array[i][j+1]) { 
						return true;
					}
					else {
						return false;
					}
				}
				else if(j == 0) {
					if(App.array[i][j] == App.array[i-1][j] || App.array[i][j] == App.array[i+1][j] || App.array[i][j] == App.array[i][j+1] ) { 
						return true;
					}
					else {
						return false;
					}
				}
				else if(j == side - 1) {
					if(App.array[i][j] == App.array[i-1][j] || App.array[i][j] == App.array[i+1][j] || App.array[i][j] == App.array[i][j-1] ) { 
						return true;
					}
					else {
						return false;
					}
				}
				else if(i == 0) {
					if(App.array[i][j] == App.array[i][j-1] || App.array[i][j] == App.array[i][j+1] || App.array[i][j] == App.array[i+1][j] ) { 
						return true;
					}
					else {
						return false;
					}
				}
				else if(i == side - 1) {
					if(App.array[i][j] == App.array[i][j-1] || App.array[i][j] == App.array[i][j+1] || App.array[i][j] == App.array[i-1][j] ) { 
						return true;
					}
					else {
						return false;
					}
				}
				else {
					if(App.array[i][j] == App.array[i][j-1] || App.array[i][j] == App.array[i][j+1] || App.array[i][j] == App.array[i+1][j] || App.array[i][j] == App.array[i-1][j]) { 
						return true;
					}
					else {
						return false;
					}
				}
			}
		}
		return false;
	}
	
	private void merge(int x, int y, int nextx, int nexty) {
		Node curr = getNode(x, y);
		Node next = getNode(nextx, nexty);
		int val = 2 * App.array[x][y];
		
		grid.getChildren().remove(curr);
		grid.getChildren().remove(next);
		App.array[x][y] = 0;
		genBlock(nextx, nexty, val);
	}
	
	private boolean spaceAval() {
		for(int i = 0; i < side; i++) {
			for(int j = 0; j < side; j++) {
				if(App.array[i][j] == 0) {
					return true;
				}
			}
		}
		return false;
	}
	
	protected void slide(int xinc, int yinc, int start) {	
		for(int i = 0; i < side * side; i++) {
			int j = Math.abs(start - i);
			int x = j % side;
			int y = j / side;
			int nextx = x + xinc;
			int nexty = y + yinc;
			
			while(nextx >= 0 && nextx < side && nexty >= 0 && nexty < side) {					
				int curr = App.array[x][y];
				int next = App.array[nextx][nexty];
				
				if(next == 0 && curr == 0) { //if both curr and next point to empty spaces, then tiles were already moved
					break;
				}
				else if(next == curr && next != 0 && curr != 0) { //if curr and next point to equal tiles, merge tiles
					merge(x, y, nextx, nexty);
					points += (2 * curr);
				}
				else if(next == 0) { //if next node is empty, shift tile
					genBlock(nextx, nexty, curr);
					grid.getChildren().remove(getNode(x,y));
					App.array[x][y] = 0;
				}
				else { //if next node isn't empty, shift pointers
					x += xinc;
					y += yinc;
					nextx += xinc;
					nexty += yinc;
				}
			}
		}
		
		score.setText("Score: " + points);
		
		if(spaceAval()) {
			randGen();
		}
		else if(!canMerge()) {
			gameOver(false);
		}
	}
}
