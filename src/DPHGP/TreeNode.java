package DPHGP;

import java.util.ArrayList;

public class TreeNode {
    private String function;          //定义内部节点和根节点的阈值函数
    private String function1;          //定义叶子节点的输出函数
    private String similaritymeasure; //定义相似度量方法
    private double constants;         //阈值函数的参数
    private TreeNode parent;          //定义父节点属性
    private int deep;                 //定义树的深度
    private int flag;                 //定义叶子节点标志位
    private int serial;                 //集合中的元素序号（一棵树有多个元素，表示第几个元素）
    private double[][] output1;          //设置当前节点输出的PBM
    private double[][] output2;          //设置当前节点输出的NBM
    private String subTree;              //用于设置节点是左子树、右子树、根节点
    private double[][] SimF;             //用于设置当前节点输入的相似度矩阵
    private TreeNode leftTree;
    private TreeNode rightTree;
    private double fitness;
    public void setFitness(double fitness) {
        this.fitness = fitness;
    }
    public double getFitness() {
        return fitness;
    }
    private  ArrayList<TreeNode> subInformation;
    public void setSubInformation(ArrayList<TreeNode> subInformation) {
        this.subInformation = subInformation;
    }
    public ArrayList<TreeNode> getSubInformation() {
        return subInformation;
    }
    public void setLeftTree(TreeNode leftTree) {
        this.leftTree = leftTree;
    }
    public TreeNode getLeftTtree() {
        return leftTree;
    }
    public void setRightTree(TreeNode rightTree) {
        this.rightTree = rightTree;
    }
    public TreeNode getRightTree() {
        return rightTree;
    }
    public void setSimF(double[][] SimF) {this.SimF = SimF;}
    public double[][] getSimF() {return SimF;}
    public void setSubTree(String subTree) {this.subTree = subTree;}
    public String getSubTree() {return subTree;}
    public void setOutPut2(double[][] output2) {this.output2 = output2;}
    public double[][] getOutPut2() {return output2;}
    public void setParent(TreeNode parent) {this.parent = parent;}
    public TreeNode getParent() {return parent;}
    public void setOutPut1(double[][] output1) {this.output1 = output1;}
    public double[][] getOutPut1() {return output1;}

    public void setFunction(String function) {this.function = function;}
    public String getFunction() {return function;}
    public void setFunction1(String function1) {this.function1 = function1;}
    public String getFunction1() {return function1;}
    public void setSimilarityMeasure(String similaritymeasure) {
        this.similaritymeasure = similaritymeasure;
    }
    public String getSimilarityMeasure() {
        return similaritymeasure;
    }
    public void setConstants(double constants) {
        this.constants = constants;
    }
    public double getConstants() {
        return constants;
    }
    public void setDeep(int deep) {
        this.deep = deep;
    }
    public int getDeep() {
        return deep;
    }
    public void setFlag(int flag) {
        this.flag = flag;
    }
    public int getFlag() {
        return flag;
    }
    public void setSerial(int serial) {
        this.serial = serial;
    }
    public int getSerial() {
        return serial;
    }
    public static String toString(TreeNode node) {
        return "[TreeNode =" + node.getSerial() + ",deep=" + node.getDeep() + ",相似度量方法:" + node.getSimilarityMeasure() + ",阈值函数:" + node.getFunction() + ",constant=" + node.getConstants() + ",function1=" + node.getFunction1() + ",标志位=" + node.getFlag() + "]";
    }
}
