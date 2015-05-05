package entities;

import graphics.JCanvas;
import static main.Go_Game_UI.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

public class Shape extends ArrayList<Rock> {
	
	private static final long serialVersionUID = 1L;
	private Color color;
	private JCanvas canvas;
	private boolean selected;
	private ArrayList<Integer> freedom;
	
	public Shape(Rock r,JCanvas canvas) {
		super();
		this.color = r.getColor();
		this.add(r);
		this.selected = false;
		this.canvas = canvas;
		this.freedom = new ArrayList<Integer>();
		
		if(canvas.isFree(r, 1, 0));
			freedom.add(r.getX() + 1 + r.getY() * GOBANSIZE);		
		if(canvas.isFree(r, -1, 0));
			freedom.add(r.getX() - 1 + r.getY() * GOBANSIZE);		
		if(canvas.isFree(r, 0, 1));
			freedom.add(r.getX() + GOBANSIZE + r.getY() * GOBANSIZE);		
		if(canvas.isFree(r, 0, -1));
			freedom.add(r.getX() - GOBANSIZE + r.getY() * GOBANSIZE);
	}
	
	public Shape get(int x, int y) {
		for(int i = 0; i < this.size(); i++)
			if(this.get(i).getX() == x && this.get(i).getY() == y)
				return this;
		
		return null;
	}

	public void draw(Graphics g) {
		for(int i = 0; i < this.size(); i++)
			this.get(i).draw(g,selected);
		
		Color c = g.getColor();
		g.setColor(Color.RED);
		
		if(selected)
			for(int i = 0; i < freedom.size(); i++) {
				g.fillOval((freedom.get(i) % GOBANSIZE) * GRIDSIZE - ROCKSIZE / 4 + GRIDSIZE, (freedom.get(i) / GOBANSIZE) * GRIDSIZE - ROCKSIZE / 4 + GRIDSIZE, ROCKSIZE / 2,ROCKSIZE / 2);
			}
		
		g.setColor(c);
		
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
	
	public void select(boolean select) {
		this.selected = select;
	}

}
