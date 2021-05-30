package java0.homework;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @Author lzg
 * 在main函数启动一个新线程或线程池，
 * * 异步运行一个方法，拿到这个方法的返回值后，退出主线程？
 * CompletableFuture
 */
public class Homework04 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        long start = System.currentTimeMillis();
        //有返回值的异步任务
        CompletableFuture<Integer> cf = CompletableFuture.supplyAsync(() -> {
            int result = sum(); //这是得到的返回值
            return result;

        });
        System.out.println(Thread.currentThread() + ".... time->" + System.currentTimeMillis());
        Integer result = cf.get();
        //等待子任务执行完成

        // 确保  拿到result 并输出
        System.out.println("异步计算结果为：" + result);

        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");

        // 然后退出main线程
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
