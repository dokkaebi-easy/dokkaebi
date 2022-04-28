const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = function (app) {
  app.use(
    '/api',
    createProxyMiddleware({
      target: 'http://k6s205.p.ssafy.io:8482',
      changeOrigin: true,
    }),
  );
};
