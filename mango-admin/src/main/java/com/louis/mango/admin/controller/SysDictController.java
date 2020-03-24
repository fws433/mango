package com.louis.mango.admin.controller;

import com.louis.mango.admin.model.SysDict;
import com.louis.mango.admin.service.SysDictService;
import com.louis.mangocore.http.HttpResult;
import com.louis.mangocore.page.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("dict")
public class SysDictController {
    @Autowired
    private SysDictService sysDictService;
    @PreAuthorize("hasAuthority('sys:dict:add')AND hasAuthority('sys:dict:edit')")
    @PostMapping(value = "/save")
    public HttpResult save(@RequestBody SysDict record){

        return HttpResult.ok(sysDictService.save(record));
    }
    @PreAuthorize("hasAuthority('sys:dict；delete')")
    @PostMapping(value = "/delete")
    public HttpResult delete(@RequestBody List<SysDict> records){
        return HttpResult.ok(sysDictService.delete(records));
    }
    @PreAuthorize("hasAuthority('sys:dict:view')")
    @PostMapping(value = "/findPage")
    public HttpResult findPage(@RequestBody PageRequest pageRequest){
        return HttpResult.ok(sysDictService.findPage(pageRequest));
    }
    @PreAuthorize("hasAuthority('sys:dict:view')")
    @GetMapping(value="/findByLabel")
    public HttpResult findByLabel(@RequestParam String lable){
        return HttpResult.ok(sysDictService.findByLabel(lable));
    }


}
