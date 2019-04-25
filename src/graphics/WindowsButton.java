
package graphics;

import javafx.event.EventHandler;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Skin; 
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.scene.text.Font;
import rearrange.GameScreen;

/**@author thisismuzib@gmail.com **/

public final class WindowsButton extends Control{
    
    
    protected Integer boundaryCircleRadius=7;
    private ColorX fill= ColorX.rgb(200, 200, 200);
    protected Label text = new Label("Button");
    protected Path innerBoundary = new Path();
    private EventHandler<MouseEvent> handleMouseEnterEvent, handleMouseExitEvent, handleMousePressedEvent, handleMouseReleasedEvent;
    private final DropShadow glow = new DropShadow();
    
    
    // constructors
    public WindowsButton() {
        this(50,20);
    }
    public WindowsButton(String name){
        this(50,20);
        this.setText(name);
    }
    public WindowsButton(Integer width, Integer height){
        text.setEffect(glow);
        this.fill = GameScreen.psFill.brighterBy(200);
        innerBoundary.setEffect(new DropShadow());
        text.setFont(Font.font("eurofurence bold",20));
        this.resize(width, height);
        refreshEventHandlers();
    }
    
    // property getters and setters
    public Label getText() {
        return text;
    }
    public void setText(String button_text) {
        text.setText( button_text );
    }
    public Color getFill() {
        return fill.toColor();
    }
    public void setFill(Color fill) {
        this.fill = new ColorX(fill);
        innerBoundary.setFill(fill);
        clearEventHandlers();
        refreshEventHandlers();
    }
    
    public void setTextFont(Font font){
        text.setFont(font);
    }

    public ColorX getTextFill() {
        return new ColorX((Color)this.text.getTextFill());
    }

    public void setTextFill(ColorX textFill) {
        this.text.setTextFill(textFill.toColor());
    }
    
    // others
    @Override
    protected Skin<?> createDefaultSkin() {
        return new WindowsButtonSkin(this);
    }
    
    private void clearEventHandlers(){
        this.removeEventHandler(MouseEvent.MOUSE_ENTERED, handleMouseEnterEvent);
        this.removeEventHandler(MouseEvent.MOUSE_EXITED, handleMouseExitEvent);
        this.removeEventHandler(MouseEvent.MOUSE_PRESSED, handleMousePressedEvent);
        this.removeEventHandler(MouseEvent.MOUSE_RELEASED, handleMouseReleasedEvent);
    }
    private void refreshEventHandlers(){
        
        handleMouseEnterEvent = event -> glow.setColor(new ColorX((Color)text.getTextFill()).brighterBy(0).toColor());
        handleMouseExitEvent = event -> glow.setColor(Color.BLACK);
        handleMousePressedEvent = event -> innerBoundary.setEffect(new DropShadow());
        handleMouseReleasedEvent = event-> innerBoundary.setEffect(new DropShadow());
        
        this.addEventHandler(MouseEvent.MOUSE_ENTERED, handleMouseEnterEvent);
        this.addEventHandler(MouseEvent.MOUSE_EXITED, handleMouseExitEvent);
        this.addEventHandler(MouseEvent.MOUSE_PRESSED, handleMousePressedEvent);
        this.addEventHandler(MouseEvent.MOUSE_RELEASED, handleMouseReleasedEvent);
    }
}
