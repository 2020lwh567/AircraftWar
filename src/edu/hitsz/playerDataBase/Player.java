package edu.hitsz.playerDataBase;

public class Player {
    private String testUserName;
    private int score;
    private String time;

    public Player(String testUserName, int score, String time) {
        this.testUserName = testUserName;
        this.score = score;
        this.time = time;
    }

    public String getTestUserName() {
        return this.testUserName;
    }

    public int getScore() {
        return this.score;
    }

    public String getTime() {
        return this.time;
    }
}
