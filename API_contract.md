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

description : tạo tài khoản mới
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

{
    "processSuccess": true,
    "data": "no data",
    "error": "no error",
    "message": "registered successfully",
    "httpStatus": 201
}


error response:

    {
        "processSuccess": false,
        "data": "no data",
        "error": "existed user name or phone number or student code or account name",
        "message": "register failed",
        "httpStatus": 409
    }
    or
    {
        "processSuccess": false,
        "data": "no data",
        "error": "there's a null value assign to a not null field",
        "message": "register failed",
        "httpStatus": 400
    }


POST /account/login

description : đăng nhập

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

error response : 

    khi user name không tồn tại
    {
        "processSuccess": false,
        "data": "no data",
        "error": "no username existed",
        "message": "login failed",
        "httpStatus": 404`
    }
    or
    khi user name hoặc password là null
    {
        "processSuccess": false,
        "data": "no data",
        "error": "null password or username",
        "message": "login failed",
        "httpStatus": 400
    }


PATCH /account/reset_password

description : đổi mật khẩu 

URL Params: None

Data Params:

    {
        userName: String,
        password: String,
    }

Headers:Content-Type: application/json

success response 
    
    {
    Http code: 200,
    message: "reset successfully"
    }

error response 

    {
    "processSuccess": false,
    "data": "no data",
    "error": "invalid password",
    "message": "reset failed",
    "httpStatus": 400
    }
    or
    {
    "processSuccess": false,
    "data": "no data",
    "error": "no existed account",
    "message": "reset failed",
    "httpStatus": 404
    }


#ExchangeClassRequest
ExchangeClassRequest object

    {
        "id": int,
        "studentCode": String,
        "classCode": String,
        "slot": String
    }

POST /exchangeClass

Description: Thêm một yêu cầu đổi lớp mới.

URL Params: None

Data Params:

    {
        "studentCode": "string",
        "classCode": "string",
        "slot": String
    }


Headers: Content-Type: application/json


Success Response:

HTTP Code: 201 CREATED
Content:
    
    {
        "processSuccess": true,
        "message": "request added successfully",
        "error": "no error",
        "data": "no data"
    }


Error Response:

Trường hợp studentCode đã tồn tại:

HTTP Code: 409 CONFLICT
    Content:

    {
        "processSuccess": false,
        "message": "already existed request for this student code",
        "error": "CONFLICT",
        "data": "no data"
    }


Trường hợp lỗi hệ thống (DB, internal error):

HTTP Code: 500 INTERNAL_SERVER_ERROR
Content:

    {
        "processSuccess": false,
        "message": "can not add for some internal errors",
        "error": "INTERNAL_SERVER_ERROR",
        "data": "no data"
    }

DELETE /exchangeClass/{id}

Description: Xóa một yêu cầu đổi lớp theo id.

URL Params:

    id (int) : id của request cần xóa

Headers:

Content-Type: application/json


Success Response:

    HTTP Code: 204 NO CONTENT
    (no body)


Error Response:

    Khi không tìm thấy id:
    
    HTTP Code: 404 NOT_FOUND
    Content:
    {
    "processSuccess": false,
    "message": "no request with id: {id}",
    "error": "NOT_FOUND",
    "data": "no data"
    }

GET /exchangeClass/{classCode}

Description: Lấy danh sách yêu cầu đổi lớp theo classCode.
URL Params:

    classCode (string): mã lớp cần lấy

Headers:

    Content-Type: application/json


Success Response:

    HTTP Code: 200 OK
    Content:
    {
        "processSuccess": true,
        "message": "request found successfully",
        "error": "no error",
        "data": list of request
    }

Error Response:
    
    Khi không có request nào cho classCode:
    HTTP Code: 404 NOT_FOUND
    Content:
    {
        "processSuccess": false,
        "message": "no request with that class code: {classCode}",
        "error": "NOT_FOUND",
        "data": null
    }




