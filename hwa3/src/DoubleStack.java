import java.util.*;

/**
 * Stack manipulation.
 * 
 * @since 1.8
 */
public class DoubleStack {
   private LinkedList<Double> stack;

   public static void main(String[] argum) {
      System.out.println(DoubleStack.interpret("2. 15. -"));
   }

   DoubleStack() {
      stack = new LinkedList<>();
   }

   @Override
   public Object clone() throws CloneNotSupportedException {
      DoubleStack copy = new DoubleStack();
      copy.stack = new LinkedList<>(this.stack);
      return copy;
   }

   public boolean stEmpty() {
      return stack.isEmpty();
   }

   public void push(double a) {
      stack.push(a);
   }

   public double pop() {
      if (stEmpty()) {
         throw new IllegalArgumentException("Cannot pop from an empty stack");
      }
      return stack.pop();
   }

   public void op(String s) {
      if (stack.size() < 2) {
         throw new IllegalArgumentException("Not enough elements in the stack for operation " + s);
      }
      double a = pop();
      double b = pop();

      switch (s) {
         case "+":
            push(b + a);
            break;
         case "-":
            push(b - a);
            break;
         case "*":
            push(b * a);
            break;
         case "/":
            if (a == 0) {
               throw new ArithmeticException("Cannot divide by 0");
            }
            push(b / a);
            break;
         default:
            throw new IllegalArgumentException("Invalid operation: " + s);
      }
   }

   public double tos() {
      if (stEmpty()) {
         throw new NoSuchElementException("Cannot read from an empty stack");
      }
      return stack.peek();
   }

   @Override
   public boolean equals(Object o) {
      if (o instanceof DoubleStack) {
         DoubleStack other = (DoubleStack) o;
         return stack.equals(other.stack);
      }
      return false;
   }

   @Override
   public String toString() {
      StringBuilder sb = new StringBuilder();
      Iterator<Double> it = stack.descendingIterator();
      while (it.hasNext()) {
         sb.append(it.next());
      }
      return sb.toString();
   }

   public static double interpret(String pol) {
      if (pol == null || pol.trim().isEmpty()) {
         throw new IllegalArgumentException("No expression provided");
      }

      DoubleStack stack = new DoubleStack();
      String[] tokens = pol.trim().split("\\s+");
      for (String token : tokens) {
         try {
            if (token.isEmpty())
               continue;
            if (token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/")) {
               stack.op(token);
            } else if (token.equals("SWAP")) {
               if (stack.stack.size() < 2) {
                  throw new IllegalArgumentException("Not enough elements to SWAP");
               }
               double a = stack.pop();
               double b = stack.pop();
               stack.push(a);
               stack.push(b);
            } else if (token.equals("ROT")) {
               if (stack.stack.size() < 3) {
                  throw new IllegalArgumentException("Not enough elements to ROT");
               }
               double a = stack.pop();
               double b = stack.pop();
               double c = stack.pop();
               stack.push(b);
               stack.push(a);
               stack.push(c);
            } else if (token.equals("DUP")) {
               if (stack.stack.size() < 1) {
                  throw new IllegalArgumentException("Not enough elements to DUP");
               }
               stack.push(stack.tos());
            } else {
               try {
                  stack.push(Double.parseDouble(token));
               } catch (NumberFormatException e) {
                  throw new IllegalArgumentException("Illegal symbol or incorrect number format: " + token);
               }
            }
         } catch (Exception e) {
            throw new IllegalArgumentException("Error processing token '" + token + "': " + e.getMessage() + " in " + pol);
         }
      }

      if (stack.stack.size() != 1) {
         throw new IllegalArgumentException("Expression leaves too many numbers on stack: " + pol);
      }

      return stack.pop();
   }
}
