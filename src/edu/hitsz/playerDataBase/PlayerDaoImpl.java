package edu.hitsz.playerDataBase;

import java.util.LinkedList;
import java.util.List;

public class PlayerDaoImpl implements PlayerDao{
    private List<Player> players;

    public PlayerDaoImpl() {
        players = new LinkedList<>();
    }

    @Override
    public List<Player> getAllPlayers() {
        return players;
    }

    @Override
    public void add(Player player) {
        players.add(player);
    }

    @Override
    public void delete(Player player) {

    }

    @Override
    public void showLeaderboard() {
        System.out.println("********************************");
        System.out.println("     得分排行榜     ");
        System.out.println("********************************");
        for (int i=0 ; i<getAllPlayers().size() ; i++){
            Player player = getAllPlayers().get(i);
            System.out.printf("第%d名：%s, %d, %s\n", i+1, player.getTestUserName(), player.getScore(), player.getTime());
        }
    }
}
