<template>
 
        <input type="text" v-model="user.name" placeholder="name">
        <input type="text" v-model="user.password" placeholder="password">
        <input type="text" v-model="user.email" placeholder="email">
        <button @click="save">click</button>
   
</template>
<script lang="ts">
import { Vue } from "vue-class-component";
import User from "@/models/User"
export default class AppLogin extends Vue{
    user:User={
        name:'',
        email:'',
        password:''
    }
    save(){
        console.log(this.user);
    }
}
</script>