package service;

import core.Global;
import model.Rank;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;


public class RankService {
    private static final String RANK_FILE = "config/leaderboard.txt";
    /*
    * 功能：添加记录
    * 1.调用loadRecords()读取旧数据存入新建的集合中
    * 2.把newRecord加进新集合
    * 3.按分数(score)从大到小排序
    * 4.如果超过10条，只保留前10条
    * 5.调用saveRecords()把新集合写回文件
    * */


    public static void addRecord(Rank newrecord) {
        // 在此处完成代码
        ArrayList<Rank> nowRecords = new ArrayList<>(loadRecords());
        nowRecords.add(newrecord);
        //重新排序
        nowRecords.sort(cmpRank);
        //筛掉重名存档：排序后同一名字第一次出现的就是该名字的最高分，只保留这一条
        HashSet<String> names = new HashSet<>();
        Iterator<Rank> it = nowRecords.iterator();
        while (it.hasNext()) {
            if (!names.add(it.next().getName())) {
                it.remove();
            }
        }
        //取前10个
        if(nowRecords.size() > 10){
            nowRecords.subList(10,nowRecords.size()).clear();
        }
        //写入文件
        saveRecords(nowRecords);
    }
    //自定义一个比较器cmpRank
    static Comparator<Rank> cmpRank = (a, b) ->{
        return b.getScore() - a.getScore();
    };
    /*
    功能：读取成绩
    从文件中读取所有记录，封装成ArrayList返回
    1.创建一个ArrayList<Rank>列表，用于存储排行榜数据。
    2.创建File指向RANK_FILE
        如果不存在->返回空列表
        存在->使用BufferedReader读取文件中的数据，
        while(文件里面还有字){

        使用split(",")方法将数据切分。
          把切分出来的数据存入ArrayList<Rank>中
          }

    3.返回ArrayList<Rank>

    *

    */

    public static ArrayList<Rank> loadRecords()  {
        // 在此处完成代码
        // 创建一个ArrayList<Rank>列表，用于存储排行榜数据。
        ArrayList<Rank> ranks = new ArrayList<>();
        // 创建File对象，指向RANK_FILE
        File file = new File(RANK_FILE);
        if (!file.exists()) {
            return ranks;
        }
        try {
            // 创建BufferedReader对象，用于读取文件数据
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            // 读取文件第一行数据
            String line = bufferedReader.readLine();
            // 循环读取文件数据，直到文件末尾
            while (line != null) {
                // 使用split(",")方法将数据切分。
                String[] parts = line.split(",");
                // 如果切分后的数据长度为4，则创建Rank对象并添加到列表中
                if (parts.length == 4) {
                    // 创建Rank对象并添加到列表中,这里假设parts[0]是名字，parts[1]是分数，parts[2]是层数，parts[3]是剩余血量，传入顺序只能这样，可能是构造器中按照字母排序
                    ranks.add(new Rank(Integer.parseInt(parts[2]), parts[0], Integer.parseInt(parts[3]), Integer.parseInt(parts[1])));
                }
                // 读取下一行数据
                line = bufferedReader.readLine();
            }
            // 关闭BufferedReader对象
            bufferedReader.close();
        } catch (IOException e) {
        }
        return ranks;
    }
    /*
    功能：写回文件
    1.创建config文件夹
    2.把list写入RANK_FILE（这是定义好的路径“config/leaderboard.txt”）
      名字+“，”+分数+“，”+层数+“，”+剩余血量
     */
    private static void saveRecords(ArrayList<Rank> list) {
        new File("config").mkdir();
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(RANK_FILE), StandardCharsets.UTF_8))) {
            for (Rank rank : list) {
                bw.write(rank.getName() + "," + rank.getScore() + "," + rank.getFloor() + "," + rank.getRemainingHp());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}