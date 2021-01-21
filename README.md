<h2 align="center">Whale Photos（鲸图）</h2>

## 介绍
使用`Webdav`连接网盘，直接展示网盘中所有图片的一款相册

## 待开发功能
- [x] 照片墙, 可以查看照片详情
- [x] 相册，按照图片第一层文件夹分类，创建相册
- [ ] 按照颜色创建相册
- [ ] 时间，按照牌照时间或者文件上传时间分类图片
- [ ] 人物，按照人脸分类图片
- [ ] 地点，按照地点分类图片，在地图上显示

## 后端
使用Nodejs编写后端，后端文件位于`server`目录下

## Webdav配置
在`server/config.yml`配置文件中配置Webdav的地址和认证信息以及可匹配的图片类型（大小写不敏感）

## 使用Docker（推荐）
待实施

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

内容转载自知乎[2020年还有哪些支持WebDAV的网盘？ - 傅悦的回答 - 知乎](https://www.zhihu.com/question/347182171/answer/1105742734)

## 许可证
[GPL](https://github.com/kimentanm/whale-photos/blob/master/LICENSE)

Copyright (c) 2020-present Kimen Tang
