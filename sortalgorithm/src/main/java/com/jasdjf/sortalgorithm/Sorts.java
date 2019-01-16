package com.jasdjf.sortalgorithm;

public class Sorts {

    /**
     * 两两进行比较，大数下沉，小数冒起来。
     */
    public void bubbleSort(int[] srcArr){
        int[] arr = new int[srcArr.length];
        System.arraycopy(srcArr,0,arr,0,srcArr.length);
        long time1 = System.currentTimeMillis();
        int tmp;
        boolean flag;
        for (int i = arr.length-1; i>0; i--) {
            flag = false;
            for (int j = 0; j < i; j++) {
                if(arr[j] > arr[j+1]){
                    tmp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = tmp;
                    flag = true;
                }
            }
            if(!flag)   break;//如果一趟下来没有一次交换位置的操作，则表示当前序列已经是有序序列了，就不需要再进行下一趟了
        }
        long time2 = System.currentTimeMillis();
        System.out.println("Bubble sort time="+(time2-time1));
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            builder.append(arr[i]).append(",");
        }
        System.out.println(builder.toString());
    }

    /**
     * 遍历数组，每次从数组中n位置开始遍历，找出数组中最小的数，与位置n进行交换
     */
    public void selectionSort(int[] srcArr){
        int[] arr = new int[srcArr.length];
        System.arraycopy(srcArr,0,arr,0,srcArr.length);
        long time1 = System.currentTimeMillis();
        int index;
        int tmp;
        /*for (int i = arr.length-1; i>0; i--) {
            index = 0;
            for (int j = 0; j < i; j++) {
                if(arr[index] < arr[j+1]){
                    index = j+1;
                }
            }
            if (index != i) {
                tmp = arr[i];
                arr[i] = arr[index];
                arr[index] = tmp;
            }
        }*/
        for (int i = 0; i < arr.length - 1; i++) {
            index = i;
            for (int j = i+1; j < arr.length; j++) {
                if(arr[index] > arr[j]){
                    index = j;
                }
            }
            if (index != i) {
                tmp = arr[i];
                arr[i] = arr[index];
                arr[index] = tmp;
            }
        }
        long time2 = System.currentTimeMillis();
        System.out.println("Selection sort time="+(time2-time1));
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            builder.append(arr[i]).append(",");
        }
        System.out.println(builder.toString());
    }

    public void chooseSort(int[] srcArr){
        int[] arr = new int[srcArr.length];
        System.arraycopy(srcArr,0,arr,0,srcArr.length);
        int min_index;
        int tmp;
        long time1 = System.currentTimeMillis();
        for(int i=0;i<arr.length-1;i++){
            min_index = i;
            for(int j=i+1;j<arr.length;j++){
                if(arr[min_index]>arr[j]){
                    min_index = j;
                }
            }
            if (min_index != i) {
                tmp = arr[i];
                arr[i] = arr[min_index];
                arr[min_index] = tmp;
            }
        }
        long time2 = System.currentTimeMillis();
        System.out.println("choose sort time:"+(time2-time1)+"ms");
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            builder.append(arr[i]).append(",");
        }
        System.out.println(builder.toString());
    }

    public void quickSort(int[] srcArr){
        int[] arr = new int[srcArr.length];
        System.arraycopy(srcArr,0,arr,0,srcArr.length);
        long time1 = System.currentTimeMillis();
        quick_sort(arr,0,arr.length-1);
        long time2 = System.currentTimeMillis();
        System.out.println("Quick sort time="+(time2-time1));
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            builder.append(arr[i]).append(",");
        }
        System.out.println(builder.toString());
    }

    private void quick_sort(int[] arr,int start,int end){
        if(start>=end){
            return;
        }
        int key = arr[end];
        int i=start,j = end;
        while(i<j){
            while(i<j && arr[i]<=key){
                i++;
            }
            if(i<j){
                arr[j] = arr[i];
            }
            while(i<j && arr[j]>key){
                j--;
            }
            if(i<j){
                arr[i] = arr[j];
            }
        }
        arr[i] = key;
        quick_sort(arr,start,i-1);
        quick_sort(arr,i+1,end);
    }


    public void heapSort(int[] srcArr){
        int[] arr = new int[srcArr.length];
        System.arraycopy(srcArr,0,arr,0,srcArr.length);
        long time1 = System.currentTimeMillis();
        for (int i = arr.length / 2; i >= 0; i--) {
            adjustHeap(arr,i,arr.length);
        }
        int tmp;
        for (int i = arr.length-1; i >= 0; i--) {
            tmp = arr[0];
            arr[0] = arr[i];
            arr[i] = tmp;
            adjustHeap(arr,0,i);
        }
        long time2 = System.currentTimeMillis();
        System.out.println("Heap sort time:"+(time2-time1));
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            builder.append(arr[i]).append(",");
        }
        System.out.println(builder.toString());
    }

    private void adjustHeap(int[] arr,int parent,int end){
        int childIndex = 2*parent+1;
        int tmp = arr[parent];
        while(childIndex<end){//这里不是只判断当前节点与其子节点，如果发生了变化，那么交换了的那个子节点下的所有树都需要重新判断
            if(childIndex+1<end && arr[childIndex]<arr[childIndex+1]){
                childIndex++;
            }
            if(tmp>arr[childIndex]){
                break;
            }
            arr[parent] = arr[childIndex];
            parent = childIndex;
            childIndex = parent*2+1;
        }
        arr[parent] = tmp;
    }

    public void QuickSort(int[] srcArr){
        int[] arr = new int[srcArr.length];
        System.arraycopy(srcArr,0,arr,0,srcArr.length);
        long time1 = System.currentTimeMillis();
        Quick_Sort(arr,0,arr.length-1);
        long time2 = System.currentTimeMillis();
        System.out.println("Quick sort time:"+(time2-time1));
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            builder.append(arr[i]).append(",");
        }
        System.out.println(builder.toString());
    }

    private void Quick_Sort(int[] arr,int start,int end){
        if(start >= end){
            return;
        }
        int key = arr[end];
        int i=start,j=end;
        while(i < j){
            while(i<j && arr[i] <= key){
                i++;
            }
            if(i<j){
                arr[j] = arr[i];
            }
            while(i<j && arr[j]>key){
                j--;
            }
            if(i<j){
                arr[i] = arr[j];
            }
        }
        arr[i] = key;
        Quick_Sort(arr,start,i-1);
        Quick_Sort(arr,i+1,end);
    }
}
