const express = require('express');
const morgan = require('morgan');
const { createProxyMiddleware } = require('http-proxy-middleware');

const PORT = 80;
const HOST = 'localhost';
const children = {
  'fileService': [
    'http://dap-course_file_service1_1:3050',
    'http://dap-course_file_service2_1:3051',
    'http://dap-course_file_service3_1:3052'
  ],
  'userService': [
    'http://dap-course_user_service_1:8084',
    'http://dap-course_user_service_1:8084',
    'http://dap-course_user_service_1:8084'
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

const app = express();

app.use(morgan('dev'));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));

// Authorization
app.use('/', (req, res, next) => {
  if (req.headers.authorization) {
    next();
  } else {
    res.sendStatus(403);
  }
});

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
      const string = serviceChildren[getCurrentChild()];

      return string
    },
  })
);

app.use(
  '/userService',
  createProxyMiddleware({
    changeOrigin: true,
    pathRewrite: {
      [`^/userService`]: '',
    },
    router: () => {
      const serviceChildren = children['userService']
      const childrenSize = serviceChildren.length;

      return serviceChildren[currentChildIterator(childrenSize)]
    },
  }),
);

app.listen(PORT, () => {
  console.log(`Starting Proxy at ${HOST}:${PORT}`);
});
