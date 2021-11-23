import java.util.*;
import java.util.stream.IntStream;

public class KMP {
    public static List<Integer> KMPMatcher(int[] pattern, int[] text, boolean last) {
        int n = text.length, m = pattern.length;
        int[] pi = compute_prefix_function(pattern);
        int q = 0, i = 0; //index for pattern, index for text
        List<Integer> result = new ArrayList<Integer>();
        //Scans the text from left to right
        while (i < n) {
            if (pattern[q] == text[i]) {
                q++;
                i++;
                if (q == m) {
                    //slide the pattern to the right
                    result.add(q - 1);
                    if(!last) {
                        return result;
                    }
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

    public static int[] compute_prefix_function(int[] pattern){
        int m = pattern.length;
        int[] pi = new int[m];
        int pos = 1, cnd = 0;
        pi[0] = 0;
        while (pos < m){
            if(pattern[pos]  == pattern[cnd]){
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

    public static int[] split(String line){
        String[] lineSplit = line.split(" ");
        int[] numArr = new int[lineSplit.length];
        for(int i = 0; i < lineSplit.length; i++){
            numArr[i] = Integer.parseInt(lineSplit[i]);
        }
        return numArr;
    }


    public static int[] arraySlicing(int[] arr, int start, int end){
        return IntStream.range(start, end).map(i -> arr[i]).toArray();
    }

    public static void main(String[] args){
        Integer start = null;
        Integer end = null;
        Scanner scanner = new Scanner(System.in);
        int[] NG = split(scanner.nextLine());
        int N = NG[0], G = NG[1];
        int[] slope = split(scanner.nextLine());
        List<int[]> groundPhases = new ArrayList<int[]>();
        for(int i = 0; i < G; i++){
            int[] ground = split(scanner.nextLine());
            if (i != G - 1){
                scanner.nextLine();
            }
            groundPhases.add(ground);
        }

        //Running KMP on every possibility
        for(int i = 0; i < groundPhases.size(); i++){
            if(i == 0){
                //Save the result as start
                start = KMPMatcher(groundPhases.get(i), slope, false).get(0);
                if(start == -1) {
                    System.out.println("Impossible");
                    System.exit(0);
                }
                slope = arraySlicing(slope, start + 1, slope.length);
                System.out.println(Arrays.toString(slope));
                System.out.println(start);
            } else if(i == groundPhases.size() - 1){
                //Run it normal but save the result and break
                List<Integer> result = KMPMatcher(groundPhases.get(i), slope, true);
                end = result.get(result.size() - 1);
                if(end == -1) {
                    System.out.println("Impossible");
                    System.exit(0);
                }
            } else {
                Integer result = KMPMatcher(groundPhases.get(i), slope, false).get(0);
                if(result == -1) {
                    System.out.println("Impossible");
                    System.exit(0);
                }
                slope = arraySlicing(slope, result + 1, slope.length);
            }

        }
        if(start != null && end != null){
            System.out.println(start + " " + (end + 1));
        } else {
            System.out.println("Impossible");
        }





    }
}
