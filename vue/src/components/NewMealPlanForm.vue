<template>
<div class="background">

    <div class="weekly-plan-name">
      <div class="title-add-section" v-if="!planNameAdded">
        <input
          placeholder="Meal Plan Name"
          type="text"
          class="meal-plan-name"
          id="meal-plan-name"
          v-model="mealPlanName"
          @keyup.enter="addName"
        />
        <button class="addBtn" @click.prevent="addName">Add</button>
      </div>

      <div class="addedTitleDisplay" v-if="planNameAdded">
        <h1 @click="modifyName">{{ mealPlanName }}</h1>
        <!-- <button class="modifyBtn" @click.prevent="modifyName">Edit Name</button> -->
      </div>
    </div>
    <form class="mealForm">
      <div class="options">
      <div class="dateOptions">
          <label for="date">Select Date: </label>
          <input
            type="date"
           class="form-control"
            name="date"
            v-model="mealPlan.for_date"
          />
        </div>

        <div class="mealTypeOptions">
          <label for="mealType">Select Meal: </label>
          <select name="mealType" class="form-control" v-model="mealPlan.meal_type">
            <option value=1> Breakfast </option>
            <option value=2> Lunch </option>
            <option value=3> Dinner </option>
            <option value=4> Snack </option>
            <option value=5> Appetizer </option>
          </select>
        </div>

        <div class="recipesOptions">
          <label for="recipe">Select Recipe: </label>
          <select name="recipe" class="form-control" v-model="currentSelectedRecipe">
            <option
              v-for="recipe in this.$store.state.recipes"
              v-bind:key="recipe.recipe_id"
              :value="recipe.recipe_name"
            >
              {{ recipe.recipe_name }}
            </option>
          </select>
        </div>
 <div class="btn">
           <button class="addToPlanBtn" @click.prevent="addMealCombo">Add Meal </button>
       </div>
      </div>
      
   
    </form>

    <table>
      <thead>
          <tr>
            <div class="planContainer">
              <th class="tdata">Date</th>
              <th class="tdata">Meal</th>
              <th class="tdata">Recipe</th>
            </div>
          </tr>
       
      </thead>
      <tbody>
        <tr v-for="plan in listOfPlans" v-bind:key="plan.for_date">
          <div class="planContainer">
          <td class="tdata">{{plan.for_date}}</td>
          <td class="tdata">{{convertMealTypeToWord(plan.meal_type)}}</td>
          <td class="tdata">{{getRecipeName(plan.recipe_id)}}</td>
          </div>
        </tr>

      </tbody>
    </table>


    
    <div class="btn">
     <button class="addToMealPlanBtn" @click.prevent="addMealToDB"> Add To Meal Plan </button>
     </div>

</div>
</template>


<script>
import recipeService from "../services/RecipeService.js";
import mealPlanService from "../services/MealPlanService"

