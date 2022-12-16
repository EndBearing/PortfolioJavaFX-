package application;

import java.util.Random;

public class MediumEnemy extends Enemy{
    protected final String level = "middle";
    private final int max_num = 3;

    public String getLevel(){
        return level;
    }

    // 現在の山の中身を最大値にして、乱数で返す
    public int choice_number(int mount_num){
        int num = max_num; //1-3の数
        while(!can_sub(num, mount_num)){
            num -= 1;
        }
        return num;
    }

    // 山を選択する
    public int choice_mount() {
        int reach_mount_num = Main.nim.check_pattern_and_reach();
        System.out.println(reach_mount_num);
        int mount_num;
        if(reach_mount_num == -1){
            mount_num = new Random().nextInt(9);
        }else{
            mount_num = reach_mount_num;
        }
        while(!has_content(mount_num)){
            mount_num = new Random().nextInt(9);
        }
        return mount_num;
    }

    // 中身があれば真を返す。
    public static boolean has_content(int i){
        int[] now = Main.nim.getMount();
        if(now[i] == 0){
            return false;
        }
        return true;
    }

    public static boolean can_sub(int select_num, int mount_num){
        int[] now = Main.nim.getMount();
        if(select_num <= now[mount_num]){
            return true;
        }
        return false;
    }

    // 勝ちパターンがあるかどうかを調べて、あれば、その山の数を返す
    public static int check_pattern(){

        return 0;
    }
}