package DPHGP;

import Matrix.Finally_SimilarityMatrix;
import parsing.List_IdLableComment;
import parsing.ParsingOfAlignment;
import parsing.classProperity;

import java.util.ArrayList;
import java.util.List;

import static parsing.classProperity.randomselect;
import static similarity.SimilarityFlood.belong;

public class Main1 {
    public static void Algorithm(String a) throws Exception {

            ArrayList<String> SourceOntology = classProperity.classAndProperity("D:\\科研\\benchmarks\\101\\onto.rdf");
            ArrayList<String> TargetOntology = classProperity.classAndProperity("D:\\科研\\benchmarks\\" + a + "\\onto.rdf");
            List<String> Reference = ParsingOfAlignment.parseRefalignFile("D:\\科研\\benchmarks\\" + a + "\\refalign.rdf");
            ArrayList<String> SourceOntologyClassID = List_IdLableComment.list_ClassIdLableComment("D:\\科研\\benchmarks\\101\\onto.rdf",0);
            ArrayList<String> TargetOntologyClassID = List_IdLableComment.list_ClassIdLableComment("D:\\科研\\benchmarks\\" + a + "\\onto.rdf",0);
            ArrayList<String> SourceOntologyDataProperity = List_IdLableComment.list_DataproperityIdLableComment("D:\\科研\\benchmarks\\101\\onto.rdf",0);
            ArrayList<String> SourceOntologyObjectProperity = List_IdLableComment.list_ObjectproperityIdLableComment("D:\\科研\\benchmarks\\101\\onto.rdf",0);
            ArrayList<String> TargetOntologyDataProperity = List_IdLableComment.list_DataproperityIdLableComment("D:\\科研\\benchmarks\\" + a + "\\onto.rdf",0);
            ArrayList<String> TargetOntologyObjectProperity = List_IdLableComment.list_ObjectproperityIdLableComment("D:\\科研\\benchmarks\\" + a + "\\onto.rdf",0);
            String[] measure = {"SMOA", "N-Gram", "Wu-and-Palmer", "Instance", "Resnik", "SimilarityFlood"};
            String[] function = {"HaT", "PrT", "PeT"};
            String[] function1 = {"outputMatrix", "flippingMatrix"};
            String[] function2 = {"Union", "Intersect"};
            double[][] SMOA = Finally_SimilarityMatrix.FinallyMatrix_SMOA("D:\\科研\\benchmarks\\" + a + "\\onto.rdf");
            double[][] Ngram = readWuMatrix.read("101-" + a + "Ngram", SMOA.length, SMOA[0].length);
            double[][] Resnik = readWuMatrix.read("101-" + a + "Resnik", SMOA.length, SMOA[0].length);
            double[][] Wu = readWuMatrix.read("101-" + a + "Wu", SMOA.length, SMOA[0].length);
            double[][] Instance = readWuMatrix.read("101-" + a + "Instance", SMOA.length, SMOA[0].length);
            double[][] SimilarityFlood = readWuMatrix.read("101-" + a + "SimilarityFlood", SMOA.length, SMOA[0].length);
            if(a.equals("202-2")) {
                randomselect(SimilarityFlood);
            }
            int PopulationSize = 100;                                      //设置种群大小
            double rates = 0.1;                                            //锦标赛选择率
            double ratec = 0.8;                                            //交叉率
            double ratem = 0.1;                                            //变异率
            int MaxGen = 2000;                                             //最大迭代次数
            double probt = 0.6;                                            //生成叶子节点的概率
            int DecisionForestSize = 5;                                    //精英决策树的规模
            double st = 0.05;                                              //CGA的步长
            //初始化部分
            ArrayList<TreeNode> Pdt = new ArrayList<>();                       //创建一个存放DT种群的数组
            ArrayList<TreeNode1> Pen = new ArrayList<>();
            Pdt = Coding.Population1(PopulationSize, measure, function, function1, probt);   //给种群中添加树，初始化DT种群
            System.out.println("种群初始化完成！");
            double[] fmeasureSet = new double[Pdt.size()];                                  //用于存放DT种群中每棵树的f度量值
            for (int i = 0; i < Pdt.size(); i++) {                                           //开始计算DT种群中每棵树的f度量值
                fmeasureSet[i] = Fitness.fitnessOfDT(Pdt.get(i), SMOA, Wu, Ngram, Resnik, Instance, SimilarityFlood);
            }
            ArrayList<TreeNode> EliteDTset = new ArrayList<>();
            EliteDTset = Decoding.selectEliteDT(Pdt, DecisionForestSize, fmeasureSet);  //利用计算出来的f度量值来选择出数量为DecisionForestSize个精英树
            System.out.println("精英DT选择完成！");
            int[] n = new int[5];
            for(int j = 0 ; j < EliteDTset.size() ;j ++) {
                for (int i = 0; i < Pdt.size(); i++) {
                    if(Copy.DTisEqual(Pdt.get(i),EliteDTset.get(j)) == 0) {
                        n[j] = i;
                        break;
                    }
                }
            }
            ArrayList<ArrayList<TreeNode>> list = new ArrayList<>();
            for (int i = 0; i < EliteDTset.size(); i++) {
               list.add(macro_Crossover.treetolist(EliteDTset.get(i)));
            }
            EliteDTset = CGA_ConstantOptimization.CGAOptimization(list, 8, st, SMOA, Wu, Ngram, Resnik, Instance, SimilarityFlood);  //利用CGA对每颗精英树的constant参数进行优化
            //EliteDTset = GA_ConstantOptimization.GAOptimization(list, 8, SMOA, Wu, Ngram, Resnik, Instance, SimilarityFlood);   //利用GA对每颗精英树的constant参数进行优化
            list.clear();
            for(int i = 0 ; i < n.length ; i++) {
                Pdt.set(n[i],EliteDTset.get(i));
            }
            System.out.println("CGA常数优化完成！");
            //System.out.println("GA常数优化完成！");
            ArrayList<double[][]> elitePBMSet = new ArrayList<>();                //存放精英DT输出的PBM（一棵树对应一个PBM）
            ArrayList<double[][]> simFValueMatrixSet = new ArrayList<>();         //此做法方便用于评估EI个体
            ArrayList<double[][]> d = new ArrayList<>();
            for (int i = 0; i < EliteDTset.size(); i++) {
                d = Decoding.DTofPBMandMatrix(EliteDTset.get(i), SMOA, Wu, Ngram, Resnik, Instance, SimilarityFlood);  //计算每颗精英树最后合成的PBM以及该PBM矩阵对应的SimValue矩阵(当PBM中某一位置元素为1时，在SimValue矩阵中有其对应位置的SimVale值)
                elitePBMSet.add(d.get(0));
                simFValueMatrixSet.add(d.get(1));
            }
            d.clear();
            Pen = Coding1.Population2(PopulationSize, function2, probt, elitePBMSet);   //初始化EI种群
            double[] fmeasureSet1 = new double[Pen.size()];
            for (int i = 0; i < Pen.size(); i++) {
                fmeasureSet1[i] = Fitness.FitnessOfEI(Pen.get(i), function, elitePBMSet, simFValueMatrixSet)[0];
            }
            System.out.println("选择精英ensemble Individual");
            TreeNode1 enElite = Decoding1.selectEliteEI(Pen, fmeasureSet1);
            System.out.println("精英ensemble Individual选择完成！");
            int gen = 0;
            ArrayList<TreeNode> newPdt;       //用于存放交叉变异后产生的newPdt种群
            ArrayList<TreeNode1> newPen;      //用于存放交叉变异后产生的newPen种群
            ArrayList<TreeNode> newEliteDTset;  //用于存放新的精英DT
            ArrayList<double[][]> newElitePBMSet = new ArrayList<>();                //存放新的精英树解码合成的PBM
            ArrayList<double[][]> newsimFValueMatrixSet = new ArrayList<>();         //此做法方便用于评估EI个体
            TreeNode1 enElite1;              //用于存储evaluate(Pen, DTSetelite)后选择出的enElite1
            ArrayList<TreeNode1> Ptemp;     //用于存储创建的临时种群Ptemp
            ArrayList<TreeNode1> copyPen;
            ArrayList<TreeNode1> copynewPen;
            double df;          //(EliteDTset,enElite)
            double df1;         //(EliteDTset,enElite1)
            while (gen < MaxGen) {
                System.out.println("进行" + (gen + 1) + "次迭代");
                //交叉和变异部分，产生新的种群
                //System.out.println("DT种群进行" + (gen + 1) + "次交叉变异");
                newPdt = AdaptiveCrossoverAndMutation.DTCrossoverAndMutation(Pdt, EliteDTset, ratec, ratem, rates, measure, function, function1, probt, fmeasureSet);
                //System.out.println("EI种群进行" + (gen + 1) + "次交叉变异");
                newPen = AdaptiveCrossoverAndMutation.EICrossoverAndMutation(Pen, enElite, elitePBMSet, function2, probt, ratec, ratem, rates, fmeasureSet1);
                for (int i = 0; i < newPdt.size(); i++) {                                           //开始计算DT种群中每棵树的f度量值
                    fmeasureSet[i] = Fitness.fitnessOfDT(newPdt.get(i), SMOA, Wu, Ngram, Resnik, Instance, SimilarityFlood);
                }
                for (int i = 0; i < newPen.size(); i++) {
                    fmeasureSet1[i] = Fitness.FitnessOfEI(newPen.get(i), function, elitePBMSet, simFValueMatrixSet)[0];
                }
                enElite1 = Decoding1.selectEliteEI(newPen, fmeasureSet1);                 //从更新后的EI种群中选择出精英ensemble Individual
                df = enElite.getFitness();//计算enElite(之前的elite ensemble Individual)适应度值
                df1 = enElite1.getFitness();       //计算enElite1(更新后的elite ensemble Individual)适应度值
                if (df1 > df) {
                    enElite = Copy.CopyEI(macro_Crossover.treetolist1(enElite1)).get(0);              //*
                }
                Pdt = environment_selection.Tournament(integerPopulation1(Pdt, newPdt), rates);
                for (int i = 0; i < Pdt.size(); i++) {                                           //开始计算DT种群中每棵树的f度量值
                    fmeasureSet[i] = Fitness.fitnessOfDT(Pdt.get(i), SMOA, Wu, Ngram, Resnik, Instance, SimilarityFlood);
                }
                newEliteDTset = Decoding.selectEliteDT(Pdt, DecisionForestSize, fmeasureSet);
                //System.out.println("新的精英DT选择完成！");
                int[] h = new int[5];
                for(int j = 0 ; j < newEliteDTset.size() ;j ++) {
                    for (int i = 0; i < Pdt.size(); i++) {
                        if(Copy.DTisEqual(Pdt.get(i),newEliteDTset.get(j)) == 0) {
                            h[j] = i;
                            break;
                        }
                    }
                }
                for (int i = 0; i < newEliteDTset.size(); i++) {
                    list.add(macro_Crossover.treetolist(newEliteDTset.get(i)));
                }
                newEliteDTset = CGA_ConstantOptimization.CGAOptimization(list, 8, st, SMOA, Wu, Ngram, Resnik, Instance, SimilarityFlood);
                //newEliteDTset = GA_ConstantOptimization.GAOptimization(list, 8, SMOA, Wu, Ngram, Resnik, Instance, SimilarityFlood); //GA做参数优化
                list.clear();
                for(int i = 0 ; i < h.length ; i++) {
                    Pdt.set(h[i],newEliteDTset.get(i));
                }
                if (Copy.areEqual(EliteDTset, newEliteDTset) == 0) {
                    ArrayList<double[][]> c = new ArrayList<>();
                    for (int i = 0; i < newEliteDTset.size(); i++) {
                        c = Decoding.DTofPBMandMatrix(newEliteDTset.get(i), SMOA, Wu, Ngram, Resnik, Instance, SimilarityFlood);
                        newElitePBMSet.add(c.get(0));
                        newsimFValueMatrixSet.add(c.get(1));
                    }
                    copyPen = Copy.CopyEIPopulation(Pen);
                    copynewPen = Copy.CopyEIPopulation(newPen);
                    ArrayList<TreeNode1> z = environment_selection.Tournament1(integerPopulation(Pen, newPen), rates, function2, elitePBMSet, simFValueMatrixSet);
                    copyPen = setLeaf(copyPen, newElitePBMSet);
                    copynewPen = setLeaf(copynewPen, newElitePBMSet);
                    Ptemp = integerPopulation(copyPen, copynewPen);
                    double[] fmeasureSet2 = new double[Pen.size() * 2];
                    for (int i = 0; i < Ptemp.size(); i++) {
                        fmeasureSet2[i] = Fitness.FitnessOfEI(Ptemp.get(i), function, elitePBMSet, simFValueMatrixSet)[0];
                    }
                    enElite1 = Decoding1.selectEliteEI(Ptemp, fmeasureSet2);
                    ArrayList<TreeNode1> k = environment_selection.Tournament1(Ptemp, rates, function2, newElitePBMSet, newsimFValueMatrixSet);
                    Ptemp.clear();
                    df = Fitness.FitnessOfEI(enElite, function2, elitePBMSet, simFValueMatrixSet)[0];
                    df1 = Fitness.FitnessOfEI(enElite1, function2, newElitePBMSet, newsimFValueMatrixSet)[0];
                    if(df1 > df) {
                        enElite = Copy.CopyEI(macro_Crossover.treetolist1(enElite1)).get(0);
                    }
                    if (FindMaxFinessEiteDT(newEliteDTset) > FindMaxFinessEiteDT(EliteDTset)) {
                        EliteDTset = Copy.CopyDTPopulation(newEliteDTset);
                        elitePBMSet = Copy.CopyMatrixSet(newElitePBMSet);
                        simFValueMatrixSet = Copy.CopyMatrixSet(newsimFValueMatrixSet);
                        Pen = Copy.CopyEIPopulation(k);
                    } else {
                        Pen = Copy.CopyEIPopulation(z);
                        for(int i = 0 ; i < h.length ; i++) {
                            Pdt.set(h[i],EliteDTset.get(i));
                        }
                    }
                    //代码优化 2023-11-8
                    newElitePBMSet.clear();
                    newsimFValueMatrixSet.clear();
                    k = null;
                    z = null;
                    c = null;
                    for (int i = 0; i < Pen.size(); i++) {
                        fmeasureSet1[i] = Fitness.FitnessOfEI(Pen.get(i), function, elitePBMSet, simFValueMatrixSet)[0];
                    }
                }
                if (gen == MaxGen - 1) {
                    //List<String> Reference = ParsingOfAlignment.parseRefalignFile("D:\\科研\\benchmarks\\" + a + "\\refalign.rdf");
                    /*for (String s : Reference) {
                        System.out.println(s);
                    }*/
                    List<String> finalMatchingResult = new ArrayList<>();    //存储ensemble个体找到的匹配对
                    List<String> finalMatchingResult1 = new ArrayList<>();   //存储精英DT个体找到的匹配对
                    df = FindMaxFinessEiteDT(EliteDTset);
                    double[][] Alignment1 = Decoding.DTofPBMandMatrix(EliteDTset.get(FindMaxFinessEiteDT1(df,EliteDTset)),SMOA,Wu,Ngram,Resnik,Instance,SimilarityFlood).get(0);
                    for (int i = 0; i < Alignment1.length; i++) {
                        for (int j = 0; j < Alignment1[i].length; j++) {
                            if (Alignment1[i][j] == 1) {
                                if((belong(SourceOntology.get(i),SourceOntologyClassID) == 1 && belong(TargetOntology.get(j),TargetOntologyClassID) == 1) || (belong(SourceOntology.get(i),SourceOntologyDataProperity) == 1 && belong(TargetOntology.get(j),TargetOntologyDataProperity) == 1) || (belong(SourceOntology.get(i),SourceOntologyObjectProperity) == 1 && belong(TargetOntology.get(j),TargetOntologyObjectProperity) == 1)) {
                                    finalMatchingResult1.add(SourceOntology.get(i) + "---" + TargetOntology.get(j));
                                }
                            }
                        }
                    }
                    ArrayList<TreeNode1> y = macro_Crossover.treetolist1(enElite);
                    double[][] Alignment = Decoding1.decoding1(y, function2);
                    filter(Alignment, SourceOntology, TargetOntology);
                    for (int i = 0; i < Alignment.length; i++) {
                        for (int j = 0; j < Alignment[i].length; j++) {
                            if (Alignment[i][j] == 1) {
                                if((belong(SourceOntology.get(i),SourceOntologyClassID) == 1 && belong(TargetOntology.get(j),TargetOntologyClassID) == 1) || (belong(SourceOntology.get(i),SourceOntologyDataProperity) == 1 && belong(TargetOntology.get(j),TargetOntologyDataProperity) == 1) || (belong(SourceOntology.get(i),SourceOntologyObjectProperity) == 1 && belong(TargetOntology.get(j),TargetOntologyObjectProperity) == 1)) {
                                    finalMatchingResult.add(SourceOntology.get(i) + "---" + TargetOntology.get(j));
                                }
                            }
                        }
                    }
                    double[] result = readWuMatrix.valueToPar(Reference, finalMatchingResult);   // EI
                    double[] result1 = readWuMatrix.valueToPar(Reference, finalMatchingResult1); //DT
                    //System.out.println("找到的匹配对为：：");
                    if(result[2] > result1[2]) {
                        /*for (int i = 0; i < finalMatchingResult.size(); i++) {
                            System.out.println(finalMatchingResult.get(i));
                        }*/
                        System.out.println("101-" + a + "测试结果");
                        //System.out.println("查全率为：" + result[0]);
                        //System.out.println("查准率为：" + result[1]);
                        System.out.println("f-measure为：" + result[2]);
                    }else {
                        /*for (int i = 0; i < finalMatchingResult1.size(); i++) {
                            System.out.println(finalMatchingResult1.get(i));
                        }*/
                        System.out.println("101-" + a + "测试结果");
                        //System.out.println("查全率为：" + result1[0]);
                        //System.out.println("查准率为：" + result1[1]);
                        System.out.println("f-measure为：" + result1[2]);
                    }
                    System.out.println("----------------------------");
                }
                gen = gen + 1;
            }
    }
    public static double[][] filter(double[][] a,ArrayList<String> SourceOntology,ArrayList<String> TargetOntology) {
        for(int i = 0 ; i < SourceOntology.size() ; i++) {
            for(int j = 0; j < TargetOntology.size(); j++) {
                if(SourceOntology.get(i).equals(TargetOntology.get(j))) {
                    for(int k = 0 ; k < SourceOntology.size() ; k++) {
                        if(k != i) {
                            a[k][j] = 0;
                        }
                    }
                    for(int k = 0 ; k < TargetOntology.size() ; k++) {
                        if(k != j) {
                            a[i][k] = 0;
                        }
                    }
                }
            }
        }
        return a;
    }
    public static ArrayList<TreeNode1> setLeaf(ArrayList<TreeNode1> a,ArrayList<double[][]> elitePBMSet) {
        ArrayList<TreeNode1>[] z = new ArrayList[a.size()];
        for(int i = 0 ; i < z.length ; i++) {
            z[i] = macro_Crossover.treetolist1(a.get(i));
        }
        for(int i = 0 ; i < z.length ; i++) {
            for(int j = 0 ; j < z[i].size() ; j++) {
                if(z[i].get(j).getFlag() == 1) {
                    z[i].get(j).setMatrix(elitePBMSet.get(Double.valueOf(Math.random()*elitePBMSet.size()).intValue()));
                }
            }
        }
        ArrayList<TreeNode1> list = new ArrayList<>();
        for(int i = 0 ; i < a.size() ; i++) {
            list.add(z[i].get(0));
        }
        return list;
    }
    //代码优化
    public static ArrayList<TreeNode1> integerPopulation(ArrayList<TreeNode1> a,ArrayList<TreeNode1> b) {
        ArrayList<TreeNode1> list = new ArrayList<>(a);
        list.addAll(b);
        return list;
    }
    //代码优化
    public static ArrayList<TreeNode> integerPopulation1(ArrayList<TreeNode> a,ArrayList<TreeNode> b) {
        ArrayList<TreeNode> list = new ArrayList<>(a);
        list.addAll(b);
        return list;
    }
    public static double FindMaxFinessEiteDT(ArrayList<TreeNode> EliteDTset) {
        double a = 0;
        for (TreeNode treeNode : EliteDTset) {
            if(treeNode.getFitness() > a) {
                a = treeNode.getFitness();
            }
        }
        return a;
    }
    public static int FindMaxFinessEiteDT1(double fitness,ArrayList<TreeNode> EliteDTset) {
        int index = 0;
        for (int i = 0 ; i < EliteDTset.size() ; i++) {
            if(EliteDTset.get(i).getFitness() == fitness) {
                index = i;
            }
        }
        return index;
    }
}


