<template>
  <div class="page">
    <div class="bg screen1">
      <div class="first-container">
        <h1 class="logo-name">Planner Ahead.</h1>
        <div class="about-one">
          Your one-stop solution for planning, cooking, and enjoying delicious
          and healthy meals every day!
        </div>
      </div>
    </div>
    <div class="bg screen2">
      <div class="mission-container">
        <img src="../assets/screenshots/sample-screenshot.png" alt="recipe" />
        <div>
          <h1 class="mission">our mission<span>_</span></h1>
          <div class="mission-one mission-statement">
            We are a team of passionate food lovers, nutrition experts, and tech
            enthusiasts who came together to make meal planning and cooking
            easier and more enjoyable
          </div>

          <!-- <div class="mission-two mission-statement">
            At Meal Planner, we believe that eating healthy and delicious meals
            should be effortless, fun, and affordable for everyone
          </div> -->

          <div class="mission-two mission-statement">
            That's why we created a simple and intuitive meal planning platform
            that helps you plan your meals for the week, create a shopping list,
            and cook delicious and healthy meals in no time
          </div>
        </div>
      </div>
    </div>
    <div class="bg screen3">
      <h2 class="special-title">Simplify your meal planning</h2>
      <section class="section">
        <div class="specialSection">
          <div class="icon"><i class="bx bx-time-five"></i></div>
          <div class="section-title">Save Time</div>
          <p class="specialPara">
            <span style="font-weight: bold; text-align: center"
              >Take the hassle out of meal planning.
            </span>
            <br />
            No more flipping through recipes books and coming up with meal ideas
            everyday.
          </p>
        </div>

        <div class="specialSection">
          <div class="icon"><i class="bx bx-customize"></i></div>
          <div class="section-title">Customize</div>

          <!-- <i class='bx bxs-customize'></i> -->
          <p class="specialPara">
            Our platform allows you to personalize your meal plans based on your
            favorite foods, allergies, and nutritional goals.
          </p>
        </div>

        <div class="specialSection">
          <div class="icon"><i class="bx bx-layer-plus"></i></div>

          <div class="section-title">Features</div>
          <!-- <i class='bx bx-restaurant'></i> -->

          <!-- <i class='bx bxs-layer-plus'></i> -->

          <p class="specialPara">
            Save all your recipes & created meal plans for later use.
            <br />
        Add, modify, delete any recipes or meal plans at any
            time. Have tailored meal plans organized in one space!
          </p>
        </div>
      </section>
    </div>

    <!-- <div class="bg screen4">
      <div class="testimonial">
        <div class="testi-content">
          <div class="slide">
            <img src="../assets/person-one.jpg" alt="" class="image" />
            <p>
              As a busy graduate student, using the Planner Ahead meal planner
              has saved me so much time so that I can focus on cooking.
            </p>
            <i class="bx bxs-quote-alt-left quote-icon"></i>
            <div class="details">
              <span class="name">Christina</span>
              <span class="job"> Graduate Student </span>
            </div>
          </div>
        </div>
      </div>
    </div> -->

    <div class="bg screen4">
      <div class="testimonial">
        <div class="testi-content">
          <swiper-container
            :slidesPerView="1"
            :spaceBetween="30"
            :loop="true"
            :pagination="{
              clickable: true,
            }"
            :navigation="true"
            :modules="modules"
            class="mySwiper"
          >
            <swiper-slide>
              <div class="slide">
                <img src="../assets/person-one.jpg" alt="" class="image" />
                <p>
                  As a busy graduate student, using the Planner Ahead meal
                  planner has saved me so much time so that I can focus on
                  cooking.
                </p>
                <i class="bx bxs-quote-alt-left quote-icon"></i>
                <div class="details">
                  <span class="name">Christina</span>
                  <!-- <span class="job"> Graduate Student </span> -->
                </div>
              </div>
            </swiper-slide>
            <swiper-slide>
              <div class="slide">
                <img src="../assets/person-two.jpg" alt="" class="image" />
                <p>
                  I had all these recipes written down that my Mom and Grandma
                  used to make. I was always spilling food on them and ruining
                  them. Now, I input all my family recipes in one location where
                  they'll never get splashed with pasta sauce again! 
                </p>
                <i class="bx bxs-quote-alt-left quote-icon"></i>
                <div class="details">
                  <span class="name">Matthew</span>
                  <!-- <span class="job"> Business Analyst </span> -->
                </div>
              </div>
            </swiper-slide>
            <swiper-slide>
              <div class="slide">
                <img src="../assets/person-three.jpg" alt="" class="image" />
                <p>
           I really love the features that Planner Ahead provides. I get to save all my recipes in one place, and it's so easy to look up any recipe that I need to use for the day!
           Plus, the grocery list feature saves me time.

                </p>
                <i class="bx bxs-quote-alt-left quote-icon"></i>
                <div class="details">
                  <span class="name">Nicky</span>
                  <!-- <span class="job"> CEO </span> -->
                </div>
              </div>
            </swiper-slide>
          </swiper-container>
        </div>
      </div>
    </div>
    <script type="application/javascript" src="https://cdn.jsdelivr.net/npm/swiper@10/swiper-element-bundle.min.js"></script>

  </div>
