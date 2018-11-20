package c.tries;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Contacts {

    private static Node root = new Node();

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        int numberOfOperations = scanner.nextInt();

        for (int c = 0; c < numberOfOperations; c++) {
            String op = scanner.next();
            String contact = scanner.next();
            if (op.equals("add")) {
                root.add(contact);
            } else {
                if (op.equals("find")) {
                    root.find(contact);
                }
            }
        }
    }
}

class Node {

    //26, for a-z chars
    Map<Character, Node> map = new HashMap<>(26, .75f);
    int size = 0;

    private Node getNode(char c) {
        return map.get(c);
    }

    private void setNode(char c, Node node) {
        map.put(c, node);
    }

    public void add(String s) {
        //Add with index = 0; at its starting position, first char
        add(s, 0);
    }

    private void add(String s, int index) {
        size++;
        if (index == s.length()) return;
        char current = s.charAt(index);
        Node child = getNode(current);
        if (child == null) {
            child = new Node();
            setNode(current, child);
        }
        child.add(s, 1 + index);
    }

    public int find(String s) {
        return findCount(s, 0);
    }

    private int findCount(String s, int index) {
        if (index == s.length()) return size;
        Node child = getNode(s.charAt(index));
        if (child == null) return 0;
        return child.findCount(s, 1 + index);
    }
}