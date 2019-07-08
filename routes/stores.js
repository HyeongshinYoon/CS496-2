//const router = require('express').Router();
var Store = require('../models/store');
var Menu = require('../models/menu');
//var ObjectId = mongoose.Schema.Types.ObjectId;

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

exports.getStoreMenu = function(req, res){
  Store.find({ id:req.params.id },{'scoreArray': { $elemMatch:{'menuName':req.body.menuName}}}, function(err, menu){
    if(err){
      res.send(err);
    }
    res.json(menu);
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

   Store.findOneAndUpdate({id:req.params.id}, {$pull: {"scoreArray": {menuId: req.body.menuId}}}, function(err, user){
      if(err){
        res.send(err)
      }
      res.json({message:"storeInfo was updated", data:user});
    });
}

exports.updateStoreStar = function(req, res){

   Store.findOneAndUpdate({id:req.params.id}, {$pull: {"scoreArray": {menuId: req.body.menuId}}}, function(err, user){
      if(err){
        res.send(err)
      }
      res.json({message:"storeInfo was updated", data:user});
    });
   Store.findOneAndUpdate({id:req.params.id}, {$push: {"scoreArray": req.body}}, function(err, user){
      if(err){
        res.send(err)
      }
      res.json({message:"storeInfo was updated", data:user});
    });
}

exports.addStoreStar = function(req, res){
   Store.findOne({id:req.params.id}, function(err, store){
      if(err) return res.send(err);
      if(!store) return res.send(err);
      menu = new Menu();
      menu.menuName = req.body.menuName;
      menu.menuId = req.body.menuId;
      menu.votedNumber = req.body.votedNumber;
      menu.totalScore = req.body.totalScore;

      store.scoreArray.push(menu);
      store.save(function(err){
        if(err) return res.send(err);
      })
      res.json({message:"storeInfo was updated"});
    });
}

exports.updateStore = function(req, res){
  Store.update({id:req.params.id}, {
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