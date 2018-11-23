package c.balancedBrackets;

import java.util.*;

public class BalancedBraces {

    private static final Scanner scanner = new Scanner(System.in);

    private static Set<Character> openings = new TreeSet<Character>();
    private static Set<Character> closings = new TreeSet<Character>();
    private static Map<Character, Character> closures = new HashMap<>();

    public static void main(String[] args) {
        int t = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        openings.addAll(Arrays.asList('{', '[', '('));
        closings.addAll(Arrays.asList('}', ']', ')'));

        closures.put('{', '}');
        closures.put('[', ']');
        closures.put('(', ')');
        closures.put('}', '{');
        closures.put(']', '[');
        closures.put(')', '(');

        for (int tItr = 0; tItr < t; tItr++) {
            String expression = scanner.nextLine();
            System.out.println(isBalanced(expression));
        }

        scanner.close();
    }

    private static String isBalanced(String string) {
        LinkedList<Character> stack = new LinkedList<>();

        for (char c : string.toCharArray()) {
            if (!closures.keySet().contains(c)) {
                return "NO";
            }
            if (openings.contains(c)) {
                stack.push(c);
                System.out.println("Stack is: " + stack);
                continue;
            }
            if (closings.contains(c)) {
                if (stack.isEmpty()){ return "NO";}
                char topOfStack = stack.pop();
                System.out.println("pop " + topOfStack + " because " + c);
                //verify if the corresponding opening is in the top of stack
                if (closures.get(c) == topOfStack) {
                    //Pop and continue
                    System.out.println("stack is: " + stack);
                } else {
                    return "NO";
                }
            }
        }
        return "YES";
    }
}
