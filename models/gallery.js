var mongoose = require('mongoose');

// Define Schemas
var photoSchema = new mongoose.Schema({
  path:  {
    type: String,
    required: true
  },
  label: {
    type: String,
    required: true
  },
  createdAt: {
    type: Date,
    default: Date.now
  }
},
{
  collection: 'gallery'
});

module.exports = mongoose.model("Photo", photoSchema);