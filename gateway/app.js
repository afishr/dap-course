const express = require('express');
const morgan = require('morgan');
const { createProxyMiddleware } = require('http-proxy-middleware');

const PORT = 80;
const HOST = 'localhost';
const children = ['http://localhost:1234', 'http://localhost:4321'];

const currentChildIterator = (childrenSize, start = -1) => {
  return () => {
    start++;

    if (start >= childrenSize) {
      start = 0;
    }

    return start;
  };
};
const getCurrentChild = currentChildIterator(children.length);

const app = express();

app.use(morgan('dev'));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));

app.use(
  '/',
  createProxyMiddleware({
    changeOrigin: false,
    router: () => {
      return children[getCurrentChild()]
  }
  }),
);

app.listen(PORT, () => {
  console.log(`Starting Proxy at ${HOST}:${PORT}`);
});
