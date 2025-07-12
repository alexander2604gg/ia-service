package com.ia.alexander.service;

import java.util.List;

public interface BucketService {
    String createBucket (String bucketName);
    boolean checkIfBucketExist (String bucketName);
    List<String> listBuckets ();
}
