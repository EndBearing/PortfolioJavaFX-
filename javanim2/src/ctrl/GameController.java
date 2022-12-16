package ctrl;

import application.Main;
import application.Nim;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class GameController implements Initializable {

    private int modified_num;
    private boolean flg_played = false;
    private boolean[] checked_list = new boolean[9];
    private static List<Text> textList = new ArrayList<Text>();
    private static List<TextFlow> paneList = new ArrayList<TextFlow>();
    public static ObservableList<String> gameRecord;
    public static ListView<String> listView;

    @FXML private Button button;
    @FXML private Button num1;
    @FXML private Button num2;
    @FXML private Button num3;

    @FXML private GridPane gridpane;

    @FXML private Text displaying_number;

    @FXML private BorderPane BP;

    @FXML
    public void toStartGUI(ActionEvent event){showAlert2home();}
    public void exitGame(ActionEvent event){showAlert2exit();}
    public void select1(ActionEvent event){
        selectNumber(event);
        displayNum(modified_num);
    }
    public void select2(ActionEvent event){
        selectNumber(event);
        displayNum(modified_num);
    }
    public void select3(ActionEvent event){
        selectNumber(event);
        displayNum(modified_num);
    }
    public void onClickForDecide(ActionEvent event){
        inspectNum();
        if(flg_played){
            flg_played = false; // プレイヤー処理の終了
            Main.nim.next_phase();
        }
    }

    

    @Override // FXML読み込み後の初期設定
    public void initialize(URL location, ResourceBundle resources){

        // logLists作成
        gameRecord = FXCollections.observableArrayList();
        listView = new ListView<String>(gameRecord);
        gameRecord.clear();
        gameRecord.add("化け猫2");
        BP.setRight(listView);


        // gridpane内コンポーネント作成
        int areanum = 0;
        for(int x = 0; x < 3; x++){
            for(int y = 0; y <3; y++){
                textList.add(new Text(y +":"+ x + ""));
                paneList.add(new TextFlow());
                Text text = textList.get(areanum);
                TextFlow tflow = paneList.get(areanum);
                tflow.getChildren().add(text);
                gridpane.add(tflow, x, y);
                areanum++;
            }
        }

        // マウント内数値の表示にクリックイベントを設定
        for(int i = 0; i < 9; i++){
            final int TEXT_NUM = i;  // get関数の仕様により、定数として格納
            textList.get(TEXT_NUM).setOnMouseClicked( event -> checked_list[TEXT_NUM] = toggleChecked(checked_list[TEXT_NUM], TEXT_NUM));
        }
    }


    //--------------------------------------------------
    // ゲッターセッター
    //--------------------------------------------------
    public boolean getFlg_played(){
        return flg_played;
    }

    public void setFlg_played(boolean b){
        flg_played = b;
    }

    public List<Text> getTextList(){
        return textList;
    }

    public static ListView<String> getListView(){
        return listView;
    }
    public static ObservableList<String> getNames(){
        return gameRecord;
    }

    //--------------------------------------------------
    // 表示関連
    //--------------------------------------------------

    // 山選択のオンオフの切り替え（文字色も変更）
    private boolean toggleChecked(boolean isChecked, int listNum){
        // 選択された山が空なら終了
        if(Main.nim.getMount()[listNum] == 0){
            return isChecked;
        }
        
        // 次の値設定のためにリセット
        int checked_num = check_toggle_and_num(checked_list);
        if(checked_num != -1){
        textList.get(checked_num).setFill(Color.BLACK);
        display_mount_content(checked_num);
        checked_list[checked_num] = false;
        }

        // 値設定
        if(isChecked == false){
            textList.get(listNum).setFill(Color.RED);
            isChecked = true;
        }else{
            textList.get(listNum).setFill(Color.BLACK);
            isChecked = false;
        }
        return isChecked;
    }

    // 空パネルの表示色変更
    public void add_Pane_Color_css(int coordinate){
        TextFlow tflow = paneList.get(coordinate);
        boolean player = Nim.getIsYourTurn();
        if(player == false){
            tflow.setStyle( "-fx-background-color: green" );
        }else{
            tflow.setStyle( "-fx-background-color: blue" );
        }
    }

    // 山の中身を領域に表示する（全て表示）
    public void display_all_mount_contents(int[] mount){
        for(int i = 0; mount.length > i; i++){
            try {
                mount = Main.nim.getMount();
                Integer inter = Integer.valueOf(mount[i]);
                textList.get(i).setText(inter.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } 
    }

    // 山の中身を領域に表示する（引数指定の山だけ表示）
    public void display_mount_content(int i){
        try {
            int[] mount = Main.nim.getMount();
            Integer inter = Integer.valueOf(mount[i]);
            textList.get(i).setText(inter.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    } 

    // 画面に入力値を表示
    private void displayNum(int receved_num) {
        if(receved_num < 1){ // 入力値を間違いなく空にするとき-1による変更を可能にする目的
            displaying_number.setText("0");
            modified_num = 0;
        }else{
            displaying_number.setText(Integer.toString(receved_num));
        }
    }

    // gridpane以下のコンポーネントの初期化
    private void reset_someSetting(){
        for (int i = 0; i < checked_list.length; i++) {
            // パネルの初期化（背景CSSリセット）
            paneList.get(i).setStyle("");
            // テキストの初期化（選択時状態を解除）
            textList.get(i).setFill(Color.BLACK);
        }
        // logの中身を削除
        gameRecord.clear();
    }

    //--------------------------------------------------
    // ダイアログ表示
    //--------------------------------------------------

    // GameGUIに来た時にダイアログ表示（難易度選択画面）
    // TODO: 今後、このダイアログ上で、生成する敵の難易度選択をできるようにする
    // TODO: 高難易度ＣＰＵの作成
    public void showAlert() {
		Optional<ButtonType> result = showDialog("ゲームを開始します");
        if (result.get() == ButtonType.OK) {
            Nim.setEnemy(1); // テスト用に１にしてる。　0指定でeasy
        }else if(result.get() == ButtonType.CANCEL){
            Nim.setEnemy(1);
        }
        display_all_mount_contents(Main.nim.getMount());
    }

    // 勝敗表示のダイアログ表示
    public void showAlert2result(){
        Optional<ButtonType> result;
        if(Nim.getIsYourTurn()){
            result = show_single_Dialog("あなたの勝ちです \n スタート画面に戻ります");
        }else{
            result = show_single_Dialog("あなたの負けです \n スタート画面に戻ります");
        }

        if(result.get() == ButtonType.OK){
            reset_someSetting();
            Main.showStartGUI();
        }
    }

    // スタート画面に戻るダイアログ表示
    private void showAlert2home() {
		Optional<ButtonType> result = showDialog("スタート画面に戻ります");
        if (result.get() == ButtonType.OK) {
            reset_someSetting();
            Main.showStartGUI();
        }else if(result.get() == ButtonType.CANCEL){}
	}

    // ゲームを終了するダイアログ表示
    private void showAlert2exit() {
		Optional<ButtonType> result = showDialog("本当にゲームをやめますか？");
        if (result.get() == ButtonType.OK) {
            System.exit(0);
        }else if(result.get() == ButtonType.CANCEL){}
	}

    // 骨
    // dialogを表示（ボタン複数）。返り値は選択されたボタンのvalue
    public Optional<ButtonType> showDialog(String s){
        Alert dialog = new Alert(AlertType.CONFIRMATION);
		dialog.setHeaderText(null);
		dialog.setContentText(s);
		Optional<ButtonType> result = dialog.showAndWait();
        return result;
    }

    // 骨
    // dialogを表示（ボタン単数）。返り値は選択されたボタンのvalue
    public Optional<ButtonType> show_single_Dialog(String s){
        Alert dialog = new Alert(AlertType.INFORMATION);
		dialog.setHeaderText(null);
		dialog.setContentText(s);
		Optional<ButtonType> result = dialog.showAndWait();
        return result;
    }


    //--------------------------------------------------
    // その他
    //--------------------------------------------------

    // Nimシステム側に決定された数字を転送
    public void inspectNum(){
        if(Nim.getIsYourTurn()){
            int mount_num = check_toggle_and_num(checked_list);
            // 入力値が0, または, 山が選択されていないとき終了
            if(modified_num != 0 && mount_num != -1){
                flg_played = Main.nim.subnum_mount(mount_num, modified_num);
            }
            displayNum(-1); // 入力後数値の初期化
        }
    }

    public void inspectClickedNum() {
        if(Nim.getIsYourTurn()){
            int mount_num = check_toggle_and_num(checked_list);
            
            // 入力値が0, または, 山が選択されていないとき終了
            if(modified_num != 0 && mount_num != -1){
                flg_played = Main.nim.subnum_mount(mount_num, modified_num);
            }
            displayNum(-1); // 入力後数値の初期化
        }
    }

    // booleanを調べ、オン状態ならその番号を返す.(checked_listの判定に使ったが、カプセル化を意識)
    public int check_toggle_and_num(boolean[] b){
        for (int i = 0; i < b.length; i++) {
            if(b[i] == true) return i;
        }
        return -1;
    }

    // numberボタンの数値選択処理
    public void selectNumber(ActionEvent event){
        Button b = (Button)event.getSource();
        String s = b.getText();
        modified_num = Integer.parseInt(s);
    }

    public static void addListView(String s) {      
        gameRecord.add(s);
    }

}
