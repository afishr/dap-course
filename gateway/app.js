const express = require('express');
const morgan = require('morgan');
const flatCache = require('flat-cache');
const { createProxyMiddleware } = require('http-proxy-middleware');
const redis = require('redis');
const axios = require('axios')

const PORT = 80;
const HOST = 'localhost';
const children = {
  'fileService': [
    'http://dap-course_file_service1_1:3050',
    'http://dap-course_file_service2_1:3051',
    'http://dap-course_file_service3_1:3052',
  ],
  'userService': [
    'http://dap-course_user_service_1:8084',
    'http://dap-course_user_service_1:8084',
    'http://dap-course_user_service_1:8084',
  ],
};

const currentChildIterator = (childrenSize, start = -1) => {
  return () => {
    start++;

    if (start >= childrenSize) {
      start = 0;
    }

    return start;
  };
};

const redisClient = redis.createClient({ url: 'redis://dap-course_redis_1:6379' });

(async () => {
  redisClient.on('error', (err) => console.log('Redis Client Error', err));

  await redisClient.connect();
})();

const app = express();

app.use(morgan('dev'));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));

// app.get('/', flatCacheMiddleware);

// Authorization
app.use('/', (req, res, next) => {
  if (req.headers.authorization) {
    next();
  } else {
    res.sendStatus(403);
  }
});

// create redis middleware
let redisMiddleware = async (req, res, next) => {
  if (req.method !== 'GET') {
    // next();
    return;
  }

  const url = req.url || req.originalUrl;
  console.log(`url ${url}`);

  const redisData = await redisClient.get(url);
  console.log(`redisData ${redisData}`);

  if (redisData !== null) {
    console.log('got from redis');

    return res.send(JSON.parse(redisData));
  } else {
    axios.get(url, { headers: req.headers }).then(r => {
      console.log(r.statusText);
      redisClient.setEx(req.url || req.originalUrl, 3600, JSON.stringify(r.data));
      console.log('got from server');

      return res.send(r.data);
    });
  }

};

app.use(
  '/fileService',
  createProxyMiddleware({
    changeOrigin: true,
    pathRewrite: {
      [`^/fileService`]: '',
    },
    router: () => {
      const serviceChildren = children['fileService'];
      const childrenSize = serviceChildren.length;
      const getCurrentChild = currentChildIterator(childrenSize);

      return serviceChildren[getCurrentChild()];
    },
  }),
);

app.use(
  '/userService',
  createProxyMiddleware({
    changeOrigin: true,
    pathRewrite: {
      [`^/userService`]: '',
    },
    router: () => {
      const serviceChildren = children['userService'];
      const childrenSize = serviceChildren.length;
      const getCurrentChild = currentChildIterator(childrenSize);

      return serviceChildren[getCurrentChild()];
    },
  }),
);

app.use(redisMiddleware);

app.listen(PORT, () => {
  console.log(`Starting Proxy at ${HOST}:${PORT}`);
});
