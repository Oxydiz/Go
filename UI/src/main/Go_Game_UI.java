package main;
import entities.AI.AILevel;
import graphics.JCanvas;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import listeners.PlayerListener;

public class Go_Game_UI {
	
	/*TODO :
	 * Bug : dans les shapes, il faut gérer les cotés
	 * 		EDIT : Done (Valentin)
	 * Bug : l'utilisateur peut placer des pions sur les cotés gauche droit mais pas en haut
	 * 		EDIT : Done (Valentin)
	 * Bug : IA_Easy commence à partir en couille tant qu'elle connait pas la couleur car elle essaie de bouffer ses jetons
	 * 		EDIT : cf note (Valentin)
	 * Bug : On voit qu'une demi pierre soit mettre un padding , soit GobanSize à 10 et corriger le bug des cotés en les interdisant
	 * 		EDIT : Ouais il faudra changer l'affichage, mais ce sera plus simple avec des assets (Valentin)
	 * Bug : L'IA peut mettre une pierre quand elle vient de se la faire prendre..
	 * 		EDIT : cd note (Valentin)
	 * 			EDIT : Done (Valentin)
	 * 
	 * NOTE : 	MAXIME JE VAIS TE BUTER :) Cette implémentation de l'IA est à chier xD
	 * 			Pour ton information, le meilleur moen de faire ce genre d'IA c'est de lui faire créer des inputs
	 * 			et les process comme si c'était un humain. Du coup on va générer des MouseEvents personnalisé
	 * 			avec un boolean pour savoir si c'est l'IA et faire des return en conséquences (Valentin)
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
	public static final String VERSION = "0.4.7";
	
	public static final int GOBANSIZE = 9;
	public static final int GRIDSIZE = 100;
	public static final int ROCKSIZE = 80;
	public static final int DEADZONE = GRIDSIZE / 4;
	public static final String PLAYERTURN = "<html>PLAYER TURN</html>";
	public static final String IATURN = "<html>IA TURN</html>";
	
	public static void main(String[] args) {
		//Set the goban
		JCanvas goban = new JCanvas(AILevel.RANDOM);
		goban.setPreferredSize(new Dimension((GOBANSIZE - 1) * GRIDSIZE,(GOBANSIZE - 1) * GRIDSIZE));
		new PlayerListener(goban);
		
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
		//Show the splash screen
//			BufferedImage image = ImageIO.read(Go_Game_UI.class.getResourceAsStream("/images/splash.jpg"));
//			JLabel background = new JLabel(new ImageIcon(image));
//			frame.add(background,BorderLayout.CENTER);

		//After 2s show the main component and delete the splash
		final Timer timer = new Timer(2000, null);
		ActionListener listener = new ActionListener() {
		    @Override 
		    public void actionPerformed(ActionEvent e) {
		    	goban.setVisible(true);
		    	box_text.setVisible(true);
//	            	frame.remove(background);
		    	frame.pack();
				frame.setLocationRelativeTo(null);
		    }
		};
		timer.setRepeats(false);
		timer.addActionListener(listener);
		timer.start();
		
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
