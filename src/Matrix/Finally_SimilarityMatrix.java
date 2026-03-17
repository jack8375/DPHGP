package Matrix;

import parsing.List_IdLableComment;
import parsing.ParsingOfInstance;
import similarity.InstanceBasedSimilarityMeasure;
import similarity.SimilarityFlood;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Finally_SimilarityMatrix {
    //将SMOA相似度量方法生成的class矩阵与properity矩阵进行对角合成
    public static double[][] FinallyMatrix_SMOA(String file1) throws IOException {
        ArrayList<String> list  = List_IdLableComment.list_ClassIdLableComment("D:\\科研\\benchmarks\\101\\onto.rdf", 0);   //将源本体和目标本体class的id分别放到集合中
        ArrayList<String> list1 = List_IdLableComment.list_ClassIdLableComment(file1,0);
        ArrayList<String> list2 = List_IdLableComment.DataAndObjectProperityIdLableComment("D:\\科研\\benchmarks\\101\\onto.rdf", 0);   //将源本体和目标本体属性的id分别放到集合中
        ArrayList<String> list3 = List_IdLableComment.DataAndObjectProperityIdLableComment(file1,0);
        double[][] Matrix = Matrix_Simliary.ClassMatrix_SMOA(file1);
        double[][] Matrix1 = Matrix_Simliary.ProperityMatrix_SMOA(file1);
        double[][] Matrix2 = new double[list.size()+list2.size()][list1.size()+list3.size()];
        for(int i = 0; i < list.size()+list2.size(); i ++) {
            if(i < list.size()) {
                for(int j = 0; j < list1.size()+list3.size();j ++) {
                    if(j < list1.size()) {
                        Matrix2[i][j] = Matrix[i][j];
                    }
                    else {
                        Matrix2[i][j] = 0.0;
                    }
                }
            }
            else {
                for(int j = 0; j < list1.size()+list3.size();j ++) {
                    if(j < list1.size()) {
                        Matrix2[i][j] = 0.0;
                    }
                    else {
                        Matrix2[i][j] = Matrix1[i-list.size()][j-list1.size()];
                    }
                }
            }
        }
        return Matrix2;
        /*for(int i = 0; i < list.size()+list2.size(); i ++) {
            for(int j = 0; j < list1.size()+list3.size();j ++) {
                System.out.print(String.format("%.2f",Matrix2[i][j])+" ");
            }
            System.out.println();
        }*/
    }
    //将NGramL相似度量方法生成的class矩阵与properity矩阵进行对角合成
    public static double[][] FinallyMatrix_NGramL(String file1) throws IOException {
        ArrayList<String> list = List_IdLableComment.list_ClassIdLableComment("D:\\科研\\benchmarks\\101\\onto.rdf", 0);   //将源本体和目标本体class的id分别放到集合中
        ArrayList<String> list1 = List_IdLableComment.list_ClassIdLableComment(file1, 0);
        ArrayList<String> list2 = List_IdLableComment.DataAndObjectProperityIdLableComment("D:\\科研\\benchmarks\\101\\onto.rdf", 0);   //将源本体和目标本体属性的id分别放到集合中
        ArrayList<String> list3 = List_IdLableComment.DataAndObjectProperityIdLableComment(file1, 0);
        double[][] Matrix = Matrix_Simliary.ClassMatrix_NGramL(file1);
        double[][] Matrix1 = Matrix_Simliary.ProperityMatrix_NGramL(file1);
        double[][] Matrix2 = new double[list.size() + list2.size()][list1.size() + list3.size()];
        for (int i = 0; i < list.size() + list2.size(); i++) {
            if (i < list.size()) {
                for (int j = 0; j < list1.size() + list3.size(); j++) {
                    if (j < list1.size()) {
                        Matrix2[i][j] = Matrix[i][j];
                    } else {
                        Matrix2[i][j] = 0.0;
                    }
                }
            } else {
                for (int j = 0; j < list1.size() + list3.size(); j++) {
                    if (j < list1.size()) {
                        Matrix2[i][j] = 0.0;
                    } else {
                        Matrix2[i][j] = Matrix1[i - list.size()][j - list1.size()];
                    }
                }
            }
        }
        return Matrix2;
    }
    //将wuAndPalmer相似度量方法生成的class矩阵与properity矩阵进行对角合成
    public static double[][] FinallyMatrix_wuAndPalmer(String file1) throws IOException {
        ArrayList<String> list = List_IdLableComment.list_ClassIdLableComment("D:\\科研\\benchmarks\\101\\onto.rdf", 0);   //将源本体和目标本体class的id分别放到集合中
        ArrayList<String> list1 = List_IdLableComment.list_ClassIdLableComment(file1, 0);
        ArrayList<String> list2 = List_IdLableComment.DataAndObjectProperityIdLableComment("D:\\科研\\benchmarks\\101\\onto.rdf", 0);   //将源本体和目标本体属性的id分别放到集合中
        ArrayList<String> list3 = List_IdLableComment.DataAndObjectProperityIdLableComment(file1, 0);
        double[][] Matrix = Matrix_Simliary.ClassMatrix_wuAndPalmer(file1);
        double[][] Matrix1 = Matrix_Simliary.ProperityMatrix_wuAndPalmer(file1);
        double[][] Matrix2 = new double[list.size() + list2.size()][list1.size() + list3.size()];
        for (int i = 0; i < list.size() + list2.size(); i++) {
            if (i < list.size()) {
                for (int j = 0; j < list1.size() + list3.size(); j++) {
                    if (j < list1.size()) {
                        Matrix2[i][j] = Matrix[i][j];
                    } else {
                        Matrix2[i][j] = 0.0;
                    }
                }
            } else {
                for (int j = 0; j < list1.size() + list3.size(); j++) {
                    if (j < list1.size()) {
                        Matrix2[i][j] = 0.0;
                    } else {
                        Matrix2[i][j] = Matrix1[i - list.size()][j - list1.size()];
                    }
                }
            }
        }
        return Matrix2;
    }
    ////将Resnik相似度量方法生成的class矩阵与properity矩阵进行对角合成
    public static double[][] FinallyMatrix_Resnik(String file1) throws IOException {
        ArrayList<String> list = List_IdLableComment.list_ClassIdLableComment("D:\\科研\\benchmarks\\101\\onto.rdf", 0);   //将源本体和目标本体class的id分别放到集合中
        ArrayList<String> list1 = List_IdLableComment.list_ClassIdLableComment(file1, 0);
        ArrayList<String> list2 = List_IdLableComment.DataAndObjectProperityIdLableComment("D:\\科研\\benchmarks\\101\\onto.rdf", 0);   //将源本体和目标本体属性的id分别放到集合中
        ArrayList<String> list3 = List_IdLableComment.DataAndObjectProperityIdLableComment(file1, 0);
        double[][] Matrix = Matrix_Simliary.ClassMatrix_Resnik(file1);
        double[][] Matrix1 = Matrix_Simliary.ProperityMatrix_Resnik(file1);
        double[][] Matrix2 = new double[list.size() + list2.size()][list1.size() + list3.size()];
        for (int i = 0; i < list.size() + list2.size(); i++) {
            if (i < list.size()) {
                for (int j = 0; j < list1.size() + list3.size(); j++) {
                    if (j < list1.size()) {
                        Matrix2[i][j] = Matrix[i][j];
                    } else {
                        Matrix2[i][j] = 0.0;
                    }
                }
            } else {
                for (int j = 0; j < list1.size() + list3.size(); j++) {
                    if (j < list1.size()) {
                        Matrix2[i][j] = 0.0;
                    } else {
                        Matrix2[i][j] = Matrix1[i - list.size()][j - list1.size()];
                    }
                }
            }
        }
        return Matrix2;
    }

    public static double[][] FinallyMatrix_InstanceBased(String file1) throws IOException {
        ArrayList<ArrayList<String>> list = ParsingOfInstance.getins("D:\\科研\\benchmarks\\101\\onto.rdf");
        ArrayList<ArrayList<String>> list1 = ParsingOfInstance.getins(file1);
        ArrayList<ArrayList<String>> list2 = InstanceBasedSimilarityMeasure.getins(list , list1);  //由instance-based度量方法得到class（properity）中的部分对齐

        ArrayList<String> a = List_IdLableComment.list_ClassIdLableComment("D:\\科研\\benchmarks\\101\\onto.rdf", 0);   //将源本体和目标本体class的id分别放到集合中
        ArrayList<String> b = List_IdLableComment.list_ClassIdLableComment(file1, 0);
        ArrayList<String> c = List_IdLableComment.DataAndObjectProperityIdLableComment("D:\\科研\\benchmarks\\101\\onto.rdf", 0);   //将源本体和目标本体属性的id分别放到集合中
        ArrayList<String> d = List_IdLableComment.DataAndObjectProperityIdLableComment(file1, 0);
        double[][] Matrix = new double[a.size() + c.size()][b.size() + d.size()];

        ArrayList<String> list5 = new ArrayList<>();
        list5.addAll(list2.get(0));
        list5.addAll(list2.get(1));
        for (int i = 0; i < a.size() + c.size(); i++) {
            if (i < a.size()) {
                for (int j = 0; j < b.size() + d.size(); j++) {
                    if (j < b.size()) {
                        String s1 = a.get(i) +"---" + b.get(j);
                        if(list5.contains(s1)) {
                            Matrix[i][j] = 1.00;
                        } else {
                            Matrix[i][j] = 0.00;
                        }
                    } else {
                        String s1 = a.get(i) +"---" + d.get(j-b.size());
                        if(list5.contains(s1)) {
                            Matrix[i][j] = 1.00;
                        } else {
                            Matrix[i][j] = 0.00;
                        }
                    }
                }
            } else {
                for (int j = 0; j < b.size() + d.size(); j++) {
                    if (j < b.size()) {
                        String s1 = c.get(i-a.size()) +"---" + b.get(j);
                        if(list5.contains(s1)) {
                            Matrix[i][j] = 1.00;
                        } else {
                            Matrix[i][j] = 0.00;
                        }
                    } else {
                        String s2 = c.get(i-a.size()) +"---" + d.get(j-b.size());
                        if(list5.contains(s2)) {
                            Matrix[i][j] = 1.00;
                        }else {
                            Matrix[i][j] = 0.00;
                        }
                    }
                }
            }
        }
        return Matrix;
    }
    public static double[][] SimilarityFlood(String file1) throws Exception {
        ArrayList<String> list = List_IdLableComment.list_ClassIdLableComment("D:\\科研\\benchmarks\\101\\onto.rdf", 0);   //将源本体和目标本体class的id分别放到集合中
        ArrayList<String> list1 = List_IdLableComment.list_ClassIdLableComment(file1, 0);
        ArrayList<String> list2 = List_IdLableComment.DataAndObjectProperityIdLableComment("D:\\科研\\benchmarks\\101\\onto.rdf", 0);   //将源本体和目标本体属性的id分别放到集合中
        ArrayList<String> list3 = List_IdLableComment.DataAndObjectProperityIdLableComment(file1, 0);
        //double[][] Matrix = Matrix_Simliary.ClassMatrix_SMOA(file1);
        double[][] Matrix = SimilarityFlood.classMatrix(file1);
        double[][] Matrix1 = SimilarityFlood.ProperityMatrix(file1, Matrix);
        double[][] Matrix2 = new double[list.size() + list2.size()][list1.size() + list3.size()];
        for (int i = 0; i < list.size() + list2.size(); i++) {
            if (i < list.size()) {
                for (int j = 0; j < list1.size() + list3.size(); j++) {
                    if (j < list1.size()) {
                        Matrix2[i][j] = Matrix[i][j];
                    } else {
                        Matrix2[i][j] = 0.0;
                    }
                }
            } else {
                for (int j = 0; j < list1.size() + list3.size(); j++) {
                    if (j < list1.size()) {
                        Matrix2[i][j] = 0.0;
                    } else {
                        Matrix2[i][j] = Matrix1[i - list.size()][j - list1.size()];
                    }
                }
            }
        }
        //jzonly(Matrix2);  //208测试集提高结果用
        return Matrix2;
    }
    private static double[][] jzonly(double[][] sfclass1) {
        // 用于存放每行每列最大值的行列索引
        List<Integer> allrowIndex = new ArrayList<>();
        List<Integer> allcolumeIndex = new ArrayList<>();

        //因为它的结构相似度值只是一个相对的值,所以将每行每列的最大值转换为1,表示匹配
        for (int i = 0; i < sfclass1.length; i++) {
            double maxOfRow = findMaxOfRow(i, sfclass1); // 第i行的最大值
            for (int j = 0; j < sfclass1[i].length; j++) {
                double maxOfColume = findMaxOfColume(j, sfclass1); // 第j列的最大值
                if ((sfclass1[i][j] == maxOfRow) && (sfclass1[i][j] == maxOfColume)
                        && (sfclass1[i][j] != 0)) { // 若此元素在该行与该列中都是最大值，则我们认为其为最佳配对,这个不动
                    allrowIndex.add(i);
                    allcolumeIndex.add(j);
                }
            }
        }
        for(int i = 0 ; i < allrowIndex.size() ; i++) {
            for(int j = 0 ; j < sfclass1[0].length ; j ++) {
                if(j != allcolumeIndex.get(i)) {
                    sfclass1[allrowIndex.get(i)][j] = 0;
                }
            }
        }
        for(int i = 0 ; i < allcolumeIndex.size() ; i++) {
            for(int j = 0 ; j < sfclass1.length ; j ++) {
                if(j != allrowIndex.get(i)) {
                    sfclass1[j][allcolumeIndex.get(i)] = 0;
                }
            }
        }
        return sfclass1;
    }
    /**
     * i代表第i行
     * @param similarArr 相似度二维数组
     * @return 输出该二维数组第i行中最大的一个元素
     */
    public static double findMaxOfRow(int i, double[][] similarArr) {

        double max = findMaxOfArr(similarArr[i]);
        return max;
    }

    /**
     * @return 输出该二维数组第j列中最大的一个元素
     */
    public static double findMaxOfColume(int j, double[][] similarArr) {

        double[] columeArr = new double[similarArr.length]; // 此数组用于存放相似度二维数组中的第j列元素
        for (int i = 0; i < columeArr.length; i++) {
            columeArr[i] = similarArr[i][j];
        }
        double max = findMaxOfArr(columeArr);
        return max;
    }
    /**
     * 找到一个数组中的最大值
     */
    public static double findMaxOfArr(double[] arr) {

        double max = arr[0];
        for (int i = 0; i < arr.length; i++) {
            if (max < arr[i]) {
                max = arr[i];
            }
        }
        return max;
    }
}
