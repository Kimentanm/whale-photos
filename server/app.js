const express = require('express');
const app = express();
const { createClient } = require('webdav')
const ymal = require('yamljs');
const internalIp = require('internal-ip');
const webdavConfig = ymal.load('server/config.yml').webdav
console.log(webdavConfig)
const request = require('request');
const url = require('url');
// const imagemin = require('gulp-imagemin')
const fs = require('fs')
const sharp = require('sharp')

let client;

app.get('/img', (req, res) => {
  const filename = req.query.filename;
  const path = './data' + filename
  // 判断缩略图是否存在，若存在直接读取缩略图,若不存在的话则去访问
  fs.stat(path, async(err, stat) => {
    if (stat && stat.isFile()) {
      fs.createReadStream(path).pipe(res)
    } else {
      const downloadLink = client.getFileDownloadLink(filename);
      const imgUrl = downloadLink + `?username=${webdavConfig.username}&password=${webdavConfig.password}`
      const parsedUrl = url.parse(imgUrl);
      const referrer = parsedUrl.protocol + '//' + parsedUrl.host;
      const options = {
        method: 'GET',
        url: imgUrl,
        headers: {
          'Referer': referrer,
          'Accept-Encoding': 'gzip, deflate'
        }
      };
      const requestStream = request(options)
      // 图片压缩
      const buffer = await streamToBuffer(requestStream);
      const lastPath = path.substring(0, path.lastIndexOf('/'));
      fs.mkdir(lastPath, { recursive: true }, () => {
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
    if (fileName.endsWith('.' + imgTypeList[i])) {
      flag = true;
      break
    }
  }
  return flag;
}

function streamToBuffer(stream) {
  return new Promise((resolve, reject) => {
    const buffers = [];
    stream.on('error', reject);
    stream.on('data', (data) => buffers.push(data))
    stream.on('end', () => resolve(Buffer.concat(buffers)))
  });
}

const server = app.listen(3000, () => {
  const host = internalIp.v4.sync();
  const port = server.address().port;
  console.log('Example app listening at http://%s:%s', host, port);
});
