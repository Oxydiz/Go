package listeners;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import entities.Rock;
import graphics.JCanvas;
import static main.Go_Game_UI.*;

public class SimpleMouseListener extends JCanvasMouseListener {
	
	private class OutOfRangeException extends Exception {
		private static final long serialVersionUID = 1L;
	}

	Color playerColor = Color.BLACK;

	public SimpleMouseListener(JCanvas canvas) {
		super(canvas);
	}

	@Override
	protected void rightClickAction(MouseEvent e) {

		Point p;
		try {
			p = getPoint(e);
		} catch (OutOfRangeException e1) {
			return;
		}
		
		if (!canvas.isFree(p))
			canvas.removeRock(p);

		super.leftClickAction(e);

	}

	@Override
	protected void leftClickAction(MouseEvent e) {

		Point p;
		try {
			p = getPoint(e);
		} catch(OutOfRangeException e1) {
			return;
		}
		
		if (canvas.isFree(p)) {
			if(!canvas.immediateDeath(p,playerColor)){
				canvas.addRock(createRock(p));
				
				
				
				//--------------IA TRY AND FAILED (VERY CALCULATION MUCH MEMORY)
				/*
				 * TODO:
				 * 	Réussir à faire wait 1s après le changement de player..
				 * */
				if(canvas.getCurrent_player_text().getText()=="<html>IA TURN</html>"){
					//Should put a delay  displaying IA TURN.. but not .
					/*try {
					    Thread.sleep(1000);
					} 
					catch(InterruptedException ex) {
					    Thread.currentThread().interrupt();
					}*/
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
				//--------------IA END--------------------------
				
				
				
			}				
		}

		super.leftClickAction(e);

	}

	private Point getPoint(MouseEvent e) throws OutOfRangeException {

		// Get mouse emplacement
		Point p = e.getPoint();
		
		// Modify the coordinate to fetch our goban intersection
		if((p.x % GRIDSIZE) <= (GRIDSIZE - DEADZONE) / 2)
			p.x -= p.x % GRIDSIZE;
		else if((p.x % GRIDSIZE) >= (GRIDSIZE - (GRIDSIZE - DEADZONE) / 2))
			p.x += GRIDSIZE - (p.x % GRIDSIZE);
		else
			throw new OutOfRangeException();
		
		if((p.y % GRIDSIZE) <= (GRIDSIZE - DEADZONE) / 2)
			p.y -= p.y % GRIDSIZE;
		else if((p.y % GRIDSIZE) >= (GRIDSIZE - (GRIDSIZE - DEADZONE) / 2))
			p.y += GRIDSIZE - (p.y % GRIDSIZE);
		else
			throw new OutOfRangeException();
		
		p.x /= GRIDSIZE;
		p.y /= GRIDSIZE;
		
		return p;
	}

	private Rock createRock(Point p) {
		
		playerColor = Color.BLACK; //(playerColor == Color.BLACK) ? playerColor = Color.WHITE : Color.BLACK;
		return new Rock(playerColor, p.x, p.y);

	}

}