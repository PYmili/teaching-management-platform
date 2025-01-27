<template>
  <el-dialog v-model="props.dialogVisible" title="添加员工" :show-close="false">
    <el-form :model="form">
      <el-form-item label="名称" required>
        <el-input v-model="form.departmentName" placeholder="输入部门的名称" />
      </el-form-item>
      <el-form-item label="描述" required>
        <el-input v-model="form.departmentDescription" placeholder="对于部门的描述"></el-input>
      </el-form-item>
      <el-form-item label="父级部门" required>
        <el-select v-model="deprantmentSelectModel" placeholder="父级部门选择">
            <el-option v-for="(item) in props.deprantmentOptions" 
                :key="item.id" 
                :label="item.departmentName" 
                :value="item.id">
            </el-option>
        </el-select>
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="buttons">
        <el-button type="primary" @click="handleOk">确定</el-button>
        <el-button type="primary" @click="handleCancel">取消</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script setup>
import { nextTick, reactive, ref } from "vue";
import { useCookies } from "vue3-cookies";
import { ElMessage } from "element-plus";
import { useRouter } from "vue-router";
import axios from "axios";

/* 组件参数定义 */
const props = defineProps({
  // 组件显示控制
  dialogVisible: Boolean,
  /** 部门选项数据 */
  deprantmentOptions: Array
});

/* 响应式数据定义 */
const deprantmentSelectModel = ref(1);  // 部门选择器绑定值
/** 表单初始化模板 */
const initForm = {
  departmentName: "",
  departmentDescription: "",
  parentDepartmentId: deprantmentSelectModel
};
const form = reactive({ ...initForm }); // 响应式表单对象

/* 工具类实例化 */
const { cookies } = useCookies();       // Cookie管理实例
const router = useRouter();             // 路由实例

/* 自定义事件定义 */
const emit = defineEmits([
  /** 更新对话框显示状态事件 */
  "updateDialogVisible",
  /** 更新表格数据事件 */ 
  "updateTableData"
]);

/* 核心业务方法 */
/**
 * 处理表单提交
 * @returns {Promise<void>} 异步操作结果
 */
async function handleOk() {
  // 表单验证
  if (!form.departmentName) {
    return ElMessage({
      type: "warning",
      message: "请输入必填数据！",
      showClose: true
    });
  }

  // 数据绑定
  form.departmentId = deprantmentSelectModel.value;

  // 权限验证
  const jwt = cookies.get('jwt');
  if (!jwt) {
    ElMessage({
      type: "warning",
      message: "管理员用户信息错误！请重新登录",
      showClose: true
    })
    cookies.remove('jwt');
    router.push("/login");
    return;
  }

  const api_url = import.meta.env.VITE_API_HOST + "department/insert";
  // 执行添加请求
  const res = await nextTick(async () => {
    return await axios.post(api_url, form, {
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
      ElMessage({
        type: "error",
        message: error.response.data.data,
        showClose: true
      });
      return false;
    });
  });

  // 结果处理
  emit("updateTableData");
  if (res === true) {
    emit('updateDialogVisible', false);
  }
}

/**
 * 处理取消操作
 * @returns {void}
 */
async function handleCancel() {
  Object.assign(form, initForm);        // 重置表单数据
  emit("updateDialogVisible", false);   // 关闭对话框
}
</script>

<style scoped>
.buttons {
    width: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
    gap: 10px;
}
</style>