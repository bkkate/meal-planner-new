<template>
  <div class="mealPlans">
    <div class="top-title">
      <h1>My Meal Plans</h1>
      <div class="desc">Build tailored meal plans with your own recipes.</div>
      <div class="desc-2">Let's bring the joy back to cooking!</div>
    </div>
    <section class="filters">
      <div class="filter-btn">current</div>
      <div class="filter-btn">previous</div>
      <div class="filter-btn">future</div>
    </section>
    <div class="current">
      <h2 class="plan-time">Current Plans</h2>
      <div
        class="current-plans"
        v-for="mealplan in currentMealPlans"
        v-bind:key="mealplan[0].meal_plan_id"
      >
      <div class="plan">
        <div class="planTitle">
          <h2>{{ mealplan[0].plan_name }}</h2>
          <!-- <h3> <router-link v-bind:to="{name: 'mealplandetails', params: {mealPlanId: mealplan[0].meal_plan_id}}" >View Details</router-link> </h3> -->
          <p style="font-weight: bold">{{ mealplan[0].for_date }}</p>
          <p>to</p>
          <p style="font-weight: bold">
            {{ mealplan[mealplan.length - 1].for_date }}
          </p>
          <br />
          <div class="buttons">
            <!-- <button @click.prevent="showDetailTable(mealplan[0].meal_plan_id)"> View Details</button> -->
           <i class='bx bxs-trash'></i>
           <button>
              <router-link
                :to="{
                  name: 'modify-meal-plan',
                  params: { mealPlanId: mealplan[0].meal_plan_id },
                }"
              >
                Modify 
              </router-link>
            </button>
            <button @click.prevent="deletePlan(mealplan[0].meal_plan_id)">
              Delete 
            </button>
          </div>
        </div>

      </div>
        
        <meal-plan-details
          v-if="showMealPlan"
          v-bind:meal-plan="mealplan"
        ></meal-plan-details>
      </div>
    </div>

    <div class="future">
      <h2 class="plan-time">Future Plans</h2>
      <div
        class="future-plans"
        v-for="mealplan in futureMealPlans"
        v-bind:key="mealplan[0].meal_plan_id"
      >
        <div class="plan">
          <div class="planTitle">
            <h2>{{ mealplan[0].plan_name }}</h2>
            <!-- <h3> <router-link v-bind:to="{name: 'mealplandetails', params: {mealPlanId: mealplan[0].meal_plan_id}}" >View Details</router-link> </h3> -->
            <p style="font-weight: bold">{{ mealplan[0].for_date }}</p>
            <p>to</p>
            <p style="font-weight: bold">
              {{ mealplan[mealplan.length - 1].for_date }}
            </p>
            <br />
            <div class="buttons">
              <!-- <button @click.prevent="showDetailTable(mealplan[0].meal_plan_id)"> View Details</button> -->
              <button>
                <router-link
                  :to="{
                    name: 'modify-meal-plan',
                    params: { mealPlanId: mealplan[0].meal_plan_id },
                  }"
                >
                  Modify Plan
                </router-link>
              </button>
              <button @click.prevent="deletePlan(mealplan[0].meal_plan_id)">
                Delete Plan
              </button>
            </div>
          </div>
        </div>

        <meal-plan-details
          v-if="showMealPlan"
          v-bind:meal-plan="mealplan"
        ></meal-plan-details>
      </div>
    </div>

    <div class="previous">
      <h2 class="plan-time">Previous Plans</h2>
      <div
        class="previous-plans"
        v-for="mealplan in previousMealPlans"
        v-bind:key="mealplan[0].meal_plan_id"
      >
        <div class="plan">
          <div class="planTitle">
            <h2>{{ mealplan[0].plan_name }}</h2>
            <!-- <h3> <router-link v-bind:to="{name: 'mealplandetails', params: {mealPlanId: mealplan[0].meal_plan_id}}" >View Details</router-link> </h3> -->
            <p style="font-weight: bold">{{ mealplan[0].for_date }}</p>
            <p>to</p>
            <p style="font-weight: bold">
              {{ mealplan[mealplan.length - 1].for_date }}
            </p>
            <br />
            <div class="buttons">
              <!-- <button @click.prevent="showDetailTable(mealplan[0].meal_plan_id)"> View Details</button> -->
              <button>
                <router-link
                  :to="{
                    name: 'modify-meal-plan',
                    params: { mealPlanId: mealplan[0].meal_plan_id },
                  }"
                >
                  Modify Plan
                </router-link>
              </button>
              <button @click.prevent="deletePlan(mealplan[0].meal_plan_id)">
                Delete Plan
              </button>
            </div>
          </div>
        </div>

        <meal-plan-details
          v-if="showMealPlan"
          v-bind:meal-plan="mealplan"
        ></meal-plan-details>
      </div>
    </div>

    <!-- <div class="side-btn">
      <button class="show-details" v-on:click="showMealPlan = !showMealPlan">
        {{ showMealPlan ? "Hide" : "Show" }} Details
      </button>
      <button class="add-meal">
        <router-link :to="{ name: 'add-meal-plan' }">
          Add Meal Plan
        </router-link>
      </button>
    </div> -->
  </div>
</template>
<script>
// import { computed } from 'vue';
import mealPlanService from "../services/MealPlanService.js";
import recipeService from "../services/RecipeService.js";
import MealPlanDetails from "./MealPlanDetails.vue";

