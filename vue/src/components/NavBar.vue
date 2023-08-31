<template>
  <div id="headerNav">
    <div class="logoImg" id="top-bar">
      <img
        class="logo"
        src="../assets/Orange Minimalist Food Festival Logo.png"
        alt="Planner ahead logo"
      />
    </div>
    <!-- <div id="brandName"> Planner Ahead </div> -->

    <ul class="nav-links">
      <li id="home">
        <router-link v-bind:to="{ name: 'home' }">Home</router-link>
      </li>
      <li id="aboutUs">
        <router-link v-bind:to="{ name: 'about-us' }">About</router-link>
      </li>

      <li id="myRecipes">
        <router-link
          v-bind:to="{
            name: 'recipes',
            params: { userId: this.$store.state.user.id },
          }"
          v-if="$store.state.token != ''"
          >Recipes</router-link
        >
      </li>
      <li id="mealPlans">
        <router-link
          v-bind:to="{
            name: 'mealplans',
            params: { userId: this.$store.state.user.id },
          }"
          v-if="$store.state.token != ''"
          >Meal Plans</router-link
        >
      </li>
      <li id="grocery-list">
        <router-link
          v-bind:to="{
            name: 'grocery-list',
            params: { userId: this.$store.state.user.id },
          }"
          v-if="$store.state.token != ''"
          >Grocery</router-link
        >
      </li>
      <li id="logout">
        <router-link
          v-bind:to="{ name: 'logout' }"
          v-if="$store.state.token != ''"
          >Logout</router-link
        >
      </li>
    </ul>
  </div>
</template>

<script>
export default {};
</script>

<style scoped>
@media screen and (max-width: 450px) {
  #top-bar {
    display: none;
  }
}
@media screen and (max-width: 1024px) {
  #headerNav {
    display: flex;
    justify-content: flex-start;
    align-items: center;
  }
  .nav-links {
    display: flex;
    list-style: none;
    margin: 0;
    padding: 0;
    /* background-color: darkgrey; */
    padding: 10px;
    flex-shrink: 1;
  }

  .nav-links li {
    justify-content: flex-start;
  }
}

#headerNav {
  font-family: "Poppins", sans-serif;
  display: flex;
  justify-content: space-between;
  align-items: center;
  /* background-color: rgb(246, 249, 242); */
    background-color: #fff;
  width: 100%;
  height: 60px;
  padding: 5px 0;
  /* position: fixed; */
      border-bottom: 1px solid rgb(224, 231, 223);

}

.logoImg {
  height: 68px;
  padding: 5px 10px;

}

.logo {
  height: 100%;
  width: auto;
   border-radius: 10px;
}

.nav-links {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
  list-style: none;
  margin: 0;
  padding: 0;
}

.nav-links li {
  margin: 0 10px;
  padding: 5px;

  text-align: center;
}

.nav-links a {
  /* color: black;
  text-decoration: none;
  font-size: 18px;
  font-weight: 600;
  font-family: "Dosis", monospace, sans-serif;
  padding: 10px;
  margin: 0 auto;
  background-color: #89c36f;
  border-radius: 10px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.5); */
  font-family: "Dosis", monospace, sans-serif;
  text-decoration: none;
  color: #39364f;
  line-height: 20px;
  font-weight: 600;
  font-size: 16px;
}
.nav-links a:hover {
  background-color: #e2e2df;
  border-radius: 100px;
  padding: 5px 10px;
  outline: 3px solid transparent;
  font-weight:bold;
}
</style>
