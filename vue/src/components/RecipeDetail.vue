<template>
  <!-- <div class="recipe-detail" id="max-container">
    <div class="right">
      <img
        id="myrecipesbtn"
        class="back-arrow noprint"
        src="../assets/backArrow.png"
        alt="Back Arrow"
        @click="$router.go(-1)"
      />
      <div class="recipe-container">
        <h2 id="recipe-name">{{ recipe.recipe_name }}</h2>
        <div class="details">
          <div id="edit-button noprint">
            <edit-recipe-form
              class="noprint"
              v-bind:recipe-from="recipe"
              v-bind:list-ingredients="ingredients"
            />
          </div>

          <div id="direction-section">
            <h3>Directions</h3>
            <p align="justify" class="directions">{{ recipe.directions }}</p>
          </div>

          <div id="ingredients-section">
            <h3>Ingredients</h3>

            <div
              v-for="(ingredient, index) in ingredients"
              v-bind:key="index"
              class="ingredient-list"
            >
              <input
                type="checkbox"
                v-bind:value="index"
                id="list-item"
                v-model="idsToAdd"
                v-on:click="addIdToSomething(index)"
              />

              <h4 id="ingredient-name">{{ ingredient.ingredient_name }}:</h4>
              <p id="ingredient-amount">{{ ingredient.amount }}</p>
            </div>
            <button
              class="addGroceries noprint"
              type="submit"
              v-on:click.prevent="sendToGroceryDB()"
            >
              Add To Grocery List
            </button>
          </div>

          <div class="tag">
            <h5 class="tag">Tags:</h5>
            <p class="tag">{{ recipe.tags }}</p>
          </div>
          <br />
          <div class="time">
            <h5 class="time">Prep Time:</h5>
            <p class="time">{{ recipe.prep_time }} min</p>
          </div>
          <br />
          <button v-on:click="handlePrint()" class="print-btn noprint">
            Print Recipe
          </button>
        </div>
      </div>
    </div>
    <div class="left-container">
      <img
        :src="getRecipeId(recipe.recipeId)"
        alt="first imag"
        class="foodPic"
      />
    </div>
  </div> -->

  <div class="recipe-detail" id="max-container">
    <section class="top">
      <img
        id="myrecipesbtn"
        class="back-arrow noprint"
        src="../assets/backArrow.png"
        alt="Back Arrow"
        @click="$router.go(-1)"
      />
      <div class="recipe-name">
        <h2>{{ recipe.recipe_name }}</h2>
      </div>
    </section>
    <div class="edit-button-box">
      <button v-on:click="showForm = !showForm" class="edit-recipe-btn">
        Edit Recipe
      </button>
    </div>

    <div class="edit-recipe">
      <edit-recipe-form
        class="noprint"
        v-bind:recipe-from="recipe"
        v-bind:list-ingredients="ingredients"
        v-if="showForm"
      />
    </div>
    <section v-if="!showForm">
      <div class="recipe-container">
        <div class="left-container">
          <div class="details">
            <div id="direction-section" class="section">
              <h3>Directions</h3>
              <p align="justify" class="directions">{{ recipe.directions }}</p>
            </div>

            <div class="ingredients-section section">
              <div >
                <h3>Ingredients</h3>
                <div
                  v-for="(ingredient, index) in ingredients"
                  v-bind:key="index"
                  class="ingredient-list"
                >
                  <input
                    type="checkbox"
                    v-bind:value="index"
                    id="list-item"
                    v-model="idsToAdd"
                    v-on:click="addIdToSomething(index)"
                  />

                  <div class="ingredient">
                    <span class="ingredient-name"
                      >{{ ingredient.ingredient_name }}:</span
                    >
                    {{ ingredient.amount }}
                  </div>
                </div>
              </div>

              <button
                class="addGroceries noprint btn"
                type="submit"
                v-on:click.prevent="sendToGroceryDB()"
              >
                Add to Grocery List
              </button>
            </div>

            <div class="tag section">
              <h3 class="tag">Tags</h3>
              <p class="tag">{{ recipe.tags }}</p>
            </div>
            <div class="time section">
              <h3 class="time">Prep Time</h3>
              <p class="time">{{ recipe.prep_time }} min</p>
            </div>
          </div>
        </div>
        <div class="right-container">
          <img
            :src="getRecipeId(recipe.recipeId)"
            alt="first imag"
            class="foodPic"
          />
        </div>
      </div>
      <div class="print-btn-box">
        <button v-on:click="handlePrint()" class="print-btn noprint">
          Print Recipe
        </button>
      </div>
    </section>
  </div>
