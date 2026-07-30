package com.ruoyi.lite.core.base.framework.web.domain;

import cn.hutool.core.collection.CollUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ruoyi.lite.core.module.system.domain.SysDept;
import com.ruoyi.lite.core.module.system.domain.SysMenu;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Treeselect树结构实体类
 *
 * @author fooyao
 */
@Data
public class TreeSelect implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /** 节点ID */
    private Long id;

    /** 节点名称 */
    private String label;

    /** 是否有子节点（用于懒加载） */
    private Boolean hasChildren;

    /** 子节点 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<TreeSelect> children;

    public TreeSelect() {

    }

    public TreeSelect(SysDept dept) {
        this.id = dept.getDeptId();
        this.label = dept.getDeptName();
        this.hasChildren = dept.getChildren() != null && !dept.getChildren().isEmpty();
        this.children = dept.getChildren().stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    /**
     * 懒加载模式构造函数（不递归加载子节点）
     */
    public TreeSelect(SysDept dept, boolean lazy) {
        this.id = dept.getDeptId();
        this.label = dept.getDeptName();
        if (lazy) {
            // 懒加载模式：只标记是否有子节点，不加载子节点
            this.hasChildren = CollUtil.isNotEmpty(dept.getChildren());
            this.children = null;
        } else {
            this.hasChildren = dept.getChildren() != null && !dept.getChildren().isEmpty();
            this.children = dept.getChildren().stream().map(TreeSelect::new).collect(Collectors.toList());
        }
    }

    public TreeSelect(SysMenu menu) {
        this.id = menu.getMenuId();
        this.label = menu.getMenuName();
        this.children = menu.getChildren().stream().map(TreeSelect::new).collect(Collectors.toList());
    }

}
