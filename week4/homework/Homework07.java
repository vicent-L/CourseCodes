package java0.homework;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @Author lzg
 * 在main函数启动一个新线程或线程池，
 * * 异步运行一个方法，拿到这个方法的返回值后，退出主线程？
 * CompletableFuture  whenComplete  回调方法
 */
public class Homework07 {

    public static void main(String[] args) {

        long start = System.currentTimeMillis();
        //有返回值的异步任务
        CompletableFuture<Integer> subf1 = CompletableFuture.supplyAsync(() -> {
            int result = sum(); //这是得到的返回值
            return result;

        });
        System.out.println(Thread.currentThread() + ".... time->" + System.currentTimeMillis());

        CompletableFuture<Integer> subf2 = subf1.whenComplete((a, b) -> {

            if (b != null) {
                System.out.println("subf1 run exeception ");
//                b.printStackTrace();
            } else {
                System.out.println("subf1 run succ,result->" + a);
            }
        });

        Integer result = null;
        try {
            result = subf2.get();
            System.out.println("异步计算结果为：" + result);
        } catch (InterruptedException e) {
            System.out.println("InterruptedException");
            e.printStackTrace();
        } catch (ExecutionException e) {
            System.out.println("ExecutionException");
            e.printStackTrace();
        }


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
