package similarity;

import edu.sussex.nlp.jws.JWS;
import edu.sussex.nlp.jws.WuAndPalmer;
import fr.inrialpes.exmo.ontosim.string.StringDistances;
import org.apache.lucene.search.spell.NGramDistance;

public class SimilarWay {
    // WordNet安装路径选择（不要进入到最内层）
    private static String dir = "D:\\代码\\DPHGP\\Wordnet-2.1\\WordNet";
    // WordNet版本为2.1版
    private static JWS ws = new JWS(dir, "2.1");
    // 变量声明
    private static WuAndPalmer wu = ws.getWuAndPalmer();

   //N-Gram相似度量方法
    public static double NGramLSimilarity(String a, String b, int n) {
        if (a == null || b == null || a.length() <= 0 || b.length() <= 0 || a.equals("null") || b.equals("null")) {
            return 0.0;
        }

        if (a.equals(b)) {
            return 1.0;
        }

        NGramDistance ng = new NGramDistance(n);
        return ng.getDistance(a.replace("(", " ").replace(")", " ").replace("/", " ").replace(",", " ").replace(" ", ""), b.replace("(", " ").replace(")", " ").replace("/", " ").replace(",", " ").replace(" ", ""));
    }

    //Wu and Palmer相似度量方法
    public static double Similarity_wuAndPalmer(String str1, String str2) {
        if ((str1 == null || str1.length() <= 0) || (str2 == null || str2.length() <= 0) || str1.equals("null") || str2.equals("null")) {
            return 0.0;
        }

        String[] strs1 = splitString(str1.replace("(", " ").replace(")", " ").replace("/", " ").replace(",", " "));
        String[] strs2 = splitString(str2.replace("(", " ").replace(")", " ").replace("/", " ").replace(",", " "));
        double sum = 0;
        for (String s1 : strs1) {
            double max = 0;
            for (String s2 : strs2) {
                double sc = maxScoreOfWu(s1, s2);
                if (max < sc) {
                    max = sc;
                }
            }
            sum += max;
        }
        double Similarity = 2 * sum / (strs1.length + strs2.length);
        if (Similarity > 1.0) {
            return 1.0;
        } else {
            return Similarity;
        }
    }



        private static double maxScoreOfWu(String str1, String str2) {
            if ((str1 == null || str1.length() <= 0) || (str2 == null || str2.length() <= 0)) {
                return 0.0;
            }
            if (str1.equals(str2)) {
                return 1.0;
            }
            double sc = wu.max(str1, str2, "n");
            double sc2 = wu.max(str1, str2, "v");
            return sc > sc2 ? sc : sc2;
        }

    //SMOA相似度量方法
    public static double Similarity_SMOA1(String str1, String str2) {
        if ((str1 == null || str1.length() <= 0) || (str2 == null || str2.length() <= 0) || str1.equals("null") || str2.equals("null")) {
            return 0.0;
        }
        String[] strs1 = splitString(str1.replace("(", " ").replace(")", " ").replace("/", " ").replace(",", " "));
        String[] strs2 = splitString(str2.replace("(", " ").replace(")", " ").replace("/", " ").replace(",", " "));
        double sum = 0;
        for(int i = 0; i < strs1.length; i++){
            double max = 0.0;
            for(int j = 0; j < strs2.length; j++){
                double sc = SMOA(strs1[i],strs2[j]);
                if(max < sc) {
                    max = sc;
                }
            }
            sum += max;
        }
        double Similarity = (2 * sum) / (strs1.length + strs2.length);
        return Similarity;
    }
    public static void main(String[] args) {
        double a = Similarity_SMOA1("book","Book");
        System.out.println();
    }

    public static double SMOA(String a, String b) {
        if ((a == null || a.length() <= 0) || (b == null || b.length() <= 0)) {
            return 0.0;
        }
        if (a.equals(b)) {
            return 1.0;
        }
        return 1d - StringDistances.smoaDistance(a, b);
    }

    private static String[] splitString(String str) {
        String[] ret = str.split(" ");
        return ret;
    }



}

