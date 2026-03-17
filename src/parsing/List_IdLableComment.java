package parsing;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


//将class中的id，lable,commment分别放到集合中
public class List_IdLableComment {
    public static ArrayList<String> list_ClassIdLableComment(String file,int i) throws IOException {
        List<String> list = ParsingOfClass.findClassOfOneOnt(file);
        return slectIdLableComment(list,i);
    }


    //将Dataproperity中的id，lable,commment分别放到集合中
    public static ArrayList<String> list_DataproperityIdLableComment(String file,int i) throws IOException {
        List<String> list = ParsingOfDataproperity.findDataproperityOfOneOnt(file);
        return slectIdLableComment(list,i);
    }


    //将Objectproperity中的id，lable,commment分别放到集合中
    public static ArrayList<String> list_ObjectproperityIdLableComment(String file, int i) throws IOException {
        List<String> list = ParsingOfObjectproperity.findObjectproperityOfOneOnt(file);
        return slectIdLableComment(list,i);
    }
    //将Dataproperity和Objectproperity的id（lable,comment）放到一个集合中；
    public static ArrayList<String> DataAndObjectProperityIdLableComment(String file, int i) throws IOException {
        ArrayList<String> list = new ArrayList<>();
        list.addAll(list_DataproperityIdLableComment(file,i));
        list.addAll(list_ObjectproperityIdLableComment(file,i));
        return list;
    }

    private static String[] splitString(String str) {
        String[] ret = str.split("---");
        return ret;
    }

    public static ArrayList<String> slectIdLableComment(List<String> list , int i) {
        ArrayList<String> list1 = new ArrayList<>();
        for(String a : list) {
            String[] strs = splitString(a);
            list1.add(strs[i]);
        }
        return list1;
    }
}
