<template>
  <div id="app">

    <div class="nav-bar noprint">
      <nav-bar></nav-bar>
    </div>
    
    <router-view />

    <div class="footer-parent noprint">
      <page-bottom class="footer"/>
    </div>
  
  </div>
</template>

<script>
import PageBottom from './components/PageBottom.vue'
import NavBar from './components/NavBar.vue'

export default {
  components: { 
    PageBottom,
    NavBar
   },
};
</script>

<style>

* {
  margin: 0;
  padding:0;
}

#content {
  padding-bottom: 2.5rem;
}
nav-bar {
  position: fixed;
}
router-view {
  grid-area: view;
}
.footer {
  grid-area: footer;
  /* position: absolute; */
  bottom: 0;

  /* height: 2.5rem; */
  /* margin: 10%; */
}
@media print{
.noprint{
  display: none;
}
}
</style>

