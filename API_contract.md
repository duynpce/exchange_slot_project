#Accounts
    
Account object
    
    {
        id : int
        userName: String
        password: String
        phoneNumber: String
        accountName: String
        studentCode: String
        classCode : String
    }

POST /register

description : tạo tài khoản mới
URL Params: None

Data Params:
    
        {
            userName: String,
            password: String,
            phoneNumber: String,
            accountName: String,
            studentCode: String,
            classCode : String,
            role : String ("USER or "ADMIN")
        }

Headers: Content-Type: application/json

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


POST /login

description : đăng nhập

URL Params: None

URL Params:

    {
        username: String,
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
    or
    khi sai mật 
    content:
            Http code : 403
            {
                refresh token: "no refresh token" ,
                access token: "no access token",
                message: "login failed  please check user name or password"
            }


JWT login
Headers:Content-Type: application/json

URL Param: None

Data Param (in header)
{
"Authorization" : "Bearer " + token(string)
}

success response : incomplete

error responseL incomplete

PATCH /reset_password

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

GET /exchangeClass/{classCode}/page/{page}

Description: Lấy danh sách yêu cầu đổi lớp theo classCode.
URL Params:

    classCode (string): mã lớp cần lấy
    page: là trang số mấy

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

POST /exchangeSlot

Description:
Thêm một yêu cầu đổi ca học mới.

URL Params:
NONE

Data Param:

    {
        "studentCode": "string",
        "classCode": "string",
        "slot": "string"
    }


Headers:
Content-Type: application/json

 Success Response:

    HTTP Code: 201 CREATED 
    Content:
    {
        "processSuccess": true,
        "message": "slot request added successfully",
        "error": "no error",
        "data": "no data"
    }

Error Responses:

Khi studentCode đã tồn tại yêu cầu đổi slot:
    
    HTTP Code: 409 CONFLICT
    
    Content:
    {
        "processSuccess": false,
        "message": "already existed slot request for this student code",
        "error": "CONFLICT",
        "data": "no data"
    }


Khi có lỗi hệ thống (lỗi DB, exception nội bộ):

    HTTP Code: 500 INTERNAL_SERVER_ERROR 
    Content:
    
    {
    "processSuccess": false,
    "message": "can not add slot request due to internal errors",
    "error": "INTERNAL_SERVER_ERROR",
    "data": "no data"
    }

DELETE /exchangeSlot/{id}

Description: Xóa một yêu cầu đổi slot theo id.

URL Params:

    id : int.

Headers: Content-Type: application/json

 Success Response:

    HTTP Code: 204 NO CONTENT
    
    Content: (no body)

 Error Response:

Khi không tìm thấy slot request theo id:

    HTTP Code: 404 NOT_FOUND
    
    Content: 
    {
        "processSuccess": false,
        "message": "no slot request with id: {id}",
        "error": "NOT_FOUND",
        "data": "no data"
    }

GET /exchangeSlot/class/{classCode}/page/{page}

Description: Lấy danh sách slot request theo mã lớp (classCode) và phân trang.

URL Params:

    classCode : string.
    
    page : int(>=0).

Headers: Content-Type: application/json

Success Response:

    HTTP Code: 200 OK
    Content:
    {
        "processSuccess": true,
        "message": "slot request(s) found successfully",
        "error": "no error",
        "data": [
        {
        "id": 1,
        "studentCode": "S123456",
        "classCode": "CS101",
        "slot": "3-4",
        },
        ...
        ]
    }

Error Response:

Khi không có slot request nào cho classCode:
    
    HTTP Code: 404 NOT_FOUND
    Content:
    {
        "processSuccess": false,
        "message": "no slot request with class code: {classCode}",
        "error": "NOT_FOUND",
        "data": null
    }

GET /exchangeSlot/subject/{subjectCode}/page/{page}

Description:
Lấy danh sách slot request theo mã môn học (subjectCode) và phân trang.

URL Params:

    subjectCode : String.
    page : int.

Headers: Content-Type: application/json

Success Response:

    HTTP Code: 200 OK
    Content:
    {
        "processSuccess": true,
        "message": "slot request(s) found successfully",
        "error": "no error",
        "data": [ ... ]
    }

Error Response:

Khi không có slot request nào cho subjectCode:
    
    HTTP Code: 404 NOT_FOUND
    Content:
    {
        "processSuccess": false,
        "message": "no slot request with subject code: {subjectCode}",
        "error": "NOT_FOUND",
        "data": null
    }

GET /exchangeSlot/slot/{slot}/page/{page}

Description:
Lấy danh sách slot request theo ca học (slot) và phân trang.

URL Params:

    slot :  String("1,2" or "3,4").
    page : int(>=0).

Headers:Content-Type: application/json

Success Response:

    HTTP Code: 200 OK
    Content:
    {
        "processSuccess": true,
        "message": "slot request(s) found successfully",
        "error": "no error",
        "data": [ ... ]
    }

Error Response:

Khi không có slot request nào cho slot:

    HTTP Code: 404 NOT_FOUND
    Content:
    {
        "processSuccess": false,
        "message": "no slot request with slot: {slot}",
        "error": "NOT_FOUND",
        "data": null
    }

GET /exchangeSlot/subject_code&class_code?subjectCode=...&classCode=...&page=...

Description:
Lấy danh sách slot request theo subjectCode và classCode, có phân trang.

Query(Data) Params:
    
    subjectCode (string)
    classCode (string)
    page (int)

Headers: Content-Type: application/json

Success Response:
    
    HTTP Code: 200 OK 
    Content:
    {
        "processSuccess": true,
        "message": "slot request(s) found successfully",
        "error": "no error",
        "data": [ ... ]
    }

Error Response:

Không có dữ liệu phù hợp:

    HTTP Code: 404 NOT_FOUND
    Content:
    {
        "processSuccess": false,
        "message": "no slot request with subject code: {subjectCode} and class code: {classCode}",
        "error": "NOT_FOUND",
        "data": null
    }

