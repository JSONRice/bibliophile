var path = require('path');
var webpack = require('webpack');

var NgAnnotatePlugin = require('ng-annotate-webpack-plugin');
var HtmlWebpackPlugin = require('html-webpack-plugin');
var ExtractTextPlugin = require('extract-text-webpack-plugin');

const PATHS = {
    source: path.join(__dirname, 'app'),
    vendor: ['angular', 'angular-ui-router'],
    build: path.join(__dirname, '../../../target/classes/static')
};

module.exports = {
    entry: {
        app: PATHS.source,
        vendor: PATHS.vendor
    },
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
            use: ExtractTextPlugin.extract({
                use: 'css-loader',
                fallback: 'style-loader'
            })
        },{
            test: /\.scss$/,
            use: ExtractTextPlugin.extract({
                use: ['css-loader', 'sass-loader'],
                fallback: 'style-loader'
            })
        },{
            test: /\.html$/,
            loader: 'raw-loader'
        }]
    },
    plugins: [
        new ExtractTextPlugin({filename: 'bundle.css'}),
        new NgAnnotatePlugin({add: true}),
        new HtmlWebpackPlugin({template: 'index.html'}),
        new webpack.optimize.CommonsChunkPlugin({name: 'vendor', filename: 'vendor.js'})
    ]
};
