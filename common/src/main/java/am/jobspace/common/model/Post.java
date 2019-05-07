package am.jobspace.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

import java.util.HashSet;
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
  private String title;
  @Column
  private String description;
  @Column
  private String shortDescription;
  @ManyToOne
  private Category category;
  @ManyToOne
  private User user;
  @Temporal(TemporalType.DATE)
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  @Column
  private Date postDate;
  @Column
  private String location;
  @Column
  private Double salary;
  @Column
  private String picUrl;
  @Column
  private boolean saved;
  @Column
  private int view;

  @Column(name = "approved")
  @Enumerated(EnumType.STRING)
  private PostApproved postApproved=PostApproved.PASSIVE;
}

