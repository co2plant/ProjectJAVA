package tictactoe;
import javax.swing.*;

import Kiosk.CSV_manager;
import Kiosk.GameOver;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;

public class TicTacToeGame extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;

	private JButton[][] buttons;
    private char[][] board;
    private char currentPlayer;
    private JLabel turnLabel;
    private int scoreO;
    private int scoreX;
    private int gameCount;
    private JLabel scoreLabelO;
    private JLabel scoreLabelX;
    private JLabel resultLabel;
    private final int width = 900;
    private final int height = 700;
    Toolkit tk = Toolkit.getDefaultToolkit();
    CSV_manager CSV = new CSV_manager();
    private Random random;  // 추가(수정) 코드
 
    public TicTacToeGame() {
        setTitle("Tic Tac Toe");
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(width, height);
        setLayout(new BorderLayout());
        Dimension screen = tk.getScreenSize();
    	int f_xpos = (int)(screen.getWidth() / 2 - width / 2);
    	int f_ypos = (int)(screen.getHeight() / 2 - height / 2);
    	setLocation(f_xpos, f_ypos);
        buttons = new JButton[3][3];
        board = new char[3][3];
        currentPlayer = 'O';
        
        addWindowListener(new WindowAdapter() {
        	public void windowClosing(WindowEvent e)
        	{
        		dispose();
        		new Kiosk.Main_Frame();
        	}
		});

        Font font = new Font("Arial", Font.BOLD, 40);
        
        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(3, 3));

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col] = new JButton();
                buttons[row][col].setFont(font);
                gamePanel.add(buttons[row][col]);
                buttons[row][col].addActionListener(this);
            }
        }

        // 최상단에 보여지는 플레이어 턴
        turnLabel = new JLabel("Player " + currentPlayer + "'s turn");
        turnLabel.setHorizontalAlignment(SwingConstants.CENTER);
        turnLabel.setFont(new Font("Arial", Font.BOLD, 30));

        scoreO = 0;     // Player O의 점수판
        scoreX = 0;     // Player X의 점수판
        gameCount = 0;  // 게임은 3판(무승부 판 제외)

        scoreLabelO = new JLabel("Player O: " + scoreO);
        scoreLabelO.setFont(new Font(scoreLabelO.getFont().getName(), Font.BOLD, 30));
        scoreLabelX = new JLabel("Player X: " + scoreX);
        scoreLabelX.setFont(new Font(scoreLabelX.getFont().getName(), Font.BOLD, 30));
        
        resultLabel = new JLabel();
        resultLabel.setFont(new Font(resultLabel.getFont().getName(), Font.BOLD, 50));

        add(scoreLabelO, BorderLayout.EAST);
        add(scoreLabelX, BorderLayout.WEST);
        add(resultLabel, BorderLayout.SOUTH);

        add(turnLabel, BorderLayout.NORTH);
        add(gamePanel, BorderLayout.CENTER);

        setVisible(true);
    }




    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        int row = -1;
        int col = -1;

        // 2차원 배열에서 버튼의 위치 찾기
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j] == button) {
                    row = i;
                    col = j;
                    break;
                }
            }
        }

        // 정상 수행인 경우 수행되는 내용
        if (isValidMove(row, col)) {
            makeMove(row, col);
            button.setText(Character.toString(currentPlayer));
            button.setForeground(getPlayerColor(currentPlayer));
            if (isWinner()) {
                if (currentPlayer == 'O') {
                    scoreO++;
                } else {
                    scoreX++;
                }
                gameCount++;
                updateScoreLabel();

                    if (scoreX >= 1) {
                    CSV.CSV_Write("TicTacToe", Integer.toString(scoreO));
                    dispose();
					new GameOver("Score : "+scoreO);
					return;
                }

                resetGame();
            } else if (isBoardFull()) {
                JOptionPane.showMessageDialog(this, "It's a draw!");
                resetGame();
            } else {
                currentPlayer = (currentPlayer == 'O') ? 'X' : 'O';
                turnLabel.setText("Player " + currentPlayer + "'s turn");
                
                if (currentPlayer == 'X') {
                    makeComputerMove(); // 추가 코드: 컴퓨터의 움직임 처리
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "잘못된 선택입니다. 다시 클릭해주세요.");
        }
    }
    
    // 추가 코드
    private void makeComputerMove() {
    	boolean moveMade = false;
    	random = new Random(); 
        while (!moveMade) {
            int row = random.nextInt(3);
            int col = random.nextInt(3);
            if (isValidMove(row, col)) {
                makeMove(row, col);
                buttons[row][col].setText(Character.toString(currentPlayer));
                buttons[row][col].setForeground(getPlayerColor(currentPlayer));
                moveMade = true;
                if (isWinner()) {
                    if (currentPlayer == 'O') {
                        scoreO++;
                    } else {
                        scoreX++;
                    }
                    gameCount++;
                    updateScoreLabel();

                    if (scoreX >= 1) {
                        CSV.CSV_Write("TicTacToe", Integer.toString(scoreO));
                        dispose();
    					new GameOver("Score : "+scoreO);
    					return;
                    }

                    resetGame();
                } else if (isBoardFull()) {
                    JOptionPane.showMessageDialog(this, "It's a draw!");
                    resetGame();
                } else {
                    currentPlayer = (currentPlayer == 'O') ? 'X' : 'O';
                    turnLabel.setText("Player " + currentPlayer + "'s turn");
                }
            }
        }
    }

    private boolean isValidMove(int row, int col) {
        return (row >= 0 && row < 3 && col >= 0 && col < 3 && board[row][col] == '\u0000');
    }

    private void makeMove(int row, int col) {
        board[row][col] = currentPlayer;
    }

    // 가로, 세로, 대각선 중 하나라도 같은 문자로 통일되면 승리
    private boolean isWinner() {
        // 행 체크
        for (int row = 0; row < 3; row++) {
            if (board[row][0] == currentPlayer && board[row][1] == currentPlayer && board[row][2] == currentPlayer) {
                return true;
            }
        }

        // 열 체크
        for (int col = 0; col < 3; col++) {
            if (board[0][col] == currentPlayer && board[1][col] == currentPlayer && board[2][col] == currentPlayer) {
                return true;
            }
        }

        // 대각선 체크
        if (board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer) {
            return true;
        }
        if (board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer) {
            return true;
        }

        return false;
    }

    private boolean isBoardFull() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board[row][col] == '\u0000') {  // null값인 경우
                    return false;
                }
            }
        }
        return true;
    }

    private void resetGame() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].setText("");
                buttons[row][col].setEnabled(true);
                board[row][col] = '\u0000'; // null 값 
            }
        }
        currentPlayer = 'O';
        turnLabel.setText("Player " + currentPlayer + "'s turn");
    }

    private Color getPlayerColor(char player) {
        return (player == 'O') ? Color.BLUE : Color.RED;
    }

    // 점수판 업데이트
    private void updateScoreLabel() {
        scoreLabelO.setText("Player O: " + scoreO);
        scoreLabelX.setText("Player X: " + scoreX);
    }


}