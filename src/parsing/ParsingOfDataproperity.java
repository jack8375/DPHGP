package parsing;

import com.hp.hpl.jena.ontology.DatatypeProperty;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ParsingOfDataproperity {

    /**
     * @param path 为一个本体文件的路径名
     * @return 第一个list集合包含了一个本体文件的dataproperity及其label、comment
     * @throws FileNotFoundException
     */
    public static List<String> findDataproperityOfOneOnt(String path) {
        OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        //��ȡ�����ļ�
        try {
            ontModel.read(new FileInputStream(path), "");
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        ArrayList<String> ontDp = new ArrayList<>();
        // ������ʾģ���еĶ������ԣ��ڵ�����������ɸ��ֲ���
        for (Iterator<DatatypeProperty> i = ontModel.listDatatypeProperties(); i.hasNext(); ) {
            DatatypeProperty od = (DatatypeProperty) i.next(); // ��������ǿ��ת��
            if (!od.isAnon() && (od.getURI().toString().startsWith("http://oaei")
                    || od.getURI().toString().startsWith("http://ebiquity")
                    || od.getURI().toString().startsWith("http://www.aifb")
            )) { // ������������࣬���ӡ�������
                String s = "" + od.getComment(null);
                if (s.equals("")) {
                    s = "null";
                }
                String Label = od.getLabel(null);		// 获取相同对象下的label
                ontDp.add(od.getLocalName() + "---" + Label + "---" + s);
                //System.out.println(od.getLocalName() + "   " + od.getLabel(null) + "   " + s );
            }
        }
        return ontDp;
    }
}
