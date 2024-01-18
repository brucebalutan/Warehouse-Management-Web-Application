Warehouse Management Web Application

Project is done using Java Spring along with many other libraries
H2-Database is used for persistence so data is not saved from different runtimes
Most functions are done through REST API consumptions especially item and distribution centre functions

Front-end is done via HTML Bootstrap

Pages involved in the project:
  - Login and register page
  - Item addition page with attribute restriction
  - Items page with searchability function based on branding and date added
  - Management page showing various distribution centres and their locations along with item deletion capabilities; this also has the ability to add items from distribution centres to the warehouse prioritizing closest warehouse with available item

Users are given various positions and depending on position, will have different permissions
  - Admins: Have permissions for everything; they're the only one with permissions to the management page
  - Warehouse Employees: Can add item entries to the warehouse
  - Users: Only able to use the availbale items page and searchability function

There is docker functionality but it is up to the user.

This project uses the distribution centre api (another repository) to populate the distribution centres and to provide the rest api available for consumption
