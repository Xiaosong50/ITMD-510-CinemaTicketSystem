package dao;

import java.sql.Connection;
import java.sql.Statement;

import model.DBConnection;

public class DatabaseInitializer {
	public static void initializeDatabase() {
		String[] createTableQueries = {
				// users table
				"create table if not exists xl_users (" + "user_id int primary key auto_increment, "
						+ "user_name varchar(50) not null, " + "user_password varchar(50) not null, "
						+ "user_gender varchar(10), " + "user_phone varchar(20), " + "user_email varchar(50))",

				// admins table
				"create table if not exists xl_admins (" + "admin_id int primary key auto_increment, "
						+ "admin_name varchar(50) not null, " + "admin_password varchar(50) not null, "
						+ "admin_phone varchar(20), " + "admin_position varchar(20))",

				// movie table
				"create table if not exists xl_movie (" + "movie_id int primary key auto_increment, "
						+ "title varchar(100) not null, " + "director varchar(50), " + "release_date date, "
						+ "movie_description text, " + "duration int)",

				// actor table
				"create table if not exists xl_actor (" + "actor_id int primary key auto_increment, "
						+ "actor_name varchar(50) not null)",

				// movie_actor table
				"create table if not exists xl_movie_actor (" + "movie_id int not null, " + "actor_id int not null, "
						+ "primary key (movie_id, actor_id), "
						+ "foreign key (movie_id) references xl_movie(movie_id) on delete cascade, "
						+ "foreign key (actor_id) references xl_actor(actor_id) on delete cascade)",

				// cinema table
				"create table if not exists xl_cinema (" + "cinema_id int primary key auto_increment, "
						+ "cinema_name varchar(50) not null, " + "cinema_address varchar(100), "
						+ "cinema_phone varchar(20))",

				// hall table
				"create table if not exists xl_hall (" + "hall_id int primary key auto_increment, "
						+ "cinema_id int not null, " + "hall_name varchar(50) not null, " + "hall_type varchar(20), "
						+ "seat_count int, "
						+ "foreign key (cinema_id) references xl_cinema(cinema_id) on delete cascade)",

				// seat table
				"create table if not exists xl_seat (" + "seat_id int primary key auto_increment, "
						+ "hall_id int not null, " + "seat_number varchar(10) not null, "
						+ "foreign key (hall_id) references xl_hall(hall_id) on delete cascade)",

				// movie_schedule table
				"create table if not exists xl_movie_schedule (" + "schedule_id int primary key auto_increment, "
						+ "movie_id int not null, " + "hall_id int not null, " + "start_time datetime not null, "
						+ "end_time datetime, " + "ticket_price decimal(8, 2) not null, "
						+ "foreign key (movie_id) references xl_movie(movie_id) on delete cascade, "
						+ "foreign key (hall_id) references xl_hall(hall_id) on delete cascade)",

				// seat_schedule table
				"create table if not exists xl_seat_schedule (" + "seat_schedule_id int primary key auto_increment, "
						+ "schedule_id int not null, " + "seat_id int not null, "
						+ "is_seat_sold boolean default false not null, "
						+ "foreign key (schedule_id) references xl_movie_schedule(schedule_id) on delete cascade, "
						+ "foreign key (seat_id) references xl_seat(seat_id) on delete cascade)",

				// payment table
				"create table if not exists xl_payment (" + "payment_id int primary key auto_increment, "
						+ "payment_name varchar(20) not null unique)",

				// orders table
				"create table if not exists xl_orders (" + "order_id int primary key auto_increment, "
						+ "user_id int not null, " + "schedule_id int not null, "
						+ "order_date timestamp default current_timestamp, " + "total_price decimal(8, 2) not null, "
						+ "payment_id int, " + "foreign key (user_id) references xl_users(user_id) on delete cascade, "
						+ "foreign key (schedule_id) references xl_movie_schedule(schedule_id) on delete cascade, "
						+ "foreign key (payment_id) references xl_payment(payment_id) on delete set null)",

				// order_seat table
				"create table if not exists xl_order_seat (" + "order_seat_id int primary key auto_increment, "
						+ "order_id int not null, " + "seat_schedule_id int not null, "
						+ "foreign key (order_id) references xl_orders(order_id) on delete cascade, "
						+ "foreign key (seat_schedule_id) references xl_seat_schedule(seat_schedule_id) on delete cascade)",

				// admin_management table
				"create table if not exists xl_admin_management (" + "manager_id int not null, "
						+ "admin_id int not null, " + "primary key (manager_id, admin_id), "
						+ "foreign key (manager_id) references xl_admins(admin_id) on delete cascade, "
						+ "foreign key (admin_id) references xl_admins(admin_id) on delete cascade)" };

		String[] initialDataQueries = {
				"insert into xl_admins (admin_name, admin_password, admin_phone, admin_position) "
						+ "select * from (select 'Manager' as admin_name, '123' as admin_password, '1234567890' as admin_phone, 'manager' as admin_position) as temp "
						+ "where not exists (select 1 from xl_admins where admin_name = 'Manager');",

				"insert into xl_admins (admin_name, admin_password, admin_phone, admin_position) "
						+ "select * from (select 'Admin1' as admin_name, '123' as admin_password, '9876543210' as admin_phone, 'admin' as admin_position) as temp "
						+ "where not exists (select 1 from xl_admins where admin_name = 'Admin1');",

				"insert into xl_users (user_name, user_password, user_gender, user_phone, user_email) "
						+ "select * from (select 'user1' as user_name, '123456' as user_password, 'male' as user_gender, '9876543210' as user_phone, 'user1@example.com' as user_email) as temp "
						+ "where not exists (select 1 from xl_users where user_name = 'user1');",

				"insert into xl_movie (title, director, release_date, movie_description, duration) "
						+ "select * from (select 'Inception' as title, 'Christopher Nolan' as director, '2010-07-16' as release_date, 'A mind-bending thriller' as movie_description, 148 as duration) as temp "
						+ "where not exists (select 1 from xl_movie where title = 'Inception');",

				"insert into xl_actor (actor_name) "
						+ "select * from (select 'Leonardo Dicaprio' as actor_name union all select 'Joseph Gordon-Levitt' union all select 'Ellen Page') as temp "
						+ "where not exists (select 1 from xl_actor where actor_name = 'Leonardo Dicaprio');",

				"insert into xl_movie_actor (movie_id, actor_id) "
						+ "select 1 as movie_id, actor_id from xl_actor where actor_name in ('Leonardo Dicaprio', 'Joseph Gordon-Levitt', 'Ellen Page') "
						+ "and not exists (select 1 from xl_movie_actor where movie_id = 1 and actor_id = xl_actor.actor_id);",

				"insert into xl_cinema (cinema_name, cinema_address, cinema_phone) "
						+ "select * from (select 'Central Cinema' as cinema_name, '123 Main St' as cinema_address, '1234567890' as cinema_phone) as temp "
						+ "where not exists (select 1 from xl_cinema where cinema_name = 'Central Cinema');",

				"insert into xl_hall (cinema_id, hall_name, hall_type, seat_count) "
						+ "select * from (select 1 as cinema_id, 'IMAX Hall' as hall_name, 'IMAX' as hall_type, 50 as seat_count) as temp "
						+ "where not exists (select 1 from xl_hall where hall_name = 'IMAX Hall');",

				"SET @row = 0;",
				"INSERT INTO xl_seat (hall_id, seat_number) " + "SELECT 1 AS hall_id, "
						+ "CONCAT(CHAR(65 + FLOOR((seq - 1) / 10)), ((seq - 1) % 10) + 1) AS seat_number " + "FROM ( "
						+ "    SELECT @row := @row + 1 AS seq "
						+ "    FROM (SELECT 0 UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) t1, "
						+ "         (SELECT 0 UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) t2 "
						+ ") seq " + "WHERE seq <= 50 " + "AND NOT EXISTS (SELECT 1 FROM xl_seat WHERE hall_id = 1);",

				"insert into xl_movie_schedule (movie_id, hall_id, start_time, end_time, ticket_price) "
						+ "select * from (select 1 as movie_id, 1 as hall_id, '2024-11-01 10:00:00' as start_time, '2024-11-01 12:28:00' as end_time, 50.00 as ticket_price) as temp "
						+ "where not exists (select 1 from xl_movie_schedule where movie_id = 1 and hall_id = 1);",

				"insert into xl_seat_schedule (schedule_id, seat_id, is_seat_sold) "
						+ "select 1 as schedule_id, seat_id, false from xl_seat where hall_id = 1 "
						+ "and not exists (select 1 from xl_seat_schedule where schedule_id = 1 and seat_id = xl_seat.seat_id);",

				"insert into xl_payment (payment_name) "
						+ "select * from (select 'Credit Card' as payment_name union all select 'Debit Card' union all select 'Paypal' union all select 'Apple Pay' union all select 'Google Pay') as temp "
						+ "where not exists (select 1 from xl_payment where payment_name = 'Credit Card');",

				"insert into xl_admin_management (manager_id, admin_id) "
						+ "select * from (select 1 as manager_id, 2 as admin_id) as temp "
						+ "where not exists (select 1 from xl_admin_management where manager_id = 1 and admin_id = 2);" };

		try (Connection connection = DBConnection.getConnection(); Statement statement = connection.createStatement()) {

			for (String query : createTableQueries) {
				statement.executeUpdate(query);
			}

			for (String query : initialDataQueries) {
				statement.executeUpdate(query);
			}

			System.out.println("Database initialized successfully, including initial data.");

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Database initialization failed.");
		}
	}
}
