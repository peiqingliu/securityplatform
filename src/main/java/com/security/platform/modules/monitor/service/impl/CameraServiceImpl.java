package com.security.platform.modules.monitor.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.security.platform.common.vo.SearchVo;
import com.security.platform.modules.monitor.dao.CameraDao;
import com.security.platform.modules.monitor.entity.Camera;
import com.security.platform.modules.monitor.service.CameraService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @version 1.0
 * @auther liupeiqing
 * @date 2019/12/20 11:32
 * @description 摄像头实现层
 */
@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class CameraServiceImpl implements CameraService {

    CameraDao cameraDao;

    @Override
    public CameraDao getRepository() {
        return cameraDao;
    }

    @Override
    public Page<Camera> findByCondition(Camera camera, SearchVo searchVo, Pageable pageable) {

        return cameraDao.findAll(new Specification<Camera>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<Camera> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

                Path<String> devcieIdFiled = root.get("devcieId");
                Path<String> cameraNameFiled = root.get("cameraName");
                Path<String> cameraCodeField = root.get("cameraCode");
                Path<String> cameraModelField = root.get("cameraModel");
                Path<String> deviceIpField = root.get("deviceIp");
                Path<Integer> statusField = root.get("status");
                Path<Date> createTimeField = root.get("createTime");


                List<Predicate> list = new ArrayList<Predicate>();
                //设备id
                if (StrUtil.isNotBlank(camera.getDevcieId())){
                    list.add(cb.equal(cameraNameFiled,camera.getDevcieId()));
                }
                //名称
                if (StrUtil.isNotBlank(camera.getCameraName())){
                    list.add(cb.like(cameraNameFiled,'%' + camera.getCameraName() + '%'));
                }
                //编码
                if (StrUtil.isNotBlank(camera.getCameraCode())){
                    list.add(cb.equal(cameraCodeField, camera.getCameraCode()));
                }
                //型号
                if (StrUtil.isNotBlank(camera.getCameraModel())){
                    list.add(cb.like(cameraModelField,'%' + camera.getCameraModel() + '%'));
                }
                //ip
                if (StrUtil.isNotBlank(camera.getDeviceIp())){
                    list.add(cb.equal(deviceIpField,camera.getDeviceIp()));
                }

                //状态
             /*   if (null != camera.getStatus()){
                    list.add(cb.equal(statusField,camera.getStatus()));
                }*/

                //创建时间
                if (StrUtil.isNotBlank(searchVo.getStartDate()) && StrUtil.isNotBlank(searchVo.getEndDate())){
                    Date start = DateUtil.parse(searchVo.getStartDate());
                    Date end = DateUtil.parse(searchVo.getEndDate());
                    list.add(cb.between(createTimeField,start,DateUtil.endOfDay(end)));
                }

                //数据权限

                Predicate[] arr = new Predicate[list.size()];
                cq.where(list.toArray(arr));
                return null;
            }
        },pageable);
    }

    @Override
    public Camera findByDevcieId(String devcieId) {
        return cameraDao.findByDevcieId(devcieId);
    }

    @Override
    public Camera findByLoginHandle(Long loginHandle) {
        return cameraDao.findByLoginHandle(loginHandle);
    }

    @Override
    public List<Camera> findByGroupId(String groupId) {
        return cameraDao.findAllByGroupId(groupId);
    }

    @Override
    public Long count() {
        return cameraDao.count();
    }


    @Override
    public long countByLoginHandleNot(long loginHandle) {
        return cameraDao.countDistinctByLoginHandleNot(loginHandle);
    }
}
