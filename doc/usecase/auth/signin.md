# /auth/signin

> ## Data
* Required:
    - username
    - password

> ## Main Flow
1. Get user auth data;
2. Validate data;
3. Authenticate User on repository;
4. Create JWT Token;
5. Return Token in Header;

> ## Alternative Flow: Invalid data
3. Throw error

> ## Alternative Flow: Invalide username or password
4. Throw error