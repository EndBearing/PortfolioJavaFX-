package ctrl;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;


public class StartController implements Initializable{
    @FXML // メンバ
    private Button button;

    @Override // 初期化処理
    public void initialize(URL location, ResourceBundle resources){}

    @FXML //スタートボタン
    public void onClickforStart(ActionEvent event){
        Main.showGameGUI();
    }

    @FXML // 終了ボタン
    public void onClickforEnd(ActionEvent event){
        // java終了メソッド
        System.exit(0);
    }
}
