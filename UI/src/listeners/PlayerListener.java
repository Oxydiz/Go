package listeners;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;

import entities.IA;
import entities.Rock;
import graphics.JCanvas;
import static main.Go_Game_UI.*;

public class PlayerListener extends JCanvasMouseListener {
	String IA_Level = "Random";
	
	private class OutOfRangeException extends Exception {
		private static final long serialVersionUID = 1L;
	}

	Color playerColor = Color.BLACK;

	public PlayerListener(JCanvas canvas, String IA_Level) {
		super(canvas);
		this.IA_Level = IA_Level;
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
		} 
		catch(OutOfRangeException e1) {
			return;
		}
		
		if (canvas.isFree(p)) {
			if(!canvas.immediateDeath(p,playerColor)){
				canvas.addRock(createRock(p));
				if(IA_Level == "Random"){
					IA.IA_Random(canvas);
				}
				else{
					IA.IA_Easy(canvas);
				}
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