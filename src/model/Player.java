package model;

public class Player {
    //玩家名称
    private String name;
    //玩家血量上限
    private int hpMax;
    //玩家当前血量
    private int hp;
    //玩家能量上限
    private int energyMax;
    //玩家当前能量
    private int energy;
    //玩家速度
    private int speed;
    //玩家基础防御,开局buff获得的防御加成
    private int baseDefense;
    //玩家临时，使用卡牌获得的防御加成，每局开始时要清0
    private int temporaryDefense;
    //buffid
    private int buffId;
    //增益专用
    private int tempdamage;

    public Player() {
    }

    public Player(int baseDefense, int buffId, int energy, int energyMax, int hp, int hpMax, String name, int speed, int temporaryDefense) {
        this.baseDefense = baseDefense;
        this.buffId = buffId;
        this.energy = energy;
        this.energyMax = energyMax;
        this.hp = hp;
        this.hpMax = hpMax;
        this.name = name;
        this.speed = speed;
        this.temporaryDefense = temporaryDefense;
    }

    public int getBuffId() {
        return buffId;
    }

    public void setBuffId(int buffId) {
        this.buffId = buffId;
    }

    public int getBaseDefense() {
        return baseDefense;
    }

    public void setBaseDefense(int baseDefense) {
        this.baseDefense = baseDefense;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public int getEnergyMax() {
        return energyMax;
    }

    public void setEnergyMax(int energyMax) {
        this.energyMax = energyMax;
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

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getTemporaryDefense() {
        return temporaryDefense;
    }

    public void setTemporaryDefense(int temporaryDefense) {
        this.temporaryDefense = temporaryDefense;
    }

    public void setTempdamage(int tempdamage){this.tempdamage = tempdamage;}

    public int getTempdamage(){return tempdamage;}
}
