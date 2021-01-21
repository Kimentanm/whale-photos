<template>
  <v-container id="photo-time">
    <waterfall row :list="imageDetailList"></waterfall>
  </v-container>
</template>

<script>
import PhotoMixin from '@/view/mixin/photoMixin'
import Waterfall from '@/components/Waterfall';
export default {
  name: 'PhotoTime',
  components: {
    Waterfall
  },
  mixins: [
    PhotoMixin
  ],
  props: {},
  data() {
    return {}
  },
  computed: {},
  watch: {},
  mounted() {
  },
  async created() {
    await this.getAllPhotoList()
  },
  methods: {}
}
</script>

<style lang="less">
#photo-time {
  height: 100%;
  width: 100%;
}
</style>
