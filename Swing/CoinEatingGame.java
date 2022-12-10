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

import SwingProject.CoinEatingGame.Trap;

public class CoinEatingGame {
	private JFrame frame = new JFrame("Coin Eating Game");

	private int WIDTH = 1280, HEIGHT = 960;
	private int SPEED = 3;
	private int stepX = 0, stepY = 0;
	private boolean up = false, down = false, left = false, right = false;
    private Color background = new Color(204, 255, 255);
	
	private Image player = new ImageIcon("player.png").getImage();
	private Image point = new ImageIcon("point.png").getImage();
	private Image trap = new ImageIcon("trap.png").getImage();
	private Image life = new ImageIcon("life.png").getImage();
	private Image godState = new ImageIcon("godState.png").getImage();
	private Image deadState = new ImageIcon("deadState.png").getImage();
	private Image item_GodMod = new ImageIcon("item_GodMod.png").getImage();
	private Image item_Heal = new ImageIcon("item_Heal.png").getImage();

	private int playerX, playerY;
	private int playerWidth = 50, playerHeight = 50;
	private int pointX, pointY;
	private int pointWidth = 30, pointHeight = 30;
	private int trapWidth = 50, trapHeight = 50;
	private int item_GodModX, item_GodModY;
	private int item_GodModWidth = 50, item_GodModHeight = 30;
	private int item_HealX, item_HealY;
	private int item_HealWidth = 40, item_HealHeight = 40;
	private int score = 0;
	private int itemScore = 0;
	private int lifeCnt = 3;
	private int lifeWidth = 40, lifeHeight = 40;
	private float time = 0.0f;
	private final float GOD_TIME = 1.5f;
	private float godtime = GOD_TIME;
	private final float Item_God_Time = 4.0f;
	private float itemgodtime = Item_God_Time;
	private boolean hitGodMod = false;
	private boolean itemGodMod = false;
	private boolean itemDrop = false, healDrop = false, itemOn = false, healOn = false, itemRe = false, healRe = false;
	private boolean end = false;
	
	
	private LinkedList<Trap> traps = new LinkedList<>();
	
	private GamePanel gamePanel = new GamePanel();
	private Trap t = new Trap(0, 0);
	private Key move = new Key();
	private Crash crash = new Crash();
	
	public CoinEatingGame() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		frame.setSize(WIDTH, HEIGHT);
		frame.setResizable(false); 
		frame.setLocationRelativeTo(null);

		t.makeTraps();	
		
		frame.getContentPane().add(gamePanel);
		frame.setVisible(true);

