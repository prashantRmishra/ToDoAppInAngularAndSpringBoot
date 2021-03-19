package com.example.demo.controller;

import java.util.List;

import javax.websocket.server.PathParam;

import com.example.demo.model.Response;
import com.example.demo.model.ToDoBean;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TODoController {
    @Autowired
    JdbcTemplate jdbcTemplate;
    
    @PostMapping("/save/{task}")
    ResponseEntity<Object> saveTask(@RequestBody ToDoBean bean,@PathVariable String task){
        int result=0;
        bean.setTask(task);
        bean.setStatus("false");
        Response res = new Response();
       try {
        result+=this.jdbcTemplate.update("insert into todo(task,status) values(?,?);",new Object[]{
            bean.getTask(),bean.getStatus()});
       } catch (Exception e) {
          e.printStackTrace();
       }
       if(result >0) {
           res.setExecution("success");
       }
       else{
           res.setExecution("failure");
       }
        return ResponseEntity.ok(res);
    }
    //All
    @GetMapping("/gettask")
    ResponseEntity<Object> getTask(){
        List<ToDoBean> list=null;
        list = this.jdbcTemplate.query("select * from todo;",new ToDOMapper()) ;
        return ResponseEntity.ok(list);
    }

    //completed:checked
    @GetMapping("/gettaskdone")
    ResponseEntity<Object> getTaskDone(){
        List<ToDoBean> list=null;
        list = this.jdbcTemplate.query("select * from todo where status= 'true';",new ToDOMapper()) ;
        return ResponseEntity.ok(list);
    }

    //active:unchecked
    @GetMapping("/gettaskrem")
    ResponseEntity<Object> getTaskRem(){
        List<ToDoBean> list=null;
        list = this.jdbcTemplate.query("select * from todo where status= 'false';",new ToDOMapper()) ;
        return ResponseEntity.ok(list);
    }
    
    @DeleteMapping("/deleteall")
    ResponseEntity<Object> deleteAll(){
        int result =0;
        Response res = new Response();
        try {
            result+=this.jdbcTemplate.update("delete from todo;");
        } catch (Exception e) {
           e.printStackTrace();
        }
        if(result > 0) {
            res.setExecution("success");
        }
        else{
            res.setExecution("failure");
        }
        return ResponseEntity.ok(res);
    }

    @PostMapping("/check/{id}")
    ResponseEntity<Object> checkClicked(@PathVariable int id,@RequestParam String checked){
        int result=0;
        Response res = new Response();

        try {
           
            
            result+=this.jdbcTemplate.update("update todo set status=? where id = ?", new Object[]{checked,id});
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(result > 0) {
            res.setExecution("success");
        }
        else{
            res.setExecution("failure");
        }
        return ResponseEntity.ok(res);

    }
 
}
