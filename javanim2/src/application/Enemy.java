package application;

public class Enemy {
    protected String level = "non";

    public String getLevel(){
        return level;
    }


    // ここら辺はオーバーライド用
    public int choice_number(int max_num){
        return 0;
    }
    public int choice_mount() {
        return 0;
    }
    public static boolean has_content(int i){
            return false;
    }
}
