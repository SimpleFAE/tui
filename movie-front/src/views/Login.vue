<template>
  <div class="login_container">
    <div class="grok_background">
      <div class="grok_text">CINEMA</div>
    </div>
    <div class="login_box">
      <h1 class="title">影院管理系统</h1>
      <el-form class="login_form" :model="loginForm" :rules="loginFormRules" ref="loginFormRef">
        <el-form-item prop="userName">
          <el-input v-model="loginForm.userName" placeholder="用户名" clearable
                    prefix-icon="el-icon-user" class="input-style"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input v-model="loginForm.password" type="password" placeholder="密码" show-password
                    prefix-icon="el-icon-lock" class="input-style"></el-input>
        </el-form-item>
        <el-form-item class="btns">
          <el-button type="primary" class="btn-style btn-login" @click="login">登录</el-button>
          <el-button type="default" class="btn-style btn-reset" @click="resetLoginForm">重置</el-button>
        </el-form-item>
      </el-form>
      <p class="footer-text">© 2025 小陶子影院系统. 保留所有权利.</p>
    </div>
  </div>
</template>

<script>
export default {
  name: "Login",
  data() {
    return {
      //登录表单数据对象
      loginForm: {
        userName: 'admin',
        password: '123456'
      },
      //表单验证规则
      loginFormRules: {
        //验证用户名
        userName: [
          { required: true, message: "请输入用户名称", trigger: "blur"},
          { min:2, max: 20, message: "长度在2到20个字符之间", trigger: "blur"}
        ],
        //验证密码
        password: [
          { required: true, message: "请输入密码", trigger: "blur"},
          { min:6, max: 16, message: "长度在6到16个字符之间", trigger: "blur"}
        ]
      }
    }
  },
  methods:{
    success(params) {
      console.log(params);
      this.login()
    },
    //点击重置按钮，重置表单
    resetLoginForm(){
      console.log(this.$refs)
      this.$refs.loginFormRef.resetFields();
    },
    login() {
  this.$refs.loginFormRef.validate(async valid => {
    if(!valid) return;
    try {
      axios.defaults.headers.post['Content-Type'] = 'application/json'
      const { data: res } = await axios.post('sysUser/login', JSON.stringify(this.loginForm))
      if(res.code !== 200) return this.$message.error(res.msg);
      
      if(res.data.sysUser.sysRole.children === null || res.data.sysUser.sysRole.children[0] === null) {
        this.$message.error("抱歉，您没有权限登录，请联系管理员获取权限")
        return
      }
      
      this.$message.success("登录成功")
      
      // 保存登录信息
      window.sessionStorage.setItem("token", res.data.token)
      window.sessionStorage.setItem("loginUser", JSON.stringify({
        sysUser: res.data.sysUser, 
        cinemaId: res.data.cinemaId, 
        cinemaName: res.data.cinemaName
      }));
      window.sessionStorage.setItem("btnPermission", res.data.sysUser.sysRole.roleId === 1 ? "admin" : "admin")
      
      // 直接跳转到home页面，让路由配置处理重定向到welcome
      await this.$router.push('/home');
    } catch (error) {
      console.error('登录错误:', error);
      this.$message.error('登录失败，请稍后重试');
    }
  })
}
  }
}
</script>

<style scoped>
.login_container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #000;
  position: relative;
  overflow: hidden;
}
.grok_background {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}

.grok_text {
  font-size: 25vw;
  font-weight: bold;
  color: transparent;
  -webkit-text-stroke: 2px;
  letter-spacing: 1vw;
  user-select: none;
  position: relative;
  animation: rainbow-stroke 12s linear infinite;
}

.grok_text::before,
.grok_text::after {
  content: 'CINEMA';
  position: absolute;
  left: 0;
  top: 0;
  pointer-events: none;
  color: transparent;
}

.grok_text::before {
  -webkit-text-stroke: 2px;
  animation: glow1 6s linear infinite, rainbow-stroke 12s linear infinite;
  filter: blur(3px);
}

.grok_text::after {
  -webkit-text-stroke: 2px;
  animation: glow2 6s linear infinite, rainbow-stroke 12s linear infinite;
  filter: blur(3px);
}

