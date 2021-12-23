const express = require('express');
const morgan = require('morgan');
const { createProxyMiddleware } = require('http-proxy-middleware');

const PORT = 80;
const HOST = 'localhost';
const children = {
  'file': 'http://localhost:3050',
  'user': 'http://localhost:8084',
  'cache': 'http://localhost:3050',
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
    target: 'http://dap-course_file_service_1:3050',
    changeOrigin: true,
    pathRewrite: {
      [`^/fileService`]: '',
    },
  })
);

app.use(
  '/userService',
  createProxyMiddleware({
    target: 'http://dap-course_user_service_1:8084',
    changeOrigin: true,
    pathRewrite: {
      [`^/userService`]: '',
    },
  }),
);

app.listen(PORT, () => {
  console.log(`Starting Proxy at ${HOST}:${PORT}`);
});
