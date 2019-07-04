var mongoose = require('mongoose');

// Define Schemas
var photoSchema = new mongoose.Schema({
  photoId: {
    type: Number, required: true
  },
  path:  { type: String },
  img: {
    data: Buffer, contentType: String
  },
  timestamp: {type: Date, default: Date.now}
},
{
  collection: 'gallery'
});

module.exports = mongoose.model("Photo", photoSchema);