package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class ExampleDaoGenerator {
    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1, "com.example.greendao.dao");
        addBill(schema);

        new DaoGenerator().generateAll(schema, "./app/src/main/java-gen");
    }

    private static void addBill(Schema schema) {
        Entity entity = schema.addEntity("Bill");
        entity.addIdProperty();
        entity.addIntProperty("userId");
        entity.addIntProperty("movieId");
        entity.addStringProperty("date");
        entity.addStringProperty("theaterName");
        entity.addStringProperty("movieName");
        entity.addIntProperty("price");
        entity.addStringProperty("ticketId");
    }
}
