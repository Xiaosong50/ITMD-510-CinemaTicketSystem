This project is made by JAVA, JavaFX, JBDC, MySQL.

The URL of Database:

    http://www.papademas.net:81/phpmyadmin/db_structure.php?server=1&db=510fp&token=b687d56f7529d86719f28cff42699433
    
Also, you can change the DBConnection.java file to connect other database, example:

    private static final String URL = "jdbc:mysql://www.papademas.net:3307/510fp?autoReconnect=true&useSSL=false";
                            You can change the URL to you database, like "jdbc:mysql://localhost:3306/fp510"
    private static final String USER = "your database user name";
    private static final String PASSWORD = "your password";

For the first time running this project, there will be 14 tables created,

and insert some initial data, including: user, admins, payments, movie, actor, director, cinema, hall, seat, movie schedule, seat schedule, admin_managment.


