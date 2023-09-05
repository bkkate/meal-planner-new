<template>
  <div class="home">
    <section class="welcome">
      <div class="left-welcome">
        <!-- <div class="text-section"></div> -->
      </div>
      <div class="right-current">
        <div class="plan-box">
          <h2 class="current">Current plans</h2>
          <div class="current-mealplan" v-if="currentPlanPresent">
            <div
              class="is-current"
              v-for="(meal, index) in mealPlans"
              v-bind:key="index"
            >
              <div @click="moveToMealPlan">
                <h3 class="current-name">{{ meal[0].plan_name }}</h3>
                <p class="date-range">
                  <span>{{ meal[0].for_date }} </span> to
                  <span>{{ meal[meal.length - 1].for_date }}</span>
                </p>
              </div>
            </div>
            <div class="add-more">
              <router-link :to="{ name: 'add-meal-plan' }"
                >Add more meal plans</router-link
              >
            </div>
          </div>
          <div class="no-current-mealplan" v-else>
            <p>You don't have any current plan.</p>
            <div class="add-more">
              <router-link :to="{ name: 'add-meal-plan' }"
                >Add new meal plans</router-link
              >
            </div>
          </div>
        </div>
      </div>
    </section>

    <section class="suggested">
      <div class="recipe-container">
        <div class="recipe-title">
          <h2>Recipe of the Day
          </h2>
          <div class="recipe-title-meta">
            suggested from your personalized recipe list
          </div>
          <div class="view-more"> <router-link
            v-bind:to="{
            name: 'recipes',
            params: { userId: this.$store.state.user.id },
          }"
            >
            view more <i class='bx bx-right-arrow-alt'></i>
            </router-link>
          </div>
          </div>
        <div class="recipe">
          <router-link
            :to="{
              name: 'recipesId',
              params: { recipeId: randomRecipe.recipeId },
            }"
            ><div><div class="food-img">
            <img
              :src="getRecipeId(randomRecipe.recipeId)"
              alt="first imag"
              class="foodPic"
            />
          </div>
             <h3 class="recipe-name">{{ randomRecipe.recipe_name }}</h3> </div></router-link
          >
          
        </div>
      </div>
    </section>
  </div>
</template>

<script>
import RecipeService from "../services/RecipeService";
import MealPlanService from "../services/MealPlanService";

export default {
  name: "home",
  data() {
    return {
      recipe: [],
      randomRecipe: {},
      mealPlans: [],
      dateRanges: [],
      currentMealPlan: [],
      isHappeningNow: true,
      // thePlan: null,
      // dates: [],
      currentPlanExists: false,
    };
  },

  created() {
    RecipeService.getRecipes(this.$store.state.user.id).then((response) => {
      this.recipe = response.data;
      this.randomized();
    });

    MealPlanService.listAllMealPlans(this.$store.state.user.id).then(
      (response) => {
        response.data.forEach((plan) => {
          let toDate = new Date(plan[plan.length - 1].for_date);
          let fromDate = new Date(plan[0].for_date);
          let currentDate = new Date();
          if (
            toDate.getTime() >= currentDate.getTime() &&
            fromDate.getTime() <= currentDate.getTime()
          ) {
            this.mealPlans.push(plan);
          }
        });

        this.currentPlanExists = this.currentPlanPresent();
      }
    );

    this.dateRanges.push();
  },

  methods: {
    randomized() {
      const chosenNumber = Math.floor(Math.random() * this.recipe.length);
      this.randomRecipe = this.recipe[chosenNumber];
    },
    currentPlanPresent() {
      return this.mealPlans.length;
    },
    moveToMealPlan() {
      this.$router.push({
        name: "mealplans",
        params: { userId: this.$store.state.user.id },
      });
    },
    getRecipeId(recipeId) {
      let matchingImage = this.$store.state.images.find(
        (recipesimg) => recipesimg.id === recipeId
      );
      if (matchingImage != undefined) {
        return matchingImage.path;
      } else {
        return this.randomItem(this.images);
      }
    },
  },
};
</script>

<style scoped>
@import url(https://fonts.googleapis.com/css2?family=Dosis&family=Overpass&family=Playfair+Display&family=Raleway:wght@300;400&display=swap);

.home {
  font-family: "Overpass", "Raleway", "Dosis", sans-serif;
  margin: 0;
}

section.welcome {
  height: 90vh;
  background: url(../assets/white-drawing.jpg) no-repeat center center;
  background-size: contain;
  background-size: cover;
  display: flex;
}

.left-welcome {
  width: 35%;
}
.right-current {
  width: 65%;
}
/* .left-welcome .text-section {
  padding: 40px 80px 0px 130px;
  font-size: 2em;
  color: #5e5c61;
} */

.right-current .plan-box {
  position: relative;
  top: 15%;
}

.current-mealplan {
  padding-left: 30px;
}
.is-current {
  text-align: center;
  width: 80%;
  height: 20%;
  background-color: #fff;
  border-radius: 20px;
  border: 0.5px solid rgb(229, 224, 224);
  padding: 20px;
  font-family: Arial;
  margin: 0 auto;
}

.is-current:hover {
  cursor: pointer;
  box-shadow: 0px 2px 8px rgba(30, 10, 60, 0.06),
    0px 4px 12px rgba(30, 10, 60, 0.08);
  background: rgb(250, 249, 246);
  transition: 0.3s;
  /* border-left: 0.5rem solid #11a56a; */
}

h2.current {
  color: #58555b;
  font-size: 2.5em;
  padding-bottom: 80px;
  text-align: center;
}
h3.current-name {
  font-size: 1.4em;
  padding-bottom: 10px;
}

.add-more {
  text-align: center;
  width: 240px;
  height: 32px;
  background: #646769;
  border-color: #fff;
  text-transform: uppercase;
  padding: 15px 5px 5px 5px;
  margin: 0 auto;
  margin-top: 60px;
}

.add-more:hover {
  background: #adabab;
  color: black;
}
.add-more a {
  font-size: 18px;
  color: #fff;
}

.add-more a:hover {
  text-decoration: none;
}

section.suggested {
  /* background: url(../assets/recipe-of-the-day.jpg) no-repeat center center;
  -webkit-background-size: cover;
  -moz-background-size: cover;
  -o-background-size: cover;
  background-size: cover; */
  height: 95vh;
  /* background: rgb(243, 251, 230); */
  background:rgb(250, 246, 222);
}

.recipe-container {
  display:flex;
  justify-content:space-around;
  align-items:center;
  height:100%;
  margin: 0 40px;
}

.recipe-title{
  font-size:25px;
}

.recipe-title-meta{
  font-size: 20px;
  color: #585757;
    padding-top:15px;
}

.view-more{
  font-size: 20px;
  padding-top: 20px;
  color: #1c1a1a;
}
.view-more:hover{
   text-decoration:none;
}
.food-img img {
  width: 450px;
  height: 350px;
  background-color: white;
  margin: 0;
}

a {
  text-decoration: none;
  color:black;
}

i {
  font-size:20x;   
}

h3.recipe-name{
  text-align:center;
  font-size:23px;
  font-family: "Dosis", sans-serif;
  color: #5a5858;
}
/* a:hover {
  text-decoration: underline;
} */
</style>
