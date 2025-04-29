package view;

import controller.GameController;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class InitialPageView extends JFrame {
    private JLabel titleLabel;
    private JButton normalGameStart;
    private JButton vsComputerGameStart;
    private JButton tutorialMode;
    private static final int WIDTH = 500;
    private static final int HEIGHT = 640;
    private static final int BUTTON_FONT_SIZE = 25;
    private static final int TITLE_FONT_SIZE = 45;
    private static final int BUTTON_WIDTH = 240;
    private static final int BUTTON_HEIGHT = 70;
    private static final Color FOREGROUND_COLOR = Color.WHITE;
    private static final Color BACKGROUND_COLOR = Color.BLACK;

    private BufferedImage background;

    public InitialPageView() {
        // Set up the JFrame
        setTitle("Nine Man Morris");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null); // Center the JFrame on the screen

        // Create a panel to hold the components
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // retrieve the image of the background and set the background of the board
                try {
                    background = ImageIO.read(getClass().getResource("/images/board_background.png"));
                } catch (IOException e) {
                }
                g.drawImage(background, 0, 0, null); // image full size
            }
        };
        panel.setLayout(new BorderLayout());

        // Create the title label
        titleLabel = new JLabel("Nine Man Morris");
        titleLabel.setFont(new Font("Comic Sans MS", Font.BOLD, TITLE_FONT_SIZE));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(FOREGROUND_COLOR);
        panel.add(titleLabel, BorderLayout.CENTER);

        // Create the buttons panel
        JPanel buttonPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // retrieve the image of the background and set the background of the board
                try {
                    background = ImageIO.read(getClass().getResource("/images/board_background.png"));
                } catch (IOException e) {
                }
                g.drawImage(background, 0, 0, null); // image full size
            }
        };

        // Create constraints to control the button panel's alignment
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 100;
        gbc.gridy = 100;
        gbc.insets = new Insets(0, 0, 50, 0);

        // Set text for the button
        normalGameStart = new JButton("Game Start!");
        vsComputerGameStart = new JButton("VS Computer!");
        tutorialMode = new JButton("Tutorial First!");
        normalGameStart.setFont(new Font("Comic Sans MS", Font.BOLD, BUTTON_FONT_SIZE));
        vsComputerGameStart.setFont(new Font("Comic Sans MS", Font.BOLD, BUTTON_FONT_SIZE));
        tutorialMode.setFont(new Font("Comic Sans MS", Font.BOLD, BUTTON_FONT_SIZE));

        // Set size of the button
        normalGameStart.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        vsComputerGameStart.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        tutorialMode.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));

        // Set appealing of the button
        normalGameStart.setForeground(FOREGROUND_COLOR);
        normalGameStart.setBackground(BACKGROUND_COLOR);
        vsComputerGameStart.setForeground(FOREGROUND_COLOR);
        vsComputerGameStart.setBackground(BACKGROUND_COLOR);
        tutorialMode.setForeground(FOREGROUND_COLOR);
        tutorialMode.setBackground(BACKGROUND_COLOR);


        // Add action listeners to the buttons
        normalGameStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openGameView(false);
            }
        });

        tutorialMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameController.getInstance().startTutorial();
            }
        });

        vsComputerGameStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openGameView(true);
            }
        });

        // Add the buttons to the panel
        buttonPanel.add(normalGameStart, gbc);
        gbc.gridy++;
        buttonPanel.add(vsComputerGameStart, gbc);
        gbc.gridy++;
        buttonPanel.add(tutorialMode, gbc);

        // Add the button panel to the main panel
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Set the main panel as the content pane of the JFrame
        setContentPane(panel);

        // Display the JFrame
        setVisible(true);
    }

    private void openGameView(boolean playWithComputer) {
        // start game
        GameController gameController = GameController.getInstance();
        gameController.startGame(playWithComputer);
    }
}
