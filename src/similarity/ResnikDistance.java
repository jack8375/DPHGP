package similarity;

import edu.sussex.nlp.jws.JWS;
import edu.sussex.nlp.jws.Resnik;
import java.math.BigDecimal;

public class ResnikDistance {
    private static String dir = "D:\\Wordnet-2.1\\WordNet";   // WordNet安装路径选择（不要进入到最内层）
    // WordNet版本为2.1版
    private static JWS ws = new JWS(dir, "2.1");
    // 变量声明
    private static Resnik res = ws.getResnik();

    /**
     * 比较两个字符串再基于语义结构上的相似度,
     * 使用Wu和Palmer算法求相似度最大值；Wu和Palmer算法适当地考虑了概念的密度信息，
     * 认为层次越深概念间的语义距离越小，相似度越大 使用WuAndPalmer计算两个字符串相似度最大值
     */
    public static double ResnikSimilarity(String str1, String str2) {
        //两种不同的路径方法,选取相似度大的一个
        double sc = res.max(str1, str2, "n");//有个n,有个v,n这个好像算的比较准,计算n的相似
        double sc1 = res.max(str1, str2, "v");// 计算v的相似
        double sc2;
        if (sc <= sc1) {
            sc2 = sc1;
        } else {
            sc2 = sc;              //将v、n中大的值认为是相似度
        }
        if(sc2 == 0) {
            return 0.0;
        } else {
            return 1 - 1/decimalType(sc2);  //保留两位小数
        }


    }

    /**
     * 在wordnet的方法上加上判断是句子还是单词,既可以求出句子的相似度,也可以求出单词的相似度
     * 如果出现"null"表明该项为空,相似度为0
     *
     * 比较两个句子的相似度:
     * 例如第一个数组的第一个元素的最大相似度为它和第二个数组的每个元素相比的最大相似度
     * (第一个数组的每个元素的最大相似度相加的和 + 第二个数组的每个元素的最大相似度相加的和)/(两个数组的长度和)
     */
    public static double SimilaryOfResnik(String str1, String str2) {
        String[] strs1 = splitString(str1);
        String[] strs2 = splitString(str2);
        //如果两个字符串数组的个数为1,说明是两个字符串,不是句子
        if (str1.equals("null") || str2.equals("null")) {//如果str1和str2两个钟任何一个为"null"，返回相似度为0
            return 0.0;
        }
        if (str1.equals(str2) ) {                       //如果str1和str2完全一样，直接得到相似度为1
            return 1.0;
        }
        if (strs1.length == 1 && strs2.length == 1) {   //判断str1和str2都只是一个字符串，直接使用WordNet来得到相似度
            return ResnikSimilarity(str1,str2);
        }
        //其余情况则是两个句子,则采用将字符串的方法变成句子的方法来转换
        double sum1 = 0;
        for(int i = 0; i < strs1.length; i++){
            double max = 0.0;
            for(int j = 0; j < strs2.length; j++){
                double sc = ResnikSimilarity(strs1[i],strs2[j]);
                if(max <= sc) {
                    max = sc;
                }
            }
            sum1 += max;  //将第一个字符数组的每个字符串相似度最大值相加
        }
        double sum2 = 0;
        for(int i = 0; i < strs1.length; i++){
            double max = 0.0;
            for(int j = 0; j < strs2.length; j++){
                double sc = ResnikSimilarity(strs1[i],strs2[j]);
                if(max <= sc) {
                    max = sc;
                }
            }
            sum2 += max;  //将第二个字符数组的每个字符串相似度最大值相加
        }
        double Similarity = (sum1 + sum2) / (double)(strs1.length + strs2.length);
        if(Similarity > 1.0) {
            return 1.0;
        } else if(Similarity < 0.0) {
            return 0.0;
        } else {
            return Similarity;
        }
    }


    /**
     * 根据空格切分字符串
     * 得到的ret[0]表示第一个字符串(即为第一个单词)
     */
    private static String[] splitString(String str) {
        String[] ret = str.split(" ");
        return ret;
    }

    private static double decimalType(double x) {                //x保留两位小数
        BigDecimal b = new BigDecimal(x);
        double f = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(); //保留2位小数
        return f;
    }
}
