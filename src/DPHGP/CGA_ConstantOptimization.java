package DPHGP;

import java.util.ArrayList;
import java.util.Random;

public class CGA_ConstantOptimization {
    /**
     * @param eliteDTset 输入精英DT集合
     * @param a      一个常数格雷码编码的位数
     * @param st     CGA的步长
     * @return       返回更新常数后的精英DT集合
     */
    public static ArrayList<TreeNode> CGAOptimization(ArrayList<ArrayList<TreeNode>> eliteDTset, int a, double st,double[][] SMOA, double[][] Wu, double[][] Ngram, double[][] Resnik, double[][] Instance, double[][] SimilarityFlood) throws Exception {
        ArrayList<TreeNode> s = new ArrayList<>();
        for(int i = 0 ; i < eliteDTset.size(); i++) {
            s.add(DTCGA(eliteDTset.get(i),a,st,SMOA, Wu, Ngram, Resnik, Instance, SimilarityFlood).get(0));
        }
        return s;
    }
    public static ArrayList<TreeNode> DTCGA(ArrayList<TreeNode> eliteDT, int a, double st,
                                            double[][] SMOA, double[][] Wu, double[][] Ngram, double[][] Resnik, double[][] Instance, double[][] SimilarityFlood) throws Exception {
        double[] constant = getConstant(eliteDT);
        double[] PV = new double[constant.length * a];
        for (int h = 0; h < PV.length; h++) {                                                         //将PV向量中的每个元素设置为0.5
            PV[h] = 0.5;
        }
        int[] indElite = CGAcoding.ConstantSetCoding(constant, a);
        int[] indNew;
        double[] constant1;
        double fitness1;
        double fitness2;
        while (Condition(PV) == 1) {                                              //如果PV向量中的每个元素为0或1时，该while循环终止
            indNew = PVIndividual(PV);                                           //由PV向量生成一个新编码个体（该个体与精英DT的常数集合编码个体进行比较）
            constant1 = CGAcoding.Decoding(indNew, a);                            //对PV向量生成的新编码个体进行解码，变成与之对应的常数集合
            fitness1 = eliteDT.get(0).getFitness();                              //计算indElite个体的适应度值
            int t = 0;
            for (int k = 0; k < eliteDT.size(); k++) {                           //重新设置树中每个节点的输入常数（该常数为indNew个体解码后的常数）
                if (eliteDT.get(k).getFlag() == 0) {
                    eliteDT.get(k).setConstants(constant1[t++]);
                }
            }
            fitness2 = Fitness.fitnessOfDT1(eliteDT, SMOA, Wu, Ngram, Resnik, Instance, SimilarityFlood);  //计算indNew个体的适应度值
            if (fitness2 > fitness1) {                                          //如果indNew个体优于indElite个体,则把indNew作为精英个体,同时更新常数集合
                indElite = copy1(indNew);
                constant = copy2(constant1);
            } else {                                                              //如果indElite个体优于indNew个体,则将树中的常数重置为indElite对应的常数集合
                int g = 0;
                for (int h = 0; h < eliteDT.size(); h++) {
                    if (eliteDT.get(h).getFlag() == 0) {
                        eliteDT.get(h).setConstants(constant[g++]);
                    }
                }
                eliteDT.get(0).setFitness(fitness1);
            }
            for(int j = 0 ; j < PV.length ; j ++) {
                if(indElite[j] == 1) {
                    PV[j] = PV[j] + st;
                    if(PV[j] > 1) {
                        PV[j] = 1;
                    }
                }
                if(indElite[j] == 0) {
                    PV[j] = PV[j] - st;
                    if(PV[j] < 0) {
                        PV[j] = 0;
                    }
                }
            }
        }
        return eliteDT;
    }

    public static int Condition(double[] PV) {                  //判断PV向量中的每个元素是否为0或1，如果是,则返回true;反之则返回false
        int flag = 0;
        for(int i = 0 ; i < PV.length ; i ++) {
            if(PV[i] > 0 && PV[i] < 1) {
                flag = 1;
                break;
            }else {
                flag = 0;
            }
        }
        return flag;
    }
    public static int[] PVIndividual(double[] PV) {    //由PV向量生成一个新编码个体
        int[] list = new int[PV.length];
        Random random = new Random();
        for(int i = 0 ; i < PV.length ; i ++) {
            list[i] = random.nextInt(2);
        }
        return list;
    }
    public static int[] copy1(int[] a) {      //用于indElite和indNew之间替换
        int[] b = new int[a.length];
        for(int j = 0 ; j < a.length ; j ++) {
            b[j] = a[j];
        }
        return b;
    }
    public static double[] copy2(double[] a) {      //用于更新常数集合
        double[] b = new double[a.length];
        for(int j = 0 ; j < a.length ; j ++) {
            b[j] = a[j];
        }
        return b;
    }
    public static double[] getConstant(ArrayList<TreeNode> eliteDT) {
        int num = 0;
        for (int j = 0; j < eliteDT.size(); j++) {                                                    //确定一颗精英树的常数的个数
            if (eliteDT.get(j).getFlag() == 0) {
                num = num + 1;
            }
        }
        double[] ConstantSet = new double[num];
        int a = 0;
        for (int j = 0; j < eliteDT.size(); j++) {                                                    //确定一颗精英树的常数集合
            if (eliteDT.get(j).getFlag() == 0) {
                ConstantSet[a] = eliteDT.get(j).getConstants();;
                a++;
            }
            if(a == num) {
                break;
            }
        }
        return ConstantSet;
    }
}



