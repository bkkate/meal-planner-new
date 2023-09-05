<template>
<div class="background">

    <form class="form-register" @submit.prevent="register">
      <h1 class="h3 mb-3 font-weight-normal">Create Account</h1>
      <div class="alert alert-danger" role="alert" v-if="registrationErrors">
        {{ registrationErrorMsg }}
      </div>
      <label for="username" class="sr-only">Username</label>
      <input
        type="text"
        id="username"
        class="form-control"
        placeholder="Username"
        v-model="user.username"
        required
        autofocus
      />
      <label for="password" class="sr-only">Password</label>
      <input
        type="password"
        id="password"
        class="form-control"
        placeholder="Password"
        v-model="user.password"
        required
      />
      <input
        type="password"
        id="confirmPassword"
        class="form-control"
        placeholder="Confirm Password"
        v-model="user.confirmPassword"
        required
      />
    
      <button class="btn btn-lg btn-primary btn-block" type="submit">
        Create Account
      </button>  <router-link :to="{ name: 'login' }">Have an account?</router-link>
    </form>

  </div>
</template>

<script>
import authService from '../services/AuthService';

export default {
  name: 'register',
  data() {
    return {
      user: {
        username: '',
        password: '',
        confirmPassword: '',
        role: 'user',
      },
      registrationErrors: false,
      registrationErrorMsg: 'There were problems registering this user.',
    };
  },
  methods: {
    register() {
      if (this.user.password != this.user.confirmPassword) {
        this.registrationErrors = true;
        this.registrationErrorMsg = 'Password & Confirm Password do not match.';
      } else {
        authService
          .register(this.user)
          .then((response) => {
            if (response.status == 201) {
              this.$router.push({
                path: '/login',
                query: { registration: 'success' },
              });
              
            }
          })
          .catch((error) => {
            const response = error.response;
            this.registrationErrors = true;
            if (response.status === 400) {
              this.registrationErrorMsg = 'Bad Request: Validation Errors';
            }
          });
      }
    },
    clearErrors() {
      this.registrationErrors = false;
      this.registrationErrorMsg = 'There were problems registering this user.';
    },
  },
};
</script>

<style scoped>

.background {
  font-family: "Dosis", monospace, sans-serif;
 background-color:#eff1e8;
 height:90vh;
 display:flex;
 align-items:center;
 text-align:center;
}

h1{
  padding:10px;
}

.form-register {
  display: grid;
  gap: 10px;
  width:60%;
  margin: 0 auto;
  padding: 20px;
  border-radius: 5px;
font-family: 'Dosis', monospace, sans-serif;

}

input {
  font-family: "Dosis", monospace, sans-serif;
  display: block;
    width: 80%;
    margin: 0 auto;
    outline: none;
    background-color: #9191911f;
    border: none;
    border-radius: 5px;
    font-weight: 500;
    font-size: 17px;
    letter-spacing: 0.8px;
    font-size: 15px;
    backdrop-filter: blur(15px);
    -webkit-border-radius: 5px;
    -moz-border-radius: 5px;
    -ms-border-radius: 5px;
    -o-border-radius: 5px;
    text-align: center;  
    margin-bottom: 5px;
  border-radius: 5px;
  padding: 15px 10px;
}


.alert {
  margin-bottom: 20px;
}

label {
  display: none; 
}


router-link {
  display: block;
  margin-bottom: 10px;
}

button[type="submit"] {
  background-color: #89c36f; 
  color: #fff;
  padding: 15px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s ease;
  font-family: 'Dosis', monospace, sans-serif;
    width:80%;
    margin: 0 auto;
    font-size: 17px;
}

button[type="submit"]:hover {
  background-color: #2e7d32; 
  width:80%;
}



</style>
