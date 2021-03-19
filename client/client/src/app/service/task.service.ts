import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http'
import { environment } from'src/environments/environment'
import { Observable } from 'rxjs';
@Injectable({
  providedIn: 'root'
})
export class TaskService {

  constructor(private http:HttpClient) { }
  baseUrl = environment.baseUrl
  saveTask(task:any):Observable<any>{
    return this.http.post(this.baseUrl+'save/'+task,{observe:'response'})
  }


  getTasks():Observable<any>{
    return this.http.get(this.baseUrl+'gettask');
  }
  //remaining:Active
  getTasksRem():Observable<any>{
    return this.http.get(this.baseUrl+'gettaskrem');
  }
  //done:completed
  getTasksDon():Observable<any>{
    return this.http.get(this.baseUrl+'gettaskdone');
  }
  deleteAll():Observable<any>{
    return this.http.delete(this.baseUrl+'deleteall');
  }
  checkClicked(id,checked):Observable<any>{
    var data = new FormData();
    data.append('checked',checked);
    return this.http.post(this.baseUrl+'check/'+id,data,{observe:'response'});
  }
}
