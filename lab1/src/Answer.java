import java.time.Instant;
import java.util.*;

/**
 * Lab 1.
 * 
 * @since 1.8
 */
public class Answer {

    public static void main(String[] param) {

        // conversion double -> String
        double num = 3.14;
        System.out.println(String.valueOf(num));

        // conversion String -> int
        String str = "1234";
        try {
            System.out.println(Integer.parseInt(str));
        } catch (NumberFormatException e) {
            System.err.println(e);
        }   

        /*
         * "hh:mm:ss"
         * I'd rather use java.time, but you specified java.util.Calendar, so here it is
         */
        Calendar now = Calendar.getInstance();
        int hour = now.get(Calendar.HOUR_OF_DAY);
        int minute = now.get(Calendar.MINUTE);
        int second = now.get(Calendar.SECOND);
        String timeString = String.format("%02d:%02d:%02d", hour, minute, second);
        System.out.println(timeString);

        // cos 45 deg
        double angle = 45;
        double radian = Math.toRadians(angle);
        System.out.println(Math.cos(radian));

        // table of square roots
        for (int i = 0; i <= 100; i += 5) {
            System.out.printf("%d %.2f\n", i, Math.sqrt(i));
        }

        // case reverse
        String firstString = "ABcd12";
        String result = reverseCase(firstString);
        System.out.println("\"" + firstString + "\" -> \"" + result + "\"");

        // reverse string
        System.out.println(new StringBuilder(firstString).reverse());

        // count words
        String s = "How  many      words   here";
        int nw = countWords(s);
        System.out.println(s + "\t" + nw);

        // pause. COMMENT IT OUT BEFORE JUNIT-TESTING!
        long start = Instant.now().toEpochMilli();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = Instant.now().toEpochMilli();
        System.out.println(end - start);

        /*
         * int vs Integer
         * int - 32 bit primitive data type to store int numbers
         * Integer - class, stores int as obj and uses more memory. Used for operations
         * on int (e.g. parseInt() above).
         */

        final int LSIZE = 100;
        ArrayList<Integer> randList = new ArrayList<Integer>(LSIZE);
        Random generaator = new Random();
        for (int i = 0; i < LSIZE; i++) {
            randList.add(generaator.nextInt(1000));
        }
        System.out.println(randList.toString());

        // minimal element
        System.out.println(Collections.min(randList));

        // HashMap tasks:

        // create
        HashMap<String, String> map = new HashMap<>();
        map.put("I231", "DSA");
        map.put("I232", "OOP");
        map.put("I233", "C++");
        map.put("I234", "JS");
        map.put("I235", "Java");

        // print all keys
        map.keySet().stream().forEach(System.out::println);

        // remove a key
        map.remove("I231");

        // print all pairs
        map.forEach((key, value) -> System.out.println(key + " - " + value));

        // reverse list
        System.out.println("Before reverse:  " + randList);
        reverseList(randList);
        System.out.println("After reverse: " + randList);

        // custom maximum element in collection finder
        System.out.println("Maximum: " + maximum(randList));
    }

    /**
     * Finding the maximal element.
     * 
     * @param a Collection of Comparable elements
     * @return maximal element.
     * @throws NoSuchElementException if <code> a </code> is empty.
     */
    static public <T extends Object & Comparable<? super T>> T maximum(Collection<? extends T> a)
            throws NoSuchElementException {
        if (a == null || a.isEmpty()) {
            throw new NoSuchElementException("Collection is null or empty");
        }

        T max = null;
        for (T element : a) {
            if (max == null || element.compareTo(max) > 0) {
                max = element;
            }
        }

        return max;
    }

    /**
     * Counting the number of words. Any number of any kind of
     * whitespace symbols between words is allowed.
     * 
     * @param text text
     * @return number of words in the text
     */
    public static int countWords(String text) {
        if (text == null || text.isEmpty()) {
            return 0;
        }
        return new StringTokenizer(text).countTokens();
    }

    /**
     * Case-reverse. Upper -> lower AND lower -> upper.
     * 
     * @param s string
     * @return processed string
     */
    public static String reverseCase(String s) {
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);

            if (Character.isUpperCase(ch)) {
                ch = Character.toLowerCase(ch);
            } else if (Character.isLowerCase(ch)) {
                ch = Character.toUpperCase(ch);
            }

            sb.append(ch);
        }

        return sb.toString();
    }

    /**
     * List reverse. Do not create a new list.
     * 
     * @param list list to reverse
     */
    public static <T extends Object> void reverseList(List<T> list)
            throws UnsupportedOperationException {
        if (list == null || list.isEmpty()) {
            throw new RuntimeException("List cannot be null");
        }

        Stack<T> stack = new Stack<>();
        for (T element : list) {
            stack.push(element);
        }

        ListIterator<T> iterator = list.listIterator();
        while (iterator.hasNext()) {
            iterator.next();
            iterator.set(stack.pop());
        }
    }
}
