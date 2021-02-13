package net.nocpiun.snake.robot;

import net.nocpiun.snake.Game;
import net.nocpiun.snake.util.*;

public class Robot {
	private int[] foodPosition = new int[2];
	private int foodBlockId;
	
	public void onFoodChange(int foodBlockId) {
		this.foodPosition = this.idToPosition(foodBlockId);
		this.foodBlockId = foodBlockId;
	}
	
	public void onMove(int movedBlockId, Game game) {
		int[] snakePosition = this.idToPosition(movedBlockId);
		Dir dir = game.dir;
		
		if(!game.isGameFinish) {
			if(snakePosition[1] < this.foodPosition[1]) {
				
				if(dir == Dir.UP) {
					if(snakePosition[0] % 30 == 0) {
						game.keyDown(Key.D);
					} else {
						game.keyDown(Key.A);
					}
				} else if(dir == Dir.LEFT || dir == Dir.RIGHT) {
					game.keyDown(Key.S);
				}
				
			} else if(snakePosition[1] > this.foodPosition[1]) {
				
				if(dir == Dir.DOWN) {
					if(snakePosition[0] % 30 == 0) {
						game.keyDown(Key.D);
					} else {
						game.keyDown(Key.A);
					}
				} else if(dir == Dir.LEFT || dir == Dir.RIGHT) {
					game.keyDown(Key.W);
				}
				
			} else if(snakePosition[1] == this.foodPosition[1]) {
				
				if((this.foodBlockId % 30 == 0 || (this.foodBlockId + 1) % 30 == 0) && (snakePosition[0] - 1 == this.foodPosition[0] || snakePosition[0] + 1 == this.foodPosition[0])) {
					game.keyDown(Key.W);
				} else if((this.foodBlockId < 30 || this.foodBlockId > 1170) && (snakePosition[1] + 1 == this.foodPosition[1] || snakePosition[1] - 1 == this.foodPosition[1])) {
					game.keyDown(Key.A);
				} else {
					if(snakePosition[0] > this.foodPosition[0] && dir != Dir.LEFT) {
						game.keyDown(Key.A);
					} else if(snakePosition[0] < this.foodPosition[0] && dir != Dir.RIGHT) {
						game.keyDown(Key.D);
					}
				}
				
			}
			
			switch(dir) {
			case UP:
				if(snakePosition[1] - 1 < 0) {
					if(snakePosition[0] % 30 == 0) {
						game.keyDown(Key.D);
					} else {
						game.keyDown(Key.A);
					}
				}
				break;
			case DOWN:
				if(snakePosition[1] + 1 > 39) {
					if(snakePosition[0] % 30 == 0) {
						game.keyDown(Key.D);
					} else {
						game.keyDown(Key.A);
					}
				}
				break;
			case LEFT:
				if(snakePosition[0] - 1 < 0) {
					if(snakePosition[1] - 1 < 0) {
						game.keyDown(Key.S);
					} else {
						game.keyDown(Key.W);
					}
				}
				break;
			case RIGHT:
				if(snakePosition[0] + 1 > 29) {
					if(snakePosition[1] + 1 > 39) {
						game.keyDown(Key.W);
					} else {
						game.keyDown(Key.S);
					}
				}
				break;
			}
		}
	}
	
	private int[] idToPosition(int blockId) {
		int[] position = new int[2];
		
		position[0] = blockId % 30;
		position[1] = blockId / 30;
		
		return position;
	}
}
