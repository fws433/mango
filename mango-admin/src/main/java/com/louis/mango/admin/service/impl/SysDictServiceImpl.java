package com.louis.mango.admin.service.impl;

import com.louis.mango.admin.dao.SysDictMapper;
import com.louis.mango.admin.model.SysDict;
import com.louis.mango.admin.service.SysDictService;
import com.louis.mangocore.page.MybatisPageHelper;
import com.louis.mangocore.page.PageRequest;
import com.louis.mangocore.page.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SysDictServiceImpl implements SysDictService {
    @Autowired
    private SysDictMapper sysDictMapper;
    @Override
    public List<SysDict> findByLabel(String label) {
        return sysDictMapper.findByLabel(label);
    }

    @Override
    public int save(SysDict record) {
        if(record.getId()==null||record.getId()==0){
            return sysDictMapper.insertSelective(record);
        }
        return sysDictMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int delete(SysDict record) {

        return sysDictMapper.deleteByPrimaryKey(record.getId());
    }

    @Override
    public int delete(List<SysDict> records) {
        for (SysDict record:records){
            delete(record);
        }
        return 1;
    }

    @Override
    public SysDict findById(Long id) {
        return sysDictMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageResult findPage(PageRequest pageRequest) {
        Object label=pageRequest.getParam("label");
        if(label!=null){
            return MybatisPageHelper.findPage(pageRequest,sysDictMapper,
                    "findPageByLabel",label);
        }
        return MybatisPageHelper.findPage(pageRequest,sysDictMapper);
    }
}
