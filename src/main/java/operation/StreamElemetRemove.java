package operation;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class StreamElemetRemove {
    public static void main(String[] args) {
        List<String> numberNameList = Arrays.asList("one", "two", "three", "four");
        numberNameList.stream().filter(name -> {
            return name.toLowerCase().equals("two");
        });

        List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);

        List<Integer> squaresList = numbers.stream().map( i -> i*i).distinct().collect(Collectors.toList());

        System.out.println("squaresList :"+squaresList);

        Random random = new Random();
        random.ints().limit(10).forEach(System.out::println);

    }
}
