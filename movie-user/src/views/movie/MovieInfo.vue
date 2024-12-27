<template>
  <div class="movie-container">
    <div class="header">
      <div class="header-inner clearfix">
        <div class="movie-info-left">
          <div class="avatar-shadow">
            <img class="avatar" :src="movieInfo.moviePoster" alt="电影海报">
          </div>
        </div>
        <div class="movie-info-right">
          
          <div class="movie-info-msg">
            <h1 class="movie-name">{{movieInfo.movieName}}</h1>
            <h1>&nbsp;|</h1>
            <ul>
              <li>{{movieInfo.movieCategoryList}}</li>
              <li>{{movieInfo.movieArea}} / {{movieInfo.movieLength}}分钟</li>
              <li>{{movieInfo.releaseDate}} 上映</li>
            </ul>
          </div>
          <div class="movie-info-btn">
            <el-button class="buy-btn" type="primary" @click="toChooseSession">特惠购票</el-button>
          </div>
          <div class="movie-info-score">
            <div class="movie-index box-office-container">
              <span class="movie-index-title">累计票房</span>
              <div class="box-office-price">
                <span class="box-office">{{movieInfo.movieBoxOffice}}</span>
                <span class="unit">元</span>
              </div>
            </div>
          </div>
          <div class="movie-index rating-container">
    <el-rate v-model="averageRating" disabled></el-rate>
    <span class="rating-score">{{ averageRating.toFixed(1) }}</span>
  </div>
        </div>
      </div>
    </div>
    <div class="movie-info-detail-container">
      <div class="movie-info-detail clearfix">
        <div class="movie-info">
          <el-tabs v-model="activeName" type="card">
            <!-- 电影简介 Tab -->
            <el-tab-pane label="简介" name="introduction">
              <div class="tab-body">
                <h2>{{ movieInfo.movieName }}</h2>
                <p>{{ movieInfo.movieIntroduction }}</p>
              </div>
            </el-tab-pane>

            <!-- 电影图片 Tab -->
            <el-tab-pane label="图片" name="pictures">
    <div class="tab-body">
      <div class="movie-pictures">
        <div v-for="(pic, index) in movieInfo.moviePictures" :key="index" class="picture-item">
          <img :src="pic" :alt="`Movie Picture ${index + 1}`" class="movie-picture">
        </div>
      </div>
    </div>
  </el-tab-pane>

            <!-- 评论与评分 Tab -->
            <el-tab-pane label="评论与评分" name="comments">
              <div class="tab-body">
                <div class="comment-section">
                  <h3>评分</h3>
                  <el-rate v-model="rating" show-text></el-rate>

                  <el-input
                    type="textarea"
                    placeholder="写下你的评论..."
                    v-model="comment"
                    rows="4"
                    style="margin-top: 10px;">
                  </el-input>
                  <el-button type="primary" @click="submitReview" style="margin-top: 10px;">提交评论</el-button>

                  <h3 style="margin-top: 20px;">所有评论</h3>
                  <div class="filter-section" style="margin-bottom: 20px;">
  <el-button type="text" @click="filterComments('latest')">最新</el-button>
  <el-button type="text" @click="filterComments('highest')">最好</el-button>
  <el-button type="text" @click="filterComments('lowest')">最差</el-button>
</div>
      


                  <!-- 条件渲染：显示评论列表或提示 -->
                  <div v-if="commentsList.length > 0" class="reviews-container">
                    <div v-for="review in commentsList" :key="review.id" class="review-item">
                      <div class="review-header">
                        <img :src="review.userPicture" alt="User Avatar" class="user-avatar">
                        <div class="user-info">
                          <p class="user-name"><strong>{{ review.userName }}</strong></p>
                          <el-rate v-model="review.rating" disabled show-score text-color="#ff9900"></el-rate>
                        </div>
                      </div>
                      <p class="comment-content">{{ review.comment }}</p>
                      <div class="review-footer">
                        <span class="comment-date">{{ formatDate(review.reviewDate) }}</span>
                        
                      </div>
                    </div>
                  </div>
                  <div v-else class="no-comments">
                    <p>暂无评论，快来留下第一条评论吧！</p>
                  </div>
                </div>
              </div>
            </el-tab-pane>
          </el-tabs>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import movieItem from './../../components/movie/movie-item';
import moment from 'moment';

