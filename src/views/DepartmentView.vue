<template>
  <el-container>
    <el-main>
      <DepartmentMainComponent
        :table-data="tableData"
        @updateTableData="updateTableData"
      />
    </el-main>
  </el-container>
</template>

<script setup>
import { ref } from "vue";
import axios from "axios";
import { ElMessage } from "element-plus";
import { onMounted } from "vue";
import { useCookies } from "vue3-cookies";
import DepartmentMainComponent from "@/components/department/DepartmentMain.vue";

// 工具类
const { cookies } = useCookies();
// 变量
const tableData = ref([]);

async function getTableDataRequest() {
  const jwt = cookies.get("jwt");
  if (jwt === null || jwt === undefined) {
    ElMessage({
      type: "error",
      message: "错误！请尝试重新登录！",
      showClose: true,
    });
    return console.error("没有找到jwt: {}", jwt);
  }

  const api_url = import.meta.env.VITE_API_HOST + "department/list";
  await axios
    .get(api_url, {
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${jwt}`,
      },
    })
    .then((response) => {
      tableData.value = response.data.data;
    })
    .catch((error) => {
      ElMessage({
        type: "error",
        message: "服务器请求错误！",
        showClose: true,
      });
      console.error(error);
    });
}

/**
 * 更新数据的emit
 */
async function updateTableData() { await getTableDataRequest(); }

onMounted(async () => {
  await getTableDataRequest();
});
</script>

<style scoped>
:deep(.el-main) {
  height: 90vh;
  display: flex !important;
  justify-content: center !important;
  align-items: center !important;
}
</style>