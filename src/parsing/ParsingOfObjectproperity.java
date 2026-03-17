package parsing;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.ontology.OntProperty;
import org.apache.jena.rdf.model.ModelFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ParsingOfObjectproperity {

    /**
     *
     * @param path 为一个本体文件的路径名
     * @return 一个list集合包含了一个本体文件的objectproperity及其label、comment
     * @throws FileNotFoundException
     */
    public static List<String> findObjectproperityOfOneOnt(String path) throws FileNotFoundException {

        OntModel ontMode2 = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        ontMode2.read(new FileInputStream(path), "");
        List<String> list = new ArrayList<>();		// 用于存放一个本体文件下的objectproperity及其doman，中间用~~~分割
        Iterator<?> exIter = ontMode2.listObjectProperties();
        while (exIter.hasNext()) {
            OntProperty op = (OntProperty) exIter.next();
            if (!op.isAnon() && ((op.getURI().toString().startsWith("http://oaei")) || (op.getURI().toString().startsWith("http://ebiquity")) || (op.getURI().toString().startsWith("http://www.aifb"))) && !(op.toString().startsWith("#"))) {		// 这是对父类的字符串进行裁剪，去除各种定位与标识符
                String strOfObjectPro = op.getLocalName();		//   也可获取本地ID
//				String strOfObjectPro = op.toString().substring(op.toString().indexOf("#") + 1);
                String s1 = tearMyProerity(strOfObjectPro);		// 得到dataproperity
                String strOfNormalLabel = op.getLabel(null);		// 获取相同对象下的label
                String strOfNormalCom;
                try {
                    strOfNormalCom = tearComment(op.getComment(null));		// 获取相同对象下的comment
                } catch (Exception e) {
                    strOfNormalCom = null;
                }
                list.add(s1 + "---" + strOfNormalLabel + "---" + strOfNormalCom);		// 字符串的拼接
            }
        }
        //System.out.println("objectproperity的个数为：" + list.size());
        return list;
    }
    /**
     *
     * @param label 待被裁剪的字符串
     * @return 被去了尾巴的字符串
     */
    private static String tearComment(String label) {

        if (label != null) {
            String s = label.substring(0, label.length() - 1);
            return s;
        } else {
            return "null";
        }
    }
    /*
     * 删除掉一些与内容无关的标识符
     */
    private static String tearMyProerity(String strOfDataPro) {

        int index = strOfDataPro.lastIndexOf("/");
        String normalStr = strOfDataPro.substring(index + 1);
        return normalStr;
    }

}
