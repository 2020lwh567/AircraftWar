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

        // 进入菜单界面
        StartMenu menuPanel = new StartMenu();
        mainFrame.add(menuPanel);
        mainFrame.setVisible(true);

        synchronized (obj){
            while(menuPanel.isVisible()) {
                try {
                    obj.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        // 移除菜单页面
        mainFrame.remove(menuPanel);

        //进入游戏界面
        Game game = CreateGamePanel.createGamePanel();
        mainFrame.add(game);
        mainFrame.setVisible(true);
        game.action();

        synchronized (obj){
            while(game.isVisible()) {
                try {
                    obj.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        //移除游戏页面
        mainFrame.remove(game);

        //进入得分界面
        EndMenu endMenu = new EndMenu(game.getDifficulty());
        endMenu.addJPanel(endMenu);
        mainFrame.setVisible(true);
        // 传入当前玩家得分、游戏时间、难度
        endMenu.showPopUps(game.getScore(), game.getCurrentTime(), game.getDifficulty());

    }
}
