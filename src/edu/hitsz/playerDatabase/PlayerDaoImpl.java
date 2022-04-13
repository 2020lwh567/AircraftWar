package edu.hitsz.playerDatabase;

import java.io.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class PlayerDaoImpl implements PlayerDao{
    private List<Player> players;
    String filePath = "./src/edu/hitsz/playerDataBase/playerData.csv";

    public PlayerDaoImpl() {
        try{
            //导入历史用户数据
            players = CsvOperator.readCsv(filePath);
        }
        catch (IOException e){
            //csv文件不存在，初始化为空列表
            players = new LinkedList<>();
        }
    }

    @Override
    public List<Player> getAllPlayers() {
        return players;
    }

    @Override
    public void add(Player player) {
        players.add(player);

        //按照分数从高到低排序
        Collections.sort(players);
    }

    @Override
    public void delete(Player player) {
        //该函数暂时没用到
        players.remove(player);
    }

    @Override
    public void showLeaderboard() {
        System.out.println("********************************");
        System.out.println("         得分排行榜         ");
        System.out.println("********************************");
        for (int i=0 ; i<getAllPlayers().size() ; i++){
            Player player = getAllPlayers().get(i);
            System.out.printf("第%d名：%s, %d, %s\n", i+1, player.getTestUserName(), player.getScore(), player.getTime());
        }
        CsvOperator.writeCsv(getAllPlayers(), filePath);
    }


}
