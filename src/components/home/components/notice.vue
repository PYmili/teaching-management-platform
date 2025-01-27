<template>
  <el-card>
    <template #header>
      <div class="notice-header">
        <div class="notice-title">{{ title }}</div>
        <div class="notice-date">{{ date }}</div>
      </div>
    </template>
    <el-collapse>
      <el-collapse-item title="详细内容 (点击展开)">
        <div v-html="content"></div>
      </el-collapse-item>
    </el-collapse>
  </el-card>
</template>

<script setup>
import { ref } from "vue";

const { title, date, content } = defineProps({
    title: String,
    date: String,
    content: String
})
</script>

<style scoped>
.notice-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.notice-date {
  font-size: 15px;
}

:deep(.el-card__header) {
  font-size: 17px;
}

.card-ul {
  margin-left: -15px;
}

@media (max-width: 768px) {
  :deep(.el-card__header) {
    font-size: 15px;
  }
}
</style>