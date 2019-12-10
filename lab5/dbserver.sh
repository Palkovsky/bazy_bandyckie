#!/bin/bash

CNAME="neo4j_node"
case "$1" in
    tty)
        docker exec -it $CNAME /bin/bash
        ;;
    kill)
        docker kill $CNAME
        ;;
    *)
        docker pull neo4j
        docker run \
               --name $CNAME --rm -it \
               -p 7687:7687 -p 7474:7474 \
               -v $(pwd)/data:/data \
               --env=NEO4J_AUTH=none \
               --env=NEO4J_dbms_allow__upgrade=true \
               neo4j
        ;;
esac
