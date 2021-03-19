package com.example.demo.controller;


import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.demo.model.ToDoBean;

import org.springframework.jdbc.core.RowMapper;
public class ToDOMapper implements  RowMapper<ToDoBean>{

    @Override
    public ToDoBean mapRow(ResultSet rs, int rowNum) throws SQLException {
        ToDoBean bean = new ToDoBean();
        bean.setId(rs.getString("id"));
        bean.setTask(rs.getString("task"));
        bean.setStatus(rs.getString("status"));
        return bean;
    }
    
}
