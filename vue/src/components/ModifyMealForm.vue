<template>
  <div class="background">
    <div class="addedTitleDisplay" v-if="!planNameAdded">
      <h1 class="meal-name" @click="modifyName">{{ mealPlanName }}</h1>
      <!-- <img
        src="../assets/pencil-edit-icon.jpg"
        @click="modifyName"
        class="pencil-icon"
        height="30"
        width="30"
      /> -->
    </div>
    <div class="weekly-plan-name">
      <div class="title-add-section" v-if="planNameAdded">
        <input
          type="text"
          class="meal-plan-name"
          id="meal-plan-name"
          v-model="mealPlanName"
          @keyup.enter="addName"
        />
        <!-- <button class="addBtn" @click.prevent="addName">Add</button> -->
      </div>
    </div>
    <div class="wrapper">
      <div class="buttons">
        <button
          class="addToPlanBtn"
          :class="{ special: btnHighlight }"
          @click.prevent="addNewMeal"
        >
          Add New Meal
        </button>
        <button class="addToPlanBtn" @click.prevent="addMealToDB">
          Save All Changes
        </button>
      </div>
      <form class="mealForm" v-show="showAddOptions">
        <div class="options">
          <div class="dateOptions">
            <label for="date">Select Date </label>
            <input
              type="date"
              id="date"
              name="date"
              v-model="mealPlan.for_date"
              class="form-control"
            />
          </div>

          <div class="mealTypeOptions">
            <label for="mealType">Select Meal: </label>
            <select
              name="mealType"
              id="mealType"
              v-model="mealPlan.meal_type"
              class="form-control"
            >
              <option value="1">Breakfast</option>
              <option value="2">Lunch</option>
              <option value="3">Dinner</option>
              <option value="4">Snack</option>
              <option value="5">Appetizer</option>
            </select>
          </div>

          <div class="recipesOptions">
            <label for="recipe">Select Recipe: </label>
            <select
              name="recipe"
              id="recipe"
              v-model="currentSelectedRecipe"
              class="form-control"
            >
              <option
                v-for="recipe in this.$store.state.recipes"
                v-bind:key="recipe.recipe_id"
                :value="recipe.recipe_name"
              >
                {{ recipe.recipe_name }}
              </option>
            </select>
          </div>
          <button
            class="form-btn editBtn"
            v-if="!addingNewMeal"
            @click.prevent="addChangeToPlan(currentIdx)"
          >
            Edit
          </button>
          <button
            class="form-btn editBtn"
            v-else
            @click.prevent="addNewMealToPlan"
          >
            Add
          </button>
        </div>
      </form>

      <section>
        <div class="body">
          <div
            class="row"
            v-for="(plan, index) in displayModifiedCombos"
            v-bind:key="index"
          >
            <div class="planContainer">
              <div class="data-container">
                <div class="tdata placehold column" style="color: #ecf2f0">
                  <img
                    src="../assets/pencil-edit-icon.jpg"
                    height="30"
                    width="30"
                    class="pencil-icon"
                    @click="editAndDisplay(index)"
                  />
                  <!-- <i class='bx bx-x x-icon'></i> -->
                </div>
                <div class="date-meal column">
                  <div class="tdata">{{ plan.for_date }}</div>
                  <div class="tdata">
                    {{ convertMealTypeToWord(plan.meal_type) }}
                  </div>
                </div>
                <div class="recipe-name column">
                  {{ getRecipeName(plan.recipe_id) }}
                </div>
              </div>
              <form class="mealForm edit-form" v-if="editRow === index">
                <div class="options">
                  <div class="dateOptions">
                    <label for="date">Select Date: </label>
                    <input
                      type="date"
                      id="date"
                      name="date"
                      v-model="mealPlan.for_date"
                      class="form-control"
                    />
                  </div>

                  <div class="mealTypeOptions">
                    <label for="mealType">Select Meal: </label>
                    <select
                      name="mealType"
                      id="mealType"
                      v-model="mealPlan.meal_type"
                      class="form-control"
                    >
                      <option value="1">Breakfast</option>
                      <option value="2">Lunch</option>
                      <option value="3">Dinner</option>
                      <option value="4">Snack</option>
                      <option value="5">Appetizer</option>
                    </select>
                  </div>

                  <div class="recipesOptions">
                    <label for="recipe">Select Recipe: </label>
                    <select
                      name="recipe"
                      id="recipe"
                      v-model="currentSelectedRecipe"
                      class="form-control"
                    >
                      <option
                        v-for="recipe in recipes"
                        v-bind:key="recipe.recipe_id"
                        :value="recipe.recipe_name"
                      >
                        {{ recipe.recipe_name }}
                      </option>
                    </select>
                  </div>
                  <button
                    class="form-btn editBtn"
                    v-if="!addingNewMeal"
                    @click.prevent="addChangeToPlan(currentIdx)"
                  >
                    Edit
                  </button>
                  <button
                    class="form-btn editBtn"
                    v-else
                    @click.prevent="addNewMealToPlan"
                  >
                    Add
                  </button>
                </div>
              </form>
            </div>
          </div>
        </div>
      </section>
    </div>
  </div>
