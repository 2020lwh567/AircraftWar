package edu.hitsz.playerDatabase;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

public class CsvOperator {
    /**读csv文件*/
    public static LinkedList<Player> readCsv(String path) throws IOException {
        LinkedList<Player> list = new LinkedList<>();
        BufferedReader reader = null;
        try {
            FileInputStream fis = new FileInputStream(path);
            InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
            reader = new BufferedReader(isr);
            String line;
            boolean firstLine = true;
            while ((line = reader.readLine()) != null) {
                if(firstLine){
                    //首行表格标题不读，跳过
                    firstLine = false;
                    continue;
                }
                String[] rows = line.split(",");
                Player player = new Player(rows[1], Integer.parseInt(rows[2]), rows[3]);
                list.add(player);
            }
            System.out.printf("读入文件 %s\n", path);
        }
        // 当文件不存在时，将异常上报到上层处理
        catch (IOException e) {
            throw e;
        }
        finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return list;
    }

    /**写入csv文件*/
    public static void writeCsv(List<Player> playerList, String filePath) {
        try {
            FileOutputStream fos = new FileOutputStream(new File(filePath));
            OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
            BufferedWriter bw = new BufferedWriter(osw);
            String header = "名次,用户名,分数,时间";
            bw.write(header + "\n");
            for (int i=0 ; i<playerList.size() ; i++) {
                Player player = playerList.get(i);
                String line = (i+1) + ","
                        + player.getTestUserName() + ","
                        + player.getScore() + ","
                        + player.getTime();
                bw.write(line + "\n");
            }
            bw.close();
            osw.close();
            fos.close();
            System.out.printf("写出文件 %s\n",filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
