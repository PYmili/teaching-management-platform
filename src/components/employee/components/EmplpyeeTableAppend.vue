<template>
  <EmplpyeeAppendRequest ref="appendRequestRef" />
  <el-dialog v-model="props.dialogVisible" title="添加员工" :show-close="false">
    <el-form :model="form">
      <el-form-item label="姓名" required>
        <el-input v-model="form.name" placeholder="输入员工的姓名" />
      </el-form-item>
      <el-form-item label="部门" required>
        <el-select v-model="deprantmentSelectModel" placeholder="部门选择">
            <el-option v-for="(item) in props.deprantmentOptions" 
                :key="item.id" 
                :label="item.departmentName" 
                :value="item.id">
            </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="电话号码">
        <el-input v-model="form.phone" placeholder="输入员工的电话号码" />
      </el-form-item>
      <el-form-item label="邮件">
        <el-input v-model="form.email" placeholder="输入员工的邮件" />
      </el-form-item>
      <el-form-item label="qq">
        <el-input v-model="form.qq" placeholder="输入员工的QQ号" />
      </el-form-item>
      <el-form-item label="微信">
        <el-input v-model="form.wechat" placeholder="输入员工的微信号" />
      </el-form-item>
      <el-form-item label="是否可用" required>
        <el-radio-group v-model="form.available">
            <el-radio label="是" :value="true" />
            <el-radio label="否" :value="false" />
        </el-radio-group>
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
import EmplpyeeAppendRequest from "../requests/EmployeeAppendRequest.vue";
import { useCookies } from "vue3-cookies";
import { ElMessage } from "element-plus";
import { useRouter } from "vue-router";

/* 组件参数定义 */
const props = defineProps({
  /** 对话框显示状态 */
  dialogVisible: Boolean,
  /** 部门选项数据 */
  deprantmentOptions: Array
});

/* 响应式数据定义 */
/** 表单初始化模板 */
const initForm = {
  name: "",
  departmentId: NaN,
  phone: "",
  email: "",
  qq: "",
  wechat: "",
  available: true,
};
const form = reactive({ ...initForm }); // 响应式表单对象
const deprantmentSelectModel = ref(1);  // 部门选择器绑定值
const appendRequestRef = ref(null);     // 子组件实例引用

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
  if (!form.name) {
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

  // 执行添加请求
  const res = await nextTick(async () => {
    return await appendRequestRef.value.request(form, jwt);
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