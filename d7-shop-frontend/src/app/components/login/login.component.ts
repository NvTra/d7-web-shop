import { Component, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from '../../services/user.service';
import { LoginDTO } from '../../dtos/users/login.dto';
import { LoginResponse } from 'src/app/resposes/user/LoginResponse';
import { TokenService } from 'src/app/services/token.services';
import { RoleService } from 'src/app/services/role.service';
import { Role } from 'src/app/models/role';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent {
  @ViewChild('loginForm') loginForm!: NgForm;
  phoneNumber: string = '0903420443';
  password: string = '123456';
  rememberMe: boolean = true;
  roles: Role[] = [];
  selectedRole: Role | undefined;
  constructor(
    private router: Router,
    private userService: UserService,
    private tokenService: TokenService,
    private roleService: RoleService
  ) {}

  ngOnInit() {
    this.roleService.getRoles().subscribe({
      next: (roles: Role[]) => {
        debugger;
        this.roles = roles;
        this.selectedRole = roles.length > 0 ? roles[0] : undefined;
        // this.router.navigate(['/login']);
      },

      error: (error: any) => {
        debugger;
        alert(`Error getting roles: ${error.error}`);
      },
    });
  }
  login() {
    const loginDTO: LoginDTO = {
      phone_number: this.phoneNumber,
      password: this.password,
      role_id: this.selectedRole?.id ?? 1,
    };
    this.userService.login(loginDTO).subscribe({
      next: (response: LoginResponse) => {
        debugger;
        const { token } = response;
        this.tokenService.setToken(token);
        // this.router.navigate(['/login']);
      },
      complete: () => {
        debugger;
      },
      error: (error: any) => {
        debugger;
        alert(error?.error?.message);
      },
    });
  }
  onPhoneNumberChange() {
    console.log(this.phoneNumber + ' ');
  }
}
