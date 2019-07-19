const Person = require('../models/person');

//Simple version, without validation or sanitation
exports.test = function (req, res) {
    res.send('Greetings from the Test controller!');
};

exports.personCreate = function (req, res, next) {
    let person = new Person({
            name: req.body.name,
            personId: req.body.personId,
            privateKey: req.body.privateKey,
            qrUrl: "",
            balance: req.body.balance,
            gender: req.body.gender,
            address: req.body.address,
            nid: req.body.nid,
            ttl: req.body.ttl,
        }
    );

    Person.findOne({personId: req.body.personId}, function (err, p) {
        if (err) return next(err);
        if (p === null){
            person.save(function (err) {
                if (err) {
                    return next(err);
                }
                res.status(200).send('Person created successfully')
            })
        } else {
            res.status(406).send('Person already exist!')
        }
    })

    
};

exports.personAddFace = function (req, res, next) {

    Person.findOne({personId: req.params.personId}, function (err, person) {
        if (err) return next(err);

        let face = {
            faceId: req.body.faceId,
            faceData: req.body.faceData,
            faceUrl: req.body.faceUrl,
        }
    
        if (person.personFaces === undefined)
            person.personFaces = [face]
        else
            person.personFaces.push(face)
    
        person.save(function (err) {
            if (err) {
                return next(err);
            }
            res.status(200).send('Person face added successfully')
        })
    });

    //console.log("found person " + JSON.stringify(person));
};

exports.personDeleteFace = function (req, res, next) {
    Person.findOneAndUpdate({personId: req.params.personId}, { $pull: {personFaces: {faceId: req.params.faceId}} }, function (err, person) {
        if (err) return next(err);
        res.status(200).send('Person face deleted successfully');
    });
};

exports.personDetails = function (req, res, next) {
    Person.findOne({personId: req.params.personId}, function (err, person) {
        if (err) return next(err);
        res.status(200).send(person);
    })
};

exports.allPersonDetails = function (req, res, next) {
    Person.find({}, function (err, persons) {
        if (err) return next(err);
        res.status(200).send(persons);
    })
};

exports.personUpdate = function (req, res, next) {
    Person.findOneAndUpdate({personId: req.params.personId}, {$set: req.body}, function (err, person) {
        if (err) return next(err);
        res.status(200).send('Person updated successfully');
    });
};

exports.personDelete = function (req, res, next) {
    Person.findOneAndRemove({personId: req.params.personId}, function (err) {
        if (err) return next(err);
        res.status(200).send('Person deleted successfully');
    })
};