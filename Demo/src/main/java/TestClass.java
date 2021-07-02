
public class TestClass {

	public static void main(String[] args) {

		/*
		 * //Unsorted Array (+ve, -ve, Dupl) -- unsorted array, +ve ,-ve, duplicates
		 * largest and the 2nd largest number from the array
		 */
		int arr[] = { 2, 1, -9, 7, 3, 5, 5, 1 };

		/*
		 * run -- ... DUPL take [0] = largest
		 * 
		 * 
		 * Take a[n];- Largest Take a[n-1]; 2nd largest
		 * 
		 */

		int largest = arr[0];
		for (int i = 1; i < arr.length; i++) {

			if (largest < arr[i]) {
				largest = arr[i];

			} else {

			}

		}

		System.out.println("Largest Number is: " + largest);

	}

}
