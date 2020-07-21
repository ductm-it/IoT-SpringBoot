package com.iot.platform.Service;

import java.io.IOException;
import java.util.List;

import javax.annotation.PreDestroy;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@SuppressWarnings("deprecation")
public class ElasticService {

    protected static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").serializeNulls().create();

    @Autowired
    protected RestHighLevelClient restClient;

    public BulkResponse bulkIndex(String index, List<?> list) throws IOException {
        BulkRequest bulkRequest = new BulkRequest();

        list.forEach(t -> {
            IndexRequest indexRequest = new IndexRequest(index).type("_doc").source(gson.toJson(t), XContentType.JSON);
            bulkRequest.add(indexRequest);
        });

        BulkResponse bulkResponse = restClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        return bulkResponse;
    }

    public BulkResponse bulkIndex(String index, Object object) throws IOException {
        BulkRequest bulkRequest = new BulkRequest();

        IndexRequest indexRequest = new IndexRequest(index).type("_doc").source(gson.toJson(object), XContentType.JSON);
        bulkRequest.add(indexRequest);

        BulkResponse bulkResponse = restClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        return bulkResponse;
    }

    @PreDestroy
    public void cleanup() {
        try {
            System.out.println("Closing the ES REST client");
            this.restClient.close();
        } catch (IOException ioe) {
            System.out.println("Problem occurred when closing the ES REST client: " + ioe.getMessage());
        }
    }

}