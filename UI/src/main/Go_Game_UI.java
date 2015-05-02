package main;
import graphics.JCanvas;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import listeners.PlayerListener;

public class Go_Game_UI {
	
	/*TODO :
	 * Bug : dans les shapes, il faut gérer les cotés
	 * Bug : l'utilisateur peut placer des pions sur les cotés gauche droit mais pas en haut
	 * Bug : IA_Easy commence à partir en couille tant qu'elle connait pas la couleur car elle essaie de bouffer ses jetons
	 * Bug : On voit qu'une demi pierre soit mettre un padding , soit GobanSize à 10 et corriger le bug des cotés en les interdisant
	 * Bug : L'IA peut mettre une pierre quand elle vient de se la faire prendre..(verifier couleur des voisins pour la position) et si pas de place ->exception fin du game
	 * 
	*Begin Note Max : 
	*	Menu IA (
	*		Medium: random point vers des pierres existantes (et si 3 connexions sur une pierre noire alors la manger)
	*		IA Hard : lorem ipsum dolores sit j'en sais rien xD
	*			EDIT : T'inquiètes on bidouillera un truc xD (Valentin)
	*Fin Note Max
	 */
	
	public static final String TITLE = "Jeu de Go";
	public static final String VERSION = "0.4.5";
	
	public static final int GOBANSIZE = 9;
	public static final int GRIDSIZE = 100;
	public static final int ROCKSIZE = 80;
	public static final int DEADZONE = GRIDSIZE / 4;
	
	public static void main(String[] args) {
		//Set the goban
		JCanvas goban = new JCanvas();
		goban.setPreferredSize(new Dimension(GOBANSIZE * GRIDSIZE,GOBANSIZE * GRIDSIZE));
		new PlayerListener(goban,"Random");
		
		Box box_text = Box.createVerticalBox();
		JLabel current_text_player = new JLabel("<html>PLAYER TURN</html>",SwingConstants.CENTER);
		current_text_player.setFont (current_text_player.getFont ().deriveFont (64.0f));
		box_text.add(current_text_player);
		goban.setCurrent_player_text(current_text_player);	
	
		
		//Hide the component
		goban.setVisible(false);
		box_text.setVisible(false);

		//Build the main frame
		JFrame frame = new JFrame(TITLE + " " + VERSION);
		try {
			//Show the splash screen
			BufferedImage image = ImageIO.read(Go_Game_UI.class.getResourceAsStream("/images/splash.jpg"));
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
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	} 
}