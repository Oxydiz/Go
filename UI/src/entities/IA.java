package entities;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;
import graphics.JCanvas;
import static main.Go_Game_UI.*;

public class IA {
	public static void IA_Easy(JCanvas canvas){
		final Timer timer = new Timer(500, null);
        ActionListener listener = new ActionListener() {
            @Override 
            public void actionPerformed(ActionEvent e) {
            	if(canvas.getCurrent_player_text().getText()=="<html>IA TURN</html>"){
    				//List free zone
    				Rock IA_free;			
    				ArrayList<Rock> freeZone = new ArrayList<Rock>();
    				for(int i = 1; i<GOBANSIZE; i++){
    					for(int j = 1; j<GOBANSIZE; j++){
    						IA_free = new Rock(Color.WHITE,i,j);
    						if(canvas.isFree(IA_free)){
    							freeZone.add(IA_free);
    						}
    					}
    				}
    				
    				//Choose a free space in the goban
    				int choice_IA = (int)(Math.random() * (freeZone.size()));
    				
    				//Add the new
    				canvas.addRock(freeZone.get(choice_IA));
    				canvas.repaint();
            	}
            	
            }
        };
        timer.setRepeats(false);
        timer.addActionListener(listener);
        timer.start();
	}
}
