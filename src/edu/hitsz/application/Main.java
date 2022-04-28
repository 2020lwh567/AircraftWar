package edu.hitsz.application;

import edu.hitsz.ui.EndMenu;
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
    public static JFrame mainFrame;

    public static void main(String[] args) {

        System.out.println("Hello Aircraft War");

        // 获得屏幕的分辨率，初始化 Frame
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        mainFrame = new JFrame("Aircraft War");
        mainFrame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        mainFrame.setResizable(false);
        //设置窗口的大小和位置,居中放置
        mainFrame.setBounds(((int) screenSize.getWidth() - WINDOW_WIDTH) / 2, 0,
                WINDOW_WIDTH, WINDOW_HEIGHT);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        synchronized (obj){
            try {
//                synchronized (obj){
//                    System.out.println("before menu");
//                    obj.notifyAll();
//                }
                // 进入菜单界面
                StartMenu menuPanel = new StartMenu();
                mainFrame.add(menuPanel);
                mainFrame.setVisible(true);
                obj.wait();
                mainFrame.remove(menuPanel);

                //进入游戏界面
                Game game = CreateGamePanel.createGamePanel();
                mainFrame.add(game);
                mainFrame.setVisible(true);
                game.action();
                obj.wait();
                mainFrame.remove(game);

                //System.out.println("back main");

                //进入得分界面
                EndMenu endMenu = new EndMenu(game.getDifficulty());
                endMenu.addJPanel(endMenu);
                mainFrame.setVisible(true);
                endMenu.showPopUps(game.getScore(), game.getCurrentTime(), game.getDifficulty());

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
