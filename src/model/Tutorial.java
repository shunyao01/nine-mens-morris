package model;

import controller.GameController;

import javax.swing.*;

public class Tutorial {

    private static final int TUTORIAL_COUNT = 7;

    public static int getTutorialCount() {
        return TUTORIAL_COUNT;
    }

    public String runTutorial(int tutorialIndex) {
        String msg = switch (tutorialIndex) {
            case 0 -> set();
            case 1 -> move();
            case 2 -> remove();
            case 3 -> jump();
            case 4 -> winning1();
            case 5 -> winning2();
            case 6 -> end();
            default -> "";
        };
        return msg;
    }

    public String set() {
        // Reset Board and State
        Board board = GameController.getInstance().getBoard();
        board.resetBoard();
        GameController.getInstance().setCurrentState(GameState.SET);

        // Initialize Position: Empty Board

        // Specify message
        return "<html>SET: Game starts with 9 tokens and 24 positions. <br> Star's turn to start. Set a token. Then click next.<html>";
    }

    public String move() {
        // Reset Board and State
        GameController gameController = GameController.getInstance();
        Board board = gameController.getBoard();
        gameController.restart();
        gameController.getFirstPlayer().setTokensOnBoard(4);
        gameController.getFirstPlayer().setTokensToSet(0);
        gameController.getSecondPlayer().setTokensOnBoard(4);
        gameController.getSecondPlayer().setTokensToSet(0);

        // Initialize Position
        board.getNodeFromIndex(0).setTokenColor(TokenColor.WHITE);
        board.getNodeFromIndex(1).setTokenColor(TokenColor.WHITE);
        board.getNodeFromIndex(7).setTokenColor(TokenColor.WHITE);
        board.getNodeFromIndex(10).setTokenColor(TokenColor.WHITE);
        board.getNodeFromIndex(23).setTokenColor(TokenColor.BLACK);
        board.getNodeFromIndex(8).setTokenColor(TokenColor.BLACK);
        board.getNodeFromIndex(13).setTokenColor(TokenColor.BLACK);
        board.getNodeFromIndex(20).setTokenColor(TokenColor.BLACK);

        gameController.setCurrentState(GameState.MOVE);

        // Specify message
        return "<html>MOVE: Select and move a star token to an adjacent empty position.<html>";
    }

    public String remove() {
        // Reset Board and State
        GameController gameController = GameController.getInstance();
        Board board = gameController.getBoard();
        gameController.restart();
        gameController.getFirstPlayer().setTokensOnBoard(3);
        gameController.getFirstPlayer().setTokensToSet(0);
        gameController.getSecondPlayer().setTokensOnBoard(4);
        gameController.getSecondPlayer().setTokensToSet(0);

        // Initialize Position
        board.getNodeFromIndex(0).setTokenColor(TokenColor.WHITE);
        board.getNodeFromIndex(1).setTokenColor(TokenColor.WHITE);
        board.getNodeFromIndex(2).setTokenColor(TokenColor.WHITE);
        board.getNodeFromIndex(23).setTokenColor(TokenColor.BLACK);
        board.getNodeFromIndex(16).setTokenColor(TokenColor.BLACK);
        board.getNodeFromIndex(4).setTokenColor(TokenColor.BLACK);
        board.getNodeFromIndex(8).setTokenColor(TokenColor.BLACK);

        GameController.getInstance().setCurrentState(GameState.REMOVE);

        // Specify message
        return "<html>REMOVE: A mill is formed. Choose a moon to remove. <br> Mill happens when a row of three tokens is formed.<html>";
    }

