//const router = require('express').Router();
var Phone = require('../models/phone');

exports.getPhones = function(req, res){
  Phone.find(function(err, users){
      if(err){
        res.send(err);
      }
      res.json(users);
  });
  //res.send("Getting all phones");
};

exports.getPhone = function(req, res){

  Phone.find({id:req.params.id}, function(err, phone){
      if(err){
        res.send(err);
      }
      res.json(phone);
  });
}

exports.addPhone = function(req, res){
  var phone = new Phone();

  phone.id = req.body.id;
  phone.name = req.body.name;
  phone.img = req.body.img;
  phone.phone = req.body.phone;
  phone.group = req.body.group;
  phone.email = req.body.email;

  phone.save(function(err){
    if(err){
      res.send(err)
    }

    res.send({message:"phoneInfo was saved.", data:phone});
  });
}

exports.updatePhone = function(req, res){

  Phone.update({id:req.params.id}, {
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

  Phone.deleteOne({id:req.params.id}, function(err){
      if(err){
        res.send(err)
      }
      res.json({message:"The user was deleted"});
  })
}