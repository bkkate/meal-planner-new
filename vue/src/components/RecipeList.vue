<template>
  <div class="page">
    <section class="top-title">
      <div class="title">My Recipes</div>
      <div class="desc">Manage your recipes and add new ones!</div>
    </section>
    <button class="addNewRecipe" v-on:click="toggleShowForm()">
      Add New Recipe
    </button>

    <new-recipe-form id="form" v-if="showForm" />

    <section class="filters">
      <div class="filter-btn all">all</div>
      <div class="filter-btn">favorites</div>
      <div class="filter-btn">name</div>
      <div class="filter-btn tags">tags</div>
    </section>

    <div class="recipe">
      <div
        v-for="recipe in recipes"
        v-bind:key="recipe.recipeId"
        class="recipe-card"
      >
        <div class="food-name">
          <router-link
            class="h1"
            v-bind:to="{
              name: 'recipesId',
              params: { recipeId: recipe.recipeId },
            }"
            >{{ recipe.recipe_name }}</router-link
          >
        </div>
        <div class="image">
          <img
            :src="getRecipeId(recipe.recipeId)"
            alt="first imag"
            class="foodPic"
          />
        </div>

        <div class="meta-heart">
  
          <div>
            <div class="tags">
              <span class="tag">Tags:</span>
              <span class="tag">{{ recipe.tags }}</span>
            </div>
            <div class="time">
              <span class="time">Prep Time:</span>
              <span class="time">{{ recipe.prep_time }}</span>
            </div>
          </div>
          <div class="icon">
            <i></i>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import recipeService from "../services/RecipeService.js";
import NewRecipeForm from "./NewRecipeForm.vue";

export default {
  name: "recipes",
  data() {
    return {
      recipes: [],
      showForm: false,
      images: [
        require("../assets/meal-sample1.jpg"),
        require("../assets/meal-sample2.png"),
        require("../assets/meal-sample3.png"),
        require("../assets/meal-sample-4.jpg"),
        require("../assets/food-plate-icon.png"),
        require("../assets/1M.png"),
        require("../assets/M6.png"),
        require("../assets/M9.png"),
      ],
    };
  },
  components: {
    NewRecipeForm,
  },
  methods: {
    toggleShowForm() {
      this.showForm = !this.showForm;
    },
    randomItem(items) {
      return items[Math.floor(Math.random() * items.length)];
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

  //** might not need this*/
  created() {
    recipeService.getRecipes(this.$route.params.userId).then((response) => {
      this.recipes = response.data;
    });
  },
  // computed: {
  //     recipe() {

  //       return this.$store.state.recipesimg.find(recipesimg => recipesimg.id === this.$route.params.recipe.recipe.Id)
  //     }
  //   }
};
</script>
<style scoped>
@import url(https://fonts.googleapis.com/css2?&family=Italiana&family=Lato&family=Poppins&family=Nanum+Gothic:wght@400;700&family=Noto+Sans:wght@300;400&family=Open+Sans:wght@300;400;500;600;700;800&display=swap);

.page {
  font-family: "Dosis", "Poppins", sans-serif;
  text-align: center;
  /* background: url(../assets/white-background.jpg) no-repeat center center fixed;
  -webkit-background-size: cover;
  -moz-background-size: cover;
  -o-background-size: cover;
  background-size: cover; */
  /* padding-bottom: 50px; */
  margin: 0px;
  background-color: #f7f7f7;
  background-color: #fff;
}

section.top-title {
  background: url("../assets/light-orange-right.jpg") no-repeat center center;
  /* background: url('../assets/blue-right.jpg') no-repeat center center; */
  background-size: cover;
  height: 320px;
  text-align: left;
  margin-bottom: 20px;
}

.title {
  color: #422800;
  /* color:#0d75134b; */
  /* text-shadow: 2px 1px 1px black; */
  font-size: 36px;
  align-content: center;
  padding: 100px 100px 40px 100px;
}

.desc {
  padding: 0px 100px 100px 100px;
  font-size: 20px;
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

.filter-btn.all,
.filter-btn.tags {
  padding-right: 20px;
  padding-left: 20px;
}

.filter-btn:hover {
  background-color: #f8f7f4;
}

div.recipe {
  display: flex;
  justify-content: center;
  flex-wrap: wrap;
  gap: 30px;
  padding: 30px;
}

.recipe-card {
  width: 300px;
  height: 400px;
  background: white;
  border-radius: 20px;
  border: 0.5px solid rgb(229, 224, 224);
}

.recipe-card:hover {
  /* border-inline-end: 3px solid rgb(226, 226, 226);
  border-bottom: 3px solid rgb(226, 226, 226); */
  cursor: pointer;
  box-shadow: 0px 2px 8px rgba(30, 10, 60, 0.06),
    0px 4px 12px rgba(30, 10, 60, 0.08);
  transition: 0.3s;
}

a.h1 {
  color: rgb(11, 44, 116);
  text-decoration: none;
  font-size: 25px;
}

.food-name {
  margin: 8px;
}

.meta-heart{
  display: flex;
  justify-content: space-evenly;
  padding-left: 85px;
}

.tag {
  display: inline-block;
  padding: 4px;
}
.time {
  display: inline-block;
  padding: 3px;
}

#form {
  margin: auto;
  margin-top: 5px;
  padding: 20px;
  /* border: solid 1px rgb(161, 160, 160);
   border-radius: 5px; */
  width: 80vw;
}
img.foodPic {
  width: 100%;
  height: 250px;
  background-color: white;
  margin: 0;
  padding: 0;
}

i {
  cursor: pointer;
  padding: 10px 12px 8px;
  background: #fff;
  border-radius: 50%;
  border: solid 1px rgb(199, 197, 197);
  color: #aaa;
  transition: 0.2s;
}

i:hover {
  color: #666;
}

i:before {
  font-family: fontawesome;
  content: "\f004";
  font-style: normal;
}

.press-heart{
  animation: size .4s;
  color:#e23b3b;
}

button {
  appearance: none;
  background: transparent;
  border: 1px solid rgba(8, 9, 9, 0.15);
  border-radius: 300px;
  box-shadow: rgba(27, 31, 35, 0.04) 0 1px 0,
    rgba(255, 255, 255, 0.25) 0 1px 0 inset;
  color: #000000;
  cursor: pointer;
  display: inline-block;
  font-size: 14px;
  font-weight: 500;
  line-height: 34px;
  padding: 6px 16px;
  position: relative;
  transition: background-color 0.7s, color 0.7s;
}

button:hover {
  background-color: #e7e6df;
  text-decoration: none;
  transition-duration: 0.1s;
}

button:active {
  background-color: #edeff2;
  box-shadow: rgba(225, 228, 232, 0.2) 0 1px 0 inset;
  transition: none 0s;
}
</style>
