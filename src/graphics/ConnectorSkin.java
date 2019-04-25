
package graphics;

import javafx.geometry.Point2D;
import javafx.scene.control.SkinBase;

/*** @author thisismuzib ***/
public class ConnectorSkin extends SkinBase<Connector>{

    public ConnectorSkin(Connector control) {
        super(control);
        //Point2D object = this.getSkinnable();
    }

    @Override
    protected double computePrefHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset) {
        return super.computePrefHeight(width, topInset, rightInset, bottomInset, leftInset); 
    }

    @Override
    protected double computePrefWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset) {
        return super.computePrefWidth(height, topInset, rightInset, bottomInset, leftInset); 
    }

    @Override
    protected void layoutChildren(double contentX, double contentY, double contentWidth, double contentHeight) {
        this.update();
        super.layoutChildren(contentX, contentY, contentWidth, contentHeight); 
    }
    
    private void update(){
        
    }
}
