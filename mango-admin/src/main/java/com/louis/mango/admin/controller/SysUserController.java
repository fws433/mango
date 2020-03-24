package com.louis.mango.admin.controller;

import com.alibaba.druid.pool.vendor.SybaseExceptionSorter;
import com.louis.mango.admin.constant.SysConstants;
import com.louis.mango.admin.model.SysUser;
import com.louis.mango.admin.service.SysUserService;
import com.louis.mango.admin.util.PasswordUtils;
import com.louis.mangocore.http.HttpResult;
import com.louis.mangocore.page.PageRequest;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/*
 * 1调用service层中继承的crud里面的save方法查询形参对象record的ID编号是否在数据库中存在，
 * 如果没有这个编号，将方法体中user对象设置为null，如果有这个编号id,将这个id在数据库所存的对象赋给user对象，此时user对相不为null
 *
 * 2如果user对象不为Null,则判断数据库中该位置对象是不是为管理员，如果是就直接返回显示超级管理员不能修改
 * 3.判断record形参用户密码是不是等于null，如果不等于null,判断方法体中user对象是否等于null:如果等于null，那么就新增用户，但是
 * 在新增前调用UserService层findByName方法判断形参record用户对象名字在数据库中是否存在，如果存在则返回并且输出用户名已经存在,
 * 如果不存在则调用service层save方法添加用户对象到数据库
 *
 *
 *
 * */
@RestController
@RequestMapping("user")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;
    @PreAuthorize("hasAuthority('sys:user:view')")
    @GetMapping(value = "/findAll")
    public Object findAll(){

        return sysUserService.findAll();
    }
    @PreAuthorize("hasAuthority('sys:user:add') AND hasAuthority('sys:user:edit')")
    @PostMapping(value="/save")
    public HttpResult save(@RequestBody SysUser record) {

    /*1调用service层中继承的crud里面的save方法查询形参对象record的ID编号是否在数据库中存在，
    如果没有这个编号，将方法体中user对象设置为null，如果有这个编号id,将这个id在数据库所存的对象赋给user对象，此时user对相不为null
      */
    SysUser user = sysUserService.findById(record.getId());
    /*
    * 2如果user对象不为Null,则判断数据库中该位置对象是不是为管理员，如果是就直接返回显示超级管理员不能修改
    * */
        if (user != null) {
            if (SysConstants.ADMIN.equalsIgnoreCase(user.getName())) {
                return HttpResult.error("超级管理员不允许修改!");
            }
        }
    /*
    *  3.判断record形参用户密码是不是等于null，如果不等于null,判断方法体中user对象是否等于null:如果等于null，那么就新增用户，但是
    * 在新增前调用UserService层findByName方法判断形参record用户对象名字在数据库中是否存在，如果存在则返回并且输出用户名已经存在,
    * 如果不存在则调用service层save方法添加用户对象到数据库.
    * 4.如果user对象不等于null，那么我们就判断形参对象record和user对象（该id编号在数据库中对应的对象）的密码是否相等，
    * 不相等就修改密码，如果相等就不用操作，如果相等就显示用户名已存在
    * */
        if (record.getPassword() != null) {
            String salt = PasswordUtils.getSalt();
            if (user == null) {
                // 新增用户
                if (sysUserService.findByName(record.getName()) != null) {
                    return HttpResult.error("用户名已存在!");
                }
                String password = PasswordUtils.encode(record.getPassword(), salt);
                record.setSalt(salt);
                record.setPassword(password);
            } else {
                // 修改用户, 且修改了密码
                if (!record.getPassword().equals(user.getPassword())) {
                    String password = PasswordUtils.encode(record.getPassword(), salt);
                    record.setSalt(salt);
                    record.setPassword(password);
                }
            }
        }



        return HttpResult.ok(sysUserService.save(record));
    }
    @PostMapping(value="/delete")
    public HttpResult delete(@RequestBody List<SysUser> records){
        for (SysUser record:records){
            SysUser sysUser=sysUserService.findById(record.getId());
            if (sysUser !=null&& SysConstants.ADMIN.equalsIgnoreCase(sysUser.getName())){
                return HttpResult.error("超级管理员不允许删除");
            }

        }
        return HttpResult.ok(sysUserService.delete(records));
    }
    @PreAuthorize("hasAuthority('sys:user:view')")
    @PostMapping(value="/findPage")
    public HttpResult findPage(@RequestBody PageRequest pageRequest){
        return HttpResult.ok(sysUserService.findPage(pageRequest));
    }
    @PreAuthorize("hasAuthority('sys:user:view')")
    @GetMapping(value = "/findByName")
    public HttpResult findByName(@RequestParam String name){

        return HttpResult.ok(sysUserService.findByName(name));
    }
    @PreAuthorize("hasAuthority('sys:user:view')")
    @GetMapping(value="/findPermissions")
    public HttpResult findPermissions(@RequestParam String name){
        return HttpResult.ok(sysUserService.findPermissions(name));
    }
    @PreAuthorize("hasAuthority('sys:user:view')")
    @GetMapping(value="/findUserRoles")
    public HttpResult findUserRoles(@RequestParam Long userId){
        return HttpResult.ok(sysUserService.findUserRoles(userId));
    }
}
