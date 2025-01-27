<template>
  <el-affix>
    <el-menu
      background-color="#ffffff"
      text-color="#000000"
      active-text-color="#ff0000"
      :default-active="activeIndex"
      mode="horizontal"
      :ellipsis="false"
      @select="handleSelect"
      :router="true"
    >
      <el-menu-item>
        <img style="width: 50px" src="/images/login/logo.svg" alt="logo" />
      </el-menu-item>
      <el-menu-item index="/">首页</el-menu-item>
      <el-sub-menu index="other">
        <template #title>更多</template>
        <el-menu-item index="/employee">员工管理</el-menu-item>
        <el-menu-item index="/department">部门管理</el-menu-item>
      </el-sub-menu>
      <el-sub-menu index="administrator" class="administrator">
        <template #title>管理员</template>
        <el-menu-item index="/login">登录/注册</el-menu-item>
      </el-sub-menu>
    </el-menu>
  </el-affix>
</template>

<script lang="ts" setup>
import { ref, watch } from "vue";
import { useRoute } from "vue-router";

const route = useRoute();
const activeIndex = ref("/");

const handleSelect = (key: string, keyPath: string[]) => {
  // console.log(key, keyPath);
};

// 监听路由变化并更新 activeIndex
watch(() => route.path, newPath => {
  activeIndex.value = newPath;
});
</script>

<style scoped>
:deep(.el-menu) {
  margin: 0;
  padding: 0;
  width: 100%;
}
:deep(.el-menu--horizontal) {
  display: flex !important;
  justify-content: center !important;
}
.items {
  display: flex;
}

.administrator {
  margin-left: 1000px;
}

@media (max-width: 768px) {
  .administrator {
    margin-left: 0;
  }
}
</style>