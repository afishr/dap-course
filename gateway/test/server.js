const express = require('express');

const app = express();

app.use('/', (req, res) => {
  res.end(`app ${process.argv[2]} on the line`);
});

app.listen(process.argv[2], () => {
  console.log(`Starting Proxy at ${process.argv[2]}`);
});
