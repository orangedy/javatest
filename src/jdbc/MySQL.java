package jdbc;

import java.sql.*;

public class MySQL {
	public static void main(String[] args) throws InstantiationException,
	IllegalAccessException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		String url = "jdbc:mysql://localhost:3306/bbscrawl";
		String userName = "root";
		String passWord = "123";
		// myDBΪ��ݿ���
		try {
			Connection con = DriverManager.getConnection(url, userName, passWord);
			Statement sql = con.createStatement();
			sql.execute("drop table if exists student");
			sql.execute("create table student(id int not null auto_increment,name varchar(20) not null default 'name',math int not null default 60,primary key(id));");
			sql.execute("insert student values(1,'AAA','99')");
			sql.execute("insert student values(2,'BBB','77')");
			sql.execute("insert student values(3,'CCC','65')");
			String query = "select * from student";
			ResultSet result = sql.executeQuery(query);
			System.out.println("Student");
			System.out.println("---------------------------------");
			System.out.println("a" + " " + "b" + " " + "c");
			System.out.println("---------------------------------");
			int number;
			String name;
			String math;
			while (result.next()) {
				number = result.getInt("id");
				name = result.getString("name");
				math = result.getString("math");
				System.out.println(number + " " + name + " " + math);

			}
			sql.close();
			con.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		}
	}

}