</template>

<script>
import EditRecipeForm from "./EditRecipeForm.vue";
import recipeService from "../services/RecipeService.js";
import IngredientService from "../services/IngredientService";
import GroceryListService from "../services/GroceryListService";

export default {
  name: "recipe-detail",
  components: {
    EditRecipeForm,
  },
  data() {
    return {
      showForm: false,
      recipe: {
        recipe_id: "",
        recipeName: "",
        directions: "",
        tags: "",
        prep_time: "",
        food_pic: "",
        is_public: "",
      },
      ingredients: [],
      tagsInBoxes: null,
      idsToAdd: [],
      itemsToAdd: [],
    };
  },
  methods: {
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

    addIdToSomething(ingredientId) {
      this.idsToAdd.push(ingredientId);
    },
    sendToGroceryDB() {
      console.log("sendToGroceryDb is being run");
      this.idsToAdd.forEach((id) => {
        this.itemsToAdd.push({
          ingredient_name: this.ingredients[id].ingredient_name,
          quantity: this.ingredients[id].amount,
        });
      });

      GroceryListService.addGroceriesToDB(
        this.$store.state.user.id,
        this.itemsToAdd
      );
      this.itemsToAdd = [];
      this.idsToAdd = [];
    },
    toggleShowForm() {
      this.showForm = !this.showForm;
    },
    handlePrint() {
      window.print();
    },
  },
  created() {
    recipeService
      .getRecipeById(this.$route.params.recipeId)
      .then((response) => {
        this.recipe = response.data;
      });
    IngredientService.getIngredientsForRecipe(this.$route.params.recipeId).then(
      (response) => {
        this.ingredients = response.data;
      }
    );
  },
};
</script>

