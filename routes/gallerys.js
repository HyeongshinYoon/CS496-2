var Photo = require('../models/gallery');
var fs = require("fs");

exports.getPhotos = function(req, res){
  Photo.find(function(err, photos){
      if(err){
        res.send(err);
      }
      res.json(photos);
  });
  //res.send("Getting all phones");
};

exports.getPhoto = function(req, res){
  Photo.find({photoId:req.params.photoId}, function(err, photos){
      if(err){
        res.send(err);
      }
      res.json(photos);
  });
  //res.send("Getting all phones");
};

exports.addPhoto = function(req, res){
  var userPhoto = new Photo();
  
  userPhoto.photoId = req.body.photoId;
  userPhoto.img.data = fs.readFileSync(req.files.userPhoto.path);
  userPhoto.img.contentType = 'image/png';
  userPhoto.save(function(err){
    if(err){
      res.send(err)
    }

    res.send({message:"photo was saved.", data:userPhoto});
  });
}

exports.deletePhoto = function(req, res){

  Photo.deleteOne({photoId:req.params.photoId}, function(err){
      if(err){
        res.send(err)
      }
      res.json({message:"The user was deleted"});
  })
}