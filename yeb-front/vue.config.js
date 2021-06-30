let proxyObj = {}
proxyObj['/'] = {
    // websocket
    ws: false,
    // 目标地址
    target: 'http://192.168.222.128:80',
    // 发送请求头 host 会被设置成target
    changeOrigin: true,

    // 不重写请求地址
    pathRewrite: {
        '^/': "/"
    }


};

module.exports = {
    devServer: {
        host: 'localhost',
        port: 8080,
        proxy: proxyObj
    },
    publicPath: './',
}