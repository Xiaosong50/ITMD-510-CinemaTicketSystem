This project is made by JAVA, JavaFX, JBDC, MySQL.

The URL of Database:

    http://www.papademas.net:81/phpmyadmin/db_structure.php?server=1&db=510fp&token=b687d56f7529d86719f28cff42699433
    
Also, you can change the DBConnection.java file to connect other database, example:

    private static final String URL = "jdbc:mysql://www.papademas.net:3307/510fp?autoReconnect=true&useSSL=false";
                            You can change the URL to you database, like "jdbc:mysql://localhost:3306/fp510"
    private static final String USER = "your database user name";
    private static final String PASSWORD = "your password";

For login:

    For user:
    Username: user1; Password: 123456.
    
    For admin: 
    manager: Username: Manager, Password: 123;
    normal admin: Username: Admin1, Password: 123.

For the first time running this project, there will be 14 tables created,
and insert some initial data, including: user, admins, payments, movie, actor, director, cinema, hall, seat, movie schedule, seat schedule, admin_managment etc.

There are 14 tables the project used:
    
    1. xl_users:
    user_id(PK), user_name, user_password, user_gender, user_phone, user_email
    
    2. xl_admins:
    admin_id(PK), admin_name, admin_password, admin_phone, admin_position
    
    3. xl_movie:
    movie_id(PK), title, director, release_date, movie_description, duration
    
    4. xl_actor:
    actor_id(PK), actor_name
    
    5. xl_movie_actor:
    movie_id(PK,FK), actor_id(PK,FK) 
    
    6. xl_cinema:
    cinema_id(PK), cinema_name, cinema_address, cinema_phone
    
    7. xl_hall:
    hall_id(PK), cinema_id(FK), hall_name, hall_type, seat_count
    
    8. xl_seat:
    seat_id(PK), hall_id(FK), seat_number
    
    9. xl_movie_schedule:
    schedule_id(PK), movie_id(FK), hall_id(FK), start_time, end_time, ticket_price
    
    10. xl_seat_schedule:
    seat_schedule_id(PK), schedule_id(FK), seat_id(FK), is_seat_sold
    
    11. xl_payment:
    payment_id(PK), payment_name
    
    12. xl_orders:
    order_id(PK), user_id(FK), schedule_id(FK), order_date, total_price, payment_id(FK)
    
    13. xl_order_seat:
    order_seat_id(PK), order_id(FK), seat_schedule_id(FK)
    
    14. xl_admin_management:
    manager_id(PK,FK), admin_id(PK,FK)
    
The functions of the project:
    
    For user:
        user login;
        check movies;
        search movies;
        check movie details;
        check movie schedules;
        check seats;
        buy tickets;
        check and modify user profile information;
        check history orders;
        user logout.
    
    for admin:
        admin login;
        movie management(check, add, modify and delete movie);
        movie schedule management with selected movie(check, add, modify and delete movie schedule);
        cinema management(check, add, modify and delete cinema);
        hall management with selected cinema(check, add, modify and delete hall);
        order management(check orders);
        admin management(only manager have the Permission)(check, add, modify(only the position) and delete admin);
        check and modify admin profile information;
        admin logout.







