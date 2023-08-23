<template>
  <div>
    <form action="#" v-on:submit.prevent="" class="recipe-form">
      <div class="name-prep">
        <div class="section">
          <label for="recipe-name">Recipe name </label>
          <input
            type="text"
            class="recipe-name form-control"
            id="recipe-name"
            v-model="recipe.recipe_name"
          />
        </div>
        <div>
          <label for="prep-time">Prep time </label>
          <div>
            <input
              type="number"
              class="prep-time form-control"
              size="5"
              v-model="recipe.prep_time"
            />
            <span> mins </span>
          </div>
        </div>
      </div>

      <div class="section">
        <label for="directions">Directions </label>
        <textarea
          name="directions"
          class="directions form-control"
          v-model="recipe.directions"
        ></textarea>
      </div>

      <div class="section dir">
        <label for="directions">Tags </label>
        <div class="tags-text-btn">
          <input
            class="addTag form-control"
            type="text"
            placeholder="Add a tag"
            v-model="inputTag"
          />
          <button class="addTag btn" @click.prevent="concatTag()">
            <span>Add</span>
          </button>
        </div>
      </div>

      <div class="tag-list" v-bind:show="recipe.tags">{{ recipe.tags }}</div>

      <div class="ingredients-content section">
        <div>
          <label for="ingredient"> Ingredient </label>
          <input
            type="text"
            id="ingredient"
            class="form-control"
            v-model="inputIngredient.ingredient_name"
          />
        </div>

        <div>
          <label for="amount">Amount </label>
          <input
            type="text"
            class="form-control"
            id="amount"
            v-model="inputIngredient.amount"
          />
        </div>

        <div>
          <label for="unit">Unit </label>
          <select name="units" class="form-control" id="units" v-model="unit">
            <option value="cups">cups</option>
            <option value="Tbsp">Tbsp</option>
            <option value="Tsp">Tsp</option>
            <option value="grams">g</option>
            <option value="pounds">lbs</option>
            <option value="ounces">oz</option>
            <option value="quarts">quarts</option>
            <option value="pints">pints</option>
            <option value="gallons">gallons</option>
            <option value="units">units</option>
          </select>
        </div>

        <button class="addIngredient btn" @click.prevent="concatIngredient()">
          <span>Add</span>
        </button>
      </div>

      <div>
        <ul v-for="ingredient in ingredients" v-bind:key="ingredient.name">
          <li>{{ ingredient.amount }} of {{ ingredient.ingredient_name }}</li>
        </ul>
      </div>
      <div class="section upload">
        <label for="image-upload" class="file-upload">Upload a picture: </label>

        <input
          type="file"
          id="image-upload"
          ref="uploadImage"
          @change="uploadImage"
        />
      </div>

      <div class="btn-align">
        <input
          class="submitBtn btn"
          type="submit"
          v-on:click="addRecipeToDatabase()"
          value="Submit"
        />
        <button class="cancelBtn btn" @click.prevent="clear()">
          <span>Clear</span>
        </button>
      </div>
    </form>
  </div>
