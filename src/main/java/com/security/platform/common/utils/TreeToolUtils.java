package com.security.platform.common.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.security.platform.modules.system.vo.PermissionVO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @auther liupeiqing
 * @date 2019/12/18 15:17
 * @description 将list转成tree
 */
@Component
@AllArgsConstructor
public class TreeToolUtils {

    public List<PermissionVO> rootList; //根节点对象存放到这里

    public List<PermissionVO> bodyList; //其他节点存放到这里，可以包含根节点


    public List<PermissionVO> getTree(){   //调用的方法入口
        if(bodyList != null && !bodyList.isEmpty()){
            //声明一个map，用来过滤已操作过的数据
            Map<String,String> map = Maps.newHashMapWithExpectedSize(bodyList.size());
            rootList.forEach(beanTree -> getChild(beanTree,map));//传递根对象和一个空map
            return rootList;
        }
        return null;
    }

    public void getChild(PermissionVO permissionVO,Map<String,String> map){
        List<PermissionVO> childList = Lists.newArrayList();
        bodyList.stream()
                .filter(c -> !map.containsKey(c.getId()))//map内不包含子节点的code
                .filter(c ->c.getParentId().equals(permissionVO.getId()))//子节点的父id==根节点的code 继续循环
                .forEach(c ->{
                    map.put(c.getId(),c.getParentId());//当前节点code和父节点id
                    getChild(c,map);//递归调用
                    childList.add(c);
                });
        permissionVO.setChildren(childList);
    }

    public static void main(String[] args){
        PermissionVO beanTree1 = new PermissionVO();
        beanTree1.setId("540000");
        beanTree1.setName("西藏省");
        beanTree1.setParentId("100000"); //最高节点
        PermissionVO beanTree2 = new PermissionVO();
        beanTree2.setId("540100");
        beanTree2.setName("拉萨市");
        beanTree2.setParentId("540000");
        PermissionVO beanTree3 = new PermissionVO();
        beanTree3.setId("540300");
        beanTree3.setName("昌都市");
        beanTree3.setParentId("540000");
        PermissionVO beanTree4 = new PermissionVO();
        beanTree4.setId("540121");
        beanTree4.setName("林周县");
        beanTree4.setParentId("540100");
        PermissionVO beanTree5 = new PermissionVO();
        beanTree5.setId("540121206");
        beanTree5.setName("阿朗乡");
        beanTree5.setParentId("540121");
        PermissionVO beanTree6 = new PermissionVO();
        List<PermissionVO> rootList = new ArrayList<>();
        rootList.add(beanTree1);
        List<PermissionVO> bodyList = new ArrayList<>();
        bodyList.add(beanTree1);
        bodyList.add(beanTree2);
        bodyList.add(beanTree3);
        bodyList.add(beanTree4);
        bodyList.add(beanTree5);
        TreeToolUtils utils =  new TreeToolUtils(rootList,bodyList);
        List<PermissionVO> result =  utils.getTree();
        result.get(0);
    }


}
