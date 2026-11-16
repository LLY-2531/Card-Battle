package model;

public class Buff {
    //buff名称
    private String name;
    //buff描述
    private String describe;
    //buff编号,抽取时用编号抽取
    private int id;
    //buff血量上限加成,玩家初始血量为hpMax,hpMaxadd的部分应自动补满
    private int hpMaxAdd;

    //buff防御加成，即永久护甲,战斗时结算伤害=玩家当前血量-（敌人伤害-永久护甲-临时护甲）
    private int buffDefenseAdd;
    //buff速度加成
    private int speedAdd;
    //是否补满当前血量
    private boolean isFillHp;

    public Buff() {
    }

    public Buff(int buffDefenseAdd, String describe, int hpMaxAdd, int id, boolean isFillHp, String name, int speedAdd) {
        this.buffDefenseAdd = buffDefenseAdd;
        this.describe = describe;
        this.hpMaxAdd = hpMaxAdd;
        this.id = id;
        this.isFillHp = isFillHp;
        this.name = name;
        this.speedAdd = speedAdd;
    }

    public boolean isFillHp() {
        return isFillHp;
    }

    public void setFillHp(boolean fillHp) {
        isFillHp = fillHp;
    }

    public int getBuffDefenseAdd() {
        return buffDefenseAdd;
    }

    public void setBuffDefenseAdd(int buffDefenseAdd) {
        this.buffDefenseAdd = buffDefenseAdd;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public int getHpMaxAdd() {
        return hpMaxAdd;
    }

    public void setHpMaxAdd(int hpMaxAdd) {
        this.hpMaxAdd = hpMaxAdd;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSpeedAdd() {
        return speedAdd;
    }

    public void setSpeedAdd(int speedAdd) {
        this.speedAdd = speedAdd;
    }
}
