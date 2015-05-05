package main;
import graphics.JCanvas;

import java.awt.*;

import javax.swing.Box;
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
	public static final String VERSION = "0.3.0";
	
	public static final int GOBANSIZE = 9;
	public static final int GRIDSIZE = 100;
	public static final int ROCKSIZE = 80;
	public static final int DEADZONE = GRIDSIZE / 3;
	
	public static void main(String[] args) {
		//Set the goban
		JCanvas goban = new JCanvas();
		goban.setPreferredSize(new Dimension(GOBANSIZE * GRIDSIZE,GOBANSIZE * GRIDSIZE));
		new SimpleMouseListener(goban);
		
		Box box_text = Box.createVerticalBox();
		JLabel text_title = new JLabel("<html>Score</html>",SwingConstants.CENTER);
		text_title.setFont (text_title.getFont ().deriveFont (64.0f));
		box_text.add(text_title);
		JLabel text_content = new JLabel("<html>Player 1 : 0 rock <br> Player 2 : 0 rock</html>");
		text_content.setFont (text_content.getFont ().deriveFont (32.0f));
		box_text.add(text_content);

		//Build the main frame
		JFrame frame = new JFrame(TITLE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		frame.setResizable(false);
		frame.setAlwaysOnTop(true);
		frame.add(goban, BorderLayout.LINE_START);
		frame.add(box_text, BorderLayout.PAGE_END);		
		frame.pack();
		frame.setVisible(true);
		
		//Design improvement (Background and Centerscreen)
		frame.setBackground(Color.GRAY);
		frame.setLocationRelativeTo(null);
	} 
}