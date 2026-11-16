package service;
import core.Global;
import model.BossSkill;
import model.NormalEnemySkill;
import model.PlayerCard;

import java.util.ArrayList;

import static view.ConsoleView.RED;
import static view.ConsoleView.printColored;

public class BattleService {


    /*
        负责人:聂童童
        功能: 开始新一层战斗
            1. 调 EnemyService_create(floor) 创建敌人
           调用 EnemyService.EnemyService_create(f);
            2. 重置回合数 round = 1
            Global.round
            3. 重置双方临时护甲、重置玩家能量
            Global.player
            4. 调用CardService.CardService_drawFive()抽 5 张牌
            5. 调用CardService.CardService_discardHand()清空手牌
            6. 抽取敌人本回合意图
            意图放在：Global.currentEnemyIntent(存入对象)
            敌人技能在 EnemyService_randomSkill()
            敌人意图要根据是否为boss从NormalEnemyIntent或BossEnemyIntent中随机获取

        参数: f 当前层数
        返回值: void
    */
    public static void BattleService_start(int f) {
        // 在此处完成代码
        //创建敌人
        EnemyService.EnemyService_create(f);
        //重置回合数
        Global.round = 1;
        //重置敌人护甲,玩家临时护甲,重置玩家能量
        Global.currentEnemy.setDefense(0);
        Global.player.setTemporaryDefense(0);
        Global.player.setEnergy(Global.player.getEnergyMax());
        //清空手牌
        Global.playerCardPoolTemp.clear();
        //随机抽5张牌
        CardService.CardService_drawFive();
        //随机抽取敌人技能
        Object o = EnemyService.EnemyService_randomSkill();
        //将技能传给Global类
        Global.currentEnemyIntent = o;
    }

    /*
        负责人:宋~
        功能: 结算伤害血量
            先扣临时护甲(敌人是defense)  -> 扣血
        参数: raw 原始伤害
             isPlayer true表示打玩家(扣玩家血), false表示打敌人(扣敌人血)
             ignoreArmor 是否无视护甲(穿刺卡)
             若无视护甲，直接扣血
        返回值: int 实际造成的扣血量
    */
    public static int BattleService_applyDamage(int raw, boolean isPlayer, boolean ignoreArmor) {
        // 在此处完成代码
        if(isPlayer){
            //打玩家
            //计算当前玩家的buff护甲和临时护甲
            int armor = Global.player.getBaseDefense() + Global.player.getTemporaryDefense();
            //计算实际伤害
            int dam = raw - armor;
            if(dam > 0){
                //扣玩家血量
                Global.player.setHp(Global.player.getHp() - dam);
                return dam;
            }else{
                return 0;
            }
        }else{
            //打敌人
            //技能无视防御时，返回原始伤害
            if(ignoreArmor){
                //扣敌人血量
                Global.currentEnemy.setHp(Global.currentEnemy.getHp() - raw);
                return raw;
            }else{
                //敌人护甲值
                int enemyArmor = Global.currentEnemy.getDefense();
                if(raw > enemyArmor){
                    //实际伤害
                    int dam = raw - enemyArmor;
                    //扣敌人血量
                    Global.currentEnemy.setHp(Global.currentEnemy.getHp() - dam);
                    return dam;
                }else{
                    return 0;
                }
            }

        }

    }

