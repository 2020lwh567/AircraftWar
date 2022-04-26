package edu.hitsz.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static edu.hitsz.application.Main.obj;

public class StartMenu extends JPanel {
    private static Dimension buttonSize = new Dimension(200, 50);
    public JComboBox<String> comboBox;
    private JButton easy;
    private JButton medium;
    private JButton hard;

    public StartMenu() {
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 200, 90));
        initUpperPanel();
        initLowerPanel();
    }

    //初始化上方难度选择按钮
    private void initButton(JButton button) {
        button.setPreferredSize(buttonSize);
        button.setFont(new Font("Dialog", 1, 20));
        this.add(button);
    }

    //给三个按钮添加监听时间
    private void addListener(JButton button, int difficulty){
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                synchronized(obj){
                    CreateGamePanel.setDifficulty(difficulty);
                    CreateGamePanel.setMusicOn(comboBox.getSelectedIndex());
                    obj.notifyAll();
                }
            }
        });
    }

    private void initUpperPanel() {
        easy = new JButton("简单模式");
        medium = new JButton("普通模式");
        hard = new JButton("困难模式");

        initButton(easy);
        initButton(medium);
        initButton(hard);

        addListener(easy, 1);
        addListener(medium, 2);
        addListener(hard, 3);
    }

    private void initLowerPanel() {
        JPanel bottomPanel = new JPanel();
        JLabel label = new JLabel("音效开关：");
        label.setFont(new Font("Dialog", 1, 25));
        bottomPanel.add(label);

        String[] listData = new String[]{"开","关"};
        comboBox = new JComboBox<String>(listData);
        comboBox.setPreferredSize(new Dimension(80, 40));
        comboBox.setFont(new Font("Dialog", 1, 20));
        comboBox.setSelectedIndex(0);
        bottomPanel.add(comboBox);

        this.add(bottomPanel);
    }
}
