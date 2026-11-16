package service;
import core.Global;

import java.io.*;

public class SettingsService {
    //设置文件路径
    public static final String SETTINGS_FILE = "config/settings.txt";
    /*
      功能: 读取 config/settings.txt 读取设置到 Global中的settings Map
  */
    public static void SettingsService_init() {
        // 读取设置文件
        File file = new File(SETTINGS_FILE);
        // 如果文件不存在则返回
        if (!file.exists()) {
            return;
        }
        // 读取文件内容
        try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"))) {
            String line;
            // 按行读取文件内容
            while ((line = br.readLine()) != null) {
                // 将行内容按 = 分割成键值对
                String[] parts = line.split("=");
                // 如果分割后的数组长度为2，则认为是有效的设置行
                if (parts.length == 2) {
                    // 获取键和值
                    String key = parts[0];
                    boolean value = Boolean.parseBoolean(parts[1]);
                    // 将键值对存入 Global.settings Map
                    Global.settings.put(key, value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
        功能: 玩家修改开关后，调用这个方法回到硬盘，把 settings Map 写回 config/settings.txt

        只存不改
    */
    public static void SettingsService_save() {
        new File("config").mkdir();
        try(BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(SETTINGS_FILE), "UTF-8"))) {
            for (String key : Global.settings.keySet()) {
                boolean value = Global.settings.get(key);
                bw.write(key + "=" + value);
                bw.newLine();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // 在此处完成代码
    }


}
