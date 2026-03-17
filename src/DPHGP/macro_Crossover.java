package DPHGP;

import java.util.ArrayList;
import java.util.Random;

public class macro_Crossover {
    //DT个体的宏交叉
    public static ArrayList<ArrayList<TreeNode>> DTmacro_Crossover(ArrayList<TreeNode> parent1,ArrayList<TreeNode> parent2,double ratec) {  //一般交叉操作时,b = 0;当交叉节点的选择不合理时，b = 1;
        ArrayList<ArrayList<TreeNode>> list = new ArrayList<>();
        ArrayList<Integer> num1 = new ArrayList<>();
        ArrayList<Integer> num2 = new ArrayList<>();
        for(int i = 0 ; i < parent1.size() ; i ++) {
            if(parent1.get(i).getFlag() == 0) {
                num1.add(i);
            }
        }
        for(int i = 0 ; i < parent2.size() ; i ++) {
            if(parent2.get(i).getFlag() == 0) {
                num2.add(i);
            }
        }
        double a = Math.random();
        Random rand = new Random();
        if(a < ratec) {//进行交叉操作
            int s1;
            int s2;
            while(true) {
                s1 = num1.get(rand.nextInt(num1.size()));
                s2 = num2.get(rand.nextInt(num2.size()));
                if(parent1.get(s1).getDeep() == parent2.get(s2).getDeep()) {
                    break;
                }
            }
            //System.out.println(s1);
            //System.out.println(s2);
            TreeNode node1 = parent1.get(s1);
            TreeNode node2 = parent2.get(s2);
            if (s1 == 0 && s2 == 0) {
                list.add(parent2);
                list.add(parent1);
            }else if(s1 != 0 && s2 != 0) {
                TreeNode copynode1 = Copy.copyDTNode(node1);
                copynode1.setDeep(node2.getDeep());
                copynode1.setParent(node2.getParent());
                node2.setSubTree(node1.getSubTree());
                node2.setFlag(node1.getFlag());
                node2.setDeep(node1.getDeep());
                node2.setParent(node1.getParent());
                if(s1 % 2 == 0) {
                    node2.getParent().setRightTree(node2);
                    node2.getParent().getSubInformation().set(1,node2);
                }
                if(s1 % 2 == 1) {
                    node2.getParent().setLeftTree(node2);
                    node2.getParent().getSubInformation().set(0,node2);
                }
                if(s2 % 2 == 0) {
                    copynode1.getParent().setRightTree(copynode1);
                    copynode1.getParent().getSubInformation().set(1,copynode1);
                }
                if(s2 % 2 == 1) {
                    copynode1.getParent().setLeftTree(copynode1);
                    copynode1.getParent().getSubInformation().set(0,copynode1);
                }
                parent1 = treetolist(parent1.get(0));
                parent2 = treetolist(parent2.get(0));
                list.add(parent1);
                list.add(parent2);
                node1 = null;
            }else if(s1 == 0 && s2 != 0) {
                node1.setSubTree(node2.getSubTree());
                node1.setFlag(node2.getFlag());
                node1.setDeep(node2.getDeep());
                node1.setParent(node2.getParent());
                if(s2 % 2 == 0) {
                    node1.getParent().setRightTree(node1);
                    node1.getParent().getSubInformation().set(1,node1);
                }
                if(s2 % 2 == 1) {
                    node1.getParent().setLeftTree(node1);
                    node1.getParent().getSubInformation().set(0,node1);
                }
                node2.setDeep(0);
                node2.setParent(null);
                parent1 = treetolist(node2);
                parent2 = treetolist(parent2.get(0));
                list.add(parent1);
                list.add(parent2);
            }else {
                node2.setSubTree(node1.getSubTree());
                node2.setFlag(node1.getFlag());
                node2.setDeep(node1.getDeep());
                node2.setParent(node1.getParent());
                if(s1 % 2 == 0) {
                    node2.getParent().setRightTree(node2);
                    node2.getParent().getSubInformation().set(1,node2);
                }
                if(s1 % 2 == 1) {
                    node2.getParent().setLeftTree(node2);
                    node2.getParent().getSubInformation().set(0,node2);
                }
                node1.setDeep(0);
                node1.setParent(null);
                parent1 = treetolist(parent1.get(0));
                parent2 = treetolist(node1);
                list.add(parent1);
                list.add(parent2);
            }
        }else {
            list.add(parent1);
            list.add(parent2);
        }
        return list;
    }
    public static ArrayList<TreeNode> treetolist(TreeNode node) {
        ArrayList<TreeNode> list = new ArrayList<>();
        list.add(node);
        node.setSubTree("根节点");
        for (int i = 0; i < list.size(); i++) {
            TreeNode root = list.get(i);
            root.setSerial(i);
            int deep = root.getDeep();
            if (root.getLeftTtree() != null) {
                root.getLeftTtree().setDeep(deep + 1);
                list.add(root.getLeftTtree());
            }
            if (root.getRightTree() != null) {
                root.getRightTree().setDeep(deep + 1);
                list.add(root.getRightTree());
            }
        }
        return list;
    }
    //集成个体的宏交叉(待测试)
    public static ArrayList<ArrayList<TreeNode1>> EImacro_Crossover(ArrayList<TreeNode1> parent1,ArrayList<TreeNode1> parent2,double ratec) {
        ArrayList<ArrayList<TreeNode1>> list = new ArrayList<>();
        ArrayList<Integer> num1 =new ArrayList<>();
        ArrayList<Integer> num2 =new ArrayList<>();
        for(int i = 0 ; i < parent1.size() ; i ++) {
            if(parent1.get(i).getFlag() == 0) {
                num1.add(i);
            }
        }
        for(int i = 0 ; i < parent2.size() ; i ++) {
            if(parent2.get(i).getFlag() == 0) {
                num2.add(i);
            }
        }
        double a = Math.random();
        Random rand = new Random();
        if(a < ratec) {                                                                                  //进行交叉操作
            int s1;
            int s2;
            while(true) {
                s1 = num1.get(rand.nextInt(num1.size()));
                s2 = num2.get(rand.nextInt(num2.size()));
                if(parent1.get(s1).getDeep() == parent2.get(s2).getDeep()) {
                    break;
                }
            }
            //System.out.println(s1);
            //System.out.println(s2);
            TreeNode1 node1 = parent1.get(s1);
            TreeNode1 node2 = parent2.get(s2);
            if (s1 == 0 && s2 == 0) {
                list.add(parent2);
                list.add(parent1);
            }else if(s1 != 0 && s2 != 0) {
                TreeNode1 copynode1 = Copy.copyEINode(node1);
                copynode1.setDeep(node2.getDeep());
                copynode1.setParent(node2.getParent());
                node2.setFlag(node1.getFlag());
                node2.setDeep(node1.getDeep());
                node2.setParent(node1.getParent());
                if(s1 % 2 == 0) {
                    node2.getParent().setRightTree(node2);
                }
                if(s1 % 2 == 1) {
                    node2.getParent().setLeftTree(node2);
                }
                if(s2 % 2 == 0) {
                    copynode1.getParent().setRightTree(copynode1);
                }
                if(s2 % 2 == 1) {
                    copynode1.getParent().setLeftTree(copynode1);
                }
                parent1 = treetolist1(parent1.get(0));
                parent2 = treetolist1(parent2.get(0));
                list.add(parent1);
                list.add(parent2);
                node1 = null;
            }else if(s1 == 0 && s2 != 0) {
                node1.setFlag(node2.getFlag());
                node1.setDeep(node2.getDeep());
                node1.setParent(node2.getParent());
                if(s2 % 2 == 0) {
                    node1.getParent().setRightTree(node1);
                }
                if(s2 % 2 == 1) {
                    node1.getParent().setLeftTree(node1);
                }
                node2.setDeep(0);
                node2.setParent(null);
                parent1 = treetolist1(node2);
                parent2 = treetolist1(parent2.get(0));
                list.add(parent1);
                list.add(parent2);
            }else {
                node2.setFlag(node1.getFlag());
                node2.setDeep(node1.getDeep());
                node2.setParent(node1.getParent());
                if(s1 % 2 == 0) {
                    node2.getParent().setRightTree(node2);
                }
                if(s1 % 2 == 1) {
                    node2.getParent().setLeftTree(node2);
                }
                node1.setDeep(0);
                node1.setParent(null);
                parent1 = treetolist1(parent1.get(0));
                parent2 = treetolist1(node1);
                list.add(parent1);
                list.add(parent2);
            }
        }else {
            list.add(parent1);
            list.add(parent2);
        }
        return list;
    }
    public static ArrayList<TreeNode1> treetolist1(TreeNode1 node) {
        ArrayList<TreeNode1> list = new ArrayList<>();
        list.add(node);
        for (int i = 0; i < list.size(); i++) {
            TreeNode1 root = list.get(i);
            root.setSerial(i);
            int deep = root.getDeep();
            if (root.getLeftTtree() != null) {
                root.getLeftTtree().setDeep(deep + 1);
                list.add(root.getLeftTtree());
            }
            if (root.getRightTree() != null) {
                root.getRightTree().setDeep(deep + 1);
                list.add(root.getRightTree());
            }
        }
        return list;
    }
}
