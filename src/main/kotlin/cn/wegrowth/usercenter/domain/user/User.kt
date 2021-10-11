package cn.wegrowth.usercenter.domain.user

import org.hibernate.annotations.GenericGenerator
import java.io.Serializable
import java.time.OffsetDateTime
import javax.persistence.*

@Entity
@Table(name = "users")
class User(
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    val id: String? = null,
    val username: String,
    val password: String,
    @Column(name = "create_date")
    private var createDate: OffsetDateTime? = null
) : Serializable {

}