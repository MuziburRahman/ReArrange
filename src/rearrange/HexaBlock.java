package rearrange;

import graphics.ColorX;
import graphics.PointPolar;
import javafx.animation.RotateTransition;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.TreeMap;
import java.util.stream.Collectors;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public final class HexaBlock extends Control{

    private int id, myRow, symmetry=1, spin=0, totalConnections=0;
    private int nColumn,nRow;
    private Integer state=0;
    private final HexaBlockSkin skin = new HexaBlockSkin(this);
    public TreeMap<Integer, Integer> sorroundings = new TreeMap();/* neighbours id, timeCrossed*/
    private final List<Long> connectedDirections = new ArrayList();
    private final double radius=ReArrange.screen.radius;
    private final PointPolar rcenter = new PointPolar();
    private PointPolar center;
    public static final ColorX HEXFILL = GameScreen.psFill.brighterBy(100) ,  LINESTROKE = ColorX.rgb(179, 137, 0);
    public final Circle circle = new Circle ( rcenter.toPoint2D().getX(), rcenter.toPoint2D().getY(), radius/6 , LINESTROKE.dimmedBy(50).toColor());
    public HexaBlock(){}
    
    private int rowOf(Integer Id){
        return (int) Math.ceil(Id.doubleValue()/nColumn);
    }
    public  HexaBlock(int r, int c){
        /* setting up id, coloumn ,row, crossedMap;  */
        nColumn = ReArrange.screen.nColumn;
        nRow = ReArrange.screen.nRow;
        this.id=(r-1)*nColumn+c;
        myRow= rowOf(id);
       
       this.setOnMouseClicked(( MouseEvent e)-> {
           //ReArrange.alg.doit();
           if(ReArrange.alg.worries>0 && totalConnections>0){
               //Connector.isAvailableBetween(5, 6);
               if (state==0) ReArrange.alg.worries++;// if sttate has changed from 0 to another
                rotateWithAnimation((e.getButton()==MouseButton.PRIMARY)?60: -60);
               if(state==0) {
                    ReArrange.alg.worries--;
                    if (ReArrange.alg.worries==0){
                        GameScreen.celebrate();
                    }
                }
               ReArrange.screen.worries.setText(ReArrange.alg.worries.toString());
               ReArrange.screen.state.setText(this.state.toString()) ;
           }
        });
    }
  
    public void dstate(int times){
        state+= times;
        state= state%6;
        //connector filler to be added
    }
    public void refresh(){
        //this.circle.setVisible(false);
        skin.refresh();
        connectedDirections.clear();
        this.symmetry=1;
        state=0;
    }
    
    public void setCenter(PointPolar p){
        this.setTranslateX(p.getX()-radius);
        this.setTranslateY(p.getY()-radius);
        this.center = p;
    }
    
    public void setSpin(Integer spin) {
        this.spin = spin;
    }
    public Integer getid() {
        return id;
    }
    public Integer getState() {
        return this.state;
    }
    public PointPolar getCenter() {
        return center;
    }
    public  double getRadius() {
        return radius;
    }
    public void increaseConnections(){
        totalConnections++;
    }
    public int getTotalConnections(){
        return totalConnections;
    }
    
    public void configureNeighbours (){
        if(id>1)
            if (this.center.asOrigin(2*radius, Math.PI).equals(GameScreen.Hexa(id-1).getCenter()))/*there is a hexa to the left */
                sorroundings.put(id-1, 0);
        if(this.id< nColumn*nRow)
            if (this.center.asOrigin(2*radius, 0).equals(GameScreen.Hexa(id+1).getCenter()) )
                sorroundings.put(id+1, 0);
        if (myRow>1){  /*topright and topleft*/
            sorroundings.put(id-nColumn, 0);
            /*from inspection, i noticed, hexa(id- ncolumn) always exists, but the other one, might not exist if hexa is bordered*/
            if(myRow%2 != 0 && rowOf(id-nColumn-1)==myRow-1){// leftInclined 
                sorroundings.put(id-nColumn-1, 0);
            }
            else if(myRow%2 == 0 && rowOf(id-nColumn+1)==myRow-1){// rightInclined 
                sorroundings.put(id-nColumn+1, 0);
            }
        }
        if (myRow<nRow){  /*bottomright and bottomleft*/
            sorroundings.put(id+nColumn, 0);
            /*from inspection, i noticed, hexa(id+ ncolumn) always exists, but the other one, might not exist if hexa is bordered*/
            if(myRow%2 != 0 && rowOf(id+nColumn-1)==myRow+1){// leftInclined 
                sorroundings.put(id+nColumn-1, 0);
            }
            else if(myRow%2 == 0 && rowOf(id+nColumn+1)==myRow+1){// rightInclined 
                sorroundings.put(id+nColumn+1, 0);
            }
        }
    }
    public void configureSymmetry() { /*this method also include the process of making uncrossed hexas' circle visible*/
        this.circle.setVisible(true);
        
        connectedDirections.sort((e,f)->(int) (e-f));
        if (connectedDirections.size()==2 ){
            if (Math.abs(connectedDirections.get(0)-connectedDirections.get(1)) ==180) symmetry = 2;
        }
        if (connectedDirections.size()==3){
            if (Objects.equals(connectedDirections.get(0)- connectedDirections.get(1), connectedDirections.get(1)- connectedDirections.get(2)))
                symmetry = 3;
        }
        if (connectedDirections.size()==4){
            List<Integer> unConnectedDirections = new ArrayList();
            for (int i=0; i<=300; i+=60){
                if (connectedDirections.indexOf(new Long(i)) == -1) unConnectedDirections.add(i);
            }
            if (Math.abs(unConnectedDirections.get(0)-unConnectedDirections.get(1)) ==180) symmetry = 2;
        }
        if (connectedDirections.size()==6) symmetry = 6;
    }
    public void rotate(int times){
        if (this.symmetry==6) return;
        times= times%6;
        this.setRotate(times*60);
        dstate(times*symmetry);
    }
    public final void rotateWithAnimation(int degree){
        spin +=  degree;
        RotateTransition tt=new RotateTransition(Duration.millis(200), this);
        tt.setByAngle(degree);
        tt.setOnFinished(e-> {
            if (spin != this.getRotate())
                this.setRotate(spin);
        });
        tt.play();
        //tt.setOnFinished(v->);
        dstate(((degree==60)? 1:-1)*symmetry);    
    }
    
    
    // others
    public Long angleWith(Integer id){
        Long toReturn =Math.round(Math.toDegrees(center.angleWith(GameScreen.Hexa(id).getCenter())));
        return toReturn==360L? 0:toReturn;
    }
    public Boolean isConnectedTo(HexaBlock h){
        return connectedDirections.indexOf(this.angleWith(h.getid())) != -1;
    }
    public HexaBlock next (){
        /* the treemap is always sorted ascendingly, means the 1st value has always the lowest crossedpoints */
        final List<Integer> ids = new ArrayList(sorroundings.keySet()), values = new ArrayList(sorroundings.values()),highestids, finalList;
        values.sort((v1,v2)->v1-v2);
        Random r= new Random();
        //System.out.print("\n case ->  id "+id+" sorroundings = "+sorroundings);
        //System.out.print("sorted values = "+values);
        //ids.forEach(k-> System.out.print("\n\t id "+ k +" has been crossed from this hexa "+sorroundings.get(k)+" times"));
        
        highestids = ids.stream().filter(k-> Objects.equals(sorroundings.get(k), values.get(0)))/*gets the least crossed directions*/
                .collect(Collectors.toList());
        //System.out.print("\n\tso least cossed are "+ highestids);
        //highestids.forEach(ID -> System.out.print("\n\t id "+ID+" has been crossed "+GameScreen.Hexa(ID).getTotalConnections()+" times"));
        highestids.sort((k1, k2)-> GameScreen.Hexa(k1).getTotalConnections()-GameScreen.Hexa(k2).getTotalConnections());
        //System.out.print("\n\tso, sorted list = "+ highestids);
        /*so now, highestkeys list holds the least crossed directions (all) and least line possesing hexas (1st ones)*/
        finalList = highestids.stream()
                .filter(ID -> GameScreen.Hexa(ID).getTotalConnections()==GameScreen.Hexa(highestids.get(0)).getTotalConnections())
                .collect(Collectors.toList());
        return GameScreen.Hexa(finalList.get(r.nextInt(finalList.size())));
    }
    public void connectWith(HexaBlock h){
        this.increaseConnections();
        //System.out.print("\n case ->  id "+id+" sorroundings = "+sorroundings+" toGetId="+ h.getid());
        connectedDirections.add(this.angleWith(h.getid()));
        int crossedtimes = sorroundings.get(h.getid());
        sorroundings.replace(h.getid(), crossedtimes+1);
        this.skin.lineTo(this.angleWith(h.getid()));
        if (!h.isConnectedTo(this))
            h.connectWith(this);
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return skin;
    }
    @Override
    public String toString(){
        return "HexaBlock "+id;
    }
}