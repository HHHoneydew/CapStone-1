# Accounting Ledger App

This is a Java-based Command Line Interface (CLI) application designed to track financial transactions for personal or business use.  
Users can record deposits and payments, view their transaction ledger, and generate various financial reports.  
All data is stored in a CSV file named `transactions.csv`.




## 🖼️ Screenshots
**Home Screen**
![HomePage](https://github.com/user-attachments/assets/98f7d1b8-3945-4a50-b003-1fbf1cf7b4ab)

**Ledger Screen**
![LedgerScreen](https://github.com/user-attachments/assets/56f71681-0159-49fa-b60a-d24052565f35)

**Report Screen**
![ReportScreen](https://github.com/user-attachments/assets/b9777157-357e-4004-9cfa-4d13173a20d2)

**All Transaction**
![image](https://github.com/user-attachments/assets/5849166a-4dd5-4756-890e-04e4a0fa2740)

**All Deposits & All Payments**
![image](https://github.com/user-attachments/assets/b15745e6-ac7b-4b14-9a59-a360142ad342)


---

---

## 🛠️ Features

- **Add Deposits**: Input and save deposit transactions
- **Make Payments (Debits)**: Input and save outgoing payments
- **View Ledger**:
    - All Transactions
    - Deposits Only
    - Payments Only
- **Generate Reports**:
    - Month to Date
    - Previous Month
    - Year to Date
    - Previous Year
    - Search by Vendor
- **Persistent Storage**:
    - All transactions saved to `transactions.csv`
---

### Interesting Piece Of Code
![image](https://github.com/user-attachments/assets/1d128220-bab6-49be-90fd-facb920f7032)
Explantion:
I used this to help me display the different screen options instead of manually writing a System.OutPrint for each option. Imagine there are 100 options and manually writing System.OutPrint for each is insane!

