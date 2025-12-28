#exchange_slot_project
============================

Project allowing students to exchange slots in FPT University:

causes: school arrange classes for students and that lead to scheduling conflicts, personal preferences ... etc.

purpose: help students to find others who have the same need to exchange their class easily.

tech stack:

    Backend: Spring Boot, MySQL, Docker, Redis
    Frontend: Vanilla JavaScript, HTML, CSS
    demo link: https://exchange-class.vercel.app/

features:

    security:
        validation(validate client requests)
        authentication(login, signup, logout)
        authorization(role-based access control)
        JWT(access token and refresh token store in HttpOnly cookie(to prevent XSS attack))
        password encryption (with BCrypt)
        rate limiting with redis ( prevent DDOS attack)
        endpoint protection with Spring Security(CORS, access control)

    CRUD operations (create, read, update, delete exchange requests, accounts, etc.)
    pagination for large datasets
    Real-time chat application with WebSocket (implementation in progress)
    caching with Redis to improve performance

general documentations:

    - Backend:
        - Conventions:
            nameing:
                - classes: PascalCase (e.g., AccountService, ExchangeClassRequestDTO)
                - methods and variables: camelCase (e.g., getAccountById, studentCode)
                - constants: UPPER_SNAKE_CASE (e.g., MAX_REQUESTS_PER_MINUTE)
                - packages: lowercase (e.g., com.example.project.service)
        
        - Folder Structure :
            
            - dto(data transfer object): objects used for communicate with client (frontend)
            - entity: ORM to perform operations with Database
            - mapper: Transfer (map) DTO into Entity and vice versa
            - repository(interface): Extends JpaRepository  to interact with Database
            - service: Call Repository and contain specific logic for a specific object
            - validator: Validate if the request of client (frontend) is valid
            - controller: Define url , query method and call Service and Validator
            - exception : Contain custom exception and global exception handler
            - config: Configuration of security(Spring security), cache (redis) and web socket
            - utility : Contain methods for general use
            - constant: contain enums that contain constant values
        
        - general flow:
            - Input : Client → Request → Controller(DTO ↔ Mapper ↔ Entity) → Validator → Service → Repository → Database
            - Output : Database → Response → Repository → Service → Controller(Entity ↔ Mapper ↔ DTO) → Client

details documentations:

    Backend: 
        dto : 
            - CreateXxxDTO : used when client create a new object
            - UpdateXxxDTO : used when client update an existing object   
            - GetXxxDTO : used when client get an object or list of objects from server
            - xxxResponseDTO : used when server respond to client such as LoginResponseDTO contain access token and refresh token
            - ActionDTO : used when client perform an action such as RefreshAccesstokenDTO
            - ResponeDTO : general response contain message ,error and status code
        entity :
            - each class represent a table in database such as Account and Chat
            - each class express exactly the structure of the table in database 
            and its relationships with other tables such as one-to-many, many-to-one
        mapper :  
            - each class contain methods to map between DTO and Entity such as AccountMapper
            - use MapStruct framework to generate mapping code automatically
        repository :
            - each interface extend JpaRepository to interact with database such as AccountRepository
            - contain methods to perform CRUD operations and custom queries such as findByUsername, findByStudentCode
        service :
            - each class contain business logic and further validation for a specific object such as AccountService
            - its the interface of repository layer and call repository to perform operations with database
            - call Repository to perform operations with database and return result to Controller
        validator :
            - it's the second validation layer after validation using starter validation annotations 
            in DTO classes for more complex validation rules such as check if username already exists in database
            - each class contain methods to validate some kind of client requests such as add,  update
            for a specific object such as AccountValidator
            - throw custom exception with corresponding status and message when validation fails
        controller :
            - each class define url endpoints and query methods for a specific object or purpose 
            such as AccountController or AuthController
            - call Service and Validator to process client requests and return response to client
        exception :
            - contain custom exception (BaseException)
            - contain global exception handler that help handle exceptions thrown in application 
            and return appropriate responseBadRequest when validation fails
        config :
            - contain configuration classes for security(Spring Security), cache(Redis), web socket(WebSocket) and rate limiting(Redis)
        utility :   
            - contain general methods for use in different parts of application 
            such as JwtUtil for generate and validate JWT tokens
        constant :
            - contain enums that define constant values used in application such as Role (USER, ADMIN)
            or number of items per page for pagination

        Security
            - urls
                - /auth/** : permit all (public)
                - Post , Patch /class : only ADMIN role
                - others : USER and ADMIN role
            - JWT:
                - generate access token and refresh token during login
                - access token expire in short time (15 minutes)
                - refresh token expire in long time (7 days)
                - store refresh token in HttpOnly cookie to prevent XSS attack
                - provide endpoint to refresh access token using refresh token
            - authentication format: 
                headers: "Authorization" : "Bearer" + access_token
                cookies:  "refresh_token": refresh_token (HttpOnly)
            - rate limiting:
                - limit number of requests per minute per IP address
                - use redis to store request count and timestamp
                - return 429 Too Many Requests when limit exceeded
            - password encryption:
                - use BCrypt to hash password before store in database
                - verify password during login by comparing hashed password
            - endpoint protection:
                - use Spring Security to protect endpoints based on roles
                - configure CORS to allow requests from frontend domain

        CRUD operations
            - implement CRUD operations for accounts, exchange requests, classes, etc.
            - use pagination for endpoints that return large datasets
            - validate client requests using Validator layer before processing

        Real-time chat application: 
            - use WebSocket to implement real-time chat between users
            - /ws endpoint for WebSocket connection
        
        Caching with Redis:
            - Time to live : 10  minutes  
            - cache type: cache-aside strategy (lazy loading --> when data requested, check cache first, if not found, load from database and store in cache)
            - cache frequently accessed data such as class list to improve performance

        Test:
            - unit test for service layer using JUnit and Mockito 
            (each method have a corresponding unit test to test its logic and behavior)
            - integration test for controller layer using Spring Boot Test and Test Container (MySQL container to mock database)
            (each endpoint have 2 test cases: success and failure(fail validation, not found, etc.))

         - Database(7 table): 
        accounts:
            id INT (pk),
            account_name VARCHAR(255) (unique),
            username VARCHAR(255) (unique),
            passwords VARCHAR(255) (encrypted),
            phone_number VARCHAR(25) (unique),
            student_code VARCHAR(255) (unique),
            class_code VARCHAR(15) (fk class(class_code))

        chat:
            id INT (pk),
            user_id_1 INT (fk accounts(id)),
            user_id_2 INT (fk accounts(id))

        message:
            id INT (pk),
            chat_id INT (fk chat(id)),
            sender_id INT (fk accounts(id)),
            message_content VARCHAR(1000)
            
        class:
            id INT (pk),
            class_code VARCHAR(15) (unique),
            slot VARCHAR(3) ('1,2' or '3,4')
            
        exchange_class_request:
            id INT (pk),
            student_code VARCHAR(255) (fk accounts(student_code)),
            current_slot VARCHAR(3) ('1,2' or '3,4'),
            current_class VARCHAR(15) (fk class(class_code)),
            desired_class VARCHAR(15) (fk class(class_code)),
            desired_slot VARCHAR(3) ('1,2' or '3,4')
            
        exchange_slot_request:
            id INT (pk),
            student_code VARCHAR(255) (fk accounts(student_code)),
            current_slot VARCHAR(3) ('1,2' or '3,4'),
            class_code VARCHAR(15) (fk class(class_code)),
        
        
 
