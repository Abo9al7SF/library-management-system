import java.util.ArrayList;
import java.util.Scanner;

// Base class for all users (Admin and Library Members)
class user {
	private String userid;
	private String username;

	// Constructor to initialize user
	public user(String userid, String username) {
		this.userid = userid;
		this.username = username;
	}

	// Getter for user ID
	public String getUserid() {
		return userid;
	}

	// Getter for username
	public String getusername() {
		return username;
	}

	// Method for user login verification
	public boolean Login(String inputUserid, String inputUsername) {
		return this.userid.equals(inputUserid) && this.username.equals(inputUsername);
	}
}

// Admin class that inherits from user
class admin extends user {
	private String adminid;

	// Constructor to initialize admin
	public admin(String userid, String username, String adminid) {
		super(userid, username);
		this.adminid = adminid;
	}

	// Add a new book to the library
	public void addbook(ArrayList<Book> books, String bookid, String bookname, String ISBN) {
		books.add(new Book(bookid, bookname, ISBN));
		System.out.println("Book successfully added: " + bookname + " ISBN: " + ISBN);
	}

	// Remove a book from the library
	public void removeBook(ArrayList<Book> books, String bookid) {
		boolean found = false;
		for (Book book : books) {
			if (book.getBookid().equals(bookid)) {
				books.remove(book);
				System.out.println("Book deleted: " + book.getBookname());
				found = true;
				break;
			}
		}
		if (!found) {
			System.out.println("Book not found.");
		}
	}

	// Update details of an existing book
	public void updatebook(ArrayList<Book> books, String bookid, String newbookname, String newisbn) {
		boolean found = false;
		for (Book book : books) {
			if (book.getBookid().equals(bookid)) {
				book.setBookname(newbookname);
				book.setisbn(newisbn);
				System.out.println("Updated book: " + newbookname + " ISBN: " + newisbn);
				found = true;
				break;
			}
		}
		if (!found) {
			System.out.println("Book not found.");
		}
	}
}

// Library member class that inherits from user
class librarymember extends user {
	private String memberid;

	// Constructor to initialize library member
	public librarymember(String userid, String username, String memberid) {
		super(userid, username);
		this.memberid = memberid;
	}

	// Search for a book in the library
	public String searchbook(ArrayList<Book> books, String bookid) {
		for (Book book : books) {
			if (book.getBookid().equals(bookid)) {
				return "Found: " + book.getBookname() + " ISBN: " + book.getisbn();
			}
		}
		return "Book not found.";
	}

	// Borrow a book from the library
	public void borrowbook(ArrayList<Book> books, String bookid) {
		boolean found = false;
		for (Book book : books) {
			if (book.getBookid().equals(bookid)) {
				System.out.println("You borrowed: " + book.getBookname() + " ISBN: " + book.getisbn());
				found = true;
				break;
			}
		}
		if (!found) {
			System.out.println("Book is not available.");
		}
	}
}

// Book class to represent books in the library
class Book {
	private String bookid;
	private String bookname;
	private String isbn;

	// Constructor to initialize a book
	public Book(String bookid, String bookname, String isbn) {
		this.bookid = bookid;
		this.bookname = bookname;
		this.isbn = isbn;
	}

	// Getter for book ID
	public String getBookid() {
		return bookid;
	}

	// Getter for book name
	public String getBookname() {
		return bookname;
	}

	// Getter for ISBN
	public String getisbn() {
		return isbn;
	}

	// Setter for updating book name
	public void setBookname(String bookname) {
		this.bookname = bookname;
	}

	// Setter for updating ISBN
	public void setisbn(String isbn) {
		this.isbn = isbn;
	}
}

// Main class for the library system
public class librarysystem {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		ArrayList<Book> books = new ArrayList<>();
		ArrayList<admin> admins = new ArrayList<>();
		ArrayList<librarymember> members = new ArrayList<>();

		// Initialize a default admin
		admins.add(new admin("admin1", "AdminUser", "A001"));

		// Initialize some default books
		books.add(new Book("B001", "The Great Gatsby", "9780743273565"));
		books.add(new Book("B002", "To Kill a Mockingbird", "9780061120084"));
		books.add(new Book("B003", "1984", "9780451524935"));
		books.add(new Book("B004", "Pride and Prejudice", "9780141439518"));

