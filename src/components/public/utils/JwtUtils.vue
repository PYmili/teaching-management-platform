<template></template>

<script setup>
import axios from "axios";
import { useCookies } from "vue3-cookies";

const { cookies } = useCookies();
const apiUrl = `${import.meta.env.VITE_API_HOST}jwt/verify`;
const JWT = cookies.get('jwt');

async function verfiyJwt() {
  if (JWT === null) return false;
  return await axios.post(apiUrl + `?jwt=${JWT}`,{}, { 
    headers: { "Content-Type": "application/json" } 
  }).then(response => {
    // console.log(response.data);
    if (response.data.data === 'success') {
      return true;
    } {
      return false;
    }
  }).catch(error => { 
    console.error(error.response);
    return false;
  });
}

defineExpose({
  verfiyJwt,
});
</script>

<style>
</style>