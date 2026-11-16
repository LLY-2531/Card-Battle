package model;

public class Rank {
    //玩家名
    private String name;
    //排行榜分数
    private int score;
    //楼层
    private int floor;
    //剩余血量
    private int remainingHp;

    public Rank() {
    }

    public Rank(int floor, String name, int remainingHp, int score) {
        this.floor = floor;
        this.name = name;
        this.remainingHp = remainingHp;
        this.score = score;
    }

    public int getRemainingHp() {
        if(remainingHp <=0){
            this.remainingHp=0;
        }
        return remainingHp;
    }

    public void setRemainingHp(int remainingHp) {
        this.remainingHp = remainingHp;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
