<template></template>

<script setup>
import { ref } from "vue";
// request
import axios from "axios";
// cookies
import { useCookies } from "vue3-cookies";
import { ElMessage } from "element-plus";
// hash
import CryptoJS from "crypto-js";

const { cookies } = useCookies();
const requestUrl = `${import.meta.env.VITE_API_HOST}user/login`;
const requestData = ref({
  username: null,
  password: null,
});
const message = (type, msg) => {
  ElMessage({
    showClose: true,
    message: msg,
    type: type,
  });
  if (type === "error") return false;
  else return true;
};

function init(params) {
  if (!params) {
    return message("error", "请求参数不能为空！");
  }
  if (params.username === "" || params.password === "") {
    return message("error", "用户名或密码不能为空！");
  }
  requestData.value = params;
}

async function request() {
//   console.log(requestData.value);
  const passwrodHash = CryptoJS.SHA256(requestData.value.password).toString();

  return await axios({
    method: "post",
    url: requestUrl,
    data: {
        username: requestData.value.username,
        password: passwrodHash
    },
    responseType: "json",
  })
    .then((response) => {
      if (response.data.status !== 200) {
        return message("error", response.data.data);
      } else {
        cookies.set("jwt", response.data.data);
        return message("success", "登录成功！");
      }
    })
    .catch((error) => {
      console.error(error);
      return message("error", error.response.data.data);
    });
}

defineExpose({
  init,
  request,
});
</script>

<style>
</style>