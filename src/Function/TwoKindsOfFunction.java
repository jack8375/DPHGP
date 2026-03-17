package Function;

public class TwoKindsOfFunction {
    public static double[][] Intersect(double[][] a,double[][] b) {       //与
        double[][] Matrix = new double[a.length][a[0].length];
        for(int i = 0 ; i < a.length ; i ++) {
            for(int j = 0 ; j < a[i].length ; j++) {
                if((int)a[i][j] == 1 && (int)b[i][j] == 1) {
                    Matrix[i][j] = 1;
                }else {
                    Matrix[i][j] = 0;
                }
            }
        }
        return Matrix;
    }
    public static double[][] Union(double[][] a,double[][] b) {         //或
        double[][] Matrix = new double[a.length][a[0].length];
        for(int i = 0 ; i < a.length ; i ++) {
            for(int j = 0 ; j < a[i].length ; j++) {
                if((int)a[i][j] == 0 && (int)b[i][j] == 0) {
                    Matrix[i][j] = 0;
                }else {
                    Matrix[i][j] = 1;
                }
            }
        }
        return Matrix;
    }
}
