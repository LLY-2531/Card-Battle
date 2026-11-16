package jiagou;

import java.util.Random;
import java.util.Scanner;

import core.Global;
import service.*;
import view.*;


public class Main {


    public static void main(String[] args) {


        // 1. 初始化控制台环境（如果系统是 Windows，启用 ANSI 颜色）
        ConsoleView.enableWindowsAnsi();

        // 2. 读取持久化设置（从 config/settings.txt 读取开关状态）
        SettingsService.SettingsService_init();

        // 3. 填充静态配置池（只在游戏启动时执行一次）
        // 填充 5 层敌人模板、普通/Boss技能池
        EnemyService.EnemyService_initTemplates();
        // 填充 22 张永久卡牌
        CardService.CardService_initPool();
        // 填充 5 个开局 Buff
        BuffService.BuffService_initPool();

        // 4. 启动游戏主循环
        // 播放背景音乐
        SoundService.playBGM();
        ConsoleView.menuView(); // Changed from menuView() to View.menuView()

        // 5. 退出程序（menuView 内部选择退出时会执行 System.exit(0)）
    }
}
















