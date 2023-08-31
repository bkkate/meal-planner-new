<template>
  <div class="list">
    <h2 class="list-title">Grocery</h2>
    <div class="list-container">
      <div class="inputs-and-button">
        <div class="inputs">
          <div>
            <label class="input-label" for="itemName">Item Name: </label>
            <input class="item" type="text" id="itemName" v-model="itemName" />
          </div>
          <div>
            <label class="input-label" for="amount">Amount:</label>
            <input class="amount" type="text" id="amount" v-model="amount" />
          </div>
          <!-- <button class="add-grocery" @click="addSelectedIngredients">
          Add to List
        </button> -->
        </div>
        <button class="add-grocery" @click="addSelectedIngredients">
          Add to List
        </button>
      </div>

      <div class="items-container">
        <div class="inner-box">
          <div v-if="isListEmpty">
            <div
              v-for="(list, index) in updatedFoodList"
              :key="index"
              class="list-items"
            >
              <input
                type="checkbox"
                v-model="selectedIngredients"
                :value="list"
              />
              <label v-if="list.ingredient_name != undefined" class="item-name"
                >{{ list.ingredient_name }}: {{ list.quantity }}
              </label>
              <label v-else class="item-name"
                >{{ list.non_food_option }}: {{ list.quantity }}</label
              >
            </div>
          </div>
          <div v-else>
            <p class="emptyMsg">
              You currently don't have any groceries on your list.
            </p>
          </div>
        </div>
      </div>
      <div class="delete-btns">
        <button class="btn" @click="deleteSelected">Delete Selected</button>
        <button class="btn" @click="deleteAll">Delete All</button>
      </div>
      <div class="print-btn">
        <button class="btn" v-on:click="handlePrint()">
        Print Grocery List
      </button>
      </div>
      
    </div>
  </div>
