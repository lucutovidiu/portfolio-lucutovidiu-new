package com.lucutovidiu.expiredprod;

import org.springframework.stereotype.Service;

@Service
public interface ExpiredProducts {

    void emailExpiredProducts();
}
