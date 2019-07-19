const express = require('express');
const router = express.Router();

// Require the controllers WHICH WE DID NOT CREATE YET!!
const person_controller = require('../controllers/person.controller');


// a simple test url to check that all of our files are communicating correctly.
router.get('/test', person_controller.test);
router.post('/create', person_controller.personCreate);
router.get('/all', person_controller.allPersonDetails);
router.get('/:personId', person_controller.personDetails);
router.put('/:personId/update', person_controller.personUpdate);
router.delete('/:personId/delete', person_controller.personDelete);
router.post('/:personId/addPersonFace', person_controller.personAddFace);
router.delete('/:personId/:faceId/delete', person_controller.personDeleteFace);
module.exports = router;