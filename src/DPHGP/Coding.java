package DPHGP;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Coding {
    public static ArrayList<TreeNode> Population1(int groupsize, String[] measure,String[] function,String[] function1,double probt) {   //生成一群树
        @SuppressWarnings("unchecked")
        ArrayList<TreeNode> list = new ArrayList<>();
        for(int i = 0 ; i < groupsize ; i++) {
            TreeNode DT = createSingleTree(measure,function,function1,probt);
            list.add(DT);
        }
        return list;
    }
    /**
     *
     * @param measure 相似度量方法的集合
     * @param function 阈值函数集合
     * @param function1 叶子节点输出函数集合
     * @param  probt    叶子节点生成的概率
     * @return DT     一棵树
     */
    public static TreeNode createSingleTree(String[] measure,String[] function,String[] function1,double probt) {         //生成一棵树
        ArrayList<TreeNode> DT = new ArrayList<>();                                   //创建一个集合用于存放一颗树的节点
        int s1 = Double.valueOf(Math.random()*function.length).intValue();         //产生一个随机数，在[0,2]区间
        TreeNode root = new TreeNode();
        root.setFunction(function[s1]);                                               //设置根节点的阈值函数
        root.setSimilarityMeasure(measure[Double.valueOf(Math.random()*measure.length).intValue()]);   //设置根节点阈值函数输入的相似度量方法

        root.setConstants(baoliuliangwei(Math.random()));                                             //设置阈值函数参数
        root.setDeep(0);                                                              //设置根节点的深度，由于是根节点，所以深度为0
        root.setFlag(0);                                                              //设置叶子节点标志位,等于1为叶子节点
        root.setSerial(0);                                                            //表示树中的第几个元素
        root.setSubTree("根节点");
        DT.add(root);                                                                 //将根节点添加到DT集合中
        int serial = 1;
        for(int i = 0 ; i < DT.size() ; i ++) {                                       //遍历存储树节点的集合
            TreeNode node = DT.get(i);                                                //取出集合中的第i个节点
            while(node.getFunction() != null) {                                       //判断树中第i个节点是否设置了function属性,如果有则证明该节点不是叶子节点，可以增长左右子树
                ArrayList<TreeNode> information = new ArrayList<>();
                double a = Math.random();
                if(DT.size() > 10) {                //限制树的层数
                    a = 0.1;
                }
                if(DT.size() < 3) {
                    a = 0.8;
                }
                if(a > probt) {                                                         //生成内部节点
                    int deep = node.getDeep();
                    TreeNode left = new TreeNode();
                    left.setFunction(function[Double.valueOf(Math.random()*function.length).intValue()]);
                    left.setSimilarityMeasure(measure[Double.valueOf(Math.random()*measure.length).intValue()]);
                    left.setConstants(baoliuliangwei(Math.random()));                                             //设置阈值函数参数
                    left.setDeep(deep + 1);
                    left.setFlag(0);
                    left.setSerial(serial);
                    serial += 1;
                    left.setSubTree("左子树");
                    left.setParent(node);
                    node.setLeftTree(left);
                    information.add(left);
                    DT.add(left);

                    TreeNode right = new TreeNode();
                    right.setFunction(function[Double.valueOf(Math.random()*function.length).intValue()]);
                    right.setSimilarityMeasure(measure[Double.valueOf(Math.random()*measure.length).intValue()]);
                    right.setConstants(baoliuliangwei(Math.random()));                                             //设置阈值函数参数
                    right.setDeep(deep + 1);
                    right.setFlag(0);
                    right.setSerial(serial);
                    serial += 1;
                    right.setSubTree("右子树");
                    right.setParent(node);
                    node.setRightTree(right);
                    information.add(right);
                    DT.add(right);
                    node.setSubInformation(information);
                }else {                                                                 //生成叶子节点
                    TreeNode left = new TreeNode();
                    int m = node.getDeep();
                    left.setFunction1(function1[0]);
                    left.setDeep(m+1);
                    left.setFlag(1);           //设置为叶子节点
                    left.setSerial(serial);
                    serial++;
                    left.setParent(node);
                    left.setSubTree("左子树");
                    node.setLeftTree(left);
                    information.add(left);
                    DT.add(left);

                    TreeNode right = new TreeNode();
                    right.setFunction1(function1[1]);
                    right.setDeep(m+1);
                    right.setFlag(1);           //设置为叶子节点
                    right.setSerial(serial);
                    serial++;
                    right.setParent(node);
                    right.setSubTree("右子树");
                    node.setRightTree(right);
                    information.add(right);
                    DT.add(right);
                    node.setSubInformation(information);
                }
                i++;
                node = DT.get(i);
            }
        }
        return DT.get(0);
    }
    public static double baoliuliangwei(double a) {                    //将输入的double类型数据保留两位小数
        DecimalFormat df = new DecimalFormat("#.##");
        double result = Double.parseDouble(df.format(a));
        return result;
    }
}



