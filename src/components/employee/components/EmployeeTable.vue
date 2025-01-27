<template>
  <el-card>
    <template #header>员工管理</template>
    <el-table
      :border="true"
      :data="props.tableData"
      :row-class-name="tableRowClassName"
      @selection-change="handleTableSelectionChange"
    >
      <el-table-column type="selection" />
      <el-table-column property="id" label="ID">
        <template #default="scope">{{ scope.row.id }}</template>
      </el-table-column>
      <el-table-column property="name" label="名称" />
      <el-table-column label="部门">
        <template #default="scope">{{
          departmentMapperById(scope.row.departmentId)
        }}</template>
      </el-table-column>
      <el-table-column property="phone" label="手机号" />
      <el-table-column property="email" label="邮件" />
      <el-table-column property="qq" label="QQ" />
      <el-table-column property="wechat" label="微信" />
      <el-table-column label="是否可用">
        <template #default="scope">{{
          scope.row.available ? "是" : "否"
        }}</template>
      </el-table-column>
      <el-table-column label="创建日期">
        <template #default="scope">{{
          parseDate(scope.row.createdAt)
        }}</template>
      </el-table-column>
    </el-table>
    <div class="pagination">
      <el-pagination
        background
        layout="prev, pager, next"
        :page-size="pageSize"
        :total="total"
        v-model:current-page="currentPage"
        @next-click="handleNextClick"
        @prev-click="handlePrevClick"
        @current-change="handleNextClick"
      />
    </div>
    <template #footer>
      <div class="buttons">
        <el-button-group>
          <el-button
            type="default"
            :icon="Plus"
            @click="dialogVisible = !dialogVisible"
          ></el-button>
          <el-button
            type="default"
            :icon="Delete"
            @click="handleDeleteEmployee"
          ></el-button>
        </el-button-group>
        <el-button-group>
          <el-button type="info" :icon="Download" @click="handleExport">导出员工数据</el-button>
        </el-button-group>
      </div>
    </template>
  </el-card>
  <EmplpyeeTableAppendComponent
    :dialogVisible="dialogVisible"
    :deprantmentOptions="departments"
    @updateDialogVisible="handleUpdateDialogVisible"
    @updateTableData="updateTableData"
  />
  <EmployeeTableRemoveComponent
    :data="tableSelectionArray"
    ref="tableRemoveComponentRef"
    @updateTableData="updateTableData"
  />
</template>

<script setup>
/* 导入依赖 */
import { nextTick, onMounted, ref } from "vue";
import { ElMessage, ElTable } from "element-plus";
import axios from "axios";
import { useCookies } from "vue3-cookies";
import { Plus, Delete, Download } from "@element-plus/icons-vue";
import EmplpyeeTableAppendComponent from "./EmplpyeeTableAppend.vue";
import EmployeeTableRemoveComponent from "./EmployeeTableRemove.vue";

/* 初始化Cookie工具 */
const { cookies } = useCookies();

/* 定义组件Props */
const props = defineProps({
  tableData: Array, // 接收父组件传递的表格数据
});

/* 响应式变量定义 */
const total = ref(0); // 员工总数
const totalApiUrl = `${import.meta.env.VITE_API_HOST}employee/total`; // 总数接口URL
const pageSize = 10; // 分页大小
const currentPage = ref(1); // 当前页码
const emit = defineEmits(["updateRequset", "updateTableData"]); // 定义组件事件
const departments = ref([]); // 部门数据列表
const tableSelectionArray = ref([]); // 表格选中行数据
const dialogVisible = ref(false); // 对话框可见性
const tableRemoveComponentRef = ref(null); // 删除表格数据的组件引用

/**
 * 表格行样式处理
 * @param param0 
 */
const tableRowClassName = ({ row, rowIndex }) => {
  if (!row.available) return "warning-row"; // 不可用行添加警告样式
  return "";
};

/**
 * 日期格式化工具
 * @param params 需要处理的数据
 */
function parseDate(params) {
  return params.split("T")[0] + " " + params.split("T")[1].split(".000")[0]; // ISO格式转本地时间格式
}

/**
 * 上一页点击事件
 */
function handlePrevClick() {
  if (currentPage === 1) return;
  const start = Math.floor(currentPage.value * 10 - 10);
  emit("updateRequset", { start: start, number: 10 }); // 触发父组件数据更新
}

/**
 * 下一页点击事件
 */
