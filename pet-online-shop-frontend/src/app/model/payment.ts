import {Attachment} from "./attachment";

export class Payment {
  public id: number;
  public date: Date;
  public time: Date;
  public amount: String;
  public isCash: boolean;
  public clientName: String;
  public attachment: Attachment;
}
