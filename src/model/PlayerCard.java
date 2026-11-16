package model;

public class PlayerCard {
    //卡牌名称
    private String name;
    //能量消耗
    private int cost;
    //卡牌伤害
    private int damage;
    //防御值
    private int defense;
    //卡牌编号，随机抽取时用卡牌编号抽出
    private String id;
    //卡牌描述
    private String describe;
    //连击次数
    private int combo;
    //是否无视护甲（穿刺）
    private boolean ignoreArmor;
    //自伤
    private int selfDamage;

    public PlayerCard(int combo, int cost, int damage, int defense, String describe, String id, boolean ignoreArmor, String name, int selfDamage) {
        this.combo = combo;
        this.cost = cost;
        this.damage = damage;
        this.defense = defense;
        this.describe = describe;
        this.id = id;
        this.ignoreArmor = ignoreArmor;
        this.name = name;
        this.selfDamage = selfDamage;
    }

    public int getSelfDamage() {
        return selfDamage;
    }

    public void setSelfDamage(int selfDamage) {
        this.selfDamage = selfDamage;
    }

    public PlayerCard() {
    }

    public boolean isIgnoreArmor() {
        return ignoreArmor;
    }

    public void setIgnoreArmor(boolean ignoreArmor) {
        this.ignoreArmor = ignoreArmor;
    }

    public int getCombo() {
        return combo;
    }

    public void setCombo(int combo) {
        this.combo = combo;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "PlayerCard{" +
                "combo=" + combo +
                ", name='" + name + '\'' +
                ", cost=" + cost +
                ", damage=" + damage +
                ", defense=" + defense +
                ", id='" + id + '\'' +
                ", describe='" + describe + '\'' +
                ", ignoreArmor=" + ignoreArmor +
                ", selfDamage=" + selfDamage +
                '}';
    }
}
