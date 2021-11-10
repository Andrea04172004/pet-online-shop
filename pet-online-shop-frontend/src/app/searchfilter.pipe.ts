import {Pipe, PipeTransform} from '@angular/core';
import {User} from './model/User';

@Pipe({
  name: 'searchfilter',
})
export class SearchfilterPipe implements PipeTransform {

  transform(Users: User[], searchValue: string): User[] {

    if (!Users || !searchValue) {
      return Users;
    }
    return Users.filter((user) =>
      user.firstName.toLocaleLowerCase().includes(searchValue.toLocaleLowerCase()) ||
      user.lastName.toLocaleLowerCase().includes(searchValue.toLocaleLowerCase()) ||
      //user.phoneList.toLocaleLowerCase().includes(searchValue.toLocaleLowerCase()) ||
      user.email.toLocaleLowerCase().includes(searchValue.toLocaleLowerCase()) ||
      user.role.toLocaleLowerCase().includes(searchValue.toLocaleLowerCase()));
  }
}
