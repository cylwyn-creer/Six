package six.object;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import six.gui.Menu;
import six.gui.Sound;
import six.gui.Winner;

public class Board1 extends JPanel implements Runnable {

	private static final long serialVersionUID = 42L;
	
	private Tile[] redTile = new Tile[19];
	private Tile[] blackTile = new Tile[19];
	private Tile picked = null;
	
	private BufferedImage[] target = new BufferedImage[2];
	private int targetX = 0;
	private int targetY = 0;
	private boolean project = true;
	
	private boolean maximize = false;
	
	private boolean gameOver = false;
	
	private boolean pick = false;
	private boolean justStarted = true;
	
	private int turn = 1;
	
	private int redTiles = 19;
	private int blackTiles = 19;
	
	private Point bestMove;
	
	private MouseEventHandler mouseHandler;
	private MouseMotionEventHandler mouseMotionHandler;
	
	public static Thread thread;
	public boolean running = false;
	
	private Toolkit toolkit;
	private Image[] cursorImage = new Image[4];
	private Cursor[] cursor = new Cursor[4];
	
	private Board1 currentBoard;
	private Sound sound;
	
	private Queue<Tile> moves = new LinkedList<Tile>();
	
	public Board1() {
	
		initUI();
		initComponents();
		createTiles();
		
	}
	
	public int getTurn() {
		
		return turn;
		
	}
	
	public void run() {
		
		while(running) {
			
			repaint();
			
			if(tilesNoMore()) {
				
				Winner win = new Winner();
				Winner.winner = 2;
				win.broadCastWinner();
				win.setModal(true);
				win.setVisible(true);
				
				gameOver = false;
				running = false;
				
			}
			
			if(gameOver) {
				
				Winner win = new Winner();
				
				if(turn == 1)
					Winner.winner = 1;
				else
					Winner.winner = 0;
				
				win.broadCastWinner();
				win.setModal(true);
				win.setVisible(true);
				
				gameOver = false;
				running = false;
				
			}
			
			if(maximize && turn == 2) {
				
				//sound.playClick();
				
				bestMove = null;
				
				int maxDepth = 1;
				//int maxDepth = redTiles + blackTiles;
				/*
				if(maxDepth > 1) {
					
					maxDepth = 1;
					
				}
				*/
				Menu.label[9].setIcon(Menu.loadIcon);
				alphaBeta(currentBoard, 2, maxDepth, Integer.MIN_VALUE, Integer.MAX_VALUE);
				tileOnPlace(bestMove.x, bestMove.y);
				
				maximize = false;
				Menu.label[9].setIcon(new ImageIcon("src/images/comp1.png"));
				Menu.label[9].repaint();
				Menu.label[9].revalidate();
				revalidate();
				repaint();
				Menu.panel.revalidate();
				Menu.panel.repaint();
				
			}
			
		}
		
	}
	
	public void start() {
		
		if(running) {
			
			return;
			
		}
		
		running = true;
		thread = new Thread(this);
		thread.start();
		
	}
	
	public void initUI() {
		
		setBounds(0, 0, 1188, 558);
		setLayout(null);
		
	}
	
	public void initComponents() {
		
		sound = new Sound();
		
		createCursor();
		createTarget();
		
		mouseHandler = new MouseEventHandler();
		addMouseListener(mouseHandler);
		
		mouseMotionHandler = new MouseMotionEventHandler();
		addMouseMotionListener(mouseMotionHandler);
		
		currentBoard = this;
	}
	
	public void createCursor() {
		
		toolkit = Toolkit.getDefaultToolkit();
		
		try {
			
			cursorImage[0] = ImageIO.read(new File("src/six/res/image/cursor/red.png"));
			cursorImage[1] = ImageIO.read(new File("src/six/res/image/cursor/black.png"));
			
		}
		catch(Exception e) {
			
			System.out.println("Problem loading tile image!");
			
		}
		
		cursor[0] = toolkit.createCustomCursor(cursorImage[0], new Point(16, 16), "Red");
		cursor[1] = toolkit.createCustomCursor(cursorImage[1], new Point(16, 16), "Black");
		
		setCursor(cursor[0]);
	}
	
