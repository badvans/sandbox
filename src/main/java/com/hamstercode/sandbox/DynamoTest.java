package com.hamstercode.sandbox;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.*;
import java.util.UUID;


public class DynamoTest {

    public static void main(String[] args) {
        System.out.println("before");
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-west-2"))
                .build();
        DynamoDB dynamoDB = new DynamoDB(client);
      /*  ArrayList<AttributeDefinition> attributeDefinitions = new ArrayList<>();
        AttributeDefinition attr1 = new AttributeDefinition();
        attr1.setAttributeName("ID");
        attr1.setAttributeType(ScalarAttributeType.S);
        attributeDefinitions.add(attr1);
        ArrayList<KeySchemaElement> keySchema = new ArrayList<>();
        KeySchemaElement e = new KeySchemaElement();
        e.setAttributeName("ID");
        e.setKeyType(KeyType.HASH);
        keySchema.add(e);
        ProvisionedThroughput provisionedThroughput = new ProvisionedThroughput();
        provisionedThroughput.setReadCapacityUnits(1L);
        provisionedThroughput.setWriteCapacityUnits(1L);
        client.createTable(attributeDefinitions, "TestTable", keySchema, provisionedThroughput);*/
        client.listTables().getTableNames().forEach(System.out::println);

        Table testTable = dynamoDB.getTable("TestTable");
        String uuid = UUID.randomUUID().toString();
        Item item = new Item().withPrimaryKey("ID", "AAAAA").withString("Some_key", "Perfect fucking value");
        testTable.putItem(item);

        ItemCollection<ScanOutcome> scanOutcome = testTable.scan(new ScanFilter("ID").exists());
        System.out.println(scanOutcome.getAccumulatedItemCount());

        Item item1 = testTable.getItem("ID", "AAAAA");
        System.out.println(item1.toJSONPretty());

        client.shutdown();
        System.out.println("after");
    }
}
