package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ctrl.GameController;

// this class is a GameMaster.
// ex) use enemyAI Class
public class Nim{
    private final int max_contents_num = 7;
    private int mount[] = new int[9];
    // private static StringBuilder sb;
    private static boolean isYourTurn = true;
    private List<Integer> playerPane = new ArrayList<Integer>();
    private List<Integer> enemyPane = new ArrayList<Integer>();
    private static EasyEnemy enemy;

    Nim(){
        // sb = new StringBuilder();
        make_mount_num();

    }

    //--------------------------------------------------
    // ゲッターセッター
    //--------------------------------------------------

    public int[] getMount(){
        return mount;
    }

    public static boolean getIsYourTurn(){
        return isYourTurn;
    }

    // 難易度別インスタンス化
    public static void setEnemy(int levelnum){
        switch(levelnum){
            case 0:
                enemy = new EasyEnemy();
            break;
            case 1:

            break;
        }
    }

    // テスト用
    public Enemy getEnemy(){
        return enemy;
    }

    
    //--------------------------------------------------
    // ターン制御
    //--------------------------------------------------

    // ターン制御
    public static void change_player(){
        if(getIsYourTurn() == false){
            isYourTurn = true;
        }else{
            isYourTurn = false;
        }
        
    }
    
    // ターン遷移時処理
    public void next_phase(){
        if(check_pattern(isYourTurn)){
            // judge();
            Main.gc.showAlert2result();
        }
        change_player();
        if(!isYourTurn)enemy_phase();// isYourTurn がfalseなら敵の行動
    }

    // 敵CPUの行動処理
    private void enemy_phase(){
        int mount_num = enemy.choice_mount();
        int content_num = enemy.choice_number(mount_num);
        // System.out.println("山のいち：" +mount_num + " 敵の入力値:" + content_num + "元の数:" + mount[mount_num]);
        subnum_mount(mount_num, content_num);
        next_phase();
    }

    //--------------------------------------------------
    // 勝敗制御
    //--------------------------------------------------

    // 勝ちパターンの有無を判定
    public boolean check_pattern(boolean turn){
        List<Integer> list;
        if(turn == false){
            list = enemyPane;
        }else{
            list = playerPane;
        }
        boolean b0 = list.contains(0);
        boolean b1 = list.contains(1);
        boolean b2 = list.contains(2);
        boolean b3 = list.contains(3);
        boolean b4 = list.contains(4);
        boolean b5 = list.contains(5);
        boolean b6 = list.contains(6);
        boolean b7 = list.contains(7);
        boolean b8 = list.contains(8);

        if(b0){
            if (b1 && b2){
                // System.out.println("012"); // 左縦一列
                return true;
            }
            if(b3 && b6){
                // System.out.println("036"); // 上横一行
                return true;
            }
            if(b4 && b8){
                // System.out.println("048"); // 左上から右下
                return true;
            }
        }
        if(b1 && b4 && b7){
            // System.out.println("147"); // 中横一行
            return true;
        }
        if(b2){
            if(b5 && b8){
                // System.out.println("258"); // 下横一行
                return true;
            }
            if(b4 && b6){
                // System.out.println("246"); // 右上から左下
                return true;
            }
        }
        if(b3 && b4 && b5){
                // System.out.println("345"); // 中縦一列
                return true;
        }
        if(b6){
            if(b7 && b8){
                // System.out.println("678"); // 右縦一列
                return true;
            }
        }

        return false;
    }

    //--------------------------------------------------
    // 数値操作系
    //--------------------------------------------------

    // // 結果画面表示関数呼び出し（今後の機能追加のために、関数は用意しておく）
    // private void judge(){
    //     Main.gc.showAlert2result();
    // }

    // 山の中身作成
    private void make_mount_num(){
        for(int i = 0; mount.length > i; i++){
            int tmp = new Random().nextInt(max_contents_num) + 1; // 0回避
            mount[i] = tmp; 
        }
    }

    // 入力値の結合
    // public static String modifyNum(int i){
    //     if(i == -1){
    //         sb.delete(0, sb.length());
    //     }
    //     else{
    //         Integer integer = Integer.valueOf(i);
    //         String str = integer.toString();
    //         sb.append(str);
    //     }
    //     return sb.toString();
    // }

    // 山の座標番号, 送信された数
    public boolean subnum_mount(int i, int inNum){
        int content = mount[i];
        if(inNum > content){
            // System.out.println("out of range"); // TODO:範囲外であることを知らせる関数作り
            return false;
        }
        content = content - inNum;
        GameController.addListView(setLogText(i, inNum));
        if(content == 0){
            if(isYourTurn == true){ //ターン制度が成功したとき、ここの条件式がisYourTurn = true でターン切り替えがうまくいかないバグが発生した
                playerPane.add(i);
            }else{
                enemyPane.add(i);
            }
            Main.gc.toggle_Pane_Color(i);
        }
        mount[i] = content;
        
        Main.gc.display_all_mount_contents(mount);
        return true;
    }

    public String setLogText(int mount_num, int subnum){
        String turn = "";
        if(isYourTurn){
            turn = new String("あなた:");
        }else if(!isYourTurn){
            turn = new String("あいて:");

        }
        return turn + "山の位置:" +mount_num + "; 引いた数:" + subnum; // 先行or後攻 + : + 山の位置 + : + 引かれた数;を表示予定
    }



}


