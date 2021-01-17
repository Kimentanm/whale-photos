<template>
  <v-container class="photo-wall" style="height: 100%">
    <Waterfall
      ref="waterfall"
      :list="imagesList"
      :gutter="10"
      :width="240"
      :breakpoints="{
        1200: { //当屏幕宽度小于等于1200
          rowPerView: 4,
        },
        800: { //当屏幕宽度小于等于800
          rowPerView: 3,
        },
        500: { //当屏幕宽度小于等于500
          rowPerView: 2,
        }
      }"
      animation-effect="fadeInUp"
      background-color="#fff"
    >
      <template slot="item" slot-scope="props">
        <div class="waterfall-img" @click="showImgDetail(props.data)">
          <img width="100%" :src="props.data.src" alt="" @load="$refs.waterfall.refresh">
          <div class="overlay"></div>
          <span class="img-name">{{ props.data.basename }}</span>
        </div>
      </template>
    </Waterfall>

    <vue-gallery-slideshow :images="imageDetailList" :index="index" @close="index = null"></vue-gallery-slideshow>
  </v-container>
</template>

<script>
import Waterfall from 'vue-waterfall-plugin';
import VueGallerySlideshow from '@/components/GallerySlideshow';
import PhotoMixin from '@/view/mixin/photoMixin'

export default {
  name: 'PhotoWall',
  components: {
    Waterfall,
    VueGallerySlideshow
  },
  mixins: [
    PhotoMixin
  ],
  data: () => ({
    index: null
  }),
  watch: {
  },
  async created() {
    await this.getAllPhotoList()
  },
  methods: {
    showImgDetail(item) {
      this.index = this.imagesList.findIndex(img => img.src === item.src)
    }
  }
}
</script>
<style lang="less">
.photo-wall {
  .waterfall-img {
    position: relative;
    cursor: pointer;

    img {
      border-radius: 2%;
    }

    .overlay {
      position: absolute;
      left: 0;
      top: 0;
      opacity: 0;
      content: "";
      background-color: black;
      width: 100%;
      height: ~'calc(100% - 7px)';
      -webkit-transition: .3s;
      -o-transition: .3s;
      transition: .3s;
      border-radius: 2%;
    }

    &:hover .overlay {
      opacity: .7;
      -webkit-transition: .3s;
      -o-transition: .3s;
      transition: .3s
    }

    .img-name {
      position: absolute;
      top: 50%;
      left: 50%;
      transform: translate(-50%, -50%);
      color: #fff;
      font-size: 15px;
      opacity: 0;
      width: 100%;
      text-align: center;
    }

    &:hover .img-name {
      opacity: 1;
      visibility: visible;
      -webkit-transition: .3s;
      -o-transition: .3s;
      transition: .3s
    }
  }

  .vgs__container {
    top: 14%;
  }

  @media(max-width: 767px){
    .vgs__container {
      top: 50%;
    }
  }
}
</style>
