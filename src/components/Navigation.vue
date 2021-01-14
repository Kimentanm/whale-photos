<template>
  <v-card id="navigation">
    <v-navigation-drawer
      v-model="drawer"
      :mini-variant.sync="mini"
      app
      dark
    >
      <v-list-item class="px-2" @click.stop="mini = !mini">
        <v-list-item-avatar>
          <v-img src="https://cdn.jsdelivr.net/gh/kimentanm/image-store/img/20201217103914.jpeg"></v-img>
        </v-list-item-avatar>

        <v-list-item-content>
          <v-list-item-title>Kimen Tang</v-list-item-title>
          <v-list-item-subtitle>管理员</v-list-item-subtitle>
        </v-list-item-content>
      </v-list-item>

      <v-divider></v-divider>

      <v-list dense>
        <v-list-item
          v-for="item in menuList"
          :key="item.title"
          link
          @click="$router.push({name: item.name})"
        >
          <v-list-item-icon>
            <v-icon>{{ item.icon }}</v-icon>
          </v-list-item-icon>

          <v-list-item-content>
            <v-list-item-title v-text="item.title"></v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list>
    </v-navigation-drawer>
  </v-card>
</template>

<script>
import router from '@/router/routers';
export default {
  name: 'Navigation',
  components: {},
  props: {
    value: {
      type: Boolean,
      default: true
    }
  },
  data() {
    return {
      drawer: true,
      items: [
        { title: '照片墙', icon: 'mdi-wall' },
        { title: '相簿', icon: 'mdi-google-photos' },
        { title: '时间', icon: 'mdi-camera-timer' },
        { title: '人物', icon: 'mdi-tooltip-account' },
        { title: '地点', icon: 'mdi-crosshairs-gps' },
      ],
      mini: false,
    }
  },
  computed: {
    menuList() {
      const menuList = [];
      router.forEach(router => {
        if (router.name === 'photo') {
          router.children.forEach(photoRoute => {
            if (photoRoute.meta.showInMenu) {
              photoRoute.meta.name = photoRoute.name
              menuList.push(photoRoute.meta)
            }
          })
        }
      })
      return menuList;
    }
  },
  watch: {
    value(val) {
      this.drawer = val
    },
    drawer(val) {
      this.$emit('input', val)
    }
  },
  mounted() {
  },
  created() {},
  methods: {}
}
</script>

<style lang="less">
#navigation {
}
</style>
