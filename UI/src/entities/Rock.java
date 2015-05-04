package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import static main.Go_Game_UI.*;

public class Rock {
	
	private int x;
	private int y;
	private Color color;
	
	public Rock(Color c,int x,int y) {
		
		this.color = c;
		this.x = x;
		this.y = y;
		
	}
	
	public Rock(Point p) {
		this.color = Color.RED;
		this.x = p.x;
		this.y = p.y;
	}
	
	public void draw(Graphics g,boolean selected) {
		
		Color c = g.getColor();
		g.setColor(selected ? Color.BLUE : color);
		g.fillOval(this.getX() * GRIDSIZE - ROCKSIZE / 2, this.getY() * GRIDSIZE - ROCKSIZE / 2, ROCKSIZE, ROCKSIZE);
		g.setColor(c);
		
	}
	
	public Color getColor() {
		return color;
	}

	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getPosition() {
		return x + y * GOBANSIZE;
	}

}
