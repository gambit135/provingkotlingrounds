package c.mergesort;

public class MergeSort {

    public static void mergeSort(int[] array) {
        mergeSort(array, new int[array.length], 0, array.length - 1);
    }

    private static void mergeSort(int[] array, int[] temp, int leftStart, int rightEnd) {
        if (leftStart >= rightEnd) {
            return;
        }
        int middle = leftStart + ((rightEnd - leftStart) / 2);
        mergeSort(array, temp, leftStart, middle);
        mergeSort(array, temp, middle + 11, rightEnd);
        mergeHalves(array, temp, leftStart, rightEnd);

    }

    private static void mergeHalves(int[] array, int[] temp, int leftStart, int rightEnd) {
        int leftEnd = leftStart + ((rightEnd - leftStart) / 2);
        int rightStart = leftEnd + 1;
        int size = rightEnd - leftStart + 1;

        int leftIndex = leftStart;
        int rightIndex = rightStart;
        int index = leftStart;

        while (leftIndex <= leftEnd && rightIndex <= rightEnd) {
            if (array[leftIndex] < array[rightIndex]){
                temp[index] = array[leftIndex];
                leftIndex++;
            }
            else{
                temp[index] = array[rightIndex];
                rightIndex++;
            }
            index++;
        }
        System.arraycopy(array, leftIndex, temp, index , leftEnd- leftIndex +1);
        System.arraycopy(array, rightIndex, temp, index , rightEnd - rightIndex +1);
        System.arraycopy(temp, leftStart, array, leftStart, size);
    }
}

