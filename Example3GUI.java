import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.ThreadLocalRandom;

public class Example3GUI {
    private JFrame frame;
    private JTextField guessField;
    private JLabel feedbackLabel;
    private JLabel attemptsLabel;
    private JButton guessButton;
    private JButton newGameButton;

    private int target;
    private int attempts;

    public Example3GUI() {
        initComponents();
        newGame();
    }

    private void initComponents() {
        frame = new JFrame("Guessing Game");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(360, 180);
        frame.setLayout(new BorderLayout(8, 8));

        JPanel top = new JPanel(new BorderLayout(4, 4));
        JLabel instr = new JLabel("I'm thinking of a number between 1 and 100.");
        top.add(instr, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        guessField = new JTextField(8);
        guessButton = new JButton("Guess");
        inputPanel.add(new JLabel("Your guess:"));
        inputPanel.add(guessField);
        inputPanel.add(guessButton);
        top.add(inputPanel, BorderLayout.CENTER);

        frame.add(top, BorderLayout.NORTH);

        feedbackLabel = new JLabel(" ");
        feedbackLabel.setFont(feedbackLabel.getFont().deriveFont(Font.PLAIN, 14f));
        frame.add(feedbackLabel, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        attemptsLabel = new JLabel("Attempts: 0");
        newGameButton = new JButton("New Game");
        bottom.add(attemptsLabel);
        bottom.add(newGameButton);
        frame.add(bottom, BorderLayout.SOUTH);

        // Actions
        guessButton.addActionListener(e -> doGuess());
        guessField.addActionListener(e -> doGuess());
        newGameButton.addActionListener(e -> newGame());

        // Center on screen
        frame.setLocationRelativeTo(null);
    }

    private void newGame() {
        target = ThreadLocalRandom.current().nextInt(1, 101);
        attempts = 0;
        attemptsLabel.setText("Attempts: 0");
        feedbackLabel.setText("Enter a number and press Guess.");
        guessField.setText("");
        guessField.setEnabled(true);
        guessButton.setEnabled(true);
        guessField.requestFocusInWindow();
    }

    private void doGuess() {
        String text = guessField.getText().trim();
        int guess;
        try {
            guess = Integer.parseInt(text);
        } catch (NumberFormatException ex) {
            feedbackLabel.setText("Please enter a valid integer (1-100).");
            return;
        }

        if (guess < 1 || guess > 100) {
            feedbackLabel.setText("Guess must be between 1 and 100.");
            return;
        }

        attempts++;
        attemptsLabel.setText("Attempts: " + attempts);

        if (guess < target) {
            feedbackLabel.setText("Too low.");
        } else if (guess > target) {
            feedbackLabel.setText("Too high.");
        } else {
            feedbackLabel.setText("Correct! The number was " + target + ".");
            JOptionPane.showMessageDialog(frame,
                    "Correct! You guessed the number in " + attempts + " attempts.",
                    "You win!",
                    JOptionPane.INFORMATION_MESSAGE);
            guessField.setEnabled(false);
            guessButton.setEnabled(false);
        }
        guessField.selectAll();
        guessField.requestFocusInWindow();
    }

    public void show() {
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Example3GUI gui = new Example3GUI();
            gui.show();
        });
    }
}
