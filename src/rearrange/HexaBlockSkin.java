
package rearrange;

import graphics.ColorX;
import graphics.PointPolar;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

/*** @author thisismuzib@gmail.com ***/
public class HexaBlockSkin extends SkinBase<HexaBlock>{
    private Polygon hexagon= new Polygon();
    private final List<Line> lines = new ArrayList();/*separate list for lines so that I can remove them without removing the hexagon*/
    public static final ColorX HEXFILL = GameScreen.psFill.brighterBy(100) ,  LINESTROKE = ColorX.rgb(179, 137, 0);
    private final AnchorPane container = new AnchorPane();
    private double radius=50;
    private final double LINESTROKEWIDTH = 5;

    public HexaBlockSkin(HexaBlock control) {
        super(control);
        hexagon.setFill(HEXFILL.toColor());
        //radius = this.getSkinnable().getRadius();
        //cv.setStyle("-fx-background-color: #000000");
    }

    @Override
    protected double computePrefHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset) {
        return this.getSkinnable().getRadius()*2;
    }

    @Override
    protected double computePrefWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset) {
        return radius*2;
    }

    @Override
    protected void layoutChildren(double contentX, double contentY, double contentWidth, double contentHeight) {
        update();
        super.layoutChildren(contentX, contentY, contentWidth, contentHeight);
    }
    public void lineTo (double angl){
        PointPolar p = PointPolar.fromPoint2D(radius, radius).asOrigin(Math.sqrt(3)*radius/2-2.5 , Math.toRadians(angl));
        Line l = new Line(radius,radius, p.getX(), p.getY());
        l.setStroke(Color.YELLOW);
        l.setStrokeWidth(LINESTROKEWIDTH);
        lines.add(l);
        container.getChildren().add(l);
    }
    public void refresh (){
        lines.clear();
        update();
    }
    public void update(){
        this.getChildren().clear();
        container.getChildren().clear();
        PointPolar rcenter = PointPolar.fromPoint2D(radius, radius);
        hexagon = new Polygon (rcenter.asOrigin(radius, -Math.PI/2).toPoint2D().getX(), rcenter.asOrigin(radius, -Math.PI/2).toPoint2D().getY() ,
                rcenter.asOrigin(radius, -Math.PI/6).toPoint2D().getX(), rcenter.asOrigin(radius, -Math.PI/6).toPoint2D().getY() ,
                rcenter.asOrigin(radius, Math.PI/6).toPoint2D().getX(), rcenter.asOrigin(radius, Math.PI/6).toPoint2D().getY() ,
                rcenter.asOrigin(radius, Math.PI/2).toPoint2D().getX(), rcenter.asOrigin(radius, Math.PI/2).toPoint2D().getY(),
                rcenter.asOrigin(radius, 5*Math.PI/6).toPoint2D().getX(), rcenter.asOrigin(radius, 5*Math.PI/6).toPoint2D().getY() ,
                rcenter.asOrigin(radius, -5*Math.PI/6).toPoint2D().getX(), rcenter.asOrigin(radius, -5*Math.PI/6).toPoint2D().getY());
        hexagon.setFill(HEXFILL.toColor());
        container.getChildren().addAll(hexagon);
        container.getChildren().addAll(lines);
        this.getChildren().addAll(container);
    }
    
}
