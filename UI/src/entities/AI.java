package entities;

import static main.Go_Game_UI.*;
import graphics.JCanvas;

import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import listeners.PlayerListener;

public class AI {

	public enum AILevel {
		NONE,RANDOM, EASY, MEDIUM, HARD;
	}

	private AILevel level;
	private JCanvas canvas;
	private PlayerListener listener;

	public AI(JCanvas goban, AILevel level) throws Exception {

		this.canvas = goban;
		this.level = level;
		if (level == null)
			throw new Exception();

	}

	public void init() {

		MouseListener m[] = canvas.getMouseListeners();

		if (m.length == 0) {
			System.err.println("Could not access listeners in AI initialization");
			System.exit(-1);
		} else if (m[0].getClass().equals(PlayerListener.class)) {
			this.listener = (PlayerListener) m[0];
		} else {
			System.err.println("Invalid listener in AI initialization");
			System.exit(-1);
		}

	}

	public void play() {

		switch (level) {

		case RANDOM:
			playRandom();
			break;

		case EASY:
		case MEDIUM:
		case HARD:
		default:
			break;

		}

	}

	private void placeRock(Point p) {

		listener.mouseClicked(new MouseEvent(new Component() {
			private static final long serialVersionUID = 1L;
		}, MouseEvent.MOUSE_CLICKED, System.currentTimeMillis(), 0, p.x * GRIDSIZE, p.y * GRIDSIZE, 1, false, MouseEvent.BUTTON1));

	}

	public void playRandom() {

		ArrayList<Integer> free = canvas.getFree();
		
		if(free.size() == 0)
			return;

		boolean played = false;
		Color c = listener.getColor();
		int r = (int) ((Math.random() * GOBANSIZE * GOBANSIZE)) % free.size();

		while (!played) {

			Point p = new Point(r % GOBANSIZE, r / GOBANSIZE);

			if (canvas.isFree(p)) {

				if (!canvas.immediateDeath(p, c)) {
					placeRock(p);
					played = true;
				}

			}

			r++;
			r %= free.size();

		}

	}

}
