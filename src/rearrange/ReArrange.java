package rearrange;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ReArrange extends Application {
    
    public static Stage myStage= new Stage();
    
    public static void main(String[] args) {
        launch(args);
    }
    
    public static GameAlg alg= new GameAlg();
    public static GameScreen screen= new GameScreen();
    
    public static void setScene(Scene scene){
        myStage.setScene(scene);
    }
   
    @Override
    public void start(Stage primaryStage){
        screen.initialize();
        myStage= primaryStage;
        myStage.getIcons().add(new Image("res/gh.png"));
        myStage.setTitle("ReArrange");
        myStage.setScene(screen.ps);
        myStage.initStyle(StageStyle.TRANSPARENT);
        myStage.setFullScreenExitHint("");
        myStage.show();
    }    
}
