package java0.homework;

import java.util.concurrent.*;

/**
 * @Author lzg
 * @Date 2021-05-30 11:13
 * Callable
 */
public class Homework01 {

    public static void main(String[] args) {

        long start = System.currentTimeMillis();

        Callable<Integer> callable = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                int result = sum();
                //返回值
                return result;
            }
        };

        FutureTask<Integer> futureTask = new FutureTask<Integer>(callable);
        Thread thread = new Thread(futureTask);
        thread.start();


//        futureTask.get();
        try {
            int result = futureTask.get(3, TimeUnit.SECONDS);
            // 确保  拿到result 并输出
            System.out.println("异步计算结果为：" + result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }


        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
    }

    private static int sum() {
        return fibo(36);
    }

    private static int fibo(int a) {
        if (a < 2)
            return 1;
        return fibo(a - 1) + fibo(a - 2);
    }
}
