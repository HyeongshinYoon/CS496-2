var Photo = require('../models/gallery');

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
  Photo.findOne({label:req.params.label}, function(err, photos){
    if(err){
      res.send(err);
    }
    var path = require('path')
    var realpath = path.join(__dirname, '..', photos.path)
    res.download(realpath);
  });
  //res.send("Getting all phones");
};

exports.addPhoto = function(req, res){
  var path = require('path')
  var remove = path.join(__dirname, '..', '..', 'public')
  if (!req.file) return res.send('Please upload a file')
  var relPath = req.file.path.replace(remove, '')
  var newImage = new Photo(req.body)
  //newImage.logEntryId = req.params.log_entry_id
  newImage.path = relPath
  newImage.save(function(err, image) {
    if(err) res.send(err)
      res.json(image)
  })
}

exports.deletePhoto = function(req, res){

  Photo.deleteOne({label:req.params.label}, function(err){
      if(err){
        res.send(err)
      }
      res.json({message:"The user was deleted"});
  })
}