<style scoped>
@import url(https://fonts.googleapis.com/css2?family=Dosis&family=Overpass&family=Playfair+Display&family=Raleway:wght@300;400&display=swap);

div.recipe-detail {
  font-family: "Overpass", "Raleway", "Dosis", sans-serif;
  padding: 35px;
  background: rgb(252, 250, 241);
  min-height: 85vh;
}

div.section {
  margin-bottom: 20px;
}

.back-arrow {
  width: 30px;
}

div.recipe-name {
  display: inline-block;
  margin: 0 auto;
}
h2 {
  font-size: 1.8em;
  color: black;
  text-transform: uppercase;
  width: 85vw;
  text-align: center;
}

h3 {
  color: rgb(100, 81, 28);
  padding-bottom: 8px;
}

p, .ingredient-list{
  padding: 2px 0 2px 5px;
}

.recipe-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
div.ingredient {
  display: inline;
}
.ingredients-section {
  display: flex;
  align-items: center;
  gap: 100px;
}

button.btn {
  cursor: pointer;
  font: "Raleway", "Dosis", sans-serif;
  border: 1px solid rgba(8, 9, 9, 0.15);
  /* background: #ededea; */
  background: transparent;
  border: 1px solid rgba(8, 9, 9, 0.15);
  border-radius: 300px;
  box-shadow: rgba(27, 31, 35, 0.04) 0 1px 0,
    rgba(255, 255, 255, 0.25) 0 1px 0 inset;
  color: #000000;
  cursor: pointer;
  /* display: inline-block; */
  font-size: 15px;
  font-weight: 500;
  line-height: 30px;
  padding: 6px 15px;
  height: 40%;
}

.left-container {
  padding: 20px 30px 0 0;
}

img.foodPic {
  width: 400px;
  height: 400px;
}

div.print-btn-box {
  text-align: center;
}

button.edit-recipe-btn,
button.print-btn {
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

@media (max-width: 890px) {
  img.foodPic {
    width: 300px;
    height: 300px;
  }

  .ingredients-section {
    display: flex;
    align-items: center;
    gap: 35px;
  }

  h2 {
    width: 80vw;
  }
}
@media (max-width: 450) {
  img.foodPic {
    width: 150px;
    height: 150px;
  }

  .ingredients-section {
    display: flex;
    align-items: center;
    gap: 5px;
  }
}

@media print {
  .noprint {
    display: none;
  }
}
/* 
@media (max-width: 450) {
  .recipe-detail {
    display: grid;
    grid-template-columns: 1fr;
    grid-template-areas:
      "name"
      "pic"
      "details";
    align-items: center;
    justify-items: center; 
  }
  #recipe-name {
    grid-area: name;
  }
  img.left {
    grid-area: pic;
  }
  .details {
    display: grid;
    grid-template-columns: 1fr;
    grid-template-areas:
      "edit"
      "directions"
      "ing"
      "tag"
      "time";
    grid-area: details;
  }
  #edit-button {
    grid-area: edit;
  }
  #direction-section {
    grid-area: directions;
  }
  #ingredients-section {
    grid-area: ing;
  }
  .tag {
    grid-area: tag;
  }
  .time {
    grid-area: time;
  }
}
@media (max-width: 1024px) {
  .recipe-detail {
    background-color: #ebf2ef;
    border-radius: 10px;
    padding: 2%;
  }
  .left-container {
    display: flex;
  }
  .left {
    width: 400px;
    align-self: flex-end;
  }
}
@media (max-width: 1200px) {
  .left {
    width: 500px;
  }
}
@media print {
  .noprint {
    display: none;
  }
}

.recipe-detail {
  display: grid;
  grid-template-columns: 1fr 1fr;
  grid-template-areas: "lefty righty";
  padding: 20px;
  font-size: 18px;

  background-color: #ebf2ef;
}
.recipe-container {
  background-color: #ebf2ef;
  border-radius: 10px;
  padding: 2%;
  font-family: "Dosis", monospace, sans-serif;
}
h2,
h3 {
  color: #0a1704;
}
.left {
  grid-area: righty;
  float: right;
  clear: both;
  padding: 10px;
  position: relative;
}
li {
  list-style-type: none;
}
.right {
  grid-area: lefty;
  float: left;
  clear: both;
  padding: 10px;
}

.tag {
  display: inline-block;
}
p.tag,
p.time {
  margin-top: 0;
  margin-left: 6px;
}
.time {
  display: inline-block;
}
.time h5,
.tag h5 {
  margin-top: 0;
}
#ingredient-name {
  display: inline-block;
  margin-top: 0;
  margin-left: 40px;
}
#ingredient-amount {
  display: inline-block;
  margin-top: 0;
  margin-left: 6px;
}
button:hover {
  transform: scale(1.05);
}

button:focus {
  outline: 0 solid transparent;
}
button {
  background-color: #cdeccd;
  border: 2px solid #422800;
  border-radius: 10px;
  box-shadow: #422800 4px 4px 0 0;
  color: #422800;
  cursor: pointer;
  display: inline-block;
  font-weight: 600;
  font-size: 15px;
  padding: 0 3px;
  line-height: 30px;
  text-align: center;
  text-decoration: none;
  font-family: "Dosis", monospace, sans-serif;
}
.addGroceries {
  margin-bottom: 20px;
}
.left-container {
  display: flex;
}
img {
  max-width: 90%;
  justify-content: center;
  align-self: center;
  border-radius: 5%;
  margin-left: 25px;
}

h5 {
  font-size: 0.9em;
} */
</style>
