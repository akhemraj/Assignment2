import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

public class BookDatabaseKeeper {

	public static void main(String[] args) throws ClassNotFoundException, SQLException, ParseException {

		String myDriver = "com.mysql.jdbc.Driver";
		String myUrl = "jdbc:mysql://localhost/library";

		Class.forName(myDriver);
		Connection conn = DriverManager.getConnection(myUrl, "root", "1234");

		System.out.println(
				"Please select the option: 1.Enter Book  2.Get all Book data  3.Edit Book data  4.Delete Book Data   ");
		Scanner in = new Scanner(System.in);
		int choice = Integer.parseInt(in.nextLine());

		switch (choice) {

		case 1:
			System.out.println("Enter the book details:");
			//Taking inputs
			System.out.print("Enter name:");
			String name = in.nextLine();

			System.out.println("\nEnter Author:");
			String author = in.nextLine();

			System.out.println("Enter Catageory:");
			String catagory = in.nextLine();

			System.out.println("Enter Serial Number:");
			int serialNum = Integer.parseInt(in.nextLine());

			System.out.println("Enter id:");
			int id = Integer.parseInt(in.nextLine());

			Calendar calendar = Calendar.getInstance();
			java.sql.Date issueDate = new java.sql.Date(calendar.getTime().getTime());

			SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			System.out.println("Enter due Date(MM/dd/yyyy):");
			String dueDate = in.nextLine();
			df.parse(dueDate);

			System.out.println("\nEnter borrower name:");
			String borrower = in.nextLine();

			//mysql insert query to enter records
			String query = " insert into books (id, name, author, catagory, serial, dueDate, borrower)"
					+ " values (?, ?, ?, ?, ?,?,?)";
			;

			// create the mysql insert preparedstatement
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setInt(1, id);
			preparedStmt.setString(2, name);
			preparedStmt.setString(3, author);
			preparedStmt.setString(4, catagory);
			preparedStmt.setInt(5, serialNum);
			preparedStmt.setString(6, dueDate);
			preparedStmt.setString(7, borrower);

			// execute the preparedstatement
			preparedStmt.execute();

			conn.close();

			System.out.println("\nBook data enterd successfully.....\n\n");

			break;

		case 2:
			System.out.println(".......ALL BOOKS INFO......\n");
			System.out.println("ID \t Name \t Author \t Category \t SerialNum \t IssueDate \t DueDate \t Borrower \n");

			String query2 = "SELECT * from books";
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query2);

			while (rs.next()) {
				int _id = rs.getInt("id");
				String _name = rs.getString("name");
				String _author = rs.getString("author");
				String _catagory = rs.getString("catagory");
				int _serial = rs.getInt("serial");
				Date _dateCreated = rs.getDate("date_created");
				String _dueDate = rs.getString("dueDate");
				String _borrower = rs.getString("borrower");

				// print the results
				System.out.format("%s \t %s \t %s \t\t %s \t\t %s \t\t %s \t %s \t %s\n", _id, _name, _author,
						_catagory, _serial, _dateCreated, _dueDate, _borrower);
			}
			st.close();

			break;

		case 3:
			System.out.println("\n.....Update book record......");
			System.out.println("\nEnter id of a book to edit its due date: ");
			int _id = Integer.parseInt(in.nextLine());

			System.out.println("\nEnter new due date(MM/DD/YYYY):");
			String newDueDate = in.nextLine();

			String query3 = "UPDATE books SET duedate = ? where id = ?  ";

			PreparedStatement prepdstmt = conn.prepareStatement(query3);
			prepdstmt.setString(1, newDueDate);
			prepdstmt.setInt(2, _id);

			prepdstmt.executeUpdate();

			conn.close();

			System.out.println("\n...Due date updated successfully");

			break;
		case 4:
			System.out.println("\n....Delete book record....");
			System.out.println("\n Enter id of book to be deleted:");
			int delId = Integer.parseInt(in.nextLine());

			String query4 = "delete from books where id = ?";
			PreparedStatement preparedStmt2 = conn.prepareStatement(query4);
			preparedStmt2.setInt(1, delId);

			// execute the preparedstatement
			preparedStmt2.execute();

			conn.close();

			System.out.println("\n...Record Deleted Successfully");

			break;

		default:
			System.out.println("You have enterd wrong choice.");
		}

	}

}
