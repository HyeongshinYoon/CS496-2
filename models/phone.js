var mongoose = require('mongoose');

// Define Schemes
var phoneSchema = new mongoose.Schema({
  id: {
    type:Number,
  },
  name: {
    type:String,
  },
  img: {
    type:String,
    default:null,
  },
  phone: {
    type:String,
  },
  email: {
    type:String,
  },
  group: {
    type:String,
  },
},
{
  collection: 'phone'
});

module.exports = mongoose.model("Phone", phoneSchema);