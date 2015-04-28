package Dir_Graphics;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JPanel;

public class JCanvas extends JPanel {
	private static final long serialVersionUID = -1351443492057511284L;
	
	private List<IDrawable> drawables = new LinkedList<IDrawable>();
	
	public void paint(Graphics g) {
		//Paint the goban	
		g.setColor(Color.ORANGE);
		g.fillRect(0,0,this.getWidth(),this.getHeight());
		
		g.setColor(Color.BLACK);
		for(int i=0;i<=this.getWidth();i+=100){
			g.drawLine(i, 0, i, this.getHeight());
		}
		for(int i=0;i<=this.getHeight();i+=100){
			g.drawLine(0, i, this.getWidth(), i);
		}
		
		//Paint the rocks
		for (Iterator<IDrawable> iter = drawables.iterator(); iter.hasNext();) {
			IDrawable d = (IDrawable) iter.next();
			d.draw(g);	
		}
	}

	public void addDrawable(IDrawable d) {
		drawables.add(d);
		repaint();
	}

	public void removeDrawable(IDrawable d) {
		drawables.remove(d);
		repaint();
	}

	public void clear() {
		drawables.clear();
		repaint();
	}
	
	
    public List<IDrawable> findDrawables(Point p) {
        List<IDrawable> l = new ArrayList<IDrawable>();
        for (Iterator<IDrawable> iter = drawables.iterator(); iter.hasNext();) {
            IDrawable element = (IDrawable) iter.next();
            if (element.getRectangle().contains(p)) {
                l.add(element);
            }
        }
        return l;
    }

    public boolean isFree(Rectangle rect) {
        for (Iterator<IDrawable> iter = drawables.iterator(); iter.hasNext();) {
            IDrawable element = (IDrawable) iter.next();
            //System.out.println(element.getRectangle());
            if (element.getRectangle().intersects(rect)) {
                return false;
            }
        }
        return true;
    }

	
}