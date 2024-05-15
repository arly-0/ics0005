import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Vertex {

   private String id;
   private Vertex next;
   private Arc first;
   private int info = 0;

   Vertex(String s, Vertex v, Arc e) {
      id = s;
      next = v;
      first = e;
   }

   Vertex(String s) {
      this(s, null, null);
   }

   public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public Vertex getNext() {
      return next;
   }

   public void setNext(Vertex next) {
      this.next = next;
   }

   public Arc getFirst() {
      return first;
   }

   public void setFirst(Arc first) {
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
      return id;
   }

   /**
    * Calculates the eccentricity of a given vertex in the graph.
    * Eccentricity is the maximum distance (shortest path length) from the vertex
    * to any other vertex in the graph.
    * 
    * @return The eccentricity value (maximum distance).
    * @throws NullPointerException if the provided vertex is not found in the
    *                                  graph.
    */
   public int eccentricity() throws NullPointerException {
      if (this == null || first == null) {
         throw new NullPointerException("Vertex not found in the graph.");
      }

      // Use Breadth-First Search (BFS) to explore reachable vertices and their
      // distances
      HashMap<Vertex, Integer> distances = new HashMap<>();

      // Starting vertex distance is 0
      distances.put(this, 0);
      Queue<Vertex> queue = new LinkedList<>();
      queue.add(this);

      while (!queue.isEmpty()) {
         Vertex current = queue.poll();
         for (Arc neighbor = current.first; neighbor != null; neighbor = neighbor.getNext()) {
            Vertex neighborVertex = neighbor.getTarget();
            if (!distances.containsKey(neighborVertex)) {
               distances.put(neighborVertex, distances.get(current) + 1);
               queue.add(neighborVertex);
            }
         }
      }

      // Find the maximum distance from the distances map
      int eccentricity = 0;
      for (Integer distance : distances.values()) {
         eccentricity = Math.max(eccentricity, distance);
      }

      return eccentricity;
   }
}