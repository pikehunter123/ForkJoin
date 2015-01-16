/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forkandjoin;

import static forkandjoin.Main.partition;
import java.util.Arrays;
import static java.util.concurrent.ForkJoinTask.invokeAll;
import java.util.concurrent.RecursiveAction;

/**
 *
 * @author Nesterov001
 */
public class Main {

    static int N = 100_000;
    
    public static void main(String[] args) {
        //ForkJoinPool forkJoinPool = new ForkJoinPool();
        int ar[] = new int[N];
        for (int i = 0; i < N; i++) {
            //int num = (int) (Math.random() * 10);
            //ar[i] = num;
            ar[i] = N;
        }
        long millis = System.currentTimeMillis();
        quicksort(ar, 0, ar.length - 1);
//        forkJoinPool.execute(new Sort(ar, 0, ar.length - 1));
//        forkJoinPool.shutdown();
//        try {
//            forkJoinPool.awaitTermination(1, TimeUnit.DAYS);
//        } catch (InterruptedException ex) {
//            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//        }
        System.out.println(System.currentTimeMillis() - millis);
        //System.out.println(Arrays.toString(ar));

    }

    static void quicksort(int[] ar, int start, int end) {
        if (start >= end) {
            return;
        }
        int pivot = partition(ar, start, end);
        quicksort(ar, start, pivot - 1);
        quicksort(ar, pivot + 1, end);
    }

    static int partition(int[] ar, int start, int end) {
        int p = ar[start];
        int marker = start;
        for (int i = start; i <= end; i++) {
            if (ar[i] < p) {
                int temp = ar[i];
                for (int j = i; j >= marker + 1; j--) {
                    ar[j] = ar[j - 1];
                }
                ar[marker] = temp;
                marker++;
            }
        }
        return marker;
    }
}

class Sort extends RecursiveAction {

    int[] array;
    int start;
    int end;

    public Sort(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }    
    
    @Override
    protected void compute() {        
        if (start >= end) {
            return;
        }
        int pivot = partition(array, start, end);
        Sort sort1 = new Sort(array, start, pivot - 1);
        Sort sort2 = new Sort(array, pivot + 1, end);       
        invokeAll(Arrays.asList(sort1, sort2));        
    }
    
    
}