export default {
  name: "my-meal-plans",
  components: {
    MealPlanDetails,
  },
  data() {
    return {
      showMealPlan: false,
      mealPlans: [],
      currentMealPlans: [],
      previousMealPlans: [],
      futureMealPlans: [],
      viewDetails: {},
      test: false,
    };
  },

  created() {
    //  this.populateMealplanList();
    this.populatePlansByDate();

    recipeService.getRecipes(this.$route.params.userId).then((response) => {
      this.$store.commit("SET_RECIPE", response.data.sort());
    });
  },
  computed: {
    viewDetailSection() {
      return this.viewDetails;
    },
    viewMealPlans() {
      return this.mealPlans;
    },
  },

  methods: {
    showDetailTable(mealPlanId) {
      this.viewDetails[mealPlanId] = !this.viewDetails[mealPlanId];
      console.log(this.viewDetails[mealPlanId]);
    },
    getRecipeName(currentRecipeId) {
      let recipeObj = this.$store.state.recipes.find(
        (recipe) => recipe.recipeId === currentRecipeId
      );
      return recipeObj.recipe_name;
    },

    populateMealplanList() {
      mealPlanService
        .listAllMealPlans(this.$route.params.userId)
        .then((response) => {
          this.mealPlans = response.data;
          this.mealPlans.forEach((mealPlan) => {
            this.viewDetails[mealPlan[0].plan_name] = false;
          });
        });
    },

    populatePlansByDate() {
      mealPlanService
        .listAllMealPlans(this.$route.params.userId)
        .then((response) => {
          this.mealPlans = response.data;
          this.mealPlans.forEach((mealPlan) => {
            this.viewDetails[mealPlan[0].plan_name] = false;

            let toDate = new Date(mealPlan[mealPlan.length - 1].for_date);
            let fromDate = new Date(mealPlan[0].for_date);
            let currentDate = new Date();

            if (
              toDate.getTime() >= currentDate.getTime() &&
              fromDate.getTime() <= currentDate.getTime()
            ) {
              this.currentMealPlans.push(mealPlan);
            } else if (
              toDate.getTime() > currentDate.getTime() &&
              fromDate.getTime() > currentDate.getTime()
            ) {
              this.futureMealPlans.push(mealPlan);
            } else {
              this.previousMealPlans.push(mealPlan);
            }
          });
          console.log(this.currentMealPlans);
          console.log(this.futureMealPlans);
          console.log(this.previousMealPlans);
        });
    },
    convertMealTypeToWord(mealType) {
      if (mealType === 1) {
        return "Breakfast";
      } else if (mealType === 2) {
        return "Lunch";
      } else if (mealType === 3) {
        return "Dinner";
      } else if (mealType === 4) {
        return "Snack";
      } else {
        return "Appetizer";
      }
    },
    deletePlan(mealPlanId) {
      if (confirm("You can't undo the deletion. Would you like to proceed?")) {
        mealPlanService.deleteMealPlan(this.$route.params.userId, mealPlanId);
        // this.populateMealplanList();
        location.reload();
      }
    },
  },
};
</script>
<style scoped>
@import url(https://fonts.googleapis.com/css2?family=Dosis&family=Playfair+Display&family=Raleway:wght@300;400&display=swap);
.mealPlans {
  font-family:  "Raleway", "Dosis", sans-serif;
}

h1 {
  color: #422800;

  grid-area: "title";
  text-align: center;
  font-size: 36px;
  align-content: center;
  padding: 100px 100px 40px 0px;
}

h2.plan-time {
    color:rgb(124 111 80);
  margin: 50px 85px 15px 85px;
  border-bottom: 1px solid rgb(223, 223, 229);
}

section.filters {
  display: flex;
  justify-content: center;
  gap: 18px;
  margin: 30px;
  margin-bottom: 5px;
}

.filter-btn {
  cursor: pointer;
  border: 1px solid rgba(8, 9, 9, 0.15);
  appearance: none;
  background: transparent;
  border: 1px solid rgba(8, 9, 9, 0.15);
  border-radius: 300px;
  box-shadow: rgba(27, 31, 35, 0.04) 0 1px 0,
    rgba(255, 255, 255, 0.25) 0 1px 0 inset;
  color: #000000;
  cursor: pointer;
  display: inline-block;
  font-size: 15px;
  font-weight: 500;
  line-height: 34px;
  padding: 6px 15px;
}

a {
  text-decoration: none;
  color: rgb(8, 84, 59);
}
p {
  margin: 0.5em;
}

.desc {
  padding: 0px 100px 10px 100px;
  font-size: 20px;
}
.desc-2 {
  padding: 0px 100px 10px 100px;
  font-size: 18px;
}

.top-title {
  background: url(../assets/veggies.jpg) no-repeat center center fixed;
  /* background: url("https://d3i4yxtzktqr9n.cloudfront.net/web-eats-v2/7981ed1c9d3bc9f4.svg") no-repeat center center fixed; */
  -webkit-background-size: cover;
  -moz-background-size: cover;
  -o-background-size: cover;
  background-size: cover;
  padding-bottom: 160px;
  height: 150px;
}

.plan {
  width: 80%;
  margin: 0 auto;
    color: #422800;
  background-color: #fbfbf6;
    border: 1px solid #ebebe6;
    border-radius: 20px;
    margin-bottom: 20px;
}

.tdata {
  padding: 10px;
}

.buttons {
  display: flex;
  justify-content: space-evenly;
}

.show-details,
.add-meal {
  margin-right: 15px;
  background: #fff;
  backface-visibility: hidden;
  border-radius: 0.375rem;
  border-width: 0.125rem;
  color: #212121;
  cursor: pointer;
  font-family: "Dosis", monospace, sans-serif;
  font-size: 1rem;
  font-weight: 700;
  line-height: 1.2;
  padding: 0.5rem;
  position: relative;
  text-align: left;
  margin-bottom: 15px;
  display: block;
}

.move-to-top {
  border-radius: 50%;
  position: fixed;
  right: 10%;
  bottom: 25%;
}
</style>