</template>

<script>
export default {
  name: "aboutUs",
};
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Poppins:wght@100;200;300;500&family=Dosis&family=Raleway:wght@300;400&display=swap");

.bg.screen1,
.bg.screen2,
.bg.screen4 {
    font-family: "Poppins", "Raleway", "Dosis", sans-serif;

  /* height: 100vh;
    background-position: center;
    background-repeat: no-repeat;
    background-size: cover;
   
    box-sizing: border-box; */
  font-weight: 300;
  display: flex;
  min-height: 100vh;
  flex-direction: column;
  align-items: center;
  justify-content: center;

  /*  this is where the magic happens:  */
  background-attachment: fixed;
  background-position: center;
  background-size: cover;
}

.bg.screen3 {
     font-family: 'Helvetica',"Poppins", "Raleway", "Dosis", sans-serif;
     font-weight: 300;
  min-height: 100vh;
  /*  this is where the magic happens:  */
  background-attachment: fixed;
  background-position: center;
  background-size: cover;
}

.screen1 {
  background-image: url(../assets/mealPlan-photo.jpg);
}

.first-container {
  padding: 150px 200px 0px 180px;
}

.screen1 .logo-name {
  font-size: 3.5em;
  text-align: center;
  padding-bottom: 10px;
}

.screen1 .about-one {
  font-size: 1.3em;
  text-align: center;
  padding: 80px 120px 50px 45px;
}

.screen2 {
  background-color: #e6f3e6;
}

.screen2 .mission-container {
  display: flex;
}

.screen2 img {
  display: block;
  max-width: 400px;
  max-height: 600px;
  width: auto;
  height: auto;
  margin: 50px;
  box-shadow: 0px 2px 8px rgba(30, 10, 60, 0.06),
    0px 4px 12px rgba(30, 10, 60, 0.08);
  border-radius: 10px;
}

.screen2 .mission {
  padding-top: 50px;
}

.screen2 .mission-statement {
  font-size: 18px;
  padding-top: 15px;
}
.screen3 {
  background-image: url(../assets/yellow-gradient.jpg);
}

.screen3 .special-title {
  font-size: 2em;
  padding: 80px 50px 0 50px;
}

.screen3 .section {
  display: flex;
  margin: 0 auto;
  column-gap: 50px;
  height: 65vh;
  align-items: center;
  padding: 0 50px;
}

.screen3 .specialSection {
  width: 100%;
}

.specialSection .section-title {
  text-align: center;
  font-size: 26px;
  padding: 10px;
}

p.specialPara {
  text-align: center;
  font-size: 18px;
  padding: 8px;
  color: rgb(66, 55, 9);
  line-height: 28px;
}

.icon {
  text-align: center;
}
i {
  font-size: 50px;
  color: rgb(34, 174, 137);
}

.screen4 {
  /* background-color: #fbf7f2; */
  background-color: rgb(248, 238, 225);
  font-family: "Poppins", "Raleway", "Dosis", sans-serif;
  box-sizing: border-box;
}

.testimonial {
  position: relative;
  max-width: 900px;
  width: 80%;
  padding: 50px 0;
  background-color: rgb(248, 238, 225);
}
.testimonial .slide {
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
  row-gap: 20px;
}

.slide p {
  padding: 0 100px;
  text-align: center;
  font-size: 15px;
  font-weight: 300;
  color: #333;
}

.slide .quote-icon {
  font-size: 25px;
  color: #4070f4;
}

.slide .details {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.details .name {
  font-size: 16px;
  font-weight: 700;
  color: #333;
}

.details .job {
  font-size: 14px;
  font-weight: 400;
  color: #333;
}
.swiper {

  position: relative;
  overflow: clip;
  list-style: none;
  padding: 0;
  z-index: 1;
  display: block;
}
.testimonial .image {
  height: 170px;
  width: 170px;
  object-fit: cover;
  border-radius: 50%;
}


@media only screen and (max-width: 990px) {
  .first-container {
    padding: 250px 200px 0px 120px;
  }

  .screen1 .logo-name {
    font-size: 3.5em;
    text-align: center;
  }

  .screen1 .about-one {
    font-size: 1.5em;
    text-align: center;
    padding: 20px 80px 50px 20px;
  }


p.specialPara {
  text-align: center;
  font-size: 16px;
  padding: 8px;
  color: rgb(66, 55, 9);
  line-height: 24px;
}
}

@media only screen and (max-width: 600px) {
  .bg {
    width: 100%;
  }
  .first-container {
    padding: 250px 80px 0px 50px;
  }

  .screen1 .logo-name {
    font-size: 3em;
    text-align: center;
  }

  .screen1 .about-one {
    font-size: 1.4em;
    text-align: center;
  }
}
</style>
