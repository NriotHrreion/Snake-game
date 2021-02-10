package net.nocpiun.snake;

import java.util.List;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import net.nocpiun.snake.util.*;

public class Game {
	private GuiMainMenu mainMenu;
	private GuiInGame inGame;
	
	private boolean isGameFinish = false;
	private int score = 4;
	private Dir dir;
	private List<Integer> bodyBlocks;
	
	private Food food;
	
	public Game() {
		this.mainMenu = new GuiMainMenu();
		this.inGame = new GuiInGame();
		this.dir = Dir.RIGHT;
		
		this.bodyBlocks = new ArrayList<>();
		for(int i = 0; i < 7; i++) {
			this.bodyBlocks.add(i);
		}
		
		this.initGuis();
	}
	
	private void initGuis() {
		this.mainMenu.addButtonListener(new ButtonEvent() {
			@Override
			public void onClick(String name) {
				switch(name) {
				case "start":
					mainMenu.hideGui();
					inGame.showGui();
					
					Game.this.food = new Food();
					Game.this.food.spawn();
					
					new Timer().schedule(new TimerTask() {
						@Override
						public void run() {
							try {
								if(!Game.this.isGameFinish) {
									Game.this.update();
									if(!Game.this.isGameFinish) {
										Game.this.inGame.setTitle("Snake Game - Score: "+ Game.this.score);
									}
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}, 0, 1000); // run snake
					break;
				}
			}
		});
		this.mainMenu.showGui();
		
		this.inGame.addActionListener(new GuiActionEvent() {
			@Override
			public void onUpdate(String status) {
				if(status == "dead") {
					Game.this.gameFinish();
					return;
				}
				
				for(int i = 0; i < Game.this.bodyBlocks.size(); i++) {
					if(i + 1 == Game.this.bodyBlocks.size() && Game.this.bodyBlocks.get(i) == Game.this.food.foodBlockId) {
						switch(Game.this.dir) {
						case UP:
							Game.this.bodyBlocks.add(Game.this.bodyBlocks.get(i) - 30);
							break;
						case DOWN:
							Game.this.bodyBlocks.add(Game.this.bodyBlocks.get(i) + 30);
							break;
						case LEFT:
							Game.this.bodyBlocks.add(Game.this.bodyBlocks.get(i) - 1);
							break;
						case RIGHT:
							Game.this.bodyBlocks.add(Game.this.bodyBlocks.get(i) + 1);
							break;
						}
						
						Game.this.score++;
						
						Game.this.food.remove();
						Game.this.food = new Food();
						Game.this.food.spawn();
					}
				}
			}
			@Override
			public void onKeyDown(Key key) {
				Game.this.keyDown(key);
			}
		});
		this.inGame.addButtonListener(new ButtonEvent() {
			@Override
			public void onClick(String name) {
				//
			}
		});
	}
	
	private void update() {
		int unsetBlock = this.bodyBlocks.get(0);
		for(int i = 0; i < this.bodyBlocks.size(); i++) {
			if(i + 1 < this.bodyBlocks.size()) {
				this.bodyBlocks.set(i, this.bodyBlocks.get(i + 1));
			} else {
				int blockId = this.bodyBlocks.get(i);
				
				switch(this.dir) {
				case UP:
					if(blockId - 30 >= 0) {
						this.bodyBlocks.set(i, blockId - 30);
					} else {
						this.gameFinish();
					}
					break;
				case DOWN:
					if(blockId + 30 <= 1199) {
						this.bodyBlocks.set(i, blockId + 30);
					} else {
						this.gameFinish();
					}
					break;
				case LEFT:
					if(blockId % 30 != 0) {
						this.bodyBlocks.set(i, blockId - 1);
					} else {
						this.gameFinish();
					}
					break;
				case RIGHT:
					if((blockId + 1) % 30 != 0) {
						this.bodyBlocks.set(i, blockId + 1);
					} else {
						this.gameFinish();
					}
					break;
				}
			}
		}
		for(int i = 0; i < this.bodyBlocks.size(); i++) {
			if(i + 1 == this.bodyBlocks.size()) {
				this.inGame.setBodyBlock(this.bodyBlocks.get(i), true);
			} else {
				this.inGame.setBodyBlock(this.bodyBlocks.get(i), false);
			}
		}
		this.inGame.unsetBodyBlock(unsetBlock);
	}
	
	private void gameFinish() {
		if(!isGameFinish) {
			isGameFinish = true;
		}
		
		this.inGame.setTitle("Snake Game - Dead");
		Logger.log("Player dead, game finished");
	}
	
	private void keyDown(Key key) {
		switch(key) {
		case W:
			if(this.dir != Dir.DOWN) this.dir = Dir.UP;
			break;
		case A:
			if(this.dir != Dir.RIGHT) this.dir = Dir.LEFT;
			break;
		case S:
			if(this.dir != Dir.UP) this.dir = Dir.DOWN;
			break;
		case D:
			if(this.dir != Dir.LEFT) this.dir = Dir.RIGHT;
			break;
		}
	}
	
	public static void start() {
		new Game();
	}
	
	private class Food {
		public int foodBlockId;
		
		private int getRandom(int min, int max) {
			return (int)(Math.random() * (max - min) + min);
		}
		
		public boolean spawn() {
			int randomBlock = this.getRandom(0, 1199);
			this.foodBlockId = randomBlock;
			Game.this.inGame.setFoodBlock(this.foodBlockId);
			return true;
		}
		
		public void remove() {
			Game.this.inGame.unsetFoodBlock(this.foodBlockId);
		}
	}
}
