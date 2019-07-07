var mongoose = require('mongoose');

var menuSchema = new mongoose.Schema({
  menuId: Number,
  menuName: String,
  votedNumber: Number,
  totalScore: Number
});

// Define Schemas
var storeSchema = new mongoose.Schema({
  id: {
    type: Number
  },
  name: {
    type: String
  },
  scoreArray: {
    type: [menuSchema],
    default: []
  }
},
{
  collection: 'store'
});

module.exports = mongoose.model("Store", storeSchema);