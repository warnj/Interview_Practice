
public class HW332 {
	
	static int a_called = 0;
	static int b_called = 0;

	public static void main(String[] args) {
		int[] arr = {11};
		System.out.println("Arrlength: " + arr.length);
		System.out.println(first17_a(arr, 0));
		System.out.println(first17_b(arr, 0));
	}

	static int first17_a (int[] array, int i) {
		a_called++;
		System.out.println("a call # " + a_called + " on index: " + i);
		if (i >= array.length) {
			return -1;
		}
		if (array[i] == 17) {
			return 0;
		}
		if (first17_a(array, i + 1) == -1) {
			return -1;
		}
		return 1 + first17_a(array,i+1);
	}

	static int first17_b (int[] array, int i) {
		b_called++;
		System.out.println("b call # " + b_called + " on index: " + i);
		if (i >= array.length) {
			return -1;
		}
		if (array[i] == 17) {
			return 0;
		}
		int x = first17_b(array, i + 1);
		if (x == -1) {
			return -1;
		}
		return x + 1;
	}
}
