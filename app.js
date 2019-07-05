// ENV
//require('dotenv').config();
// DEPENDENCIES
var path = require("path");
var express = require("express");
var bodyParser = require("body-Parser");
var mongoose = require("mongoose");
var phoneController = require("./routes/phones");
var galleryController = require("./routes/gallerys");
var multer = require('multer');
var fs = require("fs");

mongoose.connect('mongodb://localhost:27017/week2', { useNewUrlParser: true });


var storage = multer.diskStorage({
  //경로 설정
  destination : function(req, file, cb){    
    cb(null, 'public/uploads/');
  },
    filename: (req, file, cb) => {
        cb(null, `${new Date().toISOString().replace(/:/g, '-')}${file.originalname}`);
    }
});

var upload = multer({
	storage: storage});


var app = express();

app.use(bodyParser.urlencoded({
	extended: true
}));
app.use(bodyParser.json());

app.use(express.static(path.join(__dirname, 'public')))

var router = express.Router();

app.use("/api", router);
// app.use("/", phoneController);
// app.use("/gallery", galleryController);

router.route("/phones").get(phoneController.getPhones);

router.route("/phone/:id").get(phoneController.getPhone);

router.route("/addPhone").post(phoneController.addPhone);

router.route("/updatePhone/:id").post(phoneController.updatePhone);

router.route("/deletePhone/:id").get(phoneController.deletePhone);

router.route("/photos").get(galleryController.getPhotos);

router.route("/photo/:label").get(galleryController.getPhoto);

router.route("/addPhoto").post(galleryController.addPhoto);

router.route("/addPhoto/:label").post(upload.single("data"), galleryController.updatePhoto);

router.route("/deletePhoto/:label").get(galleryController.deletePhoto);

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