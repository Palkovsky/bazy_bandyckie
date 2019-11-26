package pl.edu.agh.bd.mongo;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import com.mongodb.*;

import static com.mongodb.client.model.Filters.eq;

public class MongoLab {
	private static final String DB_NAME = "lab4";
	private static final String HOSTNAME = "localhost";
	private static final int PORT = 27017;

	private MongoClient mongoClient;
	private DB db;

	public MongoLab() throws UnknownHostException {
		mongoClient = new MongoClient(HOSTNAME, PORT);
		db = mongoClient.getDB(DB_NAME);
	}

	private void showCollections() {
		for (String name : db.getCollectionNames()) {
			System.out.println("colname: " + name);
		}
	}

	private void _6a() {
		DBCursor cursor = db.getCollection("business")
				.find(new BasicDBObject("stars", 5.0));

		while (cursor.hasNext()) {
			System.out.println(cursor.next());
		}
		cursor.close();
	}

	private void _6b() {
    	/* 5a
    	   db.business.aggregate([
			 {$match: {categories: {$elemMatch: {$eq: "Restaurants"}}}},
			 {$group: { _id: "$city", total: { $sum: 1} }},
			 {$sort: {total: -1}}
		   ]);
    	 */

		AggregationOutput output = db.getCollection("business")
				.aggregate(new ArrayList<>(Arrays.asList(
						new BasicDBObject("$match",
								new BasicDBObject("categories",
										new BasicDBObject("$elemMatch", new BasicDBObject("$eq", "Restaurants")))),
						new BasicDBObject("$group",
								new BasicDBObject("_id", "$city")
										.append("total", new BasicDBObject("$sum", 1))),
						new BasicDBObject("$sort", new BasicDBObject("total", -1))
				)));

		for (DBObject object : output.results()) {
			System.out.println(object);
		}
	}

	private void _6c() {
		/*
		db.business.aggregate([
			{$match: {
				categories: {$elemMatch: {$eq: "Hotels"}},
				"attributes.Wi-Fi": {$eq: "free"},
				stars: {$gte: 4.5}
			}},
			{$group: { _id: "$state", total: { $sum: 1} }},
			{$sort: {total: -1} }
		]);
		 */

		AggregationOutput output = db.getCollection("business")
				.aggregate(new ArrayList<>(Arrays.asList(
						new BasicDBObject("$match",
								new BasicDBObject()
										.append("categories",
												new BasicDBObject("$elemMatch",
														new BasicDBObject("$eq", "Hotels")))
										.append("attributes.Wi-Fi",
												new BasicDBObject("$eq", "free"))
										.append("stars",
												new BasicDBObject("$gte", 4.5)))
						,
						new BasicDBObject("$group",
								new BasicDBObject("_id", "$state")
										.append("total", new BasicDBObject("$sum", 1))),
						new BasicDBObject("$sort", new BasicDBObject("total", -1))
				)));

		for (DBObject object : output.results()) {
			System.out.println(object);
		}
	}

	private void _7() {
		AggregationOutput output = db.getCollection("review")
				.aggregate(new ArrayList<>(Arrays.asList(
						new BasicDBObject("$match",
								new BasicDBObject("stars",
										new BasicDBObject("$gte", 4.5))),
						new BasicDBObject("$group",
								new BasicDBObject("_id", "$user_id")
										.append("total", new BasicDBObject("$sum", 1))),
						new BasicDBObject("$sort", new BasicDBObject("total", -1))
				)));

		Iterator<DBObject> iterator = output.results().iterator();
		if (!iterator.hasNext()) {
			System.out.println("ERROR: Unable to locate user.");
			return;
		}

		DBObject first = iterator.next();
		String userId = (String) first.get("_id");

		Cursor cursor = db.getCollection("user")
				.find(new BasicDBObject("user_id", userId));

		if (cursor.hasNext()) {
			System.out.println(cursor.next());
			return;
		}

		System.out.println("ERROR: Unable to locate user.");
	}

	private void _8() {
		DBCollection collection = db.getCollection("review");

		String mapFunc =
				"function() { \n" +
						"        emit(\"counts\", \n" +
						"            {\n" +
						"                \"funny\" : this.votes.funny,\n" +
						"                \"useful\": this.votes.useful,\n" +
						"                \"cool\": this.votes.cool\n" +
						"            }\n" +
						"    )}";
		String reduceFunc =
				"function(key, values) {\n" +
						"        let summed = {\"funny\": 0, \"useful\": 0, \"cool\": 0};\n" +
						"        for(let i=0; i<values.length; i++) {\n" +
						"            summed[\"funny\"] += values[i][\"funny\"];\n" +
						"            summed[\"useful\"] += values[i][\"useful\"];\n" +
						"            summed[\"cool\"] += values[i][\"cool\"];\n" +
						"        }\n" +
						"        return summed;\n" +
						"    }";

		MapReduceCommand mapRed =
				new MapReduceCommand(collection, mapFunc, reduceFunc, null, MapReduceCommand.OutputType.INLINE, null);

		MapReduceOutput output = collection.mapReduce(mapRed);
		System.out.println(output.results().iterator().next());
	}

	public static void main(String[] args) throws UnknownHostException {
		MongoLab mongoLab = new MongoLab();
		mongoLab.showCollections();
		// mongoLab._6a();
		// mongoLab._6b();
		// mongoLab._6c();
		// mongoLab._7();
		mongoLab._8();
	}
}
