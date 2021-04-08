<h2 align="center">Whale Photos（鲸图）<img width="20" src="./public/favicon.ico" /></h2>
<p align="center">
<img alt="GitHub Repo stars" src="https://img.shields.io/github/stars/Kimentanm/whale-photos">
<img alt="GitHub" src="https://img.shields.io/github/license/Kimentanm/whale-photos">
<img alt="Vue Version" src="https://img.shields.io/badge/vue-v2.6.11-green">
<img alt="Vuetify Version" src="https://img.shields.io/badge/vuetify-v2.2.11-blue">
</p>

## 介绍
使用`Webdav`连接网盘，直接展示网盘中所有图片的一款相册

## 在线体验
[在线DEMO](http://photos.kimen.com.cn/)

## 开发列表
- [x] 照片墙, 可以查看照片详情
- [x] 相册，按照图片第一层文件夹分类，创建相册
- [ ] 按照颜色创建相册
- [ ] 时间，按照牌照时间或者文件上传时间分类图片
- [ ] 人物，按照人脸分类图片
- [ ] 地点，按照地点分类图片，在地图上显示
- [ ] App端、小程序端、桌面端……

## 效果图
![照片墙](https://cdn.jsdelivr.net/gh/kimentanm/image-store/img/20210125220036.png)
![照片详情](https://cdn.jsdelivr.net/gh/kimentanm/image-store/img/20210125220126.png)
![相簿](https://cdn.jsdelivr.net/gh/kimentanm/image-store/img/20210125131031.png)
![时间相簿](https://cdn.jsdelivr.net/gh/kimentanm/image-store/img/20210125220152.png)

## 后端
使用Nodejs编写后端，后端文件位于`server`目录下
后端主要处理图片压缩和图片信息处理

## Webdav配置
在`server/config.yml`配置文件中配置Webdav的地址和认证信息以及可匹配的图片类型（大小写不敏感）
```yaml
webdav:
  url: http://192.168.31.91:83/dav
  username: Kimentanm@hotmail.com
  password: VoZ0vzcyCnA8kSd19aNi39w7QuUwcCUf
  imgType:
    - jpg
    - jpeg
    - png
```

## 使用Docker（推荐）
### 从DockerHub获取
待实施
### 从源码构建
```bash
# build image
docker build -t whale-photos:latest .
# create container from image
docker run -d  \
--name whale  \
-p 8080:80  \
-v whale-photos-config:/home/server/src/config  \
whale-photos-server
```
在浏览器中访问`http://localhost:8080`

## 快速上手

```bash
# clone the project
git clone https://github.com/kimentanm/whale-photos.git

# enter the project directory
cd whale-photos

# install dependency
npm install # or cnpm install or yarn install

# start front
npm run dev # or yarn dev

# start backend
node server/app.js
```

## 支持Webdav的网盘
- Dropbox（美国，墙外）
- Google drive（美国，墙外）
- Teracloud（日本，墙内）
- 坚果云（中国，墙内）
- yandex（俄罗斯，墙内）
- TransIP（荷兰，墙内）
- Box
- 4shared
- MyDrive（瑞士）
- DriveOnWeb（德国，墙外）
- HiDrive
- Storegate
- LiveDrive
- FileAnywhere
- DriveHQ
- iDriveSync
- Hetzner
- iCloud
- CloudMe
- CloudSafe
- Cloudreve

内容整理自知乎[2020年还有哪些支持WebDAV的网盘？ - 傅悦的回答 - 知乎](https://www.zhihu.com/question/347182171/answer/1105742734)

## 许可证
[GPL](https://github.com/kimentanm/whale-photos/blob/master/LICENSE)

Copyright (c) 2020-present Kimen Tang
