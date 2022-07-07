package algorithms.floatfoo;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import static algorithms.floatfoo.Manachers.lps;

public class ManachersTest {
    @Test
    public void primitiveTest1() {
        assertEquals("abababa", lps("abababa"));
    }


    @Test
    public void primitiveTest2() {
        assertEquals("abababa", lps("abababab"));
    }

    @Test
    public void primitiveTest3() {
        assertEquals("bababab", lps("babababa"));
    }

    @Test
    public void notPalindormicStrings() {
        assertEquals(lps("notastring"), "n");
    }
}
