//const router = require('express').Router();
var User = require('../models/user');

exports.getUsers = function(req, res){
  User.find(function(err, users){
      if(err){
        res.send(err);
      }
      res.json(users);
  });
  //res.send("Getting all phones");
};

exports.getUser = function(req, res){

  User.find({id:req.params.id}, function(err, user){
      if(err){
        res.send(err);
      }
      res.json(user);
  });
}

exports.addUser = function(req, res){
  var user = new User();

  user.id = req.body.id;
  user.name = req.body.name;
  user.scoreArray = req.body.scoreArray;

  user.save(function(err){
    if(err){
      res.send(err)
    }
    res.send({message:"userInfo was saved.", data:user});
  });
}

exports.deleteUserStar = function(req, res){
  User.update({id:req.params.id}, function(err, user){
    user.scoreArray.pull({menuId: req,body,menuId});
    user.save(function(err){
      if(err){
        res.send(err)
      }
      res.json({message:"userInfo was deleted", data:user})
    })
  })
}

exports.updateUserStar = function(req, res){
  User.update({id:req.params.id}, function(err, user){
    user.scoreArray.pull({menuId: req.body.menuId});
    user.scoreArray.push({menuId: req.body.menuId, menuName: req.body.menuName, votedNumber: req.body.votedNumber, totalScore: req.body.totalScore});
    user.save(function(err){
      if(err){
        res.send(err)
      }
      res.json({message:"userInfo was updated", data:user});
    });
  });
}

exports.addUserStar = function(req, res){
  User.update({id:req.params.id}, function(err, user){
    user.scoreArray.push({menuId: req.body.menuId, menuName: req.body.menuName, votedNumber: req.body.votedNumber, totalScore: req.body.totalScore});
    user.save(function(err){
      if(err){
        res.send(err)
      }
      res.json({message:"userInfo was deleted", data:user})
    })
  })
}

exports.updateUser = function(req, res){

  User.update({id:req.params.id}, {
    id:req.body.id,
    name:req.body.name,
    scoreArray:req.body.scoreArray,
  }, function(err, num, raw){
    if(err){
      res.send(err)
    }
    res.json(num);
  });
}

exports.deleteUser = function(req, res){

  User.deleteOne({id:req.params.id}, function(err){
      if(err){
        res.send(err)
      }
      res.json({message:"The user was deleted"});
  })
}