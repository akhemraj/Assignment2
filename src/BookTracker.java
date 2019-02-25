import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class BookTracker extends BookDatabaseKeeper {

	public static void main(String[] args) throws ClassNotFoundException, SQLException, ParseException {

		String myDriver = "com.mysql.jdbc.Driver";
		String myUrl = "jdbc:mysql://localhost/library";

		Class.forName(myDriver);
		Connection conn = DriverManager.getConnection(myUrl, "root", "1234");

		System.out.println(".......TRACKING BOOK RECORDS........");

		System.out.println(
				"Please select the option: 1.BorrowTime  2.Who Borroewd Book  3.Due Date of Book  4.Book Availaibility");
		Scanner in = new Scanner(System.in);
		int choice = Integer.parseInt(in.nextLine());

		switch (choice) {

		case 1:
			System.out.println("\nEnter id of a book:");
			String _id = in.nextLine();
			String query = "SELECT date_created from books where id ='" + _id + "'";

			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			rs.next();
			String issueDate = rs.getString("date_created");

			System.out.println("Book was borrowed on: Date and Time: " + issueDate);

			st.close();

			break;

		case 2:
			System.out.println("\nEnter id of a book:");
			String _id2 = in.nextLine();
			String query2 = "SELECT borrower from books where id ='" + _id2 + "'";

			Statement st2 = conn.createStatement();
			ResultSet rs2 = st2.executeQuery(query2);
			rs2.next();
			String _borrower = rs2.getString("borrower");

			System.out.println("Book was borrowed by:" + _borrower);

			st2.close();

		case 3:
			System.out.println("\nEnter id of a book:");
			String _id3 = in.nextLine();
			String query3 = "SELECT dueDate from books where id ='" + _id3 + "'";

			Statement st3 = conn.createStatement();
			ResultSet rs3 = st3.executeQuery(query3);
			rs3.next();
			String _dueDate = rs3.getString("dueDate");

			System.out.println("Book should be returned by:" + _dueDate);

			st3.close();

		case 4:
			System.out.println("\n ....BOOK AVAILAIBILTY.....");
			System.out.println("Enter name of a book:");
			String bookName = in.nextLine();

			String query4 = "SELECT name FROM books";

			Statement st4 = conn.createStatement();
			ResultSet rs4 = st4.executeQuery(query4);

			List<String> searchNames = new ArrayList<>();

			while (rs4.next()) {
				String addName = rs4.getString("name");
				searchNames.add(addName);
			}

			int flag = 0;
			for (String addName : searchNames) {
				if (addName.equals(bookName)) {
					flag = 1;
					break;
				}

			}

			if (flag == 1) {
				System.out.println("BOOK FOUND");
				String query5 = "SELECT dueDate from books where name='" + bookName + "'";
				Statement st5 = conn.createStatement();
				ResultSet rs5 = st5.executeQuery(query5);

				rs5.next();
				String dueDate = rs5.getString("dueDate");

				// convert string into date
				Date _duedate = new SimpleDateFormat("MM/dd/yyyy").parse(dueDate);
				System.out.println("\nDue date of book submission is:" + _duedate);

				// get todays date
				DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
				Date today = new Date();
				// System.out.println(dateFormat.format(today));

				// compare current date with due date
				if (_duedate.before(today)) {
					System.out.println("Available right now");
				} else {
					System.out.println("Not available right now");
				}

			}

			else
				System.out.println("BOOK NOT FOUND");

			st4.close();

			break;

		default:
			System.out.println("WRONG CHOICE");
		}

	}

}
