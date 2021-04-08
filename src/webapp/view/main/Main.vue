<template>
  <v-app>
    <navigation v-model="showNavigation" />

    <toolbar @navigation-show="showNavigation = !showNavigation" />

    <v-main>
      <router-view />
    </v-main>
  </v-app>
</template>

<script>
import Toolbar from '@/components/Toolbar';
import Navigation from '@/components/Navigation';

export default {
  name: 'Main',

  components: {
    Toolbar,
    Navigation
  },
  data: () => ({
    showNavigation: true
  }),
  async created() {
    await this.connectWebdav()
  },
  methods: {
    async connectWebdav() {
      const res = await this.$http.get('/connect')
      if (res.code === 200) {
        console.log(res.msg);
      }
    }
  }
};
</script>
