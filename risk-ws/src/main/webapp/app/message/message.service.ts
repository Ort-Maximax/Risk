import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class MessageService {

  private _messages: string[] = [];

  public constructor() { }

  public get messages(): string[] {
    return this._messages;
  }

  public add(message: string): void {
    this._messages.push(message);
  }

  public clear(): void {
    this._messages = [];
  }

}
