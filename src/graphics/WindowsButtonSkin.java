
package graphics;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

/** @author thisismuzib@gmail.com **/
public class WindowsButtonSkin extends SkinBase <WindowsButton>{
    
    protected StackPane container = new StackPane();
    private final WindowsButton object;
    public Path innerBoundary;
    
    
    public WindowsButtonSkin(WindowsButton control) {
        super(control);
        object = getSkinnable();
        innerBoundary = object.innerBoundary;
        innerBoundary.setFill(object.getFill());
        getChildren().add(container);
    }

    @Override
    protected void layoutChildren(double contentX, double contentY, double contentWidth, double contentHeight) {
        update();
        this.layoutInArea(container, contentX, contentY, contentWidth, contentHeight, -1 , HPos.CENTER, VPos.CENTER);
    }

    @Override
    protected double computePrefHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset) {
        return  topInset + bottomInset + object.getHeight() ; 
    }

    @Override
    protected double computePrefWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset) {
        return rightInset + object.getWidth() + leftInset; 
    }

    /**/@Override
    protected double computeMaxHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset) {
        return 200.0 ; 
    }

    @Override
    protected double computeMinHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset) {
        return 20;
    }

    @Override
    protected double computeMinWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset) {
        return 50;
    }

    @Override
    protected double computeMaxWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset) {
        return 500.0; 
    }
    private void update(){//System.out.print("\n update was called -_-");
        double radii =  7;
        double width = object.getWidth()==0? 50 : object.getWidth()-2*radii-2*innerBoundary.getStrokeWidth();
        double height = object.getHeight()==0? 20 : object.getHeight()-2*radii-2*innerBoundary.getStrokeWidth(); 
        if (container != null){
            innerBoundary.getElements().clear();
            container.getChildren().clear();
            this.getChildren().remove(container);
        }
        // the 0,0 is the point where left wall and upper wall meet together
        innerBoundary.getElements().add(new MoveTo(radii,0));        
        innerBoundary.getElements().add(new LineTo(radii+width,0));
        innerBoundary.getElements().add(new ArcTo(radii, radii, 0, radii*2+width, radii, false, true));
        innerBoundary.getElements().add(new LineTo(radii*2+width, radii+height));
        innerBoundary.getElements().add(new ArcTo(radii, radii, 0, radii+width, radii*2+height , false, true));
        innerBoundary.getElements().add(new LineTo(radii, radii*2+height));
        innerBoundary.getElements().add(new ArcTo(radii, radii, 0, 0, radii+height , false, true));
        innerBoundary.getElements().add(new LineTo(0, radii));
        innerBoundary.getElements().add(new ArcTo(radii, radii, 0, radii, 0 , false, true));
        innerBoundary.getElements().add(new ClosePath());
        
        container.getChildren().addAll(innerBoundary, object.text);
        this.getChildren().add(container);
    }
}
