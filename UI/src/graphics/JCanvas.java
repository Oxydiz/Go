package graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JPanel;

import entities.InfluenceMap;
import entities.Rock;
import entities.Shape;
import static main.Go_Game_UI.*;

public class JCanvas extends JPanel {
	private static final long serialVersionUID = -1351443492057511284L;

	private ArrayList<Shape> shapes;
	private InfluenceMap map;

	public JCanvas() {
		this.shapes = new ArrayList<Shape>();
		this.map = new InfluenceMap();
	}

	@Override
	public void paint(Graphics g) {
		// Paint the goban
		g.setColor(Color.ORANGE);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

		g.setColor(Color.BLACK);
		for (int i = 0; i <= this.getWidth(); i += GRIDSIZE) {
			g.drawLine(i, 0, i, this.getHeight());
		}
		for (int i = 0; i <= this.getHeight(); i += GRIDSIZE) {
			g.drawLine(0, i, this.getWidth(), i);
		}
		// Display the last line
		g.drawLine(0, this.getHeight() - 1, this.getWidth(), this.getHeight() - 1);

		for (int i = 0; i < shapes.size(); i++)
			shapes.get(i).draw(g);
		
		map.overlay(g);
	}

	private void captureEnemy(Rock r) {

		boolean c, re = false;

		for (int i = 0; i < shapes.size(); i++) {

			c = true;

			if (shapes.get(i).getColor() == r.getColor())
				continue;

			for (int j = 0; j < shapes.get(i).size(); j++)
				if (isFree(shapes.get(i).get(j), 1, 0) || isFree(shapes.get(i).get(j), -1, 0) || isFree(shapes.get(i).get(j), 0, 1) || isFree(shapes.get(i).get(j), 0, -1)) {
					c = false;
					break;
				}

			if (c) {
				for (int j = 0; j < shapes.get(i).size(); j++)
					removeRock(shapes.get(i).get(j), false);
				shapes.remove(i);
				re = true;
			}
		}

		if (re)
			this.repaint();

	}

	private void fuseShapes(Rock r) {
		ArrayList<Shape> a = getNeighbours(r);

		for (int i = 0; i < a.size(); i++) {
			shapes.remove(a.get(i));
		}

		a.add(0, new Shape(r));
		while (a.size() > 1) {
			a.get(0).addAll(a.get(1));
			a.remove(1);
		}

		shapes.add(a.get(0));

	}

	private ArrayList<Shape> getNeighbours(Rock r) {
		ArrayList<Shape> a = new ArrayList<Shape>();
		Shape tmp;

		if (shapes.size() == 0)
			return a;

		for (int i = 0; i < shapes.size(); i++) {
			if (shapes.get(i).getColor() != r.getColor())
				continue;

			tmp = shapes.get(i).get(r.getX() + 1, r.getY());
			if (tmp != null)
				a.add(tmp);
			tmp = shapes.get(i).get(r.getX() - 1, r.getY());
			if (tmp != null)
				a.add(tmp);
			tmp = shapes.get(i).get(r.getX(), r.getY() + 1);
			if (tmp != null)
				a.add(tmp);
			tmp = shapes.get(i).get(r.getX(), r.getY() - 1);
			if (tmp != null)
				a.add(tmp);
		}

		return a;
	}

	public void addRock(Rock r) {
		fuseShapes(r);
		map.placeRock(r);
		captureEnemy(r);
	}

	public void removeRock(Rock r, boolean repaint) {
		map.set(r.getPosition(), r.getOppositeColor());
		if (repaint)
			this.repaint();
	}

	public void removeRock(Point p) {
		for (int i = 0; i < shapes.size(); i++) {
			Rock r = shapes.get(i).get(p);
			if (r != null) {
				removeRock(r, true);
				shapes.get(i).remove(r);
			}
		}

	}


	public boolean isFree(Point p) {
		return map.isFree(p.x + p.y * GOBANSIZE);
	}

	public boolean isFree(Rock r) {
		return isFree(r, 0, 0);
	}

	public boolean isFree(Rock r, int x, int y) {
		return map.isFree(r.getPosition() + x + (y * GOBANSIZE));
	}

}