</template>

<script>
import recipeService from "../services/RecipeService.js";
import mealPlanService from "../services/MealPlanService";

export default {
  name: "modify-meal-plan",
  data() {
    return {
      recipes: [],
      btnHighlight: false,
      planNameAdded: false,
      showAddOptions: false,
      editRow: -1,
      mealPlanName: "",
      currentSelectedRecipe: "",
      currentIdx: 0,
      listOfPlans: [],
      listOfNewlyAddedPlans: [],
      listOfTotalPlans: [],
      addingNewMeal: false,
      mealPlan: {
        plan_name: "",
        // get the recipe Id based on the recipe name when user adds the combo
        recipe_id: 0,
        for_date: "",
        // will have to convert it to a number. It stores as a string when user selects meal type
        meal_type: 0,
      },
    };
  },

  computed: {
    currentRecipeId() {
      let recipeObj = this.$store.state.recipes.find(
        (recipe) => recipe.recipe_name === this.currentSelectedRecipe
      );
      return recipeObj.recipeId;
    },
    displayModifiedCombos() {
      return this.listOfTotalPlans;
    },
  },

  created() {
    recipeService.getRecipes(this.$store.state.user.id).then((response) => {
      this.$store.commit("SET_RECIPE", response.data.sort());
      this.recipes = response.data.sort();
    });

    this.getListOfMealPlans();
  },

  methods: {
    getListOfMealPlans() {
      mealPlanService
        .getMealPlanById(this.$route.params.mealPlanId)
        .then((response) => {
          this.listOfPlans = response.data;
          this.listOfTotalPlans = response.data;
          this.mealPlanName = this.listOfPlans[0].plan_name;
        });
    },
    clear() {
      this.mealPlan = {
        plan_name: "",
        recipe_id: 0,
        for_date: "",
        meal_type: 0,
      };

      this.currentSelectedRecipe = "";
    },
    addName() {
      this.planNameAdded = false;
    },
    modifyName() {
      this.planNameAdded = true;
    },

    getRecipeName(currentRecipeId) {
      let recipeObj = this.$store.state.recipes.find(
        (recipe) => recipe.recipeId === currentRecipeId
      );
      return recipeObj.recipe_name;
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

    addMealToDB() {
      this.listOfPlans.forEach((plan) => {
        plan.plan_name = this.mealPlanName;
      });

      console.log(this.listOfPlans);
      console.log(this.listOfNewlyAddedPlans);
      mealPlanService.updateMealPlan(
        this.$route.params.mealPlanId,
        this.listOfPlans
      );
      mealPlanService.addMoreMealsToExistingPlan(
        this.listOfNewlyAddedPlans,
        this.$store.state.user.id
      );
      this.clear();
      this.$router.push({
        name: "mealplans",
        params: { userId: this.$store.state.user.id },
      });
    },

    editAndDisplay(index) {
      console.log(index);
      this.currentIdx = index;
      this.editRow = index;
    },

    addNewMeal() {
      this.showAddOptions = !this.showAddOptions;
      this.addingNewMeal = !this.addingNewMeal;

      this.btnHighlight = !this.btnHighlight;
    },

    addNewMealToPlan() {
      this.mealPlan.plan_name = this.mealPlanName;
      this.mealPlan.recipe_id = this.currentRecipeId;
      this.mealPlan.meal_type = parseInt(this.mealPlan.meal_type);
      this.mealPlan.meal_plan_id = this.$route.params.mealPlanId;
      this.listOfNewlyAddedPlans.push(this.mealPlan);
      this.listOfTotalPlans.push(this.mealPlan);
      this.clear();
      this.addingNewMeal = !this.addingNewMeal;
      this.showOptions = !this.showOptions;
    },

    addChangeToPlan(index) {
      this.listOfPlans[index].for_date = this.mealPlan.for_date;
      this.listOfPlans[index].meal_type = parseInt(this.mealPlan.meal_type);
      this.listOfPlans[index].recipe_id = this.currentRecipeId;
      this.showOptions = !this.showOptions;
      this.clear();
      this.editRow=-1;
    },
  },
};
</script>

<style scoped>
.background {
  font-family: "Raleway", "Dosis", monospace, sans-serif;
  background: url("../assets/dumplings-left.jpeg");
  background-position: center;
  background-repeat: no-repeat;
  background-size: cover;
  display: flex;
  justify-content: center;
  min-height: 80vh;
  /* background-color: #FFFACD; */
}
.wrapper {
  padding: 60px 0 60px 15px;
  margin: 0;
}

.buttons {
  display: flex;
  justify-content: center;
  gap: 30px;
  padding-bottom: 50px;
}

.addToPlanBtn {
  cursor: pointer;
  font: "Raleway", "Dosis", sans-serif;
  border: 1px solid rgba(8, 9, 9, 0.15);
  appearance: none;
  background: #ededea;
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

.addToPlanBtn:hover {
  background-color: #fbfbf9;
}

.special {
  background-color: rgb(247, 242, 209);
  color: rgb(32, 31, 33);
}

.special:hover {
  background-color: rgb(247, 242, 209);
  color: rgb(32, 31, 33);
}

/* 
.addNewMealBtn {
  margin-top: 1em;
} */

h1.meal-name {
  display: inline-block;
  margin-right: auto;
}
.addedTitleDisplay {
  padding: 180px 40px 180px 0px;
}

input.meal-plan-name {
  width: 200px;
  height: 50px;
  border: 2px solid var(--grey-light-ui-200, #eeedf2);
  font-family: "Raleway", "Dosis", sans-serif;
  font-size: 15px;
  border-radius: 360px;
  margin: 0 40px;
  padding-left: 30px;
}

input.meal-plan-name:focus {
  outline: none;
}

.weekly-plan-name {
  text-align: center;
  padding: 180px 0;
  /* background-color: rgba(255, 239, 14, 0.411); */
  border-radius: 25px 25px 0 0;
}

.options {
  display: flex;
  justify-content: center;
  align-items: center;
  /* background-color:rgb(241, 249, 253); */
  margin: 0 auto;
}

.planContainer .data-container {
  display: flex;
  /* justify-content: center; */
  text-align: center;
  align-items: center;
  padding: 15px;
  gap: 80px;
}
.column {
  max-width: 200px;
}

.tdata {
  padding: 5px 0;
}

section {
  border-radius: 25px;
  text-align: center;
  margin: 0 auto;
  width: 100%;
}

.tdata.placehold {
 display:flex;
 justify-content: flex-start;
 gap:10px;
}

div.row {
  display: flex;
  flex-direction: column;
}
/* .tdata:nth-child(even) {
  background-color: #dfe9e6;
} */

.planContainer {
  border-top: 1px solid rgb(215, 195, 163);
}

.date-meal {
  padding-left: 15px;
}

.dateOptions,
.mealTypeOptions,
.recipesOptions {
  display: inline-block;
}

img.pencil-icon {
  border-radius: 150px;
  margin-left: 15px;
}

.form-control {
  border: 1px solid #ccc;
  display: block;
  height: 40px;
  margin: 0 15px 25px;
  border-radius: 20px;
  background: #f8f8f8;
  max-width: 130px;
  text-align: center;
}

.form-btn {
  background: transparent;
  border: 1px solid #bebbbb;
  border-radius: 8px;
  color: #222222;
  cursor: pointer;
  display: inline-block;
  font-size: 15px;
  line-height: 10px;
  outline: none;
  padding: 13px 23px;
  position: relative;
  text-align: center;
  margin-bottom: 10px;
}
/* 
.x-icon{
  color:red;
 
  border-radius: 50px;
  font-size: 30px;

} */
.edit-form{
  padding: 20px 5px;
}
</style>
