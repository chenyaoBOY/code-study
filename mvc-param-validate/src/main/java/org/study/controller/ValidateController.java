package org.study.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.study.vo.PageVo;
import org.study.vo.PageVo2;
import org.study.vo.RefundParam;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * @author chenyao
 * @date 2020/12/15 10:22
 * @description
 */
@RestController
public class ValidateController {


    /**
     * 校验不通过
     * {
     *     "timestamp": "2020-12-15T08:18:16.116+0000",
     *     "status": 400,
     *     "error": "Bad Request",
     *     "errors": [
     *         {
     *             "codes": [
     *                 "NotNull.pageVo.pageNum",
     *                 "NotNull.pageNum",
     *                 "NotNull.java.lang.Integer",
     *                 "NotNull"
     *             ],
     *             "arguments": [
     *                 {
     *                     "codes": [
     *                         "pageVo.pageNum",
     *                         "pageNum"
     *                     ],
     *                     "arguments": null,
     *                     "defaultMessage": "pageNum",
     *                     "code": "pageNum"
     *                 }
     *             ],
     *             "defaultMessage": "当前页不能为空",
     *             "objectName": "pageVo",
     *             "field": "pageNum",
     *             "rejectedValue": null,
     *             "bindingFailure": false,
     *             "code": "NotNull"
     *         },
     *         {
     *             "codes": [
     *                 "NotNull.pageVo.pageSize",
     *                 "NotNull.pageSize",
     *                 "NotNull.java.lang.Integer",
     *                 "NotNull"
     *             ],
     *             "arguments": [
     *                 {
     *                     "codes": [
     *                         "pageVo.pageSize",
     *                         "pageSize"
     *                     ],
     *                     "arguments": null,
     *                     "defaultMessage": "pageSize",
     *                     "code": "pageSize"
     *                 }
     *             ],
     *             "defaultMessage": "分页大小不能为空",
     *             "objectName": "pageVo",
     *             "field": "pageSize",
     *             "rejectedValue": null,
     *             "bindingFailure": false,
     *             "code": "NotNull"
     *         },
     *         {
     *             "codes": [
     *                 "NotBlank.pageVo.name",
     *                 "NotBlank.name",
     *                 "NotBlank.java.lang.String",
     *                 "NotBlank"
     *             ],
     *             "arguments": [
     *                 {
     *                     "codes": [
     *                         "pageVo.name",
     *                         "name"
     *                     ],
     *                     "arguments": null,
     *                     "defaultMessage": "name",
     *                     "code": "name"
     *                 }
     *             ],
     *             "defaultMessage": "name 不能为空",
     *             "objectName": "pageVo",
     *             "field": "name",
     *             "rejectedValue": null,
     *             "bindingFailure": false,
     *             "code": "NotBlank"
     *         }
     *     ],
     *     "message": "Validation failed for object='pageVo'. Error count: 3",
     *     "path": "/page"
     * }
     * @param vo
     * @return
     */
    @PostMapping("/page")
    public String get(@RequestBody @Valid PageVo vo){
        /**
         * 对应的入参添加  @Valid 注解 即可
         * 但是返回的内容为原生
         */
        return "ok";
    }
    @PostMapping("/page3")
    public String get3(@RequestBody PageVo vo){
        /**
         * 需要使用AOP 生效 {@link org.study.aop.ControllerAop}
         */
        return "ok";
    }
    @PostMapping("/page4")
    public String get3(@RequestBody PageVo2 vo){
        return "ok";
    }
    @PostMapping("/page2")
    public String get2( @NotBlank(message = "name不可为空") @Valid String name){
        /**
         * 不生效
         */
        return "ok";
    }
    @PostMapping("/enum")
    public String enumTest(@RequestBody RefundParam param){
        /**
         * 不生效
         */
        return "ok";
    }
}
