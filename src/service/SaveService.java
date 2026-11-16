package service;
import core.Global;
import jiagou.Main;
import model.SaveData;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class SaveService {
    //存档
    public static final String SAVE_DIR = "saves";
    //设置文件
    public static final String SETTINGS_FILE = "config/settings.txt";
    //排行榜文件
    public static final String LEADERBOARD_FILE = "config/leaderboard.txt";

    /*
        负责人:万
        功能: 存档
        把当前玩家属性与层数保存到 saves/玩家名.txt (IO流)
        创建file->命名SAVE_DIR,Global.player.getName()+".txt"
        一行一行写入玩家数据：名字，层数，血量，最大血量，速度，基础防御（buff获得的，卡牌护甲每局自动清0不存档），最大能量，buff编号
        参数: floorToPlay 下次进入的层数
        返回值: void
    */
    public static void SaveService_save(int floorToPlay) {
        //创建文件夹
        new File(SAVE_DIR).mkdirs();
        //创建以玩家名命名的文件
        File file = new File(SAVE_DIR, Global.player.getName() + ".txt");
        //创建 BufferedWriter 对象
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"))) {
            bw.write("name="+Global.player.getName());
            bw.newLine();
            bw.write("floorToPlay="+String.valueOf(floorToPlay));
            bw.newLine();
            bw.write("hp="+String.valueOf(Global.player.getHp()));
            bw.newLine();
            bw.write("hpMax="+String.valueOf(Global.player.getHpMax()));
            bw.newLine();
            bw.write("speed="+String.valueOf(Global.player.getSpeed()));
            bw.newLine();
            bw.write("baseDefense="+String.valueOf(Global.player.getBaseDefense()));
            bw.newLine();
            bw.write("energyMax="+String.valueOf(Global.player.getEnergyMax()));
            bw.newLine();
            bw.write("buffId="+String.valueOf(Global.player.getBuffId()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 在此处完成代码
    }

    /*
        负责人:万
        功能: 检查 saves（SAVE_DIR） 目录下是否有存档文件
        参数: void
        返回值: boolean 是否有存档
    */
    public static boolean SaveService_hasSave() {
        File dir=new File(SAVE_DIR);
        String[] files = dir.list();
        // 如果文件夹为空或不存在，返回 false
        if (files == null || files.length == 0) {
            return false;
        }
        // 遍历文件夹中的文件，检查是否有以 .txt 结尾的文件，有则返回 true
        for (String file : files) {
            if (file.endsWith(".txt")) {
                return true;
            }
        }
        return false;
    }

    /*
        负责人:万
        功能: 读取指定名字的存档，封装为 SaveData （在model中定义）返回
        参数: name 玩家名
        返回值: SaveData 找不到返回 null
    */
    public static SaveData SaveService_load(String name) {
        File file = new File(SAVE_DIR, name + ".txt");
        if (!file.exists()) {
            return null;
        }
        SaveData data = new SaveData();
        data.setName(name);
        try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("=");
                if (parts.length == 2) {
                    String key = parts[0];
                    String value = parts[1];
                    switch (key) {
                        case "name":
                            data.setName(value);
                            break;
                        case "floorToPlay":
                            data.setFloorToPlay(Integer.parseInt(value));
                            break;
                        case "hp":
                            data.setHp(Integer.parseInt(value));
                            break;
                        case "hpMax":
                            data.setHpMax(Integer.parseInt(value));
                            break;
                        case "speed":
                            data.setSpeed(Integer.parseInt(value));
                            break;
                        case "baseDefense":
                            data.setBaseDefense(Integer.parseInt(value));
                            break;
                        case "energyMax":
                            data.setEnergyMax(Integer.parseInt(value));
                            break;
                        case "buffId":
                            data.setBuffId(Integer.parseInt(value));
                            break;
                    }
                }
            }
            // 全部循环读完，所有行解析完成，才return！！移到while外面！！
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /*
        负责人:龙
        功能: 遍历 saves（SAVE_DIR）目录，返回所有存档名（不含 .txt 后缀），按名字排序
        参数: void
        返回值: ArrayList<String> 存档名列表，没有存档时返回空列表
    */
    public static ArrayList<String> SaveService_listSaves() {
        ArrayList<String> names = new ArrayList<>();
        File dir = new File(SAVE_DIR);
        String[] files = dir.list();
        // 文件夹不存在或为空，返回空列表
        if (files == null) {
            return names;
        }
        // 收集所有 .txt 文件名，去掉后缀作为存档名
        for (String file : files) {
            if (file.endsWith(".txt")) {
                names.add(file.substring(0, file.length() - 4));
            }
        }
        Collections.sort(names);
        return names;
    }
}
