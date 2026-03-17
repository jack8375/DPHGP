package DPHGP;

import Function.ThreeKindsOfFunction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Copy {
    public static ArrayList<TreeNode> CopyDT(ArrayList<TreeNode> DT) {
        ArrayList<TreeNode> newDT = new ArrayList<TreeNode>();
        for(int i = 0; i < DT.size(); i++) {
            //得到树的各个属性
            String function = DT.get(i).getFunction();
            String function1 = DT.get(i).getFunction1();
            String similarityMeasure = DT.get(i).getSimilarityMeasure();
            double constant = DT.get(i).getConstants();
            int deep = DT.get(i).getDeep();
            int Flag = DT.get(i).getFlag();
            String subtree = DT.get(i).getSubTree();
            double fitness = DT.get(i).getFitness();

            //复制树的各个属性
            TreeNode node = new TreeNode();
            node.setFunction(function);
            node.setFunction1(function1);
            node.setSimilarityMeasure(similarityMeasure);
            node.setConstants(constant);
            node.setDeep(deep);
            node.setFlag(Flag);
            node.setSubTree(subtree);
            node.setSerial(i);
            node.setFitness(fitness);
            newDT.add(node);
        }
        int[] layer = new int[newDT.get(newDT.size() - 1).getDeep() + 1];  //定义一个一维数组，用来存放每一层有几个
        for(int i = 0; i < newDT.size(); i++) {
            int deep = newDT.get(i).getDeep();
            layer[deep]++;
        }
        int l = 0;
        int n = 0;
        int m = 0;        //每一层开始的序号
        for(int i = 0; i < layer.length; i++) {	  //设置左右支树
            l = m;
            n = m;
            for(int j = 0; j < layer[i]; j++) {
                if(newDT.get(n).getFlag() == 0) {
                    newDT.get(n).setLeftTree(newDT.get(l + layer[i]));
                    newDT.get(l + layer[i]).setParent(newDT.get(n));
                    newDT.get(n).setRightTree(newDT.get(l + layer[i] + 1));
                    newDT.get(l + layer[i] + 1).setParent(newDT.get(n));
                    ArrayList<TreeNode> information = new ArrayList<>();
                    information.add(newDT.get(n).getLeftTtree());
                    information.add(newDT.get(n).getRightTree());
                    newDT.get(n).setSubInformation(information);
                    l += 2;
                }
                n++;
            }
            m = m + layer[i];
        }
        return newDT;
    }
    public static ArrayList<TreeNode1> CopyEI(ArrayList<TreeNode1> EI) {
        ArrayList<TreeNode1> newEI = new ArrayList<TreeNode1>();
        for(int i = 0; i < EI.size(); i++) {
            //得到树的各个属性
            String function = EI.get(i).getFunction();
            int deep = EI.get(i).getDeep();
            int Flag = EI.get(i).getFlag();
            if(Flag == 1) {
                //复制树的各个属性
                if(EI.get(i).getMatrix() != null) {
                    double[][] Matrix = ThreeKindsOfFunction.copyMatrix(EI.get(i).getMatrix());//
                    TreeNode1 node = new TreeNode1();
                    node.setFunction(function);
                    node.setDeep(deep);
                    node.setFlag(Flag);
                    node.setSerial(i);
                    newEI.add(node);
                    node.setMatrix(Matrix);//
                }else {
                    TreeNode1 node = new TreeNode1();
                    node.setFunction(function);
                    node.setDeep(deep);
                    node.setFlag(Flag);
                    node.setSerial(i);
                    newEI.add(node);
                }
            }else {
                //复制树的各个属性
                double fitness = EI.get(i).getFitness();
                TreeNode1 node = new TreeNode1();
                node.setFunction(function);
                node.setDeep(deep);
                node.setFlag(Flag);
                node.setSerial(i);
                node.setFitness(fitness);
                newEI.add(node);
            }
        }
        int[] layer = new int[newEI.get(EI.size() - 1).getDeep() + 1];  //定义一个一维数组，用来存放每一层有几个
        for(int i = 0; i < EI.size(); i++) {
            int deep = newEI.get(i).getDeep();
            layer[deep]++;
        }
        int l = 0;
        int n = 0;
        int m = 0;        //每一层开始的序号
        for(int i = 0; i < layer.length; i++) {	  //设置左右支树
            l = m;
            n = m;
            for(int j = 0; j < layer[i]; j++) {
                if(newEI.get(n).getFlag() == 0) {
                    newEI.get(n).setLeftTree(newEI.get(l + layer[i]));
                    newEI.get(l + layer[i]).setParent(newEI.get(n));
                    newEI.get(n).setRightTree(newEI.get(l + layer[i] + 1));
                    newEI.get(l + layer[i] + 1).setParent(newEI.get(n));
                    l += 2;
                }
                n++;
            }
            m = m + layer[i];
        }
        return newEI;
    }
    //判断两个EI个体是否相同
    public static int EIisEqual(TreeNode1 DT1,TreeNode1 DT2) {                                 //#
        ArrayList<TreeNode1> list1 = macro_Crossover.treetolist1(DT1);
        ArrayList<TreeNode1> list2 = macro_Crossover.treetolist1(DT2);
        int a = 0;
        if(list1.size() != list2.size()) {
            a = 1;
        }else {
            for(int i = 0 ; i < list1.size() ; i ++) {
                if(!Objects.equals(list1.get(i).getFunction(), list2.get(i).getFunction())) {
                    a = 1;
                    break;
                }
                if(list1.get(i).getDeep() != list2.get(i).getDeep()) {
                    a = 1;
                    break;
                }
                if(list1.get(i).getFlag() != list2.get(i).getFlag()) {
                    a = 1;
                    break;
                }
                if(list1.get(i).getFitness() != list2.get(i).getFitness()) {
                    a = 1;
                    break;
                }
                if(list1.get(i).getFlag() == 1 && list2.get(i).getFlag() == 1) {
                    if (!arrayEquals(list1.get(i).getMatrix(), list2.get(i).getMatrix())) {
                        a = 1;
                        break;
                    }
                }
            }
        }
        return a;
    }
    public static boolean arrayEquals(double[][] array1,double[][] array2){
        for (int i = 0; i < array1.length; i++) {  //二维数组中的每个数组使用equals方法比较
            if (!Arrays.equals(array1[i], array2[i]))
                return false;  //有一个为false，返回false
        }
        return true;
    }
    public static int DTisEqual(TreeNode DT1,TreeNode DT2) {                                 //#
        ArrayList<TreeNode> list1 = macro_Crossover.treetolist(DT1);
        ArrayList<TreeNode> list2 = macro_Crossover.treetolist(DT2);
        int a = 0;
        if(list1.size() != list2.size()) {
            a = 1;
        }else {
            for(int i = 0 ; i < list1.size() ; i ++) {
                if(!Objects.equals(list1.get(i).getFunction(), list2.get(i).getFunction())) {
                    a = 1;
                    break;
                }
                if(!Objects.equals(list1.get(i).getFunction1(), list2.get(i).getFunction1())) {
                    a = 1;
                    break;
                }
                if(!Objects.equals(list1.get(i).getSimilarityMeasure(), list2.get(i).getSimilarityMeasure())) {
                    a = 1;
                    break;
                }
                if(list1.get(i).getConstants() != list2.get(i).getConstants()) {
                    a = 1;
                    break;
                }
                if(list1.get(i).getDeep() != list2.get(i).getDeep()) {
                    a = 1;
                    break;
                }
                if(list1.get(i).getFlag() != list2.get(i).getFlag()) {
                    a = 1;
                    break;
                }
            }
        }
        return a;
    }
    //判断newEliteDT和EliteDT是否相同,相同返回true,不同则返回false
    public static int areEqual(ArrayList<TreeNode> list1, ArrayList<TreeNode> list2) {  //a=1时说明newEliteDT和EliteDT相同
        int a = 0;
        ArrayList<TreeNode> list3 = CopyDTPopulation(list2);
        for(int i = 0 ;i < list1.size() ;i ++) {
            for(int j = 0 ;j < list3.size() ;j ++) {
                if(DTisEqual(list1.get(i),list3.get(j)) == 0) {
                    list3.remove(j);
                    break;
                }
            }
        }
        if(list3.isEmpty()) {
            a = 1;
        }
        return a;
    }
    public static ArrayList<TreeNode1> CopyEIPopulation(ArrayList<TreeNode1> Pen) {
        ArrayList<TreeNode1> newPen = new ArrayList<>();
        for(int i = 0 ;i < Pen.size() ;i ++) {
            newPen.add(Copy.CopyEI(macro_Crossover.treetolist1(Pen.get(i))).get(0));
        }
        return newPen;
    }
    public static ArrayList<TreeNode> CopyDTPopulation(ArrayList<TreeNode> Pdt) {
        ArrayList<TreeNode> newPdt = new ArrayList<>();
        for(int i = 0 ;i < Pdt.size() ;i ++) {
            newPdt.add(Copy.CopyDT(macro_Crossover.treetolist(Pdt.get(i))).get(0));
        }
        return newPdt;
    }
    public static ArrayList<double[][]> CopyMatrixSet(ArrayList<double[][]> a) {
        ArrayList<double[][]> b = new ArrayList<>();
        for(int i = 0 ;i < a.size() ;i ++) {
            b.add(ThreeKindsOfFunction.copyMatrix(a.get(i)));
        }
        return b;
    }
    public static TreeNode copyDTNode(TreeNode node) {
        TreeNode newnode = new TreeNode();
        newnode.setConstants(node.getConstants());
        newnode.setFunction(node.getFunction());
        newnode.setSimilarityMeasure(node.getSimilarityMeasure());
        newnode.setSubTree(node.getSubTree());
        newnode.setLeftTree(node.getLeftTtree());
        newnode.setRightTree(node.getRightTree());
        newnode.getLeftTtree().setParent(newnode);
        newnode.getRightTree().setParent(newnode);
        newnode.setFunction1(node.getFunction1());
        newnode.setFlag(node.getFlag());
        newnode.setSubInformation(node.getSubInformation());
        newnode.getSubInformation().set(0,newnode.getLeftTtree());
        newnode.getSubInformation().set(1,newnode.getRightTree());
        return newnode;
    }
    public static TreeNode1 copyEINode(TreeNode1 node) {
        TreeNode1 newnode = new TreeNode1();
        newnode.setFunction(node.getFunction());
        newnode.setLeftTree(node.getLeftTtree());
        newnode.setRightTree(node.getRightTree());
        newnode.getLeftTtree().setParent(newnode);
        newnode.getRightTree().setParent(newnode);
        newnode.setFlag(node.getFlag());
        return newnode;
    }
    public static double[] Copyshuzu(double[] a) {
        double[] b = new double[a.length];
        for(int i = 0 ;i < a.length ;i ++) {
            b[i] = a[i];
        }
        return b;
    }
}
