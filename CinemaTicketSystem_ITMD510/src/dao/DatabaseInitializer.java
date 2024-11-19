package dao;

import java.sql.Connection;
import java.sql.Statement;

import model.DBConnection;

public class DatabaseInitializer {
	public static void createTables() {
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
				// Insert initial data into the admins table
				"INSERT INTO admins (admin_name, admin_password, admin_phone, admin_position) "
						+ "SELECT * FROM (SELECT 'manager' AS admin_name, '123' AS admin_password, '1234567890' AS admin_phone, 'manager' AS admin_position) AS temp "
						+ "WHERE NOT EXISTS (SELECT 1 FROM admins WHERE admin_name = 'manager');" };

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
