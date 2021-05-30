package java0.homework;

import javax.xml.transform.Result;
import java.util.concurrent.*;

/**
 * @Author lzg
 * 在main函数启动一个新线程或线程池，
 * * 异步运行一个方法，拿到这个方法的返回值后，退出主线程？
 * CompletableFuture 指定thenApply / thenApplyAsync异步回调方法
 */
public class Homework06 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        long start = System.currentTimeMillis();
        //有返回值的异步任务
        CompletableFuture<Integer> subf1 = CompletableFuture.supplyAsync(() -> {
            int result = sum(); //这是得到的返回值
            return result;

        });
        System.out.println(Thread.currentThread() + ".... time->" + System.currentTimeMillis());

  /*      //subf1执行异常时，将抛出的异常作为入参传递给回调方法
        CompletableFuture<Integer> exceptionHandle= subf1.exceptionally((param)->{
            System.out.println(Thread.currentThread()+" start,time->"+System.currentTimeMillis());

            param.printStackTrace();
            System.out.println(Thread.currentThread()+" exit,time->"+System.currentTimeMillis());
            return -1;
        });

        System.out.println(    exceptionHandle.get());*/

        //cf关联的异步任务的返回值作为方法入参，传入到thenApply的方法中
        CompletableFuture<Integer> subf2 = subf1.thenApply((result) -> {
            try {
                Thread.sleep(TimeUnit.SECONDS.toMillis(2));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return result;
        });

        Integer result = subf2.get();
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
