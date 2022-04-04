import java.io.*;

public class I {
    public static final int[] id = {1, 0, 0, 1};
    private static int[][] a;
    private static int rf;

    public static void set(int i, int[] v, int x, int lx, int rx){
        if (rx - lx == 1){
            a[x] = v;
        } else {
            int m = (lx + rx) / 2;
            if (i < m){
                set (i, v, 2 * x + 1, lx, m);
            } else {
                set(i, v, 2 * x + 2, m, rx);
            }

            a[x][0] = (a[2 * x + 1][0] * a[2 * x + 2][0]
                    + a[2 * x + 1][1] * a[2 * x + 2][2]) % rf;
            a[x][1] = (a[2 * x + 1][0] * a[2 * x + 2][1]
                    + a[2 * x + 1][1] * a[2 * x + 2][3]) % rf;
            a[x][2] = (a[2 * x + 1][2] * a[2 * x + 2][0]
                    + a[2 * x + 1][3] * a[2 * x + 2][2]) % rf;
            a[x][3] = (a[2 * x + 1][2] * a[2 * x + 2][1]
                    + a[2 * x + 1][3] * a[2 * x + 2][3]) % rf;
             /*
            for (int ii = 0; ii < 2; ii++) {
                for (int j = 0; j < 2; j++) {
                    for (int k = 0; k < 2; k++) {
                        a[x][ii][j] += a[2 * x + 1][ii][k] * a[2 * x + 2][k][j];
                        a[x][ii][j] %= rf;
                    }
                }
            }
            */
        }
    }

    public static int[] sum(int n, int lx, int rx, int l, int r){
        if (rx < l || lx > r){
            return id;
        }
        if (lx >= l && rx <= r){
            return a[n];
        }
        int m = (lx + rx) / 2;
        int[] s1 = sum(2 * n + 1, lx, m, l, r);
        int[] s2 = sum(2 * n + 2, m + 1, rx, l, r);
        //int[] z = new int[4];

        int z1 = (s1[0] * s2[0] + s1[1] * s2[2]) % rf;
        int z2 = (s1[0] * s2[1] + s1[1] * s2[3]) % rf;
        int z3 = (s1[2] * s2[0] + s1[3] * s2[2]) % rf;
        int z4 = (s1[2] * s2[1] + s1[3] * s2[3]) % rf;
        return new int[]{z1, z2, z3, z4};

         /*
        for (int ii = 0; ii < 2; ii++) {
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 2; k++) {
                    z[ii][j] += s1[ii][k] * s2[k][j];
                    z[ii][j] %= rf;
                }
            }
        }
        return z;

          */
    }

    public static boolean check(char c) {
        return Character.isDigit(c) || Character.isLetter(c) || c == '-' || c == '+';
    }

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in, I::check);
        rf = Integer.parseInt(sc.nextElement());
        int n = Integer.parseInt(sc.nextElement());
        int m = Integer.parseInt(sc.nextElement());
        int len = (int)Math.pow(2, (int)(Math.log(n * 2 - 1)/Math.log(2)));
        a = new int[2 * len - 1][4];
        for (int i = 0; i < n; i++){
            int[] t = new int[4];
            t[0] = Integer.parseInt(sc.nextElement());
            t[1] = Integer.parseInt(sc.nextElement());
            t[2] = Integer.parseInt(sc.nextElement());
            t[3] = Integer.parseInt(sc.nextElement());
            a[i + len - 1] = t;
        }
        for (int i = len - 2; i >= 0; i--){
            a[i][0] = (a[2 * i + 1][0] * a[2 * i + 2][0]
                    + a[2 * i + 1][1] * a[2 * i + 2][2]) % rf;
            a[i][1] = (a[2 * i + 1][0] * a[2 * i + 2][1]
                    + a[2 * i + 1][1] * a[2 * i + 2][3]) % rf;
            a[i][2] = (a[2 * i + 1][2] * a[2 * i + 2][0]
                    + a[2 * i + 1][3] * a[2 * i + 2][2]) % rf;
            a[i][3] = (a[2 * i + 1][2] * a[2 * i + 2][1]
                    + a[2 * i + 1][3] * a[2 * i + 2][3]) % rf;
        }
        for (int i = 0; i < m; i++){
            int t1 = Integer.parseInt(sc.nextElement());
            int t2 = Integer.parseInt(sc.nextElement());
            int[] res = sum(0, 0, len - 1, t1 - 1, t2 - 1);
            //System.out.println(res[0] + " " + res[1] + "\n" + res[2] + " " + res[3] + "\n");
            //System.out.println(String.format("%d %d\n%d %d\n",res[0], res[1], res[2], res[3]));

            //System.out.println(res[0] + " " + res[1]);
            //System.out.println(res[2] + " " + res[3]);
            //System.out.println();
            System.out.print(res[0] + " " + res[1] + "\n" + res[2] + " " + res[3] + "\n\n");
            //PrintWriter writer = new PrintWriter(System.out);
            //writer.write(res[0] + " " + res[1] + "\n" + res[2] + " " + res[3] + "\n\n");
            /*
            System.out.println(String.join(" ", res[0]));
            System.out.println(String.join(" ", res[1]));
            System.out.println();
             */
        }
    }
}

