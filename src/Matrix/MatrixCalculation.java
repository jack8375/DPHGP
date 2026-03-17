package Matrix;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.RealMatrix;


/**
 * 该类提供一些矩阵计算的方法，矩阵的除法a/b相当于a*（b的逆矩阵）
 * 1.arrayToMatrix(string[][]):可以将二维数组转换为矩阵
 * 2.transposedMatrix(RealMatrix):可以将矩阵转置
 * 3.multiplyMatrix(RealMatrix1,RealMatrix2):可以将两个矩阵相乘
 * 4.inverseMatrix(RealMatrix1):将矩阵求逆
 * 5.addMatrix(RealMatrix1,RealMatrix2):两矩阵相加
 * 6.subMatrix(RealMatrix1,RealMatrix2):两矩阵相减
 * 7.matrixToArr(RealMatrix1):将矩阵化为二维数组
 * 8.numTimesMatrix(n,RealMatrix1):将常数n与矩阵相乘
 */

public class MatrixCalculation {
    // 把二维数组转化为矩阵，输入为一个double型的二维数组，返回的为
    public static RealMatrix arrayToMatrix(double[][] array) {
        RealMatrix rm = new Array2DRowRealMatrix(array);
        return rm;
    }

    // 把输入的矩阵进行转置处理，输入输出都为一个矩阵类
    public static RealMatrix transposedMatrix(RealMatrix rm) {
        RealMatrix rmtranspose = rm.transpose();
        return rmtranspose;
    }

    // 将两个矩阵进行相乘运算，输入为两个矩阵，输出为该两个可乘矩阵的乘积
    public static RealMatrix multiplyMatrix(RealMatrix rm1, RealMatrix rm2) {
        RealMatrix rm = rm1.multiply(rm2);
        return rm;
    }

    // 将一个矩阵进行逆计算，输入为一个方阵，输出为一个方阵
    public static RealMatrix inverseMatrix(RealMatrix a) {
        RealMatrix result = new LUDecomposition(a).getSolver().getInverse();
        return result;
    }

    // 矩阵加法,输入为两个方阵，输出为一个方阵
    public static RealMatrix addMatrix(RealMatrix a, RealMatrix b) {
        RealMatrix result = a.add(b);
        return result;
    }

    // 矩阵减法,输入为两个方阵，输出为一个方阵
    public static RealMatrix subMatrix(RealMatrix a, RealMatrix b) {
        RealMatrix result = a.subtract(b);
        return result;
    }

    // 矩阵转化为数组，输入为一个矩阵，输出为对应的double二维数组
    public static double[][] matrixToArr(RealMatrix A) {
        double matrixToArray[][]=A.getData();
        return matrixToArray;
    }

    // 一个数与一个矩阵相乘，输入为一个数和一个矩阵，输出为该数与此矩阵相乘后的结果
    public static RealMatrix numTimesMatrix(double d, RealMatrix A) {
        double matrixToArray[][]=A.getData();
        for (int i = 0; i < matrixToArray.length; i++) {
            for (int j = 0; j < matrixToArray[0].length; j++) {
                matrixToArray[i][j] = d * matrixToArray[i][j];
            }
        }
        RealMatrix matrix = new Array2DRowRealMatrix(matrixToArray);
        return matrix;
    }

    //矩阵除法，输入为两个方阵，输出为一个方阵
    public static RealMatrix divMatrix(RealMatrix rm1, RealMatrix rm2) {
        RealMatrix rm3 = inverseMatrix(rm2);
        RealMatrix rm = rm1.multiply(rm3);
        return rm;
    }
}
