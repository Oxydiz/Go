package graphics;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JPanel;

import entities.Rock;
import static main.Go_Game_UI.*;

public class JCanvas extends JPanel {
	private static final long serialVersionUID = -1351443492057511284L;

	private ArrayList<Rock> rocks;
	
	public JCanvas() {
		this.rocks = new ArrayList<Rock>();
	}
	
	@Override
	public void paint(Graphics g) {		
		//Paint the goban	
		g.setColor(Color.ORANGE);
		g.fillRect(0,0,this.getWidth(),this.getHeight());
		
		g.setColor(Color.BLACK);
		for(int i=0;i<=this.getWidth();i+=GRIDSIZE){
			g.drawLine(i, 0, i, this.getHeight());
		}
		for(int i=0;i<=this.getHeight();i+=GRIDSIZE){
			g.drawLine(0, i, this.getWidth(), i);
		}
		//Display the last line
		g.drawLine(0, this.getHeight()-1, this.getWidth(), this.getHeight()-1);
		
		for(int i = 0; i < rocks.size(); i++)
			rocks.get(i).draw(g);
	}
	
	public void addRock(Rock r) {
		rocks.add(r);
	}

	public void removeRock(Point p) {

        for(int i = 0; i < rocks.size(); i++)
        	if(rocks.get(i).getX() == p.x && rocks.get(i).getY() == p.y)
        		rocks.remove(i);
        
	}
    
    public boolean isFree(Rock r) {
        for(int i = 0; i < rocks.size(); i++)
            if (r.getX() == rocks.get(i).getX() && r.getY() == rocks.get(i).getY())
                return false;
        return true;
    }

	
}