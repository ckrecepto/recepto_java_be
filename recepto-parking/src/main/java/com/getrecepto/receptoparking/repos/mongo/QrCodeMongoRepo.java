package com.getrecepto.receptoparking.repos.mongo;

import com.getrecepto.receptoparking.constants.VehicleType;
import com.getrecepto.receptoparking.entities.mongo.QrCodeDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Repository
public class QrCodeMongoRepo {
    @Autowired
    private MongoTemplate mongoTemplate;

    private final int BUFFER_MINUTES = 10;

    public int insertQrCodes(List<QrCodeDocument> qrCodeDocumentList) {
        Collection collection = this.mongoTemplate.insertAll(qrCodeDocumentList);
        return collection.size();
    }

    //everytime this method is being called, will return unused qrCode.
    public QrCodeDocument getAvailableQrCodeDocument(VehicleType vehicleType, float amount) {
        LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(this.BUFFER_MINUTES);
        Query query = new Query();
        // get query to get unused and unexpired document with least expiry time avl
        query
                .addCriteria(Criteria.where("vehicleType").is(vehicleType))
                .addCriteria(Criteria.where("amount").is(amount))
                .addCriteria(Criteria.where("used").is(false))
                .addCriteria(Criteria.where("expiry").gte(expiryTime))
                .with(Sort.by(Sort.Direction.ASC, "expiry"));
        List<QrCodeDocument> qrCodeDocumentList = this.mongoTemplate.find(query, QrCodeDocument.class);
        if (qrCodeDocumentList == null || qrCodeDocumentList.size() == 0) {
            return null;
        }
        QrCodeDocument res = qrCodeDocumentList.get(0);
        res.setUsed(true);
        //Update record in mongo
        mongoTemplate.save(res);
        //
        return res;
    }

    public int getAvailableQrCodes(VehicleType vehicleType, float amount) {
        LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(this.BUFFER_MINUTES);
        Query query = new Query();
        // get query to get unused and unexpired document with least expiry time avl
        query
                .addCriteria(Criteria.where("vehicleType").is(vehicleType))
                .addCriteria(Criteria.where("amount").is(amount))
                .addCriteria(Criteria.where("used").is(false))
                .addCriteria(Criteria.where("expiry").gte(expiryTime))
                .with(Sort.by(Sort.Direction.ASC, "expiry"));
        List<QrCodeDocument> qrCodeDocumentList = this.mongoTemplate.find(query, QrCodeDocument.class);
        if (qrCodeDocumentList == null || qrCodeDocumentList.size() == 0) {
            return 0;
        }
        return qrCodeDocumentList.size();
    }
}
