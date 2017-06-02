package six.object;

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
import java.util.LinkedList;
import java.util.Queue;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import six.gui.Menu;
import six.gui.Sound;
import six.gui.Winner;

public class Board2 extends JPanel implements Runnable {

	private static final long serialVersionUID = 42L;
	
	private Tile[] redTile = new Tile[19];
	private Tile[] blackTile = new Tile[19];
	private Tile picked = null;
	
	private BufferedImage[] target = new BufferedImage[2];
	private int targetX = 0;
	private int targetY = 0;
	private boolean project = true;
	
	private boolean pick = false;
	private boolean justStarted = true;
	
	private int turn = 1;
	private int phase = 1;
	
	private int redTiles = 19;
	private int blackTiles = 19;
	
	private MouseEventHandler mouseHandler;
	private MouseMotionEventHandler mouseMotionHandler;
	
	private Thread thread;
	private boolean running = false;
	
	private Toolkit toolkit;
	private Image[] cursorImage = new Image[4];
	private Cursor[] cursor = new Cursor[4];
	
	private ArrayList<Tile> tileCluster1 = new ArrayList<Tile>();
	private ArrayList<Tile> tileCluster2 = new ArrayList<Tile>();
	
	private Sound sound;
	
	public Board2() {
	
		initUI();
		initComponents();
		
		start();
		
	}
	
