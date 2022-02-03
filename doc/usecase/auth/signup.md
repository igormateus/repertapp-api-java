# /auth/signup

> ## Data
* Required:
    - username
    - password
* Optional:
    - name
    - email
    - bio

> ## Main Flow
1. Get data for new user;
2. Validate data;
3. Verify unique values (username, name, email);
4. Create user on data base;
5. Return new user without password;

> ## Alternative Flow: Invalid data
3. Throw error

> ## Alternative Flow: Not unique data
4. Throw error