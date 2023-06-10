package Classify.src;
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

public class ClassificationGame extends JPanel implements KeyListener, Runnable {
    static Queue<Integer> queue = new LinkedList<Integer>(); // 0 RED 1 BLUE

    Toolkit toolkit = Toolkit.getDefaultToolkit();
    Image bulletImage = toolkit.getImage("src/Classify/img/Bullet.png");
    Image scopeImage = toolkit.getImage("src/Classify/img/Scope.png");

    private static JButton leftButton;
    private static JButton rightButton;

    final static int screenWidth = 1280;
    final static int screenHeight = 720;
    private JFrame frame;
    static int score = 0;
    static int timeLeft = 60; // 게임 시간 (초)

    public ClassificationGame() {
        setPreferredSize(new Dimension(screenWidth, screenHeight));
        setBackground(Color.WHITE);
        setFocusable(true);
        addKeyListener(this);
        
        for (int i = 0; i < 7; i++)
            queue.add((int) (Math.random() * 100));

        frame = new JFrame("ClassificationGame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(screenWidth + 300, screenHeight + 200);
        frame.setResizable(false);

        // button initialize
        leftButton = new JButton("RED");
        rightButton = new JButton("BLUE");

        leftButton.setBounds(50, screenHeight - 150, 100, 100);
        rightButton.setBounds(screenWidth - 150, screenHeight - 150, 100, 100);
        frame.setUndecorated(true);
        frame.getContentPane().add(leftButton);
        frame.getContentPane().add(rightButton);
        
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
        	new Kiosk.Main_Frame();
            frame.dispose();
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

            // Repaint the panel to update the time left
            repaint();
        }

        // Game over logic here
        System.out.println("Game Over");
    }
}
