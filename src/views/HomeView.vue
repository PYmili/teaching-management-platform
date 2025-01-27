<template>
  <el-container class="container">
    <el-main>
      <HomeMainVueComponent />
    </el-main>
    <el-footer>
      <HomeFooterComponent />
    </el-footer>
  </el-container>
</template>

<script setup>
import HomeMainVueComponent from "@/components/home/HomeMain.vue";
import HomeFooterComponent from "@/components/home/HomeFooter.vue";

</script>

<style scoped>
.container {
  width: 100%;
  margin: 0;
  padding: 0;
}
:deep(.el-header) {
  width: 100%;
  --el-header-padding: 0px;
}
:deep(.el-main) {
  --el-main-padding: 0px;
}
:deep(.el-footer) {
  bottom: 0;
  height: 50px;
}
</style>