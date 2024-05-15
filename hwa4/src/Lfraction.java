import java.util.*;

/**
 * This class represents fractions of form n/d where n and d are long integer
 * numbers. Basic operations and arithmetics for fractions are provided.
 */
public class Lfraction implements Comparable<Lfraction> {

   /** Main method. Different tests. */
   public static void main(String[] param) {
      Lfraction f1 = new Lfraction(-1, 250);
      Lfraction f2 = new Lfraction(-1, 375);
      f1.plus(f2);
   }

   private long numerator;
   private long denominator;

   /**
    * Constructor.
    * 
    * @param a numerator
    * @param b denominator > 0
    */
   public Lfraction(long a, long b) {
      if (b == 0) {
         throw new IllegalArgumentException("Denominator cannot be zero");
      }

      // Use the gcd to find the greatest common divisor of a and b
      long g = gcd(a, b);

      // Divide both a and b by g to reduce the fraction
      a = a / g;
      b = b / g;

      // If b is negative, multiply both a and b by -1 to make the denominator
      // positive
      if (b < 0) {
         a = -a;
         b = -b;
      }

      // Assign the reduced and normalized values of a and b to the fields
      this.numerator = a;
      this.denominator = b;
   }

   /**
    * Public method to access the numerator field.
    * 
    * @return numerator
    */
   public long getNumerator() {
      return this.numerator;
   }

   /**
    * Public method to access the denominator field.
    * 
    * @return denominator
    */
   public long getDenominator() {
      return this.denominator;
   }

   /**
    * Conversion to string.
    * 
    * @return string representation of the fraction
    */
   @Override
   public String toString() {
      if (this.denominator == 1) {
         return Long.toString(this.numerator);
      } else {
         return this.numerator + "/" + this.denominator;
      }
   }

   /**
    * Equality test.
    * 
    * @param m second fraction
    * @return true if fractions this and m are equal
    */
   @Override
   public boolean equals(Object m) {
      if (m == null || !(m instanceof Lfraction)) {
         return false;
      }
      
      Lfraction other = (Lfraction) m;
      long gcdThis = gcd(this.numerator, this.denominator);
      long gcdOther = gcd(other.numerator, other.denominator);

      return (this.numerator / gcdThis == other.numerator / gcdOther) &&
            (this.denominator / gcdThis == other.denominator / gcdOther);
   }

   /**
    * Hashcode has to be the same for equal fractions and in general, different
    * for different fractions.
    * 
    * @return hashcode
    */
   @Override
   public int hashCode() {
      /*
       * Use the formula (int) (numerator ^ (numerator >>> 32)) * 31 + (int)
       * (denominator ^ (denominator >>> 32))
       * to compute the hash code
       */

      return (int) (this.numerator ^ (this.numerator >>> 32)) * 31
            + (int) (this.denominator ^ (this.denominator >>> 32));
   }

   /**
    * Sum of fractions.
    * 
    * @param m second addend
    * @return this+m
    */
   public Lfraction plus(Lfraction m) {
      if (m == null) {
         throw new NullPointerException("Cannot add null fraction");
      }

      /*
       * Use the formula (a/b) + (c/d) = (ad + bc) / bd to compute the numerator and
       * denominator of the sum
       * where a and b are the numerator and denominator of this fraction and c and d
       * are the numerator and denominator of m
       */

      long a = this.numerator;
      long b = this.denominator;
      long c = m.numerator;
      long d = m.denominator;
      long num = a * d + b * c;
      long den = b * d;

      return new Lfraction(num, den);
   }

   /**
    * Multiplication of fractions.
    * 
    * @param m second factor
    * @return this*m
    */
   public Lfraction times(Lfraction m) {
      if (m == null) {
         throw new NullPointerException("Cannot multiply by null fraction");
      }

      /*
       * Use the formula (a/b) * (c/d) = (ac) / (bd) to compute the numerator and
       * denominator of the product
       * where a and b are the numerator and denominator of this fraction and c and d
       * are the numerator and denominator of m
       */

      long a = this.numerator;
      long b = this.denominator;
      long c = m.numerator;
      long d = m.denominator;
      long num = a * c;
      long den = b * d;

      return new Lfraction(num, den);
   }

   /**
    * Inverse of the fraction. n/d becomes d/n.
    * 
    * @return inverse of this fraction: 1/this
    */
   public Lfraction inverse() {
      if (this.numerator == 0) {
         throw new ArithmeticException("Cannot invert zero fraction");
      }

      /*
       * Use the formula (a/b)^(-1) = (b/a) to compute the numerator and denominator
       * of the inverse
       * where a and b are the numerator and denominator of this fraction
       */

      long a = this.numerator;
      long b = this.denominator;
      long num = b;
      long den = a;

      return new Lfraction(num, den);
   }

   /**
    * Opposite of the fraction. n/d becomes -n/d.
    * 
    * @return opposite of this fraction: -this
    */
   public Lfraction opposite() {
      /*
       * Use the formula -(a/b) = (-a)/b to compute the numerator and denominator of
       * the opposite
       * where a and b are the numerator and denominator of this fraction
       */

      long a = this.numerator;
      long b = this.denominator;
      long num = -a;
      long den = b;

      return new Lfraction(num, den);
   }

