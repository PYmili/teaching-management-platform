<template>
  <el-card>
    <template #header>部门</template>
    <el-table
      :border="true"
      :data="props.tableData"
      @selection-change="handleTableSelectionChange"
    >
      <el-table-column type="selection" />
      <el-table-column property="id" label="ID">
        <template #default="scope">{{ scope.row.id }}</template>
      </el-table-column>
      <el-table-column property="departmentName" label="部门" />
      <el-table-column property="departmentDescription" label="描述" />
      <el-table-column property="parentDepartmentId" label="父级部门">
        <template #default="scope">{{
          GetParentDepartmentName(scope.row.parentDepartmentId)
        }}</template>
      </el-table-column>
      <el-table-column label="创建日期">
        <template #default="scope">{{
          parseDate(scope.row.createdAt)
        }}</template>
      </el-table-column>
    </el-table>
    <template #footer>
      <div class="buttons">
        <el-button-group>
          <el-button
            type="default"
            :icon="Plus"
            @click="handleAppendEvent"
          ></el-button>
          <el-button
            type="default"
            :icon="Delete"
            @click="handleRemoveEvent"
          ></el-button>
        </el-button-group>
      </div>
    </template>
    <TableAppendComponent 
        :dialogVisible="AppendDialogVisible"
        :deprantmentOptions="props.tableData"
        @updateDialogVisible="updateDialogVisible"
        @updateTableData="updateTableData" />
  </el-card>
</template>

<script setup>
import { ref } from "vue";
import { Plus, Delete } from "@element-plus/icons-vue";
import TableAppendComponent from "./components/TableAppend.vue";

const props = defineProps({
  tableData: Array,
});

// 表格选中项
const tableSelectionArray = ref([]);
// 添加窗口的显示控制
const AppendDialogVisible = ref(false);
const emit = defineEmits(["updateTableData"]);

/**
 * 更新添加数据的窗口值
 * @param _visible 
 */
function updateDialogVisible(_visible) {
    AppendDialogVisible.value = _visible;
}

/**
 * 发送更新表格数据的emit
 */
function updateTableData() { emit("updateTableData"); }

/**
 * 日期格式化工具
 * @param params 需要处理的数据
 */
function parseDate(params) {
  return params.split("T")[0] + " " + params.split("T")[1].split(".000")[0]; // ISO格式转本地时间格式
}

/**
 * 通过部门id获取名称
 * @param departmentId
 */
function GetParentDepartmentName(departmentId) {
  if (departmentId === null || departmentId === undefined) {
    return "暂无";
  }
  return props.tableData.find((item) => {
    return item.id === departmentId;
  }).departmentName;
}

/**
 * 处理表格选择
 * @param rows
 */
function handleTableSelectionChange(rows) {
  console.log("表格选中 rows: ", rows);
  tableSelectionArray.value = rows;
}

/**
 * 处理添加事件
 */
function handleAppendEvent() {
    if (AppendDialogVisible.value === true) {
        AppendDialogVisible.value = false;
    }
    updateDialogVisible(true);
}
/**
 * 处理移除事件
 */
function handleRemoveEvent() {}
</script>

<style scoped>
:deep(.el-card) {
  width: 100% !important;
}
:deep(.el-table) {
  width: 1600px;
}
:deep(.el-table) .warning-row {
  --el-table-tr-bg-color: var(--el-color-warning-light-9);
}
:deep(.el-table) .success-row {
  --el-table-tr-bg-color: var(--el-color-success-light-9);
}

.pagination {
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}
:deep(.el-pagination) {
  margin-top: 10px;
}

.buttons {
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 10px;
}

@media (max-width: 768px) {
  :deep(.el-table) {
    margin-top: 240px;
  }
}
</style>