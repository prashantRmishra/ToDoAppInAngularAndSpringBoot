import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms'
import { TaskService } from './service/task.service'

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  ngOnInit() {

    this.service.getTasks().subscribe(res=>{
      if (res != null) {
        this.taskList.length = 0;
        for (let i = 0; i < res.length; i++) {
          this.taskList.push(res[i]);
        }
      }
    })
  }
  title = 'client';
  todo: FormGroup;
  constructor(private fb: FormBuilder,
    private service: TaskService) {
    this.todo = this.fb.group({
      task: ['', Validators.required]

    })
  }
  data: task;
  dataFromDb: task[] = []
  taskList: task[] = []
  id = 1;
  activeCall: boolean = false;
  clearCAll: boolean = false;
  completeCall: boolean = false;
  allcall: boolean = false
  saveTask() {

    this.taskList.push({ task: this.todo.controls['task'].value, id: this.id++, status: 'false' })
    this.service.saveTask(this.todo.controls['task'].value).subscribe(data => {
      if (data.execution == "success") {
        this.service.getTasks().subscribe(res => {
          if (res != null) {
            this.taskList.length = 0;
            for (let i = 0; i < res.length; i++) {
              this.taskList.push(res[i]);
            }
          }

        });
      }
    });
  }
  checkClicked(i,event) {
   var checked=event.target.checked;

    this.service.checkClicked(i,checked).subscribe(data => {
      if (data.execution == "success") {
        this.service.getTasks().subscribe(res => {
          if (res != null) {
            this.taskList.length = 0;
            for (let i = 0; i < res.length; i++) {
              this.taskList.push(res[i]);
            }
          }

        });
      }
    });
  }
  all() {
    this.service.getTasks().subscribe(res => {
      if (res != null) {
        this.taskList.length = 0;
        for (let i = 0; i < res.length; i++) {
          this.taskList.push(res[i]);
        }
      }

    });
  }
  activeList: task[] = []
  active() {
    this.service.getTasksRem().subscribe(data => {
      if (data != null) {
        this.taskList.length = 0;
        for (let i = 0; i < data.length; i++) {
          this.taskList.push(data[i]);
        }
      }
    })
  }
  complete() {
    this.service.getTasksDon().subscribe(data => {
      if (data != null) {
        this.taskList.length = 0;
        for (let i = 0; i < data.length; i++) {
          this.taskList.push(data[i]);
        }
      }
    })
  }
  clearAll() {
    this.service.deleteAll().subscribe(data => {
      if (data.execution == "success") {
        this.taskList.length = 0;
      }
    })
  }


}
export interface task {
  id: any
  task: string,
  status: string;
}
