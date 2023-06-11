package Kiosk;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameOver extends JFrame implements KeyListener{
	
	final int width = 1280; final int height = 720;
	final int width_center = width/2; final int height_center = height/2;
	
	public JFrame frame;
	
	ImageIcon GameOver = new ImageIcon("Image/GAMEOVER.png");
	
	Toolkit tk = Toolkit.getDefaultToolkit();
	public GameOver() {
		frame = new JFrame("GameOver");
		frame.setSize(width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screen = tk.getScreenSize();
    	int f_xpos = (int)(screen.getWidth() / 2 - width / 2);
    	int f_ypos = (int)(screen.getHeight() / 2 - height / 2);
    	frame.setContentPane(new JLabel(GameOver));
    	frame.setUndecorated(true);
    	frame.setLocation(f_xpos, f_ypos);
    	frame.setResizable(false);
    	frame.addKeyListener(this);
    	frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        requestFocus();
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
        	new Kiosk.Main_Frame();
            frame.dispose();
        }
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
