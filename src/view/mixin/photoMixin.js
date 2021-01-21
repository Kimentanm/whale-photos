import { shuffle } from 'lodash';

export default {
  data() {
    return {
      imagesList: [],
      imageDetailList: [],
    }
  },
  methods: {
    async getAllPhotoList() {
      const res = await this.$http.get('/photo/list')
      if (res.code === 200) {
        res.data.forEach(item => {
          const dirArray = item.filename.split('/')
          dirArray.shift()
          dirArray.pop();
          this.imagesList.push({
            filename: item.filename,
            src: `/api/img?filename=${item.filename}`,
            basename: item.basename,
            albumPath: dirArray.join('/')
          })
        });
      }
      this.imagesList = shuffle(this.imagesList)
      this.imageDetailList = this.imagesList.map(item => {
        return {
          url: `/api/imgDetail?filename=${item.filename}`,
          miniUrl: `/api/img?filename=${item.filename}`,
        }
      })
    }
  }
}
