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

  User.remove({_id:req.params.id}, function(err){
      if(err){
        res.send(err)
      }
      res.json({message:"The user was deleted"});
  })
}



// Find All
// router.get('/', (req, res) => {
//   Phone.findAll()
//     .then((phones) => {
//       if (!phones.length) return res.status(404).send({ err: 'Phone not found' });
//       res.send(`find successfully: ${phones}`);
//     })
//     .catch(err => res.status(500).send(err));
// });

// // Find One by phoneid
// router.get('/phoneid/:phoneid', (req, res) => {
//   Phone.findOneByPhoneid(req.params.phoneid)
//     .then((phone) => {
//       if (!phone) return res.status(404).send({ err: 'Phone not found' });
//       res.send(`findOne successfully: ${phone}`);
//     })
//     .catch(err => res.status(500).send(err));
// });

// // Create new phone document
// router.post('/', (req, res) => {
//   Phone.create(req.body)
//     .then(phone => res.send(phone))
//     .catch(err => res.status(500).send(err));
// });

// // Update by phoneid
// router.put('/phoneid/:phoneid', (req, res) => {
//   Phone.updateByPhoneid(req.params.phoneid, req.body)
//     .then(phone => res.send(phone))
//     .catch(err => res.status(500).send(err));
// });

// // Delete by phoneid
// router.delete('/phoneid/:phoneid', (req, res) => {
//   Phone.deleteByPhoneid(req.params.phoneid)
//     .then(() => res.sendStatus(200))
//     .catch(err => res.status(500).send(err));
// });

// module.exports = router;