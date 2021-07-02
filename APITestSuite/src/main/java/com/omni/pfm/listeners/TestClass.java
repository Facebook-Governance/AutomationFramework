package com.omni.pfm.listeners;

public class TestClass {

	public static void main(String[] args) {
		
		//All -
		

		int arr[] = { 16, 17, 4, 2, 3, 5, 17, 2};

		for (int i = 0; i < arr.length; i++) {
		
			int j;
			for (j = i + 1; j < arr.length; j++) {
				if (arr[i] < arr[j]) {
					/*
					 * System.out.println(arr[i] + " Not a leader");
					 */					break;
				}
				
			}
			if(j==arr.length) {
				System.out.println(arr[i]);
			}


		}

	}

}
