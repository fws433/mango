package com.louis.mango.admin.service.impl;


import com.louis.mango.admin.dao.SysRoleMapper;
import com.louis.mango.admin.dao.SysUserMapper;
import com.louis.mango.admin.dao.SysUserRoleMapper;
//import com.louis.mango.admin.model.SysUser;
//import com.louis.mango.admin.model.SysUserRole;
import com.louis.mango.admin.model.SysMenu;
import com.louis.mango.admin.model.SysRole;
import com.louis.mango.admin.model.SysUser;
import com.louis.mango.admin.model.SysUserRole;
import com.louis.mango.admin.service.SysMenuService;
import com.louis.mango.admin.service.SysUserService;
import com.louis.mangocore.page.MybatisPageHelper;
import com.louis.mangocore.page.PageRequest;
import com.louis.mangocore.page.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.*;

@Service

public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    @Autowired
    private SysMenuService sysMenuService;

   /* @Autowired
    private SysMenuServiceImpl sysMenuService;*/
    @Override
    public List<SysUser> findAll() {
        return null;
    }

    @Override
    public SysUser findByName(String username) {

        return sysUserMapper.findByName(username);
    }

    @Override
    public Set<String> findPermissions(String userName) {
        Set<String>perms=new HashSet<>();
        List<SysMenu>sysMenus=sysMenuService.findByUser(userName);
        for(SysMenu sysMenu: sysMenus){
            if(sysMenu.getPerms()!=null&&!"".equals(sysMenu.getPerms())){
                perms.add(sysMenu.getPerms());
            }
        }
        return perms;
    }
   //查询用户角色
    @Override
    public List<SysUserRole> findUserRoles(Long userId) {
        return sysUserRoleMapper.findUserRoles(userId);
    }

    @Transactional
    @Override
    //保存用户
    public int save(SysUser record) {

           Long id=null;
           if(record.getId()==null || record.getId()==0){
               //新增用户
               sysUserMapper.insertSelective(record);
               id=record.getId();
           }else {
               sysUserMapper.updateByPrimaryKeySelective(record);
           }
            // 更新用户角色
            if (id != null && id == 0) {
                return 1;
            }
            if (id != null) {
                for (SysUserRole sysUserRole : record.getUserRoles()) {
                    sysUserRole.setUserId(id);
                }
            } else {
                sysUserRoleMapper.deleteByUserId(record.getId());
            }
            for (SysUserRole sysUserRole : record.getUserRoles()) {
                sysUserRoleMapper.insertSelective(sysUserRole);
            }
            return 1;
        }
    @Override
    //删除用户
    public int delete(SysUser record) {

        return sysUserMapper.deleteByPrimaryKey(record.getId());
        //return 0;
    }

    @Override
    public int delete(List<SysUser> records) {

        for (SysUser record : records) {
            delete(record);
        }
        return 1;
    }

    @Override
    public SysUser findById(Long id) {
        return sysUserMapper.selectByPrimaryKey(id);
       // return null;
    }

    @Override
    public PageResult findPage(PageRequest pageRequest) {
        PageResult pageResult = null;
        Object name = pageRequest.getParam("name");
        Object email = pageRequest.getParam("email");
        if (name != null) {
            if (email != null) {
                pageResult = MybatisPageHelper.findPage(pageRequest, sysUserMapper, "findPageByNameAndEmail", name, email);
            } else {
                pageResult = MybatisPageHelper.findPage(pageRequest, sysUserMapper, "findPageByName", name);
            }
        } else {
            pageResult = MybatisPageHelper.findPage(pageRequest, sysUserMapper);
        }
        // 加载用户角色信息
        findUserRoles(pageResult);
        return pageResult;

    }
    private void findUserRoles(PageResult pageResult) {
        List<?> content = pageResult.getContent();
        for (Object object : content) {
            SysUser sysUser = (SysUser) object;
            List<SysUserRole> userRoles = findUserRoles(sysUser.getId());
            sysUser.setUserRoles(userRoles);
            sysUser.setRoleNames(getRoleNames(userRoles));
        }
    }
    private String getRoleNames(List<SysUserRole> userRoles) {
        StringBuilder sb = new StringBuilder();
        for (Iterator<SysUserRole> iter = userRoles.iterator(); iter.hasNext(); ) {
            SysUserRole userRole = iter.next();
            SysRole sysRole = sysRoleMapper.selectByPrimaryKey(userRole.getRoleId());
            if (sysRole == null) {
                continue;
            }
            sb.append(sysRole.getRemark());
            if (iter.hasNext()) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }




}
