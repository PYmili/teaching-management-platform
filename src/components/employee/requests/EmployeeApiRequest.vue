<template></template>

<script setup>
import axios from "axios";

/**
 * 分页查询员工数据接口
 * @param {string} jwt - 认证令牌
 * @param {Object} data - 分页参数对象
 * @param {number} data.start - 起始位置
 * @param {number} data.number - 查询数量
 * @returns {Promise<Array|null>} 员工数据数组或null(请求失败时)
 */
async function request(jwt, data) {
    // 构建API请求地址
    const apiUrl = `${import.meta.env.VITE_API_HOST}employee/range` + 
                 `?start=${data.start}&number=${data.number}`;

    // 发送带认证的GET请求
    return axios.get(apiUrl, {
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${jwt}`
        }
    }).then(response => {
        // 成功时返回数据部分
        return response.data.data;
    }).catch(error => {
        // 失败时返回null保持数据一致性
        return null;
    });
}

/* 暴露组件方法 */
defineExpose({
    /** 对外暴露的分页请求方法 */
    request
})
</script>

<style>
</style>