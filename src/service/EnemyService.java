package service;

import core.Global;
import model.BossSkill;
import model.Enemy;
import model.NormalEnemySkill;

import java.util.Random;

public class EnemyService {
    /*
       功能: 初始化普通技能池、Boss技能池、5层敌人模板(enemy Map)(integer对应敌人层数，string对应敌人名字)
       1.Global.normalEnemySkillPool.add
       2.Global.bossSkillPool.add
       3.Global.enemy.put

       参数: void
       返回值: void
   */
    public static void EnemyService_initTemplates() {
        // 在此处完成代码
//      1.Global.normalEnemySkillPool.add
        Global.normalEnemySkillPool.add(new NormalEnemySkill(7, 0, "猛击", "1", "猛击"));
        Global.normalEnemySkillPool.add(new NormalEnemySkill(11, 0, "重击", "2", "重击"));
        Global.normalEnemySkillPool.add(new NormalEnemySkill(0, 6, "防御姿态", "3", "防御姿态"));
//      2.Global.bossSkillPool.add
        Global.bossSkillPool.add(new BossSkill(10, 0, "巨剑斩", "1", "巨剑斩"));
        Global.bossSkillPool.add(new BossSkill(14, 0, "破甲一击", "2", "破甲一击"));
        Global.bossSkillPool.add(new BossSkill(6, 10, "盾击", "3", "盾击"));
        Global.bossSkillPool.add(new BossSkill(0, 15, "铁壁", "4", "铁壁"));
        Global.bossSkillPool.add(new BossSkill(12, 0, "处决", "5", "处决"));
//      3.Global.enemy.put
        Global.enemy.put(1, new Enemy(1.0f, 0, 30, 30, false, "灰狼", 10));
        Global.enemy.put(2, new Enemy(1.2f, 0, 45, 45, false, "石傀儡", 7));
        Global.enemy.put(3, new Enemy(1.4f, 0, 55, 55, false, "暗影刺客", 15));
        Global.enemy.put(4, new Enemy(1.6f, 0, 70, 70, false, "重甲卫兵", 9));
        Global.enemy.put(5, new Enemy(1.5f, 0, 100, 100, true, "堕落骑士(Boss)", 11));
    }

    /*
        功能: 根据层数（传入的参数f）从 enemy Map 创建当前敌人副本，赋给 currentEnemy
         已申明当前敌人
    public static Enemy currentEnemy;
            注意：必须是副本，不能直接返回模板，否则打一次血量就变了
        参数: f 层数
        返回值: void
    */
    public static void EnemyService_create(int f) {
        // 在此处完成代码
//      根据层数（传入的参数f）从 enemy Map 创建当前敌人副本，赋给 currentEnem
        Global.currentEnemy = new Enemy(Global.enemy.get(f).getAttackBonus(), Global.enemy.get(f).getDefense(), Global.enemy.get(f).getHp(), Global.enemy.get(f).getHpMax(), Global.enemy.get(f).isBoss(), Global.enemy.get(f).getName(), Global.enemy.get(f).getSpeed());
//      已申明当前敌人
//      public static Enemy currentEnemy;
//      注意：必须是副本，不能直接返回模板，否则打一次血量就变了
//      参数: f 层数
//      返回值: void
    }

    /*
        功能: 敌人随机抽技能
            currentEnemy.isBoss() 为 true 从 bossSkillPool 抽，否则从 normalEnemySkillPool 抽
            Global.bossSkillPool
            Global.normalEnemySkillPool
        参数: void
        返回值: Object 返回抽到的技能对象 (后续可转为 NormalEnemySkill 或 BossSkill)
    */
    public static Object EnemyService_randomSkill() {
        // 在此处完成代码
//      currentEnemy.isBoss() 为 true 从 bossSkillPool 抽，否则从 normalEnemySkillPool 抽
//      Global.bossSkillPool
//      Global.normalEnemySkillPool
        Random r = new Random();
        if (Global.currentEnemy.isBoss()) {
            return Global.bossSkillPool.get(r.nextInt(Global.bossSkillPool.size()));
        } else {
            return Global.normalEnemySkillPool.get(r.nextInt(Global.normalEnemySkillPool.size()));
        }
//      参数: void
//      返回值: Object 返回抽到的技能对象 (后续可转为 NormalEnemySkill 或 BossSkill)
    }
}
