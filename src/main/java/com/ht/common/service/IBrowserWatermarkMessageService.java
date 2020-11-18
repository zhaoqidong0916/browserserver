package com.ht.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ht.common.entity.BrowserWatermarkConfig;
import com.ht.common.page.R;
import com.ht.common.request.watermark.WatermarkPageRequest;
import com.ht.common.request.watermark.WatermarkUpdateRequest;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yakun.shi
 * @since 2020-03-06
 */
public interface IBrowserWatermarkMessageService extends IService<BrowserWatermarkConfig> {

    R watermarkUpdate(WatermarkUpdateRequest request);

    R watermarkList(WatermarkPageRequest request);

    R watermarkDetail(String id);

    R watermarkUpload(MultipartFile file);
}
