package main;
import entities.AI.AILevel;
import graphics.JCanvas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import listeners.PlayerListener;

public class Go_Game_UI {
	/*TODO : 
	 * Bug du thread, aussi bien dans IA random que Easy 
	 * Decompte des points (fonction qui compte le nombre d'intersection dans une shape (avec color)
	 * Choisir son IA
		3 Gros boutons (IA_EASY,IA_MEDIUM_IA_HARD)
		
		IA Hard, principe de MonteCarlo
	 */
	
	public static final String TITLE = "Jeu de Go";
	public static final String VERSION = "0.4.9";
	
	public static final int GOBANSIZE = 9;
	public static final int GRIDSIZE = (Toolkit.getDefaultToolkit().getScreenSize().getHeight() >= 1800)?100:80;
	public static final int ROCKSIZE = (Toolkit.getDefaultToolkit().getScreenSize().getHeight() >= 1800)?80:60;
	public static final int DEADZONE = GRIDSIZE / 4;
	public static final String PLAYERTURN = "<html>PLAYER TURN</html>";
	public static final String IATURN = "<html>IA TURN</html>";
	
	public static void main(String[] args) {
		//Set the goban		
		JCanvas goban = new JCanvas(AILevel.RANDOM);
		goban.setPreferredSize(new Dimension((GOBANSIZE + 1) * GRIDSIZE,(GOBANSIZE + 1) * GRIDSIZE));
		new PlayerListener(goban);
		
		//Set the text
		Box box_text = Box.createVerticalBox();
		JLabel current_text_player = new JLabel("<html>PLAYER TURN</html>",SwingConstants.CENTER);
		current_text_player.setFont (current_text_player.getFont().deriveFont (64.0f));
		box_text.add(current_text_player);
		goban.setCurrent_player_text(current_text_player);	
	
		//Set the ending button
		JButton end_button = new JButton("PASS");
		end_button.setPreferredSize(new Dimension((GOBANSIZE+1)*GRIDSIZE,70));
		end_button.setMinimumSize(new Dimension((GOBANSIZE+1)*GRIDSIZE,70));
		end_button.setMaximumSize(new Dimension((GOBANSIZE+1)*GRIDSIZE,70));
		end_button.setFont(end_button.getFont().deriveFont (42.0f));
		end_button.setForeground(Color.WHITE);
		end_button.setBackground(Color.GRAY);
		box_text.add(end_button);
		
		//Hide the component
		goban.setVisible(false);
		box_text.setVisible(false);

		//Build the main frame
		JFrame frame = new JFrame(TITLE + " " + VERSION);
		//Show the splash screen
		BufferedImage image;
		try {
			image = ImageIO.read(Go_Game_UI.class.getResourceAsStream("/images/splash.jpg"));
			JLabel background = new JLabel(new ImageIcon(image));
			frame.add(background,BorderLayout.CENTER);
			
			//After 2s show the main component and delete the splash
			final Timer timer = new Timer(2000, null);
			ActionListener listener = new ActionListener() {
			    @Override 
			    public void actionPerformed(ActionEvent e) {
			    	goban.setVisible(true);
			    	box_text.setVisible(true);
	            	frame.remove(background);
			    	frame.pack();
					frame.setLocationRelativeTo(null);
			    }
			};
			timer.setRepeats(false);
			timer.addActionListener(listener);
			timer.start();
			
		} 
		catch (IOException e1) {
			e1.printStackTrace();
		}
		
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
		
		goban.initAI();
	} 
}
