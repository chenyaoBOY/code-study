package org.study.mongo;

import com.alibaba.fastjson.JSON;
import com.mongodb.client.*;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.study.mongo.entity.OrderInfo;
import org.study.mongo.entity.OrderProduct;
import org.study.mongo.entity.OrderUser;

/**
 * @author chenyao
 * @date 2021/2/7 16:27
 * @description
 */
@Slf4j
public class MongoApi {

    public static void main(String[] args) {
        MongoClient mongoClient = MongoClientManage.getMongoClient();

        MongoDatabase localDb = mongoClient.getDatabase("local");
        findFirst(localDb);
        mongoClient.close();
    }

    private static void findFirst(MongoDatabase dbName){
        MongoCollection<OrderInfo> demo = dbName.getCollection("demo",OrderInfo.class);
        FindIterable<OrderInfo> documents = demo.find();
        for (OrderInfo document : documents) {
            log.warn("document content:{}",JSON.toJSONString(document));
        }
    }


    private void readInfo(MongoClient mongoClient) {

        MongoIterable<String> dbNames = mongoClient.listDatabaseNames();
        for (String dbName : dbNames) {
            log.warn("dbName:{}", dbName);
        }
        MongoDatabase localDb = mongoClient.getDatabase("local");
        MongoIterable<String> listCollectionNames = localDb.listCollectionNames();
        for (String listCollectionName : listCollectionNames) {
            log.warn("listCollectionName:{}", listCollectionName);
        }
    }

    private void insert(MongoDatabase dbName) {
        MongoCollection<OrderInfo> demo = dbName.getCollection("demo", OrderInfo.class);
        demo.insertOne(getOrderInfo());
    }

    private static OrderInfo getOrderInfo() {
        OrderUser orderUser = new OrderUser(1001L, "chenyao");
        OrderProduct orderProduct = new OrderProduct(100L, "苹果");
        return new OrderInfo("GSDKLGN123", "12.8", orderUser, orderProduct);
    }
}
