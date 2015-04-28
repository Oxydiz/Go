package Dir_Graphics;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;

import static main.Go_Game_UI.*;
import Entities.Rock;

public class SimpleMouseListener extends JCanvasMouseListener {
	
	Color playerColor = Color.BLACK;
	
	public SimpleMouseListener(JCanvas canvas) {
		super(canvas);
	}

	@Override
	protected void rightClickAction(MouseEvent e) {
		
		Point p = getPoint(e);
		if(!canvas.isFree(new Rock(p)))
			canvas.removeRock(p);
		canvas.repaint();

	}

	@Override
	protected void leftClickAction(MouseEvent e) {
		
		Rock r = new Rock(getPoint(e));
		if(canvas.isFree(r)) {
			canvas.addRock(createRock(e));
		}
		
		canvas.repaint();
		
	}
	
	private Point getPoint(MouseEvent e) {

		//Get mouse emplacement 
		Point p = e.getPoint();
		
		//Modify the coordinate to fetch our goban intersection
		p.x = ((p.x%GRIDSIZE) > GRIDSIZE/2)?p.x + (GRIDSIZE-(p.x%GRIDSIZE)):p.x - (p.x%GRIDSIZE);
		p.y = ((p.y%GRIDSIZE) > GRIDSIZE/2)?p.y + (GRIDSIZE-(p.y%GRIDSIZE)):p.y - (p.y%GRIDSIZE);
		
		return p;
	}
	
	private Rock createRock(MouseEvent e) {

		Point p = getPoint(e);
		
		playerColor = (playerColor == Color.BLACK)?playerColor = Color.WHITE:Color.BLACK;
		return new Rock(playerColor,p.x,p.y);
		
	}

}