package parsing;

import com.hp.hpl.jena.ontology.*;
import com.hp.hpl.jena.rdf.model.ModelFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;

public class Parsingthreeinformation1 {
    /*
     * 输入file为文件路径
     * 输出为关于class的id---子类的字符串集合
     */

    public static ArrayList<String> getClass(String file) {            //创建一个本体语言指定语言类型.
        OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        //读取本体文件
        try {
            ontModel.read(new FileInputStream(file), "");
        } catch (FileNotFoundException e1) {
            System.out.println("路径错误");
        }
        ArrayList<String> ontClass = new ArrayList<String>();          // 迭代显示模型中的类，在迭代过程中完成各种操作
        for (Iterator<OntClass> i = ontModel.listClasses(); i.hasNext();) {
            OntClass c = (OntClass) i.next(); // 返回类型强制转换
            if (!c.isAnon() && ( c.getURI().toString().startsWith("http://oaei")
                    || c.getURI().toString().startsWith("http://ebiquity")
                    || c.getURI().toString().startsWith("http://www.aifb"))) { // 如果不是匿名类，则打印类的名字
                String str1 = "";// 获取URI
                // 迭代显示当前类的直接子类
                if (c.hasSubClass()) {
                    for (Iterator<OntClass> it = c.listSubClasses(); it.hasNext();){
                        OntClass sp = (OntClass) it.next();
                        String strSP = sp.getURI();
                        try { // 另一种简化处理URI的方法
                            str1 = str1 + "---"  +   strSP.substring(strSP.indexOf('#') + 1);
                        } catch (Exception e) {
                        }
                    }
                }
                ontClass.add(c.getLocalName() + str1);
                //System.out.println(c.getLocalName() + "---" + c.getLabel(null) + "---"  + s + str1);
                //System.out.println(++num);//统计个数
            }
        }
        return ontClass;
    }
    /*
     * 输入file为文件路径
     * 输出为关于object的id---domain---range 的字符串集合
     */

    public static ArrayList<String> getObject(String file) {
        OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        //读取本体文件
        //E:\\本体\\bench\\benchmarks\\101\\onto.rdf
        try {
            ontModel.read(new FileInputStream(file), "");
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        ArrayList<String> ontOp = new ArrayList<>();
//        int num = 0;
        // 迭代显示模型中的对象属性，在迭代过程中完成各种操作
        for (Iterator<ObjectProperty> i = ontModel.listObjectProperties(); i.hasNext();) {
            ObjectProperty op =  i.next(); // 返回类型强制转换
            if (!op.isAnon() && (op.getURI().toString().startsWith("http://oaei")
                    || op.getURI().toString().startsWith("http://ebiquity")
                    || op.getURI().toString().startsWith("http://www.aifb"))) { // 如果不是匿名类，则打印类的名字
                String str = "";
                if (op.hasDomain(null)) {
                    for (Iterator<?> it = op.listDomain(); it.hasNext();){
                        OntClass c = (OntClass) it.next();
                        if (!c.isAnon()) {
                            String strSP = c.toString();
                            try {
                                str = str + "---" +  strSP.substring(strSP.indexOf('#') + 1);
                            } catch (Exception e) {
                                str = str + "---" + "null";
                            }
                        } else {
                            str = str + "---" + "null";
                        }

                    }
                } else {
                    str = str + "---" + "null";
                }

                String str1 = "";
                if (op.hasRange(null)) {
                    for (Iterator<?> it = op.listRange(); it.hasNext();){
                        OntClass c = (OntClass) it.next();
                        if (!c.isAnon() ) { // 如果不是匿名类，则打印类的名字
                            String strSP = c.toString();
                            try {
                                strSP =  strSP.substring(strSP.lastIndexOf('/') + 1);
                            } catch (Exception e) {
                            }
                            try {
                                str1 = str1 + "---" +  strSP.substring(strSP.indexOf('#') + 1);
                            } catch (Exception e) {
                                str1 = str1 + "---" + "null";
                            }
                        }else {
                            str1 = str1 + "---" + "null";
                        }
                    }
                } else {
                    str1 = str1 + "---" + "null";
                }
                ontOp.add(op.getLocalName() + str + str1);
                //System.out.println(op.getLocalName() + "---" + op.getLabel(null) + "---" +s + str + str1);
                //System.out.println(++num);
            }
        }
        return ontOp;
    }
    /*
     * 输入file为文件路径
     * 输出为关于data的id---domain---range的字符串集合
     * data的range基本都是String类的所以只考虑它的domain
     */
    public static ArrayList<String> getData(String file) {
        OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        //读取本体文件
        //E:\\本体\\bench\\benchmarks\\101\\onto.rdf
        try {
            ontModel.read(new FileInputStream(file), "");
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        ArrayList<String> ontDp = new ArrayList<>();
//      int num = 0;
        // 迭代显示模型中的对象属性，在迭代过程中完成各种操作
        for (Iterator<DatatypeProperty> i = ontModel.listDatatypeProperties(); i.hasNext();) {
            DatatypeProperty od = (DatatypeProperty) i.next(); // 返回类型强制转换
            if (!od.isAnon() && (od.getURI().toString().startsWith("http://oaei")
                    || od.getURI().toString().startsWith("http://ebiquity")
                    || od.getURI().toString().startsWith("http://www.aifb"))) { // 如果不是匿名类，则打印类的名字
                String str = "";
                if (od.hasDomain(null)) {
                    for (Iterator<?> it = od.listDomain(); it.hasNext();){
                        OntClass c = (OntClass) it.next();
                        if (!c.isAnon() ) { // 如果不是匿名类，则打印类的名字
                            String strSP = c.toString();
                            try { // 另一种简化处理URI的方法
                                str = str + "---" +  strSP.substring(strSP.indexOf('#') + 1);
                            } catch (Exception e) {
                                str = str + "---" + "null";
                            }
                        }else {
                            str = str + "---" + "null";
                        }

                    }
                } else {
                    str = str + "---" + "null";
                }
                String str1 = "";
                if (od.hasRange(null)) {
                    for (Iterator<?> it = od.listRange(); it.hasNext();){
                        OntClass c = (OntClass) it.next();
                        if (!c.isAnon() ) { // 如果不是匿名类，则打印类的名字
                            String strSP = c.toString();
                            try {
                                strSP =  strSP.substring(strSP.lastIndexOf('/') + 1);
                            } catch (Exception e) {
                            }
                            try {
                                str1 = str1 + "---" +  strSP.substring(strSP.indexOf('#') + 1);
                            } catch (Exception e) {
                                str1 = str1 + "---" + "null";
                            }
                        }else {
                            str1 = str1 + "---" + "null";
                        }
                    }
                } else {
                    str1 = str1 + "---" + "null";
                }
                ontDp.add(od.getLocalName() + str + str1);
            }
        }
        return ontDp;
    }
}
