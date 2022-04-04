public class test {
    public static void main(String[] args) throws Exception {
        for (int y = 0; y < 100000; y++) {
            int len = (int)(Math.random() * 10 + 2);
            StringBuilder s = new StringBuilder();
            for (int i = 0; i < len; i++){
                //System.err.println((char)('a' + (int)(Math.random() * 25)));
                s.append((char)('a' + (int)(Math.random() * 5)));
            }
            System.err.println(s);
            String s2 = ETest.main(s.toString());
            System.err.println(s2);
            int count = 0;
            for (int i = 0; i < s2.length(); i++){
                if (s2.charAt(i) == ' '){
                    count++;
                }
            }
            StringBuilder s4 = new StringBuilder(Integer.toString(count));
            s4.append(" ");
            s4.append(s2);
            System.err.println(s4);
            String s3 = FTest.main(s4.toString());
            System.err.println(s3);
            if (!s3.equals(s.toString())){
                throw new Exception("different");
            }
        }
    }
}
