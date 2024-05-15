/**
 * This header represents a graph.
 */
public class Graph {

    private String id;
    private Vertex first;
    private int info = 0;

    Graph(String s, Vertex v) {
        id = s;
        first = v;
    }

    Graph(String s) {
        this(s, null);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Vertex getFirst() {
        return first;
    }

    public void setFirst(Vertex first) {
        this.first = first;
    }

    public int getInfo() {
        return info;
    }

    public void setInfo(int info) {
        this.info = info;
    }

    @Override
    public String toString() {
        String nl = System.getProperty("line.separator");
        StringBuffer sb = new StringBuffer(nl);
        sb.append(id);
        sb.append(nl);
        Vertex v = first;
        while (v != null) {
            sb.append(v.toString());
            sb.append(" -->");
            Arc a = v.getFirst();
            while (a != null) {
                sb.append(" ");
                sb.append(a.toString());
                sb.append(" (");
                sb.append(v.toString());
                sb.append("->");
                sb.append(a.getTarget().toString());
                sb.append(")");
                a = a.getNext();
            }
            sb.append(nl);
            v = v.getNext();
        }
        return sb.toString();
    }

    public Vertex createVertex(String vid) {
        Vertex res = new Vertex(vid);
        res.setNext(first);
        first = res;
        return res;
    }

    public Arc createArc(String aid, Vertex from, Vertex to) {
        Arc res = new Arc(aid);
        res.setNext(from.getFirst());
        from.setFirst(res);
        res.setTarget(to);
        return res;
    }

    /**
     * Create a connected undirected random tree with n vertices.
     * Each new vertex is connected to some random existing vertex.
     * 
     * @param n number of vertices added to this graph
     */
    public void createRandomTree(int n) {
        if (n <= 0)
            return;
        Vertex[] varray = new Vertex[n];
        for (int i = 0; i < n; i++) {
            varray[i] = createVertex("v" + String.valueOf(n - i));
            if (i > 0) {
                int vnr = (int) (Math.random() * i);
                createArc("a" + varray[vnr].toString() + "_"
                        + varray[i].toString(), varray[vnr], varray[i]);
                createArc("a" + varray[i].toString() + "_"
                        + varray[vnr].toString(), varray[i], varray[vnr]);
            } else {
            }
        }
    }

    /**
     * Create an adjacency matrix of this graph.
     * Side effect: corrupts info fields in the graph
     * 
     * @return adjacency matrix
     */
    public int[][] createAdjMatrix() {
        info = 0;
        Vertex v = first;
        while (v != null) {
            v.setInfo(info++);
            v = v.getNext();
        }
        int[][] res = new int[info][info];
        v = first;
        while (v != null) {
            int i = v.getInfo();
            Arc a = v.getFirst();
            while (a != null) {
                int j = a.getTarget().getInfo();
                res[i][j]++;
                a = a.getNext();
            }
            v = v.getNext();
        }
        return res;
    }

    /**
     * Create a connected simple (undirected, no loops, no multiple
     * arcs) random graph with n vertices and m edges.
     * 
     * @param n number of vertices
     * @param m number of edges
     */
    public void createRandomSimpleGraph(int n, int m) {
        if (n <= 0)
            return;
        if (n > 2500)
            throw new IllegalArgumentException("Too many vertices: " + n);
        if (m < n - 1 || m > n * (n - 1) / 2)
            throw new IllegalArgumentException("Impossible number of edges: " + m);
        first = null;
        createRandomTree(n); // n-1 edges created here
        Vertex[] vert = new Vertex[n];
        Vertex v = first;
        int c = 0;
        while (v != null) {
            vert[c++] = v;
            v = v.getNext();
        }
        int[][] connected = createAdjMatrix();
        int edgeCount = m - n + 1; // remaining edges
        while (edgeCount > 0) {
            int i = (int) (Math.random() * n); // random source
            int j = (int) (Math.random() * n); // random target
            if (i == j)
                continue; // no loops
            if (connected[i][j] != 0 || connected[j][i] != 0)
                continue; // no multiple edges
            Vertex vi = vert[i];
            Vertex vj = vert[j];
            createArc("a" + vi.toString() + "_" + vj.toString(), vi, vj);
            connected[i][j] = 1;
            createArc("a" + vj.toString() + "_" + vi.toString(), vj, vi);
            connected[j][i] = 1;
            edgeCount--; // a new edge happily created
        }
    }
}