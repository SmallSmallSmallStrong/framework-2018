package com.sdyijia.modules.sys.service;

import com.sdyijia.config.shiro.MyShiroRealm;
import com.sdyijia.exception.SaveException;
import com.sdyijia.modules.base.service.BaseService;
import com.sdyijia.modules.sys.bean.SysPermission;
import com.sdyijia.modules.sys.repository.SysPermissionRepository;
import com.sdyijia.utils.SpringUtil;
import com.sdyijia.utils.tool.ToolStr;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service("permissionService")
public class PermissionService extends BaseService<SysPermissionRepository, SysPermission> {

    @Autowired
    private SysPermissionRepository sysPermissionRepository;

    /**
     * 保存
     *
     * @param permission
     * @param request
     * @throws SaveException
     */
    public void save(SysPermission permission, HttpServletRequest request) throws SaveException {
        if (permission == null) {
            throw new SaveException("传参错误，请检查");
        }
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        /** 页面控件的文件流* */
        MultipartFile multipartFile = multipartRequest.getFile("file");
        Long dst = new Date().getTime();
        String newFileName = SpringUtil.saveFile(multipartFile, UPLOADURL, ToolStr.L2S(dst), request);
        if (newFileName != null) {
            permission.setImgUrl(UPLOADURL + "/" + newFileName);
        }
        try {
            if (permission.getId() == null || permission.getId() <= 0) {
                //save
                sysPermissionRepository.save(permission);
            } else {
                SysPermission dbpermission = sysPermissionRepository.getOne(permission.getId());
                sysPermissionRepository.save(permission);
            }
        } catch (Exception e) {
            throw new SaveException(e.getMessage());
        }
    }
 

    /**
     * 清除全部缓存信息
     */
    public void clearAllCache() {
        RealmSecurityManager securityManager = (RealmSecurityManager) SecurityUtils.getSecurityManager();
        MyShiroRealm permissionRealm = (MyShiroRealm) securityManager.getRealms().iterator().next();
        permissionRealm.clearAllCache();
    }

}
