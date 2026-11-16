package service;

import core.Global;
import model.PlayerCard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class CardService {
    /*
       功能: 初始化 22 张永久卡到 playerCardPool 中 (按参数表填写数值)
       Global.playerCardPool.add
       注意数据存储在Global中，用Global.playerCardPool.add
       参数: void
       返回值: void
   */
    public static void CardService_initPool() {
        Collections.addAll(Global.playerCardPool,
                // -------------------- 攻击牌 9 张 --------------------
                new PlayerCard(1, 1, 6, 0, "造成6点伤害", "01", false, "打击", 0),
                new PlayerCard(1, 1, 8, 0, "造成8点伤害", "02", false, "突刺", 0),
                new PlayerCard(1, 1, 5, 0, "造成5点伤害，自身受2点反噬", "03", false, "双刃斩", 2),
                new PlayerCard(1, 2, 14, 0, "造成14点伤害", "04", false, "重击", 0),
                new PlayerCard(3, 2, 4, 0, "连续攻击3次，每次4点", "05", false, "连斩", 0),
                new PlayerCard(1, 2, 10, 0, "造成10点伤害（无视护甲）", "06", true, "穿刺", 0),
                new PlayerCard(1, 2, 10, 3, "造成10点伤害，并获得3点临时护甲", "07", false, "横扫", 0),
                new PlayerCard(1, 3, 24, 0, "造成24点伤害", "08", false, "致命一击", 0),
                new PlayerCard(4, 3, 5, 0, "连续攻击4次，每次5点", "09", false, "暴风连击", 0),
                // -------------------- 防御牌 5 张 --------------------
                new PlayerCard(0, 1, 0, 5, "获得5点临时护甲", "10", false, "防御", 0),
                new PlayerCard(0, 1, 0, 7, "获得7点临时护甲", "11", false, "坚守", 0),
                new PlayerCard(0, 2, 0, 14, "获得14点临时护甲", "12", false, "铁壁", 0),
                new PlayerCard(1, 2, 5, 8, "造成5点伤害，并获得8点临时护甲", "13", false, "格挡反击", 0),
                new PlayerCard(1, 2, 6, 12, "造成6点伤害，并获得12点临时护甲", "14", false, "护盾冲击", 0),
                // -------------------- 回复牌 4 张 --------------------
                // 回复牌用负的selfDamage表示回血
                new PlayerCard(0, 1, 0, 0, "恢复6点生命", "15", false, "小治疗", -6),
                new PlayerCard(0, 2, 0, 0, "恢复12点生命", "16", false, "治疗", -12),
                new PlayerCard(0, 3, 0, 0, "恢复20点生命", "17", false, "大治疗", -20),
                new PlayerCard(1, 2, 8, 0, "造成8点伤害，恢复4点生命", "18", false, "吸血打击", -4),
                // -------------------- 增益牌 4 张 --------------------
                // combo连击次数为0时,damage存的是本场战斗的伤害加成
                new PlayerCard(0, 1, 1, 0, "本场战斗伤害+1", "19", false, "战意", 0),
                new PlayerCard(0, 2, 3, 0, "本场战斗伤害+3", "20", false, "蓄力", 0),
                new PlayerCard(0, 3, 5, 0, "本场战斗伤害+5", "21", false, "狂暴", 0),
                new PlayerCard(0, 2, 2, 5, "本场战斗伤害+2，并获得5点临时护甲", "22", false, "战吼", 0));
    }

    /*
        功能: 从 playerCardPool 随机抽 5 张不重复的卡，放入 playerCardPoolDraw（放5张）
        Global.playerCardPoolDraw.
        1.清空playerCardPoolDraw
        2.从playerCardPool中随机抽5张不重复的卡
        3.将这5张卡放入playerCardPoolDraw
        参数: void
        返回值: void
    */
    public static void CardService_drawFive() {
        // 在此处完成代码
        Random r = new Random();
        //清空playerCardPoolDraw
        Global.playerCardPoolDraw.clear();
        //将卡池中的所有卡牌添加到list集合中
        ArrayList<PlayerCard> list = new ArrayList<>(Global.playerCardPool);
        //随机抽5张
        for (int i = 0; i < 5; i++) {
            //获取0到list.size()的随机数
            int index = r.nextInt(list.size());
            //打乱集合
            Collections.shuffle(list);
            //将随机到的元素元素放入playerCardPoolDraw
            Global.playerCardPoolDraw.add(list.get(index));
            //从list集合中去除此卡牌,防止重复抽取
            list.remove(index);
        }
    }


}
