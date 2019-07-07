var mongoose = require('mongoose');

// Define Schemes
var scoreSchema = new mongoose.Schema({
  menuNumber: Number,
  score: Number,
});

var userSchema = new mongoose.Schema({
  id: {
    type:Number,
  },
  name: {
    type:String,
  },
  scoreArray: {
    type:[scoreSchema],
    default: []
  },
},
{
  collection: 'user'
});

module.exports = mongoose.model("User", userSchema);