
package graphics;

import java.util.ArrayList;
import java.util.List;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.VBox;

/** @author thisismuzib@gmail.com **/
public class DropdownMenuSkin extends SkinBase <DropdownMenu>{
    
    private final DropdownMenu object;
    private final VBox container = new VBox(1);
    private final List<Integer> showedContents = new ArrayList();
    
    public DropdownMenuSkin(DropdownMenu control) {
        super(control);
        object = getSkinnable();
        container.heightProperty().addListener((observable, oldV, newV) -> {
            //if (newV.intValue() > showedContents.size())
        });
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

    @Override
    protected double computeMaxHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset) {
        return  topInset + bottomInset + object.getHeight() ; 
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
        return rightInset + object.getWidth() + leftInset; 
    }
    protected void update(){
        
        getChildren().add(container);
    }
}