export default {
  name: "add-meal-plan",
  data() {
    return {
      planNameAdded: false,
      mealPlanName: "",
      currentSelectedRecipe: "",
      listOfPlans:[],
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

  created() {
    recipeService.getRecipes(this.$store.state.user.id).then((response) => {
      this.$store.commit("SET_RECIPE", response.data.sort());
    });
  },

  computed: {
    // mealTypeName() {
    //   if (this.mealPlan.meal_type === "1") {
    //     return "Breakfast";
    //   }
    //   else if (this.mealPlan.meal_type === "2") {
    //     return "Lunch";
    //   }
    //   else if(this.mealPlan.meal_type === "3") {
    //     return "Dinner";
    //   }
    //   else if(this.mealPlan.meal_type === "4") {
    //     return "Snack";
    //   }
    //   else {
    //     return "Appetizer"
    //   }
    // },

    currentRecipeId() {
      let recipeObj =  this.$store.state.recipes.find((recipe) => 
         recipe.recipe_name === this.currentSelectedRecipe
      );
      return recipeObj.recipeId;
    }
  },

// List<MealPlan>
// each MealPlan object should have: plan_name, recipe_id, for_date, meal_type int 

  methods: {
    clear() {
       this.mealPlan =  {
        plan_name: "",
        recipe_id: 0,
        for_date: "",
        meal_type: 0
      };

      this.currentSelectedRecipe = "";
    },
    addName(){
        this.planNameAdded = true;
    },
    modifyName() {
        this.planNameAdded = false;
    },

    getRecipeName(currentRecipeId) {
        let recipeObj = this.$store.state.recipes.find((recipe) => recipe.recipeId === currentRecipeId);
        return recipeObj.recipe_name;
    },

    convertMealTypeToWord(mealType) {
      if (mealType === 1) {
        return "Breakfast";
      }
      else if (mealType === 2) {
        return "Lunch";
      }
      else if(mealType === 3) {
        return "Dinner";
      }
      else if(mealType === 4) {
        return "Snack";
      }
      else {
        return "Appetizer"
      }
    },
    addMealCombo() {
         this.mealPlan.meal_type = parseInt(this.mealPlan.meal_type);
         this.mealPlan.plan_name = this.mealPlanName;
         this.mealPlan.recipe_id = this.currentRecipeId;

         this.listOfPlans.push(this.mealPlan);

        this.clear();
    },
    addMealToDB() {
      mealPlanService.addMealPlan(this.$store.state.user.id, this.listOfPlans);
      this.listOfPlans=[];
      this.clear();
      this.mealPlanName= "";
      this.$router.push({name: 'mealplans', params: {userId: this.$store.state.user.id }});

    }
  },
};
</script>

<style scoped>

@media (max-width: 1150px){
  .dateOptions{
  margin: 20px;
}
.mealTypeOptions{
  margin: 20px;
}
.recipesOptions{
  margin: 20px;
}
  
}

.background{
  text-align: center;
  font-family: "Raleway", 'Dosis', monospace, sans-serif;
  background: url(../assets/dumplings-right.jpg) no-repeat center center fixed;
  -webkit-background-size: cover;
  -moz-background-size: cover;
  -o-background-size: cover;
  background-size: cover;
  padding-bottom: 50px;
  padding-top: 50px;
  height:100vh;
}

.weekly-plan-name{
  margin: 5px 0px 25px;
}
input.meal-plan-name {
  width: 180px;
  height: 50px;
  border: 2px solid var(--grey-light-ui-200, #eeedf2);
  font-family: "Raleway", "Dosis", sans-serif;
  font-size: 15px;
  border-radius: 360px;
  margin: 0 40px;
  padding-left: 30px;
}

.options {
  display: flex;
  justify-content: center;
  align-items: center;
  /* background-color:rgb(241, 249, 253); */
  margin: 0 auto;
}
.form-control {
  border: 1px solid #ccc;
  display: block;
  height: 40px;
  margin: 0 5px 25px;
  border-radius: 20px;
  background: #f8f8f8;
  max-width: 200px;
  text-align: center;
}
.addBtn,
.modifyBtn, 
.addToPlanBtn,
.addToMealPlanBtn {
    /* cursor: pointer;
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
  padding: 6px 15px; */

   cursor: pointer;
  font: "Raleway", "Dosis", sans-serif;
  appearance: none;
  background: #5e5e5b;
  border: 1px solid rgba(8, 9, 9, 0.15);
  box-shadow: rgba(27, 31, 35, 0.04) 0 1px 0,
    rgba(217, 215, 215, 0.25) 0 1px 0 inset;
  color: #fff;
  cursor: pointer;
  display: inline-block;
  font-size: 15px;
  font-weight: 500;
  line-height: 30px;
  padding: 6px 15px;
  margin-top: 15px;
}

.addBtn{
  background:transparent;
color:black;
}
.addToPlanBtn{
margin:0;
width:100px;
margin-bottom:10px;
background:transparent;
color:black;
}

.addBtn:hover,
.modifyBtn:hover, 
.addToPlanBtn {
  font-weight: 2em;
  cursor: pointer;
}

.addToMealPlanBtn {
margin-top: 60px;
}


.options {
  display: flex;
  justify-content: center;
  /* background-color:rgb(241, 249, 253); */
  margin: 0 auto;
  max-width: 50%;
}

table{
  margin: 0px 90px;
  width:50%;
}

thead tr div.planContainer{
  border-bottom: 1px solid black;
}

.tdata{
  padding: 1.2em;
  width:50%;
}

th.tdata{
  font-size:1.2em;
}

.planContainer {
  display: flex;
  justify-content: space-evenly;
  align-items: center;
}

.dateOptions, .mealTypeOptions, .recipesOptions {
  display: inline-block;
  margin: 20px;
}

/* .mealForm {
  background-color:white;
} */

</style>