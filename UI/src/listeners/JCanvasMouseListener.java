package listeners;

import graphics.JCanvas;

import java.awt.event.*;

import javax.swing.SwingUtilities;

public abstract class JCanvasMouseListener extends MouseAdapter {

	protected JCanvas canvas;

	public JCanvasMouseListener(JCanvas canvas) {
		super();
		this.canvas = canvas;
		canvas.addMouseListener(this);
	}
		
	public JCanvas getCanvas() {
		return canvas;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (SwingUtilities.isLeftMouseButton(e)) {
			leftClickAction(e);
		} else {
			rightClickAction(e);
		}
	}

	
	protected void rightClickAction(MouseEvent e) {
		canvas.repaint();
	}

	protected void leftClickAction(MouseEvent e) {

		canvas.repaint();
	}
	
}