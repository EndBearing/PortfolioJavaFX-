package application;

import ctrl.GameController;
import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

// ディスプレイ管理だけ。
public class Main extends Application{
    private static Scene scene;
    private static Scene scene2;

    private static Stage StartStage;
    private static final String GAME_UI_URL = "/application/GameGUI.fxml";
    private static final String START_UI_URL = "/application/StartGUI.fxml";

    public static Nim nim;
    public static GameController gc;

    @Override
    public void start(Stage StartStage){
        Main.StartStage = StartStage;
        try{
            Parent root_StartFXML = FXMLLoader.load(getClass().getResource(Main.START_UI_URL));
            Parent root_GameFXML  = FXMLLoader.load(getClass().getResource(Main.GAME_UI_URL));

            Main.scene  = new Scene(root_StartFXML);
            Main.scene2 = new Scene(root_GameFXML);

            StartStage.setTitle("JAVAFX--Nim & TIC TAC TOE");
            StartStage.getIcons().add(new Image("/image/icon_image.png")); // icon指定
            StartStage.setResizable(false); // ウィンドウサイズ固定
            showStartGUI();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    // スタート画面を表示
    public static void showStartGUI(){
        try{
            System.gc();
            StartStage.setScene(Main.scene);
            StartStage.show();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    // ゲーム画面を表示
    public static void showGameGUI(){
        try{
            nim = new Nim();
            gc = new GameController();
            StartStage.setScene(Main.scene2); 
            StartStage.show();
            gc.showAlert(); // 難易度選択用(今後実装)
        } catch(Exception e){
            System.out.println("missing try show GameUI");
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        launch(args);
    }
}
