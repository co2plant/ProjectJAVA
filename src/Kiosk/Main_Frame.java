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

public class Main_Frame extends JFrame implements KeyListener{
	final int width = 1280; final int height = 720;
	final int width_center = width/2; final int height_center = height/2;
	final int Font_Size = 32;
	final int Puzzle_Font_Size = 20;
	//이미지 아이콘---------------------------------------------------
	ImageIcon Box_shooting;
	ImageIcon Box_pingpong;
	ImageIcon Box_classify;
	ImageIcon Box_puzzle;
	ImageIcon Box_tictactoe;
	ImageIcon font;
	ImageIcon best_record;
	ImageIcon shooting;
	ImageIcon pingpong;
	ImageIcon classify;
	ImageIcon puzzle;
	ImageIcon tictactoe;
	ImageIcon GameOver;
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
	
	//CSV 매니저---------------------------------------
	CSV_manager CSV = new CSV_manager();
	//-----------------------------------------------------
	
	//폰트-----------------------------------------------
	Font neo;
	Font Puzzle_neo;
	//-----------------------------------------------------
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
    	
    	neo = loadExternalFont(Font_Size); // 폰트 세팅
    	Puzzle_neo = loadExternalFont(Puzzle_Font_Size); // 폰트 세팅
    	image_set();//이미지 세팅

    	main_panel_set();//패널 세팅

    	button_set();//버튼 이벤트리스너 할당

    	draw_record();
    	
        setVisible(true);
	}
	
	private void main_panel_set()
	{
		main.setVisible(true);
		main.setLayout(null);
		JLabel font_text = new JLabel(font);
		font_text.setBounds(0, height-19, 1052,19);
		
		JLabel record = new JLabel(best_record);
		record.setBounds(width_center-330, 25, 661,76);
		
		//버튼 이미지 셋업--------------------------
    	button_shooting = new JLabel(Box_shooting);
    	button_shooting.setBounds(width_center-600, height_center+120, 200,200);
    	
    	button_puzzle = new JLabel(Box_puzzle);
    	button_puzzle.setBounds(width_center-350, height_center+120, 200,200);
    	
    	button_pingpong = new JLabel(Box_pingpong);
    	button_pingpong.setBounds(width_center-100, height_center+120, 200,200);
    	
    	button_tictactoe = new JLabel(Box_tictactoe);
    	button_tictactoe.setBounds(width_center+150, height_center+120, 200,200);
    	
    	button_classify = new JLabel(Box_classify);
    	button_classify.setBounds(width_center+400, height_center+120, 200,200);
    	//--------------------------------------
    	
    	//베스트 스코어 프레임 셋업-------------------
    	JLabel record_shoot = new JLabel(shooting);
		record_shoot.setBounds(width_center-510, 120, 200,300);
    	JLabel record_puzzle = new JLabel(puzzle);
		record_puzzle.setBounds(record_shoot.getLocation().x+200, 120, 200,300);
		JLabel record_ping = new JLabel(pingpong);
		record_ping.setBounds(record_puzzle.getLocation().x+200, 120, 200,300);
		JLabel record_tictactoe = new JLabel(tictactoe);
		record_tictactoe.setBounds(record_ping.getLocation().x+200, 120, 200,300);
		JLabel record_classify = new JLabel(classify);
		record_classify.setBounds(record_tictactoe.getLocation().x+200, 120, 200,300);
		//--------------------------------------
		
    	main.add(button_shooting);
    	main.add(button_puzzle);
    	main.add(button_pingpong);
    	main.add(button_tictactoe);
    	main.add(button_classify);
    	main.add(font_text);
    	main.add(record);
    	main.add(record_puzzle);
    	main.add(record_ping);
    	main.add(record_shoot);
    	main.add(record_classify);
    	main.add(record_tictactoe);
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
		best_record = new ImageIcon("Image/BEST_RECORD.png");
		shooting = new ImageIcon("Image/record_shooting.png");
		pingpong = new ImageIcon("Image/record_pingpong.png");
		classify = new ImageIcon("Image/record_classify.png");
		puzzle = new ImageIcon("Image/record_puzzle.png");
		tictactoe = new ImageIcon("Image/record_tic.png");
		GameOver = new ImageIcon("Image/GAMEOVER.png");
	}
	
	private void draw_record()
	{
		JLabel[] score = new JLabel[3];
		String[] score_string = read_record("Shooting");
		for(int i=0;i<3;i++)
		{
			score[i] = new JLabel(score_string[i]);
			score[i].setFont(neo);
			score[i].setBounds(240, 120+(i*70),100,100);
			score[i].setForeground(Color.WHITE);
			main.add(score[i]);
			main.setComponentZOrder(score[i], 0);
		}
		

		score_string = read_record("Puzzle");
		for(int i=0;i<3;i++)
		{
			score[i] = new JLabel(score_string[i]);
			score[i].setFont(Puzzle_neo);
			score[i].setBounds(390, 120+(i*70),1000,100);
			score[i].setForeground(Color.WHITE);
			main.add(score[i]);
			main.setComponentZOrder(score[i], 0);
		}

		score_string = read_record("PingPong");
		for(int i=0;i<3;i++)
		{
			score[i] = new JLabel(score_string[i]);
			score[i].setFont(neo);
			score[i].setBounds(650, 120+(i*70),100,100);
			score[i].setForeground(Color.WHITE);
			main.add(score[i]);
			main.setComponentZOrder(score[i], 0);
		}
		score_string = read_record("TicTacToe");
		for(int i=0;i<3;i++)
		{
			score[i] = new JLabel(score_string[i]);
			score[i].setFont(neo);
			score[i].setBounds(850, 120+(i*70),100,100);
			score[i].setForeground(Color.WHITE);
			main.add(score[i]);
			main.setComponentZOrder(score[i], 0);
		}

		score_string = read_record("Classify");
		for(int i=0;i<3;i++)
		{
			score[i] = new JLabel(score_string[i]);
			score[i].setFont(neo);
			score[i].setBounds(1025, 120+(i*70),100,100);
			score[i].setForeground(Color.WHITE);
			main.add(score[i]);
			main.setComponentZOrder(score[i], 0);
		}
	}
	
	private String[] read_record(String name)
	{
		String[] record_string = new String[3];
		List<String[]> record_list = new ArrayList<>();
		record_list = CSV.CSV_Reader(name);
		
		for(int i=0;i<record_list.size();i++)
		{
			record_string[i] = record_list.get(i)[1];
		}
		for(int i = record_list.size();i<3;i++)
			record_string[i] = "//";
			
		return record_string;
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
	
	private Font loadExternalFont(int Font_Size) 
	{
        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, new File("Font/neodgm.ttf"));
            font = font.deriveFont(Font.PLAIN, Font_Size);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);
            return font;
        } catch (FontFormatException | IOException e) {
            return new Font("Arial", Font.PLAIN, Font_Size);
        }
    }
	
	public void keyPressed(KeyEvent e)
	{
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			System.exit(0);
        }
	}


	@Override
	public void keyReleased(KeyEvent arg0) {}

	@Override
	public void keyTyped(KeyEvent arg0) {}

	public static void main(String[] args)
	{
		new Main_Frame();
	}
}
