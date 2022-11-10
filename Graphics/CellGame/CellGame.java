package p1110;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class CellGame {
	JFrame jFrame = new JFrame("Cell Game");
	int WIDTH = 1024, HEIGHT = 768;
	int x = 100, y = 100, radius = 5;
	int speed = 1;
	
	ArrayList<Jewel> jewels = new ArrayList<>();
	
	public CellGame() {
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setSize(WIDTH, HEIGHT);
		
		jFrame.getContentPane().add(new GamePanel());
		jFrame.addKeyListener(new GameKeyListener());
		
		jFrame.setVisible(true);
	}
	
	public void go() {
		for(int i = 0; i < 100; i++) jewels.add(new Jewel());
		
		while(true) {
			
			Iterator<Jewel> alba = jewels.iterator();
			while(alba.hasNext()) {
				Jewel jewel = alba.next();
				double distance = Math.sqrt((x - jewel.getX()) * (x - jewel.getX()) + (y - jewel.getY()) * (y - jewel.getY()));
				if (distance < radius) {
					radius++;
					speed++;
					alba.remove();
				}
			}
			try {
				Thread.sleep(speed);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			jFrame.repaint();
		}
	}
	
	class GamePanel extends JPanel{
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			for(Jewel jewel : jewels) {
				g.setColor(jewel.getColor());
				g.fillRect(jewel.getX(), jewel.getY(), jewel.getWidth(), jewel.getWidth());
			}
				
			g.setColor(Color.BLUE);
			g.fillOval(x, y, 2 * radius, 2 * radius);
		}
	}
	private class Jewel {
		private int x = 0;
		private int y = 0;
		private int width = 0;
		private Color color = null;
		
		
		public Jewel() {
			setX((int) (Math.random() * WIDTH));
			setY((int) (Math.random() * HEIGHT));
			setColor(new Color((int) (Math.random() * 256),(int) (Math.random() * 256),(int) (Math.random() * 256)));
			setWidth(4 + (int) (Math.random() * 6));
		}

		public int getX() {
			return x;
		}
		public void setX(int x) {
			this.x = x;
		}
		public int getY() {
			return y;
		}
		public void setY(int y) {
			this.y = y;
		}
		public int getWidth() {
			return width;
		}
		public void setWidth(int width) {
			this.width = width;
		}
		public Color getColor() {
			return color;
		}
		public void setColor(Color color) {
			this.color = color;
		} 
	}

	class GameKeyListener implements KeyListener{

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			switch (e.getKeyCode()) {
			case KeyEvent.VK_UP: 
				y-=5;
				break;
			case KeyEvent.VK_DOWN: 
				y+=5;
				break;
			case KeyEvent.VK_LEFT: 
				x-=5;
				break;
			case KeyEvent.VK_RIGHT: 
				x+=5;
				break;
			default:
				break;
			}
			jFrame.repaint();
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
