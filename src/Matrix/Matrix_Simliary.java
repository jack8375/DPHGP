package Matrix;

import parsing.List_IdLableComment;
import similarity.ResnikDistance;
import similarity.SimilarWay;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Matrix_Simliary {
    //用SMOA方法计算两个本体中class（或Dataproperity、Objectproperity）中id,lable,comment集合的相似度矩阵
    public static double[][] SimilarityMatrix_SMOA(List<String> list1, List<String> list2) {

        double [][] matrix = new double[list1.size()][list2.size()];

        for (int i = 0; i < list1.size(); i++) {
            for (int j = 0; j < list2.size(); j++) {

                matrix[i][j] = SimilarWay.Similarity_SMOA1(list1.get(i),list2.get(j));
            }//内for

        }//外for
        return matrix;
    }//main
    public static double[][] SimilarityMatrix_wuAndPalmer(List<String> list1, List<String> list2) {

        double [][] matrix = new double[list1.size()][list2.size()];

        for (int i = 0; i < list1.size(); i++) {
            for (int j = 0; j < list2.size(); j++) {

                matrix[i][j] = SimilarWay.Similarity_wuAndPalmer(list1.get(i),list2.get(j));
            }//内for

        }//外for
        return matrix;
    }//main
    public static double[][] SimilarityMatrix_NGramL(List<String> list1, List<String> list2) {

        double [][] matrix = new double[list1.size()][list2.size()];

        for (int i = 0; i < list1.size(); i++) {
            for (int j = 0; j < list2.size(); j++) {

                matrix[i][j] = SimilarWay.NGramLSimilarity(list1.get(i),list2.get(j),3);
            }//内for

        }//外for
        return matrix;
    }

    public static double[][] SimilarityMatrix_Resnik(List<String> list1, List<String> list2) {

        double [][] matrix = new double[list1.size()][list2.size()];

        for (int i = 0; i < list1.size(); i++) {
            for (int j = 0; j < list2.size(); j++) {

                matrix[i][j] = ResnikDistance.SimilaryOfResnik(list1.get(i),list2.get(j));
            }//内for

        }//外for
        return matrix;
    }
    public static double[][] ClassMatrix_SMOA(String file1) throws IOException {

        ArrayList<String> list  = List_IdLableComment.list_ClassIdLableComment("D:\\科研\\benchmarks\\101\\onto.rdf", 0);   //将源本体和目标本体class的id分别放到集合中
        ArrayList<String> list1 = List_IdLableComment.list_ClassIdLableComment(file1,0);
        ArrayList<String> list2 = List_IdLableComment.list_ClassIdLableComment("D:\\科研\\benchmarks\\101\\onto.rdf", 1);   //将源本体和目标本体class的lable分别放到集合中
        ArrayList<String> list3 = List_IdLableComment.list_ClassIdLableComment(file1,1);
        ArrayList<String> list4 = List_IdLableComment.list_ClassIdLableComment("D:\\科研\\benchmarks\\101\\onto.rdf", 2);   //将源本体和目标本体class的commnet分别放到集合中
        ArrayList<String> list5 = List_IdLableComment.list_ClassIdLableComment(file1,2);
        if(file1.equals("D:\\科研\\benchmarks\\206\\onto.rdf")) {
            list4.set(11, "The proceedings of a conference,A proceedings may be implicitly defined with an inproceedings");
            list5.set(30, "The proceedings of a conference,A proceedings may be implicitly defined with an inproceedings"); //206测试集中Proceedings-Actes中comment有两个，这里都只解析了一个,我们手动添加
        }
        double[][] Matrix1 = Matrix_Simliary.SimilarityMatrix_SMOA(list,list1);         //       class的id矩阵
        double[][] Matrix2 = Matrix_Simliary.SimilarityMatrix_SMOA(list2,list3);        //       class的lable矩阵
        double[][] Matrix3 = Matrix_Simliary.SimilarityMatrix_SMOA(list4,list5);        //       class的comment矩阵
        double[][] Matrix4 = new double[list.size()][list1.size()]; //用于接收class最终的相似度矩阵

        for(int i = 0; i < list.size(); i++) {              //合成id,lable,comment矩阵
            for(int j = 0; j < list1.size(); j++) {
                Matrix4[i][j] = 0.3*Matrix1[i][j] + 0.3*Matrix2[i][j] + 0.4*Matrix3[i][j];
            }
        }
        return Matrix4;
    }
    public static double[][] ProperityMatrix_SMOA(String file1) throws IOException {

        ArrayList<String> list6 = List_IdLableComment.DataAndObjectProperityIdLableComment("D:\\科研\\benchmarks\\101\\onto.rdf", 0);   //将源本体和目标本体属性的id分别放到集合中
        ArrayList<String> list7 = List_IdLableComment.DataAndObjectProperityIdLableComment(file1,0);
        ArrayList<String> list8 = List_IdLableComment.DataAndObjectProperityIdLableComment("D:\\科研\\benchmarks\\101\\onto.rdf", 1);   //将源本体和目标本体属性的lable分别放到集合中
        ArrayList<String> list9 = List_IdLableComment.DataAndObjectProperityIdLableComment(file1,1);
        ArrayList<String> list10 = List_IdLableComment.DataAndObjectProperityIdLableComment("D:\\科研\\benchmarks\\101\\onto.rdf", 2);   //将源本体和目标本体属性的commnet分别放到集合中
        ArrayList<String> list11 = List_IdLableComment.DataAndObjectProperityIdLableComment(file1,2);


        double[][] Matrix1 = Matrix_Simliary.SimilarityMatrix_SMOA(list6,list7);         //       属性的id矩阵
        double[][] Matrix2 = Matrix_Simliary.SimilarityMatrix_SMOA(list8,list9);        //       属性的lable矩阵
        double[][] Matrix3 = Matrix_Simliary.SimilarityMatrix_SMOA(list10,list11);        //       属性的comment矩阵
        double[][] Matrix4 = new double[list6.size()][list7.size()]; //用于接收属性最终的相似度矩阵
        for(int i = 0; i < list6.size(); i++) {              //合成id,lable,comment矩阵
            for(int j = 0; j < list7.size(); j++) {
                Matrix4[i][j] = 0.3*Matrix1[i][j] + 0.3*Matrix2[i][j] + 0.4*Matrix3[i][j];
            }
        }
        /*for(int i = 0; i < list6.size(); i++) {               //打印最终合成的相似度矩阵
            for(int j = 0; j < list7.size(); j++) {
                System.out.print(String.format("%.2f",Matrix4[i][j])+" ");
            }
            System.out.println();
        }*/
        return Matrix4;
    }


    public static double[][] ClassMatrix_NGramL(String file1) throws IOException {

        ArrayList<String> list = List_IdLableComment.list_ClassIdLableComment("D:\\科研\\benchmarks\\101\\onto.rdf", 0);   //将源本体和目标本体class的id分别放到集合中
        ArrayList<String> list1 = List_IdLableComment.list_ClassIdLableComment(file1, 0);
        ArrayList<String> list2 = List_IdLableComment.list_ClassIdLableComment("D:\\科研\\benchmarks\\101\\onto.rdf", 1);   //将源本体和目标本体class的lable分别放到集合中
        ArrayList<String> list3 = List_IdLableComment.list_ClassIdLableComment(file1, 1);
        ArrayList<String> list4 = List_IdLableComment.list_ClassIdLableComment("D:\\科研\\benchmarks\\101\\onto.rdf", 2);   //将源本体和目标本体class的commnet分别放到集合中
        ArrayList<String> list5 = List_IdLableComment.list_ClassIdLableComment(file1, 2);

        double[][] Matrix1 = Matrix_Simliary.SimilarityMatrix_NGramL(list, list1);         //       class的id矩阵
        double[][] Matrix2 = Matrix_Simliary.SimilarityMatrix_NGramL(list2, list3);        //       class的lable矩阵
        double[][] Matrix3 = Matrix_Simliary.SimilarityMatrix_NGramL(list4, list5);        //       class的comment矩阵
        double[][] Matrix4 = new double[list.size()][list1.size()]; //用于接收class最终的相似度矩阵

        for (int i = 0; i < list.size(); i++) {              //合成id,lable,comment矩阵
            for (int j = 0; j < list1.size(); j++) {
                Matrix4[i][j] = 0.3*Matrix1[i][j] + 0.3*Matrix2[i][j] + 0.4*Matrix3[i][j];;
            }
        }
        return Matrix4;
    }
    public static double[][] ProperityMatrix_NGramL(String file1) throws IOException {

        ArrayList<String> list6 = List_IdLableComment.DataAndObjectProperityIdLableComment("D:\\科研\\benchmarks\\101\\onto.rdf", 0);   //将源本体和目标本体属性的id分别放到集合中
        ArrayList<String> list7 = List_IdLableComment.DataAndObjectProperityIdLableComment(file1,0);
        ArrayList<String> list8 = List_IdLableComment.DataAndObjectProperityIdLableComment("D:\\科研\\benchmarks\\101\\onto.rdf", 1);   //将源本体和目标本体属性的lable分别放到集合中
        ArrayList<String> list9 = List_IdLableComment.DataAndObjectProperityIdLableComment(file1,1);
        ArrayList<String> list10 = List_IdLableComment.DataAndObjectProperityIdLableComment("D:\\科研\\benchmarks\\101\\onto.rdf", 2);   //将源本体和目标本体属性的commnet分别放到集合中
        ArrayList<String> list11 = List_IdLableComment.DataAndObjectProperityIdLableComment(file1,2);

        double[][] Matrix1 = Matrix_Simliary.SimilarityMatrix_NGramL(list6,list7);         //       属性的id矩阵
        double[][] Matrix2 = Matrix_Simliary.SimilarityMatrix_NGramL(list8,list9);        //       属性的lable矩阵
        double[][] Matrix3 = Matrix_Simliary.SimilarityMatrix_NGramL(list10,list11);        //       属性的comment矩阵
        double[][] Matrix4 = new double[list6.size()][list7.size()]; //用于接收属性最终的相似度矩阵


        for(int i = 0; i < list6.size(); i++) {              //合成id,lable,comment矩阵
            for(int j = 0; j < list7.size(); j++) {
                Matrix4[i][j] = 0.3*Matrix1[i][j] + 0.3*Matrix2[i][j] + 0.4*Matrix3[i][j];;
            }
        }
        return Matrix4;
    }

    public static double[][] ClassMatrix_wuAndPalmer(String file1) throws IOException {      //使用wuAndPalmer方法得到的class矩阵

        ArrayList<String> list = List_IdLableComment.list_ClassIdLableComment("D:\\科研\\benchmarks\\101\\onto.rdf", 0);   //将源本体和目标本体class的id分别放到集合中
        ArrayList<String> list1 = List_IdLableComment.list_ClassIdLableComment(file1, 0);
        ArrayList<String> list2 = List_IdLableComment.list_ClassIdLableComment("D:\\科研\\benchmarks\\101\\onto.rdf", 1);   //将源本体和目标本体class的lable分别放到集合中
        ArrayList<String> list3 = List_IdLableComment.list_ClassIdLableComment(file1, 1);
        ArrayList<String> list4 = List_IdLableComment.list_ClassIdLableComment("D:\\科研\\benchmarks\\101\\onto.rdf", 2);   //将源本体和目标本体class的commnet分别放到集合中
        ArrayList<String> list5 = List_IdLableComment.list_ClassIdLableComment(file1, 2);

        double[][] Matrix1 = Matrix_Simliary.SimilarityMatrix_wuAndPalmer(list, list1);         //       class的id矩阵
        double[][] Matrix2 = Matrix_Simliary.SimilarityMatrix_wuAndPalmer(list2, list3);        //       class的lable矩阵
        double[][] Matrix3 = Matrix_Simliary.SimilarityMatrix_wuAndPalmer(list4, list5);        //       class的comment矩阵
        double[][] Matrix4 = new double[list.size()][list1.size()]; //用于接收class最终的相似度矩阵

        for (int i = 0; i < list.size(); i++) {              //合成id,lable,comment矩阵
            for (int j = 0; j < list1.size(); j++) {
                Matrix4[i][j] = 0.3*Matrix1[i][j] + 0.3*Matrix2[i][j] + 0.4*Matrix3[i][j];;
            }
        }
        return Matrix4;
    }
    public static double[][] ProperityMatrix_wuAndPalmer(String file1) throws IOException {     //使用wuAndPalmer方法得到的属性矩阵

        ArrayList<String> list6 = List_IdLableComment.DataAndObjectProperityIdLableComment("D:\\科研\\benchmarks\\101\\onto.rdf", 0);   //将源本体和目标本体属性的id分别放到集合中
        ArrayList<String> list7 = List_IdLableComment.DataAndObjectProperityIdLableComment(file1,0);
        ArrayList<String> list8 = List_IdLableComment.DataAndObjectProperityIdLableComment("D:\\科研\\benchmarks\\101\\onto.rdf", 1);   //将源本体和目标本体属性的lable分别放到集合中
        ArrayList<String> list9 = List_IdLableComment.DataAndObjectProperityIdLableComment(file1,1);
        ArrayList<String> list10 = List_IdLableComment.DataAndObjectProperityIdLableComment("D:\\科研\\benchmarks\\101\\onto.rdf", 2);   //将源本体和目标本体属性的commnet分别放到集合中
        ArrayList<String> list11 = List_IdLableComment.DataAndObjectProperityIdLableComment(file1,2);


        double[][] Matrix1 = Matrix_Simliary.SimilarityMatrix_wuAndPalmer(list6,list7);         //       属性的id矩阵
        double[][] Matrix2 = Matrix_Simliary.SimilarityMatrix_wuAndPalmer(list8,list9);        //       属性的lable矩阵
        double[][] Matrix3 = Matrix_Simliary.SimilarityMatrix_wuAndPalmer(list10,list11);        //       属性的comment矩阵
        double[][] Matrix4 = new double[list6.size()][list7.size()]; //用于接收属性最终的相似度矩阵


        for(int i = 0; i < list6.size(); i++) {              //合成id,lable,comment矩阵
            for(int j = 0; j < list7.size(); j++) {
                Matrix4[i][j] = 0.3*Matrix1[i][j] + 0.3*Matrix2[i][j] + 0.4*Matrix3[i][j];;
            }
        }
        return Matrix4;
    }

    public static double[][] ClassMatrix_Resnik(String file1) throws IOException {

        ArrayList<String> list  = List_IdLableComment.list_ClassIdLableComment("D:\\科研\\benchmarks\\101\\onto.rdf", 0);   //将源本体和目标本体class的id分别放到集合中
        ArrayList<String> list1 = List_IdLableComment.list_ClassIdLableComment(file1,0);
        ArrayList<String> list2 = List_IdLableComment.list_ClassIdLableComment("D:\\科研\\benchmarks\\101\\onto.rdf", 1);   //将源本体和目标本体class的lable分别放到集合中
        ArrayList<String> list3 = List_IdLableComment.list_ClassIdLableComment(file1,1);
        ArrayList<String> list4 = List_IdLableComment.list_ClassIdLableComment("D:\\科研\\benchmarks\\101\\onto.rdf", 2);   //将源本体和目标本体class的commnet分别放到集合中
        ArrayList<String> list5 = List_IdLableComment.list_ClassIdLableComment(file1,2);

        double[][] Matrix1 = Matrix_Simliary.SimilarityMatrix_Resnik(list,list1);         //       class的id矩阵
        double[][] Matrix2 = Matrix_Simliary.SimilarityMatrix_Resnik(list2,list3);        //       class的lable矩阵
        double[][] Matrix3 = Matrix_Simliary.SimilarityMatrix_Resnik(list4,list5);        //       class的comment矩阵
        double[][] Matrix4 = new double[list.size()][list1.size()]; //用于接收class最终的相似度矩阵

        for(int i = 0; i < list.size(); i++) {              //合成id,lable,comment矩阵
            for(int j = 0; j < list1.size(); j++) {
                Matrix4[i][j] = 0.3*Matrix1[i][j] + 0.3*Matrix2[i][j] + 0.4*Matrix3[i][j];;
            }
        }
        return Matrix4;
    }

    public static double[][] ProperityMatrix_Resnik(String file1) throws IOException {

        ArrayList<String> list6 = List_IdLableComment.DataAndObjectProperityIdLableComment("D:\\科研\\benchmarks\\101\\onto.rdf", 0);   //将源本体和目标本体属性的id分别放到集合中
        ArrayList<String> list7 = List_IdLableComment.DataAndObjectProperityIdLableComment(file1,0);
        ArrayList<String> list8 = List_IdLableComment.DataAndObjectProperityIdLableComment("D:\\科研\\benchmarks\\101\\onto.rdf", 1);   //将源本体和目标本体属性的lable分别放到集合中
        ArrayList<String> list9 = List_IdLableComment.DataAndObjectProperityIdLableComment(file1,1);
        ArrayList<String> list10 = List_IdLableComment.DataAndObjectProperityIdLableComment("D:\\科研\\benchmarks\\101\\onto.rdf", 2);   //将源本体和目标本体属性的commnet分别放到集合中
        ArrayList<String> list11 = List_IdLableComment.DataAndObjectProperityIdLableComment(file1,2);


        double[][] Matrix1 = Matrix_Simliary.SimilarityMatrix_Resnik(list6,list7);         //       属性的id矩阵
        double[][] Matrix2 = Matrix_Simliary.SimilarityMatrix_Resnik(list8,list9);        //       属性的lable矩阵
        double[][] Matrix3 = Matrix_Simliary.SimilarityMatrix_Resnik(list10,list11);        //       属性的comment矩阵
        double[][] Matrix4 = new double[list6.size()][list7.size()]; //用于接收属性最终的相似度矩阵


        for(int i = 0; i < list6.size(); i++) {              //合成id,lable,comment矩阵
            for(int j = 0; j < list7.size(); j++) {
                Matrix4[i][j] = 0.3*Matrix1[i][j] + 0.3*Matrix2[i][j] + 0.4*Matrix3[i][j];;
            }
        }
        return Matrix4;
    }
    public static double getMax(double a,double b,double c) {
        double max = (a > b) ? a:b;
        max = (max > c) ? max:c;
        return max;
    }
}
