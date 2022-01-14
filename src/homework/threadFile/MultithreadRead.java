package homework.threadFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MultithreadRead implements Runnable {
    public static AtomicInteger countOfKeyword = new AtomicInteger();
    public static String path = "C:\\Users\\NEK\\Desktop\\sample.txt";
    String keyword;
    Thread thread;
    List<String> strings;

    MultithreadRead(String keyword, List<String> strings) {
        this.strings = strings;
        this.keyword = keyword;
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        for (String string : strings) {
            if (string.contains(keyword))
                countOfKeyword.incrementAndGet();

        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        int count = (int) Files.lines(Paths.get(path)).count();
        String keyword = ";";
        System.out.println("all lines:" + count);
        long currentTime = System.currentTimeMillis();

        List<String> string = Files.readAllLines(Paths.get(path));
        List<String> str1 = string.subList(1, 700000);
        List<String> str2 = string.subList(700001, 1400000);
        List<String> str3 = string.subList(1400001, 2100000);
        List<String> str4 = string.subList(2100001, 2800000);
        List<String> str5 = string.subList(2800001, count);

        new MultithreadRead(keyword, str1).thread.join();
        new MultithreadRead(keyword, str2).thread.join();
        new MultithreadRead(keyword, str3).thread.join();
        new MultithreadRead(keyword, str4).thread.join();
        new MultithreadRead(keyword, str5).thread.join();

        System.out.println("found lines :" + countOfKeyword);
        long finishTime = System.currentTimeMillis();
        System.out.println("time: " + (finishTime - currentTime));

    }
}


