package am.jobspace.common.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.io.Serializable;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user")
public class User implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @Column
  @NotNull
  @Size(min = 4, max = 10, message = "{name.notempty}")
  private String name;
  @Column
  private String surname;
  @Column
  private String number;
  @Column
  private String email;
  @Column
  @Enumerated(EnumType.STRING)
  private Gender gender;
  @Column
  private String password;
  @Column(name = "user_type")
  @Enumerated(EnumType.STRING)
  private UserType userType;
  @Column(name = "pic_url")
  private String picUrl;
  @Temporal(TemporalType.DATE)
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  @Column
  private Date profileCreated;


  @Column
  private String country;
}
