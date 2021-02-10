package net.nocpiun.snake;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;

import javax.swing.*;

import net.nocpiun.snake.util.*;

public class GuiMainMenu extends JFrame implements Gui {
	private final int width = 400;
	private final int height = 200;
	
	public GuiActionEvent actionEventListener;
	public ButtonEvent buttonEventListener;
	
	public GuiMainMenu() {
		super("Snake");
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
	
	private void initGui() {
		JPanel panel = new JPanel();
		JLabel lb_title = new JLabel("Snake");
		lb_title.setFont(new Font("Arial", Font.BOLD, 30));
		panel.add(lb_title);
		JButton c = new JButton("                                                     "
				+"                                                     "
				+"                                               ");
	    c.setContentAreaFilled(false);
	  	c.setBorderPainted(false);
		c.setEnabled(false);
		panel.add(c);
		JButton btn_start = new JButton("Start");
		btn_start.setFocusPainted(false);
		btn_start.setBackground(Color.WHITE);
		btn_start.setName("start");
		
		GuiMainMenu self = this;
		btn_start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JButton btn = (JButton)e.getSource();
				
				String name = btn.getName();
				Logger.log("Button '"+ name +"' Clicked");
				self.buttonEventListener.onClick(name);
			}
		});
		
		panel.add(btn_start);
		
		this.add(panel);
	}
}
