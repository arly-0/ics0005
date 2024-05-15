import java.util.*;

public class Tnode {
   private String name;
   private Tnode firstChild;
   private Tnode nextSibling;

   public Tnode(String name) {
      this.name = name;
   }

   @Override
   public String toString() {
      StringBuilder sb = new StringBuilder();
      buildLeftParentheticString(this, sb);
      return sb.toString();
   }

   private static void buildLeftParentheticString(Tnode node, StringBuilder sb) {
      if (node == null) {
         throw new RuntimeException("Node cannot be null");
      }
      sb.append(node.name);

      if (node.firstChild != null) {
         sb.append("(");
         buildLeftParentheticString(node.firstChild, sb);
         sb.append(")");
      }

      if (node.nextSibling != null) {
         sb.append(",");
         buildLeftParentheticString(node.nextSibling, sb);
      }
   }

   public static Tnode buildFromRPN(String pol) throws RuntimeException {
      Stack<Tnode> stack = new Stack<>();

      String[] tokens = pol.split("\\s+");

      for (String token : tokens) {
         if (isOperand(token)) {
            Tnode operandNode = new Tnode(token);
            stack.push(operandNode);
         } else if (isOperator(token)) {
            if (stack.size() < 2)
               throw new RuntimeException("Not enough operands for operation: " + pol);

            Tnode rightOperand = stack.pop();
            Tnode leftOperand = stack.pop();

            Tnode operatorNode = new Tnode(token);
            operatorNode.firstChild = leftOperand;
            leftOperand.nextSibling = rightOperand;

            stack.push(operatorNode);
         } else if (token.equals("DUP")) {
            if (stack.isEmpty())
               throw new RuntimeException("Nothing to duplicate: " + pol);
            stack.push(deepCopy(stack.peek()));
         } else if (token.equals("SWAP")) {
            if (stack.size() < 2)
               throw new RuntimeException("Not enough elements to swap: " + pol);
            Tnode first = stack.pop();
            Tnode second = stack.pop();
            stack.push(first);
            stack.push(second);
         } else if (token.equals("ROT")) {
            if (stack.size() < 3)
               throw new RuntimeException("Not enough elements to rotate: " + pol);
            Tnode first = stack.pop();
            Tnode second = stack.pop();
            Tnode third = stack.pop();
            stack.push(second);
            stack.push(first);
            stack.push(third);
         } else {
            throw new RuntimeException("Invalid token: " + token);
         }
      }
      if (stack.isEmpty() || stack.size() > 1) {
         throw new RuntimeException("Invalid RPN expression: " + pol);
      }
      return stack.pop();
   }

   private static boolean isOperator(String token) {
      return token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/");
   }

   private static boolean isOperand(String token) {
      return token.matches("-?\\d+");
   }

   private static Tnode deepCopy(Tnode original) {
      if (original == null) {
         return null;
      }
      Tnode newNode = new Tnode(original.name);
      newNode.firstChild = deepCopy(original.firstChild);
      newNode.nextSibling = deepCopy(original.nextSibling);
      return newNode;
   }

   public static void main(String[] args) {
      String rpn = "2 5 SWAP -";
      System.out.println("RPN: " + rpn);
      Tnode res = buildFromRPN(rpn);
      System.out.println("Tree: " + res);
   }
}
