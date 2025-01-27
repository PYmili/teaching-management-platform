<template>
  
</template>

<script setup>
import { ref } from "vue";
import axios from "axios";
import { ElMessage } from "element-plus";

const API_URL = `${import.meta.env.VITE_API_HOST}` 
                + "employee/insert";

async function request(data, jwt) {
    return await axios.post(API_URL, data, {
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${jwt}`
        }
    }).then(response => {
        ElMessage({
            type: "success",
            message: `${response.data.data}`,
            showClose: true
        });
        return true;
    }).catch(error => {
        console.error(error);
        ElMessage({
            type: "error",
            message: `${error.response.data.data}`,
            showClose: true
        });
        return false;
    });
}

defineExpose({
    request
})
</script>

<style>

</style>