	public void createTiles() {
		
		for(int a = 0; a < 19; a++) {
			
			redTile[a] = new Tile(0);
			blackTile[a] = new Tile(1);
			
		}
		
		redTile[18].moveTo(540, 252);
		redTile[18].setTilePlacing(true);
		moves.offer(redTile[18]);
		blackTile[18].moveTo(594, 252);
		blackTile[18].setTilePlacing(true);
		moves.offer(blackTile[18]);
		setAdjacentTiles(redTile[18]);
		setAdjacentTiles(blackTile[18]);
		
		redTiles--;
		blackTiles--;
		
		picked = redTile[redTiles - 1];
		
	}
	
	public void createTarget() {
		
		try {
			
			target[0] = ImageIO.read(new File("src/six/res/image/tiles/target/red.png"));
			target[1] = ImageIO.read(new File("src/six/res/image/tiles/target/black.png"));
			
		}
		catch(Exception e) {
			
			System.out.println("Problem loading tile image!");
			
		}
		
	}
	
	public void tileOnPlace(int x, int y) {
		
		int xOffset = (x / 27) * 27;
		int yOffset = (y / 42) * 42;
		
		if(yOffset % 84 == 0) {
			
			if(xOffset % 54 != 0) {
				
				xOffset -= 27;
				
			}
			
		}
		else {
			
			if(xOffset % 27 != 0) {
				
				xOffset -= 27;
				
			}
			
			if(xOffset % 54 == 0) {
				
				xOffset -= 27;
				
			}
			
		}
		
		if(isOccupied(xOffset, yOffset) == false && isValidLocation(xOffset, yOffset)) {
			
			if(justStarted) {
				
				justStarted = false;
				
			}
		
			picked.moveTo(xOffset, yOffset);
			picked.setTileState(true);
			moves.offer(picked);
			
			picked.setTilePlacing(true);
				
			if(picked.getType() == 0) {
					
				redTiles--;
					
			}
			else {
					
				blackTiles--;
					
			}
			
			setAdjacentTiles(picked);
			gameOver = isGameOver(picked);
			
			if(turn == 2 && redTiles > 0) {
					
				picked = redTile[redTiles - 1];
				setCursor(cursor[0]);
				turn = 1;
					
			}
			else if(turn == 1 && blackTiles > 0) {
					
				picked = blackTile[blackTiles - 1];
				setCursor(cursor[1]);
				turn = 2;
					
			}
		}
		
		maximize = true;
		
	}
	
	public boolean isOccupied(int x, int y) {
		
		for(int a = 0; a < 19; a++) {
			
			if(redTile[a].getX() == x && redTile[a].getY() == y) {
			
				return true;
				
			}
			
			if(blackTile[a].getX() == x && blackTile[a].getY() == y) {
				
				return true;
				
			}
			
		}
		
		return false; 
		
	}
	
	public void projectTarget(int x, int y) {
		
		int xOffset = (x / 27) * 27;
		int yOffset = (y / 42) * 42;
		
		if(yOffset % 84 == 0) {
			
			if(xOffset % 54 != 0) {
				
				xOffset -= 27;
				
			}
			
		}
		else {
			
			if(xOffset % 27 != 0) {
				
				xOffset -= 27;
				
			}
			
			if(xOffset % 54 == 0) {
				
				xOffset -= 27;
				
			}
			
		}
		
		if(isOccupied(xOffset, yOffset) == false) {
		
			targetX = xOffset;
			targetY = yOffset;
			
			project = true;
			
		}
		
	}
	
	public boolean isValidLocation(int x, int y) {
		
		boolean result = false;
		
		Tile tempTile = new Tile(picked.getType());
		tempTile.moveTo(x, y);
		
		setTemporaryAdjacentTiles(tempTile);
		
		for(int a = 0; a < 6; a++) {
			
			if(tempTile.getAdjacentTile(a) != null && !picked.equals(tempTile.getAdjacentTile(a))) {
					
				if(tempTile.getAdjacentTile(a).getTilePlacing() == true) {
						
					if(justStarted) {
							
						if(tempTile.getAdjacentTile(a).getType() == tempTile.getType()) {
								
							result = false;
							break;
								
						}
							
					}
						
					result = true;
					break;
						
				}
				
			}
			
		}
		
		return result;
		
	}
	
