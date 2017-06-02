package six.gui;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.Dimension;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class Exit extends JPanel
{
	private SixLogo label;
	private ImageIcon askExit;

	private ButtonLabel[] opt;
	
	private JLabel wantExit;
	
	public Exit() {
		
		setSize(1280, 720);
		setLayout(null);
		
		initComponents();
		addComponents();
		
		label.start();
		
	}
	
	public void initComponents() {
		
		label = new SixLogo("src/images/six1.png", "src/images/six2.png");
		
		wantExit = new JLabel(new ImageIcon("src/images/rsz_1wantExit.png"));
		wantExit.setBounds(600, 130, 636, 128);
		
		opt = new ButtonLabel[2];
		opt[0] = new ButtonLabel("src/images/rsz_yes1.png","src/images/rsz_yes2.png", 600, 250);
		opt[1] = new ButtonLabel("src/images/rsz_1no1.png","src/images/rsz_no2.png", 600, 350);
		
	}
	
	public void addComponents() {
		
		add(label);
		add(wantExit);
		add(opt[0]);
		add(opt[1]);
		
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