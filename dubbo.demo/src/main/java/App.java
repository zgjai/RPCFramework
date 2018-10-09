package main.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * @author zhangguijiang
 * @date 2018/10/8 下午7:55
 * @description
 */
public class App {

    private static final int nRounds = 10000000;

    private static final Random random = new Random();
    private static final List<String> list = new ArrayList<>();
    private static final Map<String, Integer> map = new HashMap<>();

    static {
        map.put("S", 1);
        map.put("A1", 5);
        map.put("A2", 5);
        map.put("A3", 5);
        map.put("B1", 12);
        map.put("B2", 12);
        map.put("B3", 12);
        map.put("B4", 12);
        map.put("B5", 12);
        map.put("B6", 12);
        map.put("B7", 12);
        map.forEach((k, v) -> {
            for (int i = 0; i < v; i++) {
                list.add(k);
            }
        });
    }

    private double run(int rounds) {
        long totalTimes = 0;
        for (int round = 0; round < rounds; round++) {
            Set<String> resultSet = new HashSet<>();
            long times = 0;
            for (; ; ) {
                if (resultSet.size() == map.keySet().size()) {
                    totalTimes += times;
                    break;
                }
                times++;
                resultSet.add(list.get(random.nextInt(list.size())));
            }
        }
        return ((float) totalTimes / rounds);
    }

    public static void main(String[] args) {
        App app = new App();
        System.out.println("average number of times =" + app.run(nRounds));
    }
}
