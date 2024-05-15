public class Tellis {

    public static void main(String[] param) {
        System.out.println(mahub(2, 3, 4, 5, 6)); // true
        System.out.println(mahub(4, 3, 2, 5, 6)); // true
        System.out.println(mahub(3, 4, 2, 5, 6)); // true
        System.out.println(mahub(6, 5, 4, 3, 2)); // false
        System.out.println(mahub(2, 2, 2, 1, 1)); // false
    }

    public static boolean mahub(double a, double b, double c,
            double x, double y) {
        if (a <= 0 || b <= 0 || c <= 0 || x <= 0 || y <= 0) {
            throw new IllegalArgumentException("Invalid input parameters");
        }

        // Check if face with sides a and b fits
        boolean ab = (a <= x && b <= y) || (a <= y && b <= x);
        // Check if face with sides a and c fits
        boolean ac = (a <= x && c <= y) || (a <= y && c <= x);
        // Check if face with sides b and c fits
        boolean bc = (b <= x && c <= y) || (b <= y && c <= x);

        return ab || ac || bc;
    }

}