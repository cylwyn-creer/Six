package six.gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import six.object.Board1;
import six.object.Board2;

@SuppressWarnings("serial")
public class ButtonLabel extends JLabel implements MouseListener {
	
	private ImageIcon[] icon = new ImageIcon[3];
	private Sound sound;
	private String enter;
	
	public ButtonLabel(String enter, String exit,int x, int y){
		
		this.enter = enter;
		
		sound = new Sound();
		
		icon[0] = new ImageIcon(enter);
		icon[1] = new ImageIcon(exit);
		setIcon(icon[1]);
		setBounds(x,y,icon[1].getIconWidth(),icon[1].getIconHeight());
		addMouseListener(this);
		
	}

	@SuppressWarnings("deprecation")
	@Override
	public void mouseClicked(MouseEvent e) {
		
		sound.playClick();
		if(enter.equals("src/images/newgame1.png")){
			
			setIcon(icon[1]);
			Menu.removePanel();
			Menu.newGame();
			
		}else if(enter.equals("src/images/how2play1.png")){
			
			setIcon(icon[1]);
			Menu.removePanel();
			Menu.how2play();
			
		}else if(enter.equals("src/images/about1.png")){
			
			setIcon(icon[1]);
			Menu.removePanel();
			Menu.about();
			
		}else if(enter.equals("src/images/exit1.png")){
			
			setIcon(icon[1]);
			Menu.removePanel();
			Menu.exit();
			
		}else if(enter.equals("src/images/oneplayer1.png")){
			
			setIcon(icon[1]);
			Menu.isOnePlayer = true;
			Menu.removePanel();
			Menu.onePlayerRed();
			
		}else if(enter.equals("src/images/twoplayer1.png")){
			
			setIcon(icon[1]);
			Menu.isOnePlayer = false;
			Menu.removePanel();
			Menu.chooseTile();
			
		}else if(enter.equals("src/images/redTile2.PNG")){
			
			setIcon(icon[1]);
			Menu.removePanel();
			
			Menu.twoPlayerRed();
			
		}else if(enter.equals("src/images/blackTile2.png")){
			
			setIcon(icon[1]);
			Menu.removePanel();
			
			Menu.twoPlayerBlack();
			
		}else if(enter.equals("src/images/back2.png")){
			
			setIcon(icon[1]);
			Menu.removePanel();
			Menu.back();
			
		}else if(enter.equals("src/images/back2a.png")){
			
			setIcon(icon[1]);
			Menu.removePanel();
			Menu.back2a();
			
		}else if(enter.equals("src/images/rsz_yes1.png")){
			
			System.exit(0);
			
		}else if(enter.equals("src/images/rsz_1no1.png")){
			
			setIcon(icon[1]);
			Menu.removePanel();
			Menu.back();
			
		}else if(enter.equals("src/images/mainMenu2.png")){
			
			setIcon(icon[1]);
			Menu.removeUpper();
			Menu.removeLower();
			Menu.removePanel();
			Menu.numOfTiles1 = 18;
			Menu.numOfTiles2 = 18;
			
			Menu.label[6].setText(Menu.numOfTiles1+"");
			Menu.label[7].setText(Menu.numOfTiles2+"");
			
			Menu.back();
			
		}else if(enter.equals("src/images/restart2.png")){
			
			setIcon(icon[1]);
			Menu.removeLower();
			Menu.numOfTiles1 = 18;
			Menu.numOfTiles2 = 18;
			
			Menu.label[6].setText(Menu.numOfTiles1+"");
			Menu.label[7].setText(Menu.numOfTiles2+"");
			
			if(Menu.isOnePlayer) {
				
				Menu.onePlayerRed();
				
			} else {
				
				Menu.twoPlayerBoard = new Board2();
				Menu.lower.add(Menu.twoPlayerBoard);
				
			}
			
		}
		
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		
		setIcon(icon[0]);
		revalidate();
		repaint();
		Menu.panel.revalidate();
		Menu.panel.repaint();
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
		setIcon(icon[1]);
		revalidate();
		repaint();
		Menu.panel.revalidate();
		Menu.panel.repaint();
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
		setIcon(icon[1]);
		revalidate();
		repaint();
		Menu.panel.revalidate();
		Menu.panel.repaint();
		
	}
	
}