	public void run() {
		
		while(running) {
			
			repaint();
			
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
		createTiles();
		createTarget();
		
		mouseHandler = new MouseEventHandler();
		addMouseListener(mouseHandler);
		
		mouseMotionHandler = new MouseMotionEventHandler();
		addMouseMotionListener(mouseMotionHandler);
		
	}
	
	public void createCursor() {
		
		toolkit = Toolkit.getDefaultToolkit();
		
		try {
			
			cursorImage[0] = ImageIO.read(new File("src/six/res/image/cursor/red.png"));
			cursorImage[1] = ImageIO.read(new File("src/six/res/image/cursor/black.png"));
			cursorImage[2] = ImageIO.read(new File("src/six/res/image/cursor/red_hand.png"));
			cursorImage[3] = ImageIO.read(new File("src/six/res/image/cursor/black_hand.png"));
			
		}
		catch(Exception e) {
			
			System.out.println("Problem loading tile image!");
			
		}
		
		cursor[0] = toolkit.createCustomCursor(cursorImage[0], new Point(16, 16), "Red");
		cursor[1] = toolkit.createCustomCursor(cursorImage[1], new Point(16, 16), "Black");
		cursor[2] = toolkit.createCustomCursor(cursorImage[2], new Point(16, 16), "Red Hand");
		cursor[3] = toolkit.createCustomCursor(cursorImage[3], new Point(16, 16), "Black Hand");
		
		setCursor(cursor[0]);
	}
	
	public void createTiles() {
		
		for(int a = 0; a < 19; a++) {
			
			redTile[a] = new Tile(0);
			blackTile[a] = new Tile(1);
			
		}
		
		redTile[18].moveTo(540, 252);
		redTile[18].setTilePlacing(true);
		blackTile[18].moveTo(594, 252);
		blackTile[18].setTilePlacing(true);
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
	
	public void tileOnHover(int x, int y) {
		
		int xOffset = (x / 27) * 27;
		int yOffset = (y / 42) * 42;
		
		for(int a = 0; a < 19; a++) {
			
			if(turn == 1) {
				
				if((redTile[a].getX() == xOffset || redTile[a].getX() == xOffset - 27) && redTile[a].getY() == yOffset && redTile[a].getTilePlacing() != true && pick == true) {
					
					redTile[a].setMouseState(true);
					
				}
				else {
					
					redTile[a].setMouseState(false);
					
				}
				
			}
			else {
				
				if((blackTile[a].getX() == xOffset || blackTile[a].getX() == xOffset - 27) && blackTile[a].getY() == yOffset && blackTile[a].getTilePlacing() != true && pick == true) {
					
					blackTile[a].setMouseState(true);
					
				}
				else {
					
					blackTile[a].setMouseState(false);
					
				}
				
			}
			
		}
		
	}
	
	public void tileOnPick(int x, int y) {
		
		sound.playClick();
		
		int xOffset = (x / 27) * 27;
		int yOffset = (y / 42) * 42;
		
		for(int a = 0; a < 19; a++) {
			
			if((redTile[a].getX() == xOffset || redTile[a].getX() == xOffset - 27) && redTile[a].getY() == yOffset && turn == 1 && redTile[a].getTilePlacing() != true && redTile[a].getTileStateInGame() == true) {
			
				picked = redTile[a];
				pick = false;
				redTile[a].setTileState(false);
				setCursor(cursor[0]);
				refreshAdjacentTiles();
				
				if(phase == 2 && picked.isBridge()) {
					
					removeSeparatedTiles();
					if(Menu.isRed==false)
					{
						Menu.label[6].setText(""+blackTiles);
						Menu.label[7].setText(""+redTiles);
					}
					else
					{
						Menu.label[7].setText(""+blackTiles);
						Menu.label[6].setText(""+redTiles);
					}
					
					Menu.upper.revalidate();
					Menu.upper.repaint();
					
					Menu.panel.revalidate();
					Menu.panel.repaint();
					
					
				}
				
				break;
				
			}
			
			if((blackTile[a].getX() == xOffset || blackTile[a].getX() == xOffset - 27) && blackTile[a].getY() == yOffset && turn == 2 && blackTile[a].getTilePlacing() != true && blackTile[a].getTileStateInGame() == true) {
				
				picked = blackTile[a];
				pick = false;
				blackTile[a].setTileState(false);
				setCursor(cursor[1]);
				refreshAdjacentTiles();
				
				if(phase == 2 && picked.isBridge()) {
					
					removeSeparatedTiles();
					
				}
				
				break;
				
			}
			
		}
		
	}
	
	public void tileOnPlace(int x, int y) {
		
		sound.playClick();
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
			
			if(phase == 1) {
				
				picked.setTilePlacing(true);
				
				if(picked.getType() == 0) {
					
					redTiles--;
					--Menu.numOfTiles2;
					if(Menu.isRed==true)
						Menu.label[6].setText(""+redTiles);
					else
						Menu.label[7].setText(""+redTiles);
					Menu.upper.revalidate();
					Menu.upper.repaint();
					
					Menu.lower.revalidate();
					Menu.lower.repaint();
					
					Menu.panel.revalidate();
					Menu.panel.repaint();
					
				}
				else {
					
					blackTiles--;
					--Menu.numOfTiles1;
					if(Menu.isRed==false)
						Menu.label[6].setText(""+blackTiles);
					else
						Menu.label[7].setText(""+blackTiles);
					Menu.upper.revalidate();
					Menu.upper.repaint();
					
					Menu.lower.revalidate();
					Menu.lower.repaint();
					
					Menu.panel.revalidate();
					Menu.panel.repaint();
					
				}
				
			}
			
			setAdjacentTiles(picked);
			checkLinear(picked);
			checkCircular(picked);
			checkTriangular(picked);
			
			if(phase == 2) {
				
				checkTileCount();
				
			}
			
			if(phase == 2) {
				
				pick = true;
				picked = null;
				
				if(turn == 1) {
					
					turn = 2;
					setCursor(cursor[3]);
					
				}
				else {
					
					turn = 1;
					setCursor(cursor[2]);
					
				}
				
			}
			else {
				
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
			
		}
		
		if(proceedPhase2() && phase == 1) {
			
			for(int a = 0; a < 19; a++) {
				
				redTile[a].setTilePlacing(false);
				blackTile[a].setTilePlacing(false);
				
			}
			
			phase = 2;
			redTiles = 19;
			blackTiles = 19;
			
			pick = true;
			setCursor(cursor[2]);
			turn = 1;
			picked = null;
			
		}
		
	}
	
	public boolean proceedPhase2() {
		
		if(redTiles == 0 && blackTiles == 0) {
			
			return true;
			
		}
		else {
			
			return false;
			
		}
		
	}
	
	public boolean isOccupied(int x, int y) {
		
		for(int a = 0; a < 19; a++) {
			
			if(redTile[a].getX() == x && redTile[a].getY() == y && redTile[a].getTileStateInGame() == true) {
			
				return true;
				
			}
			
			if(blackTile[a].getX() == x && blackTile[a].getY() == y && blackTile[a].getTileStateInGame() == true) {
				
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
				
				if(phase == 1) {
					
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
				else {
					
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
			
			if(redTile[a].getX() == x - 27 && redTile[a].getY() == y - 42 && redTile[a].getTileStateInGame() == true) {
				
				centerTile.setAdjacentTile(0, redTile[a]);
				centerTile.updateNumberOfAdjacentTiles(1);
				redTile[a].setAdjacentTile(3, centerTile);
				redTile[a].updateNumberOfAdjacentTiles(1);
				
				
			}
			else if(redTile[a].getX() == x - 54 && redTile[a].getY() == y && redTile[a].getTileStateInGame() == true) {
				
				centerTile.setAdjacentTile(1, redTile[a]);
				centerTile.updateNumberOfAdjacentTiles(1);
				redTile[a].setAdjacentTile(4, centerTile);
				redTile[a].updateNumberOfAdjacentTiles(1);
				
			}
			else if(redTile[a].getX() == x - 27 && redTile[a].getY() == y + 42 && redTile[a].getTileStateInGame() == true) {
				
				centerTile.setAdjacentTile(2, redTile[a]);
				centerTile.updateNumberOfAdjacentTiles(1);
				redTile[a].setAdjacentTile(5, centerTile);
				redTile[a].updateNumberOfAdjacentTiles(1);
				
			}
			else if(redTile[a].getX() == x + 27 && redTile[a].getY() == y + 42 && redTile[a].getTileStateInGame() == true) {
				
				centerTile.setAdjacentTile(3, redTile[a]);
				centerTile.updateNumberOfAdjacentTiles(1);
				redTile[a].setAdjacentTile(0, centerTile);
				redTile[a].updateNumberOfAdjacentTiles(1);
				
			}
			else if(redTile[a].getX() == x + 54 && redTile[a].getY() == y && redTile[a].getTileStateInGame() == true) {
				
				centerTile.setAdjacentTile(4, redTile[a]);
				centerTile.updateNumberOfAdjacentTiles(1);
				redTile[a].setAdjacentTile(1, centerTile);
				redTile[a].updateNumberOfAdjacentTiles(1);
				
			}
			else if(redTile[a].getX() == x + 27 && redTile[a].getY() == y - 42 && redTile[a].getTileStateInGame() == true) {
				
				centerTile.setAdjacentTile(5, redTile[a]);
				centerTile.updateNumberOfAdjacentTiles(1);
				redTile[a].setAdjacentTile(2, centerTile);
				redTile[a].updateNumberOfAdjacentTiles(1);
				
			}
			
			if(blackTile[a].getX() == x - 27 && blackTile[a].getY() == y - 42 && blackTile[a].getTileStateInGame() == true) {
				
				centerTile.setAdjacentTile(0, blackTile[a]);
				centerTile.updateNumberOfAdjacentTiles(1);
				blackTile[a].setAdjacentTile(3, centerTile);
				blackTile[a].updateNumberOfAdjacentTiles(1);
				
			}
			else if(blackTile[a].getX() == x - 54 && blackTile[a].getY() == y && blackTile[a].getTileStateInGame() == true) {
				
				centerTile.setAdjacentTile(1, blackTile[a]);
				centerTile.updateNumberOfAdjacentTiles(1);
				blackTile[a].setAdjacentTile(4, centerTile);
				blackTile[a].updateNumberOfAdjacentTiles(1);
				
			}
			else if(blackTile[a].getX() == x - 27 && blackTile[a].getY() == y + 42 && blackTile[a].getTileStateInGame() == true) {
				
				centerTile.setAdjacentTile(2, blackTile[a]);
				centerTile.updateNumberOfAdjacentTiles(1);
				blackTile[a].setAdjacentTile(5, centerTile);
				blackTile[a].updateNumberOfAdjacentTiles(1);
				
			}
			else if(blackTile[a].getX() == x + 27 && blackTile[a].getY() == y + 42 && blackTile[a].getTileStateInGame() == true) {
				
				centerTile.setAdjacentTile(3, blackTile[a]);
				centerTile.updateNumberOfAdjacentTiles(1);
				blackTile[a].setAdjacentTile(0, centerTile);
				blackTile[a].updateNumberOfAdjacentTiles(1);
				
			}
			else if(blackTile[a].getX() == x + 54 && blackTile[a].getY() == y && blackTile[a].getTileStateInGame() == true) {
				
				centerTile.setAdjacentTile(4, blackTile[a]);
				centerTile.updateNumberOfAdjacentTiles(1);
				blackTile[a].setAdjacentTile(1, centerTile);
				blackTile[a].updateNumberOfAdjacentTiles(1);
				
			}
			else if(blackTile[a].getX() == x + 27 && blackTile[a].getY() == y - 42 && blackTile[a].getTileStateInGame() == true) {
				
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
			
			if(redTile[a].getX() == x - 27 && redTile[a].getY() == y - 42 && redTile[a].getTileStateInGame() == true) {
				
				centerTile.setAdjacentTile(0, redTile[a]);
				
			}
			else if(redTile[a].getX() == x - 54 && redTile[a].getY() == y && redTile[a].getTileStateInGame() == true) {
				
				centerTile.setAdjacentTile(1, redTile[a]);
				
			}
			else if(redTile[a].getX() == x - 27 && redTile[a].getY() == y + 42 && redTile[a].getTileStateInGame() == true) {
				
				centerTile.setAdjacentTile(2, redTile[a]);
				
			}
			else if(redTile[a].getX() == x + 27 && redTile[a].getY() == y + 42 && redTile[a].getTileStateInGame() == true) {
				
				centerTile.setAdjacentTile(3, redTile[a]);
				
			}
			else if(redTile[a].getX() == x + 54 && redTile[a].getY() == y && redTile[a].getTileStateInGame() == true) {
				
				centerTile.setAdjacentTile(4, redTile[a]);
				
			}
			else if(redTile[a].getX() == x + 27 && redTile[a].getY() == y - 42 && redTile[a].getTileStateInGame() == true) {
				
				centerTile.setAdjacentTile(5, redTile[a]);
				
			}
			
			if(blackTile[a].getX() == x - 27 && blackTile[a].getY() == y - 42 && blackTile[a].getTileStateInGame() == true) {
				
				centerTile.setAdjacentTile(0, blackTile[a]);
				
			}
			else if(blackTile[a].getX() == x - 54 && blackTile[a].getY() == y && blackTile[a].getTileStateInGame() == true) {
				
				centerTile.setAdjacentTile(1, blackTile[a]);
				
			}
			else if(blackTile[a].getX() == x - 27 && blackTile[a].getY() == y + 42 && blackTile[a].getTileStateInGame() == true) {
				
				centerTile.setAdjacentTile(2, blackTile[a]);
				
			}
			else if(blackTile[a].getX() == x + 27 && blackTile[a].getY() == y + 42 && blackTile[a].getTileStateInGame() == true) {
				
				centerTile.setAdjacentTile(3, blackTile[a]);
				
			}
			else if(blackTile[a].getX() == x + 54 && blackTile[a].getY() == y && blackTile[a].getTileStateInGame() == true) {
				
				centerTile.setAdjacentTile(4, blackTile[a]);
				
			}
			else if(blackTile[a].getX() == x + 27 && blackTile[a].getY() == y - 42 && blackTile[a].getTileStateInGame() == true) {
				
				centerTile.setAdjacentTile(5, blackTile[a]);
				
			}
			
		}
		
	}
	
	public void checkLinear(Tile startTile) {
		
		for(int a = 0; a < 6; a++) {
		
			if((1 + startTile.traverseLinearly(a, startTile.getType())) >= 6) {
				
				Winner win = new Winner();
				
				if(startTile.getType() == 0) {
					
					Winner.winner = 0;
					win.broadCastWinner();
					win.setModal(true);
					win.setVisible(true);
					
				} else {
					
					Winner.winner = 1;
					win.broadCastWinner();
					win.setModal(true);
					win.setVisible(true);
					
				}
				
			}
			
		}
		
	}
	
	public void checkCircular(Tile startTile) {
		
		for(int a = 0; a < 6; a++) {
			
			if((1 + startTile.traverseCircularly(a, startTile.getType(), 0)) == 6) {
				
				Winner win = new Winner();
				
				if(startTile.getType() == 0) {
					
					Winner.winner = 0;
					win.broadCastWinner();
					win.setModal(true);
					win.setVisible(true);
					
				} else {
					
					Winner.winner = 1;
					win.broadCastWinner();
					win.setModal(true);
					win.setVisible(true);
					
				}
				
			}
			
		}
		
	}
	
	public void checkTriangular(Tile startTile) {
		
		for(int a = 0; a < 6; a++) {
			
			if((1 + startTile.traverseTriangularly(a, startTile.getType())) == 6) {
				
				Winner win = new Winner();
				
				if(startTile.getType() == 0) {
					
					Winner.winner = 0;
					win.broadCastWinner();
					win.setModal(true);
					win.setVisible(true);
					
				} else {
					
					Winner.winner = 1;
					win.broadCastWinner();
					win.setModal(true);
					win.setVisible(true);
					
				}
					
		
				
				
			}
			
		}
		
	}
	
	public void checkTileCount() {
		
		Winner win = new Winner();
		
		if(redTiles >= 6 && blackTiles < 6) {
			
			Winner.winner = 0;
			win.broadCastWinner();
			win.setModal(true);
		 	win.setVisible(true);
			
		} else if(redTiles < 6 && blackTiles >= 6) {
			
			Winner.winner = 1;
			win.broadCastWinner();
			win.setModal(true);
		 	win.setVisible(true);
			
		} else if(redTiles < 6 && blackTiles < 6) {
			
			Winner.winner = 2;
			win.broadCastWinner();
			win.setModal(true);
		 	win.setVisible(true);
			
		}
			
		
		
	}
	
	public void removeSeparatedTiles() {
		
		int cluster = 1;
		
		redTiles=19;
		blackTiles=19;
		for(int a = 0; a < 6; a++) {
			
			if(picked.getAdjacentTile(a) != null) {
				
				if(cluster == 1) {
					
					traverseTileCluster(picked.getAdjacentTile(a), 1);
					cluster++;
					
				}
				else {
					
					traverseTileCluster(picked.getAdjacentTile(a), 2);
					
					boolean proceed = true;
					
					for(int b = 0; b < tileCluster2.size(); b++) {
						
						if(tileCluster1.contains(tileCluster2.get(b))) {
							
							proceed = false;
							break;
							
						}
						
					}
					
					if(proceed) {
						
						if(tileCluster2.size() < tileCluster1.size()) {
							
							for(int c = 0; c < tileCluster2.size(); c++) {
								
								Tile current = tileCluster2.get(c);
								
								current.setTileStateInGame(false);
								
								if(current.getType() == 0) {
									
									redTiles--;
									
								}
								else {
									
									blackTiles--;
									
								}
								
							}
							
						}
						else {
							
							for(int d = 0; d < tileCluster1.size(); d++) {
								
								Tile current = tileCluster1.get(d);
								
								current.setTileStateInGame(false);
								
								if(current.getType() == 0) {
									
									redTiles--;
									
								}
								else {
									
									blackTiles--;
									
								}
								
							}
							
							tileCluster1.clear();
							tileCluster1.addAll(tileCluster2);
							
						}
						
					}
					
					tileCluster2.clear();
					
				}
				
			}
			
		}
		
		if(Menu.isRed==false)
		{
			Menu.label[6].setText(""+blackTiles);
			Menu.label[7].setText(""+redTiles);
		}
		else
		{
			Menu.label[7].setText(""+blackTiles);
			Menu.label[6].setText(""+redTiles);
		}
		
		Menu.upper.revalidate();
		Menu.upper.repaint();
		
		Menu.panel.revalidate();
		Menu.panel.repaint();
		tileCluster1.clear();
		tileCluster2.clear();
		
	}
	
	public void traverseTileCluster(Tile startTile, int cluster) {
		
		Queue<Tile> queue = new LinkedList<Tile>();
		
		queue.offer(startTile);
		startTile.setTraverseState(true);
		if(cluster == 1) {
			
			tileCluster1.add(startTile);
			
		}
		else if(cluster == 2) {
			
			tileCluster2.add(startTile);
			
		}
		
		while(!queue.isEmpty()) {
			
			Tile currentTile = queue.poll();
			
			for(int a = 0; a < 6; a++) {
				
				if(currentTile.getAdjacentTile(a) != null && currentTile.getAdjacentTile(a).getTraverseState() == false) {
					
					queue.offer(currentTile.getAdjacentTile(a));
					currentTile.getAdjacentTile(a).setTraverseState(true);
					if(cluster == 1) {
						
						tileCluster1.add(currentTile.getAdjacentTile(a));
						
					}
					else if(cluster == 2) {
						
						tileCluster2.add(currentTile.getAdjacentTile(a));
						
					}
					
				}
				
			}
			
		}
		
		for(int a = 0; a < 19; a++) {
			
			redTile[a].setTraverseState(false);
			blackTile[a].setTraverseState(false);
			
		}
		
	}
	
	public void paintComponent(Graphics g) {
		
		//g.setColor(Color.GRAY);
		//g.fillRect(0, 0, 1188, 558);
		int m=0;
		int n=0;
		Image bgImage =  new ImageIcon("src/images/wooden.jpg").getImage();
		g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
		
		for(int a = 0; a < 19; a++) {
			if(phase == 1) {
				
				if(redTile[a].getTileState() == true && redTile[a].getTileStateInGame() == true && redTile[a].getTilePlacing() == true) {
					
					g.drawImage(redTile[a].getImage(), redTile[a].getX(), redTile[a].getY(), 54, 54, this);
					
				}
				
				if(blackTile[a].getTileState() == true && blackTile[a].getTileStateInGame() == true && blackTile[a].getTilePlacing() == true) {
					
					g.drawImage(blackTile[a].getImage(), blackTile[a].getX(), blackTile[a].getY(), 54, 54, this);
					
				}
				
			}
			else {
				
				if(redTile[a].getTileState() == true && redTile[a].getTileStateInGame() == true) {
					
					g.drawImage(redTile[a].getImage(), redTile[a].getX(), redTile[a].getY(), 54, 54, this);
					++m;
					
				}
				
				if(blackTile[a].getTileState() == true && blackTile[a].getTileStateInGame() == true) {
					
					g.drawImage(blackTile[a].getImage(), blackTile[a].getX(), blackTile[a].getY(), 54, 54, this);
					++n;
					
				}
				
			}
			
		}
		
		if(pick == false && project == true && isValidLocation(targetX, targetY)) {
			
			if(turn == 1) {
				
				g.drawImage(target[0], targetX, targetY, 54, 54, this);
			}
			else {
				g.drawImage(target[1], targetX, targetY, 54, 54, this);
				
			}
			
		}
		if(phase==2)
		{
			if(Menu.isRed==false)
			{
				Menu.label[6].setText(""+n);
				Menu.label[7].setText(""+m);
			}
			else
			{
				Menu.label[7].setText(""+n);
				Menu.label[6].setText(""+m);
			}
			redTiles=m;
			blackTiles=n;
			
			Menu.upper.revalidate();
			Menu.upper.repaint();
			
			Menu.panel.revalidate();
			Menu.panel.repaint();
		}
	
	}
	
	private class MouseEventHandler extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			
			if(pick == true && picked == null && phase == 2) {
				
				tileOnPick(e.getX(), e.getY());
				
			}
			else if(pick == false && picked != null) {
				
				tileOnPlace(e.getX(), e.getY());
			}
			
		}
		
	}
	
	private class MouseMotionEventHandler extends MouseMotionAdapter {
		
		@Override
		public void mouseMoved(MouseEvent e) {
				
			tileOnHover(e.getX(), e.getY());
			if(pick == false) {
				
				projectTarget(e.getX(), e.getY());
				
			}
			
		}
		
	}

}
