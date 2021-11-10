export class Phone {

  constructor(
    public id?: number,
    public phoneNumber?: string) {
  }

  get phone(): string {
    return this.phoneNumber;
  }


  set phoneN(value: string) {
    this.phoneNumber = value;
  }
}

