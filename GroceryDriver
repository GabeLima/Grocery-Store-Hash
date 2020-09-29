// Gabriello Lima, 112803276, R01
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import org.json.simple.parser.ParseException;

public class GroceryDriver{
	/*
	 * It is important to note that each exception is handled with an according
	 * print statement detailing the error which occured, hence all exceptions
	 * merely print said message.
	 * 
	 * 
	 * 
	 * 
	 */
	public static void main(String[] args) {
		HashedGrocery HGObj = new HashedGrocery();
		Scanner stdin = new Scanner(System.in);
		boolean quitFlag = false;
		System.out.println("Business day " + HGObj.getBusinessDay() + ".");
		while(!quitFlag) {
			System.out.println("(L) Load item catalog    \r\n" + 
					"(A) Add items              \r\n" + 
					"(B) Process Sales      \r\n" + 
					"(C) Display all items\r\n" + 
					"(N) Move to next business day  \r\n" + 
					"(Q) Quit  ");
			System.out.print("Enter option: ");
			try {
				String selection = stdin.next().toLowerCase();
				switch(selection) {
					case("l"):
						System.out.print("Enter file to load: ");
						String filename = stdin.next();
						HGObj.addItemCatalog(filename);
						break;
					case("a"):
						System.out.print("Enter item code: ");
						String code = stdin.next();
						System.out.print("Enter item name: ");
						String name = stdin.next();
						System.out.print("Enter Quantity in store: ");
						int qty = stdin.nextInt();
						System.out.print("Enter Average sales per day: ");
						int avg = stdin.nextInt();
						System.out.print("Enter price: ");
						Double price = stdin.nextDouble();
						HGObj.addItem(new Item(code, name, qty, avg, 0, 0, price));
						break;
					case("b"):
						System.out.print("Enter filename: ");
						filename = stdin.next();
						HGObj.processSales(filename);
						break;
					case("c"):
						System.out.println(HGObj.toString());
						break;
					case("n"):
						System.out.println("Advancing business day...");
						HGObj.nextBusinessDay();
						break;
					case("q"):
						System.out.println("Terminating program normally...");
						quitFlag = true;
						break;
					}
				}catch(IOException e) {
					System.out.println(e.getMessage());
				} catch (ParseException e) {
					System.out.println(e.getMessage());
				}catch(NumberFormatException e){
			        System.out.println("Please provide correct input");
					stdin.next();
				}catch (InputMismatchException e) {
					System.out.println("Please provide correct input");
					stdin.next();
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
		}
		stdin.close();
	}
}
