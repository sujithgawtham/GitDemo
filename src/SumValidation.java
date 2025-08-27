import org.testng.Assert;
import org.testng.annotations.Test;

import files.payload;
import io.restassured.path.json.JsonPath;

public class SumValidation {
	
	@Test
	public void totalCoursePrice() {
		
		JsonPath js = new JsonPath(payload.coursePrice());
		int sum = 0;
		int count = js.getInt("courses.size()");

		for(int i=0; i<count; i++){
			int price = js.getInt("courses["+i+"].price");
			int copies = js.getInt("courses["+i+"].copies");
			int total = price * copies;
			// to calculate the total price, first declare a variable called "sum" and then initialize it to 0
            // on every loop iteration the total from above variable will go into the below variable sum and gets added such as
			// on the 1st iteration 0 + 300 = 300 and then for 2nd 300 + 160 = 460 and for 3rd 460 + 450 = 910 and for 4th 910 + 252 = 1162
			// so the sum will be 1162
			sum = sum + total;

			System.out.println(total);

		}
		System.out.println(sum);
		int purchaseAmount = js.getInt("dashboard.purchaseAmount");
		Assert.assertEquals(sum, purchaseAmount, "Its not matching");
	}
	

}
