import files.payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		JsonPath  js = new JsonPath(payload.coursePrice());
		
		// Print no of courses returned by API. the .size() method is used to get size of the array and can be used only in array.
		int count = js.getInt("courses.size()");
		System.out.println(count);
		
		// Print purchase amount
		int amount = js.getInt("dashboard.purchaseAmount");
		System.out.println(amount);
		
		//Print title of the first course
		String title = js.get("courses[0].title");
		System.out.println(title);
		
		//Print all course titles and its prices. 
		//Since the array is dynamic(means the array size can be changed) we use for loop
		// it will start from 0(i=0),
		//i<count --> run/loop until the size of the count (means it will loop/run for 4 time which is the given size of the array)/ number of iterations 
		//i++ --> this means start the next loop once the previous loop is done until it reaches 4th time.
		for(int i=0; i<count; i++) {
			
			String courseTitles = js.get("courses["+i+"].title");
			System.out.println(courseTitles);
			int coursePrices = js.get("courses["+i+"].price");
			System.out.println(coursePrices);
			
			//or instead of assigning it to variables we can either use getSting and getInt method into a print statement directly
//			System.out.println(js.getString("courses["+i+"].title"));
//			System.out.println(js.getInt("courses["+i+"].price"));
//			
			}
		// print number of copies from RPA course
		
		System.out.println("print number of copies from RPA course");
		for(int i=0; i<count; i++) {
			String courseTitles = js.getString("courses["+i+"].title");
			// equalsIgnoreCase will just ignores the upper and lower letters
			if (courseTitles.equalsIgnoreCase("RPA")) {
				int copies = js.getInt("courses["+i+"].copies");
				System.out.println(copies);
				break;
			}
		}
		

	}

}
