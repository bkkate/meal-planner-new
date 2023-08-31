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
      <div class="recipe">
        <router-link
          :to="{
            name: 'recipesId',
            params: { recipeId: randomRecipe.recipeId },
          }"
          ><h3>{{ randomRecipe.recipe_name }}</h3></router-link
        >
        <div class="food-img">
          <img
            :src="getRecipeId(randomRecipe.recipeId)"
            alt="first imag"
            class="foodPic"
          />
        </div>
      </div>
    </section>
    <!-- <div class="container2">
      <h2>Current Plans</h2>
      <h2>Suggested Recipe</h2>
    </div>
    <div class="container1">
      <section class="current-mealplan" v-if="currentPlanPresent">
        <div
          class="is-current"
          v-for="(meal, index) in mealPlans"
          v-bind:key="index"
        >
          <div @click="moveToMealPlan">
            <h2 class="current-name">{{ meal[0].plan_name }}</h2>
            <p class="date-range">
              <span>{{ meal[0].for_date }} </span> to
              <span>{{ meal[meal.length - 1].for_date }}</span>
            </p>
          </div>
        </div>
        <div class="add-more">
          <router-link :to="{ name: 'add-meal-plan' }"
            >Add more meal plans!</router-link
          >
        </div>
      </section>

      <section class="no-current-mealplan" v-else>
        <p>You don't have any current plan.</p>
        <div class="add-more">
          <router-link :to="{ name: 'add-meal-plan' }"
            >Add new meal plans!</router-link
          >
        </div>
      </section>

      <section class="random-recipe">
        <router-link
          :to="{
            name: 'recipesId',
            params: { recipeId: randomRecipe.recipeId },
          }"
          ><h3>{{ randomRecipe.recipe_name }}</h3></router-link
        >
        <div class="food-img">
          <img
            :src="getRecipeId(randomRecipe.recipeId)"
            alt="first imag"
            class="foodPic"
          />
        </div>
      </section>
    </div> -->
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
  width:240px;
  height:32px;
  background: #545759;
  border-color: #fff;
  text-transform: uppercase;
  padding:15px 5px 5px 5px;
  margin:0 auto;
  margin-top: 60px;

}

.add-more:hover{
  background:#cbcaca;
  color:black;
}
.add-more a {
  font-size: 18px;
  color: #fff;
}

.add-more a:hover {
  text-decoration: none;
}

section.suggested {
  /* background: url(../assets/plum-plate.jpg) no-repeat center center; */
  /* background-size: contain;    */
  background-size: cover;
  height: 95vh;
}

.recipe {
  /* text-align:end;
    padding-top: 130px;
    padding-right:150px; */
}

.food-img img {
  width: 300px;
  height: 250px;
  background-color: white;
  margin: 0;
  border-radius: 70px;
}
/* .home {
  padding: 2.5rem;
  font-family: "Dosis", monospace, sans-serif;
  -webkit-background-size: cover;
  -moz-background-size: cover;
  -o-background-size: cover;
  background-size: cover;
  padding-bottom: 100px;
}
.container1 {
  display: flex;
  justify-content: space-around;
  flex-wrap: wrap;
  border-radius: 10px;
  padding: 2%;
}
section:hover {
  border: 1px solid #dcf3d9;
  border-radius: 9px;
  box-shadow: 0 0 4px 1px rgba(213, 235, 222, 0.925);
}
.container2 {
  display: flex;
  justify-content: space-around;
}

h2,
h3 {
  color: black;
  text-align: center;
  margin-bottom: 0;
}

h1 {
  color: rgb(11, 24, 13);
  text-align: center;
  padding-top: 30px;
  padding-bottom: 50px;
}

body {
  font: Arial, sans-serif;
}

.recipes {
  margin: 0 auto;
}

section {
  background-color: #ebf2ef;
  min-width: 45%;
  border-radius: 10px;
  border: dotted 2px;
}

.food-img {
  text-align: center;
  padding: 5%;
}

img {
  border-radius: 50px;
  max-width: 300px;
  max-height: 250px;
}

.is-current {
  display: flex;
  border: 2px solid #333333;
  max-width: 60%;
  justify-content: center;
  margin: 0 auto;
  margin-top: 15px;
  font-size: 0.9rem;
  border-radius: 10px;
  text-transform: uppercase;
  letter-spacing: 0.3ch;
  background: #ffffff;
  margin-top: 30px;
}

.is-current:hover {
  padding-left: 0.5rem;
  border-left: 0.5rem solid #11a56a;
}

.current-mealplan,
.no-current-mealplan {
  text-align: center;
  font-size: 1.5em;
  margin: 0px;
}

p.date-range {
  text-align: center;
}

.add-more {
  text-align: center;
  max-width: 100%;
  font-size: 0.8em;
  margin: 30;
  margin-top: 20px;
  margin-bottom: 20px;
} */

a {
  text-decoration: none;
}

a:hover {
  text-decoration: underline;
}
</style>
