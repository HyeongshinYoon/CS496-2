var mongoose = require('mongoose');

var menuSchema = new mongoose.Schema({
   _id: { type: String },
  menuId: Number,
  menuName: String,
  votedNumber: Number,
  totalScore: Number
},{ _id: false });

// Define Schemas
var storeSchema = new mongoose.Schema({
  id: {
    type: String
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