package com.Test;

public class TestClass {

	public static void main(String[] args) {

		/*
		 * //Unsorted Array (+ve, -ve, Dupl) -- unsorted array, +ve ,-ve, duplicates
		 * largest and the 2nd largest number from the array
		 */
		int arr[] = { 8,3, 6, 2, 1, -9, 7, 3, 5, 5, 1 };
		
		int largest = Integer.MIN_VALUE;
		int second = Integer.MIN_VALUE;
		for (int i = 0; i < arr.length; i++) {

			if (largest < arr[i]) {
				second = largest;
				largest = arr[i];

			} else if (arr[i] != largest && second < arr[i]) {
				second = arr[i];
			}

		}

		System.out.println("Largest Number is: " + largest);

		System.out.println("Second Largest Number is: " + second);
	}

}
