package com.project.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.project.utilities.ConnectionUtil;
public class Authenticator {
	
	public String authenticate(String username, String password) throws Exception {
		
		try {
			Connection con = ConnectionUtil.getConnection();
			Statement statement = con.createStatement();
			
			if (username == null || username == "") {
				return "userNameCannotBeBlank";
			} else {
				if (password == null || password == "") {
					return "passwordCannotBeBlank";
				}
			}
			ResultSet rs = statement.executeQuery("select user_id from user where user_name='"+username+"' and password='"+password+"' ");
			
			if (rs.next()) {
				if (username.startsWith("a"))
					return "admin";
				else if(username.startsWith("d"))
					return "developer";
				else if(username.startsWith("t"))
					return "tester";
				
			} else {
				return "failure";
			}
				
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "failure";
    }

	
	
	public long getUserIdFromUsername(HttpServletRequest request, String username) throws Exception {
		long userId = 0;
		try {
			Connection con = ConnectionUtil.getConnection();
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery("select user_id from user where user_name = '" + username + "'");
			
			while (rs.next()) {
				userId = rs.getLong("user_id");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userId;
	}
}

