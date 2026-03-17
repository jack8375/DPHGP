package DPHGP;

import Function.TwoKindsOfFunction;

import java.util.ArrayList;
import java.util.Arrays;

public class Decoding1 {
     /**
      * 解析集成个体
      * @param  EI  输入一个集成个体
      * @paeam  function  包含“Union”,"Intersect"的字符串数组
      * @paeam  list  由DT种群产生的PBM集合（有多少颗精英树,该集合就有多少个元素）
      * @return  返回一个合成的矩阵
      */
     public static double[][] decoding1(ArrayList<TreeNode1> EI, String[] function) {
          for(int i = EI.size()-1; i >= 0 ; i --) {
               if(EI.get(i).getFlag() == 0) {
                    if (EI.get(i).getFunction() == "Union") {
                         EI.get(i).setMatrix(TwoKindsOfFunction.Union(EI.get(i).getLeftTtree().getMatrix(), EI.get(i).getRightTree().getMatrix()));
                    }
                    if (EI.get(i).getFunction() == "Intersect") {
                         EI.get(i).setMatrix(TwoKindsOfFunction.Intersect(EI.get(i).getLeftTtree().getMatrix(), EI.get(i).getRightTree().getMatrix()));
                    }
               }
          }
          return EI.get(0).getMatrix();
     }
     //选择精英EI(一个)
     public static TreeNode1 selectEliteEI(ArrayList<TreeNode1> Pen,double[] fmeasureSet) {
          TreeNode1 eliteEI = new TreeNode1();
          double[] a = Copy.Copyshuzu(fmeasureSet);
          Arrays.parallelSort(a);
          for(int j = 0 ; j < fmeasureSet.length ; j ++) {
               if(fmeasureSet[j] == a[a.length - 1]) {
                    eliteEI = Pen.get(j);   //
                    break;
               }
          }
          return eliteEI;
     }
}
