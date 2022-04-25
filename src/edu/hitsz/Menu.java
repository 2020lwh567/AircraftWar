package edu.hitsz;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu {
    private JButton EasyButton;
    private JButton HardButton;
    private JButton MediumButton;
    private JComboBox SoundcomboBox;
    private JPanel MainPanel;
    private JPanel TopPanel;
    private JPanel ButtomPanel;

    public Menu() {
        EasyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        MediumButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        HardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Menu");
        frame.setSize(300,200);
        frame.setContentPane(new Menu().MainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }


}
