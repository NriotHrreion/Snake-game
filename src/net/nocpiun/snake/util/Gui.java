package net.nocpiun.snake.util;

public interface Gui {
	public GuiActionEvent actionEventListener = null;
	public ButtonEvent buttonEventListener = null;
	
	public void showGui();
	public void hideGui();
	public void addActionListener(GuiActionEvent listener);
	public void addButtonListener(ButtonEvent listener);
}
