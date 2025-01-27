<template>
  <JwtUtils ref="jwtUtilsRef" />
  <EmployeeApiRequest ref="requestRef" />
  <EmployeeTableComponent 
    :tableData="tableData" 
    ref="tableRef" 
    @updateRequset="updateRequsetData" 
    @updateTableData="updateTableData"/>
</template>

<script setup>
import { onMounted, ref, nextTick } from "vue";
import { ElMessage } from "element-plus";
import { useRouter } from "vue-router";
import JwtUtils from "@/components/public/utils/JwtUtils.vue";
import EmployeeTableComponent from "./components/EmployeeTable.vue";
import EmployeeApiRequest from "@/components/employee/requests/EmployeeApiRequest.vue";
import { useCookies } from "vue3-cookies";

const jwtUtilsRef = ref(null);
const router = useRouter();
const { cookies } = useCookies();
const requestRef = ref(null);
const tableData = ref([]);
const tableRef = ref(null);
const requestData = ref({
  start: 0,
  number: 10
});

async function tableRequest(params) {
  await nextTick(async () => {
    const verfiyResult = await jwtUtilsRef.value.verfiyJwt();
    if (verfiyResult === false) {
      ElMessage({
        type: "error",
        message: "您未登录！",
        showClose: true,
      });
      return router.push("/login");
    }

    const jwt = cookies.get("jwt");
    const response = await requestRef.value.request(jwt, requestData.value);
    tableData.value = response;
    // console.log(tableData.value);
  });
}

const updateRequsetData = async (params) => {
  // console.log(params);
  requestData.value = params;
  await tableRequest();
}

async function updateTableData() {
  return await tableRequest();
}

onMounted(async () => {
  await tableRequest();
});
</script>

<style scoped>
</style>