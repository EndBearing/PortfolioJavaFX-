package application;

import java.util.Random;

public class EasyEnemy extends Enemy{
    protected final String level = "easy";

    public String getLevel(){
        return level;
    }

    // 現在の山の中身を最大値にして、乱数で返す
    public int choice_number(int max_num){
        return new Random().nextInt(max_num)+1;
    }

    // 山を選択する
    public int choice_mount() {
        int mount_num = new Random().nextInt(9);
        while(!has_content(mount_num)){
            mount_num = new Random().nextInt(9);
        }
        return mount_num;
    }
    // 山の中身が指定する数字以上あれば真を返す
    public static boolean has_content(int i){
        int[] now = Main.nim.getMount();
        if(now[i] == 0){
            return false;
        }
        return true;
    }
}