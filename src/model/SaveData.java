package model;

public class SaveData {
    private String name;
    private int floorToPlay;
    private int hp, hpMax, speed, baseDefense, energyMax, score, buffId;

    public SaveData() {
    }

    public SaveData(int baseDefense, int buffId, int energyMax, int floorToPlay, int hp, int hpMax, String name, int score, int speed) {
        this.baseDefense = baseDefense;
        this.buffId = buffId;
        this.energyMax = energyMax;
        this.floorToPlay = floorToPlay;
        this.hp = hp;
        this.hpMax = hpMax;
        this.name = name;
        this.score = score;
        this.speed = speed;
    }

    public int getBaseDefense() {
        return baseDefense;
    }

    public void setBaseDefense(int baseDefense) {
        this.baseDefense = baseDefense;
    }

    public int getBuffId() {
        return buffId;
    }

    public void setBuffId(int buffId) {
        this.buffId = buffId;
    }

    public int getEnergyMax() {
        return energyMax;
    }

    public void setEnergyMax(int energyMax) {
        this.energyMax = energyMax;
    }

    public int getFloorToPlay() {
        return floorToPlay;
    }

    public void setFloorToPlay(int floorToPlay) {
        this.floorToPlay = floorToPlay;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getHpMax() {
        return hpMax;
    }

    public void setHpMax(int hpMax) {
        this.hpMax = hpMax;
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

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
