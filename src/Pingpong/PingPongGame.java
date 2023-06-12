package Pingpong;
import javax.swing.*;

import Kiosk.CSV_manager;
import Kiosk.GameOver;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class PingPongGame extends JFrame implements ActionListener {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int BALL_SIZE = 20;
    private static final int PADDLE_WIDTH = 10;
    private static final int PADDLE_HEIGHT = 80;
    private static final int PADDLE_SPEED = 5;
    private static final int CPU_MOVE_INTERVAL = 20;

    private boolean already_exit = false;
    private JPanel gamePanel;
    private Timer timer;
    private int ballX, ballY;
    private int paddle1Y, paddle2Y;
    private int ballXSpeed, ballYSpeed;
    private boolean up1Pressed, down1Pressed;
    private int player1Score, player2Score;
    private Random random;
    CSV_manager CSV = new CSV_manager();
    public PingPongGame() {
        setTitle("Ping Pong Game");
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setUndecorated(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screen = tk.getScreenSize();
    	int f_xpos = (int)(screen.getWidth() / 2 - WIDTH / 2);
    	int f_ypos = (int)(screen.getHeight() / 2 - HEIGHT / 2);
    	setLocation(f_xpos, f_ypos);
        random = new Random();
        gamePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                g.setColor(Color.WHITE);
                g.setFont(new Font("Arial", Font.BOLD, 16));
                g.drawString("Player 1: " + player1Score, 50, 50);
                g.drawString("Player 2: " + player2Score, WIDTH - 120, 50);

                g.setColor(Color.GREEN);
                g.fillRect(ballX, ballY, BALL_SIZE, BALL_SIZE);
                g.fillRect(PADDLE_WIDTH, paddle1Y, PADDLE_WIDTH, PADDLE_HEIGHT);
                g.fillRect(WIDTH + PADDLE_WIDTH * 77, paddle2Y, PADDLE_WIDTH, PADDLE_HEIGHT);
            }
        };

        gamePanel.setBackground(Color.BLACK);
        add(gamePanel);

        ballX = WIDTH / 2 - BALL_SIZE / 2;
        ballY = HEIGHT / 2 - BALL_SIZE / 2;
        paddle1Y = HEIGHT / 2 - PADDLE_HEIGHT / 2;
        paddle2Y = HEIGHT / 2 - PADDLE_HEIGHT / 2;
        ballXSpeed = 2;
        ballYSpeed = 2;
        up1Pressed = false;
        down1Pressed = false;
        player1Score = 0;
        player2Score = 0;

        timer = new Timer(5, this);
        timer.start();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    up1Pressed = true;
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    down1Pressed = true;
                }
                
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                	already_exit = true;
                	new Kiosk.Main_Frame();
                    dispose();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    up1Pressed = false;
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    down1Pressed = false;
                }
            }
        });

        setVisible(true);

        startCPUPlayer();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    	if(already_exit == false) {
    	
        if (ballX >= WIDTH - BALL_SIZE - PADDLE_WIDTH * 2) {
            if (ballY + BALL_SIZE >= paddle2Y && ballY <= paddle2Y + PADDLE_HEIGHT) {
                ballXSpeed = -ballXSpeed;
            } else {
                player1Score++;
                resetBall();
            }
        } else if (ballX <= PADDLE_WIDTH * 2) {
            if (ballY + BALL_SIZE >= paddle1Y && ballY <= paddle1Y + PADDLE_HEIGHT) {
                ballXSpeed = -ballXSpeed;
            } else {
                player2Score++;
                resetBall();
            }
        }

        if (player2Score >= 1) {
        	CSV.CSV_Write("PingPong", Integer.toString(player1Score));
        	player2Score = 0;
        	already_exit = true;
            dispose();
            new GameOver("Score : "+Integer.toString(player1Score));
        }

        if (ballY >= HEIGHT - BALL_SIZE || ballY <= 0) {
            ballYSpeed = -ballYSpeed;
        }

        if (up1Pressed && paddle1Y > 0) {
            paddle1Y -= PADDLE_SPEED;
        }
        if (down1Pressed && paddle1Y < HEIGHT - PADDLE_HEIGHT) {
            paddle1Y += PADDLE_SPEED;
        }

        ballX += ballXSpeed;
        ballY += ballYSpeed;
        
        gamePanel.repaint();
    	}
    }

    private void resetBall() {
        ballX = WIDTH / 2 - BALL_SIZE / 2;
        ballY = HEIGHT / 2 - BALL_SIZE / 2;
        ballXSpeed = -ballXSpeed;
        ballYSpeed = -ballYSpeed;
    }

    private void resetGame() {
        player1Score = 0;
        player2Score = 0;
        resetBall();
        startCPUPlayer();
    }

    private void startCPUPlayer() {
        Timer cpuTimer = new Timer(CPU_MOVE_INTERVAL, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(already_exit)
            		return;
                // CPU logic to move the paddle automatically
                int paddle2Center = paddle2Y + PADDLE_HEIGHT / 2;
                int ballCenter = ballY + BALL_SIZE / 2;

                if (paddle2Center < ballCenter && paddle2Y < HEIGHT - PADDLE_HEIGHT) {
                    paddle2Y += PADDLE_SPEED;
                } else if (paddle2Center > ballCenter && paddle2Y > 0) {
                    paddle2Y -= PADDLE_SPEED;
                }
                
            }
        });
        if(already_exit == false)
        	cpuTimer.start();
    }


}
