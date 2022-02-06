// Vatsal Shah

import java.util.*;

public class BinaryNumber {
	private int data[];  
	private boolean overflow; 
	
	public BinaryNumber(int length) {
		if(length < 1) {
			throw new IllegalArgumentException("Cannot have length less than 1");
		}
		data = new int[length]; 
		for(int i=0; i < data.length; i++) {
			data[i] = 0;
		}
	}
	 	
	public BinaryNumber(String str) {
		if(str.length() == 0) {
			throw new IllegalArgumentException("Cannot have empty string");
		}
		if(str.trim().length() == 0) {
			throw new IllegalArgumentException("Cannot have empty spaces as string");
		}
		if(str.length() != str.trim().length()) {
			throw new IllegalArgumentException("Cannot have leading or trailing spaces");
		}
		data = new int[str.length()];
		for(int i=0; i < data.length; i++) {
			char charAt = str.charAt(i);
			if((Character.getNumericValue(charAt) != 0) && (Character.getNumericValue(charAt) != 1)) {
				throw new IllegalArgumentException("Can only have 1s and 0s.");	
			}
			data[i] = Character.getNumericValue(charAt);
		}
	}
	
	public int getLength() {
		return data.length;
	}
	
	public int getDigit(int index) {
		if(index > getLength()) {
			throw new IllegalArgumentException("Index out of Bounds");
		}
		return data[index];
	}
	
//	Private reallocation method
	private int[] reallocate(int amount) {
		return Arrays.copyOf(data, data.length + amount);
	}
	
	public void shiftR(int amount) {
		if(amount < 0) {
			throw new IllegalArgumentException("Cannot have amount less than 0");
		}
		int[] copyArray = reallocate(amount);
		int temp;
		int rightElement;
		for(int i = 0; i < amount; i++) {
			rightElement = copyArray[copyArray.length - 1];
			for(int j = 0; j < copyArray.length; j++) {
				temp = copyArray[j];
				copyArray[j] = rightElement;
				rightElement = temp;
			}	
		}
		data = copyArray;
	}
	
	public void add(BinaryNumber aBinaryNumber) {
		if(data.length != aBinaryNumber.getLength()) {
			throw new IllegalArgumentException("Length not equal!");
		} else {
			int carry = 0;
			clearOverflow();
			for (int i = 0; i < data.length; i++) {
				int sum = data[i] + aBinaryNumber.data[i] + carry;
				switch (sum){
					case 1:
						data[i] = sum;
						carry = 0;
						clearOverflow();
						break;
					case 2:
						data[i] = 0;
						carry = 1;
						overflow = true;
						break;
					case 3:
						data[i] = 1;
						carry = 1;
						overflow = true;
						break;
					default:
						data[i] = sum;
						carry = 0;
						clearOverflow();
				}		
			}
		}
	}
	
	public String toString() {
		if(overflow) {
			return "Overflow";
		}
		String binary = "";
		for (int i = 0; i < data.length; i++) {
			binary += String.valueOf(data[i]); 
		}
		return binary;
	}
	
	public int toDecimal() {
		int decimal = 0;
		for(int i = 0; i < data.length; i++) {
			decimal = (int) (Math.pow(2, i) * data[i]) + decimal;
		}
		return decimal;
	}
	
	public void clearOverflow() {
		overflow=false;
	}
	
	public static void main(String[] args) {
		BinaryNumber b1=new BinaryNumber(5);
		BinaryNumber b2=new BinaryNumber("00001"); 
		System.out.println(b1.toString());
		System.out.println(b2.toDecimal());
		System.out.println(b1.getLength());
		System.out.println(b1.getDigit(1));
		b1.shiftR(0);
		b1.add(b2);
		System.out.println(b1.toString());
	}
}