</template>
<script>
import RecipeService from "../services/RecipeService";
import IngredientService from "../services/IngredientService";
import ImageService from "../services/ImageService.js";
export default {
  data() {
    return {
      formData: null,
      inputTag: "",
      inputIngredient: {
        ingredient_name: "",
        amount: "",
        ingredient_id: 0,
      },
      unit: "",
      recipe: {
        recipe_name: "",
        directions: "",
        tags: "",
        prep_time: "",
        food_pic: "",
        is_public: false,
        is_favorite: false,
      },
      ingredients: [],
      imageData: null,
      recipe_id: "",
    };
  },
  methods: {
    // onFileChange(event) {
    //   const file = event.target.files[0];
    //   if (file) {
    //     const reader = new FileReader();
    //     reader.onload = (event) => {
    //       this.recipe.food_pic = event.target.result; // changes to base 64 bytes
    //     };
    //     reader.readAsDataURL(file); //reading the file as a URL
    //   }
    // },
    uploadImage(event) {
      const file = event.target.files[0];
      this.imageData = file;

      const formData = new FormData();
      formData.append("image", file);
      formData.append("contentType", file.type);

      this.imageData = formData;
      console.log(this.imageData);
    },
    clear() {
      this.inputTag = "";
      this.inputIngredient = {
        ingredient_name: "",
        amount: "",
        ingredient_id: 0,
      };
      this.unit = "";
      this.recipe = {
        recipe_name: "",
        directions: "",
        tags: "",
        prep_time: "",
        food_pic: "",
        is_public: false,
      };
      this.ingredients = [];
    },
    concatTag() {
      if (this.recipe.tags === "") {
        this.recipe.tags += this.inputTag;
      } else {
        this.recipe.tags += ", " + this.inputTag;
      }
      this.inputTag = "";
    },
    concatIngredient() {
      this.inputIngredient.amount += " " + this.unit;
      this.ingredients.push(this.inputIngredient);
      this.inputIngredient = {
        ingredient_name: "",
        amount: "",
        ingredient_id: "",
      };
      this.unit = "";
    },
    addRecipeToDatabase() {
      RecipeService.addNewRecipe(this.$store.state.user.id, this.recipe)
        .then((response) => {
          // once new recipe was added to database
          // if (response.status === 200 || response.status === 201) {
          //   console.log("Recipe successfully created!");
          this.recipe_id = response.data.recipeId;

          // add ingredients of the recipe to the database
          this.addIngredientToDatabase();

          // add recipe image on recipe table
          if (this.imageData) {
            ImageService.uploadImage(this.imageData, this.recipe_id)
              .then((response) => {
                if (response.status === 200 || response.status === 201) {
                  console.log("Image successfully uploaded!");
                  this.$router.go();
                }
              })
              .catch(() => {
                console.log("Image was not uploaded");
              });
            // }
          }
        })
        .catch((error) => {
          this.handleError(error);
        });

      location.reload();
    },
    addIngredientToDatabase() {
      this.ingredients.forEach((ingredient) => {
        IngredientService.addIngredient(this.$store.state.user.id, ingredient)
          .then((response) => {
            if (response.status === 201 || response.status === 200) {
              ingredient.ingredient_id = response.data.ingredient_id;
              IngredientService.addIngredientForRecipe(
                this.recipe_id,
                ingredient
              )
                .then((response) => {
                  if (response.status === 201 || response.status === 200) {
                    this.showForm = false;
                    this.clear();
                    window.location.reload();
                  }
                })
                .catch((error) => {
                  this.handleError(error);
                });
            }
          })
          .catch((error) => {
            this.handleError(error);
          });
      });
    },
    handleError(error) {
      if (error.response) {
        this.errorMsg =
          "Error submitting new recipe. Response recived was '" +
          error.response.statusText +
          "'";
      } else if (error.request) {
        this.errorMsg =
          "Error submitting new recipe. Server could not be reached.";
      } else {
        this.errorMsg =
          "Error submitting new recipe. Request could not be reached.";
      }
    },
  },
};
</script>

<style scoped>
form.recipe-form {
  padding: 35px;
  background-color: rgba(255, 255, 255, 0.8);
  border: solid 1px rgb(169, 168, 168);
  border-radius: 10px;
}
.form-control {
  border: 1px solid #ccc;
  display: block;
  height: 40px;
  padding: 0 20px;
  border-radius: 20px;
  background: 0 0;
}

.section {
  margin-bottom: 18px;
}

.section.dir {
  margin-bottom: 5px;
}

.name-prep {
  margin-bottom: 12px;
  display: flex;
  justify-content: flex-start;
  gap: 50px;
}

.ingredients-content {
  margin-bottom: 12px;
  display: flex;
  justify-content: flex-start;
  gap: 10px;
}
#amount {
  max-width: 40px;
  text-align: center;
}

.form-control.prep-time {
  display: inline;
  width: 50px;
  text-align: center;
}

.addTag {
  display: inline;
}

.tags-text-btn {
  display: flex;
  gap: 20px;
  margin-bottom: 0;
  padding-bottom: 0;
}

label {
  display: inline-block;
  width: 97%;
  text-align: start;
  padding-bottom: 5px;
}

li {
  list-style-type: none;
}

.file-upload {
  margin-bottom: 16px;
}

.directions {
  width: 90%;
  min-height: 100px;
  font-size: 16px;
  font-family: "Dosis", sans-serif;
}

.btn {
  background-color: #f7f8fa;
  border: 1px solid #a4a3a3;
  box-sizing: border-box;
  color: #353434;
  cursor: pointer;
  display: inline-block;
  font-size: 14px;
  margin: 0;
  height: 8px;
  outline: none;
  padding: 1rem 1rem;
  text-align: center;
  text-decoration: none;
  transition: all 300ms cubic-bezier(0.23, 1, 0.32, 1);
  width: fit-content;
  border-radius: 0.25rem;
  margin: 5px;
  line-height: 0.2em;
}

button.addIngredient.btn {
  margin-top: 25px;
}

.btn:hover {
  color: #fff;
  background-color: #aca8a8;
  box-shadow: rgba(61, 61, 61, 0.25) 0 2px 2px;
}

.btn-align {
  display: flex;
  justify-content: center;
}

div.tag-list {
  color: rgb(46, 110, 77);
  text-align: left;
  margin-bottom: 20px;
  padding-left: 18px;
}

label.file-upload {
  text-align: center;
  margin-top: 15px;
  margin-bottom: 2px;
}

div.section.upload {
  /* display: flex;
  justify-content: flex-start; */
}
</style>