class Scanner {
    private Reader reader;
    private char last = '0';
    private char lastPrev = '0';

    private final Checker checker;

    public boolean check(char c){ 			//checks if symbol is "good"
        return checker.check(c);
    }

    public interface Checker {
        boolean check(char c);
    }

    public boolean checkLine(Reader reader) throws IOException {  		//checks if symbol at position j in string s is a line separator
        boolean bool = (last == '\r' || (last == '\n' && (lastPrev != '\r')));
        return bool;
    }

    public Scanner(String str, Checker checker) throws IOException{		// scanner from string
        this.reader = new BufferedReader(new StringReader(str));
        this.checker = checker;
        this.last = (char)reader.read();
    }

    public Scanner(InputStream inputStream, Checker checker) throws IOException{		// scanner from inputstream
        this.reader = new BufferedReader(new InputStreamReader(inputStream));
        this.checker = checker;
        this.last = (char)reader.read();
    }

    public Scanner(File file, Checker checker) throws FileNotFoundException, UnsupportedEncodingException, IOException{		//scanner from file
        this.reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf8"));
        this.checker = checker;
        this.last = (char)reader.read();
    }

    public boolean hasNext() throws IOException{ 					// checks if there is next element
        boolean b = false;
        while (last != (char)-1) {
            if (check(last) || checkLine(reader)){
                b = true;
                break;
            }
            lastPrev = last;
            last = (char)reader.read();
        }
        if (b == false){
            reader.close();
        }
        return b;
    }

    public boolean hasNextInLine() throws IOException{ 					// checks if there is next element
        boolean b = false;
        while (last != (char)-1 && !checkLine(reader)) {
            //System.err.println(last + " " + lastPrev + " " + checkLine(reader) + " " + check(last));
            //System.err.println(last == '\r');
            //System.err.println(last == '\n');
            if (check(last) /*|| checkLine(reader)*/){
                b = true;
                break;
            } else {
                lastPrev = last;
                last = (char)reader.read();
            }
        }
        return b;
    }

    public String nextElement() throws IOException{
        StringBuilder str = new StringBuilder();
        //last = (char)reader.read();
        //System.err.println(last);
        while (last != (char)-1 && !check(last)){
            //System.err.println(last);
            lastPrev = last;
            last = (char)reader.read();
        }
        while (last != (char)-1 && check(last)){
            //System.err.println(last);
            str.append(Character.toLowerCase(last));
            lastPrev = last;
            last = (char)reader.read();
        }
        //System.err.println(str.length() + " len");
        if (str.length() == 0){
            str.append(System.lineSeparator());
        }
        String res = "";
        if (str.length() > 2 && str.charAt(0) == '0' && str.charAt(1) == 'x'){
            String strTemp = str.substring(2, str.length());
            //System.err.println(strTemp);
            long longTemp = Long.parseLong(strTemp, 16);
            //System.err.println(longTemp);
            if (longTemp > (long) Integer.MAX_VALUE){
                longTemp -= ((long) Integer.MAX_VALUE + 1) * 2;
            }
            res = Long.toString(longTemp);
        } else {
            res = str.toString();
        }
        //System.err.println("res= " + res);
        return res;
    }

    public void nextLine() throws IOException{
        if (checkLine(reader)){
            lastPrev = last;
            last = (char)reader.read();
            //System.err.println(lastPrev + " " + last);
        }
    }

    public String readNextLine() throws IOException{
        StringBuilder str = new StringBuilder();
        while (last != (char)-1 && !checkLine(reader)){
            //System.err.println(last);
            str.append(Character.toLowerCase(last));
            lastPrev = last;
            last = (char)reader.read();
        }
        lastPrev = last;
        last = (char)reader.read();
        //System.err.println(str.length() + " len");
        if (str.length() == 0){
            str.append(System.lineSeparator());
        }
        return str.toString();
    }
}
