package com.jasdjf.testmvp;

import org.junit.Test;

import java.io.File;
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

    @Test
    public void renameFIle(){
        File file = new File("C:\\Users\\zhuangwei\\Desktop\\g_p");
        File[] childFiles = file.listFiles();
        for(File childFile : childFiles){
            if(childFile.getName().startsWith("IMG")){
                childFile.renameTo(new File(childFile.getAbsolutePath().toLowerCase()));
            }
        }
    }

    @Test
    public void test_bubble(){//冒泡排序，10000个数排序耗时大约在180~200ms之间，优化后(即加了是否交换标志之后)，耗时大约在140~160ms之间
        //int[] arr = new int[]{15,28,27,13,29};
        int[] arr = getRandomArray(10000);
        long time1 = System.currentTimeMillis();
        int temp;//临时变量
        boolean flag;//是否交换的标志
        for(int i=0; i<arr.length-1; i++){   //表示趟数，一共arr.length-1次。

            flag = false;
            for(int j=arr.length-1; j>i; j--){

                if(arr[j] < arr[j-1]){
                    temp = arr[j];
                    arr[j] = arr[j-1];
                    arr[j-1] = temp;
                    flag = true;
                }
            }
            if(!flag)
                break;
        }

        /*int tmp;
        for(int i=1;i<arr.length;i++){
            for(int j=0;j<arr.length-i;j++){
                if(arr[j]>arr[j+1]){
                    tmp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = tmp;
                }
            }
        }*/

        long time2 = System.currentTimeMillis();
        String str = "";
        for(int i=0;i<arr.length;i++){
            str += arr[i]+" ";
        }
        System.out.println(str);
        System.out.println("time:"+(time2-time1)+"ms");

    }

    @Test
    public void chooseSort(){//选择排序，10000个数排序耗时大约在85~100ms之间
        //int[] arr = new int[]{15,28,27,13,29};
        int[] arr = getRandomArray(10000);
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
        String str = "";
        for(int i=0;i<arr.length;i++){
            str += arr[i]+" ";
        }
        System.out.println(str);
        System.out.println("time:"+(time2-time1)+"ms");
    }

    @Test
    public void insertionSort(){//插入排序，10000个数排序耗时大约在25~40ms之间
        //int[] arr = new int[]{15,28,27,13,29};
        int[] arr = getRandomArray(10000);
        int tmp;
        long time1 = System.currentTimeMillis();
        for(int i=1;i<arr.length;i++){
            for(int j=i;j>0;j--){
                if (arr[j] < arr[j-1]) {
                    tmp = arr[j];
                    arr[j] = arr[j-1];
                    arr[j-1] = tmp;
                } else {
                    break;
                }
            }
        }
        long time2 = System.currentTimeMillis();
        String str = "";
        for(int i=0;i<arr.length;i++){
            str += arr[i]+" ";
        }
        System.out.println(str);
        System.out.println("time:"+(time2-time1)+"ms");
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
    public void shellSort(){//希尔排序，10000个数排序耗时大约在5~6ms之间
        //int[] arr = new int[]{15,28,27,13,29};
        //int[] arr = new int[]{4,7,9,6,8,5,3};
        int[] arr = getRandomArray(10000);
        int tmp;
        long time1 = System.currentTimeMillis();
        /*for(int grap=arr.length/2;grap>0;grap=grap/2){
            //System.out.println("grap:"+grap);
            for(int i=grap;i<arr.length;i++){
                int j=i;
                while(j-grap>=0){
                    //System.out.println("arr:"+arr[j]);
                    if (arr[j] < arr[j-grap]) {
                        tmp = arr[j];
                        arr[j] = arr[j-grap];
                        arr[j-grap] = tmp;
                    } else {
                        break;
                    }
                    j -= grap;
                }
            }
        }*/
        int grap=arr.length;
        do{
            grap=grap/3+1;
            //System.out.println("grap:"+grap);
            for(int i=grap;i<arr.length;i++){
                int j=i;
                while(j-grap>=0){
                    //System.out.println("arr:"+arr[j]);
                    if (arr[j] < arr[j-grap]) {
                        tmp = arr[j];
                        arr[j] = arr[j-grap];
                        arr[j-grap] = tmp;
                    } else {
                        break;
                    }
                    j -= grap;
                }
            }
        } while(grap>1);
        long time2 = System.currentTimeMillis();
        String str = "";
        for(int i=0;i<arr.length;i++){
            str += arr[i]+" ";
        }
        System.out.println(str);
        System.out.println("time:"+(time2-time1)+"ms");
    }

    @Test
    public void testQuickSort(){//快速排序，耗时大约在3~4ms之间
        //int[] arr = {8,7,9,6,8,5,3};
        int[] arr = getRandomArray(10000);
        long time1 = System.currentTimeMillis();
        quickSort(arr,0,arr.length-1);
        long time2 = System.currentTimeMillis();
        String str = "";
        for(int i=0;i<arr.length;i++){
            str += arr[i]+" ";
        }
        System.out.println(str);
        System.out.println("time:"+(time2-time1)+"ms");
    }

    private void quickSort(int[] arr,int start,int end){
        if(start>=end){
            return;
        }
        int first = arr[start];
        int last = arr[end];
        int middle = arr[(start+end)/2];
        int key;
        if((first>=middle && first<=last) || (first>=last && first<=middle)){
            key = first;
        } else if((middle>=first && middle<=last) || (middle>=last && middle<=first)){
            key = middle;
        } else {
            key = last;
        }
        //int key = arr[end];
        int i=start,j=end;
        while(i<j){
            while(i<j && arr[i]<=key){
                i++;
            }
            if(i<j){
                arr[j] = arr[i];
            }
            while(j>i && arr[j]>key){
                j--;
            }
            if(j>i){
                arr[i] = arr[j];
            }
        }
        arr[i] = key;
        quickSort(arr,start,i-1);
        quickSort(arr,i+1,end);
    }

    @Test
    public void heapSort(){
        //int[] arr = {8,7,9,6,8,5,3,4,5};
        int[] arr = getRandomArray(10000);
        long time1 = System.currentTimeMillis();
        //构造初始大顶堆，大顶堆是指所有非叶节点都大于其子节点的完全二叉树
        for(int i=arr.length/2-1;i>=0;i--){//从最后一个非叶节点开始
            adjustHeap(arr,i,arr.length);
        }
        int tmp;
        //将堆顶元素与最后一个元素交换位置，然后去掉最后一个元素，将剩下的元素再当做一个完全二叉树，继续构造大顶堆
        for(int j=arr.length-1;j>0;j--){
            //交换堆顶元素与最后一个元素
            tmp = arr[0];
            arr[0]=arr[j];
            arr[j] = tmp;

            adjustHeap(arr,0,j);//将剩余元素组成的完全二叉树继续构造大顶堆
        }
        long time2 = System.currentTimeMillis();
        String str = "";
        for(int i=0;i<arr.length;i++){
            str += arr[i]+" ";
        }
        System.out.println(str);
        System.out.println("time:"+(time2-time1)+"ms");
    }

    private void adjustHeap(int[] arr,int parent,int length){
        int tmp = arr[parent];
        int childIndex = 2*parent+1;//获取父节点的左子节点
        while(childIndex<length){
            //判断是否有右子节点，如果有且右子节点的值大于左子节点，则index指向右子节点
            if(childIndex+1<length && arr[childIndex]<arr[childIndex+1]){
                childIndex++;
            }
            // 比较父节点与子节点中的较大值，若父节点的值大，则没必要再向下比较
            // 为什么没必要再向下比较，这里有两种情况：
            // 1、如果是首次构造大顶堆时进入这里，由于这时是从最后一个非叶节点开始的，前面已经都已经比较过了，这里就没必要再进行比较
            // 2、如果是后面交换了堆顶值和最后一个值时进入这里，这时除了首节点可能小于其子节点的值，其他节点都在首次构造大顶堆时就已经比较过了
            if(tmp>=arr[childIndex]){
                break;
            }
            arr[parent] = arr[childIndex];//交换了父节点与子节点中的较大值
            parent = childIndex;
            childIndex = 2*childIndex+1;//将交换了的子节点当做下一个父节点，获取其左子节点，继续下一轮比较
        }
        arr[parent] = tmp;
    }

    @Test
    public void mergeSort(){
        int[] arr = getRandomArray(10000);
        //int[] arr = {8,7,9,6,8,5,3,4,5,1};
        int[] result = new int[arr.length];
        long time1 = System.currentTimeMillis();
        devide(arr,0,arr.length,result);
        long time2 = System.currentTimeMillis();
        String str = "";
        for(int i=0;i<arr.length;i++){
            str += arr[i]+" ";
        }
        System.out.println(str);
        System.out.println("time:"+(time2-time1)+"ms");
    }

    private void conquer(int[] arr,int start,int middle,int end,int[] result){
        int i=start,j=middle;
        int m = 0;
        while(i<middle && j<end){
            if(arr[j]<=arr[i]){
                result[m++] = arr[j++];
            } else {
                result[m++] = arr[i++];
            }
        }
        while(i<middle){
            result[m++] = arr[i++];
        }
        while(j<end){
            result[m++] = arr[j++];
        }
        System.arraycopy(result, 0, arr, start, m);
    }

    private void devide(int[] arr,int start,int end,int[] result){
        if(start<end-1){
            int mid = (start+end)/2;
            devide(arr,start,mid,result);
            devide(arr,mid,end,result);
            conquer(arr,start,mid,end,result);
        }
    }

    int reverseOrderPairCount = 0;
    @Test
    public void mergeSortForReverseOrderPair(){//用归并排序求逆序数对
        int arr[] = {1, 7, 2, 9, 6, 4, 5, 3};
        int[] tmp = new int[arr.length];
        devideForReverseOrderPair(arr,0,arr.length,tmp);
        String str = "";
        for(int i=0;i<arr.length;i++){
            str += arr[i]+" ";
        }
        System.out.println(str);
        System.out.println("reverseOrderPairCount="+reverseOrderPairCount);
    }

    private void devideForReverseOrderPair(int[] arr,int start,int end,int[] tmp){
        if(start<end-1){
            int mid = (start+end)/2;
            devideForReverseOrderPair(arr,start,mid,tmp);
            devideForReverseOrderPair(arr,mid,end,tmp);
            conquerForReverseOrderPair(arr,start,mid,end,tmp);
        }
    }

    private void conquerForReverseOrderPair(int[] arr,int start,int middle,int end,int[] tmp){
        int i=start,j=middle;
        int k = 0;
        while(i<middle && j<end){
            if(arr[i]<=arr[j]){
                tmp[k++] = arr[i++];
            } else {
                tmp[k++] = arr[j++];
                reverseOrderPairCount += middle-i+1;
            }
        }
        while (i < middle) {
            tmp[k++] = arr[i++];
        }
        while (j < end) {
            tmp[k++] = arr[j++];
        }
        System.arraycopy(tmp,0,arr,start,k);
    }

    @Test
    public void test(){//有一个数组，里面除一个数只出现一次外其余的数都出现两次，找出数组中只出现一次的数,原理是相同的两个数亦或结果为0
        int result = 0;
        int[] arr = {5,5,2,2,8,3,3,6,6,9,9};
        for(int i=0;i<arr.length;i++){
            result ^= arr[i];
        }
        System.out.println(result);
    }

    @Test
    public void test1(){
        int[] arr = {54,54,25,25,85,34,34,7,655,655,977,977};
        int result = 0;
        for(int i=0;i<arr.length;i++){
            result^=arr[i];
        }
        int n = 0;
        for(n=0;n<32;n++){
            if((result >> n)==1){
                break;
            }
        }
        int resultA = 0;
        int resultB = 0;
        for(int i=0;i<arr.length;i++){
            if((arr[i] >> n)==1){
                resultA ^= arr[i];
            } else {
                resultB ^= arr[i];
            }
        }
        System.out.println(resultA);
        System.out.println(resultB);
    }

    @Test
    public void randomSum(){
        Random random = new Random();
        int sum = 20;
        int n = 4;
        int arr[] = new int[n-1];
        for (int i=0;i<n-1;i++) {
            arr[i] = random.nextInt(20);
        }
        int tmp[] = new int[arr.length];
        devide(arr,0,arr.length,tmp);
        String str = "";
        for(int i=0;i<arr.length;i++){
            str += arr[i]+" ";
        }
        System.out.println(str);
        String str1 = "";
        for(int i=0;i<n;i++){
            str1+=(i+1)+"：";
            if(i==0){
                str1 += arr[i];
            } else if(i==arr.length){
                str1 += sum-arr[i-1];
            } else {
                str1 += arr[i]-arr[i-1];
            }
            str1+="\n";
        }
        System.out.println(str1);
    }
}