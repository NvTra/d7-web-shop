<template>
    <section class="vh-100">
        <div class="wrapper">
            <form class="form-right text-start">
                <h2 class="text-uppercase text-center">Đăng ký tài khoản</h2>
                <div class="row">
                    <div class="col-sm-6 mb-3">
                        <label>Họ và tên</label>
                        <input type="text" name="fullname" v-model="fullName" class="input-field">
                    </div>
                    <div class="col-sm-6 mb-3">
                        <label>Email</label>
                        <input type="email" class="input-field" v-model="email" name="email">
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-6 mb-3">
                        <label>Mật khẩu</label>
                        <input type="password" class="input-field" v-model="password">
                    </div>
                    <div class="col-sm-6 mb-3">
                        <label>Nhập lại mật khẩu</label>
                        <input type="password" class="input-field" v-model="retypePassword">
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-6 mb-3">
                        <label>Số điện thoại</label>
                        <input type="text" class="input-field" v-model="phoneNumber">
                    </div>
                    <div class="col-sm-6 mb-3">
                        <label>Địa chỉ</label>
                        <input type="password" class="input-field" v-model="address">
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-6 mb-3">
                        <label>Ngày sinh</label>
                        <input type="date" class="input-field" v-model="dayOfBirth">
                    </div>
                    <div class="col-sm-6 mb-3">
                        <label>Giới tính</label>
                        <select style="padding: 10px !important;" class="form-select"
                            aria-label="Default select example">
                            <option value="1">Nam</option>
                            <option value="2">Nữ</option>
                            <option value="3">Khác</option>
                        </select>
                    </div>
                </div>
                <div class="col-sm-6">
                    <div class="mb-3">
                        <label class="option">I agree to the <a href="#">Terms and Conditions</a>
                            <input type="checkbox" checked>
                            <span class="checkmark"></span>
                        </label>
                    </div>

                </div>
                <div class="form-field text-center">
                    <input type="submit" value="Register" class="register" name="register">
                </div>
            </form>
        </div>
    </section>
</template>
<script lang="ts">
import { RegisterDTO } from '@/dtos/register.dto';
import { Vue } from 'vue-class-component';


export default class AppRegister extends Vue {
    fullName: string | undefined;
    phoneNumber: string | undefined;
    email: string | undefined;
    password: string | undefined;
    retypePassword: string | undefined;
    gender: number | undefined;
    dayOfBirth: Date | undefined;
    address: string | undefined

    save() {
        const registerDTO: RegisterDTO = {
            fullName: this.fullName,
            phoneNumber: this.phoneNumber,
            email: this.email,
            password: this.password,
            retypePassword: this.retypePassword,
            gender: this.gender,
            dateOfBirth: this.dayOfBirth,
            address: this.address,
            facebook_account_id: 0,
            google_account_id: 0,
            roleId: 0
        }
        console.log(registerDTO)
    }
}
</script>