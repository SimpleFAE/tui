import axios from 'axios'
import { Message } from 'element-ui'
import { getToken } from './auth'  // 修改导入路径

const service = axios.create({
  baseURL: '/api',
  timeout: 5000
})

service.interceptors.request.use(
  config => {
    // 由于统计接口允许匿名访问，可以注释掉 token 相关代码
    // const token = getToken()
    // if (token) {
    //   config.headers['Authorization'] = token
    // }
    return config
  },
  error => {
    console.error('请求错误:', error)
    return Promise.reject(error)
  }
)

service.interceptors.response.use(
  response => response.data,
  error => {
    Message({
      message: error.message || '请求失败',
      type: 'error',
      duration: 5 * 1000
    })
    return Promise.reject(error)
  }
)

export default service