package DPHGP;

import java.util.ArrayList;

import static DPHGP.Coding.baoliuliangwei;

public class micro_Mutation {
    //DT个体的微变异
    public static ArrayList<TreeNode> DTMicroMutation(ArrayList<TreeNode> DT,double ratem,String[] measure,String[] function) {
        ArrayList<TreeNode> newDT = new ArrayList<>();
        double a = Math.random();
        if(a < ratem) {
            double b = Math.random();
            if(b > 0.5) {
                while (true) {
                    int s1 = Double.valueOf(Math.random() * DT.size()).intValue();
                    //System.out.println(s1);
                    if (DT.get(s1).getFlag() == 0) {
                        DT.get(s1).setSimilarityMeasure(measure[Double.valueOf(Math.random() * measure.length).intValue()]);
                        DT.get(s1).setFunction(function[Double.valueOf(Math.random() * function.length).intValue()]);
                        DT.get(s1).setConstants(baoliuliangwei(Math.random()));
                        newDT = DT;
                        break;
                    }
                }
            }else {
                newDT = DT;
            }
        }else {
            newDT = DT;
        }
        return newDT;
    }
}

