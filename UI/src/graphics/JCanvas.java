package graphics;

import static main.Go_Game_UI.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import listeners.PlayerListener;
import entities.AI;
import entities.AI.AILevel;
import entities.Rock;
import entities.Shape;

public class JCanvas extends JPanel {
	private static final long serialVersionUID = -1351443492057511284L;
	
	private ArrayList<Shape> shapes;
	private ArrayList<Integer> free;
	private ArrayList<Integer> ko;
	private JLabel current_player_text;
	private AI ai;

	public JCanvas(AILevel level) {
		this.shapes = new ArrayList<Shape>();
		this.free = new ArrayList<Integer>();
		this.ko = new ArrayList<Integer>();
		try {
			this.ai = new AI(this, level);
		} catch (Exception e) {
			this.ai = null;
		}
		
		for (int i = 0; i < GOBANSIZE; i++)
			for (int j = 0; j < GOBANSIZE; j++)
				free.add(j + i * GOBANSIZE);
	}
	
	public void initAI() {
		ai.init();
	}

	@Override
	public void paint(Graphics g) {
		// Paint the goban
		/* Some interesting color 
		g.setColor(new Color(126,82,58));
		g.setColor(new Color(214,138,103));
		g.setColor(new Color(150,113,23));
		g.setColor(new Color(195,176,125));
		*/
		g.setColor(new Color(203,160,0));
		
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

		g.setColor(new Color(32,32,32));
		for (int i = 0; i <= this.getWidth(); i += GRIDSIZE) {
			g.drawLine(i, 0, i, this.getHeight());
			g.drawLine(i+1, 0, i+1, this.getHeight());
		}
		for (int i = 0; i <= this.getHeight(); i += GRIDSIZE) {
			g.drawLine(0, i, this.getWidth(), i);
			g.drawLine(0, i+1, this.getWidth(), i+1);
		}
		// Display the last line
		g.drawLine(0, this.getHeight() - 1, this.getWidth(), this.getHeight() - 1);
		g.drawLine(0, this.getHeight() - 2, this.getWidth(), this.getHeight() - 2);

		for (int i = 0; i < shapes.size(); i++)
			shapes.get(i).draw(g);
	}

	private boolean captureEnemy(Rock r) {

		boolean c, re = false;

		for (int i = 0; i < shapes.size(); i++) {

			c = true;

			if (shapes.get(i).getColor() == r.getColor())
				continue;

			for (int j = 0; j < shapes.get(i).size(); j++)
				if (freeSpace(shapes.get(i).get(j)) > 0) {
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
		
		return re;

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
	
	private void ko(Rock r) {
		
		int position = isFree(r, 1, 0) ? r.getPosition() + 1 : isFree(r, -1, 0) ? r.getPosition() - 1 :
				isFree(r, 0, 1) ? r.getPosition() + GOBANSIZE : r.getPosition() - GOBANSIZE;
		
		ko.add(position);
		
	}
	
	public void addRock(Rock r) {
		
		unselect();
		ko = new ArrayList<Integer>();
		fuseShapes(r);
		free.remove((Integer) (r.getX() + r.getY() * GOBANSIZE));
		boolean c = captureEnemy(r);
		if(c && freeSpace(r) == 1) {
			ko(r);
		}
		
		//Change player
		PlayerListener.switchColor();
		if(current_player_text.getText() == PLAYERTURN){
			current_player_text.setText(IATURN);
			
			repaint();
			
			Timer t = new Timer();
			t.schedule(new TimerTask() {
				
				@Override
				public void run() {
					ai.play();
				}
			}, 600);
		}
		else{
			current_player_text.setText(PLAYERTURN);
			
			repaint();
		}
		current_player_text.setHorizontalAlignment(SwingConstants.CENTER);			
	}

	public void removeRock(Rock r, boolean repaint) {
		free.add(r.getPosition());
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
	
	public int freeSpace(Rock r) {
		int f = isFree(r, 1, 0) ? 1 : 0;
		f += isFree(r, -1, 0) ? 1 : 0;
		f += isFree(r, 0, 1) ? 1 : 0;
		f += isFree(r, 0, -1) ? 1 : 0;
		return f;
	}

	public boolean isFree(Point p) {
		
		if(p.x < 0 || p.x >= GOBANSIZE || p.y < 0 || p.y >= GOBANSIZE)
			return false;
		
		return free.contains(p.x + p.y * GOBANSIZE) && ! ko.contains(p.x + p.y * GOBANSIZE);
	}

	public boolean isFree(Rock r) {
		return isFree(r, 0, 0);
	}

	public boolean isFree(Rock r, int x, int y) {
		
		if(r.getX() + x < 0 || r.getX() + x >= GOBANSIZE || r.getY() + y < 0 || r.getY() + y >= GOBANSIZE)
			return false;
		
		return free.contains(r.getPosition() + x + (y * GOBANSIZE)) && !ko.contains(r.getPosition() + x + (y * GOBANSIZE));
	}
	
	public boolean immediateDeath(Point p, Color c) {

		Rock r = new Rock(p);
		
		int f = 0;
		
		if(p.x < GOBANSIZE - 1)
			f += !isFree(r, 1, 0) ? getRock(r, 1, 0).getColor().equals(c) ? 1 : 0 : 1;
		if(p.x > 0)
			f += !isFree(r, -1, 0) ? getRock(r, -1, 0).getColor().equals(c) ? 1 : 0 : 1;
		if(p.y < GOBANSIZE - 1)
			f += !isFree(r, 0, 1) ? getRock(r, 0, 1).getColor().equals(c) ? 1 : 0 : 1;
		if(p.y > 0)
			f += !isFree(r, 0, -1) ? getRock(r, 0, -1).getColor().equals(c) ? 1 : 0 : 1;

		return f == 0;

	}

	public Rock getRock(Rock r, int x, int y) {

		for (int i = 0; i < shapes.size(); i++) {

			Rock o = shapes.get(i).get(new Point(r.getX() + x, r.getY() + y));

			if (o != null)
				return o;
		}

		return null;
	}
	
	public Shape getShape(Point p) {
		if(isFree(p))
			return null;
		else
			for(int i = 0; i < shapes.size();i++)
				if(shapes.get(i).get(p) != null)
					return shapes.get(i);
		
		return null;
	}
	
	public JLabel getCurrent_player_text() {
		return current_player_text;
	}

	public void setCurrent_player_text(JLabel current_player_text) {
		this.current_player_text = current_player_text;
	}
	
	public void setCurrent_player_text(String current_player_text) {
		this.current_player_text.setText(current_player_text);
	}
	
	public ArrayList<Integer> getFree() {
		return free;
	}
	
	public void unselect() {
		for(int i = 0; i < shapes.size();i++) {
			shapes.get(i).select(false);
		}
	}

}