	public void setAdjacentTiles(Tile centerTile) {
		
		centerTile.refreshAdjacentTiles();
		
		int x = centerTile.getX();
		int y = centerTile.getY();
		
		for(int a = 0; a < 19; a++) {
			
			if(redTile[a].getX() == x - 27 && redTile[a].getY() == y - 42) {
				
				centerTile.setAdjacentTile(0, redTile[a]);
				centerTile.updateNumberOfAdjacentTiles(1);
				redTile[a].setAdjacentTile(3, centerTile);
				redTile[a].updateNumberOfAdjacentTiles(1);
				
				
			}
			else if(redTile[a].getX() == x - 54 && redTile[a].getY() == y) {
				
				centerTile.setAdjacentTile(1, redTile[a]);
				centerTile.updateNumberOfAdjacentTiles(1);
				redTile[a].setAdjacentTile(4, centerTile);
				redTile[a].updateNumberOfAdjacentTiles(1);
				
			}
			else if(redTile[a].getX() == x - 27 && redTile[a].getY() == y + 42) {
				
				centerTile.setAdjacentTile(2, redTile[a]);
				centerTile.updateNumberOfAdjacentTiles(1);
				redTile[a].setAdjacentTile(5, centerTile);
				redTile[a].updateNumberOfAdjacentTiles(1);
				
			}
			else if(redTile[a].getX() == x + 27 && redTile[a].getY() == y + 42) {
				
				centerTile.setAdjacentTile(3, redTile[a]);
				centerTile.updateNumberOfAdjacentTiles(1);
				redTile[a].setAdjacentTile(0, centerTile);
				redTile[a].updateNumberOfAdjacentTiles(1);
				
			}
			else if(redTile[a].getX() == x + 54 && redTile[a].getY() == y) {
				
				centerTile.setAdjacentTile(4, redTile[a]);
				centerTile.updateNumberOfAdjacentTiles(1);
				redTile[a].setAdjacentTile(1, centerTile);
				redTile[a].updateNumberOfAdjacentTiles(1);
				
			}
			else if(redTile[a].getX() == x + 27 && redTile[a].getY() == y - 42) {
				
				centerTile.setAdjacentTile(5, redTile[a]);
				centerTile.updateNumberOfAdjacentTiles(1);
				redTile[a].setAdjacentTile(2, centerTile);
				redTile[a].updateNumberOfAdjacentTiles(1);
				
			}
			
			if(blackTile[a].getX() == x - 27 && blackTile[a].getY() == y - 42) {
				
				centerTile.setAdjacentTile(0, blackTile[a]);
				centerTile.updateNumberOfAdjacentTiles(1);
				blackTile[a].setAdjacentTile(3, centerTile);
				blackTile[a].updateNumberOfAdjacentTiles(1);
				
			}
			else if(blackTile[a].getX() == x - 54 && blackTile[a].getY() == y) {
				
				centerTile.setAdjacentTile(1, blackTile[a]);
				centerTile.updateNumberOfAdjacentTiles(1);
				blackTile[a].setAdjacentTile(4, centerTile);
				blackTile[a].updateNumberOfAdjacentTiles(1);
				
			}
			else if(blackTile[a].getX() == x - 27 && blackTile[a].getY() == y + 42) {
				
				centerTile.setAdjacentTile(2, blackTile[a]);
				centerTile.updateNumberOfAdjacentTiles(1);
				blackTile[a].setAdjacentTile(5, centerTile);
				blackTile[a].updateNumberOfAdjacentTiles(1);
				
			}
			else if(blackTile[a].getX() == x + 27 && blackTile[a].getY() == y + 42) {
				
				centerTile.setAdjacentTile(3, blackTile[a]);
				centerTile.updateNumberOfAdjacentTiles(1);
				blackTile[a].setAdjacentTile(0, centerTile);
				blackTile[a].updateNumberOfAdjacentTiles(1);
				
			}
			else if(blackTile[a].getX() == x + 54 && blackTile[a].getY() == y) {
				
				centerTile.setAdjacentTile(4, blackTile[a]);
				centerTile.updateNumberOfAdjacentTiles(1);
				blackTile[a].setAdjacentTile(1, centerTile);
				blackTile[a].updateNumberOfAdjacentTiles(1);
				
			}
			else if(blackTile[a].getX() == x + 27 && blackTile[a].getY() == y - 42) {
				
				centerTile.setAdjacentTile(5, blackTile[a]);
				centerTile.updateNumberOfAdjacentTiles(1);
				blackTile[a].setAdjacentTile(2, centerTile);
				blackTile[a].updateNumberOfAdjacentTiles(1);
				
			}
			
		}
		
	}
	
	public void refreshAdjacentTiles() {
		
		for(int a = 0; a < 6; a++) {
			
			if(picked.getAdjacentTile(a) != null) {
				
				picked.getAdjacentTile(a).setAdjacentTile((a + 3) % 6, null);
				picked.getAdjacentTile(a).updateNumberOfAdjacentTiles(-1);
				
			}
			
		}
		
	}
	
