package com.ht.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ht.common.entity.BrowserVisitSafe;
import com.ht.common.page.R;
import com.ht.common.request.safe.visit.VisitAddRequest;
import com.ht.common.request.safe.visit.VisitPageRequest;
import com.ht.common.request.safe.visit.VisitStateEditRequest;
import com.ht.common.request.safe.visit.VisitUpdateRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yakun.shi
 * @since 2020-03-09
 */
public interface IBrowserVisitSafeService extends IService<BrowserVisitSafe> {

    R visitAdd(VisitAddRequest request);

    R visitUpdate(VisitUpdateRequest request);

    R visitList(VisitPageRequest request);

    R visitDelete(String id);

    R visitDetail(String id);

    R visitStateEdit(VisitStateEditRequest request);
}
