
# 📚 Library Management System (Java Console App)

A simple library management system built with Java. This console-based application allows administrators to manage books and users to search for and borrow books.

---

## 🛠 Features

### 👤 Authentication
- User registration with role selection (**ADMIN** or **USER**)
- Login with secure password hashing (MD5)

### 👨‍🏫 Admin Features
- View all available books
- Search for a book by ID or title
- Add new books
- Update book details (title, author, or both)
- Remove books (soft delete)

### 🙋‍♂️ User Features
- View all available books
- Search for a book
- Borrow a book
- Return a book

---

## 💾 Data Storage

All data is stored in plain text files:
- `books.txt` — contains book information
- `users.txt` — contains registered users

---

## 📂 Project Structure

LibraryManagementSystem/
├── model/ # Data models (Book, User, Role)
|
├── service/ # Business logic (LibraryService, UserService)
|
├── repository/ # File-based storage logic
|
├── ui/ # Console-based UI (MainMenu, AdminMenu, UserMenu)
|
├── util/ # Utility classes (e.g., HashUtil)
|
└── books.txt, users.txt

---

## 🚀 How to Run

1. Make sure you have **Java 17 or later** installed.
2. Clone the repository:
   ```bash
   git clone https://github.com/OzodN/LibraryManagementSystem.git
   cd LibraryManagementSystem
3. Open the project in IntelliJ IDEA (or your favorite IDE).
4. Run the MainMenu class located in the ui package.

👨‍💻 Author

OzodN — aspiring Java backend developer
[GitHub Profile](https://github.com/OzodN/)

📄 License

This project is licensed under the MIT License.
