# exchange_slot_project
project allow students to exchange slot in FPT university

Tech stack :
- Backend : Spring Boot, Mysql, Docker ,Redis
- Frontend : Vanilla Javascript, HTML, CSS

main features:
- Authentication(JWT, password encryption , authorization, etc.).
- CRUD ( create, read ,update, delete  exchange requests, account etc.).
- Real-time chat application with web socket (implementing).

general documents :
- Frontend:
  .....
- Backend:
    - Folder Structure :
        - DTO(data transfer object): objects used for communicate with client (frontend)
        - Entity: ORM to perform operations with Database
        - Mapper: Transfer (map) DTO into Entity and vice versa
        - Repository(interface): Extends JpaRepository  to interact with Database
        - Service: Call Repository and contain specific logic for a specific object
        - Validator: Validate if the request of client (frontend) is valid
        - Controller: Define url , query method and call Service and Validator
        - Exception : Contain custom exception and global exception handler
        - Config: Configuration of security(Spring security), cache (redis) and web socket
        - Utility : Contain methods for general use
    - general flow:
        - Input : Client → Request → Controller(DTO ↔ Mapper ↔ Entity) → Validator → Service → Repository → Database
        - Output : Database → Response → Repository → Service → Controller(Entity ↔ Mapper ↔ DTO) → Client

    - Database(7 table):
      accounts, chat, message, class, exchange_class_request, exchange_slot_request.