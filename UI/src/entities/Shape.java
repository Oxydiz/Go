package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

public class Shape extends ArrayList<Rock> {
	private static final long serialVersionUID = 1L;
	private Color color;
	
	
	public Shape(Rock r) {
		super();
		this.color = r.getColor();
		this.add(r);
	}
	
	public Shape get(int x, int y) {
		for(int i = 0; i < this.size(); i++)
			if(this.get(i).getX() == x && this.get(i).getY() == y)
				return this;
		
		return null;
	}

	public void draw(Graphics g) {
		for(int i = 0; i < this.size(); i++)
			this.get(i).draw(g);
		
	}
	
	public void remove(Point p) {
		for(int i = 0; i < this.size(); i++)
			if(this.get(i).getX() == p.x && this.get(i).getY() == p.y)
				this.remove(i);
	}
	
	public Color getColor() {
		return color;
	}

	public Rock get(Point p) {
		for(int i = 0; i < this.size(); i++)
			if(this.get(i).getX() == p.x && this.get(i).getY() == p.y)
				return this.get(i);
		
		return null;
	}

}
