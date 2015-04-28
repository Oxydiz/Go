package main;
import graphics.JCanvas;

import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JLabel;

import listeners.SimpleMouseListener;

public class Go_Game_UI {
	
	/*TODO :
	 * Permettre la suppression d'un élement (fonctionne mais doit être moins precis)
	 * 
	 * EDIT :
	 * 	Je pense l'avoir fait avec ma deadzone. (Valentin)
	 * 
	 */
	
	public static final String TITLE = "Jeu de Go";
	public static final String VERSION = "0.2.1";
	
	public static final int GOBANSIZE = 9;
	public static final int GRIDSIZE = 100;
	public static final int ROCKSIZE = 50;
	public static final int DEADZONE = GRIDSIZE / 3;
	
	public static void main(String[] args) {
		//Set the goban
		JCanvas goban = new JCanvas();
		goban.setPreferredSize(new Dimension(GOBANSIZE * GRIDSIZE,GOBANSIZE * GRIDSIZE));
		new SimpleMouseListener(goban);
		
		JLabel title = new JLabel("soloes");

		//Build the main frame
		JFrame frame = new JFrame(TITLE + " " + VERSION);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		frame.setResizable(false);
		frame.setAlwaysOnTop(true);
		frame.add(title, BorderLayout.PAGE_START);
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