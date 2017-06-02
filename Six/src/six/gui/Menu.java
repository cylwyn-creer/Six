package six.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import six.object.Board1;
import six.object.Board2;

@SuppressWarnings("serial")
public class Menu extends JPanel {
	
	public static ButtonLabel[] buttonLabel = new ButtonLabel[15];
	public static JLabel[] label = new JLabel[10];
	public static String[] string = {"src/images/avatar1.png","src/images/avatar2.png"
			,"src/images/miniBlackTile.png","src/images/miniRedTile.png"};
	public static JPanel panel,board, upper, lower, center;
	public static SixLogo logo;
	public static About about;
	public static HowToPlay howPlay;
	public static Exit exit;
	public static Board1 onePlayerBoard;
	public static Board2 twoPlayerBoard;
	public static boolean isOnePlayer;
	public static int numOfTiles1= 18;
	public static int numOfTiles2= 18;
	public static boolean isRed=true;
	private Sound sound;
	public static ImageIcon loadIcon;
	public static boolean toOptionPane = false;
	
	public Menu() {
		
		setLayout(null);
		setPreferredSize(new Dimension(1280, 720));
		
		initComponents();
		addComponents();

		sound.playBG();
		
	}
	
	public void initComponents() {
		
		sound = new Sound();
		
		panel = new BgPanel();
		panel.setLayout(null);
		panel.setBounds(0, 0, 1280, 720);
		
		board = new BoardPanel();
		board.setLayout(null);
		board.setBounds(0, 0, 1280, 720);
		
		upper = new JPanel();
		upper.setLayout(null);
		upper.setPreferredSize(new Dimension(1280,100));
		upper.setBounds(0,0,1280,100);
		upper.setBackground(new Color(0,0,0,0));
		upper.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		lower = new JPanel();
		lower.setLayout(null);
		lower.setPreferredSize(new Dimension(1188,558));
		lower.setBounds(40,110,1188,558);
		lower.setBackground(new Color(0,0,0,0));
		lower.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		center = new JPanel();
		center.setLayout(null);
		center.setPreferredSize(new Dimension(380,100));
		center.setBounds(450,0,380,100);
		center.setBackground(new Color(0,0,0,0));
		center.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		logo = new SixLogo("src/images/six1.png", "src/images/six2.png");
		logo.start();
		
		about = new About();
		about.setBounds(0,-5,1280,720);
		
		exit = new Exit();
		exit.setBounds(0,-5,1280,720);
		
		howPlay = new HowToPlay();
		howPlay.setBounds(0,-5,1280,720);
		
		for(int a = 0; a < 4 ; a++)
			label[a] = new JLabel(new ImageIcon(string[a]));
			
		label[4] = new JLabel("PLAYER 1");
		label[4].setFont(new Font("Calibri", Font.PLAIN, 50));
		
		label[5] = new JLabel("PLAYER 2");
		label[5].setFont(new Font("Calibri", Font.PLAIN, 50));
		
		label[6] = new JLabel(numOfTiles1+"");
		label[6].setFont(new Font("Calibri", Font.BOLD, 50));
		
		label[7] = new JLabel(numOfTiles2+"");
		label[7].setFont(new Font("Calibri", Font.BOLD, 50));
		
		label[8] = new JLabel(new ImageIcon("src/images/comp2.PNG"));
		label[9] = new JLabel(new ImageIcon("src/images/comp1.png"));
		
		loadIcon = new ImageIcon("src/images/giphy.gif");
		
		buttonLabel[0] = new ButtonLabel("src/images/newgame1.png","src/images/newgame2.png", 595,120);
		buttonLabel[1] = new ButtonLabel("src/images/how2play1.png","src/images/how2play2.png", 595,220);
		buttonLabel[2] = new ButtonLabel("src/images/about1.png","src/images/about2.png", 595,320);
		buttonLabel[3] = new ButtonLabel("src/images/exit1.png","src/images/exit2.png", 595,420);
		buttonLabel[4] = new ButtonLabel("src/images/oneplayer1.png","src/images/oneplayer2.png",595,220);
		buttonLabel[5] = new ButtonLabel("src/images/twoplayer1.png","src/images/twoplayer2.png",595,320);
		buttonLabel[6] = new ButtonLabel("src/images/choose.PNG","src/images/choose.PNG",595,120);
		buttonLabel[7] = new ButtonLabel("src/images/blackTile2.png","src/images/blackTile.png",650,270);
		buttonLabel[8] = new ButtonLabel("src/images/redTile2.PNG","src/images/redTile.PNG",920,270);
		buttonLabel[9] = new ButtonLabel("src/images/restart2.png","src/images/restart.PNG",30,-10);
		buttonLabel[10] = new ButtonLabel("src/images/mainMenu2.png","src/images/mainMenu.png",30,35);
		buttonLabel[11] = new ButtonLabel("src/images/blackTile2a.png","src/images/blackTile.png",650,270);
		buttonLabel[12] = new ButtonLabel("src/images/redTile2a.PNG","src/images/redTile.PNG",920,270);
		buttonLabel[13] = new ButtonLabel("src/images/back2.png","src/images/back.png",900,570);
		buttonLabel[14] = new ButtonLabel("src/images/back2a.png","src/images/back.png",900,570);
		
		
	}
	
