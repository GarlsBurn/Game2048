package com.javarush.task.pro.task08.task0803;

import java.util.Scanner;

/* 
Минимальный элемент массива
*/

public class Solution {

    public static void main(String[] args) {
        int[] intArray = getArrayOfTenElements();
        System.out.println(min(intArray));
    }

    public static int min(int[] ints) {
        int x;
        int[] arr2 = new int[10];
        arr2 = getArrayOfTenElements();
        for (int i = 1; i < 10; i++){
            ints[i - 1] = arr2[i];
            
        }
        x = ints[9];
        return x;
    }

    public static int[] getArrayOfTenElements() {
        Scanner con = new Scanner(System.in);
        int a = con.nextInt();
        int[] arr = new int[10];
        for (int i = 0; i < 10; i++){
        arr[i] = a;
        }
        return arr;
    }
}
