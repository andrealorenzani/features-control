curl -X POST localhost:8080/user/add/id0/pippo
curl -X POST localhost:8080/user/add/id1/pluto

curl -X POST localhost:8080/capability/add/read
curl -X POST localhost:8080/capability/add/write
curl -X POST localhost:8080/capability/add/access
curl -X POST localhost:8080/capability/add/execute

curl -X PUT localhost:8080/user/add/capabilities/id0/read
curl -X PUT localhost:8080/user/add/capabilities/id0/write
curl -X PUT localhost:8080/user/add/capabilities/id1/write
curl -X PUT localhost:8080/user/add/capabilities/id1/access,execute

curl -X POST localhost:8080/company/add/cp1/TIDE
curl -X POST localhost:8080/company/add/cp2/BBC

curl -X PUT localhost:8080/company/add/capabilities/cp1/read
curl -X PUT localhost:8080/company/add/capabilities/cp1/write
curl -X PUT localhost:8080/company/add/capabilities/cp2/access,execute

curl -X POST localhost:8080/version/add/10092017
curl -X POST localhost:8080/version/add/20092017

curl -X PUT localhost:8080/version/add/capabilities/10092017/write
curl -X PUT localhost:8080/version/add/capabilities/20092017/read,access

curl -X POST localhost:8080/user/add/id2/superman
curl -X POST localhost:8080/version/add/30092017
curl -X POST localhost:8080/company/add/cp3/SKYNET
curl -X PUT localhost:8080/user/add/capabilities/id2/read,write,access,execute
curl -X PUT localhost:8080/version/add/capabilities/30092017/read,access,execute
curl -X PUT localhost:8080/company/add/capabilities/cp3/write,access,execute
