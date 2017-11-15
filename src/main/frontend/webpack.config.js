var path = require('path');
var ngAnnotatePlugin = require('ng-annotate-webpack-plugin');

const PATHS = {
    source: path.join(__dirname, 'app'),
    build: path.join(__dirname, '../../../target/classes/static')
};

module.exports = {
    entry: [
        PATHS.source
    ],
    output: {
        path: PATHS.build,
        publicPath: '',
        filename: 'bundle.js'
    },
    module: {
        rules: [{
            test: /\.js$/,
            loader: 'babel-loader',
            exclude: /node_modules/
        },{
            test: /\.css$/,
            loader: 'css-loader'
        },{
            test: /\.html$/,
            loader: 'raw-loader'
        }]
    },
    plugins: [
        new ngAnnotatePlugin({add: true})
    ]
};