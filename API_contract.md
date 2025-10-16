#Accounts
    
Account object
    
    {
        id : int
        userName: String
        password: String
        phoneNumber: String
        accountName: String
        studentCode: String
        classCode: String
    }

POST /register

description : tạo tài khoản mới
URL Params: None

Data Params:
    
        {
            username: String,
            password: String,
            phoneNumber: String,
            accountName: String,
            studentCode: String,
            classCode: String
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
    
khi có đã có field đã tồn tại

    {
        "processSuccess": false,
        "data": "no data",
        "error": "existed field name" ,
        "message": "register failed",
        "httpStatus": 409
    }
        sample:
        {
            "processSuccess": false,
            "data": "no data",
            "error": "existed username" ,
            "message": "register failed",
            "httpStatus": 409
        }

khi có null field
    
    {
        "processSuccess": false,
        "data": "no data",
        "error": "null field name",
        "message": "register failed",
        "httpStatus": 400
    }
        sample:
            {
                "processSuccess": false,
                "data": "no data",
                "error": "null username",
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
        content:
            {
                refresh token: string,
                access token: String
                message: "login sucess"
            }

error response : 

    sai mật khẩu
    content:
        {
            "processSuccess": false,
            "data": "no data",
            "error": "unauthorizaed",
            "message": "incorrect password",
            "httpStatus": 400
        }

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


JWT login
Headers:Content-Type: application/json

URL Param: None

Data Param (in header)
{
"Authorization" : "Bearer " + token(string)
}

success response : none 

error response:

    khi chưa đăng nhập hoặc sai token
    {
        "error": "status 401 - unauthorized, invalid token or haven't logged in",
        "message": "this action need authentication - please login to perform"
    }

    khi không có quyền truy cập
    json
    {
        you do not have the authorization to access this endpoint
    }

PATCH /reset_password

description : đổi mật khẩu 

URL Params: None

Data Params:

    {
        userName: String,
        newPassword: String,
    }

Headers:Content-Type: application/json

success response 
    
    {
    Http code: 200,
    message: "reset successfully"
    }

error response 

    có giá trị là null thì vẫn thông báo như ở trên
    
    khi mật khẩu không hợp lệ
    {
    "processSuccess": false,
    "data": "no data",
    "error": "invalid password",
    "message": "reset failed",
    "httpStatus": 400
    }

    khi không tìm thấy tài khoản
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
        "desiredClassCode": String,
        "CurrentclassCode": String,
        "desiredSlot" : String ("1,2" or "3.4")
        "currentSlot": String ("1,2" or "3.4" and != desiredSlot)
    }

POST /exchange_class

Description: Thêm một yêu cầu đổi lớp mới.

URL Params: None

Data Params:

    {
        "studentCode": "string",
        "desiredClassCode": "string",
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

    các trường hợp null và không tồn tại xử lý như ở trên

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

DELETE /exchange_class/id/{id}

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

GET /exchange_class/{classCode}/page/{page}

Description: Lấy danh sách yêu cầu đổi lớp theo classCode.
URL Params:

    classCode (string): mã lớp cần lấy
    page: là trang số mấy (>=0)
    example :
    /exchange_class/SE2003/page/0

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

POST /exchange_slot

Description:
Thêm một yêu cầu đổi ca học mới.

URL Params:
NONE

Data Param:

    {
        "studentCode": "string",
        "desiredSlot": "string"
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
    
    null và không tồn tại xử lý như ở trên

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

DELETE /exchange_slot/id/{id}

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


GET /exchange_slot/slot/{slot}/page/{page}

Description:
Lấy danh sách slot request theo ca học (slot) và phân trang.

URL Params:

    slot :  String("1,2" or "3,4").
    page : int(>=0).
    example : /exchange_slot/slot/3,4/page/0

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
