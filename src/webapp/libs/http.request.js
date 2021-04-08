import Axios from 'axios'
const baseURL = '/api'

class HttpRequest {
  constructor() {
    this.options = {
      method: '',
      url: ''
    }
    // 存储请求队列
    this.queue = {}
  }
  // 销毁请求实例
  destroy(url) {
    delete this.queue[url]
    const queue = Object.keys(this.queue)
    return queue.length
  }
  // 请求拦截
  interceptors(instance, url) {
    // 添加请求拦截器
    instance.interceptors.request.use(config => {
      // 在发送请求之前做些什么
      return config
    }, error => {
      // 对请求错误做些什么
      return Promise.reject(error)
    })

    // 添加响应拦截器
    instance.interceptors.response.use((res) => {
      const { data } = res
      const is = this.destroy(url)
      if (!is) {
        setTimeout(() => {
          // Spin.hide()
        }, 500)
      }
      return data
    }, (error) => {
      // 对响应错误做点什么
      return Promise.reject(error)
    })
  }
  // 创建实例
  create() {
    const conf = {
      baseURL: baseURL,
      // timeout: 2000,
      headers: {
        'Content-Type': 'application/json; charset=utf-8',
        'X-URL-PATH': location.pathname
      }
    }
    return Axios.create(conf)
  }

  handleError(error) {
    return Promise.reject(error);
  }
  // 请求实例
  request(options) {
    const instance = this.create()
    this.interceptors(instance, options.url)
    options = Object.assign({}, options)
    this.queue[options.url] = instance
    return instance(options)
  }

  get(url, params) {
    return this.request({ url, params, method: 'get' })
      .catch(this.handleError);
  }

  post(url, data) {
    return this.request({ url, data, method: 'post' })
      .catch(this.handleError);
  }

  put(url, data) {
    return this.request({ url, data, method: 'put' })
      .catch(this.handleError);
  }

  delete(url) {
    return this.request({ url, method: 'delete' })
      .catch(this.handleError);
  }
}

const http = new HttpRequest();
export default http
