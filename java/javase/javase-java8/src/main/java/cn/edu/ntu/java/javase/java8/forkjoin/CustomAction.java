package cn.edu.ntu.java.javase.java8.forkjoin;

import cn.edu.ntu.java.javase.java8.utils.AccumulatorHelper;

import java.util.concurrent.RecursiveAction;

/**
 * @author zack <br>
 * @create 2020-04-04 21:15 <br>
 */
public class CustomAction extends RecursiveAction {

    private static final long THRESHOLD = 10000000L;
    private long start;
    private long end;

    public CustomAction() {}

    public CustomAction(long start, long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected void compute() {
        long length = end - start;

        if (length <= THRESHOLD) {
            for (long i = start; i <= end; i++) {
                AccumulatorHelper.accumulate(i);
            }
        } else {
            long middle = (start + end) / 2;
            CustomTask forkJoinLeft = new CustomTask(start, middle);
            forkJoinLeft.fork();
            CustomTask forkJoinRight = new CustomTask(middle + 1, end);
            forkJoinRight.fork();
        }
    }
}
