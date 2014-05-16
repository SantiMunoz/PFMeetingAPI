/*Copyright 2014 Santi Muñoz Mallofre
 
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.*/
package com.planfeed.bbdd;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySqlConnection {

	private static String server = "jdbc:mysql://localhost/planfeed";
	private static String user = "root";
	private static String pass = "root";
	private static String driver = "com.mysql.jdbc.Driver";

	private static Connection conn;

	public MySqlConnection() throws Exception {
		try {
			
			Class.forName(driver);
			conn = DriverManager.getConnection(server, user, pass);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getStackTrace().toString());
		}
	}

	public Connection getConn() {
		return conn;
	}
}
