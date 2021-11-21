import java.util.ArrayList;
import java.util.List;

public class KMP {
    public static List<Integer> KMPMatcher(String pattern, String text) {
        int n = text.length();
        int m = pattern.length();
        int[] pi = compute_prefix_function(pattern);
        int q = 0; //index for pattern
        int i = 0; // index for text
        List<Integer> result = new ArrayList<>();
        int counter = 0;
        //Scans the text from left to right
        while (i < n) {
            if (pattern.charAt(q) == text.charAt(i)) {
                q++;
                i++;
                if (q == m) {
                    //slide the patteren to the right
                    result.add(i - 1);
                    q = pi[q - 1];
                }

            } else {
                if (q == 0) {
                    i++;
                } else {
                    q = pi[q];
                }
            }
        }

        return result;
    }

    public static int[] compute_prefix_function(String pattern){
        int m = pattern.length();
        int[] pi = new int[m];
        int pos = 1;
        int cnd = 0;
        pi[0] = 0;
        while (pos < m){
            if(pattern.charAt(pos)  == pattern.charAt(cnd)){
                cnd++;
                pi[pos] = pi[cnd];
                pos++;
            } else {
                if(cnd != 0){
                    cnd = pi[cnd - 1];
                } else {
                    pi[pos] = cnd;
                    pos++;
                }
            }
        }
        return pi;
    }

    public static void main(String[] args){
        String pattern = "eher";
        String text = "Anders Reher Christense";
        System.out.println(KMPMatcher(pattern, text));
    }
}