	public void setTemporaryAdjacentTiles(Tile centerTile) {
		
		int x = centerTile.getX();
		int y = centerTile.getY();
		
		for(int a = 0; a < 19; a++) {
			
			if(redTile[a].getX() == x - 27 && redTile[a].getY() == y - 42) {
				
				centerTile.setAdjacentTile(0, redTile[a]);
				
			}
			else if(redTile[a].getX() == x - 54 && redTile[a].getY() == y) {
				
				centerTile.setAdjacentTile(1, redTile[a]);
				
			}
			else if(redTile[a].getX() == x - 27 && redTile[a].getY() == y + 42) {
				
				centerTile.setAdjacentTile(2, redTile[a]);
				
			}
			else if(redTile[a].getX() == x + 27 && redTile[a].getY() == y + 42) {
				
				centerTile.setAdjacentTile(3, redTile[a]);
				
			}
			else if(redTile[a].getX() == x + 54 && redTile[a].getY() == y) {
				
				centerTile.setAdjacentTile(4, redTile[a]);
				
			}
			else if(redTile[a].getX() == x + 27 && redTile[a].getY() == y - 42) {
				
				centerTile.setAdjacentTile(5, redTile[a]);
				
			}
			
			if(blackTile[a].getX() == x - 27 && blackTile[a].getY() == y - 42) {
				
				centerTile.setAdjacentTile(0, blackTile[a]);
				
			}
			else if(blackTile[a].getX() == x - 54 && blackTile[a].getY() == y) {
				
				centerTile.setAdjacentTile(1, blackTile[a]);
				
			}
			else if(blackTile[a].getX() == x - 27 && blackTile[a].getY() == y + 42) {
				
				centerTile.setAdjacentTile(2, blackTile[a]);
				
			}
			else if(blackTile[a].getX() == x + 27 && blackTile[a].getY() == y + 42) {
				
				centerTile.setAdjacentTile(3, blackTile[a]);
				
			}
			else if(blackTile[a].getX() == x + 54 && blackTile[a].getY() == y) {
				
				centerTile.setAdjacentTile(4, blackTile[a]);
				
			}
			else if(blackTile[a].getX() == x + 27 && blackTile[a].getY() == y - 42) {
				
				centerTile.setAdjacentTile(5, blackTile[a]);
				
			}
			
		}
		
	}
	
	public boolean checkLinear(Tile startTile) {
		
		for(int a = 0; a < 6; a++) {
		
			//insert winner notification
			if((1 + startTile.traverseLinearly(a, startTile.getType())) >= 6) {
				
				return true;
				
			}
			
		}
		
		return false;
		
	}
	
	public boolean checkCircular(Tile startTile) {
		
		for(int a = 0; a < 6; a++) {
			
			//insert winner notification
			if((1 + startTile.traverseCircularly(a, startTile.getType(), 0)) == 6) {
				
				return true;
				
			}
			
		}
		
		return false;
		
	}
	
	public boolean checkTriangular(Tile startTile) {
		
		for(int a = 0; a < 6; a++) {
			
			//insert winner notification
			if((1 + startTile.traverseTriangularly(a, startTile.getType())) == 6) {
				
				return true;
				
			}
			
		}
		
		return false;
		
	}
	
	public boolean tilesNoMore() {
		
		if(redTiles == 0 && blackTiles == 0) {
			
			return true;
			
		}
		else {
			
			return false;
			
		}
		
	}
	
	public boolean isGameOver(Tile startTile) {
		
		return checkLinear(startTile) || checkCircular(startTile) || checkTriangular(startTile);
		
	}
	
	public void paintComponent(Graphics g) {
		
		Image bgImage =  new ImageIcon("src/images/wooden.jpg").getImage();
		g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
		
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, 1187, 557);
		
		for(int a = 0; a < 19; a++) {

			if(redTile[a].getTileState() == true && redTile[a].getTilePlacing() == true) {
					
				g.drawImage(redTile[a].getImage(), redTile[a].getX(), redTile[a].getY(), 54, 54, this);
					
			}
				
			if(blackTile[a].getTileState() == true && blackTile[a].getTilePlacing() == true) {
					
				g.drawImage(blackTile[a].getImage(), blackTile[a].getX(), blackTile[a].getY(), 54, 54, this);
					
			}
			
		}
		
