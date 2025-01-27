<template>
  <div class="main">
    <div class="carousel">
      <el-carousel :interval="3000" type="card" height="300px">
        <el-carousel-item v-for="item in carouselImages" :key="item">
          <img class="carousel-img" :src="item" />
        </el-carousel-item>
      </el-carousel>
    </div>
    <el-container>
      <el-main>
        <div class="el-main-content">
          <div class="title">最新通知</div>
          <div class="notice">
            <div class="notice-list">
              <NoticeComponent
                v-for="(item, index) in noticeData"
                :key="index"
                :title="item.title"
                :date="item.date"
                :content="item.content"
              />
            </div>
          </div>
        </div>
      </el-main>
    </el-container>
  </div>
</template>

<script setup>
import { onMounted, ref } from "vue";

// network request
import axios from "axios";

import NoticeComponent from "./components/notice.vue";

const carouselImages = ref([
  import.meta.env.BASE_URL + "images/home/home-1.jpg",
  import.meta.env.BASE_URL + "images/home/home-2.jpg",
  import.meta.env.BASE_URL + "images/home/home-3.jpg",
  import.meta.env.BASE_URL + "images/home/home-4.jpg",
]);

const noticeData = ref([]);
const apiUrl = `${
  import.meta.env.VITE_API_HOST
}notice/find-range?start=1&number=3`;

onMounted(() => {
  axios
    .get(apiUrl)
    .then((response) => {
      let responseData = response.data.data;
      noticeData.value = responseData.map((item) => ({
        title: item.title,
        date: `${item.createdAt.split("T")[0]} 
            ${item.createdAt.split("T")[1].split(".")[0]}`,
        content: item.content,
      }));
    })
    .catch((error) => {
      console.log(error);
    });
});
</script>

<style scoped>
/* .main {
  background-color: #00000093;
} */

.carousel {
  width: 100%;
}

.carousel-img {
  width: 100%;
  height: 300px;
  object-fit: cover;
}

:deep(.el-main) {
  display: flex !important;
  justify-content: center !important;
}

.el-main-content {
  width: 1200px;
}

.title {
  margin-top: 30px;
  font-size: 40px;
  color: rgb(38, 0, 255);
  margin-left: 30px;
  margin-right: 30px;
}

.title::after {
  display: block;
  content: "";
  width: 100%;
  height: 5px;
  background-color: #969696;
}

.notice {
  width: 100%;
  margin-top: 20px;
  display: flex;
  justify-content: center;
  margin-bottom: 20px;
}

.notice-list {
  width: 90%;
  display: flex;
  flex-direction: column;
  gap: 10px;
}

@media (max-width: 768px) {
  .notice {
    margin-top: 10px;
    display: grid;
    gap: 10px;
  }
  .notice-list {
    width: 320px;
  }
}
</style>