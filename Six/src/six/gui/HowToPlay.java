package six.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class HowToPlay extends JPanel {

	private JPanel instructionsPanel, shapesPanel;
	private JLabel headingL, instructionsL, shapesL, winningShapes, listInstructions;
	private ButtonLabel back;
	
	public HowToPlay() {
	
		setLayout(null);
		setSize(1280, 720);
		
		initComponents();
		addComponents();
		
	}
	
	public void initComponents() {
		
		try{
			
			headingL = new JLabel(new ImageIcon("src/images/how2play2.png"));
			headingL.setBounds(350, 0, 600, 118);
			
			instructionsL = new JLabel(new ImageIcon("src/images/instructions.png"));
			instructionsL.setBounds(350, 100, 600, 118);
			
			shapesL = new JLabel(new ImageIcon("src/images/winning.png"));
			shapesL.setBounds(-20, -30, 600, 118);
			
			winningShapes = new JLabel(new ImageIcon("src/images/winningShapes.png"));
			winningShapes.setBounds(0, 75, 550, 540);
			
		} catch(Exception e) {;}
		
		instructionsPanel = new JPanel();
		instructionsPanel.setBounds(20, 120, 650, 540);
		instructionsPanel.setBackground(new Color(0, 0, 0, 150));
		instructionsPanel.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
		
		shapesPanel = new JPanel();
		shapesPanel.setLayout(null);
		shapesPanel.setBounds(680, 120, 550, 540);
		shapesPanel.setBackground(new Color(0, 0, 0, 150));
		shapesPanel.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
		
		listInstructions = new JLabel("<html>1.Players take turns placing one hexagon at a time. <br>"
				+"&nbsp&nbsp&nbsp Red starts and must place a hexagon edge-to-edge <br> &nbsp&nbsp&nbsp(not corner to corner) with the initial "
				+ "black hexagon but <br>  &nbsp&nbsp&nbsp may not connect with the initial red hexagon. <br> 2.Subsequent hexagons may"
				+ " be placed anywhere, so long <br> &nbsp&nbsp&nbsp as they connect with a hexagon already on the board."
				+ "<br>3.If all 38 hexagons have been placed without either player<br>  &nbsp&nbsp&nbsp winning, game continues with the "
				+ "players taking turns <br>  &nbsp&nbsp&nbsp moving a previously-placed hexagon of their color<br>  &nbsp&nbsp&nbsp to a new location "
				+ "of their choosing. <br>4.If moving one hexagon creates two or more separated <br> &nbsp&nbsp&nbsp groups then the "
				+ "smaller group of hexagons will be <br>  &nbsp&nbsp&nbsp removed in the game. Thus, minimizing the number <br> &nbsp&nbsp&nbsp of total"
				+ " tiles in the game. <br> 5.The winner is the first player to create one of the <br> &nbsp&nbsp&nbsp winning shapes with his tiles."
				+ "When one player has six<br>&nbsp&nbsp&nbsp or more tiles he wins if the other player has less than six. <br>&nbsp&nbsp&nbsp And a draw happens if both players have less"
				+ " than six tiles.</html>");
		listInstructions.setBounds(25, 120, 650, 540);
		listInstructions.setForeground(Color.WHITE);
		listInstructions.setFont(new Font("Calibri", Font.BOLD, 22));
		
		back = new ButtonLabel("src/images/back2.png","src/images/back.png",200,450);
		
	}
	
	public void addComponents() {
		
		add(headingL);
		
		add(instructionsPanel);
		instructionsPanel.add(instructionsL);
		instructionsPanel.add(listInstructions);
		
		add(shapesPanel);
		shapesPanel.add(shapesL);
		shapesPanel.add(winningShapes);
		shapesPanel.add(back);
		
	}
	
	@Override
	protected void paintComponent(Graphics g) {
	
		try{
			
			Image bgImage = ImageIO.read(new File("src/images/backgroundFade.png"));
			super.paintComponent(g);
			g.drawImage(bgImage, 0, 0, null);
			
		} catch(Exception e) {;}
		
	
	}
	
}