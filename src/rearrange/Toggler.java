
package rearrange;

public class Toggler {
    
    Object o1, o2, current;
    private static Object cur;

    public Toggler(Object o1, Object o2) {
        this.o1=o1;
        this.o2=o2;
        current = o1;
    }
    public Object toggle(){
        current =current.equals(o1) ? o2:o1;
        return current ;
    }

    public Object getCurrent() {
        return current;
    }
    
}
