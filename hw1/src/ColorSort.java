/** Sorting of balls.
 * @since 1.8
 */


// https://en.wikipedia.org/wiki/Dutch_national_flag_problem

public class ColorSort {

   enum Color {red, green, blue};
   
   public static void main (String[] param) {
      // for debugging
   }
   
   public static void reorder (Color[] balls) {
      // Init pointers
      int low = 0; // first unprocessed ball
      int mid = 0; // current ball
      int high = balls.length - 1; // last unprocessed ball
      
      // Loop until mid crosses high
      while (mid <= high) {
         // Get current ball color
         Color color = balls[mid];
         
         // If current ball is red, swap it with the ball at low and increment both low and mid
         if (color == Color.red) {
            swap(balls, low, mid);
            low++;
            mid++;
         }
         // If current ball is blue, swap it with the ball at high and decrement high
         else if (color == Color.blue) {
            swap(balls, mid, high);
            high--;
         }
         // If current ball is green, just increment mid
         else {
            mid++;
         }
      }
   }
   
   // Helper method to swap two elements in an array
   public static void swap(Color[] balls, int i, int j) {
      Color temp = balls[i];
      balls[i] = balls[j];
      balls[j] = temp;
   }   
}

