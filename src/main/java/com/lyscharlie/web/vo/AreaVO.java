package com.lyscharlie.web.vo;

import java.util.ArrayList;
import java.util.List;

public class AreaVO {

	private Long id;

	private String name;

	private Long parentId;

	private List<AreaVO> children;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public List<AreaVO> getChildren() {
		return children;
	}

	public void setChildren(List<AreaVO> children) {
		this.children = children;
	}

	public void addChild(AreaVO area) {
		if (null == this.children) {
			this.children = new ArrayList<AreaVO>();
		}
		this.children.add(area);
	}

}
