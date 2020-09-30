import org.json.simple.JSONObject;
import java.util.Hashtable;
import java.util.Enumeration;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class HashedGrocery{
	private int businessDay = 1;
	private Hashtable<String, Item> hashtable = new Hashtable<>();

 /*
  * This method adds Items to the hashtable
  * 
  * 
  * 
  * @throws Exception
  * When an Item is being added to the hashtable that already exists within
  * the hashtable.
  * 
  */
	public void addItem(Item item) throws Exception {
		if(hashtable.containsKey(item.getCode())) {
			throw new Exception(item.getCode()+ ": Cannot add item as "
	            	  + "item code already exists.");
		}
		hashtable.put(item.getCode(), item);
		System.out.println(item.toString() + " is added to inventory.");
	}
	
	/*
	 * This method updates Items in the hashtable, specifically the quantity
	 * of the items in the stores.
	 * 
	 */
	
	public void updateItem(Item item, int adjustByQty) {
		hashtable.get(item.getCode()).adjustQty(adjustByQty);;
	}
	/*
	 * This method takes a file and adds all existing Items within that file 
	 * to the hashtable.
	 * 
	 * @throws IOException
	 * When the file that is entered does not exist or cannot be found
	 * @throws ParseException
	 * When you fail to parse a String that is ought to have a special format,
	 * or in this case when the file doesn't contain appropriate formatting/
	 * expected values
	 * @throws Exception
	 * When the method addItem() throws an exception to this
	 */
	public void addItemCatalog(String fileName) throws IOException, ParseException, Exception  {
		File file = new File(fileName);
	    if (! file.exists()) {
	      throw new IOException("Could not find file: " + fileName);
	    }
		FileInputStream fis;
		fis = new FileInputStream(fileName);
		 InputStreamReader isr = new InputStreamReader(fis);
		 JSONParser parser = new JSONParser();
		 JSONArray objs = (JSONArray) parser.parse(isr);
		 for(int i = 0; i < objs.size(); i ++) {
			 JSONObject obj = (JSONObject) objs.get(i);
			 String code = (String) obj.get("itemCode");
			 String name = (String) obj.get("itemName");
			 int avgSales = Integer.parseInt((String) obj.get("avgSales"));
			 int qty = Integer.parseInt((String)obj.get("qtyInStore"));
			 double price = Double.parseDouble((String) obj.get("price"));
			 int onOrder = Integer.parseInt((String) obj.get("amtOnOrder"));
			 int arrDay = calculateArrivalDay(onOrder);
			 addItem(new Item(code, name, qty, avgSales, onOrder, arrDay, price));
		 }
	}
	
	/*
	 * This method processes the sales from a sales file. It also prints 
	 * a statement detailing what was sold and at what quantity.
	 * All variables are fairly self explanatory.
	 * 
	 * 
	 * 
	 * @throws IOException
	 * When the file that is entered does not exist or cannot be found
	 * @throws ParseException
	 * When you fail to parse a String that is ought to have a special format,
	 * or in this case when the file doesn't contain appropriate formatting/
	 * expected values
	 * 
	 */
	public void processSales(String fileName)throws IOException, ParseException {
		File file = new File(fileName);
	    if (! file.exists()) {
	      throw new IOException("Could not find file: " + fileName);
	    }
		FileInputStream fis;
		fis = new FileInputStream(fileName);
		InputStreamReader isr = new InputStreamReader(fis);
		 JSONParser parser = new JSONParser();
		 JSONArray objs = (JSONArray) parser.parse(isr);
		 for(int i = 0; i < objs.size(); i ++) {
			 JSONObject obj = (JSONObject) objs.get(i);
			 String code = (String) obj.get("itemCode");
			 Item thisItem = hashtable.get(code);
			 if(thisItem == null) {
				 System.out.println(code + ": Cannot buy as it is not in the "
				   + "grocery store.");
				 continue;
			 }
			 String name = thisItem.getName();
			 int sold =  Integer.parseInt((String) obj.get("qtySold"));
			 int onOrder = thisItem.getOnOrder();
			 int qty = thisItem.getQty();
			 if(sold > qty) {
				 System.out.println(code + ": Not enough stock for sale. Not "
				   + "updated.");
				 continue;
			 }
			 updateItem(thisItem, -sold);
			 if(!thisItem.minimumAmt() && onOrder == 0) {
				 thisItem.setOnOrder(onOrder = thisItem.restockingAmt());
				 thisItem.setArrDay(calculateArrivalDay(thisItem.restockingAmt()));
				 if(sold != 0) {
					 System.out.println(code + ": " + sold + " units of " + name + 
					   " are sold. Order has been placed for " + onOrder +" more "
					   + "units.");
				 }
			 }
			 else if(sold != 0) {
					 System.out.println(code + ": " + sold + " units of " + name + 
					   " are sold.");
				 }
			 }
			 
		 }
	
	/*
	 * This method iterates to the next business day and checks to see if any
	 * orders have arrived. If yes, a statement is printed. If not, a statement
	 * is printed.
	 * 
	 * @parameter enumeration
	 * used to iterate through the hashtable
	 * I realize now I couldve used keySet() and iterate from there, but I'm 
	 * going to leave it because I think it's cool. :)
	 * 
	 * 
	 */
	public void nextBusinessDay() {
		businessDay++;
		System.out.println("Business day " + businessDay + ".");
		Enumeration<String> enumeration = hashtable.keys();
		boolean ordersArrived = false;
		while(enumeration.hasMoreElements()) {
			String key = enumeration.nextElement();
			if(hashtable.get(key).getArrDay() == businessDay) {
				updateItem(hashtable.get(key), hashtable.get(key).getOnOrder());
				hashtable.get(key).ordersArrived();
				ordersArrived = true;
			}
        }
		if(ordersArrived == false) {
			System.out.println("No orders have arrived.");
		}
	}
	
	
	/*
	 * This method is used to print the hashtable in the I/O format of option C
	 * in the user menu. It iterates through the hashtable using enumeration
	 * and calls the toString() of each item.
	 * 
	 * @parameter enumeration
	 * used to iterate through the hashtable
	 */
	public String toString() {
		Enumeration<String> enumeration = hashtable.keys();
		String tempString = "Item code   Name                "
		  + "Qty   AvgSales   Price    OnOrder    ArrOnBusDay \n";
		tempString += "--------------------------------------------------------"
		  + "------------------------ \n";
		while(enumeration.hasMoreElements()) {
			String key = enumeration.nextElement();
			tempString += hashtable.get(key).properFormat() + "\n";
		}
		return tempString;
	}
	/*
	 * This method is used to calculate the arrival day of orders.
	 * 
	 */
	public int calculateArrivalDay(int onOrder) {
		if(onOrder > 0) {
			return businessDay +3;
		}
		else
			return 0;
	}
	public int getBusinessDay() {
		return businessDay;
	}
}
