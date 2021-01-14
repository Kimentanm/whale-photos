<template>
  <v-container style="height: 100%">
    <vue-waterfall-easy style="height: 100%;width: 100%" :imgs-arr="imagesItem" @scrollReachBottom="getData" />
  </v-container>
</template>

<script>
import VueWaterfallEasy from 'vue-waterfall-easy'

export default {
  name: 'PhotoWall',
  components: {
    VueWaterfallEasy
  },
  data: () => ({
    imagesItem: []
  }),
  async created() {
    this.getAllPhotoList()
  },
  methods: {
    getData() {},
    async getAllPhotoList() {
      const res = await this.$http.get('/photo/list')
      if (res.code === 200) {
        res.data.forEach(item => {
          this.imagesItem.push({
            src: `/api/img?filename=${item.filename}`
          })
        });
      }
    }
  }
}
</script>
