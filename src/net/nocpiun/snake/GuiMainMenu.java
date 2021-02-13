package net.nocpiun.snake;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;

import javax.swing.*;

import net.nocpiun.snake.util.*;

public class GuiMainMenu extends JFrame implements Gui {
	private final int width = 400;
	private final int height = 280;
	
	private final String[] levels = new String[] {"Noob", "Easy", "Normal", "Hard", "Master", "Robot"};
	
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
		panel.setBackground(Color.WHITE);
		
		JLabel lb_title = new JLabel("Snake");
		lb_title.setFont(new Font("Arial", Font.BOLD, 30));
		panel.add(lb_title);
		
		JButton c1 = new JButton("                                                     "
				+"                                               ");
	    c1.setContentAreaFilled(false);
	  	c1.setBorderPainted(false);
		c1.setEnabled(false);
		panel.add(c1);
		
		JComboBox<String> cb_levelSelector = new JComboBox<String>(this.levels);
		cb_levelSelector.setBackground(Color.WHITE);
		cb_levelSelector.setFocusable(false);
		cb_levelSelector.setSelectedIndex(2);
		panel.add(cb_levelSelector);
		
		JButton c2 = new JButton("                                                     "
				+"                                               ");
		c2.setContentAreaFilled(false);
		c2.setBorderPainted(false);
		c2.setEnabled(false);
		panel.add(c2);
		
		JButton btn_start = new JButton("Start");
		btn_start.setFocusPainted(false);
		btn_start.setBackground(Color.WHITE);
		btn_start.setName("start");
		
		btn_start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JButton btn = (JButton)e.getSource();
				
				String name = btn.getName();
				Logger.log("Button '"+ name +"' Clicked");
				GuiMainMenu.this.actionEventListener.onComboBoxSelect(cb_levelSelector.getSelectedIndex());
				GuiMainMenu.this.buttonEventListener.onClick(name);
			}
		});
		
		cb_levelSelector.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					GuiMainMenu.this.actionEventListener.onComboBoxSelect(cb_levelSelector.getSelectedIndex());
				}
			}
		});
		
		panel.add(btn_start);
		
		this.add(panel);
	}
}
