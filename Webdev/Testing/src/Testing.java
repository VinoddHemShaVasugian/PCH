import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class Testing {
	public static void main(String[] args) {
		/*int[] a = new int[] { 5, 2, 4, 1, 3 };
		for (int i = 0; i < a.length; i++) {
			System.out.println(a[i]);

		}
		Arrays.sort(a);
		for (int i = 0; i < a.length; i++) {
			System.out.println(a[i]);

		}
		
		String [] myArray = {"one", "Two", "Three", "Four", "Five", "Six","Seven"};
        System.out.println("Original Array:" + Arrays.asList(myArray));
        Collections.reverse(Arrays.asList(myArray));
        System.out.println("Reversed Array:" + Arrays.asList(myArray));*/
        
     
        	String original, reverse = "";
        	Scanner in = new Scanner (System.in);
        	 

//        	System.out.println("Softcrylic care about the health of our employees and their family members");

        	original = in.nextLine();

        	int length = original.length();

        	for(int i = length -1; i >= 0 ; i--){

        	reverse = reverse + original.charAt(i);

        	System.out.println("Softcrylic care about the health of our employees and their family members"+ reverse);

        	}        
	}
}