   /**
    * Difference of fractions.
    * 
    * @param m subtrahend
    * @return this-m
    */
   public Lfraction minus(Lfraction m) {
      if (m == null) {
         throw new NullPointerException("Cannot subtract null fraction");
      }

      /*
       * Use the formula (a/b) - (c/d) = (ad - bc) / bd to compute the numerator and
       * denominator of the difference
       * where a and b are the numerator and denominator of this fraction and c and d
       * are the numerator and denominator of m
       */

      long a = this.numerator;
      long b = this.denominator;
      long c = m.numerator;
      long d = m.denominator;
      long num = a * d - b * c;
      long den = b * d;

      return new Lfraction(num, den);
   }

   /**
    * Quotient of fractions.
    * 
    * @param m divisor
    * @return this/m
    */
   public Lfraction divideBy(Lfraction m) {
      if (m == null) {
         throw new NullPointerException("Cannot divide by null fraction");
      }
      if (m.numerator == 0) {
         throw new ArithmeticException("Cannot divide by zero fraction");
      }

      /*
       * Use the formula (a/b) / (c/d) = (ad) / (bc) to compute the numerator and
       * denominator of the quotient
       * where a and b are the numerator and denominator of this fraction and c and d
       * are the numerator and denominator of m
       */

      long a = this.numerator;
      long b = this.denominator;
      long c = m.numerator;
      long d = m.denominator;
      long num = a * d;
      long den = b * c;

      return new Lfraction(num, den);
   }

   /**
    * Comparision of fractions.
    * 
    * @param m second fraction
    * @return -1 if this < m; 0 if this==m; 1 if this > m
    */
   @Override
   public int compareTo(Lfraction m) {
      if (m == null) {
         throw new NullPointerException("Cannot compare to null fraction");
      }

      /*
       * Use the formula (a/b) - (c/d) = (ad - bc) / bd to compute the difference of
       * the fractions
       * where a and b are the numerator and denominator of this fraction and c and d
       * are the numerator and denominator of m
       */

      long a = this.numerator;
      long b = this.denominator;
      long c = m.numerator;
      long d = m.denominator;
      long diff = a * d - b * c;

      if (diff > 0) {
         return 1;
      } else if (diff == 0) {
         return 0;
      } else {
         return -1;
      }
   }

   /**
    * Clone of the fraction.
    * 
    * @return new fraction equal to this
    */
   @Override
   public Object clone() throws CloneNotSupportedException {
      return new Lfraction(this.numerator, this.denominator);
   }

   /**
    * Integer part of the (improper) fraction.
    * 
    * @return integer part of this fraction
    */
   public long integerPart() {
      long a = this.numerator;
      long b = this.denominator;

      return Math.floorDiv(a, b);
   }

   /**
    * Extract fraction part of the (improper) fraction
    * (a proper fraction without the integer part).
    * 
    * @return fraction part of this fraction
    */
   public Lfraction fractionPart() {
      long a = this.numerator;
      long b = this.denominator;
      long num = Math.floorMod(a, b);

      return new Lfraction(num, b);
   }

   /**
    * Approximate value of the fraction.
    * 
    * @return real value of this fraction
    */
   public double toDouble() {
      long a = this.numerator;
      long b = this.denominator;

      return (double) a / b;
   }

   /**
    * Double value f presented as a fraction with denominator d > 0.
    * 
    * @param f real number
    * @param d positive denominator for the result
    * @return f as an approximate fraction of form n/d
    */
   public static Lfraction toLfraction(double f, long d) {
      if (d == 0) {
         throw new IllegalArgumentException("Denominator cannot be zero");
      }

      /*
       * Use the formula round(f * d) / d to compute the numerator and denominator of
       * the approximate fraction
       * where f is the double value and d is the given denominator
       */

      long num = Math.round(f * d);
      long den = d;

      return new Lfraction(num, den);
   }

   /**
    * Conversion from string to the fraction. Accepts strings of form
    * that is defined by the toString method.
    * 
    * @param s string form (as produced by toString) of the fraction
    * @return fraction represented by s
    */

   /*
    * testValueOfErr2(LfractionTest)
    * java.lang.AssertionError: Expected exception: java.lang.RuntimeException
    * forced me to use RuntimeException instead of proper NullPointerException and
    * NumberFormatException
    */
   public static Lfraction valueOf(String s) {
      if (s == null) {
         throw new RuntimeException("Cannot convert null string to fraction");
      }

      String[] parts = s.split("/", -1);

      if (parts.length != 1 && parts.length != 2) {
         throw new RuntimeException("Invalid fraction string: " + s);
      }

      try {
         long a = Long.parseLong(parts[0]);
         long b = parts.length == 2 ? Long.parseLong(parts[1]) : 1;

         return new Lfraction(a, b);
      } catch (NumberFormatException e) {
         throw new RuntimeException("Invalid fraction string: " + s);
      }
   }

   // Find the greatest common divisor of two long integers
   private static long gcd(long a, long b) {
      a = Math.abs(a);
      b = Math.abs(b);

      // Euclidean algorithm
      while (b != 0) {
         long r = a % b;
         a = b;
         b = r;
      }

      return a;
   }
}