package my.training;

import java.util.Comparator;
import java.util.List;

public class Sorting {
    public static void main(String[] args) {
        var strings = new java.util.ArrayList<>(List.of("one", "two", "three"));
        strings.forEach(System.out::println);
        strings.sort(Comparator.naturalOrder());
        strings.forEach(System.out::println);
    }
}
