package edu.hitsz.ui;

import edu.hitsz.playerDatabase.Player;
import edu.hitsz.playerDatabase.PlayerDao;
import edu.hitsz.playerDatabase.PlayerDaoImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import static edu.hitsz.application.Main.mainFrame;

public class EndMenu extends JPanel {
    private static final String[] COLUM = new String[]{"名次", "用户名", "分数", "时间"};
    private  JScrollPane tableContainer;
    private JLabel JLabeltabelHead;
    private JButton bottomButton;
    private PlayerDao playerDaoImpl;
    public JPanel preJPanel = null;

    //玩家的list
    private List<Player> players;

    public EndMenu(int difficult){
        playerDaoImpl = new PlayerDaoImpl(difficult);
        showEndPanel(difficult);
    }

    /**添加或删除玩家后，更新列表*/
    public void changeEndPanel(int difficult){
        EndMenu endMenu = new EndMenu(difficult);
        addJPanel(endMenu);
    }

    public void showEndPanel(int difficult){
        //表头
        switch (difficult) {
            case 1:
                JLabeltabelHead = new JLabel("难度:EASY");
                break;
            case 2:
                JLabeltabelHead = new JLabel("难度:MEDIUM");
                break;
            case 3:
                JLabeltabelHead = new JLabel("难度:HARD");
                break;
            default: ;
        }
        JLabeltabelHead.setPreferredSize(new Dimension(400, 30));

        //表格
        String[][] ans = getPlayerData();
        JTable table = new JTable(ans, COLUM);
        tableContainer = new JScrollPane(table);

        //删除按钮
        bottomButton = new JButton("删除选中记录");
        bottomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = table.getSelectedRow();
                //System.out.println(index);
                if(index!=-1){
                    if (JOptionPane.showConfirmDialog(null, "是否确定删除选定玩家？", "提示", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.YES_OPTION){
                        // 从数据库中删除
                        playerDaoImpl.delete(index);

                        // 从显示的列表中删除
                        removeTotal();

                        //重新展示画面
                        showEndPanel(difficult);
                        mainFrame.revalidate();
                    }
                }
            }
        });

        this.add(JLabeltabelHead);
        this.add(tableContainer, BorderLayout.CENTER);
        this.add(bottomButton);
    }

    /**展示弹窗，记录用户名*/
    public void showPopUps(int score, String time, int difficult){
        String inputName = JOptionPane.showInputDialog("请输入玩家姓名：");
        if(inputName!=null){
            playerDaoImpl.add(new Player(inputName, score, time));
            changeEndPanel(difficult);
        }
    }

    /**移除所有元素*/
    public void removeTotal(){
        this.remove(JLabeltabelHead);
        this.remove(tableContainer);
        this.remove(bottomButton);
    }

    private String[][] getPlayerData() {
        try {
            players = PlayerDaoImpl.readCsv(PlayerDaoImpl.filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[][] ans = new String[players.size()][4];
        for (int i=0 ; i<players.size() ; i++){
            ans[i][0] = Integer.toString(i+1);
            ans[i][1] = players.get(i).getTestUserName();
            ans[i][2] = Integer.toString(players.get(i).getScore());
            ans[i][3] = players.get(i).getTime();
        }
        return ans;
    }

    /**在上层的mainFrame中加入新的panel*/
    public void addJPanel(JPanel newJPanel) {
        if (preJPanel == null) {
            this.preJPanel = newJPanel;
            mainFrame.add(newJPanel);
        } else {
            mainFrame.remove(preJPanel);
            mainFrame.add(newJPanel);
            mainFrame.revalidate();
            mainFrame.repaint();
            preJPanel = newJPanel;
        }
    }
}
