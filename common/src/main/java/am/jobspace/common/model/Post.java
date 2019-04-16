package am.jobspace.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "post")
public class Post  {

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
  @DateTimeFormat(iso= DateTimeFormat.ISO.DATE)
  @Column
  private Date postDate;
}

