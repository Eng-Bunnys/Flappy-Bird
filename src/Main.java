import Game.GameManager;
import UI.StartMenu;

import javax.swing.*;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        JFrame GameFrame = new JFrame("Flappy Bird");

        ImageIcon GameIcon = new ImageIcon(Objects.requireNonNull(Main.class.getResource("/Resources/flappybird.png")));
        GameFrame.setIconImage(GameIcon.getImage());

        GameFrame.setSize(GameManager.GameWidth, GameManager.GameHeight);
        GameFrame.setLocationRelativeTo(null);
        GameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GameFrame.setResizable(false);

        StartMenu StartMenu = new StartMenu();
        GameManager FlappyBird = new GameManager();

        GameFrame.add(StartMenu);
        GameFrame.setVisible(true);

        StartMenu.setMainMenuListener(new StartMenu.MainMenuListener() {
            @Override
            public void onStartGame() {
                GameFrame.remove(StartMenu);
                GameFrame.add(FlappyBird);
                GameFrame.revalidate();
                GameFrame.repaint();
                FlappyBird.requestFocus();
                FlappyBird.StartGame();
            }
        });

        FlappyBird.setGameEndListener(new GameManager.GameEndListener() {
            @Override
            public void onGameEnd() {
                GameFrame.remove(FlappyBird);
                GameFrame.add(StartMenu);
                GameFrame.revalidate();
                GameFrame.repaint();
            }
        });
    }
}
