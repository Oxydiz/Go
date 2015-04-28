package Dir_Graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.List;

public class SimpleMouseListener extends JCanvasMouseListener {
	Color playerColor = Color.BLACK;
	
	public SimpleMouseListener(JCanvas canvas) {
		super(canvas);
	}

	protected void rightClickAction(MouseEvent e) {
		List<IDrawable> selectedDrawables = canvas.findDrawables(e.getPoint());
		if (selectedDrawables.size() == 0) return;
		IDrawable drawable = (IDrawable) selectedDrawables.get(0);
		canvas.removeDrawable(drawable);
	}

	protected void leftClickAction(MouseEvent e) {
		IDrawable rect = createDrawable(e);
		if (canvas.isFree(rect.getRectangle())) {
			canvas.addDrawable(rect);
		}
	}

	private IDrawable createDrawable(MouseEvent e) {
		//Get mouse emplacement 
		Point p = e.getPoint();
		
		//Modify the coordinate to fetch our goban intersection
		p.x = ((p.x%100) > 50)?p.x + (100-(p.x%100)):p.x - (p.x%100);
		p.y = ((p.y%100) > 50)?p.y + (100-(p.y%100)):p.y - (p.y%100);
		
		Dimension dim = new Dimension(50, 50);
		
		//Define the color and the player
		playerColor = (playerColor == Color.BLACK)?playerColor = Color.WHITE:Color.BLACK;
		return new CircleDrawable(playerColor, p, dim);
	}

}