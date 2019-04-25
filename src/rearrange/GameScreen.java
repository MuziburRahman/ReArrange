
package rearrange;

import graphics.ColorX;
import graphics.PointPolar;
import graphics.WindowsButton;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.animation.PathTransition;
import javafx.scene.layout.Background;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;


public final class GameScreen{
    public Scene ps;
    public static ColorX psFill = ColorX.rgb(0, 26, 0);
    public static final AnchorPane MAINSCREEN = new AnchorPane(), GAMESCREEN = new AnchorPane();
    public double radius=50 ;
    public static final double MAINSCREENWIDTH= Screen.getPrimary().getBounds().getWidth()*0.8;
    public static final double MAINSCREENHEIGHT= Screen.getPrimary().getBounds().getHeight()*0.6;
    public static final double GAMESCREENWIDTH= MAINSCREENWIDTH*0.8;
    public static final double GAMESCREENHEIGHT= MAINSCREENHEIGHT*0.8;
    public static final Integer MINCOLUMN=5, MINROW=4, MINCX=20, MAXCX=70;
    public final Integer nColumn, nRow;
    public double wastedWidth, wastedHeight;
    public static final List<HexaBlock> HEXALIST= new ArrayList();
    public static final Path TOTALPATH = new Path();
    public Label state= new Label("0"), worries= new Label("00"), id= new Label("00");
    private final Image  refresh= new Image("res/refresh.png");
    private final ImageView  iv_rf= new ImageView();
    private final HBox hbox = new HBox(10);
    private final VBox vbox = new VBox(50);
    private Line l1,l2;
    private Rectangle rec;
    private final WindowsButton cb = new WindowsButton(200,30);
    public static  Circle circle ;
    private static PathTransition animation ;
    private double delX=0, delY=0 ;
    
    public GameScreen(){
        /*1st fix the circumradius*/
        while (((GAMESCREENWIDTH-radius)*0.5/radius) * ((GAMESCREENHEIGHT/radius-2)/Math.sqrt(3)+1) <20  ){
            if(radius<20) System.exit(0);// implementation needed here...........................................................
            radius--;
        }
        nColumn=(int) ((GAMESCREENWIDTH-radius)*0.5/radius);
        nRow= (int) ((GAMESCREENHEIGHT/radius-2)/Math.sqrt(3)+1);
        wastedWidth = GAMESCREENWIDTH -radius - nColumn*2*radius;
        wastedHeight = GAMESCREENHEIGHT - 2*radius - (nRow-1)*radius*Math.sqrt(3);
        circle = new Circle ( 20, Color.rgb(0, 150, 204));
    }
    public void initialize(){
        // adding the hexas
        
        for(int r=1;  r<=nRow;  r++){
            PointPolar firstHexaCenter = r==1? 
                    PointPolar.fromPoint2D(radius, radius):
                    Hexa((r-2)*nColumn+1).getCenter().asOrigin(2*radius,r%2==0?Math.PI/3:Math.PI*2/3);
            for(int c=1;  c<=nColumn;  c++){
                int k=(r-1)*nColumn+c;
                //System.out.print(k);
                HexaBlock hb = new HexaBlock(r,c);
                hb.setCenter(firstHexaCenter.asOrigin((c-1)*2*radius, 0));
                HEXALIST.add(hb);
            }
        }
        /*** now configure neighbours ***/
        GAMESCREEN.getChildren().addAll(HEXALIST);
        GAMESCREEN.getChildren().add(circle);
            
        HEXALIST.stream().forEachOrdered(ahexa -> ahexa.configureNeighbours());
        //the close-open bar
            //the close button
        Group exit= new Group();
        exit.setOnMouseClicked(e->ReArrange.myStage.close());
        exit.setOnMouseEntered(e->{
            l1.setStroke(Color.BLACK);
            l2.setStroke(Color.BLACK);
            rec.setFill(Color.RED);
                });
        exit.setOnMouseExited(e ->{
            l1.setStroke(Color.RED);
            l2.setStroke(Color.RED);
            rec.setFill(Color.rgb(255, 0, 0, 0.3));
                });
        l1= new Line(18,17,28,27);
        l2= new Line(28,17,18,27);
        rec =new Rectangle(9,10,30,26);
        rec.setFill(Color.rgb(255, 0, 0, 0.35));
        l1.setStroke(Color.RED); l2.setStroke(Color.RED);
        l1.setStrokeWidth(3); l2.setStrokeWidth(3);
        exit.getChildren().addAll(rec,l1,l2);
        
        iv_rf.setImage(refresh);
        iv_rf.setOnMouseClicked(e->{
            ReArrange.alg.start();
            //GAMESCREEN.getChildren().remove(TOTALPATH);
            TOTALPATH.getElements().clear();
                });
        
        hbox.getChildren().addAll(iv_rf,exit,worries,state,id);
        hbox.setPadding(new Insets(10));
        hbox.setTranslateX(MAINSCREENWIDTH-hbox.prefWidth(20)-25);
        hbox.setTranslateY(5);
        
        //
        cb.setText("করলা");
        cb.setFill(HexaBlock.HEXFILL.brighterBy(50).toColor());
        cb.setTextFill(HexaBlock.LINESTROKE);
        //cb.setOnMouseClicked( e -> cb.resize(200, 50) );
        vbox.getChildren().addAll(cb);
        GAMESCREEN.setTranslateX(MAINSCREENWIDTH*0.1+ wastedWidth/2);
        GAMESCREEN.setTranslateY(MAINSCREENHEIGHT*0.1+ wastedHeight/2);
        MAINSCREEN.getChildren().addAll(hbox,vbox, GAMESCREEN);
        MAINSCREEN.setOnMousePressed(e->{
            delX = ReArrange.myStage.getX()-e.getScreenX();
            delY = ReArrange.myStage.getY()-e.getScreenY();
            //cb.setText(String.valueOf(ReArrange.myStage.getX())+"  "+String.valueOf(e.getScreenX()));
                });
        MAINSCREEN.setOnMouseDragged(e -> {
            ReArrange.myStage.setX(e.getScreenX()+delX);
            ReArrange.myStage.setY(e.getScreenY()+delY);
        });
        
        //MAINSCREEN.setStyle("-fx-background-color: #001a00");
        MAINSCREEN.setBackground(Background.EMPTY);
        ReArrange.alg.start();
        
        ps = new Scene(MAINSCREEN,MAINSCREENWIDTH, MAINSCREENHEIGHT);
        ps.setFill(Color.rgb(0, 26, 0, 0.5));
    }
    public static void addPath(PointPolar p){
        if (TOTALPATH.getElements().isEmpty()) TOTALPATH.getElements().add(new MoveTo(p.getX(), p.getY()));
        else TOTALPATH.getElements().add(new LineTo(p.getX(), p.getY()));
    }
    public static HexaBlock Hexa (int id){
        return HEXALIST.get(id-1);
    }
    public static void celebrate (){
        animation = new PathTransition(Duration.millis(30000), TOTALPATH, circle);
        animation.setNode(circle);
        animation.setPath(TOTALPATH);
        animation.play();
    }
}