export default {
  name: "MovieInfo",
  components: {
    movieItem
  },
  data() {
  return {
    userInfo: {
      userPicture: '', // 设置默认值
      // 其他属性...
    },
    movieInfo: {
      moviePictures: []
    },
    movieId: this.$route.params.movieId,
    activeName: 'introduction',
    colors: ['#99A9BF', '#F7BA2A', '#FF9900'],
    httpURL: this.global.base,
    rating: 0, // 评分
    comment: '', // 评论内容
    commentsList: [], // 存储所有评论
    averageRating: 0 // 电影平均评分
  };
},

  created() {
    this.getMovieInfo();
    this.getComments();  // 加载评论列表
    this.getAverageRating(); // 获取平均评分
  },
  methods: {
      // 获取电影评分
  async getAverageRating() {
    try {
      const { data: res } = await axios.get(`sysMovie/${this.movieId}/rating`);
      if (res.code === 200) {
        this.averageRating = res.data; // 设置平均评分
      } else {
        this.$message.error('获取评分失败');
      }
    } catch (error) {
      this.$message.error('获取评分失败');
    }
  },
    async filterComments(type) {
  try {
    const { data: res } = await axios.get(`/sysMovie/${this.movieId}/reviews`, {
      params: { sortType: type }
    });
    if (res.code === 200) {
      this.commentsList = res.data; // 更新评论列表
    } else {
      this.$message.error('获取评论失败');
    }
  } catch (error) {
    this.$message.error('获取评论失败');
  }
},

    // 获取电影信息
    async getMovieInfo() {
      const { data: res } = await axios.get('sysMovie/find/' + this.movieId);
      if (res.code !== 200) return this.$message.error('数据查询失败');
      
      this.movieInfo = res.data;
      this.movieInfo.moviePoster = this.httpURL + JSON.parse(res.data.moviePoster)[0];
      this.movieInfo.moviePictures = JSON.parse(this.movieInfo.moviePictures).map((obj) => {
        return this.httpURL + obj;
      });
      this.movieInfo.movieCategoryList = this.movieInfo.movieCategoryList.map((obj) => {
        return obj.movieCategoryName;
      }).join(" ");
    },

    // 获取评论列表
    async getComments() {
      const generateRandomName = () => {
  const firstNames = ['Alice', 'Bob', 'Charlie', 'Diana', 'Ethan', 'Fiona', 'George', 'Hannah', 'Isaac', 'Julia'];
  const lastNames = ['Smith', 'Johnson', 'Williams', 'Brown', 'Jones', 'Garcia', 'Miller', 'Davis', 'Rodriguez', 'Martinez'];
  return `${firstNames[Math.floor(Math.random() * firstNames.length)]} ${lastNames[Math.floor(Math.random() * lastNames.length)]}`;
};



const generateRandomColor = () => {
  const colors = ['#FF6633', '#FFB399', '#FF33FF', '#FFFF99', '#00B3E6', 
                  '#E6B333', '#3366E6', '#999966', '#99FF99', '#B34D4D'];
  return colors[Math.floor(Math.random() * colors.length)];
};


try {
    const { data: res } = await axios.get(`sysMovie/${this.movieId}/reviews`);
    if (res.code === 200) {
      this.commentsList = res.data.map((comment) => {
        const name = generateRandomName();
        const color = generateRandomColor();
        return {
          ...comment,
          userName: name,
          userPicture: this.generateRandomAvatar(name, color) // 这里使用生成的头像URL
        };
      });
    } else {
      this.$message.error('加载评论失败');
    }
  } catch (error) {
    this.$message.error('加载评论失败');
  }
},

generateRandomAvatar(name, color) {
  const initials = name.split(' ').map(word => word.charAt(0)).join('');
  return `data:image/svg+xml;base64,${btoa(`
    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 40 40">
      <circle cx="20" cy="20" r="20" fill="${color}"/>
      <text x="50%" y="50%" text-anchor="middle" fill="white" font-size="16" font-family="Arial, sans-serif" dy=".3em">${initials}</text>
    </svg>
  `)}`;
}
,

    // 切换到图片展示
    showPictures() {
      this.activeName = 'pictures';
    },

    // 跳转到购票页面
    toChooseSession() {
      let cinemaId = 1;
      this.$router.push('/chooseSession/' + cinemaId);
    },

    // 提交评论
    async submitReview() {
      if (this.comment.trim() === '') {
        this.$message.warning('评论内容不能为空！');
        return;
      }

      const userId = 1; // 假设用户ID为1，实际应该从登录信息获取
      const reviewData = {
        userId,
        rating: this.rating,
        comment: this.comment
      };

      // 提交评论到后端 
      try {
        const { data: res } = await axios.post(`sysMovie/${this.movieId}/reviews`, reviewData);
        if (res.code === 200) {
          this.$message.success('评论提交成功');
          this.comment = ''; // 清空评论
          this.rating = 0; // 清空评分
          this.getComments(); // 获取最新的评论
        } else {
          this.$message.error('提交评论失败');
        }
      } catch (error) {
        this.$message.error('提交评论失败');
      }
    },

    // 格式化日期
    formatDate(dateString) {
      return moment(dateString).format('YYYY-MM-DD');
    },

    // 点赞评论
    async likeReview(reviewId) {
      // 这里可以添加点赞的逻辑
      try {
        // 假设点赞是通过POST请求实现的
        const { data: res } = await axios.post(`sysMovie/${this.movieId}/reviews/${reviewId}/like`);
        if (res.code === 200) {
          this.$message.success('点赞成功！');
          // 更新评论列表以反映点赞状态的变化
          this.getComments();
        } else {
          this.$message.error('点赞失败');
        }
      } catch (error) {
        this.$message.error('点赞失败');
      }
    }
  }
};
</script>



