import java.util.*;
import java.util.stream.IntStream;

public class KMP {
    public static List<Integer> KMPmatcher(int[] pattern, int[] text, boolean last) {
        int m = pattern.length;
        int n = text.length;
        int[] pi = computePrefixFunction(pattern);
        int q = 0;
        List<Integer> result = new ArrayList<Integer>();
        for (int i = 0; i < n; i++) {
            while (q >= 0 && pattern[q] != text[i]) {
                if (q > 0) {
                    q = pi[q - 1];
                } else {
                    q--;
                }
            }
            q++;
            if (q == m) {
                q = pi[m - 1];
                result.add(i - m + 1);
                if (!last) {
                    return result;
                }

            }

        }
        if(result.size() == 0){
            result.add(-1);
        }
        return result;
    }

    public static int[] computePrefixFunction(int[] pattern) {
        int m = pattern.length;
        int[] pi = new int[m];
        pi[0] = 0;
        int k = 0;
        for (int i = 1; i < m; i++) {
            while (k >= 0 && pattern[k] != pattern[i]) {
                if (k - 1 >= 0) {
                    k = pi[k - 1];
                } else {
                    k--;
                }
            }
            k++;
            pi[i] = k;
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
        return Arrays.stream(arr, start, end).toArray();
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
                start = KMPmatcher(groundPhases.get(i), slope, false).get(0);
                if(start == -1) {
                    System.out.println("Impossible");
                    System.exit(0);
                }
                System.arraycopy(slope, start + groundPhases.get(i).length, slope, 0, slope.length - start - 1);
                System.out.println(Arrays.toString(groundPhases.get(i)));
                System.out.println(Arrays.toString(slope));
            } else if(i == groundPhases.size() - 1){
                //Run it normal but save the result and break
                List<Integer> result =KMPmatcher(groundPhases.get(i), slope, true);
                end = result.get(result.size() - 1);
                if(end == -1) {
                    System.out.println("Impossible");
                    System.exit(0);
                }
                System.out.println(Arrays.toString(groundPhases.get(i)));
                System.out.println(Arrays.toString(slope));
            } else {
                Integer result = KMPmatcher(groundPhases.get(i), slope, false).get(0);
                if(result == -1) {
                    System.out.println("Impossible");
                    System.exit(0);
                }
                System.arraycopy(slope, result + groundPhases.get(i).length, slope, 0, slope.length - result - 1);
                System.out.println(Arrays.toString(groundPhases.get(i)));
                System.out.println(Arrays.toString(slope));
            }

        }
//        groundPhases.get(groundPhases.size() - 1).length
        if(start != null && end != null){
            int realEnd = N - end;
            System.out.println(start + 1 + " " + realEnd);
        } else {
            System.out.println("Impossible");
        }

    }
}
