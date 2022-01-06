import java.util.*;
import static java.util.Arrays.copyOfRange;

public class KMP {
    public static List<Integer> kmpMatcher(int[] pattern, int[] text, boolean last) {
        int m = pattern.length, n = text.length;
        List<Integer> result = new ArrayList<>();
        if(m < n){
            int[] pi = computePrefixFunction(pattern);
            int q = 0;
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
                    if (!last) {
                        result.add(i - m + 1);
                        return result;
                    } else {
                        result.add(i);
                    }
                }
            }
        }
        if(result.size() == 0){
            result.add(-1);
        }
        return result;
    }

        public static int[] computePrefixFunction(int[] pattern) {
            int m = pattern.length, k = 0;
            int[] pi = new int[m];
            pi[0] = 0;
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
        return copyOfRange(arr, start, end);
    }

    public static void checkImpossible(int checkNum){
        if(checkNum == -1){
            System.out.println("Impossible");
            System.exit(0);
        }
    }


    public static void main(String[] args){
        Integer start = null, end = null;
        int cut = 0;
        Scanner scanner = new Scanner(System.in);
        int[] NG = split(scanner.nextLine());
        int N = NG[0], G = NG[1];
        if(N >= G){
            int[] slope = split(scanner.nextLine());
            List<int[]> groundPhases = new ArrayList<>();
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
                    start = kmpMatcher(groundPhases.get(i), slope, false).get(0);
                    checkImpossible(start);
                    slope = arraySlicing(slope, start + groundPhases.get(i).length + 1, slope.length);
                    cut += start + groundPhases.get(i).length + 1;
                } else if(i == groundPhases.size() - 1){
                    //Last time running KMP and saving the result as end
                    List<Integer> result = kmpMatcher(groundPhases.get(i), slope, true);
                    end = result.get(result.size() - 1);
                    checkImpossible(end);

                } else {
                    //Run it normal but save the result and break
                    Integer result = kmpMatcher(groundPhases.get(i), slope, false).get(0);
                    checkImpossible(result);
                    slope = arraySlicing(slope, result + groundPhases.get(i).length + 1, slope.length);
                    cut += result + groundPhases.get(i).length + 1;
                }
            }
            if(start != null && end != null){
                int realEnd = cut + end - start + 1;
                System.out.println(start + 1 + " " + realEnd);
            } else {
                System.out.println("Impossible");
            }
        } else {
            System.out.println("Impossible");
        }
    }
}
