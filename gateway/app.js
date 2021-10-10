const express = require('express');
const morgan = require("morgan");
const { createProxyMiddleware } = require('http-proxy-middleware');


const PORT = 80;
const HOST = 'localhost';
const children = [
  'http://localhost:3001'
];
const currentChild = 0;

const app = express();

app.use(morgan('dev'));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));

app.use(
  '/',
  createProxyMiddleware({
    target: children[currentChild],
    changeOrigin: false,
  }),
);

app.listen(PORT, () => {
  console.log(`Starting Proxy at ${HOST}:${PORT}`);
});
