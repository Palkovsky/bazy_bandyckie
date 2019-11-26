package pl.edu.agh.bd.mongo;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class MongoLab {
	private MongoClient mongoClient;
	private DB db;
	
	public MongoLab() throws UnknownHostException {
		mongoClient = new MongoClient();
		db = mongoClient.getDB("admin");
	}
	
	private void showCollections(){
		for(String name : db.getCollectionNames()){
			System.out.println("colname: "+name);
		}
	}

	public static void main(String[] args) throws UnknownHostException {
		MongoLab mongoLab = new MongoLab();
		mongoLab.showCollections();
	}

}
