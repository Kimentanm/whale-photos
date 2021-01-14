import Vue from 'vue'
import App from './App.vue'
import router from './router'
import vuetify from './plugins/vuetify';
import http from '@/libs/http.request'

Vue.config.productionTip = false
Vue.prototype.$http = http

new Vue({
  router,
  vuetify,
  render: h => h(App)
}).$mount('#app')
