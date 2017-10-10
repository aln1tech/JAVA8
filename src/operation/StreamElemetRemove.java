package operation;

import java.util.Arrays;
import java.util.List;

public class StreamElemetRemove {
    public static void main(String[] args) {
        List<String> numberNameList = Arrays.asList("one", "two", "three", "four");
        numberNameList.stream().filter(name -> {
            return name.toLowerCase().equals("two");
        });

    }
}
