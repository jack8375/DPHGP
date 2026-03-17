package DPHGP;

import java.util.ArrayList;

public class macro_Mutation {
    //DT个体的宏变异
    public static ArrayList<TreeNode> DTMacroMutation(ArrayList<TreeNode> DT,double ratem,double ratec,String[] measure,String[] function,String[] function1,double probt) {
        ArrayList<TreeNode> newDT = new ArrayList<>();
        double a = Math.random();
        if(a < ratem) {
            //ArrayList<TreeNode> randomDT = Copy.CopyDT(Pdt[Double.valueOf(Math.random()*Pdt.length).intValue()]);
            ArrayList<TreeNode> randomDT = macro_Crossover.treetolist(Coding.createSingleTree(measure,function,function1,probt));
            ArrayList<ArrayList<TreeNode>> list = macro_Crossover.DTmacro_Crossover(DT,randomDT,ratec);
            newDT = list.get(0);
        }else {
            newDT = DT;
            //System.out.println("没有发生变异");
        }
        return newDT;
    }
    //集成个体的宏变异(未测试)
    public static ArrayList<TreeNode1> EIMacroMutation(ArrayList<TreeNode1> EI,double ratem,double ratec,String[] function,double probt,ArrayList<double[][]> elitePBMSet) {
        ArrayList<TreeNode1> newEI = new ArrayList<>();
        double a = Math.random();
        if(a < ratem) {
            //ArrayList<TreeNode1> randomEI = Copy.CopyEI(Pen[Double.valueOf(Math.random()*Pen.length).intValue()]);;
            ArrayList<TreeNode1> randomEI = macro_Crossover.treetolist1(Coding1.createEnsembleIndividual(function,probt,elitePBMSet));
            ArrayList<ArrayList<TreeNode1>> list = macro_Crossover.EImacro_Crossover(EI,randomEI,ratec);
            newEI = list.get(0);
        }else {
            newEI = EI;
            //System.out.println("没有发生变异");
        }
        return newEI;
    }
}
