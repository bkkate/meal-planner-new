<template>
  <!-- <div class="footer"> -->
  <!-- <div class="container1"> -->
<div class ="container1">
<div class="footer">
  <div class="container2">
    <div class="aboutus">
      <h2 class="header">About Us</h2>
      <div class="aboutUsText">
      <p>"Effortlessly plan, cook, and enjoy deliciously healthy meals with Meal Planner - your one-stop solution for mealtime bliss!"</p>
      </div>
      <ul class="social-media">
        <li class="sm_list"><a href="#" class="sm_a"><i class="fa fa-facebook" aria-hidden="true"></i></a></li>
        <li class="sm_list"><a href="#" class="sm_a"><i class="fa fa-twitter" aria-hidden="true"></i></a></li>
        <li class="sm_list"><a href="#" class="sm_a"><i class="fa-brands fa-instagram"></i></a></li>
        <li class="sm_list"><a href="#" class="sm_a"><i class="fa-brands fa-linkedin"></i></a></li>
      </ul>
    </div>
    <div class="quicklinks">
      <h2 class="header">Quick Links</h2>
      <ul>
        <li id="aboutUs"><router-link v-bind:to="{ name: 'about-us' }">About Us</router-link></li>
        <li id="mealPlans"><router-link v-bind:to="{ name: 'mealplans', params: {userId: this.$store.state.user.id}}">Meal Plans</router-link></li>
        <li id="myRecipes"> <router-link v-bind:to="{ name: 'recipes', params: {userId: this.$store.state.user.id} }">My Recipes</router-link> </li>
        <li id="grocery-list"><router-link v-bind:to="{ name: 'grocery-list', params: {userId: this.$store.state.user.id}}">Grocery List</router-link></li>
      </ul>
    </div>
    <div class ="contact">
      <h2 class="header">Contact Us</h2>
      <ul class="info">
        <li>
          <span><i class="fa-solid fa-map"></i></span>
          <p>Coding Street
            <br>
            Code City, TX 77777
          </p>
        </li>
        <li>
          <span><i class="fa-solid fa-phone"></i></span>
            <p><a href="#">+1(777)-777-7777</a>
           </p>
           </li>
            <li>
              <span><i class="fa-solid fa-envelope"></i></span>
                 <p><a href="#">contact@plannerahead.com</a>
              </p>
            </li>
            </ul>    
   </div>
  </div>
    <div class="copyright">
    <p>Copyright © 2023 Planner Ahead</p>
  </div>
</div>
</div>
</template>
<script>
export default {
    name: 'footer',
    components: {
  }
}

</script>
<style>
   div.footer {
  font-family: 'Dosis', sans-serif;
  margin: 0;
  padding: 2px 15px;
  box-sizing: border-box;
}
 .container1 {
    border-top: 1px solid rgb(224, 231, 223);
  display: grid;
  justify-content: end;
  align-content: end;
  max-height: 10%;
  text-align:center;
  
}
.footer {
  position: relative;
  width: 100%;
  height: auto;

  background-color: #fafafa;
  justify-items: center;
}

.container2 {
  width: 100%;
  display: grid;
  grid-template-columns: 2fr 1fr 2fr;
  grid-gap: 40px;

}

.header {
  position: relative;
  color:black;
  font-weight: 500;
  margin-bottom: 15px;

  padding-top: 30px;
  text-align: center;
  
}
/* .header::before {
  content: '';
  position: absolute;
  bottom: -5px;
  left: 0;
  width: 50px;
  height: 2px;
  background: #FFEB3B;
  margin-left: 35px;
  
} */
.aboutUsText {
  color: black;
  /* padding-top: 10px; */
}
.social-media {
  margin-top: 20px;
  display: flex;
  justify-content: center;
  gap: 10px;
  padding-bottom: 25px;
}
.sm_list {
  list-style: none;
}
.sm_a {
  display: inline-block;
  width: 48px;
  height: 48px;
  background: #ceecc1;
  display: grid;
  align-content: center;
  justify-content: center;
  text-decoration: none;
  border-radius: 4px;
   color: black;
  font-size: 20px;
}
.sm_a:hover {
  background: #dde1f0
}
/* .sm_a .fa {
  color: black;
  font-size: 20px;
} */
.quicklinks {
  position: flex;
    text-align:center;
}
.quicklinks li {
  list-style: none;

}
.quicklinks li a {
  color:black;
  text-decoration: none;
  margin-bottom: 10px;
  display: inline-block;
}
.quicklinks li a:hover {
 font-weight: 600;
}
.info {
  position: relative;
}
.info li {
  display: flex;
  gap: 15px;
  
}

.info li span:nth-child(1) {
  color:black;
  font-size: 20px;
  grid-area: images;
  
}
.info li span {
  color:black;
  
 
}
.info li p {
  grid-area: word;
  align-items: start;
  margin-top: 0;
}
.info a {
  color:black;
  text-decoration: none;
}

.copyright {
  width: 100%;
  background: #c6d7d3;
  padding: 2px 0;
  text-align: center;
  color: black;
}
@media (max-width: 991px)
{
  footer
  {
    padding: 40px;
  }
  footer .container1 {
    grid-template-columns: repeat(2,1fr);
  }
}
@media (max-width: 769px)
{
  footer .container
  {
    grid-template-columns: repeat(2,1fr);
  }
}
</style>