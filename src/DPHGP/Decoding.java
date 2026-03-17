package DPHGP;

import Function.ThreeKindsOfFunction;
import Function.TwoKindsOfFunction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Decoding {
    /**
     * @param DT 输入一棵树的集合
     * @paeam SMOA，Ngram，Wu，Resnik，Instance，SimilarityFlood 相似度矩阵
     * @return  二进制矩阵集合
     */
    public static ArrayList<double[][]> decoding1(ArrayList<TreeNode> DT, double[][] SMOA,double[][] Wu, double[][] Ngram, double[][] Resnik, double[][] Instance, double[][] SimilarityFlood) {
        ArrayList<double[][]> list = new ArrayList<>();              //存放叶子节点的输出矩阵
        double[][] binaryMatrix = new double[SMOA.length][SMOA[0].length];
        for (int i = 0; i < SMOA.length; i++) {                               //构造一个和相似度矩阵维数相同并且元素全为1的矩阵作为根节点的输入二进制矩阵
            for (int j = 0; j < SMOA[i].length; j++) {
                binaryMatrix[i][j] = 1.0;
            }
        }
        double[][] Matrix = new double[SMOA.length][SMOA[0].length];         //中间变量
        for (int i = 0; i < DT.size(); i++) {                                //遍历DT树中的节点，从根节点开始
            TreeNode root = DT.get(i);                                       //拿到第i个节点
            if (root.getFlag() == 0) {                                       //非叶子节点进行过滤操作
                if (root.getSimilarityMeasure().equals("SMOA")) {
                    root.setSimF(SMOA);
                }
                if (root.getSimilarityMeasure().equals("Wu-and-Palmer")) {
                    root.setSimF(Wu);
                }
                if (root.getSimilarityMeasure().equals("N-Gram")) {
                    root.setSimF(Ngram);;
                }
                if (root.getSimilarityMeasure().equals("Instance")) {
                    root.setSimF(Instance);
                }
                if (root.getSimilarityMeasure().equals("Resnik")) {
                    root.setSimF(Resnik);
                }
                if (root.getSimilarityMeasure().equals("SimilarityFlood")) {
                    root.setSimF(SimilarityFlood);
                }
                if (root.getDeep() == 0) {
                    Matrix = ThreeKindsOfFunction.copyMatrix(binaryMatrix);
                }
                if (root.getDeep() != 0) {
                    if(root.getSubTree() == "左子树") {
                        Matrix = ThreeKindsOfFunction.copyMatrix(root.getParent().getOutPut1());
                        //double[][] a = root.getParent().getOutPut1();
                        /*System.out.println("左子树的输入矩阵为：");
                        for(int c =0 ; c < a.length ; c++ ) {
                            for(int b = 0 ; b < a[c].length; b++) {
                                System.out.print(a[c][b] + " ");
                            }
                            System.out.println();
                        }
                        System.out.println("-----------------------------------------------------------------------------");*/
                    }
                    if(root.getSubTree() == "右子树") {
                        Matrix = ThreeKindsOfFunction.copyMatrix(root.getParent().getOutPut2());
                        //double[][] a = root.getParent().getOutPut2();
                        /*System.out.println("右子树的输入矩阵为：");
                        for(int c =0 ; c < a.length ; c++ ) {
                            for(int b = 0 ; b < a[c].length; b++) {
                                System.out.print(a[c][b] + " ");
                            }
                            System.out.println();
                        }
                        System.out.println("-----------------------------------------------------------------------------");*/
                    }
                }
                double[][] binaryMatrix1 = new double[SMOA.length][SMOA[0].length];
                double[][] binaryMatrix2 = new double[SMOA.length][SMOA[0].length];
                double[][] a = new double[SMOA.length][SMOA[0].length];
                if (root.getFunction().equals("HaT")) {
                    binaryMatrix1 = ThreeKindsOfFunction.HaT(Matrix, root.getSimF(), root.getConstants());
                }
                if (root.getFunction().equals("PrT")) {
                    binaryMatrix1 = ThreeKindsOfFunction.PrT(Matrix, root.getSimF(), root.getConstants());
                }
                if (root.getFunction().equals("PeT")) {
                    binaryMatrix1 = ThreeKindsOfFunction.PeT(Matrix, root.getSimF(), root.getConstants());
                }
                /*for(int a = 0 ; a < binaryMatrix1.length ; a++ ) {
                    for(int b = 0 ; b < binaryMatrix1[a].length; b++) {
                        System.out.print(binaryMatrix1[a][b] + " ");
                    }
                    System.out.println();
                }
                System.out.println("------------------------------------------------------------------------------");*/
                a = ThreeKindsOfFunction.copyMatrix(binaryMatrix1);
                binaryMatrix2 = ThreeKindsOfFunction.flippingMatrix(a);
                root.setOutPut1(binaryMatrix1);
                root.setOutPut2(binaryMatrix2);
            } else {                          //由叶子节点得到最终结果
                if(root.getFunction1().equals("outputMatrix")) {
                    double[][] s = root.getParent().getOutPut1();
                    list.add(ThreeKindsOfFunction.copyMatrix(s));
                    /*System.out.println("叶子节点输出的PBM为：");
                    for(int c =0 ; c < a.length ; c++ ) {
                        for(int b = 0 ; b < a[c].length; b++) {
                            System.out.print(a[c][b] + " ");
                        }
                        System.out.println();
                    }
                    System.out.println("---------------------------------------------------------------------------");*/
                }
            }
        }
        /*for(int i = 0; i < DT.size(); i++) {
            if(DT.get(i).getFlag() == 0) {
                DT.get(i).setOutPut1(null);
                DT.get(i).setOutPut2(null);
            }
        }*/
        return list;
    }
    /**
     * 输入一颗树，返回该树最终合成的PBM
     */
    public static ArrayList<double[][]> DTofPBMandMatrix(TreeNode dt,double[][] SMOA,double[][] Wu, double[][] Ngram, double[][] Resnik, double[][] Instance, double[][] SimilarityFlood) {
        ArrayList<double[][]> list2 = new ArrayList<>();
        double[][] b = new double[SMOA.length][SMOA[0].length];
        ArrayList<TreeNode> DT = macro_Crossover.treetolist(dt);
        ArrayList<double[][]> list = Decoding.decoding1(DT,SMOA,Wu,Ngram,Resnik,Instance,SimilarityFlood); //解码之后得到的PBM
        double[][] finalPBM;
        if(list.size() == 1) {
            finalPBM = list.get(0);
        }else {
            finalPBM = integratePBM(list);
        }
        list2.add(finalPBM);
        for(int i = 0 ; i < b.length ; i++) {
            for(int j = 0 ; j < b[i].length ; j++) {
                if(list2.get(0)[i][j] == 1) {
                    b[i][j] = SimFValue(DT,list,i,j);
                }else {
                    b[i][j] = 0;
                }
            }
        }
        list2.add(b);
        return list2;
    }
     //为CGA优化提高速度而设置DTofPBMandMatrix1
    public static ArrayList<double[][]> DTofPBMandMatrix1(ArrayList<TreeNode> DT,double[][] SMOA,double[][] Wu, double[][] Ngram, double[][] Resnik, double[][] Instance, double[][] SimilarityFlood) {
        ArrayList<double[][]> list2 = new ArrayList<>();
        double[][] b = new double[SMOA.length][SMOA[0].length];
        ArrayList<double[][]> list = Decoding.decoding1(DT,SMOA,Wu,Ngram,Resnik,Instance,SimilarityFlood); //解码之后得到的PBM
        double[][] finalPBM;
        if(list.size() == 1) {
            finalPBM = list.get(0);
        }else {
            finalPBM = integratePBM(list);
        }
        list2.add(finalPBM);
        for(int i = 0 ; i < b.length ; i++) {
            for(int j = 0 ; j < b[i].length ; j++) {
                if(list2.get(0)[i][j] == 1) {
                    b[i][j] = SimFValue(DT,list,i,j);
                }else {
                    b[i][j] = 0;
                }
            }
        }
        list2.add(b);
        return list2;
    }
    public static double[][] integratePBM(ArrayList<double[][]> list) {          //如果DT个体的PBM集合大小超过1,则使用该方法进行合成
        double[][] Matrix1 = list.get(0);
        for(int i = 1 ; i < list.size() ; i ++) {
            Matrix1 = TwoKindsOfFunction.Union(Matrix1,list.get(i));
        }
        return Matrix1;
    }
    /**
     * DT  输入一颗树
     * list 一棵树叶子节点的PBM
     * row 对应矩阵的行
     * column 对应矩阵的列
     * return 对齐对应的相似度值
     */
    public static double SimFValue(ArrayList<TreeNode> DT,ArrayList<double[][]> list,int row,int column) {      //计算对齐对应的相似度值
        ArrayList<Integer> list2 = new ArrayList<>();                                              //list2存储DT树中输出为PBM的叶子节点的序号
        ArrayList<Double> list3 = new ArrayList<>();                                               //list3存储输出的SF平均值

        double max = 0;
        for(int j = 0 ; j < DT.size() ; j++) {                           //拿到这些叶子节点在树集合中的位置
            TreeNode node = DT.get(j);
            if(node.getFunction1() == "outputMatrix") {
                list2.add(node.getSerial());
            }
        }
        for(int i = 0 ; i < list.size() ; i ++) {
            if(list.get(i)[row][column] == 1) {
                list3.add(averageSimF(DT.get(list2.get(i)),row,column));
            }
        }
        if(list3.size() == 1) {
            max = list3.get(0);
        }else {
            for(int i = 0 ; i < list3.size() ; i++) {
                max = Math.max(max,list3.get(i));
            }
        }
        return max;
    }
    public static double averageSimF(TreeNode node,int row,int column) {                                   //求沿着划分路径上对应位置的SF的均值
        double sum = 0;
        int i = 0;
        double average = 0;
        while(true) {
            node = node.getParent();
            if(node.getSubTree().equals("右子树") || node.getSubTree().equals("根节点")) {
                sum += node.getSimF()[row][column];
                i ++;
                break;
            }
            if(node.getSubTree().equals("左子树")) {
                sum += node.getSimF()[row][column];
                i ++;
            }
        }
        average = sum/i;
        return average;
    }
    //输入一个树种群，从中选择出指定数量的精英DT
    public static ArrayList<TreeNode> selectEliteDT(ArrayList<TreeNode> Pdt,int Size,double[] fmeasureSet) {
        ArrayList<TreeNode> EliteDTset = new ArrayList<>();   //存放选择出来的精英树
        double[] fmeasureSet1 = Copy.Copyshuzu(fmeasureSet);
        Set set = new HashSet();
        for(int i = 0 ;i < fmeasureSet1.length ; i++) {
            set.add(fmeasureSet1[i]);
        }
        Object[] c = set.toArray();
        double[] v = new double[c.length];
        for (int i = 0; i < c.length; i++) {
            if (c[i] instanceof Double) {
                v[i] = (Double) c[i];
            }
        }
        Arrays.parallelSort(v);
        ArrayList<TreeNode> copyPdt = Copy.CopyDTPopulation(Pdt);
        if(v.length >= 50) {
            for (int k = v.length - 1; k >= 0; k--) {
                for (int h = 0; h < copyPdt.size(); h++) {
                    if (copyPdt.get(h).getFitness() == v[k]) {
                        EliteDTset.add(copyPdt.get(h));
                        copyPdt.remove(h);
                        break;
                    }
                }
                if (EliteDTset.size() == Size) {
                    break;
                }
            }
        }else {
            Arrays.parallelSort(fmeasureSet1);
            for (int k = fmeasureSet1.length - 1; k >= 0; k--) {
                for (int h = 0; h < copyPdt.size(); h++) {
                    if (copyPdt.get(h).getFitness() == fmeasureSet1[k]) {
                        EliteDTset.add(copyPdt.get(h));
                        copyPdt.remove(h);
                        break;
                    }
                }
                if (EliteDTset.size() == Size) {
                    break;
                }
            }
        }
        return EliteDTset;
    }
    public static int FitnessMinimum(ArrayList<TreeNode> Pdt,double[] fmeasureSet) {
        double[] fmeasureSet1 = Copy.Copyshuzu(fmeasureSet);
        int index = 0;
        Arrays.parallelSort(fmeasureSet1);
        for(int k = 0 ; k < Pdt.size() ; k ++) {
            if (Pdt.get(k).getFitness() == fmeasureSet1[0]) {
                index = k;
            }
        }
        return index;
    }
    public static int baohan1(TreeNode1 ei,ArrayList<TreeNode1> set) {   //a=1时，包含；a=0时，不包含；
        int a = 0;
        if(set.isEmpty()) {
            a = 0;
        }
        for(int i = 0 ; i < set.size() ;i ++) {
            if(Copy.EIisEqual(ei,set.get(i)) == 0) {
                a = 1;
                break;
            }
        }
        return a;
    }
    public static int baohan(TreeNode DT,ArrayList<TreeNode> set) {   //a=1时，包含；a=0时，不包含；
        int a = 0;
        if(set.isEmpty()) {
            a = 0;
        }
        for(int i = 0 ; i < set.size() ;i ++) {
            if(Copy.DTisEqual(DT,set.get(i)) == 0) {
                a = 1;
                break;
            }
        }
        return a;
    }
}
