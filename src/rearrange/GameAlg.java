package rearrange;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.util.stream.Collectors;


public class GameAlg {
    public List<HexaBlock> crossedHexas= new ArrayList();
    public HexaBlock startingHexa, endingHexa,f ,l;
    public Integer worries;
    private int hexaToTouch;
    /*in this class, when the start method is invoked, hexagons remain same; obnly lines get changed*/
    
    
    public void start(){
        
        /* refreshing everything */
        Random r= new Random();
        GameScreen.HEXALIST.stream().forEach(ahexa -> ahexa.refresh());
        crossedHexas= new ArrayList();
        startingHexa = GameScreen.HEXALIST.get(r.nextInt(GameScreen.HEXALIST.size()));
        hexaToTouch=(int) (ReArrange.screen.nRow*ReArrange.screen.nColumn*1.2);
        
        /* setting up main criss crosses, not visualizing them */
        
        f = startingHexa;  l = new HexaBlock();
        GameScreen.addPath(f.getCenter());
        crossedHexas.add(f);
        for(int i=1; i<=hexaToTouch; i++){
            l= f.next();
            GameScreen.addPath(l.getCenter());
            if(!f.isConnectedTo(l)) f.connectWith(l);
            if(crossedHexas.indexOf(l)==-1) crossedHexas.add(l);
            f=l;
        }
        
        crossedHexas.stream().forEachOrdered(h ->{
            h.configureSymmetry();
            h.rotate(r.nextInt(5)+1);
            h.setSpin((int) h.getRotate()) ;
        });
        worries= crossedHexas.stream().filter(p -> p.getState()!=0).collect(Collectors.toList()).size();
    }
}
