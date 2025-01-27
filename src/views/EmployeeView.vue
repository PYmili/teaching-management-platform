<template>
  
  <el-container>
    <el-main>
      <EmployeeMainComponent />
    </el-main>
  </el-container>
</template>

<script setup>
import { ref } from "vue";
import EmployeeMainComponent from "@/components/employee/EmployeeMain.vue";
</script>

<style scoped>
:deep(.el-main) {
  width: 90vh;
  display: flex !important;
  justify-content: center !important;
  align-items: center !important;
}
</style>