<template>
  <el-container>
    <el-main>
      <LoginMainComponent :UserForm="UserForm" />
    </el-main>
  </el-container>
</template>

<script setup>
import { ref } from "vue";
import LoginMainComponent from "@/components/login/LoginMain.vue";

const UserForm = ref({
    username: "",
    password: ""
});

</script>

<style scoped>
:deep(.el-main) {
    height: 90vh;
    display: flex !important;
    justify-content: center !important;
    align-items: center;
}
</style>