		if(pick == false && project == true && isValidLocation(targetX, targetY)) {
			
			if(turn == 1) {
				
				g.drawImage(target[0], targetX, targetY, 54, 54, this);
				
			}
			else {
				
				g.drawImage(target[1], targetX, targetY, 54, 54, this);
				
			}
			Menu.label[6].setText(""+redTiles);
			Menu.label[7].setText(""+blackTiles);
			
			Menu.upper.revalidate();
			Menu.upper.repaint();
			
			Menu.panel.revalidate();
			Menu.panel.repaint();
		}
	
	}
	
	private class MouseEventHandler extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
				
			if(turn == 1) {
				
				if(maximize != true) {
					
					//sound.playClick();
					
				}
				
				tileOnPlace(e.getX(), e.getY());
					
			}
			
		}
		
	}
	
	private class MouseMotionEventHandler extends MouseMotionAdapter {
		
		@Override
		public void mouseMoved(MouseEvent e) {
				
			if(pick == false) {
				
				projectTarget(e.getX(), e.getY());
				
			}
			
		}
		
	}
	
	//**************************************\\
	//*************/'''\******|''|**********\\
	//************/  _  \*****|  |**********\\
	//***********/  / \  \****|  |**********\\
	//**********/  /___\  \***|  |**********\\
	//*********/  _______  \**|  |**********\\
	//********/__/       \__\*|__|**********\\
	//**************************************\\
	
	//getting the max score for every winning shape
	public double getLinearScore(Tile startTile) {
		
		double[] tempScore = new double[6];
		
		for(int i = 0; i < 6; i++) {
			
			tempScore[i] = 1 + startTile.traverseLinearly(i, startTile.getType());
			if(tempScore[i] < 6) {
				
				if(tempScore[i] >= 0)
					tempScore[i] = Math.pow(10, tempScore[i] - 1);
				else
					tempScore[i] = 0 - (Math.pow(10, tempScore[i] - 1));
					
			}
			else {
				
				tempScore[i] = Integer.MAX_VALUE;
				
			}
			
		}
		
		//System.out.println("Lin - " + (tempScore[5] + tempScore[4] + tempScore[3] + tempScore[2] + tempScore[1] + tempScore[0]));
		
		Arrays.sort(tempScore);
		//System.out.println("Lin - " + tempScore[5]);
		
		return tempScore[5];
		
	}
	
	public double getCircularScore(Tile startTile) {
		
		double[] tempScore = new double[6];
		
		for(int i = 0; i < 6; i++) {
			
			tempScore[i] = 1 + startTile.traverseCircularly(i, startTile.getType(), 0);
			if(tempScore[i] < 6) {
				
				if(tempScore[i] >= 0)
					tempScore[i] = Math.pow(10, tempScore[i] - 1);
				else
					tempScore[i] = 0 - (Math.pow(10, tempScore[i] - 1));
				
			}
			else {
				
				tempScore[i] = Integer.MAX_VALUE;
				
			}
			
		}
		
		//System.out.println("Cir - " + (tempScore[5] + tempScore[4] + tempScore[3] + tempScore[2] + tempScore[1] + tempScore[0]));
		
		Arrays.sort(tempScore);
		//System.out.println("Cir - " + tempScore[5]);
		
		return tempScore[5];

	}
	
	public double getTriangularScore(Tile startTile) {
		
		double[] tempScore = new double[6];
		
		for(int i = 0; i < 6; i++) {
			
			tempScore[i] = 1 + startTile.traverseTriangularly(i, startTile.getType());
			if(tempScore[i] < 6) {
				
				if(tempScore[i] >= 0)
					tempScore[i] = Math.pow(10, tempScore[i] - 1);
				else
					tempScore[i] = 0 - (Math.pow(10, tempScore[i] - 1));
				
			}
			else {
				
				tempScore[i] = Integer.MAX_VALUE;
				
			} 
			
		}
		
		//System.out.println("Tri - " + (tempScore[5] + tempScore[4] + tempScore[3] + tempScore[2] + tempScore[1] + tempScore[0]));
		
		Arrays.sort(tempScore);
		//System.out.println("Tri - " + tempScore[5]);
		
		return tempScore[5];
		
	}
	
	//determine every possible move
	public ArrayList<Point> getAllPossibleMoves() {
		
		ArrayList<Point> location = new ArrayList<Point>();
		
		for(int y = 0; y < 546; y += 42) {
			
			if(y % 84 == 0) {
				
				for(int x = 0; x < 1188; x += 54) {
					
					if(isValidLocation(x, y) && isOccupied(x, y) == false) {
						
						location.add(new Point(x, y));
						
					}
					
				}
				
			}
			else if(y % 42 == 0) {
				
				for(int x = 27; x < 1188; x += 54) {
					
					if(isValidLocation(x, y) && isOccupied(x, y) == false) {
						
						location.add(new Point(x, y));
						
					}
					
				}
				
			}
			
		}
		
		return location;
		
	}
	
	//game heuristic
	public int evaluate() {
		
		int myScore = 0;
		int enemyScore = 0;
		
		for(int i = 0; i < 19; i++) {
				
			if(redTile[i].getTilePlacing() == true) {
					
				//System.out.println("RED");
				enemyScore += (getLinearScore(redTile[i]) + getCircularScore(redTile[i]) + getTriangularScore(redTile[i]));
					
			}
				
			if(blackTile[i].getTilePlacing() == true) {
					
				//System.out.println("BLACK");
				myScore += (getLinearScore(blackTile[i]) + getCircularScore(blackTile[i]) + getTriangularScore(blackTile[i]));
					
			}
			
		}
		
		//System.out.println("myScore = " + myScore);
		//System.out.println("enemyScore = " + enemyScore);
		//System.out.println("score = " + (myScore - enemyScore));
		
		return myScore - enemyScore;
		
	}
	
	public void setPicked(Tile picked) {
		
		this.picked = picked;
		
	}
	
	public Board1 applyMove(Point moveTo) {
		
		Board1 updatedBoard = new Board1();
		
		int i = 0;
		while(i < moves.size()) {
			
			Tile move = moves.poll();
			updatedBoard.tileOnPlace(move.getX(), move.getY());
			moves.offer(move);
			i++;
			
		}
		
		updatedBoard.tileOnPlace(moveTo.x, moveTo.y);
		
		return updatedBoard;
		
	}
	
	public void undoLastMove() {
		
		int i = 0;
		while(i < moves.size()) {
			
			if(i == moves.size() - 1) {
				
				moves.poll();
				
			}
			i++;
			
		}
		
	}
	
	public Tile getTileToMove() {
		
		Tile tileChoosen = null;
		double scoreOfTile = Integer.MAX_VALUE;
		
		for(int i = 0; i < 19; i++) {
			
			if(turn == 1) {
				
				double tempScore = (getLinearScore(redTile[i]) + getCircularScore(redTile[i]) + getTriangularScore(redTile[i]));
				
				if(tempScore >= 0 && tempScore < scoreOfTile) {
					
					tileChoosen = redTile[i];
					scoreOfTile = tempScore;
					
				}
				
			}
			else {
				
				double tempScore = (getLinearScore(blackTile[i]) + getCircularScore(blackTile[i]) + getTriangularScore(blackTile[i]));
				
				if(tempScore >= 0 && tempScore < scoreOfTile) {
					
					tileChoosen = blackTile[i];
					scoreOfTile = tempScore;
					
				}
				
			}
			
		}
		
		return tileChoosen;
		
	}
	
	//alpha beta pruning
	public double alphaBeta(Board1 board, int turn, int depth, double alpha, double beta) {
		
		//System.out.println("Current Depth : " + depth);
		
		if(depth == 0 || gameOver) {
			
			return board.evaluate();
			
		}
		
		ArrayList<Point> possibleMoves = board.getAllPossibleMoves();
		
		if(board.getTurn() == 2) {
			
			double score = Integer.MIN_VALUE;
			
			for(Point move : possibleMoves) {
				
				Board1 newBoard = board.applyMove(move);
				
				score = Math.max(score, board.alphaBeta(newBoard, 1, depth - 1, alpha, beta));
				newBoard.undoLastMove();
				
				if(alpha < score) {
					
					bestMove = move;
					
				}
				
				alpha = Math.max(alpha, score);
				
				if(beta <= alpha) {
					
					break;
					
				}
				
			}
			
			return alpha;
			
		}
		else {
			
			double score = Integer.MAX_VALUE;
			
			for(Point move : possibleMoves) {
				
				Board1 newBoard = board.applyMove(move);
				
				score = Math.min(score, board.alphaBeta(newBoard, 2, depth - 1, alpha, beta));
				newBoard.undoLastMove();
				
				if(beta > score) {
					
					bestMove = move;
					
				}
				
				beta = Math.min(beta, score);
				
				if(beta <= alpha) {
					
					break;
					
				}
				
			}
			
			return beta;
			
		}
		
	}
	
}
