// mongo-init.js
db = db.getSiblingDB('eLearning');

db.createUser({
  user: "eLearning",
  pwd: "sisi.123@",
  roles: [{
    role: "dbOwner",
    db: "eLearning"
  }]
});