@keyframes glow1 {
  0%, 100% { 
    opacity: 0.2;
  }
  50% { 
    opacity: 0.5;
  }
}
@keyframes move-gradient {
  0% {
    background-position: 0% 0%;
  }
  100% {
    background-position: -300% 0%;
  }
}
@keyframes glow2 {
  0%, 100% { 
    opacity: 0.5;
  }
  50% { 
    opacity: 0.2;
  }
}
@keyframes move-gradient {
  0% {
    background-position: 0% 0%;
  }
  100% {
    background-position: -400% 0%; /* 移动的距离是背景大小的4倍 */
  }
}
@keyframes rainbow-stroke {
  0% {
    -webkit-text-stroke-color: red;
  }
  16.6667% {
    -webkit-text-stroke-color: orange;
  }
  33.3334% {
    -webkit-text-stroke-color: yellow;
  }
  50% {
    -webkit-text-stroke-color: green;
  }
  66.6667% {
    -webkit-text-stroke-color: blue;
  }
  83.3334% {
    -webkit-text-stroke-color: indigo;
  }
  100% {
    -webkit-text-stroke-color: violet;
  }
}
.login_box {
  width: 400px;
  padding: 40px;
  background-color: rgba(30, 30, 30, 0.9);
  border-radius: 20px;
  box-shadow: 0 15px 30px rgba(0, 0, 0, 0.3);
  text-align: center;
  backdrop-filter: blur(10px);
  transition: all 0.3s ease;
  position: relative;
  z-index: 1;
  border: 1px solid rgba(255, 255, 255, 0.1);
}

.login_box:hover {
  transform: translateY(-5px);
  box-shadow: 0 20px 40px rgba(0, 0, 0, 0.4);
}

.title {
  font-size: 28px;
  margin-bottom: 30px;
  color: #fff;
  font-weight: bold;
  text-shadow: 0 0 10px rgba(255, 255, 255, 0.1);
}

.input-style {
  margin-bottom: 20px;
}

.input-style /deep/ .el-input__inner {
  background-color: rgba(255, 255, 255, 0.05);
  border: 1px solid rgba(255, 255, 255, 0.1);
  border-radius: 10px;
  height: 50px;
  padding-left: 45px;
  color: #fff;
  transition: all 0.3s ease;
}

.input-style /deep/ .el-input__inner:focus {
  border-color: rgba(255, 255, 255, 0.2);
  box-shadow: 0 0 15px rgba(255, 255, 255, 0.1);
}

.input-style /deep/ .el-input__prefix {
  left: 15px;
  font-size: 18px;
  color: rgba(255, 255, 255, 0.5);
}

.btns {
}

.btn-style {
  width: 48%;
  height: 50px;
  border-radius: 10px;
  font-size: 16px;
  font-weight: bold;
  transition: all 0.3s ease;
  background: linear-gradient(145deg, rgba(255, 255, 255, 0.1), rgba(255, 255, 255, 0.05));
  border: 1px solid rgba(255, 255, 255, 0.1);
  color: #fff;
}

.btn-style:hover {
  transform: translateY(-2px);
  box-shadow: 0 0 20px rgba(255, 255, 255, 0.1);
  background: linear-gradient(145deg, rgba(255, 255, 255, 0.15), rgba(255, 255, 255, 0.07));
}

.footer-text {
  margin-top: 30px;
  font-size: 12px;
  color: rgba(255, 255, 255, 0.3);
}

@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

@keyframes glow {
  from {
    text-shadow: 
      0 0 5px rgba(255, 255, 255, 0.1),
      0 0 10px rgba(255, 255, 255, 0.1),
      0 0 15px rgba(255, 255, 255, 0.1),
      0 0 20px rgba(255, 255, 255, 0.1);
  }
  to {
    text-shadow: 
      0 0 10px rgba(255, 255, 255, 0.2),
      0 0 20px rgba(255, 255, 255, 0.2),
      0 0 30px rgba(255, 255, 255, 0.2),
      0 0 40px rgba(255, 255, 255, 0.2);
  }
}

/* Element UI dark theme overrides */
:deep(.el-message-box),
:deep(.el-message) {
  background-color: #1e1e1e;
  border: 1px solid rgba(255, 255, 255, 0.1);
  color: #fff;
}

:deep(.el-message-box__title),
:deep(.el-message__content) {
  color: #fff;
}
</style>