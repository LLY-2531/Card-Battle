package model;

public class Enemy {
    //敌人名称
    private String name;
    //敌人血量上限
    private int hpMax;
    //敌人当前血量
    private int hp;

    //敌人速度
    private int speed;
    //敌人防御，作为敌人护甲，每回合清0
    private int defense;
    //是否为boss
    private boolean isBoss;
    //攻击加成倍数 1-4层相同技能不同层数的敌人数值不同
    private float attackBonus;

    public Enemy() {
    }

    public Enemy(float attackBonus, int defense, int hp, int hpMax, boolean isBoss, String name, int speed) {
        this.attackBonus = attackBonus;
        this.defense = defense;
        this.hp = hp;
        this.hpMax = hpMax;
        this.isBoss = isBoss;
        this.name = name;
        this.speed = speed;
    }

    public float getAttackBonus() {
        return attackBonus;
    }

    public void setAttackBonus(float attackBonus) {
        this.attackBonus = attackBonus;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
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

    public boolean isBoss() {
        return isBoss;
    }

    public void setBoss(boolean boss) {
        isBoss = boss;
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
}
