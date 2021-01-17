<template>
  <v-container v-if="showAlbum" id="photo-album">
    <div style="width: calc(100% + 32px);display: flex;flex-wrap: wrap">
      <div v-for="itemKey in Object.keys(albumMap)" :key="itemKey" class="photo-album-content">
        <div class="photo-album-main">
          <div class="photo-album-main-content">
            <div class="photo-album-main-row">
              <div class="album-photo-item btl">
                <span
                  :style="albumPhotoStyle(albumMap[itemKey][0])"
                ></span>
              </div>
              <div class="album-photo-item btr">
                <span
                  :style="albumPhotoStyle(albumMap[itemKey][1])"
                ></span>
              </div>
            </div>
            <div class="photo-album-main-row">
              <div class="album-photo-item bbl">
                <span
                  :style="albumPhotoStyle(albumMap[itemKey][2])"
                ></span>
              </div>
              <div class="album-photo-item bbr">
                <span
                  :style="albumPhotoStyle(albumMap[itemKey][3])"
                ></span>
              </div>
            </div>
          </div>
        </div>
        <div class="photo-album-title">{{ itemKey.split('/').join('-') }}</div>
      </div>
    </div>
  </v-container>
</template>

<script>
import PhotoMixin from '@/view/mixin/photoMixin'

export default {
  name: 'PhotoAlbum',
  components: {},
  mixins: [
    PhotoMixin
  ],
  props: {},
  data() {
    return {
      albumMap: {},
      showAlbum: false
    }
  },
  computed: {},
  watch: {},
  mounted() {
  },
  async created() {
    await this.getAllPhotoList()
    this.imagesList.forEach(item => {
      let photoList = this.albumMap[item.albumPath]
      if (!photoList) {
        photoList = []
      }
      photoList.push(item)
      this.albumMap[item.albumPath] = photoList
    })
    if (Object.keys(this.albumMap).length) {
      this.showAlbum = true;
    }
  },
  methods: {
    albumPhotoStyle(item) {
      return { backgroundImage: `url(${item?.src})` }
    },
  }
}
</script>

<style lang="less">
#photo-album {
  height: 100%;
  width: 100%;

  .photo-album-row {
    display: flex;
  }

  .photo-album-content {
    flex-grow: 1;
    flex-shrink: 0;
    position: relative;
    margin-right: 20px;
    max-width: calc(100%/2 - 32px - 1px);
    flex-basis: calc(100%/2 - 32px - 1px);

    .photo-album-main {
      cursor: pointer;
      padding-top: 100%;
      border-radius: 8px;
      transform: translateZ(0);
      transition: all .135s cubic-bezier(0.0, 0.0, 0.2, 1);
      background-color: #e8eaed;

      &-content {
        position: absolute;
        inset: 0;
        display: flex;
        flex-direction: column;
      }

      &-row {
        flex: 0 1 50%;
        flex-direction: row;
        display: flex
      }

      .btl {
        border-top-left-radius: 8px;
      }
      .btr {
        border-top-right-radius: 8px;
      }
      .bbl {
        border-bottom-left-radius: 8px;
      }
      .bbr {
        border-bottom-right-radius: 8px;
      }

      .album-photo-item {
        flex: 1 1 auto;
        position: relative;
        overflow: hidden;
        border: 1px solid #fff;

        span {
          position: absolute;
          inset: -4px;
          background-size: cover;
          background-position: center;
          opacity: 1;
          transition: opacity linear .15s;
        }
      }
    }

    .photo-album-title {
      color: #3c4043;
      margin: 8px 0 16px;
      letter-spacing: .01785714em;
      font-family: 'Google Sans',Roboto,Arial,sans-serif;
      font-size: .875rem;
      font-weight: 500;
      line-height: 1.25rem;
      overflow: hidden;
      text-overflow: ellipsis;
      -webkit-box-orient: vertical;
      -webkit-line-clamp: 1;
      display: -webkit-box;
      max-height: 20px;
    }
  }

  @media screen and (min-width: 960px) {
    .photo-album-content {
      max-width: calc(100%/3 - 32px - 1px);
      flex-basis: calc(100%/3 - 32px - 1px);
    }
  }

  @media screen and (min-width: 1264px) {
    .photo-album-content {
      max-width: calc(100%/5 - 32px - 1px);
      flex-basis: calc(100%/5 - 32px - 1px);
    }
  }

  @media screen and (min-width: 1904px) {
    .photo-album-content {
      max-width: calc(100%/7 - 32px - 1px);
      flex-basis: calc(100%/7 - 32px - 1px);
    }
  }
}
</style>
