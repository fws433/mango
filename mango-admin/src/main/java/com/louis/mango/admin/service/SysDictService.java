package com.louis.mango.admin.service;

import com.louis.mango.admin.model.SysDict;
import com.louis.mangocore.service.CurdService;

import java.util.List;

public interface SysDictService extends CurdService<SysDict> {
    List<SysDict>findByLabel(String lable);
}
