package six.gui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

public class SplashScreen extends JFrame{
	
	private JLabel image;
	private static JProgressBar progressBar;
	private Thread t = null;
	
	public SplashScreen() {
		
		setSize(520, 393);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setUndecorated(true);
		setBackground(new Color(0, 0, 0, 0));
		setLayout(null);
		
		image = new JLabel(new ImageIcon("src/images/splashScreen.png"));
		image.setBounds(0, 0, 520, 393);
		add(image);
		
		progressBar = new JProgressBar();
		progressBar.setMinimum(0);
		progressBar.setMaximum(100);
		progressBar.setStringPainted(true);
		progressBar.setForeground(Color.BLACK);
		progressBar.setPreferredSize(new Dimension(470, 30));
		progressBar.setBounds(40, 320, 451, 20);
		progressBar.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		add(progressBar);
		
		t = new Thread() {
			public void run() {
				int a = 0;
				
				while(a <= 100) {
					progressBar.setValue(a);
					try {
						sleep(8);
					}
					catch(InterruptedException e) {
						e.printStackTrace();
					}
					a++;
				}
			}
		};
		t.start();
	}

}
