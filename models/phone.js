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
    default:"",
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

// // Create new todo document
// phoneSchema.statics.create = function (payload) {
//   // this === Model
//   const phone = new this(payload);
//   // return Promise
//   return phone.save();
// };

// // Find All
// phoneSchema.statics.findAll = function () {
//   // return promise
//   // V4부터 exec() 필요없음
//   return this.find({});
// };

// // Find One by todoid
// phoneSchema.statics.findOneByPhoneid = function (id) {
//   return this.findOne({ id });
// };

// // Update by todoid
// phoneSchema.statics.updateByPhoneid = function (id, payload) {
//   // { new: true }: return the modified document rather than the original. defaults to false
//   return this.findOneAndUpdate({ id }, payload, { new: true });
// };

// // Delete by todoid
// phoneSchema.statics.deleteByPhoneid = function (id) {
//   return this.remove({ id });
// };

// Create Model & Export
