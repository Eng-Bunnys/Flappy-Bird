package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class StartMenu extends JPanel {
    private JButton StartButton;
    private MainMenuListener listener;

    public StartMenu() {
        setLayout(new BorderLayout());

        // Loading the background image
        ImageIcon BackgroundImage = new ImageIcon(
                Objects.requireNonNull(getClass().getResource("/Resources/flappybirdbg.png")));
        JLabel BackgroundLabel = new JLabel(BackgroundImage);
        BackgroundLabel.setLayout(new FlowLayout());

        // Loading the start button icon
        ImageIcon StartButtonIcon = new ImageIcon(
                Objects.requireNonNull(getClass().getResource("/Resources/PlayButton.png")));
        StartButton = new JButton(StartButtonIcon);
        StartButton.setBorderPainted(false);
        StartButton.setContentAreaFilled(false);
        StartButton.setFocusPainted(false);

        BackgroundLabel.add(StartButton);
        add(BackgroundLabel, BorderLayout.CENTER);

        StartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listener != null)
                    listener.onStartGame();
            }
        });
    }

    public void setMainMenuListener(MainMenuListener listener) {
        this.listener = listener;
    }

    public interface MainMenuListener {
        void onStartGame();
    }
}
