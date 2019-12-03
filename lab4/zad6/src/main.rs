use mongodb::{
    ThreadedClient,
    Client,
    bson, doc,
    db::{Database, ThreadedDatabase},
    coll::options::FindOptions
};

static HOSTNAME: &str = "localhost";
static PORT: u16 = 27017;
static DB: &str = "lab4";

fn main() {
    let client = Client::connect(HOSTNAME, PORT)
        .expect("Failed to initialize client.");

    let db = client.db(DB);

    // _a(&db);
    // _b(&db);
    // _c(&db);
    // _d(&db);
    // _e(&db);
    // _f(&db);
    _g(&db);
}

fn _a(db: &Database) {
    db.collection("business")
        .aggregate(vec![
            doc! {"$group": {"_id": "$city"}},
            doc! {"$sort": {"_id": 1}}
        ], None)
        .unwrap()
        .for_each(|item| {
            let name = {
                let unwrapped = item.unwrap();
                let city = unwrapped.get_str("_id").unwrap();
                String::from(city)
            };
            println!("{}", name)
        });
}

fn _b(db: &Database) {
    let query = doc! {"$where": "function() { return new Date(this.date).getFullYear() >= 2011}"};
    let count = db.collection("review")
        .find(Some(query), None)
        .unwrap()
        .count();
    println!("{}", count);
}

fn _c(db: &Database) {
    let query = doc! {"open": false};
    let projection = doc! {"name": 1, "full_address": 1, "stars": 1};
    let options = FindOptions {
        projection: Some(projection),
        ..Default::default()
    };
    db.collection("business")
        .find(Some(query), Some(options))
        .unwrap()
        .for_each(|item| {
            let unwraped = item.unwrap();
            let name = unwraped.get_str("name")
                .unwrap();
            let full_address = unwraped.get_str("full_address")
                .unwrap();
            let stars = unwraped.get_f64("stars")
                .unwrap();

            println!("Name: {}, Address: {}, Stars: {}",
                     name, full_address, stars)
        });
}

fn _d(db: &Database) {
    let query = doc! {"$and": [{"votes.funny": 0}, {"votes.useful": 0}]};
    let sort = doc! {"name": 1};
    let options = FindOptions {
        sort: Some(sort),
        ..Default::default()
    };
    db.collection("user")
        .find(Some(query), Some(options))
        .unwrap()
        .for_each(|item| {
            println!("{:?}", item.unwrap());
        });
}

fn _e(db: &Database) {
    db.collection("tip")
        .aggregate(vec![
            doc! {"$match": {"date": {"$regex": "^2012-*"}}},
            doc! {"$group": {"_id": "$business_id", "cnt": {"$sum": 1}}},
            doc! {"$sort": {"cnt": 1}}
        ], None)
        .unwrap()
        .for_each(|item| {
            println!("{:?}", item.unwrap());
        });
}

fn _f(db: &Database) {
    db.collection("review")
        .aggregate(vec![
            doc! {"$match": {"stars" : {"$gte": 4.0}}},
            doc! {"$group": {"_id": "$business_id", "avg": {"$avg": "$stars"}}},
        ], None)
        .unwrap()
        .for_each(|item| {
            println!("{:?}", item.unwrap());
        });
}

fn _g(db: &Database) -> i32 {
    let filter = doc! {"stars" : 2.0};
    db.collection("business")
        .delete_many(filter, None)
        .map(|result| result.deleted_count)
        .unwrap_or(0)
}
