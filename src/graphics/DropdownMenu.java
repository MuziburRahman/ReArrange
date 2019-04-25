
package graphics;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.Control;

/** @author thisismuzib@gmail.com **/
public class DropdownMenu extends Control{
    
    private final ObservableList<String> itemNames = FXCollections.observableArrayList() ;
    private ObservableList<WindowsButton> items = FXCollections.observableArrayList() ;
    public String value, caption;

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }
    
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
    public ObservableList<String> getItems() {
        return itemNames;
    }

    public DropdownMenu() {
        
    }
    
    public DropdownMenu(ObservableList list) {
        items = list;
        itemNames.forEach(name -> items.add(new WindowsButton(name)));
        itemNames.addListener((ListChangeListener.Change<? extends String> c) -> {
            items.add(new WindowsButton(itemNames.get(itemNames.size()-1)));
        });
    }
}
