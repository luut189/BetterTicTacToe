import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class GUI extends JFrame implements ActionListener {
    
    public JButton[] box = new JButton[9];
    public JButton reset;
    public JLabel winner;

    private JPanel board, function;

    GUI(String title, int width, int height) {
        GameHandler.initBoard();

        board = new JPanel();
        board.setPreferredSize(new Dimension(width, height));
        board.setBackground(Color.black);
        board.setLayout(new GridLayout(3,3));

        function = new JPanel();
        function.setPreferredSize(new Dimension(width, 30));
        function.setBackground(Color.black);
        function.setLayout(new BorderLayout());

        for(int i = 0; i < box.length; i++) {
            box[i] = new JButton();
            box[i].setForeground(Color.white);
            box[i].setBackground(Color.black);
            box[i].setFont(new Font("Cambria", Font.PLAIN, 70));
            box[i].addActionListener(this);
            box[i].setFocusable(false);
            board.add(box[i]);
        }

        winner = new JLabel("");
        winner.setFont(new Font("Cambria", Font.PLAIN, 25));
        winner.setBackground(Color.black);
        winner.setForeground(Color.white);
        winner.setFocusable(false);

        reset = new JButton("Reset");
        reset.setForeground(Color.white);
        reset.setBackground(Color.black);
        reset.addActionListener(this);
        reset.setFocusable(false);

        function.add(winner, BorderLayout.WEST);
        function.add(reset, BorderLayout.EAST);
        
        this.setTitle(title);
        this.setLayout(new BorderLayout());

        this.add(board, BorderLayout.NORTH);
        this.add(function, BorderLayout.SOUTH);
        this.pack();
        this.getContentPane().setBackground(Color.black);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        this.setVisible(true);

        while(true) {
            GameHandler.fillSquare(box);
        }
    }

    private void setWinner() {
        int result = GameHandler.checkWinner();
        if(result != 0) {
            for(int i = 0; i < box.length; i++) {
                box[i].setEnabled(false);
            }
            winner.setText((result == 3) ? "Tie" : (result == 1) ? "Player won" : "Bot won");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object n = e.getSource();
        for(int i = 0; i < box.length; i++) {
            if(n == box[i]) {
                if(GameHandler.isEmpty(i)) {
                    GameHandler.setBoardValue(1, i);
                    System.out.println("Player placed: " + (i+1));

                    if(GameHandler.checkWinner() == 0) {
                        GameHandler.botMove();
                    }

                    setWinner();
                    System.out.println(GameHandler.turn);
                } else {
                    System.out.println("Taken");
                }
            }
        }

        if(n == reset) {
            GameHandler.initBoard();
            for(int i = 0; i < box.length; i++) {
                box[i].setEnabled(true);
            }
        }
    }
}
