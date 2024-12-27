// Token的key
const TokenKey = 'Admin-Token'

// 获取token
export function getToken() {
  return window.sessionStorage.getItem(TokenKey)
}

// 设置token
export function setToken(token) {
  return window.sessionStorage.setItem(TokenKey, token)
}

// 移除token
export function removeToken() {
  return window.sessionStorage.removeItem(TokenKey)
}