function handleNextClick() {
  const start = (currentPage.value - 1) * 10;
  emit("updateRequset", { start: start, number: 10 }); // 触发父组件数据更新
}

/**
 * 对话框显示控制
 * @param visible Boolean
 */
function handleUpdateDialogVisible(visible) {
  dialogVisible.value = visible; // 更新对话框状态
}

/**
 * 表格中被选中的变化处理
 * @param rows 
 */
function handleTableSelectionChange(rows) {
  // 为选中行添加部门名称字段
  const updatedRows = rows.map((item) => ({
    ...item,
    departmentName: departmentMapperById(item.departmentId), // 映射部门ID为名称
  }));
  tableSelectionArray.value = updatedRows; // 更新选中数据
}

/**
 * 删除员工处理
 */
function handleDeleteEmployee() {
  if (tableSelectionArray.value.length === 0) {
    return ElMessage({ // 未选择时的提示
      type: "warning",
      message: "请选择要删除的用户！",
      showClose: true,
    });
  }
  tableRemoveComponentRef.value.setDialogVisible(true); // 显示删除确认对话框
}

/**
 * Excel导出处理
 */
async function handleExport() {
  const API_URL = `${import.meta.env.VITE_API_HOST}employee/export`;
  const jwt = cookies.get('jwt');

  try {
    // 请求二进制数据
    const response = await axios.get(API_URL, {
      headers: { "Authorization": `Bearer ${jwt}` },
      responseType: 'blob' // 声明响应类型为Blob
    });

    // 创建可下载文件
    const blob = new Blob([response.data], {
      type: response.headers['content-type']
    });
    const downloadUrl = window.URL.createObjectURL(blob);
    
    // 动态创建下载链接
    const link = document.createElement('a');
    link.href = downloadUrl;
    link.setAttribute('download', getFileNameFromHeaders(response.headers));
    document.body.appendChild(link);
    link.click();

    // 清理资源
    document.body.removeChild(link);
    window.URL.revokeObjectURL(downloadUrl);

    ElMessage({ type: "success", message: "文件下载成功" });

  } catch (error) {
    ElMessage({ type: "error", message: "导出失败" });
  }
}

/**
 * 从响应头解析文件名
 * @param headers Response header.
 */
function getFileNameFromHeaders(headers) {
  const contentDisposition = headers['content-disposition'];
  if (!contentDisposition) return 'employees.xlsx';
  
  // 使用正则表达式提取文件名
  const fileNameRegex = /filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/;
  const matches = fileNameRegex.exec(contentDisposition);
  return matches ? matches[1].replace(/['"]/g, '') : 'employees.xlsx';
}

/**
 * 部门ID映射名称
 * @param id 部门的id
 */
function departmentMapperById(id) {
  if (!id) { // 空值处理
    console.warn(`部门ID异常: ${id}`);
    return null;
  }
  try {
    return departments.value.find((item) => item.id === id).departmentName;
  } catch (error) {
    console.error(`部门查询失败 ID: ${id}`, error);
    return null;
  }
}

/* 部门名称查询方法 */
async function getDepartmentNameById(id) {
  const jwt = cookies.get("jwt");
  const apiUrl = `${import.meta.env.VITE_API_HOST}department/find?id=${id}`;
  return await axios
    .get(apiUrl, {
      headers: { Authorization: `Bearer ${jwt}` }
    })
    .then((response) => {
      return response.data.data.departmentName; // 返回部门名称
    })
    .catch((error) => {
      return "暂无"; // 异常返回默认值
    });
}

/**
 * 数据初始化方法
 */
async function requestEmployeeData() {
  const jwt = cookies.get("jwt");
  if (!jwt) return;

  // 获取员工总数
  await axios.get(totalApiUrl, { headers: { Authorization: `Bearer ${jwt}` } })
    .then((response) => { total.value = response.data.data });

  // 获取部门列表
  const departmentAllApiurl = `${import.meta.env.VITE_API_HOST}department/list`;
  await axios.get(departmentAllApiurl, { headers: { Authorization: `Bearer ${jwt}` } })
    .then((response) => { departments.value = response.data.data });
}

/**
 * 表格数据更新触发
 */
function updateTableData() {
  console.log("触发表格更新");
  emit("updateTableData"); // 通知父组件更新
}

/* 组件挂载钩子 */
onMounted(async () => {
  await requestEmployeeData(); // 初始化加载数据
});
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