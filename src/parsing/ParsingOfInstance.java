package parsing;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.StmtIterator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;

public class ParsingOfInstance {
    public static ArrayList<ArrayList<String>> getins(String file) {
        //创建一个本体语言指定语言类型.
        OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        //读取本体文件
        try {
            ontModel.read(new FileInputStream(file), "");
        } catch (FileNotFoundException e1) {
            System.out.println("路径错误");
        }
        ArrayList<ArrayList<String>> ontins = new ArrayList<>();
        // 迭代显示模型中的类，在迭代过程中完成各种操作
//      ArrayList<String> st = new ArrayList<>();
//      int num = 0;
        for (Iterator<Individual> i = ontModel.listIndividuals(); i.hasNext();) {
            ArrayList<String> oClass = new ArrayList<>();
            Individual c =  i.next(); // 返回类型强制转换
            if (!c.isAnon() && ( c.getURI().toString().startsWith("http://oaei")
                    || c.getURI().toString().startsWith("http://ebiquity")
                    || c.getURI().toString().startsWith("http://www.aifb")
            )) { // 如果不是匿名类，则打印类的名字
//				System.out.println("+++"+c.getLocalName());
                oClass.add(c.getLocalName());  //name , label ,type , 属性+对象---属性+对象
//				System.out.println(c.getLabel(null));
                oClass.add(c.getLabel(null));
//				System.out.println(c.getProperty(null));
//				System.out.println(c.getRDFType());

                String str1 = c.getRDFType().toString();
                if (str1.contains("#")) {
                    oClass.add(str1.substring(str1.indexOf('#') + 1));//	http://oaei.ontologymatching.org/2011/benchmarks/101/onto.rdf#Conference
//					System.out.println(str1.substring(str1.indexOf('#') + 1));
                } else {
                    if (str1.contains("Person")) {   //http://xmlns.com/foaf/0.1/Person
                        oClass.add("Person");
//						System.out.println("Person");
                    }
                }
                String str3 = "";
                for (StmtIterator s = c.listProperties(); s.hasNext();) { //----a72192307, publisher, a131020767]
                    String ss = s.next().toString();
                    //用,进行切分
                    String str2 = "";
                    if (ss.contains("label") || ss.contains("type")) {
                    }else {
                        String[] arr1 = ss.split(",");
                        if (arr1[1].contains("#")) {
                            str2 += arr1[1].substring(arr1[1].indexOf('#') + 1); //+ "+" + arr1[2].substring(arr1[2].indexOf('#') + 1);

//							if (!arr1[1].substring(arr1[1].indexOf('#') + 1).contains("Name") && !arr1[1].substring(arr1[1].indexOf('#') + 1).contains("name")) {
////								System.out.println(arr1[1].substring(arr1[1].indexOf('#') + 1));
//								st.add(arr1[1].substring(arr1[1].indexOf('#') + 1));
//							}
                        }else {
                            str2 += arr1[1].replace("http://xmlns.com/foaf/0.1/", "");

//							if (!arr1[1].replace("http://xmlns.com/foaf/0.1/", "").contains("Name") && !arr1[1].replace("http://xmlns.com/foaf/0.1/", "").contains("name")) {
////								System.out.println(arr1[1].substring(arr1[1].indexOf('#') + 1));
//								st.add(arr1[1].substring(arr1[1].indexOf('#') + 1));
//							}
                        }
                        if (arr1[2].contains("#string")) {
                            str2 += "+"+arr1[2].substring(0,arr1[2].indexOf("^^")) + "string";
                        }else if (arr1[2].contains("#") && !arr1[2].contains("string")) {
                            str2 += "+"+arr1[2].substring(arr1[2].indexOf('#') + 1);
                        }else if (!arr1[2].contains("#") && !arr1[2].contains("string")) {
                            str2 += "+"+arr1[2];
                        }
                        str3 += str2 + "---";
//						System.out.println(ss);
                    }
                }
//				System.out.println(str3);
                oClass.add(str3);
                ontins.add(oClass);
            }
        }
//		newway.quchong(st);
//		for (int i = 0; i < st.size(); i++) {
//			System.out.println(st.get(i));
//		}
        return ontins;
    }
}