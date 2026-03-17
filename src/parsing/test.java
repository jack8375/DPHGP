package parsing;

import java.util.ArrayList;
import java.util.List;

import static DPHGP.Coding.baoliuliangwei;

public class test {
    public static void main(String[] args) throws Exception {
        List<Double> List = new ArrayList<>();
        for (double i = 2.4; i <= 5.01; i += 0.04) {
            List.add(i);
        }
        for(int i = 0 ; i < List.size() ; i++) {
            System.out.println(baoliuliangwei(List.get(i)));
        }
    }
}
