<template>
  <div>
    <div class="main">
      <div class="board" v-for="(item, index) in newReleaseList" :key="item.movieId">
        <div class="left">
          <i class="board-index">{{ index + 1 }}</i>
        </div>
        <div class="middle1">
          <a :href="'/movieInfo/' + item.movieId">
            <img :src="global.base + JSON.parse(item.moviePoster)[0]" :alt="item.movieName">
          </a>
        </div>
        <div class="middle2">
          <a :href="'/movieInfo/' + item.movieId">
            <p class="name">{{ item.movieName }}</p>
          </a>
          <p class="releaseTime">上映时间：{{ formatReleaseDate(item.releaseDate) }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "NewReleaseList",
  data() {
    return {
      queryInfo: {
        pageNum: 1,
        pageSize: 10,
      },
      newReleaseList: []
    };
  },
  created() {
    this.getNewReleaseMovies();  // 调用正确的方法
  },
  methods: {
    // 获取新电影榜单数据
    async getNewReleaseMovies() {
      try {
        console.log("Attempting to fetch new releases...");
        const { data: resp } = await axios.get("/newReleases", { params: this.queryInfo });  // 使用正确的参数
        console.log("Response received:", resp);

        // 检查是否返回了数据
        if (!resp || resp.length === 0) {
          console.log("No new release movies found.");
          this.$message.warning('暂无新电影上榜');  // 显示暂无数据的提示信息
          this.newReleaseList = [];  // 确保 newReleaseList 为空数组
        } else {
          this.newReleaseList = resp;  // 将新电影榜单数据赋值给 newReleaseList
        }
      } catch (error) {
        console.error('Error fetching new releases:', error);
        this.$message.error('Failed to load new releases.');
      }
    },

    // 格式化上映时间
    formatReleaseDate(date) {
      return date.split(" ")[0]; // 返回日期部分
    }
  }
}
</script>


<style scoped>

.main{
  width: 950px;
  margin: 0 auto;
  margin-top: 70px;
}

.board{
  display: flex;
  margin: 25px 0;
}

.left{
  display: flex;
  align-items: center;
  margin-right: 25px;
}

.middle1{
  display: flex;
  align-items: center;
}

.middle2{
  display: flex;
  /*align-items: center;*/
  flex-direction: column;
  justify-content: center;
  margin-left: 25px;
  width: 300px;
}

.middle2 > a {
  color: #373d41;
  font-size: 26px;
  text-decoration: none;
}

.name, .star, .releaseTime{
  margin-top: 8px;
  margin-bottom: 8px;
}

.releaseTime{
  color: #999999;
}

.right{
  display: flex;
  font-size: 40px;
  font-weight: 700;
  font-style: italic;
  color: #ffb400;
  margin-left: 320px;
  align-items: center;
}

.board-index{
  background: #ffb400;
  color: #fff;
  display: inline-block;
  width: 50px;
  height: 50px;
  line-height: 50px;
  text-align: center;
  font-size: 18px;
  font-weight: 700;
  align-items: center;
}

img{
  width: 160px;
  height: 220px;
}

</style>
