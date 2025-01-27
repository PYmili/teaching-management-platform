<template>
  <JwtUtils ref="jwtUtilsRef" />
  <LoginRequest ref="requestRef" />
  <el-card>
    <el-image class="el-card-img" :src="elCardImgUrl" :fit="fit"></el-image>
    <el-form :model="UserForm" status-icon label-width="auto">
      <el-form-item label="用户名">
        <el-input v-model="UserForm.username" placeholder="请输入用户名" />
      </el-form-item>
      <el-form-item label="密码">
        <el-input
          v-model="UserForm.password"
          placeholder="请输入密码"
          type="password"
          show-password
        />
      </el-form-item>
      <el-form-item label="" class="form-button-group">
        <el-button type="primary" @click="hanldeLogin">登录</el-button>
        <el-button type="danger">取消</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>

<script setup>
import { nextTick, onMounted, ref } from "vue";
import LoginRequest from "./requests/LoginRequest.vue";
import { useRouter } from "vue-router";
import JwtUtils from "../public/utils/JwtUtils.vue";
import { ElMessage } from "element-plus";

const router = useRouter();
const props = defineProps({
  UserForm: {},
});
const { UserForm } = props;
const elCardImgUrl = ref("/images/login/alp-2724664.jpeg");
const fit = ref("fill");
const jwtUtilsRef = ref(null);
const requestRef = ref(null);

async function hanldeLogin(params) {
  return await nextTick(async () => {
    if (requestRef.value == null)
      return console.error(`login request ref is ${requestRef}`);
    if (requestRef.value.init(UserForm) == false) {
      return console.error(`request params value error!`);
    }
    const response = await requestRef.value.request();
    if (response === true) {
      router.push("/");
    }
  });
}

onMounted(async () => {
  await nextTick(async () => {
    const verfiyResult = await jwtUtilsRef.value.verfiyJwt();
    if (verfiyResult === true) {
      ElMessage({
        type: "warning",
        message: "您已登录！",
        showClose: true,
      });
      return router.push("/");
    }
  });
});
</script>

<style scoped>
:deep(.el-card__body) {
  width: 900px !important;
  display: flex !important;
  justify-content: flex-start;
  align-items: center;
}
.el-card-img {
  width: 400px;
  border-radius: 10px 0 0 10px;
}
:deep(.el-form) {
  width: 600px;
  height: 400px;
  padding: 20px;
  background-color: #9b97971f;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
:deep(.el-form-item__label) {
  font-weight: 600;
}
:deep(.el-input) {
  width: 200px;
}
:deep(.form-button-group) .el-form-item__content {
  display: flex !important;
  justify-content: center !important;
  gap: 20px;
}

@media (max-width: 768px) {
  :deep(.el-image) {
    display: none !important;
  }
  :deep(.el-form) {
    width: auto;
    height: auto;
    padding: 20px;
    background-color: transparent;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
  }
  :deep(.el-form-item__label) {
    font-weight: 400;
  }
  :deep(.el-card__body) {
    width: auto;
    display: flex !important;
  }
}
</style>