	public void addComponents() {
		
		toOptionPane = false;
		
		panel.add(buttonLabel[0]);
		panel.add(buttonLabel[1]);
		panel.add(buttonLabel[2]);
		panel.add(buttonLabel[3]);
		panel.add(logo);
		
		add(panel);
		
	}
	
	public static void back() {
		
		toOptionPane = false;
		
		panel.add(buttonLabel[0]);
		panel.add(buttonLabel[1]);
		panel.add(buttonLabel[2]);
		panel.add(buttonLabel[3]);
		panel.add(logo);
		panel.revalidate();
		panel.repaint();
		
	}
	
	public static void back2a() {
		
		newGame();
		
	}
	
	public static void newGame() {
		
		toOptionPane = false;
		
		JLabel head = new JLabel(new ImageIcon("src/images/newgame3.PNG"));
		head.setBounds(695, 50, 400, 100);
		panel.add(head);
		panel.add(buttonLabel[4]);
		panel.add(buttonLabel[5]);
		panel.add(buttonLabel[13]);
		panel.add(logo);
		panel.revalidate();
		panel.repaint();
		
	}
	
	public static void how2play() {
		
		toOptionPane = false;
		
		panel.add(howPlay);
		panel.revalidate();
		panel.repaint();
		
	}
	
	public static void about() {
		
		toOptionPane = false;
		
		panel.add(about);
		panel.revalidate();
		panel.repaint();
		
	}
	
	public static void exit() {
		
		toOptionPane = false;
		
		panel.add(exit);
		panel.revalidate();
		panel.repaint();
		
	}
	
	public static void chooseTile() {
		
		toOptionPane = false;
		
		panel.add(buttonLabel[6]);
		panel.add(buttonLabel[7]);
		panel.add(buttonLabel[8]);
		panel.add(buttonLabel[14]);
		panel.add(logo);
		panel.revalidate();
		panel.repaint();
		upper.repaint();
		
	}
	
	public static void twoPlayerRed() {
		
		toOptionPane = true;
		
		twoPlayerBoard = new Board2();
		isOnePlayer = false;
		
		label[1].setBounds(0,5,90,90);
		label[4].setBounds(100,5,200,100);
		label[3].setBounds(310,10,60,75);
		label[6].setBounds(390,5,100,100);
		label[0].setBounds(835,5,90,90);
		label[5].setBounds(935,5,200,100);
		label[2].setBounds(1135,10,60,75);
		label[7].setBounds(1210,5,100,100);
		
		center.add(buttonLabel[9]);
		center.add(buttonLabel[10]);
		
		upper.add(center);
		upper.add(label[1]);
		upper.add(label[4]);
		upper.add(label[3]);
		upper.add(label[6]);
		upper.add(label[0]);
		upper.add(label[5]);
		upper.add(label[2]);
		upper.add(label[7]);
		
		lower.add(twoPlayerBoard);
		lower.revalidate();
		lower.repaint();
		
		board.add(upper);
		board.add(lower);
		panel.add(board);
		panel.revalidate();
		panel.repaint();
		
	}
	
