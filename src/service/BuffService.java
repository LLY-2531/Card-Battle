package service;
import core.Global;
import model.Buff;

public class BuffService {
    /*
      功能: 初始化 5 个 Buff 到 buffPool 中 (强健体魄/迅捷之靴/铁壁护符/生命之泉/疾风之翼)
     调用 Global.bossSkillPool.add
      参数: void
      返回值: void
  */
    public static void BuffService_initPool() {
        // 在此处完成代码
        Global.buffPool.clear();
        Global.buffPool.add(new Buff(
                0,
                "血量上限+15",
                15,
                1,
                false,
                "强健体魄",
                0
        ));
        Global.buffPool.add(new Buff(
                0,
                "速度+4",
                0,
                2,
                false,
                "迅捷之靴",
                4
        ));
        Global.buffPool.add(new Buff(
                2,
                "基础护甲+2",
                0,
                3,
                false,
                "铁壁护符",
                0
        ));
        Global.buffPool.add(new Buff(
                0,
                "血量上限+10，当前血量补满",
                10,
                4,
                true,
                "生命之泉",
                0
        ));
        Global.buffPool.add(new Buff(
                0,
                "速度+2，血量上限+5",
                5,
                5,
                false,
                "疾风之翼",
                2
        ));
    }


    /*
        功能: 开局抽取buff并赋值加成属性
        从 buffPool 随机抽 1 个，立即应用到 player
            1. player.hpMax 和 hp 同步加上 hpMaxAdd
            Global.player.setHpMax(Global.player.getHpMax() + hpMaxAdd);
            Global.player.setHp(Global.player.getHp() + hpMaxAdd);
            2. speed 和 baseDefense 加上对应加成
            (形式同上)
            3. 如果 isFillHp 为 true，将 hp 设为 hpMax
            4. 记录 buffId 到 player
        参数: void
        返回值: void
    */
    public static void BuffService_drawAndApply() {
        // 在此处完成代码
        //从 buffPool 随机抽 1 个：索引范围必须是 0 ~ size-1，否则池子第一个 Buff 永远抽不到
        Buff buff = Global.buffPool.get(Global.rand.nextInt(Global.buffPool.size()));
        //设置血量上限
        Global.player.setHpMax(Global.player.getHpMax()+buff.getHpMaxAdd());
        //是否恢复血量
        if(buff.isFillHp()) Global.player.setHp(Global.player.getHpMax());
        //设置基础护甲
        Global.player.setBaseDefense(Global.player.getBaseDefense()+buff.getBuffDefenseAdd());
        //设置速度
        Global.player.setSpeed(Global.player.getSpeed()+buff.getSpeedAdd());
        //设置buffid
        Global.player.setBuffId(buff.getId());
    }
}


