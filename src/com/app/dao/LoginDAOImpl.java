package com.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.app.model.Login;
import com.app.util.DBConnectionUtil;

public class LoginDAOImpl implements LoginDAO{

	@Override
	public String loginCheck(Login loginBean) {
		String query="select * from tbl_login where email=? and password=?";
		
		try{
			Connection con=DBConnectionUtil.openConnection();
			PreparedStatement ps=con.prepareStatement(query);
			ps.setString(1,loginBean.getEmail());
			ps.setString(2,loginBean.getPassword());
			
			ResultSet rs=ps.executeQuery();
			
			if(rs.next()){
				return "true";
			}
			else{
				return "false";
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return "error";
	}

}
