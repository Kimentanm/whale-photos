const express = require('express');
const app = express();
const { createClient } = require('webdav')
const ymal = require('yamljs');
const internalIp = require('internal-ip');
const webdavConfig = ymal.load('server/config.yml').webdav
console.log(webdavConfig)
const fs = require('fs')
const sharp = require('sharp')
const axios = require('axios');
const stream = require('stream');
const exif = require('exif-parser')

let client;

app.get('/imgDetail', async(req, res) => {
  const filename = req.query.filename;
  const imgUrl = client.getFileDownloadLink(filename);
  const response = await axios.get(imgUrl, { responseType: 'arraybuffer' });
  // Buffer转Stream
  const buffer = response.data;
  const bufferStream = new stream.PassThrough();
  bufferStream.end(buffer)
  bufferStream.pipe(res)
});

app.get('/img', (req, res) => {
  const filename = req.query.filename;
  const path = './data' + filename
  // 判断缩略图是否存在，若存在直接读取缩略图,若不存在的话则去访问
  fs.stat(path, async(err, stat) => {
    if (err) {
      console.log(err)
    }
    if (stat && stat.isFile()) {
      fs.createReadStream(path).pipe(res)
    } else {
      const imgUrl = client.getFileDownloadLink(filename);
      console.log(imgUrl);
      const response = await axios.get(imgUrl, { responseType: 'arraybuffer' });
      // 图片压缩
      const buffer = response.data;
      exif.create(buffer)
      const parser = exif.create(buffer)
      const result = parser.parse()
      console.log(JSON.stringify(result, null, 2))
      const lastPath = path.substring(0, path.lastIndexOf('/'));
      fs.mkdir(lastPath, { recursive: true }, async() => {
        const sharpStream = sharp(buffer)
          .resize(1000)
          .webp({ quality: 80 })
        sharpStream.pipe(fs.createWriteStream(path))
        sharpStream.pipe(res)
      })
    }
  });
});

app.get('/photo/list', async(req, res) => {
  const directoryItems = await client.getDirectoryContents('/', { deep: true });
  const imagesItem = []
  directoryItems.forEach(file => {
    if (file.type === 'file' && imgMatch(file.basename)) {
      imagesItem.push(file)
    }
  });
  res.json({
    code: 200,
    data: imagesItem
  });
});

app.get('/connect', (req, res) => {
  const opt = {
    username: webdavConfig.username,
    password: webdavConfig.password
  }
  client = createClient(webdavConfig.url, opt);
  res.json({
    code: 200,
    msg: 'success'
  });
});

app.get('/', async(req, res) => {
  const directoryItems = await client.getDirectoryContents('/', { deep: true });
  const imagesItem = [];
  directoryItems.forEach(file => {
    if (file.type === 'file' && imgMatch(file.basename)) {
      const photoPath = file.filename.substring(7, file.filename.length)
      const downloadLink = client.getFileDownloadLink(photoPath);
      const imgUrl = downloadLink + `?username=${opt.username}&password=${opt.password}`
      imagesItem.push({
        src: imgUrl
      })
    }
  });
  res.json(imagesItem);
});

const imgMatch = (fileName) => {
  const imgTypeList = webdavConfig.imgType;
  let flag = false;
  for (let i = 0; i < imgTypeList.length; i++) {
    if (fileName.toUpperCase().endsWith('.' + imgTypeList[i].toUpperCase())) {
      flag = true;
      break
    }
  }
  return flag;
}

function toArrayBuffer(buf) {
  const ab = new ArrayBuffer(buf.length);
  const view = new Uint8Array(ab);
  for (let i = 0; i < buf.length; ++i) {
    view[i] = buf[i];
  }
  return ab;
}

const server = app.listen(3000, () => {
  const host = internalIp.v4.sync();
  const port = server.address().port;
  console.log('Example app listening at http://%s:%s', host, port);
});
