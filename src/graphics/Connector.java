
package graphics;

import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import rearrange.GameScreen;
import rearrange.HexaBlock;

/*** @author thisismuzib@gmail.com ***/
public class Connector extends Control{
    
    private final Integer id_1, id_2;
    //public final Direction d1to2, d2to1;

    public Connector(Integer id1, Integer id2) {
        this.id_1 = id1;
        this.id_2 = id2;
        /*this.d1to2 = GameScreen.Hexa(id1).neighbours.directionTowards(id2);
        this.d2to1 = this.d1to2.inverse();*/
    }
    public static Boolean isAvailableBetween(Integer id1, Integer id2){
        /* first get their mutual direction */
        HexaBlock h1= GameScreen.Hexa(id1),  h2= GameScreen.Hexa(id2);
        //Direction a = h1.neighbours.directionTowards(id2), b= a.inverse();
        
        /* then determine whether they have connections in these direction with the prsesent state */
        //return h1.isConnectedTo(a.next(-h1.getState())) && h2.isConnectedTo(b.next(-h2.getState()));
        return true;
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new ConnectorSkin(this); 
    }
    
}
