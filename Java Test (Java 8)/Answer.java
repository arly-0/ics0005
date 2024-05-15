import java.util.Arrays;

public class Answer {

    public static void main(String[] args) {
        System.out.println(result(new double[] { 0., 1., 2., 3., 4. }));
        System.out.println(result(new double[] { 9.5, 8.7, 9.2, 9.8, 10.0 })); // 9.5
        System.out.println(result(new double[] { 5.0, 5.0, 5.0 })); // 5.0
    }

    public static double result(double[] marks) {
        if (marks == null || marks.length == 0 || marks.length < 3) {
            throw new IllegalArgumentException("Invalid input array");
        }

        return Arrays.stream(marks)
                .sorted()
                .skip(1)
                .limit(marks.length - 2)
                .average()
                .orElse(0);
    }

}