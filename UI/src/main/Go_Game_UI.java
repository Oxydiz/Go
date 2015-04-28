package main;
import graphics.JCanvas;

import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import listeners.SimpleMouseListener;

public class Go_Game_UI {
	
	/*TODO :
	 * Suppression validée
	 * Ajout du score en bas
	 * Ajout de la dernière ligne du goban
	 */
	
	public static final String TITLE = "Jeu de Go";
	public static final String VERSION = "0.2.1";
	
	public static final int GOBANSIZE = 9;
	public static final int GRIDSIZE = 100;
	public static final int ROCKSIZE = 80;
	public static final int DEADZONE = GRIDSIZE / 3;
	
	public static void main(String[] args) {
		//Set the goban
		JCanvas goban = new JCanvas();
		goban.setPreferredSize(new Dimension(GOBANSIZE * GRIDSIZE,GOBANSIZE * GRIDSIZE));
		new SimpleMouseListener(goban);
		
		JLabel title = new JLabel("<html>Score<br> Player 1 : 0 <br> Player 2 : 0</html>");
		title.setFont (title.getFont ().deriveFont (64.0f));

		//Build the main frame
		JFrame frame = new JFrame("Jeu de go");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		frame.setResizable(false);
		frame.setAlwaysOnTop(true);
		frame.add(title, BorderLayout.PAGE_END);
		frame.add(goban, BorderLayout.LINE_START);
		frame.pack();
		frame.setVisible(true);
		
		//Design improvement (Background and Centerscreen)
		frame.setBackground(Color.GRAY);
		frame.setLocationRelativeTo(null);
	} 
}
/*
 * List<IDrawable> selectedDrawables = canvas.findDrawables(e.getPoint());
		if (selectedDrawables.size() == 0) return;
		IDrawable drawable = (IDrawable) selectedDrawables.get(0);
		canvas.removeDrawable(drawable);
 * 
 */