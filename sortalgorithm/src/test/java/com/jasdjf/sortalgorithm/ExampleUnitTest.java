package com.jasdjf.sortalgorithm;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    public int[] getRandomArray(int max){
        int[] array = new int[max];
        Random random = new Random();
        for(int i=0;i<max;i++){
            array[i] = random.nextInt(max);
        }
        return array;
    }

    @Test
    public void test_sort(){
        //int[] arr = {5,3,4,9,6,3,7,4,1,15,584,787,66};
        int[] arr = getRandomArray(10000);
        Sorts sorts = new Sorts();
        //sorts.bubbleSort(arr);
        //sorts.selectionSort(arr);
        //sorts.chooseSort(arr);
        //sorts.quickSort(arr);
        sorts.heapSort(arr);
        sorts.QuickSort(arr);
    }

    @Test
    public void testTryCatch(){
        try{
            System.out.println("try");
            //throw new Exception("////");
            return;
        } catch (Exception e){
            System.out.println("catch");
            //return;
        } finally {
            System.out.println("finally");
            //return;
        }
        System.out.println("out");
    }
}