	public static void twoPlayerBlack() {
		
		toOptionPane = true;
		
		isRed = false;
		twoPlayerBoard = new Board2();
		isOnePlayer = false;
		
		label[0].setBounds(0,5,90,90);
		label[4].setBounds(100,5,200,100);
		label[2].setBounds(310,10,60,75);
		label[6].setBounds(390,5,100,100);
		label[1].setBounds(835,5,90,90);
		label[5].setBounds(935,5,200,100);
		label[3].setBounds(1135,10,60,75);
		label[7].setBounds(1210,5,100,100);
		
		center.add(buttonLabel[9]);
		center.add(buttonLabel[10]);
		
		upper.add(center);
		upper.add(label[1]);
		upper.add(label[4]);
		upper.add(label[3]);
		upper.add(label[6]);
		upper.add(label[0]);
		upper.add(label[5]);
		upper.add(label[2]);
		upper.add(label[7]);
		
		lower.add(twoPlayerBoard);
		lower.revalidate();
		lower.repaint();
		
		board.add(upper);
		board.add(lower);
		panel.add(board);
		panel.revalidate();
		panel.repaint();
		
	}
	
	public static void onePlayerRed(){
		
		toOptionPane = true;
		
		onePlayerBoard = new Board1();
		onePlayerBoard.start();
		isOnePlayer = true;
		
		label[1].setBounds(0,5,90,90);
		label[4].setBounds(100,5,200,100);
		label[3].setBounds(310,10,60,75);
		label[6].setBounds(390,5,100,100);
		label[9].setBounds(835,5,90,90);
		label[5].setBounds(935,5,200,100);
		label[2].setBounds(1135,10,60,75);
		label[7].setBounds(1210,5,100,100);
		
		center.add(buttonLabel[9]);
		center.add(buttonLabel[10]);
		
		upper.add(center);
		upper.add(label[1]);
		upper.add(label[4]);
		upper.add(label[3]);
		upper.add(label[6]);
		upper.add(label[9]);
		upper.add(label[5]);
		upper.add(label[2]);
		upper.add(label[7]);
		upper.repaint();
		upper.revalidate();
		
		lower.add(onePlayerBoard);
		lower.revalidate();
		lower.repaint();
		
		board.add(upper);
		board.add(lower);
		panel.add(board);
		panel.revalidate();
		panel.repaint();
		
	}
	
	
	public static void removeLower() {
		
		Menu.lower.removeAll();
		Menu.lower.revalidate();
		Menu.lower.repaint();
		
	}
	
	public static void removeUpper() {
		
		Menu.upper.removeAll();
		Menu.upper.revalidate();
		Menu.upper.repaint();
		
	}
	
	public static void removePanel() {
		
		Menu.panel.removeAll();
		Menu.panel.revalidate();
		Menu.panel.repaint();
		
	}
	
	class BgPanel extends JPanel {
		
	    Image backgroundImage = new ImageIcon("src/images/backgroundFade.PNG").getImage();
	    
	    public void paintComponent(Graphics g) {
	    	
	      g.drawImage(backgroundImage, 0, 0, null);
	      
	    }
	    
	}
	
	class BoardPanel extends JPanel {
		
	    Image backgroundImage = new ImageIcon("src/images/wooden.jpg").getImage();
	    
	    public void paintComponent(Graphics g) {
	    	
	      g.drawImage(backgroundImage, 0, 0, null);
	      
	    }
	    
	}
	
}
