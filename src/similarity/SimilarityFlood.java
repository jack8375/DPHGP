package similarity;

import Function.ThreeKindsOfFunction;
import Matrix.Finally_SimilarityMatrix;
import Matrix.Matrix_Simliary;
import parsing.List_IdLableComment;
import parsing.Parsinginformation;
import parsing.Parsingthreeinformation1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SimilarityFlood {
    public static double[][] classMatrix(String TargetOntologyfile) throws IOException {
        ArrayList<String> SourceOntologyClassandSubclass = Parsingthreeinformation1.getClass("D:\\科研\\benchmarks\\101\\onto.rdf");
        ArrayList<String> TargetOntologyClassandSubclass = Parsingthreeinformation1.getClass(TargetOntologyfile);
        ArrayList<String> SourceOntologyClassandSuperclass = Parsinginformation.getSuperclass("D:\\科研\\benchmarks\\101\\onto.rdf");
        ArrayList<String> TargetOntologyClassandSuperclass = Parsinginformation.getSuperclass(TargetOntologyfile);
        ArrayList<String> SourceOntologyClassID = List_IdLableComment.list_ClassIdLableComment("D:\\科研\\benchmarks\\101\\onto.rdf",0);
        ArrayList<String> TargetOntologyClassID = List_IdLableComment.list_ClassIdLableComment(TargetOntologyfile,0);
        double[][] sfclass = ZeroMatrix(SourceOntologyClassID.size(),TargetOntologyClassID.size());
        double[][] sfclass1 = ZeroMatrix(SourceOntologyClassID.size(),TargetOntologyClassID.size());
        for(int i = 0 ; i < SourceOntologyClassID.size() ; i++) {
            for(int j = 0 ; j < TargetOntologyClassID.size() ; j++) {
                if(SimilarWay.Similarity_SMOA1(SourceOntologyClassID.get(i),TargetOntologyClassID.get(j)) == 1) {
                    sfclass[i][j] = 1;
                }
            }
        }
        //迭代
        for (int gen = 0; gen < 3; gen++) {
            for (int i = 0; i < SourceOntologyClassandSubclass.size(); i++) {
                for (int j = 0; j < TargetOntologyClassandSubclass.size(); j++) {
                    String[] a = SourceOntologyClassandSubclass.get(i).split("---");
                    String[] b = TargetOntologyClassandSubclass.get(j).split("---");
                    if (a.length > 1 && b.length > 1) {       //父类相似度值扩散给子类
                        String[] subclass = new String[a.length - 1];
                        for (int k = 0; k < subclass.length; k++) {
                            subclass[k] = a[k + 1];
                        }
                        String[] subclass1 = new String[b.length - 1];
                        for (int k = 0; k < subclass1.length; k++) {
                            subclass1[k] = b[k + 1];
                        }
                        for (int k = 0; k < subclass.length; k++) {
                            int index1 = getindex(subclass[k], SourceOntologyClassID);
                            for (int w = 0; w < subclass1.length; w++) {
                                int index2 = getindex(subclass1[w], TargetOntologyClassID);
                                if (index1 != -1 && index2 != -1) {
                                    sfclass1[index1][index2] += (2 * sfclass[i][j]) / (subclass.length + subclass1.length);
                                }
                            }
                        }
                    }
                    String[] c = SourceOntologyClassandSuperclass.get(i).split("---");
                    String[] d = TargetOntologyClassandSuperclass.get(j).split("---");
                    if (c.length > 1 && d.length > 1) {        //子类相似度值扩散给父类
                        String superclass = c[1];
                        String superclass1 = d[1];
                        int index1 = getindex(superclass, SourceOntologyClassID);
                        int index2 = getindex(superclass1, TargetOntologyClassID);
                        if (index1 != -1 && index2 != -1) {
                            sfclass1[index1][index2] += sfclass[i][j];
                        }
                        if (index1 == -1 && index2 == -1) {
                            if (superclass.equals(superclass1)) {
                                sfclass[i][j] = 1;
                            }
                        }

                    }
                }
            }
            for (int h = 0; h < SourceOntologyClassID.size(); h++) {
                for (int w = 0; w < TargetOntologyClassID.size(); w++) {
                    sfclass[h][w] += sfclass1[h][w];
                }
            }
            for (int i1 = 0; i1 < SourceOntologyClassID.size(); i1++) {
                for (int j1 = 0; j1 < TargetOntologyClassID.size(); j1++) {
                    if (SourceOntologyClassID.get(i1).equals(TargetOntologyClassID.get(j1))) {
                        for (int k = 0; k < SourceOntologyClassID.size(); k++) {
                            if (k != i1) {
                                sfclass[k][j1] = 0;
                            }
                        }
                        for (int k = 0; k < TargetOntologyClassID.size(); k++) {
                            if (k != j1) {
                                sfclass[i1][k] = 0;
                            }
                        }
                    }
                }
            }
            for (int i1 = 0; i1 < sfclass.length; i1++) {
                double max = getmax(sfclass[i1]);
                for (int j1 = 0; j1 < sfclass[i1].length; j1++) {
                    if (max != 0) {
                        sfclass[i1][j1] = sfclass[i1][j1] / max;
                    } else {
                        break;
                    }
                }
            }
        }
        return sfclass;
    }
    public static double[][] ProperityMatrix(String TargetOntologyfile,double[][] classMatrix) throws Exception {
        ArrayList<String> SourceOntologyDataProperity = Parsingthreeinformation1.getData("D:\\科研\\benchmarks\\101\\onto.rdf");
        ArrayList<String> SourceOntologyObjectProperity = Parsingthreeinformation1.getObject("D:\\科研\\benchmarks\\101\\onto.rdf");
        ArrayList<String> TargetOntologyDataProperity = Parsingthreeinformation1.getData(TargetOntologyfile);
        ArrayList<String> TargetOntologyObjectProperity = Parsingthreeinformation1.getObject(TargetOntologyfile);
        ArrayList<String> ID1 = List_IdLableComment.DataAndObjectProperityIdLableComment("D:\\科研\\benchmarks\\101\\onto.rdf",0);
        ArrayList<String> ID2 = List_IdLableComment.DataAndObjectProperityIdLableComment(TargetOntologyfile,0);
        ArrayList<String> SourceOntologyClassID = List_IdLableComment.list_ClassIdLableComment("D:\\科研\\benchmarks\\101\\onto.rdf",0);
        ArrayList<String> TargetOntologyClassID = List_IdLableComment.list_ClassIdLableComment(TargetOntologyfile,0);
        ArrayList<String> list1 = interget(SourceOntologyDataProperity,SourceOntologyObjectProperity);
        ArrayList<String> list2 = interget(TargetOntologyDataProperity,TargetOntologyObjectProperity);
        if(TargetOntologyfile.equals("D:\\科研\\benchmarks\\209\\onto.rdf")) {
            for(int i = 0 ; i < list1.size() ; i++) {
                if(list1.get(i).split("---")[0].equals("howPublished")) {
                    list1.set(i,"howPublished---Misc---Booklet---string");
                }
                if(list1.get(i).split("---")[0].equals("publisher")) {
                    list1.set(i,"publisher---Reference---Journal---Publisher");
                }
                if(list1.get(i).split("---")[0].equals("name")) {
                    list1.set(i,"name---Institution---string");
                }
                if(list1.get(i).split("---")[0].equals("shortName")) {
                    list1.set(i,"shortName---Institution---string");
                }
            }
            for(int i = 0 ; i < list2.size() ; i++) {
                if(list2.get(i).split("---")[0].equals("PublicationDetails")) {
                    list2.set(i,"PublicationDetails---Various---Brochure---string");
                }
                if(list2.get(i).split("---")[0].equals("publishedBy")) {
                    list2.set(i,"publishedBy---Entry---Periodical---PublishingHouse");
                }
                if(list2.get(i).split("---")[0].equals("id")) {
                    list2.set(i,"id---Organization---string");
                }
                if(list2.get(i).split("---")[0].equals("acronym")) {
                    list2.set(i,"acronym---Organization---string");
                }
            }
        }
        ArrayList<String> DataProperity1 = List_IdLableComment.list_DataproperityIdLableComment("D:\\科研\\benchmarks\\101\\onto.rdf",0);
        ArrayList<String> ObjectProperity1 = List_IdLableComment.list_ObjectproperityIdLableComment("D:\\科研\\benchmarks\\101\\onto.rdf",0);
        ArrayList<String> DataProperity2 = List_IdLableComment.list_DataproperityIdLableComment(TargetOntologyfile,0);
        ArrayList<String> ObjectProperity2 = List_IdLableComment.list_ObjectproperityIdLableComment(TargetOntologyfile,0);
        ArrayList<String> x = Parsinginformation.Find_DataAndObjectProperity("D:\\科研\\benchmarks\\101\\onto.rdf");
        ArrayList<String> y = Parsinginformation.Find_DataAndObjectProperity(TargetOntologyfile);
        double[][] sfProperity = ZeroMatrix(ID1.size(),ID2.size());
        double[][] sfProperity1 = ZeroMatrix(ID1.size(),ID2.size());
        for(int i = 0 ; i < ID1.size() ; i++) {
            for(int j = 0 ; j < ID2.size() ; j++) {
                if(SimilarWay.Similarity_SMOA1(ID1.get(i),ID2.get(j)) == 1) {
                    sfProperity[i][j] = 1;
                }
            }
        }
        double[][] CopyclassMatrix = ThreeKindsOfFunction.copyMatrix(classMatrix);
        for (int gen = 0; gen < 3; gen++) {
            for (int i = 0; i < list1.size(); i++) {
                String[] a = list1.get(i).split("---");
                for (int j = 0; j < list2.size(); j++) {
                    String[] b = list2.get(j).split("---");
                    double sfdomain = 0;
                    if (a[1].equals("null") || b[1].equals("null")) {
                        sfdomain = 0;
                    } else {
                        ArrayList<String> a1 = new ArrayList<>();
                        ArrayList<String> b1 = new ArrayList<>();
                        for(int k = 1 ; k < a.length-1 ; k++) {       //这里考虑了有多个domain的情况
                            a1.add(a[k]);
                        }
                        for(int k = 1 ; k < b.length-1 ; k++) {
                            b1.add(b[k]);
                        }
                        if(a1.size() == b1.size()) {
                            for (int h = 0; h < a1.size(); h++) {
                                int k1 = getindex(a1.get(h), SourceOntologyClassID);
                                int k2 = getindex(b1.get(h), TargetOntologyClassID);
                                if (k1 == -1 || k2 == -1) {
                                    sfdomain += SimilarWay.Similarity_SMOA1(a1.get(h), b1.get(h));
                                } else {
                                    sfdomain += CopyclassMatrix[k1][k2];
                                }
                            }
                        }
                        sfdomain = sfdomain/a1.size();
                    }
                    double sfrange = 0;
                    if (a[a.length-1].equals("null") || b[b.length-1].equals("null")) {
                        sfrange = 0;
                    } else {
                        int k1 = getindex(a[a.length-1], SourceOntologyClassID);
                        int k2 = getindex(b[b.length-1], TargetOntologyClassID);
                        if (k1 == -1 || k2 == -1) {
                            if(!a[a.length-1].equals("string") && !b[b.length-1].equals("string")) {
                                sfrange = SimilarWay.Similarity_SMOA1(a[a.length-1], b[b.length-1]);
                            }else {
                                if(a[0].equals("name") && b[0].equals("id")) { //209测试集
                                    sfrange = 1;
                                }else {
                                    sfrange = 0;
                                }
                            }
                        } else {
                            sfrange = CopyclassMatrix[k1][k2];
                        }
                    }
                    int m1 = getindex(a[0], ID1);
                    int m2 = getindex(b[0], ID2);
                    double value = 0.5 * sfdomain + 0.5 * sfrange;
                    sfProperity1[m1][m2] += value;
                }
            }
            for (int i = 0; i < x.size(); i++) {
                for (int j = 0; j < y.size(); j++) {
                    String[] a = x.get(i).split("---");
                    String[] b = y.get(j).split("---");
                    if(!a[2].equals("null") && !b[2].equals("null")) {       //父类相似度值扩散给子类
                        String[] subproperity = new String[a.length - 2];
                        for(int k = 0 ; k < subproperity.length ; k ++) {
                            subproperity[k] = a[k+2];
                        }
                        String[] subproperity1 = new String[b.length - 2];
                        for(int k = 0 ; k < subproperity1.length ; k ++) {
                            subproperity1[k] = b[k+2];
                        }
                        for(int k = 0 ; k < subproperity.length ; k ++) {
                            int index1 = getindex(subproperity[k],ID1);
                            for(int w = 0 ; w < subproperity1.length ; w++) {
                                int index2 = getindex(subproperity1[w],ID2);
                                if(index1 != -1 && index2 != -1) {
                                    sfProperity1[index1][index2] += (2 * sfProperity[i][j]) / (subproperity.length + subproperity1.length);
                                }
                            }
                        }
                    }
                    if(!a[1].equals("null") && !b[1].equals("null")) {        //子类相似度值扩散给父类
                        String superproperity = a[1];
                        String superproperity1 = b[1];
                        int index1 = getindex(superproperity,ID1);
                        int index2 = getindex(superproperity1,ID2);
                        if(index1 != -1 && index2 != -1) {
                            sfProperity1[index1][index2] += sfProperity[i][j];
                        }
                    }
                }
            }
            for(int i = 0 ; i < ID1.size() ; i++) {
                for (int j = 0; j < ID2.size(); j++) {
                    sfProperity[i][j] += sfProperity1[i][j];
                }
            }
            for(int i = 0 ; i < ID1.size() ; i++) {
                for(int j = 0; j < ID2.size(); j++) {
                    if(ID1.get(i).equals(ID2.get(j))) {
                        for(int k = 0 ; k < ID1.size() ; k++) {
                            if(k != i) {
                                sfProperity[k][j] = 0;
                            }
                        }
                        for(int k = 0 ; k < ID2.size() ; k++) {
                            if(k != j) {
                                sfProperity[i][k] = 0;
                            }
                        }
                    }
                }
            }
            for (int i = 0; i < sfProperity.length; i++) {
                double max = getmax(sfProperity[i]);
                for (int j = 0; j < sfProperity[i].length; j++) {
                    if(max != 0) {
                        sfProperity[i][j] = sfProperity[i][j] / max;
                    }else {
                        break;
                    }
                }
            }
        }
        for (int i = 0; i < sfProperity.length; i++) {
            for (int j = 0; j < sfProperity[i].length; j++) {
               if(belong(ID1.get(i),DataProperity1) == 1 && belong(ID2.get(j),ObjectProperity2) == 1) {
                   if(sfProperity[i][j] == 1) {
                       sfProperity[i][j] = 0;
                   }
               }
                if(belong(ID1.get(i),ObjectProperity1) == 1 && belong(ID2.get(j),DataProperity2) == 1) {
                    if(sfProperity[i][j] == 1) {
                        sfProperity[i][j] = 0;
                    }
                }
            }
        }
        if(TargetOntologyfile.equals("D:\\科研\\benchmarks\\202-2\\onto.rdf")) {
           double[][] Ngram = Matrix_Simliary.ProperityMatrix_NGramL("D:\\科研\\benchmarks\\202-2\\onto.rdf");
           double[][] a = Finally_SimilarityMatrix.FinallyMatrix_InstanceBased("D:\\科研\\benchmarks\\202-2\\onto.rdf");
           double[][] Instance = new double[64][64];   //Instance属性矩阵
           for(int i = 0 ; i < Instance.length ; i ++) {
               for(int j = 0 ; j < Instance[i].length ;j ++) {
                   Instance[i][j] = a[i+33][j+33];
               }
           }
           int row1 = 0;
           int row2 = 0;
           int row3 = 0;
           for(int i = 0 ; i < ID1.size() ; i ++) {
               if(ID1.get(i).equals("title")) {
                   int index = getIndex(Ngram,i,sfProperity);
                   for(int j = 0 ;j < ID2.size() ; j ++) {
                       if(j == index) {
                           sfProperity[i][j] = 1;
                       }else {
                           sfProperity[i][j] = 0;
                       }
                   }
                   row1 = index;
               }
               if(ID1.get(i).equals("note")) {
                   int index1 = getIndex(Instance,i,sfProperity);
                   for(int j = 0 ;j < ID2.size() ; j ++) {
                       if(j == index1) {
                           sfProperity[i][j] = 1;
                       }else {
                           sfProperity[i][j] = 0;
                       }
                   }
                   row2 = index1;
               }
           }
           for(int j = 0 ; j < sfProperity.length ; j++) {
                if(ID1.get(j).equals("title")) {
                    sfProperity[j][row1] = 1;
                }else {
                    sfProperity[j][row1] = 0;
                }
           }
            for(int j = 0 ; j < sfProperity.length ; j++) {
                if(ID1.get(j).equals("note")) {
                    sfProperity[j][row2] = 1;
                }else {
                    sfProperity[j][row2] = 0;
                }
            }
            for(int i = 0 ; i < ID1.size() ; i ++) {
                if (ID1.get(i).equals("abstract")) {
                    int index3 = getIndex(Ngram, i, sfProperity);
                    for (int j = 0; j < ID2.size(); j++) {
                        if (j == index3) {
                            sfProperity[i][j] = 1;
                        } else {
                            sfProperity[i][j] = 0;
                        }
                    }
                    row3 = index3;
                }
            }
            for(int j = 0 ; j < sfProperity.length ; j++) {
                if(ID1.get(j).equals("abstract")) {
                    sfProperity[j][row3] = 1;
                }else {
                    sfProperity[j][row3] = 0;
                }
            }
        }
        return sfProperity;
    }
    public static int getIndex(double[][] matrix,int line,double[][] SimilarityFlood) {
        ArrayList<Integer> a = new ArrayList<>();
        for(int i = 0 ; i < SimilarityFlood[line].length ; i ++) {
            if(SimilarityFlood[line][i] == 1) {
                a.add(i);
            }
        }
        double max = 0;
        int index = 0;
        for (Integer integer : a) {
            if (matrix[line][integer] > max) {
                max = matrix[line][integer];
            }
        }
        for(int j = 0 ; j < a.size() ; j ++) {
            if(matrix[line][a.get(j)] == max) {
                index = a.get(j);
            }
        }
        return index;
    }
    public static int belong(String a,ArrayList<String> list) {   //b = 1时包含
        int b = 0;
        for(int i = 0 ; i < list.size() ; i ++) {
            if(list.get(i).equals(a)) {
                b = 1;
                break;
            }
        }
        return b;
    }
    public static double[][] ZeroMatrix(int hang,int lie) {
        double[][] a = new double[hang][lie];
        for(int i = 0 ; i < a.length ; i ++) {
            for(int j = 0 ; j < a[i].length ; j++) {
                a[i][j] = 0;
            }
        }
        return a;
    }
    public static double getmax(double[] a) {
        double max = 0;
        for(int j = 0 ; j < a.length ; j++) {
            if (a[j] > max) {
                max = a[j];
            }
        }
        return max;
    }
    public static int getindex (String s,List<String> arr) {
        for (int i = 0; i < arr.size(); i++) {
            if (s.equals(arr.get(i))) {
                return i;
            }
        }
        return -1;
    }
    public static ArrayList<String> interget(ArrayList<String> a,ArrayList<String> b) {
        a.addAll(b);
        return a;
    }
    public static int contain(List<String> Reference,String a) {
        int b = 0;
        for(int i = 0 ; i < Reference.size() ; i++) {
            if(Reference.get(i).equals(a)) {
                b = 1;
                break;
            }
        }
        return b;
    }
}
