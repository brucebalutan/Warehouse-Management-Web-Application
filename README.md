# Warehouse Management Web Application

Project is done using Java Spring along with many other libraries.
H2-Database is used for persistence so data is not saved from different runtimes.
Most functions are done through REST API consumptions especially item and distribution centre functions.
There is docker functionality but it is up to the user.

Front-end is done via HTML Bootstrap

# Users are given various positions and depending on position, will have different permissions
  - Admins: Have permissions for everything; they're the only one with permissions to the management page
  - Warehouse Employees: Can add item entries to the warehouse
  - Users: Only able to use the availbale items page and searchability function

# Registration Page
![image](https://github.com/brucebalutan/Warehouse-Management-Web-Application/assets/19336369/95b6d32e-f3da-45a7-9d74-218fe2c45fdb)
  - Form that allows the user to register their username, password, and role
    - If role is not one of the above listed, it will default to user (this demo will be using admin)
    - Password is encrypted in the database

# Login Page
![image](https://github.com/brucebalutan/Warehouse-Management-Web-Application/assets/19336369/bbf25023-b3c5-46c1-b93c-02028289b515)
  - Login page form

# Add Item Page (Available for warehouse and Admins only)
![image](https://github.com/brucebalutan/Warehouse-Management-Web-Application/assets/19336369/6e352b88-32f2-4456-b9a3-6e12e72c19c8)
  - Item addition page with attribute restriction and error handling
    - Year of Creation must be at least 2022
    - Price must be at greater 1000

# Item List (Preloaded with items) (Available for all roles)
![image](https://github.com/brucebalutan/Warehouse-Management-Web-Application/assets/19336369/2347c477-2de8-446c-bad3-505cdff4ce51)
  - Items page with searchability function based on branding and date added
![image](https://github.com/brucebalutan/Warehouse-Management-Web-Application/assets/19336369/a1e48980-e707-4a4d-a62e-ba768ec6ca0f)
  - Sort functionality as well to sort based on branding
![image](https://github.com/brucebalutan/Warehouse-Management-Web-Application/assets/19336369/be8f2712-e3fa-4df6-a0a5-011bb8ebe4f1)

# Management Page (Available for admin only)
![image](https://github.com/brucebalutan/Warehouse-Management-Web-Application/assets/19336369/e4a85eeb-7be7-4c18-887d-92cbb9de1602)
  - Management page showing various distribution centres and their locations along with item deletion capabilities
![image](https://github.com/brucebalutan/Warehouse-Management-Web-Application/assets/19336369/ace93401-a583-4a4d-847b-371ee05ca3f6)
  - Shows various distribution centres (populated via a REST API consumption from Distribution Centre API)
![image](https://github.com/brucebalutan/Warehouse-Management-Web-Application/assets/19336369/1c6c6752-202a-481c-a697-e901c030ed4a)
  - This also has the ability to add items from distribution centres to the warehouse prioritizing closest warehouse with available item
![image](https://github.com/brucebalutan/Warehouse-Management-Web-Application/assets/19336369/196b2f38-6f60-4f31-b8d0-612ee0364b22)

# H2-Database Page
![image](https://github.com/brucebalutan/Warehouse-Management-Web-Application/assets/19336369/40119217-8b4d-41c4-a6cd-487629188868)
  - H2-database with Items and Users Shown
![image](https://github.com/brucebalutan/Warehouse-Management-Web-Application/assets/19336369/fab55279-a056-42a1-a101-3bb5dbad2b13)
  - User entry with password encryption (tokens were not implemented)

# Restrictions
This project uses the distribution centre api (another repository) to populate the distribution centres and to provide the rest api available for consumption
