package DPHGP;

public class TreeNode1 {
    private String function;            //定义内部节点和根节点的阈值函数
    private int deep;                   //定义树的深度
    private int flag;                   //定义叶子节点标志位
    private int serial;                 //集合中的元素序号（一棵树有多个元素，表示第几个元素）
    private TreeNode1 leftTree;         //定义左子树
    private TreeNode1 rightTree;        //定义右子树
    private double[][] Matrix;          //定义叶子节点中存放的PBM的属性
    private TreeNode1 parent;          //定义父节点属性
    private double fitness;
    public void setFitness(double fitness) {
        this.fitness = fitness;
    }
    public double getFitness() {
        return fitness;
    }
    public void setParent(TreeNode1 parent) {this.parent = parent;}
    public TreeNode1 getParent() {return parent;}
    public void setMatrix(double[][] Matrix) {this.Matrix = Matrix;}
    public double[][] getMatrix() {return Matrix;}
    public void setFunction(String function) {this.function = function;}
    public String getFunction() {return function;}
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
    public void setLeftTree(TreeNode1 leftTree) {
        this.leftTree = leftTree;
    }
    public TreeNode1 getLeftTtree() {
        return leftTree;
    }
    public void setRightTree(TreeNode1 rightTree) {
        this.rightTree = rightTree;
    }
    public TreeNode1 getRightTree() {
        return rightTree;
    }
    public static String toString(TreeNode1 node) {
        return "[TreeNode =" + node.getSerial() + ",deep=" + node.getDeep() + ",阈值函数:" + node.getFunction() + ",标志位=" + node.getFlag() + "]";
    }
}
