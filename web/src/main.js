import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import Antd from 'ant-design-vue';
import 'ant-design-vue/dist/reset.css';
import * as Icons from '@ant-design/icons-vue'

// createApp(App).use(Antd).use(store).use(router).mount('#app')

const app = createApp(App);
app.use(Antd).use(store).use(router).mount('#app')

const icons = Icons;

for (const [key, icon] of Object.entries(icons)) {
    app.component(key, icon);
}
