package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;

import static main.Go_Game_UI.*;

public class InfluenceMap extends HashMap<Integer, Control>{
	private static final long serialVersionUID = 1L;
	
	public InfluenceMap() {
		super();
		
		for(int i = 0; i < GOBANSIZE; i++)
			for(int j = 0; j < GOBANSIZE; j++)
				this.put(i + j * GOBANSIZE, Control.NONE);
	}
	
	public void placeRock(Rock r) {
		this.replace(r.getPosition(), r.getColor().equals(Color.BLACK) ? Control.BLACK : Control.WHITE);
	}
	
	public boolean isFree(int position) {
		
		if(this.containsKey(position))
			return this.get(position) == Control.NONE;
		
		return false;
		
	}
	
	public void set(int position,Color c) {
		
		if(this.containsKey(position))
			this.replace(position, c.equals(Color.BLACK) ? Control.BLACK : Control.WHITE);
		else
			this.put(position, c.equals(Color.BLACK) ? Control.BLACK : Control.WHITE);
		
	}

	public void overlay(Graphics g) {
	
		Color c = g.getColor();
		for(Entry<Integer,Control> e : this.entrySet()) {
			switch(e.getValue()) {
			case NONE :
					g.setColor(Color.GREEN);
					break;
			case BLACK :
				g.setColor(Color.BLUE);
				break;
			case WHITE :
				g.setColor(Color.YELLOW);
				break;
			}
			
			g.fillOval((e.getKey() % GOBANSIZE) * GRIDSIZE - ROCKSIZE / 4,(e.getKey() / GOBANSIZE) * GRIDSIZE - ROCKSIZE / 4, ROCKSIZE / 2, ROCKSIZE / 2);
		}
		
		g.setColor(c);
		
	}

}
