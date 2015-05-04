package listeners;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;

import entities.Rock;
import entities.Shape;
import graphics.JCanvas;
import static main.Go_Game_UI.*;

public class PlayerListener extends JCanvasMouseListener {

	private class OutOfRangeException extends Exception {
		private static final long serialVersionUID = 1L;
	}

	private static Color playerColor = Color.BLACK;

	public PlayerListener(JCanvas canvas) {
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
	}

	@Override
	protected void leftClickAction(MouseEvent e) {

		Point p;
		try {
			p = getPoint(e);
		} catch (OutOfRangeException e1) {
			return;
		}

		if (canvas.isFree(p)) {
			if (!canvas.immediateDeath(p, playerColor)) {
				canvas.addRock(createRock(p));
			}
		}

	}

	@Override
	protected void middleClickAction(MouseEvent e) {

		canvas.unselect();
		Point p;

		try {
			p = getPoint(e);
		} catch (OutOfRangeException e1) {
			return;
		}

		Shape s = canvas.getShape(p);

		if (s != null)
			canvas.getShape(p).select(true);
		
		super.middleClickAction(e);

	}

	private Point getPoint(MouseEvent e) throws OutOfRangeException {

		// Get mouse emplacement
		Point p = e.getPoint();
		p.x -= GRIDSIZE;
		p.y -= GRIDSIZE;

		// Modify the coordinate to fetch our goban intersection
		if ((p.x % GRIDSIZE) <= (GRIDSIZE - DEADZONE) / 2)
			p.x -= p.x % GRIDSIZE;
		else if ((p.x % GRIDSIZE) >= (GRIDSIZE - (GRIDSIZE - DEADZONE) / 2))
			p.x += GRIDSIZE - (p.x % GRIDSIZE);
		else
			throw new OutOfRangeException();

		if ((p.y % GRIDSIZE) <= (GRIDSIZE - DEADZONE) / 2)
			p.y -= p.y % GRIDSIZE;
		else if ((p.y % GRIDSIZE) >= (GRIDSIZE - (GRIDSIZE - DEADZONE) / 2))
			p.y += GRIDSIZE - (p.y % GRIDSIZE);
		else
			throw new OutOfRangeException();

		p.x /= GRIDSIZE;
		p.y /= GRIDSIZE;

		return p;
	}

	private Rock createRock(Point p) {
		return new Rock(playerColor, p.x, p.y);

	}

	public static void switchColor() {
		playerColor = (playerColor == Color.BLACK) ? playerColor = Color.WHITE : Color.BLACK;
	}

	public Color getColor() {
		return playerColor;
	}

}