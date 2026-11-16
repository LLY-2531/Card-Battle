package model;

public class NormalEnemySkill {
    //技能名称
    private String name;

    //技能伤害
    private int damage;
    //技能防御值
    private int defense;
    //技能编号，随机释放技能时用编号抽取
    private String id;
    //技能描述
    private String describe;

    public NormalEnemySkill() {
    }

    public NormalEnemySkill(int damage, int defense, String describe, String id, String name) {
        this.damage = damage;
        this.defense = defense;
        this.describe = describe;
        this.id = id;
        this.name = name;
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
        return "NormalEnemySkill{" +
                "damage=" + damage +
                ", name='" + name + '\'' +
                ", defense=" + defense +
                ", id='" + id + '\'' +
                ", describe='" + describe + '\'' +
                '}';
    }
}
