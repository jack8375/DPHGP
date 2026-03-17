package Function;

import DPHGP.readWuMatrix;
import Matrix.Finally_SimilarityMatrix;
import parsing.classProperity;

import java.io.IOException;
import java.util.ArrayList;

public class test1 {
    public static void main(String[] args) throws IOException {
        double[][] SMOA = Finally_SimilarityMatrix.FinallyMatrix_SMOA("D:\\科研\\benchmarks\\237\\onto.rdf");
        double[][] Ngram = readWuMatrix.read("101-237Ngram", SMOA.length, SMOA[0].length);
        double[][] Resnik = readWuMatrix.read("101-237Resnik", SMOA.length, SMOA[0].length);
        double[][] Wu = readWuMatrix.read("101-237Wu", SMOA.length, SMOA[0].length);
        double[][] Instance = readWuMatrix.read("101-237Instance", SMOA.length, SMOA[0].length);
        double[][] SimilarityFlood = readWuMatrix.read("101-237SimilarityFlood", SMOA.length, SMOA[0].length);
        ArrayList<String> SourceOntology =  classProperity.classAndProperity("D:\\科研\\benchmarks\\101\\onto.rdf");
        ArrayList<String> TargetOntology =  classProperity.classAndProperity("D:\\科研\\benchmarks\\237\\onto.rdf");
        int num = 0;
        int num1 = 0;
        for(int i = 0 ; i < SourceOntology.size() ; i ++) {
            if(SourceOntology.get(i).equals("abstract")) {
                num = i;
            }
        }
        for(int j = 0 ; j < TargetOntology.size() ; j ++) {
            if(TargetOntology.get(j).equals("dsqndsz")) {
                num1 = j;
            }
        }
        System.out.println(num);
        System.out.println(num1);
        System.out.println(SMOA[num][num1]);
        System.out.println(Ngram[num][num1]);
        System.out.println(Resnik[num][num1]);
        System.out.println(Wu[num][num1]);
        System.out.println(Instance[num][num1]);
        System.out.println(SimilarityFlood[num][num1]);
    }
}
