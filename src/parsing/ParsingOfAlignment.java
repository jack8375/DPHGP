package parsing;

import fr.inrialpes.exmo.align.parser.XMLParser;
import org.semanticweb.owl.align.Alignment;
import org.semanticweb.owl.align.Cell;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ParsingOfAlignment {
    /**
     * 解析标准文件 refalign.rdf
     * @param refalignFilePath 标准文件路径
     * @return 解析后的Alignment对象转成list集合，其中已经去除了资源标识符
     */
    public static List<String> parseRefalignFile(String refalignFilePath) throws Exception {
        Alignment alignment = new XMLParser().parse(new FileInputStream(new File(refalignFilePath)));
        Iterator<Cell> it = alignment.iterator();
        List<String> listOfAllAlignment = new ArrayList<String>();
        while (it.hasNext()) {
            Object o = it.next();
            String strMerge = (tearWay(((Cell) o).getObject1().toString()) + "---" + tearWay(((Cell) o).getObject2().toString()));
            listOfAllAlignment.add(strMerge);
        }
        return listOfAllAlignment;
    }

    /**
     * @param  s 待修剪的字符串
     * @return 返回去除了“#”号及其之前的东西而剩下部分
     */
    public static String tearWay(String s) {
        String sEnd = s.substring(s.indexOf("#") + 1);
        return sEnd;
    }
}
