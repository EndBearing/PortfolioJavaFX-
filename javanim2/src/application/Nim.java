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
    private static boolean isYourTurn = true;
    private List<Integer> playerPane = new ArrayList<Integer>();
    private List<Integer> enemyPane = new ArrayList<Integer>();
    private static Enemy enemy;

    Nim(){
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
                enemy = new MediumEnemy();
            break;
        }
    }

    public int getContent(int i){
        int i2 = mount[i];
        return i2;
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
                // 左縦一列
                return true;
            }
            if(b3 && b6){
                // 上横一行
                return true;
            }
            if(b4 && b8){
                // 左上から右下
                return true;
            }
        }
        if(b1 && b4 && b7){
            // 中横一行
            return true;
        }
        if(b2){
            if(b5 && b8){
                // 下横一行
                return true;
            }
            if(b4 && b6){
                // 右上から左下
                return true;
            }
        }
        if(b3 && b4 && b5){
                // 中縦一列
                return true;
        }
        if(b6){
            if(b7 && b8){
                // 右縦一列
                return true;
            }
        }

        return false;
    }

    public int check_pattern_and_reach(){
        int reach_int  = -1; // リーチがない時は、-1
        List<Integer> list = enemyPane;
        boolean b0 = list.contains(0);
        boolean b1 = list.contains(1);
        boolean b2 = list.contains(2);
        boolean b3 = list.contains(3);
        boolean b4 = list.contains(4);
        boolean b5 = list.contains(5);
        boolean b6 = list.contains(6);
        boolean b7 = list.contains(7);
        boolean b8 = list.contains(8);

        // 一列のうち、二つがウマッていたら、のこりのパネルの座標情報を返送

        if(b0){
            // 左縦一列
            if (b1){
                reach_int = 2;
            }else if(b2){
                reach_int = 1;
            }

            // 上横一行
            if(b3){
                reach_int = 6;
            }else if(b6){
                reach_int = 3;
            }

            // 左上から右下
            if(b4){
                reach_int = 8;
            }else if(b8){
                reach_int = 4;
            }
        }

        // 中横一行
        if(b1){
            if(b4){
                reach_int = 7;
            }else if(b7){
                reach_int = 4;
            }
        }else if(b4){
            if(b1){
                reach_int = 7;
            }else if(b7){
                reach_int = 1;
            }
        }else if(b7){
            if(b1){
                reach_int = 4;
            }else if(b4){
                reach_int = 1;
            }
        }

        if(b2){
            // 下横一行
            if(b5){
                reach_int = 8;
            }else if(b8){
                reach_int = 5;
            }

            // 右上から左下
            if(b4){
                
                reach_int = 6;
            }else if(b6){
                reach_int = 4;
            }
        }

        // 中縦一列
        if(b3){
            if(b4){
                reach_int = 5;
            }else if(b5){
                reach_int = 4;
            }
        }else if(b4){
            if(b3){
                reach_int = 5;
            }else if(b5){
                reach_int = 3;
            }
        }else if(b5){
            if(b4){
                reach_int = 3;
            }else if(b3){
                reach_int = 4;
            }
        }

        // 右縦一列
        if(b6){
            if(b7){
                reach_int = 8; 
            }else if(b8){
                reach_int = 7;
            }
        }

        return reach_int;
    }

    //--------------------------------------------------
    // 数値操作系
    //--------------------------------------------------

    // 山の中身作成
    private void make_mount_num(){
        for(int i = 0; mount.length > i; i++){
            int tmp = new Random().nextInt(max_contents_num) + 1; // 0回避
            mount[i] = tmp; 
        }
    }

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
            if(isYourTurn == true){
                playerPane.add(i);
            }else{
                enemyPane.add(i);
            }
            Main.gc.add_Pane_Color_css(i);
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
        return turn + "山の位置:" + mount_num + "; 引いた数:" + subnum;
    }

    // TODO: 先行後攻をランダムにする関数と、ゲーム開始時に起動＋表示処理


}


