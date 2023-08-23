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
      <div class="filter-btn all" @click="filterDisplay">all</div>
      <div class="filter-btn" @click="filterDisplay">favorites</div>
      <div class="filter-btn" @click="handleSearch">name</div>
      <div class="filter-btn tags" @click="handleSearch">tags</div>
    </section>

    <div class="search-bar" v-if="showSearchBar">
      <svg
        xmlns="http://www.w3.org/2000/svg"
        width="25"
        height="25"
        viewBox="0 0 50 50"
      >
        <path
          d="M 21 3 C 11.621094 3 4 10.621094 4 20 C 4 29.378906 11.621094 37 21 37 C 24.710938 37 28.140625 35.804688 30.9375 33.78125 L 44.09375 46.90625 L 46.90625 44.09375 L 33.90625 31.0625 C 36.460938 28.085938 38 24.222656 38 20 C 38 10.621094 30.378906 3 21 3 Z M 21 5 C 29.296875 5 36 11.703125 36 20 C 36 28.296875 29.296875 35 21 35 C 12.703125 35 6 28.296875 6 20 C 6 11.703125 12.703125 5 21 5 Z"
        ></path>
      </svg>
      <input
        type="text"
        class="search-input"
        placeholder="Search..."
        v-model="searchTerm"
        v-on:keyup.enter="searchResults"
      />
    </div>

    <div class="recipe">
      <div
        v-for="recipe in currentRecipesDisplay"
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
          <img :src="getImgSrc(recipe)" alt="first imag" class="foodPic" />
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
            <i
              class="heart"
              v-bind:class="{ 'heart-active': recipe.is_favorite }"
              @click="toggleFavorites(recipe)"
            ></i>
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
      currentRecipesDisplay: [],
      favorites: [],
      showForm: false,
      showSearchBar: false,
      searchTerm: "",
      searchCategory: "",
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

    getImgSrc(recipe) {
      // sample data
      let matchingImage = this.$store.state.images.find(
        (recipesimg) => recipesimg.id === recipe.recipeId
      );
      if (matchingImage != undefined) {
        return matchingImage.path;
      } else {
        // saved photo retrieval (if not null)
        const imageData = recipe.image;
        const imageType = recipe.image_type;
        if (imageData != null) {
          const dataURL = `data:${imageType};base64,${imageData}`;
          return dataURL;
        }

        // random picture display if user didn't save any photo
        return this.randomItem(this.images);
      }
    },

    toggleFavorites(recipe) {
      // if the is_favorite property true,
      if (recipe.is_favorite) {
        recipe.is_favorite = false;
        // update database
        recipeService.modifyRecipe(recipe.recipeId, recipe);

        // update our state
        this.favorites = this.favorites.filter((recipe) => recipe.is_favorite);
      } else {
        recipe.is_favorite = true;
        recipeService.modifyRecipe(recipe.recipeId, recipe);
        this.favorites.push(recipe);
      }
    },

    // filter buttons
    filterDisplay(event) {
      const filterType = event.target.innerText;
      switch (filterType) {
        case "all":
          this.currentRecipesDisplay = this.recipes;
          break;
        case "favorites":
          this.currentRecipesDisplay = this.favorites;
          break;
        default:
          this.currentRecipesDisplay = this.recipes;
      }
      this.removeActiveButton();
      event.target.classList.add("special");

      // to make sure if user clicks on these tags with the search bar open, close the search bar
      this.showSearchBar = false;
    },

    // Clicking on the 'name' or 'tags' buttons
    handleSearch(event) {
      // highlighting button
      this.removeActiveButton();
      event.target.classList.add("special");
      const filterType = event.target.innerText;

      // toggle search bar
      this.showSearchBar = !this.showSearchBar;

      if (filterType === "name") {
        this.searchCategory = "name";
      }
      // if filterType is tags
      else {
        this.searchCategory = "tags";
      }
    },

    searchResults() {
      if (this.searchCategory ==="name") {
          this.currentRecipesDisplay = this.recipes.filter((recipe) => recipe.recipe_name.includes(this.searchTerm))
      }
      if (this.searchCategory === "tags") {
        this.currentRecipesDisplay = this.recipes.filter((recipe) => {
          const tagsArr = recipe.tags.split(",");
          let includesTag = false;
          tagsArr.forEach((tag) => {
            if (tag.toLowerCase() === this.searchTerm.toLowerCase()) includesTag = true;
          });
          return includesTag;
        })
      }
    },

    // removing the "special" styling from all the .special class btns
    removeActiveButton() {
      document.querySelector(".special")?.classList.remove("special");
    },
  },
  computed: {
    selectedRecipeList() {
      return this.currentRecipesDisplay;
    },
  },
  created() {
    recipeService.getRecipes(this.$route.params.userId).then((response) => {
      const data = response.data;
      // sort the result by recipe name alphabetical order
      this.recipes = data.sort((a, b) => {
        return a.recipe_name.localeCompare(b.recipe_name);
      });
      this.currentRecipesDisplay = [...this.recipes];
      console.log(this.currentRecipesDisplay);
      this.favorites = data.filter((recipe) => recipe.is_favorite);
    });
  },
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

input.search-input {
  border: none;
  padding: 10px;
}

input.search-input:focus {
  outline: none;
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

.special {
  background-color: rgb(121, 121, 121);
  color: #fff;
}

.special:hover {
  background-color: rgb(121, 121, 121);
  color: #fff;
}

.search-bar {
  display: flex;
  justify-content: flex-start;
  gap: 10px;
  width: 300px;
  height: 50px;
  border: 2px solid var(--grey-light-ui-200, #eeedf2);
  align-items: center;
  border-radius: 360px;
  box-shadow: 0 16px 32px -25px rgba(0, 0, 0, 0.2);
  margin: 0 auto;
  margin-top: 20px;
}

svg {
  padding-left: 30px;
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

.meta-heart {
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
  width: 90%;
  height: 250px;
  background-color: white;
  margin: 0;
  padding: 0;
}

i.heart {
  cursor: pointer;
  padding: 10px 12px 8px;
  background: #fff;
  border-radius: 50%;
  border: solid 1px rgb(199, 197, 197);
  color: #aaa;
  transition: 0.2s;
}

i.heart:hover {
  color: #666;
}

i.heart:before {
  font-family: fontawesome;
  content: "\f004";
  font-style: normal;
}

i.heart-active {
  color: #e23b3b;
}
/* .press-heart {
  animation: size 0.4s;
  color: #e23b3b;
} */

i.heart-active:hover {
  color: #f47f7f;
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
