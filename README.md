# Milkman-Assistant
This application will basically act as an Assistant for Milkman which holds the whole record of the quantity of milk he delivered, the price which he has fixed and all the variations if any. Moreover this application will also act useful for the Customers or Users as they can view their whole Monthly Record in just one click. 

## How to Use

- Import mySQL in your project for database.
- create 4 Tables usertbl, customerentry, routine, bills.
- usertbl: contains 2 fields Username, Password.
- customerentry: Name, Pic, Mobile, Address, CQty, CPrice, BQty, BPrice, DOS, Status.
- routine Name: Cvar, BVar, VarDate.
- bills: Name, Start Date, End Date, CQty, BQty, Amnt, Status, PaymentMode.
- Run main.java provided in login package using username and password from usertbl.
- To use SMS feature of this application use your id and password in SMS package in class SST_SMS.java file.
