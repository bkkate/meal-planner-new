<template>
  <div class="mealPlans">
    <div class="top-title">
      <h1>My Meal Plans</h1>
      <div class="desc">Build tailored meal plans with your own recipes.</div>
      <div class="desc-2">Let's bring the joy back to cooking!</div>
    </div>
    <section class="add-new-btn">
      <div class="add-meal">
        <router-link :to="{ name: 'add-meal-plan' }">
          Add Meal Plan
        </router-link>
      </div>
    </section>
    <section class="filters">
      <div class="filter-btn" id="current-btn" @click="handleFilter">
        current
      </div>
      <div class="filter-btn" id="previous-btn" @click="handleFilter">
        previous
      </div>
      <div class="filter-btn" id="future-btn" @click="handleFilter">future</div>
    </section>
    <section class="all-plans">
      <div class="current">
        <h2 class="plan-time">Current Plans</h2>
        <div class="current-plans plans">
          <div
            class="plan"
            v-for="mealplan in currentMealPlans"
            v-bind:key="mealplan[0].meal_plan_id"
          >
            <div class="planTitle">
              <h2 class="plan-name">{{ mealplan[0].plan_name }}</h2>
              <!-- <h3> <router-link v-bind:to="{name: 'mealplandetails', params: {mealPlanId: mealplan[0].meal_plan_id}}" >View Details</router-link> </h3> -->
              <div>
                {{ mealplan[0].for_date }} <span>to</span>

                {{ mealplan[mealplan.length - 1].for_date }}
              </div>
              <div>View Details</div>
            </div>
            <div class="buttons">
              <!-- <button @click.prevent="showDetailTable(mealplan[0].meal_plan_id)"> View Details</button> -->

              <router-link
                :to="{
                  name: 'modify-meal-plan',
                  params: { mealPlanId: mealplan[0].meal_plan_id },
                }"
              >
                <i class="bx bx-edit"></i>
              </router-link>
              <div @click.prevent="deletePlan(mealplan[0].meal_plan_id)">
                <i class="bx bxs-trash"> </i>
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
        <div class="future-plans plans">
          <div
            class="plan"
            v-for="mealplan in futureMealPlans"
            v-bind:key="mealplan[0].meal_plan_id"
          >
            <div class="planTitle">
              <h2 class="plan-name">{{ mealplan[0].plan_name }}</h2>
              <!-- <h3> <router-link v-bind:to="{name: 'mealplandetails', params: {mealPlanId: mealplan[0].meal_plan_id}}" >View Details</router-link> </h3> -->
              <div>
                {{ mealplan[0].for_date }}
                <span>to</span>

                {{ mealplan[mealplan.length - 1].for_date }}
              </div>
              <div>View Details</div>
            </div>
            <div class="buttons">
              <!-- <button @click.prevent="showDetailTable(mealplan[0].meal_plan_id)"> View Details</button> -->

              <router-link
                :to="{
                  name: 'modify-meal-plan',
                  params: { mealPlanId: mealplan[0].meal_plan_id },
                }"
              >
                <i class="bx bx-edit"></i>
              </router-link>
              <div @click.prevent="deletePlan(mealplan[0].meal_plan_id)">
                <i class="bx bxs-trash"> </i>
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
        <div class="previous-plans plans">
          <div
            class="plan"
            v-for="mealplan in previousMealPlans"
            v-bind:key="mealplan[0].meal_plan_id"
          >
            <div class="planTitle">
              <h2 class="plan-name">{{ mealplan[0].plan_name }}</h2>
              <!-- <h3> <router-link v-bind:to="{name: 'mealplandetails', params: {mealPlanId: mealplan[0].meal_plan_id}}" >View Details</router-link> </h3> -->
              <div>
                {{ mealplan[0].for_date }}
                <span>to</span>

                {{ mealplan[mealplan.length - 1].for_date }}
              </div>
              <div>View Details</div>
            </div>
            <div class="buttons">
              <!-- <button @click.prevent="showDetailTable(mealplan[0].meal_plan_id)"> View Details</button> -->
              <router-link
                :to="{
                  name: 'modify-meal-plan',
                  params: { mealPlanId: mealplan[0].meal_plan_id },
                }"
              >
                <i class="bx bx-edit"></i>
              </router-link>
              <div @click.prevent="deletePlan(mealplan[0].meal_plan_id)">
                <i class="bx bxs-trash"> </i>
              </div>
            </div>
          </div>

          <meal-plan-details
            v-if="showMealPlan"
            v-bind:meal-plan="mealplan"
          ></meal-plan-details>
        </div>
      </div>
    </section>

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
    handleFilter(event) {
      // button styling
      document.querySelector(".special")?.classList.remove("special");
      event.target.classList.add("special");

      // scrolling to corresponding section
      switch (event.target.id) {
        case "current-btn":
          document
            .querySelector(".current")
            .scrollIntoView({ behavior: "smooth" });
          break;

        case "future-btn":
          document
            .querySelector(".future")
            .scrollIntoView({ behavior: "smooth" });
          break;

        case "previous-btn":
          document
            .querySelector(".previous")
            .scrollIntoView({ behavior: "smooth" });
          break;
      }
    },
  },
};
</script>
<style scoped>
@import url(https://fonts.googleapis.com/css2?family=Dosis&family=Playfair+Display&family=Raleway:wght@300;400&display=swap);
.mealPlans {
  font-family: "Dosis", sans-serif;
  font-weight: 100;
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
  /* color: rgb(163, 126, 45); */
  color: rgb(157, 115, 32);
  margin: 10px 85px 15px 85px;
  /* border-bottom: 1px solid rgb(223, 223, 229); */
  text-align: center;
  font-weight: 300;
  text-shadow: 1px 1px 0px rgba(217, 190, 131, 0.2);
}

.current,
.future,
.previous {
  /* padding: 15px 0 50px 0; */
}

.current .plans {
  background: #f5f5f5;
  background: #f4f0ec;
  border-top: 1px solid rgb(179, 178, 177);
}

.future .plans {
  background: #f8efdb;
  border-top: 1px solid rgb(179, 178, 177);
}

.previous .plans {
  /* background: #ebe7e2; */
  background: #ffffe0;
  border-top: 1px solid rgb(179, 178, 177);
}

h2.plan-name {
  font-weight: 300;
  font-size: 20px;
}

section.filters {
  display: flex;
  justify-content: center;
  gap: 18px;

  margin-bottom: 5px;
}

section.all-plans {
  /* background-color: #f2f3e2; */
  padding-top: 15px;
  padding-bottom: 80px;
  font-family: "Poppins", "Raleway", "Dosis", sans-serif;
  font-weight: 200;
}

.filter-btn,
.add-meal {
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

  line-height: 34px;
  padding: 6px 15px;
}

.filter-btn:hover,
.add-meal:hover {
  background-color: #f3f2ef;
  text-decoration: none;
  transition-duration: 0.1s;
}

.add-new-btn {
  text-align: center;
  margin: 25px;
}
.active {
  background-color: #edeff2;
  box-shadow: rgba(225, 228, 232, 0.2) 0 1px 0 inset;
  transition: none 0s;
}
a {
  text-decoration: none;
  color: black;
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

.plans {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  gap: 20px;
  padding: 50px;
  /* margin: 0 50px; */
}

.plan {
  /* box-sizing: border-box;  */
  width: 35%;
  color: #422800;
  display: flex;
  justify-content: center;
  text-align: center;
  border: 1px solid rgb(202, 201, 198);
  /* background-color: rgb(248, 246, 242);
    background-color: #fbfbf5;*/
  border-radius: 20px;
  margin-bottom: 20px;
  padding: 30px;
}

.tdata {
  padding: 10px;
}

/* .buttons {
  display: flex;
  justify-content: center;
} */

/* .show-details,
.add-meal {
  margin-right: 15px;
  background: #fff;
  backface-visibility: hidden;
  border-radius: 0.375rem;
  border-width: 0.125rem;
  color: #212121;
  cursor: pointer;
  font-size: 1rem;
  font-weight: 700;
  line-height: 1.2;
  padding: 0.5rem;
  position: relative;
  text-align: left;
  margin-bottom: 15px;
  display: block;
} */

.move-to-top {
  border-radius: 50%;
  position: fixed;
  right: 10%;
  bottom: 25%;
}
</style>
