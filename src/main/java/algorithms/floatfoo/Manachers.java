package algorithms.floatfoo;

public class Manachers {
    public static String lps(String s) {
        // trivial cases
        if (s == null || s.length() == 0) return "";

        // create hashed version of array
        char[] hashed= new char[2*s.length() + 1];
        prework(hashed, s);

        int max = 0;

        int C = 0, R = 0;

        int[] lps = new int[hashed.length];

        for (int i = 1; i < hashed.length; i++) {
            // first step - if mirror update
            //
            // second - extend
            //
            // check for right bound violation

            if ( i < R && i >= C ) {
                lps[i] = Math.min(lps[C - ( i - C )], R - i);
                if (i == 7) {
                }
            }

            while (i - lps[i] - 1 >= 0 && i + lps[i] + 1 < hashed.length && hashed[i - lps[i] - 1] == hashed[i + lps[i] + 1]) {
                lps[i]++;
            }


            if ( i + lps[i] > R  ) {
                max = lps[max] >= lps[i] ? max : i;
                C = i;
                R = i + lps[i];
            }

        }
        StringBuilder res = new StringBuilder();
        for (int i = max - lps[max] ; i <= max + lps[max]; i++) {
            if (hashed[i] != '#') {
                res.append(hashed[i]);
            }
        }

        return res.toString();//s.substring(max/2 - lps[max]/2, max/2 + lps[max]/2) - this should also work, but some cases do not work as intendent
    }

    private static void prework(char[] hash, String s) {
        for (int i = 0; i < hash.length; i++) {
            hash[i] = i % 2 == 0 ? '#' : s.charAt(i / 2) ;
        }
    }

    public static void main(String[] args) {
        String test = lps("cbbd");
        System.out.println(test);
    }
}
