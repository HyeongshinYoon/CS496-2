//const router = require('express').Router();
var Store = require('../models/store');

exports.getStores = function(req, res){
  Store.find(function(err, users){
      if(err){
        res.send(err);
      }
      res.json(users);
  });
  //res.send("Getting all phones");
};

exports.getStore = function(req, res){

  Store.find({id:req.params.id}, function(err, user){
      if(err){
        res.send(err);
      }
      res.json(user);
  });
}

exports.addStore = function(req, res){
  var store = new Store();

  store.id = req.body.id;
  store.name = req.body.name;
  store.scoreArray = req.body.scoreArray;

  store.save(function(err){
    if(err){
      res.send(err)
    }

    res.send({message:"storeInfo was saved.", data:store});
  });
}

exports.deleteStoreStar = function(req, res){
  Store.update({id:req.parms.id}, function(err, user){
    user.scoreArray.pull({menuNumber: req.body.id});
    user.save(function(err){
      if(err){
        res.send(err)
      }
      res.json({message:"userInfo was updated", data:user});
    });
  });
}

exports.updateStoreStar = function(req, res){
  Store.update({id:req.parms.id}, function(err, user){
    user.scoreArray.pull({menuNumber: req.body.id});
    user.scoreArray.push({menuNumber: req.body.id, score: req.body.score});
    user.save(function(err){
      if(err){
        res.send(err)
      }
      res.json({message:"userInfo was updated", data:user});
    });
  });
}

exports.addStoreStar = function(req, res){
  Store.update({id:req.parms.id}, function(err, user){
    user.scoreArray.push({menuNumber: req.body.id, score: req.body.score});
    user.save(function(err){
      if(err){
        res.send(err)
      }
      res.json({message:"userInfo was updated", data:user});
    });
  });
}

exports.updateStore = function(req, res){

  Store.update({id:req.params.id}, {
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

exports.deleteStore = function(req, res){

  Store.deleteOne({id:req.params.id}, function(err){
      if(err){
        res.send(err)
      }
      res.json({message:"The store was deleted"});
  })
}