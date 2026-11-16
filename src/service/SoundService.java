package service;

import core.Global;
import javax.sound.sampled.*;
import java.io.File;

public class SoundService {

    private static Clip bgmClip;

    //背景音乐（循环）
    public static void playBGM() {
        // 读取状态
        if (!Global.settings.get("bgmOn")) return;
        if (bgmClip != null && bgmClip.isRunning()) return;
        try {
            // 读取文件
            AudioInputStream ais = AudioSystem.getAudioInputStream(new File("music/bgm.wav"));
            // 获取Clip
            bgmClip = AudioSystem.getClip();
            // 打开文件
            bgmClip.open(ais);
            // 循环播放
            bgmClip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            // 没文件/没声卡，静默跳过
        }
    }

    //停止背景音乐
    public static void stopBGM() {
        if (bgmClip != null) {
            bgmClip.stop();
            bgmClip.close();
            bgmClip = null;
        }
    }

    //播放一次音效
    public static void playSound(String path) {
        if (!Global.settings.get("soundOn")) return;
        try {
            // 读取文件
            AudioInputStream ais = AudioSystem.getAudioInputStream(new File(path));
            // 获取Clip
            Clip clip = AudioSystem.getClip();
            // 打开文件
            clip.open(ais);
            // 播放
            clip.start();
        } catch (Exception e) {
            System.out.println("播放音效失败：" + e);
        }
    }
}
