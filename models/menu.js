var mongoose = require('mongoose');

var menuSchema = new mongoose.Schema({
   _id: { type: String },
  menuId: Number,
  menuName: String,
  votedNumber: Number,
  totalScore: Number
},{ _id: false });


module.exports = mongoose.model("Menu", menuSchema);