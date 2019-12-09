package com.card.alumni.service;

import com.card.alumni.common.UnifiedResponse;
import com.card.alumni.vo.query.AlumniQuery;

import java.util.Date;

/**
 * @author sunxiaodong10 2019/12/9
 * @date 11:03 PM
 */
public interface AlumniService {

    /**
     * 查询协会校友会
     * @param alumniQuery
     * @return
     */
    UnifiedResponse queryAlumniService(AlumniQuery alumniQuery);
}
