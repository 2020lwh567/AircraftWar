package edu.hitsz.application;

import edu.hitsz.ui.StartMenu;
import edu.hitsz.ui.CreateGamePanel;

import javax.swing.*;
import java.awt.*;

/**
 * 程序入口
 * @author hitsz
 */
public class Main {

    public static final int WINDOW_WIDTH = 512;
    public static final int WINDOW_HEIGHT = 768;
    public static final Object obj = new Object();

    public static void main(String[] args) {

        System.out.println("Hello Aircraft War");

        // 获得屏幕的分辨率，初始化 Frame
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        JFrame frame = new JFrame("Aircraft War");
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setResizable(false);
        //设置窗口的大小和位置,居中放置
        frame.setBounds(((int) screenSize.getWidth() - WINDOW_WIDTH) / 2, 0,
                WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        synchronized (obj){
            try {
//                synchronized (obj){
//                    System.out.println("before menu");
//                    obj.notifyAll();
//                }
                // 进入菜单界面
                StartMenu menuPanel = new StartMenu();
                frame.add(menuPanel);
                frame.setVisible(true);
                obj.wait();
                frame.remove(menuPanel);

                //进入游戏界面
                Game game = CreateGamePanel.createGamePanel();
                frame.add(game);
                frame.setVisible(true);
                game.action();
                obj.wait();


            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
