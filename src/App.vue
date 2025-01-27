<template>
  <el-container>
    <el-header>
      <AppHeaderComponent :activeIndex="activeIndex" />
    </el-header>
    <el-main>
      <router-view></router-view>
    </el-main>
  </el-container>
</template>

<script setup>
import { ref } from "vue";
import AppHeaderComponent from "@/components/app/AppHeader.vue";

const activeIndex = ref('/');
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
}
</style>