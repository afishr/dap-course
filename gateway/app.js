const express = require('express');
const morgan = require('morgan');
const { createProxyMiddleware } = require('http-proxy-middleware');

const PORT = 80;
const HOST = 'localhost';
const children = {
  'file': 'http://localhost:3050',
  'cache': 'http://localhost:3050',
};

const app = express();

app.use(morgan('dev'));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));

app.use(
  '/',
  createProxyMiddleware({
    changeOrigin: false,
    router: (req) => {
      const path = req.path.split('/');

      console.log(`Request on /${path[1]}`)

      return children[path[1]]
    }
  }),
);

app.listen(PORT, () => {
  console.log(`Starting Proxy at ${HOST}:${PORT}`);
});
