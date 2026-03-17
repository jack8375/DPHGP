package DPHGP;

import java.util.ArrayList;
import java.util.Collections;

import static org.apache.jena.atlas.lib.RandomLib.random;

public class AdaptiveCrossoverAndMutation {
    public static ArrayList<TreeNode> DTCrossoverAndMutation(ArrayList<TreeNode> Pdt,ArrayList<TreeNode> EliteDTset,double ratec,double ratem,double rates,
                                                             String[] measure,String[] function,String[] function1,double probt,double[] fmeasureSet) {

        ArrayList<TreeNode>[] newPdt = new ArrayList[Pdt.size()];                                               //用于存储交叉变异产生的新个体
        ArrayList<TreeNode> a = new ArrayList<>();                                   //用于存储轮盘赌选择出的个体
        ArrayList<TreeNode> list1 = new ArrayList<>();                         //list1和list2用于存储将a平均分成的两组个体
        ArrayList<TreeNode> list2 = new ArrayList<>();
        ArrayList<Integer> list3 = new ArrayList<>();
        ArrayList<TreeNode> parent1 = new ArrayList<>();
        ArrayList<TreeNode> parent2 = new ArrayList<>();
        ArrayList<TreeNode> copyparent1 = new ArrayList<>();
        ArrayList<TreeNode> copyparent2 = new ArrayList<>();

        for(int i = 0 ; i < Pdt.size() ;i = i + 2 ) {
            ArrayList<TreeNode> CopyPdt = Copy.CopyDTPopulation(Pdt);  //为轮盘赌做准备
            ArrayList<Double> copyfmeasureSet = new ArrayList<>();
            for(int k = 0 ; k < fmeasureSet.length ; k++) {
                copyfmeasureSet.add(fmeasureSet[k]);
            }
            ArrayList<ArrayList<TreeNode>> list = new ArrayList<>();
            a = RouletteWheelSelection(Pdt,CopyPdt,copyfmeasureSet,rates);
            list3.clear();
            for(int j = 0 ; j < a.size() ; j ++) {
                list3.add(j);
            }
            Collections.shuffle(list3);
            list1.clear();
            list2.clear();
            for(int j = 0 ; j < a.size() ; j ++) {
                if(list1.size() < a.size()/2) {
                    list1.add(a.get(list3.get(j)));
                }else {
                    list2.add(a.get(list3.get(j)));
                }
            }
            TreeNode Rparent1 = selectOneDT(list1);
            TreeNode Rparent2 = selectOneDT(list2);
            parent1 = macro_Crossover.treetolist(Rparent1);
            parent2 = macro_Crossover.treetolist(Rparent2);
            /*for(int k = 0 ; k < parent1.size() ; k ++) {
                System.out.println(TreeNode.toString(parent1.get(k)));
            }
            for(int h = 0 ; h < parent2.size() ; h ++) {
                System.out.println(TreeNode.toString(parent2.get(h)));
            }*/
            copyparent1 = Copy.CopyDT(parent1);
            copyparent2 = Copy.CopyDT(parent2);
           if(judge(Rparent1,EliteDTset) == 1 || judge(Rparent2,EliteDTset) == 1) {
                //System.out.println("微交叉");
                list = micro_Crossover.DTmicro_Crossover(copyparent1,copyparent2,ratec,0);
            }else {
                //System.out.println("宏交叉");
                list = macro_Crossover.DTmacro_Crossover(copyparent1,copyparent2,ratec);
            }
            //list  = macro_Crossover.DTmacro_Crossover(copyparent1,copyparent2,ratec);
            newPdt[i] = list.get(0);
            newPdt[i+1] = list.get(1);
        }
        for(int i = 0 ; i < newPdt.length ; i++) {
            if(judge(newPdt[i].get(0),EliteDTset) == 1) {
                //System.out.println("微变异");
                newPdt[i] = micro_Mutation.DTMicroMutation(newPdt[i],ratem,measure,function);
                //System.out.println(newPdt[i].size());
            }else {
                //System.out.println("宏变异");
                newPdt[i] = macro_Mutation.DTMacroMutation(newPdt[i],ratem,ratec,measure,function,function1,probt);
                //System.out.println(newPdt[i].size());
            }
            //newPdt[i] = macro_Mutation.DTMacroMutation(newPdt[i],ratem,ratec,measure,function,function1,probt);
        }
        list1.clear();
        for(int i = 0 ; i < newPdt.length ; i++) {
            list1.add(newPdt[i].get(0));
        }
        return list1;
    }

