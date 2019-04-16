package am.jobspace.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "post")
public class Post {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @Column
  private String shortDescription;
  @Column
  private String title;
  @Column
  private String description;
  @ManyToOne
  private Category category;
  @ManyToOne
  private User user;
  @Temporal(TemporalType.TIMESTAMP)
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  @Column
  private Date postDate;
  @Column
  private Double price;
//  @OneToMany
//  private Images images;
  @OneToMany
  @JoinColumn(name = "pic_id",referencedColumnName = "id")
  private Set<Images> images=new HashSet<>();
}

