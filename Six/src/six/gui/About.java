package six.gui;
import javax.swing.JPanel;
import javax.swing.border.*;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class About extends JPanel {
	
	private JLabel label, label2, label3;
	private JLabel copyRights, area2, aboutIcon;
	private JTextArea area1, area3;
	private JPanel purposePanel, developersPanel, creditsPanel;
	
	private SixLogo sixLabel;
	private ButtonLabel back;
	private ImageIcon icon;
	 
	private Border border;
	 
	public About() {
		
		setSize(1280, 720);
		setLayout(null);
		
		initComponents();
		addComponents();
		
	}
	
	public void initComponents() {
		
		border = BorderFactory.createLineBorder(Color.WHITE);
		
		label= new JLabel("PURPOSE OF PROGRAM");
		label.setBounds(45, -20, 350, 100);
		label.setFont(new Font("Calibri", Font.BOLD, 25));
		label.setForeground(Color.WHITE);
		
		label2= new JLabel("DEVELOPERS");
		label2.setBounds(60, -20, 350, 100);
		label2.setFont(new Font("Calibri", Font.BOLD, 25));
		label2.setForeground(Color.WHITE);
		
		label3= new JLabel("CREDITS");
		label3.setBounds(120, -15, 350, 100);
		label3.setFont(new Font("Calibri", Font.BOLD, 25));
		label3.setForeground(Color.WHITE);
		
		icon = new ImageIcon("src/images/about2.png");
		aboutIcon= new JLabel(icon);
		aboutIcon.setBounds(325, 20, icon.getIconWidth(), icon.getIconHeight());
		
		area1= new JTextArea(10, 10);
		area1.setText("         Six is a deceptively simple-looking  abstract strategy game "
				+ ". Players do not need to be overtly "
				+ "academic to win the game. Just by virtue of playing, Six can  teach  important  social  skills such "
				+ "as    conveying  tactics   verbally,   waiting,    taking turns and enjoying interactions   with others (two-player"
				+ " game). Six can  foster the capability to focus and leng-  then the attention span by upbeating"
				+ "   the completion of the game. ");
		area1.setFont(new Font("Calibri", Font.BOLD, 20));
		area1.setForeground(Color.WHITE);
		area1.setLineWrap(true);
		area1.setBounds(0, 60, 335, 290);
		area1.setOpaque(false);
		
		area2= new JLabel();
		area2.setText("<html><center>Creer, Cylwyn Ronald M.<br>Dublin, Rodney O.<br>Pol, Cyril Kaye G.<br>"
				+ "Opetina, Patricia Nicole </center></html>");
		area2.setFont(new Font("Calibri", Font.BOLD, 20));
		area2.setForeground(Color.WHITE);
		
		area2.setBounds(30, 40, 235, 120);
		area2.setOpaque(false);
		
		area3= new JTextArea(10, 10);
		area3.setText("   StackOverflow\n   PhotoShop 7.0\n        Google\n      Facebook");
		area3.setFont(new Font("Calibri", Font.BOLD, 20));
		area3.setForeground(Color.WHITE);
		
		area3.setBounds(90, 70, 385, 100);
		area3.setOpaque(false);
		
		sixLabel = new SixLogo("src/images/sixAbout.png", "src/images/sixAbout2.png");
		sixLabel.start();
		sixLabel.setBounds(460, 10, 1000, 600);
		
		purposePanel = new JPanel(null);
		purposePanel.setBounds(70, 160, 350, 350);
		purposePanel.setBackground(new Color(0, 0, 0, 150));
		purposePanel.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
		
		developersPanel = new JPanel(null);
		developersPanel.setBounds(500, 470, 270, 170);
		developersPanel.setBackground(new Color(0, 0, 0, 150));
		developersPanel.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
		
		creditsPanel = new JPanel(null);
		creditsPanel.setBounds(850, 160, 350, 350);
		creditsPanel.setBackground(new Color(0, 0, 0, 150));
		creditsPanel.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
		
		copyRights = new JLabel("<html>Copyrights &Copy 2017. All Rights Reserved</html>");
		copyRights.setBounds(515,610,400,100);
		copyRights.setFont(new Font("Calibri", Font.BOLD, 15));
		copyRights.setForeground(Color.WHITE);
		
		back = new ButtonLabel("src/images/back2.png","src/images/back.png", 900, 570);
		
	}
	
	public void addComponents() {
		
		add(purposePanel);
		purposePanel.add(label);
		purposePanel.add(area1);
		
		add(developersPanel);
		developersPanel.add(label2);
		developersPanel.add(area2);
		
		add(creditsPanel);
		creditsPanel.add(label3);
		creditsPanel.add(area3);
		
		add(aboutIcon);
		add(sixLabel);
		add(copyRights);
		add(back);
		
	}

	@Override
	protected void paintComponent(Graphics g) {
	
		try {
			
			Image bgImage = ImageIO.read(new File("src/images/backgroundFade.png"));
			super.paintComponent(g);
			g.drawImage(bgImage, 0, 0, null);
			
		} catch(Exception e) {;}
		
	
	}
	
}