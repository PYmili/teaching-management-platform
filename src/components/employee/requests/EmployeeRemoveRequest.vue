<template>
  
</template>

<script setup>
import axios from 'axios';
import { ElMessage } from 'element-plus';
import { ref } from 'vue';
import { useCookies } from 'vue3-cookies';

const API_URL = `${import.meta.env.VITE_API_HOST}` 
                + "employee/remove";
const { cookies } = useCookies();

async function request(data) {
    const jwt = cookies.get('jwt');
    return await axios.post(API_URL, data, {
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${jwt}`
        }
    }).then(response => {
        ElMessage({
            type: "success",
            message: response.data.data,
            showClose: true
        });
        return true;
    }).catch(error => {
        console.log(error);
        ElMessage({
            type: "error",
            message: error.response.data.data
        })
        return false;
    });
}

defineExpose({
    request
});
</script>

<style>

</style>