// Gabriello Lima, 112803276, R01
public class Item{
	private String itemCode;
	private String name;
	private int qtyInStore;
	private int averageSalesPerDay;
	private int onOrder;
	private int arrivalDay;
	private double price;
	public Item() {
		
	}
	public Item(String newCode, String newName, int qty, int avgSales, int ordering, int arrDay, double newPrice) {
		itemCode = newCode;
		name = newName;
		qtyInStore = qty;
		averageSalesPerDay = avgSales;
		onOrder = ordering;
		arrivalDay = arrDay;
		price = newPrice;
	}
	public String toString() {
		String tempString = itemCode;
		tempString += ": " + name;
		return tempString;
	}
	/*
	 * This method returns this items sample I/O format. HashedGrocery's 
	 * toString() method calls this repeatedly on various items to mimic
	 * it entirely.
	 * 
	 * 
	 */
	public String properFormat() {
		String tempString = itemCode;
		tempString += space(itemCode, 12) + name;
		tempString += space(name, 20) + qtyInStore;
		tempString +=  space(qtyInStore, 8)+ averageSalesPerDay;
		tempString += space(averageSalesPerDay, 10) + price;
		tempString += space(price, 11) + onOrder;
		tempString += space(onOrder, 12) + arrivalDay;
		return tempString;
	}
	/*
	 * These three methods, space(), are used in the spacing of the sample
	 * I/O print format. Used in tangent with the properFormat() method.
	 * 
	 * 
	 */
	public String space(String s, int x) {
		String tempString = "";
		for(int i = 0; i < x - ((String) s).length(); i ++) {
			tempString += " ";
		}
		return tempString;
	}
	public String space(int s, int x) {
		int length = String.valueOf(s).length();
		String tempString = "";
		for(int i = 0; i < x - length; i ++) {
			tempString += " ";
		}
		return tempString;
	}
	public String space(Double s, int x) {
		int length = String.valueOf(s).length();
		String tempString = "";
		for(int i = 0; i < x - length; i ++) {
			tempString += " ";
		}
		return tempString;
	}
	
	
	
	public String getCode() {
		return itemCode;
	}
	public void adjustQty(int qty) {
		qtyInStore += qty;
	}
	public int getArrDay() {
		return arrivalDay;
	}
	public void setArrDay(int newArr) {
		arrivalDay = newArr;
	}
	public int getOnOrder() {
		return onOrder;
	}
	public void setOnOrder(int newOnOrder) {
		onOrder = newOnOrder;
	}
	public void ordersArrived() {
		arrivalDay = 0;
		onOrder = 0;
	}
	public String getName() {
		return name;
	}
	public int getQty() {
		return qtyInStore;
	}
	/*
	 * This method is used in determining the amount to restock when supplies
	 * of this item run low.
	 * 
	 * 
	 */
	public int restockingAmt() {
		return 2 * averageSalesPerDay;
	}
	/*
	 * This method calculates the minimum amount of supplies needed for this item
	 * over a certain period of days.
	 */
	public boolean minimumAmt() {
		return qtyInStore >= (3 * averageSalesPerDay);
	}
}
