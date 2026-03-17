package DPHGP;

public class test2 {
    public static void main(String[] args) throws Exception {

        //String[] s = {"101","103","104","201-2","202-2","203","204","221","222","223","224","225","228","230","231","232","233","236","237","238","241","205","206","207","208","209","210","248-2","249-2","250-2","251-2","252-2","253-2","254-2","257-2","258-2","259-2","260-2","261-2","262-2"};
        //String[] s = {"205","206","207","208","209","210","248-2","249-2","250-2","251-2","252-2","253-2","254-2","257-2","258-2","259-2","260-2","261-2","262-2"};
        //String[] s = {"239","240","246","247"};
        //String[] s = {"205","228","250-2"};
        String[] s = {"101"};
        for (String string : s) {
            int h = 0;
            /*double[][] SMOA = Finally_SimilarityMatrix.FinallyMatrix_SMOA("D:\\科研\\benchmarks\\" + s[i] + "\\onto.rdf");
            double[][] Ngram = Finally_SimilarityMatrix.FinallyMatrix_NGramL("D:\\科研\\benchmarks\\" + s[i] + "\\onto.rdf");
            double[][] Resnik = Finally_SimilarityMatrix.FinallyMatrix_Resnik("D:\\科研\\benchmarks\\" + s[i] + "\\onto.rdf");
            double[][] Wu = Finally_SimilarityMatrix.FinallyMatrix_wuAndPalmer("D:\\科研\\benchmarks\\" + s[i] + "\\onto.rdf");
            double[][] Instance = Finally_SimilarityMatrix.FinallyMatrix_InstanceBased("D:\\科研\\benchmarks\\" + s[i] + "\\onto.rdf");
            double[][] SimilarityFlood = Finally_SimilarityMatrix.SimilarityFlood("D:\\科研\\benchmarks\\" + s[i] + "\\onto.rdf");
            readWuMatrix.writeFile(SMOA, "101-" + s[i] + "SMOA.txt");
            readWuMatrix.writeFile(Ngram, "101-" + s[i] + "Ngram.txt");
            readWuMatrix.writeFile(Resnik, "101-" + s[i] + "Resnik.txt");
            readWuMatrix.writeFile(Wu, "101-" + s[i] + "Wu.txt" );
            readWuMatrix.writeFile(Instance, "101-" + s[i] + "Instance.txt");
            readWuMatrix.writeFile(SimilarityFlood, "101-" + s[i] + "SimilarityFlood.txt");*/
            /*while(h < 10) {
                try {
                    Main1.Algorithm(string);
                    h++;
                } catch (Exception e) {
                    //System.out.println("异常跳出" + e);
                }
            }*/
            Main1.Algorithm(string);
        }
    }
}
