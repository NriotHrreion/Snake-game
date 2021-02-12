package net.nocpiun.snake;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.*;

import javax.swing.*;

import net.nocpiun.snake.util.*;

public class GuiInGame extends JFrame implements Gui {
	private final int width = 600;
	private final int height = 400;
	
	private JPanel gamePanel;
	private JPanel infoPanel;
	private JLabel scoreLabel;
	
	public GuiActionEvent actionEventListener;
	public ButtonEvent buttonEventListener;
	
	public GuiInGame() {
		super("Snake Game");
		this.setSize(this.width, this.height);
		this.setLocation(200, 130);
		this.setResizable(false);
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		this.initGui();
	}
	
	public void addActionListener(GuiActionEvent listener) {
		this.actionEventListener = listener;
	}
	
	public void addButtonListener(ButtonEvent listener) {
		this.buttonEventListener = listener;
	}
	
	public void showGui() {
		this.setVisible(true);
	}
	
	public void hideGui() {
		this.setVisible(false);
	}
	
	public void setBodyBlock(int blockId, boolean isHeadBlock) {
		JPanel block = (JPanel)this.gamePanel.getComponent(blockId);
		if(isHeadBlock) {
			if(block.getName() == "body") {
				this.actionEventListener.onUpdate("dead");
				return;
			}
		}
		if(block.getName() == "none") {
			block.setBackground(Color.BLACK);
			block.setName("body");
			this.actionEventListener.onUpdate("none");
		}
	}
	
	public void unsetBodyBlock(int blockId) {
		JPanel block = (JPanel)this.gamePanel.getComponent(blockId);
		block.setBackground(null);
		if(block.getName() == "body") {
			block.setName("none");
			this.actionEventListener.onUpdate("none");
		}
	}
	
	public boolean setFoodBlock(int blockId) {
		JPanel block = (JPanel)this.gamePanel.getComponent(blockId);
		if(block.getName() == "none") {
			block.setBackground(Color.RED);
			block.setName("food");
			this.actionEventListener.onUpdate("none");
			return true;
		} else {
			return false;
		}
	}
	
	public void unsetFoodBlock(int blockId) {
		JPanel block = (JPanel)this.gamePanel.getComponent(blockId);
		block.setBackground(Color.BLACK);
		if(block.getName() == "food") {
			block.setName("none");
			this.actionEventListener.onUpdate("none");
		}
	}
	
	public String getBlockName(int blockId) {
		JPanel block = (JPanel)this.gamePanel.getComponent(blockId);
		return block.getName();
	}
	
	public void displayScore(int score) {
		this.scoreLabel.setText("Score: "+ score);
	}
	
	private void initGui() {
		this.setLayout(new GridLayout(1, 2));
		
		this.gamePanel = new JPanel(new GridLayout(40, 30));
		this.gamePanel.setFocusable(true);
		this.gamePanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		this.gamePanel.setBackground(Color.WHITE);
		for(int i = 0; i < 1200; i++) {
			JPanel content = new JPanel();
			content.setName("none");
			content.setBorder(BorderFactory.createLineBorder(new Color(206, 206, 206)));
			content.setBackground(null);
			this.gamePanel.add(content);
		}
		this.gamePanel.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				Key key = null;
				
				switch(e.getKeyCode()) {
				case 87:
					key = Key.W;
					break;
				case 65:
					key = Key.A;
					break;
				case 83:
					key = Key.S;
					break;
				case 68:
					key = Key.D;
					break;
				}
				
				if(key != null) {
					Logger.log("Key '"+ key.toString() +"' Pressed");
					GuiInGame.this.actionEventListener.onKeyDown(key);
				}
			}
		});
		this.add(this.gamePanel);
		
		this.infoPanel = new JPanel();
		JButton btn_exit = new JButton("Exit");
		btn_exit.setFocusPainted(false);
		btn_exit.setFocusable(false);
		btn_exit.setBackground(Color.WHITE);
		btn_exit.setName("exit");
		btn_exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JButton btn = (JButton)e.getSource();
				
				String name = btn.getName();
				Logger.log("Button '"+ name +"' Clicked");
				System.exit(0);
//				self.buttonEventListener.onClick(name);
			}
		});
		JButton c = new JButton("                                                     "
				+"            ");
	    c.setContentAreaFilled(false);
	  	c.setBorderPainted(false);
		c.setEnabled(false);
		JLabel lb_info = new JLabel("Copyright (c) NriotHrreion 2021");
		lb_info.setFont(new Font("Arial", Font.ITALIC + Font.BOLD, 12));
		lb_info.setForeground(Color.GRAY);
		JLabel lb_control = new JLabel("Press 'W A S D' to control the snake");
		lb_control.setFont(new Font("Arial", Font.BOLD, 12));
		JButton c1 = new JButton("                                                     "
				+"            ");
	    c1.setContentAreaFilled(false);
	  	c1.setBorderPainted(false);
		c1.setEnabled(false);
		this.scoreLabel = new JLabel("Score: ");
		this.scoreLabel.setFont(new Font("Arial", Font.BOLD, 15));
		this.scoreLabel.setForeground(Color.BLACK);
		
		this.infoPanel.add(btn_exit);
		this.infoPanel.add(c);
		this.infoPanel.add(lb_info);
		this.infoPanel.add(lb_control);
		this.infoPanel.add(c1);
		this.infoPanel.add(this.scoreLabel);
		this.add(this.infoPanel);
	}
}
