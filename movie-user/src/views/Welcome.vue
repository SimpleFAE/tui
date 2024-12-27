<template>
  <el-container>
    <el-header>
      <div class="header-inner">
        <a @click.prevent="toWelcome" class="logo">
          <img style="width: 270px; height: 68px; margin-top: 6px;" src="../assets/homeLogo.jpg">
        </a>
        <el-menu
          :default-active="activeUrl"
          class="nav-menu"
          mode="horizontal"
          :router="true"
          active-text-color="#409EFF"
          text-color="#000000">
          <el-menu-item :index="item.path" v-for="item in menuList" :key="item.id">{{item.name}}</el-menu-item>
        </el-menu>
        <div class="searchContainer">
          <el-input v-model="kw" class="searchBar" placeholder="搜索电影" @keyup.enter.native="search"></el-input>
          <el-button id="searchBtn" icon="el-icon-search" type="primary" circle @click="search"></el-button>
        </div>
        <el-dropdown @command="handleCommand">
          <span class="el-dropdown-link">
            <el-avatar :src="url" :size="50">{{url === '' || url === null ? 'user': ''}}</el-avatar>
            <i class="el-icon-arrow-down el-icon--right icon-arrow"></i>
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item command="userMenu" v-if="isToken">个人中心</el-dropdown-item>
            <el-dropdown-item command="logout" v-if="isToken">退出</el-dropdown-item>
            <el-dropdown-item command="login" v-if="!isToken">登录</el-dropdown-item>
            <el-dropdown-item command="register" v-if="!isToken">注册账号</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
    </el-header>
    <el-main>
      <router-view></router-view>
    </el-main>
    <el-footer>
      <div class="footer-mini"></div>
      <div class="footer">
        <img style="width: 512px; height: 70px" src="../assets/register-footer.jpg">
      </div>
      <el-backtop></el-backtop>
    </el-footer>
  </el-container>
</template>

<script>
export default {
  name: "Welcome",
  data() {
    return {
      isToken: '',
      cinemaInfo: '',
      url: '',
      activeUrl: this.$route.path.substring(0, this.$route.path.indexOf('/',1) === -1 ? this.$route.path.length : this.$route.path.indexOf('/',1)),
      menuList: [
        {
          id: 1,
          name: '首页',
          path: '/home'
        },
        {
          id: 2,
          name: '电影',
          path: '/movie'
        },
        {
          id: 3,
          name: '榜单',
          path: '/rankingList'
        },
        {
          id: 4,
          name: '关于我们',
          path: '/aboutUs'
        }
      ],
      // 搜索关键字
      kw: ''
    }
  },
  created() {
    this.getCinemaInfo();
    this.isToken = window.sessionStorage.getItem("token");
    
    const loginUser = JSON.parse(window.sessionStorage.getItem('loginUser'));
    if (loginUser && loginUser.userPicture) {
      const picture = JSON.parse(loginUser.userPicture);
      if (picture && picture.length > 0) {
        this.url = this.global.base + picture[0];
      }
    }
  },
  watch: {
    '$route'() {
      this.activeUrl = this.$route.path.substring(0, this.$route.path.indexOf('/',1) === -1 ? this.$route.path.length : this.$route.path.indexOf('/',1));
    }
  },
  methods: {
    async getCinemaInfo() {
      try {
        const { data: res } = await axios.get('sysCinema');
        if (res.code === 200) {
          this.cinemaInfo = res.data;
        } else {
          this.$message.error('获取影院信息失败');
        }
      } catch (error) {
        this.$message.error('网络错误');
      }
    },
    handleSelect(key, keyPath) {
      console.log(key, keyPath);
    },
    search() {
      let tmp = this.kw.trim();
      if (tmp) {
        this.$router.push({ name: 'searchMovie', query: { kw: tmp } });
      }
      this.kw = '';
    },
    handleCommand(command) {
      if (command === 'logout') {
        window.sessionStorage.clear();
        this.$message.success('已退出登录');
        this.$router.push('/login');
      } else {
        this.$router.push('/' + command);
      }
    },
    toWelcome() {
      this.$router.push('/welcome');
    }
  }
}
</script>

<style scoped>
.el-header {
  height: 80px !important;
  border-bottom: 1px solid #e6e6e6;
}

.header-inner {
  width: 75%;
  margin: 0 12.5%;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.logo {
  height: 80px;
  width: 200px;
  cursor: pointer;
}

.nav-menu {
  border-bottom: 0px solid #eee !important;
  height: 60px;
  margin: 9px auto;
}

.el-menu-item {
  font-size: 20px;
}

.el-menu-item:hover {
  color: #409EFF !important;
}

.searchContainer {
  padding: 20px 0;
  display: flex;
}

.searchBar >>> input {
  border-radius: 50px;
}

#searchBtn {
  transform: translate(-100%, 0);
}

.el-dropdown-link {
  width: 70px;
  margin: 15px 0;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.icon-arrow {
  transition: all 0.2s ease-in-out;
}

.el-dropdown-link:hover .icon-arrow {
  transform: rotate(180deg) scale(1);
}

.footer-mini {
  border-top: 1px solid #EEE;
  padding-top: 20px;
  text-align: center;
}

.footer {
  margin-top: 20px;
  display: flex;
  justify-content: center;
}

.el-main {
  padding: 0px;
}
</style>
