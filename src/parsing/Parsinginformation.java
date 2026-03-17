package parsing;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import org.apache.jena.ontology.OntProperty;
import org.apache.jena.util.iterator.ExtendedIterator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class Parsinginformation {
    /*
     * 输入file为文件路径
     * 输出为关于class的id---父类;
     */
    public static ArrayList<String> getSuperclass(String file) {            //创建一个本体语言指定语言类型.
        OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        //读取本体文件
        try {
            ontModel.read(new FileInputStream(file), "");
        } catch (FileNotFoundException e1) {
            System.out.println("路径错误");
        }
        ArrayList<String> oClass = new ArrayList<>();
        for (Iterator<OntClass> i = ontModel.listClasses(); i.hasNext();) {
            OntClass c = (OntClass) i.next(); // 返回类型强制转换
            if (!c.isAnon() && ( c.getURI().toString().startsWith("http://oaei")
                    || c.getURI().toString().startsWith("http://ebiquity")
                    || c.getURI().toString().startsWith("http://www.aifb"))) { // 如果不是匿名类，则打印类的名字
                String str = "";// 获取URI
                // 迭代显示当前类的直接父类
                if(c.hasSuperClass()) {
                    for (Iterator<OntClass> it = c.listSuperClasses(); it.hasNext();){
                        OntClass sp = (OntClass) it.next();
                        String strSP = sp.getURI();
                        try { // 另一种简化处理URI的方法
                            //str = str + strSP.substring(strSP.indexOf('#') + 1);
                            str = str + "---" +  strSP.substring(strSP.indexOf('#') + 1);
                        } catch (Exception e) {
                        }
                    } // super class ends
                }
                oClass.add(c.getLocalName() + str);
            }
        }
        return oClass;
    }
    /*
     * 输入file为文件路径
     * 输出为关于ObjectProperity的id---父类---子类;
     */
    public static List<String> Find_ObjectProperity(String path) throws IOException {

        org.apache.jena.ontology.OntModel ontModel_op = org.apache.jena.rdf.model.ModelFactory.createOntologyModel(org.apache.jena.ontology.OntModelSpec.OWL_DL_MEM.OWL_MEM);
        ontModel_op.read(new FileInputStream(path),"");
        List<String> list_op = new ArrayList<>();
        Iterator<?> it = ontModel_op.listObjectProperties();
        while (it.hasNext()){
            OntProperty op  = (OntProperty) it.next();
            if ( !op.isAnon() ) {
                String str_id = op.getLocalName();
                String son = Link_ObjectProperity_Son(op);
                String father = Link_ObjectProperity_Father(op);
                list_op.add(str_id + "---" + father + "---" + son);
            }
        }
        return list_op;
    }
    public static String Link_ObjectProperity_Son(OntProperty OP) {

        String all_subclass = "";

        for(ExtendedIterator<? extends  OntProperty> it = OP.listSubProperties(); it.hasNext();) {
            OntProperty op_subclass = (OntProperty) it.next();
            String s = op_subclass.toString().substring(op_subclass.toString().indexOf("#") + 1);
            all_subclass = all_subclass + s +"---";
        }
        if (all_subclass.equals("") || all_subclass.equals(null)){
            return null;
        }else {
            return all_subclass;
        }
    }

    public static String Link_ObjectProperity_Father(OntProperty OP) {

        String all_supclass = "";

        for(ExtendedIterator<? extends  OntProperty> it = OP.listSuperProperties(); it.hasNext();) {
            OntProperty op_supclass = (OntProperty) it.next();
            String s = op_supclass.toString().substring(op_supclass.toString().indexOf("#") + 1);
            all_supclass += s;
        }

        if (all_supclass.equals("") || all_supclass.equals(null)){
            return null;
        }else {
            return all_supclass;
        }
    }
    /*
     * 输入file为文件路径
     * 输出为关于DataProperity的id---父类---子类;
     */
    public static List<String> Find_DataProperity(String path) throws IOException {

        org.apache.jena.ontology.OntModel ontModel_dp = org.apache.jena.rdf.model.ModelFactory.createOntologyModel(org.apache.jena.ontology.OntModelSpec.OWL_DL_MEM.OWL_MEM);
        ontModel_dp.read(new FileInputStream(path),"");
        List<String> list_dp = new ArrayList<>();
        Iterator<?> it = ontModel_dp.listDatatypeProperties();
        while (it.hasNext() ){
            OntProperty dp  = (OntProperty) it.next();
            if ( !dp.isAnon() ) {
                String str_id = dp.getLocalName();
                String son = Link_DataProperity_Son(dp);
                String father = Link_DataProperity_Father(dp);
                list_dp.add(str_id + "---" + father  + "---" + son);
            }
        }
        return list_dp;
    }
    public static String Link_DataProperity_Son(OntProperty DP) {

        String all_subclass = "";

        for(ExtendedIterator<? extends  OntProperty> it = DP.listSubProperties(); it.hasNext();) {
            OntProperty dp_subclass = (OntProperty) it.next();
            String s = dp_subclass.toString().substring(dp_subclass.toString().indexOf("#") + 1);
            all_subclass = all_subclass + s +"---";
        }
        if (all_subclass.equals("") || all_subclass.equals(null)){
            return null;
        }else {
            return all_subclass;
        }

    }
    public static String Link_DataProperity_Father(OntProperty DP) {

        String all_supclass = "";

        for(ExtendedIterator<? extends  OntProperty> it = DP.listSuperProperties(); it.hasNext();) {
            OntProperty dp_supclass = (OntProperty) it.next();
            String s = dp_supclass.toString().substring(dp_supclass.toString().indexOf("#") + 1);
            all_supclass += s;
        }
        if (all_supclass.equals("") || all_supclass.equals(null)){
            return null;
        }else {
            return all_supclass;
        }
    }
    public static ArrayList<String> Find_DataAndObjectProperity(String path) throws IOException {
        ArrayList<String> list = new ArrayList<>();
        ArrayList<String> list3 = new ArrayList<>();
        List<String> list1 = Find_ObjectProperity(path);
        List<String> list2 = Find_DataProperity(path);
        list.addAll(list2);
        list.addAll(list1);
        ArrayList<String> ID = List_IdLableComment.DataAndObjectProperityIdLableComment(path,0);
        for(int i = 0 ; i < list.size() ; i ++) {
            if(!ID.contains(list.get(i).split("---")[0])) {
                list.remove(i);
                i = i - 1;
            }
        }
        duplicate(list);
        for(int i = 0 ; i < ID.size() ; i ++) {
            for(int j = 0 ; j < list.size() ; j++) {
                String a = list.get(j).split("---")[0];
                if(ID.get(i).equals(a)) {
                    list3.add(list.get(j));
                    break;
                }
            }
        }
        return list3;
    }
    //集合元素去重
    private static void duplicate(ArrayList<String> list) {
        LinkedHashSet<String> set = new LinkedHashSet<>();
        for (String i : list) {
            set.add(i);
        }
        list.clear();
        list.addAll(set);
    }
}
