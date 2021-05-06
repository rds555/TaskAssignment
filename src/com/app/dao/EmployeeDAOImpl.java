package com.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.app.model.Employee;
import com.app.util.DBConnectionUtil;

public class EmployeeDAOImpl implements EmployeeDAO {
	
	Connection connection = null;
	ResultSet resultSet = null;
	Statement statement = null;
	PreparedStatement preparedStatement = null;
	
	@Override
	public List<Employee> get() {
		
		List<Employee> list = null;
		Employee employee = null;
		
		try {
			
			list = new ArrayList<Employee>();
			//String sql = "SELECT * FROM tbl_employee";
			String plsql = "CALL Getemployee()";
			connection = DBConnectionUtil.openConnection();
			CallableStatement cst= connection.prepareCall(plsql);
			//statement = connection.createStatement();
			//resultSet = statement.executeQuery(sql);
				ResultSet resultSet=cst.executeQuery();
			while(resultSet.next()) {
				employee = new Employee();
				employee.setId(resultSet.getInt("id"));
				employee.setName(resultSet.getString("name"));
				employee.setDepartment(resultSet.getString("department"));
				employee.setDob(resultSet.getString("dob"));
				list.add(employee);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Employee get(int id) {
		Employee employee = null;
		try {
			employee = new Employee();
			//String sql = "SELECT * FROM tbl_employee where id="+id;
				String plsql = "CALL Getoneemployee(?)";
			connection = DBConnectionUtil.openConnection();
			CallableStatement cst= connection.prepareCall(plsql);
			//statement = connection.createStatement();
			//resultSet = statement.executeQuery(sql);
			cst.setInt(1, id);
			ResultSet resultSet=cst.executeQuery();
			if(resultSet.next()) {
				employee.setId(resultSet.getInt("id"));
				employee.setName(resultSet.getString("name"));
				employee.setDepartment(resultSet.getString("department"));
				employee.setDob(resultSet.getString("dob"));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return employee;
	}

	@Override
	public boolean save(Employee e) {
		boolean flag = false;
		try {
			//String sql = "INSERT INTO tbl_employee(name, department, dob)VALUES"
			//		+ "('"+e.getName()+"', '"+e.getDepartment()+"', '"+e.getDob()+"')";
			connection = DBConnectionUtil.openConnection();
			CallableStatement cst= connection.prepareCall("{CALL Addemployee(?,?,?)}");
			cst.setString(1,e.getName());
			cst.setString(2,e.getDepartment());
			cst.setString(3,e.getDob());
			//preparedStatement = connection.prepareStatement(sql);
			//preparedStatement.executeUpdate();
			cst.executeUpdate();
			flag = true;
		}catch(SQLException ex) {
			ex.printStackTrace();
		}
		return flag;
	}

	@Override
	public boolean delete(int id) {
		boolean flag = false;
		try {
			//String sql = "DELETE FROM tbl_employee where id="+id;
			String plsql = "CALL Deleteemployee(?)";
			connection = DBConnectionUtil.openConnection();
			//preparedStatement = connection.prepareStatement(sql);
			//preparedStatement.executeUpdate();
				CallableStatement cst= connection.prepareCall(plsql);
			cst.setInt(1, id);
			cst.executeUpdate();
			flag = true;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public boolean update(Employee employee) {
		boolean flag = false;
		try {
			String sql = "UPDATE tbl_employee SET name = '"+employee.getName()+"', "
					+ "department = '"+employee.getDepartment()+"', dob = '"+employee.getDob()+"' where id="+employee.getId();
			connection = DBConnectionUtil.openConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.executeUpdate();
			flag = true;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}

}
