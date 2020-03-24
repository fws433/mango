package com.louis.mango.admin.service.impl;

import com.louis.mango.admin.constant.SysConstants;
import com.louis.mango.admin.dao.SysMenuMapper;
import com.louis.mango.admin.model.SysMenu;
import com.louis.mango.admin.service.SysMenuService;
import com.louis.mangocore.page.PageRequest;
import com.louis.mangocore.page.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SysMenuServiceImpl implements SysMenuService {
        @Autowired
        private SysMenuMapper sysMenuMapper;

    @Override
    public List<SysMenu> findTree(String userName, int menuType) {
        return null;
    }

    @Override
    public List<SysMenu> findByUser(String userName) {
        if(userName==null ||  SysConstants.ADMIN.equalsIgnoreCase(userName)){
            return sysMenuMapper.findAll();
        }
        return sysMenuMapper.findByUserName(userName);
    }


    @Override
    public int save(SysMenu record) {
        return 0;
    }

    @Override
    public int delete(SysMenu record) {
        return 0;
    }

    @Override
    public int delete(List<SysMenu> records) {
        return 0;
    }

    @Override
    public SysMenu findById(Long id) {
        return null;
    }

    @Override
    public PageResult findPage(PageRequest pageRequest) {
        return null;
    }
}
