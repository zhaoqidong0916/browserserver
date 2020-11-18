package com.ht.common.service;

import com.ht.common.page.R;

/**
 * @description:
 * @author: yaKun.shi
 * @create: 2020-04-27 09:38
 **/
public interface IHomePageService {
    R getCollectCount(Integer type);

    R getBrowserActiveCount(Integer type);

    R getBrowserUrlCount(Integer type);
}
