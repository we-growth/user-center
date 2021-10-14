package cn.wegrowth.usercenter.domain.user

import org.hibernate.annotations.GenericGenerator
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
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

    private val username: String,

    private val password: String?,

    private val wechatOpenId: String?,

    @Column(name = "create_date")
    private var createDate: OffsetDateTime? = null
) : UserDetails, Serializable {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf<GrantedAuthority>(SimpleGrantedAuthority("ROLE_ADMIN"))
    }

    override fun getPassword(): String? {
        return password
    }

    override fun getUsername(): String {
        return username
    }


    override fun isAccountNonExpired(): Boolean {
        return true
    }


    override fun isAccountNonLocked(): Boolean {
        return true
    }

    /**
     * Indicates whether the user's credentials (password) has expired. Expired
     * credentials prevent authentication.
     * @return `true` if the user's credentials are valid (ie non-expired),
     * `false` if no longer valid (ie expired)
     */
    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    /**
     * Indicates whether the user is enabled or disabled. A disabled user cannot be
     * authenticated.
     * @return `true` if the user is enabled, `false` otherwise
     */
    override fun isEnabled(): Boolean {
        return true
    }

}