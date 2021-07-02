
import java.util.Arrays;

public class ArrayGame {

	public static void main(String[] args) {
		int array[] = new int[] { 1, 2, 4, 6, 6,9 };
		System.out.println(returnMinimumOperations(array));
	}
	

	public  static int returnMinimumOperations(int[] array) {
		int i = 0;
		do {
			if (checkIfArrayContainsSameNumbers(array)) {
				System.out.println("Array is equal now :: " + Arrays.toString(array));
				break;
			} else {
				i++;
				System.out.println("Array Situation now :: " + Arrays.toString(array));
				int indexOfHighestNumber = getTheIndexOfHighestNumberInTheArray(array);
				array = incrementValuesByOneExcpetNumberInGivenIndex(array, indexOfHighestNumber);
				continue;
			}
		} while (true);
		return i;

	}

	public static boolean checkIfArrayContainsSameNumbers(int[] array) {
		int number = array[0];
		for (int i : array) {
			if (number != i) {
				return false;
			}
			number = i;
		}
		return true;
	}

	public static int getTheIndexOfHighestNumberInTheArray(int[] array) {
		int number = array[0];
		int index = 0;
		for (int i = 0; i < array.length; i++) {
			if(number < array[i]) {
				index = i;
				number = array[i];
			}
		}
		return index;
	}

	public static int[] incrementValuesByOneExcpetNumberInGivenIndex(int[] array, int index) {
		for (int i = 0; i < array.length; i++) {
			if(i != index) {
				int value = array[i];
				array[i] = ++value;
			}
		}
		return array;
	}

}
