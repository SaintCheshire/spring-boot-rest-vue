import Vue from 'vue'
import App from './App.vue'
import vuetify from "@/plugins/vuetify/vuetify";
import router from "@/plugins/router/router";
import {store} from "@/plugins/vuex/vuex"

Vue.config.productionTip = false

new Vue(
    {
      render: h => h(App),
      vuetify,
      router: router,
      store
    }
).$mount('#app')

