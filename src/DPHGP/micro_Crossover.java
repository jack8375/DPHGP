package DPHGP;

import Function.ThreeKindsOfFunction;

import java.util.ArrayList;
public class micro_Crossover {
    //DT个体的微交叉
    public static ArrayList<ArrayList<TreeNode>> DTmicro_Crossover(ArrayList<TreeNode> parent1, ArrayList<TreeNode> parent2, double ratec,int b) {
        ArrayList<ArrayList<TreeNode>> list = new ArrayList<>();
        double a = Math.random();
        if(a < ratec || b > 0) {                                                                                  //进行交叉操作
            int s1 = Double.valueOf(Math.random() * parent1.size()).intValue();
            int s2 = Double.valueOf(Math.random() * parent2.size()).intValue();
            //System.out.println(s1);
            //System.out.println(s2);
            TreeNode node1 = parent1.get(s1);
            TreeNode node2 = parent2.get(s2);
            if (node1.getFlag() == 0 && node2.getFlag() == 0) {
                TreeNode node = new TreeNode();                //中间结点
                node.setSimilarityMeasure(node1.getSimilarityMeasure());
                node.setConstants(node1.getConstants());
                node1.setSimilarityMeasure(node2.getSimilarityMeasure());
                node1.setConstants(node2.getConstants());
                list.add(parent1);
                node2.setSimilarityMeasure(node.getSimilarityMeasure());
                node2.setConstants(node.getConstants());
                list.add(parent2);
                node = null;
            } else {
                //System.out.println("请重新选择交叉点！");
                list = DTmicro_Crossover(parent1, parent2, ratec, 1);
            }
        }else {
            list.add(parent1);
            list.add(parent2);
        }
        return list;
    }
    //集成个体的微交叉(待测试)
    public static ArrayList<ArrayList<TreeNode1>> EImicro_Crossover(ArrayList<TreeNode1> parent1, ArrayList<TreeNode1> parent2, double ratec,int b) {
        ArrayList<ArrayList<TreeNode1>> list = new ArrayList<>();
        double a = Math.random();
        if(a < ratec || b > 0) {                                                                                  //进行交叉操作
            int s1 = Double.valueOf(Math.random() * parent1.size()).intValue();
            int s2 = Double.valueOf(Math.random() * parent2.size()).intValue();
            //System.out.println(s1);
            //System.out.println(s2);
            TreeNode1 node1 = parent1.get(s1);
            TreeNode1 node2 = parent2.get(s2);
            if(node1.getFlag() == 0 && node2.getFlag() == 0) {
                TreeNode1 node = new TreeNode1();       //中间结点
                node.setFunction(node1.getFunction());
                node1.setFunction(node2.getFunction());
                list.add(parent1);
                node2.setFunction(node.getFunction());
                list.add(parent2);
                node = null;
            } else if(node1.getFlag() == 1 && node2.getFlag() == 1) {
                double[][] matrix = ThreeKindsOfFunction.copyMatrix(node1.getMatrix());
                node1.setMatrix(node2.getMatrix());
                node2.setMatrix(matrix);
                list.add(parent1);
                list.add(parent2);
                matrix = null;
            } else {
                //System.out.println("请重新选择交叉点！");
                list = EImicro_Crossover(parent1, parent2, ratec, 1);
            }
        }else {
            list.add(parent1);
            list.add(parent2);
        }
        return list;
    }
}
