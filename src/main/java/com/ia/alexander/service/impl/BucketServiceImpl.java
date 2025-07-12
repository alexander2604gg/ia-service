package com.ia.alexander.service.impl;

import com.ia.alexander.service.BucketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BucketServiceImpl implements BucketService {

    private final S3Client s3Client;

    @Override
    public String createBucket(String bucketName) {
        CreateBucketResponse createBucketResponse = s3Client.createBucket(bucketBuilder -> bucketBuilder.bucket(bucketName));
        return createBucketResponse.location();
    }

    @Override
    public boolean checkIfBucketExist(String bucketName) {
        try {
            s3Client.headBucket(b -> b.bucket(bucketName));
            return true;
        } catch (S3Exception e) {
            if (e.statusCode() == 404) {
                return false;
            }
            throw e;
        }
    }

    @Override
    public List<String> listBuckets() {
        ListBucketsResponse bucketsResponse = s3Client.listBuckets();
        List<String> bucketsName = List.of();
        if(bucketsResponse.hasBuckets()){
            bucketsName = bucketsResponse.buckets()
                    .stream()
                    .map(Bucket::name)
                    .toList();
        }
        return bucketsName;
    }

}
