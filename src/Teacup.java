import java.util.ArrayList;
import java.util.Arrays;

public class Teacup {

    public int size;
    public Teacup(int s) {
        size = s;

    }

    public int addString(String s) {
        if (s.equals("")) return 0;
        String [] ints = s.split(" ");
        int sum = 0;
        for (String i : ints) {
            if (!i.equals("")) {
                sum += Integer.parseInt(i);
            }
        }
        return sum;
    }

    public String generate(int i, String choice) {
        int sum = addString(choice);
        if (i < 1) i = 1;
        if (sum >= size) return choice;
        return generate(i - 1, choice + i + " ");
    }


    public void printChoices(int amt, String soFar, int currentSize) {
        if (addString(soFar) == 10) System.out.println(soFar);
        if (addString(soFar) < 10) {
            for (int i = 1; i <= currentSize; i++) {
                printChoices(amt - i, soFar + " " + i, i);
            }
        }

    }

    public int[][] dp(int [] set, int [][] profit) {
//        for (int i = 0; i < profit.length; i++) {
//            profit[i][0] = 0;
//            profit[0][i] = 0;
//        }
        for (int j = 1; j < profit[0].length; j++) {
            for (int i = 1; i < profit.length; i++) {
                int numTea = j;
                int largestSize = i;

                int use = 0;
                int dont = profit[largestSize - 1][numTea];
                if (largestSize <= numTea) {
                    use = set[largestSize] + profit[largestSize][numTea - largestSize];
                }
                else {
                    largestSize--;
                }
                profit[i][j] = Math.max(use, dont);
            }
        }
        return profit;
    }

    public void printProfit(int [][] profit)  {
        for (int i = 0; i < profit.length; i++) {
            System.out.print(i + " ");
        }
        for (int i = 1; i < profit.length; i++) {
            System.out.print(i + " ");
            int n = 0,k = 0;
            while (n != profit.length)
            {
                while (k != profit[n].length)
                {
                    System.out.print(profit[n][k] + " ");
                    k++;
                }
                k = 0;
                n++;
                System.out.println("");
            }
        }
    }

    public void printResults (int [][] profit) {
        for (int i = 1; i < profit[0].length; i++) {
            String g = findGrouping(i, profit);
            String s = "Best Sum for (" + i + " teacup) : $" + profit[profit.length - 1][i] + g;
            System.out.println(s);
        }
    }
    public String findGrouping(int numTea, int [][] profit) {
        String s = " ";
        int currSize = profit.length - 1;

        while (numTea > 0) {
            int curr = profit[currSize][numTea];
            int above = profit[currSize - 1][numTea];

            if (curr == above) {
                currSize--;
            }
            else {
                s += currSize + " ";
                numTea -= currSize;
            }
        }
        return s;
    }



    public static void main(String [] args) {
        Teacup t = new Teacup(10);
        int [] set1 = new int[]{0,1, 3, 5, 9, 10, 15, 17, 18, 19, 22, 25, 27};
        int [] set2 = new int[]{0,2, 5, 8, 9, 10, 15, 19, 23, 24, 29, 30, 32};
//        System.out.println(t.addString("1 2 3 4 5"));
//        System.out.println(t.generate(2, "3"));
        System.out.println("**************** Part 1 ****************");
        t.printChoices(10, "", 10);

        System.out.println("**************** Part 2 ****************");
        int [][] arr = new int [13][25];
        System.out.println("Set 1");
        int [][] profit = t.dp(set1, arr);
        t.printResults(profit);

        System.out.println("Set 2");
        profit = t.dp(set2, arr);
        t.printResults(profit);
    }

}