<style scoped>
/* 容器整体样式 */
.movie-container {
  padding: 40px;
  background-color: #f7f7f7;
  font-family: 'Helvetica Neue', Arial, sans-serif;
  max-width: 1200px;
  margin: 0 auto;
}

/* 头部信息样式 */
.header-inner {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding-bottom: 20px;
  border-bottom: 1px solid #ddd;
  margin-bottom: 30px;
}

.movie-info-left {
  flex: 1;
  text-align: center;
}

.avatar-shadow {
  padding: 15px;
  background-color: #fff;
  border-radius: 10px;
  box-shadow: 0px 4px 15px rgba(0, 0, 0, 0.1);
}

.avatar {
  width: 300px;
  height: auto;
  border-radius: 15px;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.avatar:hover {
  transform: scale(1.05);
  box-shadow: 0px 6px 20px rgba(0, 0, 0, 0.2);
}

/* 电影信息样式 */
.movie-info-right {
  flex: 2;
  padding-left: 30px;
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.movie-info-msg h1 {
  font-size: 32px;
  font-weight: bold;
  color: #333;
  margin-bottom: 15px;
}

.movie-info-msg ul {
  list-style: none;
  padding-left: 0;
}

.movie-info-msg li {
  font-size: 16px;
  color: #666;
  margin-bottom: 10px;
}

.movie-info-btn {
  margin-top: 25px;
}

.buy-btn {
  width: 220px;
  height: 50px;
  font-size: 18px;
  font-weight: bold;
  border-radius: 25px;
  background-color: #ff5a5a;
  color: #fff;
  border: none;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.buy-btn:hover {
  background-color: #ff3838;
}

/* 电影评分与票房信息 */
.movie-info-score {
  margin-top: 20px;
  font-size: 16px;
  color: #888;
}

.box-office-container {
  display: flex;
  align-items: center;
  margin-top: 10px;
  font-size: 18px;
  color: #333;
}

.box-office-price {
  display: flex;
  align-items: flex-end;
}

.box-office {
  font-size: 28px;
  font-weight: bold;
  color: #ff5a5a;
}

.unit {
  font-size: 14px;
  margin-left: 5px;
  color: #999;
}

/* Tabs 样式 */
.el-tabs__header {
  background-color: #f7f7f7;
  border-radius: 10px;
  padding: 15px;
}

.el-tabs__item.is-active {
  color: #ff5a5a !important;
}

.el-tabs__item {
  font-size: 18px;
  font-weight: bold;
  color: #333;
  padding-bottom: 10px;
}

/* 评论区 */
.comment-section {
  padding: 25px;
  background-color: #fff;
  border-radius: 10px;
  box-shadow: 0px 2px 10px rgba(0, 0, 0, 0.05);
  margin-top: 30px;
}

.comment-section h3 {
  font-size: 22px;
  margin-bottom: 15px;
  color: #333;
}

.comment-section .el-rate {
  margin-bottom: 15px;
}

.comment-section .el-input {
  margin-bottom: 15px;
  border-radius: 8px;
  padding: 12px;
}

.comment-section .el-button {
  margin-top: 15px;
  padding: 12px 30px;
  border-radius: 25px;
  background-color: #ff5a5a;
  color: #fff;
}

.comment-section .el-button:hover {
  background-color: #ff3838;
}

/* 评论列表样式 */
.reviews-container {
  margin-top: 20px;
}

.review-item {
  padding: 20px;
  margin-bottom: 15px;
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
}

.review-item:hover {
  transform: translateY(-5px);
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.15);
}

.review-header {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}

.user-avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  margin-right: 10px;
}

.user-info {
  flex: 1;
}

.user-name {
  font-size: 16px;
  margin-bottom: 5px;
}

.comment-content {
  margin-bottom: 10px;
  font-size: 14px;
  color: #333;
  line-height: 1.5;
}

.review-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 12px;
  color: #888;
}

.comment-date {
  margin-right: 10px;
}

.like-btn {
  padding: 0;
  font-size: 12px;
  color: #ff5a5a;
  transition: all 0.3s ease;
}

.like-btn:hover {
  color: #ff3838;
  transform: scale(1.1);
}

.no-comments {
  text-align: center;
  padding: 30px;
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  font-size: 16px;
  color: #666;
}

/* 图片展示部分 */
.movie-pictures {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
  gap: 15px;
}

.picture-item {
  position: relative;
  overflow: hidden;
}

.movie-picture {
  width: 100%;
  height: auto;
  max-height: 300px;
  object-fit: cover;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.movie-picture:hover {
  transform: scale(1.05);
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

.picture-item::after {
  content: attr(alt);
  position: absolute;
  bottom: 0;
  left: 0;
  width: 100%;
  background: rgba(0, 0, 0, 0.7);
  color: white;
  padding: 5px 0;
  text-align: center;
  font-size: 12px;
  opacity: 0;
  transition: opacity 0.3s ease;
}

.picture-item:hover::after {
  opacity: 1;
}

.rating-container {
  margin-top: 10px;
}

.rating-score {
  font-size: 16px;
  font-weight: bold;
  margin-left: 10px;
  color: #ff9900;
}

</style>
