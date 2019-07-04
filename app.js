// ENV
//require('dotenv').config();
// DEPENDENCIES
var express = require("express");
var bodyParser = require("body-Parser");
var mongoose = require("mongoose");
var phoneController = require("./routes/phones");


mongoose.connect('mongodb://localhost:27017/phones', { useNewUrlParser: true });

var app = express();

app.use(bodyParser.urlencoded({
	extended: true
}));
app.use(bodyParser.json());

var router = express.Router();

app.use("/api", router);

router.route("/phones").get(phoneController.getPhones);

router.route("/phone/:id").get(phoneController.getPhone);

router.route("/addPhone").post(phoneController.addPhone);

router.route("/updatePhone/:id").post(phoneController.updatePhone);

router.route("/deletePhone/:id").get(phoneController.deletePhone);

app.listen(3000);
// const app = express();
// const port = process.env.PORT || 4500;

// // Static File Service
// app.use(express.static('public'));
// // Body-parser
// app.use(bodyParser.urlencoded({ extended: true }));
// app.use(bodyParser.json());

// // Node의 native Promise 사용
// mongoose.Promise = global.Promise;

// // Connect to MongoDB
// mongoose.connect(process.env.MONGO_URI, { useNewUrlParser: true })
//   .then(() => console.log('Successfully connected to mongodb'))
//   .catch(e => console.error(e));

// // ROUTERS
// app.use('/phones', require('./routes/phones'));

// app.listen(port, () => console.log(`Server listening on port ${port}`));