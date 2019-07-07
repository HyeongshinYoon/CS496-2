//const router = require('express').Router();
var User = require('../models/phone');

exports.getPhones = function(req, res){
  User.find(function(err, users){
      if(err){
        res.send(err);
      }
      res.json(users);
  });
  //res.send("Getting all phones");
};

exports.getPhone = function(req, res){

  User.find({id:req.params.id}, function(err, user){
      if(err){
        res.send(err);
      }
      res.json(user);
  });
}

exports.addPhone = function(req, res){
  var user = new User();

  user.id = req.body.id;
  user.name = req.body.name;
  user.img = req.body.img;
  user.phone = req.body.phone;
  user.group = req.body.group;
  user.email = req.body.email;

  user.save(function(err){
    if(err){
      res.send(err)
    }

    res.send({message:"phoneInfo was saved.", data:user});
  });
}

exports.updatePhone = function(req, res){

  User.update({id:req.params.id}, {
    id:req.body.id,
    name:req.body.name,
    img:req.body.img,
    phone:req.body.phone,
    group:req.body.group,
    email:req.body.email
  }, function(err, num, raw){
    if(err){
      res.send(err)
    }
    res.json(num);
  });
}

exports.deletePhone = function(req, res){

  User.deleteOne({id:req.params.id}, function(err){
      if(err){
        res.send(err)
      }
      res.json({message:"The user was deleted"});
  })
}