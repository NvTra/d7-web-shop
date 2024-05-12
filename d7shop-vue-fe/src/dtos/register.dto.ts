export class RegisterDTO {
  fullName: string | undefined;
  phoneNumber: string | undefined;
  email: string | undefined;
  password: string | undefined;
  retypePassword: string | undefined;
  gender: number | undefined;
  dateOfBirth: Date | undefined;
  address: string | undefined;
  facebook_account_id = 0;
  google_account_id = 0;
  roleId = 1;
  constructor(data: any) {
    this.fullName = data.fullName;
    this.phoneNumber = data.phoneNumber;
    this.password = data.password;
    this.retypePassword = data.retypePassword;
    this.dateOfBirth = data.dateOfBirth;
    this.address = data.address;
    this.facebook_account_id = data.facebook_account_id || 0;
    this.google_account_id = data.google_account_id || 0;
    this.roleId = data.roleId || 1;
  }
}
