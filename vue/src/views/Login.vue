<template>
  <div class="login-page">
    <div class="form">
      <form class="form-signin" @submit.prevent="login">
        <h2> SIGN IN </h2>
        <div class="alert alert-danger" role="alert" v-if="invalidCredentials">
          Invalid username and password!
        </div>
        <div
          class="alert alert-success"
          role="alert"
          v-if="this.$route.query.registration"
        >
          Thank you for registering, please sign in.
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
        /><br />

        <div><button class="btn" type="submit">Submit</button></div>
        <router-link :to="{ name: 'register' }" class="needacc"
          >Need an account?</router-link
        >
      </form>
    </div>
  </div>
</template>

<script>
import authService from "../services/AuthService";
// import MainLogin from "../components/MainLogin.vue"

export default {
  name: "login",
  data() {
    return {
      user: {
        username: "",
        password: "",
      },
      invalidCredentials: false,
    };
  },
  methods: {
    login() {
      authService
        .login(this.user)
        .then((response) => {
          if (response.status == 200) {
            this.$store.commit("SET_AUTH_TOKEN", response.data.token);
            this.$store.commit("SET_USER", response.data.user);
            // this.$store.commit("SET_USER_ID", response.data.user.userId)//set user id
            this.$router.push("/");
          }
        })
        .catch((error) => {
          const response = error.response;

          if (response.status === 401) {
            this.invalidCredentials = true;
          }
        });
    },
  },
};
</script>
<style scoped>
.login-page {
  background: url("../assets/white-drawings.jpg");
  background-position: center;
  background-repeat: no-repeat;
  background-size: cover;
  height: 90vh;
  display:flex;
  align-items:center;
   font-family: "Dosis", monospace, sans-serif;
}

.form {
  width:50%;
  height:60%;
  text-align: center;
  background-color: rgb(245, 245, 243);
  border-radius: 25px;
  margin: 0 auto;
}
.form-signin {
  max-width: 300px;
  padding: 15px;
  margin: 0 auto;
}

.form-signin .form-control {
  box-sizing: border-box;
  height: auto;
  padding: 10px;
  font-size: 16px;
  align-content: center;
  text-align: center;
  box-shadow: 0 0px 1px rgba(0, 0, 0, 0.5);
}
.form-signin input:hover {
  color: black;
  background-color: #fcfaf4;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.3);
}

h2{
  padding:25px;
}
.btn {
  padding: 10px;
  width: 40%;
  font-size: 16px;
  margin:0 0 15px 0;
}
.sr-only {
  padding: 10px;
  font-weight: bold;
}
input {
  font-family: "Dosis", monospace, sans-serif;
  display: block;
    width: 100%;
    margin: 0.8rem 0;
    outline: none;
    background-color: #9191911f;
    border: none;
    border-radius: 5px;
    font-weight: 500;
    letter-spacing: 0.8px;
    font-size: 15px;
    backdrop-filter: blur(15px);
    -webkit-border-radius: 5px;
    -moz-border-radius: 5px;
    -ms-border-radius: 5px;
    -o-border-radius: 5px;
}



a{
  text-decoration:none;
  font-size: 18px;
}
</style>
