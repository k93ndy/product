package com.demogroup.product.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(
  name = "product",
  uniqueConstraints={@UniqueConstraint(columnNames={"id", "name"})}
)
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @Column(name = "name", nullable=false)
  private String name;
  
  @OneToMany(cascade=CascadeType.ALL, mappedBy="product")
  private Set<Attribute> attribute = new HashSet<>();

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
  
  public void setAttributes(Set<Attribute> attribute) {
    this.attribute = attribute;
  }
  
  public Set<Attribute> getAttributes() {
    return attribute;
  }

}