    /*
        负责人:龙
        功能:

            1.玩家打出一张手牌

            2. 校验能量是否足够
            3. 扣能量
            4. 按 combo（连击次数） 循环结算伤害
            5. 结算反噬伤害 (selfDamage)
            6. 结算获得的护甲 (defense)

            ----具体操作
            1.创建一个ArrayList<String>来存储战斗日志
            2.调用Global.playerCardPoolTemp.get(handIndex)获取本次出的手牌
            3.调用获取到的手牌获取能耗
            4.校验能量是否足够，扣掉费用记录在Global.player.energy
            5.按 combo（连击次数） 循环结算伤害
            6.结算反噬伤害 (selfDamage)用获取到手牌。getSelfDamage()获取反伤
              如果反伤>0，则扣玩家血量
            7.结算获得的护甲 (defense)用获取到手牌.getDefense()获取护甲
        参数: handIndex 手牌在 playerCardPoolTemp 中的下标
        返回值: ArrayList<String> 返回战斗日志，供 View 延迟打印
    */
    public static int stoi(String s){
        int ans = s.charAt(0)-'0';
        ans = ans*10 + s.charAt(1)-'0';
        return ans;
    }
    public static ArrayList<String> BattleService_playerPlayCard(int handIndex) {
        // 在此处完成代码
        //新建一个日志ArrayList
        ArrayList<String> logList = new ArrayList<>();
        //选择的手牌
        PlayerCard nowcard = Global.playerCardPoolTemp.get(handIndex);
        //判断花费能量
        if(Global.player.getEnergy() >= nowcard.getCost()){
            Global.player.setEnergy(Global.player.getEnergy() - nowcard.getCost());
            logList.add(Global.player.getName()+"使用了"+nowcard.getName());
        }else{
            printColored(Global.player.getName()+"的能量不足以发动这张牌",RED);
            return new ArrayList<>();
        }

        //定义伤害
        int nowdamage = nowcard.getDamage()* nowcard.getCombo();

        //对增益效果的加成
        if(Global.player.getTempdamage() != 0){
            nowdamage += Global.player.getTempdamage();
        }
        //对恢复牌特判
        if(stoi(nowcard.getId()) >= 15 && stoi(nowcard.getId()) <= 18) {
            logList.add(Global.player.getName() + nowcard.getDescribe());
            int recover = -nowcard.getSelfDamage();
            if(Global.player.getHp()+recover >= Global.player.getHpMax()){
                logList.add(Global.player.getName()+"血量回满,达到上限");
                Global.player.setHp(Global.player.getHpMax());
            }else{
                Global.player.setHp(Global.player.getHp()+recover);
            }
            return logList;
        }
        //对增益牌特判
        if(stoi(nowcard.getId()) >= 19 && stoi(nowcard.getId()) <= 22){
            logList.add(nowcard.getDescribe());
            Global.player.setTempdamage(Global.player.getTempdamage()+nowcard.getDamage());
            return logList;
        }
        //是否忽视护甲
        if(!nowcard.isIgnoreArmor()) {
            if(nowdamage >= Global.currentEnemy.getDefense()){
                int hpcost = (nowdamage-Global.currentEnemy.getDefense());
                Global.currentEnemy.setHp(Global.currentEnemy.getHp()-hpcost);
                if(hpcost != 0){
                    logList.add(Global.player.getName()+"对"+Global.currentEnemy.getName()+"造成了"+hpcost+"伤害");
                }
            }else{
                int defencecost = Global.currentEnemy.getDefense()-nowdamage;
                Global.currentEnemy.setDefense(Global.currentEnemy.getDefense()-defencecost);
                if(defencecost != 0){
                    logList.add(Global.player.getName()+"对"+Global.currentEnemy.getName()+"造成"+defencecost+"护甲伤害");
                }
            }
        }else{
            logList.add("对"+Global.currentEnemy.getName()+"的血量造成了"+nowdamage+"伤害");
            Global.currentEnemy.setHp(Global.currentEnemy.getHp()-nowdamage);
        }
        //增加护甲
        if(nowcard.getDefense() != 0){
            logList.add("对"+Global.player.getName()+"增加了"+nowcard.getDefense()+"护甲");
            Global.player.setTemporaryDefense(Global.player.getTemporaryDefense() + nowcard.getDefense());
        }
        //自伤
        if(nowcard.getSelfDamage() != 0){
            logList.add("对"+Global.player.getName()+"造成了"+nowcard.getSelfDamage()+"伤害");
            Global.player.setHp(Global.player.getHp()-nowcard.getSelfDamage());
        }

        return logList;
    }
    /*
        负责人:陈
        功能: 敌人回合
            根据 currentEnemyIntent (敌人意图)结算伤害和护甲
            实际伤害 = (int)(技能基础伤害 * currentEnemy.attackBonus)
            1.创建ArrayList<String>来存储战斗日志
            2.获取敌人技能->Global.currentEnemyIntent
            3.结算护甲->Global.currentEnemy.setDefense()
            4.结算伤害->调用BattleService_applyDamage
            5.实际伤害 = (int)(技能基础伤害 * currentEnemy.attackBonus)
        参数: void
        返回值: ArrayList<String> 返回战斗日志
    */


    public static ArrayList<String> BattleService_enemyTurn() {
        // 在此处完成代码
//      currentEnemyIntent (敌人意图)结算伤害和护甲
//      实际伤害 = (int)(技能基础伤害 * currentEnemy.attackBonus)
//      1.创建ArrayList<String>来存储战斗日志
        ArrayList<String> log = new ArrayList<>();
        String skillName;
//      3.实际伤害 = (int)(技能基础伤害 * currentEnemy.attackBonus)
        int raw = Global.getIntentDamage();
        int defenseNow = Global.getIntentDefense();
//      4.结算护甲->Global.currentEnemy.setDefense()
        if (defenseNow > 0) {
            if (Global.currentEnemyIntent instanceof NormalEnemySkill) {
                skillName = ((NormalEnemySkill) Global.currentEnemyIntent).getName();
                log.add("敌人使用了" + skillName + "，获得了" + defenseNow + "点护甲");
            } else if (Global.currentEnemyIntent instanceof BossSkill) {
                skillName = ((BossSkill) Global.currentEnemyIntent).getName();
                log.add("敌人使用了" + skillName + "，获得了" + defenseNow + "点护甲");
            }
        }
//      5.结算伤害->调用BattleService_applyDamage
        if (raw > 0) {
            int damage = BattleService_applyDamage(raw, true, false);
            if ((Global.currentEnemyIntent instanceof NormalEnemySkill)) {
                skillName = ((NormalEnemySkill) Global.currentEnemyIntent).getName();
                log.add("敌人使用了" + skillName + "，造成了" + damage + "点伤害");
            } else if (Global.currentEnemyIntent instanceof BossSkill) {
                skillName = ((BossSkill) Global.currentEnemyIntent).getName();
                log.add("敌人使用了" + skillName + "，造成了" + damage + "点伤害");
            }
        }
//      返回值: ArrayList<String> 返回战斗日志
        return new ArrayList<>(log);
    }
}


