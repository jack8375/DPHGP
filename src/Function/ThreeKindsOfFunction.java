package Function;

import java.util.ArrayList;
import java.util.Arrays;

public class ThreeKindsOfFunction {
    //硬阈值
    public static double[][] HaT(double[][] binaryMatrix,double[][] SimF,double Constant) {    //输入：一个二进制矩阵，用相似度量方法得到的矩阵SimF，一个常数Constant(0-1之间).
        for(int i = 0 ; i < binaryMatrix.length ; i ++) {
            if(zero(SimF[i]) == 1) {
                for (int j = 0; j < binaryMatrix[i].length; j++) {
                    if ((int) binaryMatrix[i][j] == 1) {
                        if (SimF[i][j] < Constant) {
                            binaryMatrix[i][j] = 0;
                        } else {
                            binaryMatrix[i][j] = 1;
                        }
                    }
                }
            }else {
                Arrays.fill(binaryMatrix[i], 0);
            }
        }
        return binaryMatrix;                             //返回一个通过阈值函数过滤后的二进制矩阵
    }

    //比例阈值
    public static double[][] PrT(double[][] binaryMatrix,double[][] SimF,double Constant) {    //输入：一个二进制矩阵，用相似度量方法得到的矩阵SimF，一个常数Constant(0-1之间).
        double d = 0;
        for(int i = 0 ; i < binaryMatrix.length ; i ++) {
            if(zero(SimF[i]) == 1) {
                d = Threshold1(i, SimF, binaryMatrix, Constant);
                for (int j = 0; j < binaryMatrix[i].length; j++) {
                    if ((int) binaryMatrix[i][j] == 1) {
                        if (SimF[i][j] < d) {
                            binaryMatrix[i][j] = 0;
                        }
                    }
                }
            }else {
                Arrays.fill(binaryMatrix[i], 0);
            }
        }
        return binaryMatrix;                             //返回一个通过阈值函数过滤后的二进制矩阵
    }

    //百分比阈值
    public static double[][] PeT(double[][] binaryMatrix,double[][] SimF,double Constant) {    //输入：一个二进制矩阵，用相似度量方法得到的矩阵SimF，一个常数Constant(0-1之间).
        for(int i = 0 ; i < binaryMatrix.length ; i ++) {
            if(zero(SimF[i]) == 1) {
                double[] x = Threshold2(i, SimF, binaryMatrix, Constant);   //注意：Threshold2(i,SimF,binaryMatrix,Constant)必须要放在外层循环中,每行只计算一次百分比阈值
                int num = 0;
                for (int j = 0; j < binaryMatrix[i].length; j++) {
                    if (x[1] > 0) {
                        if (Constant != 1 && Constant != 0) {
                            if ((int) binaryMatrix[i][j] == 1) {
                                if (SimF[i][j] <= x[0]) {
                                    binaryMatrix[i][j] = 0;
                                    num++;
                                }
                            }
                            if (num == (int) (x[1] * (1 - Constant))) {
                                break;
                            }
                        }
                        if (Constant == 0) {
                            if ((int) binaryMatrix[i][j] == 1) {
                                if (SimF[i][j] <= x[0]) {
                                    binaryMatrix[i][j] = 0;
                                }
                            }
                        }
                    }
                }
            }else {
                Arrays.fill(binaryMatrix[i], 0);
            }
        }
        return binaryMatrix;                             //返回一个通过阈值函数过滤后的二进制矩阵
    }

    public static double Threshold1(int line,double[][] Matrix,double[][] binaryMatrix,double Constant) {   //求PrT函数当前行的阈值,line为当前行
        ArrayList<Double> list = new ArrayList<>();
        for(int i = 0 ; i < Matrix[line].length ; i ++) {  //由于此函数只针对二进制矩阵值为1的位置进行操作，所以把相似度矩阵Matrix对应二进制矩阵位置为1的SF值放到集合中
            if((int)binaryMatrix[line][i] == 1) {
               list.add(Matrix[line][i]);
            }
        }
        double max = 0.00;
        if(list.size() > 0) {
            for (double a : list) {    //遍历集合，找到ArrayList<Double>集合中的最大值，这个最大值也就是相似度矩阵中对应“line“行的最大相似度值
                if (a > max) {
                    max = a;
                }
            }
        }
        return max*Constant;          //用最大相似度值乘以设定的参数Constant，就得到了PrT函数中的阈值
    }

    public static double[] Threshold2(int line,double[][] Matrix,double[][] binaryMatrix,double Constant) {   // 求PeT函数当前行的阈值,line为当前行
        double[] s = new double[2];
        ArrayList<Double> list = new ArrayList<>();
        for(int i = 0 ; i < Matrix[line].length ; i ++) {   //由于此函数只针对二进制矩阵值为1的位置进行操作，所以把相似度矩阵Matrix对应二进制矩阵位置为1的SF值放到集合中
            if((int)binaryMatrix[line][i] == 1) {
                list.add(Matrix[line][i]);
            }
        }
        double num = 0;
        if(list.size() > 0) {
            double[] a = new double[list.size()];         //创建一个和list集合一样大小的一维数组
            for (int i = 0; i < list.size(); i++) {     //向数组里面添加元素
                a[i] = list.get(i);
            }
            Arrays.parallelSort(a);
            int index;
            if(Constant != 1 && Constant != 0) {
                index = (int) (a.length * (1-Constant));
            }else if(Constant == 1){
                index = 0;
            }else {
                index = a.length - 1;
            }
            num = a[index];
            s[0] = num;
            s[1] = list.size();
        }else {
            s[0] = 0;
            s[1] = 0;
        }
        //System.out.println(Arrays.toString(a));  //打印排序后的数组
        //System.out.println(a[i]);
        return s;
    }
    public static double[][] flippingMatrix(double[][] binaryMatrix) {     //将一个输入的二进制矩阵反转,值为1的变0,值为0的变1.
        double[][] x = new double[binaryMatrix.length][binaryMatrix[0].length];
        for(int i = 0 ; i < binaryMatrix.length ; i ++) {
            for(int j = 0 ; j < binaryMatrix[i].length ; j ++) {
                int a = (int)binaryMatrix[i][j];
                if(a == 1) {
                    x[i][j] = 0;
                }
                if(a == 0) {
                    x[i][j] = 1;
                }
            }
        }
        return x;
    }
    public static double[][] copyMatrix(double[][] binaryMatrix) {
        double[][] a = new double[binaryMatrix.length][binaryMatrix[0].length];
        for(int i = 0 ; i < binaryMatrix.length ; i ++) {
            for(int j = 0 ; j < binaryMatrix[i].length ; j ++) {
                a[i][j] = binaryMatrix[i][j];
            }
        }
        return a;
    }
    public static int zero(double[] b) {      //a = 1时说明b中不全为零；a = 0时说明b中全为零
        int a = 0;
        for(int i = 0 ; i < b.length ; i++) {
            if(b[i] != 0) {
                a = 1;
                break;
            }
        }
        return a;
    }
}
