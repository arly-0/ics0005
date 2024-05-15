import java.util.concurrent.TimeUnit;

public class GraphTask {

   /** Main method. */
   public static void main(String[] args) {
      GraphTask a = new GraphTask();
      a.run();
   }

   /** Actual main method to run examples and everything. */
   public void run() {
      Graph g = new Graph("G");
      g.createRandomSimpleGraph(2000, 2500);

      long startTime = System.nanoTime();
      g.getFirst().eccentricity();
      long endTime = System.nanoTime();
      long totalTime = TimeUnit.NANOSECONDS.toMillis(endTime - startTime);

      System.out.println(
            "Eccentricity for graph with 2000 vertices and 2500 edges execution time in milliseconds: " + totalTime);
   }
}
