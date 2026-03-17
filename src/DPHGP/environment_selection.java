package DPHGP;

import java.util.ArrayList;
import java.util.Random;

public class environment_selection {
    public static ArrayList<TreeNode> Tournament(ArrayList<TreeNode> Population,double rates) {
        ArrayList<TreeNode> selectedDT = new ArrayList<>();
        int size = (int)(Population.size() * rates);
        for (int t = 0; t < 100; t++) {
            ArrayList<TreeNode> competitors = new ArrayList<>();
            Random random = new Random();
            // 随机选择20个体作为竞争者
            for (int i = 0; i < size; i++) {
                int index = random.nextInt(Population.size());
                competitors.add(Population.get(index));
            }
            // 找出竞争者中评估值最高的个体
            selectedDT.add(AdaptiveCrossoverAndMutation.selectOneDT(competitors));
            /*for(int j = 0 ;j < Population.size() ; j ++) {
                if(Copy.DTisEqual(Population.get(j),selectedDT.get(t)) == 0) {
                    Population.remove(j);
                    //break;
                }
            }*/
            competitors.clear();
        }
        return selectedDT;
    }

    public static ArrayList<TreeNode1> Tournament1(ArrayList<TreeNode1> Population,double rates,String[] function, ArrayList<double[][]> elitePBMSet,ArrayList<double[][]> simFValueMatrixSet) {
        ArrayList<TreeNode1> selectedEI = new ArrayList<>();
        int size = (int)(Population.size() * rates);
        for (int t = 0; t < 100; t++) {
            ArrayList<TreeNode1> competitors = new ArrayList<>();
            Random random = new Random();
            // 随机选择20个体作为竞争者
            for (int i = 0; i < size; i++) {
                int index = random.nextInt(Population.size());
                competitors.add(Population.get(index));
            }
            // 找出竞争者中评估值最高的个体
            selectedEI.add(AdaptiveCrossoverAndMutation.selectOneEI(competitors));
            /*for(int j = 0 ;j < Population.size() ; j ++) {
                if(Copy.EIisEqual(Population.get(j),selectedEI.get(t)) == 0) {
                    Population.remove(j);
                    break;
                }
            }*/
            competitors.clear();
        }
        return selectedEI;
    }
}
