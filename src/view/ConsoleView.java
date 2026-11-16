package view;
import core.Global;
import model.*;
import service.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class ConsoleView {
    public static final String RESET = "\033[0m";
    public static final String RED = "\033[31m";
    public static final String GREEN = "\033[32m";
    public static final String YELLOW = "\033[33m";
    public static final String CYAN = "\033[36m";
    public static final String MAGENTA = "\033[35m";

    /*
        功能: 启用 Windows 控制台 ANSI 颜色转义
    */
    public static void enableWindowsAnsi() {
        // 判断系统是否是 Windows
        if (System.getProperty("os.name").contains("Windows")) {
            // 启用 Windows 控制台 ANSI 颜色转义
            try {
                new ProcessBuilder("cmd", "/c", "").inheritIO().start().waitFor();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /*
        功能: 打印带颜色的文本，受 settings.get("colorOn") 控制
        Global.settings.get("colorOn");
        如果Global.settings.get("colorOn")是true->打开颜色开关
        打印color+txt+RESET
        参数：String text, 要打印的文本String color要打印的颜色
        返回值void
    */
    public static void printColored(String text, String color) {
        if (Global.settings.get("colorOn")) {
            System.out.println(color + text + RESET);
        } else {
            System.out.println(text);
        }
        // 在此处完成代码
    }

    /*
        功能: 读取一行输入，并去掉首尾空格
        参数：String prompt""
        返回值: String
    */
    public static String readLine(String prompt) {
        System.out.print(prompt);
        return Global.scan.nextLine().trim();
        }

    /*
        功能: 延迟打印
        参数：ms停顿的秒数
        返回值: void
    */
    public static void delay(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 在此处完成代码
    }

    // -------------------- 主菜单界面 --------------------
    /*
        功能: 主菜单主循环
        while(true){
         展示 1-6 选项，有存档时标注
            1 开始新游戏 -> startNewGameView
            2 读取存档 -> loadSaveView
            调用SaveService.SaveService_hasSave()判断有无存档
              如果没有存档提示没有存档，停留在主菜单界面
              如果有存档就跳转loadSaveView
            3 排行榜 -> rankView
            4 设置 -> settingsView
            5 团队介绍 -> teamView
            6 退出游戏 -> System.exit(0)
            若输入无效提示无效返回循环



        }

    */
    public static void menuView() {

        // 在此处完成代码
        while (true) {
            System.out.println("=================================");
            printColored("卡 牌 爬 塔",YELLOW);
            System.out.println("=================================");
//            展示 1-6 选项，有存档时标注
            System.out.println("1.开始新游戏");
            System.out.println("2.读取存档");
            System.out.println("3.排行榜");
            System.out.println("4.设置");
            System.out.println("5.团队介绍");
            System.out.println("6.退出游戏");
            System.out.println("=================================");
            System.out.println("请选择操作：");
            String switchNum = Global.scan.nextLine();
            switch (switchNum) {
//                1 开始新游戏 -> startNewGameView
                case "1":
                    startNewGameView();
                    break;
//                2 读取存档 -> loadSaveView
//            调用SaveService.SaveService_hasSave()判断有无存档
//            如果没有存档提示没有存档，停留在主菜单界面
//                    如果有存档就跳转loadSaveView
                case "2":
                    if (SaveService.SaveService_hasSave()) {
                        loadSaveView();
                    }
                    break;
//            3 排行榜 -> rankView
                case "3":
                    rankView();
                    break;
//            4 设置 -> settingsView
                case "4":
                    settingsView();
                    break;
//            5 团队介绍 -> teamView
                case "5":
                    teamView();
                    break;
//            6 退出游戏 -> System.exit(0)
                case "6":
                    System.exit(0);
                    break;
//            若输入无效提示无效返回循环
                default:
                    System.out.println("无效输入，请重新输入");
                    break;
            }

        }
    }


    // -------------------- 输入名字界面 --------------------
    /*
        功能: 1.输入玩家名，调用Global.player创建新的玩家对象，再setName存进玩家类
       2 初始化新游戏数据，按照数值文件中的参数初始化player属性值
       Global.player.setxxxx
       3.重置楼层数为1
       Global.floor = 1
       3.抽 Buff
       调用BuffService.BuffService_drawAndApply()
       4.进入 buffView(直接调用buffView)
    */
    public static void startNewGameView() {
        // 在此处完成代码
//      1.输入玩家名，调用Global.player创建新的玩家对象，再setName存进玩家类
        System.out.println("请输入玩家名：");
        String playerName = Global.scan.nextLine();
        Global.player = new Player();
        Global.player.setName(playerName);
//      2.初始化新游戏数据，按照数值文件中的参数初始化player属性值
//      Global.player.setxxxx
        Global.player.setHp(80);
        Global.player.setHpMax(80);
        Global.player.setSpeed(12);
        Global.player.setEnergyMax(3);
        Global.player.setEnergy(3);
        Global.player.setBaseDefense(0);
        Global.player.setTemporaryDefense(0);
//      3.重置楼层数为1
        Global.floor = 1;
//      4.抽 Buff
//      调用BuffService.BuffService_drawAndApply()
        BuffService.BuffService_drawAndApply();
//      5.进入 buffView(直接调用buffView)
        buffView();
    }

    // -------------------- 读取存档界面 --------------------
    /*
        功能: 输入存档名读取，
        1.提示玩家输入存档名
        2.判断是否有此存档（调用SaveService_load(玩家输入的存档名)）
          如果没有提示玩家——>是否重新查找，
                          是：循环
                          否：返回主菜单menuView()
          如果有存档
             调用SaveService.SaveService_load（此方法已读取存档的玩家数值并封装为saveDate）赋值给SaveDate
             //SaveDate d=SaveService.SaveService_load存档名);
             用Global.player创建一个新玩家，把saveDate的数值赋给新玩家
             3.能量补满，护甲清0
             Global.player
             4.层数变成存档的层数->SaveDate里的floorToPlay赋值给Global.floor
             5.进入buffView


    */
    public static void loadSaveView() {
        // 在此处完成代码
        //遍历存档并打印
        ArrayList<String> saveNames = SaveService.SaveService_listSaves();
        System.out.println("=================================");
        printColored("存档界面",YELLOW);
        System.out.println("=================================");
        if (saveNames.isEmpty()) {
            System.out.println("暂无存档");
        } else {
            for (String saveName : saveNames) {
                SaveData d = SaveService.SaveService_load(saveName);
                if (d != null) {
                    System.out.println("- " + saveName + "（第" + d.getFloorToPlay() + "层，血量" + d.getHp() + "/" + d.getHpMax() + "）");
                } else {
                    System.out.println("- " + saveName);
                }
            }
        }
        System.out.println("=================================");

        //死循环以重复获得存档名
        while(true){
            //命名存档名为dataname，用 readLine（nextLine）读取，避免残留换行影响后续输入
            String dataname = readLine("请输入存档名：");
            //获得存档
            SaveData saveData = SaveService.SaveService_load(dataname);
            if(saveData == null){
                System.out.println("存档不存在，重新查找请输入是，若退出回至主菜单，请回车");
                //获得玩家选项
                String choice = Global.scan.nextLine();
                //保护输入
                while(!Objects.equals(choice, "是")) {
                    if(choice.isEmpty()) return;
                    System.out.println("请重新输入");
                    choice = Global.scan.nextLine();
                }
                //继续循环输入存档
            }else{
                //设置基础属性
                Global.player.setBaseDefense(saveData.getBaseDefense());
                Global.player.setBuffId(saveData.getBuffId());
                Global.player.setEnergyMax(saveData.getEnergyMax());
                Global.player.setHp(saveData.getHp());
                Global.player.setHpMax(saveData.getHpMax());
                Global.player.setName(saveData.getName());
                Global.player.setSpeed(saveData.getSpeed());
                //设置能量点与临时护甲
                Global.player.setEnergy(Global.player.getEnergyMax());
                Global.player.setTemporaryDefense(0);
                Global.floor = saveData.getFloorToPlay();
                //读档后直接进入战斗，不再经过抽取 BUFF 界面
                battleView();
                return;
            }
        }
    }

    // -------------------- 开局 Buff 界面 --------------------
    /*
        功能: 1.展示抽到的 Buff 和当前属性
        遍历buff池（Global.buffpool）获取其中每个对象的id与玩家buffid对比（Global.player.getBuffId()）
        2.打印buff,展示玩家当前属性,打印时调用printColored
        3.进入 battleView
          回车进入 battleView
    */
    public static void buffView() {
        // 在此处完成代码
        System.out.println("=================================");
        printColored("抽取BUFF加成",YELLOW);
        System.out.println("=================================");
        System.out.println();
        //当前玩家
        Player player = Global.player;
        //遍历BUFF池
        for (Buff buff : Global.buffPool) {
            if(player.getBuffId() == buff.getId()){
                printColored("你抽中了"+buff.getName()+",此BUFF的效果为"+buff.getDescribe(),YELLOW);
            }
        }
        //展示玩家当前属性
        System.out.println("玩家当前属性:");
        printColored("血流量:"+player.getHp()+"/"+player.getHpMax(),RED);
        printColored("能量:"+player.getEnergy(),CYAN);
        printColored("速度:"+player.getSpeed(),GREEN);
        printColored("护甲:"+player.getBaseDefense(),MAGENTA);
        //回车进入battleView
        System.out.println();
        System.out.println();
        System.out.println("回车进入战斗....");
        System.out.println("=================================");
        //读取回车
        Global.scan.nextLine();
        //跳转界面
        battleView();
    }


    // -------------------- 战斗界面 --------------------
    /*
        功能: 战斗主循环
            1. 调 BattleService_start 初始化传入Global.floor
           进入战斗循环
           while（true）{
           显示第几层第几回合
            2. 展示面板(血量/能量/护甲)敌人和玩家都打印
            调用printColored打印
            3. 展示 5 张卡供选择
            遍历Global.playerCardPoolDraw
             4.让玩家选择3 张
                 清空存三张的卡牌池Global.playerCardPoolTemp
                 把这三张加入Global.playerCardPoolTemp
            5. 展示敌人意图
                技能，预估伤害（技能伤害*对应层数敌人攻击数值倍数）
                     技能伤害通过Global.currentEnemyIntent.getDamage()获取，攻击倍数通过Global.currentEnemy.getAttackBonus()获取
            6. 判先手，进入玩家出牌阶段或敌人回合
               如果玩家速度>=敌人速度->玩家先手->调用玩家回合playerTurnPhase()，否则调用敌人回合enemyTurnPhase()
        双方血量未归0则循环战斗
            7.有一方血量归0 判断输赢，
                敌人血量<=0,玩家赢调 rewardView进入奖励界面，
                玩家血量<=0,玩家输:
                    打印战败提示
                      创建新Rank类  Rank newRecord=new Rank();
                        计算成绩，把成绩，层数，玩家名，剩余血量都存入该Rank类
                           调用RankService.addRecord(newRecord);
                回主菜单

            }
    */


    public static void battleView() {
        // 在此处完成代码
        // 初始化本层战斗
        BattleService.BattleService_start(Global.floor);
        // 初始化回合，进入while循环后回合数会自增，需要初始化为0
        Global.round = 0;
        // 进入战斗循环(每一回合)
        while (true){
            // 每回合开始时，玩家补满能量，玩家和敌人护甲清0，回合数加一
            Global.player.setEnergy(Global.player.getEnergyMax());
            Global.player.setTemporaryDefense(0);
            Global.currentEnemy.setDefense(0);
            Global.player.setTempdamage(0);
            Global.round++;

            // 每回合重新随机抽 5 张牌
            CardService.CardService_drawFive();
            // 每回合重新随机抽敌人技能
            Global.currentEnemyIntent = EnemyService.EnemyService_randomSkill();

            //打印回合开始信息，玩家信息，敌人信息
            printColored("=====第 " + Global.floor + " 层第 " + Global.round + " 回合=====", YELLOW);
            printColored(Global.player.getName() + "当前属性：", RESET);
            printColored("血流量" + Global.player.getHp() + "/" + Global.player.getHpMax(), RED);
            printColored("能量" + Global.player.getEnergy() + "/" + Global.player.getEnergyMax(), CYAN);
            printColored("速度" + Global.player.getSpeed(), GREEN);
            printColored("护甲状态" + Global.player.getBaseDefense(), MAGENTA);
            printColored("=================================", RESET);
            //延迟时间
            delay(1000);

            printColored(Global.currentEnemy.getName() + "当前属性：", RESET);
            printColored("血流量" + Global.currentEnemy.getHp() + "/" + Global.currentEnemy.getHpMax(), RED);
            printColored("速度" + Global.currentEnemy.getSpeed(), GREEN);
            printColored("护甲状态" + Global.currentEnemy.getDefense(), MAGENTA);
            printColored("=================================", RESET);
            //延迟时间
            delay(1000);

            //请选择三张卡牌
            printColored("请选择三张卡牌", RESET);
            for(int i = 0; i < Global.playerCardPoolDraw.size(); i++){
                // 获取卡牌对象，详细展示卡牌信息
                PlayerCard card = Global.playerCardPoolDraw.get(i);
                printColored((i+1) + ". " + card.getName() + " 耗能：" + card.getCost() + " 效果：" + card.getDescribe(), CYAN);
            }

            // 定义一个boolean类型的值，用于判断输入是否有效，true代表无效，需要重新进入循环
            boolean isTrue = true;
            while(isTrue){
                // 初始化设置为false，表示输入有效
                isTrue = false;
                // 获取用户输入
                // 获取用户输入，提示用户输入3个卡牌编号，编号之间用空格分隔
                String input = readLine("请输入3个卡牌编号（空格分隔）：");

                // 去除输入首尾空格，并按空格分割成字符串数组，\s+表示一个或者多个空格，\表示转义字符
                String[] parts = input.trim().split("\\s+");
                // 检查输入的长度是否为3
                if (parts.length != 3) {
                    printColored("必须输入3个编号", YELLOW);
                    isTrue = true;
                    continue;
                }

                int[] cardIndices = new int[3];
                // 检查输入是否为数字
                // 遍历输入的编号，转换为int类型并存储在cardIndices数组中
                for(int i = 0; i < 3; i++){
                    int index;
                    try{
                        // 将输入的字符串转换为int类型，减去1作为卡牌索引，例如输入数字为1-5，而卡牌索引是0-4
                        index = Integer.parseInt(parts[i]) - 1;
                        // 存储的卡牌索引是，0-4其中之三
                        cardIndices[i] = index;
                    } catch (NumberFormatException e) {
                        // 输入无效，重新输入，这个NumberFormatException可以抛出非数字的输入
                        printColored("输入无效，请输入数字", YELLOW);
                        isTrue = true;
                        break;
                    }
                }
                // 检查输入的编号是否在有效范围内
                for (int i = 0; i < 3; i++) {
                    if(cardIndices[i] < 0 || cardIndices[i] >= Global.playerCardPoolDraw.size()){
                        printColored("输入无效，请输入正确的编号", YELLOW);
                        isTrue = true;
                        break;
                    }

                }

                // 检查输入的编号是否重复
                for(int i = 0; i < 3; i++){
                    for(int j=i+1; j<3; j++){
                        if(cardIndices[i] == cardIndices[j]){
                            printColored("输入无效，请输入不同的编号", YELLOW);
                            isTrue = true;
                            break;
                        }

                    }
                }

                // 如果输入有效，将选择的卡牌添加到临时卡牌池并退出循环，此时存入到临时卡牌池后，三个技能的索引就变成了012
                if(!isTrue){
                    Global.playerCardPoolTemp.clear();
                    for(int i = 0; i < 3; i++){
                        Global.playerCardPoolTemp.add(Global.playerCardPoolDraw.get(cardIndices[i]));
                    }
                    break;
                }

            }

            // 打印选择的卡牌和敌人意图
//            printColored("你选择了：" + Global.playerCardPoolTemp, RED);
            // 打印选卡列表
            printColored("你选择了：", CYAN);
//            for (int i = 0; i < Global.playerCardPoolTemp.size(); i++) {
//                PlayerCard c = Global.playerCardPoolTemp.get(i);
//                printColored((i + 1) + "." + c.getName(), CYAN);
//                printColored("耗能：" + c.getCost(), CYAN);
//                printColored("效果：" + c.getDescribe(), CYAN);
//            }
            for(int i = 0; i < Global.playerCardPoolTemp.size(); i++){
                // 获取卡牌对象，详细展示卡牌信息
                PlayerCard card = Global.playerCardPoolTemp.get(i);
                printColored((i+1) + ". " + card.getName() + " 耗能：" + card.getCost() + " 效果：" + card.getDescribe(), CYAN);
            }
            printColored("=================================", RESET);
            //延迟时间
            delay(1000);



            printColored("敌人意图:", RED);

            // 技能名：currentEnemyIntent 是 Object，需要强转
            String skillName = "未知技能";
            // 判断 currentEnemyIntent 的类型并获取技能名
            if (Global.currentEnemyIntent instanceof NormalEnemySkill) {
                skillName = ((NormalEnemySkill) Global.currentEnemyIntent).getName();
            } else if (Global.currentEnemyIntent instanceof BossSkill) {
                skillName = ((BossSkill) Global.currentEnemyIntent).getName();
            }

            printColored("技能：" + skillName, RED);
            printColored("预计伤害：" + Global.getIntentDamage(), RED);
            printColored("=================================", RESET);
            //延迟时间
            delay(1000);


//            // 打印选卡列表
//            for (int i = 0; i < Global.playerCardPoolTemp.size(); i++) {
//                PlayerCard c = Global.playerCardPoolTemp.get(i);
//                printColored((i + 1) + "." + c.getName(), CYAN);
//                printColored("  耗能：" + c.getCost(), CYAN);
//                printColored("  效果：" + c.getDescribe(), CYAN);
//            }

            // 根据速度判定先手，进入玩家出牌阶段或敌人回合
            if(Global.player.getSpeed() >= Global.currentEnemy.getSpeed()){
                printColored("玩家先手", CYAN);
                // 玩家出牌，返回值表示是正常结束回合或者玩家存档退出
                Boolean playerTurnPhaseResult = playerTurnPhase();
                // 如果敌人血量为0且玩家未退出，则进入奖励界面
                if(Global.currentEnemy.getHp() <= 0 && playerTurnPhaseResult){
                    // 播放胜利音效
                    SoundService.playSound("sound/win.wav");
                    printColored("敌人血量为0,玩家赢", GREEN);

                    // 创建Rank对象并设置属性
                    Rank rank = new Rank();
                    rank.setFloor(Global.floor);
                    rank.setName(Global.player.getName());
                    rank.setRemainingHp(Global.player.getHp());
                    rank.setScore(Global.floor * 100 + Global.player.getHp());
                    RankService.addRecord(rank);

                    // 打印本局结束信息
                    printColored("========第" + Global.floor + "层通过========", CYAN);
                    printColored("到达层数：" + Global.floor, CYAN);
                    printColored("剩余血量：" + Global.player.getHp(), CYAN);
                    printColored("本局得分：" + (Global.floor * 100 + Global.player.getHp()) , CYAN);
                    printColored("=================================", CYAN);

                    // 如果是第五层，则返回主菜单，否则进入奖励界面
                    if (Global.floor >= 5) {
                        readLine("按回车返回主菜单");
                        return;
                    }
                    readLine("按回车进入奖励界面");
                    rewardView();
                    // 跳出战斗循环，防止奖励界面之后回来
                    return;
                }else if(!playerTurnPhaseResult){
                    printColored("玩家存档退出", RED);
                    // 跳出战斗循环，防止存档退出后回来
                    return;
                }
                // 若敌人未死亡，同时没有存档退出，进入敌人回合
                printColored("敌人回合", YELLOW);
                enemyTurnPhase();
                // 如果玩家血量为0，则游戏结束
                if(Global.player.getHp() <= 0){
                    // 播放失败音效
                    SoundService.playSound("sound/lose.wav");
                    printColored("玩家血量为0,玩家输", RED);
                    // 计入排行榜
                    Rank rank = new Rank();
                    rank.setFloor(Global.floor);
                    rank.setName(Global.player.getName());
                    rank.setRemainingHp(Global.player.getHp());
                    rank.setScore(Global.floor * 100 + Global.player.getHp());
                    RankService.addRecord(rank);

                    // 打印本局结束信息
                    printColored("=============本局结束==============", CYAN);
                    printColored("到达层数：" + Global.floor, CYAN);
                    printColored("本局得分：" + (Global.floor * 100 + Global.player.getHp()) , CYAN);
                    printColored("=================================", CYAN);

                    readLine("按回车返回主菜单");
                    return;
                }
            }else{
                // 敌人先手，逻辑与玩家先手类似
                printColored("敌人先手", YELLOW);
                enemyTurnPhase();
                if(Global.player.getHp() <= 0){
                    // 播放失败音效
                    SoundService.playSound("sound/lose.wav");
                    printColored("玩家血量为0,玩家输", RED);
                    // 计入排行榜
                    Rank rank = new Rank();
                    rank.setFloor(Global.floor);
                    rank.setName(Global.player.getName());
                    rank.setRemainingHp(Global.player.getHp());
                    rank.setScore(Global.floor * 100 + Global.player.getHp());
                    RankService.addRecord(rank);
                    // 打印本局结束信息
                    printColored("=============本局结束==============", CYAN);
                    printColored("到达层数：" + Global.floor, CYAN);
                    printColored("本局得分：" + (Global.floor * 100 + Global.player.getHp()), CYAN);
                    printColored("=================================", CYAN);

                    readLine("按回车返回主菜单");
                    return;
                }
                printColored("玩家回合", YELLOW);
                Boolean playerTurnPhaseResult = playerTurnPhase();
                if(Global.currentEnemy.getHp() <= 0 && playerTurnPhaseResult == true){
                    // 播放胜利音效
                    SoundService.playSound("sound/win.wav");
                    printColored("敌人血量为0,玩家赢", GREEN);

                    // 计入排行榜
                    Rank rank = new Rank();
                    rank.setFloor(Global.floor);
                    rank.setName(Global.player.getName());
                    rank.setRemainingHp(Global.player.getHp());
                    rank.setScore(Global.floor * 100 + Global.player.getHp());
                    RankService.addRecord(rank);

                    // 打印本局结束信息
                    printColored("========第" + Global.floor + "层通过========", CYAN);
                    printColored("到达层数：" + Global.floor, CYAN);
                    printColored("剩余血量：" + Global.player.getHp(), CYAN);
                    printColored("本局得分：" + (Global.floor * 100 + Global.player.getHp()) , CYAN);
                    printColored("=================================", CYAN);

                    if (Global.floor >= 5) {
                        readLine("按回车返回主菜单");
                        return;
                    }
                    readLine("按回车进入奖励界面");
                    rewardView();
                    return;
                }else if(playerTurnPhaseResult == false){
                    printColored("玩家存档退出", RED);
                    return;
                }
            }

        }
    }
    /*
        功能: 玩家出牌阶段
            1.展示手中的三张手牌，循环接受输入
            while(true){
            1.玩家输入牌号
            2.校验能量
            3.结算伤害BattleService.
            （当能剩余能量>手牌最小能量，就循环让玩家出牌， 输入 0 结束（无论能量剩余几点都结束出牌），
            输入 -1 存档退出，调用SaveService.SaveService_save()保存并返回主菜单）
            2.输入牌号出牌并延迟打印日志
            调用BattleService.BattleService_playerPlayCard(传入手牌编号)此方法已写入日志，可遍历此方法返回值后延迟打印

            }

    */


    public static boolean playerTurnPhase() {
        // 在此处完成代码
        // 打印手牌信息
        printColored("--- 手牌信息 ---", RESET);
        for (int i = 0; i < Global.playerCardPoolTemp.size(); i++) {
            PlayerCard c = Global.playerCardPoolTemp.get(i);
            printColored((i + 1) + "." + c.getName(), CYAN);
            printColored("耗能：" + c.getCost(), CYAN);
            printColored("效果：" + c.getDescribe(), CYAN);
        }

        while(true){
            // 打印当前能量值
            printColored("当前能量值：" + Global.player.getEnergy(), CYAN);
            // 获取当前能量值
            int energy = Global.player.getEnergy();
            // 判断能量是否不足
            if(energy == 0){
                printColored("能量值不足，已结束本回合", CYAN);
                break;
            }
            int cardIndex;
            // 获取玩家输入的牌号，输入0结束回合，输入-1存档退出，输入非数字报错，并重新输入
            try{
                cardIndex = Integer.parseInt(readLine("请输入牌号(注：输入0结束本回合，输入-1存档退出)："));
            }catch(Exception e){
                printColored("无效的输入，请重新输入！", YELLOW);
                continue;
            }
            // 判断输入是否为0或-1
            if(cardIndex == 0){
                printColored("已结束本回合", CYAN);
                break;
            }else if(cardIndex == -1){
                SaveService.SaveService_save(Global.floor);
                return false;
            }else if(cardIndex > 0 && cardIndex <= Global.playerCardPoolTemp.size()){
                // 获取玩家输入的卡牌
                PlayerCard card = Global.playerCardPoolTemp.get(cardIndex - 1);
                // 判断卡牌能量是否足够，如果足够则打出卡牌
                if(card.getCost() <= energy){
                    ArrayList<String> logs = BattleService.BattleService_playerPlayCard(cardIndex - 1);
                    // 播放攻击音效
                    SoundService.playSound("sound/hit.wav");
                    for(String log : logs){
                        printColored(log, RESET);
                        delay(1000);
                    }
                    if(Global.currentEnemy.getHp() <= 0){
                        return true;
                    }
                    // 循环多次出牌
                    continue;
                }else{
                    // 能量不足，提示玩家重新选择
                    printColored("能量不足，请重新选择！", CYAN);
                    continue;
                }
            }else{
                // 无效的牌号，提示玩家重新输入
                printColored("无效的牌号，请重新输入！", YELLOW);
                continue;
            }
        }
        return true;
    }

    /*
        功能: 敌人回合阶段
           1. 调 BattleService_enemyTurn获取日志，遍历延迟打印日志
    */
    public static void enemyTurnPhase() {
        // 在此处完成代码
        //调用方法，获取日志，存入集合中
        ArrayList<String> logs = BattleService.BattleService_enemyTurn();
        //遍历集合，打印数据
        for(String log : logs){
            //使用定义好的打印方法打印，可附加颜色
            printColored(log,YELLOW);
            //延迟打印，单位为毫秒
            delay(1000);
        }
    }


    // -------------------- 奖励界面 --------------------
    /*
        功能: 1.胜利后回血、恢复最大血量的25%且不超过最大血量Global.player.setHp
        2.打印日志
        3.选奖励、打印奖励选项(提升血量最大值，速度，护甲属性)
            接收玩家选项
               把对应的属性提升数值写入Global.player
                 打印日志
         4.层数加一Global.floor
         5.调用SaveService.SaveService_save(Global.floor)存档
         6.提示回车进入下一层
         7.调用battleView开始新战局

    */
    public static void rewardView() {
        // 在此处完成代码
        System.out.println("=================================");
        printColored("排 行 榜",YELLOW);
        System.out.println("=================================");
//      1.胜利后回血、恢复最大血量的25%且不超过最大血量Global.player.setHp
        printColored("=================================", RED);
        printColored("恭喜通关第" + Global.floor + "层获得奖励", RED);
        printColored("=================================", RED);
        int temp = Global.player.getHp();
        Global.player.setHp(Global.player.getHp() + (int) (Global.player.getHpMax() * 0.25));
        if (Global.player.getHp() >= Global.player.getHpMax()) {
            Global.player.setHp(Global.player.getHpMax());
        }
//      2.打印日志
        printColored("恢复了" + (Global.player.getHp() - temp) + "点血量", GREEN);
//      3.选奖励、打印奖励选项(提升血量最大值，速度，护甲属性)
        printColored("从以下奖励中选择:", CYAN);
        printColored("1.提升血量最大值(+12)", GREEN);
        printColored("2.提升速度(+1)", GREEN);
        printColored("3.提升护甲(+3)", GREEN);
        System.out.println("=================================");
//      接收玩家选项
        String switchNum = Global.scan.nextLine();
//      把对应的属性提升数值写入Global.player
//                打印日志
        do {
            if (switchNum.equals("1") || switchNum.equals("2") || switchNum.equals("3")) {
                switch (switchNum) {
                    case "1":
                        Global.player.setHpMax(Global.player.getHpMax() + 12);
                        Global.player.setHp(Global.player.getHp() + 12);
                        printColored("最大生命值增加了12点血量", GREEN);
                        break;
                    case "2":
                        Global.player.setSpeed(Global.player.getSpeed() + 1);
                        printColored("速度增加了1点", GREEN);
                        break;
                    case "3":
                        Global.player.setBaseDefense(Global.player.getBaseDefense() + 3);
                        printColored("护甲增加了3点", GREEN);
                        break;
                }
            } else {
                printColored("无效的选项，请重新选择", CYAN);
                switchNum = Global.scan.nextLine();
            }
        } while (!switchNum.equals("1") && !switchNum.equals("2") && !switchNum.equals("3"));
//      4.层数加一Global.floor
        Global.floor++;
//      5.调用SaveService.SaveService_save(Global.floor)存档
        SaveService.SaveService_save(Global.floor);
//      6.提示回车进入下一层
        printColored("请按回车进入下一层", CYAN);
//      7.调用battleView开始新战局
        battleView();
    }




    // -------------------- 设置界面 --------------------
    /*
        功能: 修改开关并保存
       Global 已申明
        public static HashMap<String, Boolean> settings = new HashMap<String, Boolean>();
    static {
        settings.put("soundOn", true);
        settings.put("bgmOn", true);
        settings.put("colorOn", true);
    }
    while（true）{
    1.打印设置菜单
       调用Global.settings.获取开关状态
       打印：1.颜色显示：开
            2.背景音乐：开
            3.音效：开
            4.返回主菜单
         这样的界面
         提示玩家可以通过输入开关序号修改开关状态
         如果玩家输入1/2/3->开变关，关变开
           输入4->返回主菜单

    }


    */
    public static void settingsView() {
        // 在此处完成代码
//      修改开关并保存
        while (true) {
            System.out.println("=================================");
            printColored("设置",YELLOW);
            System.out.println("=================================");
//      1.打印设置菜单
//      调用Global.settings.获取开关状态
//      打印：
//      1.颜色显示：开
//      2.背景音乐：开
//      3.音效：开
//      4.返回主菜单
            System.out.println("1.颜色显示：" + Global.settings.get("colorOn"));
            System.out.println("2.背景音乐：" + Global.settings.get("bgmOn"));
            System.out.println("3.音效：" + Global.settings.get("soundOn"));
            System.out.println("4.返回主菜单");
            System.out.println("=================================");
//      提示玩家可以通过输入开关序号修改开关状态
            String switchNum = readLine("请输入你的选择：").trim();
//      如果玩家输入1/2/3->开变关，关变开
            switch (switchNum) {
                case "1":
                    Global.settings.put("colorOn", !Global.settings.get("colorOn"));
                    break;
                case "2":
                    Global.settings.put("bgmOn", !Global.settings.get("bgmOn"));
                    // 背景音乐需要多加一条让他立马停止
                    if (Global.settings.get("bgmOn")) {
                        SoundService.playBGM();
                    } else {
                        SoundService.stopBGM();
                    }
                    break;

                case "3":
                    Global.settings.put("soundOn", !Global.settings.get("soundOn"));
                    break;
//      输入4->返回主菜单
                case "4":
                    return;
                default:
                    printColored("无效的选项，请重新选择", RED);
            }
            SettingsService.SettingsService_save();
        }
    }

    // -------------------- 团队介绍界面 --------------------
    /*
        功能: 展示成员
    */
    public static void teamView() {
        // 在此处完成代码
        System.out.println("=================================");
        printColored("本 游 戏 由 能*跑*就*行 制 作",YELLOW);
        System.out.println("=================================");
        printColored("宋~   负责 组织会议，实现代码",GREEN);
        printColored("万    负责 架构设计，实现代码",GREEN);
        printColored("聂童童 负责 原型设计，实现代码",GREEN);
        printColored("陈    负责 实现代码",GREEN);
        printColored("龙    负责 实现代码",GREEN);
        System.out.println("=================================");
        readLine("按回车键返回主菜单");
    }
//---------------------------排行榜界面------------------
    /*
      功能：展示排行榜
      1.调用RankService.RankService_load()获取排行榜数据装入ArrayList<Rank>
          如果没有数据打印提示
        2.遍历打印排行榜数据（）
            把每个元素赋值给Rank对象
              再通过Rank对象的get方法获取数据打印
         3.返回主菜单
    * */
public static void rankView() {
    // 在此处完成代码
    System.out.println("=================================");
    printColored("排 行 榜",YELLOW);
    System.out.println("=================================");
//      1.调用RankService.RankService_load()获取排行榜数据装入ArrayList<Rank>
//              如果没有数据打印提示
    ArrayList<Rank> ranks = RankService.loadRecords();
    if (ranks.isEmpty()) {
        System.out.println("没有数据");
        return;
    }
//      2.遍历打印排行榜数据（）
//      把每个元素赋值给Rank对象
//              再通过Rank对象的get方法获取数据打印
    System.out.println("玩家姓名\t" + "分数\t" + "\t层数\t" + "\t剩余血量");
    for (Rank rank : ranks) {
        System.out.println(rank.getName() + "\t\t" + rank.getScore() + "\t\t" + rank.getFloor() + "\t\t" + rank.getRemainingHp());
    }
//      3.返回主菜单3
    System.out.println("按回车返回主菜单");
    if (Global.scan.nextLine().isEmpty())
        System.out.println("=================================");
}
}