    public String jump() {
        // Reset Board and State
        GameController gameController = GameController.getInstance();
        Board board = gameController.getBoard();
        gameController.restart();
        gameController.getFirstPlayer().setTokensOnBoard(3);
        gameController.getFirstPlayer().setTokensToSet(0);
        gameController.getSecondPlayer().setTokensOnBoard(4);
        gameController.getSecondPlayer().setTokensToSet(0);

        // Initialize Position
        board.getNodeFromIndex(0).setTokenColor(TokenColor.WHITE);
        board.getNodeFromIndex(1).setTokenColor(TokenColor.WHITE);
        board.getNodeFromIndex(2).setTokenColor(TokenColor.WHITE);
        board.getNodeFromIndex(23).setTokenColor(TokenColor.BLACK);
        board.getNodeFromIndex(16).setTokenColor(TokenColor.BLACK);
        board.getNodeFromIndex(4).setTokenColor(TokenColor.BLACK);
        board.getNodeFromIndex(8).setTokenColor(TokenColor.BLACK);

        GameController.getInstance().setCurrentState(GameState.JUMP);

        // Specify message
        return "<html>JUMP: When a player has three tokens left, jump is available. <br>Move a star token to any empty position.<html>";
    }

    public String winning1() {
        // Reset Board and State
        GameController gameController = GameController.getInstance();
        Board board = gameController.getBoard();
        gameController.restart();
        gameController.getFirstPlayer().setTokensOnBoard(3);
        gameController.getSecondPlayer().setTokensOnBoard(3);

        // Initialize Position
        board.getNodeFromIndex(0).setTokenColor(TokenColor.WHITE);
        board.getNodeFromIndex(1).setTokenColor(TokenColor.WHITE);
        board.getNodeFromIndex(2).setTokenColor(TokenColor.WHITE);
        board.getNodeFromIndex(23).setTokenColor(TokenColor.BLACK);
        board.getNodeFromIndex(16).setTokenColor(TokenColor.BLACK);

        GameController.getInstance().setCurrentState(GameState.MOVE);

        // Specify message
        return "<html>Winning condition 1: <br>Remove moon tokens until 2 moons are left. Star wins. <html>";
    }

    public String winning2() {
        // Reset Board and State
        GameController gameController = GameController.getInstance();
        Board board = gameController.getBoard();
        gameController.restart();
        gameController.getFirstPlayer().setTokensOnBoard(4);
        gameController.getSecondPlayer().setTokensOnBoard(6);

        // Initialize Position
        board.getNodeFromIndex(1).setTokenColor(TokenColor.WHITE);
        board.getNodeFromIndex(5).setTokenColor(TokenColor.WHITE);
        board.getNodeFromIndex(9).setTokenColor(TokenColor.WHITE);
        board.getNodeFromIndex(13).setTokenColor(TokenColor.WHITE);
        board.getNodeFromIndex(23).setTokenColor(TokenColor.WHITE);
        board.getNodeFromIndex(0).setTokenColor(TokenColor.BLACK);
        board.getNodeFromIndex(6).setTokenColor(TokenColor.BLACK);
        board.getNodeFromIndex(7).setTokenColor(TokenColor.BLACK);
        board.getNodeFromIndex(8).setTokenColor(TokenColor.BLACK);
        board.getNodeFromIndex(14).setTokenColor(TokenColor.BLACK);
        board.getNodeFromIndex(15).setTokenColor(TokenColor.BLACK);

        gameController.setCurrentState(GameState.MOVE);

        // Specify message
        return "<html>Winning condition 2: <br>All moons are blocked and no legal moves for moon. Star wins.<html>";
    }

    public String end() {
        // Reset Board and State
        String[] options = {"Play Computer", "Play Human"};
        GameController gameController = GameController.getInstance();
        int input = JOptionPane.showOptionDialog(
                null,
                "Do you want to play with human or computer?",
                "Tutorial ends.",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);

        if (input == JOptionPane.YES_OPTION) {
            gameController.restart();
            gameController.rebuild(true);
        } else if (input == JOptionPane.NO_OPTION) {
            gameController.restart();
            gameController.rebuild(false);
        }

        // Specify message
        return "<html>Congratulations on finishing the tutorial. Now play a real game.<html>";
    }

}