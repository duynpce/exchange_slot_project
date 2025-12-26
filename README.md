# exchange_slot_project
project allow students to exchange slot in FPT university

link demo : https://exchange-class.vercel.app/

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

    - Database(7 table):
      accounts, chat, message, class, exchange_class_request, exchange_slot_request.