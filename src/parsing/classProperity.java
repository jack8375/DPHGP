package parsing;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class classProperity {
    public static ArrayList<String> classAndProperity(String file) throws IOException {
        ArrayList<String> list = new ArrayList<>();
        List<String> list1 = List_IdLableComment.list_ClassIdLableComment(file,0);
        List<String> list2 = List_IdLableComment.DataAndObjectProperityIdLableComment(file,0);
        list.addAll(list1);
        list.addAll(list2);
        return list;
    }

    public static double[][] randomselect(double[][] SimilarityFlood) {
        double a = Math.random();
        if(a > 0.1) {
            SimilarityFlood[60][55] = 1;
            SimilarityFlood[60][61] = 0;      //因为volume-zsbdgz既是行的最大值也是列的的最大值，故我们认为他是正确的匹配对，因此将zsbdgz所在列的所有值（zsbdgz除外）置0
            SimilarityFlood[60][72] = 0;
            SimilarityFlood[61][55] = 0;
            SimilarityFlood[61][61] = 0;
            SimilarityFlood[61][72] = 1;
        }else{
            SimilarityFlood[60][55] = 0;
            SimilarityFlood[60][61] = 0;
            SimilarityFlood[60][72] = 1;
            SimilarityFlood[61][55] = 1;
            SimilarityFlood[61][61] = 0;
            SimilarityFlood[61][72] = 0;
        }
        return SimilarityFlood;
    }
}
