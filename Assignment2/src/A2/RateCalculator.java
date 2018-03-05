package A2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class RateCalculator {
	static String fromPostal, toPostal, type;
	static float length, width, height, weight;
	
	public static void main(String args[]) {
		
		if(args == null) {
			System.out.print("No arguments provided!");
			return;
		}
		else if (args.length != 7) {
			System.out.print("Input has to be of the form: FromPostalCode ToPostalCode Length(cm) Width(cm) Height(cm) Weight (kg) PostType(Regular Xpress Priority)");
			return;
		}
		
		fromPostal = args[0].toUpperCase();
		toPostal = args[1].toUpperCase();
		type = args[6].toUpperCase();
		try {
			length = Float.parseFloat(args[2]);
			width = Float.parseFloat(args[3]);
			height = Float.parseFloat(args[4]);
			weight = Float.parseFloat(args[5]);
		} catch (NumberFormatException e) {
			System.out.print("length, width, height and weight should all be floating numbers");
			return;
		}
		
		if (!validPostal(fromPostal)) {
			System.out.print("Invalid FromPostalCode");
			return;
		}
		
		if (!validPostal(toPostal)) {
			System.out.print("Invalid ToPostalCode");
			return;
		}
		
		if ((length <= 0.0) || (length > 200.0)){
			System.out.print("length has to be less than 200 and greater than 0");
			return;
		}
		
		if ((width <= 0.0) || (width > 200.0)){
			System.out.print("width has to be less than 200 and greater than 0");
			return;
		}
		
		if ((height <= 0.0) || (height > 200.0)){
			System.out.print("height has to be less than 200 and greater than 0");
			return;
		}
		
		if ((weight <= 0.0) || (weight > 30.0)){
			System.out.print("weight has to be less than 30 and greater than 0");
			return;
		}
		
		if (length + 2*(height + width) > 300.0) {
			System.out.print("Length + girth must not exceed 300 cm");
			return;
		}
		
		if (!(type.equals("REGULAR")) && !(type.equals("XPRESS")) && !(type.equals("PRIORITY"))) {
			System.out.print("Post type can only be 'Regular', 'Express', or 'Priority'");
			return;
		}
		
		//Arguments are valid
		System.out.print(calculateRate());
	}
	
	public static String calculateRate() {
		String csvFile = type + ".csv";
	    String line = "";
	    String[] entry;
	    String cvsSplitBy = ",";
	    int codeIndex;
	    String code = getCode();
	    try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
	    	line = br.readLine();
	    	entry = line.split(cvsSplitBy);
	    	for (codeIndex = 0; codeIndex < entry.length; codeIndex++) {
	    		if (entry[codeIndex].equals(code)) {
	    			break;
	    		}
	    	}

	        while ((line = br.readLine()) != null) {
	            entry = line.split(cvsSplitBy);
	            if(weight <= Float.parseFloat(entry[0])) {
	            	return "The rate is: " + entry[codeIndex];
	            }
	        }
	
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
		return "";
	}
	
	public static String getCode() {
		String csvFile = "Code.csv";
	    String line = "";
	    String cvsSplitBy = ",";
	    String code = "";
	    try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
	
	        while ((line = br.readLine()) != null) {
	            String[] entry = line.split(cvsSplitBy);
	            if((entry[0].substring(0,3)).equals((fromPostal.substring(0,3)))) {
	            	code += entry[1];
	            	return code;
	            }
	        }
	
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return code;
	}
	public static boolean validPostal(String postal) {
		if(!Character.isLetter(postal.charAt(0)) || !Character.isLetter(postal.charAt(2)) 
		|| !Character.isLetter(postal.charAt(4)) || !Character.isDigit(postal.charAt(1)) 
		|| !Character.isDigit(postal.charAt(3)) || !Character.isDigit(postal.charAt(5))) {
			return false;
		}
		return true;
	}
	
}
