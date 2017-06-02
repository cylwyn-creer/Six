package six.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import six.object.Board1;
import six.object.Board2;

public class Winner extends JDialog {
	
	public static int winner = -1;
	
	private JLabel heading;
	private JLabel restart, menu;
	
	private ButtonHandler handler;
	private Sound sound;
	
	public Winner() {
	
		setLayout(null);
		setSize(400, 400);
		setLocationRelativeTo(null);
		setFocusableWindowState(false);
		setFocusable(false);
		setUndecorated(true);
		setAlwaysOnTop(true);
		setBackground(new Color(255, 255, 255, 100));
		
		initComponents();
		addComponents();
	
	}
	
	public void initComponents() {
		
		sound = new Sound();
		handler = new ButtonHandler();
		
		heading = new JLabel();
		heading.setBounds(0, 0, 500, 281);
		
		
		restart = new JLabel(new ImageIcon("src/images/restart.png"));
		restart.setBounds(40, 250, 330, 66);
		
		menu = new JLabel(new ImageIcon("src/images/mainMenu.png"));
		menu.setBounds(40, 300, 330, 66);
		
	}
	
	public void addComponents() {
		
		restart.addMouseListener(handler);
		menu.addMouseListener(handler);
		
		add(heading);
		add(restart);
		add(menu);
		
	}
	
	public void broadCastWinner() {

		sound.playWin();
		
		if(winner == 0)
			heading.setIcon(new ImageIcon("src/images/redWins.png"));
		else if(winner == 1)
			heading.setIcon(new ImageIcon("src/images/blackWins.png"));
		else if(winner == 2)
			heading.setIcon(new ImageIcon("src/images/draw.png"));
		
	}
	
	public void setVisibility() { 
		
		this.setVisible(false);
		
	}
	
	private class ButtonHandler extends MouseAdapter {
		
		public void mouseEntered(MouseEvent e) {
			
			if(e.getSource() == restart)
				restart.setIcon(new ImageIcon("src/images/restart2.png"));
			else if(e.getSource() == menu)	
				menu.setIcon(new ImageIcon("src/images/mainMenu2.png"));
			
		}
		
		public void mouseExited(MouseEvent e) {
			
			if(e.getSource() == restart)
				restart.setIcon(new ImageIcon("src/images/restart.png"));
			else if(e.getSource() == menu) 
				menu.setIcon(new ImageIcon("src/images/mainMenu.png"));
			
		}
		
		public void mouseClicked(MouseEvent e) {
			
			sound.playClick();
			
			if(e.getSource() == restart) {
				
				setVisibility();
				Menu.numOfTiles1 = 18;
				Menu.numOfTiles2 = 18;
				Menu.lower.removeAll();
				Menu.lower.revalidate();
				Menu.lower.repaint();
				
				Menu.label[6].setText(Menu.numOfTiles1+"");
				Menu.label[7].setText(Menu.numOfTiles2+"");
				
				if(Menu.isOnePlayer) {
					
					Menu.onePlayerRed();
					
				} else {
					
					Menu.twoPlayerBoard = new Board2();
					Menu.lower.add(Menu.twoPlayerBoard);
					
				}
				
			} else if(e.getSource() == menu) {
				
				setVisibility();
				Menu.numOfTiles1 = 18;
				Menu.numOfTiles2 = 18;
				Menu.upper.removeAll();
				Menu.upper.revalidate();
				Menu.upper.repaint();
				Menu.lower.removeAll();
				Menu.lower.revalidate();
				Menu.lower.repaint();
				Menu.panel.removeAll();
				Menu.panel.revalidate();
				Menu.panel.repaint();
				
				Menu.label[6].setText(Menu.numOfTiles1+"");
				Menu.label[7].setText(Menu.numOfTiles2+"");
				
				Menu.back();

			}
			
		}
		
	}
	
}