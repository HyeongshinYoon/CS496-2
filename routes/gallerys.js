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
  Photo.findOne({label:req.params.label}, {
    }, function(err, photos){
      if(err){
        res.send(err);
      }
      res.json(photos);
      var npath = __dirname + "/" + path
      res.download(npath);
  });
  //res.send("Getting all phones");
};

exports.addPhoto = function(req, res){
  var photo = new Photo(
)
  photo.label = req.body.label;
  photo.save(function(err){
    if(err){
      res.send(err)
    }

    res.send({message:"phoneInfo was saved.", data:user});
  });
}


exports.updatePhoto = function(req, res){
  Photo.update({label:req.params.label}, {
    var path = require('path')
    var remove = path.join(__dirname, 'public')
    if (!req.file) return res.send('Please upload a file')
    var relPath = req.file.path.replace(remove, '')
    path:relPath
  }, function(err, num, raw){
    if(err) res.send(err)
    res.json(image)
 });
}

exports.deletePhoto = function(req, res){

  Photo.deleteOne({label:req.params.label}, function(err){
      if(err){
        res.send(err)
      }
      res.json({message:"The user was deleted"});
  })
}