</template>
<script>
import groceryListService from "../services/GroceryListService.js";
export default {
  name: "grocery-list",
  data() {
    return {
      amount: "",
      itemName: "",
      grocerylist: [],
      addedgroceries: [],
      selectedIngredients: [],
      updatedFoodList: [],
      nonFoodList: [],
      itemIndex: "",
      itemAmount: [],
    };
  },
  computed: {
    isListEmpty() {
      return this.grocerylist.length;
    },
  },
  created() {
    this.pageloadingmethod();
  },
  methods: {
    handlePrint() {
      window.print();
    },
    addSelectedIngredients() {
      this.addedgroceries.push({
        quantity: this.amount,
        non_food_option: this.itemName,
      });
      groceryListService.addGroceriesToDB(
        this.$route.params.userId,
        this.addedgroceries
      );
      // this.pageloadingmethod();
      // this.addItemsTogether();
      location.reload();
    },
    pageloadingmethod() {
      groceryListService
        .getAllGroceryListItems(this.$route.params.userId)
        .then((response) => {
          this.grocerylist = response.data;
          this.addItemsTogether();
        });
    },
    deleteSelected() {
      this.selectedIngredients.forEach((ingredient) => {
        console.log(ingredient.list_id);
        groceryListService.deleteSelectedLists(ingredient.list_id);
      });
      location.reload();
    },
    deleteAll() {
      if (
        confirm(
          "Are you sure you want to delete the whole list? You cannot undo the deletion."
        )
      ) {
        groceryListService.clearGroceryList(this.$route.params.userId);
      }
      location.reload();
    },
    addItemsTogether() {
      for (let i = 0; i < this.grocerylist.length; i++) {
        let currentGrocery = this.grocerylist[i];
        if (currentGrocery.ingredient_name != null) {
          // if it's a food item
          if (
            i != 0 &&
            currentGrocery.ingredient_name ===
              this.grocerylist[i - 1].ingredient_name
          ) {
            // if the ingredient names match

            // example case:
            // if carrots: 2 cups,  carrots: 1 tsp,   carrots: 3 cups
            // updatedFoodList would have carrots: 2 cups, 1 tsp.  We need to add 3 cupts to 2 cups

            let amountCurr = currentGrocery.quantity.split(" "); // [3, cups]
            let unitCurr = amountCurr[1];

            // "2 cups, 1 tsp"
            let amountOfSameIngredient =
              this.updatedFoodList[
                this.updatedFoodList.length - 1
              ].quantity.split(", "); // ['2 cups', '1 tsp']

            let amountUpdated = false;

            for (let i = 0; i < amountOfSameIngredient.length; i++) {
              if (amountOfSameIngredient[i].includes(unitCurr)) {
                let amountTotal =
                  parseInt(
                    amountOfSameIngredient[i].substring(
                      0,
                      amountOfSameIngredient[i].indexOf(" ")
                    )
                  ) + parseInt(amountCurr[0]);
                amountOfSameIngredient[i] =
                  amountTotal.toString() +
                  " " +
                  amountOfSameIngredient[i].substring(
                    amountOfSameIngredient[i].indexOf(" ") + 1
                  );
                amountUpdated = true;
              }
            }

            if (!amountUpdated) {
              amountOfSameIngredient.push(currentGrocery.quantity);
            }

            this.updatedFoodList[this.updatedFoodList.length - 1].quantity =
              amountOfSameIngredient.join(", ");
          } else {
            this.updatedFoodList.push(currentGrocery);
          }
        } else {
          // store non-food groceries to nonFoodList array to handle them together
          this.nonFoodList.push(currentGrocery);
        }
      }

      this.updatedFoodList = this.updatedFoodList.concat(this.nonFoodList);
    },
  },
};
</script>
<style scoped>
@import url(https://fonts.googleapis.com/css2?family=Dosis&family=Overpass&family=Playfair+Display&family=Raleway:wght@300;400&display=swap);

.list {
  font-family: "Overpass", "Raleway", "Dosis", sans-serif;
  font-size: 1.1em;
  padding-bottom:50px;
  filter: brightness(98%);

  background: url(../assets/groceries-white.jpeg) no-repeat center center;
  -webkit-background-size: cover;
  -moz-background-size: cover;
  -o-background-size: cover;
  background-size: cover;
  /* background: rgb(234, 238, 230); */
  min-height: 80vh;
}

h2 {
  text-align: center;
  font-size: 2em;
  padding: 35px;
}
.list-items {
  margin: 5px;
  text-align: center;
}
.items-container {
  border-radius: 10px;
  padding-top: 10px;
  padding-bottom: 10px;
  /* margin-left: auto;
  width: 65%; */
}
.list-container{
  margin-left:auto;
  width:75%;
}
.inner-box {
  /* border-top: 1px solid rgb(179, 177, 158); */
  border-bottom: 1px solid rgb(179, 177, 158);
  margin: 0 50px;
  padding: 12px 0 12px 0;
}

div.inputs-and-button{
  margin-left:auto;
  text-align: center;
}

.inputs {
  display: flex;
  justify-content: center;
  gap: 15px;
  height: 50px;
}

input.item,
input.amount {
  height: 40px;
  border: 2px solid var(--grey-light-ui-200, #eeedf2);
  font-size: 14px;
  border-radius: 360px;
  margin: 0 40px;
  padding-left: 15px;
}

input.item {
  width: 130px;
}

input.amount {
  width: 80px;
}

button.add-grocery {
  /* background: transparent;
  border: 1px solid #bebbbb;
  border-radius: 8px;
  color: #222222;
  cursor: pointer;
  display: inline-block;
  font-size: 15px;
  line-height: 10px;
  outline: none;
  padding: 0 5px;
  text-align: center;
  margin: 15px 15px 0px 10px; */

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
  margin: 20px 0 10px 25px;
}

button.btn {
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
.delete-btns {
  text-align: center;
}

.delete-btns .btn {
  margin: 5px;
}

.print-btn {
  text-align:center;
  margin:10px;
}

.emptyMsg {
  text-align: center;
  font-size: 1.5em;
}

#itemName,
#amount {
  margin: 10px;
}
@media print {
  button {
    display: none;
  }
  #itemName {
    display: none;
  }
  #amount {
    display: none;
  }
  .input-label {
    display: none;
  }
}
</style>