    //使用轮盘赌的方法从DT种群中选择出一定数量的个体
    public static ArrayList<TreeNode> RouletteWheelSelection(ArrayList<TreeNode> soursePdt,ArrayList<TreeNode> Pdt,ArrayList<Double> fmeasureSet,double rates) {
        ArrayList<TreeNode> selectedIndividuals = new ArrayList<>();
        int size = (int)(soursePdt.size()*rates);
        for(int k = 0 ; k < size ; k ++) {
            double totalFmeasure = 0;
            for (int j = 0; j < fmeasureSet.size(); j++) {
                totalFmeasure += fmeasureSet.get(j);
            }
            double r = random.nextDouble() * totalFmeasure; // 生成一个[0, totalFitness)之间的随机数
            double partialFmeasure = 0.0;
            for (int j = 0; j < fmeasureSet.size(); j++) {
                partialFmeasure += fmeasureSet.get(j);
                if (partialFmeasure >= r) {
                    selectedIndividuals.add(Pdt.get(j));
                    Pdt.remove(j);
                    fmeasureSet.remove(j);
                    break;
                }
            }
        }
        return selectedIndividuals;
    }
    //从一组树里面选择出f-measure值最高的个体
    public static TreeNode selectOneDT(ArrayList<TreeNode> list) {
        TreeNode dt = new TreeNode();
        double max = 0;
        for(int i = 0 ;i < list.size() ; i ++) {
            if(list.get(i).getFitness() > max) {
                max = list.get(i).getFitness();
            }
        }
        for(int j = 0 ;j < list.size() ; j ++) {
            if(list.get(j).getFitness() == max) {
                dt = list.get(j);
                break;
            }
        }
        return dt;
    }
    public static int judge(TreeNode DT,ArrayList<TreeNode> EliteDTset) {   //a=1时，DT属于精英树
        int a = 0;
        for(int i = 0 ; i < EliteDTset.size() ;i ++) {
            if(Copy.DTisEqual(DT,EliteDTset.get(i)) == 0) {
                a = 1;
                break;
            }
        }
        return a;
    }
    //EI个体的交叉变异(未测试)
    public static ArrayList<TreeNode1> EICrossoverAndMutation(ArrayList<TreeNode1> Pen,TreeNode1 enElite,ArrayList<double[][]> elitePBMSet,String[] function,double probt,
                                                              double ratec,double ratem,double rates,double[] fmeasureSet) {

        ArrayList<TreeNode1>[] newPen = new ArrayList[Pen.size()];                                               //用于存储交叉产生的新个体
        ArrayList<TreeNode1> a = new ArrayList<>();                                    //用于存储轮盘赌选择出的个体
        ArrayList<TreeNode1> list1 = new ArrayList<>();                         //list1和list2用于存储将a平均分成的两组个体
        ArrayList<TreeNode1> list2 = new ArrayList<>();
        ArrayList<Integer> list3 = new ArrayList<>();
        ArrayList<TreeNode1> parent1 = new ArrayList<>();
        ArrayList<TreeNode1> parent2 = new ArrayList<>();
        ArrayList<TreeNode1> copyparent1 = new ArrayList<>();
        ArrayList<TreeNode1> copyparent2 = new ArrayList<>();
        for(int i = 0 ; i < Pen.size() ;i = i + 2 ) {
            ArrayList<ArrayList<TreeNode1>> list = new ArrayList<>();
            a = RouletteWheelSelection1(Pen,fmeasureSet,rates);
            list3.clear();
            for(int j = 0 ; j < a.size() ; j ++) {
                list3.add(j);
            }
            Collections.shuffle(list3);
            list1.clear();
            list2.clear();
            for(int j = 0 ; j < a.size() ; j ++) {
                if(list1.size() < a.size()/2) {
                    list1.add(a.get(list3.get(j)));
                }else {
                    list2.add(a.get(list3.get(j)));
                }
            }
            TreeNode1 Rparent1 = selectOneEI(list1);
            TreeNode1 Rparent2 = selectOneEI(list2);
            parent1 = macro_Crossover.treetolist1(Rparent1);
            parent2 = macro_Crossover.treetolist1(Rparent2);
            /*for(int k = 0 ; k < parent1.size() ; k ++) {
                System.out.println(TreeNode1.toString(parent1.get(k)));
            }
            for(int h = 0 ; h < parent2.size() ; h ++) {
                System.out.println(TreeNode1.toString(parent2.get(h)));
            }*/
            copyparent1 = Copy.CopyEI(parent1);                         //此处将叶子节点的输入PBM复制过来了
            copyparent2 = Copy.CopyEI(parent2);
            if(Copy.EIisEqual(Rparent1,enElite) == 0 || Copy.EIisEqual(Rparent2,enElite) == 0) {
                //System.out.println("微交叉");
                list = micro_Crossover.EImicro_Crossover(copyparent1,copyparent2,ratec,0);
            }else {
                //System.out.println("宏交叉");
                list = macro_Crossover.EImacro_Crossover(copyparent1,copyparent2,ratec);
            }
            //list = macro_Crossover.EImacro_Crossover(copyparent1,copyparent2,ratec);
            newPen[i] = list.get(0);
            newPen[i+1] = list.get(1);
        }
        for(int i = 0 ; i < newPen.length ; i++) {
            //System.out.println("宏变异");
            newPen[i] = macro_Mutation.EIMacroMutation(newPen[i],ratem,ratec,function,probt,elitePBMSet);
            //System.out.println(newPen[i].size());
        }
        list1.clear();
        for(int i = 0 ; i < newPen.length ; i++) {
            list1.add(newPen[i].get(0));
        }
        return list1;
    }
    //使用轮盘赌的方法从EI种群中选择出一定数量的个体
    public static ArrayList<TreeNode1> RouletteWheelSelection1(ArrayList<TreeNode1> Pen,double[] fmeasureSet,double rates) {
        ArrayList<TreeNode1> selectedIndividuals = new ArrayList<>();
        int size = (int)(Pen.size()*rates);
        double totalFmeasure = 0;
        for (int j = 0; j < fmeasureSet.length; j++) {
            totalFmeasure += fmeasureSet[j];
        }
        for (int i = 0; i < size; i++) {
            double r = random.nextDouble() * totalFmeasure; // 生成一个[0, totalFitness)之间的随机数
            double partialFmeasure = 0.0;
            for(int j = 0 ; j < fmeasureSet.length ; j++) {
                partialFmeasure += fmeasureSet[j];
                if(partialFmeasure >= r) {
                    selectedIndividuals.add(Pen.get(j));
                    break;
                }
            }
        }
        return selectedIndividuals;
    }
    public static TreeNode1 selectOneEI(ArrayList<TreeNode1> list) {
        TreeNode1 ei = new TreeNode1();
        double max = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getFitness() > max) {
                max = list.get(i).getFitness();
            }
        }
        for (int j = 0; j < list.size(); j++) {
            if (list.get(j).getFitness() == max) {
                ei = list.get(j);
                break;
            }
        }
        return ei;
    }
}


