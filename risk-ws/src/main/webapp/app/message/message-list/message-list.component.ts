import {
  Component,
  OnInit
} from '@angular/core';

import { MessageService } from '../message.service';

@Component({
  selector: 'app-message-list',
  templateUrl: './message-list.component.html',
  styleUrls: ['./message-list.component.css']
})
export class MessageListComponent implements OnInit {

  constructor(
    private _messageService: MessageService
  ) { }

  ngOnInit() {
  }

  get messages(): string[] {
    return this._messageService.messages;
  }

  clear(): void {
    this._messageService.clear();
  }

}
