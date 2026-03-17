package parsing;

import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.iterator.ExtendedIterator;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ParsingOfClass {

    /**
     * @return class的 id---label---comment
     * @throws IOException
     */
    public static List<String> findClassOfOneOnt(String file) throws IOException {
        OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);		// 创造本体模型
        ontModel.read(new FileInputStream(file), "");		// 读取本体文件
        List<String> listOfThree = new ArrayList<>();		//此List集合就是需要返回的对象


        String strOfClass = "";
        for (ExtendedIterator<OntClass> i = ontModel.listClasses(); i.hasNext();) {		// 利用Jena中的迭代器进行捕获
            OntClass c = (OntClass)i.next();		// c 为一个class对象
            if (!c.isAnon() && ((c.getURI().toString().startsWith("http://oaei")) || (c.getURI().toString().startsWith
                    ("http://ebiquity")) || (c.getURI().toString().startsWith("http://www.aifb")))) {		// 判断class是否为空，null没有意义
                strOfClass = c.getLocalName();		//   也可获取本地ID
                //System.out.println(strOfClass);
//				strOfClass = c.toString().substring(c.toString().indexOf("#") + 1);		// 可去掉class的一些资源标识符
//				System.out.println(strOfClass);
                String strOfNormalClass = tearWay(strOfClass);		// 此方法去掉一些零碎的符号
                String strOfNormalLabel = c.getLabel(null);		// 获取相同对象下的label
                String strOfNormalCom;
                try {
                    strOfNormalCom = tearComment(c.getComment(null));		// 获取相同对象下的comment
                } catch (Exception e) {
                    strOfNormalCom = null;
                }
                listOfThree.add(strOfNormalClass + "---" + strOfNormalLabel + "---" + strOfNormalCom);		// 字符串的拼接
                //listOfFatherSons.add();
            }
        }
       // System.out.println("class的个数为：" + listOfThree.size());
        return listOfThree;
    }

    /**
     * 判断一个字符串是否是空,若是空返回XXXnoXXX，不为空的话，返回原串
     */
    public static String predispose(String s) {
        if (s == null || s.length() == 0) {
            return null;
        } else {
            return s;
        }
    }



    /**
     *
     * @param comment 待被裁剪的字符串
     * @return 被去了尾巴的字符串
     */
    private static String tearComment(String comment) {

        if (comment != null) {
            String s = comment.substring(0, comment.length() - 1);
            return s;
        }

        return null;
    }

    /**
     *
     * @param strOfClass 被裁剪的字符串
     * @return	将"/"及其前所有去掉后的字符串
     */
    private static String tearWay(String strOfClass) {
        int index = strOfClass.lastIndexOf("/");
        String normalStr = strOfClass.substring(index + 1);
        return normalStr;
    }

}



