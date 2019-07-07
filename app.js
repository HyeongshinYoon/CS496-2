// ENV
//require('dotenv').config();
// DEPENDENCIES
var path = require("path");
var express = require("express");
var bodyParser = require("body-Parser");
var mongoose = require("mongoose");
var phoneController = require("./routes/phones");
var galleryController = require("./routes/gallerys");
var userController = require("./routes/users");
var storeController = require("./routes/stores");
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

app.use(express.static(path.join(__dirname, '..', '..', 'public')))

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
router.route("/addPhoto").post(upload.single("data"), galleryController.addPhoto);
router.route("/deletePhoto/:label").get(galleryController.deletePhoto);

router.route("/users").get(userController.getUsers);
router.route("/user/:id").get(userController.getUser);
router.route("/addUser").post(userController.addUser);
router.route("/updateUser").post(userController.updateUser);
router.route("/deleteUserStar/:id").post(userController.deleteUserStar);
router.route("/updateUserStar/:id").post(userController.updateUserStar);
router.route("/addUserStar/:id").post(userController.addUserStar);
router.route("/deleteUser/:id").get(userController.deleteUser);

router.route("/stores").get(storeController.getStores);
router.route("/store/:id").get(storeController.getStore);
router.route("/addStore").post(storeController.addStore);
router.route("/updateStore").post(storeController.updateStore);
router.route("/deleteStoreStar/:id").post(storeController.deleteStoreStar);
router.route("/updateStoreStar/:id").post(storeController.updateStoreStar);
router.route("/addStoreStar/:id").post(storeController.addStoreStar);
router.route("/deleteStore/:id").get(storeController.deleteStore);

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