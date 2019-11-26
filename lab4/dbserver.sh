#!/bin/bash

CNAME="mongo_node"
case "$1" in
    tty)
        docker exec -it $CNAME /bin/bash
        ;;
    kill)
        docker kill $CNAME
        ;;
    seed)
        # petla panie ferdku mowi to panu cos
        docker exec $CNAME mongoimport --db lab4 --collection business --type json --file /dataset/yelp_academic_dataset_business.json
        docker exec $CNAME mongoimport --db lab4 --collection checkin --type json --file /dataset/yelp_academic_dataset_checkin.json
        # gigabajt. big data boża.
        docker exec $CNAME mongoimport --db lab4 --collection review --type json --file /dataset/yelp_academic_dataset_review.json
        docker exec $CNAME mongoimport --db lab4 --collection tip --type json --file /dataset/yelp_academic_dataset_tip.json
        docker exec $CNAME mongoimport --db lab4 --collection user --type json --file /dataset/yelp_academic_dataset_user.json
        ;;
    *)
        docker pull mongo
        docker run --name $CNAME --rm -it -d -p 27017:27017 -v $(pwd)/dataset:/dataset mongo
        ;;
esac
