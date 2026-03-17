package DPHGP;

import java.util.ArrayList;

public class Coding1 {
    public static ArrayList<TreeNode1> Population2(int groupsize,String[] function,double probt,ArrayList<double[][]> elitePBMSet) {   //生成一群集成个体
        @SuppressWarnings("unchecked")
        ArrayList<TreeNode1> list = new ArrayList<>();
        while(list.size() < groupsize) {
            TreeNode1 ei = createEnsembleIndividual(function,probt,elitePBMSet);
            /*if(Decoding.baohan1(ei,list) == 0) {
                list.add(ei);
            }*/
            list.add(ei);
        }
        return list;
    }
    /**
     * @param function 函数集合（求和、乘积）
     * @return EI     集成个体
     */
    public static TreeNode1 createEnsembleIndividual(String[] function,double probt,ArrayList<double[][]> elitePBMSet) {         //生成一棵树
        ArrayList<TreeNode1> EI = new ArrayList<>();                                   //创建一个集合用于存放一颗树的节点
        TreeNode1 root = new TreeNode1();
        root.setFunction(function[Double.valueOf(Math.random()*function.length).intValue()]);                                               //设置根节点的阈值函数
        root.setDeep(0);                                                              //设置根节点的深度，由于是根节点，所以深度为0
        root.setFlag(0);                                                              //设置叶子节点标志位,等于1为叶子节点
        root.setSerial(0);                                                            //表示树中的第几个元素
        EI.add(root);                                                                 //将根节点添加到DT集合中
        int serial = 1;
        for(int i = 0 ; i < EI.size() ; i ++) {                                       //遍历存储树节点的集合
            TreeNode1 node = EI.get(i);                                                //取出集合中的第i个节点
            while(node.getFunction() != null) {                                       //判断树中第i个节点是否设置了function属性,如果有则证明该节点不是叶子节点，可以增长左右子树
                double a = Math.random();
                if(EI.size() > 4) {                //限制树的层数为3层
                    a = 0.1;
                }
                if(EI.size() < 3) {
                    a = 0.6;
                }
                if(a > probt) {                                                         //生成内部节点
                    int deep = node.getDeep();
                    TreeNode1 left = new TreeNode1();
                    left.setFunction(function[Double.valueOf(Math.random()*function.length).intValue()]);
                    left.setDeep(deep + 1);
                    left.setFlag(0);
                    left.setSerial(serial);
                    serial += 1;
                    node.setLeftTree(left);
                    left.setParent(node);
                    EI.add(left);

                    TreeNode1 right = new TreeNode1();
                    right.setFunction(function[Double.valueOf(Math.random()*function.length).intValue()]);
                    right.setDeep(deep + 1);
                    right.setFlag(0);
                    right.setSerial(serial);
                    serial += 1;
                    node.setRightTree(right);
                    right.setParent(node);
                    EI.add(right);
                }else {                                                                 //生成叶子节点
                    TreeNode1 left = new TreeNode1();
                    int m = node.getDeep();
                    left.setMatrix(elitePBMSet.get(Double.valueOf(Math.random()*elitePBMSet.size()).intValue()));
                    left.setDeep(m+1);
                    left.setFlag(1);           //设置为叶子节点
                    left.setSerial(serial);
                    serial++;
                    node.setLeftTree(left);
                    left.setParent(node);
                    EI.add(left);

                    TreeNode1 right = new TreeNode1();
                    right.setMatrix(elitePBMSet.get(Double.valueOf(Math.random()*elitePBMSet.size()).intValue()));
                    right.setDeep(m+1);
                    right.setFlag(1);           //设置为叶子节点
                    right.setSerial(serial);
                    serial++;
                    node.setRightTree(right);
                    right.setParent(node);
                    EI.add(right);
                }
                i++;
                node = EI.get(i);
            }
        }
        return EI.get(0);
    }
}
