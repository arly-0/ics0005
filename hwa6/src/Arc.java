/**
 * Arc represents one arrow in the graph. Two-directional edges are
 * represented by two Arc objects (for both directions).
 */
public class Arc {

    private String id;
    private Vertex target;
    private Arc next;
    private int info = 0;

    Arc(String s, Vertex v, Arc a) {
        id = s;
        target = v;
        next = a;
    }

    Arc(String s) {
        this(s, null, null);
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Vertex getTarget() {
        return target;
    }

    public void setTarget(Vertex target) {
        this.target = target;
    }

    public Arc getNext() {
        return next;
    }

    public void setNext(Arc next) {
        this.next = next;
    }

    public int getInfo() {
        return info;
    }

    public void setInfo(int info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return id;
    }
}