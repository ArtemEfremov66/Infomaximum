import org.example.Calculations;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculationsTests {
    Calculations calculations = new Calculations();
    @Test
    public void duplicate() {
        List<String[]> list = new ArrayList<>();
        list.add(new String[]{"test", "test1"});
        list.add(new String[]{"test", "test1"});
        HashMap<String, Integer> expected = new HashMap<>();
        expected.put("test test1", 1);
        calculations.duplicate(list);
        HashMap<String, Integer> actual = calculations.getDuplicate();
        assertEquals(expected, actual);
    }
}
