package DPHGP;

import java.util.ArrayList;

import static DPHGP.CGA_ConstantOptimization.getConstant;
import static org.apache.jena.atlas.lib.RandomLib.random;

public class GA_ConstantOptimization {
    public static ArrayList<TreeNode> GAOptimization(ArrayList<ArrayList<TreeNode>> eliteDTset, int a, double[][] SMOA, double[][] Wu, double[][] Ngram, double[][] Resnik, double[][] Instance, double[][] SimilarityFlood) {
        ArrayList<TreeNode> s = new ArrayList<>();
        for(int i = 0 ; i < eliteDTset.size(); i++) {
            s.add(DTGA(eliteDTset.get(i) ,a ,SMOA, Wu, Ngram, Resnik, Instance, SimilarityFlood).get(0));
            //System.out.println("第" + (i+1) + "棵树优化完成");
        }
        return s;
    }
    public static ArrayList<TreeNode> DTGA(ArrayList<TreeNode> eliteDT, int a,//a表示每个常数格雷码编码位数
                                           double[][] SMOA, double[][] Wu, double[][] Ngram, double[][] Resnik, double[][] Instance, double[][] SimilarityFlood) {

        int populationSize = 20; //种群大小
        double crossoverRate = 0.7;   //交叉率
        double mutationRate = 0.05;   //变异率
        int MaxGen = 20;             //最大迭代次数
        double[] constant = getConstant(eliteDT);
        int length = constant.length * a;  //GA个体的编码长度
        ArrayList<ArrayList<Integer>> Population = InitializePopulation(populationSize, length);  //初始化种群
        double f1 = eliteDT.get(0).getFitness();
        double[] fitnessValue = new double[populationSize];
        for (int i = 0; i < populationSize; i++) {     //对种群中的每个个体进行评估
            fitnessValue[i] = fitness(Population.get(i), eliteDT, a, SMOA, Wu, Ngram, Resnik, Instance, SimilarityFlood);
        }
        double f2 = 0;
        int location = 0;
        for (int j = 0; j < fitnessValue.length; j++) {
            if (fitnessValue[j] > f2) {
                f2 = fitnessValue[j];
                location = j;
            }
        }
        ArrayList<Integer> EliteIndividual = Population.get(location); //选择精英解
        ArrayList<ArrayList<Integer>> newPopulation = new ArrayList<>();
        ArrayList<Integer> newEliteIndividual = new ArrayList<>();
        int gen = 0;
        while (gen < MaxGen) {
            //System.out.println("迭代" + (gen+1) + "次");
            //交叉和变异
            newPopulation = CrossoveAndMutation(Population,crossoverRate,mutationRate,fitnessValue);
            for (int i = 0; i < newPopulation.size(); i++) {     //对种群中的每个个体进行评估
                fitnessValue[i] = fitness(newPopulation.get(i), eliteDT, a, SMOA, Wu, Ngram, Resnik, Instance, SimilarityFlood);
            }
            double f3 = 0;
            int location1 = 0;
            for (int j = 0; j < fitnessValue.length; j++) {
                if (fitnessValue[j] > f3) {
                    f3 = fitnessValue[j];
                    location1 = j;
                }
            }
            newEliteIndividual = newPopulation.get(location1); //选择新的精英解
            if(f3 > f2) {
                EliteIndividual = copyIndividual(newEliteIndividual);
                f2 = fitness(EliteIndividual, eliteDT, a, SMOA, Wu, Ngram, Resnik, Instance, SimilarityFlood);
            }
            newPopulation.clear();
            newEliteIndividual.clear();
            gen = gen + 1;
        }
        if(f2 > f1) {
            int[] binary = ListToArray(EliteIndividual);
            double[] constant1 = CGAcoding.Decoding(binary,a);
            int g = 0;
            for (int h = 0; h < eliteDT.size(); h++) {
                if (eliteDT.get(h).getFlag() == 0) {
                    eliteDT.get(h).setConstants(constant1[g++]);
                }
            }
            eliteDT.get(0).setFitness(f2);
        }else {
            int g = 0;
            for (int h = 0; h < eliteDT.size(); h++) {
                if (eliteDT.get(h).getFlag() == 0) {
                    eliteDT.get(h).setConstants(constant[g++]);
                }
            }
            eliteDT.get(0).setFitness(f1);
        }
        return eliteDT;
    }
    public static ArrayList<ArrayList<Integer>> InitializePopulation(int populationSize, int length) {   //生成GA种群
        ArrayList<ArrayList<Integer>> list = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            list.add(Individual(length));
        }
        return list;
    }
    public static ArrayList<Integer> Individual(int length) {       //生成GA个体
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            list.add(random.nextInt(2));
        }
        return list;
    }
    public static double fitness(ArrayList<Integer> individual, ArrayList<TreeNode> eliteDT, int a,
                                 double[][] SMOA, double[][] Wu, double[][] Ngram, double[][] Resnik, double[][] Instance, double[][] SimilarityFlood) {

        double f = 0;
        int[] c = ListToArray(individual);
        double[] constant = CGAcoding.Decoding(c, a);
        int t = 0;
        for (int k = 0; k < eliteDT.size(); k++) {                           //重新设置树中内部结点的输入常数
            if (eliteDT.get(k).getFlag() == 0) { //判断是否为内部结点
                eliteDT.get(k).setConstants(constant[t++]);
            }
        }
        f = Fitness.fitnessOfDT1(eliteDT, SMOA, Wu, Ngram, Resnik, Instance, SimilarityFlood);
        return f;
    }

    public static int[] ListToArray(ArrayList<Integer> list) {    //ArrayList<Integer>转换为int[]
        int[] a = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            a[i] = list.get(i);
        }
        return a;
    }
    public static ArrayList<Double> ArrayToList(double[] a) {    //double[]转换为ArrayList<Double>
        ArrayList<Double> list = new ArrayList<>();
        for (double v : a) {
            list.add(v);
        }
        return list;
    }
    public static ArrayList<ArrayList<Integer>> Crossover(ArrayList<Integer> parent1, ArrayList<Integer> parent2) {   //单点交叉
        ArrayList<ArrayList<Integer>> list = new ArrayList<>();
        int CrossoverPoint = random.nextInt(parent1.size());   //随机选择交叉点
        ArrayList<Integer> GeneFragment1 = new ArrayList<>();  //存放亲本1要交叉的基因片段
        ArrayList<Integer> GeneFragment2 = new ArrayList<>();  //存放亲本2要交叉的基因片段
        for (int i = CrossoverPoint; i < parent1.size(); i++) {
            GeneFragment1.add(parent1.get(i));
        }
        for (int j = CrossoverPoint; j < parent2.size(); j++) {
            GeneFragment2.add(parent2.get(j));
        }
        int num = 0;
        for (int i = CrossoverPoint; i < parent1.size(); i++) {  //在亲本1插入亲本2要交叉的基因片段
            parent1.set(i, GeneFragment2.get(num++));
        }
        num = 0;
        for (int j = CrossoverPoint; j < parent2.size(); j++) {  //在亲本2插入亲本1要交叉的基因片段
            parent2.set(j, GeneFragment1.get(num++));
        }
        list.add(parent1);
        list.add(parent2);
        return list;
    }
    public static ArrayList<Integer> Mutation(ArrayList<Integer> Individual) {
        int MutationPoint = random.nextInt(Individual.size());   //随机选择变异点
        if (Individual.get(MutationPoint) == 0) {
            Individual.set(MutationPoint, 1);
        } else {
            Individual.set(MutationPoint, 0);
        }
        return Individual;
    }
    public static ArrayList<ArrayList<Integer>> CrossoveAndMutation(ArrayList<ArrayList<Integer>> Population,double crossoverRate,double mutationRate,double[] fitnessValue) {
        ArrayList<ArrayList<Integer>> newPopulation = new ArrayList<>();
        ArrayList<ArrayList<Integer>> Offspring = new ArrayList<>();
        ArrayList<ArrayList<Integer>> parent = new ArrayList<>();
        for(int i = 0 ; i < 10; i++) {
            ArrayList<ArrayList<Integer>> CopyPopulation = copyPopulation(Population);
            ArrayList<Double> fitnessSet= ArrayToList(fitnessValue);
            parent = RouletteWheel(CopyPopulation,fitnessSet);
            double s = Math.random();
            if(s < crossoverRate) {
                Offspring = Crossover(parent.get(0),parent.get(1));
            }else {
                Offspring.add(parent.get(0));
                Offspring.add(parent.get(1));
            }
            newPopulation.addAll(Offspring);
            Offspring.clear();
            parent.clear();
        }
        for(int i = 0 ; i < newPopulation.size() ; i++) {
            double n = Math.random();
            if(n < mutationRate) {
                newPopulation.set(i,Mutation(newPopulation.get(i)));
            }
        }
        return newPopulation;
    }
    public static ArrayList<ArrayList<Integer>> RouletteWheel(ArrayList<ArrayList<Integer>> CopyPopulation,ArrayList<Double> fmeasureSet) {
        ArrayList<ArrayList<Integer>> selectedIndividuals = new ArrayList<>();
        for(int k = 0 ; k < 2 ; k ++) {
            double totalFmeasure = 0;
            for (int j = 0; j < fmeasureSet.size(); j++) {
                totalFmeasure += fmeasureSet.get(j);
            }
            double r = random.nextDouble() * totalFmeasure; // 生成一个[0, totalFitness)之间的随机数
            double partialFmeasure = 0.0;
            for (int j = 0; j < fmeasureSet.size(); j++) {
                partialFmeasure += fmeasureSet.get(j);
                if (partialFmeasure >= r) {
                    selectedIndividuals.add(CopyPopulation.get(j));
                    CopyPopulation.remove(j);
                    fmeasureSet.remove(j);
                    break;
                }
            }
        }
        return selectedIndividuals;
    }
    public static ArrayList<ArrayList<Integer>> copyPopulation(ArrayList<ArrayList<Integer>> Population) {
        ArrayList<ArrayList<Integer>> list = new ArrayList<>();
        for (ArrayList<Integer> integer : Population) {
            list.add(copyIndividual(integer));
        }
        return list;
    }
    public static ArrayList<Integer> copyIndividual(ArrayList<Integer> individual) {
        ArrayList<Integer> a = new ArrayList<>();
        a.addAll(individual);
        return a;
    }
}
