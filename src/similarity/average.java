package similarity;

public class average {
    public static void main(String[] args) throws Exception {       //数据处理
        double[] a = {0.903,0.903,0.866,0.903,0.903,0.866,0.903,0.866,0.903,0.903,0.903};
        double sum = 0;
        for(int i = 0 ; i < a.length ; i ++) {
            sum += a[i];
        }
        double average = sum/a.length;
        double b = 0;
        for(int i = 0 ; i < a.length ; i ++) {
            b += Math.pow(a[i]-average,2);
        }
        double c = Math.sqrt(b/a.length);
        System.out.println(average);
        System.out.println(c);
    }
}
