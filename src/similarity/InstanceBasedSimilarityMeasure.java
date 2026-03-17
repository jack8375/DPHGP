package similarity;

import java.util.ArrayList;
import java.util.HashSet;

public class InstanceBasedSimilarityMeasure {
    //基于实例的相似度量方法
    public static ArrayList<ArrayList<String>> getins(ArrayList<ArrayList<String>> listins1, ArrayList<ArrayList<String>> listins2) {
        ArrayList<String> fallclass = new ArrayList<>();
        ArrayList<String> fallpro = new ArrayList<>();
        ArrayList<String> fallprocorr = new ArrayList<>();
        for (int i = 0; i < listins1.size(); i++) {
            for (int j = 0; j < listins2.size(); j++) {
                //判断name,label是否一样，如果一样的话就把对应的type加入匹配对中

                ArrayList<String> fallprotest = new ArrayList<>();
                double d = 1 - SimilarWay.Similarity_SMOA1(listins1.get(i).get(0), listins2.get(j).get(0));
                double d2 = 1 - SimilarWay.Similarity_SMOA1(listins1.get(i).get(1), listins2.get(j).get(1));
                if (d == 0 || d2 == 0) {
                    fallclass.add(listins1.get(i).get(2) + "---" + listins2.get(j).get(2));
//					System.out.println(listins1.get(i).get(2)+"---"+listins2.get(j).get(2));
//					System.out.println(listins1.get(i).get(3));
//					System.out.println(listins2.get(j).get(3));
//					System.out.println("--------------------------------");
                    //根据class匹配来得出属性关系的匹配
                    String[] arr1 = listins1.get(i).get(3).split("---");
                    String[] arr2 = listins2.get(j).get(3).split("---");
                    for (int k = 0; k < arr1.length; k++) {
                        String[] arrr1 = arr1[k].split("\\+");
                        for (int m = 0; m < arr2.length; m++) {
                            String[] arrr2 = arr2[m].split("\\+");
                            try {
                                if (arrr1[1].contains(":") || arrr2[1].contains(":")) { // date+ 161aad2:185a0a60dd2:-7f54]
                                    String[] arrrr1 = arrr1[1].split(":");
                                    String[] arrrr2 = arrr2[1].split(":");
                                    //如果arrr1中和arrr2中有一个相同，那么arrr1[0]+"---"+arrr2[0]是匹配对
                                    for (int l = 0; l < arrrr1.length; l++) {
                                        for (int l2 = 0; l2 < arrrr2.length; l2++) {
                                            if (arrrr1[l].equals(arrrr2[l2])) {
                                                fallprotest.add(arrr1[0] + "---" + arrr2[0]);
//												System.out.println("***"+arrr1[0]+"---"+arrr2[0]);
                                                break;
                                            }
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                // TODO: handle exception
                            }

                            try {
                                if (arrr1[1].equals(arrr2[1])) {
                                    fallprotest.add(arrr1[0] + "---" + arrr2[0]);
//									System.out.println(arrr1[0]+"---"+arrr2[0]);
                                }
                            } catch (Exception e) {
                                // TODO: handle exception
                            }

                            //判断fallprotest是否有多匹配的现象
                            quchong(fallprotest);
                            for (int l = 0; l < fallprotest.size(); l++) {
                                int num1 = 0;
                                String[] ayy1 = fallprotest.get(l).split("---");
                                for (int n = 0; n < fallprotest.size(); n++) {
                                    if (fallprotest.contains(ayy1[0]+"---"+fallprotest.get(n).split("---")[1])) {
                                        num1++;
                                    }
                                }
                                if (num1 == 1 /*&& fallpro.size() != 0*/) {
                                    //这个是准确的，就需要对fallpro中的含有两个的进行去除
                                    fallprocorr.add(fallprotest.get(l));
//									System.out.println(fallprotest.get(l));
//									ArrayList<String> fallproii = new ArrayList<>(fallpro);
//									for (int n = 0; n < fallproii.size(); n++) {
//										if (fallproii.get(n).contains(ayy1[0]) || fallproii.get(n).contains(ayy1[1])) {
//											try {
//												fallpro.remove(fallproii.get(n));
//											} catch (Exception e) {
//												// TODO: handle exception
//											}
//										}
//									}
                                }
                            }
                            fallpro.addAll(fallprotest);
                        }
                    }
                }
            }
        }
        quchong(fallpro);
        quchong(fallprocorr);
//		System.out.println("属性匹配对的个数："+fallpro.size());
//		System.out.println("属性匹配对唯一的个数："+fallprocorr.size());
        ArrayList<String> fallpro1 = new ArrayList<>(fallpro);
        /*System.out.println(fallpro);
        System.out.println("-------------------------------------------------");
        System.out.println(fallprocorr);
        System.out.println("-------------------------------------------------");*/
        for (int i = 0; i < fallpro1.size(); i++) {

            for (int j = 0; j < fallprocorr.size(); j++) {
                String[] arr1 = fallprocorr.get(j).split("---");
                if (fallpro1.get(i).contains(arr1[0]) || fallpro1.get(i).contains(arr1[1]) ) {
                    fallpro.remove(fallpro1.get(i));
                }
            }
        }
        fallpro.addAll(fallprocorr);
		/*for (int i = 0; i < fallpro.size(); i++) {
			System.out.println(fallpro.get(i));
		}*/
        quchong(fallclass);
        //System.out.println(fallclass);
        fallclass.remove("Person---Person");
//		System.out.println(fallpro.size());
//		System.out.println(fallclass.size());
//		for (int i = 0; i < fallclass.size(); i++) {
//			fallclass.get(i).replaceAll("\\s*", "");
//		}
//		ArrayList<String> fallclass1 = new ArrayList<>(fallclass);
        ArrayList<String> fallpro11 = new ArrayList<>();
        for (int i = 0; i < fallpro.size(); i++) {
            if (fallpro.get(i).contains(" ")) {
                String ss = fallpro.get(i);
//				System.out.println("==="+ss.replaceAll(" ", ""));
                ss = ss.replaceAll(" ", "");
//				fallpro.set(i, ss);
//				System.out.println("+++"+fallpro.get(i));
//				fallpro11.remove(fallpro.get(i));
                fallpro11.add(ss);
            }else{
                fallpro11.add(fallpro.get(i));
            }
        }


        ArrayList<ArrayList<String>> all = new ArrayList<>();
        all.add(fallclass);
        all.add(fallpro11);
        return all;

    }

    public static void quchong(ArrayList<String> list){
        HashSet<String> hs = new HashSet<>();
        hs.addAll(list);
        list.clear();
        list.addAll(hs);
    }











}