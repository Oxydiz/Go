package entities;

import static main.Go_Game_UI.*;
import graphics.JCanvas;

import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import listeners.PlayerListener;

public class AI {

	public enum AILevel {
		NONE,RANDOM, EASY, MEDIUM, HARD;
	}

	private AILevel level;
	private JCanvas canvas;
	private PlayerListener listener;

	public AI(JCanvas goban, AILevel level) throws Exception {

		this.canvas = goban;
		this.level = level;
		if (level == null)
			throw new Exception();

	}

	public void init() {

		MouseListener m[] = canvas.getMouseListeners();

		if (m.length == 0) {
			System.err.println("Could not access listeners in AI initialization");
			System.exit(-1);
		} else if (m[0].getClass().equals(PlayerListener.class)) {
			this.listener = (PlayerListener) m[0];
		} else {
			System.err.println("Invalid listener in AI initialization");
			System.exit(-1);
		}

	}

	public void play() {

		switch (level) {

		case RANDOM:
			playRandom();
			break;

		case EASY:
			playEasy();
			break;
		case MEDIUM:
		case HARD:
		default:
			break;

		}

	}

	private void placeRock(Point p) {

		listener.mouseClicked(new MouseEvent(new Component() {
			private static final long serialVersionUID = 1L;
		}, MouseEvent.MOUSE_CLICKED, System.currentTimeMillis(), 0, p.x * GRIDSIZE + GRIDSIZE, p.y * GRIDSIZE + GRIDSIZE, 1, false, MouseEvent.BUTTON1));

	}

	public void playRandom() {
		ArrayList<Integer> free = canvas.getFree();
		
		if(free.size() == 0)
			return;

		boolean played = false;
		Color c = listener.getColor();
		int r = (int) ((Math.random() * GOBANSIZE * GOBANSIZE)) % free.size();

		while (!played) {
			Point p = new Point(free.get(r) % GOBANSIZE, free.get(r) / GOBANSIZE);
			if (canvas.isFree(p))
				if (!canvas.immediateDeath(p, c)) {
					placeRock(p);
					played = true;
				}

			r++;
			r %= free.size();
		}
	}
	
	public void playEasy(){
		//donne si est free goabnsize*j+i
		ArrayList<Integer> free = canvas.getFree();	
		ArrayList<Rock> freeZone = new ArrayList<Rock>();
		
		if(free.size() == 0)
			return;

		//Check easy point 
		for(int i = 1; i<GOBANSIZE-1; i++){
			for(int j = 1; j<GOBANSIZE-1; j++){
				
				//Goban size -2
				//Verifier la couleur du middle pion si c'est un noiro alors on le bouffe
				if(!canvas.isFree(new Rock(Color.RED,i,j))){
					
					//Don't eat your allies
					Rock colorRock = new Rock(Color.WHITE,i,j);
					colorRock = canvas.getRock(colorRock, 0, 0);
					if(colorRock.getColor().getRed() == 255){
						break;
					}
					
					//Don't suicide
					//getRcok(new ppoint(i,j).getColor(
					int colorRockXless = (canvas.getRock(new Rock(Color.WHITE,i-1,j),0,0) == null)?0:canvas.getRock(new Rock(Color.WHITE,i-1,j),0,0).getColor().getRed();
					int colorRockXmore = (canvas.getRock(new Rock(Color.WHITE,i+1,j),0,0) == null)?0:canvas.getRock(new Rock(Color.WHITE,i+1,j),0,0).getColor().getRed();
					int colorRockYless = (canvas.getRock(new Rock(Color.WHITE,i,j-1),0,0) == null)?0:canvas.getRock(new Rock(Color.WHITE,i,j-1),0,0).getColor().getRed();
					int colorRockYmore = (canvas.getRock(new Rock(Color.WHITE,i,j+1),0,0) == null)?0:canvas.getRock(new Rock(Color.WHITE,i,j+1),0,0).getColor().getRed();
					//Noir = 0, 255=blanc
					System.out.println(colorRockXless+" "+colorRockYless+" "+colorRockXmore+" "+colorRockYmore+" i:"+i+" j:"+j);
					if(colorRockXless + colorRockXmore + colorRockYless +colorRockYmore == 0){
						break;
					}
					
					
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
				else{
					freeZone.add(new Rock(Color.WHITE,i,j));
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
