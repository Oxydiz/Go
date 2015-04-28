import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JLabel;

import Dir_Graphics.JCanvas;
import Dir_Graphics.SimpleMouseListener;

public class Go_Game_UI {
	/*TODO :
	 * Permettre la suppression d'un élement (fonctionne mais doit être moins precis)
	 */
	public static void main(String[] args) {
		//Set the goban
		JCanvas goban = new JCanvas();
		goban.setPreferredSize(new Dimension(900,900));
		new SimpleMouseListener(goban);
		
		JLabel title = new JLabel("Lorem ipsum doloes");

		//Build the main frame
		JFrame frame = new JFrame("Jeu de go");
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