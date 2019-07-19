// app.js
const express = require('express');
const bodyParser = require('body-parser');
var https = require('https');
var http = require('http');
var forceSsl = require('express-force-ssl');
var path = require("path");

const person = require('./routes/person.route'); // Imports routes for the products
// initialize our express app
const app = express();
var fs = require('fs');
var key = fs.readFileSync(path.resolve(__dirname, 'private.key'));
var cert = fs.readFileSync(path.resolve(__dirname, 'primary.crt'));
var ca = fs.readFileSync(path.resolve(__dirname, 'intermediate.crt'));

var options = {
    key: key,
    cert: cert,
    ca: ca
  };

// Set up mongoose connection
const mongoose = require('mongoose');
let dev_db_url = 'mongodb://103.103.181.19:27017/bio';
const mongoDB = process.env.MONGODB_URI || dev_db_url;
mongoose.connect(mongoDB, { useNewUrlParser: true });
mongoose.Promise = global.Promise;
const db = mongoose.connection;
db.on('error', console.error.bind(console, 'MongoDB connection error:'));

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: false}));

app.use('/person', person);

let port = 1234;

app.use(forceSsl);

// app.listen(port, () => {
//   console.log('Server is up and running on port numner ' + port);
// });

https.createServer(options, app).listen(port, () => {
    console.log('Server is up and running on port numner ' + port);
});