package six.core;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.plaf.OptionPaneUI;

import six.gui.Exit;
import six.gui.GUIFrame;
import six.gui.Menu;
import six.gui.SplashScreen;

public class Main {
	
	private GUIFrame frame;
	private SplashScreen splashScreen;
	
	public Main() throws InterruptedException {
		
		splashScreen = new SplashScreen();
		splashScreen.setIconImage(new ImageIcon("src/images/six1.png").getImage());
		splashScreen.setVisible(true);
		Thread.sleep(1000);
		splashScreen.dispose();
		
		frame = new GUIFrame();
		frame.setIconImage(new ImageIcon("src/images/six1.png").getImage());
		
	}

	public static void main(String[] args) throws InterruptedException {

		new Main();
		
	}

}
