package core;

import model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;


public class Global {
    public static Scanner scan = new Scanner(System.in);
    public static Random rand = new Random();
    //当前玩家对象
    public static Player player = new Player();
    //永久卡牌池
    public static ArrayList<PlayerCard> playerCardPool = new ArrayList<PlayerCard>();
    //临时卡牌,装3张牌
    public static ArrayList<PlayerCard> playerCardPoolTemp = new ArrayList<PlayerCard>();
    //抽取卡牌池，装5张
    public static ArrayList<PlayerCard> playerCardPoolDraw = new ArrayList<PlayerCard>();
    //永久buff池
    public static ArrayList<Buff> buffPool = new ArrayList<Buff>();
    //当前层数
    public static int floor = 1;
    //当前回合
    public static int round = 1;
    //普通敌人技能池
    public static ArrayList<NormalEnemySkill> normalEnemySkillPool = new ArrayList<NormalEnemySkill>();
    //boss技能池
    public static ArrayList<BossSkill> bossSkillPool = new ArrayList<BossSkill>();
    //排行榜
    public static ArrayList<Rank> rank = new ArrayList<Rank>();
    //敌人,integer对应1-5层。层数->敌人
    public static HashMap<Integer, Enemy> enemy = new HashMap<Integer, Enemy>();
    //当前敌人
    public static Enemy currentEnemy;
    //设置
    public static HashMap<String, Boolean> settings = new HashMap<>();



    static {
        settings.put("soundOn", true);
        settings.put("bgmOn", true);
        settings.put("colorOn", true);
    }
    // 存当前回合敌人要放的技能
    public static Object currentEnemyIntent;
    public static int getIntentDamage() {
        if (currentEnemyIntent instanceof NormalEnemySkill) {
            return (int)(((NormalEnemySkill) currentEnemyIntent).getDamage() * currentEnemy.getAttackBonus());
        } else if (currentEnemyIntent instanceof BossSkill) {
            return (int)(((BossSkill) currentEnemyIntent).getDamage() * currentEnemy.getAttackBonus());
        }
        return 0;
    }
    public static int getIntentDefense() {
        if (currentEnemyIntent instanceof NormalEnemySkill) {
            return ((NormalEnemySkill) currentEnemyIntent).getDefense();
        } else if (currentEnemyIntent instanceof BossSkill) {
            return ((BossSkill) currentEnemyIntent).getDefense();
        }
        return 0;
    }
}



