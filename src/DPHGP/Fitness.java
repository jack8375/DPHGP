package DPHGP;

import java.util.ArrayList;
import java.util.List;

public class Fitness {
    //评估单棵树
    public static double  fitnessOfDT (TreeNode dt, double[][] SMOA, double[][] Wu, double[][] Ngram, double[][] Resnik, double[][] Instance, double[][] SimilarityFlood) {
        ArrayList<double[][]> list = Decoding.DTofPBMandMatrix(dt,SMOA,Wu,Ngram,Resnik,Instance,SimilarityFlood);
        double[][] PBM = list.get(0);
        int num1 = 0;                                                        //存放源本体中匹配实体的数量
        int num2 = 0;                                                        //存放目标本体中匹配实体的数量
        int num3 = 0;                                                        //存放匹配对的个数
        double num4 = 0;                                                     //存放SimFValue之和
        double recall = 0;      //近似查全
        double precision = 0;   //近似查准
        double fmeasure = 0;    //近似f-measure
        double asvA = 0;
        double cardA = 0;
        for(int i = 0 ; i < PBM.length ; i ++) {                              //统计源本体中匹配实体的数量
            for(int j = 0 ; j < PBM[i].length ; j ++) {
                if(PBM[i][j] == 1) {
                    num1 += 1;
                    break;
                }
            }
        }
        for(int i = 0 ; i < PBM[0].length ; i ++) {                           //统计目标本体中匹配实体的数量
            for(int j = 0 ; j < PBM.length ; j ++) {
                if(PBM[j][i] == 1) {
                    num2 += 1;
                    break;
                }
            }
        }
        for(int i = 0 ; i < PBM.length ; i ++) {
            for(int j = 0 ; j < PBM[i].length ; j ++) {
                if(PBM[i][j] == 1) {
                    num3 += 1;                                                //统计匹配对的个数
                    num4 += list.get(1)[i][j];
                }
            }
        }
        recall = (double)(num1 + num2)/(PBM.length + PBM[0].length);  //近似查全
        asvA = (double)num4/num3;
        cardA = (double)(num1 + num2)/(2*num3);
        precision = Math.sqrt(asvA*cardA);       //近似查准
        fmeasure = (2*recall*precision)/(recall + precision);
        if(recall == 0 || precision == 0) {
            fmeasure = 0;
        }
        dt.setFitness(fmeasure);
        return fmeasure;
    }
    //用于CGA优化时提高速度
    public static double  fitnessOfDT1 (ArrayList<TreeNode> DT, double[][] SMOA, double[][] Wu, double[][] Ngram, double[][] Resnik, double[][] Instance, double[][] SimilarityFlood) {
        ArrayList<double[][]> list = Decoding.DTofPBMandMatrix1(DT,SMOA,Wu,Ngram,Resnik,Instance,SimilarityFlood);
        double[][] PBM = list.get(0);
        int num1 = 0;                                                        //存放源本体中匹配实体的数量
        int num2 = 0;                                                        //存放目标本体中匹配实体的数量
        int num3 = 0;                                                        //存放匹配对的个数
        double num4 = 0;                                                     //存放SimFValue之和
        double recall = 0;      //近似查全
        double precision = 0;   //近似查准
        double fmeasure = 0;    //近似f-measure
        double asvA = 0;
        double cardA = 0;
        for(int i = 0 ; i < PBM.length ; i ++) {                              //统计源本体中匹配实体的数量
            for(int j = 0 ; j < PBM[i].length ; j ++) {
                if(PBM[i][j] == 1) {
                    num1 += 1;
                    break;
                }
            }
        }
        for(int i = 0 ; i < PBM[0].length ; i ++) {                           //统计目标本体中匹配实体的数量
            for(int j = 0 ; j < PBM.length ; j ++) {
                if(PBM[j][i] == 1) {
                    num2 += 1;
                    break;
                }
            }
        }
        for(int i = 0 ; i < PBM.length ; i ++) {
            for(int j = 0 ; j < PBM[i].length ; j ++) {
                if(PBM[i][j] == 1) {
                    num3 += 1;                                                //统计匹配对的个数
                    num4 += list.get(1)[i][j];
                }
            }
        }
        recall = (double)(num1 + num2)/(PBM.length + PBM[0].length);
        asvA = (double)num4/num3;
        cardA = (double)(num1 + num2)/(2*num3);
        precision = Math.sqrt(asvA*cardA);
        fmeasure = (2*recall*precision)/(recall + precision);
        if(recall == 0 || precision == 0) {
            fmeasure = 0;
        }
        DT.get(0).setFitness(fmeasure);
        return fmeasure;
    }
    public static double  fitnessOfDT2 (TreeNode dt, double[][] SMOA, double[][] Wu, double[][] Ngram, double[][] Resnik, double[][] Instance, double[][] SimilarityFlood, ArrayList<String> SourceOntology, ArrayList<String> TargetOntology, List<String> Reference) {
        ArrayList<double[][]> list = Decoding.DTofPBMandMatrix(dt,SMOA,Wu,Ngram,Resnik,Instance,SimilarityFlood);
        double[][] PBM = list.get(0);
        List<String> finalMatchingResult = new ArrayList<>();
        double fmeasure = 0;    //f-measure
        for(int i = 0 ; i < PBM.length ; i ++) {
            for(int j = 0 ; j < PBM[i].length ; j ++) {
                if(PBM[i][j] == 1) {
                    finalMatchingResult.add(SourceOntology.get(i) + "---" + TargetOntology.get(j));
                }
            }
        }
        fmeasure = readWuMatrix.valueToPar(Reference,finalMatchingResult)[2];
        return fmeasure;
    }
    //评估一个EI个体
    public static double[] FitnessOfEI(TreeNode1 ei, String[] function, ArrayList<double[][]> elitePBMSet, ArrayList<double[][]> simFValueMatrixSet) {
        double[] a = new double[3];
        ArrayList<TreeNode1> EI = macro_Crossover.treetolist1(ei);
        double[][] PBM = Decoding1.decoding1(EI,function);
        int num1 = 0;                                                        //存放源本体中匹配实体的数量
        int num2 = 0;                                                        //存放目标本体中匹配实体的数量
        int num3 = 0;                                                        //存放匹配对的个数
        int num4 = 0;                                                        //存放SimFValue之和
        double recall = 0;                                                   //近似查全
        double precision = 0;                                                 //近似查准
        double fmeasure = 0;                                                 //近似f-measure
        double asvA = 0;
        double cardA = 0;
        for(int i = 0 ; i < PBM.length ; i ++) {                              //统计源本体中匹配实体的数量
            for(int j = 0 ; j < PBM[i].length ; j ++) {
                if(PBM[i][j] == 1) {
                    num1 += 1;
                    break;
                }
            }
        }
        for(int i = 0 ; i < PBM[0].length ; i ++) {                           //统计目标本体中匹配实体的数量
            for(int j = 0 ; j < PBM.length ; j ++) {
                if(PBM[j][i] == 1) {
                    num2 += 1;
                    break;
                }
            }
        }
        ArrayList<double[][]> list = new ArrayList<>();
        for(int i = 0 ; i < EI.size() ; i ++) {
            TreeNode1 node = EI.get(i);
            if(node.getFlag() == 1) {
                double[][] matrix = node.getMatrix();
                for (int j = 0; j < elitePBMSet.size(); j++) {
                    if(Copy.arrayEquals(elitePBMSet.get(j),matrix)) {
                        list.add(simFValueMatrixSet.get(j));
                    }
                }
            }
        }
        for(int i = 0 ; i < PBM.length ; i ++) {
            for(int j = 0 ; j < PBM[i].length ; j ++) {
                if(PBM[i][j] == 1) {
                    num3 += 1;//统计匹配对的个数
                    double max = 0;
                    for(int k = 0 ; k < list.size() ; k ++) {
                        if(list.get(k)[i][j] > max) {
                            max = list.get(k)[i][j];
                        }
                    }
                    num4 += max;
                }
            }
        }
        recall = (double)(num1 + num2)/(PBM.length + PBM[0].length);;
        asvA = (double)num4/num3;
        cardA = (double)(num1 + num2)/(2*num3);
        precision = Math.sqrt(asvA*cardA);
        fmeasure = (2*recall*precision)/(recall + precision);
        if(recall == 0 || precision == 0) {
            fmeasure = 0;
        }
        a[0] = fmeasure;
        a[1] = recall;
        a[2] = precision;
        ei.setFitness(a[0]);
        return a;
    }

}

