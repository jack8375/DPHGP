package DPHGP;

public class CGAcoding {
    /**
     * @param ConstantSet 输入一棵树的常数集合
     * @param a      一个常数格雷码编码的位数
     * @return list   该常数集合对应的格雷码编码集合
     */
    public static int[] ConstantSetCoding(double[] ConstantSet, int a) {  //a为每个常数格雷码编码的位数
        int[] list = new int[a*ConstantSet.length];
        int number = 0;
        int w1 = 0;
        int w2 = 0;
        String str1;
        String str2;
        String str;
        int index = 0;
        for(int i = 0 ; i < ConstantSet.length ; i ++) {
            double Constant = ConstantSet[i];
            number = (int)(Constant*100);
            w1 = number / 10;
            w2 = number % 10;
            str1 = binaryToGrayCode(decimalToBinary(w1,a/2));
            str2 = binaryToGrayCode(decimalToBinary(w2,a/2));
            str = str1 + str2;
            int b = 0;
            for(int j = 0 ; j < str.length() ; j ++) {
                b = Character.getNumericValue(str.charAt(j));
                list[index] = b;
                index++;
            }
        }
        return list;
    }
    /**
     * @param decimal 输入一个0-9的十进制数
     * @param b      该十进制数格雷码编码的位数
     * @return binary   该常数集合对应的格雷码编码字符串
     */
    public static String decimalToBinary(int decimal,int b) {
        String binary = Integer.toBinaryString(decimal); // 使用Integer类的toBinaryString方法将十进制数转换为二进制编码
        int length = binary.length();
        if (length < b) {
            // 如果二进制编码位数不足，则在前面补0，使其成为四位
            for (int i = 0; i < 4 - length; i++) {
                binary = "0" + binary;
            }
        }
        return binary;
    }
    /**
     * @param  binaryString 输入一个二进制编码字符串
     * @return grayCodeString   该二进制编码字符串对应的格雷码编码字符串
     */
    public static String binaryToGrayCode(String binaryString) {               //将二进制数转化为格雷码
        String grayCodeString = "";
        grayCodeString += binaryString.charAt(0); // 第一个字符保持不变
        char currentBit;
        char previousBit;
        char grayCodeBit;
        for (int i = 1; i < binaryString.length(); i++) {
            // 格雷码的每一位等于二进制码的当前位与前一位的异或运算
            currentBit = binaryString.charAt(i);
            previousBit = binaryString.charAt(i - 1);
            grayCodeBit = (currentBit == previousBit) ? '0' : '1';
            grayCodeString += grayCodeBit;
        }
        return grayCodeString;
    }
    /**
     * @param  list 输入一个格雷码编码集合
     * @param  a    一个常数格雷码编码的位数
     * @return list1  返回该格雷码编码集合对应的常数(0-1)集合
     */
    public static double[] Decoding(int[] list,int a) {
        int size = list.length/a;
        String[] b = new String[size];
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < list.length; i += a) {
            StringBuilder group = new StringBuilder();
            for (int j = i; j < i + a && j < list.length; j++) {
                group.append(list[j]);
            }
            stringBuilder.append(group.toString()).append(" ");
        }
        String result = stringBuilder.toString().trim();
        b = result.split(" ");
        double[] list1 = new double[size];
        String s1;
        int decimal;
        double c;
        for(int k = 0 ; k < b.length ; k ++) {
            s1 = b[k];
            decimal = grayCodeToNumber(s1.substring(0,s1.length()/2))*10 + grayCodeToNumber(s1.substring(s1.length()/2,s1.length()));
            c = Coding.baoliuliangwei(decimal*0.01);
            if(c > 1) {             //
                c = Coding.baoliuliangwei(c - 1);
            }
            list1[k] = c;
        }
        return list1;
    }
    /**
     * @param  grayCode 输入一个格雷码编码字符串
     * @return num  返回该格雷码编码对应的常数
     */
    public static int grayCodeToNumber(String grayCode) {
        int n = grayCode.length();
        int[] binary = new int[n];
        int sum = 0;
        // 将格雷码转换为二进制
        binary[0] = grayCode.charAt(0) - '0';
        for (int i = 1; i < n; i++) {
            binary[i] = binary[i - 1] ^ (grayCode.charAt(i) - '0');
        }
        // 将二进制转换为常数
        for(int j = 0 ; j < binary.length ; j ++) {
            if(binary[j] == 1) {
                sum += Math.pow(2,binary.length - 1 - j);
            }
        }
        return sum;
    }
}
