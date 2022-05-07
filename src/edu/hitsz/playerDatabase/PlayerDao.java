package edu.hitsz.playerDatabase;

import java.util.List;

public interface PlayerDao {
    List<Player> getAllPlayers();
    void add(Player player);
    void delete(int playerIndex);
    void sort();
    //void showLeaderboard();
}
