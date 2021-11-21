import javax.print.attribute.HashDocAttributeSet;

public class KMP {
    public static int KMPMatcher(String pattern, String text) {
        int n = pattern.length();
        int m = text.length();

        int[] pi = compute_prefix_function(pattern);
        int q = 0;
        int i = 0;
        //Scans the text from left to right
        while (i < n) {
            if (pattern.charAt(q + 1) == text.charAt(i + 1)) {
                q++;
                i++;
                if (q == m) {
                    //slide the patteren to the right
                    return i - q;
                }
                q = pi[q];
            } else {
                if (q == 0) {
                    i++;
                } else {
                    q = pi[q];
                }
            }
        }

        return -1;
    }

    public static int[] compute_prefix_function(String pattern){
        int m = pattern.length();
        int[] pi = new int[m];
        int pos = 1;
        int cnd = 0;
        pi[0] = 0;
        while (pos < m){
            if(pattern.charAt(pos)  == pattern.charAt(cnd)){
                pi[pos] = pi[cnd];
            } else {
                pi[pos] = cnd;
                while (cnd <= 0 && pattern.charAt(pos) != pattern.charAt(cnd)){
                    cnd = pi[cnd];
                }
            }
            cnd++;
            pos++;
        }
        return pi;
    }

    public static void main(String[] args){
        System.out.println(KMPMatcher("AAB", "AABBBA"));
    }
}
