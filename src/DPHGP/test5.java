package DPHGP;

import parsing.classProperity;

import java.io.IOException;
import java.util.ArrayList;

public class test5 {
    public static void main(String[] args) throws IOException {
        ArrayList<String> list = classProperity.classAndProperity("D:\\科研\\benchmarks\\103\\onto.rdf");
        readWuMatrix.write(list,"D:\\stringData\\103.txt");
    }
}
