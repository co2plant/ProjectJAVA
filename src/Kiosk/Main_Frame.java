package Kiosk;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Main_Frame extends JFrame implements KeyListener{
	final int width = 1280; final int height = 720;
	final int width_center = width/2; final int height_center = height/2;
	//이미지 아이콘---------------------------------------------------
	ImageIcon Box_shooting;
	ImageIcon Box_pingpong;
	ImageIcon Box_classify;
	ImageIcon Box_puzzle;
	ImageIcon Box_tictactoe;
	ImageIcon font;
	//---------------------------------------------------
	
	
	//이미지 버튼---------------------------------------------------
	JLabel button_shooting;
	JLabel button_pingpong;
	JLabel button_classify;
	JLabel button_puzzle;
	JLabel button_tictactoe;
	//---------------------------------------------------
	
	//패널---------------------------------------------------
	JPanel main = new JPanel();
	//---------------------------------------------------
	Toolkit tk = Toolkit.getDefaultToolkit();
	public Main_Frame()
	{
		
		//프레임 세팅
		setSize(width, height);
        setTitle("Kiosk");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screen = tk.getScreenSize();
    	int f_xpos = (int)(screen.getWidth() / 2 - width / 2);
    	int f_ypos = (int)(screen.getHeight() / 2 - height / 2);
    	setUndecorated(true);
    	setLocation(f_xpos, f_ypos);
    	setResizable(false);
    	setBackground(Color.white);
    	addKeyListener(this);
    	//---------------
    	
    	
    	
    	image_set();//이미지 세팅

    	main_panel_set();//패널 세팅

    	button_set();//버튼 이벤트리스너 할당

    	
        setVisible(true);
	}
	
	private void main_panel_set()
	{
		main.setVisible(true);
		main.setLayout(null);
		JLabel font_text = new JLabel(font);
		font_text.setBounds(0, height-19, 1052,19);
		
    	button_shooting = new JLabel(Box_shooting);
    	button_shooting.setBounds(width_center-600, height_center+50, 200,200);
    	
    	button_puzzle = new JLabel(Box_puzzle);
    	button_puzzle.setBounds(width_center-350, height_center+50, 200,200);
    	
    	button_pingpong = new JLabel(Box_pingpong);
    	button_pingpong.setBounds(width_center-100, height_center+50, 200,200);
    	
    	button_tictactoe = new JLabel(Box_tictactoe);
    	button_tictactoe.setBounds(width_center+150, height_center+50, 200,200);
    	
    	button_classify = new JLabel(Box_classify);
    	button_classify.setBounds(width_center+400, height_center+50, 200,200);
    	
    	
    	main.add(button_shooting);
    	main.add(button_puzzle);
    	main.add(button_pingpong);
    	main.add(button_tictactoe);
    	main.add(button_classify);
    	main.add(font_text);
    	add(main);
	}

	
	
	private void image_set()
	{
		Box_shooting = new ImageIcon("Image/box_shooting.png");
		Box_pingpong = new ImageIcon("Image/box_pingpong.png");
		Box_classify = new ImageIcon("Image/box_classify.png");
		Box_puzzle = new ImageIcon("Image/box_puzzle.png");
		Box_tictactoe = new ImageIcon("Image/box_tictactoe.png");
		font = new ImageIcon("Image/font.png");
	}
	
	private void button_set()
	{
		button_shooting.addMouseListener(new MouseListener() {

			@Override
            public void mousePressed(MouseEvent e) {
				new Shooting.Main_class();
				dispose();
			}
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseClicked(MouseEvent e) {}
        });
		
		button_pingpong.addMouseListener(new MouseListener() {
			@Override
            public void mousePressed(MouseEvent e) {
				new Pingpong.PingPongGame();
				dispose();
			}
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseClicked(MouseEvent e) {}
        });
	
		button_puzzle.addMouseListener(new MouseListener() {
			@Override
            public void mousePressed(MouseEvent e) {
				new Puzzle.game();
				dispose();
			}
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseClicked(MouseEvent e) {}
        });
		
		button_tictactoe.addMouseListener(new MouseListener() {
			@Override
            public void mousePressed(MouseEvent e) {
				new tictactoe.TicTacToeGame();
				dispose();
			}
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseClicked(MouseEvent e) {}
        });
		
		button_classify.addMouseListener(new MouseListener() {
			@Override
            public void mousePressed(MouseEvent e) {
				new Classify.src.ClassificationGame();
				dispose();
			}
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseClicked(MouseEvent e) {}
        });
		
		
	}
	
	public void keyPressed(KeyEvent e){
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			System.exit(0);
        }
	}

	public static void main(String[] args)
	{
		new Main_Frame();
	}

	@Override
	public void keyReleased(KeyEvent arg0) {}

	@Override
	public void keyTyped(KeyEvent arg0) {}

}
