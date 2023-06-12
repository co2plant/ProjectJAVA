package Classify.src;

import Kiosk.CSV_manager;
import Kiosk.Main_Frame;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.util.LinkedList;
import java.util.Queue;

//Author : co2plant

public class ClassificationGame extends JPanel implements KeyListener, Runnable {
     Queue<Integer> queue = new LinkedList<Integer>(); // 0 RED 1 BLUE

    Toolkit toolkit = Toolkit.getDefaultToolkit();
    
    //이미지 불러오기 점수를 취합하는 메인프레임과 다르게 툴킷을 사용함(메인은 이미지 아이콘)
    Image bulletImage = toolkit.getImage("src/Classify/img/LEFT.png");
    Image scopeImage = toolkit.getImage("src/Classify/img/RIGHT.png");
    //게임오버 이미지였으나 게임오버 클래스를 따로 생성 하므로 제거함.
    //Image gameoverImage = toolkit.getImage("src/Classify/img/GAMEOVER.png");
    

    //private JButton leftButton;
    //private JButton rightButton;
    
    //프레임
    private JFrame frame;
    //프레임 크기 조절용 Define
    static final int screenWidth = 1280;
    static final int screenHeight = 720;
    
    //
    private boolean already_exit = false;
    
    //내부에서 사용하는 스코어 변수
    int score = 0;
    int timeLeft = 30; // 게임 시간 (초)
    CSV_manager CSV = new CSV_manager();
    
    public ClassificationGame() {
        setPreferredSize(new Dimension(screenWidth, screenHeight));
        setBackground(Color.WHITE);
        setFocusable(true);
        addKeyListener(this);
        
        //카드 
        for (int i = 0; i < 7; i++)
            queue.add((int) (Math.random() * 100));

        frame = new JFrame("ClassificationGame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(screenWidth + 300, screenHeight + 200);
        frame.setResizable(false);

        // button initialize
        // Graphic갱신 시 버튼이 사라지며 버튼 이벤트 리스너 등록 시 키보드 갱신이 안되는 버그가 있어서 수
        /*
        leftButton = new JButton("RED");
        rightButton = new JButton("BLUE");

        leftButton.setBounds(50, screenHeight - 150, 100, 100);
        rightButton.setBounds(screenWidth - 150, screenHeight - 150, 100, 100);
        
        frame.getContentPane().add(leftButton);
        frame.getContentPane().add(rightButton);
        */
        frame.setUndecorated(true);
        frame.getContentPane().add(this);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        requestFocus();

        // Start the game thread
        Thread gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.fillRect((screenWidth - 480) / 2, 0, 480, screenHeight);
        g.setColor(Color.WHITE);
        g.drawString("Score : " + Integer.toString(score), (screenWidth - 100) / 2, 100);
        g.drawString("Time Left : " + Integer.toString(timeLeft), (screenWidth - 100) / 2, 120);

        int i = 0;
        for (int element : queue) {
            int localWidth = 200;
            int localHeight = 300;
            int x = (screenWidth - localWidth) / 2;
            int y = (screenHeight - 50 * queue.size()) + (i * 50);

            if (element > 50) {
                g.drawImage(bulletImage, x, y, localWidth, localHeight, this);
            } else {
                g.drawImage(scopeImage, x, y, localWidth, localHeight, this);
            }
            i++;
        }
    }
    

    private void discardCard(boolean isLEFT) {
        int tmp = queue.poll();
        if (tmp > 50 && isLEFT) {
            System.out.println("Success");
            queue.add((int) (Math.random() * 100));
            score += 10;
        } else if (tmp < 50 && !isLEFT) {
            System.out.println("Success");
            queue.add((int) (Math.random() * 100));
            score += 10;
        } else {
            System.out.println("Fail");
            queue.add((int) (Math.random() * 100));
            score -= 10;
        }

        // Check for collision with obstacles or goal
        // TODO: Implement collision detection logic

        // Repaint the panel to update the graphics
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_LEFT) {
            discardCard(true);
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            discardCard(false);
        }
        
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
        	already_exit = true;
        	frame.dispose();
        	new Main_Frame();
        }
        requestFocus(); // 키 입력을 계속 받도록 포커스 요청
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Not used
    }


    @Override
    public void run() {
        while (timeLeft > 0) {
            try {
                // Delay for 1 second
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Decrease the time left
            timeLeft--;
            if(already_exit)
            	return;
            // Repaint the panel to update the time left
            repaint();
        }
        if(already_exit == false) {
        CSV.CSV_Write("Classify",Integer.toString(score));
        // Game over logic here
        System.out.println("Game Over");
        frame.dispose();
        new Kiosk.GameOver("Score : "+Integer.toString(score));
        }
    }
}
