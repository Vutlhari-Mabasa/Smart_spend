
# 💸 Expense Tracker App (Spending)

An Android application that allows users to manage their personal expenses effectively. Users can register/login, add expense categories, log expenses,
set monthly spending goals, and view summaries of expenses grouped by category within selected dates.


## 📱 Features

- 🔐 **User Authentication**
  - Register and log in securely.
  
- 🗂️ **Category Management**
  - Add and manage spending categories like Food, Transport, Entertainment, etc.

- 💰 **Expense Tracking**
  - Record expenses with details such as date, time, description, amount, and category.
  - Option to attach a photo of the receipt.

- 🎯 **Monthly Goals**
  - Set a budget goal for each month to stay within your limits.

- 📊 **View Reports**
  - Filter expenses between two dates.
  - View total amount spent per category in a RecyclerView.


com.example.spending/
│
├── data/
│   ├── AppDatabase.kt          # Room database config
│   ├── User.kt                 # User entity
│   ├── Expense.kt              # Expense entity
│   ├── Category.kt             # Category entity
│   ├── CategoryTotal.kt        # Data class for reports
│   ├── DAOs (UserDao, ExpenseDao, CategoryDao)
│   └── CategoryTotalAdapter.kt # RecyclerView adapter
│
├── activities/
│   ├── DashboardActivity.kt
│   ├── AddCategoryActivity.kt
│   ├── CreateExpenseActivity.kt
│   ├── MonthlyGoalActivity.kt
│   └── ViewTotalByCategoryActivity.kt
│
├── viewmodels/
│   └── UserViewModel.kt
│
├── layouts/
│   └── activity\_\*.xml          # Layout files for activities
└── drawables/
└── logo.png



2. Open in Android Studio.
3. Let Gradle sync and install dependencies.
4. Run the app on an emulator or physical device.



## 🧪 How to Use

1. **Register/Login**

   * Create a new account or log in using existing credentials.

2. **Dashboard**

   * Navigate using buttons:

     * Add Category
     * Create Expense
     * View Expenses
     * Set Monthly Goals
     * View Total by Category

3. **Add Category**

   * Create your custom expense categories.

4. **Create Expense**

   * Add expenses with optional photo and assign them to a category.

5. **Set Goals**

   * Define your budget for the current month.

6. **View Total by Category**

   * Select a date range and get a summary of your expenses per category.


## 🧑‍💻 Developer Notes

* Room is used to persist data locally.
* MVVM architecture is followed for clean separation of concerns.
* All database operations are done on background threads using Kotlin coroutines.
* The app uses a dark, modern UI to enhance usability and reduce eye strain.

LINK TO YOUTUBE VIDEO: https://youtube.com/shorts/MRH53fozgf4?si=rKcEVX1mbSaeZQDy
GITHUB REPOSITORY: https://github.com/Vutlhari-Mabasa/Smart_spend.git
