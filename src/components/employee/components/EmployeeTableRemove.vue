<template>
  <el-dialog v-model="dialogVisible" title="删除员工" :show-close="false">
    <el-table :border="true" :data="props.data">
      <el-table-column property="id" label="ID">
        <template #default="scope">{{ scope.row.id }}</template>
      </el-table-column>
      <el-table-column property="name" label="名称" />
      <el-table-column label="部门">
        <template #default="scope">{{ scope.row.departmentName }}</template>
      </el-table-column>
      <el-table-column property="phone" label="手机号" />
      <el-table-column property="email" label="邮件" />
      <el-table-column property="qq" label="QQ" />
      <el-table-column property="wechat" label="微信" />
      <el-table-column label="是否可用">
        <template #default="scope">{{ scope.row.available ? "是" : "否" }}</template>
      </el-table-column>
      <el-table-column label="创建日期">
        <template #default="scope">{{ scope.row.createdAt.split("T")[0] }}</template>
      </el-table-column>
    </el-table>
    <template #footer>
      <div class="buttons">
        <el-button type="primary" @click="handleOk">确定</el-button>
        <el-button type="primary" @click="handleCancel">取消</el-button>
      </div>
    </template>
  </el-dialog>
  <EmployeeRemoveRequest ref="employeeRemoveRequestRef" />
</template>

<script setup>
import { nextTick, ref } from "vue";
import EmployeeRemoveRequest from "../requests/EmployeeRemoveRequest.vue";

// 从父组件获取数据
const props = defineProps({
  data: Array, // 接收员工数据数组
});

/* 自定义事件定义 */
const emit = defineEmits(["updateTableData"]); // 定义表格更新事件

/* 响应式状态管理 */
const dialogVisible = ref(false); // 对话框显示状态
const employeeRemoveRequestRef = ref(null); // 删除请求组件实例引用


/**
 * 设置当前对话框是否显示
 * @param _visible Boolean
 */
function setDialogVisible(_visible) {
  dialogVisible.value = _visible;
}

/**
 * 处理确认事件，发送删除员工请求
 */
async function handleOk() {
  // 前置条件检查
  if (props.data.length === 0) return;

  let res = true; // 操作结果标识
  
  // 等待DOM更新后执行删除操作
  await nextTick(async () => {
    // 遍历删除所有选中项
    props.data.map(async (item) => {
      res = await employeeRemoveRequestRef.value.request(item.id);
    });
  });

  // 操作后处理
  emit("updateTableData"); // 触发表格更新
  if (res === true) dialogVisible.value = false; // 成功时关闭对话框
}

/**
 * 取消操作
 */
function handleCancel() {
  dialogVisible.value = false; // 直接关闭对话框
}

/* 暴露组件方法 */
defineExpose({
  setDialogVisible
});
</script>

<style scoped>
</style>