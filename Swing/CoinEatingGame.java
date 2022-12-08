package SwingProject;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class CoinEatingGame {
	JFrame frame = new JFrame("Coin Eating Game");

	JPanel gamePanel = new GamePanel();

	int WIDTH = 1280, HEIGHT = 960;
	int x = 100, y = 480;
	int  SPEED = 3;
	int stepX = 0, stepY = 0;
	boolean up = false, down = false, left = false, right = false;
	boolean crashCheck = false;
    Color background = new Color(204, 255, 255);
	
	private Image player = new ImageIcon("player.png").getImage();
	private Image point = new ImageIcon("point.png").getImage();
	private Image trap = new ImageIcon("trap.png").getImage();
	private Image life = new ImageIcon("life.png").getImage();
	private Image godState = new ImageIcon("godState.png").getImage();
	private Image deadState = new ImageIcon("deadState.png").getImage();

	private int playerX, playerY;
	private int playerWidth = 50, playerHeight = 50;
	private int pointX, pointY;
	private int pointWidth = 30, pointHeight = 30;
	private int trapWidth = 50, trapHeight = 50;
	private int score;
	private int lifeCnt = 3;
	private int lifeWidth = 40, lifeHeight = 40;
	private final float GOD_TIME = 1.5f;
	private float godtime = GOD_TIME;
	private boolean godMod = false;
	private boolean end = false;
	
	LinkedList<Trap> traps = new LinkedList<>();
	
	public CoinEatingGame() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(WIDTH, HEIGHT);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);

		makeTraps();
		
		frame.getContentPane().add(gamePanel);
		frame.setVisible(true);

		frame.addKeyListener(new Key());
	}
	
	public void go() {
		playerX = (WIDTH - playerWidth) / 2;
		playerY = (HEIGHT - playerHeight) / 2;
		
		pointX = (int) (Math.random() * (WIDTH - pointWidth * 2) - pointWidth);
		pointY = (int) (Math.random() * (HEIGHT - pointHeight * 2) - pointHeight);
		if (pointX < 220 && pointY < 115) {
			pointX = (int) (Math.random() * (WIDTH - pointWidth * 2) - pointWidth);
			pointY = (int) (Math.random() * (HEIGHT - pointHeight * 2) - pointHeight);
		}
		while (true) {
			moveTraps();
			
			if(godMod) {
				godtime -= SPEED / 1000.0f;
				if(godtime <= 0) {
					godMod = false;
					godtime = GOD_TIME;
				}
			}
			if(godMod) {
				playerX += stepX * 2;
				playerY += stepY * 2;
			}else {
				playerX += stepX;
				playerY += stepY;
			}
			
			try {
				Thread.sleep(SPEED);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			keyProcess();
			crashCheck();
			frame.repaint();
			
			if(lifeCnt == 0) {
				end = true;
				break;
			}
		}
	}
	
	public void crashCheck() {
		if ((playerX + 30 >= pointX - 20 && playerX + 30 <= (pointX + pointWidth + 20))
				&& (playerY + 30 >= pointY && playerY + 30 <= (pointY + pointHeight + 20))) {
			score += 100;
			pointX = (int) (Math.random() * (WIDTH - playerWidth));
			pointY = (int) (Math.random() * (HEIGHT - playerHeight - 30)) + 30;
		}
	}
	
	class GamePanel extends JPanel {
		public void paintComponent(Graphics g) {	
			super.paintComponent(g);
			setBackground(background);
			g.drawImage(point, pointX, pointY, pointWidth, pointHeight, this);
			if(lifeCnt != 0)
			g.drawImage((godMod) ? godState : player, playerX, playerY, playerWidth, playerHeight, this);
			else g.drawImage(deadState, playerX, playerY, playerWidth, playerHeight, this);
			g.setColor(Color.BLACK);
			g.setFont(new Font("Arial", Font.BOLD, 40));
			g.drawString("SCORE : " + score, 30, 50);		
			
			for(Trap t: traps) {
				g.drawImage(trap, t.getX(), t.getY(), trapWidth, trapHeight, this);
			}
			
			for(int i=0; i<lifeCnt; i++) {
				g.drawImage(life, 30 + i * 55 , 70, lifeWidth, lifeHeight, this);
			}
			
			if(end) {
				g.setFont(new Font("Serif", Font.BOLD, 100));
				g.setColor(Color.red);
				g.drawString("GAME OVER", 320, 450);
			}
		}
	}
	
	class Trap {
		int x, y;
		int stepX = 0, stepY = 0;
		boolean crash =false;

		public Trap(int x, int y) {
			this.x = x;
			this.y = y;
			setStepX();
			setStepY();
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

		public int getStepX() {
			return stepX;
		}

		public void setStepX() {
			this.stepX = (int) (Math.random() * 3) - 1;
		}
		
		public void setStepX(int stepX) {
			this.stepX = stepX;
		}

		public int getStepY() {
			return stepY;
		}

		public void setStepY() {
			this.stepY = (int) (Math.random() * 3) - 1;
		}
		
		public void setStepY(int stepY) {
			this.stepY = stepY;
		}
		
		public boolean getCrash() {
			return crash;
		}

		public void setCrash(boolean crash) {
			this.crash = crash;
		}
	}

	public void makeTraps() {
		traps.add(new Trap(0, 0));
		traps.add(new Trap(WIDTH - 50, 0));
		traps.add(new Trap(0, HEIGHT - 100));
		traps.add(new Trap(WIDTH - 50, HEIGHT - 100));
		
	}
	
	public void moveTraps() {
		for(Trap t: traps) {
			if(t.getX() <= 0) t.setStepX((int)(Math.random() * 3));
			else if(t.getX() >= WIDTH - 50) t.setStepX(-2);
			if(t.getY() <= 0) t.setStepY((int)(Math.random() * 3));
			else if(t.getY() >= HEIGHT - 100) t.setStepY(-2);
			t.setX(t.getX() + t.stepX);
			t.setY(t.getY() + t.stepY);
				
			if((playerX + 30 >= t.getX() && playerX + 30 <= t.getX() + 50 ) &&
					(playerY + 30 >= t.getY() && playerY + 30 <= t.getY() + 50))
			{
				t.setCrash(true);
				if(!godMod) {
					godMod = true;
					lifeCnt--;
				}		
			}
			else 
			{
			t.setCrash(false);
			}
		}
	}
	
	public void keyProcess() {
		if (playerX < 0) left = false;
		if (playerX > 1260) right = false;
		if (playerY < 0) up = false;
		if (playerY > 900) down = false;
			
		if (up && left) {
			stepX = -1;
			stepY = -1;
		} else if (up && right) {
			stepX = 1;
			stepY = -1;
		} else if (down && left) {
			stepX = -1;
			stepY = 1;
		} else if (down && right) {
			stepX = 1;
			stepY = 1;
		} else if (up) {
			stepX = 0;
			stepY = -1;
		} else if (down) {
			stepX = 0;
			stepY = 1;
		} else if (left) {
			stepX = -1;
			stepY = 0;
		} else if (right) {
			stepX = 1;
			stepY = 0;
		}
		if(up == false & down == false & left == false & right == false) {
			stepX = 0;
			stepY = 0;
		}
	}
	
	class Key implements KeyListener {

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyPressed(KeyEvent e) {
			
				switch (e.getKeyCode()) {
				case KeyEvent.VK_UP:
					if(playerY > 0)
					up = true;
					break;
				case KeyEvent.VK_DOWN:
					if(playerY < 960)
					down = true;
					break;
				case KeyEvent.VK_LEFT:
					if(playerX > 0)
					left = true;
					break;
				case KeyEvent.VK_RIGHT:
					if(playerX < 1280)
					right = true;
					break;
				default:
					break;
				}

				frame.repaint();
			}

		@Override
		public void keyReleased(KeyEvent e) {
			
				switch (e.getKeyCode()) {
				case KeyEvent.VK_UP:
					up = false;
					break;
				case KeyEvent.VK_DOWN:
					down = false;
					break;
				case KeyEvent.VK_LEFT:
					left = false;
					break;
				case KeyEvent.VK_RIGHT:
					right = false;
					break;
				default:
					break;
				}
			}
	}
}
