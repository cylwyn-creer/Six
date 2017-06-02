package six.gui;

import javax.swing.JLabel;
import javax.swing.ImageIcon;

import java.lang.Exception;

@SuppressWarnings("serial")
public class SixLogo extends JLabel implements Runnable {
	
	private Thread bannerAnimation;
	private boolean action = true;
	private int animationIndex = 0;
	private int animationSpeed = 1000;

	
	private String[] bannerURL = new String[2];
								  
	public SixLogo(String bannerURL1, String bannerURL2) {
		
		bannerURL[0] = bannerURL1;
		bannerURL[1] = bannerURL2;
		setProperties();
		
	}
	
	public void setProperties() {
		
		setIcon(new ImageIcon(bannerURL[0]));
		setBounds(50, 110, 530, 401);
		
	}
	
	public void run()
	{
		try {
			
			while(action != false) {
				
				setIcon(new ImageIcon(bannerURL[animationIndex++]));
				
				if(animationIndex == 2) {
					
					animationIndex = 0;
					
				}
				
				Thread.sleep(animationSpeed);
			}
			
		} catch(Exception e) {
			
			e.printStackTrace();
			
		}
	}
	
	public void start() {
		
		if(bannerAnimation == null) {
			
			bannerAnimation = new Thread(this);
			bannerAnimation.start();
			
		}
		
	}

}