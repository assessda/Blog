module.exports = {
  
  
  devServer: {
    
    open: process.platform === 'darwin',
    host: '0.0.0.0',
    port: 80,   /* 服务启动配置 */
    https: false,
    hotOnly: false,
    proxy: null, // string | Object
    before: app => {}

  }
}
