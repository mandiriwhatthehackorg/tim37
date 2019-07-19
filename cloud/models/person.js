const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const PersonFace = new Schema({
    faceId: {type: String, required: true},
    faceData: {type: String, required: true},
    faceUrl: {type: String, required: true},
}, { _id: false });
module.exports = mongoose.model('PersonFace', PersonFace);

let PersonSchema = new Schema({
    name: {type: String, required: true, max: 100},
    personId: {type: String, required: true},
    privateKey: {type: String, required: true},
    qrUrl: {type: String, required: false},
    balance: {type: Number, required: true},
    gender: {type: Number, required: false},
    address: {type: String, required: false},
    nid: {type: String, required: false},
    ttl: {type: String, required: false},
    personFaces: [{type: Schema.Types.Object, ref: 'PersonFace' }],
}, { collection: 'users' });

// Export the model
module.exports = mongoose.model('Person', PersonSchema);