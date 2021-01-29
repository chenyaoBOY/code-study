package org.study.demo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author chenyao
 * @date 2021/1/29 10:55
 * @description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QueryInfo {

    private String queryDsl;
    private String indexName;

}
