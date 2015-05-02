package entities;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;
import graphics.JCanvas;
import static main.Go_Game_UI.*;

public class IA {
	public static void IA_Random(JCanvas canvas){
		final Timer timer = new Timer(300, null);
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
	
	public static void IA_Easy(JCanvas canvas){
		final Timer timer = new Timer(500, null);
        ActionListener listener = new ActionListener() {
            @Override 
            public void actionPerformed(ActionEvent e) {
            	if(canvas.getCurrent_player_text().getText()=="<html>IA TURN</html>"){
            		//Check easy point 
    				for(int i = 2; i<GOBANSIZE-1; i++){
    					for(int j = 2; j<GOBANSIZE-1; j++){
    						//Verifier la couleur du middle pion si c'est un noiro alors on le bouffe
    						//System.out.println(canvas.getRock(new Rock(Color.BLACK,i,j),i,j).getColor()); à mettre dans le true
    						if(!canvas.isFree(new Rock(Color.RED,i,j))){
    							if(!canvas.isFree( new Rock(Color.WHITE,i-1,j)) && 
    									!canvas.isFree( new Rock(Color.WHITE,i+1,j)) &&
    									!canvas.isFree( new Rock(Color.WHITE,i,j-1)) &&
    									canvas.isFree( new Rock(Color.WHITE,i,j+1))){
    								canvas.addRock(new Rock(Color.WHITE,i,j+1));
    			    				canvas.repaint();
    			    				return;
    							}
    							
    							if(!canvas.isFree( new Rock(Color.WHITE,i-1,j)) && 
    									!canvas.isFree( new Rock(Color.WHITE,i+1,j)) &&
    									canvas.isFree( new Rock(Color.WHITE,i,j-1)) &&
    									!canvas.isFree( new Rock(Color.WHITE,i,j+1))){
    								canvas.addRock(new Rock(Color.WHITE,i,j-1));
    			    				canvas.repaint();
    			    				return;
    							}
    							
    							if(!canvas.isFree( new Rock(Color.WHITE,i-1,j)) && 
    									canvas.isFree( new Rock(Color.WHITE,i+1,j)) &&
    									!canvas.isFree( new Rock(Color.WHITE,i,j-1)) &&
    									!canvas.isFree( new Rock(Color.WHITE,i,j+1))){
    								canvas.addRock(new Rock(Color.WHITE,i+1,j));
    			    				canvas.repaint();
    			    				return;
    							}	
    							
    							if(canvas.isFree( new Rock(Color.WHITE,i-1,j)) && 
    									!canvas.isFree( new Rock(Color.WHITE,i+1,j)) &&
    									!canvas.isFree( new Rock(Color.WHITE,i,j-1)) &&
    									!canvas.isFree( new Rock(Color.WHITE,i,j+1))){
    								canvas.addRock(new Rock(Color.WHITE,i-1,j));
    			    				canvas.repaint();
    			    				return;
    							}
    						}
    						
    					}
    				}
            		
            		
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