		frame.addKeyListener(new Key());
	}
	
	public void go() {
		playerX = (WIDTH - playerWidth) / 2;
		playerY = (HEIGHT - playerHeight) / 2;
		
		pointX = (int) (Math.random() * (WIDTH - pointWidth * 3) + pointWidth);
		pointY = (int) (Math.random() * (HEIGHT - pointHeight * 3) + pointHeight);
		if (pointX < 220 && pointY < 160) {
			while(pointX >= 220 && pointY >= 160) {
				pointX = (int) (Math.random() * (WIDTH - pointWidth * 3) + pointWidth);
				pointY = (int) (Math.random() * (HEIGHT - pointHeight * 3) + pointHeight);
			}
		}
			
		while (true) {
			t.moveTraps();
			
			time += SPEED / 1000.0f;
			
			if(hitGodMod) {
				godtime -= SPEED / 1000.0f;
				if(godtime <= 0) {
					hitGodMod = false;
					godtime = GOD_TIME;
				}
			}
			
			if(itemGodMod) {
				itemgodtime -= SPEED / 1000.0f;
				if(itemgodtime <= 0) {
					itemGodMod = false;
					itemOn = false;
					itemgodtime = Item_God_Time;
				}
			}
			
			if(hitGodMod || itemGodMod) {
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
			move.playerMove();
			crash.pointCrashCheck();
			crash.itemCrashCheck();
			frame.repaint();
			
			if(lifeCnt == 0) {
				end = true;
				break;
			}
		}
	}
	
	class GamePanel extends JPanel {
		public void paintComponent(Graphics g) {	
			super.paintComponent(g);
			setBackground(background);
			
			g.setColor(Color.BLACK);
			g.setFont(new Font("Arial", Font.BOLD, 40));
			g.drawString("진행 시간 : " + (int) time + "초", 30, 50);

			g.setColor(Color.BLACK);
			g.setFont(new Font("Arial", Font.BOLD, 40));
			g.drawString("SCORE : " + score, 30, 100);
			
			for(int i=0; i<lifeCnt; i++) {
				g.drawImage(life, 30 + i * 55 , 120, lifeWidth, lifeHeight, this);
			}
			
			g.drawImage(point, pointX, pointY, pointWidth, pointHeight, this);
			
			if(lifeCnt != 0)
			g.drawImage((hitGodMod || itemGodMod) ? godState : player, playerX, playerY, playerWidth, playerHeight, this);
			else g.drawImage(deadState, playerX, playerY, playerWidth, playerHeight, this);		
			
			for(Trap t: traps) {
				g.drawImage(trap, t.getX(), t.getY(), trapWidth, trapHeight, this);
			}		
			
			if (itemDrop) {
				g.drawImage(item_GodMod, item_GodModX, item_GodModY, item_GodModWidth, item_GodModHeight, this);
			}
			if (healDrop) {
				g.drawImage(item_Heal, item_HealX, item_HealY, item_HealWidth, item_HealHeight, this);
			}
			
			if(end) {
				g.setFont(new Font("Serif", Font.BOLD, 100));
				g.setColor(Color.RED);
				g.drawString("GAME OVER", 320, 440);
				
				g.setFont(new Font("Serif", Font.BOLD, 60));
				g.setColor(Color.BLACK);
				g.drawString("최종 점수 : " + score + "점", 400, 520);
			}
		}
	}
	
	class Crash {
		
		private void pointCrashCheck() {
			if ((playerX + 30 >= pointX - 20 && playerX + 30 <= (pointX + pointWidth + 20))
					&& (playerY + 30 >= pointY && playerY + 30 <= (pointY + pointHeight + 20))) {
				score += 100;
				itemScore += 100;
				itemRe = false;
				pointX = (int) (Math.random() * (WIDTH - pointWidth * 3) + pointWidth);
				pointY = (int) (Math.random() * (HEIGHT - pointHeight * 3) + pointHeight);
				if (pointX < 220 && pointY < 160) {
					while(pointX >= 220 && pointY >= 160) {
						pointX = (int) (Math.random() * (WIDTH - pointWidth * 3) + pointWidth);
						pointY = (int) (Math.random() * (HEIGHT - pointHeight * 3) + pointHeight);
					}
				}
			}
		}
		
		private void itemCrashCheck() {
			if (score % 700 == 0 && score > 0 && !itemOn && !itemRe) {
				item_GodModX = (int)(Math.random() * (WIDTH - item_GodModWidth * 3) + item_GodModWidth);
				item_GodModY = (int)(Math.random() * (HEIGHT - item_GodModHeight * 3) + item_GodModHeight);
				itemDrop = true;                                     
				itemOn = true;
				if (item_GodModX < 220 && item_GodModY < 160) {
					while(item_GodModX >= 220 && item_GodModY >= 160) {
						item_GodModX = (int) (Math.random() * (WIDTH - item_GodModWidth * 3) + item_GodModWidth);
						item_GodModY = (int) (Math.random() * (HEIGHT - item_GodModHeight * 3) + item_GodModHeight);
					}
				}
			}
			
			if ((playerX + 30 >= item_GodModX && playerX + 30 <= (item_GodModX + item_GodModWidth + 20))
					&& (playerY + 30 >= item_GodModY && playerY + 30 <= (item_GodModY + item_GodModHeight + 20))) {
				itemDrop = false;
				itemGodMod = true;
				itemRe = true;
			}
			
			if (itemScore >= 1500 && lifeCnt < 3 && !healRe) {
				healDrop = true;
				healRe = true;
				item_HealX = (int)(Math.random() * (WIDTH - item_HealWidth * 3) + item_HealWidth);
				item_HealY = (int)(Math.random() * (HEIGHT - item_HealHeight * 3) + item_HealHeight);
				itemScore = 0;
				healOn = true;
				if (item_HealX < 220 && item_HealY < 160) {
					while(item_HealX >= 220 && item_HealY >= 160) {
						item_HealX = (int) (Math.random() * (WIDTH - item_HealWidth * 3) + item_HealWidth);
						item_HealY = (int) (Math.random() * (HEIGHT - item_HealHeight * 3) + item_HealHeight);
					}
				}
			}
			
			if ((playerX + 30 >= item_HealX && playerX + 30 <= (item_HealX + item_HealWidth + 20))
					&& (playerY + 30 >= item_HealY && playerY + 30 <= (item_HealY + item_HealHeight + 20))) {
				if (healOn) {
					healDrop = false;
					healRe = false;
					lifeCnt++;
				}
				healOn = false;		
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
		
		private void makeTraps() {
			traps.add(new Trap(0, 0));
			traps.add(new Trap(WIDTH - 50, 0));
			traps.add(new Trap(0, HEIGHT - 100));
			traps.add(new Trap(WIDTH - 50, HEIGHT - 100));
			
		}
		
		private void moveTraps() {
			for(Trap t: traps) {
				if(t.getX() <= 0) t.setStepX((int)(Math.random() * 3));
				else if(t.getX() >= WIDTH - 50) t.setStepX((int)(Math.random() * 3) * -1);
				if(t.getY() <= 0) t.setStepY((int)(Math.random() * 3));
				else if(t.getY() >= HEIGHT - 100) t.setStepY((int)(Math.random() * 3) * -1);
				t.setX(t.getX() + t.stepX);
				t.setY(t.getY() + t.stepY);
				
				if((playerX + 30 >= t.getX() && playerX + 30 <= t.getX() + 50 ) &&
						(playerY + 30 >= t.getY() && playerY + 30 <= t.getY() + 50)) {
					t.setCrash(true);
					if(!hitGodMod && !itemGodMod) {
						hitGodMod = true;
						lifeCnt--;
					}		
				}else {
				t.setCrash(false);
				}
				
			}
		}
	}
	
	class Key implements KeyListener {

		private void playerMove() {
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
			}
			else if (down) {
				stepX = 0;
				stepY = 1;
			}
			else if (left) {
				stepX = -1;
				stepY = 0;
			}
			else if (right) {
				stepX = 1;
				stepY = 0;
			}
			if(up == false & down == false & left == false & right == false) {
				stepX = 0;
				stepY = 0;
			}
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyPressed(KeyEvent e) {
			
				switch (e.getKeyCode()) {
				case KeyEvent.VK_UP:
					up = true;
					break;
				case KeyEvent.VK_DOWN:
					down = true;
					break;
				case KeyEvent.VK_LEFT:
					left = true;
					break;
				case KeyEvent.VK_RIGHT:
					right = true;
					break;
				default:
					break;
				}
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