		System.out.println("Welcome to the library system!");

		while (true) {
			// Main menu
			System.out.println("\n1. Signup");
			System.out.println("2. Login");
			System.out.println("3. Exit");
			System.out.print("Enter your choice: ");
			int choice = scanner.nextInt();
			scanner.nextLine();

			switch (choice) {
			case 1:
				// Register a new library member
				System.out.println("\nSign up for new members:");
				System.out.print("Enter User ID: ");
				String newuserid = scanner.nextLine();
				System.out.print("Enter Username: ");
				String newusername = scanner.nextLine();
				System.out.print("Enter Member ID: ");
				String memberid = scanner.nextLine();
				members.add(new librarymember(newuserid, newusername, memberid));
				System.out.println("Account created successfully.");
				break;

			case 2:
				// Login to the system
				System.out.println("\nLog into your account:");
				System.out.print("Enter User ID: ");
				String userid = scanner.nextLine();
				System.out.print("Enter Username: ");
				String username = scanner.nextLine();

				boolean loggedin = false;

				// Admin login
				for (admin Admin : admins) {
					if (Admin.Login(userid, username)) {
						System.out.println("Logged in as Admin.");
						loggedin = true;
						int adminchoice;
						do {
							System.out.println("\nAdmin Menu:");
							System.out.println("1. Add a new book");
							System.out.println("2. Delete a book");
							System.out.println("3. Update a book");
							System.out.println("4. Logout");
							System.out.print("Enter choice: ");
							adminchoice = scanner.nextInt();
							scanner.nextLine();

							switch (adminchoice) {
							case 1:
								System.out.print("Enter Book ID: ");
								String bookid = scanner.nextLine();
								System.out.print("Enter Book Name: ");
								String bookname = scanner.nextLine();
								System.out.print("Enter ISBN: ");
								String isbn = scanner.nextLine();
								Admin.addbook(books, bookid, bookname, isbn);
								break;

							case 2:
								System.out.print("Enter Book ID to delete: ");
								bookid = scanner.nextLine();
								Admin.removeBook(books, bookid);
								break;

							case 3:
								System.out.print("Enter Book ID to update: ");
								bookid = scanner.nextLine();
								System.out.print("Enter New Book Name: ");
								bookname = scanner.nextLine();
								System.out.print("Enter New ISBN: ");
								isbn = scanner.nextLine();
								Admin.updatebook(books, bookid, bookname, isbn);
								break;

							case 4:
								System.out.println("Logged out from Admin account.");
								break;

							default:
								System.out.println("Invalid choice.");
							}
						} while (adminchoice != 4);
						break;
					}
				}

				// Member login
				if (!loggedin) {
					for (librarymember Member : members) {
						if (Member.Login(userid, username)) {
							System.out.println("Logged in as Library Member.");
							loggedin = true;
							int memberchoice;
							do {
								System.out.println("\nMember Menu:");
								System.out.println("1. Search for a book");
								System.out.println("2. Borrow a book");
								System.out.println("3. Logout");
								System.out.print("Enter choice: ");
								memberchoice = scanner.nextInt();
								scanner.nextLine();

								switch (memberchoice) {
								case 1:
									System.out.print("Enter Book ID to search: ");
									String bookid = scanner.nextLine();
									System.out.println(Member.searchbook(books, bookid));
									break;

								case 2:
									System.out.print("Enter Book ID to borrow: ");
									bookid = scanner.nextLine();
									Member.borrowbook(books, bookid);
									break;

								case 3:
									System.out.println("Logged out from Member account.");
									break;

								default:
									System.out.println("Invalid choice.");
								}
							} while (memberchoice != 3);
							break;
						}
					}
				}

				if (!loggedin) {
					System.out.println("Invalid login credentials.");
				}
				break;

			case 3:
				// Exit the program
				System.out.println("Thanks for using the library system. Goodbye!");
				scanner.close();
				return;

			default:
				System.out.println("Invalid input. Try again.");
			}
		}
	}
}