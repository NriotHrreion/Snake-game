package net.nocpiun.snake;

import java.util.List;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import net.nocpiun.snake.util.*;
import net.nocpiun.snake.robot.Robot;

public class Game {
	private GuiMainMenu mainMenu;
	private GuiInGame inGame;
	
	public boolean isGameFinish = false;
	public boolean isRobotMode = false;
	private int score = 0;
	private int speed = 0;
	public Dir dir;
	private List<Integer> bodyBlocks;
	
	private Food food;
	private Robot robot;
	
	public Game() {
		this.mainMenu = new GuiMainMenu();
		this.inGame = new GuiInGame(this);
		this.dir = Dir.RIGHT;
		
		this.bodyBlocks = new ArrayList<>();
		for(int i = 0; i < 3; i++) {
			this.bodyBlocks.add(i);
		}
		
		this.initGuis();
	}
	
	private void initGuis() {
		this.mainMenu.addActionListener(new GuiActionEvent() {
			@Override
			public void onComboBoxSelect(int selectLvl) {
				switch(selectLvl) { // Difficult Level Choose
				case 0: // Noob
					Game.this.speed = 1000; // VERY VERY VERY EZ..... (jian dan dao hao wu you xi ti yan)
					break;
				case 1: // Easy
					Game.this.speed = 500; // EZEZEZ!!! (ru guo ni Normal hai shi jue de kun nan, ke yi shi shi Easy)
					break;
				case 2: // Normal
					Game.this.speed = 270; // Default Difficult (rong yi shang shou de nan du, ru guo ni wan de hen 6 de hua, ke yi shi shi Hard)
					break;
				case 3: // Hard
					Game.this.speed = 150; // <== The Best Experience Difficult (zong zhi wo hen xi huan zhe ge nan du de ti yan, ha ha ha XD)
					break;
				case 4: // Master
					Game.this.speed = 90; // God Like! (zui nb de nan du!!!)
					break;
				case 5: // Robot
					Game.this.speed = 270;
					Game.this.isRobotMode = true;
					Game.this.robot = new Robot(); // Robot Auto Playing (h a c k e r)
					break;
				}
			}
		});
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
										Game.this.inGame.displayScore(Game.this.score);
									}
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}, 0, Game.this.speed); // run snake
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
						// eat food
						
						int headBlockId = Game.this.bodyBlocks.get(i);
						
						switch(Game.this.dir) {
						case UP:
							if(headBlockId - 30 >= 0) {
								Game.this.bodyBlocks.add(headBlockId - 30);
							} else {
								Game.this.gameFinish();
							}
							break;
						case DOWN:
							if(headBlockId + 30 <= 1199) {
								Game.this.bodyBlocks.add(headBlockId + 30);
							} else {
								Game.this.gameFinish();
							}
							break;
						case LEFT:
							if(headBlockId % 30 != 0) {
								Game.this.bodyBlocks.add(headBlockId - 1);
							} else {
								Game.this.gameFinish();
							}
							break;
						case RIGHT:
							if((headBlockId + 1) % 30 != 0) {
								Game.this.bodyBlocks.add(headBlockId + 1);
							} else {
								Game.this.gameFinish();
							}
							break;
						}
						
						Game.this.score++;
						if(Game.this.speed == 90) { // Master +2 score per food
							Game.this.score++;
						}
						
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
				if(this.isRobotMode) {
					this.robot.onMove(this.bodyBlocks.get(i), this);
				}
			} else {
				this.inGame.setBodyBlock(this.bodyBlocks.get(i), false);
			}
		}
		
		this.inGame.unsetBodyBlock(unsetBlock);
	}
	
	private void gameFinish() {
		if(!isGameFinish) {
			isGameFinish = true;
			
			this.inGame.setTitle("Snake Game - Dead");
			Logger.log("Player dead, game finished");
		}
	}
	
	public void keyDown(Key key) {
		Logger.log("Key '"+ key.toString() +"' Pressed");
		
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
			if(!Game.this.isGameFinish) {
				int randomBlock = this.getRandom(0, 1199);
				this.foodBlockId = randomBlock;
				
				String blockName = Game.this.inGame.getBlockName(this.foodBlockId);
				if(blockName == "none") {
					Game.this.inGame.setFoodBlock(this.foodBlockId);
				} else {
					this.remove();
					this.spawn();
					return false;
				}
				
				if(Game.this.isRobotMode) {
					Game.this.robot.onFoodChange(this.foodBlockId);
				}
				return true;
			}
			return false;
		}
		
		public void remove() {
			Game.this.inGame.unsetFoodBlock(this.foodBlockId);
		}
	}
}
