package am.jobspace.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "ads")
public class Ads {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;
    @Column
    private String title;
    @Column
    private String shortText;
    @Column(columnDefinition="text")
    private String text;
    @ManyToOne
    private Category category;
    @ManyToOne
    private User user;
    @Column(name="pic_url")
    private String picUrl;
}
