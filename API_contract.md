#Accounts
    
Account object
    
    {
        id : int
        userName: String
        password: String
        phoneNumber: String
        accountName: String
        studentCode: String
    }

POST /account/register

URL Params: None

Data Params:

    {
        userName: String,
        password: String,
        phoneNumber: String,
        accountName: String,
        studentCode: String,
    }

Headers:
    Content-Type: application/json

success response :

    Http code : 201 
    message: register successfully

error response:

    Http code : 409
    error: CONFLICT
    message: existed account or phone number or account name

POST /account/login

URL Params: None

URL Params:

    {
        userName: String,
        password: String,
    }

Headers:Content-Type: application/json

success response: 

    Http code : 200
        if correct password and user's name 
        content:
            {
                refresh token: string,
                access token: String
                message: "login sucess"
            }
        if not 
        content:
            {
                refresh token: "no refresh token" ,
                access token: "no access token",
                message: "login failed  please check user name or password"
            }

error response : incomplete

PATCH /account/reset_password

URL Params: None

Data Params:

    {
        userName: String,
        password: String,
    }

Headers:
Content-Type: application/json

success response 
    
    Http code: 200,
    message: "reset successfully"

error response 

    Http code: 404,
    error: no existed account,
    message: "reset failed"


